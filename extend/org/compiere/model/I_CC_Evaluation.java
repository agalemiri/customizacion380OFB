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

/** Generated Interface for CC_Evaluation
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_CC_Evaluation 
{

    /** TableName=CC_Evaluation */
    public static final String Table_Name = "CC_Evaluation";

    /** AD_Table_ID=2000014 */
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

    /** Column name AD_User1_ID */
    public static final String COLUMNNAME_AD_User1_ID = "AD_User1_ID";

	/** Set AD_User1_ID	  */
	public void setAD_User1_ID (int AD_User1_ID);

	/** Get AD_User1_ID	  */
	public int getAD_User1_ID();

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

    /** Column name Boton1 */
    public static final String COLUMNNAME_Boton1 = "Boton1";

	/** Set Boton1	  */
	public void setBoton1 (String Boton1);

	/** Get Boton1	  */
	public String getBoton1();

    /** Column name BtnGenerateMove */
    public static final String COLUMNNAME_BtnGenerateMove = "BtnGenerateMove";

	/** Set BtnGenerateMove	  */
	public void setBtnGenerateMove (String BtnGenerateMove);

	/** Get BtnGenerateMove	  */
	public String getBtnGenerateMove();

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

    /** Column name CC_CIE10_ID */
    public static final String COLUMNNAME_CC_CIE10_ID = "CC_CIE10_ID";

	/** Set CC_CIE10_ID	  */
	public void setCC_CIE10_ID (int CC_CIE10_ID);

	/** Get CC_CIE10_ID	  */
	public int getCC_CIE10_ID();

	public I_CC_CIE10 getCC_CIE10() throws RuntimeException;

    /** Column name CC_Evaluation_ID */
    public static final String COLUMNNAME_CC_Evaluation_ID = "CC_Evaluation_ID";

	/** Set CC_Evaluation ID	  */
	public void setCC_Evaluation_ID (int CC_Evaluation_ID);

	/** Get CC_Evaluation ID	  */
	public int getCC_Evaluation_ID();

    /** Column name CC_Hospitalization_ID */
    public static final String COLUMNNAME_CC_Hospitalization_ID = "CC_Hospitalization_ID";

	/** Set CC_Hospitalization ID	  */
	public void setCC_Hospitalization_ID (int CC_Hospitalization_ID);

	/** Get CC_Hospitalization ID	  */
	public int getCC_Hospitalization_ID();

	public I_CC_Hospitalization getCC_Hospitalization() throws RuntimeException;

    /** Column name Comments */
    public static final String COLUMNNAME_Comments = "Comments";

	/** Set Comments.
	  * Comments or additional information
	  */
	public void setComments (String Comments);

	/** Get Comments.
	  * Comments or additional information
	  */
	public String getComments();

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

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

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

    /** Column name DietFood */
    public static final String COLUMNNAME_DietFood = "DietFood";

	/** Set DietFood	  */
	public void setDietFood (String DietFood);

	/** Get DietFood	  */
	public String getDietFood();

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

    /** Column name M_Movement_ID */
    public static final String COLUMNNAME_M_Movement_ID = "M_Movement_ID";

	/** Set Inventory Move.
	  * Movement of Inventory
	  */
	public void setM_Movement_ID (int M_Movement_ID);

	/** Get Inventory Move.
	  * Movement of Inventory
	  */
	public int getM_Movement_ID();

	public org.compiere.model.I_M_Movement getM_Movement() throws RuntimeException;

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

    /** Column name PrintFormatType */
    public static final String COLUMNNAME_PrintFormatType = "PrintFormatType";

	/** Set Format Type.
	  * Print Format Type
	  */
	public void setPrintFormatType (String PrintFormatType);

	/** Get Format Type.
	  * Print Format Type
	  */
	public String getPrintFormatType();

    /** Column name ProcedureName */
    public static final String COLUMNNAME_ProcedureName = "ProcedureName";

	/** Set Procedure.
	  * Name of the Database Procedure
	  */
	public void setProcedureName (String ProcedureName);

	/** Get Procedure.
	  * Name of the Database Procedure
	  */
	public String getProcedureName();

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

    /** Column name Repose */
    public static final String COLUMNNAME_Repose = "Repose";

	/** Set Repose	  */
	public void setRepose (String Repose);

	/** Get Repose	  */
	public String getRepose();

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
