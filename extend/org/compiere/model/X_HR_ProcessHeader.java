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

/** Generated Model for HR_ProcessHeader
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_HR_ProcessHeader extends PO implements I_HR_ProcessHeader, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190606L;

    /** Standard Constructor */
    public X_HR_ProcessHeader (Properties ctx, int HR_ProcessHeader_ID, String trxName)
    {
      super (ctx, HR_ProcessHeader_ID, trxName);
      /** if (HR_ProcessHeader_ID == 0)
        {
			setHR_ProcessHeader_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HR_ProcessHeader (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_ProcessHeader[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getC_PeriodTo() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_PeriodTo_ID(), get_TrxName());	}

	/** Set C_PeriodTo_ID.
		@param C_PeriodTo_ID C_PeriodTo_ID	  */
	public void setC_PeriodTo_ID (int C_PeriodTo_ID)
	{
		if (C_PeriodTo_ID < 1) 
			set_Value (COLUMNNAME_C_PeriodTo_ID, null);
		else 
			set_Value (COLUMNNAME_C_PeriodTo_ID, Integer.valueOf(C_PeriodTo_ID));
	}

	/** Get C_PeriodTo_ID.
		@return C_PeriodTo_ID	  */
	public int getC_PeriodTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PeriodTo_ID);
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

	/** Set HR_ProcessHeader ID.
		@param HR_ProcessHeader_ID HR_ProcessHeader ID	  */
	public void setHR_ProcessHeader_ID (int HR_ProcessHeader_ID)
	{
		if (HR_ProcessHeader_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_ProcessHeader_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_ProcessHeader_ID, Integer.valueOf(HR_ProcessHeader_ID));
	}

	/** Get HR_ProcessHeader ID.
		@return HR_ProcessHeader ID	  */
	public int getHR_ProcessHeader_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ProcessHeader_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}