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

/** Generated Model for HR_ProcessBPDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_HR_ProcessBPDetail extends PO implements I_HR_ProcessBPDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171214L;

    /** Standard Constructor */
    public X_HR_ProcessBPDetail (Properties ctx, int HR_ProcessBPDetail_ID, String trxName)
    {
      super (ctx, HR_ProcessBPDetail_ID, trxName);
      /** if (HR_ProcessBPDetail_ID == 0)
        {
			setHR_ProcessBPDetail_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HR_ProcessBPDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_ProcessBPDetail[")
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

	public org.eevolution.model.I_HR_Concept getHR_Concept() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Concept)MTable.get(getCtx(), org.eevolution.model.I_HR_Concept.Table_Name)
			.getPO(getHR_Concept_ID(), get_TrxName());	}

	/** Set AD Payroll Concept.
		@param HR_Concept_ID 
		The Payroll Concept allows to define all the perception and deductions elements needed to define a payroll.
	  */
	public void setHR_Concept_ID (int HR_Concept_ID)
	{
		if (HR_Concept_ID < 1) 
			set_Value (COLUMNNAME_HR_Concept_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Concept_ID, Integer.valueOf(HR_Concept_ID));
	}

	/** Get AD Payroll Concept.
		@return The Payroll Concept allows to define all the perception and deductions elements needed to define a payroll.
	  */
	public int getHR_Concept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Concept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HR_ProcessBP getHR_ProcessBP() throws RuntimeException
    {
		return (I_HR_ProcessBP)MTable.get(getCtx(), I_HR_ProcessBP.Table_Name)
			.getPO(getHR_ProcessBP_ID(), get_TrxName());	}

	/** Set HR_ProcessBP ID.
		@param HR_ProcessBP_ID HR_ProcessBP ID	  */
	public void setHR_ProcessBP_ID (int HR_ProcessBP_ID)
	{
		if (HR_ProcessBP_ID < 1) 
			set_Value (COLUMNNAME_HR_ProcessBP_ID, null);
		else 
			set_Value (COLUMNNAME_HR_ProcessBP_ID, Integer.valueOf(HR_ProcessBP_ID));
	}

	/** Get HR_ProcessBP ID.
		@return HR_ProcessBP ID	  */
	public int getHR_ProcessBP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ProcessBP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HR_ProcessBPDetail ID.
		@param HR_ProcessBPDetail_ID HR_ProcessBPDetail ID	  */
	public void setHR_ProcessBPDetail_ID (int HR_ProcessBPDetail_ID)
	{
		if (HR_ProcessBPDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_ProcessBPDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_ProcessBPDetail_ID, Integer.valueOf(HR_ProcessBPDetail_ID));
	}

	/** Get HR_ProcessBPDetail ID.
		@return HR_ProcessBPDetail ID	  */
	public int getHR_ProcessBPDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ProcessBPDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}