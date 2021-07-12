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

/** Generated Model for i_ws1appointment
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_i_ws1appointment extends PO implements I_i_ws1appointment, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200225L;

    /** Standard Constructor */
    public X_i_ws1appointment (Properties ctx, int i_ws1appointment_ID, String trxName)
    {
      super (ctx, i_ws1appointment_ID, trxName);
      /** if (i_ws1appointment_ID == 0)
        {
			seti_ws1appointment_ID (0);
        } */
    }

    /** Load Constructor */
    public X_i_ws1appointment (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_i_ws1appointment[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_PInstance getAD_PInstance() throws RuntimeException
    {
		return (org.compiere.model.I_AD_PInstance)MTable.get(getCtx(), org.compiere.model.I_AD_PInstance.Table_Name)
			.getPO(getAD_PInstance_ID(), get_TrxName());	}

	/** Set Process Instance.
		@param AD_PInstance_ID 
		Instance of the process
	  */
	public void setAD_PInstance_ID (int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
	}

	/** Get Process Instance.
		@return Instance of the process
	  */
	public int getAD_PInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DateTo.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get DateTo.
		@return End date of a date range
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
	}

	/** Set i_ws1appointment ID.
		@param i_ws1appointment_ID i_ws1appointment ID	  */
	public void seti_ws1appointment_ID (int i_ws1appointment_ID)
	{
		if (i_ws1appointment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_i_ws1appointment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_i_ws1appointment_ID, Integer.valueOf(i_ws1appointment_ID));
	}

	/** Get i_ws1appointment ID.
		@return i_ws1appointment ID	  */
	public int geti_ws1appointment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_i_ws1appointment_ID);
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

	/** Set pInstance.
		@param pInstance pInstance	  */
	public void setpInstance (String pInstance)
	{
		set_Value (COLUMNNAME_pInstance, pInstance);
	}

	/** Get pInstance.
		@return pInstance	  */
	public String getpInstance () 
	{
		return (String)get_Value(COLUMNNAME_pInstance);
	}

	/** Set Rut.
		@param Rut Rut	  */
	public void setRut (String Rut)
	{
		set_Value (COLUMNNAME_Rut, Rut);
	}

	/** Get Rut.
		@return Rut	  */
	public String getRut () 
	{
		return (String)get_Value(COLUMNNAME_Rut);
	}

	/** Set specialty.
		@param specialty specialty	  */
	public void setspecialty (String specialty)
	{
		set_Value (COLUMNNAME_specialty, specialty);
	}

	/** Get specialty.
		@return specialty	  */
	public String getspecialty () 
	{
		return (String)get_Value(COLUMNNAME_specialty);
	}

	/** Set TimeRequested.
		@param TimeRequested TimeRequested	  */
	public void setTimeRequested (Timestamp TimeRequested)
	{
		set_Value (COLUMNNAME_TimeRequested, TimeRequested);
	}

	/** Get TimeRequested.
		@return TimeRequested	  */
	public Timestamp getTimeRequested () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeRequested);
	}
}