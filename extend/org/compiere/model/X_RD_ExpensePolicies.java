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

/** Generated Model for RD_ExpensePolicies
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_RD_ExpensePolicies extends PO implements I_RD_ExpensePolicies, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210311L;

    /** Standard Constructor */
    public X_RD_ExpensePolicies (Properties ctx, int RD_ExpensePolicies_ID, String trxName)
    {
      super (ctx, RD_ExpensePolicies_ID, trxName);
      /** if (RD_ExpensePolicies_ID == 0)
        {
			setRD_ExpensePolicies_ID (0);
        } */
    }

    /** Load Constructor */
    public X_RD_ExpensePolicies (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_RD_ExpensePolicies[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set expensepolicyid.
		@param expensepolicyid expensepolicyid	  */
	public void setexpensepolicyid (int expensepolicyid)
	{
		set_Value (COLUMNNAME_expensepolicyid, Integer.valueOf(expensepolicyid));
	}

	/** Get expensepolicyid.
		@return expensepolicyid	  */
	public int getexpensepolicyid () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_expensepolicyid);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RD_ExpensePolicies ID.
		@param RD_ExpensePolicies_ID RD_ExpensePolicies ID	  */
	public void setRD_ExpensePolicies_ID (int RD_ExpensePolicies_ID)
	{
		if (RD_ExpensePolicies_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_RD_ExpensePolicies_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_RD_ExpensePolicies_ID, Integer.valueOf(RD_ExpensePolicies_ID));
	}

	/** Get RD_ExpensePolicies ID.
		@return RD_ExpensePolicies ID	  */
	public int getRD_ExpensePolicies_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RD_ExpensePolicies_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}