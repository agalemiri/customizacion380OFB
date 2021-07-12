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
import org.compiere.util.KeyNamePair;

/** Generated Model for MP_Planification
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_MP_Planification extends PO implements I_MP_Planification, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170809L;

    /** Standard Constructor */
    public X_MP_Planification (Properties ctx, int MP_Planification_ID, String trxName)
    {
      super (ctx, MP_Planification_ID, trxName);
      /** if (MP_Planification_ID == 0)
        {
			setMP_Indicator_ID (0);
			setMP_Planification_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_MP_Planification (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MP_Planification[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Expected Result.
		@param ExpectedResult Expected Result	  */
	public void setExpectedResult (BigDecimal ExpectedResult)
	{
		set_Value (COLUMNNAME_ExpectedResult, ExpectedResult);
	}

	/** Get Expected Result.
		@return Expected Result	  */
	public BigDecimal getExpectedResult () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ExpectedResult);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_MP_Indicator getMP_Indicator() throws RuntimeException
    {
		return (I_MP_Indicator)MTable.get(getCtx(), I_MP_Indicator.Table_Name)
			.getPO(getMP_Indicator_ID(), get_TrxName());	}

	/** Set MP_Indicator ID.
		@param MP_Indicator_ID MP_Indicator ID	  */
	public void setMP_Indicator_ID (int MP_Indicator_ID)
	{
		if (MP_Indicator_ID < 1) 
			set_Value (COLUMNNAME_MP_Indicator_ID, null);
		else 
			set_Value (COLUMNNAME_MP_Indicator_ID, Integer.valueOf(MP_Indicator_ID));
	}

	/** Get MP_Indicator ID.
		@return MP_Indicator ID	  */
	public int getMP_Indicator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MP_Indicator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MP_Planification ID.
		@param MP_Planification_ID MP_Planification ID	  */
	public void setMP_Planification_ID (int MP_Planification_ID)
	{
		if (MP_Planification_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MP_Planification_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MP_Planification_ID, Integer.valueOf(MP_Planification_ID));
	}

	/** Get MP_Planification ID.
		@return MP_Planification ID	  */
	public int getMP_Planification_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MP_Planification_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }
}