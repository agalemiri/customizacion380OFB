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

/** Generated Interface for RH_MedicalLicensesLine
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_RH_MedicalLicensesLine 
{

    /** TableName=RH_MedicalLicensesLine */
    public static final String Table_Name = "RH_MedicalLicensesLine";

    /** AD_Table_ID=2000014 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AccountNo */
    public static final String COLUMNNAME_AccountNo = "AccountNo";

	/** Set Account No.
	  * Account Number
	  */
	public void setAccountNo (String AccountNo);

	/** Get Account No.
	  * Account Number
	  */
	public String getAccountNo();

    /** Column name AccrualApproved */
    public static final String COLUMNNAME_AccrualApproved = "AccrualApproved";

	/** Set AccrualApproved	  */
	public void setAccrualApproved (String AccrualApproved);

	/** Get AccrualApproved	  */
	public String getAccrualApproved();

    /** Column name AccruelDate */
    public static final String COLUMNNAME_AccruelDate = "AccruelDate";

	/** Set AccruelDate	  */
	public void setAccruelDate (Timestamp AccruelDate);

	/** Get AccruelDate	  */
	public Timestamp getAccruelDate();

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

    /** Column name Amount2 */
    public static final String COLUMNNAME_Amount2 = "Amount2";

	/** Set Amount2	  */
	public void setAmount2 (BigDecimal Amount2);

	/** Get Amount2	  */
	public BigDecimal getAmount2();

    /** Column name AmountRefunded */
    public static final String COLUMNNAME_AmountRefunded = "AmountRefunded";

	/** Set AmountRefunded	  */
	public void setAmountRefunded (BigDecimal AmountRefunded);

	/** Get AmountRefunded	  */
	public BigDecimal getAmountRefunded();

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

    /** Column name EgressApproved */
    public static final String COLUMNNAME_EgressApproved = "EgressApproved";

	/** Set EgressApproved	  */
	public void setEgressApproved (String EgressApproved);

	/** Get EgressApproved	  */
	public String getEgressApproved();

    /** Column name ExitDate */
    public static final String COLUMNNAME_ExitDate = "ExitDate";

	/** Set ExitDate	  */
	public void setExitDate (Timestamp ExitDate);

	/** Get ExitDate	  */
	public Timestamp getExitDate();

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

    /** Column name PaymentType */
    public static final String COLUMNNAME_PaymentType = "PaymentType";

	/** Set PaymentType	  */
	public void setPaymentType (String PaymentType);

	/** Get PaymentType	  */
	public String getPaymentType();

    /** Column name RH_MedicalLicenses_ID */
    public static final String COLUMNNAME_RH_MedicalLicenses_ID = "RH_MedicalLicenses_ID";

	/** Set RH_MedicalLicenses_ID	  */
	public void setRH_MedicalLicenses_ID (int RH_MedicalLicenses_ID);

	/** Get RH_MedicalLicenses_ID	  */
	public int getRH_MedicalLicenses_ID();

	public I_RH_MedicalLicenses getRH_MedicalLicenses() throws RuntimeException;

    /** Column name RH_MedicalLicensesLine_ID */
    public static final String COLUMNNAME_RH_MedicalLicensesLine_ID = "RH_MedicalLicensesLine_ID";

	/** Set RH_MedicalLicensesLine ID	  */
	public void setRH_MedicalLicensesLine_ID (int RH_MedicalLicensesLine_ID);

	/** Get RH_MedicalLicensesLine ID	  */
	public int getRH_MedicalLicensesLine_ID();

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
