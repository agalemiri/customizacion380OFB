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

/** Generated Model for OFB_Reloj_Control
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_OFB_Reloj_Control extends PO implements I_OFB_Reloj_Control, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20191126L;

    /** Standard Constructor */
    public X_OFB_Reloj_Control (Properties ctx, int OFB_Reloj_Control_ID, String trxName)
    {
      super (ctx, OFB_Reloj_Control_ID, trxName);
      /** if (OFB_Reloj_Control_ID == 0)
        {
			setfecha (new Timestamp( System.currentTimeMillis() ));
			setOFB_Reloj_Control_ID (0);
        } */
    }

    /** Load Constructor */
    public X_OFB_Reloj_Control (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_OFB_Reloj_Control[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set atrasobase.
		@param atrasobase atrasobase	  */
	public void setatrasobase (Timestamp atrasobase)
	{
		set_Value (COLUMNNAME_atrasobase, atrasobase);
	}

	/** Get atrasobase.
		@return atrasobase	  */
	public Timestamp getatrasobase () 
	{
		return (Timestamp)get_Value(COLUMNNAME_atrasobase);
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

	/** Set compensadas.
		@param compensadas compensadas	  */
	public void setcompensadas (int compensadas)
	{
		set_Value (COLUMNNAME_compensadas, Integer.valueOf(compensadas));
	}

	/** Get compensadas.
		@return compensadas	  */
	public int getcompensadas () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_compensadas);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set dia.
		@param dia dia	  */
	public void setdia (String dia)
	{
		set_Value (COLUMNNAME_dia, dia);
	}

	/** Get dia.
		@return dia	  */
	public String getdia () 
	{
		return (String)get_Value(COLUMNNAME_dia);
	}

	/** Set entrada.
		@param entrada entrada	  */
	public void setentrada (Timestamp entrada)
	{
		set_Value (COLUMNNAME_entrada, entrada);
	}

	/** Get entrada.
		@return entrada	  */
	public Timestamp getentrada () 
	{
		return (Timestamp)get_Value(COLUMNNAME_entrada);
	}

	/** Set fecha.
		@param fecha fecha	  */
	public void setfecha (Timestamp fecha)
	{
		set_Value (COLUMNNAME_fecha, fecha);
	}

	/** Get fecha.
		@return fecha	  */
	public Timestamp getfecha () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fecha);
	}

	/** Set horas_trabajadas.
		@param horas_trabajadas horas_trabajadas	  */
	public void sethoras_trabajadas (Timestamp horas_trabajadas)
	{
		set_Value (COLUMNNAME_horas_trabajadas, horas_trabajadas);
	}

	/** Get horas_trabajadas.
		@return horas_trabajadas	  */
	public Timestamp gethoras_trabajadas () 
	{
		return (Timestamp)get_Value(COLUMNNAME_horas_trabajadas);
	}

	/** Set horasmediamannana.
		@param horasmediamannana horasmediamannana	  */
	public void sethorasmediamannana (Timestamp horasmediamannana)
	{
		set_Value (COLUMNNAME_horasmediamannana, horasmediamannana);
	}

	/** Get horasmediamannana.
		@return horasmediamannana	  */
	public Timestamp gethorasmediamannana () 
	{
		return (Timestamp)get_Value(COLUMNNAME_horasmediamannana);
	}

	/** Set incidencias.
		@param incidencias incidencias	  */
	public void setincidencias (String incidencias)
	{
		set_Value (COLUMNNAME_incidencias, incidencias);
	}

	/** Get incidencias.
		@return incidencias	  */
	public String getincidencias () 
	{
		return (String)get_Value(COLUMNNAME_incidencias);
	}

	/** Set OFB_Reloj_Control ID.
		@param OFB_Reloj_Control_ID OFB_Reloj_Control ID	  */
	public void setOFB_Reloj_Control_ID (int OFB_Reloj_Control_ID)
	{
		if (OFB_Reloj_Control_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_OFB_Reloj_Control_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_OFB_Reloj_Control_ID, Integer.valueOf(OFB_Reloj_Control_ID));
	}

	/** Get OFB_Reloj_Control ID.
		@return OFB_Reloj_Control ID	  */
	public int getOFB_Reloj_Control_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OFB_Reloj_Control_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set salida.
		@param salida salida	  */
	public void setsalida (Timestamp salida)
	{
		set_Value (COLUMNNAME_salida, salida);
	}

	/** Get salida.
		@return salida	  */
	public Timestamp getsalida () 
	{
		return (Timestamp)get_Value(COLUMNNAME_salida);
	}
}