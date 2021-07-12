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
package org.clinicacolonial.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.X_CC_CIE10;



/**
 *	Clinica Colonial Callouts .
 *	
 *  @author mfrojas
 *  @version $Id: 20170518 
 *  
 */
public class CalloutClinicaColonial extends CalloutEngine{


	public String validBirthDate (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value==null || value=="")
			return "";
		

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Date fi = (java.util.Date)mTab.getValue("birthday");
		if(fi.compareTo(timestamp)>0)
		{
			mTab.setValue(mField.getColumnName(), timestamp);
			return "La fecha de nacimiento no puede ser superior al día de hoy";
		}
		
		return "";
    }
	
	public String fillSchedule (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value==null || value=="")
			return "";

		String sched = " ";
		
		if(mTab.getValueAsBoolean("Signature1") == true)
			sched= sched.concat("1-");
		if(mTab.getValueAsBoolean("Signature2") == true)
			sched= sched.concat("2-");
		if(mTab.getValueAsBoolean("Signature3") == true)
			sched= sched.concat("3-");
		if(mTab.getValueAsBoolean("Signature4") == true)
			sched= sched.concat("4-");
		if(mTab.getValueAsBoolean("Signature5") == true)
			sched= sched.concat("5-");
		if(mTab.getValueAsBoolean("Signature6") == true)
			sched= sched.concat("6-");
		if(mTab.getValueAsBoolean("Signature7") == true)
			sched= sched.concat("7-");
		if(mTab.getValueAsBoolean("Signature8") == true)
			sched= sched.concat("8-");
		if(mTab.getValueAsBoolean("Signature9") == true)
			sched= sched.concat("9-");
		if(mTab.getValueAsBoolean("Signature10") == true)
			sched= sched.concat("10-");
		if(mTab.getValueAsBoolean("Signature11") == true)
			sched= sched.concat("11-");
		if(mTab.getValueAsBoolean("Signature12") == true)
			sched= sched.concat("12-");
		if(mTab.getValueAsBoolean("Signature13") == true)
			sched= sched.concat("13-");
		if(mTab.getValueAsBoolean("Signature14") == true)
			sched= sched.concat("14-");
		if(mTab.getValueAsBoolean("Signature15") == true)
			sched= sched.concat("15-");
		if(mTab.getValueAsBoolean("Signature16") == true)
			sched= sched.concat("16-");
		if(mTab.getValueAsBoolean("Signature17") == true)
			sched= sched.concat("17-");
		if(mTab.getValueAsBoolean("Signature18") == true)
			sched= sched.concat("18-");
		if(mTab.getValueAsBoolean("Signature19") == true)
			sched= sched.concat("19-");
		if(mTab.getValueAsBoolean("Signature20") == true)
			sched= sched.concat("20-");
		if(mTab.getValueAsBoolean("Signature21") == true)
			sched= sched.concat("21-");
		if(mTab.getValueAsBoolean("Signature22") == true)
			sched= sched.concat("22-");
		if(mTab.getValueAsBoolean("Signature23") == true)
			sched= sched.concat("23-");
		if(mTab.getValueAsBoolean("Signature24") == true)
			sched= sched.concat("24-");
		
				
		
		mTab.setValue("schedule", sched);
		return "";
	}
	
	public String popupCIE10 (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value==null || value=="")
			return "";
	
		int cie10 = 0;
		int cie102 = 0;
		int cie103 = 0;
		if((Integer)mTab.getValue("CC_CIE10_ID") != null)
			cie10 = (Integer)mTab.getValue("CC_CIE10_ID");
		if((Integer)mTab.getValue("CC_CIE102_ID") != null)
			cie102 = (Integer)mTab.getValue("CC_CIE10_2_ID");
		if((Integer)mTab.getValue("CC_CIE103_ID") != null)
			cie103 = (Integer)mTab.getValue("CC_CIE10_3_ID");
		
		X_CC_CIE10 cie = null;	
		if(cie10 > 0)
		{
			cie = new X_CC_CIE10(Env.getCtx(),cie10, null);
			if(cie.getDescription() != null)
			{
				mTab.fireDataStatusEEvent ("Alerta ", "Recuerde notificar en base a "+cie.getName(), true);
				return "";
			}

		}
		if(cie102 > 0)
		{
			cie = new X_CC_CIE10(Env.getCtx(),cie102, null);
			if(cie.getDescription() != null)
			{
				mTab.fireDataStatusEEvent ("Alerta ", "Recuerde notificar en base a "+cie.getName(), true);
				return "";
			}

		}		
		if(cie103 > 0)
		{
			cie = new X_CC_CIE10(Env.getCtx(),cie103, null);
			if(cie.getDescription() != null)
			{
				mTab.fireDataStatusEEvent ("Alerta ", "Recuerde notificar en base a "+cie.getName(), true);
				return "";
			}

		}

		return "";
	}
	
}	//	CalloutOrder

