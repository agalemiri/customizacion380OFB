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

/** Generated Model for HR_AttendanceLine
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_HR_AttendanceLine extends PO implements I_HR_AttendanceLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171204L;

    /** Standard Constructor */
    public X_HR_AttendanceLine (Properties ctx, int HR_AttendanceLine_ID, String trxName)
    {
      super (ctx, HR_AttendanceLine_ID, trxName);
      /** if (HR_AttendanceLine_ID == 0)
        {
			setHR_Attendance_ID (0);
			setHR_AttendanceLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HR_AttendanceLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_AttendanceLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	public I_HR_Attendance getHR_Attendance() throws RuntimeException
    {
		return (I_HR_Attendance)MTable.get(getCtx(), I_HR_Attendance.Table_Name)
			.getPO(getHR_Attendance_ID(), get_TrxName());	}

	/** Set HR_Attendance ID.
		@param HR_Attendance_ID HR_Attendance ID	  */
	public void setHR_Attendance_ID (int HR_Attendance_ID)
	{
		if (HR_Attendance_ID < 1) 
			set_Value (COLUMNNAME_HR_Attendance_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Attendance_ID, Integer.valueOf(HR_Attendance_ID));
	}

	/** Get HR_Attendance ID.
		@return HR_Attendance ID	  */
	public int getHR_Attendance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Attendance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HR_AttendanceLine ID.
		@param HR_AttendanceLine_ID HR_AttendanceLine ID	  */
	public void setHR_AttendanceLine_ID (int HR_AttendanceLine_ID)
	{
		if (HR_AttendanceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_AttendanceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_AttendanceLine_ID, Integer.valueOf(HR_AttendanceLine_ID));
	}

	/** Get HR_AttendanceLine ID.
		@return HR_AttendanceLine ID	  */
	public int getHR_AttendanceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_AttendanceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** RequestType AD_Reference_ID=2000127 */
	public static final int REQUESTTYPE_AD_Reference_ID=2000127;
	/** Entrada = IN */
	public static final String REQUESTTYPE_Entrada = "IN";
	/** Salida = OUT */
	public static final String REQUESTTYPE_Salida = "OUT";
	/** Correccion = COR */
	public static final String REQUESTTYPE_Correccion = "COR";
	/** Cometido = COM */
	public static final String REQUESTTYPE_Cometido = "COM";
	/** Forzado = FOR */
	public static final String REQUESTTYPE_Forzado = "FOR";
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