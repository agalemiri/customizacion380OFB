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

/** Generated Model for MED_Template
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_MED_Template extends PO implements I_MED_Template, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190711L;

    /** Standard Constructor */
    public X_MED_Template (Properties ctx, int MED_Template_ID, String trxName)
    {
      super (ctx, MED_Template_ID, trxName);
      /** if (MED_Template_ID == 0)
        {
			setMED_Template_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MED_Template (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MED_Template[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Valid.
		@param IsValid 
		Element is valid
	  */
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Valid.
		@return Element is valid
	  */
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set MED_Template ID.
		@param MED_Template_ID MED_Template ID	  */
	public void setMED_Template_ID (int MED_Template_ID)
	{
		if (MED_Template_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MED_Template_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MED_Template_ID, Integer.valueOf(MED_Template_ID));
	}

	/** Get MED_Template ID.
		@return MED_Template ID	  */
	public int getMED_Template_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MED_Template_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Validate.
		@param Validate Validate	  */
	public void setValidate (String Validate)
	{
		set_Value (COLUMNNAME_Validate, Validate);
	}

	/** Get Validate.
		@return Validate	  */
	public String getValidate () 
	{
		return (String)get_Value(COLUMNNAME_Validate);
	}
}