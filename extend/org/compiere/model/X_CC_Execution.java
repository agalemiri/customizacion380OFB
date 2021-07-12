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

/** Generated Model for CC_Execution
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_Execution extends PO implements I_CC_Execution, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170704L;

    /** Standard Constructor */
    public X_CC_Execution (Properties ctx, int CC_Execution_ID, String trxName)
    {
      super (ctx, CC_Execution_ID, trxName);
      /** if (CC_Execution_ID == 0)
        {
			setCC_Execution_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CC_Execution (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_Execution[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CC_Execution.
		@param CC_Execution_ID CC_Execution	  */
	public void setCC_Execution_ID (int CC_Execution_ID)
	{
		if (CC_Execution_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_Execution_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_Execution_ID, Integer.valueOf(CC_Execution_ID));
	}

	/** Get CC_Execution.
		@return CC_Execution	  */
	public int getCC_Execution_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_Execution_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CC_NursingPlan getCC_NursingPlan() throws RuntimeException
    {
		return (I_CC_NursingPlan)MTable.get(getCtx(), I_CC_NursingPlan.Table_Name)
			.getPO(getCC_NursingPlan_ID(), get_TrxName());	}

	/** Set CC_NursingPlan ID.
		@param CC_NursingPlan_ID CC_NursingPlan ID	  */
	public void setCC_NursingPlan_ID (int CC_NursingPlan_ID)
	{
		if (CC_NursingPlan_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_NursingPlan_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_NursingPlan_ID, Integer.valueOf(CC_NursingPlan_ID));
	}

	/** Get CC_NursingPlan ID.
		@return CC_NursingPlan ID	  */
	public int getCC_NursingPlan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_NursingPlan_ID);
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
}