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

/** Generated Model for MED_ScheduleDay
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_MED_ScheduleDay extends PO implements I_MED_ScheduleDay, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190711L;

    /** Standard Constructor */
    public X_MED_ScheduleDay (Properties ctx, int MED_ScheduleDay_ID, String trxName)
    {
      super (ctx, MED_ScheduleDay_ID, trxName);
      /** if (MED_ScheduleDay_ID == 0)
        {
			setMED_ScheduleDay_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MED_ScheduleDay (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MED_ScheduleDay[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CycleA1.
		@param CycleA1 CycleA1	  */
	public void setCycleA1 (Timestamp CycleA1)
	{
		set_Value (COLUMNNAME_CycleA1, CycleA1);
	}

	/** Get CycleA1.
		@return CycleA1	  */
	public Timestamp getCycleA1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CycleA1);
	}

	/** Set CycleA2.
		@param CycleA2 CycleA2	  */
	public void setCycleA2 (Timestamp CycleA2)
	{
		set_Value (COLUMNNAME_CycleA2, CycleA2);
	}

	/** Get CycleA2.
		@return CycleA2	  */
	public Timestamp getCycleA2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CycleA2);
	}

	/** Set CycleB1.
		@param CycleB1 CycleB1	  */
	public void setCycleB1 (Timestamp CycleB1)
	{
		set_Value (COLUMNNAME_CycleB1, CycleB1);
	}

	/** Get CycleB1.
		@return CycleB1	  */
	public Timestamp getCycleB1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CycleB1);
	}

	/** Set CycleB2.
		@param CycleB2 CycleB2	  */
	public void setCycleB2 (Timestamp CycleB2)
	{
		set_Value (COLUMNNAME_CycleB2, CycleB2);
	}

	/** Get CycleB2.
		@return CycleB2	  */
	public Timestamp getCycleB2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CycleB2);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set Day.
		@param Day Day	  */
	public void setDay (String Day)
	{
		set_Value (COLUMNNAME_Day, Day);
	}

	/** Get Day.
		@return Day	  */
	public String getDay () 
	{
		return (String)get_Value(COLUMNNAME_Day);
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

	/** Set GenerateHours.
		@param GenerateHours GenerateHours	  */
	public void setGenerateHours (String GenerateHours)
	{
		set_Value (COLUMNNAME_GenerateHours, GenerateHours);
	}

	/** Get GenerateHours.
		@return GenerateHours	  */
	public String getGenerateHours () 
	{
		return (String)get_Value(COLUMNNAME_GenerateHours);
	}

	public I_MED_Schedule getMED_Schedule() throws RuntimeException
    {
		return (I_MED_Schedule)MTable.get(getCtx(), I_MED_Schedule.Table_Name)
			.getPO(getMED_Schedule_ID(), get_TrxName());	}

	/** Set MED_Schedule ID.
		@param MED_Schedule_ID MED_Schedule ID	  */
	public void setMED_Schedule_ID (int MED_Schedule_ID)
	{
		if (MED_Schedule_ID < 1) 
			set_Value (COLUMNNAME_MED_Schedule_ID, null);
		else 
			set_Value (COLUMNNAME_MED_Schedule_ID, Integer.valueOf(MED_Schedule_ID));
	}

	/** Get MED_Schedule ID.
		@return MED_Schedule ID	  */
	public int getMED_Schedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MED_Schedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MED_ScheduleDay ID.
		@param MED_ScheduleDay_ID MED_ScheduleDay ID	  */
	public void setMED_ScheduleDay_ID (int MED_ScheduleDay_ID)
	{
		if (MED_ScheduleDay_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MED_ScheduleDay_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MED_ScheduleDay_ID, Integer.valueOf(MED_ScheduleDay_ID));
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
}