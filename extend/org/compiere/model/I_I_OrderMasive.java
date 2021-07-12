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

/** Generated Interface for I_OrderMasive
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0
 */
public interface I_I_OrderMasive 
{

    /** TableName=I_OrderMasive */
    public static final String Table_Name = "I_OrderMasive";

    /** AD_Table_ID=2000042 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_ClientName */
    public static final String COLUMNNAME_AD_ClientName = "AD_ClientName";

	/** Set AD_ClientName	  */
	public void setAD_ClientName (String AD_ClientName);

	/** Get AD_ClientName	  */
	public String getAD_ClientName();

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

    /** Column name AD_OrgName */
    public static final String COLUMNNAME_AD_OrgName = "AD_OrgName";

	/** Set AD_OrgName	  */
	public void setAD_OrgName (String AD_OrgName);

	/** Get AD_OrgName	  */
	public String getAD_OrgName();

    /** Column name AddressNumber */
    public static final String COLUMNNAME_AddressNumber = "AddressNumber";

	/** Set AddressNumber	  */
	public void setAddressNumber (BigDecimal AddressNumber);

	/** Get AddressNumber	  */
	public BigDecimal getAddressNumber();

    /** Column name AddressNumberL */
    public static final String COLUMNNAME_AddressNumberL = "AddressNumberL";

	/** Set AddressNumberL	  */
	public void setAddressNumberL (BigDecimal AddressNumberL);

	/** Get AddressNumberL	  */
	public BigDecimal getAddressNumberL();

    /** Column name AddressType */
    public static final String COLUMNNAME_AddressType = "AddressType";

	/** Set AddressType	  */
	public void setAddressType (String AddressType);

	/** Get AddressType	  */
	public String getAddressType();

    /** Column name AddressTypeL */
    public static final String COLUMNNAME_AddressTypeL = "AddressTypeL";

	/** Set AddressTypeL	  */
	public void setAddressTypeL (String AddressTypeL);

	/** Get AddressTypeL	  */
	public String getAddressTypeL();

    /** Column name Birthday */
    public static final String COLUMNNAME_Birthday = "Birthday";

	/** Set Birthday.
	  * Birthday or Anniversary day
	  */
	public void setBirthday (Timestamp Birthday);

	/** Get Birthday.
	  * Birthday or Anniversary day
	  */
	public Timestamp getBirthday();

    /** Column name BirthdayL */
    public static final String COLUMNNAME_BirthdayL = "BirthdayL";

	/** Set BirthdayL	  */
	public void setBirthdayL (Timestamp BirthdayL);

	/** Get BirthdayL	  */
	public Timestamp getBirthdayL();

    /** Column name Block */
    public static final String COLUMNNAME_Block = "Block";

	/** Set Block	  */
	public void setBlock (String Block);

	/** Get Block	  */
	public String getBlock();

    /** Column name BlockL */
    public static final String COLUMNNAME_BlockL = "BlockL";

	/** Set BlockL	  */
	public void setBlockL (String BlockL);

	/** Get BlockL	  */
	public String getBlockL();

    /** Column name BorthdayL */
    public static final String COLUMNNAME_BorthdayL = "BorthdayL";

	/** Set BorthdayL	  */
	public void setBorthdayL (Timestamp BorthdayL);

	/** Get BorthdayL	  */
	public Timestamp getBorthdayL();

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

    /** Column name C_Bpartner_LocationLine_ID */
    public static final String COLUMNNAME_C_Bpartner_LocationLine_ID = "C_Bpartner_LocationLine_ID";

	/** Set C_Bpartner_LocationLine_ID	  */
	public void setC_Bpartner_LocationLine_ID (int C_Bpartner_LocationLine_ID);

	/** Get C_Bpartner_LocationLine_ID	  */
	public int getC_Bpartner_LocationLine_ID();

	public org.compiere.model.I_C_BPartner_Location getC_Bpartner_LocationLine() throws RuntimeException;

    /** Column name C_BPartnerLine_ID */
    public static final String COLUMNNAME_C_BPartnerLine_ID = "C_BPartnerLine_ID";

