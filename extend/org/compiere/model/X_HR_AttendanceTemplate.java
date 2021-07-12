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

/** Generated Model for HR_AttendanceTemplate
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_HR_AttendanceTemplate extends PO implements I_HR_AttendanceTemplate, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20181123L;

    /** Standard Constructor */
    public X_HR_AttendanceTemplate (Properties ctx, int HR_AttendanceTemplate_ID, String trxName)
    {
      super (ctx, HR_AttendanceTemplate_ID, trxName);
      /** if (HR_AttendanceTemplate_ID == 0)
        {
			setHR_AttendanceTemplate_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HR_AttendanceTemplate (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_AttendanceTemplate[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set DelayBase.
		@param DelayBase DelayBase	  */
	public void setDelayBase (Timestamp DelayBase)
	{
		set_Value (COLUMNNAME_DelayBase, DelayBase);
	}

	/** Get DelayBase.
		@return DelayBase	  */
	public Timestamp getDelayBase () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DelayBase);
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

	/** Set HalfMorningEntryFriday.
		@param HalfMorningEntryFriday HalfMorningEntryFriday	  */
	public void setHalfMorningEntryFriday (Timestamp HalfMorningEntryFriday)
	{
		set_Value (COLUMNNAME_HalfMorningEntryFriday, HalfMorningEntryFriday);
	}

	/** Get HalfMorningEntryFriday.
		@return HalfMorningEntryFriday	  */
	public Timestamp getHalfMorningEntryFriday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HalfMorningEntryFriday);
	}

	/** Set HalfMorningEntryNotFriday.
		@param HalfMorningEntryNotFriday HalfMorningEntryNotFriday	  */
	public void setHalfMorningEntryNotFriday (Timestamp HalfMorningEntryNotFriday)
	{
		set_Value (COLUMNNAME_HalfMorningEntryNotFriday, HalfMorningEntryNotFriday);
	}

	/** Get HalfMorningEntryNotFriday.
		@return HalfMorningEntryNotFriday	  */
	public Timestamp getHalfMorningEntryNotFriday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HalfMorningEntryNotFriday);
	}

	/** Set HR_AttendanceTemplate ID.
		@param HR_AttendanceTemplate_ID HR_AttendanceTemplate ID	  */
	public void setHR_AttendanceTemplate_ID (int HR_AttendanceTemplate_ID)
	{
		if (HR_AttendanceTemplate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_AttendanceTemplate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_AttendanceTemplate_ID, Integer.valueOf(HR_AttendanceTemplate_ID));
	}

	/** Get HR_AttendanceTemplate ID.
		@return HR_AttendanceTemplate ID	  */
	public int getHR_AttendanceTemplate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_AttendanceTemplate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MinEntryHour.
		@param MinEntryHour MinEntryHour	  */
	public void setMinEntryHour (Timestamp MinEntryHour)
	{
		set_Value (COLUMNNAME_MinEntryHour, MinEntryHour);
	}

	/** Get MinEntryHour.
		@return MinEntryHour	  */
	public Timestamp getMinEntryHour () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MinEntryHour);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}
}