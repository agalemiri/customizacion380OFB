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

/** Generated Interface for DM_DocumentLinePM
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_DM_DocumentLinePM 
{

    /** TableName=DM_DocumentLinePM */
    public static final String Table_Name = "DM_DocumentLinePM";

    /** AD_Table_ID=2000110 */
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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

    /** Column name Amount2 */
    public static final String COLUMNNAME_Amount2 = "Amount2";

	/** Set Amount2	  */
	public void setAmount2 (BigDecimal Amount2);

	/** Get Amount2	  */
	public BigDecimal getAmount2();

    /** Column name Amount3 */
    public static final String COLUMNNAME_Amount3 = "Amount3";

	/** Set Amount3	  */
	public void setAmount3 (BigDecimal Amount3);

	/** Get Amount3	  */
	public BigDecimal getAmount3();

    /** Column name Amount4 */
    public static final String COLUMNNAME_Amount4 = "Amount4";

	/** Set Amount4	  */
	public void setAmount4 (BigDecimal Amount4);

	/** Get Amount4	  */
	public BigDecimal getAmount4();

    /** Column name Amount5 */
    public static final String COLUMNNAME_Amount5 = "Amount5";

	/** Set Amount5	  */
	public void setAmount5 (BigDecimal Amount5);

	/** Get Amount5	  */
	public BigDecimal getAmount5();

    /** Column name Amount6 */
    public static final String COLUMNNAME_Amount6 = "Amount6";

	/** Set Amount6	  */
	public void setAmount6 (BigDecimal Amount6);

	/** Get Amount6	  */
	public BigDecimal getAmount6();

    /** Column name Amount7 */
    public static final String COLUMNNAME_Amount7 = "Amount7";

	/** Set Amount7	  */
	public void setAmount7 (BigDecimal Amount7);

	/** Get Amount7	  */
	public BigDecimal getAmount7();

    /** Column name Amount8 */
    public static final String COLUMNNAME_Amount8 = "Amount8";

	/** Set Amount8	  */
	public void setAmount8 (BigDecimal Amount8);

	/** Get Amount8	  */
	public BigDecimal getAmount8();

    /** Column name Amount9 */
    public static final String COLUMNNAME_Amount9 = "Amount9";

	/** Set Amount9	  */
	public void setAmount9 (BigDecimal Amount9);

	/** Get Amount9	  */
	public BigDecimal getAmount9();

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/** Set Project.
	  * Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID);

	/** Get Project.
	  * Financial Project
	  */
	public int getC_Project_ID();

	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException;

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

    /** Column name C_ValidCombination_ID */
    public static final String COLUMNNAME_C_ValidCombination_ID = "C_ValidCombination_ID";

	/** Set Combination.
	  * Valid Account Combination
	  */
	public void setC_ValidCombination_ID (int C_ValidCombination_ID);

	/** Get Combination.
	  * Valid Account Combination
	  */
	public int getC_ValidCombination_ID();

	public org.compiere.model.I_C_ValidCombination getC_ValidCombination() throws RuntimeException;

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

    /** Column name DM_Document_ID */
    public static final String COLUMNNAME_DM_Document_ID = "DM_Document_ID";

	/** Set DM_Document	  */
	public void setDM_Document_ID (int DM_Document_ID);

	/** Get DM_Document	  */
	public int getDM_Document_ID();

	public I_DM_Document getDM_Document() throws RuntimeException;

    /** Column name DM_DocumentLinePM_ID */
    public static final String COLUMNNAME_DM_DocumentLinePM_ID = "DM_DocumentLinePM_ID";

	/** Set DM_DocumentLinePM ID	  */
	public void setDM_DocumentLinePM_ID (int DM_DocumentLinePM_ID);

	/** Get DM_DocumentLinePM ID	  */
	public int getDM_DocumentLinePM_ID();

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
