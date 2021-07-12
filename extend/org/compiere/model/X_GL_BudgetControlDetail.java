/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) any later version.										*
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net or http://www.adempiere.net/license.html         *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for GL_BudgetControlDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.9.2 - $Id$ */
public class X_GL_BudgetControlDetail extends PO implements I_GL_BudgetControlDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200428L;

    /** Standard Constructor */
    public X_GL_BudgetControlDetail (Properties ctx, int GL_BudgetControlDetail_ID, String trxName)
    {
      super (ctx, GL_BudgetControlDetail_ID, trxName);
      /** if (GL_BudgetControlDetail_ID == 0)
        {
			setGL_BudgetControlDetail_ID (0);
			setGL_BudgetControlLine_ID (0);
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_GL_BudgetControlDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_GL_BudgetControlDetail[")
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

	/** Set Comments.
		@param Comments 
		Comments or additional information
	  */
	public void setComments (String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Comments.
		@return Comments or additional information
	  */
	public String getComments () 
	{
		return (String)get_Value(COLUMNNAME_Comments);
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

	/** Set GL_BudgetControlDetail ID.
		@param GL_BudgetControlDetail_ID GL_BudgetControlDetail ID	  */
	public void setGL_BudgetControlDetail_ID (int GL_BudgetControlDetail_ID)
	{
		if (GL_BudgetControlDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_BudgetControlDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_BudgetControlDetail_ID, Integer.valueOf(GL_BudgetControlDetail_ID));
	}

	/** Get GL_BudgetControlDetail ID.
		@return GL_BudgetControlDetail ID	  */
	public int getGL_BudgetControlDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_BudgetControlDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_GL_BudgetControlLine getGL_BudgetControlLine() throws RuntimeException
    {
		return (I_GL_BudgetControlLine)MTable.get(getCtx(), I_GL_BudgetControlLine.Table_Name)
			.getPO(getGL_BudgetControlLine_ID(), get_TrxName());	}

	/** Set Budget Control Line ID.
		@param GL_BudgetControlLine_ID Budget Control Line ID	  */
	public void setGL_BudgetControlLine_ID (int GL_BudgetControlLine_ID)
	{
		if (GL_BudgetControlLine_ID < 1) 
			set_Value (COLUMNNAME_GL_BudgetControlLine_ID, null);
		else 
			set_Value (COLUMNNAME_GL_BudgetControlLine_ID, Integer.valueOf(GL_BudgetControlLine_ID));
	}

	/** Get Budget Control Line ID.
		@return Budget Control Line ID	  */
	public int getGL_BudgetControlLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_BudgetControlLine_ID);
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

	/** Set Quantity.
		@param QtyEntered 
		The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered (BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Quantity.
		@return The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SQLAmount2.
		@param SQLAmount2 SQLAmount2	  */
	public void setSQLAmount2 (BigDecimal SQLAmount2)
	{
		throw new IllegalArgumentException ("SQLAmount2 is virtual column");	}

	/** Get SQLAmount2.
		@return SQLAmount2	  */
	public BigDecimal getSQLAmount2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SQLAmount2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SQLAmount3.
		@param SQLAmount3 SQLAmount3	  */
	public void setSQLAmount3 (BigDecimal SQLAmount3)
	{
		throw new IllegalArgumentException ("SQLAmount3 is virtual column");	}

	/** Get SQLAmount3.
		@return SQLAmount3	  */
	public BigDecimal getSQLAmount3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SQLAmount3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SQLDate1.
		@param SQLDate1 SQLDate1	  */
	public void setSQLDate1 (Timestamp SQLDate1)
	{
		throw new IllegalArgumentException ("SQLDate1 is virtual column");	}

	/** Get SQLDate1.
		@return SQLDate1	  */
	public Timestamp getSQLDate1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_SQLDate1);
	}

	/** Set SQLDate2.
		@param SQLDate2 SQLDate2	  */
	public void setSQLDate2 (Timestamp SQLDate2)
	{
		throw new IllegalArgumentException ("SQLDate2 is virtual column");	}

	/** Get SQLDate2.
		@return SQLDate2	  */
	public Timestamp getSQLDate2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_SQLDate2);
	}

	/** Set Immutable Universally Unique Identifier.
		@param UUID 
		Immutable Universally Unique Identifier
	  */
	public void setUUID (String UUID)
	{
		set_Value (COLUMNNAME_UUID, UUID);
	}

	/** Get Immutable Universally Unique Identifier.
		@return Immutable Universally Unique Identifier
	  */
	public String getUUID () 
	{
		return (String)get_Value(COLUMNNAME_UUID);
	}
}