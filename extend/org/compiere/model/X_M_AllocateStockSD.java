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

/** Generated Model for M_AllocateStockSD
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_M_AllocateStockSD extends PO implements I_M_AllocateStockSD, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20181129L;

    /** Standard Constructor */
    public X_M_AllocateStockSD (Properties ctx, int M_AllocateStockSD_ID, String trxName)
    {
      super (ctx, M_AllocateStockSD_ID, trxName);
      /** if (M_AllocateStockSD_ID == 0)
        {
			setM_AllocateStockSD_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_AllocateStockSD (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_AllocateStockSD[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getC_OrderLine_ID(), get_TrxName());	}

	/** Set Sales Order Line.
		@param C_OrderLine_ID 
		Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Sales Order Line.
		@return Sales Order Line
	  */
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CCPPKg.
		@param CCPPKg CCPPKg	  */
	public void setCCPPKg (BigDecimal CCPPKg)
	{
		set_Value (COLUMNNAME_CCPPKg, CCPPKg);
	}

	/** Get CCPPKg.
		@return CCPPKg	  */
	public BigDecimal getCCPPKg () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CCPPKg);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CCPPUMB.
		@param CCPPUMB CCPPUMB	  */
	public void setCCPPUMB (BigDecimal CCPPUMB)
	{
		set_Value (COLUMNNAME_CCPPUMB, CCPPUMB);
	}

	/** Get CCPPUMB.
		@return CCPPUMB	  */
	public BigDecimal getCCPPUMB () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CCPPUMB);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_I_InventoryCencosudSD getI_InventoryCencosudSD() throws RuntimeException
    {
		return (I_I_InventoryCencosudSD)MTable.get(getCtx(), I_I_InventoryCencosudSD.Table_Name)
			.getPO(getI_InventoryCencosudSD_ID(), get_TrxName());	}

	/** Set I_InventoryCencosudSD ID.
		@param I_InventoryCencosudSD_ID I_InventoryCencosudSD ID	  */
	public void setI_InventoryCencosudSD_ID (int I_InventoryCencosudSD_ID)
	{
		if (I_InventoryCencosudSD_ID < 1) 
			set_Value (COLUMNNAME_I_InventoryCencosudSD_ID, null);
		else 
			set_Value (COLUMNNAME_I_InventoryCencosudSD_ID, Integer.valueOf(I_InventoryCencosudSD_ID));
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

	/** Set M_AllocateStockSD ID.
		@param M_AllocateStockSD_ID M_AllocateStockSD ID	  */
	public void setM_AllocateStockSD_ID (int M_AllocateStockSD_ID)
	{
		if (M_AllocateStockSD_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_AllocateStockSD_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AllocateStockSD_ID, Integer.valueOf(M_AllocateStockSD_ID));
	}

	/** Get M_AllocateStockSD ID.
		@return M_AllocateStockSD ID	  */
	public int getM_AllocateStockSD_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AllocateStockSD_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_PriorityCenco getM_PriorityCenco() throws RuntimeException
    {
		return (I_M_PriorityCenco)MTable.get(getCtx(), I_M_PriorityCenco.Table_Name)
			.getPO(getM_PriorityCenco_ID(), get_TrxName());	}

	/** Set M_PriorityCenco_ID.
		@param M_PriorityCenco_ID M_PriorityCenco_ID	  */
	public void setM_PriorityCenco_ID (int M_PriorityCenco_ID)
	{
		if (M_PriorityCenco_ID < 1) 
			set_Value (COLUMNNAME_M_PriorityCenco_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriorityCenco_ID, Integer.valueOf(M_PriorityCenco_ID));
	}

	/** Get M_PriorityCenco_ID.
		@return M_PriorityCenco_ID	  */
	public int getM_PriorityCenco_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriorityCenco_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}