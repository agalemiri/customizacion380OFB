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
import org.compiere.util.KeyNamePair;

/** Generated Model for C_SubProjectOFB
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_C_SubProjectOFB extends PO implements I_C_SubProjectOFB, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170801L;

    /** Standard Constructor */
    public X_C_SubProjectOFB (Properties ctx, int C_SubProjectOFB_ID, String trxName)
    {
      super (ctx, C_SubProjectOFB_ID, trxName);
      /** if (C_SubProjectOFB_ID == 0)
        {
			setC_SubProjectOFB_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_C_SubProjectOFB (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_SubProjectOFB[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** ActivitiesList AD_Reference_ID=2000087 */
	public static final int ACTIVITIESLIST_AD_Reference_ID=2000087;
	/** Total Estimado = 001 */
	public static final String ACTIVITIESLIST_TotalEstimado = "001";
	/** Ley de Presupuesto = 002 */
	public static final String ACTIVITIESLIST_LeyDePresupuesto = "002";
	/** Aumento total identificado = 003 */
	public static final String ACTIVITIESLIST_AumentoTotalIdentificado = "003";
	/** Disminucion total identificado = 004 */
	public static final String ACTIVITIESLIST_DisminucionTotalIdentificado = "004";
	/** Otros Gastos = 005 */
	public static final String ACTIVITIESLIST_OtrosGastos = "005";
	/** Inicio Ejecucion = 006 */
	public static final String ACTIVITIESLIST_InicioEjecucion = "006";
	/** Licitación = 007 */
/*	public static final String ACTIVITIESLIST_Licitacion = "007";
	/** Apertura = 008 */
/*	public static final String ACTIVITIESLIST_Apertura = "008";
	/** Inicio Obras = 009 */
	public static final String ACTIVITIESLIST_InicioObras = "009";
	/** Licitación = 010 */
	public static final String ACTIVITIESLIST_Licitacion = "010";
	/** Apertura = 011 */
	public static final String ACTIVITIESLIST_Apertura = "011";
	/** Set ActivitiesList.
		@param ActivitiesList ActivitiesList	  */
	public void setActivitiesList (String ActivitiesList)
	{

		set_Value (COLUMNNAME_ActivitiesList, ActivitiesList);
	}

	/** Get ActivitiesList.
		@return ActivitiesList	  */
	public String getActivitiesList () 
	{
		return (String)get_Value(COLUMNNAME_ActivitiesList);
	}

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Approval Amount.
		@param AmtApproval 
		The approval amount limit for this role
	  */
	public void setAmtApproval (BigDecimal AmtApproval)
	{
		set_Value (COLUMNNAME_AmtApproval, AmtApproval);
	}

	/** Get Approval Amount.
		@return The approval amount limit for this role
	  */
	public BigDecimal getAmtApproval () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtApproval);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** C_Phase_ID1 AD_Reference_ID=2000081 */
	public static final int C_PHASE_ID1_AD_Reference_ID=2000081;
	/** Diseño = 01 */
	public static final String C_PHASE_ID1_Disenno = "01";
	/** Ejecución = 02 */
	public static final String C_PHASE_ID1_Ejecucion = "02";
	/** Set Standard Phase.
		@param C_Phase_ID1 
		Standard Phase of the Project Type
	  */
	public void setC_Phase_ID1 (String C_Phase_ID1)
	{

		set_Value (COLUMNNAME_C_Phase_ID1, C_Phase_ID1);
	}

	/** Get Standard Phase.
		@return Standard Phase of the Project Type
	  */
	public String getC_Phase_ID1 () 
	{
		return (String)get_Value(COLUMNNAME_C_Phase_ID1);
	}

	/** C_Phase_ID2 AD_Reference_ID=2000084 */
	public static final int C_PHASE_ID2_AD_Reference_ID=2000084;
	/** Formulación de Proyectos e Identificación de Recursos = FPI */
