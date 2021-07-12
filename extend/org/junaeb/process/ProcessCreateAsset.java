/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.junaeb.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAsset;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.X_A_Asset_Use;
import org.compiere.model.X_C_InvoiceLine;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.X_A_Depreciation_Workfile;
import org.ofb.model.OFBForward;
/**
 *	
 *	
 *  @author Italo Niñoles ininoles 
 *  @version $Id: ProcessCreateAsset,java $
 */
public class ProcessCreateAsset extends SvrProcess
{
	private int				p_ID_Invoice = 0; 
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		/*ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if (name.equals("Action"))
				p_Action = para[i].getParameterAsString();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}*/
		 p_ID_Invoice=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_ID_Invoice > 0)
		{	
			MInvoice inv = new MInvoice(getCtx(), p_ID_Invoice, get_TrxName());
			MDocType dType = new MDocType(getCtx(), inv.getC_DocTypeTarget_ID(), get_TrxName());
			
			//Primero se contaran las lineas asociadas a la cabecera de documento
			String sqlcount = "SELECT count(1) from c_invoiceline where c_invoice_id = "+inv.get_ID();
			int counter = DB.getSQLValue(get_TrxName(), sqlcount);
			if(counter <= 0)
			{
				inv.set_CustomColumn("ErrorMsg", "DEBE INGRESAR LINEAS DE DOCUMENTO");
				inv.save();
				return "";

			}
			else
				inv.set_CustomColumn("ErrorMsg", "");
			
			if(inv.getDocStatus().compareTo("DR") == 0 && dType.get_ValueAsString("Type1").compareTo("ALT") == 0)
			{
				//alta
				//validacion de cantidad de lineas //30-01-2018 validacion se haa con model por problemas en web
				int cantLine = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_InvoiceLine " +
						"WHERE IsActive = 'Y' AND C_Invoice_ID = "+inv.get_ID());
				String msgError = null;
				if(cantLine != inv.get_ValueAsInt("ValidCantLine"))
				{
					//Se cambia mensajes de error a mayúsculas
					msgError= "ERROR EN CAMPO: CANTIDAD DE ACTIVOS NO CORRESPONDE CON LINEAS";
					String nameField = DB.getSQLValueString(get_TrxName(), "SELECT name FROM AD_Field_Trl " +
							" WHERE AD_Field_ID=2003920 AND AD_Language='es_MX'");
					if(nameField != null && nameField.trim().length() > 0)
						msgError = "ERROR EN CAMPO "+nameField+": CANTIDAD DE ACTIVOS NO CORRESPONDE CON LINEAS";
				} //30-01-2018 end
				if(msgError != null && msgError.trim().length() > 0)
				{//seteo de error
					inv.set_CustomColumn("ErrorMsg", msgError);
				} 
				else
				{
					createAsset(inv, dType);			
					//Al completar solo se quedara el processed el documento y cambiara el estado
					inv.set_CustomColumn("ErrorMsg", "PROCESO DE ALTA COMPLETADO");
					inv.setDocStatus("CO");
					inv.setProcessed(true);
				}
			}
			else if(inv.getDocStatus().compareTo("DR") == 0 && dType.get_ValueAsString("Type1").compareTo("TRA") == 0)
			{
				//traspaso
				transferAsset(inv, dType);	
				//Al completar solo se quedara el processed el documento y cambiara el estado
				inv.setDocStatus("CO");
				inv.setProcessed(true);
			}
			else if(inv.getDocStatus().compareTo("DR") == 0 && dType.get_ValueAsString("Type1").compareTo("MOD") == 0)
			{			
				//Modificacion
				updateAsset(inv, dType);
				//Al completar solo se quedara el processed el documento y cambiara el estado
				inv.setDocStatus("CO");
				inv.setProcessed(true);
			}
			else if(inv.getDocStatus().compareTo("DR") == 0 && dType.get_ValueAsString("Type1").compareTo("BAJ") == 0
					&& dType.get_ID() == 1000004)
			{
				
				//@mfrojas
				//Primero se debe validar si el valor de Fecha subasta, lote, valor subasta y martillero está lleno. Si 
				//todos son != null, el estado es completo inmediatamente. Si falta sólo uno de ellos, el estado es 
				//"En proceso".
				
				//Si flag == 1, faltan  valores. 
				int flag = 0;
				MInvoiceLine[] lines = inv.getLines(false);
				
				for(int lineIndex = 0; lineIndex < lines.length; lineIndex++)
				{
					MInvoiceLine iLine = lines[lineIndex];
					//Valor de subasta primero. 
					
					BigDecimal Vlr_Subasta = (BigDecimal)iLine.get_Value("Vlr_Subasta");
					if(Vlr_Subasta == null)
						Vlr_Subasta = Env.ZERO;
					if(Vlr_Subasta.compareTo(Env.ZERO) <= 0)
					{
						flag = 1;
					}
					
					//Fecha de subasta
					
					Timestamp dateStart = (Timestamp)iLine.get_Value("Fecha_Subasta");
					if(dateStart == null)
						flag = 1;

					//Lote
					
					String lote = iLine.get_ValueAsString("Lot");
					if(lote == null || lote == "")
						flag = 1;
					
					
					//Martillero
					
					String martillero = iLine.get_ValueAsString("Martillero");
					if(martillero == null || martillero == "")
						flag = 1;
					
				}
				if(flag == 1)
				{
					degradeAssetInProcess(inv, dType);
					inv.setDocStatus("IP");
					String updateasset = "UPDATE a_Asset SET AssetSituation='ENP' WHERE A_Asset_ID in (SELECT A_Asset_ID from " +
							" C_InvoiceLine WHERE C_Invoice_ID = "+inv.getC_Invoice_ID()+")";
					DB.executeUpdate(updateasset,inv.get_TrxName());
				}
				else if (flag == 0)
				{
					inv.set_CustomColumn("ErrorMsg", "");

					degradeAsset(inv, dType);
					inv.setDocStatus("CO");
					inv.setProcessed(true);

				}
			}
			else if(inv.getDocStatus().compareTo("DR") == 0 && dType.get_ValueAsString("Type1").compareTo("BAJ") == 0)
			{
				String msgError = null;
				MInvoiceLine[] lines = inv.getLines(false);
				for (int lineIndex = 0; lineIndex < lines.length; lineIndex++)
				{
					MInvoiceLine iLine = lines[lineIndex];
					//se valida que exista monto de subasta solo para baja enajenacion
					if(inv.getC_DocTypeTarget_ID() == 1000004)
					{
						BigDecimal Vlr_Subasta = (BigDecimal)iLine.get_Value("Vlr_Subasta");
						if(Vlr_Subasta == null)
							Vlr_Subasta = Env.ZERO;
						if(Vlr_Subasta.compareTo(Env.ZERO) <= 0)
						{
							msgError = "Todos los activos deben tener valor de subasta. Linea:"+(iLine.getLine()/10);
							break;
						}
					}
				}	
				if(msgError != null && msgError.trim().length() > 0)
				{//seteo de error
					inv.set_CustomColumn("ErrorMsg", msgError);
				} 
				else
				{
					degradeAsset(inv, dType);
					//inv.set_CustomColumn("ErrorMsg", ""); Se comenta para enviar mensaje de completitud
					inv.set_CustomColumn("ErrorMsg","");
					inv.setDocStatus("CO");
					inv.setProcessed(true);
				}
			}
			else if(inv.getDocStatus().compareTo("IP") == 0 && dType.get_ValueAsString("Type1").compareTo("BAJ") == 0
					&& dType.get_ID() == 1000004)
			{
				//@mfrojas
				//Primero se debe validar si el valor de Fecha subasta, lote, valor subasta y martillero está lleno. Si 
				//todos son != null, el estado es completo inmediatamente. Si falta sólo uno de ellos, el estado es 
				//"En proceso".
				
				//Si flag == 1, faltan  valores. 
				String msgError = null;
				int flag = 0;
				MInvoiceLine[] lines = inv.getLines(false);
				
				for(int lineIndex = 0; lineIndex < lines.length; lineIndex++)
				{
					MInvoiceLine iLine = lines[lineIndex];
					//Valor de subasta primero. 
					
					BigDecimal Vlr_Subasta = (BigDecimal)iLine.get_Value("Vlr_Subasta");
					if(Vlr_Subasta == null)
						Vlr_Subasta = Env.ZERO;
					if(Vlr_Subasta.compareTo(Env.ZERO) <= 0)
					{
						flag = 1;
					}
					
					//Fecha de subasta
					
					Timestamp dateStart = (Timestamp)iLine.get_Value("Fecha_Subasta");
					if(dateStart == null)
						flag = 1;

					//Lote
					
					String lote = iLine.get_ValueAsString("Lot");
					if(lote == null || lote == "")
						flag = 1;
					
					
					//Martillero
					
					String martillero = iLine.get_ValueAsString("Martillero");
					if(martillero == null || martillero == "")
						flag = 1;
					
				}
				if(flag == 1)
				{
					

					msgError= "RECUERDE INGRESAR VALOR VALOR SUBASTA, FECHA SUBASTA, MARTILLERO Y LOTE";
					inv.set_CustomColumn("ErrorMsg", msgError);

				}
				else if (flag == 0)
				{
					inv.set_CustomColumn("ErrorMsg", "");
					degradeAsset(inv, dType);
					inv.setDocStatus("CO");
					inv.setProcessed(true);

				}
				
			}
			
			else if(inv.getDocStatus().compareTo("IP") == 0 && dType.get_ValueAsString("Type1").compareTo("BAJ") == 0)
			{
				inv.set_CustomColumn("ErrorMsg", "");

				degradeAsset(inv, dType);
				inv.setDocStatus("CO");
				inv.setProcessed(true);
			}
			inv.saveEx();
		}
		return "Procesado";
	}	//	doIt
	public void createAsset(MInvoice inv, MDocType dType)	
	{	
		MInvoiceLine[] lines = inv.getLines(false);
		int group_ID = 0;
		for (int lineIndex = 0; lineIndex < lines.length; lineIndex++)
		{
			MInvoiceLine iLine = lines[lineIndex];
			if(isAsset(iLine) && iLine.isActive())
			{
				if(iLine.get_ValueAsString("A_CapvsExp").equals(X_C_InvoiceLine.A_CAPVSEXP_Capital)  )//crear activo
				{	
					//se actualiza grupo de ativo por debajo
					
					group_ID = iLine.get_ValueAsInt("A_Asset_Group_Ref2_ID");					
					if(group_ID <= 0)
					{
						group_ID = DB.getSQLValue(inv.get_TrxName(), "select a_asset_group_id from a_asset_group where isdefault='Y'");
						if(group_ID <= 0)
							throw new AdempiereException("no default asset group. Linea:"+(iLine.getLine()/10));
					}
					DB.executeUpdate("UPDATE C_InvoiceLine SET A_Asset_Group_ID = "+group_ID+" WHERE C_InvoiceLine_ID = "+iLine.get_ID(), get_TrxName());
					BigDecimal qtyAsset = iLine.getQtyEntered();
					MAsset asset = new MAsset(inv.getCtx(),  0 ,inv.get_TrxName());
					asset.setName(iLine.getDescription());
					asset.setAD_Org_ID(iLine.getAD_Org_ID());
					asset.setAssetServiceDate(inv.getDateInvoiced());
					asset.setQty(qtyAsset); //se ocupa variable manejable
					asset.setA_Asset_Group_ID(group_ID);					
					if(iLine.get_ValueAsInt("AD_User_ID") > 0)
						asset.setAD_User_ID(iLine.get_ValueAsInt("AD_user_ID"));
					//se agregan nuevos campos mop
					asset.set_CustomColumn("Brand", iLine.get_ValueAsString("Brand"));
					asset.set_CustomColumn("Model", iLine.get_ValueAsString("Model"));
					asset.set_CustomColumn("SerNo", iLine.get_ValueAsString("SerNo"));
					asset.set_CustomColumn("Color", iLine.get_ValueAsString("Color"));
					if(iLine.get_ValueAsString("AssetStatus") != null && iLine.get_ValueAsString("AssetStatus").trim().length() > 0)
						asset.set_CustomColumn("AssetStatus", iLine.get_ValueAsString("AssetStatus"));
					asset.set_CustomColumn("A_Parent_Asset_ID", iLine.get_ValueAsInt("A_Parent_Asset_ID"));
					if(iLine.get_ValueAsString("Type1") != null && iLine.get_ValueAsString("Type1").trim().length() > 0)
						asset.set_CustomColumn("Type1", iLine.get_ValueAsString("Type1"));
					asset.set_CustomColumn("Rol", iLine.get_ValueAsString("Rol"));
					asset.set_CustomColumn("GroundSurface", (BigDecimal)iLine.get_Value("GroundSurface"));
					asset.set_CustomColumn("EdificationSurface",(BigDecimal)iLine.get_Value("EdificationSurface"));
					asset.set_CustomColumn("Engine", iLine.get_ValueAsString("Engine"));
					asset.set_CustomColumn("Chassis", iLine.get_ValueAsString("Chassis"));
					asset.set_CustomColumn("LicensePlate", iLine.get_ValueAsString("LicensePlate"));
					asset.set_CustomColumn("Digito", iLine.get_ValueAsString("Digito"));
					asset.set_CustomColumn("Sigla", iLine.get_ValueAsString("Sigla"));
					asset.set_CustomColumn("Fojas", iLine.get_ValueAsString("Fojas"));
					asset.set_CustomColumn("RegistrationNumber", iLine.get_ValueAsString("RegistrationNumber"));
					asset.set_CustomColumn("Year", iLine.get_ValueAsString("Year"));
					if(iLine.get_ValueAsString("Inmueble")!= null)
						asset.set_CustomColumn("Inmueble", iLine.get_ValueAsString("Inmueble"));
					if(iLine.get_ValueAsInt("C_Region_ID") > 0)
						asset.set_CustomColumn("C_Region_ID", iLine.get_ValueAsInt("C_Region_ID"));
					if(iLine.get_ValueAsInt("C_Province_ID") > 0)
						asset.set_CustomColumn("C_Province_ID", iLine.get_ValueAsInt("C_Province_ID"));
					if(iLine.get_ValueAsInt("C_City_ID") > 0)
						asset.set_CustomColumn("C_City_ID", iLine.get_ValueAsString("C_City_ID"));
					if(iLine.get_ValueAsString("DocDestination") != null && iLine.get_ValueAsString("DocDestination").trim().length() > 0)
						asset.set_CustomColumn("DocDestination", iLine.get_ValueAsString("DocDestination"));
					asset.set_CustomColumn("DocNumber", iLine.get_ValueAsString("DocNumber"));
					asset.set_CustomColumn("Conservador", iLine.get_ValueAsString("Conservador"));
					asset.set_CustomColumn("Notaria", iLine.get_ValueAsString("Notaria"));
					asset.set_CustomColumn("N_Fecha_ValeVista", iLine.get_ValueAsString("N_Fecha_ValeVista"));
					asset.set_CustomColumn("Vlr_ValeVista",(BigDecimal)iLine.get_Value("Vlr_ValeVista"));
					asset.set_CustomColumn("Fecha_Subasta", (Timestamp)iLine.get_Value("Fecha_Subasta"));
					asset.set_CustomColumn("Vlr_Subasta",(BigDecimal)iLine.get_Value("Vlr_Subasta"));
					asset.set_CustomColumn("Lot", iLine.get_ValueAsString("Lot"));
					asset.set_CustomColumn("Martillero", iLine.get_ValueAsString("Martillero"));
					asset.set_CustomColumn("ParentAsset", iLine.get_ValueAsString("ParentAsset"));
					asset.set_CustomColumn("Date1", (Timestamp)iLine.get_Value("Date1"));
					asset.set_CustomColumn("ExpirationDate", (Timestamp)iLine.get_Value("ExpirationDate"));
					asset.set_CustomColumn("Renewable", iLine.get_ValueAsString("Renewable"));
					
					if(iLine.get_ValueAsInt("A_Asset_Group_Ref_ID") > 0)
						asset.set_CustomColumn("A_Asset_Group_Ref_ID", iLine.get_ValueAsInt("A_Asset_Group_Ref_ID"));
					if(dType.get_ID() == 1000003)
						asset.set_CustomColumn("AssetSituation", "COM");
					else
						asset.set_CustomColumn("AssetSituation", "ALT");					
					//asset.set_CustomColumn("GrandTotal", iLine.getLineTotalAmt());
					asset.set_CustomColumn("GrandTotal", iLine.getPriceEntered());
					if(iLine.get_ValueAsInt("S_Resource_ID")>0)
						asset.set_CustomColumn("S_Resource_ID", iLine.get_ValueAsInt("S_Resource_ID"));
					if(iLine.get_ValueAsInt("S_ResourceRef_ID")>0)
						asset.set_CustomColumn("S_ResourceRef_ID", iLine.get_ValueAsInt("S_ResourceRef_ID"));
					
					//se agrega subclase
					asset.set_CustomColumn("A_Asset_Group_Ref2_ID", iLine.get_ValueAsInt("A_Asset_Group_Ref3_ID"));

					
					//mfrojas 20190403
					//Revisar cuando un activo es contable o no dependiendo del monto
					
					//Obtener valor de UTM.
					
					int id_utm = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_Currency_ID) FROM C_Currency WHERE Description like 'UTM' ");
					BigDecimal totalAmtLineCLP = iLine.getPriceEntered();

					if (id_utm > 0)
					{
						BigDecimal amt3UTMclp = MConversionRate.convert(getCtx(), new BigDecimal("3.0"), id_utm, inv.getC_Currency_ID(),
						(Timestamp)inv.get_Value("DateDoc"), 0, inv.getAD_Client_ID(), inv.getAD_Org_ID());
							
						if (inv.getC_Currency_ID() != 228)
							totalAmtLineCLP = MConversionRate.convert(getCtx(), iLine.getPriceEntered(), inv.getC_Currency_ID(), 228,
								(Timestamp)inv.get_Value("DateDoc"), 0, inv.getAD_Client_ID(), inv.getAD_Org_ID());
							
						try 
						{
							if(totalAmtLineCLP.compareTo(amt3UTMclp) >= 0)									
								asset.setIsInPosession(true);
							else
								asset.setIsInPosession(false);
								
						}
						catch (Exception e) 
						{
								log.config("No se pudo hacer comparación con moneda UTM"+e.toString());
								
						}
							
					}	
					//fin campos nuevos
					asset.saveEx();
					//se actualiza por debajo el grupo de activos
					//DB.executeUpdate("UPDATE A_Asset SET A_Asset_Group_ID = "+group_ID+" WHERE A_Asset_ID = "+asset.get_ID(), get_TrxName());
					iLine.set_CustomColumn("A_Asset_ID", asset.getA_Asset_ID());
					log.config("activo "+asset.getA_Asset_ID());
					iLine.save();
										
					X_A_Asset_Use  assetuse = new X_A_Asset_Use(inv.getCtx(),0,inv.get_TrxName());
					assetuse.setA_Asset_ID(asset.getA_Asset_ID());
					assetuse.setUseDate(inv.getDateInvoiced());
					assetuse.setUseUnits(1);
					assetuse.setDescription("ALTA DE ACTIVO");
					if(iLine.get_ValueAsInt("AD_User_ID")>0)
						assetuse.set_CustomColumn("AD_User_ID", iLine.get_ValueAsInt("AD_User_ID"));
					if(iLine.get_ValueAsInt("S_Resource_ID")>0)
						assetuse.set_CustomColumn("S_Resource_ID", iLine.get_ValueAsInt("S_Resource_ID"));
					if(iLine.get_ValueAsInt("S_ResourceRef_ID")>0)
						assetuse.set_CustomColumn("S_ResourceRef_ID", iLine.get_ValueAsInt("S_ResourceRef_ID"));
					assetuse.set_CustomColumn("C_DocType_ID", inv.getC_DocTypeTarget_ID());
					assetuse.set_CustomColumn("type1", dType.get_ValueAsString("type1"));
					//campo de enlace
					assetuse.set_CustomColumn("C_InvoiceLine_ID", iLine.get_ID());
					assetuse.save();
					
					//
					/** mfrojas 20181116
					 * Ahora que el bien esta de alta, se debe revisar el depreciationworkfile creado y actualizar el costo
					 * 
					 */
					
					int depwork = 0;
					depwork = DB.getSQLValue(get_TrxName(), "SELECT coalesce(max(a_depreciation_workfile_id),0) from a_Depreciation_workfile where a_Asset_id = "+asset.get_ID());
					if(depwork == 0)
						log.config("WORK ES CERO");
					else
					{
						X_A_Depreciation_Workfile work = new X_A_Depreciation_Workfile(getCtx(),depwork,get_TrxName());
						work.setA_Asset_Cost(iLine.getPriceEntered());
						work.save();
					}
				}
			}
		}
	}//endcreateAsset()
	private boolean isAsset(MInvoiceLine sLine)
	{
		if(sLine.getC_Charge_ID()>0)
		{
			  if(sLine.getC_Charge().getC_ChargeType_ID()>0)
				if(sLine.getC_Charge().getC_ChargeType().getValue().equals("TCAF"))
						return true;
		}
		if(sLine.getM_Product_ID()>0)
		{
			if(sLine.getM_Product().getM_Product_Category().getA_Asset_Group_ID()>0 )
				return true;
		}
		return false;
	}
	
	public void degradeAsset(MInvoice inv,MDocType dType)	
	{	
		MInvoiceLine[] lines = inv.getLines(false);
		for (int lineIndex = 0; lineIndex < lines.length; lineIndex++)
		{
			MInvoiceLine iLine = lines[lineIndex];
			//se valida que exista monto de subasta solo para baja enajenacion
			//30-01-2018 validacion se hara en model por problemas en web
			/*if(inv.getC_DocTypeTarget_ID() == 2000159)
			{
				BigDecimal Vlr_Subasta = (BigDecimal)iLine.get_Value("Vlr_Subasta");
				if(Vlr_Subasta == null)
					Vlr_Subasta = Env.ZERO;
				if(Vlr_Subasta.compareTo(Env.ZERO) <= 0)
					throw new AdempiereException("Todos los activos deben tener valor de subasta. Linea:"+(iLine.getLine()/10));
			}*/
			//30-01-2018 end
			if(iLine.getA_Asset_ID() > 0 && iLine.isActive())
			{
				//Valid a_Asset
				int A_Asset = iLine.getA_Asset_ID();
				A_Asset = OFBForward.getBP(A_Asset);

				MAsset asset = new MAsset(getCtx(), A_Asset, get_TrxName());
				//se crea registro en responsable
				X_A_Asset_Use  assetuse = new X_A_Asset_Use(inv.getCtx(),0,inv.get_TrxName());
				assetuse.setA_Asset_ID(asset.getA_Asset_ID());
				assetuse.setUseDate(inv.getDateInvoiced());
				assetuse.setUseUnits(1);
				if(iLine.get_ValueAsInt("AD_User_ID")>0)
					assetuse.set_CustomColumn("AD_User_ID", iLine.get_ValueAsInt("AD_User_ID"));
				if(iLine.get_ValueAsInt("S_Resource_ID")>0)
					assetuse.set_CustomColumn("S_Resource_ID", iLine.get_ValueAsInt("S_Resource_ID"));
				if(iLine.get_ValueAsInt("S_ResourceRef_ID")>0)
					assetuse.set_CustomColumn("S_ResourceRef_ID", iLine.get_ValueAsInt("S_ResourceRef_ID"));
				//Si el tipo de documento es comodato, agregarlo
				if(inv.getC_DocTypeTarget_ID() == 2000161)
					assetuse.setDescription("BAJA DE ACTIVO POR COMODATO");
				else
					assetuse.setDescription("BAJA DE ACTIVO");
				assetuse.set_CustomColumn("C_DocType_ID", inv.getC_DocTypeTarget_ID());				
				assetuse.set_CustomColumn("type1", dType.get_ValueAsString("type1"));
				//campo de enlace
				assetuse.set_CustomColumn("C_InvoiceLine_ID", iLine.get_ID());
				assetuse.save();
				//se actualiza activo
				//Si es comodato
				if(inv.getC_DocTypeTarget_ID() == 1000005)
					asset.set_CustomColumn("AssetSituation","COM");
				else
					asset.set_CustomColumn("AssetSituation", "BAJ");
				
				asset.set_CustomColumn("GrandTotal", Env.ONE);
				//asset.setIsActive(false);
				asset.saveEx();
				//Actualizar valor de ventana saldos de activo
				//DB.executeUpdate("UPDATE A_Depreciation_workfile SET a_accumulated_depr = a_asset_cost, a_asset_remaining = 0 where a_asset_id = "+asset.get_ID(), get_TrxName());
				
			}
		}
	}
	public void transferAsset(MInvoice inv, MDocType dType)	
	{	
		MInvoiceLine[] lines = inv.getLines(false);
		for (int lineIndex = 0; lineIndex < lines.length; lineIndex++)
		{
			MInvoiceLine iLine = lines[lineIndex];
			if(iLine.getA_Asset_ID() > 0 && iLine.isActive())
			{
				//Valid a_Asset
				int A_Asset = iLine.getA_Asset_ID();
				A_Asset = OFBForward.getBP(A_Asset);
				
				MAsset asset = new MAsset(getCtx(), A_Asset, get_TrxName());
//				asset.setAD_Org_ID(iLine.getAD_Org_ID());
//				asset.setAD_User_ID(iLine.get_ValueAsInt("AD_User_ID"));
/*				if(iLine.get_ValueAsInt("S_Resource_ID")>0)
					asset.set_CustomColumn("S_Resource_ID", iLine.get_ValueAsInt("S_Resource_ID"));
				if(iLine.get_ValueAsInt("S_ResourceRef_ID")>0)
					asset.set_CustomColumn("S_ResourceRef_ID", iLine.get_ValueAsInt("S_ResourceRef_ID"));
				asset.saveEx(get_TrxName());				
				*/
				if(iLine.get_ValueAsInt("AD_NewOrg_ID") > 0)
					asset.setAD_Org_ID(iLine.get_ValueAsInt("AD_NewOrg_ID"));
					//asset.set_CustomColumn("AD_Org_ID", iLine.get_ValueAsInt("AD_NewOrg_ID"));
				if(iLine.get_ValueAsInt("C_NewRegion_ID") > 0)
					asset.set_CustomColumn("C_Region_ID", iLine.get_ValueAsInt("C_NewRegion_ID"));
				if(iLine.get_ValueAsInt("C_NewProvince_ID") > 0)
					asset.set_CustomColumn("C_Province_ID", iLine.get_ValueAsInt("C_NewProvince_ID"));
				
				log.config("new resource"+iLine.get_ValueAsInt("S_NewResource_ID"));
				log.config("TRACE ");
				if(iLine.get_ValueAsInt("S_NewResource_ID") > 0)
					asset.set_CustomColumn("S_Resource_ID", iLine.get_ValueAsInt("S_NewResource_ID"));
				if(iLine.get_ValueAsInt("S_NewResourceRef_ID") > 0)
					asset.set_CustomColumn("S_ResourceRef_ID", iLine.get_ValueAsInt("S_NewResourceRef_ID"));
				if(inv.getC_DocTypeTarget_ID()==1000009)
					asset.set_CustomColumn("AssetSituation","COM");
				asset.saveEx(get_TrxName());				

				//se crea registro en responsable
				X_A_Asset_Use  assetuse = new X_A_Asset_Use(inv.getCtx(),0,inv.get_TrxName());
				assetuse.setA_Asset_ID(asset.getA_Asset_ID());
				assetuse.setUseDate(inv.getDateInvoiced());
				assetuse.setUseUnits(1);
				if(iLine.get_ValueAsInt("AD_User_ID")>0)
					assetuse.set_CustomColumn("AD_User_ID", iLine.get_ValueAsInt("AD_User_ID"));
				if(iLine.get_ValueAsInt("S_Resource_ID")>0)
					assetuse.set_CustomColumn("S_Resource_ID", iLine.get_ValueAsInt("S_Resource_ID"));
				if(iLine.get_ValueAsInt("S_ResourceRef_ID")>0)
					assetuse.set_CustomColumn("S_ResourceRef_ID", iLine.get_ValueAsInt("S_ResourceRef_ID"));
				assetuse.setDescription("TRASPASO DE ACTIVO");
				assetuse.set_CustomColumn("C_DocType_ID", inv.getC_DocTypeTarget_ID());
				assetuse.set_CustomColumn("type1", dType.get_ValueAsString("type1"));
				//campo de enlace
				assetuse.set_CustomColumn("C_InvoiceLine_ID", iLine.get_ID());
				assetuse.save();
			}
		}
	}	
	public void updateAsset(MInvoice inv, MDocType dType)	
	{	
		MInvoiceLine[] lines = inv.getLines(false);
		for (int lineIndex = 0; lineIndex < lines.length; lineIndex++)
		{
			MInvoiceLine iLine = lines[lineIndex];
			if(iLine.getA_Asset_ID() > 0 && iLine.isActive())
			{
				//Valid a_Asset
				int A_Asset = iLine.getA_Asset_ID();
				A_Asset = OFBForward.getBP(A_Asset);
				
				MAsset asset = new MAsset(getCtx(), A_Asset, get_TrxName());
				asset.setName(iLine.getDescription());			
				asset.setAD_Org_ID(iLine.getAD_Org_ID());
				//aumento de valor
				if(dType.get_ID() == 1000623)
				{
					BigDecimal amtDiff = (BigDecimal)iLine.get_Value("PriceEffective");
					if(amtDiff == null)
						amtDiff = Env.ZERO;
					if(amtDiff.compareTo(Env.ZERO) != 0)
					{
						asset.set_CustomColumn("GrandTotal", iLine.getPriceEntered().add(amtDiff));
						asset.saveEx();
						DB.executeUpdate("UPDATE a_Depreciation_workfile SET a_asset_cost = "+iLine.getPriceEntered().add(amtDiff), get_TrxName());
					}
				}
				//disminucion de valor
				else if(dType.get_ID() == 1000624)
				{
					BigDecimal amtDiff = (BigDecimal)iLine.get_Value("PriceEffective");
					if(amtDiff == null)
						amtDiff = Env.ZERO;
					if(amtDiff.compareTo(Env.ZERO) != 0)
					{
						asset.set_CustomColumn("GrandTotal", iLine.getPriceEntered().subtract(amtDiff));
						asset.saveEx();
						DB.executeUpdate("UPDATE a_Depreciation_workfile SET a_asset_cost = "+iLine.getPriceEntered().subtract(amtDiff), get_TrxName());
					}
				}
				else
				{
					if(iLine.get_ValueAsInt("AD_User_ID") > 0)
						asset.setAD_User_ID(iLine.get_ValueAsInt("AD_user_ID"));
					//se agregan nuevos campos mop
					asset.set_CustomColumn("Brand", iLine.get_ValueAsString("Brand"));
					asset.set_CustomColumn("Model", iLine.get_ValueAsString("Model"));
					asset.set_CustomColumn("SerNo", iLine.get_ValueAsString("SerNo"));
					asset.set_CustomColumn("Color", iLine.get_ValueAsString("Color"));
					if(iLine.get_ValueAsString("AssetStatus") != null && iLine.get_ValueAsString("AssetStatus").trim().length() > 0)
						asset.set_CustomColumn("AssetStatus", iLine.get_ValueAsString("AssetStatus"));
					asset.set_CustomColumn("A_Parent_Asset_ID", iLine.get_ValueAsInt("A_Parent_Asset_ID"));
					if(iLine.get_ValueAsString("Type1") != null && iLine.get_ValueAsString("Type1").trim().length() > 0)
						asset.set_CustomColumn("Type1", iLine.get_ValueAsString("Type1"));
					asset.set_CustomColumn("Rol", iLine.get_ValueAsString("Rol"));
					asset.set_CustomColumn("GroundSurface", (BigDecimal)iLine.get_Value("GroundSurface"));
					asset.set_CustomColumn("EdificationSurface",(BigDecimal)iLine.get_Value("EdificationSurface"));
					asset.set_CustomColumn("Engine", iLine.get_ValueAsString("Engine"));
					asset.set_CustomColumn("Chassis", iLine.get_ValueAsString("Chassis"));
					asset.set_CustomColumn("LicensePlate", iLine.get_ValueAsString("LicensePlate"));
					asset.set_CustomColumn("Digito", iLine.get_ValueAsString("Digito"));
					asset.set_CustomColumn("Sigla", iLine.get_ValueAsString("Sigla"));
					asset.set_CustomColumn("Fojas", iLine.get_ValueAsString("Fojas"));
					asset.set_CustomColumn("RegistrationNumber", iLine.get_ValueAsString("RegistrationNumber"));
					asset.set_CustomColumn("Year", iLine.get_ValueAsString("Year"));
					asset.set_CustomColumn("Inmueble", iLine.get_ValueAsString("Inmueble"));
					if(iLine.get_ValueAsInt("C_Region_ID") > 0)
						asset.set_CustomColumn("C_Region_ID", iLine.get_ValueAsInt("C_Region_ID"));
					if(iLine.get_ValueAsInt("C_Province_ID") > 0)
						asset.set_CustomColumn("C_Province_ID", iLine.get_ValueAsInt("C_Province_ID"));
					if(iLine.get_ValueAsInt("C_City_ID") > 0)
						asset.set_CustomColumn("C_City_ID", iLine.get_ValueAsString("C_City_ID"));
					if(iLine.get_ValueAsString("DocDestination") != null && iLine.get_ValueAsString("DocDestination").trim().length() > 0)
						asset.set_CustomColumn("DocDestination", iLine.get_ValueAsString("DocDestination"));
					asset.set_CustomColumn("DocNumber", iLine.get_ValueAsString("DocNumber"));
					asset.set_CustomColumn("Conservador", iLine.get_ValueAsString("Conservador"));
					asset.set_CustomColumn("Notaria", iLine.get_ValueAsString("Notaria"));
					asset.set_CustomColumn("N_Fecha_ValeVista", iLine.get_ValueAsString("N_Fecha_ValeVista"));
					asset.set_CustomColumn("Vlr_ValeVista",(BigDecimal)iLine.get_Value("Vlr_ValeVista"));
					asset.set_CustomColumn("Fecha_Subasta", (Timestamp)iLine.get_Value("Fecha_Subasta"));
					asset.set_CustomColumn("Vlr_Subasta",(BigDecimal)iLine.get_Value("Vlr_Subasta"));
					asset.set_CustomColumn("Lot", iLine.get_ValueAsString("Lot"));
					asset.set_CustomColumn("Martillero", iLine.get_ValueAsString("Martillero"));
					asset.set_CustomColumn("DocNumber2", iLine.get_ValueAsString("DocNumber2"));
					asset.set_CustomColumn("ParentAsset", iLine.get_ValueAsString("ParentAsset"));
					if(iLine.get_ValueAsInt("A_Asset_Group_Ref_ID") > 0)
						asset.set_CustomColumn("A_Asset_Group_Ref_ID", iLine.get_ValueAsInt("A_Asset_Group_Ref_ID"));
					if(dType.get_ID() == 1000626)
					{
						asset.set_CustomColumn("AssetSituation","ALT");
						/*if(iLine.get_ValueAsString("AssetSituation") != null && iLine.get_ValueAsString("AssetSituation").trim().length() > 0)
						asset.set_CustomColumn("AssetSituation",iLine.get_ValueAsString("AssetSituation"));*/
						//se busca ultimo monto
						int ID_ILine = DB.getSQLValue(get_TrxName(), "SELECT C_InvoiceLine_ID FROM C_InvoiceLine cil " +
							" INNER JOIN C_Invoice ci ON (cil.C_Invoice_ID = ci.C_Invoice_ID)" +
							" WHERE cil.C_InvoiceLine_ID <> "+iLine.get_ID()+" AND cil.A_Asset_ID = ? AND PriceEntered > 1 " +
							" ORDER BY DateInvoiced DESC, cil.created DESC", asset.get_ID());
						if(ID_ILine > 0)
						{
							MInvoiceLine iLineOld = new MInvoiceLine(getCtx(), ID_ILine, get_TrxName());
							asset.set_CustomColumn("GrandTotal", iLineOld.getPriceEntered());
						}
						
					}else
					{
						if(iLine.get_ValueAsString("AssetSituation") != null && iLine.get_ValueAsString("AssetSituation").trim().length() > 0)
							asset.set_CustomColumn("AssetSituation",iLine.get_ValueAsString("AssetSituation"));
					}
					//asset.set_CustomColumn("GrandTotal", iLine.getLineTotalAmt());
					//fin campos nuevos
					asset.saveEx();
				}				
				//se crea registro de historia
				X_A_Asset_Use  assetuse = new X_A_Asset_Use(inv.getCtx(),0,inv.get_TrxName());
				assetuse.setA_Asset_ID(asset.getA_Asset_ID());
				assetuse.setUseDate(inv.getDateInvoiced());
				assetuse.setUseUnits(1);
				assetuse.setDescription("MODIFICACIÓN DE ACTIVO");
				if(iLine.get_ValueAsInt("AD_User_ID")>0)
					assetuse.set_CustomColumn("AD_User_ID", iLine.get_ValueAsInt("AD_User_ID"));
				if(iLine.get_ValueAsInt("S_Resource_ID")>0)
					assetuse.set_CustomColumn("S_Resource_ID", iLine.get_ValueAsInt("S_Resource_ID"));
				if(iLine.get_ValueAsInt("S_ResourceRef_ID")>0)
					assetuse.set_CustomColumn("S_ResourceRef_ID", iLine.get_ValueAsInt("S_ResourceRef_ID"));
				assetuse.set_CustomColumn("C_DocType_ID", inv.getC_DocTypeTarget_ID());
				assetuse.set_CustomColumn("type1", dType.get_ValueAsString("type1"));
				//campo de enlace
				assetuse.set_CustomColumn("C_InvoiceLine_ID", iLine.get_ID());
				
				if(dType.get_ID() == 1000623 || dType.get_ID() == 1000624)
				{
					assetuse.set_CustomColumn("Amt", iLine.getPriceEntered());
					BigDecimal amtDiff = (BigDecimal)iLine.get_Value("PriceEffective");

					if(dType.get_ID() == 1000623)
						assetuse.set_CustomColumn("AmountRef",iLine.getPriceEntered().add(amtDiff));
					else
						assetuse.set_CustomColumn("AmountRef",iLine.getPriceEntered().subtract(amtDiff));

				}

				assetuse.save();
			}
		}
	}//endcreateAsset()
	
	//mfrojas
	//Se agrega proceso para escribir en el assetuse cuando
	//un bien esta con baja "en proceso".
	
	public void degradeAssetInProcess(MInvoice inv,MDocType dType)
	{
		MInvoiceLine[] lines = inv.getLines(false);
		for (int lineIndex = 0; lineIndex < lines.length; lineIndex++)
		{
			MInvoiceLine iLine = lines[lineIndex];
			//se valida que exista monto de subasta solo para baja enajenacion
			//30-01-2018 validacion se hara en model por problemas en web
			/*if(inv.getC_DocTypeTarget_ID() == 2000159)
			{
				BigDecimal Vlr_Subasta = (BigDecimal)iLine.get_Value("Vlr_Subasta");
				if(Vlr_Subasta == null)
					Vlr_Subasta = Env.ZERO;
				if(Vlr_Subasta.compareTo(Env.ZERO) <= 0)
					throw new AdempiereException("Todos los activos deben tener valor de subasta. Linea:"+(iLine.getLine()/10));
			}*/
			//30-01-2018 end
			if(iLine.getA_Asset_ID() > 0 && iLine.isActive())
			{

				MAsset asset = new MAsset(getCtx(), iLine.getA_Asset_ID(), get_TrxName());
				X_A_Asset_Use  assetuse = new X_A_Asset_Use(inv.getCtx(),0,inv.get_TrxName());
				assetuse.setA_Asset_ID(asset.getA_Asset_ID());
				assetuse.setUseDate(inv.getDateInvoiced());
				assetuse.setUseUnits(1);
				if(iLine.get_ValueAsInt("AD_User_ID")>0)
					assetuse.set_CustomColumn("AD_User_ID", iLine.get_ValueAsInt("AD_User_ID"));
				if(iLine.get_ValueAsInt("S_Resource_ID")>0)
					assetuse.set_CustomColumn("S_Resource_ID", iLine.get_ValueAsInt("S_Resource_ID"));
				if(iLine.get_ValueAsInt("S_ResourceRef_ID")>0)
					assetuse.set_CustomColumn("S_ResourceRef_ID", iLine.get_ValueAsInt("S_ResourceRef_ID"));
				//Si el tipo de documento es comodato, agregarlo
				assetuse.setDescription("BAJA CON ENAJENACION EN PROCESO");

				assetuse.set_CustomColumn("C_DocType_ID", inv.getC_DocTypeTarget_ID());				
				assetuse.set_CustomColumn("type1", dType.get_ValueAsString("type1"));
				//campo de enlace
				assetuse.set_CustomColumn("C_InvoiceLine_ID", iLine.get_ID());
				assetuse.save();
			}
		}
	}
}
