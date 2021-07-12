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

/** Generated Model for MED_SurgeryRequisition
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_MED_SurgeryRequisition extends PO implements I_MED_SurgeryRequisition, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20191001L;

    /** Standard Constructor */
    public X_MED_SurgeryRequisition (Properties ctx, int MED_SurgeryRequisition_ID, String trxName)
    {
      super (ctx, MED_SurgeryRequisition_ID, trxName);
      /** if (MED_SurgeryRequisition_ID == 0)
        {
			setMED_SurgeryRequisition_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MED_SurgeryRequisition (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MED_SurgeryRequisition[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_CC_Agreement getCC_Agreement() throws RuntimeException
    {
		return (I_CC_Agreement)MTable.get(getCtx(), I_CC_Agreement.Table_Name)
			.getPO(getCC_Agreement_ID(), get_TrxName());	}

	/** Set CC_Agreement ID.
		@param CC_Agreement_ID CC_Agreement ID	  */
	public void setCC_Agreement_ID (int CC_Agreement_ID)
	{
		if (CC_Agreement_ID < 1) 
			set_Value (COLUMNNAME_CC_Agreement_ID, null);
		else 
			set_Value (COLUMNNAME_CC_Agreement_ID, Integer.valueOf(CC_Agreement_ID));
	}

	/** Get CC_Agreement ID.
		@return CC_Agreement ID	  */
	public int getCC_Agreement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_Agreement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DateEnd.
		@param DateEnd DateEnd	  */
	public void setDateEnd (Timestamp DateEnd)
	{
		set_Value (COLUMNNAME_DateEnd, DateEnd);
	}

	/** Get DateEnd.
		@return DateEnd	  */
	public Timestamp getDateEnd () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateEnd);
	}

	/** Set Date Start.
		@param DateStart 
		Date Start for this Order
	  */
	public void setDateStart (Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	/** Get Date Start.
		@return Date Start for this Order
	  */
	public Timestamp getDateStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStart);
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

	/** Set MED_SurgeryRequisition ID.
		@param MED_SurgeryRequisition_ID MED_SurgeryRequisition ID	  */
	public void setMED_SurgeryRequisition_ID (int MED_SurgeryRequisition_ID)
	{
		if (MED_SurgeryRequisition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MED_SurgeryRequisition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MED_SurgeryRequisition_ID, Integer.valueOf(MED_SurgeryRequisition_ID));
	}

	/** Get MED_SurgeryRequisition ID.
		@return MED_SurgeryRequisition ID	  */
	public int getMED_SurgeryRequisition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MED_SurgeryRequisition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set OfbButton.
		@param OfbButton OfbButton	  */
	public void setOfbButton (String OfbButton)
	{
		set_Value (COLUMNNAME_OfbButton, OfbButton);
	}

	/** Get OfbButton.
		@return OfbButton	  */
	public String getOfbButton () 
	{
		return (String)get_Value(COLUMNNAME_OfbButton);
	}
}