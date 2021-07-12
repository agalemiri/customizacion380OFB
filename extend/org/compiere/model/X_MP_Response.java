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

/** Generated Model for MP_Response
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_MP_Response extends PO implements I_MP_Response, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170809L;

    /** Standard Constructor */
    public X_MP_Response (Properties ctx, int MP_Response_ID, String trxName)
    {
      super (ctx, MP_Response_ID, trxName);
      /** if (MP_Response_ID == 0)
        {
			setMP_IndicatorDetail_ID (0);
			setMP_Response_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MP_Response (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MP_Response[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_MP_IndicatorDetail getMP_IndicatorDetail() throws RuntimeException
    {
		return (I_MP_IndicatorDetail)MTable.get(getCtx(), I_MP_IndicatorDetail.Table_Name)
			.getPO(getMP_IndicatorDetail_ID(), get_TrxName());	}

	/** Set MP_IndicatorDetail ID.
		@param MP_IndicatorDetail_ID MP_IndicatorDetail ID	  */
	public void setMP_IndicatorDetail_ID (int MP_IndicatorDetail_ID)
	{
		if (MP_IndicatorDetail_ID < 1) 
			set_Value (COLUMNNAME_MP_IndicatorDetail_ID, null);
		else 
			set_Value (COLUMNNAME_MP_IndicatorDetail_ID, Integer.valueOf(MP_IndicatorDetail_ID));
	}

	/** Get MP_IndicatorDetail ID.
		@return MP_IndicatorDetail ID	  */
	public int getMP_IndicatorDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MP_IndicatorDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MP_Response ID.
		@param MP_Response_ID MP_Response ID	  */
	public void setMP_Response_ID (int MP_Response_ID)
	{
		if (MP_Response_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MP_Response_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MP_Response_ID, Integer.valueOf(MP_Response_ID));
	}

	/** Get MP_Response ID.
		@return MP_Response ID	  */
	public int getMP_Response_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MP_Response_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set VarA.
		@param VarA VarA	  */
	public void setVarA (String VarA)
	{
		set_Value (COLUMNNAME_VarA, VarA);
	}

	/** Get VarA.
		@return VarA	  */
	public String getVarA () 
	{
		return (String)get_Value(COLUMNNAME_VarA);
	}

	/** Set VarB.
		@param VarB VarB	  */
	public void setVarB (String VarB)
	{
		set_Value (COLUMNNAME_VarB, VarB);
	}

	/** Get VarB.
		@return VarB	  */
	public String getVarB () 
	{
		return (String)get_Value(COLUMNNAME_VarB);
	}


}