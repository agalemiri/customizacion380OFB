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
import org.compiere.util.DB;

/**
 *	Administrative Requests Callouts.
 *	
 *  @author mfrojas.
 *  @version $Id: CalloutRHAdministrativeRequests.java,v 2.0 2012/12/03  Exp $
 */
public class CalloutAdministrativeRequests extends CalloutEngine
{		
	public static int bpartner = 0;
	public static int org = 0;
	public String EndDateSVC (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) throws ParseException
	{
		if(value==null)
			return "";
		
		if (mTab.get_ValueAsString("RequestType").equals("SVC"))			
		{		
			if(mTab.getValue("datestartrequest") != null)			
			{
				java.util.Date fi = (java.util.Date)mTab.getValue("datestartrequest");
				BigDecimal qtyDays= (BigDecimal)mTab.getValue("Days");
				int qty = Integer.valueOf(qtyDays.intValue());
				//mfrojas 20190821 obtener c_bpartner_id
				
				bpartner = DB.getSQLValue(null, "SELECT c_bpartner_id from hr_Administrativerequests where " +
						" hr_administrativerequests_id = "+mTab.getValue("HR_AdministrativeRequests_ID"));
				
				if(bpartner > 0)
					org = DB.getSQLValue(null, "SELECT coalesce(ad_orgref2_id,0) from hr_employee where c_bpartner_id = "+bpartner);
				//Date ff = calculateEndDate(fi, qty); //se cambia metodo para que tambien tome los feriados} ininoles				
				java.util.Date ff = calculateEndDateFeriados(fi, qty);
	
				Timestamp cff = new Timestamp(ff.getTime());
				
				mTab.setValue("dateendrequest", cff);
			}
		}
		
		return "";
	}
	public String EndHoursCOM (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) throws ParseException
	{
		if(value==null)
			return "";
		
		if (mTab.get_ValueAsString("RequestType").equals("COM"))			
		{		
			if(mTab.getValue("hoursavailable") != null && mTab.getValue("hours") != null)			
			{
				Timestamp dateStart = (Timestamp)mTab.getValue("hoursavailable");
				BigDecimal qtyDays= (BigDecimal)mTab.getValue("hours");
				/*int qty = Integer.valueOf(qtyDays.intValue());
				Calendar calendar = Calendar.getInstance();
			    calendar.setTimeInMillis(dateStart.getTime());
			    calendar.add(Calendar.HOUR_OF_DAY,qty);
			    //calendar.sett
			    Timestamp endDate = new Timestamp(calendar.getTimeInMillis());
				mTab.setValue("hoursused", endDate);
				 */
				//mfrojas 20180503 Cambio de forma, para aceptar horas y minutos
				BigDecimal minutes = qtyDays.multiply(new BigDecimal(60));
				int minutesInt = Integer.valueOf(minutes.intValue());
				log.config("minutes int "+minutesInt);
				Calendar calendar = Calendar.getInstance();
			    calendar.setTimeInMillis(dateStart.getTime());
			    calendar.add(Calendar.MINUTE,minutesInt);
			    //calendar.sett
			    Timestamp endDate = new Timestamp(calendar.getTimeInMillis());
				mTab.setValue("hoursused", endDate);
				
				
			}
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
	    while (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
	    {
	      startCal.add(Calendar.DAY_OF_MONTH, 1);
	    }
	  }
	 
	  return startCal.getTime();
	}
	
	public static java.util.Date calculateEndDateFeriados(Date startDate, int duration)
	{		
	  Calendar startCal = Calendar.getInstance();
	 
	  startCal.setTime(startDate);
			
	  for (int i = 1; i < duration; i++)
	  {
	    startCal.add(Calendar.DAY_OF_MONTH, 1);
	    while (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
	    {
	      startCal.add(Calendar.DAY_OF_MONTH, 1);
	    }
	    if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
	    {
	    	startCal.set(Calendar.HOUR_OF_DAY, 00);
	    	startCal.set(Calendar.MINUTE, 00);
	    	startCal.set(Calendar.SECOND, 00);
	    	
	    	Timestamp Hactual= new Timestamp(startCal.getTimeInMillis());
	    	    		    	
	    	String sqlDY = "select count(1) from C_NonBusinessDay where isactive='Y' " +
	    			" and trunc(date1) = '"+Hactual+"' AND " +
	    					" (ad_org_id = 0 OR ad_org_id = "+org+")";	    	
	    	int feriado = DB.getSQLValue(null,sqlDY);
	    	if(feriado >= 1)
	    	{
	    		//startCal.add(Calendar.DAY_OF_MONTH, 1);
	    		i = i-1;
	    	}
	    }

	  }
	 
	  return startCal.getTime();
	}

	
}


