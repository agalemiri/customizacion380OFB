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

/** Generated Interface for MED_Schedule
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_MED_Schedule 
{

    /** TableName=MED_Schedule */
    public static final String Table_Name = "MED_Schedule";

    /** AD_Table_ID=2000075 */
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

    /** Column name C_BPartnerMed_ID */
    public static final String COLUMNNAME_C_BPartnerMed_ID = "C_BPartnerMed_ID";

	/** Set C_BPartnerMed_ID	  */
	public void setC_BPartnerMed_ID (int C_BPartnerMed_ID);

	/** Get C_BPartnerMed_ID	  */
	public int getC_BPartnerMed_ID();

	public org.compiere.model.I_C_BPartner getC_BPartnerMed() throws RuntimeException;

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

    /** Column name DateFrom */
    public static final String COLUMNNAME_DateFrom = "DateFrom";

	/** Set Date From.
	  * Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom);

	/** Get Date From.
	  * Starting date for a range
	  */
	public Timestamp getDateFrom();

    /** Column name DateTo */
    public static final String COLUMNNAME_DateTo = "DateTo";

	/** Set Date To.
	  * End date of a date range
	  */
	public void setDateTo (Timestamp DateTo);

	/** Get Date To.
	  * End date of a date range
	  */
	public Timestamp getDateTo();

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

    /** Column name GenerateDays */
    public static final String COLUMNNAME_GenerateDays = "GenerateDays";

	/** Set GenerateDays	  */
	public void setGenerateDays (String GenerateDays);

	/** Get GenerateDays	  */
	public String getGenerateDays();

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

    /** Column name MED_Schedule_ID */
    public static final String COLUMNNAME_MED_Schedule_ID = "MED_Schedule_ID";

	/** Set MED_Schedule ID	  */
	public void setMED_Schedule_ID (int MED_Schedule_ID);

	/** Get MED_Schedule ID	  */
	public int getMED_Schedule_ID();

    /** Column name MED_Specialty_ID */
    public static final String COLUMNNAME_MED_Specialty_ID = "MED_Specialty_ID";

	/** Set MED_Specialty ID	  */
	public void setMED_Specialty_ID (int MED_Specialty_ID);

	/** Get MED_Specialty ID	  */
	public int getMED_Specialty_ID();

	public I_MED_Specialty getMED_Specialty() throws RuntimeException;

    /** Column name MED_Template_ID */
    public static final String COLUMNNAME_MED_Template_ID = "MED_Template_ID";

	/** Set MED_Template ID	  */
	public void setMED_Template_ID (int MED_Template_ID);

	/** Get MED_Template ID	  */
	public int getMED_Template_ID();

	public I_MED_Template getMED_Template() throws RuntimeException;

    /** Column name minutes */
    public static final String COLUMNNAME_minutes = "minutes";

	/** Set minutes	  */
	public void setminutes (BigDecimal minutes);

	/** Get minutes	  */
	public BigDecimal getminutes();

    /** Column name State */
    public static final String COLUMNNAME_State = "State";

	/** Set State	  */
	public void setState (String State);

	/** Get State	  */
	public String getState();

    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

	/** Set Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type);

	/** Get Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType();

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
