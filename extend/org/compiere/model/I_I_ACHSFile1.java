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

/** Generated Interface for I_ACHSFile1
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_I_ACHSFile1 
{

    /** TableName=I_ACHSFile1 */
    public static final String Table_Name = "I_ACHSFile1";

    /** AD_Table_ID=2000094 */
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

    /** Column name BP_Empresa */
    public static final String COLUMNNAME_BP_Empresa = "BP_Empresa";

	/** Set BP_Empresa	  */
	public void setBP_Empresa (String BP_Empresa);

	/** Get BP_Empresa	  */
	public String getBP_Empresa();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

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

    /** Column name Episodio */
    public static final String COLUMNNAME_Episodio = "Episodio";

	/** Set Episodio	  */
	public void setEpisodio (String Episodio);

	/** Get Episodio	  */
	public String getEpisodio();

    /** Column name FechaAtencion */
    public static final String COLUMNNAME_FechaAtencion = "FechaAtencion";

	/** Set FechaAtencion	  */
	public void setFechaAtencion (Timestamp FechaAtencion);

	/** Get FechaAtencion	  */
	public Timestamp getFechaAtencion();

    /** Column name I_ACHSFile1_ID */
    public static final String COLUMNNAME_I_ACHSFile1_ID = "I_ACHSFile1_ID";

	/** Set I_ACHSFile1 ID	  */
	public void setI_ACHSFile1_ID (int I_ACHSFile1_ID);

	/** Get I_ACHSFile1 ID	  */
	public int getI_ACHSFile1_ID();

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

    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/** Set Line Amount.
	  * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt);

	/** Get Line Amount.
	  * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name NombreEmpresa */
    public static final String COLUMNNAME_NombreEmpresa = "NombreEmpresa";

	/** Set NombreEmpresa	  */
	public void setNombreEmpresa (String NombreEmpresa);

	/** Get NombreEmpresa	  */
	public String getNombreEmpresa();

    /** Column name Prestacion */
    public static final String COLUMNNAME_Prestacion = "Prestacion";

	/** Set Prestacion	  */
	public void setPrestacion (String Prestacion);

	/** Get Prestacion	  */
	public String getPrestacion();

    /** Column name PrestacionGlosa */
    public static final String COLUMNNAME_PrestacionGlosa = "PrestacionGlosa";

	/** Set PrestacionGlosa	  */
	public void setPrestacionGlosa (String PrestacionGlosa);

	/** Get PrestacionGlosa	  */
	public String getPrestacionGlosa();

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