	/** Set C_BPartnerLine_ID	  */
	public void setC_BPartnerLine_ID (int C_BPartnerLine_ID);

	/** Get C_BPartnerLine_ID	  */
	public int getC_BPartnerLine_ID();

	public org.compiere.model.I_C_BPartner getC_BPartnerLine() throws RuntimeException;

    /** Column name C_CalendarCOPESA_ID */
    public static final String COLUMNNAME_C_CalendarCOPESA_ID = "C_CalendarCOPESA_ID";

	/** Set C_CalendarCOPESA ID	  */
	public void setC_CalendarCOPESA_ID (int C_CalendarCOPESA_ID);

	/** Get C_CalendarCOPESA ID	  */
	public int getC_CalendarCOPESA_ID();

	public I_C_CalendarCOPESA getC_CalendarCOPESA() throws RuntimeException;

    /** Column name C_CalendarCOPESAName */
    public static final String COLUMNNAME_C_CalendarCOPESAName = "C_CalendarCOPESAName";

	/** Set C_CalendarCOPESAName	  */
	public void setC_CalendarCOPESAName (String C_CalendarCOPESAName);

	/** Get C_CalendarCOPESAName	  */
	public String getC_CalendarCOPESAName();

    /** Column name C_Channel_ID */
    public static final String COLUMNNAME_C_Channel_ID = "C_Channel_ID";

	/** Set Channel.
	  * Sales Channel
	  */
	public void setC_Channel_ID (int C_Channel_ID);

	/** Get Channel.
	  * Sales Channel
	  */
	public int getC_Channel_ID();

	public org.compiere.model.I_C_Channel getC_Channel() throws RuntimeException;

    /** Column name C_ChannelName */
    public static final String COLUMNNAME_C_ChannelName = "C_ChannelName";

	/** Set C_ChannelName	  */
	public void setC_ChannelName (String C_ChannelName);

	/** Get C_ChannelName	  */
	public String getC_ChannelName();

    /** Column name C_City_ID */
    public static final String COLUMNNAME_C_City_ID = "C_City_ID";

	/** Set City.
	  * City
	  */
	public void setC_City_ID (int C_City_ID);

	/** Get City.
	  * City
	  */
	public int getC_City_ID();

	public org.compiere.model.I_C_City getC_City() throws RuntimeException;

    /** Column name C_CityL_ID */
    public static final String COLUMNNAME_C_CityL_ID = "C_CityL_ID";

	/** Set C_CityL_ID	  */
	public void setC_CityL_ID (int C_CityL_ID);

	/** Get C_CityL_ID	  */
	public int getC_CityL_ID();

	public org.compiere.model.I_C_City getC_CityL() throws RuntimeException;

    /** Column name C_CityName */
    public static final String COLUMNNAME_C_CityName = "C_CityName";

	/** Set C_CityName	  */
	public void setC_CityName (String C_CityName);

	/** Get C_CityName	  */
	public String getC_CityName();

    /** Column name C_CityNameL */
    public static final String COLUMNNAME_C_CityNameL = "C_CityNameL";

	/** Set C_CityNameL	  */
	public void setC_CityNameL (String C_CityNameL);

	/** Get C_CityNameL	  */
	public String getC_CityNameL();

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException;

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

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/** Set Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/** Get Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException;

    /** Column name C_PaymentTermName */
    public static final String COLUMNNAME_C_PaymentTermName = "C_PaymentTermName";

	/** Set C_PaymentTermName	  */
	public void setC_PaymentTermName (String C_PaymentTermName);

	/** Get C_PaymentTermName	  */
	public String getC_PaymentTermName();

    /** Column name C_Province_ID */
    public static final String COLUMNNAME_C_Province_ID = "C_Province_ID";

	/** Set C_Province ID	  */
	public void setC_Province_ID (int C_Province_ID);

	/** Get C_Province ID	  */
	public int getC_Province_ID();

	public I_C_Province getC_Province() throws RuntimeException;

    /** Column name C_ProvinceL_ID */
    public static final String COLUMNNAME_C_ProvinceL_ID = "C_ProvinceL_ID";

	/** Set C_ProvinceL_ID	  */
	public void setC_ProvinceL_ID (int C_ProvinceL_ID);

