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

/** Generated Model for I_ACHSFile3
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_I_ACHSFile3 extends PO implements I_I_ACHSFile3, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200819L;

    /** Standard Constructor */
    public X_I_ACHSFile3 (Properties ctx, int I_ACHSFile3_ID, String trxName)
    {
      super (ctx, I_ACHSFile3_ID, trxName);
      /** if (I_ACHSFile3_ID == 0)
        {
			setI_ACHSFile3_ID (0);
        } */
    }

    /** Load Constructor */
    public X_I_ACHSFile3 (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_I_ACHSFile3[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CentroDeCosto.
		@param CentroDeCosto CentroDeCosto	  */
	public void setCentroDeCosto (String CentroDeCosto)
	{
		set_Value (COLUMNNAME_CentroDeCosto, CentroDeCosto);
	}

	/** Get CentroDeCosto.
		@return CentroDeCosto	  */
	public String getCentroDeCosto () 
	{
		return (String)get_Value(COLUMNNAME_CentroDeCosto);
	}

	/** Set FechaAtencion.
		@param FechaAtencion FechaAtencion	  */
	public void setFechaAtencion (Timestamp FechaAtencion)
	{
		set_Value (COLUMNNAME_FechaAtencion, FechaAtencion);
	}

	/** Get FechaAtencion.
		@return FechaAtencion	  */
	public Timestamp getFechaAtencion () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FechaAtencion);
	}

	/** Set I_ACHSFile3 ID.
		@param I_ACHSFile3_ID I_ACHSFile3 ID	  */
	public void setI_ACHSFile3_ID (int I_ACHSFile3_ID)
	{
		if (I_ACHSFile3_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_ACHSFile3_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_ACHSFile3_ID, Integer.valueOf(I_ACHSFile3_ID));
	}

	/** Get I_ACHSFile3 ID.
		@return I_ACHSFile3 ID	  */
	public int getI_ACHSFile3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_ACHSFile3_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mail_Solicitante.
		@param Mail_Solicitante Mail_Solicitante	  */
	public void setMail_Solicitante (String Mail_Solicitante)
	{
		set_Value (COLUMNNAME_Mail_Solicitante, Mail_Solicitante);
	}

	/** Get Mail_Solicitante.
		@return Mail_Solicitante	  */
	public String getMail_Solicitante () 
	{
		return (String)get_Value(COLUMNNAME_Mail_Solicitante);
	}

	/** Set Nombre_Paciente.
		@param Nombre_Paciente Nombre_Paciente	  */
	public void setNombre_Paciente (String Nombre_Paciente)
	{
		set_Value (COLUMNNAME_Nombre_Paciente, Nombre_Paciente);
	}

	/** Get Nombre_Paciente.
		@return Nombre_Paciente	  */
	public String getNombre_Paciente () 
	{
		return (String)get_Value(COLUMNNAME_Nombre_Paciente);
	}

	/** Set Nombre_Solicitante.
		@param Nombre_Solicitante Nombre_Solicitante	  */
	public void setNombre_Solicitante (String Nombre_Solicitante)
	{
		set_Value (COLUMNNAME_Nombre_Solicitante, Nombre_Solicitante);
	}

	/** Get Nombre_Solicitante.
		@return Nombre_Solicitante	  */
	public String getNombre_Solicitante () 
	{
		return (String)get_Value(COLUMNNAME_Nombre_Solicitante);
	}

	/** Set NombreEmpresa.
		@param NombreEmpresa NombreEmpresa	  */
	public void setNombreEmpresa (String NombreEmpresa)
	{
		set_Value (COLUMNNAME_NombreEmpresa, NombreEmpresa);
	}

	/** Get NombreEmpresa.
		@return NombreEmpresa	  */
	public String getNombreEmpresa () 
	{
		return (String)get_Value(COLUMNNAME_NombreEmpresa);
	}

	/** Set Rut_Empresa.
		@param Rut_Empresa Rut_Empresa	  */
	public void setRut_Empresa (String Rut_Empresa)
	{
		set_Value (COLUMNNAME_Rut_Empresa, Rut_Empresa);
	}

	/** Get Rut_Empresa.
		@return Rut_Empresa	  */
	public String getRut_Empresa () 
	{
		return (String)get_Value(COLUMNNAME_Rut_Empresa);
	}

	/** Set Rut_Paciente.
		@param Rut_Paciente Rut_Paciente	  */
	public void setRut_Paciente (String Rut_Paciente)
	{
		set_Value (COLUMNNAME_Rut_Paciente, Rut_Paciente);
	}

	/** Get Rut_Paciente.
		@return Rut_Paciente	  */
	public String getRut_Paciente () 
	{
		return (String)get_Value(COLUMNNAME_Rut_Paciente);
	}

	/** Set Rut_Solicitante.
		@param Rut_Solicitante Rut_Solicitante	  */
	public void setRut_Solicitante (String Rut_Solicitante)
	{
		set_Value (COLUMNNAME_Rut_Solicitante, Rut_Solicitante);
	}

	/** Get Rut_Solicitante.
		@return Rut_Solicitante	  */
	public String getRut_Solicitante () 
	{
		return (String)get_Value(COLUMNNAME_Rut_Solicitante);
	}

	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	public void setStatus (String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	public String getStatus () 
	{
		return (String)get_Value(COLUMNNAME_Status);
	}

	/** Set Sucursal.
		@param Sucursal Sucursal	  */
	public void setSucursal (String Sucursal)
	{
		set_Value (COLUMNNAME_Sucursal, Sucursal);
	}

	/** Get Sucursal.
		@return Sucursal	  */
	public String getSucursal () 
	{
		return (String)get_Value(COLUMNNAME_Sucursal);
	}
}