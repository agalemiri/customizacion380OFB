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

/** Generated Interface for R_ServiceRLineAction
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_R_ServiceRLineAction 
{

    /** TableName=R_ServiceRLineAction */
    public static final String Table_Name = "R_ServiceRLineAction";

    /** AD_Table_ID=2000042 */
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

    /** Column name C_OrderShipCalendar_ID */
    public static final String COLUMNNAME_C_OrderShipCalendar_ID = "C_OrderShipCalendar_ID";

	/** Set C_OrderShipCalendar ID	  */
	public void setC_OrderShipCalendar_ID (int C_OrderShipCalendar_ID);

	/** Get C_OrderShipCalendar ID	  */
	public int getC_OrderShipCalendar_ID();

	public I_C_OrderShipCalendar getC_OrderShipCalendar() throws RuntimeException;

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

    /** Column name R_ServiceRequest_ID */
    public static final String COLUMNNAME_R_ServiceRequest_ID = "R_ServiceRequest_ID";

	/** Set ServiceRequest ID	  */
	public void setR_ServiceRequest_ID (int R_ServiceRequest_ID);

	/** Get ServiceRequest ID	  */
	public int getR_ServiceRequest_ID();

	public I_R_ServiceRequest getR_ServiceRequest() throws RuntimeException;

    /** Column name R_ServiceRequestLine_ID */
    public static final String COLUMNNAME_R_ServiceRequestLine_ID = "R_ServiceRequestLine_ID";

	/** Set ServiceRequestLine ID	  */
	public void setR_ServiceRequestLine_ID (int R_ServiceRequestLine_ID);

	/** Get ServiceRequestLine ID	  */
	public int getR_ServiceRequestLine_ID();

	public I_R_ServiceRequestLine getR_ServiceRequestLine() throws RuntimeException;

    /** Column name R_ServiceRLineAction_ID */
    public static final String COLUMNNAME_R_ServiceRLineAction_ID = "R_ServiceRLineAction_ID";

	/** Set R_ServiceRLineAction ID	  */
	public void setR_ServiceRLineAction_ID (int R_ServiceRLineAction_ID);

	/** Get R_ServiceRLineAction ID	  */
	public int getR_ServiceRLineAction_ID();

    /** Column name TypeAcction */
    public static final String COLUMNNAME_TypeAcction = "TypeAcction";

	/** Set TypeAcction	  */
	public void setTypeAcction (int TypeAcction);

	/** Get TypeAcction	  */
	public int getTypeAcction();

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