	/** Get C_ProvinceL_ID	  */
	public int getC_ProvinceL_ID();

	public I_C_Province getC_ProvinceL() throws RuntimeException;

    /** Column name C_ProvinceName */
    public static final String COLUMNNAME_C_ProvinceName = "C_ProvinceName";

	/** Set C_ProvinceName	  */
	public void setC_ProvinceName (String C_ProvinceName);

	/** Get C_ProvinceName	  */
	public String getC_ProvinceName();

    /** Column name C_ProvinceNameL */
    public static final String COLUMNNAME_C_ProvinceNameL = "C_ProvinceNameL";

	/** Set C_ProvinceNameL	  */
	public void setC_ProvinceNameL (String C_ProvinceNameL);

	/** Get C_ProvinceNameL	  */
	public String getC_ProvinceNameL();

    /** Column name C_Region_ID */
    public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/** Set Region.
	  * Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID);

	/** Get Region.
	  * Identifies a geographical Region
	  */
	public int getC_Region_ID();

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException;

    /** Column name C_RegionL_ID */
    public static final String COLUMNNAME_C_RegionL_ID = "C_RegionL_ID";

	/** Set C_RegionL_ID	  */
	public void setC_RegionL_ID (int C_RegionL_ID);

	/** Get C_RegionL_ID	  */
	public int getC_RegionL_ID();

	public org.compiere.model.I_C_Region getC_RegionL() throws RuntimeException;

    /** Column name C_RegionName */
    public static final String COLUMNNAME_C_RegionName = "C_RegionName";

	/** Set C_RegionName	  */
	public void setC_RegionName (String C_RegionName);

	/** Get C_RegionName	  */
	public String getC_RegionName();

    /** Column name C_RegionNameL */
    public static final String COLUMNNAME_C_RegionNameL = "C_RegionNameL";

	/** Set C_RegionNameL	  */
	public void setC_RegionNameL (String C_RegionNameL);

	/** Get C_RegionNameL	  */
	public String getC_RegionNameL();

    /** Column name C_Street_ID */
    public static final String COLUMNNAME_C_Street_ID = "C_Street_ID";

	/** Set C_Street ID	  */
	public void setC_Street_ID (int C_Street_ID);

	/** Get C_Street ID	  */
	public int getC_Street_ID();

	public I_C_Street getC_Street() throws RuntimeException;

    /** Column name C_StreetL_ID */
    public static final String COLUMNNAME_C_StreetL_ID = "C_StreetL_ID";

	/** Set C_StreetL_ID	  */
	public void setC_StreetL_ID (int C_StreetL_ID);

	/** Get C_StreetL_ID	  */
	public int getC_StreetL_ID();

	public I_C_Street getC_StreetL() throws RuntimeException;

    /** Column name C_StreetName */
    public static final String COLUMNNAME_C_StreetName = "C_StreetName";

	/** Set C_StreetName	  */
	public void setC_StreetName (String C_StreetName);

	/** Get C_StreetName	  */
	public String getC_StreetName();

    /** Column name C_StreetNameL */
    public static final String COLUMNNAME_C_StreetNameL = "C_StreetNameL";

	/** Set C_StreetNameL	  */
	public void setC_StreetNameL (String C_StreetNameL);

	/** Get C_StreetNameL	  */
	public String getC_StreetNameL();

    /** Column name Children */
    public static final String COLUMNNAME_Children = "Children";

	/** Set Children.
	  * Number of Children
	  */
	public void setChildren (int Children);

	/** Get Children.
	  * Number of Children
	  */
	public int getChildren();

    /** Column name ChildrenL */
    public static final String COLUMNNAME_ChildrenL = "ChildrenL";

	/** Set ChildrenL	  */
	public void setChildrenL (int ChildrenL);

	/** Get ChildrenL	  */
	public int getChildrenL();

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

    /** Column name DateCompleted */
    public static final String COLUMNNAME_DateCompleted = "DateCompleted";

	/** Set DateCompleted	  */
	public void setDateCompleted (Timestamp DateCompleted);

