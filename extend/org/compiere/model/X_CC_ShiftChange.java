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

/** Generated Model for CC_ShiftChange
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_ShiftChange extends PO implements I_CC_ShiftChange, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190123L;

    /** Standard Constructor */
    public X_CC_ShiftChange (Properties ctx, int CC_ShiftChange_ID, String trxName)
    {
      super (ctx, CC_ShiftChange_ID, trxName);
      /** if (CC_ShiftChange_ID == 0)
        {
			setCC_ShiftChange_ID (0);
			setProcessed (false);
// N
        } */
    }

    /** Load Constructor */
    public X_CC_ShiftChange (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_ShiftChange[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

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

	/** Set User/Contact2.
		@param AD_User2_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User2_ID (int AD_User2_ID)
	{
		if (AD_User2_ID < 1) 
			set_Value (COLUMNNAME_AD_User2_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User2_ID, Integer.valueOf(AD_User2_ID));
	}

	/** Get User/Contact2.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_User3_ID.
		@param AD_User3_ID AD_User3_ID	  */
	public void setAD_User3_ID (int AD_User3_ID)
	{
		if (AD_User3_ID < 1) 
			set_Value (COLUMNNAME_AD_User3_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User3_ID, Integer.valueOf(AD_User3_ID));
	}

	/** Get AD_User3_ID.
		@return AD_User3_ID	  */
	public int getAD_User3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User3_ID);
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

	public I_CC_Evaluation getCC_Evaluation() throws RuntimeException
    {
		return (I_CC_Evaluation)MTable.get(getCtx(), I_CC_Evaluation.Table_Name)
			.getPO(getCC_Evaluation_ID(), get_TrxName());	}

	/** Set CC_Evaluation ID.
		@param CC_Evaluation_ID CC_Evaluation ID	  */
	public void setCC_Evaluation_ID (int CC_Evaluation_ID)
	{
		if (CC_Evaluation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_Evaluation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_Evaluation_ID, Integer.valueOf(CC_Evaluation_ID));
	}

	/** Get CC_Evaluation ID.
		@return CC_Evaluation ID	  */
	public int getCC_Evaluation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_Evaluation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CC_Hospitalization getCC_Hospitalization() throws RuntimeException
    {
		return (I_CC_Hospitalization)MTable.get(getCtx(), I_CC_Hospitalization.Table_Name)
			.getPO(getCC_Hospitalization_ID(), get_TrxName());	}

	/** Set CC_Hospitalization ID.
		@param CC_Hospitalization_ID CC_Hospitalization ID	  */
	public void setCC_Hospitalization_ID (int CC_Hospitalization_ID)
	{
		if (CC_Hospitalization_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_Hospitalization_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_Hospitalization_ID, Integer.valueOf(CC_Hospitalization_ID));
	}

	/** Get CC_Hospitalization ID.
		@return CC_Hospitalization ID	  */
	public int getCC_Hospitalization_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_Hospitalization_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CC_ShiftChange ID.
		@param CC_ShiftChange_ID CC_ShiftChange ID	  */
	public void setCC_ShiftChange_ID (int CC_ShiftChange_ID)
	{
		if (CC_ShiftChange_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_ShiftChange_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_ShiftChange_ID, Integer.valueOf(CC_ShiftChange_ID));
	}

	/** Get CC_ShiftChange ID.
		@return CC_ShiftChange ID	  */
	public int getCC_ShiftChange_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_ShiftChange_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Description1.
		@param Description1 
		Optional short description of the record
	  */
	public void setDescription1 (String Description1)
	{
		set_Value (COLUMNNAME_Description1, Description1);
	}

	/** Get Description1.
		@return Optional short description of the record
	  */
	public String getDescription1 () 
	{
		return (String)get_Value(COLUMNNAME_Description1);
	}

	/** Set Description2.
		@param Description2 
		Optional short description of the record
	  */
	public void setDescription2 (String Description2)
	{
		set_Value (COLUMNNAME_Description2, Description2);
	}

	/** Get Description2.
		@return Optional short description of the record
	  */
	public String getDescription2 () 
	{
		return (String)get_Value(COLUMNNAME_Description2);
	}

	/** Set Description3.
		@param Description3 
		Optional short description of the record
	  */
	public void setDescription3 (String Description3)
	{
		set_Value (COLUMNNAME_Description3, Description3);
	}

	/** Get Description3.
		@return Optional short description of the record
	  */
	public String getDescription3 () 
	{
		return (String)get_Value(COLUMNNAME_Description3);
	}

	/** Set Description4.
		@param Description4 
		Optional short description of the record
	  */
	public void setDescription4 (String Description4)
	{
		set_Value (COLUMNNAME_Description4, Description4);
	}

	/** Get Description4.
		@return Optional short description of the record
	  */
	public String getDescription4 () 
	{
		return (String)get_Value(COLUMNNAME_Description4);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Planning Horizon.
		@param PlanningHorizon 
		The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.
	  */
	public void setPlanningHorizon (String PlanningHorizon)
	{
		set_Value (COLUMNNAME_PlanningHorizon, PlanningHorizon);
	}

	/** Get Planning Horizon.
		@return The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.
	  */
	public String getPlanningHorizon () 
	{
		return (String)get_Value(COLUMNNAME_PlanningHorizon);
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

	/** Set Processed2.
		@param Processed2 
		The document has been processed
	  */
	public void setProcessed2 (boolean Processed2)
	{
		set_Value (COLUMNNAME_Processed2, Boolean.valueOf(Processed2));
	}

	/** Get Processed2.
		@return The document has been processed
	  */
	public boolean isProcessed2 () 
	{
		Object oo = get_Value(COLUMNNAME_Processed2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Type AD_Reference_ID=2000182 */
	public static final int TYPE_AD_Reference_ID=2000182;
	/** Medico = ME */
	public static final String TYPE_Medico = "ME";
	/** Enfermero = EN */
	public static final String TYPE_Enfermero = "EN";
	/** Kinesiologo = KI */
	public static final String TYPE_Kinesiologo = "KI";
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