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

/** Generated Model for DM_BG_History
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_DM_BG_History extends PO implements I_DM_BG_History, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180426L;

    /** Standard Constructor */
    public X_DM_BG_History (Properties ctx, int DM_BG_History_ID, String trxName)
    {
      super (ctx, DM_BG_History_ID, trxName);
      /** if (DM_BG_History_ID == 0)
        {
			setDM_BG_History_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DM_BG_History (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_DM_BG_History[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set BG_Updated.
		@param BG_Updated BG_Updated	  */
	public void setBG_Updated (Timestamp BG_Updated)
	{
		set_Value (COLUMNNAME_BG_Updated, BG_Updated);
	}

	/** Get BG_Updated.
		@return BG_Updated	  */
	public Timestamp getBG_Updated () 
	{
		return (Timestamp)get_Value(COLUMNNAME_BG_Updated);
	}

	/** Set bg_updatedby.
		@param bg_updatedby bg_updatedby	  */
	public void setbg_updatedby (int bg_updatedby)
	{
		set_Value (COLUMNNAME_bg_updatedby, Integer.valueOf(bg_updatedby));
	}

	/** Get bg_updatedby.
		@return bg_updatedby	  */
	public int getbg_updatedby () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_bg_updatedby);
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

	/** Set DM_BG_History ID.
		@param DM_BG_History_ID DM_BG_History ID	  */
	public void setDM_BG_History_ID (int DM_BG_History_ID)
	{
		if (DM_BG_History_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DM_BG_History_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DM_BG_History_ID, Integer.valueOf(DM_BG_History_ID));
	}

	/** Get DM_BG_History ID.
		@return DM_BG_History ID	  */
	public int getDM_BG_History_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DM_BG_History_ID);
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

	/** Set docstatusold.
		@param docstatusold docstatusold	  */
	public void setdocstatusold (String docstatusold)
	{
		set_Value (COLUMNNAME_docstatusold, docstatusold);
	}

	/** Get docstatusold.
		@return docstatusold	  */
	public String getdocstatusold () 
	{
		return (String)get_Value(COLUMNNAME_docstatusold);
	}
}