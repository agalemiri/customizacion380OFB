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

/** Generated Interface for CC_MedicalIndications
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_CC_MedicalIndications 
{

    /** TableName=CC_MedicalIndications */
    public static final String Table_Name = "CC_MedicalIndications";

    /** AD_Table_ID=2000020 */
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

    /** Column name CC_MedicalIndications_ID */
    public static final String COLUMNNAME_CC_MedicalIndications_ID = "CC_MedicalIndications_ID";

	/** Set CC_MedicalIndications ID	  */
	public void setCC_MedicalIndications_ID (int CC_MedicalIndications_ID);

	/** Get CC_MedicalIndications ID	  */
	public int getCC_MedicalIndications_ID();

    /** Column name CC_MedicalOrder_ID */
    public static final String COLUMNNAME_CC_MedicalOrder_ID = "CC_MedicalOrder_ID";

	/** Set CC_MedicalOrder ID	  */
	public void setCC_MedicalOrder_ID (int CC_MedicalOrder_ID);

	/** Get CC_MedicalOrder ID	  */
	public int getCC_MedicalOrder_ID();

	public I_CC_MedicalOrder getCC_MedicalOrder() throws RuntimeException;

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

    /** Column name QtyCount */
    public static final String COLUMNNAME_QtyCount = "QtyCount";

	/** Set Quantity count.
	  * Counted Quantity
	  */
	public void setQtyCount (BigDecimal QtyCount);

	/** Get Quantity count.
	  * Counted Quantity
	  */
	public BigDecimal getQtyCount();

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

    /** Column name Signature1 */
    public static final String COLUMNNAME_Signature1 = "Signature1";

	/** Set Signature1	  */
	public void setSignature1 (boolean Signature1);

	/** Get Signature1	  */
	public boolean isSignature1();

    /** Column name Signature10 */
    public static final String COLUMNNAME_Signature10 = "Signature10";

	/** Set Signature10	  */
	public void setSignature10 (boolean Signature10);

	/** Get Signature10	  */
	public boolean isSignature10();

    /** Column name Signature11 */
    public static final String COLUMNNAME_Signature11 = "Signature11";

	/** Set Signature11	  */
	public void setSignature11 (boolean Signature11);

	/** Get Signature11	  */
	public boolean isSignature11();

    /** Column name Signature12 */
    public static final String COLUMNNAME_Signature12 = "Signature12";

	/** Set Signature12	  */
	public void setSignature12 (boolean Signature12);

	/** Get Signature12	  */
	public boolean isSignature12();

    /** Column name Signature13 */
    public static final String COLUMNNAME_Signature13 = "Signature13";

	/** Set Signature13	  */
	public void setSignature13 (boolean Signature13);

	/** Get Signature13	  */
	public boolean isSignature13();

    /** Column name Signature14 */
    public static final String COLUMNNAME_Signature14 = "Signature14";

	/** Set Signature14	  */
	public void setSignature14 (boolean Signature14);

	/** Get Signature14	  */
	public boolean isSignature14();

    /** Column name Signature15 */
    public static final String COLUMNNAME_Signature15 = "Signature15";

	/** Set Signature15	  */
	public void setSignature15 (boolean Signature15);

	/** Get Signature15	  */
	public boolean isSignature15();

    /** Column name Signature16 */
    public static final String COLUMNNAME_Signature16 = "Signature16";

	/** Set Signature16	  */
	public void setSignature16 (boolean Signature16);

	/** Get Signature16	  */
	public boolean isSignature16();

    /** Column name Signature17 */
    public static final String COLUMNNAME_Signature17 = "Signature17";

	/** Set Signature17	  */
	public void setSignature17 (boolean Signature17);

	/** Get Signature17	  */
	public boolean isSignature17();

    /** Column name Signature18 */
    public static final String COLUMNNAME_Signature18 = "Signature18";

	/** Set Signature18	  */
	public void setSignature18 (boolean Signature18);

	/** Get Signature18	  */
	public boolean isSignature18();

    /** Column name Signature19 */
    public static final String COLUMNNAME_Signature19 = "Signature19";

	/** Set Signature19	  */
	public void setSignature19 (boolean Signature19);

	/** Get Signature19	  */
	public boolean isSignature19();

    /** Column name Signature2 */
    public static final String COLUMNNAME_Signature2 = "Signature2";

	/** Set Signature2	  */
	public void setSignature2 (boolean Signature2);

	/** Get Signature2	  */
	public boolean isSignature2();

    /** Column name Signature20 */
    public static final String COLUMNNAME_Signature20 = "Signature20";

	/** Set Signature20	  */
	public void setSignature20 (boolean Signature20);

	/** Get Signature20	  */
	public boolean isSignature20();

    /** Column name Signature21 */
    public static final String COLUMNNAME_Signature21 = "Signature21";

	/** Set Signature21	  */
	public void setSignature21 (boolean Signature21);

	/** Get Signature21	  */
	public boolean isSignature21();

    /** Column name Signature22 */
    public static final String COLUMNNAME_Signature22 = "Signature22";

	/** Set Signature22	  */
	public void setSignature22 (boolean Signature22);

	/** Get Signature22	  */
	public boolean isSignature22();

    /** Column name Signature23 */
    public static final String COLUMNNAME_Signature23 = "Signature23";

	/** Set Signature23	  */
	public void setSignature23 (boolean Signature23);

	/** Get Signature23	  */
	public boolean isSignature23();

    /** Column name Signature24 */
    public static final String COLUMNNAME_Signature24 = "Signature24";

	/** Set Signature24	  */
	public void setSignature24 (boolean Signature24);

	/** Get Signature24	  */
	public boolean isSignature24();

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

    /** Column name Signature7 */
    public static final String COLUMNNAME_Signature7 = "Signature7";

	/** Set Signature7	  */
	public void setSignature7 (boolean Signature7);

	/** Get Signature7	  */
	public boolean isSignature7();

    /** Column name Signature8 */
    public static final String COLUMNNAME_Signature8 = "Signature8";

	/** Set Signature8	  */
	public void setSignature8 (boolean Signature8);

	/** Get Signature8	  */
	public boolean isSignature8();

    /** Column name Signature9 */
    public static final String COLUMNNAME_Signature9 = "Signature9";

	/** Set Signature9	  */
	public void setSignature9 (boolean Signature9);

	/** Get Signature9	  */
	public boolean isSignature9();

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
