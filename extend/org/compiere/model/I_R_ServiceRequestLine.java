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

/** Generated Interface for R_ServiceRequestLine
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_R_ServiceRequestLine 
{

    /** TableName=R_ServiceRequestLine */
    public static final String Table_Name = "R_ServiceRequestLine";

    /** AD_Table_ID=2000041 */
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

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    /** Column name C_BPartner_LocationRef_ID */
    public static final String COLUMNNAME_C_BPartner_LocationRef_ID = "C_BPartner_LocationRef_ID";

	/** Set C_BPartner_LocationRef_ID	  */
	public void setC_BPartner_LocationRef_ID (int C_BPartner_LocationRef_ID);

	/** Get C_BPartner_LocationRef_ID	  */
	public int getC_BPartner_LocationRef_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_LocationRef() throws RuntimeException;

    /** Column name C_BPartnerRef_ID */
    public static final String COLUMNNAME_C_BPartnerRef_ID = "C_BPartnerRef_ID";

	/** Set C_BPartnerRef_ID	  */
	public void setC_BPartnerRef_ID (int C_BPartnerRef_ID);

	/** Get C_BPartnerRef_ID	  */
	public int getC_BPartnerRef_ID();

	public org.compiere.model.I_C_BPartner getC_BPartnerRef() throws RuntimeException;

    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/** Set Sales Order Line.
	  * Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/** Get Sales Order Line.
	  * Sales Order Line
	  */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException;

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

    /** Column name DatePromised2 */
    public static final String COLUMNNAME_DatePromised2 = "DatePromised2";

	/** Set Date Promised2.
	  * Date Order was promised
	  */
	public void setDatePromised2 (Timestamp DatePromised2);

	/** Get Date Promised2.
	  * Date Order was promised
	  */
	public Timestamp getDatePromised2();

    /** Column name DatePromised2Ref */
    public static final String COLUMNNAME_DatePromised2Ref = "DatePromised2Ref";

	/** Set DatePromised2Ref	  */
	public void setDatePromised2Ref (Timestamp DatePromised2Ref);

	/** Get DatePromised2Ref	  */
	public Timestamp getDatePromised2Ref();

    /** Column name DatePromised3 */
    public static final String COLUMNNAME_DatePromised3 = "DatePromised3";

	/** Set DatePromised3	  */
	public void setDatePromised3 (Timestamp DatePromised3);

	/** Get DatePromised3	  */
	public Timestamp getDatePromised3();

    /** Column name DatePromised3New */
    public static final String COLUMNNAME_DatePromised3New = "DatePromised3New";

	/** Set DatePromised3New	  */
	public void setDatePromised3New (Timestamp DatePromised3New);

	/** Get DatePromised3New	  */
	public Timestamp getDatePromised3New();

    /** Column name DatePromised3Ref */
    public static final String COLUMNNAME_DatePromised3Ref = "DatePromised3Ref";

	/** Set DatePromised3Ref	  */
	public void setDatePromised3Ref (Timestamp DatePromised3Ref);

	/** Get DatePromised3Ref	  */
	public Timestamp getDatePromised3Ref();

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

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Line No.
	  * Unique line for this document
	  */
	public void setLine (int Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public int getLine();

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

    /** Column name Order_Qty */
    public static final String COLUMNNAME_Order_Qty = "Order_Qty";

	/** Set Order Qty.
	  * Order Qty
	  */
	public void setOrder_Qty (BigDecimal Order_Qty);

	/** Get Order Qty.
	  * Order Qty
	  */
	public BigDecimal getOrder_Qty();

    /** Column name QtyEntered */
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";

	/** Set Quantity.
	  * The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered (BigDecimal QtyEntered);

	/** Get Quantity.
	  * The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered();

    /** Column name RequestAcction */
    public static final String COLUMNNAME_RequestAcction = "RequestAcction";

	/** Set RequestAcction	  */
	public void setRequestAcction (String RequestAcction);

	/** Get RequestAcction	  */
	public String getRequestAcction();

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

    /** Column name Sector */
    public static final String COLUMNNAME_Sector = "Sector";

	/** Set Sector	  */
	public void setSector (int Sector);

	/** Get Sector	  */
	public int getSector();

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

    /** Column name Zone */
    public static final String COLUMNNAME_Zone = "Zone";

	/** Set Zone	  */
	public void setZone (String Zone);

	/** Get Zone	  */
	public String getZone();
}
