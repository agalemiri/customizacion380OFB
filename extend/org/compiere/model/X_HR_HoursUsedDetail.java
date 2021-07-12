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

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for HR_HoursUsedDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_HR_HoursUsedDetail extends PO implements I_HR_HoursUsedDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190402L;

    /** Standard Constructor */
    public X_HR_HoursUsedDetail (Properties ctx, int HR_HoursUsedDetail_ID, String trxName)
    {
      super (ctx, HR_HoursUsedDetail_ID, trxName);
      /** if (HR_HoursUsedDetail_ID == 0)
        {
			setHR_HoursUsedDetail_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HR_HoursUsedDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_HoursUsedDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set hours.
		@param hours hours	  */
	public void sethours (int hours)
	{
		set_Value (COLUMNNAME_hours, Integer.valueOf(hours));
	}

	/** Get hours.
		@return hours	  */
	public int gethours () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_hours);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HR_AdministrativeRequests getHR_AdministrativeRequests() throws RuntimeException
    {
		return (I_HR_AdministrativeRequests)MTable.get(getCtx(), I_HR_AdministrativeRequests.Table_Name)
			.getPO(getHR_AdministrativeRequests_ID(), get_TrxName());	}

	/** Set HR_AdministrativeRequests.
		@param HR_AdministrativeRequests_ID HR_AdministrativeRequests	  */
	public void setHR_AdministrativeRequests_ID (int HR_AdministrativeRequests_ID)
	{
		if (HR_AdministrativeRequests_ID < 1) 
			set_Value (COLUMNNAME_HR_AdministrativeRequests_ID, null);
		else 
			set_Value (COLUMNNAME_HR_AdministrativeRequests_ID, Integer.valueOf(HR_AdministrativeRequests_ID));
	}

	/** Get HR_AdministrativeRequests.
		@return HR_AdministrativeRequests	  */
	public int getHR_AdministrativeRequests_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_AdministrativeRequests_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HR_HourPlanningLine getHR_HourPlanningLine() throws RuntimeException
    {
		return (I_HR_HourPlanningLine)MTable.get(getCtx(), I_HR_HourPlanningLine.Table_Name)
			.getPO(getHR_HourPlanningLine_ID(), get_TrxName());	}

	/** Set Hour Planning Line  ID.
		@param HR_HourPlanningLine_ID Hour Planning Line  ID	  */
	public void setHR_HourPlanningLine_ID (int HR_HourPlanningLine_ID)
	{
		if (HR_HourPlanningLine_ID < 1) 
			set_Value (COLUMNNAME_HR_HourPlanningLine_ID, null);
		else 
			set_Value (COLUMNNAME_HR_HourPlanningLine_ID, Integer.valueOf(HR_HourPlanningLine_ID));
	}

	/** Get Hour Planning Line  ID.
		@return Hour Planning Line  ID	  */
	public int getHR_HourPlanningLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_HourPlanningLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}


	/** Set HR_HoursAvailable ID.
		@param HR_HoursAvailable_ID HR_HoursAvailable ID	  */
	public void setHR_HoursAvailable_ID (int HR_HoursAvailable_ID)
	{
		if (HR_HoursAvailable_ID < 1) 
			set_Value (COLUMNNAME_HR_HoursAvailable_ID, null);
		else 
			set_Value (COLUMNNAME_HR_HoursAvailable_ID, Integer.valueOf(HR_HoursAvailable_ID));
	}

	/** Get HR_HoursAvailable ID.
		@return HR_HoursAvailable ID	  */
	public int getHR_HoursAvailable_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_HoursAvailable_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HR_HoursUsedDetail ID.
		@param HR_HoursUsedDetail_ID HR_HoursUsedDetail ID	  */
	public void setHR_HoursUsedDetail_ID (int HR_HoursUsedDetail_ID)
	{
		if (HR_HoursUsedDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_HoursUsedDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_HoursUsedDetail_ID, Integer.valueOf(HR_HoursUsedDetail_ID));
	}

	/** Get HR_HoursUsedDetail ID.
		@return HR_HoursUsedDetail ID	  */
	public int getHR_HoursUsedDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_HoursUsedDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}