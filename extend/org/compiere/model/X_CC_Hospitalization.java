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

/** Generated Model for CC_Hospitalization
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_Hospitalization extends PO implements I_CC_Hospitalization, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170704L;

    /** Standard Constructor */
    public X_CC_Hospitalization (Properties ctx, int CC_Hospitalization_ID, String trxName)
    {
      super (ctx, CC_Hospitalization_ID, trxName);
      /** if (CC_Hospitalization_ID == 0)
        {
			setCC_Hospitalization_ID (0);
			setProcessed (false);
// N
			setSignature1 (true);
// Y
			setSignature2 (true);
// Y
			setSignature3 (true);
// Y
			setSignature4 (true);
// Y
			setSignature5 (true);
// Y
			setSignature6 (true);
// Y
        } */
    }

    /** Load Constructor */
    public X_CC_Hospitalization (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_Hospitalization[")
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

	/** BedComplexity AD_Reference_ID=2000033 */
	public static final int BEDCOMPLEXITY_AD_Reference_ID=2000033;
	/** Agudo = A */
	public static final String BEDCOMPLEXITY_Agudo = "A";
	/** UTI = UTI */
	public static final String BEDCOMPLEXITY_UTI = "UTI";
	/** UCI = UCI */
	public static final String BEDCOMPLEXITY_UCI = "UCI";
	/** Set BedComplexity.
		@param BedComplexity BedComplexity	  */
	public void setBedComplexity (String BedComplexity)
	{

		set_Value (COLUMNNAME_BedComplexity, BedComplexity);
	}

	/** Get BedComplexity.
		@return BedComplexity	  */
	public String getBedComplexity () 
	{
		return (String)get_Value(COLUMNNAME_BedComplexity);
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	public org.compiere.model.I_C_BPartner getC_BPartnerRef() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartnerRef_ID(), get_TrxName());	}

	/** Set C_BPartnerRef_ID.
		@param C_BPartnerRef_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartnerRef_ID (int C_BPartnerRef_ID)
	{
		if (C_BPartnerRef_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerRef_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerRef_ID, Integer.valueOf(C_BPartnerRef_ID));
	}

	/** Get C_BPartnerRef_ID.
		@return Identifies a Business Partner
	  */
	public int getC_BPartnerRef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerRef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartnerRef2() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartnerRef2_ID(), get_TrxName());	}

	/** Set C_BPartnerRef2_ID.
		@param C_BPartnerRef2_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartnerRef2_ID (int C_BPartnerRef2_ID)
	{
		if (C_BPartnerRef2_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerRef2_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerRef2_ID, Integer.valueOf(C_BPartnerRef2_ID));
	}

	/** Get C_BPartnerRef2_ID.
		@return Identifies a Business Partner
	  */
	public int getC_BPartnerRef2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerRef2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CC_Agreement getCC_Agreement() throws RuntimeException
    {
		return (I_CC_Agreement)MTable.get(getCtx(), I_CC_Agreement.Table_Name)
			.getPO(getCC_Agreement_ID(), get_TrxName());	}

	/** Set CC_Agreement ID.
		@param CC_Agreement_ID CC_Agreement ID	  */
	public void setCC_Agreement_ID (int CC_Agreement_ID)
	{
		if (CC_Agreement_ID < 1) 
			set_Value (COLUMNNAME_CC_Agreement_ID, null);
		else 
			set_Value (COLUMNNAME_CC_Agreement_ID, Integer.valueOf(CC_Agreement_ID));
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

	/** Set Invited.
		@param DateInvited 
		Date when (last) invitation was sent
	  */
	public void setDateInvited (Timestamp DateInvited)
	{
		set_Value (COLUMNNAME_DateInvited, DateInvited);
	}

	/** Get Invited.
		@return Date when (last) invitation was sent
	  */
	public Timestamp getDateInvited () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvited);
	}

	/** Set Date Start.
		@param DateStart 
		Date Start for this Order
	  */
	public void setDateStart (Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	/** Get Date Start.
		@return Date Start for this Order
	  */
	public Timestamp getDateStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStart);
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

	/** Set Description5.
		@param Description5 
		Optional short description of the record
	  */
	public void setDescription5 (String Description5)
	{
		set_Value (COLUMNNAME_Description5, Description5);
	}

	/** Get Description5.
		@return Optional short description of the record
	  */
	public String getDescription5 () 
	{
		return (String)get_Value(COLUMNNAME_Description5);
	}

	/** Set Description6.
		@param Description6 
		Optional short description of the record
	  */
	public void setDescription6 (String Description6)
	{
		set_Value (COLUMNNAME_Description6, Description6);
	}

	/** Get Description6.
		@return Optional short description of the record
	  */
	public String getDescription6 () 
	{
		return (String)get_Value(COLUMNNAME_Description6);
	}

	/** Set Description URL.
		@param DescriptionURL 
		URL for the description
	  */
	public void setDescriptionURL (String DescriptionURL)
	{
		set_Value (COLUMNNAME_DescriptionURL, DescriptionURL);
	}

	/** Get Description URL.
		@return URL for the description
	  */
	public String getDescriptionURL () 
	{
		return (String)get_Value(COLUMNNAME_DescriptionURL);
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

	/** Set Document Note.
		@param DocumentNote 
		Additional information for a Document
	  */
	public void setDocumentNote (String DocumentNote)
	{
		set_Value (COLUMNNAME_DocumentNote, DocumentNote);
	}

	/** Get Document Note.
		@return Additional information for a Document
	  */
	public String getDocumentNote () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNote);
	}

	/** Set Exempt reason.
		@param ExemptReason 
		Reason for not withholding
	  */
	public void setExemptReason (String ExemptReason)
	{
		set_Value (COLUMNNAME_ExemptReason, ExemptReason);
	}

	/** Get Exempt reason.
		@return Reason for not withholding
	  */
	public String getExemptReason () 
	{
		return (String)get_Value(COLUMNNAME_ExemptReason);
	}

	/** Set HealthInsurance.
		@param HealthInsurance HealthInsurance	  */
	public void setHealthInsurance (String HealthInsurance)
	{
		set_Value (COLUMNNAME_HealthInsurance, HealthInsurance);
	}

	/** Get HealthInsurance.
		@return HealthInsurance	  */
	public String getHealthInsurance () 
	{
		return (String)get_Value(COLUMNNAME_HealthInsurance);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set IsReadOnly1.
		@param IsReadOnly1 IsReadOnly1	  */
	public void setIsReadOnly1 (boolean IsReadOnly1)
	{
		set_Value (COLUMNNAME_IsReadOnly1, Boolean.valueOf(IsReadOnly1));
	}

	/** Get IsReadOnly1.
		@return IsReadOnly1	  */
	public boolean isReadOnly1 () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadOnly1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Location comment.
		@param LocationComment 
		Additional comments or remarks concerning the location
	  */
	public void setLocationComment (String LocationComment)
	{
		set_Value (COLUMNNAME_LocationComment, LocationComment);
	}

	/** Get Location comment.
		@return Additional comments or remarks concerning the location
	  */
	public String getLocationComment () 
	{
		return (String)get_Value(COLUMNNAME_LocationComment);
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

	/** Set OfbButton.
		@param OfbButton OfbButton	  */
	public void setOfbButton (String OfbButton)
	{
		set_Value (COLUMNNAME_OfbButton, OfbButton);
	}

	/** Get OfbButton.
		@return OfbButton	  */
	public String getOfbButton () 
	{
		return (String)get_Value(COLUMNNAME_OfbButton);
	}

	/** Set Other SQL Clause.
		@param OtherClause 
		Other SQL Clause
	  */
	public void setOtherClause (String OtherClause)
	{
		set_Value (COLUMNNAME_OtherClause, OtherClause);
	}

	/** Get Other SQL Clause.
		@return Other SQL Clause
	  */
	public String getOtherClause () 
	{
		return (String)get_Value(COLUMNNAME_OtherClause);
	}

	/** Set Planner Key.
		@param PlannerValue 
		Search Key of the Planning
	  */
	public void setPlannerValue (String PlannerValue)
	{
		set_Value (COLUMNNAME_PlannerValue, PlannerValue);
	}

	/** Get Planner Key.
		@return Search Key of the Planning
	  */
	public String getPlannerValue () 
	{
		return (String)get_Value(COLUMNNAME_PlannerValue);
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

	/** Set Signature1.
		@param Signature1 Signature1	  */
	public void setSignature1 (boolean Signature1)
	{
		set_Value (COLUMNNAME_Signature1, Boolean.valueOf(Signature1));
	}

	/** Get Signature1.
		@return Signature1	  */
	public boolean isSignature1 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature2.
		@param Signature2 Signature2	  */
	public void setSignature2 (boolean Signature2)
	{
		set_Value (COLUMNNAME_Signature2, Boolean.valueOf(Signature2));
	}

	/** Get Signature2.
		@return Signature2	  */
	public boolean isSignature2 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature3.
		@param Signature3 Signature3	  */
	public void setSignature3 (boolean Signature3)
	{
		set_Value (COLUMNNAME_Signature3, Boolean.valueOf(Signature3));
	}

	/** Get Signature3.
		@return Signature3	  */
	public boolean isSignature3 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature3);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature4.
		@param Signature4 Signature4	  */
	public void setSignature4 (boolean Signature4)
	{
		set_Value (COLUMNNAME_Signature4, Boolean.valueOf(Signature4));
	}

	/** Get Signature4.
		@return Signature4	  */
	public boolean isSignature4 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature4);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature5.
		@param Signature5 Signature5	  */
	public void setSignature5 (boolean Signature5)
	{
		set_Value (COLUMNNAME_Signature5, Boolean.valueOf(Signature5));
	}

	/** Get Signature5.
		@return Signature5	  */
	public boolean isSignature5 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature5);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature6.
		@param Signature6 Signature6	  */
	public void setSignature6 (boolean Signature6)
	{
		set_Value (COLUMNNAME_Signature6, Boolean.valueOf(Signature6));
	}

	/** Get Signature6.
		@return Signature6	  */
	public boolean isSignature6 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature6);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** SQLStatement AD_Reference_ID=319 */
	public static final int SQLSTATEMENT_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String SQLSTATEMENT_Yes = "Y";
	/** No = N */
	public static final String SQLSTATEMENT_No = "N";
	/** Set SQLStatement.
		@param SQLStatement SQLStatement	  */
	public void setSQLStatement (String SQLStatement)
	{

		throw new IllegalArgumentException ("SQLStatement is virtual column");	}

	/** Get SQLStatement.
		@return SQLStatement	  */
	public String getSQLStatement () 
	{
		return (String)get_Value(COLUMNNAME_SQLStatement);
	}
}