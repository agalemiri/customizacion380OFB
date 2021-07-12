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
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for I_OrderMC
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_I_OrderMC extends PO implements I_I_OrderMC, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180303L;

    /** Standard Constructor */
    public X_I_OrderMC (Properties ctx, int I_OrderMC_ID, String trxName)
    {
      super (ctx, I_OrderMC_ID, trxName);
      /** if (I_OrderMC_ID == 0)
        {
			setDocumentNo (null);
			setI_OrderMC_ID (0);
			setIsSOTrx (false);
        } */
    }

    /** Load Constructor */
    public X_I_OrderMC (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_I_OrderMC[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Business Partner Key.
		@param BPartnerValue 
		Key of the Business Partner
	  */
	public void setBPartnerValue (String BPartnerValue)
	{
		set_Value (COLUMNNAME_BPartnerValue, BPartnerValue);
	}

	/** Get Business Partner Key.
		@return Key of the Business Partner
	  */
	public String getBPartnerValue () 
	{
		return (String)get_Value(COLUMNNAME_BPartnerValue);
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

	/** Set DocumentNoMP.
		@param DocumentNoMP DocumentNoMP	  */
	public void setDocumentNoMP (String DocumentNoMP)
	{
		set_Value (COLUMNNAME_DocumentNoMP, DocumentNoMP);
	}

	/** Get DocumentNoMP.
		@return DocumentNoMP	  */
	public String getDocumentNoMP () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNoMP);
	}

	/** Set Import Order Mercado Publico ID.
		@param I_OrderMC_ID Import Order Mercado Publico ID	  */
	public void setI_OrderMC_ID (int I_OrderMC_ID)
	{
		if (I_OrderMC_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_OrderMC_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_OrderMC_ID, Integer.valueOf(I_OrderMC_ID));
	}

	/** Get Import Order Mercado Publico ID.
		@return Import Order Mercado Publico ID	  */
	public int getI_OrderMC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_OrderMC_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line Description.
		@param LineDescription 
		Description of the Line
	  */
	public void setLineDescription (String LineDescription)
	{
		set_Value (COLUMNNAME_LineDescription, LineDescription);
	}

	/** Get Line Description.
		@return Description of the Line
	  */
	public String getLineDescription () 
	{
		return (String)get_Value(COLUMNNAME_LineDescription);
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

	/** Set Product Name.
		@param ProductName 
		Name of the Product
	  */
	public void setProductName (String ProductName)
	{
		set_Value (COLUMNNAME_ProductName, ProductName);
	}

	/** Get Product Name.
		@return Name of the Product
	  */
	public String getProductName () 
	{
		return (String)get_Value(COLUMNNAME_ProductName);
	}
}