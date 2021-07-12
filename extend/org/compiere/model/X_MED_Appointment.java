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

/** Generated Model for MED_Appointment
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_MED_Appointment extends PO implements I_MED_Appointment, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190711L;

    /** Standard Constructor */
    public X_MED_Appointment (Properties ctx, int MED_Appointment_ID, String trxName)
    {
      super (ctx, MED_Appointment_ID, trxName);
      /** if (MED_Appointment_ID == 0)
        {
			setMED_Appointment_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MED_Appointment (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MED_Appointment[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ArrivalTime.
		@param ArrivalTime ArrivalTime	  */
	public void setArrivalTime (Timestamp ArrivalTime)
	{
		set_Value (COLUMNNAME_ArrivalTime, ArrivalTime);
	}

	/** Get ArrivalTime.
		@return ArrivalTime	  */
	public Timestamp getArrivalTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ArrivalTime);
	}

	/** Set AttentionTime.
		@param AttentionTime AttentionTime	  */
	public void setAttentionTime (Timestamp AttentionTime)
	{
		set_Value (COLUMNNAME_AttentionTime, AttentionTime);
	}

	/** Get AttentionTime.
		@return AttentionTime	  */
	public Timestamp getAttentionTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_AttentionTime);
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

	public org.compiere.model.I_C_BPartner getC_BPartnerMed() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartnerMed_ID(), get_TrxName());	}

	/** Set C_BPartnerMed_ID.
		@param C_BPartnerMed_ID C_BPartnerMed_ID	  */
	public void setC_BPartnerMed_ID (int C_BPartnerMed_ID)
	{
		if (C_BPartnerMed_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerMed_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerMed_ID, Integer.valueOf(C_BPartnerMed_ID));
	}

	/** Get C_BPartnerMed_ID.
		@return C_BPartnerMed_ID	  */
	public int getC_BPartnerMed_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerMed_ID);
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

	/** Set Entry.
		@param Entry Entry	  */
	public void setEntry (boolean Entry)
	{
		set_Value (COLUMNNAME_Entry, Boolean.valueOf(Entry));
	}

	/** Get Entry.
		@return Entry	  */
	public boolean isEntry () 
	{
		Object oo = get_Value(COLUMNNAME_Entry);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ExitTime.
		@param ExitTime ExitTime	  */
	public void setExitTime (Timestamp ExitTime)
	{
		set_Value (COLUMNNAME_ExitTime, ExitTime);
	}

	/** Get ExitTime.
		@return ExitTime	  */
	public Timestamp getExitTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ExitTime);
	}

	/** Set IsArrived.
		@param IsArrived IsArrived	  */
	public void setIsArrived (boolean IsArrived)
	{
		set_Value (COLUMNNAME_IsArrived, Boolean.valueOf(IsArrived));
	}

	/** Get IsArrived.
		@return IsArrived	  */
	public boolean isArrived () 
	{
		Object oo = get_Value(COLUMNNAME_IsArrived);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsExit.
		@param IsExit IsExit	  */
	public void setIsExit (boolean IsExit)
	{
		set_Value (COLUMNNAME_IsExit, Boolean.valueOf(IsExit));
	}

	/** Get IsExit.
		@return IsExit	  */
	public boolean isExit () 
	{
		Object oo = get_Value(COLUMNNAME_IsExit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set MED_Appointment ID.
		@param MED_Appointment_ID MED_Appointment ID	  */
	public void setMED_Appointment_ID (int MED_Appointment_ID)
	{
		if (MED_Appointment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MED_Appointment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MED_Appointment_ID, Integer.valueOf(MED_Appointment_ID));
	}

	/** Get MED_Appointment ID.
		@return MED_Appointment ID	  */
	public int getMED_Appointment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MED_Appointment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_MED_AttentionType getMED_AttentionType() throws RuntimeException
    {
		return (I_MED_AttentionType)MTable.get(getCtx(), I_MED_AttentionType.Table_Name)
			.getPO(getMED_AttentionType_ID(), get_TrxName());	}

	/** Set MED_AttentionType ID.
		@param MED_AttentionType_ID MED_AttentionType ID	  */
	public void setMED_AttentionType_ID (int MED_AttentionType_ID)
	{
		if (MED_AttentionType_ID < 1) 
			set_Value (COLUMNNAME_MED_AttentionType_ID, null);
		else 
			set_Value (COLUMNNAME_MED_AttentionType_ID, Integer.valueOf(MED_AttentionType_ID));
	}

	/** Get MED_AttentionType ID.
		@return MED_AttentionType ID	  */
	public int getMED_AttentionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MED_AttentionType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_MED_Specialty getMED_Specialty() throws RuntimeException
    {
		return (I_MED_Specialty)MTable.get(getCtx(), I_MED_Specialty.Table_Name)
			.getPO(getMED_Specialty_ID(), get_TrxName());	}

	/** Set MED_Specialty ID.
		@param MED_Specialty_ID MED_Specialty ID	  */
	public void setMED_Specialty_ID (int MED_Specialty_ID)
	{
		if (MED_Specialty_ID < 1) 
			set_Value (COLUMNNAME_MED_Specialty_ID, null);
		else 
			set_Value (COLUMNNAME_MED_Specialty_ID, Integer.valueOf(MED_Specialty_ID));
	}

	/** Get MED_Specialty ID.
		@return MED_Specialty ID	  */
	public int getMED_Specialty_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MED_Specialty_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ReservationDate.
		@param ReservationDate ReservationDate	  */
	public void setReservationDate (Timestamp ReservationDate)
	{
		set_Value (COLUMNNAME_ReservationDate, ReservationDate);
	}

	/** Get ReservationDate.
		@return ReservationDate	  */
	public Timestamp getReservationDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ReservationDate);
	}

	/** Set State.
		@param State State	  */
	public void setState (String State)
	{
		set_Value (COLUMNNAME_State, State);
	}

	/** Get State.
		@return State	  */
	public String getState () 
	{
		return (String)get_Value(COLUMNNAME_State);
	}
}