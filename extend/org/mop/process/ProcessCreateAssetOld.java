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
package org.mop.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAsset;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.X_A_Asset_Use;
import org.compiere.model.X_C_InvoiceLine;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
/**
 *	
 *	
 *  @author Italo Niñoles ininoles 
 *  @version $Id: ProcessCreateAsset,java $
 */
public class ProcessCreateAssetOld extends SvrProcess
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
			if(inv.getDocStatus().compareTo("DR") == 0 && dType.get_ValueAsString("Type1").compareTo("ALT") == 0)
			{
				//alta
				//validacion de cantidad de lineas //30-01-2018 validacion se haa con model por problemas en web
				/*int cantLine = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_InvoiceLine " +
						"WHERE IsActive = 'Y' AND C_Invoice_ID = "+inv.get_ID());
				if(cantLine != inv.get_ValueAsInt("ValidCantLine"))
				{
					String msgError = "Error en campo: Cantidad de activos no coresponde con lineas";
					String nameField = DB.getSQLValueString(get_TrxName(), "SELECT name FROM AD_Field_Trl " +
							" WHERE AD_Field_ID=2003920 AND AD_Language='es_MX'");
					if(nameField != null && nameField.trim().length() > 0)
						msgError = "Error en campo "+nameField+": Cantidad de activos no coresponde con lineas";
					throw new AdempiereException(msgError);
				}*/ //30-01-2018 end
				//validacion de montos de lineas
				/*BigDecimal amtLine = DB.getSQLValueBD(get_TrxName(), "SELECT SUM(LineTotalAmt) FROM C_InvoiceLine " +
						"WHERE IsActive = 'Y' AND C_Invoice_ID = "+inv.get_ID());
				if(amtLine == null)
					amtLine = Env.ZERO;
				BigDecimal validAmtLine = (BigDecimal)inv.get_Value("ValidTotalLine");
				if(validAmtLine == null)
					validAmtLine = Env.ZERO;
				if(amtLine.compareTo(validAmtLine) != 0)
					throw new IllegalStateException("Monto de activos no coresponde con lineas");*/
				createAsset(inv, dType);			
				//Al completar solo se quedara el processed el documento y cambiara el estado
				inv.setDocStatus("CO");
				inv.setProcessed(true);
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
					&& dType.get_ID() == 2000159)
			{
				inv.setDocStatus("IP");
			}
			else if(inv.getDocStatus().compareTo("DR") == 0 && dType.get_ValueAsString("Type1").compareTo("BAJ") == 0)
			{
				degradeAsset(inv, dType);
				inv.setDocStatus("CO");
				inv.setProcessed(true);
			}
			else if(inv.getDocStatus().compareTo("IP") == 0 && dType.get_ValueAsString("Type1").compareTo("BAJ") == 0)
			{
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
					if(iLine.get_ValueAsInt("A_Asset_Group_Ref_ID") > 0)
						asset.set_CustomColumn("A_Asset_Group_Ref_ID", iLine.get_ValueAsInt("A_Asset_Group_Ref_ID"));
					if(dType.get_ID() == 2000158)
						asset.set_CustomColumn("AssetSituation", "COM");
					else
						asset.set_CustomColumn("AssetSituation", "ALT");					
					asset.set_CustomColumn("GrandTotal", iLine.getLineTotalAmt());
					if(iLine.get_ValueAsInt("S_Resource_ID")>0)
						asset.set_CustomColumn("S_Resource_ID", iLine.get_ValueAsInt("S_Resource_ID"));
					if(iLine.get_ValueAsInt("S_ResourceRef_ID")>0)
						asset.set_CustomColumn("S_ResourceRef_ID", iLine.get_ValueAsInt("S_ResourceRef_ID"));
					//fin campos nuevos
					asset.saveEx();
					//se actualiza por debajo el grupo de activos
					//DB.executeUpdate("UPDATE A_Asset SET A_Asset_Group_ID = "+group_ID+" WHERE A_Asset_ID = "+asset.get_ID(), get_TrxName());
					iLine.set_CustomColumn("A_Asset_ID", asset.getA_Asset_ID());
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
				MAsset asset = new MAsset(getCtx(), iLine.getA_Asset_ID(), get_TrxName());
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
				assetuse.setDescription("BAJA DE ACTIVO");
				assetuse.set_CustomColumn("C_DocType_ID", inv.getC_DocTypeTarget_ID());				
				assetuse.set_CustomColumn("type1", dType.get_ValueAsString("type1"));
				//campo de enlace
				assetuse.set_CustomColumn("C_InvoiceLine_ID", iLine.get_ID());
				assetuse.save();
				//se actualiza activo
				asset.set_CustomColumn("AssetSituation", "BAJ");
				asset.set_CustomColumn("GrandTotal", Env.ONE);
				//asset.setIsActive(false);
				asset.saveEx();
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
				MAsset asset = new MAsset(getCtx(), iLine.getA_Asset_ID(), get_TrxName());
				asset.setAD_Org_ID(iLine.getAD_Org_ID());
				asset.setAD_User_ID(iLine.get_ValueAsInt("AD_User_ID"));
				if(iLine.get_ValueAsInt("S_Resource_ID")>0)
					asset.set_CustomColumn("S_Resource_ID", iLine.get_ValueAsInt("S_Resource_ID"));
				if(iLine.get_ValueAsInt("S_ResourceRef_ID")>0)
					asset.set_CustomColumn("S_ResourceRef_ID", iLine.get_ValueAsInt("S_ResourceRef_ID"));
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
				MAsset asset = new MAsset(getCtx(), iLine.getA_Asset_ID(), get_TrxName());
				asset.setName(iLine.getDescription());			
				asset.setAD_Org_ID(iLine.getAD_Org_ID());
				//aumento de valor
				if(dType.get_ID() == 2000167)
				{
					BigDecimal amtDiff = (BigDecimal)iLine.get_Value("PriceEffective");
					if(amtDiff == null)
						amtDiff = Env.ZERO;
					if(amtDiff.compareTo(Env.ZERO) != 0)
					{
						asset.set_CustomColumn("GrandTotal", iLine.getPriceEntered().add(amtDiff));
						asset.saveEx();
					}
				}
				//disminucion de valor
				else if(dType.get_ID() == 2000168)
				{
					BigDecimal amtDiff = (BigDecimal)iLine.get_Value("PriceEffective");
					if(amtDiff == null)
						amtDiff = Env.ZERO;
					if(amtDiff.compareTo(Env.ZERO) != 0)
					{
						asset.set_CustomColumn("GrandTotal", iLine.getPriceEntered().subtract(amtDiff));
						asset.saveEx();
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
					if(dType.get_ID() == 2000169)
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
				assetuse.save();
			}
		}
	}//endcreateAsset()
}
