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
import java.util.Properties;

/** Generated Model for CC_MedicalOrderDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_MedicalOrderDetail extends PO implements I_CC_MedicalOrderDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170704L;

    /** Standard Constructor */
    public X_CC_MedicalOrderDetail (Properties ctx, int CC_MedicalOrderDetail_ID, String trxName)
    {
      super (ctx, CC_MedicalOrderDetail_ID, trxName);
      /** if (CC_MedicalOrderDetail_ID == 0)
        {
			setCC_MedicalOrderDetail_ID (0);
			setOption2 (false);
// N
			setOption4 (false);
// N
			setOption5 (false);
// N
			setOption6 (false);
// N
			setOption7 (false);
// N
			setOption8 (false);
// N
        } */
    }

    /** Load Constructor */
    public X_CC_MedicalOrderDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_MedicalOrderDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_CC_MedicalOrder getCC_MedicalOrder() throws RuntimeException
    {
		return (I_CC_MedicalOrder)MTable.get(getCtx(), I_CC_MedicalOrder.Table_Name)
			.getPO(getCC_MedicalOrder_ID(), get_TrxName());	}

	/** Set CC_MedicalOrder ID.
		@param CC_MedicalOrder_ID CC_MedicalOrder ID	  */
	public void setCC_MedicalOrder_ID (int CC_MedicalOrder_ID)
	{
		if (CC_MedicalOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_MedicalOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_MedicalOrder_ID, Integer.valueOf(CC_MedicalOrder_ID));
	}

	/** Get CC_MedicalOrder ID.
		@return CC_MedicalOrder ID	  */
	public int getCC_MedicalOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_MedicalOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CC_MedicalOrderDetail ID.
		@param CC_MedicalOrderDetail_ID CC_MedicalOrderDetail ID	  */
	public void setCC_MedicalOrderDetail_ID (int CC_MedicalOrderDetail_ID)
	{
		if (CC_MedicalOrderDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_MedicalOrderDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_MedicalOrderDetail_ID, Integer.valueOf(CC_MedicalOrderDetail_ID));
	}

	/** Get CC_MedicalOrderDetail ID.
		@return CC_MedicalOrderDetail ID	  */
	public int getCC_MedicalOrderDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_MedicalOrderDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cycle Name.
		@param CycleName 
		Name of the Project Cycle
	  */
	public void setCycleName (String CycleName)
	{
		set_Value (COLUMNNAME_CycleName, CycleName);
	}

	/** Get Cycle Name.
		@return Name of the Project Cycle
	  */
	public String getCycleName () 
	{
		return (String)get_Value(COLUMNNAME_CycleName);
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

	/** Option1 AD_Reference_ID=2000068 */
	public static final int OPTION1_AD_Reference_ID=2000068;
	/** Tejido Blando = 01 */
	public static final String OPTION1_TejidoBlando = "01";
	/** Hueso = 02 */
	public static final String OPTION1_Hueso = "02";
	/** Set Option1.
		@param Option1 Option1	  */
	public void setOption1 (String Option1)
	{

		set_Value (COLUMNNAME_Option1, Option1);
	}

	/** Get Option1.
		@return Option1	  */
	public String getOption1 () 
	{
		return (String)get_Value(COLUMNNAME_Option1);
	}

	/** Set Option2.
		@param Option2 Option2	  */
	public void setOption2 (boolean Option2)
	{
		set_Value (COLUMNNAME_Option2, Boolean.valueOf(Option2));
	}

	/** Get Option2.
		@return Option2	  */
	public boolean isOption2 () 
	{
		Object oo = get_Value(COLUMNNAME_Option2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Option3 AD_Reference_ID=2000069 */
	public static final int OPTION3_AD_Reference_ID=2000069;
	/** Biopsia Puncion = 01 */
	public static final String OPTION3_BiopsiaPuncion = "01";
	/** Biopsia Quirurgica = 02 */
	public static final String OPTION3_BiopsiaQuirurgica = "02";
	/** Pieza Anatomica = 03 */
	public static final String OPTION3_PiezaAnatomica = "03";
	/** Frotis = 04 */
	public static final String OPTION3_Frotis = "04";
	/** Set Option3.
		@param Option3 Option3	  */
	public void setOption3 (String Option3)
	{

		set_Value (COLUMNNAME_Option3, Option3);
	}

	/** Get Option3.
		@return Option3	  */
	public String getOption3 () 
	{
		return (String)get_Value(COLUMNNAME_Option3);
	}

	/** Set Option4.
		@param Option4 Option4	  */
	public void setOption4 (boolean Option4)
	{
		set_Value (COLUMNNAME_Option4, Boolean.valueOf(Option4));
	}

	/** Get Option4.
		@return Option4	  */
	public boolean isOption4 () 
	{
		Object oo = get_Value(COLUMNNAME_Option4);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Option5.
		@param Option5 Option5	  */
	public void setOption5 (boolean Option5)
	{
		set_Value (COLUMNNAME_Option5, Boolean.valueOf(Option5));
	}

	/** Get Option5.
		@return Option5	  */
	public boolean isOption5 () 
	{
		Object oo = get_Value(COLUMNNAME_Option5);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Option6.
		@param Option6 Option6	  */
	public void setOption6 (boolean Option6)
	{
		set_Value (COLUMNNAME_Option6, Boolean.valueOf(Option6));
	}

	/** Get Option6.
		@return Option6	  */
	public boolean isOption6 () 
	{
		Object oo = get_Value(COLUMNNAME_Option6);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Option7.
		@param Option7 Option7	  */
	public void setOption7 (boolean Option7)
	{
		set_Value (COLUMNNAME_Option7, Boolean.valueOf(Option7));
	}

	/** Get Option7.
		@return Option7	  */
	public boolean isOption7 () 
	{
		Object oo = get_Value(COLUMNNAME_Option7);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Option8.
		@param Option8 Option8	  */
	public void setOption8 (boolean Option8)
	{
		set_Value (COLUMNNAME_Option8, Boolean.valueOf(Option8));
	}

	/** Get Option8.
		@return Option8	  */
	public boolean isOption8 () 
	{
		Object oo = get_Value(COLUMNNAME_Option8);
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
	public void setQty (String Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public String getQty () 
	{
		return (String)get_Value(COLUMNNAME_Qty);
	}

	/** Type AD_Reference_ID=101 */
	public static final int TYPE_AD_Reference_ID=101;
	/** SQL = S */
	public static final String TYPE_SQL = "S";
	/** Java Language = J */
	public static final String TYPE_JavaLanguage = "J";
	/** Java Script = E */
	public static final String TYPE_JavaScript = "E";
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
}