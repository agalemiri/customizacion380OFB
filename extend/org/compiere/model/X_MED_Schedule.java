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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for MED_Schedule
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_MED_Schedule extends PO implements I_MED_Schedule, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190711L;

    /** Standard Constructor */
    public X_MED_Schedule (Properties ctx, int MED_Schedule_ID, String trxName)
    {
      super (ctx, MED_Schedule_ID, trxName);
      /** if (MED_Schedule_ID == 0)
        {
			setMED_Schedule_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MED_Schedule (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MED_Schedule[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartnerMed() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartnerMed_ID(), get_TrxName());	}

	/** Set C_BPartnerMed_ID.
		@param C_BPartnerMed_ID C_BPartnerMed_ID	  */
	public void setC_BPartnerMed_ID (int C_BPartnerMed_ID)
	{
		if (C_BPartnerMed_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerMed_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerMed_ID, Integer.valueOf(C_BPartnerMed_ID));
	}

	/** Get C_BPartnerMed_ID.
		@return C_BPartnerMed_ID	  */
	public int getC_BPartnerMed_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerMed_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
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

	/** Set GenerateDays.
		@param GenerateDays GenerateDays	  */
	public void setGenerateDays (String GenerateDays)
	{
		set_Value (COLUMNNAME_GenerateDays, GenerateDays);
	}

	/** Get GenerateDays.
		@return GenerateDays	  */
	public String getGenerateDays () 
	{
		return (String)get_Value(COLUMNNAME_GenerateDays);
	}

	/** Set MED_Schedule ID.
		@param MED_Schedule_ID MED_Schedule ID	  */
	public void setMED_Schedule_ID (int MED_Schedule_ID)
	{
		if (MED_Schedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MED_Schedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MED_Schedule_ID, Integer.valueOf(MED_Schedule_ID));
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

	public I_MED_Template getMED_Template() throws RuntimeException
    {
		return (I_MED_Template)MTable.get(getCtx(), I_MED_Template.Table_Name)
			.getPO(getMED_Template_ID(), get_TrxName());	}

	/** Set MED_Template ID.
		@param MED_Template_ID MED_Template ID	  */
	public void setMED_Template_ID (int MED_Template_ID)
	{
		if (MED_Template_ID < 1) 
			set_Value (COLUMNNAME_MED_Template_ID, null);
		else 
			set_Value (COLUMNNAME_MED_Template_ID, Integer.valueOf(MED_Template_ID));
	}

	/** Get MED_Template ID.
		@return MED_Template ID	  */
	public int getMED_Template_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MED_Template_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set minutes.
		@param minutes minutes	  */
	public void setminutes (BigDecimal minutes)
	{
		set_Value (COLUMNNAME_minutes, minutes);
	}

	/** Get minutes.
		@return minutes	  */
	public BigDecimal getminutes () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_minutes);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Type AD_Reference_ID=101 */
	public static final int TYPE_AD_Reference_ID=101;
	/** SQL = S */
	public static final String TYPE_SQL = "S";
	/** Java Language = J */
	public static final String TYPE_JavaLanguage = "J";
	/** Java Script = E */
	public static final String TYPE_JavaScript = "E";
	/** Set Type.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type)
	{

		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Type.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType () 
	{
		return (String)get_Value(COLUMNNAME_Type);
	}
}