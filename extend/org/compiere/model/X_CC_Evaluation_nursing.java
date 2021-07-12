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

/** Generated Model for CC_Evaluation_nursing
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_Evaluation_nursing extends PO implements I_CC_Evaluation_nursing, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170704L;

    /** Standard Constructor */
    public X_CC_Evaluation_nursing (Properties ctx, int CC_Evaluation_nursing_ID, String trxName)
    {
      super (ctx, CC_Evaluation_nursing_ID, trxName);
      /** if (CC_Evaluation_nursing_ID == 0)
        {
			setCC_Evaluation_nursing_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CC_Evaluation_nursing (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_Evaluation_nursing[")
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

	/** Set User/Contact2.
		@param AD_User2_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User2_ID (int AD_User2_ID)
	{
		if (AD_User2_ID < 1) 
			set_Value (COLUMNNAME_AD_User2_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User2_ID, Integer.valueOf(AD_User2_ID));
	}

	/** Get User/Contact2.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User2_ID);
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

	/** Set CC_Evaluation_nursing ID.
		@param CC_Evaluation_nursing_ID CC_Evaluation_nursing ID	  */
	public void setCC_Evaluation_nursing_ID (int CC_Evaluation_nursing_ID)
	{
		if (CC_Evaluation_nursing_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_Evaluation_nursing_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_Evaluation_nursing_ID, Integer.valueOf(CC_Evaluation_nursing_ID));
	}

	/** Get CC_Evaluation_nursing ID.
		@return CC_Evaluation_nursing ID	  */
	public int getCC_Evaluation_nursing_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_Evaluation_nursing_ID);
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

	/** Set Comments.
		@param Comments 
		Comments or additional information
	  */
	public void setComments (String Comments)
	{
		throw new IllegalArgumentException ("Comments is virtual column");	}

	/** Get Comments.
		@return Comments or additional information
	  */
	public String getComments () 
	{
		return (String)get_Value(COLUMNNAME_Comments);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** Set Description1.
		@param Description1 
		Optional short description of the record
	  */
	public void setDescription1 (String Description1)
	{
		set_Value (COLUMNNAME_Description1, Description1);
	}

	/** Get Description1.
		@return Optional short description of the record
	  */
	public String getDescription1 () 
	{
		return (String)get_Value(COLUMNNAME_Description1);
	}

	/** Set Diuresis.
		@param Diuresis Diuresis	  */
	public void setDiuresis (BigDecimal Diuresis)
	{
		set_Value (COLUMNNAME_Diuresis, Diuresis);
	}

	/** Get Diuresis.
		@return Diuresis	  */
	public BigDecimal getDiuresis () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Diuresis);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Diuresis1.
		@param Diuresis1 Diuresis1	  */
	public void setDiuresis1 (BigDecimal Diuresis1)
	{
		set_Value (COLUMNNAME_Diuresis1, Diuresis1);
	}

	/** Get Diuresis1.
		@return Diuresis1	  */
	public BigDecimal getDiuresis1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Diuresis1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Diuresis2.
		@param Diuresis2 Diuresis2	  */
	public void setDiuresis2 (BigDecimal Diuresis2)
	{
		set_Value (COLUMNNAME_Diuresis2, Diuresis2);
	}

	/** Get Diuresis2.
		@return Diuresis2	  */
	public BigDecimal getDiuresis2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Diuresis2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set IsReadOnly1.
		@param IsReadOnly1 IsReadOnly1	  */
	public void setIsReadOnly1 (boolean IsReadOnly1)
	{
		set_Value (COLUMNNAME_IsReadOnly1, Boolean.valueOf(IsReadOnly1));
	}

	/** Get IsReadOnly1.
		@return IsReadOnly1	  */
	public boolean isReadOnly1 () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadOnly1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Pulse.
		@param Pulse Pulse	  */
	public void setPulse (BigDecimal Pulse)
	{
		set_Value (COLUMNNAME_Pulse, Pulse);
	}

	/** Get Pulse.
		@return Pulse	  */
	public BigDecimal getPulse () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Pulse);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Size X.
		@param SizeX 
		X (horizontal) dimension size
	  */
	public void setSizeX (BigDecimal SizeX)
	{
		set_Value (COLUMNNAME_SizeX, SizeX);
	}

	/** Get Size X.
		@return X (horizontal) dimension size
	  */
	public BigDecimal getSizeX () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SizeX);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}