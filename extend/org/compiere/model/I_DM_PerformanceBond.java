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

/** Generated Interface for DM_PerformanceBond
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_DM_PerformanceBond 
{

    /** TableName=DM_PerformanceBond */
    public static final String Table_Name = "DM_PerformanceBond";

    /** AD_Table_ID=2000114 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AccountingDocument */
    public static final String COLUMNNAME_AccountingDocument = "AccountingDocument";

	/** Set AccountingDocument.
	  *  guarantee billing document
	  */
	public void setAccountingDocument (BigDecimal AccountingDocument);

	/** Get AccountingDocument.
	  *  guarantee billing document
	  */
	public BigDecimal getAccountingDocument();

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

    /** Column name AD_OrgRef_ID */
    public static final String COLUMNNAME_AD_OrgRef_ID = "AD_OrgRef_ID";

	/** Set AD_OrgRef_ID	  */
	public void setAD_OrgRef_ID (int AD_OrgRef_ID);

	/** Get AD_OrgRef_ID	  */
	public int getAD_OrgRef_ID();

    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

	/** Set Amount.
	  * Amount
	  */
	public void setAmt (BigDecimal Amt);

	/** Get Amount.
	  * Amount
	  */
	public BigDecimal getAmt();

    /** Column name Amt1 */
    public static final String COLUMNNAME_Amt1 = "Amt1";

	/** Set Amount1.
	  * Amount
	  */
	public void setAmt1 (BigDecimal Amt1);

	/** Get Amount1.
	  * Amount
	  */
	public BigDecimal getAmt1();

    /** Column name C_BPartner_Bank_ID */
    public static final String COLUMNNAME_C_BPartner_Bank_ID = "C_BPartner_Bank_ID";

	/** Set C_BPartner_Bank_ID	  */
	public void setC_BPartner_Bank_ID (int C_BPartner_Bank_ID);

	/** Get C_BPartner_Bank_ID	  */
	public int getC_BPartner_Bank_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner_Bank() throws RuntimeException;

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

    /** Column name c_bpartnerp_id */
    public static final String COLUMNNAME_c_bpartnerp_id = "c_bpartnerp_id";

	/** Set c_bpartnerp_id	  */
	public void setc_bpartnerp_id (int c_bpartnerp_id);

	/** Get c_bpartnerp_id	  */
	public int getc_bpartnerp_id();

	public org.compiere.model.I_C_BPartner getc_bpartnerp() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name C_Region_ID */
    public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/** Set Region.
	  * Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID);

	/** Get Region.
	  * Identifies a geographical Region
	  */
	public int getC_Region_ID();

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException;

    /** Column name C_RfQ_ID */
    public static final String COLUMNNAME_C_RfQ_ID = "C_RfQ_ID";

	/** Set RfQ.
	  * Request for Quotation
	  */
	public void setC_RfQ_ID (int C_RfQ_ID);

	/** Get RfQ.
	  * Request for Quotation
	  */
	public int getC_RfQ_ID();

	public org.compiere.model.I_C_RfQ getC_RfQ() throws RuntimeException;

    /** Column name Comments */
    public static final String COLUMNNAME_Comments = "Comments";

	/** Set Comments.
	  * Comments or additional information
	  */
	public void setComments (String Comments);

	/** Get Comments.
	  * Comments or additional information
	  */
	public String getComments();

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

    /** Column name Date01 */
    public static final String COLUMNNAME_Date01 = "Date01";

	/** Set Date01	  */
	public void setDate01 (Timestamp Date01);

	/** Get Date01	  */
	public Timestamp getDate01();

    /** Column name Date1 */
    public static final String COLUMNNAME_Date1 = "Date1";

	/** Set Date1.
	  * Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1);

	/** Get Date1.
	  * Date when business is not conducted
	  */
	public Timestamp getDate1();

    /** Column name Date3 */
    public static final String COLUMNNAME_Date3 = "Date3";

	/** Set Date3	  */
	public void setDate3 (Timestamp Date3);

	/** Get Date3	  */
	public Timestamp getDate3();

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public Timestamp getDateAcct();

    /** Column name DateConfirm */
    public static final String COLUMNNAME_DateConfirm = "DateConfirm";

	/** Set Date Confirm.
	  * Date Confirm of this Order
	  */
	public void setDateConfirm (Timestamp DateConfirm);

	/** Get Date Confirm.
	  * Date Confirm of this Order
	  */
	public Timestamp getDateConfirm();

    /** Column name DateDelivered */
    public static final String COLUMNNAME_DateDelivered = "DateDelivered";

	/** Set Date Delivered.
	  * Date when the product was delivered
	  */
	public void setDateDelivered (Timestamp DateDelivered);

	/** Get Date Delivered.
	  * Date when the product was delivered
	  */
	public Timestamp getDateDelivered();

    /** Column name dateexpiration */
    public static final String COLUMNNAME_dateexpiration = "dateexpiration";

	/** Set dateexpiration	  */
	public void setdateexpiration (Timestamp dateexpiration);

	/** Get dateexpiration	  */
	public Timestamp getdateexpiration();

    /** Column name DateExtension */
    public static final String COLUMNNAME_DateExtension = "DateExtension";

	/** Set DateExtension	  */
	public void setDateExtension (Timestamp DateExtension);

	/** Get DateExtension	  */
	public Timestamp getDateExtension();

    /** Column name datereception */
    public static final String COLUMNNAME_datereception = "datereception";

	/** Set datereception	  */
	public void setdatereception (Timestamp datereception);

	/** Get datereception	  */
	public Timestamp getdatereception();

    /** Column name DateRequired */
    public static final String COLUMNNAME_DateRequired = "DateRequired";

	/** Set Date Required.
	  * Date when required
	  */
	public void setDateRequired (Timestamp DateRequired);

	/** Get Date Required.
	  * Date when required
	  */
	public Timestamp getDateRequired();

    /** Column name DateStart */
    public static final String COLUMNNAME_DateStart = "DateStart";

	/** Set Date Start.
	  * Date Start for this Order
	  */
	public void setDateStart (Timestamp DateStart);

	/** Get Date Start.
	  * Date Start for this Order
	  */
	public Timestamp getDateStart();

    /** Column name DateVoucher */
    public static final String COLUMNNAME_DateVoucher = "DateVoucher";

	/** Set DateVoucher	  */
	public void setDateVoucher (Timestamp DateVoucher);

	/** Get DateVoucher	  */
	public Timestamp getDateVoucher();

    /** Column name DateVoucher1 */
    public static final String COLUMNNAME_DateVoucher1 = "DateVoucher1";

	/** Set DateVoucher1	  */
	public void setDateVoucher1 (Timestamp DateVoucher1);

	/** Get DateVoucher1	  */
	public Timestamp getDateVoucher1();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Description2 */
    public static final String COLUMNNAME_Description2 = "Description2";

	/** Set Description2	  */
	public void setDescription2 (String Description2);

	/** Get Description2	  */
	public String getDescription2();

    /** Column name Description3 */
    public static final String COLUMNNAME_Description3 = "Description3";

	/** Set Description3	  */
	public void setDescription3 (String Description3);

	/** Get Description3	  */
	public String getDescription3();

    /** Column name Description4 */
    public static final String COLUMNNAME_Description4 = "Description4";

	/** Set Description4	  */
	public void setDescription4 (String Description4);

	/** Get Description4	  */
	public String getDescription4();

    /** Column name Digito */
    public static final String COLUMNNAME_Digito = "Digito";

	/** Set Digito.
	  * Digit for verify RUT information
	  */
	public void setDigito (String Digito);

	/** Get Digito.
	  * Digit for verify RUT information
	  */
	public String getDigito();

    /** Column name Digito1 */
    public static final String COLUMNNAME_Digito1 = "Digito1";

	/** Set Digito1.
	  * Digit for verify RUT information
	  */
	public void setDigito1 (String Digito1);

	/** Get Digito1.
	  * Digit for verify RUT information
	  */
	public String getDigito1();

    /** Column name DM_PerformanceBond_ID */
    public static final String COLUMNNAME_DM_PerformanceBond_ID = "DM_PerformanceBond_ID";

	/** Set Performance Bond ID	  */
	public void setDM_PerformanceBond_ID (int DM_PerformanceBond_ID);

	/** Get Performance Bond ID	  */
	public int getDM_PerformanceBond_ID();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name DocumentNo1 */
    public static final String COLUMNNAME_DocumentNo1 = "DocumentNo1";

	/** Set Document No1.
	  * Document sequence number of the document
	  */
	public void setDocumentNo1 (String DocumentNo1);

	/** Get Document No1.
	  * Document sequence number of the document
	  */
	public String getDocumentNo1();

    /** Column name DocumentType */
    public static final String COLUMNNAME_DocumentType = "DocumentType";

	/** Set Document Type.
	  * Document Type
	  */
	public void setDocumentType (String DocumentType);

	/** Get Document Type.
	  * Document Type
	  */
	public String getDocumentType();

    /** Column name DocumentType2 */
    public static final String COLUMNNAME_DocumentType2 = "DocumentType2";

	/** Set DocumentType2	  */
	public void setDocumentType2 (String DocumentType2);

	/** Get DocumentType2	  */
	public String getDocumentType2();

    /** Column name Extension */
    public static final String COLUMNNAME_Extension = "Extension";

	/** Set Extension	  */
	public void setExtension (boolean Extension);

	/** Get Extension	  */
	public boolean isExtension();

    /** Column name FirstDateReminder */
    public static final String COLUMNNAME_FirstDateReminder = "FirstDateReminder";

	/** Set FirstDateReminder	  */
	public void setFirstDateReminder (Timestamp FirstDateReminder);

	/** Get FirstDateReminder	  */
	public Timestamp getFirstDateReminder();

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

    /** Column name Location */
    public static final String COLUMNNAME_Location = "Location";

	/** Set Location	  */
	public void setLocation (String Location);

	/** Get Location	  */
	public String getLocation();

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

    /** Column name Name1 */
    public static final String COLUMNNAME_Name1 = "Name1";

	/** Set Name1	  */
	public void setName1 (String Name1);

	/** Get Name1	  */
	public String getName1();

    /** Column name NumberOffice */
    public static final String COLUMNNAME_NumberOffice = "NumberOffice";

	/** Set NumberOffice	  */
	public void setNumberOffice (BigDecimal NumberOffice);

	/** Get NumberOffice	  */
	public BigDecimal getNumberOffice();

    /** Column name NumberTransport */
    public static final String COLUMNNAME_NumberTransport = "NumberTransport";

	/** Set NumberTransport	  */
	public void setNumberTransport (BigDecimal NumberTransport);

	/** Get NumberTransport	  */
	public BigDecimal getNumberTransport();

    /** Column name NumberVoucher */
    public static final String COLUMNNAME_NumberVoucher = "NumberVoucher";

	/** Set NumberVoucher	  */
	public void setNumberVoucher (BigDecimal NumberVoucher);

	/** Get NumberVoucher	  */
	public BigDecimal getNumberVoucher();

    /** Column name NumberVoucher1 */
    public static final String COLUMNNAME_NumberVoucher1 = "NumberVoucher1";

	/** Set NumberVoucher1	  */
	public void setNumberVoucher1 (BigDecimal NumberVoucher1);

	/** Get NumberVoucher1	  */
	public BigDecimal getNumberVoucher1();

    /** Column name ofbbutton */
    public static final String COLUMNNAME_ofbbutton = "ofbbutton";

	/** Set ofbbutton	  */
	public void setofbbutton (boolean ofbbutton);

	/** Get ofbbutton	  */
	public boolean isofbbutton();

    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/** Set Phone.
	  * Identifies a telephone number
	  */
	public void setPhone (String Phone);

	/** Get Phone.
	  * Identifies a telephone number
	  */
	public String getPhone();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name resolutionnumber */
    public static final String COLUMNNAME_resolutionnumber = "resolutionnumber";

	/** Set resolutionnumber	  */
	public void setresolutionnumber (String resolutionnumber);

	/** Get resolutionnumber	  */
	public String getresolutionnumber();

    /** Column name Rut1 */
    public static final String COLUMNNAME_Rut1 = "Rut1";

	/** Set Rut1.
	  * Rut of person what delivery
	  */
	public void setRut1 (String Rut1);

	/** Get Rut1.
	  * Rut of person what delivery
	  */
	public String getRut1();

    /** Column name SecondDateReminder */
    public static final String COLUMNNAME_SecondDateReminder = "SecondDateReminder";

	/** Set SecondDateReminder	  */
	public void setSecondDateReminder (Timestamp SecondDateReminder);

	/** Get SecondDateReminder	  */
	public Timestamp getSecondDateReminder();

    /** Column name SendTicket */
    public static final String COLUMNNAME_SendTicket = "SendTicket";

	/** Set SendTicket	  */
	public void setSendTicket (boolean SendTicket);

	/** Get SendTicket	  */
	public boolean isSendTicket();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
