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

/** Generated Interface for HR_AdministrativeRequestsL
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_HR_AdministrativeRequestsL 
{

    /** TableName=HR_AdministrativeRequestsL */
    public static final String Table_Name = "HR_AdministrativeRequestsL";

    /** AD_Table_ID=2000061 */
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

    /** Column name AdmType */
    public static final String COLUMNNAME_AdmType = "AdmType";

	/** Set AdmType	  */
	public void setAdmType (String AdmType);

	/** Get AdmType	  */
	public String getAdmType();

    /** Column name AttType */
    public static final String COLUMNNAME_AttType = "AttType";

	/** Set AttType	  */
	public void setAttType (String AttType);

	/** Get AttType	  */
	public String getAttType();

    /** Column name ConfirmType */
    public static final String COLUMNNAME_ConfirmType = "ConfirmType";

	/** Set Confirmation Type.
	  * Type of confirmation
	  */
	public void setConfirmType (String ConfirmType);

	/** Get Confirmation Type.
	  * Type of confirmation
	  */
	public String getConfirmType();

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

    /** Column name dateendrequest */
    public static final String COLUMNNAME_dateendrequest = "dateendrequest";

	/** Set dateendrequest	  */
	public void setdateendrequest (Timestamp dateendrequest);

	/** Get dateendrequest	  */
	public Timestamp getdateendrequest();

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

    /** Column name datestartrequest */
    public static final String COLUMNNAME_datestartrequest = "datestartrequest";

	/** Set datestartrequest	  */
	public void setdatestartrequest (Timestamp datestartrequest);

	/** Get datestartrequest	  */
	public Timestamp getdatestartrequest();

    /** Column name Days */
    public static final String COLUMNNAME_Days = "Days";

	/** Set Days	  */
	public void setDays (BigDecimal Days);

	/** Get Days	  */
	public BigDecimal getDays();

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

    /** Column name hours */
    public static final String COLUMNNAME_hours = "hours";

	/** Set hours	  */
	public void sethours (BigDecimal hours);

	/** Get hours	  */
	public BigDecimal gethours();

    /** Column name HR_AdministrativeRequests_ID */
    public static final String COLUMNNAME_HR_AdministrativeRequests_ID = "HR_AdministrativeRequests_ID";

	/** Set HR_AdministrativeRequests	  */
	public void setHR_AdministrativeRequests_ID (int HR_AdministrativeRequests_ID);

	/** Get HR_AdministrativeRequests	  */
	public int getHR_AdministrativeRequests_ID();

	public I_HR_AdministrativeRequests getHR_AdministrativeRequests() throws RuntimeException;

    /** Column name HR_AdministrativeRequestsL_ID */
    public static final String COLUMNNAME_HR_AdministrativeRequestsL_ID = "HR_AdministrativeRequestsL_ID";

	/** Set HR_AdministrativeRequestsL ID	  */
	public void setHR_AdministrativeRequestsL_ID (int HR_AdministrativeRequestsL_ID);

	/** Get HR_AdministrativeRequestsL ID	  */
	public int getHR_AdministrativeRequestsL_ID();

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

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

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
