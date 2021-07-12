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

/** Generated Interface for CC_Other_Indications
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_CC_Other_Indications 
{

    /** TableName=CC_Other_Indications */
    public static final String Table_Name = "CC_Other_Indications";

    /** AD_Table_ID=2000047 */
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

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

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

    /** Column name CC_Other_Indications_ID */
    public static final String COLUMNNAME_CC_Other_Indications_ID = "CC_Other_Indications_ID";

	/** Set CC_Other_Indications ID	  */
	public void setCC_Other_Indications_ID (int CC_Other_Indications_ID);

	/** Get CC_Other_Indications ID	  */
	public int getCC_Other_Indications_ID();

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

    /** Column name DeliveryViaRule */
    public static final String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/** Set Delivery Via.
	  * How the order will be delivered
	  */
	public void setDeliveryViaRule (String DeliveryViaRule);

	/** Get Delivery Via.
	  * How the order will be delivered
	  */
	public String getDeliveryViaRule();

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

    /** Column name Exam */
    public static final String COLUMNNAME_Exam = "Exam";

	/** Set Exam	  */
	public void setExam (String Exam);

	/** Get Exam	  */
	public String getExam();

    /** Column name IndicationType */
    public static final String COLUMNNAME_IndicationType = "IndicationType";

	/** Set IndicationType	  */
	public void setIndicationType (String IndicationType);

	/** Get IndicationType	  */
	public String getIndicationType();

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

    /** Column name KinesiologicalTherapy */
    public static final String COLUMNNAME_KinesiologicalTherapy = "KinesiologicalTherapy";

	/** Set KinesiologicalTherapy	  */
	public void setKinesiologicalTherapy (int KinesiologicalTherapy);

	/** Get KinesiologicalTherapy	  */
	public int getKinesiologicalTherapy();

	public I_C_ValidCombination getKinesiologicalTher() throws RuntimeException;

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

    /** Column name Medicines_Status */
    public static final String COLUMNNAME_Medicines_Status = "Medicines_Status";

	/** Set Medicines_Status	  */
	public void setMedicines_Status (String Medicines_Status);

	/** Get Medicines_Status	  */
	public String getMedicines_Status();

    /** Column name NoPeriods */
    public static final String COLUMNNAME_NoPeriods = "NoPeriods";

	/** Set Number of Periods	  */
	public void setNoPeriods (String NoPeriods);

	/** Get Number of Periods	  */
	public String getNoPeriods();

    /** Column name OtherIndications */
    public static final String COLUMNNAME_OtherIndications = "OtherIndications";

	/** Set OtherIndications	  */
	public void setOtherIndications (String OtherIndications);

	/** Get OtherIndications	  */
	public String getOtherIndications();

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

    /** Column name Procedimientos */
    public static final String COLUMNNAME_Procedimientos = "Procedimientos";

	/** Set Procedimientos	  */
	public void setProcedimientos (String Procedimientos);

	/** Get Procedimientos	  */
	public String getProcedimientos();

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

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

    /** Column name QtyPlan */
    public static final String COLUMNNAME_QtyPlan = "QtyPlan";

	/** Set Quantity Plan.
	  * Planned Quantity
	  */
	public void setQtyPlan (BigDecimal QtyPlan);

	/** Get Quantity Plan.
	  * Planned Quantity
	  */
	public BigDecimal getQtyPlan();

    /** Column name Repose */
    public static final String COLUMNNAME_Repose = "Repose";

	/** Set Repose	  */
	public void setRepose (String Repose);

	/** Get Repose	  */
	public String getRepose();

    /** Column name Schedules1 */
    public static final String COLUMNNAME_Schedules1 = "Schedules1";

	/** Set Schedules1	  */
	public void setSchedules1 (String Schedules1);

	/** Get Schedules1	  */
	public String getSchedules1();

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
