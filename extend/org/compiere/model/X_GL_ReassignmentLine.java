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

/** Generated Model for GL_ReassignmentLine
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_GL_ReassignmentLine extends PO implements I_GL_ReassignmentLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180323L;

    /** Standard Constructor */
    public X_GL_ReassignmentLine (Properties ctx, int GL_ReassignmentLine_ID, String trxName)
    {
      super (ctx, GL_ReassignmentLine_ID, trxName);
      /** if (GL_ReassignmentLine_ID == 0)
        {
			setGL_Reassignment_ID (0);
			setGL_ReassignmentLine_ID (0);
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_GL_ReassignmentLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_GL_ReassignmentLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public I_GL_BudgetControlHeader getGL_BudgetControlHeaderRef() throws RuntimeException
    {
		return (I_GL_BudgetControlHeader)MTable.get(getCtx(), I_GL_BudgetControlHeader.Table_Name)
			.getPO(getGL_BudgetControlHeaderRef_ID(), get_TrxName());	}

	/** Set GL_BudgetControlHeaderRef_ID.
		@param GL_BudgetControlHeaderRef_ID GL_BudgetControlHeaderRef_ID	  */
	public void setGL_BudgetControlHeaderRef_ID (int GL_BudgetControlHeaderRef_ID)
	{
		if (GL_BudgetControlHeaderRef_ID < 1) 
			set_Value (COLUMNNAME_GL_BudgetControlHeaderRef_ID, null);
		else 
			set_Value (COLUMNNAME_GL_BudgetControlHeaderRef_ID, Integer.valueOf(GL_BudgetControlHeaderRef_ID));
	}

	/** Get GL_BudgetControlHeaderRef_ID.
		@return GL_BudgetControlHeaderRef_ID	  */
	public int getGL_BudgetControlHeaderRef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_BudgetControlHeaderRef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_GL_BudgetControlLine getGL_BudgetControlLineRef() throws RuntimeException
    {
		return (I_GL_BudgetControlLine)MTable.get(getCtx(), I_GL_BudgetControlLine.Table_Name)
			.getPO(getGL_BudgetControlLineRef_ID(), get_TrxName());	}

	/** Set GL_BudgetControlLineRef_ID.
		@param GL_BudgetControlLineRef_ID GL_BudgetControlLineRef_ID	  */
	public void setGL_BudgetControlLineRef_ID (int GL_BudgetControlLineRef_ID)
	{
		if (GL_BudgetControlLineRef_ID < 1) 
			set_Value (COLUMNNAME_GL_BudgetControlLineRef_ID, null);
		else 
			set_Value (COLUMNNAME_GL_BudgetControlLineRef_ID, Integer.valueOf(GL_BudgetControlLineRef_ID));
	}

	/** Get GL_BudgetControlLineRef_ID.
		@return GL_BudgetControlLineRef_ID	  */
	public int getGL_BudgetControlLineRef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_BudgetControlLineRef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_GL_BudgetControl getGL_BudgetControlRef() throws RuntimeException
    {
		return (org.compiere.model.I_GL_BudgetControl)MTable.get(getCtx(), org.compiere.model.I_GL_BudgetControl.Table_Name)
			.getPO(getGL_BudgetControlRef_ID(), get_TrxName());	}

	/** Set GL_BudgetControlRef_ID.
		@param GL_BudgetControlRef_ID GL_BudgetControlRef_ID	  */
	public void setGL_BudgetControlRef_ID (int GL_BudgetControlRef_ID)
	{
		if (GL_BudgetControlRef_ID < 1) 
			set_Value (COLUMNNAME_GL_BudgetControlRef_ID, null);
		else 
			set_Value (COLUMNNAME_GL_BudgetControlRef_ID, Integer.valueOf(GL_BudgetControlRef_ID));
	}

	/** Get GL_BudgetControlRef_ID.
		@return GL_BudgetControlRef_ID	  */
	public int getGL_BudgetControlRef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_BudgetControlRef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_GL_BudgetDetail getGL_BudgetDetailRef() throws RuntimeException
    {
		return (I_GL_BudgetDetail)MTable.get(getCtx(), I_GL_BudgetDetail.Table_Name)
			.getPO(getGL_BudgetDetailRef_ID(), get_TrxName());	}

	/** Set GL_BudgetDetailRef_ID.
		@param GL_BudgetDetailRef_ID GL_BudgetDetailRef_ID	  */
	public void setGL_BudgetDetailRef_ID (int GL_BudgetDetailRef_ID)
	{
		if (GL_BudgetDetailRef_ID < 1) 
			set_Value (COLUMNNAME_GL_BudgetDetailRef_ID, null);
		else 
			set_Value (COLUMNNAME_GL_BudgetDetailRef_ID, Integer.valueOf(GL_BudgetDetailRef_ID));
	}

	/** Get GL_BudgetDetailRef_ID.
		@return GL_BudgetDetailRef_ID	  */
	public int getGL_BudgetDetailRef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_BudgetDetailRef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_GL_Reassignment getGL_Reassignment() throws RuntimeException
    {
		return (I_GL_Reassignment)MTable.get(getCtx(), I_GL_Reassignment.Table_Name)
			.getPO(getGL_Reassignment_ID(), get_TrxName());	}

	/** Set GL_Reassignment ID.
		@param GL_Reassignment_ID GL_Reassignment ID	  */
	public void setGL_Reassignment_ID (int GL_Reassignment_ID)
	{
		if (GL_Reassignment_ID < 1) 
			set_Value (COLUMNNAME_GL_Reassignment_ID, null);
		else 
			set_Value (COLUMNNAME_GL_Reassignment_ID, Integer.valueOf(GL_Reassignment_ID));
	}

	/** Get GL_Reassignment ID.
		@return GL_Reassignment ID	  */
	public int getGL_Reassignment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Reassignment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GL_ReassignmentLine ID.
		@param GL_ReassignmentLine_ID GL_ReassignmentLine ID	  */
	public void setGL_ReassignmentLine_ID (int GL_ReassignmentLine_ID)
	{
		if (GL_ReassignmentLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_ReassignmentLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_ReassignmentLine_ID, Integer.valueOf(GL_ReassignmentLine_ID));
	}

	/** Get GL_ReassignmentLine ID.
		@return GL_ReassignmentLine ID	  */
	public int getGL_ReassignmentLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_ReassignmentLine_ID);
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
}