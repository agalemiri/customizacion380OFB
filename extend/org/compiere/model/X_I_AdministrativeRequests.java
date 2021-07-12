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
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for I_AdministrativeRequests
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_I_AdministrativeRequests extends PO implements I_I_AdministrativeRequests, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20191105L;

    /** Standard Constructor */
    public X_I_AdministrativeRequests (Properties ctx, int I_AdministrativeRequests_ID, String trxName)
    {
      super (ctx, I_AdministrativeRequests_ID, trxName);
      /** if (I_AdministrativeRequests_ID == 0)
        {
			setI_AdministrativeRequests_ID (0);
        } */
    }

    /** Load Constructor */
    public X_I_AdministrativeRequests (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_I_AdministrativeRequests[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AD_OrgRef_ID.
		@param AD_OrgRef_ID AD_OrgRef_ID	  */
	public void setAD_OrgRef_ID (int AD_OrgRef_ID)
	{
		if (AD_OrgRef_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgRef_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgRef_ID, Integer.valueOf(AD_OrgRef_ID));
	}

	/** Get AD_OrgRef_ID.
		@return AD_OrgRef_ID	  */
	public int getAD_OrgRef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgRef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BP Search Key.
		@param BPValue 
		Business Partner Key Value
	  */
	public void setBPValue (String BPValue)
	{
		set_Value (COLUMNNAME_BPValue, BPValue);
	}

	/** Get BP Search Key.
		@return Business Partner Key Value
	  */
	public String getBPValue () 
	{
		return (String)get_Value(COLUMNNAME_BPValue);
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

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set I_AdministrativeRequests ID.
		@param I_AdministrativeRequests_ID I_AdministrativeRequests ID	  */
	public void setI_AdministrativeRequests_ID (int I_AdministrativeRequests_ID)
	{
		if (I_AdministrativeRequests_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_AdministrativeRequests_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_AdministrativeRequests_ID, Integer.valueOf(I_AdministrativeRequests_ID));
	}

	/** Get I_AdministrativeRequests ID.
		@return I_AdministrativeRequests ID	  */
	public int getI_AdministrativeRequests_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_AdministrativeRequests_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Org Key.
		@param OrgValue 
		Key of the Organization
	  */
	public void setOrgValue (String OrgValue)
	{
		set_Value (COLUMNNAME_OrgValue, OrgValue);
	}

	/** Get Org Key.
		@return Key of the Organization
	  */
	public String getOrgValue () 
	{
		return (String)get_Value(COLUMNNAME_OrgValue);
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

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}
}