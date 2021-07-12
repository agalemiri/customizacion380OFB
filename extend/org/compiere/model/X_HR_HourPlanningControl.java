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

/** Generated Model for HR_HourPlanningControl
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_HR_HourPlanningControl extends PO implements I_HR_HourPlanningControl, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180514L;

    /** Standard Constructor */
    public X_HR_HourPlanningControl (Properties ctx, int HR_HourPlanningControl_ID, String trxName)
    {
      super (ctx, HR_HourPlanningControl_ID, trxName);
      /** if (HR_HourPlanningControl_ID == 0)
        {
			setHR_HourPlanningControl_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HR_HourPlanningControl (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_HourPlanningControl[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Available Amount.
		@param AvailableAmt 
		Amount available for allocation for this document
	  */
	public void setAvailableAmt (BigDecimal AvailableAmt)
	{
		set_Value (COLUMNNAME_AvailableAmt, AvailableAmt);
	}

	/** Get Available Amount.
		@return Amount available for allocation for this document
	  */
	public BigDecimal getAvailableAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AvailableAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Budget Amount.
		@param Budget_Amount 
		Budget amount for planning hour
	  */
	public void setBudget_Amount (BigDecimal Budget_Amount)
	{
		set_Value (COLUMNNAME_Budget_Amount, Budget_Amount);
	}

	/** Get Budget Amount.
		@return Budget amount for planning hour
	  */
	public BigDecimal getBudget_Amount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Budget_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
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

	/** Set Description2.
		@param Description2 Description2	  */
	public void setDescription2 (String Description2)
	{
		set_Value (COLUMNNAME_Description2, Description2);
	}

	/** Get Description2.
		@return Description2	  */
	public String getDescription2 () 
	{
		return (String)get_Value(COLUMNNAME_Description2);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Valorización = VA */
	public static final String DOCSTATUS_Valorizacion = "VA";
	/** En Apelación = EA */
	public static final String DOCSTATUS_EnApelacion = "EA";
	/** Autorizado Jefe Admin = AA */
	public static final String DOCSTATUS_AutorizadoJefeAdmin = "AA";
	/** Aprobación Presupuesto = AB */
	public static final String DOCSTATUS_AprobacionPresupuesto = "AB";
	/** Aprobado Coordinación = AC */
	public static final String DOCSTATUS_AprobadoCoordinacion = "AC";
	/** Autorizado Jefatura = AJ */
	public static final String DOCSTATUS_AutorizadoJefatura = "AJ";
	/** Autorizado Jefe RRFF = AR */
	//public static final String DOCSTATUS_AutorizadoJefeRRFF = "AR";
	/** Gestión Analista = GA */
	public static final String DOCSTATUS_GestionAnalista = "GA";
	/** Solicitud OC = SO */
	public static final String DOCSTATUS_SolicitudOC = "SO";
	/** Solicitud Requiriente = SR */
	public static final String DOCSTATUS_SolicitudRequiriente = "SR";
	/** Autorizado Coordinación Pago = AO */
	public static final String DOCSTATUS_AutorizadoCoordinacionPago = "AO";
	/** Autorizado Of Partes = A1 */
//	public static final String DOCSTATUS_AutorizadoOfPartes = "A1";
	/** Autorizado Encargado Unidad Requirente = A2 */
	public static final String DOCSTATUS_AutorizadoEncargadoUnidadRequirente = "A2";
	/** Autorizado Encargado Boleta = A3 */
//	public static final String DOCSTATUS_AutorizadoEncargadoBoleta = "A3";
	/** Autorizado Unidad = AU */
	public static final String DOCSTATUS_AutorizadoUnidad = "AU";
	/** Asignacion Analista = AN */
	public static final String DOCSTATUS_AsignacionAnalista = "AN";
	/** Asignacion Analista = AS */
//	public static final String DOCSTATUS_AsignacionAnalista = "AS";
	/** Factura Recepcionada = FA */
	public static final String DOCSTATUS_FacturaRecepcionada = "FA";
	/** Derivado Contraparte Tecnica = DC */
	public static final String DOCSTATUS_DerivadoContraparteTecnica = "DC";
	/** Aprobado Encargado = AE */
	public static final String DOCSTATUS_AprobadoEncargado = "AE";
	/** Solicitado = SS */
	public static final String DOCSTATUS_Solicitado = "SS";
	/** Autorizado Recepcionista = AV */
	public static final String DOCSTATUS_AutorizadoRecepcionista = "AV";
	/** Autorizado Analista Finanzas = AF */
	public static final String DOCSTATUS_AutorizadoAnalistaFinanzas = "AF";
	/** Autorizado RRHH = AH */
	public static final String DOCSTATUS_AutorizadoRRHH = "AH";
	/** Contraparte = CT */
	public static final String DOCSTATUS_Contraparte = "CT";
	/** Propuesta Revisada = PR */
//	public static final String DOCSTATUS_PropuestaRevisada = "PR";
	/** Autorizado Fiscalia = AL */
//	public static final String DOCSTATUS_AutorizadoFiscalia = "AL";
	/** Autoriza Subsecretario = AX */
//	public static final String DOCSTATUS_AutorizaSubsecretario = "AX";
	/** Autoriza Ministro = AM */
	public static final String DOCSTATUS_AutorizaMinistro = "AM";
	/** Propuesta Revisada = PP */
	public static final String DOCSTATUS_PropuestaRevisada = "PP";
	/** Revision Contraparte Tecnica = QA */
	public static final String DOCSTATUS_RevisionContraparteTecnica = "QA";
	/** Revision Profesional = QB */
//	public static final String DOCSTATUS_RevisionProfesional = "QB";
	/** Firma Contraparte Tecnica = QC */
	public static final String DOCSTATUS_FirmaContraparteTecnica = "QC";
	/** Revision Asesor Subse = QD */
//	public static final String DOCSTATUS_RevisionAsesorSubse = "QD";
	/** Numeracion y Registro = QE */
//	public static final String DOCSTATUS_NumeracionYRegistro = "QE";
	/** Publica Intencion Compra = QF */
	public static final String DOCSTATUS_PublicaIntencionCompra = "QF";
	/** Revision de Evaluacion = QG */
	public static final String DOCSTATUS_RevisionDeEvaluacion = "QG";
	/** Revision Profesional . = QH */
//	public static final String DOCSTATUS_RevisionProfesional = "QH";
	/** Decision Compra Privada = QI */
	public static final String DOCSTATUS_DecisionCompraPrivada = "QI";
	/** Revision Asesor Subse . = QJ */
//	public static final String DOCSTATUS_RevisionAsesorSubse = "QJ";
	/** Autoriza Subsecretario . = QK */
//	public static final String DOCSTATUS_AutorizaSubsecretario = "QK";
	/** Numeracion y Registro . = QL */
//	public static final String DOCSTATUS_NumeracionYRegistro = "QL";
	/** Contrato Revisado = QM */
	public static final String DOCSTATUS_ContratoRevisado = "QM";
	/** Revisión Profesional = QN */
	public static final String DOCSTATUS_RevisionProfesional = "QN";
	/** Autoriza Fiscalia . = QO */
	public static final String DOCSTATUS_AutorizaFiscalia = "QO";
	/** Contrato Derivado . = QP */
	public static final String DOCSTATUS_ContratoDerivado = "QP";
	/** Revision Asesor Subse .. = QQ */
	public static final String DOCSTATUS_RevisionAsesorSubse = "QQ";
	/** Autoriza Subsecretario .. = QR */
	public static final String DOCSTATUS_AutorizaSubsecretario = "QR";
	/** Revisión Asesor Ministro = QS */
	public static final String DOCSTATUS_RevisionAsesorMinistro = "QS";
	/** Numeracion y Registro .. = QT */
	public static final String DOCSTATUS_NumeracionYRegistro = "QT";
	/** Revision Encargado = QV */
//	public static final String DOCSTATUS_RevisionEncargado = "QV";
	/** Publica Bases de Licitacion = QW */
	public static final String DOCSTATUS_PublicaBasesDeLicitacion = "QW";
	/** Revision Licitacion = QX */
	public static final String DOCSTATUS_RevisionLicitacion = "QX";
	/** Revision Encargado . = QY */
	public static final String DOCSTATUS_RevisionEncargado = "QY";
	/** Autoriza Jefe Admin . = QZ */
//	public static final String DOCSTATUS_AutorizaJefeAdmin = "QZ";
	/** Autoriza Jefe Admin .. = Q1 */
	public static final String DOCSTATUS_AutorizaJefeAdmin = "Q1";
	/** Publica Licitacion = Q2 */
	public static final String DOCSTATUS_PublicaLicitacion = "Q2";
	/** Adjudicacion Derivada = Q3 */
	public static final String DOCSTATUS_AdjudicacionDerivada = "Q3";
	/** Firmas Plasmadas = Q4 */
	public static final String DOCSTATUS_FirmasPlasmadas = "Q4";
	/** Autorizado Fiscalia .. = QU */
	public static final String DOCSTATUS_AutorizadoFiscalia = "QU";
	/** Evaluacion de Licitacion = Q5 */
	public static final String DOCSTATUS_EvaluacionDeLicitacion = "Q5";
	/** Autorizado Jefe RRFF . = Q6 */
	public static final String DOCSTATUS_AutorizadoJefeRRFF = "Q6";
	/** Publicacion Adjudicacion = Q7 */
	public static final String DOCSTATUS_PublicacionAdjudicacion = "Q7";
	/** Evaluacion de Decision = Q8 */
	public static final String DOCSTATUS_EvaluacionDeDecision = "Q8";
	/** Pubicacion Decision Compra = Q9 */
	public static final String DOCSTATUS_PubicacionDecisionCompra = "Q9";
	/** Acuerdo Complementario = Q0 */
	public static final String DOCSTATUS_AcuerdoComplementario = "Q0";
	/** Solicitud Reasignacion = SA */
//	public static final String DOCSTATUS_SolicitudReasignacion = "SA";
	/** Solicitud Reasignacion = SE */
	public static final String DOCSTATUS_SolicitudReasignacion = "SE";
	/** Autorizado Jefe DAF = AD */
	public static final String DOCSTATUS_AutorizadoJefeDAF = "AD";
	/** Prorroga = BG10 */
	public static final String DOCSTATUS_Prorroga = "BG10";
	/** Vigente = BG01 */
	public static final String DOCSTATUS_Vigente = "BG01";
	/** Enviado a Región = BG02 */
	public static final String DOCSTATUS_EnviadoARegion = "BG02";
	/** Devuelta = BG03 */
	public static final String DOCSTATUS_Devuelta = "BG03";
	/** Vencida = BG04 */
	public static final String DOCSTATUS_Vencida = "BG04";
	/** Espera de Entrega = BG05 */
	public static final String DOCSTATUS_EsperaDeEntrega = "BG05";
	/** Cobrada = BG06 */
	public static final String DOCSTATUS_Cobrada = "BG06";
	/** Enviado por Correo = BG07 */
	public static final String DOCSTATUS_EnviadoPorCorreo = "BG07";
	/** Autorizado Of Partes = BG08 */
	public static final String DOCSTATUS_AutorizadoOfPartes = "BG08";
	/** Autorizado Encargado Boleta = BG09 */
	public static final String DOCSTATUS_AutorizadoEncargadoBoleta = "BG09";
	/** Completo = BG11 */
	public static final String DOCSTATUS_Completo = "BG11";
	/** Solicitud Devolucion = BG12 */
	public static final String DOCSTATUS_SolicitudDevolucion = "BG12";
	/** Solicitud Cobro = BG13 */
	public static final String DOCSTATUS_SolicitudCobro = "BG13";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Hour Planning Control ID.
		@param HR_HourPlanningControl_ID Hour Planning Control ID	  */
	public void setHR_HourPlanningControl_ID (int HR_HourPlanningControl_ID)
	{
		if (HR_HourPlanningControl_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_HourPlanningControl_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_HourPlanningControl_ID, Integer.valueOf(HR_HourPlanningControl_ID));
	}

	/** Get Hour Planning Control ID.
		@return Hour Planning Control ID	  */
	public int getHR_HourPlanningControl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_HourPlanningControl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HR_HourPlanning getHR_HourPlanning() throws RuntimeException
    {
		return (I_HR_HourPlanning)MTable.get(getCtx(), I_HR_HourPlanning.Table_Name)
			.getPO(getHR_HourPlanning_ID(), get_TrxName());	}

	/** Set Hour Planning ID.
		@param HR_HourPlanning_ID Hour Planning ID	  */
	public void setHR_HourPlanning_ID (int HR_HourPlanning_ID)
	{
		if (HR_HourPlanning_ID < 1) 
			set_Value (COLUMNNAME_HR_HourPlanning_ID, null);
		else 
			set_Value (COLUMNNAME_HR_HourPlanning_ID, Integer.valueOf(HR_HourPlanning_ID));
	}

	/** Get Hour Planning ID.
		@return Hour Planning ID	  */
	public int getHR_HourPlanning_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_HourPlanning_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ofbbutton.
		@param ofbbutton ofbbutton	  */
	public void setofbbutton (String ofbbutton)
	{
		set_Value (COLUMNNAME_ofbbutton, ofbbutton);
	}

	/** Get ofbbutton.
		@return ofbbutton	  */
	public String getofbbutton () 
	{
		return (String)get_Value(COLUMNNAME_ofbbutton);
	}

	/** Set ofbbutton2.
		@param ofbbutton2 ofbbutton2	  */
	public void setofbbutton2 (String ofbbutton2)
	{
		set_Value (COLUMNNAME_ofbbutton2, ofbbutton2);
	}

	/** Get ofbbutton2.
		@return ofbbutton2	  */
	public String getofbbutton2 () 
	{
		return (String)get_Value(COLUMNNAME_ofbbutton2);
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UsedAmt.
		@param UsedAmt UsedAmt	  */
	public void setUsedAmt (BigDecimal UsedAmt)
	{
		set_Value (COLUMNNAME_UsedAmt, UsedAmt);
	}

	/** Get UsedAmt.
		@return UsedAmt	  */
	public BigDecimal getUsedAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UsedAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}