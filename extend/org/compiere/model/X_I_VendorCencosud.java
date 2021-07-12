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

/** Generated Model for I_VendorCencosud
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_I_VendorCencosud extends PO implements I_I_VendorCencosud, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190325L;

    /** Standard Constructor */
    public X_I_VendorCencosud (Properties ctx, int I_VendorCencosud_ID, String trxName)
    {
      super (ctx, I_VendorCencosud_ID, trxName);
      /** if (I_VendorCencosud_ID == 0)
        {
			setI_IsImported (false);
			setI_VendorCencosud_ID (0);
        } */
    }

    /** Load Constructor */
    public X_I_VendorCencosud (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_I_VendorCencosud[")
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

	/** Set Import Error Message.
		@param I_ErrorMsg 
		Messages generated from import process
	  */
	public void setI_ErrorMsg (String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import Error Message.
		@return Messages generated from import process
	  */
	public String getI_ErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Imported.
		@param I_IsImported 
		Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Imported.
		@return Has this import been processed
	  */
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set I_VendorCencosud ID.
		@param I_VendorCencosud_ID I_VendorCencosud ID	  */
	public void setI_VendorCencosud_ID (int I_VendorCencosud_ID)
	{
		if (I_VendorCencosud_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_VendorCencosud_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_VendorCencosud_ID, Integer.valueOf(I_VendorCencosud_ID));
	}

	/** Get I_VendorCencosud ID.
		@return I_VendorCencosud ID	  */
	public int getI_VendorCencosud_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_VendorCencosud_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Locator Key.
		@param LocatorValue 
		Key of the Warehouse Locator
	  */
	public void setLocatorValue (String LocatorValue)
	{
		set_Value (COLUMNNAME_LocatorValue, LocatorValue);
	}

	/** Get Locator Key.
		@return Key of the Warehouse Locator
	  */
	public String getLocatorValue () 
	{
		return (String)get_Value(COLUMNNAME_LocatorValue);
	}

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
    {
		return (org.compiere.model.I_M_Locator)MTable.get(getCtx(), org.compiere.model.I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product_Group getM_Product_Group() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product_Group)MTable.get(getCtx(), org.compiere.model.I_M_Product_Group.Table_Name)
			.getPO(getM_Product_Group_ID(), get_TrxName());	}

	/** Set Product Group.
		@param M_Product_Group_ID 
		Group of a Product
	  */
	public void setM_Product_Group_ID (int M_Product_Group_ID)
	{
		if (M_Product_Group_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Group_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Group_ID, Integer.valueOf(M_Product_Group_ID));
	}

	/** Get Product Group.
		@return Group of a Product
	  */
	public int getM_Product_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set productgroupvalue.
		@param productgroupvalue productgroupvalue	  */
	public void setproductgroupvalue (String productgroupvalue)
	{
		set_Value (COLUMNNAME_productgroupvalue, productgroupvalue);
	}

	/** Get productgroupvalue.
		@return productgroupvalue	  */
	public String getproductgroupvalue () 
	{
		return (String)get_Value(COLUMNNAME_productgroupvalue);
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