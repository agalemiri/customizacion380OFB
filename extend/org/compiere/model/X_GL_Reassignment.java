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

/** Generated Model for GL_Reassignment
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_GL_Reassignment extends PO implements I_GL_Reassignment, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180323L;

    /** Standard Constructor */
    public X_GL_Reassignment (Properties ctx, int GL_Reassignment_ID, String trxName)
    {
      super (ctx, GL_Reassignment_ID, trxName);
      /** if (GL_Reassignment_ID == 0)
        {
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
			setDocStatus (null);
// DR
			setGL_Reassignment_ID (0);
			setIsApproved (false);
			setProcessed (false);
			setTotalLines (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_GL_Reassignment (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_GL_Reassignment[")
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

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
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

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_ValidCombination getC_ValidCombination() throws RuntimeException
    {
		return (org.compiere.model.I_C_ValidCombination)MTable.get(getCtx(), org.compiere.model.I_C_ValidCombination.Table_Name)
			.getPO(getC_ValidCombination_ID(), get_TrxName());	}

	/** Set Combination.
		@param C_ValidCombination_ID 
		Valid Account Combination
	  */
	public void setC_ValidCombination_ID (int C_ValidCombination_ID)
	{
		if (C_ValidCombination_ID < 1) 
			set_Value (COLUMNNAME_C_ValidCombination_ID, null);
		else 
			set_Value (COLUMNNAME_C_ValidCombination_ID, Integer.valueOf(C_ValidCombination_ID));
	}

	/** Get Combination.
		@return Valid Account Combination
	  */
	public int getC_ValidCombination_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ValidCombination_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Date Required.
		@param DateRequired 
		Date when required
	  */
	public void setDateRequired (Timestamp DateRequired)
	{
		set_Value (COLUMNNAME_DateRequired, DateRequired);
	}

	/** Get Date Required.
		@return Date when required
	  */
	public Timestamp getDateRequired () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateRequired);
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

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** En formulación = E1 */
	/** Esperando Aprobación = E2 */
	/** Autorizado Jefe Admin = AA */
	public static final String DOCACTION_AutorizadoJefeAdmin = "AA";
	/** Aprobación Presupuesto = AB */
	/** Autorizado Coordinación = AC */
	/** Autorizado Jefatura = AJ */
	public static final String DOCACTION_AutorizadoJefatura = "AJ";
	/** Autorizado RRFF = AR */
	public static final String DOCACTION_AutorizadoRRFF = "AR";
	/** Gestión Analista = GA */
	/** In Progress = IP */
	public static final String DOCACTION_InProgress = "IP";
	/** Solicitud OC = SO */
	public static final String DOCACTION_SolicitudOC = "SO";
	/** Solicitud Requiriente = SR */
	public static final String DOCACTION_SolicitudRequiriente = "SR";
	/** Draft = DR */
	public static final String DOCACTION_Draft = "DR";
	/** Autorizado Analista = AN */
	public static final String DOCACTION_AutorizadoAnalista = "AN";
	/** Autorizado Coordinación Pago = AO */
	/** Autorizado Of de Partes = A1 */
	public static final String DOCACTION_AutorizadoOfDePartes = "A1";
	/** Autorizado Encargado Unidad Requirente = A2 */
	public static final String DOCACTION_AutorizadoEncargadoUnidadRequirente = "A2";
	/** Autorizado Encargado Boleta = A3 */
	public static final String DOCACTION_AutorizadoEncargadoBoleta = "A3";
	/** Autorizado Unidad = AU */
	public static final String DOCACTION_AutorizadoUnidad = "AU";
	/** Aprobado Encargado = AE */
	public static final String DOCACTION_AprobadoEncargado = "AE";
	/** Asignación Analista = AS */
	/** Derivado Contraparte Técnica = DC */
	/** Derivado a Presupuesto = DP */
	public static final String DOCACTION_DerivadoAPresupuesto = "DP";
	/** Factura Recepcionada = FA */
	public static final String DOCACTION_FacturaRecepcionada = "FA";
	/** Solicitud Reasignación = SE */
	/** Solicitado = SS */
	public static final String DOCACTION_Solicitado = "SS";
	/** Autorizado Recepcionista = AV */
	public static final String DOCACTION_AutorizadoRecepcionista = "AV";
	/** Autorizado Analista Finanzas = AF */
	public static final String DOCACTION_AutorizadoAnalistaFinanzas = "AF";
	/** Autorizado RRHH = AH */
	public static final String DOCACTION_AutorizadoRRHH = "AH";
	/** Contraparte = CT */
	public static final String DOCACTION_Contraparte = "CT";
	/** Autorizado Ministro = AM */
	public static final String DOCACTION_AutorizadoMinistro = "AM";
	/** Autoriza Subsecretario = AX */
	/** Autorizado Fiscalia = AL */
	public static final String DOCACTION_AutorizadoFiscalia = "AL";
	/** Propuesta Revisada = PP */
	public static final String DOCACTION_PropuestaRevisada = "PP";
	/** Revision Contraparte Tecnica = QA */
	public static final String DOCACTION_RevisionContraparteTecnica = "QA";
	/** Revision Profesional = QB */
	/** Firma Contraparte Tecnica = QC */
	public static final String DOCACTION_FirmaContraparteTecnica = "QC";
	/** Revision Asesor Subse = QD */
	/** Numeracion y Registro = QE */
	/** Publica Intencion Compra = QF */
	public static final String DOCACTION_PublicaIntencionCompra = "QF";
	/** Revision de Evaluacion = QG */
	public static final String DOCACTION_RevisionDeEvaluacion = "QG";
	/** Revision Profesional . = QH */
	/** Decision Compra Privada = QI */
	public static final String DOCACTION_DecisionCompraPrivada = "QI";
	/** Revision Asesor Subse . = QJ */
	/** Autoriza Subsecretario . = QK */
	public static final String DOCACTION_AutorizaSubsecretario = "QK";
	/** Numeracion y registro . = QL */
	/** Contrato Revisado = QM */
	public static final String DOCACTION_ContratoRevisado = "QM";
	/** Revision Profesional .. = QN */
	public static final String DOCACTION_RevisionProfesional = "QN";
	/** Autoriza Fiscalia . = QO */
	/** Contrato Derivado = QP */
	public static final String DOCACTION_ContratoDerivado = "QP";
	/** Revision Asesor Subse .. = QQ */
	public static final String DOCACTION_RevisionAsesorSubse = "QQ";
	/** Autoriza Subseccretario .. = QR */
	public static final String DOCACTION_AutorizaSubseccretario = "QR";
	/** Revision Asesor Ministro = QS */
	public static final String DOCACTION_RevisionAsesorMinistro = "QS";
	/** Numeracion y Registro .. = QT */
	public static final String DOCACTION_NumeracionYRegistro = "QT";
	/** Revision Encargado = QV */
	/** Publica Bases de licitacion = QW */
	public static final String DOCACTION_PublicaBasesDeLicitacion = "QW";
	/** Revision Licitacion = QX */
	public static final String DOCACTION_RevisionLicitacion = "QX";
	/** Revision Encargado . = QY */
	/** Autoriza Jefe Admin . = QZ */
	public static final String DOCACTION_AutorizaJefeAdmin = "QZ";
	/** Autoriza Jefe Admin .. = Q1 */
	/** Publica Licitacion = Q2 */
	public static final String DOCACTION_PublicaLicitacion = "Q2";
	/** Adjudicacion Derivada = Q3 */
	public static final String DOCACTION_AdjudicacionDerivada = "Q3";
	/** Firmas Plasmadas = Q4 */
	public static final String DOCACTION_FirmasPlasmadas = "Q4";
	/** Autoriza Fiscalia .. = QU */
	public static final String DOCACTION_AutorizaFiscalia = "QU";
	/** Evaluacion de Licitacion = Q5 */
	public static final String DOCACTION_EvaluacionDeLicitacion = "Q5";
	/** Autorizado Jefe RRFF . = Q6 */
	public static final String DOCACTION_AutorizadoJefeRRFF = "Q6";
	/** Publicacion Adjudicacion = Q7 */
	public static final String DOCACTION_PublicacionAdjudicacion = "Q7";
	/** Evaluacion de Decision = Q8 */
	public static final String DOCACTION_EvaluacionDeDecision = "Q8";
	/** Publicacion Decision Compra = Q9 */
	public static final String DOCACTION_PublicacionDecisionCompra = "Q9";
	/** Acuerdo Complementario = Q0 */
	public static final String DOCACTION_AcuerdoComplementario = "Q0";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
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
	/** En Apelación = EA */
	/** Autorizado Jefe Admin = AA */
	public static final String DOCSTATUS_AutorizadoJefeAdmin = "AA";
	/** Aprobación Presupuesto = AB */
	/** Aprobado Coordinación = AC */
	/** Autorizado Jefatura = AJ */
	public static final String DOCSTATUS_AutorizadoJefatura = "AJ";
	/** Autorizado Jefe RRFF = AR */
	/** Gestión Analista = GA */
	/** Solicitud OC = SO */
	public static final String DOCSTATUS_SolicitudOC = "SO";
	/** Solicitud Requiriente = SR */
	public static final String DOCSTATUS_SolicitudRequiriente = "SR";
	/** Autorizado Coordinación Pago = AO */
	/** Autorizado Of Partes = A1 */
	public static final String DOCSTATUS_AutorizadoOfPartes = "A1";
	/** Autorizado Encargado Unidad Requirente = A2 */
	public static final String DOCSTATUS_AutorizadoEncargadoUnidadRequirente = "A2";
	/** Autorizado Encargado Boleta = A3 */
	public static final String DOCSTATUS_AutorizadoEncargadoBoleta = "A3";
	/** Autorizado Unidad = AU */
	public static final String DOCSTATUS_AutorizadoUnidad = "AU";
	/** Asignacion Analista = AN */
	/** Asignacion Analista = AS */
	public static final String DOCSTATUS_AsignacionAnalista = "AS";
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
	/** Autorizado Fiscalia = AL */
	/** Autoriza Subsecretario = AX */
	/** Autoriza Ministro = AM */
	public static final String DOCSTATUS_AutorizaMinistro = "AM";
	/** Propuesta Revisada = PP */
	public static final String DOCSTATUS_PropuestaRevisada = "PP";
	/** Revision Contraparte Tecnica = QA */
	public static final String DOCSTATUS_RevisionContraparteTecnica = "QA";
	/** Revision Profesional = QB */
	/** Firma Contraparte Tecnica = QC */
	public static final String DOCSTATUS_FirmaContraparteTecnica = "QC";
	/** Revision Asesor Subse = QD */
	/** Numeracion y Registro = QE */
	/** Publica Intencion Compra = QF */
	public static final String DOCSTATUS_PublicaIntencionCompra = "QF";
	/** Revision de Evaluacion = QG */
	public static final String DOCSTATUS_RevisionDeEvaluacion = "QG";
	/** Revision Profesional . = QH */
	public static final String DOCSTATUS_RevisionProfesional = "QH";
	/** Decision Compra Privada = QI */
	public static final String DOCSTATUS_DecisionCompraPrivada = "QI";
	/** Revision Asesor Subse . = QJ */
	/** Autoriza Subsecretario . = QK */
	/** Numeracion y Registro . = QL */
	/** Contrato Revisado = QM */
	public static final String DOCSTATUS_ContratoRevisado = "QM";
	/** Revisión Profesional = QN */
	/** Autoriza Fiscalia . = QO */
	public static final String DOCSTATUS_AutorizaFiscalia = "QO";
	/** Contrato Derivado . = QP */
	public static final String DOCSTATUS_ContratoDerivado = "QP";
	/** Revision Asesor Subse .. = QQ */
	public static final String DOCSTATUS_RevisionAsesorSubse = "QQ";
	/** Autoriza Subsecretario .. = QR */
	public static final String DOCSTATUS_AutorizaSubsecretario = "QR";
	/** Revisión Asesor Ministro = QS */
	/** Numeracion y Registro .. = QT */
	public static final String DOCSTATUS_NumeracionYRegistro = "QT";
	/** Revision Encargado = QV */
	/** Publica Bases de Licitacion = QW */
	public static final String DOCSTATUS_PublicaBasesDeLicitacion = "QW";
	/** Revision Licitacion = QX */
	public static final String DOCSTATUS_RevisionLicitacion = "QX";
	/** Revision Encargado . = QY */
	public static final String DOCSTATUS_RevisionEncargado = "QY";
	/** Autoriza Jefe Admin . = QZ */
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

	public I_GL_BudgetControlHeader getGL_BudgetControlHeader() throws RuntimeException
    {
		return (I_GL_BudgetControlHeader)MTable.get(getCtx(), I_GL_BudgetControlHeader.Table_Name)
			.getPO(getGL_BudgetControlHeader_ID(), get_TrxName());	}

	/** Set GL_BudgetControlHeader ID.
		@param GL_BudgetControlHeader_ID GL_BudgetControlHeader ID	  */
	public void setGL_BudgetControlHeader_ID (int GL_BudgetControlHeader_ID)
	{
		if (GL_BudgetControlHeader_ID < 1) 
			set_Value (COLUMNNAME_GL_BudgetControlHeader_ID, null);
		else 
			set_Value (COLUMNNAME_GL_BudgetControlHeader_ID, Integer.valueOf(GL_BudgetControlHeader_ID));
	}

	/** Get GL_BudgetControlHeader ID.
		@return GL_BudgetControlHeader ID	  */
	public int getGL_BudgetControlHeader_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_BudgetControlHeader_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_GL_BudgetControl getGL_BudgetControl() throws RuntimeException
    {
		return (org.compiere.model.I_GL_BudgetControl)MTable.get(getCtx(), org.compiere.model.I_GL_BudgetControl.Table_Name)
			.getPO(getGL_BudgetControl_ID(), get_TrxName());	}

	/** Set Budget Control.
		@param GL_BudgetControl_ID 
		Budget Control
	  */
	public void setGL_BudgetControl_ID (int GL_BudgetControl_ID)
	{
		if (GL_BudgetControl_ID < 1) 
			set_Value (COLUMNNAME_GL_BudgetControl_ID, null);
		else 
			set_Value (COLUMNNAME_GL_BudgetControl_ID, Integer.valueOf(GL_BudgetControl_ID));
	}

	/** Get Budget Control.
		@return Budget Control
	  */
	public int getGL_BudgetControl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_BudgetControl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_GL_BudgetControlLine getGL_BudgetControlLine() throws RuntimeException
    {
		return (I_GL_BudgetControlLine)MTable.get(getCtx(), I_GL_BudgetControlLine.Table_Name)
			.getPO(getGL_BudgetControlLine_ID(), get_TrxName());	}

	/** Set GL_BudgetControlLine ID.
		@param GL_BudgetControlLine_ID GL_BudgetControlLine ID	  */
	public void setGL_BudgetControlLine_ID (int GL_BudgetControlLine_ID)
	{
		if (GL_BudgetControlLine_ID < 1) 
			set_Value (COLUMNNAME_GL_BudgetControlLine_ID, null);
		else 
			set_Value (COLUMNNAME_GL_BudgetControlLine_ID, Integer.valueOf(GL_BudgetControlLine_ID));
	}

	/** Get GL_BudgetControlLine ID.
		@return GL_BudgetControlLine ID	  */
	public int getGL_BudgetControlLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_BudgetControlLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_GL_BudgetDetail getGL_BudgetDetail() throws RuntimeException
    {
		return (I_GL_BudgetDetail)MTable.get(getCtx(), I_GL_BudgetDetail.Table_Name)
			.getPO(getGL_BudgetDetail_ID(), get_TrxName());	}

	/** Set GL_BudgetDetail ID.
		@param GL_BudgetDetail_ID GL_BudgetDetail ID	  */
	public void setGL_BudgetDetail_ID (int GL_BudgetDetail_ID)
	{
		if (GL_BudgetDetail_ID < 1) 
			set_Value (COLUMNNAME_GL_BudgetDetail_ID, null);
		else 
			set_Value (COLUMNNAME_GL_BudgetDetail_ID, Integer.valueOf(GL_BudgetDetail_ID));
	}

	/** Get GL_BudgetDetail ID.
		@return GL_BudgetDetail ID	  */
	public int getGL_BudgetDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_BudgetDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GL_Reassignment ID.
		@param GL_Reassignment_ID GL_Reassignment ID	  */
	public void setGL_Reassignment_ID (int GL_Reassignment_ID)
	{
		if (GL_Reassignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_Reassignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_Reassignment_ID, Integer.valueOf(GL_Reassignment_ID));
	}

	/** Get GL_Reassignment ID.
		@return GL_Reassignment ID	  */
	public int getGL_Reassignment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Reassignment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Mode AD_Reference_ID=2000041 */
	public static final int MODE_AD_Reference_ID=2000041;
	/** Convenio Marco = MAR */
	public static final String MODE_ConvenioMarco = "MAR";
	/** Trato Directo = DIR */
	public static final String MODE_TratoDirecto = "DIR";
	/** Compra menor a 100 UTM = MEN */
	public static final String MODE_CompraMenorA100UTM = "MEN";
	/** Compra mayor a 100 UTM = MAY */
	public static final String MODE_CompraMayorA100UTM = "MAY";
	/** Compra menor a 3 UTM = 3ME */
	public static final String MODE_CompraMenorA3UTM = "3ME";
	/** Licitaciones Privadas = LIC */
	public static final String MODE_LicitacionesPrivadas = "LIC";
	/** Licitaciones Públicas = LIB */
	/** Contrato de Arrastre = CAR */
	public static final String MODE_ContratoDeArrastre = "CAR";
	/** Gran Compra = GC */
	public static final String MODE_GranCompra = "GC";
	/** Servicio Básico = SB */
	/** Fondo Global = FG */
	public static final String MODE_FondoGlobal = "FG";
	/** Compra menor a 3 UTM = ME3 */
	/** Set Mode.
		@param Mode Mode	  */
	public void setMode (String Mode)
	{

		set_Value (COLUMNNAME_Mode, Mode);
	}

	/** Get Mode.
		@return Mode	  */
	public String getMode () 
	{
		return (String)get_Value(COLUMNNAME_Mode);
	}

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product_Category)MTable.get(getCtx(), org.compiere.model.I_M_Product_Category.Table_Name)
			.getPO(getM_Product_Category_ID(), get_TrxName());	}

	/** Set Product Category.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Product Category.
		@return Category of a Product
	  */
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ofbbutton.
		@param ofbbutton ofbbutton	  */
	public void setofbbutton (boolean ofbbutton)
	{
		set_Value (COLUMNNAME_ofbbutton, Boolean.valueOf(ofbbutton));
	}

	/** Get ofbbutton.
		@return ofbbutton	  */
	public boolean isofbbutton () 
	{
		Object oo = get_Value(COLUMNNAME_ofbbutton);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Program AD_Reference_ID=2000053 */
	public static final int PROGRAM_AD_Reference_ID=2000053;
	/** 1 = 1 */
	public static final String PROGRAM_1 = "1";
	/** 2 = 2 */
	public static final String PROGRAM_2 = "2";
	/** Set Program.
		@param Program Program	  */
	public void setProgram (String Program)
	{

		set_Value (COLUMNNAME_Program, Program);
	}

	/** Get Program.
		@return Program	  */
	public String getProgram () 
	{
		return (String)get_Value(COLUMNNAME_Program);
	}

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines)
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	/** Get Total Lines.
		@return Total of all document lines
	  */
	public BigDecimal getTotalLines () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLines);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}