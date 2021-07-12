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

/** Generated Model for CC_Evolution_Kine
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_Evolution_Kine extends PO implements I_CC_Evolution_Kine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190805L;

    /** Standard Constructor */
    public X_CC_Evolution_Kine (Properties ctx, int CC_Evolution_Kine_ID, String trxName)
    {
      super (ctx, CC_Evolution_Kine_ID, trxName);
      /** if (CC_Evolution_Kine_ID == 0)
        {
			setCC_Evolution_Kine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CC_Evolution_Kine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_Evolution_Kine[")
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

	/** Set CC_Evolution_Kine ID.
		@param CC_Evolution_Kine_ID CC_Evolution_Kine ID	  */
	public void setCC_Evolution_Kine_ID (int CC_Evolution_Kine_ID)
	{
		if (CC_Evolution_Kine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_Evolution_Kine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_Evolution_Kine_ID, Integer.valueOf(CC_Evolution_Kine_ID));
	}

	/** Get CC_Evolution_Kine ID.
		@return CC_Evolution_Kine ID	  */
	public int getCC_Evolution_Kine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_Evolution_Kine_ID);
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

	/** Set Description2.
		@param Description2 
		Optional short description of the record
	  */
	public void setDescription2 (String Description2)
	{
		set_Value (COLUMNNAME_Description2, Description2);
	}

	/** Get Description2.
		@return Optional short description of the record
	  */
	public String getDescription2 () 
	{
		return (String)get_Value(COLUMNNAME_Description2);
	}

	/** Set KineDorsiFlexTobilloD.
		@param KineDorsiFlexTobilloD KineDorsiFlexTobilloD	  */
	public void setKineDorsiFlexTobilloD (BigDecimal KineDorsiFlexTobilloD)
	{
		set_Value (COLUMNNAME_KineDorsiFlexTobilloD, KineDorsiFlexTobilloD);
	}

	/** Get KineDorsiFlexTobilloD.
		@return KineDorsiFlexTobilloD	  */
	public BigDecimal getKineDorsiFlexTobilloD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_KineDorsiFlexTobilloD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set KineExtMunecaD.
		@param KineExtMunecaD KineExtMunecaD	  */
	public void setKineExtMunecaD (BigDecimal KineExtMunecaD)
	{
		set_Value (COLUMNNAME_KineExtMunecaD, KineExtMunecaD);
	}

	/** Get KineExtMunecaD.
		@return KineExtMunecaD	  */
	public BigDecimal getKineExtMunecaD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_KineExtMunecaD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set KineExtMunecaI.
		@param KineExtMunecaI KineExtMunecaI	  */
	public void setKineExtMunecaI (BigDecimal KineExtMunecaI)
	{
		set_Value (COLUMNNAME_KineExtMunecaI, KineExtMunecaI);
	}

	/** Get KineExtMunecaI.
		@return KineExtMunecaI	  */
	public BigDecimal getKineExtMunecaI () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_KineExtMunecaI);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set KineExtRodillaD.
		@param KineExtRodillaD KineExtRodillaD	  */
	public void setKineExtRodillaD (BigDecimal KineExtRodillaD)
	{
		set_Value (COLUMNNAME_KineExtRodillaD, KineExtRodillaD);
	}

	/** Get KineExtRodillaD.
		@return KineExtRodillaD	  */
	public BigDecimal getKineExtRodillaD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_KineExtRodillaD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set KineExtRodillaI.
		@param KineExtRodillaI KineExtRodillaI	  */
	public void setKineExtRodillaI (BigDecimal KineExtRodillaI)
	{
		set_Value (COLUMNNAME_KineExtRodillaI, KineExtRodillaI);
	}

	/** Get KineExtRodillaI.
		@return KineExtRodillaI	  */
	public BigDecimal getKineExtRodillaI () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_KineExtRodillaI);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set KineFlexCaderaD.
		@param KineFlexCaderaD KineFlexCaderaD	  */
	public void setKineFlexCaderaD (BigDecimal KineFlexCaderaD)
	{
		set_Value (COLUMNNAME_KineFlexCaderaD, KineFlexCaderaD);
	}

	/** Get KineFlexCaderaD.
		@return KineFlexCaderaD	  */
	public BigDecimal getKineFlexCaderaD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_KineFlexCaderaD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set KineFlexCaderaI.
		@param KineFlexCaderai KineFlexCaderaI	  */
	public void setKineFlexCaderai (BigDecimal KineFlexCaderai)
	{
		set_Value (COLUMNNAME_KineFlexCaderai, KineFlexCaderai);
	}

	/** Get KineFlexCaderaI.
		@return KineFlexCaderaI	  */
	public BigDecimal getKineFlexCaderai () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_KineFlexCaderai);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set KineFlexCodoD.
		@param KineFlexCodoD KineFlexCodoD	  */
	public void setKineFlexCodoD (BigDecimal KineFlexCodoD)
	{
		set_Value (COLUMNNAME_KineFlexCodoD, KineFlexCodoD);
	}

	/** Get KineFlexCodoD.
		@return KineFlexCodoD	  */
	public BigDecimal getKineFlexCodoD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_KineFlexCodoD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set KineFlexCodoI.
		@param KineFlexCodoI KineFlexCodoI	  */
	public void setKineFlexCodoI (BigDecimal KineFlexCodoI)
	{
		set_Value (COLUMNNAME_KineFlexCodoI, KineFlexCodoI);
	}

	/** Get KineFlexCodoI.
		@return KineFlexCodoI	  */
	public BigDecimal getKineFlexCodoI () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_KineFlexCodoI);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set KineFlexHombroD.
		@param KineFlexHombroD KineFlexHombroD	  */
	public void setKineFlexHombroD (BigDecimal KineFlexHombroD)
	{
		set_Value (COLUMNNAME_KineFlexHombroD, KineFlexHombroD);
	}

	/** Get KineFlexHombroD.
		@return KineFlexHombroD	  */
	public BigDecimal getKineFlexHombroD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_KineFlexHombroD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set KineFlexHombroI.
		@param KineFlexHombroI KineFlexHombroI	  */
	public void setKineFlexHombroI (BigDecimal KineFlexHombroI)
	{
		set_Value (COLUMNNAME_KineFlexHombroI, KineFlexHombroI);
	}

	/** Get KineFlexHombroI.
		@return KineFlexHombroI	  */
	public BigDecimal getKineFlexHombroI () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_KineFlexHombroI);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set KineMRCSS.
		@param KineMRCSS KineMRCSS	  */
	public void setKineMRCSS (BigDecimal KineMRCSS)
	{
		throw new IllegalArgumentException ("KineMRCSS is virtual column");	}

	/** Get KineMRCSS.
		@return KineMRCSS	  */
	public BigDecimal getKineMRCSS () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_KineMRCSS);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}