	/** Get DateCompleted	  */
	public Timestamp getDateCompleted();

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

    /** Column name DatePromised3 */
    public static final String COLUMNNAME_DatePromised3 = "DatePromised3";

	/** Set DatePromised3	  */
	public void setDatePromised3 (Timestamp DatePromised3);

	/** Get DatePromised3	  */
	public Timestamp getDatePromised3();

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

    /** Column name Digito */
    public static final String COLUMNNAME_Digito = "Digito";

	/** Set Digito.
	  * Digit for verify RUT information
	  */
	public void setDigito (String Digito);

	/** Get Digito.
	  * Digit for verify RUT information
	  */
	public String getDigito();

    /** Column name DigitoL */
    public static final String COLUMNNAME_DigitoL = "DigitoL";

	/** Set DigitoL	  */
	public void setDigitoL (String DigitoL);

	/** Get DigitoL	  */
	public String getDigitoL();

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

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/** Set EMail Address.
	  * Electronic Mail Address
	  */
	public void setEMail (String EMail);

	/** Get EMail Address.
	  * Electronic Mail Address
	  */
	public String getEMail();

    /** Column name Facebook */
    public static final String COLUMNNAME_Facebook = "Facebook";

	/** Set Facebook.
	  * Facebook
	  */
	public void setFacebook (String Facebook);

	/** Get Facebook.
	  * Facebook
	  */
	public String getFacebook();

    /** Column name FacebookL */
    public static final String COLUMNNAME_FacebookL = "FacebookL";

	/** Set FacebookL	  */
	public void setFacebookL (String FacebookL);

	/** Get FacebookL	  */
	public String getFacebookL();

    /** Column name Gender */
    public static final String COLUMNNAME_Gender = "Gender";

	/** Set Gender	  */
	public void setGender (String Gender);

	/** Get Gender	  */
	public String getGender();

    /** Column name GenderL */
    public static final String COLUMNNAME_GenderL = "GenderL";

	/** Set GenderL	  */
	public void setGenderL (String GenderL);

	/** Get GenderL	  */
	public String getGenderL();

    /** Column name HomeNumber */
    public static final String COLUMNNAME_HomeNumber = "HomeNumber";

	/** Set HomeNumber	  */
	public void setHomeNumber (BigDecimal HomeNumber);

	/** Get HomeNumber	  */
	public BigDecimal getHomeNumber();

    /** Column name HomeNumberL */
    public static final String COLUMNNAME_HomeNumberL = "HomeNumberL";

	/** Set HomeNumberL	  */
	public void setHomeNumberL (BigDecimal HomeNumberL);

	/** Get HomeNumberL	  */
	public BigDecimal getHomeNumberL();

    /** Column name HomeType */
    public static final String COLUMNNAME_HomeType = "HomeType";

	/** Set HomeType	  */
	public void setHomeType (String HomeType);

	/** Get HomeType	  */
	public String getHomeType();

    /** Column name HomeTypeL */
    public static final String COLUMNNAME_HomeTypeL = "HomeTypeL";

	/** Set HomeTypeL	  */
	public void setHomeTypeL (String HomeTypeL);

	/** Get HomeTypeL	  */
	public String getHomeTypeL();

    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/** Set Import Error Message.
	  * Messages generated from import process
	  */
	public void setI_ErrorMsg (String I_ErrorMsg);

	/** Get Import Error Message.
	  * Messages generated from import process
	  */
	public String getI_ErrorMsg();

    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/** Set Imported.
	  * Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported);

	/** Get Imported.
	  * Has this import been processed
	  */
	public boolean isI_IsImported();

    /** Column name I_OrderMasive_ID */
    public static final String COLUMNNAME_I_OrderMasive_ID = "I_OrderMasive_ID";

	/** Set I_OrderMasive ID	  */
	public void setI_OrderMasive_ID (int I_OrderMasive_ID);

	/** Get I_OrderMasive ID	  */
	public int getI_OrderMasive_ID();

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

    /** Column name IsDefaultCOPESA */
    public static final String COLUMNNAME_IsDefaultCOPESA = "IsDefaultCOPESA";

