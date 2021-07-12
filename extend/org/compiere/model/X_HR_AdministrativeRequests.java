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

/** Generated Model for HR_AdministrativeRequests
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_HR_AdministrativeRequests extends PO implements I_HR_AdministrativeRequests, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171110L;

    /** Standard Constructor */
    public X_HR_AdministrativeRequests (Properties ctx, int HR_AdministrativeRequests_ID, String trxName)
    {
      super (ctx, HR_AdministrativeRequests_ID, trxName);
      /** if (HR_AdministrativeRequests_ID == 0)
        {
			setC_BPartner_ID (0);
			setDocStatus (null);
			setDocumentNo (null);
			setHR_AdministrativeRequests_ID (0);
			setProcessed (false);
			setRequestType (null);
        } */
    }

    /** Load Constructor */
    public X_HR_AdministrativeRequests (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_AdministrativeRequests[")
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

	/** Set dateendrequest.
		@param dateendrequest dateendrequest	  */
	public void setdateendrequest (Timestamp dateendrequest)
	{
		set_Value (COLUMNNAME_dateendrequest, dateendrequest);
	}

	/** Get dateendrequest.
		@return dateendrequest	  */
	public Timestamp getdateendrequest () 
	{
		return (Timestamp)get_Value(COLUMNNAME_dateendrequest);
	}

	/** Set datestartrequest.
		@param datestartrequest datestartrequest	  */
	public void setdatestartrequest (Timestamp datestartrequest)
	{
		set_Value (COLUMNNAME_datestartrequest, datestartrequest);
	}

	/** Get datestartrequest.
		@return datestartrequest	  */
	public Timestamp getdatestartrequest () 
	{
		return (Timestamp)get_Value(COLUMNNAME_datestartrequest);
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

	public I_DM_Document getDM_Document() throws RuntimeException
    {
		return (I_DM_Document)MTable.get(getCtx(), I_DM_Document.Table_Name)
			.getPO(getDM_Document_ID(), get_TrxName());	}

	/** Set DM_Document.
		@param DM_Document_ID DM_Document	  */
	public void setDM_Document_ID (int DM_Document_ID)
	{
		if (DM_Document_ID < 1) 
			set_Value (COLUMNNAME_DM_Document_ID, null);
		else 
			set_Value (COLUMNNAME_DM_Document_ID, Integer.valueOf(DM_Document_ID));
	}

	/** Get DM_Document.
		@return DM_Document	  */
	public int getDM_Document_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DM_Document_ID);
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
	/** Autorizado Coordinación Pago = AO */
	public static final String DOCSTATUS_AutorizadoCoordinacionPago = "AO";
	/** Autorizado Of Partes = A1 */
	public static final String DOCSTATUS_AutorizadoOfPartes = "A1";
	/** Autorizado Encargado Unidad Requirente = A2 */
	public static final String DOCSTATUS_AutorizadoEncargadoUnidadRequirente = "A2";
	/** Autorizado Encargado Boleta = A3 */
	public static final String DOCSTATUS_AutorizadoEncargadoBoleta = "A3";
	/** Autorizado Unidad = AU */
	public static final String DOCSTATUS_AutorizadoUnidad = "AU";
	/** Asignacion Analista = AN */
	public static final String DOCSTATUS_AsignacionAnalista = "AN";
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

	/** Set HR_AdministrativeRequests.
		@param HR_AdministrativeRequests_ID HR_AdministrativeRequests	  */
	public void setHR_AdministrativeRequests_ID (int HR_AdministrativeRequests_ID)
	{
		if (HR_AdministrativeRequests_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_AdministrativeRequests_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_AdministrativeRequests_ID, Integer.valueOf(HR_AdministrativeRequests_ID));
	}

	/** Get HR_AdministrativeRequests.
		@return HR_AdministrativeRequests	  */
	public int getHR_AdministrativeRequests_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_AdministrativeRequests_ID);
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

	/** Set payable.
		@param payable payable	  */
	public void setpayable (int payable)
	{
		set_Value (COLUMNNAME_payable, Integer.valueOf(payable));
	}

	/** Get payable.
		@return payable	  */
	public int getpayable () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_payable);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** RequestType AD_Reference_ID=2000108 */
	public static final int REQUESTTYPE_AD_Reference_ID=2000108;
	/** Permisos Administrativos = PAD */
	public static final String REQUESTTYPE_PermisosAdministrativos = "PAD";
	/** Cometido = CMT */
	public static final String REQUESTTYPE_Cometido = "CMT";
	/** Solicitud Horas Extra a Pago = SHE */
	public static final String REQUESTTYPE_SolicitudHorasExtraAPago = "SHE";
	/** Solicitud Vacaciones = SVC */
	public static final String REQUESTTYPE_SolicitudVacaciones = "SVC";
	/** Solicitud Horas Extra a Tiempo Compensado = SHT */
	public static final String REQUESTTYPE_SolicitudHorasExtraATiempoCompensado = "SHT";
	/** Correccion asistencia y puntualidad = CAP */
	public static final String REQUESTTYPE_CorreccionAsistenciaYPuntualidad = "CAP";
	/** Permiso Paternal = PAT */
	public static final String REQUESTTYPE_PermisoPaternal = "PAT";
	/** Permiso por Fallecimiento = FAL */
	public static final String REQUESTTYPE_PermisoPorFallecimiento = "FAL";
	/** Permiso Compensatorio = COM */
	public static final String REQUESTTYPE_PermisoCompensatorio = "COM";
	/** Permiso Sin Goce Nacional = GOC */
	public static final String REQUESTTYPE_PermisoSinGoceNacional = "GOC";
	/** Acumulación = ACU */
	public static final String REQUESTTYPE_Acumulacion = "ACU";
	/** Set Request Type.
		@param RequestType Request Type	  */
	public void setRequestType (String RequestType)
	{

		set_Value (COLUMNNAME_RequestType, RequestType);
	}

	/** Get Request Type.
		@return Request Type	  */
	public String getRequestType () 
	{
		return (String)get_Value(COLUMNNAME_RequestType);
	}
}