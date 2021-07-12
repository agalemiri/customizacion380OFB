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
import org.compiere.util.KeyNamePair;

/** Generated Model for HR_Contract
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_HR_Contract extends PO implements I_HR_Contract, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20181029L;

    /** Standard Constructor */
    public X_HR_Contract (Properties ctx, int HR_Contract_ID, String trxName)
    {
      super (ctx, HR_Contract_ID, trxName);
      /** if (HR_Contract_ID == 0)
        {
			setHR_Contract_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_HR_Contract (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_Contract[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AD_OrgRef_ID.
		@param AD_OrgRef_ID AD_OrgRef_ID	  */
	public void setAD_OrgRef_ID (int AD_OrgRef_ID)
	{
		if (AD_OrgRef_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgRef_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgRef_ID, Integer.valueOf(AD_OrgRef_ID));
	}

	/** Get AD_OrgRef_ID.
		@return AD_OrgRef_ID	  */
	public int getAD_OrgRef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgRef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_OrgRef2_ID.
		@param AD_OrgRef2_ID AD_OrgRef2_ID	  */
	public void setAD_OrgRef2_ID (int AD_OrgRef2_ID)
	{
		if (AD_OrgRef2_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgRef2_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgRef2_ID, Integer.valueOf(AD_OrgRef2_ID));
	}

	/** Get AD_OrgRef2_ID.
		@return AD_OrgRef2_ID	  */
	public int getAD_OrgRef2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgRef2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_C_Campaign getC_Campaign() throws RuntimeException
    {
		return (org.compiere.model.I_C_Campaign)MTable.get(getCtx(), org.compiere.model.I_C_Campaign.Table_Name)
			.getPO(getC_Campaign_ID(), get_TrxName());	}

	/** Set Campaign.
		@param C_Campaign_ID 
		Marketing Campaign
	  */
	public void setC_Campaign_ID (int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
	}

	/** Get Campaign.
		@return Marketing Campaign
	  */
	public int getC_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException
    {
		return (org.compiere.model.I_C_Project)MTable.get(getCtx(), org.compiere.model.I_C_Project.Table_Name)
			.getPO(getC_Project_ID(), get_TrxName());	}

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** CausalTerm AD_Reference_ID=2000228 */
	public static final int CAUSALTERM_AD_Reference_ID=2000228;
	/** Aceptación renuncia = 01 */
	public static final String CAUSALTERM_AceptacionRenuncia = "01";
	/** Obtención de jubilación, pensión o renta vitalicia en un régimen previsional = 02 */
	public static final String CAUSALTERM_ObtencionDeJubilacionPensionORentaVitaliciaEnUnRegimenPrevisional = "02";
	/** Declaración de vacancia = 03 */
	public static final String CAUSALTERM_DeclaracionDeVacancia = "03";
	/** Destitución = 04 */
	public static final String CAUSALTERM_Destitucion = "04";
	/** Supresión de empleo = 05 */
	public static final String CAUSALTERM_SupresionDeEmpleo = "05";
	/** Termino del periodo legal por el cual se es designado = 06 */
	public static final String CAUSALTERM_TerminoDelPeriodoLegalPorElCualSeEsDesignado = "06";
	/** Fallecimiento = 07 */
	public static final String CAUSALTERM_Fallecimiento = "07";
	/** Set CausalTerm.
		@param CausalTerm CausalTerm	  */
	public void setCausalTerm (String CausalTerm)
	{

		set_Value (COLUMNNAME_CausalTerm, CausalTerm);
	}

	/** Get CausalTerm.
		@return CausalTerm	  */
	public String getCausalTerm () 
	{
		return (String)get_Value(COLUMNNAME_CausalTerm);
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

	/** EmployeeStatus AD_Reference_ID=53617 */
	public static final int EMPLOYEESTATUS_AD_Reference_ID=53617;
	/** Without Reason = 00 */
	public static final String EMPLOYEESTATUS_WithoutReason = "00";
	/** On Leave = 01 */
	public static final String EMPLOYEESTATUS_OnLeave = "01";
	/** Left Service = 02 */
	public static final String EMPLOYEESTATUS_LeftService = "02";
	/** Retired = 03 */
	public static final String EMPLOYEESTATUS_Retired = "03";
	/** Expired = 05 */
	public static final String EMPLOYEESTATUS_Expired = "05";
	/** Non Implemented Area = 06 */
	public static final String EMPLOYEESTATUS_NonImplementedArea = "06";
	/** Compliance by Immediate Ex = 07 */
	public static final String EMPLOYEESTATUS_ComplianceByImmediateEx = "07";
	/** Suspension of work = 08 */
	public static final String EMPLOYEESTATUS_SuspensionOfWork = "08";
	/** Strike/Lockout = 09 */
	public static final String EMPLOYEESTATUS_StrikeLockout = "09";
	/** Retrenchment = 10 */
	public static final String EMPLOYEESTATUS_Retrenchment = "10";
	/** No Work = 11 */
	public static final String EMPLOYEESTATUS_NoWork = "11";
	/** Doesnt Belong To This Employee = 12 */
	public static final String EMPLOYEESTATUS_DoesntBelongToThisEmployee = "12";
	/** Active = 13 */
	public static final String EMPLOYEESTATUS_Active = "13";
	/** Out of Coverage = OC */
	public static final String EMPLOYEESTATUS_OutOfCoverage = "OC";
	/** Set Employee Status.
		@param EmployeeStatus Employee Status	  */
	public void setEmployeeStatus (String EmployeeStatus)
	{

		set_Value (COLUMNNAME_EmployeeStatus, EmployeeStatus);
	}

	/** Get Employee Status.
		@return Employee Status	  */
	public String getEmployeeStatus () 
	{
		return (String)get_Value(COLUMNNAME_EmployeeStatus);
	}

	/** grade AD_Reference_ID=2000138 */
	public static final int GRADE_AD_Reference_ID=2000138;
	/** 1 = 01 */
	public static final String GRADE_1 = "01";
	/** 2 = 02 */
	public static final String GRADE_2 = "02";
	/** 3 = 03 */
	public static final String GRADE_3 = "03";
	/** 4 = 04 */
	public static final String GRADE_4 = "04";
	/** 5 = 05 */
	public static final String GRADE_5 = "05";
	/** 6 = 06 */
	public static final String GRADE_6 = "06";
	/** 7 = 07 */
	public static final String GRADE_7 = "07";
	/** 8 = 08 */
	public static final String GRADE_8 = "08";
	/** 9 = 09 */
	public static final String GRADE_9 = "09";
	/** 10 = 10 */
	public static final String GRADE_10 = "10";
	/** 11 = 11 */
	public static final String GRADE_11 = "11";
	/** 12 = 12 */
	public static final String GRADE_12 = "12";
	/** 13 = 13 */
	public static final String GRADE_13 = "13";
	/** 14 = 14 */
	public static final String GRADE_14 = "14";
	/** 15 = 15 */
	public static final String GRADE_15 = "15";
	/** 16 = 16 */
	public static final String GRADE_16 = "16";
	/** 17 = 17 */
	public static final String GRADE_17 = "17";
	/** 18 = 18 */
	public static final String GRADE_18 = "18";
	/** 19 = 19 */
	public static final String GRADE_19 = "19";
	/** 20 = 20 */
	public static final String GRADE_20 = "20";
	/** 21 = 21 */
	public static final String GRADE_21 = "21";
	/** 22 = 22 */
	public static final String GRADE_22 = "22";
	/** 23 = 23 */
	public static final String GRADE_23 = "23";
	/** 24 = 24 */
	public static final String GRADE_24 = "24";
	/** 25 = 25 */
	public static final String GRADE_25 = "25";
	/** 26 = 26 */
	public static final String GRADE_26 = "26";
	/** 27 = 27 */
	public static final String GRADE_27 = "27";
	/** 28 = 28 */
	public static final String GRADE_28 = "28";
	/** 29 = 29 */
	public static final String GRADE_29 = "29";
	/** 30 = 30 */
	public static final String GRADE_30 = "30";
	/** 1A = 1A */
	public static final String GRADE_1A = "1A";
	/** 1B = 1B */
	public static final String GRADE_1B = "1B";
	/** 1C = 1C */
	public static final String GRADE_1C = "1C";
	/** A = 0A */
	public static final String GRADE_A = "0A";
	/** B = 0B */
	public static final String GRADE_B = "0B";
	/** C = 0C */
	public static final String GRADE_C = "0C";
	/** 31 = 31 */
	public static final String GRADE_31 = "31";
	/** Set grade.
		@param grade grade	  */
	public void setgrade (String grade)
	{

		set_Value (COLUMNNAME_grade, grade);
	}

	/** Get grade.
		@return grade	  */
	public String getgrade () 
	{
		return (String)get_Value(COLUMNNAME_grade);
	}

	/** gradeRef AD_Reference_ID=2000138 */
	public static final int GRADEREF_AD_Reference_ID=2000138;
	/** 1 = 01 */
	public static final String GRADEREF_1 = "01";
	/** 2 = 02 */
	public static final String GRADEREF_2 = "02";
	/** 3 = 03 */
	public static final String GRADEREF_3 = "03";
	/** 4 = 04 */
	public static final String GRADEREF_4 = "04";
	/** 5 = 05 */
	public static final String GRADEREF_5 = "05";
	/** 6 = 06 */
	public static final String GRADEREF_6 = "06";
	/** 7 = 07 */
	public static final String GRADEREF_7 = "07";
	/** 8 = 08 */
	public static final String GRADEREF_8 = "08";
	/** 9 = 09 */
	public static final String GRADEREF_9 = "09";
	/** 10 = 10 */
	public static final String GRADEREF_10 = "10";
	/** 11 = 11 */
	public static final String GRADEREF_11 = "11";
	/** 12 = 12 */
	public static final String GRADEREF_12 = "12";
	/** 13 = 13 */
	public static final String GRADEREF_13 = "13";
	/** 14 = 14 */
	public static final String GRADEREF_14 = "14";
	/** 15 = 15 */
	public static final String GRADEREF_15 = "15";
	/** 16 = 16 */
	public static final String GRADEREF_16 = "16";
	/** 17 = 17 */
	public static final String GRADEREF_17 = "17";
	/** 18 = 18 */
	public static final String GRADEREF_18 = "18";
	/** 19 = 19 */
	public static final String GRADEREF_19 = "19";
	/** 20 = 20 */
	public static final String GRADEREF_20 = "20";
	/** 21 = 21 */
	public static final String GRADEREF_21 = "21";
	/** 22 = 22 */
	public static final String GRADEREF_22 = "22";
	/** 23 = 23 */
	public static final String GRADEREF_23 = "23";
	/** 24 = 24 */
	public static final String GRADEREF_24 = "24";
	/** 25 = 25 */
	public static final String GRADEREF_25 = "25";
	/** 26 = 26 */
	public static final String GRADEREF_26 = "26";
	/** 27 = 27 */
	public static final String GRADEREF_27 = "27";
	/** 28 = 28 */
	public static final String GRADEREF_28 = "28";
	/** 29 = 29 */
	public static final String GRADEREF_29 = "29";
	/** 30 = 30 */
	public static final String GRADEREF_30 = "30";
	/** 1A = 1A */
	public static final String GRADEREF_1A = "1A";
	/** 1B = 1B */
	public static final String GRADEREF_1B = "1B";
	/** 1C = 1C */
	public static final String GRADEREF_1C = "1C";
	/** A = 0A */
	public static final String GRADEREF_A = "0A";
	/** B = 0B */
	public static final String GRADEREF_B = "0B";
	/** C = 0C */
	public static final String GRADEREF_C = "0C";
	/** 31 = 31 */
	public static final String GRADEREF_31 = "31";
	/** Set gradeRef.
		@param gradeRef gradeRef	  */
	public void setgradeRef (String gradeRef)
	{

		set_Value (COLUMNNAME_gradeRef, gradeRef);
	}

	/** Get gradeRef.
		@return gradeRef	  */
	public String getgradeRef () 
	{
		return (String)get_Value(COLUMNNAME_gradeRef);
	}

	/** Set Payroll Contract.
		@param HR_Contract_ID Payroll Contract	  */
	public void setHR_Contract_ID (int HR_Contract_ID)
	{
		if (HR_Contract_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_Contract_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_Contract_ID, Integer.valueOf(HR_Contract_ID));
	}

	/** Get Payroll Contract.
		@return Payroll Contract	  */
	public int getHR_Contract_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Contract_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_HR_Job getHR_Job() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Job)MTable.get(getCtx(), org.eevolution.model.I_HR_Job.Table_Name)
			.getPO(getHR_Job_ID(), get_TrxName());	}

	/** Set Payroll Job.
		@param HR_Job_ID Payroll Job	  */
	public void setHR_Job_ID (int HR_Job_ID)
	{
		if (HR_Job_ID < 1) 
			set_Value (COLUMNNAME_HR_Job_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Job_ID, Integer.valueOf(HR_Job_ID));
	}

	/** Get Payroll Job.
		@return Payroll Job	  */
	public int getHR_Job_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Job_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_HR_Job getHR_JobRef() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Job)MTable.get(getCtx(), org.eevolution.model.I_HR_Job.Table_Name)
			.getPO(getHR_JobRef_ID(), get_TrxName());	}

	/** Set HR_JobRef_ID.
		@param HR_JobRef_ID HR_JobRef_ID	  */
	public void setHR_JobRef_ID (int HR_JobRef_ID)
	{
		if (HR_JobRef_ID < 1) 
			set_Value (COLUMNNAME_HR_JobRef_ID, null);
		else 
			set_Value (COLUMNNAME_HR_JobRef_ID, Integer.valueOf(HR_JobRef_ID));
	}

	/** Get HR_JobRef_ID.
		@return HR_JobRef_ID	  */
	public int getHR_JobRef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_JobRef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Net Days.
		@param NetDays 
		Net Days in which payment is due
	  */
	public void setNetDays (int NetDays)
	{
		set_Value (COLUMNNAME_NetDays, Integer.valueOf(NetDays));
	}

	/** Get Net Days.
		@return Net Days in which payment is due
	  */
	public int getNetDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NetDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Suplencia AD_Reference_ID=2000027 */
	public static final int SUPLENCIA_AD_Reference_ID=2000027;
	/** Planta Titular = 01 */
	public static final String SUPLENCIA_PlantaTitular = "01";
	/** Contrata = 02 */
	public static final String SUPLENCIA_Contrata = "02";
	/** Reemplazo Contrata = 03 */
	public static final String SUPLENCIA_ReemplazoContrata = "03";
	/** Honorario = 04 */
	public static final String SUPLENCIA_Honorario = "04";
	/** Directivo = 05 */
	public static final String SUPLENCIA_Directivo = "05";
	/** Suplente Interno = 06 */
	public static final String SUPLENCIA_SuplenteInterno = "06";
	/** Suplente Externo = 07 */
	public static final String SUPLENCIA_SuplenteExterno = "07";
	/** Contrata Art.87 = 08 */
	public static final String SUPLENCIA_ContrataArt87 = "08";
	/** Set Suplencia.
		@param Suplencia Suplencia	  */
	public void setSuplencia (String Suplencia)
	{

		set_Value (COLUMNNAME_Suplencia, Suplencia);
	}

	/** Get Suplencia.
		@return Suplencia	  */
	public String getSuplencia () 
	{
		return (String)get_Value(COLUMNNAME_Suplencia);
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}