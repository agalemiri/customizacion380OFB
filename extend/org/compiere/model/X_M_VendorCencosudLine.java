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

/** Generated Model for M_VendorCencosudLine
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_M_VendorCencosudLine extends PO implements I_M_VendorCencosudLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190325L;

    /** Standard Constructor */
    public X_M_VendorCencosudLine (Properties ctx, int M_VendorCencosudLine_ID, String trxName)
    {
      super (ctx, M_VendorCencosudLine_ID, trxName);
      /** if (M_VendorCencosudLine_ID == 0)
        {
			setM_VendorCencosudLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_VendorCencosudLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_VendorCencosudLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set DateEnd.
		@param DateEnd DateEnd	  */
	public void setDateEnd (Timestamp DateEnd)
	{
		set_Value (COLUMNNAME_DateEnd, DateEnd);
	}

	/** Get DateEnd.
		@return DateEnd	  */
	public Timestamp getDateEnd () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateEnd);
	}

	/** Set Date Start.
		@param DateStart 
		Date Start for this Order
	  */
	public void setDateStart (Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	/** Get Date Start.
		@return Date Start for this Order
	  */
	public Timestamp getDateStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStart);
	}

	public I_M_VendorCencosud getM_VendorCencosud() throws RuntimeException
    {
		return (I_M_VendorCencosud)MTable.get(getCtx(), I_M_VendorCencosud.Table_Name)
			.getPO(getM_VendorCencosud_ID(), get_TrxName());	}

	/** Set M_VendorCencosud ID.
		@param M_VendorCencosud_ID M_VendorCencosud ID	  */
	public void setM_VendorCencosud_ID (int M_VendorCencosud_ID)
	{
		if (M_VendorCencosud_ID < 1) 
			set_Value (COLUMNNAME_M_VendorCencosud_ID, null);
		else 
			set_Value (COLUMNNAME_M_VendorCencosud_ID, Integer.valueOf(M_VendorCencosud_ID));
	}

	/** Get M_VendorCencosud ID.
		@return M_VendorCencosud ID	  */
	public int getM_VendorCencosud_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_VendorCencosud_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_VendorCencosudLine ID.
		@param M_VendorCencosudLine_ID M_VendorCencosudLine ID	  */
	public void setM_VendorCencosudLine_ID (int M_VendorCencosudLine_ID)
	{
		if (M_VendorCencosudLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_VendorCencosudLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_VendorCencosudLine_ID, Integer.valueOf(M_VendorCencosudLine_ID));
	}

	/** Get M_VendorCencosudLine ID.
		@return M_VendorCencosudLine ID	  */
	public int getM_VendorCencosudLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_VendorCencosudLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Percentage.
		@param Percentage 
		Percent of the entire amount
	  */
	public void setPercentage (BigDecimal Percentage)
	{
		set_Value (COLUMNNAME_Percentage, Percentage);
	}

	/** Get Percentage.
		@return Percent of the entire amount
	  */
	public BigDecimal getPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Percentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set RutOC.
		@param RutOC RutOC	  */
	public void setRutOC (String RutOC)
	{
		set_Value (COLUMNNAME_RutOC, RutOC);
	}

	/** Get RutOC.
		@return RutOC	  */
	public String getRutOC () 
	{
		return (String)get_Value(COLUMNNAME_RutOC);
	}

	/** Set RutOT.
		@param RutOT RutOT	  */
	public void setRutOT (String RutOT)
	{
		set_Value (COLUMNNAME_RutOT, RutOT);
	}

	/** Get RutOT.
		@return RutOT	  */
	public String getRutOT () 
	{
		return (String)get_Value(COLUMNNAME_RutOT);
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weighted Amount.
		@param WeightedAmt 
		The amount adjusted by the probability.
	  */
	public void setWeightedAmt (BigDecimal WeightedAmt)
	{
		set_Value (COLUMNNAME_WeightedAmt, WeightedAmt);
	}

	/** Get Weighted Amount.
		@return The amount adjusted by the probability.
	  */
	public BigDecimal getWeightedAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WeightedAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}