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

/** Generated Model for HR_AdministrativeRequestsL
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_HR_AdministrativeRequestsL extends PO implements I_HR_AdministrativeRequestsL, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171214L;

    /** Standard Constructor */
    public X_HR_AdministrativeRequestsL (Properties ctx, int HR_AdministrativeRequestsL_ID, String trxName)
    {
      super (ctx, HR_AdministrativeRequestsL_ID, trxName);
      /** if (HR_AdministrativeRequestsL_ID == 0)
        {
			setHR_AdministrativeRequests_ID (0);
			setHR_AdministrativeRequestsL_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HR_AdministrativeRequestsL (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_AdministrativeRequestsL[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** AdmType AD_Reference_ID=2000132 */
	public static final int ADMTYPE_AD_Reference_ID=2000132;
	/** Dia Completo = DIA */
	public static final String ADMTYPE_DiaCompleto = "DIA";
	/** Media Manana = MAN */
	public static final String ADMTYPE_MediaManana = "MAN";
	/** Media Tarde = TAR */
	public static final String ADMTYPE_MediaTarde = "TAR";
	/** Set AdmType.
		@param AdmType AdmType	  */
	public void setAdmType (String AdmType)
	{

		set_Value (COLUMNNAME_AdmType, AdmType);
	}

	/** Get AdmType.
		@return AdmType	  */
	public String getAdmType () 
	{
		return (String)get_Value(COLUMNNAME_AdmType);
	}

	/** AttType AD_Reference_ID=2000127 */
	public static final int ATTTYPE_AD_Reference_ID=2000127;
	/** Entrada = IN */
	public static final String ATTTYPE_Entrada = "IN";
	/** Salida = OUT */
	public static final String ATTTYPE_Salida = "OUT";
	/** Correccion = COR */
	public static final String ATTTYPE_Correccion = "COR";
	/** Cometido = COM */
	public static final String ATTTYPE_Cometido = "COM";
	/** Forzado = FOR */
	public static final String ATTTYPE_Forzado = "FOR";
	/** Set AttType.
		@param AttType AttType	  */
	public void setAttType (String AttType)
	{

		set_Value (COLUMNNAME_AttType, AttType);
	}

	/** Get AttType.
		@return AttType	  */
	public String getAttType () 
	{
		return (String)get_Value(COLUMNNAME_AttType);
	}

	/** ConfirmType AD_Reference_ID=2000127 */
	public static final int CONFIRMTYPE_AD_Reference_ID=2000127;
	/** Entrada = IN */
	public static final String CONFIRMTYPE_Entrada = "IN";
	/** Salida = OUT */
	public static final String CONFIRMTYPE_Salida = "OUT";
	/** Correccion = COR */
	public static final String CONFIRMTYPE_Correccion = "COR";
	/** Cometido = COM */
	public static final String CONFIRMTYPE_Cometido = "COM";
	/** Forzado = FOR */
	public static final String CONFIRMTYPE_Forzado = "FOR";
	/** Set Confirmation Type.
		@param ConfirmType 
		Type of confirmation
	  */
	public void setConfirmType (String ConfirmType)
	{

		set_Value (COLUMNNAME_ConfirmType, ConfirmType);
	}

	/** Get Confirmation Type.
		@return Type of confirmation
	  */
	public String getConfirmType () 
	{
		return (String)get_Value(COLUMNNAME_ConfirmType);
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

	/** Set Date Required.
		@param DateRequired 
		Date when required
	  */
	public void setDateRequired (Timestamp DateRequired)
	{
		set_Value (COLUMNNAME_DateRequired, DateRequired);
	}

	/** Get Date Required.
		@return Date when required
	  */
	public Timestamp getDateRequired () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateRequired);
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

	/** Set Days.
		@param Days Days	  */
	public void setDays (BigDecimal Days)
	{
		set_Value (COLUMNNAME_Days, Days);
	}

	/** Get Days.
		@return Days	  */
	public BigDecimal getDays () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Days);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set hours.
		@param hours hours	  */
	public void sethours (BigDecimal hours)
	{
		set_Value (COLUMNNAME_hours, hours);
	}

	/** Get hours.
		@return hours	  */
	public BigDecimal gethours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_hours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_HR_AdministrativeRequests getHR_AdministrativeRequests() throws RuntimeException
    {
		return (I_HR_AdministrativeRequests)MTable.get(getCtx(), I_HR_AdministrativeRequests.Table_Name)
			.getPO(getHR_AdministrativeRequests_ID(), get_TrxName());	}

	/** Set HR_AdministrativeRequests.
		@param HR_AdministrativeRequests_ID HR_AdministrativeRequests	  */
	public void setHR_AdministrativeRequests_ID (int HR_AdministrativeRequests_ID)
	{
		if (HR_AdministrativeRequests_ID < 1) 
			set_Value (COLUMNNAME_HR_AdministrativeRequests_ID, null);
		else 
			set_Value (COLUMNNAME_HR_AdministrativeRequests_ID, Integer.valueOf(HR_AdministrativeRequests_ID));
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

	/** Set HR_AdministrativeRequestsL ID.
		@param HR_AdministrativeRequestsL_ID HR_AdministrativeRequestsL ID	  */
	public void setHR_AdministrativeRequestsL_ID (int HR_AdministrativeRequestsL_ID)
	{
		if (HR_AdministrativeRequestsL_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_AdministrativeRequestsL_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_AdministrativeRequestsL_ID, Integer.valueOf(HR_AdministrativeRequestsL_ID));
	}

	/** Get HR_AdministrativeRequestsL ID.
		@return HR_AdministrativeRequestsL ID	  */
	public int getHR_AdministrativeRequestsL_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_AdministrativeRequestsL_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}