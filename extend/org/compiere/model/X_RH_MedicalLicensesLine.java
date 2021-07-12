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

/** Generated Model for RH_MedicalLicensesLine
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_RH_MedicalLicensesLine extends PO implements I_RH_MedicalLicensesLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180530L;

    /** Standard Constructor */
    public X_RH_MedicalLicensesLine (Properties ctx, int RH_MedicalLicensesLine_ID, String trxName)
    {
      super (ctx, RH_MedicalLicensesLine_ID, trxName);
      /** if (RH_MedicalLicensesLine_ID == 0)
        {
			setRH_MedicalLicensesLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_RH_MedicalLicensesLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_RH_MedicalLicensesLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** AccountNo AD_Reference_ID=2000040 */
	public static final int ACCOUNTNO_AD_Reference_ID=2000040;
	/** Cta 11508 = 01 */
	public static final String ACCOUNTNO_Cta11508 = "01";
	/** Cta 11512 = 02 */
	public static final String ACCOUNTNO_Cta11512 = "02";
	/** Set Account No.
		@param AccountNo 
		Account Number
	  */
	public void setAccountNo (String AccountNo)
	{

		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Account No.
		@return Account Number
	  */
	public String getAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
	}

	/** Set AccrualApproved.
		@param AccrualApproved AccrualApproved	  */
	public void setAccrualApproved (String AccrualApproved)
	{
		set_Value (COLUMNNAME_AccrualApproved, AccrualApproved);
	}

	/** Get AccrualApproved.
		@return AccrualApproved	  */
	public String getAccrualApproved () 
	{
		return (String)get_Value(COLUMNNAME_AccrualApproved);
	}

	/** Set AccruelDate.
		@param AccruelDate AccruelDate	  */
	public void setAccruelDate (Timestamp AccruelDate)
	{
		set_Value (COLUMNNAME_AccruelDate, AccruelDate);
	}

	/** Get AccruelDate.
		@return AccruelDate	  */
	public Timestamp getAccruelDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_AccruelDate);
	}

	/** Set Amount2.
		@param Amount2 Amount2	  */
	public void setAmount2 (BigDecimal Amount2)
	{
		set_Value (COLUMNNAME_Amount2, Amount2);
	}

	/** Get Amount2.
		@return Amount2	  */
	public BigDecimal getAmount2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmountRefunded.
		@param AmountRefunded AmountRefunded	  */
	public void setAmountRefunded (BigDecimal AmountRefunded)
	{
		set_Value (COLUMNNAME_AmountRefunded, AmountRefunded);
	}

	/** Get AmountRefunded.
		@return AmountRefunded	  */
	public BigDecimal getAmountRefunded () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountRefunded);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set EgressApproved.
		@param EgressApproved EgressApproved	  */
	public void setEgressApproved (String EgressApproved)
	{
		set_Value (COLUMNNAME_EgressApproved, EgressApproved);
	}

	/** Get EgressApproved.
		@return EgressApproved	  */
	public String getEgressApproved () 
	{
		return (String)get_Value(COLUMNNAME_EgressApproved);
	}

	/** Set ExitDate.
		@param ExitDate ExitDate	  */
	public void setExitDate (Timestamp ExitDate)
	{
		set_Value (COLUMNNAME_ExitDate, ExitDate);
	}

	/** Get ExitDate.
		@return ExitDate	  */
	public Timestamp getExitDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ExitDate);
	}

	/** PaymentType AD_Reference_ID=2000038 */
	public static final int PAYMENTTYPE_AD_Reference_ID=2000038;
	/** Ingreso = IN */
	public static final String PAYMENTTYPE_Ingreso = "IN";
	/** Devolucion = DE */
	public static final String PAYMENTTYPE_Devolucion = "DE";
	/** Rebaja = RB */
	public static final String PAYMENTTYPE_Rebaja = "RB";
	/** Set PaymentType.
		@param PaymentType PaymentType	  */
	public void setPaymentType (String PaymentType)
	{

		set_Value (COLUMNNAME_PaymentType, PaymentType);
	}

	/** Get PaymentType.
		@return PaymentType	  */
	public String getPaymentType () 
	{
		return (String)get_Value(COLUMNNAME_PaymentType);
	}

	public I_RH_MedicalLicenses getRH_MedicalLicenses() throws RuntimeException
    {
		return (I_RH_MedicalLicenses)MTable.get(getCtx(), I_RH_MedicalLicenses.Table_Name)
			.getPO(getRH_MedicalLicenses_ID(), get_TrxName());	}

	/** Set RH_MedicalLicenses_ID.
		@param RH_MedicalLicenses_ID RH_MedicalLicenses_ID	  */
	public void setRH_MedicalLicenses_ID (int RH_MedicalLicenses_ID)
	{
		if (RH_MedicalLicenses_ID < 1) 
			set_Value (COLUMNNAME_RH_MedicalLicenses_ID, null);
		else 
			set_Value (COLUMNNAME_RH_MedicalLicenses_ID, Integer.valueOf(RH_MedicalLicenses_ID));
	}

	/** Get RH_MedicalLicenses_ID.
		@return RH_MedicalLicenses_ID	  */
	public int getRH_MedicalLicenses_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RH_MedicalLicenses_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RH_MedicalLicensesLine ID.
		@param RH_MedicalLicensesLine_ID RH_MedicalLicensesLine ID	  */
	public void setRH_MedicalLicensesLine_ID (int RH_MedicalLicensesLine_ID)
	{
		if (RH_MedicalLicensesLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_RH_MedicalLicensesLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_RH_MedicalLicensesLine_ID, Integer.valueOf(RH_MedicalLicensesLine_ID));
	}

	/** Get RH_MedicalLicensesLine ID.
		@return RH_MedicalLicensesLine ID	  */
	public int getRH_MedicalLicensesLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RH_MedicalLicensesLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}