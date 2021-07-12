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

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_PriorityCenco
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_M_PriorityCenco extends PO implements I_M_PriorityCenco, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20181129L;

    /** Standard Constructor */
    public X_M_PriorityCenco (Properties ctx, int M_PriorityCenco_ID, String trxName)
    {
      super (ctx, M_PriorityCenco_ID, trxName);
      /** if (M_PriorityCenco_ID == 0)
        {
			setM_PriorityCenco_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_PriorityCenco (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_PriorityCenco[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set M_PriorityCenco_ID.
		@param M_PriorityCenco_ID M_PriorityCenco_ID	  */
	public void setM_PriorityCenco_ID (int M_PriorityCenco_ID)
	{
		if (M_PriorityCenco_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PriorityCenco_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PriorityCenco_ID, Integer.valueOf(M_PriorityCenco_ID));
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

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (int Qty)
	{
		set_Value (COLUMNNAME_Qty, Integer.valueOf(Qty));
	}

	/** Get Quantity.
		@return Quantity
	  */
	public int getQty () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Qty);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Type AD_Reference_ID=2000053 */
	public static final int TYPE_AD_Reference_ID=2000053;
	/** SISA_OTROS = 1 */
	public static final String TYPE_SISA_OTROS = "1";
	/** SISA_OTROS_PORC = 2 */
	public static final String TYPE_SISA_OTROS_PORC = "2";
	/** SISA_ PARRILLERO_OTRO = 3 */
	public static final String TYPE_SISA_PARRILLERO_OTRO = "3";
	/** SISA_ PARRILLERO = 4 */
	public static final String TYPE_SISA_PARRILLERO = "4";
	/** PORC ZONA_1_O = 5 */
	public static final String TYPE_PORCZONA_1_O = "5";
	/** PORC ZONA_1 = 6 */
	public static final String TYPE_PORCZONA_1 = "6";
	/** PORC ZONA_2 = 7 */
	public static final String TYPE_PORCZONA_2 = "7";
	/** AMERICANO = 8 */
	public static final String TYPE_AMERICANO = "8";
	/** Set Type.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type)
	{

		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Type.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType () 
	{
		return (String)get_Value(COLUMNNAME_Type);
	}
}