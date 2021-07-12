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
import org.compiere.util.KeyNamePair;

/** Generated Model for GL_BudgetControlHeader
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_GL_BudgetControlHeader extends PO implements I_GL_BudgetControlHeader, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180323L;

    /** Standard Constructor */
    public X_GL_BudgetControlHeader (Properties ctx, int GL_BudgetControlHeader_ID, String trxName)
    {
      super (ctx, GL_BudgetControlHeader_ID, trxName);
      /** if (GL_BudgetControlHeader_ID == 0)
        {
			setGL_BudgetControlHeader_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_GL_BudgetControlHeader (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_GL_BudgetControlHeader[")
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
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		throw new IllegalArgumentException ("Amount is virtual column");	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount2.
		@param Amount2 Amount2	  */
	public void setAmount2 (BigDecimal Amount2)
	{
		set_Value (COLUMNNAME_Amount2, Amount2);
	}

	/** Get Amount2.
		@return Amount2	  */
	public BigDecimal getAmount2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set BudgetType.
		@param BudgetType BudgetType	  */
	public void setBudgetType (String BudgetType)
	{
		set_Value (COLUMNNAME_BudgetType, BudgetType);
	}

	/** Get BudgetType.
		@return BudgetType	  */
	public String getBudgetType () 
	{
		return (String)get_Value(COLUMNNAME_BudgetType);
	}

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException
    {
		return (org.compiere.model.I_C_Year)MTable.get(getCtx(), org.compiere.model.I_C_Year.Table_Name)
			.getPO(getC_Year_ID(), get_TrxName());	}

	/** Set Year.
		@param C_Year_ID 
		Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID)
	{
		if (C_Year_ID < 1) 
			set_Value (COLUMNNAME_C_Year_ID, null);
		else 
			set_Value (COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
	}

	/** Get Year.
		@return Calendar Year
	  */
	public int getC_Year_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Year_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	public static final String DOCACTION_AutorizaSubsecretario = "AX";
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

	/** Set GL_BudgetControlHeader ID.
		@param GL_BudgetControlHeader_ID GL_BudgetControlHeader ID	  */
	public void setGL_BudgetControlHeader_ID (int GL_BudgetControlHeader_ID)
	{
		if (GL_BudgetControlHeader_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_BudgetControlHeader_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_BudgetControlHeader_ID, Integer.valueOf(GL_BudgetControlHeader_ID));
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
}