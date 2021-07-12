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
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for MED_ScheduleTime
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_MED_ScheduleTime extends PO implements I_MED_ScheduleTime, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190711L;

    /** Standard Constructor */
    public X_MED_ScheduleTime (Properties ctx, int MED_ScheduleTime_ID, String trxName)
    {
      super (ctx, MED_ScheduleTime_ID, trxName);
      /** if (MED_ScheduleTime_ID == 0)
        {
			setMED_ScheduleTime_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MED_ScheduleTime (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MED_ScheduleTime[")
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

	public I_MED_ScheduleDay getMED_ScheduleDay() throws RuntimeException
    {
		return (I_MED_ScheduleDay)MTable.get(getCtx(), I_MED_ScheduleDay.Table_Name)
			.getPO(getMED_ScheduleDay_ID(), get_TrxName());	}

	/** Set MED_ScheduleDay ID.
		@param MED_ScheduleDay_ID MED_ScheduleDay ID	  */
	public void setMED_ScheduleDay_ID (int MED_ScheduleDay_ID)
	{
		if (MED_ScheduleDay_ID < 1) 
			set_Value (COLUMNNAME_MED_ScheduleDay_ID, null);
		else 
			set_Value (COLUMNNAME_MED_ScheduleDay_ID, Integer.valueOf(MED_ScheduleDay_ID));
	}

	/** Get MED_ScheduleDay ID.
		@return MED_ScheduleDay ID	  */
	public int getMED_ScheduleDay_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MED_ScheduleDay_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MED_ScheduleTime ID.
		@param MED_ScheduleTime_ID MED_ScheduleTime ID	  */
	public void setMED_ScheduleTime_ID (int MED_ScheduleTime_ID)
	{
		if (MED_ScheduleTime_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MED_ScheduleTime_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MED_ScheduleTime_ID, Integer.valueOf(MED_ScheduleTime_ID));
	}

	/** Get MED_ScheduleTime ID.
		@return MED_ScheduleTime ID	  */
	public int getMED_ScheduleTime_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MED_ScheduleTime_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_MED_Specialty getMED_Specialty() throws RuntimeException
    {
		return (I_MED_Specialty)MTable.get(getCtx(), I_MED_Specialty.Table_Name)
			.getPO(getMED_Specialty_ID(), get_TrxName());	}

	/** Set MED_Specialty ID.
		@param MED_Specialty_ID MED_Specialty ID	  */
	public void setMED_Specialty_ID (int MED_Specialty_ID)
	{
		if (MED_Specialty_ID < 1) 
			set_Value (COLUMNNAME_MED_Specialty_ID, null);
		else 
			set_Value (COLUMNNAME_MED_Specialty_ID, Integer.valueOf(MED_Specialty_ID));
	}

	/** Get MED_Specialty ID.
		@return MED_Specialty ID	  */
	public int getMED_Specialty_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MED_Specialty_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set State.
		@param State State	  */
	public void setState (String State)
	{
		set_Value (COLUMNNAME_State, State);
	}

	/** Get State.
		@return State	  */
	public String getState () 
	{
		return (String)get_Value(COLUMNNAME_State);
	}

	/** Set TimeRequested.
		@param TimeRequested TimeRequested	  */
	public void setTimeRequested (Timestamp TimeRequested)
	{
		set_Value (COLUMNNAME_TimeRequested, TimeRequested);
	}

	/** Get TimeRequested.
		@return TimeRequested	  */
	public Timestamp getTimeRequested () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeRequested);
	}
}