	/** Set IsDefaultCOPESA	  */
	public void setIsDefaultCOPESA (String IsDefaultCOPESA);

	/** Get IsDefaultCOPESA	  */
	public String getIsDefaultCOPESA();

    /** Column name IsSMS */
    public static final String COLUMNNAME_IsSMS = "IsSMS";

	/** Set IsSMS	  */
	public void setIsSMS (String IsSMS);

	/** Get IsSMS	  */
	public String getIsSMS();

    /** Column name IsWhatsapp */
    public static final String COLUMNNAME_IsWhatsapp = "IsWhatsapp";

	/** Set IsWhatsapp	  */
	public void setIsWhatsapp (String IsWhatsapp);

	/** Get IsWhatsapp	  */
	public String getIsWhatsapp();

    /** Column name LevelNo */
    public static final String COLUMNNAME_LevelNo = "LevelNo";

	/** Set Level no	  */
	public void setLevelNo (int LevelNo);

	/** Get Level no	  */
	public int getLevelNo();

    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/** Set Line Amount.
	  * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt);

	/** Get Line Amount.
	  * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt();

    /** Column name Locator_Description */
    public static final String COLUMNNAME_Locator_Description = "Locator_Description";

	/** Set Locator_Description	  */
	public void setLocator_Description (String Locator_Description);

	/** Get Locator_Description	  */
	public String getLocator_Description();

    /** Column name LocatorDescriptionL */
    public static final String COLUMNNAME_LocatorDescriptionL = "LocatorDescriptionL";

	/** Set LocatorDescriptionL	  */
	public void setLocatorDescriptionL (String LocatorDescriptionL);

	/** Get LocatorDescriptionL	  */
	public String getLocatorDescriptionL();

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/** Set Locator.
	  * Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID);

	/** Get Locator.
	  * Warehouse Locator
	  */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException;

    /** Column name M_LocatorName */
    public static final String COLUMNNAME_M_LocatorName = "M_LocatorName";

	/** Set M_LocatorName	  */
	public void setM_LocatorName (String M_LocatorName);

	/** Get M_LocatorName	  */
	public String getM_LocatorName();

    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/** Set Price List.
	  * Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/** Get Price List.
	  * Unique identifier of a Price List
	  */
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException;

    /** Column name M_PriceListName */
    public static final String COLUMNNAME_M_PriceListName = "M_PriceListName";

	/** Set M_PriceListName	  */
	public void setM_PriceListName (String M_PriceListName);

	/** Get M_PriceListName	  */
	public String getM_PriceListName();

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

    /** Column name M_Product_Name */
    public static final String COLUMNNAME_M_Product_Name = "M_Product_Name";

	/** Set M_Product_Name	  */
	public void setM_Product_Name (String M_Product_Name);

	/** Get M_Product_Name	  */
	public String getM_Product_Name();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

    /** Column name M_WareHouseName */
    public static final String COLUMNNAME_M_WareHouseName = "M_WareHouseName";

	/** Set M_WareHouseName	  */
	public void setM_WareHouseName (String M_WareHouseName);

	/** Get M_WareHouseName	  */
	public String getM_WareHouseName();

    /** Column name MaritalStatus */
    public static final String COLUMNNAME_MaritalStatus = "MaritalStatus";

	/** Set Marital Status	  */
	public void setMaritalStatus (String MaritalStatus);

	/** Get Marital Status	  */
	public String getMaritalStatus();

    /** Column name MaritalStatusL */
    public static final String COLUMNNAME_MaritalStatusL = "MaritalStatusL";

	/** Set MaritalStatusL	  */
	public void setMaritalStatusL (String MaritalStatusL);

	/** Get MaritalStatusL	  */
	public String getMaritalStatusL();

    /** Column name MonthlyAmount */
    public static final String COLUMNNAME_MonthlyAmount = "MonthlyAmount";

	/** Set MonthlyAmount	  */
	public void setMonthlyAmount (BigDecimal MonthlyAmount);

	/** Get MonthlyAmount	  */
	public BigDecimal getMonthlyAmount();

    /** Column name Name1 */
    public static final String COLUMNNAME_Name1 = "Name1";

	/** Set Name1	  */
	public void setName1 (String Name1);

