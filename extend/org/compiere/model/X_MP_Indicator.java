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
import org.compiere.util.KeyNamePair;

/** Generated Model for MP_Indicator
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_MP_Indicator extends PO implements I_MP_Indicator, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170809L;

    /** Standard Constructor */
    public X_MP_Indicator (Properties ctx, int MP_Indicator_ID, String trxName)
    {
      super (ctx, MP_Indicator_ID, trxName);
      /** if (MP_Indicator_ID == 0)
        {
			setMP_Indicator_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_MP_Indicator (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MP_Indicator[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Role)MTable.get(getCtx(), org.compiere.model.I_AD_Role.Table_Name)
			.getPO(getAD_Role_ID(), get_TrxName());	}

	/** Set Role.
		@param AD_Role_ID 
		Responsibility Role
	  */
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Role.
		@return Responsibility Role
	  */
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set currentresult.
		@param currentresult currentresult	  */
	public void setcurrentresult (String currentresult)
	{
		set_Value (COLUMNNAME_currentresult, currentresult);
	}

	/** Get currentresult.
		@return currentresult	  */
	public String getcurrentresult () 
	{
		return (String)get_Value(COLUMNNAME_currentresult);
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

	/** Set finish.
		@param finish finish	  */
	public void setfinish (String finish)
	{
		set_Value (COLUMNNAME_finish, finish);
	}

	/** Get finish.
		@return finish	  */
	public String getfinish () 
	{
		return (String)get_Value(COLUMNNAME_finish);
	}

	/** Set formula.
		@param formula formula	  */
	public void setformula (String formula)
	{
		set_Value (COLUMNNAME_formula, formula);
	}

	/** Get formula.
		@return formula	  */
	public String getformula () 
	{
		return (String)get_Value(COLUMNNAME_formula);
	}

	public I_MP_IndicatorGroup getMP_IndicatorGroup() throws RuntimeException
    {
		return (I_MP_IndicatorGroup)MTable.get(getCtx(), I_MP_IndicatorGroup.Table_Name)
			.getPO(getMP_IndicatorGroup_ID(), get_TrxName());	}

	/** Set MP_IndicatorGroup ID.
		@param MP_IndicatorGroup_ID MP_IndicatorGroup ID	  */
	public void setMP_IndicatorGroup_ID (int MP_IndicatorGroup_ID)
	{
		if (MP_IndicatorGroup_ID < 1) 
			set_Value (COLUMNNAME_MP_IndicatorGroup_ID, null);
		else 
			set_Value (COLUMNNAME_MP_IndicatorGroup_ID, Integer.valueOf(MP_IndicatorGroup_ID));
	}

	/** Get MP_IndicatorGroup ID.
		@return MP_IndicatorGroup ID	  */
	public int getMP_IndicatorGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MP_IndicatorGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MP_Indicator ID.
		@param MP_Indicator_ID MP_Indicator ID	  */
	public void setMP_Indicator_ID (int MP_Indicator_ID)
	{
		if (MP_Indicator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MP_Indicator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MP_Indicator_ID, Integer.valueOf(MP_Indicator_ID));
	}

	/** Get MP_Indicator ID.
		@return MP_Indicator ID	  */
	public int getMP_Indicator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MP_Indicator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Note.
		@param Note 
		Optional additional user defined information
	  */
	public void setNote (String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Note.
		@return Optional additional user defined information
	  */
	public String getNote () 
	{
		return (String)get_Value(COLUMNNAME_Note);
	}

	/** Set recurrence.
		@param recurrence recurrence	  */
	public void setrecurrence (boolean recurrence)
	{
		set_Value (COLUMNNAME_recurrence, Boolean.valueOf(recurrence));
	}

	/** Get recurrence.
		@return recurrence	  */
	public boolean isrecurrence () 
	{
		Object oo = get_Value(COLUMNNAME_recurrence);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set rules.
		@param rules rules	  */
	public void setrules (String rules)
	{
		set_Value (COLUMNNAME_rules, rules);
	}

	/** Get rules.
		@return rules	  */
	public String getrules () 
	{
		return (String)get_Value(COLUMNNAME_rules);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set VarA.
		@param VarA VarA	  */
	public void setVarA (String VarA)
	{
		set_Value (COLUMNNAME_VarA, VarA);
	}

	/** Get VarA.
		@return VarA	  */
	public String getVarA () 
	{
		return (String)get_Value(COLUMNNAME_VarA);
	}

	/** Set VarB.
		@param VarB VarB	  */
	public void setVarB (String VarB)
	{
		set_Value (COLUMNNAME_VarB, VarB);
	}

	/** Get VarB.
		@return VarB	  */
	public String getVarB () 
	{
		return (String)get_Value(COLUMNNAME_VarB);
	}

	/** Set verification.
		@param verification verification	  */
	public void setverification (String verification)
	{
		set_Value (COLUMNNAME_verification, verification);
	}

	/** Get verification.
		@return verification	  */
	public String getverification () 
	{
		return (String)get_Value(COLUMNNAME_verification);
	}
}