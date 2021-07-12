/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for HR_HourPlanningLine
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_HR_HourPlanningLine extends PO implements I_HR_HourPlanningLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180514L;

    /** Standard Constructor */
    public X_HR_HourPlanningLine (Properties ctx, int HR_HourPlanningLine_ID, String trxName)
    {
      super (ctx, HR_HourPlanningLine_ID, trxName);
      /** if (HR_HourPlanningLine_ID == 0)
        {
			setHR_HourPlanningline_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HR_HourPlanningLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_HR_HourPlanningLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DayTime.
		@param DayTime 
		Hour of day working
	  */
	public void setDayTime (int DayTime)
	{
		set_Value (COLUMNNAME_DayTime, Integer.valueOf(DayTime));
	}

	/** Get DayTime.
		@return Hour of day working
	  */
	public int getDayTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DayTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DayTime1.
		@param DayTime1 
		Hour of day working
	  */
	public void setDayTime1 (int DayTime1)
	{
		set_Value (COLUMNNAME_DayTime1, Integer.valueOf(DayTime1));
	}

	/** Get DayTime1.
		@return Hour of day working
	  */
	public int getDayTime1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DayTime1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DayTimeReal.
		@param DayTimeReal 
		Hour of day working
	  */
	public void setDayTimeReal (int DayTimeReal)
	{
		set_Value (COLUMNNAME_DayTimeReal, Integer.valueOf(DayTimeReal));
	}

	/** Get DayTimeReal.
		@return Hour of day working
	  */
	public int getDayTimeReal () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DayTimeReal);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DayTimeReal1.
		@param DayTimeReal1 
		Hour of day working
	  */
	public void setDayTimeReal1 (int DayTimeReal1)
	{
		set_Value (COLUMNNAME_DayTimeReal1, Integer.valueOf(DayTimeReal1));
	}

	/** Get DayTimeReal1.
		@return Hour of day working
	  */
	public int getDayTimeReal1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DayTimeReal1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	public I_HR_HourPlanningControl getHR_HourPlanningControl() throws RuntimeException
    {
		return (I_HR_HourPlanningControl)MTable.get(getCtx(), I_HR_HourPlanningControl.Table_Name)
			.getPO(getHR_HourPlanningControl_ID(), get_TrxName());	}

	/** Set Hour Planning Control ID.
		@param HR_HourPlanningControl_ID Hour Planning Control ID	  */
	public void setHR_HourPlanningControl_ID (int HR_HourPlanningControl_ID)
	{
		if (HR_HourPlanningControl_ID < 1) 
			set_Value (COLUMNNAME_HR_HourPlanningControl_ID, null);
		else 
			set_Value (COLUMNNAME_HR_HourPlanningControl_ID, Integer.valueOf(HR_HourPlanningControl_ID));
	}

	/** Get Hour Planning Control ID.
		@return Hour Planning Control ID	  */
	public int getHR_HourPlanningControl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_HourPlanningControl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set hr_hourplanningline  ID.
		@param HR_HourPlanningline_ID hr_hourplanningline  ID	  */
	public void setHR_HourPlanningline_ID (int HR_HourPlanningline_ID)
	{
		if (HR_HourPlanningline_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_HourPlanningline_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_HourPlanningline_ID, Integer.valueOf(HR_HourPlanningline_ID));
	}

	/** Get hr_hourplanningline  ID.
		@return hr_hourplanningline  ID	  */
	public int getHR_HourPlanningline_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_HourPlanningline_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set NightTime.
		@param NightTime NightTime	  */
	public void setNightTime (int NightTime)
	{
		set_Value (COLUMNNAME_NightTime, Integer.valueOf(NightTime));
	}

	/** Get NightTime.
		@return NightTime	  */
	public int getNightTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NightTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set NightTime1.
		@param NightTime1 NightTime1	  */
	public void setNightTime1 (int NightTime1)
	{
		set_Value (COLUMNNAME_NightTime1, Integer.valueOf(NightTime1));
	}

	/** Get NightTime1.
		@return NightTime1	  */
	public int getNightTime1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NightTime1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set NightTimeReal.
		@param NightTimeReal NightTimeReal	  */
	public void setNightTimeReal (int NightTimeReal)
	{
		set_Value (COLUMNNAME_NightTimeReal, Integer.valueOf(NightTimeReal));
	}

	/** Get NightTimeReal.
		@return NightTimeReal	  */
	public int getNightTimeReal () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NightTimeReal);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set NightTimeReal1.
		@param NightTimeReal1 NightTimeReal1	  */
	public void setNightTimeReal1 (BigDecimal NightTimeReal1)
	{
		set_Value (COLUMNNAME_NightTimeReal1, NightTimeReal1);
	}

	/** Get NightTimeReal1.
		@return NightTimeReal1	  */
	public BigDecimal getNightTimeReal1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NightTimeReal1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set resolutionnumber.
		@param resolutionnumber resolutionnumber	  */
	public void setresolutionnumber (String resolutionnumber)
	{
		set_Value (COLUMNNAME_resolutionnumber, resolutionnumber);
	}

	/** Get resolutionnumber.
		@return resolutionnumber	  */
	public String getresolutionnumber () 
	{
		return (String)get_Value(COLUMNNAME_resolutionnumber);
	}

	/** Set resolutionnumber2.
		@param resolutionnumber2 resolutionnumber2	  */
	public void setresolutionnumber2 (String resolutionnumber2)
	{
		set_Value (COLUMNNAME_resolutionnumber2, resolutionnumber2);
	}

	/** Get resolutionnumber2.
		@return resolutionnumber2	  */
	public String getresolutionnumber2 () 
	{
		return (String)get_Value(COLUMNNAME_resolutionnumber2);
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ValueDayTime.
		@param ValueDayTime 
		Value of daytime
	  */
	public void setValueDayTime (BigDecimal ValueDayTime)
	{
		set_Value (COLUMNNAME_ValueDayTime, ValueDayTime);
	}

	/** Get ValueDayTime.
		@return Value of daytime
	  */
	public BigDecimal getValueDayTime () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValueDayTime);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ValueNightTime.
		@param ValueNightTime 
		Value of nighttime
	  */
	public void setValueNightTime (BigDecimal ValueNightTime)
	{
		set_Value (COLUMNNAME_ValueNightTime, ValueNightTime);
	}

	/** Get ValueNightTime.
		@return Value of nighttime
	  */
	public BigDecimal getValueNightTime () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValueNightTime);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}