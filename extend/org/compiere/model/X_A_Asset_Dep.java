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

/** Generated Model for A_Asset_Dep
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_A_Asset_Dep extends PO implements I_A_Asset_Dep, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171211L;

    /** Standard Constructor */
    public X_A_Asset_Dep (Properties ctx, int A_Asset_Dep_ID, String trxName)
    {
      super (ctx, A_Asset_Dep_ID, trxName);
      /** if (A_Asset_Dep_ID == 0)
        {
			setA_Asset_Dep_ID (0);
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_A_Asset_Dep (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_A_Asset_Dep[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set A_Asset_Dep ID.
		@param A_Asset_Dep_ID A_Asset_Dep ID	  */
	public void setA_Asset_Dep_ID (int A_Asset_Dep_ID)
	{
		if (A_Asset_Dep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Dep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Dep_ID, Integer.valueOf(A_Asset_Dep_ID));
	}

	/** Get A_Asset_Dep ID.
		@return A_Asset_Dep ID	  */
	public int getA_Asset_Dep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Dep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_A_Asset_Group getA_Asset_Group() throws RuntimeException
    {
		return (org.compiere.model.I_A_Asset_Group)MTable.get(getCtx(), org.compiere.model.I_A_Asset_Group.Table_Name)
			.getPO(getA_Asset_Group_ID(), get_TrxName());	}

	/** Set Asset Group.
		@param A_Asset_Group_ID 
		Group of Assets
	  */
	public void setA_Asset_Group_ID (int A_Asset_Group_ID)
	{
		if (A_Asset_Group_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_Group_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_Group_ID, Integer.valueOf(A_Asset_Group_ID));
	}

	/** Get Asset Group.
		@return Group of Assets
	  */
	public int getA_Asset_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_A_Asset getA_Asset() throws RuntimeException
    {
		return (org.compiere.model.I_A_Asset)MTable.get(getCtx(), org.compiere.model.I_A_Asset.Table_Name)
			.getPO(getA_Asset_ID(), get_TrxName());	}

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set corrected.
		@param corrected corrected	  */
	public void setcorrected (boolean corrected)
	{
		set_Value (COLUMNNAME_corrected, Boolean.valueOf(corrected));
	}

	/** Get corrected.
		@return corrected	  */
	public boolean iscorrected () 
	{
		Object oo = get_Value(COLUMNNAME_corrected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** deptype AD_Reference_ID=2000047 */
	public static final int DEPTYPE_AD_Reference_ID=2000047;
	/** Correccion = COR */
	public static final String DEPTYPE_Correccion = "COR";
	/** Depreciacion = DEP */
	public static final String DEPTYPE_Depreciacion = "DEP";
	/** Deterioro = DET */
	public static final String DEPTYPE_Deterioro = "DET";
	/** Reevaluación = REV */
	public static final String DEPTYPE_Reevaluacion = "REV";
	/** Venta = SAL */
	public static final String DEPTYPE_Venta = "SAL";
	/** Venta Consolidada = VCO */
	public static final String DEPTYPE_VentaConsolidada = "VCO";
	//desde DPP
	/** Deteriodo = DET */
	public static final String DEPTYPE_Deteriodo = "DET";
	public static final String DEPTYPE_VentaActivo = "SAL";
	public static final String DEPTYPE_CorrecionMonetaria = "COR";


	/** Set deptype.
		@param deptype deptype	  */
	public void setDepType (String DepType)
	{

		set_Value (COLUMNNAME_DepType, DepType);
	}

	/** Get deptype.
		@return deptype	  */
	public String getDepType () 
	{
		return (String)get_Value(COLUMNNAME_DepType);
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
	/** Autorizado Presupuesto = AB */
	public static final String DOCSTATUS_AutorizadoPresupuesto = "AB";
	/** Aprobado Coordinación = AC */
	public static final String DOCSTATUS_AprobadoCoordinacion = "AC";
	/** Autorizado Jefatura = AJ */
	public static final String DOCSTATUS_AutorizadoJefatura = "AJ";
	/** Autorizado Jefe RRFF = AR */
	public static final String DOCSTATUS_AutorizadoJefeRRFF = "AR";
	/** Gestión Analista = GA */
	public static final String DOCSTATUS_GestionAnalista = "GA";
	/** Solicitud OC = SO */
	public static final String DOCSTATUS_SolicitudOC = "SO";
	/** Solicitud Requiriente = SR */
	public static final String DOCSTATUS_SolicitudRequiriente = "SR";
	/** Factura Recepcionada = FA */
	public static final String DOCSTATUS_FacturaRecepcionada = "FA";
	/** Autorizado Analista = AN */
	public static final String DOCSTATUS_AutorizadoAnalista = "AN";
	/** Autorizado Coordinación Pago = AO */
	public static final String DOCSTATUS_AutorizadoCoordinacionPago = "AO";
	/** Autorizado Of de Partes = A1 */
	public static final String DOCSTATUS_AutorizadoOfDePartes = "A1";
	/** Autorizado Encargado Unidad Requirente = A2 */
	public static final String DOCSTATUS_AutorizadoEncargadoUnidadRequirente = "A2";
	/** Autorizado Encargado Boleta = A3 */
	public static final String DOCSTATUS_AutorizadoEncargadoBoleta = "A3";
	/** Autorizado Unidad = AU */
	public static final String DOCSTATUS_AutorizadoUnidad = "AU";
	/** Asignación Analista = AS */
	public static final String DOCSTATUS_AsignacionAnalista = "AS";
	/** Derivado Contraparte Técnica = DC */
	public static final String DOCSTATUS_DerivadoContraparteTecnica = "DC";
	/** Aprobado Encargado = AE */
	public static final String DOCSTATUS_AprobadoEncargado = "AE";
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

	public org.compiere.model.I_GL_JournalBatch getGL_JournalBatch() throws RuntimeException
    {
		return (org.compiere.model.I_GL_JournalBatch)MTable.get(getCtx(), org.compiere.model.I_GL_JournalBatch.Table_Name)
			.getPO(getGL_JournalBatch_ID(), get_TrxName());	}

	/** Set Journal Batch.
		@param GL_JournalBatch_ID 
		General Ledger Journal Batch
	  */
	public void setGL_JournalBatch_ID (int GL_JournalBatch_ID)
	{
		if (GL_JournalBatch_ID < 1) 
			set_Value (COLUMNNAME_GL_JournalBatch_ID, null);
		else 
			set_Value (COLUMNNAME_GL_JournalBatch_ID, Integer.valueOf(GL_JournalBatch_ID));
	}

	/** Get Journal Batch.
		@return General Ledger Journal Batch
	  */
	public int getGL_JournalBatch_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_JournalBatch_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set In Possession.
		@param IsInPosession 
		The asset is in the possession of the organization
	  */
	public void setIsInPosession (boolean IsInPosession)
	{
		set_Value (COLUMNNAME_IsInPosession, Boolean.valueOf(IsInPosession));
	}

	/** Get In Possession.
		@return The asset is in the possession of the organization
	  */
	public boolean isInPosession () 
	{
		Object oo = get_Value(COLUMNNAME_IsInPosession);
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

	/** Set Rate.
		@param Rate 
		Rate or Tax or Exchange
	  */
	public void setRate (BigDecimal Rate)
	{
		set_Value (COLUMNNAME_Rate, Rate);
	}

	/** Get Rate.
		@return Rate or Tax or Exchange
	  */
	public BigDecimal getRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Rate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set sameyear.
		@param sameyear sameyear	  */
	public void setSameYear (boolean SameYear)
	{
		set_Value (COLUMNNAME_SameYear, Boolean.valueOf(SameYear));
	}

	/** Get sameyear.
		@return sameyear	  */
	public boolean isSameYear () 
	{
		Object oo = get_Value(COLUMNNAME_SameYear);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set void.
		@param void void	  */
	public void setVoid (boolean Void)
	{
		set_Value (COLUMNNAME_Void, Boolean.valueOf(Void));
	}

	/** Get void.
		@return void	  */
	public boolean isVoid () 
	{
		Object oo = get_Value(COLUMNNAME_Void);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set year.
		@param year year	  */
	public void setyear (boolean year)
	{
		set_Value (COLUMNNAME_year, Boolean.valueOf(year));
	}

	/** Get year.
		@return year	  */
	public boolean isyear () 
	{
		Object oo = get_Value(COLUMNNAME_year);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}