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

/** Generated Interface for MED_TemplateDay
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_MED_TemplateDay 
{

    /** TableName=MED_TemplateDay */
    public static final String Table_Name = "MED_TemplateDay";

    /** AD_Table_ID=2000078 */
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

    /** Column name CycleA1 */
    public static final String COLUMNNAME_CycleA1 = "CycleA1";

	/** Set CycleA1	  */
	public void setCycleA1 (Timestamp CycleA1);

	/** Get CycleA1	  */
	public Timestamp getCycleA1();

    /** Column name CycleA2 */
    public static final String COLUMNNAME_CycleA2 = "CycleA2";

	/** Set CycleA2	  */
	public void setCycleA2 (Timestamp CycleA2);

	/** Get CycleA2	  */
	public Timestamp getCycleA2();

    /** Column name CycleB1 */
    public static final String COLUMNNAME_CycleB1 = "CycleB1";

	/** Set CycleB1	  */
	public void setCycleB1 (Timestamp CycleB1);

	/** Get CycleB1	  */
	public Timestamp getCycleB1();

    /** Column name CycleB2 */
    public static final String COLUMNNAME_CycleB2 = "CycleB2";

	/** Set CycleB2	  */
	public void setCycleB2 (Timestamp CycleB2);

	/** Get CycleB2	  */
	public Timestamp getCycleB2();

    /** Column name Days */
    public static final String COLUMNNAME_Days = "Days";

	/** Set Days	  */
	public void setDays (String Days);

	/** Get Days	  */
	public String getDays();

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

    /** Column name MED_TemplateDay_ID */
    public static final String COLUMNNAME_MED_TemplateDay_ID = "MED_TemplateDay_ID";

	/** Set MED_TemplateDay	  */
	public void setMED_TemplateDay_ID (int MED_TemplateDay_ID);

	/** Get MED_TemplateDay	  */
	public int getMED_TemplateDay_ID();

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
