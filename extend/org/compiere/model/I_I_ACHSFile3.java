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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for I_ACHSFile3
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_I_ACHSFile3 
{

    /** TableName=I_ACHSFile3 */
    public static final String Table_Name = "I_ACHSFile3";

    /** AD_Table_ID=2000095 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name CentroDeCosto */
    public static final String COLUMNNAME_CentroDeCosto = "CentroDeCosto";

	/** Set CentroDeCosto	  */
	public void setCentroDeCosto (String CentroDeCosto);

	/** Get CentroDeCosto	  */
	public String getCentroDeCosto();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name FechaAtencion */
    public static final String COLUMNNAME_FechaAtencion = "FechaAtencion";

	/** Set FechaAtencion	  */
	public void setFechaAtencion (Timestamp FechaAtencion);

	/** Get FechaAtencion	  */
	public Timestamp getFechaAtencion();

    /** Column name I_ACHSFile3_ID */
    public static final String COLUMNNAME_I_ACHSFile3_ID = "I_ACHSFile3_ID";

	/** Set I_ACHSFile3 ID	  */
	public void setI_ACHSFile3_ID (int I_ACHSFile3_ID);

	/** Get I_ACHSFile3 ID	  */
	public int getI_ACHSFile3_ID();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name Mail_Solicitante */
    public static final String COLUMNNAME_Mail_Solicitante = "Mail_Solicitante";

	/** Set Mail_Solicitante	  */
	public void setMail_Solicitante (String Mail_Solicitante);

	/** Get Mail_Solicitante	  */
	public String getMail_Solicitante();

    /** Column name Nombre_Paciente */
    public static final String COLUMNNAME_Nombre_Paciente = "Nombre_Paciente";

	/** Set Nombre_Paciente	  */
	public void setNombre_Paciente (String Nombre_Paciente);

	/** Get Nombre_Paciente	  */
	public String getNombre_Paciente();

    /** Column name Nombre_Solicitante */
    public static final String COLUMNNAME_Nombre_Solicitante = "Nombre_Solicitante";

	/** Set Nombre_Solicitante	  */
	public void setNombre_Solicitante (String Nombre_Solicitante);

	/** Get Nombre_Solicitante	  */
	public String getNombre_Solicitante();

    /** Column name NombreEmpresa */
    public static final String COLUMNNAME_NombreEmpresa = "NombreEmpresa";

	/** Set NombreEmpresa	  */
	public void setNombreEmpresa (String NombreEmpresa);

	/** Get NombreEmpresa	  */
	public String getNombreEmpresa();

    /** Column name Rut_Empresa */
    public static final String COLUMNNAME_Rut_Empresa = "Rut_Empresa";

	/** Set Rut_Empresa	  */
	public void setRut_Empresa (String Rut_Empresa);

	/** Get Rut_Empresa	  */
	public String getRut_Empresa();

    /** Column name Rut_Paciente */
    public static final String COLUMNNAME_Rut_Paciente = "Rut_Paciente";

	/** Set Rut_Paciente	  */
	public void setRut_Paciente (String Rut_Paciente);

	/** Get Rut_Paciente	  */
	public String getRut_Paciente();

    /** Column name Rut_Solicitante */
    public static final String COLUMNNAME_Rut_Solicitante = "Rut_Solicitante";

	/** Set Rut_Solicitante	  */
	public void setRut_Solicitante (String Rut_Solicitante);

	/** Get Rut_Solicitante	  */
	public String getRut_Solicitante();

    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

	/** Set Status.
	  * Status of the currently running check
	  */
	public void setStatus (String Status);

	/** Get Status.
	  * Status of the currently running check
	  */
	public String getStatus();

    /** Column name Sucursal */
    public static final String COLUMNNAME_Sucursal = "Sucursal";

	/** Set Sucursal	  */
	public void setSucursal (String Sucursal);

	/** Get Sucursal	  */
	public String getSucursal();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
