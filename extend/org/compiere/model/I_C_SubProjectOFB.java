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

/** Generated Interface for C_SubProjectOFB
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_C_SubProjectOFB 
{

    /** TableName=C_SubProjectOFB */
    public static final String Table_Name = "C_SubProjectOFB";

    /** AD_Table_ID=2000043 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name ActivitiesList */
    public static final String COLUMNNAME_ActivitiesList = "ActivitiesList";

	/** Set ActivitiesList	  */
	public void setActivitiesList (String ActivitiesList);

	/** Get ActivitiesList	  */
	public String getActivitiesList();

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

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

    /** Column name AmtApproval */
    public static final String COLUMNNAME_AmtApproval = "AmtApproval";

	/** Set Approval Amount.
	  * The approval amount limit for this role
	  */
	public void setAmtApproval (BigDecimal AmtApproval);

	/** Get Approval Amount.
	  * The approval amount limit for this role
	  */
	public BigDecimal getAmtApproval();

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

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    /** Column name C_Phase_ID1 */
    public static final String COLUMNNAME_C_Phase_ID1 = "C_Phase_ID1";

	/** Set Standard Phase.
	  * Standard Phase of the Project Type
	  */
	public void setC_Phase_ID1 (String C_Phase_ID1);

	/** Get Standard Phase.
	  * Standard Phase of the Project Type
	  */
	public String getC_Phase_ID1();

    /** Column name C_Phase_ID2 */
    public static final String COLUMNNAME_C_Phase_ID2 = "C_Phase_ID2";

	/** Set Standard Phase.
	  * Standard Phase of the Project Type
	  */
	public void setC_Phase_ID2 (String C_Phase_ID2);

	/** Get Standard Phase.
	  * Standard Phase of the Project Type
	  */
	public String getC_Phase_ID2();

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/** Set Project.
	  * Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID);

	/** Get Project.
	  * Financial Project
	  */
	public int getC_Project_ID();

	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException;

    /** Column name C_ProjectLine_ID */
    public static final String COLUMNNAME_C_ProjectLine_ID = "C_ProjectLine_ID";

	/** Set Project Line.
	  * Task or step in a project
	  */
	public void setC_ProjectLine_ID (int C_ProjectLine_ID);

	/** Get Project Line.
	  * Task or step in a project
	  */
	public int getC_ProjectLine_ID();

	public org.compiere.model.I_C_ProjectLine getC_ProjectLine() throws RuntimeException;

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

    /** Column name CriticalKnot */
    public static final String COLUMNNAME_CriticalKnot = "CriticalKnot";

	/** Set CriticalKnot	  */
	public void setCriticalKnot (String CriticalKnot);

	/** Get CriticalKnot	  */
	public String getCriticalKnot();

    /** Column name C_SubProjectOFB_ID */
    public static final String COLUMNNAME_C_SubProjectOFB_ID = "C_SubProjectOFB_ID";

	/** Set C_SubProjectOFB ID	  */
	public void setC_SubProjectOFB_ID (int C_SubProjectOFB_ID);

	/** Get C_SubProjectOFB ID	  */
	public int getC_SubProjectOFB_ID();

    /** Column name DateContract */
    public static final String COLUMNNAME_DateContract = "DateContract";

	/** Set Contract Date.
	  * The (planned) effective date of this document.
	  */
	public void setDateContract (Timestamp DateContract);

	/** Get Contract Date.
	  * The (planned) effective date of this document.
	  */
	public Timestamp getDateContract();

    /** Column name DateFinish */
    public static final String COLUMNNAME_DateFinish = "DateFinish";

	/** Set Finish Date.
	  * Finish or (planned) completion date
	  */
	public void setDateFinish (Timestamp DateFinish);

	/** Get Finish Date.
	  * Finish or (planned) completion date
	  */
	public Timestamp getDateFinish();

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

    /** Column name G_IsValidated */
    public static final String COLUMNNAME_G_IsValidated = "G_IsValidated";

	/** Set G_IsValidated.
	  * The record is validated i
	  */
	public void setG_IsValidated (boolean G_IsValidated);

	/** Get G_IsValidated.
	  * The record is validated i
	  */
	public boolean isG_IsValidated();

    /** Column name G_RealDateEnd */
    public static final String COLUMNNAME_G_RealDateEnd = "G_RealDateEnd";

	/** Set G_RealDateEnd.
	  * The real date end
	  */
	public void setG_RealDateEnd (Timestamp G_RealDateEnd);

	/** Get G_RealDateEnd.
	  * The real date end
	  */
	public Timestamp getG_RealDateEnd();

    /** Column name G_RealDateStart */
    public static final String COLUMNNAME_G_RealDateStart = "G_RealDateStart";

	/** Set G_RealDateStart.
	  * The real date start
	  */
	public void setG_RealDateStart (Timestamp G_RealDateStart);

	/** Get G_RealDateStart.
	  * The real date start
	  */
	public Timestamp getG_RealDateStart();

    /** Column name G_VerificationMeans */
    public static final String COLUMNNAME_G_VerificationMeans = "G_VerificationMeans";

	/** Set G_VerificationMeans.
	  * Optional additional user defined information
	  */
	public void setG_VerificationMeans (String G_VerificationMeans);

	/** Get G_VerificationMeans.
	  * Optional additional user defined information
	  */
	public String getG_VerificationMeans();

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

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/** Set Note.
	  * Optional additional user defined information
	  */
	public void setNote (String Note);

	/** Get Note.
	  * Optional additional user defined information
	  */
	public String getNote();

    /** Column name OFB_Customer */
    public static final String COLUMNNAME_OFB_Customer = "OFB_Customer";

	/** Set OFB_Customer	  */
	public void setOFB_Customer (String OFB_Customer);

	/** Get OFB_Customer	  */
	public String getOFB_Customer();

    /** Column name OFB_Location */
    public static final String COLUMNNAME_OFB_Location = "OFB_Location";

	/** Set OFB_Location	  */
	public void setOFB_Location (String OFB_Location);

	/** Get OFB_Location	  */
	public String getOFB_Location();

    /** Column name OFB_Name */
    public static final String COLUMNNAME_OFB_Name = "OFB_Name";

	/** Set OFB_Name	  */
	public void setOFB_Name (String OFB_Name);

	/** Get OFB_Name	  */
	public String getOFB_Name();

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException;

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