	/** Get Name1	  */
	public String getName1();

    /** Column name Name1L */
    public static final String COLUMNNAME_Name1L = "Name1L";

	/** Set Name1L	  */
	public void setName1L (String Name1L);

	/** Get Name1L	  */
	public String getName1L();

    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";

	/** Set Name 2.
	  * Additional Name
	  */
	public void setName2 (String Name2);

	/** Get Name 2.
	  * Additional Name
	  */
	public String getName2();

    /** Column name Name2L */
    public static final String COLUMNNAME_Name2L = "Name2L";

	/** Set Name2L	  */
	public void setName2L (String Name2L);

	/** Get Name2L	  */
	public String getName2L();

    /** Column name Name3 */
    public static final String COLUMNNAME_Name3 = "Name3";

	/** Set Name 3.
	  * Additional Name 3
	  */
	public void setName3 (String Name3);

	/** Get Name 3.
	  * Additional Name 3
	  */
	public String getName3();

    /** Column name Name3L */
    public static final String COLUMNNAME_Name3L = "Name3L";

	/** Set Name3L	  */
	public void setName3L (String Name3L);

	/** Get Name3L	  */
	public String getName3L();

    /** Column name Name4 */
    public static final String COLUMNNAME_Name4 = "Name4";

	/** Set Name4	  */
	public void setName4 (String Name4);

	/** Get Name4	  */
	public String getName4();

    /** Column name Name4L */
    public static final String COLUMNNAME_Name4L = "Name4L";

	/** Set Name4L	  */
	public void setName4L (String Name4L);

	/** Get Name4L	  */
	public String getName4L();

    /** Column name Name5 */
    public static final String COLUMNNAME_Name5 = "Name5";

	/** Set Name5	  */
	public void setName5 (String Name5);

	/** Get Name5	  */
	public String getName5();

    /** Column name Name5L */
    public static final String COLUMNNAME_Name5L = "Name5L";

	/** Set Name5L	  */
	public void setName5L (String Name5L);

	/** Get Name5L	  */
	public String getName5L();

    /** Column name PartnerType */
    public static final String COLUMNNAME_PartnerType = "PartnerType";

	/** Set PartnerType.
	  * Legal or Natural
	  */
	public void setPartnerType (String PartnerType);

	/** Get PartnerType.
	  * Legal or Natural
	  */
	public String getPartnerType();

    /** Column name PartnerTypeL */
    public static final String COLUMNNAME_PartnerTypeL = "PartnerTypeL";

	/** Set PartnerTypeL.
	  * Legal or Natural
	  */
	public void setPartnerTypeL (String PartnerTypeL);

	/** Get PartnerTypeL.
	  * Legal or Natural
	  */
	public String getPartnerTypeL();

    /** Column name PaymentRule */
    public static final String COLUMNNAME_PaymentRule = "PaymentRule";

	/** Set Payment Rule.
	  * How you pay the invoice
	  */
	public void setPaymentRule (String PaymentRule);

	/** Get Payment Rule.
	  * How you pay the invoice
	  */
	public String getPaymentRule();

    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/** Set Phone.
	  * Identifies a telephone number
	  */
	public void setPhone (String Phone);

	/** Get Phone.
	  * Identifies a telephone number
	  */
	public String getPhone();

    /** Column name PhoneType */
    public static final String COLUMNNAME_PhoneType = "PhoneType";

	/** Set PhoneType	  */
	public void setPhoneType (String PhoneType);

	/** Get PhoneType	  */
	public String getPhoneType();

    /** Column name PhoneUse */
    public static final String COLUMNNAME_PhoneUse = "PhoneUse";

	/** Set PhoneUse	  */
	public void setPhoneUse (String PhoneUse);

	/** Get PhoneUse	  */
	public String getPhoneUse();

    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/** Set Order Reference.
	  * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public void setPOReference (String POReference);

	/** Get Order Reference.
	  * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public String getPOReference();

    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

	/** Set Unit Price.
	  * Actual Price 
	  */
	public void setPriceActual (BigDecimal PriceActual);

