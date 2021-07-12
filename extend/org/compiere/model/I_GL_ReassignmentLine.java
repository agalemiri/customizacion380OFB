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

/** Generated Interface for GL_ReassignmentLine
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_GL_ReassignmentLine 
{

    /** TableName=GL_ReassignmentLine */
    public static final String Table_Name = "GL_ReassignmentLine";

    /** AD_Table_ID=2000109 */
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

    /** Column name GL_BudgetControlHeaderRef_ID */
    public static final String COLUMNNAME_GL_BudgetControlHeaderRef_ID = "GL_BudgetControlHeaderRef_ID";

	/** Set GL_BudgetControlHeaderRef_ID	  */
	public void setGL_BudgetControlHeaderRef_ID (int GL_BudgetControlHeaderRef_ID);

	/** Get GL_BudgetControlHeaderRef_ID	  */
	public int getGL_BudgetControlHeaderRef_ID();

	public I_GL_BudgetControlHeader getGL_BudgetControlHeaderRef() throws RuntimeException;

    /** Column name GL_BudgetControlLineRef_ID */
    public static final String COLUMNNAME_GL_BudgetControlLineRef_ID = "GL_BudgetControlLineRef_ID";

	/** Set GL_BudgetControlLineRef_ID	  */
	public void setGL_BudgetControlLineRef_ID (int GL_BudgetControlLineRef_ID);

	/** Get GL_BudgetControlLineRef_ID	  */
	public int getGL_BudgetControlLineRef_ID();

	public I_GL_BudgetControlLine getGL_BudgetControlLineRef() throws RuntimeException;

    /** Column name GL_BudgetControlRef_ID */
    public static final String COLUMNNAME_GL_BudgetControlRef_ID = "GL_BudgetControlRef_ID";

	/** Set GL_BudgetControlRef_ID	  */
	public void setGL_BudgetControlRef_ID (int GL_BudgetControlRef_ID);

	/** Get GL_BudgetControlRef_ID	  */
	public int getGL_BudgetControlRef_ID();

	public org.compiere.model.I_GL_BudgetControl getGL_BudgetControlRef() throws RuntimeException;

    /** Column name GL_BudgetDetailRef_ID */
    public static final String COLUMNNAME_GL_BudgetDetailRef_ID = "GL_BudgetDetailRef_ID";

	/** Set GL_BudgetDetailRef_ID	  */
	public void setGL_BudgetDetailRef_ID (int GL_BudgetDetailRef_ID);

	/** Get GL_BudgetDetailRef_ID	  */
	public int getGL_BudgetDetailRef_ID();

	public I_GL_BudgetDetail getGL_BudgetDetailRef() throws RuntimeException;

    /** Column name GL_Reassignment_ID */
    public static final String COLUMNNAME_GL_Reassignment_ID = "GL_Reassignment_ID";

	/** Set GL_Reassignment ID	  */
	public void setGL_Reassignment_ID (int GL_Reassignment_ID);

	/** Get GL_Reassignment ID	  */
	public int getGL_Reassignment_ID();

	public I_GL_Reassignment getGL_Reassignment() throws RuntimeException;

    /** Column name GL_ReassignmentLine_ID */
    public static final String COLUMNNAME_GL_ReassignmentLine_ID = "GL_ReassignmentLine_ID";

	/** Set GL_ReassignmentLine ID	  */
	public void setGL_ReassignmentLine_ID (int GL_ReassignmentLine_ID);

	/** Get GL_ReassignmentLine ID	  */
	public int getGL_ReassignmentLine_ID();

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
