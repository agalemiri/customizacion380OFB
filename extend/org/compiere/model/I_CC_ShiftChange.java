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

/** Generated Interface for CC_ShiftChange
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_CC_ShiftChange 
{

    /** TableName=CC_ShiftChange */
    public static final String Table_Name = "CC_ShiftChange";

    /** AD_Table_ID=2000028 */
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

    /** Column name AD_User2_ID */
    public static final String COLUMNNAME_AD_User2_ID = "AD_User2_ID";

	/** Set User/Contact2.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User2_ID (int AD_User2_ID);

	/** Get User/Contact2.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User2_ID();

    /** Column name AD_User3_ID */
    public static final String COLUMNNAME_AD_User3_ID = "AD_User3_ID";

	/** Set AD_User3_ID	  */
	public void setAD_User3_ID (int AD_User3_ID);

	/** Get AD_User3_ID	  */
	public int getAD_User3_ID();

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

    /** Column name CC_Evaluation_ID */
    public static final String COLUMNNAME_CC_Evaluation_ID = "CC_Evaluation_ID";

	/** Set CC_Evaluation ID	  */
	public void setCC_Evaluation_ID (int CC_Evaluation_ID);

	/** Get CC_Evaluation ID	  */
	public int getCC_Evaluation_ID();

	public I_CC_Evaluation getCC_Evaluation() throws RuntimeException;

    /** Column name CC_Hospitalization_ID */
    public static final String COLUMNNAME_CC_Hospitalization_ID = "CC_Hospitalization_ID";

	/** Set CC_Hospitalization ID	  */
	public void setCC_Hospitalization_ID (int CC_Hospitalization_ID);

	/** Get CC_Hospitalization ID	  */
	public int getCC_Hospitalization_ID();

	public I_CC_Hospitalization getCC_Hospitalization() throws RuntimeException;

    /** Column name CC_ShiftChange_ID */
    public static final String COLUMNNAME_CC_ShiftChange_ID = "CC_ShiftChange_ID";

	/** Set CC_ShiftChange ID	  */
	public void setCC_ShiftChange_ID (int CC_ShiftChange_ID);

	/** Get CC_ShiftChange ID	  */
	public int getCC_ShiftChange_ID();

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

    /** Column name Date1 */
    public static final String COLUMNNAME_Date1 = "Date1";

	/** Set Date.
	  * Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1);

	/** Get Date.
	  * Date when business is not conducted
	  */
	public Timestamp getDate1();

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

    /** Column name PlanningHorizon */
    public static final String COLUMNNAME_PlanningHorizon = "PlanningHorizon";

	/** Set Planning Horizon.
	  * The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.
	  */
	public void setPlanningHorizon (String PlanningHorizon);

	/** Get Planning Horizon.
	  * The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.
	  */
	public String getPlanningHorizon();

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

    /** Column name Processed2 */
    public static final String COLUMNNAME_Processed2 = "Processed2";

	/** Set Processed2.
	  * The document has been processed
	  */
	public void setProcessed2 (boolean Processed2);

	/** Get Processed2.
	  * The document has been processed
	  */
	public boolean isProcessed2();

    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

	/** Set Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type);

	/** Get Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType();

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