	/** Get Unit Price.
	  * Actual Price 
	  */
	public BigDecimal getPriceActual();

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

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

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

    /** Column name Sector */
    public static final String COLUMNNAME_Sector = "Sector";

	/** Set Sector	  */
	public void setSector (BigDecimal Sector);

	/** Get Sector	  */
	public BigDecimal getSector();

    /** Column name SectorL */
    public static final String COLUMNNAME_SectorL = "SectorL";

	/** Set SectorL	  */
	public void setSectorL (BigDecimal SectorL);

	/** Get SectorL	  */
	public BigDecimal getSectorL();

    /** Column name StreetName */
    public static final String COLUMNNAME_StreetName = "StreetName";

	/** Set StreetName	  */
	public void setStreetName (String StreetName);

	/** Get StreetName	  */
	public String getStreetName();

    /** Column name StreetNameL */
    public static final String COLUMNNAME_StreetNameL = "StreetNameL";

	/** Set StreetNameL	  */
	public void setStreetNameL (String StreetNameL);

	/** Get StreetNameL	  */
	public String getStreetNameL();

    /** Column name Twitter */
    public static final String COLUMNNAME_Twitter = "Twitter";

	/** Set Twitter.
	  * Twitter
	  */
	public void setTwitter (String Twitter);

	/** Get Twitter.
	  * Twitter
	  */
	public String getTwitter();

    /** Column name TwitterL */
    public static final String COLUMNNAME_TwitterL = "TwitterL";

	/** Set TwitterL	  */
	public void setTwitterL (String TwitterL);

	/** Get TwitterL	  */
	public String getTwitterL();

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

    /** Column name TypePaymentRule */
    public static final String COLUMNNAME_TypePaymentRule = "TypePaymentRule";

	/** Set TypePaymentRule	  */
	public void setTypePaymentRule (String TypePaymentRule);

	/** Get TypePaymentRule	  */
	public String getTypePaymentRule();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name ValueL */
    public static final String COLUMNNAME_ValueL = "ValueL";

	/** Set ValueL	  */
	public void setValueL (String ValueL);

	/** Get ValueL	  */
	public String getValueL();

    /** Column name Villa */
    public static final String COLUMNNAME_Villa = "Villa";

	/** Set Villa	  */
	public void setVilla (String Villa);

	/** Get Villa	  */
	public String getVilla();

    /** Column name VillaL */
    public static final String COLUMNNAME_VillaL = "VillaL";

	/** Set VillaL	  */
	public void setVillaL (String VillaL);

	/** Get VillaL	  */
	public String getVillaL();

    /** Column name VIPClient */
    public static final String COLUMNNAME_VIPClient = "VIPClient";

	/** Set VIPClient.
	  * VIP Client
	  */
	public void setVIPClient (String VIPClient);

	/** Get VIPClient.
	  * VIP Client
	  */
	public String getVIPClient();

    /** Column name VipClientL */
    public static final String COLUMNNAME_VipClientL = "VipClientL";

	/** Set VipClientL	  */
	public void setVipClientL (String VipClientL);

	/** Get VipClientL	  */
	public String getVipClientL();

    /** Column name ZipCode */
    public static final String COLUMNNAME_ZipCode = "ZipCode";

	/** Set ZipCode	  */
	public void setZipCode (BigDecimal ZipCode);

	/** Get ZipCode	  */
	public BigDecimal getZipCode();

    /** Column name ZipCodeL */
    public static final String COLUMNNAME_ZipCodeL = "ZipCodeL";

	/** Set ZipCodeL	  */
	public void setZipCodeL (BigDecimal ZipCodeL);

	/** Get ZipCodeL	  */
	public BigDecimal getZipCodeL();

    /** Column name Zone */
    public static final String COLUMNNAME_Zone = "Zone";

	/** Set Zone	  */
	public void setZone (BigDecimal Zone);

	/** Get Zone	  */
	public BigDecimal getZone();

    /** Column name ZoneL */
    public static final String COLUMNNAME_ZoneL = "ZoneL";

	/** Set ZoneL	  */
	public void setZoneL (BigDecimal ZoneL);

	/** Get ZoneL	  */
	public BigDecimal getZoneL();
}
