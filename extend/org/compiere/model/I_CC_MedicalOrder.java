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

/** Generated Interface for CC_MedicalOrder
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_CC_MedicalOrder 
{

    /** TableName=CC_MedicalOrder */
    public static final String Table_Name = "CC_MedicalOrder";

    /** AD_Table_ID=2000025 */
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

    /** Column name CC_CIE9_ID */
    public static final String COLUMNNAME_CC_CIE9_ID = "CC_CIE9_ID";

	/** Set CC_CIE9_ID	  */
	public void setCC_CIE9_ID (int CC_CIE9_ID);

	/** Get CC_CIE9_ID	  */
	public int getCC_CIE9_ID();

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

    /** Column name CC_MedicalOrder_ID */
    public static final String COLUMNNAME_CC_MedicalOrder_ID = "CC_MedicalOrder_ID";

	/** Set CC_MedicalOrder ID	  */
	public void setCC_MedicalOrder_ID (int CC_MedicalOrder_ID);

	/** Get CC_MedicalOrder ID	  */
	public int getCC_MedicalOrder_ID();

    /** Column name CC_MedicalOrder1_ID */
    public static final String COLUMNNAME_CC_MedicalOrder1_ID = "CC_MedicalOrder1_ID";

	/** Set CC_MedicalOrder1_ID	  */
	public void setCC_MedicalOrder1_ID (String CC_MedicalOrder1_ID);

	/** Get CC_MedicalOrder1_ID	  */
	public String getCC_MedicalOrder1_ID();

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

    /** Column name Decision */
    public static final String COLUMNNAME_Decision = "Decision";

	/** Set Decision	  */
	public void setDecision (String Decision);

	/** Get Decision	  */
	public String getDecision();

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

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name PrintFormatType3 */
    public static final String COLUMNNAME_PrintFormatType3 = "PrintFormatType3";

	/** Set PrintFormatType3.
	  * Print Format Type
	  */
	public void setPrintFormatType3 (String PrintFormatType3);

	/** Get PrintFormatType3.
	  * Print Format Type
	  */
	public String getPrintFormatType3();

    /** Column name PrintFormatType4 */
    public static final String COLUMNNAME_PrintFormatType4 = "PrintFormatType4";

	/** Set PrintFormatType4.
	  * Print Format Type
	  */
	public void setPrintFormatType4 (String PrintFormatType4);

	/** Get PrintFormatType4.
	  * Print Format Type
	  */
	public String getPrintFormatType4();

    /** Column name PrintFormatType5 */
    public static final String COLUMNNAME_PrintFormatType5 = "PrintFormatType5";

	/** Set PrintFormatType5.
	  * Print Format Type
	  */
	public void setPrintFormatType5 (String PrintFormatType5);

	/** Get PrintFormatType5.
	  * Print Format Type
	  */
	public String getPrintFormatType5();

    /** Column name PrintFormatType6 */
    public static final String COLUMNNAME_PrintFormatType6 = "PrintFormatType6";

	/** Set PrintFormatType6.
	  * Print Format Type
	  */
	public void setPrintFormatType6 (String PrintFormatType6);

	/** Get PrintFormatType6.
	  * Print Format Type
	  */
	public String getPrintFormatType6();

    /** Column name PrintFormatType7 */
    public static final String COLUMNNAME_PrintFormatType7 = "PrintFormatType7";

	/** Set PrintFormatType7.
	  * Print Format Type
	  */
	public void setPrintFormatType7 (String PrintFormatType7);

	/** Get PrintFormatType7.
	  * Print Format Type
	  */
	public String getPrintFormatType7();

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

    /** Column name Schedules */
    public static final String COLUMNNAME_Schedules = "Schedules";

	/** Set Schedules	  */
	public void setSchedules (String Schedules);

	/** Get Schedules	  */
	public String getSchedules();

    /** Column name ServiceUser */
    public static final String COLUMNNAME_ServiceUser = "ServiceUser";

	/** Set ServiceUser	  */
	public void setServiceUser (String ServiceUser);

	/** Get ServiceUser	  */
	public String getServiceUser();

    /** Column name Update1 */
    public static final String COLUMNNAME_Update1 = "Update1";

	/** Set Update1	  */
	public void setUpdate1 (Timestamp Update1);

	/** Get Update1	  */
	public Timestamp getUpdate1();

    /** Column name Update2 */
    public static final String COLUMNNAME_Update2 = "Update2";

	/** Set Update2	  */
	public void setUpdate2 (Timestamp Update2);

	/** Get Update2	  */
	public Timestamp getUpdate2();

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