//	public static final String C_PHASE_ID2_FormulaciónDeProyectosEIdentificaciónDeRecursos = "FPI";
	/** Convenio Mandato y Licitación Consultoría = CLM */
//	public static final String C_PHASE_ID2_ConvenioMandatoYLicitaciónConsultoría = "CLM";
	/** Desarrollo Consultoría de Diseño = DCD */
//	public static final String C_PHASE_ID2_DesarrolloConsultoríaDeDiseño = "DCD";
	/** Obras Civiles = OC */
	public static final String C_PHASE_ID2_ObrasCiviles = "OC";
	/** Habilitación = HA */
//	public static final String C_PHASE_ID2_Habilitación = "HA";
	/** Cronograma = CRD */
//	public static final String C_PHASE_ID2_Cronograma = "CRD";
	/** Cronograma = CRE */
//	public static final String C_PHASE_ID2_Cronograma = "CRE";
	/** Set Standard Phase.
		@param C_Phase_ID2 
		Standard Phase of the Project Type
	  */
	public void setC_Phase_ID2 (String C_Phase_ID2)
	{

		set_Value (COLUMNNAME_C_Phase_ID2, C_Phase_ID2);
	}

	/** Get Standard Phase.
		@return Standard Phase of the Project Type
	  */
	public String getC_Phase_ID2 () 
	{
		return (String)get_Value(COLUMNNAME_C_Phase_ID2);
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
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
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

	public org.compiere.model.I_C_ProjectLine getC_ProjectLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_ProjectLine)MTable.get(getCtx(), org.compiere.model.I_C_ProjectLine.Table_Name)
			.getPO(getC_ProjectLine_ID(), get_TrxName());	}

	/** Set Project Line.
		@param C_ProjectLine_ID 
		Task or step in a project
	  */
	public void setC_ProjectLine_ID (int C_ProjectLine_ID)
	{
		if (C_ProjectLine_ID < 1) 
			set_Value (COLUMNNAME_C_ProjectLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_ProjectLine_ID, Integer.valueOf(C_ProjectLine_ID));
	}

	/** Get Project Line.
		@return Task or step in a project
	  */
	public int getC_ProjectLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** CriticalKnot AD_Reference_ID=2000089 */
	public static final int CRITICALKNOT_AD_Reference_ID=2000089;
	/** MDS = MDS */
	public static final String CRITICALKNOT_MDS = "MDS";
	/** CGR = CGR */
	public static final String CRITICALKNOT_CGR = "CGR";
	/** MOP = MOP */
	public static final String CRITICALKNOT_MOP = "MOP";
	/** OPLAP = OPL */
	public static final String CRITICALKNOT_OPLAP = "OPL";
	/** Set CriticalKnot.
		@param CriticalKnot CriticalKnot	  */
	public void setCriticalKnot (String CriticalKnot)
	{

		set_Value (COLUMNNAME_CriticalKnot, CriticalKnot);
	}

	/** Get CriticalKnot.
		@return CriticalKnot	  */
	public String getCriticalKnot () 
	{
		return (String)get_Value(COLUMNNAME_CriticalKnot);
	}

	/** Set C_SubProjectOFB ID.
		@param C_SubProjectOFB_ID C_SubProjectOFB ID	  */
	public void setC_SubProjectOFB_ID (int C_SubProjectOFB_ID)
	{
		if (C_SubProjectOFB_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_SubProjectOFB_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_SubProjectOFB_ID, Integer.valueOf(C_SubProjectOFB_ID));
	}

	/** Get C_SubProjectOFB ID.
		@return C_SubProjectOFB ID	  */
	public int getC_SubProjectOFB_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_SubProjectOFB_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Contract Date.
		@param DateContract 
		The (planned) effective date of this document.
	  */
	public void setDateContract (Timestamp DateContract)
	{
		set_Value (COLUMNNAME_DateContract, DateContract);
	}

	/** Get Contract Date.
		@return The (planned) effective date of this document.
	  */
	public Timestamp getDateContract () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateContract);
	}

	/** Set Finish Date.
		@param DateFinish 
		Finish or (planned) completion date
	  */
	public void setDateFinish (Timestamp DateFinish)
	{
		set_Value (COLUMNNAME_DateFinish, DateFinish);
	}

	/** Get Finish Date.
		@return Finish or (planned) completion date
	  */
	public Timestamp getDateFinish () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFinish);
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

	/** Set G_IsValidated.
		@param G_IsValidated 
		The record is validated i
	  */
	public void setG_IsValidated (boolean G_IsValidated)
	{
		set_Value (COLUMNNAME_G_IsValidated, Boolean.valueOf(G_IsValidated));
	}

	/** Get G_IsValidated.
		@return The record is validated i
	  */
	public boolean isG_IsValidated () 
	{
		Object oo = get_Value(COLUMNNAME_G_IsValidated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set G_RealDateEnd.
		@param G_RealDateEnd 
		The real date end
	  */
	public void setG_RealDateEnd (Timestamp G_RealDateEnd)
	{
		set_Value (COLUMNNAME_G_RealDateEnd, G_RealDateEnd);
	}

	/** Get G_RealDateEnd.
		@return The real date end
	  */
	public Timestamp getG_RealDateEnd () 
	{
		return (Timestamp)get_Value(COLUMNNAME_G_RealDateEnd);
	}

	/** Set G_RealDateStart.
		@param G_RealDateStart 
		The real date start
	  */
	public void setG_RealDateStart (Timestamp G_RealDateStart)
	{
		set_Value (COLUMNNAME_G_RealDateStart, G_RealDateStart);
	}

	/** Get G_RealDateStart.
		@return The real date start
	  */
	public Timestamp getG_RealDateStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_G_RealDateStart);
	}

	/** Set G_VerificationMeans.
		@param G_VerificationMeans 
		Optional additional user defined information
	  */
	public void setG_VerificationMeans (String G_VerificationMeans)
	{
		set_Value (COLUMNNAME_G_VerificationMeans, G_VerificationMeans);
	}

	/** Get G_VerificationMeans.
		@return Optional additional user defined information
	  */
	public String getG_VerificationMeans () 
	{
		return (String)get_Value(COLUMNNAME_G_VerificationMeans);
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

	/** Set Note.
		@param Note 
		Optional additional user defined information
	  */
	public void setNote (String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Note.
		@return Optional additional user defined information
	  */
	public String getNote () 
	{
		return (String)get_Value(COLUMNNAME_Note);
	}

	/** Set OFB_Customer.
		@param OFB_Customer OFB_Customer	  */
	public void setOFB_Customer (String OFB_Customer)
	{
		set_Value (COLUMNNAME_OFB_Customer, OFB_Customer);
	}

	/** Get OFB_Customer.
		@return OFB_Customer	  */
	public String getOFB_Customer () 
	{
		return (String)get_Value(COLUMNNAME_OFB_Customer);
	}

	/** Set OFB_Location.
		@param OFB_Location OFB_Location	  */
	public void setOFB_Location (String OFB_Location)
	{
		set_Value (COLUMNNAME_OFB_Location, OFB_Location);
	}

	/** Get OFB_Location.
		@return OFB_Location	  */
	public String getOFB_Location () 
	{
		return (String)get_Value(COLUMNNAME_OFB_Location);
	}

	/** Set OFB_Name.
		@param OFB_Name OFB_Name	  */
	public void setOFB_Name (String OFB_Name)
	{
		set_Value (COLUMNNAME_OFB_Name, OFB_Name);
	}

	/** Get OFB_Name.
		@return OFB_Name	  */
	public String getOFB_Name () 
	{
		return (String)get_Value(COLUMNNAME_OFB_Name);
	}

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getValue());
    }
}