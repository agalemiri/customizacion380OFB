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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for CC_Hospitalization
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_CC_Hospitalization 
{

    /** TableName=CC_Hospitalization */
    public static final String Table_Name = "CC_Hospitalization";

    /** AD_Table_ID=2000012 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

    /** Column name BedComplexity */
    public static final String COLUMNNAME_BedComplexity = "BedComplexity";

	/** Set BedComplexity	  */
	public void setBedComplexity (String BedComplexity);

	/** Get BedComplexity	  */
	public String getBedComplexity();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BPartnerRef_ID */
    public static final String COLUMNNAME_C_BPartnerRef_ID = "C_BPartnerRef_ID";

	/** Set C_BPartnerRef_ID.
	  * Identifies a Business Partner
	  */
	public void setC_BPartnerRef_ID (int C_BPartnerRef_ID);

	/** Get C_BPartnerRef_ID.
	  * Identifies a Business Partner
	  */
	public int getC_BPartnerRef_ID();

	public org.compiere.model.I_C_BPartner getC_BPartnerRef() throws RuntimeException;

    /** Column name C_BPartnerRef2_ID */
    public static final String COLUMNNAME_C_BPartnerRef2_ID = "C_BPartnerRef2_ID";

	/** Set C_BPartnerRef2_ID.
	  * Identifies a Business Partner
	  */
	public void setC_BPartnerRef2_ID (int C_BPartnerRef2_ID);

	/** Get C_BPartnerRef2_ID.
	  * Identifies a Business Partner
	  */
	public int getC_BPartnerRef2_ID();

	public org.compiere.model.I_C_BPartner getC_BPartnerRef2() throws RuntimeException;

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name CC_Agreement_ID */
    public static final String COLUMNNAME_CC_Agreement_ID = "CC_Agreement_ID";

	/** Set CC_Agreement ID	  */
	public void setCC_Agreement_ID (int CC_Agreement_ID);

	/** Get CC_Agreement ID	  */
	public int getCC_Agreement_ID();

	public I_CC_Agreement getCC_Agreement() throws RuntimeException;

    /** Column name CC_HealthPlan_ID */
    public static final String COLUMNNAME_CC_HealthPlan_ID = "CC_HealthPlan_ID";

	/** Set CC_HealthPlan ID	  */
	public void setCC_HealthPlan_ID (int CC_HealthPlan_ID);

	/** Get CC_HealthPlan ID	  */
	public int getCC_HealthPlan_ID();

	public I_CC_HealthPlan getCC_HealthPlan() throws RuntimeException;

    /** Column name CC_Hospitalization_ID */
    public static final String COLUMNNAME_CC_Hospitalization_ID = "CC_Hospitalization_ID";

	/** Set CC_Hospitalization ID	  */
	public void setCC_Hospitalization_ID (int CC_Hospitalization_ID);

	/** Get CC_Hospitalization ID	  */
	public int getCC_Hospitalization_ID();

    /** Column name CC_Isapre_ID */
    public static final String COLUMNNAME_CC_Isapre_ID = "CC_Isapre_ID";

	/** Set CC_Isapre ID	  */
	public void setCC_Isapre_ID (int CC_Isapre_ID);

	/** Get CC_Isapre ID	  */
	public int getCC_Isapre_ID();

	public I_CC_Isapre getCC_Isapre() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DateInvited */
    public static final String COLUMNNAME_DateInvited = "DateInvited";

	/** Set Invited.
	  * Date when (last) invitation was sent
	  */
	public void setDateInvited (Timestamp DateInvited);

	/** Get Invited.
	  * Date when (last) invitation was sent
	  */
	public Timestamp getDateInvited();

    /** Column name DateStart */
    public static final String COLUMNNAME_DateStart = "DateStart";

	/** Set Date Start.
	  * Date Start for this Order
	  */
	public void setDateStart (Timestamp DateStart);

	/** Get Date Start.
	  * Date Start for this Order
	  */
	public Timestamp getDateStart();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Description1 */
    public static final String COLUMNNAME_Description1 = "Description1";

	/** Set Description1.
	  * Optional short description of the record
	  */
	public void setDescription1 (String Description1);

	/** Get Description1.
	  * Optional short description of the record
	  */
	public String getDescription1();

    /** Column name Description2 */
    public static final String COLUMNNAME_Description2 = "Description2";

	/** Set Description2.
	  * Optional short description of the record
	  */
	public void setDescription2 (String Description2);

	/** Get Description2.
	  * Optional short description of the record
	  */
	public String getDescription2();

    /** Column name Description3 */
    public static final String COLUMNNAME_Description3 = "Description3";

	/** Set Description3.
	  * Optional short description of the record
	  */
	public void setDescription3 (String Description3);

	/** Get Description3.
	  * Optional short description of the record
	  */
	public String getDescription3();

    /** Column name Description4 */
    public static final String COLUMNNAME_Description4 = "Description4";

	/** Set Description4.
	  * Optional short description of the record
	  */
	public void setDescription4 (String Description4);

	/** Get Description4.
	  * Optional short description of the record
	  */
	public String getDescription4();

    /** Column name Description5 */
    public static final String COLUMNNAME_Description5 = "Description5";

	/** Set Description5.
	  * Optional short description of the record
	  */
	public void setDescription5 (String Description5);

	/** Get Description5.
	  * Optional short description of the record
	  */
	public String getDescription5();

    /** Column name Description6 */
    public static final String COLUMNNAME_Description6 = "Description6";

	/** Set Description6.
	  * Optional short description of the record
	  */
	public void setDescription6 (String Description6);

	/** Get Description6.
	  * Optional short description of the record
	  */
	public String getDescription6();

    /** Column name DescriptionURL */
    public static final String COLUMNNAME_DescriptionURL = "DescriptionURL";

	/** Set Description URL.
	  * URL for the description
	  */
	public void setDescriptionURL (String DescriptionURL);

	/** Get Description URL.
	  * URL for the description
	  */
	public String getDescriptionURL();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name DocumentNote */
    public static final String COLUMNNAME_DocumentNote = "DocumentNote";

	/** Set Document Note.
	  * Additional information for a Document
	  */
	public void setDocumentNote (String DocumentNote);

	/** Get Document Note.
	  * Additional information for a Document
	  */
	public String getDocumentNote();

    /** Column name ExemptReason */
    public static final String COLUMNNAME_ExemptReason = "ExemptReason";

	/** Set Exempt reason.
	  * Reason for not withholding
	  */
	public void setExemptReason (String ExemptReason);

	/** Get Exempt reason.
	  * Reason for not withholding
	  */
	public String getExemptReason();

    /** Column name HealthInsurance */
    public static final String COLUMNNAME_HealthInsurance = "HealthInsurance";

	/** Set HealthInsurance	  */
	public void setHealthInsurance (String HealthInsurance);

	/** Get HealthInsurance	  */
	public String getHealthInsurance();

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsReadOnly1 */
    public static final String COLUMNNAME_IsReadOnly1 = "IsReadOnly1";

	/** Set IsReadOnly1	  */
	public void setIsReadOnly1 (boolean IsReadOnly1);

	/** Get IsReadOnly1	  */
	public boolean isReadOnly1();

    /** Column name LocationComment */
    public static final String COLUMNNAME_LocationComment = "LocationComment";

	/** Set Location comment.
	  * Additional comments or remarks concerning the location
	  */
	public void setLocationComment (String LocationComment);

	/** Get Location comment.
	  * Additional comments or remarks concerning the location
	  */
	public String getLocationComment();

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/** Set Locator.
	  * Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID);

	/** Get Locator.
	  * Warehouse Locator
	  */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException;

    /** Column name OfbButton */
    public static final String COLUMNNAME_OfbButton = "OfbButton";

	/** Set OfbButton	  */
	public void setOfbButton (String OfbButton);

	/** Get OfbButton	  */
	public String getOfbButton();

    /** Column name OtherClause */
    public static final String COLUMNNAME_OtherClause = "OtherClause";

	/** Set Other SQL Clause.
	  * Other SQL Clause
	  */
	public void setOtherClause (String OtherClause);

	/** Get Other SQL Clause.
	  * Other SQL Clause
	  */
	public String getOtherClause();

    /** Column name PlannerValue */
    public static final String COLUMNNAME_PlannerValue = "PlannerValue";

	/** Set Planner Key.
	  * Search Key of the Planning
	  */
	public void setPlannerValue (String PlannerValue);

	/** Get Planner Key.
	  * Search Key of the Planning
	  */
	public String getPlannerValue();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Signature1 */
    public static final String COLUMNNAME_Signature1 = "Signature1";

	/** Set Signature1	  */
	public void setSignature1 (boolean Signature1);

	/** Get Signature1	  */
	public boolean isSignature1();

    /** Column name Signature2 */
    public static final String COLUMNNAME_Signature2 = "Signature2";

	/** Set Signature2	  */
	public void setSignature2 (boolean Signature2);

	/** Get Signature2	  */
	public boolean isSignature2();

    /** Column name Signature3 */
    public static final String COLUMNNAME_Signature3 = "Signature3";

	/** Set Signature3	  */
	public void setSignature3 (boolean Signature3);

	/** Get Signature3	  */
	public boolean isSignature3();

    /** Column name Signature4 */
    public static final String COLUMNNAME_Signature4 = "Signature4";

	/** Set Signature4	  */
	public void setSignature4 (boolean Signature4);

	/** Get Signature4	  */
	public boolean isSignature4();

    /** Column name Signature5 */
    public static final String COLUMNNAME_Signature5 = "Signature5";

	/** Set Signature5	  */
	public void setSignature5 (boolean Signature5);

	/** Get Signature5	  */
	public boolean isSignature5();

    /** Column name Signature6 */
    public static final String COLUMNNAME_Signature6 = "Signature6";

	/** Set Signature6	  */
	public void setSignature6 (boolean Signature6);

	/** Get Signature6	  */
	public boolean isSignature6();

    /** Column name SQLStatement */
    public static final String COLUMNNAME_SQLStatement = "SQLStatement";

	/** Set SQLStatement	  */
	public void setSQLStatement (String SQLStatement);

	/** Get SQLStatement	  */
	public String getSQLStatement();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
