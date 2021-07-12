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

/** Generated Model for CC_AdministrativeDischarge
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_AdministrativeDischarge extends PO implements I_CC_AdministrativeDischarge, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170704L;

    /** Standard Constructor */
    public X_CC_AdministrativeDischarge (Properties ctx, int CC_AdministrativeDischarge_ID, String trxName)
    {
      super (ctx, CC_AdministrativeDischarge_ID, trxName);
      /** if (CC_AdministrativeDischarge_ID == 0)
        {
			setCC_AdministrativeDischarge_ID (0);
			setProcessed (false);
// N
        } */
    }

    /** Load Constructor */
    public X_CC_AdministrativeDischarge (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_AdministrativeDischarge[")
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

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CC_AdministrativeDischarge ID.
		@param CC_AdministrativeDischarge_ID CC_AdministrativeDischarge ID	  */
	public void setCC_AdministrativeDischarge_ID (int CC_AdministrativeDischarge_ID)
	{
		if (CC_AdministrativeDischarge_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_AdministrativeDischarge_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_AdministrativeDischarge_ID, Integer.valueOf(CC_AdministrativeDischarge_ID));
	}

	/** Get CC_AdministrativeDischarge ID.
		@return CC_AdministrativeDischarge ID	  */
	public int getCC_AdministrativeDischarge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_AdministrativeDischarge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CC_Hospitalization getCC_Hospitalization() throws RuntimeException
    {
		return (I_CC_Hospitalization)MTable.get(getCtx(), I_CC_Hospitalization.Table_Name)
			.getPO(getCC_Hospitalization_ID(), get_TrxName());	}

	/** Set CC_Hospitalization ID.
		@param CC_Hospitalization_ID CC_Hospitalization ID	  */
	public void setCC_Hospitalization_ID (int CC_Hospitalization_ID)
	{
		if (CC_Hospitalization_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_Hospitalization_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_Hospitalization_ID, Integer.valueOf(CC_Hospitalization_ID));
	}

	/** Get CC_Hospitalization ID.
		@return CC_Hospitalization ID	  */
	public int getCC_Hospitalization_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_Hospitalization_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Comments.
		@param Comments 
		Comments or additional information
	  */
	public void setComments (String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Comments.
		@return Comments or additional information
	  */
	public String getComments () 
	{
		return (String)get_Value(COLUMNNAME_Comments);
	}

	/** Set Date.
		@param Date1 
		Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1)
	{
		set_Value (COLUMNNAME_Date1, Date1);
	}

	/** Get Date.
		@return Date when business is not conducted
	  */
	public Timestamp getDate1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Date1);
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

	/** Destination_Directory AD_Reference_ID=2000041 */
	public static final int DESTINATION_DIRECTORY_AD_Reference_ID=2000041;
	/** Domicilio = 01 */
	public static final String DESTINATION_DIRECTORY_Domicilio = "01";
	/** Hosp. Otro Centro = 03 */
	public static final String DESTINATION_DIRECTORY_HospOtroCentro = "03";
	/** Fallecimiento = 02 */
	public static final String DESTINATION_DIRECTORY_Fallecimiento = "02";
	/** Set Destination_Directory.
		@param Destination_Directory Destination_Directory	  */
	public void setDestination_Directory (String Destination_Directory)
	{

		set_Value (COLUMNNAME_Destination_Directory, Destination_Directory);
	}

	/** Get Destination_Directory.
		@return Destination_Directory	  */
	public String getDestination_Directory () 
	{
		return (String)get_Value(COLUMNNAME_Destination_Directory);
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

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** ExemptReason AD_Reference_ID=2000040 */
	public static final int EXEMPTREASON_AD_Reference_ID=2000040;
	/** Mejora Total = 01 */
	public static final String EXEMPTREASON_MejoraTotal = "01";
	/** Mejora Parcial = 02 */
	public static final String EXEMPTREASON_MejoraParcial = "02";
	/** Rescate = 03 */
	public static final String EXEMPTREASON_Rescate = "03";
	/** Fallecimiento = 04 */
	public static final String EXEMPTREASON_Fallecimiento = "04";
	/** Diciplinario = 05 */
	public static final String EXEMPTREASON_Diciplinario = "05";
	/** Voluntaria = 11000013 */
	public static final String EXEMPTREASON_Voluntaria = "11000013";
	/** Set Exempt reason.
		@param ExemptReason 
		Reason for not withholding
	  */
	public void setExemptReason (String ExemptReason)
	{

		set_Value (COLUMNNAME_ExemptReason, ExemptReason);
	}

	/** Get Exempt reason.
		@return Reason for not withholding
	  */
	public String getExemptReason () 
	{
		return (String)get_Value(COLUMNNAME_ExemptReason);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Format Type.
		@param PrintFormatType 
		Print Format Type
	  */
	public void setPrintFormatType (String PrintFormatType)
	{
		set_Value (COLUMNNAME_PrintFormatType, PrintFormatType);
	}

	/** Get Format Type.
		@return Print Format Type
	  */
	public String getPrintFormatType () 
	{
		return (String)get_Value(COLUMNNAME_PrintFormatType);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SQLStatement.
		@param SQLStatement SQLStatement	  */
	public void setSQLStatement (String SQLStatement)
	{
		throw new IllegalArgumentException ("SQLStatement is virtual column");	}

	/** Get SQLStatement.
		@return SQLStatement	  */
	public String getSQLStatement () 
	{
		return (String)get_Value(COLUMNNAME_SQLStatement);
	}

	/** Set SQLStatement2.
		@param SQLStatement2 SQLStatement2	  */
	public void setSQLStatement2 (String SQLStatement2)
	{
		throw new IllegalArgumentException ("SQLStatement2 is virtual column");	}

	/** Get SQLStatement2.
		@return SQLStatement2	  */
	public String getSQLStatement2 () 
	{
		return (String)get_Value(COLUMNNAME_SQLStatement2);
	}

	/** Transport AD_Reference_ID=2000111 */
	public static final int TRANSPORT_AD_Reference_ID=2000111;
	/** Por sus Medios = 01 */
	public static final String TRANSPORT_PorSusMedios = "01";
	/** Ambulacia = 02 */
	public static final String TRANSPORT_Ambulacia = "02";
	/** Set Transport.
		@param Transport Transport	  */
	public void setTransport (String Transport)
	{

		set_Value (COLUMNNAME_Transport, Transport);
	}

	/** Get Transport.
		@return Transport	  */
	public String getTransport () 
	{
		return (String)get_Value(COLUMNNAME_Transport);
	}
}