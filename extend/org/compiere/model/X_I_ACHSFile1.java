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

/** Generated Model for I_ACHSFile1
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_I_ACHSFile1 extends PO implements I_I_ACHSFile1, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200819L;

    /** Standard Constructor */
    public X_I_ACHSFile1 (Properties ctx, int I_ACHSFile1_ID, String trxName)
    {
      super (ctx, I_ACHSFile1_ID, trxName);
      /** if (I_ACHSFile1_ID == 0)
        {
			setI_ACHSFile1_ID (0);
        } */
    }

    /** Load Constructor */
    public X_I_ACHSFile1 (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_I_ACHSFile1[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set BP_Empresa.
		@param BP_Empresa BP_Empresa	  */
	public void setBP_Empresa (String BP_Empresa)
	{
		set_Value (COLUMNNAME_BP_Empresa, BP_Empresa);
	}

	/** Get BP_Empresa.
		@return BP_Empresa	  */
	public String getBP_Empresa () 
	{
		return (String)get_Value(COLUMNNAME_BP_Empresa);
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

	/** Set Episodio.
		@param Episodio Episodio	  */
	public void setEpisodio (String Episodio)
	{
		set_Value (COLUMNNAME_Episodio, Episodio);
	}

	/** Get Episodio.
		@return Episodio	  */
	public String getEpisodio () 
	{
		return (String)get_Value(COLUMNNAME_Episodio);
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

	/** Set I_ACHSFile1 ID.
		@param I_ACHSFile1_ID I_ACHSFile1 ID	  */
	public void setI_ACHSFile1_ID (int I_ACHSFile1_ID)
	{
		if (I_ACHSFile1_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_ACHSFile1_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_ACHSFile1_ID, Integer.valueOf(I_ACHSFile1_ID));
	}

	/** Get I_ACHSFile1 ID.
		@return I_ACHSFile1 ID	  */
	public int getI_ACHSFile1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_ACHSFile1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Line Amount.
		@param LineNetAmt 
		Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Line Amount.
		@return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Prestacion.
		@param Prestacion Prestacion	  */
	public void setPrestacion (String Prestacion)
	{
		set_Value (COLUMNNAME_Prestacion, Prestacion);
	}

	/** Get Prestacion.
		@return Prestacion	  */
	public String getPrestacion () 
	{
		return (String)get_Value(COLUMNNAME_Prestacion);
	}

	/** Set PrestacionGlosa.
		@param PrestacionGlosa PrestacionGlosa	  */
	public void setPrestacionGlosa (String PrestacionGlosa)
	{
		set_Value (COLUMNNAME_PrestacionGlosa, PrestacionGlosa);
	}

	/** Get PrestacionGlosa.
		@return PrestacionGlosa	  */
	public String getPrestacionGlosa () 
	{
		return (String)get_Value(COLUMNNAME_PrestacionGlosa);
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
}