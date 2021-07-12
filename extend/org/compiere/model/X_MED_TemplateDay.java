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

/** Generated Model for MED_TemplateDay
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_MED_TemplateDay extends PO implements I_MED_TemplateDay, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190826L;

    /** Standard Constructor */
    public X_MED_TemplateDay (Properties ctx, int MED_TemplateDay_ID, String trxName)
    {
      super (ctx, MED_TemplateDay_ID, trxName);
      /** if (MED_TemplateDay_ID == 0)
        {
			setMED_TemplateDay_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MED_TemplateDay (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MED_TemplateDay[")
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

	/** Set Days.
		@param Days Days	  */
	public void setDays (String Days)
	{
		set_Value (COLUMNNAME_Days, Days);
	}

	/** Get Days.
		@return Days	  */
	public String getDays () 
	{
		return (String)get_Value(COLUMNNAME_Days);
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

	/** Set MED_TemplateDay.
		@param MED_TemplateDay_ID MED_TemplateDay	  */
	public void setMED_TemplateDay_ID (int MED_TemplateDay_ID)
	{
		if (MED_TemplateDay_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MED_TemplateDay_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MED_TemplateDay_ID, Integer.valueOf(MED_TemplateDay_ID));
	}

	/** Get MED_TemplateDay.
		@return MED_TemplateDay	  */
	public int getMED_TemplateDay_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MED_TemplateDay_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}