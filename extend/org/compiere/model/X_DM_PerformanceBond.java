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

/** Generated Model for DM_PerformanceBond
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_DM_PerformanceBond extends PO implements I_DM_PerformanceBond, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180504L;

    /** Standard Constructor */
    public X_DM_PerformanceBond (Properties ctx, int DM_PerformanceBond_ID, String trxName)
    {
      super (ctx, DM_PerformanceBond_ID, trxName);
      /** if (DM_PerformanceBond_ID == 0)
        {
			setAmt (Env.ZERO);
			setDM_PerformanceBond_ID (0);
			setDocumentNo (null);
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_DM_PerformanceBond (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_DM_PerformanceBond[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AccountingDocument.
		@param AccountingDocument 
		 guarantee billing document
	  */
	public void setAccountingDocument (BigDecimal AccountingDocument)
	{
		set_Value (COLUMNNAME_AccountingDocument, AccountingDocument);
	}

	/** Get AccountingDocument.
		@return  guarantee billing document
	  */
	public BigDecimal getAccountingDocument () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AccountingDocument);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Amount1.
		@param Amt1 
		Amount
	  */
	public void setAmt1 (BigDecimal Amt1)
	{
		set_Value (COLUMNNAME_Amt1, Amt1);
	}

	/** Get Amount1.
		@return Amount
	  */
	public BigDecimal getAmt1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_BPartner getC_BPartner_Bank() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_Bank_ID(), get_TrxName());	}

	/** Set C_BPartner_Bank_ID.
		@param C_BPartner_Bank_ID C_BPartner_Bank_ID	  */
	public void setC_BPartner_Bank_ID (int C_BPartner_Bank_ID)
	{
		if (C_BPartner_Bank_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Bank_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Bank_ID, Integer.valueOf(C_BPartner_Bank_ID));
	}

	/** Get C_BPartner_Bank_ID.
		@return C_BPartner_Bank_ID	  */
	public int getC_BPartner_Bank_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Bank_ID);
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

	public org.compiere.model.I_C_BPartner getc_bpartnerp() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getc_bpartnerp_id(), get_TrxName());	}

	/** Set c_bpartnerp_id.
		@param c_bpartnerp_id c_bpartnerp_id	  */
	public void setc_bpartnerp_id (int c_bpartnerp_id)
	{
		set_Value (COLUMNNAME_c_bpartnerp_id, Integer.valueOf(c_bpartnerp_id));
	}

	/** Get c_bpartnerp_id.
		@return c_bpartnerp_id	  */
	public int getc_bpartnerp_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_c_bpartnerp_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
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

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException
    {
		return (org.compiere.model.I_C_Region)MTable.get(getCtx(), org.compiere.model.I_C_Region.Table_Name)
			.getPO(getC_Region_ID(), get_TrxName());	}

	/** Set Region.
		@param C_Region_ID 
		Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_Value (COLUMNNAME_C_Region_ID, null);
		else 
			set_Value (COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
	}

	/** Get Region.
		@return Identifies a geographical Region
	  */
	public int getC_Region_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_RfQ getC_RfQ() throws RuntimeException
    {
		return (org.compiere.model.I_C_RfQ)MTable.get(getCtx(), org.compiere.model.I_C_RfQ.Table_Name)
			.getPO(getC_RfQ_ID(), get_TrxName());	}

	/** Set RfQ.
		@param C_RfQ_ID 
		Request for Quotation
	  */
	public void setC_RfQ_ID (int C_RfQ_ID)
	{
		if (C_RfQ_ID < 1) 
			set_Value (COLUMNNAME_C_RfQ_ID, null);
		else 
			set_Value (COLUMNNAME_C_RfQ_ID, Integer.valueOf(C_RfQ_ID));
	}

	/** Get RfQ.
		@return Request for Quotation
	  */
	public int getC_RfQ_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQ_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Comments.
		@param Comments 
		Comments or additional information
	  */
	public void setComments (String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Comments.
		@return Comments or additional information
	  */
	public String getComments () 
	{
		return (String)get_Value(COLUMNNAME_Comments);
	}

	/** Set Date01.
		@param Date01 Date01	  */
	public void setDate01 (Timestamp Date01)
	{
		set_Value (COLUMNNAME_Date01, Date01);
	}

	/** Get Date01.
		@return Date01	  */
	public Timestamp getDate01 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Date01);
	}

	/** Set Date1.
		@param Date1 
		Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1)
	{
		set_Value (COLUMNNAME_Date1, Date1);
	}

	/** Get Date1.
		@return Date when business is not conducted
	  */
	public Timestamp getDate1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Date1);
	}

	/** Set Date3.
		@param Date3 Date3	  */
	public void setDate3 (Timestamp Date3)
	{
		set_Value (COLUMNNAME_Date3, Date3);
	}

	/** Get Date3.
		@return Date3	  */
	public Timestamp getDate3 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Date3);
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set Date Confirm.
		@param DateConfirm 
		Date Confirm of this Order
	  */
	public void setDateConfirm (Timestamp DateConfirm)
	{
		set_Value (COLUMNNAME_DateConfirm, DateConfirm);
	}

	/** Get Date Confirm.
		@return Date Confirm of this Order
	  */
	public Timestamp getDateConfirm () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateConfirm);
	}

	/** Set Date Delivered.
		@param DateDelivered 
		Date when the product was delivered
	  */
	public void setDateDelivered (Timestamp DateDelivered)
	{
		set_Value (COLUMNNAME_DateDelivered, DateDelivered);
	}

	/** Get Date Delivered.
		@return Date when the product was delivered
	  */
	public Timestamp getDateDelivered () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDelivered);
	}

	/** Set dateexpiration.
		@param dateexpiration dateexpiration	  */
	public void setdateexpiration (Timestamp dateexpiration)
	{
		set_Value (COLUMNNAME_dateexpiration, dateexpiration);
	}

	/** Get dateexpiration.
		@return dateexpiration	  */
	public Timestamp getdateexpiration () 
	{
		return (Timestamp)get_Value(COLUMNNAME_dateexpiration);
	}

	/** Set DateExtension.
		@param DateExtension DateExtension	  */
	public void setDateExtension (Timestamp DateExtension)
	{
		set_Value (COLUMNNAME_DateExtension, DateExtension);
	}

	/** Get DateExtension.
		@return DateExtension	  */
	public Timestamp getDateExtension () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateExtension);
	}

	/** Set datereception.
		@param datereception datereception	  */
	public void setdatereception (Timestamp datereception)
	{
		set_Value (COLUMNNAME_datereception, datereception);
	}

	/** Get datereception.
		@return datereception	  */
	public Timestamp getdatereception () 
	{
		return (Timestamp)get_Value(COLUMNNAME_datereception);
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

	/** Set Date Start.
		@param DateStart 
		Date Start for this Order
	  */
	public void setDateStart (Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	/** Get Date Start.
		@return Date Start for this Order
	  */
	public Timestamp getDateStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStart);
	}

	/** Set DateVoucher.
		@param DateVoucher DateVoucher	  */
	public void setDateVoucher (Timestamp DateVoucher)
	{
		set_Value (COLUMNNAME_DateVoucher, DateVoucher);
	}

	/** Get DateVoucher.
		@return DateVoucher	  */
	public Timestamp getDateVoucher () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateVoucher);
	}

	/** Set DateVoucher1.
		@param DateVoucher1 DateVoucher1	  */
	public void setDateVoucher1 (Timestamp DateVoucher1)
	{
		set_Value (COLUMNNAME_DateVoucher1, DateVoucher1);
	}

	/** Get DateVoucher1.
		@return DateVoucher1	  */
	public Timestamp getDateVoucher1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateVoucher1);
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

	/** Set Description3.
		@param Description3 Description3	  */
	public void setDescription3 (String Description3)
	{
		set_Value (COLUMNNAME_Description3, Description3);
	}

	/** Get Description3.
		@return Description3	  */
	public String getDescription3 () 
	{
		return (String)get_Value(COLUMNNAME_Description3);
	}

	/** Set Description4.
		@param Description4 Description4	  */
	public void setDescription4 (String Description4)
	{
		set_Value (COLUMNNAME_Description4, Description4);
	}

	/** Get Description4.
		@return Description4	  */
	public String getDescription4 () 
	{
		return (String)get_Value(COLUMNNAME_Description4);
	}

	/** Set Digito.
		@param Digito 
		Digit for verify RUT information
	  */
	public void setDigito (String Digito)
	{
		set_Value (COLUMNNAME_Digito, Digito);
	}

	/** Get Digito.
		@return Digit for verify RUT information
	  */
	public String getDigito () 
	{
		return (String)get_Value(COLUMNNAME_Digito);
	}

	/** Set Digito1.
		@param Digito1 
		Digit for verify RUT information
	  */
	public void setDigito1 (String Digito1)
	{
		set_Value (COLUMNNAME_Digito1, Digito1);
	}

	/** Get Digito1.
		@return Digit for verify RUT information
	  */
	public String getDigito1 () 
	{
		return (String)get_Value(COLUMNNAME_Digito1);
	}

	/** Set Performance Bond ID.
		@param DM_PerformanceBond_ID Performance Bond ID	  */
	public void setDM_PerformanceBond_ID (int DM_PerformanceBond_ID)
	{
		if (DM_PerformanceBond_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DM_PerformanceBond_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DM_PerformanceBond_ID, Integer.valueOf(DM_PerformanceBond_ID));
	}

	/** Get Performance Bond ID.
		@return Performance Bond ID	  */
	public int getDM_PerformanceBond_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DM_PerformanceBond_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	/** Gestión Analista = GA */
	public static final String DOCSTATUS_GestionAnalista = "GA";
	/** Solicitud OC = SO */
	public static final String DOCSTATUS_SolicitudOC = "SO";
	/** Solicitud Requiriente = SR */
	public static final String DOCSTATUS_SolicitudRequiriente = "SR";
	/** Autorizado Coordinación Pago = AO */
	public static final String DOCSTATUS_AutorizadoCoordinacionPago = "AO";
	/** Autorizado Encargado Unidad Requirente = A2 */
	public static final String DOCSTATUS_AutorizadoEncargadoUnidadRequirente = "A2";
	/** Autorizado Unidad = AU */
	public static final String DOCSTATUS_AutorizadoUnidad = "AU";
	/** Asignacion Analista = AN */
	public static final String DOCSTATUS_AsignacionAnalista = "AN";
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
	/** Autoriza Ministro = AM */
	public static final String DOCSTATUS_AutorizaMinistro = "AM";
	/** Propuesta Revisada = PP */
	public static final String DOCSTATUS_PropuestaRevisada = "PP";
	/** Revision Contraparte Tecnica = QA */
	public static final String DOCSTATUS_RevisionContraparteTecnica = "QA";
	/** Firma Contraparte Tecnica = QC */
	public static final String DOCSTATUS_FirmaContraparteTecnica = "QC";
	/** Publica Intencion Compra = QF */
	public static final String DOCSTATUS_PublicaIntencionCompra = "QF";
	/** Revision de Evaluacion = QG */
	public static final String DOCSTATUS_RevisionDeEvaluacion = "QG";
	/** Revision Profesional . = QH */
	public static final String DOCSTATUS_RevisionProfesional = "QH";
	/** Decision Compra Privada = QI */
	public static final String DOCSTATUS_DecisionCompraPrivada = "QI";
	/** Contrato Revisado = QM */
	public static final String DOCSTATUS_ContratoRevisado = "QM";
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
	/** Publica Bases de Licitacion = QW */
	public static final String DOCSTATUS_PublicaBasesDeLicitacion = "QW";
	/** Revision Licitacion = QX */
	public static final String DOCSTATUS_RevisionLicitacion = "QX";
	/** Revision Encargado . = QY */
	public static final String DOCSTATUS_RevisionEncargado = "QY";
	/** Autoriza Jefe Admin . = QZ */
	public static final String DOCSTATUS_AutorizaJefeAdmin = "QZ";
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
	public static final String DOCSTATUS_SolicitudReasignacion = "SA";
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

	/** Set Document No1.
		@param DocumentNo1 
		Document sequence number of the document
	  */
	public void setDocumentNo1 (String DocumentNo1)
	{
		set_Value (COLUMNNAME_DocumentNo1, DocumentNo1);
	}

	/** Get Document No1.
		@return Document sequence number of the document
	  */
	public String getDocumentNo1 () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo1);
	}

	/** Set Document Type.
		@param DocumentType 
		Document Type
	  */
	public void setDocumentType (String DocumentType)
	{
		set_Value (COLUMNNAME_DocumentType, DocumentType);
	}

	/** Get Document Type.
		@return Document Type
	  */
	public String getDocumentType () 
	{
		return (String)get_Value(COLUMNNAME_DocumentType);
	}

	/** Set DocumentType2.
		@param DocumentType2 DocumentType2	  */
	public void setDocumentType2 (String DocumentType2)
	{
		set_Value (COLUMNNAME_DocumentType2, DocumentType2);
	}

	/** Get DocumentType2.
		@return DocumentType2	  */
	public String getDocumentType2 () 
	{
		return (String)get_Value(COLUMNNAME_DocumentType2);
	}

	/** Set Extension.
		@param Extension Extension	  */
	public void setExtension (boolean Extension)
	{
		set_Value (COLUMNNAME_Extension, Boolean.valueOf(Extension));
	}

	/** Get Extension.
		@return Extension	  */
	public boolean isExtension () 
	{
		Object oo = get_Value(COLUMNNAME_Extension);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set FirstDateReminder.
		@param FirstDateReminder FirstDateReminder	  */
	public void setFirstDateReminder (Timestamp FirstDateReminder)
	{
		set_Value (COLUMNNAME_FirstDateReminder, FirstDateReminder);
	}

	/** Get FirstDateReminder.
		@return FirstDateReminder	  */
	public Timestamp getFirstDateReminder () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FirstDateReminder);
	}

	/** Set Location.
		@param Location Location	  */
	public void setLocation (String Location)
	{
		set_Value (COLUMNNAME_Location, Location);
	}

	/** Get Location.
		@return Location	  */
	public String getLocation () 
	{
		return (String)get_Value(COLUMNNAME_Location);
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

	/** Set Name1.
		@param Name1 Name1	  */
	public void setName1 (String Name1)
	{
		set_Value (COLUMNNAME_Name1, Name1);
	}

	/** Get Name1.
		@return Name1	  */
	public String getName1 () 
	{
		return (String)get_Value(COLUMNNAME_Name1);
	}

	/** Set NumberOffice.
		@param NumberOffice NumberOffice	  */
	public void setNumberOffice (BigDecimal NumberOffice)
	{
		set_Value (COLUMNNAME_NumberOffice, NumberOffice);
	}

	/** Get NumberOffice.
		@return NumberOffice	  */
	public BigDecimal getNumberOffice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NumberOffice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set NumberTransport.
		@param NumberTransport NumberTransport	  */
	public void setNumberTransport (BigDecimal NumberTransport)
	{
		set_Value (COLUMNNAME_NumberTransport, NumberTransport);
	}

	/** Get NumberTransport.
		@return NumberTransport	  */
	public BigDecimal getNumberTransport () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NumberTransport);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set NumberVoucher.
		@param NumberVoucher NumberVoucher	  */
	public void setNumberVoucher (BigDecimal NumberVoucher)
	{
		set_Value (COLUMNNAME_NumberVoucher, NumberVoucher);
	}

	/** Get NumberVoucher.
		@return NumberVoucher	  */
	public BigDecimal getNumberVoucher () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NumberVoucher);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set NumberVoucher1.
		@param NumberVoucher1 NumberVoucher1	  */
	public void setNumberVoucher1 (BigDecimal NumberVoucher1)
	{
		set_Value (COLUMNNAME_NumberVoucher1, NumberVoucher1);
	}

	/** Get NumberVoucher1.
		@return NumberVoucher1	  */
	public BigDecimal getNumberVoucher1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NumberVoucher1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	public void setPhone (String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	public String getPhone () 
	{
		return (String)get_Value(COLUMNNAME_Phone);
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

	/** Set resolutionnumber.
		@param resolutionnumber resolutionnumber	  */
	public void setresolutionnumber (String resolutionnumber)
	{
		set_Value (COLUMNNAME_resolutionnumber, resolutionnumber);
	}

	/** Get resolutionnumber.
		@return resolutionnumber	  */
	public String getresolutionnumber () 
	{
		return (String)get_Value(COLUMNNAME_resolutionnumber);
	}

	/** Set Rut1.
		@param Rut1 
		Rut of person what delivery
	  */
	public void setRut1 (String Rut1)
	{
		set_Value (COLUMNNAME_Rut1, Rut1);
	}

	/** Get Rut1.
		@return Rut of person what delivery
	  */
	public String getRut1 () 
	{
		return (String)get_Value(COLUMNNAME_Rut1);
	}

	/** Set SecondDateReminder.
		@param SecondDateReminder SecondDateReminder	  */
	public void setSecondDateReminder (Timestamp SecondDateReminder)
	{
		set_Value (COLUMNNAME_SecondDateReminder, SecondDateReminder);
	}

	/** Get SecondDateReminder.
		@return SecondDateReminder	  */
	public Timestamp getSecondDateReminder () 
	{
		return (Timestamp)get_Value(COLUMNNAME_SecondDateReminder);
	}

	/** Set SendTicket.
		@param SendTicket SendTicket	  */
	public void setSendTicket (boolean SendTicket)
	{
		set_Value (COLUMNNAME_SendTicket, Boolean.valueOf(SendTicket));
	}

	/** Get SendTicket.
		@return SendTicket	  */
	public boolean isSendTicket () 
	{
		Object oo = get_Value(COLUMNNAME_SendTicket);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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