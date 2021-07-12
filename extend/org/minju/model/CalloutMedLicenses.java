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
package org.minju.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartner;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.X_RH_MedicalLicenses;

/**
 *	Order Callouts.
 *	
 *  @author Italo Niñoles OFB ininoles.
 *  @version $Id: CalloutRHAdministrativeRequests.java,v 2.0 2012/12/03  Exp $
 */
public class CalloutMedLicenses extends CalloutEngine
{		
	public String EndDate (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) throws ParseException
	{
		if(value==null)
			return "";

		Date fi = (java.util.Date)mTab.getValue("datestartrequest");		
		BigDecimal qtyDays= (BigDecimal)mTab.getValue("Days");
		int qty = Integer.valueOf(qtyDays.intValue());
		//mfrojas Si la licencia médica es de tipo postnatal extendido, y la jornada es parcial
		//entonces los días no son 84.. Son 126
		
		//begin
		String LicenseType = (String)mTab.getValue("LicenseType");
		String Charac = (String)mTab.getValue("Characteristics");
		
		if(LicenseType.compareToIgnoreCase("MT")==0 && Charac.compareToIgnoreCase("02")==0)
		{
			qty = 126;
			BigDecimal newnumber = new BigDecimal(qty);
			
			mTab.setValue("Days",newnumber);		
		}
		if(LicenseType.compareToIgnoreCase("MT")==0 && Charac.compareToIgnoreCase("01")==0)
		{
			qty = 84;
			BigDecimal newnumber = new BigDecimal(qty);
			
			mTab.setValue("Days",newnumber);		
		}
		
		
		//end
		if(fi != null || qtyDays != null)
		{
			Date ff = calculateEndDate(fi, qty);		
			Timestamp cff = new Timestamp(ff.getTime());			
			mTab.setValue("DateEnd", cff);	
		}
		return "";
	}
	public String SetOrg (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) throws ParseException
	{
		if(value==null)
			return "";
		int ID_BPartner = (Integer)value;
		if(ID_BPartner > 0)
		{
			MBPartner bPart = new MBPartner(ctx, ID_BPartner, null);
			if(bPart.getAD_Org_ID() > 0)
				mTab.setValue("AD_Org_ID",bPart.get_ValueAsInt("AD_OrgRef_ID"));
		}
		return "";
	}
	
	public static java.util.Date calculateEndDate(Date startDate, int duration)
	{		
	  Calendar startCal = Calendar.getInstance();
	 
	  startCal.setTime(startDate);
			
	  for (int i = 1; i < duration; i++)
	  {
	    startCal.add(Calendar.DAY_OF_MONTH, 1);
	  }
	  return startCal.getTime();
	}
	public String noNegativeValue (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) throws ParseException
	{
		if(value==null)
			return "";
		BigDecimal num = (BigDecimal)value;
		if(num.compareTo(Env.ZERO) <= 0)
			num = num.negate();
		mTab.setValue(mField.getColumnName(),num);		
		return "";
	}
	public String setValuesOfBPartner (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) throws ParseException
	{
	
		if(value==null)
			return "";
		int ID_BPartner = (Integer)value;
		if(ID_BPartner > 0)
		{
			MBPartner bPart = new MBPartner(ctx,ID_BPartner,null);
			if(bPart.get_Value("JoinDateS") != null)
				mTab.setValue("JoinDateS", bPart.get_Value("JoinDateS"));
			if(bPart.get_Value("Suplencia") != null)
				mTab.setValue("Substitute_ID", bPart.get_Value("Suplencia"));
			if(bPart.get_Value("Gender") != null)
				mTab.setValue("Gender", bPart.get_Value("Gender"));
			if(bPart.get_Value("ISAPRE") != null)
				mTab.setValue("ISAPRE", bPart.get_Value("ISAPRE"));
			if(bPart.get_Value("AFP") != null)
				mTab.setValue("AFP", bPart.get_Value("AFP"));
			if(bPart.get_Value("Estate") != null)
				mTab.setValue("Estate", bPart.get_Value("Estate"));
			if(bPart.get_Value("Suplencia") != null)
				mTab.setValue("Substitute_ID", bPart.get_Value("Suplencia"));

			//AGREGAR SQL DE PARENT ORG DE SOCIO DE NEGOCIO.
			
		}
				
		return "";

	}
	
	public String setDays(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) throws ParseException
	{
		if(value == null)
			return "";
		
		String charac = (String)value;
		String LicenseType = "";
		LicenseType = (String)mTab.getValue("LicenseType");
		if(charac.compareToIgnoreCase("01") == 0)
		{
			if(LicenseType.compareToIgnoreCase("LicenseType")==0)
			{
				mTab.setValue("days", 84);
			}

		}
		else if (charac.compareToIgnoreCase("02") == 0)
		{
			if(LicenseType.compareToIgnoreCase("MT")==0)
			{
				mTab.setValue("days", 126);
			}
		}
		
		return "";
	}
	
	public String findOldLicense(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) throws ParseException
	{
		if(value == null)
			return "";
		Date fi = (java.util.Date)mTab.getValue("datestartrequest");		
		Calendar startCal = Calendar.getInstance();

		startCal.setTime(fi);
		startCal.add(Calendar.DAY_OF_MONTH, -1);

		Date ff = startCal.getTime();
		
		Timestamp cff = new Timestamp(ff.getTime());
		int idlicencia = 0;
		if(mTab.getValue("RH_MedicalLicenses_ID") != null)
			idlicencia = (Integer)mTab.getValue("RH_MedicalLicenses_ID");
		String sqlgetoldlicense = "SELECT coalesce(max(rh_medicallicenses_id),0) from rh_medicallicenses where c_bpartner_id = " +mTab.getValue("C_BPartner_ID")+
				"  AND dateend = '"+cff+"' AND rh_medicallicenses_id != "+idlicencia;
		log.config("oldlicense " +sqlgetoldlicense);
		int oldlicense = DB.getSQLValue("RH_MedicalLicenses", sqlgetoldlicense);
		
		if(oldlicense > 0)
		{	
			X_RH_MedicalLicenses med = new X_RH_MedicalLicenses(ctx,oldlicense,null);
			mTab.fireDataStatusEEvent ("¿Es licencia de continuidad?", "Licencia (FOLIO "+med.getFolio()+") vence el día "+cff.toLocaleString().substring(0, 10)+".", true);
		}
		return "";
	}
}


