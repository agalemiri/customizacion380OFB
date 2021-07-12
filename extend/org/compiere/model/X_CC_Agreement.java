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

/** Generated Model for CC_Agreement
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_Agreement extends PO implements I_CC_Agreement, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170704L;

    /** Standard Constructor */
    public X_CC_Agreement (Properties ctx, int CC_Agreement_ID, String trxName)
    {
      super (ctx, CC_Agreement_ID, trxName);
      /** if (CC_Agreement_ID == 0)
        {
			setCC_Agreement_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CC_Agreement (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_Agreement[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CC_Agreement ID.
		@param CC_Agreement_ID CC_Agreement ID	  */
	public void setCC_Agreement_ID (int CC_Agreement_ID)
	{
		if (CC_Agreement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_Agreement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_Agreement_ID, Integer.valueOf(CC_Agreement_ID));
	}

	/** Get CC_Agreement ID.
		@return CC_Agreement ID	  */
	public int getCC_Agreement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_Agreement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CC_HealthPlan getCC_HealthPlan() throws RuntimeException
    {
		return (I_CC_HealthPlan)MTable.get(getCtx(), I_CC_HealthPlan.Table_Name)
			.getPO(getCC_HealthPlan_ID(), get_TrxName());	}

	/** Set CC_HealthPlan ID.
		@param CC_HealthPlan_ID CC_HealthPlan ID	  */
	public void setCC_HealthPlan_ID (int CC_HealthPlan_ID)
	{
		if (CC_HealthPlan_ID < 1) 
			set_Value (COLUMNNAME_CC_HealthPlan_ID, null);
		else 
			set_Value (COLUMNNAME_CC_HealthPlan_ID, Integer.valueOf(CC_HealthPlan_ID));
	}

	/** Get CC_HealthPlan ID.
		@return CC_HealthPlan ID	  */
	public int getCC_HealthPlan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_HealthPlan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CC_Isapre getCC_Isapre() throws RuntimeException
    {
		return (I_CC_Isapre)MTable.get(getCtx(), I_CC_Isapre.Table_Name)
			.getPO(getCC_Isapre_ID(), get_TrxName());	}

	/** Set CC_Isapre ID.
		@param CC_Isapre_ID CC_Isapre ID	  */
	public void setCC_Isapre_ID (int CC_Isapre_ID)
	{
		if (CC_Isapre_ID < 1) 
			set_Value (COLUMNNAME_CC_Isapre_ID, null);
		else 
			set_Value (COLUMNNAME_CC_Isapre_ID, Integer.valueOf(CC_Isapre_ID));
	}

	/** Get CC_Isapre ID.
		@return CC_Isapre ID	  */
	public int getCC_Isapre_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_Isapre_ID);
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}
}