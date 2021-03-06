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
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for R_ServiceRequestLine
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_R_ServiceRequestLine extends PO implements I_R_ServiceRequestLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170525L;

    /** Standard Constructor */
    public X_R_ServiceRequestLine (Properties ctx, int R_ServiceRequestLine_ID, String trxName)
    {
      super (ctx, R_ServiceRequestLine_ID, trxName);
      /** if (R_ServiceRequestLine_ID == 0)
        {
			setR_ServiceRequestLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_R_ServiceRequestLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_R_ServiceRequestLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_LocationRef() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_LocationRef_ID(), get_TrxName());	}

	/** Set C_BPartner_LocationRef_ID.
		@param C_BPartner_LocationRef_ID C_BPartner_LocationRef_ID	  */
	public void setC_BPartner_LocationRef_ID (int C_BPartner_LocationRef_ID)
	{
		if (C_BPartner_LocationRef_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_LocationRef_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_LocationRef_ID, Integer.valueOf(C_BPartner_LocationRef_ID));
	}

	/** Get C_BPartner_LocationRef_ID.
		@return C_BPartner_LocationRef_ID	  */
	public int getC_BPartner_LocationRef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_LocationRef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartnerRef() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartnerRef_ID(), get_TrxName());	}

	/** Set C_BPartnerRef_ID.
		@param C_BPartnerRef_ID C_BPartnerRef_ID	  */
	public void setC_BPartnerRef_ID (int C_BPartnerRef_ID)
	{
		if (C_BPartnerRef_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerRef_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerRef_ID, Integer.valueOf(C_BPartnerRef_ID));
	}

	/** Get C_BPartnerRef_ID.
		@return C_BPartnerRef_ID	  */
	public int getC_BPartnerRef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerRef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getC_OrderLine_ID(), get_TrxName());	}

	/** Set Sales Order Line.
		@param C_OrderLine_ID 
		Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Sales Order Line.
		@return Sales Order Line
	  */
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_OrderShipCalendar getC_OrderShipCalendar() throws RuntimeException
    {
		return (I_C_OrderShipCalendar)MTable.get(getCtx(), I_C_OrderShipCalendar.Table_Name)
			.getPO(getC_OrderShipCalendar_ID(), get_TrxName());	}

	/** Set C_OrderShipCalendar ID.
		@param C_OrderShipCalendar_ID C_OrderShipCalendar ID	  */
	public void setC_OrderShipCalendar_ID (int C_OrderShipCalendar_ID)
	{
		if (C_OrderShipCalendar_ID < 1) 
			set_Value (COLUMNNAME_C_OrderShipCalendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderShipCalendar_ID, Integer.valueOf(C_OrderShipCalendar_ID));
	}

	/** Get C_OrderShipCalendar ID.
		@return C_OrderShipCalendar ID	  */
	public int getC_OrderShipCalendar_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderShipCalendar_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Promised2.
		@param DatePromised2 
		Date Order was promised
	  */
	public void setDatePromised2 (Timestamp DatePromised2)
	{
		set_Value (COLUMNNAME_DatePromised2, DatePromised2);
	}

	/** Get Date Promised2.
		@return Date Order was promised
	  */
	public Timestamp getDatePromised2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised2);
	}

	/** Set DatePromised2Ref.
		@param DatePromised2Ref DatePromised2Ref	  */
	public void setDatePromised2Ref (Timestamp DatePromised2Ref)
	{
		set_Value (COLUMNNAME_DatePromised2Ref, DatePromised2Ref);
	}

	/** Get DatePromised2Ref.
		@return DatePromised2Ref	  */
	public Timestamp getDatePromised2Ref () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised2Ref);
	}

	/** Set DatePromised3.
		@param DatePromised3 DatePromised3	  */
	public void setDatePromised3 (Timestamp DatePromised3)
	{
		set_Value (COLUMNNAME_DatePromised3, DatePromised3);
	}

	/** Get DatePromised3.
		@return DatePromised3	  */
	public Timestamp getDatePromised3 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised3);
	}

	/** Set DatePromised3New.
		@param DatePromised3New DatePromised3New	  */
	public void setDatePromised3New (Timestamp DatePromised3New)
	{
		set_Value (COLUMNNAME_DatePromised3New, DatePromised3New);
	}

	/** Get DatePromised3New.
		@return DatePromised3New	  */
	public Timestamp getDatePromised3New () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised3New);
	}

	/** Set DatePromised3Ref.
		@param DatePromised3Ref DatePromised3Ref	  */
	public void setDatePromised3Ref (Timestamp DatePromised3Ref)
	{
		set_Value (COLUMNNAME_DatePromised3Ref, DatePromised3Ref);
	}

	/** Get DatePromised3Ref.
		@return DatePromised3Ref	  */
	public Timestamp getDatePromised3Ref () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised3Ref);
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Order Qty.
		@param Order_Qty 
		Order Qty
	  */
	public void setOrder_Qty (BigDecimal Order_Qty)
	{
		set_Value (COLUMNNAME_Order_Qty, Order_Qty);
	}

	/** Get Order Qty.
		@return Order Qty
	  */
	public BigDecimal getOrder_Qty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Order_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param QtyEntered 
		The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered (BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Quantity.
		@return The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** RequestAcction AD_Reference_ID=2000047 */
	public static final int REQUESTACCTION_AD_Reference_ID=2000047;
	/** SUSPENSION = Z */
	public static final String REQUESTACCTION_SUSPENSION = "Z";
	/** C. DIR.PERM. = P */
	public static final String REQUESTACCTION_CDIRPERM = "P";
	/** C. DIR.TEMP = T */
	public static final String REQUESTACCTION_CDIRTEMP = "T";
	/** ELIMINAR = E */
	public static final String REQUESTACCTION_ELIMINAR = "E";
	/** REPONER = R */
	public static final String REQUESTACCTION_REPONER = "R";
	/** REVERTIR SUSPENSION = A */
	public static final String REQUESTACCTION_REVERTIRSUSPENSION = "A";
	/** PRODUCTO DEFECTUOSO = D */
	public static final String REQUESTACCTION_PRODUCTODEFECTUOSO = "D";
	/** CREAR SERVICE REQUEST = G */
	public static final String REQUESTACCTION_CREARSERVICEREQUEST = "G";
	/** Set RequestAcction.
		@param RequestAcction RequestAcction	  */
	public void setRequestAcction (String RequestAcction)
	{

		set_Value (COLUMNNAME_RequestAcction, RequestAcction);
	}

	/** Get RequestAcction.
		@return RequestAcction	  */
	public String getRequestAcction () 
	{
		return (String)get_Value(COLUMNNAME_RequestAcction);
	}

	public I_R_ServiceRequest getR_ServiceRequest() throws RuntimeException
    {
		return (I_R_ServiceRequest)MTable.get(getCtx(), I_R_ServiceRequest.Table_Name)
			.getPO(getR_ServiceRequest_ID(), get_TrxName());	}

	/** Set ServiceRequest ID.
		@param R_ServiceRequest_ID ServiceRequest ID	  */
	public void setR_ServiceRequest_ID (int R_ServiceRequest_ID)
	{
		if (R_ServiceRequest_ID < 1) 
			set_Value (COLUMNNAME_R_ServiceRequest_ID, null);
		else 
			set_Value (COLUMNNAME_R_ServiceRequest_ID, Integer.valueOf(R_ServiceRequest_ID));
	}

	/** Get ServiceRequest ID.
		@return ServiceRequest ID	  */
	public int getR_ServiceRequest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_ServiceRequest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ServiceRequestLine ID.
		@param R_ServiceRequestLine_ID ServiceRequestLine ID	  */
	public void setR_ServiceRequestLine_ID (int R_ServiceRequestLine_ID)
	{
		if (R_ServiceRequestLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_R_ServiceRequestLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_ServiceRequestLine_ID, Integer.valueOf(R_ServiceRequestLine_ID));
	}

	/** Get ServiceRequestLine ID.
		@return ServiceRequestLine ID	  */
	public int getR_ServiceRequestLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_ServiceRequestLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sector.
		@param Sector Sector	  */
	public void setSector (int Sector)
	{
		set_Value (COLUMNNAME_Sector, Integer.valueOf(Sector));
	}

	/** Get Sector.
		@return Sector	  */
	public int getSector () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Sector);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zone.
		@param Zone Zone	  */
	public void setZone (String Zone)
	{
		set_Value (COLUMNNAME_Zone, Zone);
	}

	/** Get Zone.
		@return Zone	  */
	public String getZone () 
	{
		return (String)get_Value(COLUMNNAME_Zone);
	}
}