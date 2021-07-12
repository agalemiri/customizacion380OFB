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

/** Generated Model for DM_DocumentLinePM
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_DM_DocumentLinePM extends PO implements I_DM_DocumentLinePM, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180516L;

    /** Standard Constructor */
    public X_DM_DocumentLinePM (Properties ctx, int DM_DocumentLinePM_ID, String trxName)
    {
      super (ctx, DM_DocumentLinePM_ID, trxName);
      /** if (DM_DocumentLinePM_ID == 0)
        {
			setDM_Document_ID (0);
			setDM_DocumentLinePM_ID (0);
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_DM_DocumentLinePM (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_DM_DocumentLinePM[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount2.
		@param Amount2 Amount2	  */
	public void setAmount2 (BigDecimal Amount2)
	{
		set_Value (COLUMNNAME_Amount2, Amount2);
	}

	/** Get Amount2.
		@return Amount2	  */
	public BigDecimal getAmount2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount3.
		@param Amount3 Amount3	  */
	public void setAmount3 (BigDecimal Amount3)
	{
		set_Value (COLUMNNAME_Amount3, Amount3);
	}

	/** Get Amount3.
		@return Amount3	  */
	public BigDecimal getAmount3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount4.
		@param Amount4 Amount4	  */
	public void setAmount4 (BigDecimal Amount4)
	{
		set_Value (COLUMNNAME_Amount4, Amount4);
	}

	/** Get Amount4.
		@return Amount4	  */
	public BigDecimal getAmount4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount5.
		@param Amount5 Amount5	  */
	public void setAmount5 (BigDecimal Amount5)
	{
		set_Value (COLUMNNAME_Amount5, Amount5);
	}

	/** Get Amount5.
		@return Amount5	  */
	public BigDecimal getAmount5 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount5);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount6.
		@param Amount6 Amount6	  */
	public void setAmount6 (BigDecimal Amount6)
	{
		set_Value (COLUMNNAME_Amount6, Amount6);
	}

	/** Get Amount6.
		@return Amount6	  */
	public BigDecimal getAmount6 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount6);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount7.
		@param Amount7 Amount7	  */
	public void setAmount7 (BigDecimal Amount7)
	{
		set_Value (COLUMNNAME_Amount7, Amount7);
	}

	/** Get Amount7.
		@return Amount7	  */
	public BigDecimal getAmount7 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount7);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount8.
		@param Amount8 Amount8	  */
	public void setAmount8 (BigDecimal Amount8)
	{
		set_Value (COLUMNNAME_Amount8, Amount8);
	}

	/** Get Amount8.
		@return Amount8	  */
	public BigDecimal getAmount8 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount8);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount9.
		@param Amount9 Amount9	  */
	public void setAmount9 (BigDecimal Amount9)
	{
		set_Value (COLUMNNAME_Amount9, Amount9);
	}

	/** Get Amount9.
		@return Amount9	  */
	public BigDecimal getAmount9 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount9);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException
    {
		return (org.compiere.model.I_C_Project)MTable.get(getCtx(), org.compiere.model.I_C_Project.Table_Name)
			.getPO(getC_Project_ID(), get_TrxName());	}

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_ValidCombination getC_ValidCombination() throws RuntimeException
    {
		return (org.compiere.model.I_C_ValidCombination)MTable.get(getCtx(), org.compiere.model.I_C_ValidCombination.Table_Name)
			.getPO(getC_ValidCombination_ID(), get_TrxName());	}

	/** Set Combination.
		@param C_ValidCombination_ID 
		Valid Account Combination
	  */
	public void setC_ValidCombination_ID (int C_ValidCombination_ID)
	{
		if (C_ValidCombination_ID < 1) 
			set_Value (COLUMNNAME_C_ValidCombination_ID, null);
		else 
			set_Value (COLUMNNAME_C_ValidCombination_ID, Integer.valueOf(C_ValidCombination_ID));
	}

	/** Get Combination.
		@return Valid Account Combination
	  */
	public int getC_ValidCombination_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ValidCombination_ID);
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

	public I_DM_Document getDM_Document() throws RuntimeException
    {
		return (I_DM_Document)MTable.get(getCtx(), I_DM_Document.Table_Name)
			.getPO(getDM_Document_ID(), get_TrxName());	}

	/** Set DM_Document.
		@param DM_Document_ID DM_Document	  */
	public void setDM_Document_ID (int DM_Document_ID)
	{
		if (DM_Document_ID < 1) 
			set_Value (COLUMNNAME_DM_Document_ID, null);
		else 
			set_Value (COLUMNNAME_DM_Document_ID, Integer.valueOf(DM_Document_ID));
	}

	/** Get DM_Document.
		@return DM_Document	  */
	public int getDM_Document_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DM_Document_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DM_DocumentLinePM ID.
		@param DM_DocumentLinePM_ID DM_DocumentLinePM ID	  */
	public void setDM_DocumentLinePM_ID (int DM_DocumentLinePM_ID)
	{
		if (DM_DocumentLinePM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DM_DocumentLinePM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DM_DocumentLinePM_ID, Integer.valueOf(DM_DocumentLinePM_ID));
	}

	/** Get DM_DocumentLinePM ID.
		@return DM_DocumentLinePM ID	  */
	public int getDM_DocumentLinePM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DM_DocumentLinePM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}