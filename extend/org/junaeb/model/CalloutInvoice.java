/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is Compiere ERP & CRM Smart Business Solution. The Initial
 * Developer of the Original Code is Jorg Janke. Portions created by Jorg Janke
 * are Copyright (C) 1999-2005 Jorg Janke.
 * All parts are Copyright (C) 1999-2005 ComPiere, Inc.  All Rights Reserved.
 * Contributor(s): ______________________________________.
 *****************************************************************************/
package org.junaeb.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MAsset;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.adempiere.exceptions.AdempiereException;

/**
 *	Invoice MOP Callouts.
 *	
 *  @author mfrojas.
 *  @version $Id: CalloutInvoice MOP.java,v 2.0 2012/12/03  Exp $
 */
public class CalloutInvoice extends CalloutEngine
{		
	
	public String SetLifeYears (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) throws ParseException
	{
		if(value==null)
			return "";

		if(mTab.getValue("A_Asset_Group_Ref3_ID")==null)
		{
			mTab.setValue("UseLifeYears", Env.ZERO);
			return "";
		}
		//int group= (Integer)mTab.getValue("A_Asset_Group_Ref3_ID");
		//if (group == 0)
			//return "";

		
		int ID_AssetG = (Integer)value;


		if(ID_AssetG > 0)
		{
			int uselifeyears = DB.getSQLValue(null, "SELECT coalesce(max(aa.a_asset_life_years),0) from A_Asset_Group aa where aa.a_asset_group_id="+ID_AssetG);
			mTab.setValue("UseLifeYears", uselifeyears);
		}
		
		return "";
	}
	
	
	public String SetFields (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) throws ParseException
	{
		if(value==null)
			return "";
		
		//obtener id de activo
		int id_activo = (Integer)value;
		
		int id_registro = (Integer)mTab.getValue("C_Invoice_ID");
		int doctype_id = DB.getSQLValue(null, "SELECT C_DocTypeTarget_ID from C_Invoice where C_Invoice_ID = "+id_registro);
		

		//Revisar tipo de documento. Si sonn altas, hacer nada.
		//Si no es alta, poner los fields dependiendo del model.
		
		//String sql = "SELECT Type1 from C_Invoice where C_Invoice_ID = ?";
		String sql = "SELECT Type1 from C_DocType where C_DocType_ID in (SELECT C_DocTypeTarget_ID from C_Invoice where C_Invoice_ID = ?)";
		String tipo = DB.getSQLValueString(null, sql, id_registro);
		
		//Si el tipo de documento no es alta, traer todos los campos.
		if(tipo.compareTo("MOD") == 0 || tipo.compareTo("BAJ")==0 || tipo.compareTo("TRA")==0)
		{
			MAsset asset = new MAsset(ctx, id_activo, null);
			//Setear ad_org
			//mTab.setValue("AD_Org_ID",asset.getAD_Org_ID());
			mTab.setValue("Description", asset.getName());
			//iLine.setDescription(asset.getName());
			//mTab.setValue("AD_Org_ID",asset.getAD_Org_ID());
			//Se agrega campo org a los campos posibles de traspaso.
			if(doctype_id != 2000163)
				mTab.setValue("AD_NewOrg_ID", asset.getAD_Org_ID());

			mTab.setValue("PriceActual", asset.get_Value("GrandTotal"));
			mTab.setValue("PriceEntered", asset.get_Value("GrandTotal"));
			//iLine.setPrice((BigDecimal)asset.get_Value("GrandTotal"));
			if(asset.getAD_User_ID() > 0)
				mTab.setValue("AD_User_ID", asset.getAD_User_ID());
				//iLine.set_CustomColumn("AD_User_ID", asset.getAD_User_ID());
			mTab.setValue("Brand", asset.get_ValueAsString("Brand"));
			//iLine.set_CustomColumn("Brand", asset.get_ValueAsString("Brand"));
			mTab.setValue("Model", asset.get_ValueAsString("Model"));
			//iLine.set_CustomColumn("Model", asset.get_ValueAsString("Model"));
			mTab.setValue("SerNo", asset.get_ValueAsString("SerNo"));
			//iLine.set_CustomColumn("SerNo", asset.get_ValueAsString("SerNo"));
			mTab.setValue("Color", asset.get_ValueAsString("Color"));
			//iLine.set_CustomColumn("Color", asset.get_ValueAsString("Color"));					
			if(asset.get_ValueAsString("AssetStatus") != null && asset.get_ValueAsString("AssetStatus").trim().length() > 0)
				mTab.setValue("AssetStatus", asset.get_ValueAsString("AssetStatus"));
				//iLine.set_CustomColumn("AssetStatus", asset.get_ValueAsString("AssetStatus"));
			mTab.setValue("A_Parent_Asset_ID", asset.get_ValueAsInt("A_Parent_Asset_ID"));
			//iLine.set_CustomColumn("A_Parent_Asset_ID", asset.get_ValueAsInt("A_Parent_Asset_ID"));
			if(asset.get_ValueAsString("Type1") != null && asset.get_ValueAsString("Type1").trim().length() > 0)
				mTab.setValue("Type1", asset.get_ValueAsString("Type1"));
				//iLine.set_CustomColumn("Type1", asset.get_ValueAsString("Type1"));
			mTab.setValue("Rol", asset.get_ValueAsString("Rol"));
			//iLine.set_CustomColumn("Rol", asset.get_ValueAsString("Rol"));
			//mTab.setValue("GroundSurface", asset.get_ValueAsString("GroundSurface"));
			mTab.setValue("GroundSurface", (BigDecimal)asset.get_Value("GroundSurface"));
			//iLine.set_CustomColumn("GroundSurface", (BigDecimal)asset.get_Value("GroundSurface"));
			//mTab.setValue("EdificationSurface", asset.get_ValueAsString("EdificationSurface"));
			mTab.setValue("EdificationSurface", (BigDecimal)asset.get_Value("EdificationSurface"));
			//iLine.set_CustomColumn("EdificationSurface",(BigDecimal)asset.get_Value("EdificationSurface"));
			mTab.setValue("Engine", asset.get_ValueAsString("Engine"));
			//iLine.set_CustomColumn("Engine", asset.get_ValueAsString("Engine"));
			mTab.setValue("Chassis", asset.get_ValueAsString("Chassis"));
			//iLine.set_CustomColumn("Chassis", asset.get_ValueAsString("Chassis"));
			mTab.setValue("LicensePlate", asset.get_ValueAsString("LicensePlate"));
			//iLine.set_CustomColumn("LicensePlate", asset.get_ValueAsString("LicensePlate"));
			mTab.setValue("Digito", asset.get_ValueAsString("Digito"));
			//iLine.set_CustomColumn("Digito", asset.get_ValueAsString("Digito"));
			mTab.setValue("Sigla", asset.get_ValueAsString("Sigla"));
			//iLine.set_CustomColumn("Sigla", asset.get_ValueAsString("Sigla"));
			mTab.setValue("Fojas", asset.get_ValueAsString("Fojas"));
			//iLine.set_CustomColumn("Fojas", asset.get_ValueAsString("Fojas"));
			mTab.setValue("RegistrationNumber", asset.get_ValueAsString("RegistrationNumber"));
			//iLine.set_CustomColumn("RegistrationNumber", asset.get_ValueAsString("RegistrationNumber"));
			mTab.setValue("Year", asset.get_ValueAsString("Year"));
			//iLine.set_CustomColumn("Year", asset.get_ValueAsString("Year"));	
			log.config("asset inmueble "+asset.get_ValueAsString("Inmueble"));
			if(asset.get_ValueAsString("Inmueble") != null && asset.get_ValueAsString("Inmueble").trim().length() > 0)
			{
				String inm = asset.get_ValueAsString("Inmueble");
				log.config("inmueble es "+inm);
				log.config("Review ");
				mTab.setValue("Inmueble", inm);
				//mTab.setValue("Inmueble", inm);
				log.config("inmueble original "+mTab.getValue("Inmueble"));
				log.config("review 3");
				//mTab.setValue("Inmueble", asset.get_ValueAsString("Inmueble").trim());
				//iLine.set_CustomColumn("Inmueble", asset.get_ValueAsString("Inmueble"));
			}
			if(asset.get_ValueAsString("Inmueble") != null && asset.get_ValueAsString("Inmueble").trim().length() > 0)
				mTab.setValue("Inmueble", asset.get_ValueAsString("Inmueble"));
			if(asset.get_ValueAsInt("C_Region_ID") > 0)
			{
				mTab.setValue("C_Region_ID", asset.get_ValueAsInt("C_Region_ID"));
				if(doctype_id != 2000163)
					mTab.setValue("C_NewRegion_ID", asset.get_ValueAsInt("C_Region_ID"));
			}
				//iLine.set_CustomColumn("C_Region_ID", asset.get_ValueAsInt("C_Region_ID"));
			if(asset.get_ValueAsInt("C_Province_ID") > 0)
			{
				mTab.setValue("C_Province_ID", asset.get_ValueAsInt("C_Province_ID"));
				if(doctype_id != 2000163)
					mTab.setValue("C_NewProvince_ID", asset.get_ValueAsInt("C_Province_ID"));
			}
				//iLine.set_CustomColumn("C_Province_ID", asset.get_ValueAsInt("C_Province_ID"));
			if(asset.get_ValueAsInt("C_City_ID") > 0)
				mTab.setValue("C_City_ID", asset.get_ValueAsString("C_City_ID"));
				//iLine.set_CustomColumn("C_City_ID", asset.get_ValueAsString("C_City_ID"));
			if(asset.get_ValueAsString("DocDestination") != null && asset.get_ValueAsString("DocDestination").trim().length() > 0)
				mTab.setValue("DocDestination", asset.get_ValueAsString("DocDestination"));
				//iLine.set_CustomColumn("DocDestination", asset.get_ValueAsString("DocDestination"));
			mTab.setValue("DocNumber", asset.get_ValueAsString("DocNumber"));
			//iLine.set_CustomColumn("DocNumber", asset.get_ValueAsString("DocNumber"));
			//iLine.set_CustomColumn("Conservador", asset.get_ValueAsString("Conservador"));
			mTab.setValue("Conservador", asset.get_ValueAsString("Conservador"));
			mTab.setValue("Notaria", asset.get_ValueAsString("Notaria"));
			//iLine.set_CustomColumn("Notaria", asset.get_ValueAsString("Notaria"));
			mTab.setValue("N_Fecha_ValeVista", asset.get_ValueAsString("N_Fecha_ValeVista"));
			//iLine.set_CustomColumn("N_Fecha_ValeVista", asset.get_ValueAsString("N_Fecha_ValeVista"));
			
			BigDecimal valevista = (BigDecimal)mTab.getValue("Vlr_ValeVista");
			if(valevista == null || valevista.compareTo(Env.ZERO)<=0)
				mTab.setValue("Vlr_ValeVista", (BigDecimal)asset.get_Value("Vlr_ValeVista"));

			mTab.setValue("DocNumber2", asset.get_ValueAsString("DocNumber2"));
			//iLine.set_CustomColumn("DocNumber2", asset.get_ValueAsString("DocNumber2"));
			//asset.set_CustomColumn("ParentAsset", iLine.get_ValueAsString("ParentAsset"));
			mTab.setValue("ParentAsset", asset.get_ValueAsString("ParentAsset"));
			mTab.setValue("AssetSituation", asset.get_ValueAsString("AssetSituation"));
			//iLine.set_CustomColumn("AssetSituation", asset.get_ValueAsString("AssetSituation"));
			int claseactivo = asset.getA_Asset_Group_ID();
			log.config("clase activo"+claseactivo);

			mTab.setValue("A_Asset_Group_Ref_ID", asset.get_ValueAsInt("A_Asset_Group_Ref_ID"));
			//iLine.set_CustomColumn("A_Asset_Group_Ref_ID", asset.get_ValueAsInt("A_Asset_Group_Ref_ID"));
			mTab.setValue("A_Asset_Group_Ref2_ID", claseactivo);
			//iLine.set_CustomColumn("A_Asset_Group_Ref2_ID", asset.getA_Asset_Group_ID());
			
			//se agrega subclase
			mTab.setValue("A_Asset_Group_Ref3_ID", asset.get_ValueAsInt("A_Asset_Group_Ref2_ID"));
			//iLine.set_CustomColumn("A_Asset_Group_Ref3_ID", asset.get_ValueAsInt("A_Asset_Group_Ref2_ID"));
			if(asset.get_ValueAsInt("S_Resource_ID")>0)
				mTab.setValue("S_Resource_ID", asset.get_ValueAsInt("S_Resource_ID"));
				//iLine.set_CustomColumn("S_Resource_ID", asset.get_ValueAsInt("S_Resource_ID"));
			if(asset.get_ValueAsInt("S_ResourceRef_ID")>0)
				mTab.setValue("S_ResourceRef_ID", asset.get_ValueAsInt("S_ResourceRef_ID"));
				//iLine.set_CustomColumn("S_ResourceRef_ID", asset.get_ValueAsInt("S_ResourceRef_ID"));
			//iLine.saveEx(po.get_TrxName());
			if(asset.get_ValueAsString("Inmueble") != null && asset.get_ValueAsString("Inmueble").trim().length() > 0)
				mTab.setValue("Inmueble", asset.get_ValueAsString("Inmueble"));
			if(asset.get_ValueAsString("Renewable") != null)
				mTab.setValue("Renewable", asset.get_ValueAsString("Renewable"));
			
			mTab.setValue("ExpirationDate", (Timestamp)asset.get_Value("ExpirationDate"));
			mTab.setValue("Date1", (Timestamp)asset.get_Value("Date1"));
			if(doctype_id == 2000163)
				mTab.setValue("AD_NewOrg_ID", 0);



		}
						
						
		return "";
	}
	public String ValidateYear (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) throws ParseException
	{
		if(value==null)
			return "";
		if(value=="")
			return "";
		
		//obtener id de activo
		String year = (String)value;
		int yearint = 0;
		try
		{
			yearint = Integer.parseInt(year);
		}
		catch (Exception e)
		{
			log.config("Verificar campo año");
			
			mTab.setValue("year", null);
			throw new AdempiereException("Verificar campo año. Debe ser numeros");
		}



		
		return "";
	}


	
}


