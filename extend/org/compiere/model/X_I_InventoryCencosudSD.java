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

/** Generated Model for I_InventoryCencosudSD
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_I_InventoryCencosudSD extends PO implements I_I_InventoryCencosudSD, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20181123L;

    /** Standard Constructor */
    public X_I_InventoryCencosudSD (Properties ctx, int I_InventoryCencosudSD_ID, String trxName)
    {
      super (ctx, I_InventoryCencosudSD_ID, trxName);
      /** if (I_InventoryCencosudSD_ID == 0)
        {
			setI_InventoryCencosudSD_ID (0);
        } */
    }

    /** Load Constructor */
    public X_I_InventoryCencosudSD (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_I_InventoryCencosudSD[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set DayDelivered.
		@param DayDelivered DayDelivered	  */
	public void setDayDelivered (BigDecimal DayDelivered)
	{
		set_Value (COLUMNNAME_DayDelivered, DayDelivered);
	}

	/** Get DayDelivered.
		@return DayDelivered	  */
	public BigDecimal getDayDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DayDelivered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set I_InventoryCencosudSD ID.
		@param I_InventoryCencosudSD_ID I_InventoryCencosudSD ID	  */
	public void setI_InventoryCencosudSD_ID (int I_InventoryCencosudSD_ID)
	{
		if (I_InventoryCencosudSD_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_InventoryCencosudSD_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_InventoryCencosudSD_ID, Integer.valueOf(I_InventoryCencosudSD_ID));
	}

	/** Get I_InventoryCencosudSD ID.
		@return I_InventoryCencosudSD ID	  */
	public int getI_InventoryCencosudSD_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_InventoryCencosudSD_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
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

	/** Set Available Quantity.
		@param QtyAvailable 
		Available Quantity (On Hand - Reserved)
	  */
	public void setQtyAvailable (BigDecimal QtyAvailable)
	{
		set_Value (COLUMNNAME_QtyAvailable, QtyAvailable);
	}

	/** Get Available Quantity.
		@return Available Quantity (On Hand - Reserved)
	  */
	public BigDecimal getQtyAvailable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAvailable);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyBox.
		@param qtyBox qtyBox	  */
	public void setqtyBox (BigDecimal qtyBox)
	{
		set_Value (COLUMNNAME_qtyBox, qtyBox);
	}

	/** Get qtyBox.
		@return qtyBox	  */
	public BigDecimal getqtyBox () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyBox);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Used.
		@param QtyUsed Quantity Used	  */
	public void setQtyUsed (BigDecimal QtyUsed)
	{
		set_Value (COLUMNNAME_QtyUsed, QtyUsed);
	}

	/** Get Quantity Used.
		@return Quantity Used	  */
	public BigDecimal getQtyUsed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUsed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}