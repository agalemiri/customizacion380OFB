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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for MH_WoundManagement
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_MH_WoundManagement extends PO implements I_MH_WoundManagement, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190806L;

    /** Standard Constructor */
    public X_MH_WoundManagement (Properties ctx, int MH_WoundManagement_ID, String trxName)
    {
      super (ctx, MH_WoundManagement_ID, trxName);
      /** if (MH_WoundManagement_ID == 0)
        {
			setdate2 (new Timestamp( System.currentTimeMillis() ));
			setMH_WoundManagement_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MH_WoundManagement (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MH_WoundManagement[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Apperarance AD_Reference_ID=2000151 */
	public static final int APPERARANCE_AD_Reference_ID=2000151;
	/** Eritematoso = 01 */
	public static final String APPERARANCE_Eritematoso = "01";
	/** Enrojecido = 02 */
	public static final String APPERARANCE_Enrojecido = "02";
	/** Amarillo Palido = 03 */
	public static final String APPERARANCE_AmarilloPalido = "03";
	/** Necrotico Grisaceo = 04 */
	public static final String APPERARANCE_NecroticoGrisaceo = "04";
	/** Necrotico Negruzco = 05 */
	public static final String APPERARANCE_NecroticoNegruzco = "05";
	/** Set Apperarance.
		@param Apperarance Apperarance	  */
	public void setApperarance (String Apperarance)
	{

		set_Value (COLUMNNAME_Apperarance, Apperarance);
	}

	/** Get Apperarance.
		@return Apperarance	  */
	public String getApperarance () 
	{
		return (String)get_Value(COLUMNNAME_Apperarance);
	}

	/** BacterialDischarge AD_Reference_ID=319 */
	public static final int BACTERIALDISCHARGE_AD_Reference_ID=319;
	/** Si = Y */
	public static final String BACTERIALDISCHARGE_Si = "Y";
	/** No = N */
	public static final String BACTERIALDISCHARGE_No = "N";
	/** Set BacterialDischarge.
		@param BacterialDischarge BacterialDischarge	  */
	public void setBacterialDischarge (String BacterialDischarge)
	{

		set_Value (COLUMNNAME_BacterialDischarge, BacterialDischarge);
	}

	/** Get BacterialDischarge.
		@return BacterialDischarge	  */
	public String getBacterialDischarge () 
	{
		return (String)get_Value(COLUMNNAME_BacterialDischarge);
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

	public I_CC_Hospitalization getCC_Hospitalization() throws RuntimeException
    {
		return (I_CC_Hospitalization)MTable.get(getCtx(), I_CC_Hospitalization.Table_Name)
			.getPO(getCC_Hospitalization_ID(), get_TrxName());	}

	/** Set CC_Hospitalization ID.
		@param CC_Hospitalization_ID CC_Hospitalization ID	  */
	public void setCC_Hospitalization_ID (int CC_Hospitalization_ID)
	{
		if (CC_Hospitalization_ID < 1) 
			set_Value (COLUMNNAME_CC_Hospitalization_ID, null);
		else 
			set_Value (COLUMNNAME_CC_Hospitalization_ID, Integer.valueOf(CC_Hospitalization_ID));
	}

	/** Get CC_Hospitalization ID.
		@return CC_Hospitalization ID	  */
	public int getCC_Hospitalization_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_Hospitalization_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date.
		@param Date1 
		Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1)
	{
		set_Value (COLUMNNAME_Date1, Date1);
	}

	/** Get Date.
		@return Date when business is not conducted
	  */
	public Timestamp getDate1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Date1);
	}

	/** Set date2.
		@param date2 date2	  */
	public void setdate2 (Timestamp date2)
	{
		set_Value (COLUMNNAME_date2, date2);
	}

	/** Get date2.
		@return date2	  */
	public Timestamp getdate2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_date2);
	}

	/** Debridement AD_Reference_ID=2000155 */
	public static final int DEBRIDEMENT_AD_Reference_ID=2000155;
	/** Mecanico = 01 */
	public static final String DEBRIDEMENT_Mecanico = "01";
	/** Enzimatico = 02 */
	public static final String DEBRIDEMENT_Enzimatico = "02";
	/** Autolitico = 03 */
	public static final String DEBRIDEMENT_Autolitico = "03";
	/** No = 04 */
	public static final String DEBRIDEMENT_No = "04";
	/** Set Debridement.
		@param Debridement Debridement	  */
	public void setDebridement (String Debridement)
	{

		set_Value (COLUMNNAME_Debridement, Debridement);
	}

	/** Get Debridement.
		@return Debridement	  */
	public String getDebridement () 
	{
		return (String)get_Value(COLUMNNAME_Debridement);
	}

	/** Depth AD_Reference_ID=319 */
	public static final int DEPTH_AD_Reference_ID=319;
	/** Si = Y */
	public static final String DEPTH_Si = "Y";
	/** No = N */
	public static final String DEPTH_No = "N";
	/** Set Depth.
		@param Depth Depth	  */
	public void setDepth (String Depth)
	{

		set_Value (COLUMNNAME_Depth, Depth);
	}

	/** Get Depth.
		@return Depth	  */
	public String getDepth () 
	{
		return (String)get_Value(COLUMNNAME_Depth);
	}

	/** Set DepthCM.
		@param DepthCM DepthCM	  */
	public void setDepthCM (String DepthCM)
	{
		set_Value (COLUMNNAME_DepthCM, DepthCM);
	}

	/** Get DepthCM.
		@return DepthCM	  */
	public String getDepthCM () 
	{
		return (String)get_Value(COLUMNNAME_DepthCM);
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

	/** Set Extension.
		@param Extension Extension	  */
	public void setExtension (String Extension)
	{
		set_Value (COLUMNNAME_Extension, Extension);
	}

	/** Get Extension.
		@return Extension	  */
	public String getExtension () 
	{
		return (String)get_Value(COLUMNNAME_Extension);
	}

	/** Exudate AD_Reference_ID=2000152 */
	public static final int EXUDATE_AD_Reference_ID=2000152;
	/** Ausente = 01 */
	public static final String EXUDATE_Ausente = "01";
	/** Escaso = 02 */
	public static final String EXUDATE_Escaso = "02";
	/** Moderado = 03 */
	public static final String EXUDATE_Moderado = "03";
	/** Abundante = 04 */
	public static final String EXUDATE_Abundante = "04";
	/** Set Exudate.
		@param Exudate Exudate	  */
	public void setExudate (String Exudate)
	{

		set_Value (COLUMNNAME_Exudate, Exudate);
	}

	/** Get Exudate.
		@return Exudate	  */
	public String getExudate () 
	{
		return (String)get_Value(COLUMNNAME_Exudate);
	}

	/** ExudateQuality AD_Reference_ID=2000153 */
	public static final int EXUDATEQUALITY_AD_Reference_ID=2000153;
	/** Seroso = 01 */
	public static final String EXUDATEQUALITY_Seroso = "01";
	/** Sero Hematico = 02 */
	public static final String EXUDATEQUALITY_SeroHematico = "02";
	/** Turbio = 03 */
	public static final String EXUDATEQUALITY_Turbio = "03";
	/** Purulento = 04 */
	public static final String EXUDATEQUALITY_Purulento = "04";
	/** Set ExudateQuality.
		@param ExudateQuality ExudateQuality	  */
	public void setExudateQuality (String ExudateQuality)
	{

		set_Value (COLUMNNAME_ExudateQuality, ExudateQuality);
	}

	/** Get ExudateQuality.
		@return ExudateQuality	  */
	public String getExudateQuality () 
	{
		return (String)get_Value(COLUMNNAME_ExudateQuality);
	}

	/** Fixation AD_Reference_ID=2000156 */
	public static final int FIXATION_AD_Reference_ID=2000156;
	/** Tela Micropore = 01 */
	public static final String FIXATION_TelaMicropore = "01";
	/** Otro = 05 */
	public static final String FIXATION_Otro = "05";
	/** Fixomul = 02 */
	public static final String FIXATION_Fixomul = "02";
	/** Elastomul = 03 */
	public static final String FIXATION_Elastomul = "03";
	/** Transparente Adhesivo = 04 */
	public static final String FIXATION_TransparenteAdhesivo = "04";
	/** Set Fixation.
		@param Fixation Fixation	  */
	public void setFixation (String Fixation)
	{

		set_Value (COLUMNNAME_Fixation, Fixation);
	}

	/** Get Fixation.
		@return Fixation	  */
	public String getFixation () 
	{
		return (String)get_Value(COLUMNNAME_Fixation);
	}

	/** Set Location comment.
		@param LocationComment 
		Additional comments or remarks concerning the location
	  */
	public void setLocationComment (String LocationComment)
	{
		set_Value (COLUMNNAME_LocationComment, LocationComment);
	}

	/** Get Location comment.
		@return Additional comments or remarks concerning the location
	  */
	public String getLocationComment () 
	{
		return (String)get_Value(COLUMNNAME_LocationComment);
	}

	/** Set MH_WoundManagement ID.
		@param MH_WoundManagement_ID MH_WoundManagement ID	  */
	public void setMH_WoundManagement_ID (int MH_WoundManagement_ID)
	{
		if (MH_WoundManagement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MH_WoundManagement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MH_WoundManagement_ID, Integer.valueOf(MH_WoundManagement_ID));
	}

	/** Get MH_WoundManagement ID.
		@return MH_WoundManagement ID	  */
	public int getMH_WoundManagement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MH_WoundManagement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Skin AD_Reference_ID=2000154 */
	public static final int SKIN_AD_Reference_ID=2000154;
	/** Sana = 01 */
	public static final String SKIN_Sana = "01";
	/** Descamada = 02 */
	public static final String SKIN_Descamada = "02";
	/** Eritematosa = 03 */
	public static final String SKIN_Eritematosa = "03";
	/** Macerada = 04 */
	public static final String SKIN_Macerada = "04";
	/** Ulcera = 05 */
	public static final String SKIN_Ulcera = "05";
	/** Set Skin.
		@param Skin Skin	  */
	public void setSkin (String Skin)
	{

		set_Value (COLUMNNAME_Skin, Skin);
	}

	/** Get Skin.
		@return Skin	  */
	public String getSkin () 
	{
		return (String)get_Value(COLUMNNAME_Skin);
	}
}