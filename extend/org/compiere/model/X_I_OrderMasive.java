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

/** Generated Model for I_OrderMasive
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_I_OrderMasive extends PO implements I_I_OrderMasive, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170726L;

    /** Standard Constructor */
    public X_I_OrderMasive (Properties ctx, int I_OrderMasive_ID, String trxName)
    {
      super (ctx, I_OrderMasive_ID, trxName);
      /** if (I_OrderMasive_ID == 0)
        {
			setI_OrderMasive_ID (0);
        } */
    }

    /** Load Constructor */
    public X_I_OrderMasive (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_I_OrderMasive[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AD_ClientName.
		@param AD_ClientName AD_ClientName	  */
	public void setAD_ClientName (String AD_ClientName)
	{
		set_Value (COLUMNNAME_AD_ClientName, AD_ClientName);
	}

	/** Get AD_ClientName.
		@return AD_ClientName	  */
	public String getAD_ClientName () 
	{
		return (String)get_Value(COLUMNNAME_AD_ClientName);
	}

	/** Set AD_OrgName.
		@param AD_OrgName AD_OrgName	  */
	public void setAD_OrgName (String AD_OrgName)
	{
		set_Value (COLUMNNAME_AD_OrgName, AD_OrgName);
	}

	/** Get AD_OrgName.
		@return AD_OrgName	  */
	public String getAD_OrgName () 
	{
		return (String)get_Value(COLUMNNAME_AD_OrgName);
	}

	/** Set AddressNumber.
		@param AddressNumber AddressNumber	  */
	public void setAddressNumber (BigDecimal AddressNumber)
	{
		set_Value (COLUMNNAME_AddressNumber, AddressNumber);
	}

	/** Get AddressNumber.
		@return AddressNumber	  */
	public BigDecimal getAddressNumber () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AddressNumber);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AddressNumberL.
		@param AddressNumberL AddressNumberL	  */
	public void setAddressNumberL (BigDecimal AddressNumberL)
	{
		set_Value (COLUMNNAME_AddressNumberL, AddressNumberL);
	}

	/** Get AddressNumberL.
		@return AddressNumberL	  */
	public BigDecimal getAddressNumberL () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AddressNumberL);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AddressType.
		@param AddressType AddressType	  */
	public void setAddressType (String AddressType)
	{
		set_Value (COLUMNNAME_AddressType, AddressType);
	}

	/** Get AddressType.
		@return AddressType	  */
	public String getAddressType () 
	{
		return (String)get_Value(COLUMNNAME_AddressType);
	}

	/** Set AddressTypeL.
		@param AddressTypeL AddressTypeL	  */
	public void setAddressTypeL (String AddressTypeL)
	{
		set_Value (COLUMNNAME_AddressTypeL, AddressTypeL);
	}

	/** Get AddressTypeL.
		@return AddressTypeL	  */
	public String getAddressTypeL () 
	{
		return (String)get_Value(COLUMNNAME_AddressTypeL);
	}

	/** Set Birthday.
		@param Birthday 
		Birthday or Anniversary day
	  */
	public void setBirthday (Timestamp Birthday)
	{
		set_Value (COLUMNNAME_Birthday, Birthday);
	}

	/** Get Birthday.
		@return Birthday or Anniversary day
	  */
	public Timestamp getBirthday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Birthday);
	}

	/** Set BirthdayL.
		@param BirthdayL BirthdayL	  */
	public void setBirthdayL (Timestamp BirthdayL)
	{
		set_Value (COLUMNNAME_BirthdayL, BirthdayL);
	}

	/** Get BirthdayL.
		@return BirthdayL	  */
	public Timestamp getBirthdayL () 
	{
		return (Timestamp)get_Value(COLUMNNAME_BirthdayL);
	}

	/** Set Block.
		@param Block Block	  */
	public void setBlock (String Block)
	{
		set_Value (COLUMNNAME_Block, Block);
	}

	/** Get Block.
		@return Block	  */
	public String getBlock () 
	{
		return (String)get_Value(COLUMNNAME_Block);
	}

	/** Set BlockL.
		@param BlockL BlockL	  */
	public void setBlockL (String BlockL)
	{
		set_Value (COLUMNNAME_BlockL, BlockL);
	}

	/** Get BlockL.
		@return BlockL	  */
	public String getBlockL () 
	{
		return (String)get_Value(COLUMNNAME_BlockL);
	}

	/** Set BorthdayL.
		@param BorthdayL BorthdayL	  */
	public void setBorthdayL (Timestamp BorthdayL)
	{
		set_Value (COLUMNNAME_BorthdayL, BorthdayL);
	}

	/** Get BorthdayL.
		@return BorthdayL	  */
	public Timestamp getBorthdayL () 
	{
		return (Timestamp)get_Value(COLUMNNAME_BorthdayL);
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

	public org.compiere.model.I_C_BPartner_Location getC_Bpartner_LocationLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_Bpartner_LocationLine_ID(), get_TrxName());	}

	/** Set C_Bpartner_LocationLine_ID.
		@param C_Bpartner_LocationLine_ID C_Bpartner_LocationLine_ID	  */
	public void setC_Bpartner_LocationLine_ID (int C_Bpartner_LocationLine_ID)
	{
		if (C_Bpartner_LocationLine_ID < 1) 
			set_Value (COLUMNNAME_C_Bpartner_LocationLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_Bpartner_LocationLine_ID, Integer.valueOf(C_Bpartner_LocationLine_ID));
	}

	/** Get C_Bpartner_LocationLine_ID.
		@return C_Bpartner_LocationLine_ID	  */
	public int getC_Bpartner_LocationLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Bpartner_LocationLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartnerLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartnerLine_ID(), get_TrxName());	}

	/** Set C_BPartnerLine_ID.
		@param C_BPartnerLine_ID C_BPartnerLine_ID	  */
	public void setC_BPartnerLine_ID (int C_BPartnerLine_ID)
	{
		if (C_BPartnerLine_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerLine_ID, Integer.valueOf(C_BPartnerLine_ID));
	}

	/** Get C_BPartnerLine_ID.
		@return C_BPartnerLine_ID	  */
	public int getC_BPartnerLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_CalendarCOPESA getC_CalendarCOPESA() throws RuntimeException
    {
		return (I_C_CalendarCOPESA)MTable.get(getCtx(), I_C_CalendarCOPESA.Table_Name)
			.getPO(getC_CalendarCOPESA_ID(), get_TrxName());	}

	/** Set C_CalendarCOPESA ID.
		@param C_CalendarCOPESA_ID C_CalendarCOPESA ID	  */
	public void setC_CalendarCOPESA_ID (int C_CalendarCOPESA_ID)
	{
		if (C_CalendarCOPESA_ID < 1) 
			set_Value (COLUMNNAME_C_CalendarCOPESA_ID, null);
		else 
			set_Value (COLUMNNAME_C_CalendarCOPESA_ID, Integer.valueOf(C_CalendarCOPESA_ID));
	}

	/** Get C_CalendarCOPESA ID.
		@return C_CalendarCOPESA ID	  */
	public int getC_CalendarCOPESA_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CalendarCOPESA_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_CalendarCOPESAName.
		@param C_CalendarCOPESAName C_CalendarCOPESAName	  */
	public void setC_CalendarCOPESAName (String C_CalendarCOPESAName)
	{
		set_Value (COLUMNNAME_C_CalendarCOPESAName, C_CalendarCOPESAName);
	}

	/** Get C_CalendarCOPESAName.
		@return C_CalendarCOPESAName	  */
	public String getC_CalendarCOPESAName () 
	{
		return (String)get_Value(COLUMNNAME_C_CalendarCOPESAName);
	}

	public org.compiere.model.I_C_Channel getC_Channel() throws RuntimeException
    {
		return (org.compiere.model.I_C_Channel)MTable.get(getCtx(), org.compiere.model.I_C_Channel.Table_Name)
			.getPO(getC_Channel_ID(), get_TrxName());	}

	/** Set Channel.
		@param C_Channel_ID 
		Sales Channel
	  */
	public void setC_Channel_ID (int C_Channel_ID)
	{
		if (C_Channel_ID < 1) 
			set_Value (COLUMNNAME_C_Channel_ID, null);
		else 
			set_Value (COLUMNNAME_C_Channel_ID, Integer.valueOf(C_Channel_ID));
	}

	/** Get Channel.
		@return Sales Channel
	  */
	public int getC_Channel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Channel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_ChannelName.
		@param C_ChannelName C_ChannelName	  */
	public void setC_ChannelName (String C_ChannelName)
	{
		set_Value (COLUMNNAME_C_ChannelName, C_ChannelName);
	}

	/** Get C_ChannelName.
		@return C_ChannelName	  */
	public String getC_ChannelName () 
	{
		return (String)get_Value(COLUMNNAME_C_ChannelName);
	}

	public org.compiere.model.I_C_City getC_City() throws RuntimeException
    {
		return (org.compiere.model.I_C_City)MTable.get(getCtx(), org.compiere.model.I_C_City.Table_Name)
			.getPO(getC_City_ID(), get_TrxName());	}

	/** Set City.
		@param C_City_ID 
		City
	  */
	public void setC_City_ID (int C_City_ID)
	{
		if (C_City_ID < 1) 
			set_Value (COLUMNNAME_C_City_ID, null);
		else 
			set_Value (COLUMNNAME_C_City_ID, Integer.valueOf(C_City_ID));
	}

	/** Get City.
		@return City
	  */
	public int getC_City_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_City_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_City getC_CityL() throws RuntimeException
    {
		return (org.compiere.model.I_C_City)MTable.get(getCtx(), org.compiere.model.I_C_City.Table_Name)
			.getPO(getC_CityL_ID(), get_TrxName());	}

	/** Set C_CityL_ID.
		@param C_CityL_ID C_CityL_ID	  */
	public void setC_CityL_ID (int C_CityL_ID)
	{
		if (C_CityL_ID < 1) 
			set_Value (COLUMNNAME_C_CityL_ID, null);
		else 
			set_Value (COLUMNNAME_C_CityL_ID, Integer.valueOf(C_CityL_ID));
	}

	/** Get C_CityL_ID.
		@return C_CityL_ID	  */
	public int getC_CityL_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CityL_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_CityName.
		@param C_CityName C_CityName	  */
	public void setC_CityName (String C_CityName)
	{
		set_Value (COLUMNNAME_C_CityName, C_CityName);
	}

	/** Get C_CityName.
		@return C_CityName	  */
	public String getC_CityName () 
	{
		return (String)get_Value(COLUMNNAME_C_CityName);
	}

	/** Set C_CityNameL.
		@param C_CityNameL C_CityNameL	  */
	public void setC_CityNameL (String C_CityNameL)
	{
		set_Value (COLUMNNAME_C_CityNameL, C_CityNameL);
	}

	/** Get C_CityNameL.
		@return C_CityNameL	  */
	public String getC_CityNameL () 
	{
		return (String)get_Value(COLUMNNAME_C_CityNameL);
	}

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
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

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
    {
		return (org.compiere.model.I_C_PaymentTerm)MTable.get(getCtx(), org.compiere.model.I_C_PaymentTerm.Table_Name)
			.getPO(getC_PaymentTerm_ID(), get_TrxName());	}

	/** Set Payment Term.
		@param C_PaymentTerm_ID 
		The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Payment Term.
		@return The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_PaymentTermName.
		@param C_PaymentTermName C_PaymentTermName	  */
	public void setC_PaymentTermName (String C_PaymentTermName)
	{
		set_Value (COLUMNNAME_C_PaymentTermName, C_PaymentTermName);
	}

	/** Get C_PaymentTermName.
		@return C_PaymentTermName	  */
	public String getC_PaymentTermName () 
	{
		return (String)get_Value(COLUMNNAME_C_PaymentTermName);
	}

	public I_C_Province getC_Province() throws RuntimeException
    {
		return (I_C_Province)MTable.get(getCtx(), I_C_Province.Table_Name)
			.getPO(getC_Province_ID(), get_TrxName());	}

	/** Set C_Province ID.
		@param C_Province_ID C_Province ID	  */
	public void setC_Province_ID (int C_Province_ID)
	{
		if (C_Province_ID < 1) 
			set_Value (COLUMNNAME_C_Province_ID, null);
		else 
			set_Value (COLUMNNAME_C_Province_ID, Integer.valueOf(C_Province_ID));
	}

	/** Get C_Province ID.
		@return C_Province ID	  */
	public int getC_Province_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Province_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Province getC_ProvinceL() throws RuntimeException
    {
		return (I_C_Province)MTable.get(getCtx(), I_C_Province.Table_Name)
			.getPO(getC_ProvinceL_ID(), get_TrxName());	}

	/** Set C_ProvinceL_ID.
		@param C_ProvinceL_ID C_ProvinceL_ID	  */
	public void setC_ProvinceL_ID (int C_ProvinceL_ID)
	{
		if (C_ProvinceL_ID < 1) 
			set_Value (COLUMNNAME_C_ProvinceL_ID, null);
		else 
			set_Value (COLUMNNAME_C_ProvinceL_ID, Integer.valueOf(C_ProvinceL_ID));
	}

	/** Get C_ProvinceL_ID.
		@return C_ProvinceL_ID	  */
	public int getC_ProvinceL_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProvinceL_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_ProvinceName.
		@param C_ProvinceName C_ProvinceName	  */
	public void setC_ProvinceName (String C_ProvinceName)
	{
		set_Value (COLUMNNAME_C_ProvinceName, C_ProvinceName);
	}

	/** Get C_ProvinceName.
		@return C_ProvinceName	  */
	public String getC_ProvinceName () 
	{
		return (String)get_Value(COLUMNNAME_C_ProvinceName);
	}

	/** Set C_ProvinceNameL.
		@param C_ProvinceNameL C_ProvinceNameL	  */
	public void setC_ProvinceNameL (String C_ProvinceNameL)
	{
		set_Value (COLUMNNAME_C_ProvinceNameL, C_ProvinceNameL);
	}

	/** Get C_ProvinceNameL.
		@return C_ProvinceNameL	  */
	public String getC_ProvinceNameL () 
	{
		return (String)get_Value(COLUMNNAME_C_ProvinceNameL);
	}

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException
    {
		return (org.compiere.model.I_C_Region)MTable.get(getCtx(), org.compiere.model.I_C_Region.Table_Name)
			.getPO(getC_Region_ID(), get_TrxName());	}

	/** Set Region.
		@param C_Region_ID 
		Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_Value (COLUMNNAME_C_Region_ID, null);
		else 
			set_Value (COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
	}

	/** Get Region.
		@return Identifies a geographical Region
	  */
	public int getC_Region_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Region getC_RegionL() throws RuntimeException
    {
		return (org.compiere.model.I_C_Region)MTable.get(getCtx(), org.compiere.model.I_C_Region.Table_Name)
			.getPO(getC_RegionL_ID(), get_TrxName());	}

	/** Set C_RegionL_ID.
		@param C_RegionL_ID C_RegionL_ID	  */
	public void setC_RegionL_ID (int C_RegionL_ID)
	{
		if (C_RegionL_ID < 1) 
			set_Value (COLUMNNAME_C_RegionL_ID, null);
		else 
			set_Value (COLUMNNAME_C_RegionL_ID, Integer.valueOf(C_RegionL_ID));
	}

	/** Get C_RegionL_ID.
		@return C_RegionL_ID	  */
	public int getC_RegionL_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RegionL_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_RegionName.
		@param C_RegionName C_RegionName	  */
	public void setC_RegionName (String C_RegionName)
	{
		set_Value (COLUMNNAME_C_RegionName, C_RegionName);
	}

	/** Get C_RegionName.
		@return C_RegionName	  */
	public String getC_RegionName () 
	{
		return (String)get_Value(COLUMNNAME_C_RegionName);
	}

	/** Set C_RegionNameL.
		@param C_RegionNameL C_RegionNameL	  */
	public void setC_RegionNameL (String C_RegionNameL)
	{
		set_Value (COLUMNNAME_C_RegionNameL, C_RegionNameL);
	}

	/** Get C_RegionNameL.
		@return C_RegionNameL	  */
	public String getC_RegionNameL () 
	{
		return (String)get_Value(COLUMNNAME_C_RegionNameL);
	}

	public I_C_Street getC_Street() throws RuntimeException
    {
		return (I_C_Street)MTable.get(getCtx(), I_C_Street.Table_Name)
			.getPO(getC_Street_ID(), get_TrxName());	}

	/** Set C_Street ID.
		@param C_Street_ID C_Street ID	  */
	public void setC_Street_ID (int C_Street_ID)
	{
		if (C_Street_ID < 1) 
			set_Value (COLUMNNAME_C_Street_ID, null);
		else 
			set_Value (COLUMNNAME_C_Street_ID, Integer.valueOf(C_Street_ID));
	}

	/** Get C_Street ID.
		@return C_Street ID	  */
	public int getC_Street_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Street_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Street getC_StreetL() throws RuntimeException
    {
		return (I_C_Street)MTable.get(getCtx(), I_C_Street.Table_Name)
			.getPO(getC_StreetL_ID(), get_TrxName());	}

	/** Set C_StreetL_ID.
		@param C_StreetL_ID C_StreetL_ID	  */
	public void setC_StreetL_ID (int C_StreetL_ID)
	{
		if (C_StreetL_ID < 1) 
			set_Value (COLUMNNAME_C_StreetL_ID, null);
		else 
			set_Value (COLUMNNAME_C_StreetL_ID, Integer.valueOf(C_StreetL_ID));
	}

	/** Get C_StreetL_ID.
		@return C_StreetL_ID	  */
	public int getC_StreetL_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_StreetL_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_StreetName.
		@param C_StreetName C_StreetName	  */
	public void setC_StreetName (String C_StreetName)
	{
		set_Value (COLUMNNAME_C_StreetName, C_StreetName);
	}

	/** Get C_StreetName.
		@return C_StreetName	  */
	public String getC_StreetName () 
	{
		return (String)get_Value(COLUMNNAME_C_StreetName);
	}

	/** Set C_StreetNameL.
		@param C_StreetNameL C_StreetNameL	  */
	public void setC_StreetNameL (String C_StreetNameL)
	{
		set_Value (COLUMNNAME_C_StreetNameL, C_StreetNameL);
	}

	/** Get C_StreetNameL.
		@return C_StreetNameL	  */
	public String getC_StreetNameL () 
	{
		return (String)get_Value(COLUMNNAME_C_StreetNameL);
	}

	/** Set Children.
		@param Children 
		Number of Children
	  */
	public void setChildren (int Children)
	{
		set_Value (COLUMNNAME_Children, Integer.valueOf(Children));
	}

	/** Get Children.
		@return Number of Children
	  */
	public int getChildren () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Children);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ChildrenL.
		@param ChildrenL ChildrenL	  */
	public void setChildrenL (int ChildrenL)
	{
		set_Value (COLUMNNAME_ChildrenL, Integer.valueOf(ChildrenL));
	}

	/** Get ChildrenL.
		@return ChildrenL	  */
	public int getChildrenL () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ChildrenL);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DateCompleted.
		@param DateCompleted DateCompleted	  */
	public void setDateCompleted (Timestamp DateCompleted)
	{
		set_Value (COLUMNNAME_DateCompleted, DateCompleted);
	}

	/** Get DateCompleted.
		@return DateCompleted	  */
	public Timestamp getDateCompleted () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateCompleted);
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

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Digito.
		@param Digito 
		Digit for verify RUT information
	  */
	public void setDigito (String Digito)
	{
		set_Value (COLUMNNAME_Digito, Digito);
	}

	/** Get Digito.
		@return Digit for verify RUT information
	  */
	public String getDigito () 
	{
		return (String)get_Value(COLUMNNAME_Digito);
	}

	/** Set DigitoL.
		@param DigitoL DigitoL	  */
	public void setDigitoL (String DigitoL)
	{
		set_Value (COLUMNNAME_DigitoL, DigitoL);
	}

	/** Get DigitoL.
		@return DigitoL	  */
	public String getDigitoL () 
	{
		return (String)get_Value(COLUMNNAME_DigitoL);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** En SR = IS */
	public static final String DOCSTATUS_EnSR = "IS";
	/** SR Terminado = ST */
	public static final String DOCSTATUS_SRTerminado = "ST";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set EMail Address.
		@param EMail 
		Electronic Mail Address
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail Address.
		@return Electronic Mail Address
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Facebook.
		@param Facebook 
		Facebook
	  */
	public void setFacebook (String Facebook)
	{
		set_Value (COLUMNNAME_Facebook, Facebook);
	}

	/** Get Facebook.
		@return Facebook
	  */
	public String getFacebook () 
	{
		return (String)get_Value(COLUMNNAME_Facebook);
	}

	/** Set FacebookL.
		@param FacebookL FacebookL	  */
	public void setFacebookL (String FacebookL)
	{
		set_Value (COLUMNNAME_FacebookL, FacebookL);
	}

	/** Get FacebookL.
		@return FacebookL	  */
	public String getFacebookL () 
	{
		return (String)get_Value(COLUMNNAME_FacebookL);
	}

	/** Set Gender.
		@param Gender Gender	  */
	public void setGender (String Gender)
	{
		set_Value (COLUMNNAME_Gender, Gender);
	}

	/** Get Gender.
		@return Gender	  */
	public String getGender () 
	{
		return (String)get_Value(COLUMNNAME_Gender);
	}

	/** Set GenderL.
		@param GenderL GenderL	  */
	public void setGenderL (String GenderL)
	{
		set_Value (COLUMNNAME_GenderL, GenderL);
	}

	/** Get GenderL.
		@return GenderL	  */
	public String getGenderL () 
	{
		return (String)get_Value(COLUMNNAME_GenderL);
	}

	/** Set HomeNumber.
		@param HomeNumber HomeNumber	  */
	public void setHomeNumber (BigDecimal HomeNumber)
	{
		set_Value (COLUMNNAME_HomeNumber, HomeNumber);
	}

	/** Get HomeNumber.
		@return HomeNumber	  */
	public BigDecimal getHomeNumber () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HomeNumber);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set HomeNumberL.
		@param HomeNumberL HomeNumberL	  */
	public void setHomeNumberL (BigDecimal HomeNumberL)
	{
		set_Value (COLUMNNAME_HomeNumberL, HomeNumberL);
	}

	/** Get HomeNumberL.
		@return HomeNumberL	  */
	public BigDecimal getHomeNumberL () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HomeNumberL);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set HomeType.
		@param HomeType HomeType	  */
	public void setHomeType (String HomeType)
	{
		set_Value (COLUMNNAME_HomeType, HomeType);
	}

	/** Get HomeType.
		@return HomeType	  */
	public String getHomeType () 
	{
		return (String)get_Value(COLUMNNAME_HomeType);
	}

	/** Set HomeTypeL.
		@param HomeTypeL HomeTypeL	  */
	public void setHomeTypeL (String HomeTypeL)
	{
		set_Value (COLUMNNAME_HomeTypeL, HomeTypeL);
	}

	/** Get HomeTypeL.
		@return HomeTypeL	  */
	public String getHomeTypeL () 
	{
		return (String)get_Value(COLUMNNAME_HomeTypeL);
	}

	/** Set Import Error Message.
		@param I_ErrorMsg 
		Messages generated from import process
	  */
	public void setI_ErrorMsg (String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import Error Message.
		@return Messages generated from import process
	  */
	public String getI_ErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Imported.
		@param I_IsImported 
		Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Imported.
		@return Has this import been processed
	  */
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set I_OrderMasive ID.
		@param I_OrderMasive_ID I_OrderMasive ID	  */
	public void setI_OrderMasive_ID (int I_OrderMasive_ID)
	{
		if (I_OrderMasive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_OrderMasive_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_OrderMasive_ID, Integer.valueOf(I_OrderMasive_ID));
	}

	/** Get I_OrderMasive ID.
		@return I_OrderMasive ID	  */
	public int getI_OrderMasive_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_OrderMasive_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set IsDefaultCOPESA.
		@param IsDefaultCOPESA IsDefaultCOPESA	  */
	public void setIsDefaultCOPESA (String IsDefaultCOPESA)
	{
		set_Value (COLUMNNAME_IsDefaultCOPESA, IsDefaultCOPESA);
	}

	/** Get IsDefaultCOPESA.
		@return IsDefaultCOPESA	  */
	public String getIsDefaultCOPESA () 
	{
		return (String)get_Value(COLUMNNAME_IsDefaultCOPESA);
	}

	/** Set IsSMS.
		@param IsSMS IsSMS	  */
	public void setIsSMS (String IsSMS)
	{
		set_Value (COLUMNNAME_IsSMS, IsSMS);
	}

	/** Get IsSMS.
		@return IsSMS	  */
	public String getIsSMS () 
	{
		return (String)get_Value(COLUMNNAME_IsSMS);
	}

	/** Set IsWhatsapp.
		@param IsWhatsapp IsWhatsapp	  */
	public void setIsWhatsapp (String IsWhatsapp)
	{
		set_Value (COLUMNNAME_IsWhatsapp, IsWhatsapp);
	}

	/** Get IsWhatsapp.
		@return IsWhatsapp	  */
	public String getIsWhatsapp () 
	{
		return (String)get_Value(COLUMNNAME_IsWhatsapp);
	}

	/** Set Level no.
		@param LevelNo Level no	  */
	public void setLevelNo (int LevelNo)
	{
		set_Value (COLUMNNAME_LevelNo, Integer.valueOf(LevelNo));
	}

	/** Get Level no.
		@return Level no	  */
	public int getLevelNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LevelNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Line Amount.
		@param LineNetAmt 
		Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Line Amount.
		@return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Locator_Description.
		@param Locator_Description Locator_Description	  */
	public void setLocator_Description (String Locator_Description)
	{
		set_Value (COLUMNNAME_Locator_Description, Locator_Description);
	}

	/** Get Locator_Description.
		@return Locator_Description	  */
	public String getLocator_Description () 
	{
		return (String)get_Value(COLUMNNAME_Locator_Description);
	}

	/** Set LocatorDescriptionL.
		@param LocatorDescriptionL LocatorDescriptionL	  */
	public void setLocatorDescriptionL (String LocatorDescriptionL)
	{
		set_Value (COLUMNNAME_LocatorDescriptionL, LocatorDescriptionL);
	}

	/** Get LocatorDescriptionL.
		@return LocatorDescriptionL	  */
	public String getLocatorDescriptionL () 
	{
		return (String)get_Value(COLUMNNAME_LocatorDescriptionL);
	}

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
    {
		return (org.compiere.model.I_M_Locator)MTable.get(getCtx(), org.compiere.model.I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_LocatorName.
		@param M_LocatorName M_LocatorName	  */
	public void setM_LocatorName (String M_LocatorName)
	{
		set_Value (COLUMNNAME_M_LocatorName, M_LocatorName);
	}

	/** Get M_LocatorName.
		@return M_LocatorName	  */
	public String getM_LocatorName () 
	{
		return (String)get_Value(COLUMNNAME_M_LocatorName);
	}

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
    {
		return (org.compiere.model.I_M_PriceList)MTable.get(getCtx(), org.compiere.model.I_M_PriceList.Table_Name)
			.getPO(getM_PriceList_ID(), get_TrxName());	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_PriceListName.
		@param M_PriceListName M_PriceListName	  */
	public void setM_PriceListName (String M_PriceListName)
	{
		set_Value (COLUMNNAME_M_PriceListName, M_PriceListName);
	}

	/** Get M_PriceListName.
		@return M_PriceListName	  */
	public String getM_PriceListName () 
	{
		return (String)get_Value(COLUMNNAME_M_PriceListName);
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

	/** Set M_Product_Name.
		@param M_Product_Name M_Product_Name	  */
	public void setM_Product_Name (String M_Product_Name)
	{
		set_Value (COLUMNNAME_M_Product_Name, M_Product_Name);
	}

	/** Get M_Product_Name.
		@return M_Product_Name	  */
	public String getM_Product_Name () 
	{
		return (String)get_Value(COLUMNNAME_M_Product_Name);
	}

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_WareHouseName.
		@param M_WareHouseName M_WareHouseName	  */
	public void setM_WareHouseName (String M_WareHouseName)
	{
		set_Value (COLUMNNAME_M_WareHouseName, M_WareHouseName);
	}

	/** Get M_WareHouseName.
		@return M_WareHouseName	  */
	public String getM_WareHouseName () 
	{
		return (String)get_Value(COLUMNNAME_M_WareHouseName);
	}

	/** Set Marital Status.
		@param MaritalStatus Marital Status	  */
	public void setMaritalStatus (String MaritalStatus)
	{
		set_Value (COLUMNNAME_MaritalStatus, MaritalStatus);
	}

	/** Get Marital Status.
		@return Marital Status	  */
	public String getMaritalStatus () 
	{
		return (String)get_Value(COLUMNNAME_MaritalStatus);
	}

	/** Set MaritalStatusL.
		@param MaritalStatusL MaritalStatusL	  */
	public void setMaritalStatusL (String MaritalStatusL)
	{
		set_Value (COLUMNNAME_MaritalStatusL, MaritalStatusL);
	}

	/** Get MaritalStatusL.
		@return MaritalStatusL	  */
	public String getMaritalStatusL () 
	{
		return (String)get_Value(COLUMNNAME_MaritalStatusL);
	}

	/** Set MonthlyAmount.
		@param MonthlyAmount MonthlyAmount	  */
	public void setMonthlyAmount (BigDecimal MonthlyAmount)
	{
		set_Value (COLUMNNAME_MonthlyAmount, MonthlyAmount);
	}

	/** Get MonthlyAmount.
		@return MonthlyAmount	  */
	public BigDecimal getMonthlyAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MonthlyAmount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Name1.
		@param Name1 Name1	  */
	public void setName1 (String Name1)
	{
		set_Value (COLUMNNAME_Name1, Name1);
	}

	/** Get Name1.
		@return Name1	  */
	public String getName1 () 
	{
		return (String)get_Value(COLUMNNAME_Name1);
	}

	/** Set Name1L.
		@param Name1L Name1L	  */
	public void setName1L (String Name1L)
	{
		set_Value (COLUMNNAME_Name1L, Name1L);
	}

	/** Get Name1L.
		@return Name1L	  */
	public String getName1L () 
	{
		return (String)get_Value(COLUMNNAME_Name1L);
	}

	/** Set Name 2.
		@param Name2 
		Additional Name
	  */
	public void setName2 (String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	/** Get Name 2.
		@return Additional Name
	  */
	public String getName2 () 
	{
		return (String)get_Value(COLUMNNAME_Name2);
	}

	/** Set Name2L.
		@param Name2L Name2L	  */
	public void setName2L (String Name2L)
	{
		set_Value (COLUMNNAME_Name2L, Name2L);
	}

	/** Get Name2L.
		@return Name2L	  */
	public String getName2L () 
	{
		return (String)get_Value(COLUMNNAME_Name2L);
	}

	/** Set Name 3.
		@param Name3 
		Additional Name 3
	  */
	public void setName3 (String Name3)
	{
		set_Value (COLUMNNAME_Name3, Name3);
	}

	/** Get Name 3.
		@return Additional Name 3
	  */
	public String getName3 () 
	{
		return (String)get_Value(COLUMNNAME_Name3);
	}

	/** Set Name3L.
		@param Name3L Name3L	  */
	public void setName3L (String Name3L)
	{
		set_Value (COLUMNNAME_Name3L, Name3L);
	}

	/** Get Name3L.
		@return Name3L	  */
	public String getName3L () 
	{
		return (String)get_Value(COLUMNNAME_Name3L);
	}

	/** Set Name4.
		@param Name4 Name4	  */
	public void setName4 (String Name4)
	{
		set_Value (COLUMNNAME_Name4, Name4);
	}

	/** Get Name4.
		@return Name4	  */
	public String getName4 () 
	{
		return (String)get_Value(COLUMNNAME_Name4);
	}

	/** Set Name4L.
		@param Name4L Name4L	  */
	public void setName4L (String Name4L)
	{
		set_Value (COLUMNNAME_Name4L, Name4L);
	}

	/** Get Name4L.
		@return Name4L	  */
	public String getName4L () 
	{
		return (String)get_Value(COLUMNNAME_Name4L);
	}

	/** Set Name5.
		@param Name5 Name5	  */
	public void setName5 (String Name5)
	{
		set_Value (COLUMNNAME_Name5, Name5);
	}

	/** Get Name5.
		@return Name5	  */
	public String getName5 () 
	{
		return (String)get_Value(COLUMNNAME_Name5);
	}

	/** Set Name5L.
		@param Name5L Name5L	  */
	public void setName5L (String Name5L)
	{
		set_Value (COLUMNNAME_Name5L, Name5L);
	}

	/** Get Name5L.
		@return Name5L	  */
	public String getName5L () 
	{
		return (String)get_Value(COLUMNNAME_Name5L);
	}

	/** Set PartnerType.
		@param PartnerType 
		Legal or Natural
	  */
	public void setPartnerType (String PartnerType)
	{
		set_Value (COLUMNNAME_PartnerType, PartnerType);
	}

	/** Get PartnerType.
		@return Legal or Natural
	  */
	public String getPartnerType () 
	{
		return (String)get_Value(COLUMNNAME_PartnerType);
	}

	/** Set PartnerTypeL.
		@param PartnerTypeL 
		Legal or Natural
	  */
	public void setPartnerTypeL (String PartnerTypeL)
	{
		set_Value (COLUMNNAME_PartnerTypeL, PartnerTypeL);
	}

	/** Get PartnerTypeL.
		@return Legal or Natural
	  */
	public String getPartnerTypeL () 
	{
		return (String)get_Value(COLUMNNAME_PartnerTypeL);
	}

	/** PaymentRule AD_Reference_ID=195 */
	public static final int PAYMENTRULE_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULE_Cash = "B";
	/** Credit Card = K */
	public static final String PAYMENTRULE_CreditCard = "K";
	/** Direct Deposit = T */
	public static final String PAYMENTRULE_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULE_Check = "S";
	/** On Credit = P */
	public static final String PAYMENTRULE_OnCredit = "P";
	/** MENSUAL RENOVABLE = D */
	public static final String PAYMENTRULE_MENSUALRENOVABLE = "D";
	/** Mixed = M */
	public static final String PAYMENTRULE_Mixed = "M";
	/** MENSUAL NO RENOVABLE = C */
	public static final String PAYMENTRULE_MENSUALNORENOVABLE = "C";
	/** Transferencia = 1 */
	public static final String PAYMENTRULE_Transferencia = "1";
	/** Efectivo = 2 */
	public static final String PAYMENTRULE_Efectivo = "2";
	/** Tarjeta de Débito = 3 */
	public static final String PAYMENTRULE_TarjetaDeDebito = "3";
	/** PAGO TOTAL = I */
	public static final String PAYMENTRULE_PAGOTOTAL = "I";
	/** Set Payment Rule.
		@param PaymentRule 
		How you pay the invoice
	  */
	public void setPaymentRule (String PaymentRule)
	{

		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	/** Get Payment Rule.
		@return How you pay the invoice
	  */
	public String getPaymentRule () 
	{
		return (String)get_Value(COLUMNNAME_PaymentRule);
	}

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	public void setPhone (String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	public String getPhone () 
	{
		return (String)get_Value(COLUMNNAME_Phone);
	}

	/** Set PhoneType.
		@param PhoneType PhoneType	  */
	public void setPhoneType (String PhoneType)
	{
		set_Value (COLUMNNAME_PhoneType, PhoneType);
	}

	/** Get PhoneType.
		@return PhoneType	  */
	public String getPhoneType () 
	{
		return (String)get_Value(COLUMNNAME_PhoneType);
	}

	/** Set PhoneUse.
		@param PhoneUse PhoneUse	  */
	public void setPhoneUse (String PhoneUse)
	{
		set_Value (COLUMNNAME_PhoneUse, PhoneUse);
	}

	/** Get PhoneUse.
		@return PhoneUse	  */
	public String getPhoneUse () 
	{
		return (String)get_Value(COLUMNNAME_PhoneUse);
	}

	/** Set Order Reference.
		@param POReference 
		Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public void setPOReference (String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	/** Get Order Reference.
		@return Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public String getPOReference () 
	{
		return (String)get_Value(COLUMNNAME_POReference);
	}

	/** Set Unit Price.
		@param PriceActual 
		Actual Price 
	  */
	public void setPriceActual (BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Unit Price.
		@return Actual Price 
	  */
	public BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
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

	/** Set Sector.
		@param Sector Sector	  */
	public void setSector (BigDecimal Sector)
	{
		set_Value (COLUMNNAME_Sector, Sector);
	}

	/** Get Sector.
		@return Sector	  */
	public BigDecimal getSector () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Sector);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SectorL.
		@param SectorL SectorL	  */
	public void setSectorL (BigDecimal SectorL)
	{
		set_Value (COLUMNNAME_SectorL, SectorL);
	}

	/** Get SectorL.
		@return SectorL	  */
	public BigDecimal getSectorL () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SectorL);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set StreetName.
		@param StreetName StreetName	  */
	public void setStreetName (String StreetName)
	{
		set_Value (COLUMNNAME_StreetName, StreetName);
	}

	/** Get StreetName.
		@return StreetName	  */
	public String getStreetName () 
	{
		return (String)get_Value(COLUMNNAME_StreetName);
	}

	/** Set StreetNameL.
		@param StreetNameL StreetNameL	  */
	public void setStreetNameL (String StreetNameL)
	{
		set_Value (COLUMNNAME_StreetNameL, StreetNameL);
	}

	/** Get StreetNameL.
		@return StreetNameL	  */
	public String getStreetNameL () 
	{
		return (String)get_Value(COLUMNNAME_StreetNameL);
	}

	/** Set Twitter.
		@param Twitter 
		Twitter
	  */
	public void setTwitter (String Twitter)
	{
		set_Value (COLUMNNAME_Twitter, Twitter);
	}

	/** Get Twitter.
		@return Twitter
	  */
	public String getTwitter () 
	{
		return (String)get_Value(COLUMNNAME_Twitter);
	}

	/** Set TwitterL.
		@param TwitterL TwitterL	  */
	public void setTwitterL (String TwitterL)
	{
		set_Value (COLUMNNAME_TwitterL, TwitterL);
	}

	/** Get TwitterL.
		@return TwitterL	  */
	public String getTwitterL () 
	{
		return (String)get_Value(COLUMNNAME_TwitterL);
	}

	/** Set Type.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Type.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType () 
	{
		return (String)get_Value(COLUMNNAME_Type);
	}

	/** Set TypePaymentRule.
		@param TypePaymentRule TypePaymentRule	  */
	public void setTypePaymentRule (String TypePaymentRule)
	{
		set_Value (COLUMNNAME_TypePaymentRule, TypePaymentRule);
	}

	/** Get TypePaymentRule.
		@return TypePaymentRule	  */
	public String getTypePaymentRule () 
	{
		return (String)get_Value(COLUMNNAME_TypePaymentRule);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set ValueL.
		@param ValueL ValueL	  */
	public void setValueL (String ValueL)
	{
		set_Value (COLUMNNAME_ValueL, ValueL);
	}

	/** Get ValueL.
		@return ValueL	  */
	public String getValueL () 
	{
		return (String)get_Value(COLUMNNAME_ValueL);
	}

	/** Set Villa.
		@param Villa Villa	  */
	public void setVilla (String Villa)
	{
		set_Value (COLUMNNAME_Villa, Villa);
	}

	/** Get Villa.
		@return Villa	  */
	public String getVilla () 
	{
		return (String)get_Value(COLUMNNAME_Villa);
	}

	/** Set VillaL.
		@param VillaL VillaL	  */
	public void setVillaL (String VillaL)
	{
		set_Value (COLUMNNAME_VillaL, VillaL);
	}

	/** Get VillaL.
		@return VillaL	  */
	public String getVillaL () 
	{
		return (String)get_Value(COLUMNNAME_VillaL);
	}

	/** Set VIPClient.
		@param VIPClient 
		VIP Client
	  */
	public void setVIPClient (String VIPClient)
	{
		set_Value (COLUMNNAME_VIPClient, VIPClient);
	}

	/** Get VIPClient.
		@return VIP Client
	  */
	public String getVIPClient () 
	{
		return (String)get_Value(COLUMNNAME_VIPClient);
	}

	/** Set VipClientL.
		@param VipClientL VipClientL	  */
	public void setVipClientL (String VipClientL)
	{
		set_Value (COLUMNNAME_VipClientL, VipClientL);
	}

	/** Get VipClientL.
		@return VipClientL	  */
	public String getVipClientL () 
	{
		return (String)get_Value(COLUMNNAME_VipClientL);
	}

	/** Set ZipCode.
		@param ZipCode ZipCode	  */
	public void setZipCode (BigDecimal ZipCode)
	{
		set_Value (COLUMNNAME_ZipCode, ZipCode);
	}

	/** Get ZipCode.
		@return ZipCode	  */
	public BigDecimal getZipCode () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ZipCode);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ZipCodeL.
		@param ZipCodeL ZipCodeL	  */
	public void setZipCodeL (BigDecimal ZipCodeL)
	{
		set_Value (COLUMNNAME_ZipCodeL, ZipCodeL);
	}

	/** Get ZipCodeL.
		@return ZipCodeL	  */
	public BigDecimal getZipCodeL () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ZipCodeL);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zone.
		@param Zone Zone	  */
	public void setZone (BigDecimal Zone)
	{
		set_Value (COLUMNNAME_Zone, Zone);
	}

	/** Get Zone.
		@return Zone	  */
	public BigDecimal getZone () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Zone);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ZoneL.
		@param ZoneL ZoneL	  */
	public void setZoneL (BigDecimal ZoneL)
	{
		set_Value (COLUMNNAME_ZoneL, ZoneL);
	}

	/** Get ZoneL.
		@return ZoneL	  */
	public BigDecimal getZoneL () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ZoneL);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}