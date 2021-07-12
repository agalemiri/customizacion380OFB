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

/** Generated Model for PP_ForecastLineCENCO
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_PP_ForecastLineCENCO extends PO implements I_PP_ForecastLineCENCO, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20181008L;

    /** Standard Constructor */
    public X_PP_ForecastLineCENCO (Properties ctx, int PP_ForecastLineCENCO_ID, String trxName)
    {
      super (ctx, PP_ForecastLineCENCO_ID, trxName);
      /** if (PP_ForecastLineCENCO_ID == 0)
        {
			setPP_ForecastLineCENCO_ID (0);
        } */
    }

    /** Load Constructor */
    public X_PP_ForecastLineCENCO (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_PP_ForecastLineCENCO[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date.
		@param Date1 
		Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1)
	{
		set_Value (COLUMNNAME_Date1, Date1);
	}

	/** Get Date.
		@return Date when business is not conducted
	  */
	public Timestamp getDate1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Date1);
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

	/** Set PP_ForecastLineCENCO ID.
		@param PP_ForecastLineCENCO_ID PP_ForecastLineCENCO ID	  */
	public void setPP_ForecastLineCENCO_ID (int PP_ForecastLineCENCO_ID)
	{
		if (PP_ForecastLineCENCO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_ForecastLineCENCO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_ForecastLineCENCO_ID, Integer.valueOf(PP_ForecastLineCENCO_ID));
	}

	/** Get PP_ForecastLineCENCO ID.
		@return PP_ForecastLineCENCO ID	  */
	public int getPP_ForecastLineCENCO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_ForecastLineCENCO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}