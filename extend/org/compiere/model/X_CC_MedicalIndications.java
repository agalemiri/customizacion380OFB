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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for CC_MedicalIndications
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_MedicalIndications extends PO implements I_CC_MedicalIndications, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170704L;

    /** Standard Constructor */
    public X_CC_MedicalIndications (Properties ctx, int CC_MedicalIndications_ID, String trxName)
    {
      super (ctx, CC_MedicalIndications_ID, trxName);
      /** if (CC_MedicalIndications_ID == 0)
        {
			setCC_MedicalIndications_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setSignature1 (false);
// N
			setSignature10 (false);
// N
			setSignature11 (false);
// N
			setSignature12 (false);
// N
			setSignature13 (false);
// N
			setSignature14 (false);
// N
			setSignature15 (false);
// N
			setSignature16 (false);
// N
			setSignature17 (false);
// N
			setSignature18 (false);
// N
			setSignature19 (false);
// N
			setSignature2 (false);
// N
			setSignature20 (false);
// N
			setSignature21 (false);
// N
			setSignature22 (false);
// N
			setSignature23 (false);
// N
			setSignature24 (false);
// N
			setSignature3 (false);
// N
			setSignature4 (false);
// N
			setSignature5 (false);
// N
			setSignature6 (false);
// N
			setSignature7 (false);
// N
			setSignature8 (false);
// N
			setSignature9 (false);
// N
        } */
    }

    /** Load Constructor */
    public X_CC_MedicalIndications (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_MedicalIndications[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
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

	/** Set CC_MedicalIndications ID.
		@param CC_MedicalIndications_ID CC_MedicalIndications ID	  */
	public void setCC_MedicalIndications_ID (int CC_MedicalIndications_ID)
	{
		if (CC_MedicalIndications_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_MedicalIndications_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_MedicalIndications_ID, Integer.valueOf(CC_MedicalIndications_ID));
	}

	/** Get CC_MedicalIndications ID.
		@return CC_MedicalIndications ID	  */
	public int getCC_MedicalIndications_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_MedicalIndications_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CC_MedicalOrder getCC_MedicalOrder() throws RuntimeException
    {
		return (I_CC_MedicalOrder)MTable.get(getCtx(), I_CC_MedicalOrder.Table_Name)
			.getPO(getCC_MedicalOrder_ID(), get_TrxName());	}

	/** Set CC_MedicalOrder ID.
		@param CC_MedicalOrder_ID CC_MedicalOrder ID	  */
	public void setCC_MedicalOrder_ID (int CC_MedicalOrder_ID)
	{
		if (CC_MedicalOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_MedicalOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_MedicalOrder_ID, Integer.valueOf(CC_MedicalOrder_ID));
	}

	/** Get CC_MedicalOrder ID.
		@return CC_MedicalOrder ID	  */
	public int getCC_MedicalOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_MedicalOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** DeliveryViaRule AD_Reference_ID=2000031 */
	public static final int DELIVERYVIARULE_AD_Reference_ID=2000031;
	/** Oral = 01 */
	public static final String DELIVERYVIARULE_Oral = "01";
	/** Enteral = 02 */
	public static final String DELIVERYVIARULE_Enteral = "02";
	/** Endovenosa = 03 */
	public static final String DELIVERYVIARULE_Endovenosa = "03";
	/** Subcutánea = 04 */
	public static final String DELIVERYVIARULE_Subcutanea = "04";
	/** Intradérmica = 05 */
	public static final String DELIVERYVIARULE_Intradermica = "05";
	/** Rectal = 06 */
	public static final String DELIVERYVIARULE_Rectal = "06";
	/** Vaginal = 07 */
	public static final String DELIVERYVIARULE_Vaginal = "07";
	/** Nasal = 08 */
	public static final String DELIVERYVIARULE_Nasal = "08";
	/** Otica = 09 */
	public static final String DELIVERYVIARULE_Otica = "09";
	/** Ocular = 10 */
	public static final String DELIVERYVIARULE_Ocular = "10";
	/** Tópica = 11 */
	public static final String DELIVERYVIARULE_Topica = "11";
	/** Set Delivery Via.
		@param DeliveryViaRule 
		How the order will be delivered
	  */
	public void setDeliveryViaRule (String DeliveryViaRule)
	{

		set_Value (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	/** Get Delivery Via.
		@return How the order will be delivered
	  */
	public String getDeliveryViaRule () 
	{
		return (String)get_Value(COLUMNNAME_DeliveryViaRule);
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

	/** DietFood AD_Reference_ID=2000070 */
	public static final int DIETFOOD_AD_Reference_ID=2000070;
	/** Regimen Liviano = R01 */
	public static final String DIETFOOD_RegimenLiviano = "R01";
	/** Liviano Hiposodico Diabetico = R02 */
	public static final String DIETFOOD_LivianoHiposodicoDiabetico = "R02";
	/** Liviano Hiposodico Papilla = R03 */
	public static final String DIETFOOD_LivianoHiposodicoPapilla = "R03";
	/** Liviano Normosodico Diabetico = R04 */
	public static final String DIETFOOD_LivianoNormosodicoDiabetico = "R04";
	/** Normal = R05 */
	public static final String DIETFOOD_Normal = "R05";
	/** Set DietFood.
		@param DietFood DietFood	  */
	public void setDietFood (String DietFood)
	{

		set_Value (COLUMNNAME_DietFood, DietFood);
	}

	/** Get DietFood.
		@return DietFood	  */
	public String getDietFood () 
	{
		return (String)get_Value(COLUMNNAME_DietFood);
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

	/** Set Exam.
		@param Exam Exam	  */
	public void setExam (String Exam)
	{
		set_Value (COLUMNNAME_Exam, Exam);
	}

	/** Get Exam.
		@return Exam	  */
	public String getExam () 
	{
		return (String)get_Value(COLUMNNAME_Exam);
	}

	/** IndicationType AD_Reference_ID=2000034 */
	public static final int INDICATIONTYPE_AD_Reference_ID=2000034;
	/** Medicamento = 01 */
	public static final String INDICATIONTYPE_Medicamento = "01";
	/** Exámen = 02 */
	public static final String INDICATIONTYPE_Examen = "02";
	/** Regimen Nutricional = 03 */
	public static final String INDICATIONTYPE_RegimenNutricional = "03";
	/** Reposo = 04 */
	public static final String INDICATIONTYPE_Reposo = "04";
	/** Terapia = 05 */
	public static final String INDICATIONTYPE_Terapia = "05";
	/** Otras = 06 */
	public static final String INDICATIONTYPE_Otras = "06";
	/** Set IndicationType.
		@param IndicationType IndicationType	  */
	public void setIndicationType (String IndicationType)
	{

		set_Value (COLUMNNAME_IndicationType, IndicationType);
	}

	/** Get IndicationType.
		@return IndicationType	  */
	public String getIndicationType () 
	{
		return (String)get_Value(COLUMNNAME_IndicationType);
	}

	public I_C_ValidCombination getKinesiologicalTher() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getKinesiologicalTherapy(), get_TrxName());	}

	/** Set KinesiologicalTherapy.
		@param KinesiologicalTherapy KinesiologicalTherapy	  */
	public void setKinesiologicalTherapy (int KinesiologicalTherapy)
	{
		set_Value (COLUMNNAME_KinesiologicalTherapy, Integer.valueOf(KinesiologicalTherapy));
	}

	/** Get KinesiologicalTherapy.
		@return KinesiologicalTherapy	  */
	public int getKinesiologicalTherapy () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_KinesiologicalTherapy);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Medicines_Status AD_Reference_ID=2000102 */
	public static final int MEDICINES_STATUS_AD_Reference_ID=2000102;
	/** NO - SUSPENDIDO = 01 */
	public static final String MEDICINES_STATUS_NO_SUSPENDIDO = "01";
	/** SUSPENDIDO = 02 */
	public static final String MEDICINES_STATUS_SUSPENDIDO = "02";
	/** Set Medicines_Status.
		@param Medicines_Status Medicines_Status	  */
	public void setMedicines_Status (String Medicines_Status)
	{

		set_Value (COLUMNNAME_Medicines_Status, Medicines_Status);
	}

	/** Get Medicines_Status.
		@return Medicines_Status	  */
	public String getMedicines_Status () 
	{
		return (String)get_Value(COLUMNNAME_Medicines_Status);
	}

	/** Set Number of Periods.
		@param NoPeriods Number of Periods	  */
	public void setNoPeriods (String NoPeriods)
	{
		set_Value (COLUMNNAME_NoPeriods, NoPeriods);
	}

	/** Get Number of Periods.
		@return Number of Periods	  */
	public String getNoPeriods () 
	{
		return (String)get_Value(COLUMNNAME_NoPeriods);
	}

	/** Set OtherIndications.
		@param OtherIndications OtherIndications	  */
	public void setOtherIndications (String OtherIndications)
	{
		set_Value (COLUMNNAME_OtherIndications, OtherIndications);
	}

	/** Get OtherIndications.
		@return OtherIndications	  */
	public String getOtherIndications () 
	{
		return (String)get_Value(COLUMNNAME_OtherIndications);
	}

	/** PrintFormatType AD_Reference_ID=255 */
	public static final int PRINTFORMATTYPE_AD_Reference_ID=255;
	/** Field = F */
	public static final String PRINTFORMATTYPE_Field = "F";
	/** Text = T */
	public static final String PRINTFORMATTYPE_Text = "T";
	/** Print Format = P */
	public static final String PRINTFORMATTYPE_PrintFormat = "P";
	/** Image = I */
	public static final String PRINTFORMATTYPE_Image = "I";
	/** Rectangle = R */
	public static final String PRINTFORMATTYPE_Rectangle = "R";
	/** Line = L */
	public static final String PRINTFORMATTYPE_Line = "L";
	/** Set Format Type.
		@param PrintFormatType 
		Print Format Type
	  */
	public void setPrintFormatType (String PrintFormatType)
	{

		set_Value (COLUMNNAME_PrintFormatType, PrintFormatType);
	}

	/** Get Format Type.
		@return Print Format Type
	  */
	public String getPrintFormatType () 
	{
		return (String)get_Value(COLUMNNAME_PrintFormatType);
	}

	/** Procedimientos AD_Reference_ID=2000093 */
	public static final int PROCEDIMIENTOS_AD_Reference_ID=2000093;
	/** prueba 1 = 01 */
	public static final String PROCEDIMIENTOS_Prueba1 = "01";
	/** Set Procedimientos.
		@param Procedimientos Procedimientos	  */
	public void setProcedimientos (String Procedimientos)
	{

		set_Value (COLUMNNAME_Procedimientos, Procedimientos);
	}

	/** Get Procedimientos.
		@return Procedimientos	  */
	public String getProcedimientos () 
	{
		return (String)get_Value(COLUMNNAME_Procedimientos);
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

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity count.
		@param QtyCount 
		Counted Quantity
	  */
	public void setQtyCount (BigDecimal QtyCount)
	{
		set_Value (COLUMNNAME_QtyCount, QtyCount);
	}

	/** Get Quantity count.
		@return Counted Quantity
	  */
	public BigDecimal getQtyCount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyCount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Repose AD_Reference_ID=2000035 */
	public static final int REPOSE_AD_Reference_ID=2000035;
	/** Absoluto = 01 */
	public static final String REPOSE_Absoluto = "01";
	/** Relativo = 02 */
	public static final String REPOSE_Relativo = "02";
	/** Levantar a Sillon = 03 */
	public static final String REPOSE_LevantarASillon = "03";
	/** Set Repose.
		@param Repose Repose	  */
	public void setRepose (String Repose)
	{

		set_Value (COLUMNNAME_Repose, Repose);
	}

	/** Get Repose.
		@return Repose	  */
	public String getRepose () 
	{
		return (String)get_Value(COLUMNNAME_Repose);
	}

	/** Schedules1 AD_Reference_ID=2000098 */
	public static final int SCHEDULES1_AD_Reference_ID=2000098;
	/** Medication Schedules = 01 */
	public static final String SCHEDULES1_MedicationSchedules = "01";
	/** Other Indications Times = 02 */
	public static final String SCHEDULES1_OtherIndicationsTimes = "02";
	/** Nursing Activities Schedules = 03 */
	public static final String SCHEDULES1_NursingActivitiesSchedules = "03";
	/** Set Schedules1.
		@param Schedules1 Schedules1	  */
	public void setSchedules1 (String Schedules1)
	{

		set_Value (COLUMNNAME_Schedules1, Schedules1);
	}

	/** Get Schedules1.
		@return Schedules1	  */
	public String getSchedules1 () 
	{
		return (String)get_Value(COLUMNNAME_Schedules1);
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

	/** Set Signature10.
		@param Signature10 Signature10	  */
	public void setSignature10 (boolean Signature10)
	{
		set_Value (COLUMNNAME_Signature10, Boolean.valueOf(Signature10));
	}

	/** Get Signature10.
		@return Signature10	  */
	public boolean isSignature10 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature10);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature11.
		@param Signature11 Signature11	  */
	public void setSignature11 (boolean Signature11)
	{
		set_Value (COLUMNNAME_Signature11, Boolean.valueOf(Signature11));
	}

	/** Get Signature11.
		@return Signature11	  */
	public boolean isSignature11 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature11);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature12.
		@param Signature12 Signature12	  */
	public void setSignature12 (boolean Signature12)
	{
		set_Value (COLUMNNAME_Signature12, Boolean.valueOf(Signature12));
	}

	/** Get Signature12.
		@return Signature12	  */
	public boolean isSignature12 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature12);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature13.
		@param Signature13 Signature13	  */
	public void setSignature13 (boolean Signature13)
	{
		set_Value (COLUMNNAME_Signature13, Boolean.valueOf(Signature13));
	}

	/** Get Signature13.
		@return Signature13	  */
	public boolean isSignature13 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature13);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature14.
		@param Signature14 Signature14	  */
	public void setSignature14 (boolean Signature14)
	{
		set_Value (COLUMNNAME_Signature14, Boolean.valueOf(Signature14));
	}

	/** Get Signature14.
		@return Signature14	  */
	public boolean isSignature14 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature14);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature15.
		@param Signature15 Signature15	  */
	public void setSignature15 (boolean Signature15)
	{
		set_Value (COLUMNNAME_Signature15, Boolean.valueOf(Signature15));
	}

	/** Get Signature15.
		@return Signature15	  */
	public boolean isSignature15 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature15);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature16.
		@param Signature16 Signature16	  */
	public void setSignature16 (boolean Signature16)
	{
		set_Value (COLUMNNAME_Signature16, Boolean.valueOf(Signature16));
	}

	/** Get Signature16.
		@return Signature16	  */
	public boolean isSignature16 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature16);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature17.
		@param Signature17 Signature17	  */
	public void setSignature17 (boolean Signature17)
	{
		set_Value (COLUMNNAME_Signature17, Boolean.valueOf(Signature17));
	}

	/** Get Signature17.
		@return Signature17	  */
	public boolean isSignature17 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature17);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature18.
		@param Signature18 Signature18	  */
	public void setSignature18 (boolean Signature18)
	{
		set_Value (COLUMNNAME_Signature18, Boolean.valueOf(Signature18));
	}

	/** Get Signature18.
		@return Signature18	  */
	public boolean isSignature18 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature18);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature19.
		@param Signature19 Signature19	  */
	public void setSignature19 (boolean Signature19)
	{
		set_Value (COLUMNNAME_Signature19, Boolean.valueOf(Signature19));
	}

	/** Get Signature19.
		@return Signature19	  */
	public boolean isSignature19 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature19);
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

	/** Set Signature20.
		@param Signature20 Signature20	  */
	public void setSignature20 (boolean Signature20)
	{
		set_Value (COLUMNNAME_Signature20, Boolean.valueOf(Signature20));
	}

	/** Get Signature20.
		@return Signature20	  */
	public boolean isSignature20 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature20);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature21.
		@param Signature21 Signature21	  */
	public void setSignature21 (boolean Signature21)
	{
		set_Value (COLUMNNAME_Signature21, Boolean.valueOf(Signature21));
	}

	/** Get Signature21.
		@return Signature21	  */
	public boolean isSignature21 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature21);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature22.
		@param Signature22 Signature22	  */
	public void setSignature22 (boolean Signature22)
	{
		set_Value (COLUMNNAME_Signature22, Boolean.valueOf(Signature22));
	}

	/** Get Signature22.
		@return Signature22	  */
	public boolean isSignature22 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature22);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature23.
		@param Signature23 Signature23	  */
	public void setSignature23 (boolean Signature23)
	{
		set_Value (COLUMNNAME_Signature23, Boolean.valueOf(Signature23));
	}

	/** Get Signature23.
		@return Signature23	  */
	public boolean isSignature23 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature23);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature24.
		@param Signature24 Signature24	  */
	public void setSignature24 (boolean Signature24)
	{
		set_Value (COLUMNNAME_Signature24, Boolean.valueOf(Signature24));
	}

	/** Get Signature24.
		@return Signature24	  */
	public boolean isSignature24 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature24);
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

	/** Set Signature7.
		@param Signature7 Signature7	  */
	public void setSignature7 (boolean Signature7)
	{
		set_Value (COLUMNNAME_Signature7, Boolean.valueOf(Signature7));
	}

	/** Get Signature7.
		@return Signature7	  */
	public boolean isSignature7 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature7);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature8.
		@param Signature8 Signature8	  */
	public void setSignature8 (boolean Signature8)
	{
		set_Value (COLUMNNAME_Signature8, Boolean.valueOf(Signature8));
	}

	/** Get Signature8.
		@return Signature8	  */
	public boolean isSignature8 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature8);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Signature9.
		@param Signature9 Signature9	  */
	public void setSignature9 (boolean Signature9)
	{
		set_Value (COLUMNNAME_Signature9, Boolean.valueOf(Signature9));
	}

	/** Get Signature9.
		@return Signature9	  */
	public boolean isSignature9 () 
	{
		Object oo = get_Value(COLUMNNAME_Signature9);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}