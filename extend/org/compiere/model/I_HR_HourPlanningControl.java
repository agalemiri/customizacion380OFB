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

/** Generated Interface for HR_HourPlanningControl
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_HR_HourPlanningControl 
{

    /** TableName=HR_HourPlanningControl */
    public static final String Table_Name = "HR_HourPlanningControl";

    /** AD_Table_ID=2000112 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

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

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

    /** Column name AvailableAmt */
    public static final String COLUMNNAME_AvailableAmt = "AvailableAmt";

	/** Set Available Amount.
	  * Amount available for allocation for this document
	  */
	public void setAvailableAmt (BigDecimal AvailableAmt);

	/** Get Available Amount.
	  * Amount available for allocation for this document
	  */
	public BigDecimal getAvailableAmt();

    /** Column name Budget_Amount */
    public static final String COLUMNNAME_Budget_Amount = "Budget_Amount";

	/** Set Budget Amount.
	  * Budget amount for planning hour
	  */
	public void setBudget_Amount (BigDecimal Budget_Amount);

	/** Get Budget Amount.
	  * Budget amount for planning hour
	  */
	public BigDecimal getBudget_Amount();

    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/** Set Period.
	  * Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID);

	/** Get Period.
	  * Period of the Calendar
	  */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException;

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

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

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

    /** Column name HR_HourPlanningControl_ID */
    public static final String COLUMNNAME_HR_HourPlanningControl_ID = "HR_HourPlanningControl_ID";

	/** Set Hour Planning Control ID	  */
	public void setHR_HourPlanningControl_ID (int HR_HourPlanningControl_ID);

	/** Get Hour Planning Control ID	  */
	public int getHR_HourPlanningControl_ID();

    /** Column name HR_HourPlanning_ID */
    public static final String COLUMNNAME_HR_HourPlanning_ID = "HR_HourPlanning_ID";

	/** Set Hour Planning ID	  */
	public void setHR_HourPlanning_ID (int HR_HourPlanning_ID);

	/** Get Hour Planning ID	  */
	public int getHR_HourPlanning_ID();

	public I_HR_HourPlanning getHR_HourPlanning() throws RuntimeException;

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

    /** Column name ofbbutton */
    public static final String COLUMNNAME_ofbbutton = "ofbbutton";

	/** Set ofbbutton	  */
	public void setofbbutton (String ofbbutton);

	/** Get ofbbutton	  */
	public String getofbbutton();

    /** Column name ofbbutton2 */
    public static final String COLUMNNAME_ofbbutton2 = "ofbbutton2";

	/** Set ofbbutton2	  */
	public void setofbbutton2 (String ofbbutton2);

	/** Get ofbbutton2	  */
	public String getofbbutton2();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

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

    /** Column name UsedAmt */
    public static final String COLUMNNAME_UsedAmt = "UsedAmt";

	/** Set UsedAmt	  */
	public void setUsedAmt (BigDecimal UsedAmt);

	/** Get UsedAmt	  */
	public BigDecimal getUsedAmt();
}
