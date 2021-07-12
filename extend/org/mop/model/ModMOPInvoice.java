/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
package org.mop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

import org.compiere.model.MAsset;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.ofb.utils.DateUtils;

/**
 *	Validator for MOP
 *
 *  @author Italo Niñoles
 */
public class ModMOPInvoice implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModMOPInvoice ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModMOPInvoice.class);
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	

	/**
	 *	Initialize Validation
	 *	@param engine validation engine
	 *	@param client client
	 */
	public void initialize (ModelValidationEngine engine, MClient client)
	{
		//client = null for global validator
		if (client != null) {
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else  {
			log.info("Initializing Model Price Validator: "+this.toString());
		}

		//	Tables to be monitored
		engine.addModelChange(MInvoice.Table_Name, this);		
		engine.addModelChange(MInvoiceLine.Table_Name, this);
		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		if((type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE) && po.get_Table_ID()==MInvoice.Table_ID) 
		{	
			MInvoice inv = (MInvoice)po;
			//vaidacion para estados del documento
			if(inv.getDocStatus().compareTo("DR")==0 || inv.getDocStatus().compareTo("IN")==0
					|| inv.getDocStatus().compareTo("IP")==0)
			{
				//validacion para tipos de documento
				//if(inv.getC_DocTypeTarget().getDocBaseType().compareTo("ARI") == 0)
				//{
					Calendar calInv = Calendar.getInstance();
					calInv.setTimeInMillis(inv.getDateInvoiced().getTime());
					calInv.set(Calendar.HOUR_OF_DAY, 0);
					calInv.set(Calendar.MINUTE, 0);
					calInv.set(Calendar.SECOND, 0);
					calInv.set(Calendar.MILLISECOND, 0);						
					Timestamp timeInv = new Timestamp(calInv.getTimeInMillis());
					
					Timestamp today = DateUtils.today();
					if(timeInv.compareTo(today) > 0)
					{	
						return "Error: Fecha de documento no puede ser mayor a hoy";
					}
				//}
			}
		}
		if((type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE) && po.get_Table_ID()==MInvoiceLine.Table_ID) 
		{	
			MInvoiceLine iLine = (MInvoiceLine)po;
			MInvoice inv = new MInvoice(po.getCtx(), iLine.getC_Invoice_ID(), po.get_TrxName());
			MDocType dType = new MDocType(po.getCtx(), inv.getC_DocTypeTarget_ID(), po.get_TrxName());
			if(inv.getDocStatus().compareTo("DR") == 0 && dType.get_ValueAsString("Type1").compareTo("BAJ") == 0
					)
			{	
				if(iLine.getA_Asset_ID() > 0)
				{
					if(iLine.is_ValueChanged("A_Asset_ID"))
					{
						MAsset asset = new MAsset(po.getCtx(), iLine.getA_Asset_ID(), po.get_TrxName());
						iLine.setDescription(asset.getName());
						iLine.setPrice((BigDecimal)asset.get_Value("GrandTotal"));
						if(asset.getAD_User_ID() > 0)
							iLine.set_CustomColumn("AD_User_ID", asset.getAD_User_ID());
						iLine.set_CustomColumn("Brand", asset.get_ValueAsString("Brand"));
						iLine.set_CustomColumn("Model", asset.get_ValueAsString("Model"));
						iLine.set_CustomColumn("SerNo", asset.get_ValueAsString("SerNo"));
						iLine.set_CustomColumn("Color", asset.get_ValueAsString("Color"));					
						if(asset.get_ValueAsString("AssetStatus") != null && asset.get_ValueAsString("AssetStatus").trim().length() > 0)
							iLine.set_CustomColumn("AssetStatus", asset.get_ValueAsString("AssetStatus"));
						iLine.set_CustomColumn("A_Parent_Asset_ID", asset.get_ValueAsInt("A_Parent_Asset_ID"));
						if(asset.get_ValueAsString("Type1") != null && asset.get_ValueAsString("Type1").trim().length() > 0)
							iLine.set_CustomColumn("Type1", asset.get_ValueAsString("Type1"));
						iLine.set_CustomColumn("Rol", asset.get_ValueAsString("Rol"));
						iLine.set_CustomColumn("GroundSurface", (BigDecimal)asset.get_Value("GroundSurface"));
						iLine.set_CustomColumn("EdificationSurface",(BigDecimal)asset.get_Value("EdificationSurface"));
						iLine.set_CustomColumn("Engine", asset.get_ValueAsString("Engine"));
						iLine.set_CustomColumn("Chassis", asset.get_ValueAsString("Chassis"));
						iLine.set_CustomColumn("LicensePlate", asset.get_ValueAsString("LicensePlate"));
						iLine.set_CustomColumn("Digito", asset.get_ValueAsString("Digito"));
						iLine.set_CustomColumn("Sigla", asset.get_ValueAsString("Sigla"));
						iLine.set_CustomColumn("Fojas", asset.get_ValueAsString("Fojas"));
						iLine.set_CustomColumn("RegistrationNumber", asset.get_ValueAsString("RegistrationNumber"));
						iLine.set_CustomColumn("Year", asset.get_ValueAsString("Year"));	
						if(asset.get_ValueAsString("Inmueble") != null && asset.get_ValueAsString("Inmueble").trim().length() > 0)
							iLine.set_CustomColumn("Inmueble", asset.get_ValueAsString("Inmueble"));
						if(asset.get_ValueAsInt("C_Region_ID") > 0)
							iLine.set_CustomColumn("C_Region_ID", asset.get_ValueAsInt("C_Region_ID"));
						if(asset.get_ValueAsInt("C_Province_ID") > 0)
							iLine.set_CustomColumn("C_Province_ID", asset.get_ValueAsInt("C_Province_ID"));
						if(asset.get_ValueAsInt("C_City_ID") > 0)
							iLine.set_CustomColumn("C_City_ID", asset.get_ValueAsString("C_City_ID"));
						if(asset.get_ValueAsString("DocDestination") != null && asset.get_ValueAsString("DocDestination").trim().length() > 0)
							iLine.set_CustomColumn("DocDestination", asset.get_ValueAsString("DocDestination"));
						iLine.set_CustomColumn("DocNumber", asset.get_ValueAsString("DocNumber"));
						//iLine.set_CustomColumn("Conservador", asset.get_ValueAsString("Conservador"));
						iLine.set_CustomColumn("Notaria", asset.get_ValueAsString("Notaria"));
						iLine.set_CustomColumn("N_Fecha_ValeVista", asset.get_ValueAsString("N_Fecha_ValeVista"));
						BigDecimal valevista = (BigDecimal)iLine.get_Value("Vlr_ValeVista");
						if(valevista != null && valevista.compareTo(Env.ZERO)<=0)
							iLine.set_CustomColumn("Vlr_ValeVista",(BigDecimal)asset.get_Value("Vlr_ValeVista"));
						//iLine.set_CustomColumn("Fecha_Subasta", (Timestamp)asset.get_Value("Fecha_Subasta"));
						//iLine.set_CustomColumn("Vlr_Subasta",(BigDecimal)asset.get_Value("Vlr_Subasta"));
						//iLine.set_CustomColumn("Lot", asset.get_ValueAsString("Lot"));
						//iLine.set_CustomColumn("Martillero", asset.get_ValueAsString("Martillero"));
						iLine.set_CustomColumn("DocNumber2", asset.get_ValueAsString("DocNumber2"));
						asset.set_CustomColumn("ParentAsset", iLine.get_ValueAsString("ParentAsset"));
						iLine.set_CustomColumn("AssetSituation", asset.get_ValueAsString("AssetSituation"));
						iLine.set_CustomColumn("A_Asset_Group_Ref2_ID", asset.getA_Asset_Group_ID());
						iLine.set_CustomColumn("A_Asset_Group_Ref_ID", asset.get_ValueAsInt("A_Asset_Group_Ref_ID"));
						//se agrega subclase
						iLine.set_CustomColumn("A_Asset_Group_Ref3_ID", asset.get_ValueAsInt("A_Asset_Group_Ref2_ID"));
						if(asset.get_ValueAsInt("S_Resource_ID")>0)
							iLine.set_CustomColumn("S_Resource_ID", asset.get_ValueAsInt("S_Resource_ID"));
						if(asset.get_ValueAsInt("S_ResourceRef_ID")>0)
							iLine.set_CustomColumn("S_ResourceRef_ID", asset.get_ValueAsInt("S_ResourceRef_ID"));
						//iLine.saveEx(po.get_TrxName());
					}
				}
			}
		}
		if((type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE) && po.get_Table_ID()==MInvoice.Table_ID
				&& po.is_ValueChanged("DocStatus")) 
		{	
			MInvoice inv = (MInvoice)po;
			MDocType dType = new MDocType(po.getCtx(), inv.getC_DocTypeTarget_ID(), po.get_TrxName());
			if(inv.getDocStatus().compareTo("CO") == 0)
			{
				if(dType.get_ValueAsString("Type1").compareTo("ALT") == 0)
				{
					//alta
					//validacion de cantidad de lineas
					int cantLine = DB.getSQLValue(po.get_TrxName(), "SELECT COUNT(1) FROM C_InvoiceLine " +
							"WHERE IsActive = 'Y' AND C_Invoice_ID = "+inv.get_ID());
					if(cantLine != inv.get_ValueAsInt("ValidCantLine"))
					{
						String msgError = "Error en campo: Cantidad de activos no coresponde con lineas";
						String nameField = DB.getSQLValueString(po.get_TrxName(), "SELECT name FROM AD_Field_Trl " +
								" WHERE AD_Field_ID=2003920 AND AD_Language='es_MX'");
						if(nameField != null && nameField.trim().length() > 0)
							msgError = "Error en campo "+nameField+": Cantidad de activos no coresponde con lineas";
						//return(msgError);
						DB.executeUpdate("UPDATE C_Invoice SET ErrorMsg = '"+msgError+"' WHERE C_Invoice_ID ="+inv.get_ID(), po.get_TrxName());
					}
				}
				if(dType.get_ValueAsString("Type1").compareTo("BAJ") == 0)
				{
					MInvoiceLine[] lines = inv.getLines(false);
					for (int lineIndex = 0; lineIndex < lines.length; lineIndex++)
					{
						MInvoiceLine iLine = lines[lineIndex];
						//se valida que exista monto de subasta solo para baja enajenacion
						if(inv.getC_DocTypeTarget_ID() == 2000159)
						{
							BigDecimal Vlr_Subasta = (BigDecimal)iLine.get_Value("Vlr_Subasta");
							if(Vlr_Subasta == null)
								Vlr_Subasta = Env.ZERO;
							if(Vlr_Subasta.compareTo(Env.ZERO) <= 0)
							{
								//return("Todos los activos deben tener valor de subasta. Linea:"+(iLine.getLine()/10));
								DB.executeUpdate("UPDATE C_Invoice SET ErrorMsg = 'Todos los activos deben tener valor de subasta. Linea:"+(iLine.getLine()/10)+"' WHERE C_Invoice_ID ="+inv.get_ID(), po.get_TrxName());
							}
						}
					}
				}
			}
		}
		
		return null;
	}	//	modelChange

	public String docValidate (PO po, int timing)
	{
		log.info(po.get_TableName() + " Timing: "+timing);
		
		return null;
	}	//	docValidate
	
	/**
	 *	User Login.
	 *	Called when preferences are set
	 *	@param AD_Org_ID org
	 *	@param AD_Role_ID role
	 *	@param AD_User_ID user
	 *	@return error message or null
	 */
	public String login (int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		log.info("AD_User_ID=" + AD_User_ID);

		return null;
	}	//	login


	/**
	 *	Get Client to be monitored
	 *	@return AD_Client_ID client
	 */
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}	//	getAD_Client_ID


	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("ModelPrice");
		return sb.toString ();
	}	//	toString


	

}	