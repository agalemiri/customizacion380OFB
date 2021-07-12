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
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for CC_Other_Indications
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_Other_Indications extends PO implements I_CC_Other_Indications, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190806L;

    /** Standard Constructor */
    public X_CC_Other_Indications (Properties ctx, int CC_Other_Indications_ID, String trxName)
    {
      super (ctx, CC_Other_Indications_ID, trxName);
      /** if (CC_Other_Indications_ID == 0)
        {
			setCC_Other_Indications_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CC_Other_Indications (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_Other_Indications[")
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

	/** Set CC_Other_Indications ID.
		@param CC_Other_Indications_ID CC_Other_Indications ID	  */
	public void setCC_Other_Indications_ID (int CC_Other_Indications_ID)
	{
		if (CC_Other_Indications_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_Other_Indications_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_Other_Indications_ID, Integer.valueOf(CC_Other_Indications_ID));
	}

	/** Get CC_Other_Indications ID.
		@return CC_Other_Indications ID	  */
	public int getCC_Other_Indications_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_Other_Indications_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	//public static final String DELIVERYVIARULE_Subcutánea = "04";
	/** Intradérmica = 05 */
	//public static final String DELIVERYVIARULE_Intradérmica = "05";
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
	//public static final String DELIVERYVIARULE_Tópica = "11";
	/** Nebulización = 12 */
	//public static final String DELIVERYVIARULE_Nebulización = "12";
	/** Intramuscular = 13 */
	public static final String DELIVERYVIARULE_Intramuscular = "13";
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
	/** Régimen Liviano = R01 */
	//public static final String DIETFOOD_RégimenLiviano = "R01";
	/** Liviano Diabético Hiposódico  = R02 */
	//public static final String DIETFOOD_LivianoDiabéticoHiposódico = "R02";
	/** Liviano Hiposódico = R03 */
	//public static final String DIETFOOD_LivianoHiposódico = "R03";
	/** Liviano Diabético Normosódico  = R04 */
	//public static final String DIETFOOD_LivianoDiabéticoNormosódico = "R04";
	/** Común = R05 */
	public static final String DIETFOOD_Común = "R05";
	/** Papilla = R14 */
	public static final String DIETFOOD_Papilla = "R14";
	/** Papilla Hiposódica = R07 */
	//public static final String DIETFOOD_PapillaHiposódica = "R07";
	/** Papilla Diabética Hiposódica = R08 */
	//public static final String DIETFOOD_PapillaDiabéticaHiposódica = "R08";
	/** Líquido = R09 */
	public static final String DIETFOOD_Líquido = "R09";
	/** Cero = R10 */
	public static final String DIETFOOD_Cero = "R10";
	/** Hídrico = R11 */
	public static final String DIETFOOD_Hídrico = "R11";
	/** Blando Hiposódico = R12 */
	//public static final String DIETFOOD_BlandoHiposódico = "R12";
	/** Blando Diabético Hiposódico = R13 */
	//public static final String DIETFOOD_BlandoDiabéticoHiposódico = "R13";
	/** Enteral = R15 */
	public static final String DIETFOOD_Enteral = "R15";
	/** Común Hiposódico = R06 */
	//public static final String DIETFOOD_ComúnHiposódico = "R06";
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
	//public static final String INDICATIONTYPE_Exámen = "02";
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

	/** Set Quantity Plan.
		@param QtyPlan 
		Planned Quantity
	  */
	public void setQtyPlan (BigDecimal QtyPlan)
	{
		set_Value (COLUMNNAME_QtyPlan, QtyPlan);
	}

	/** Get Quantity Plan.
		@return Planned Quantity
	  */
	public BigDecimal getQtyPlan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPlan);
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
}