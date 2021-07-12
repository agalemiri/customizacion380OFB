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

/** Generated Model for CC_CIE9
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_CIE9 extends PO implements I_CC_CIE9, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170704L;

    /** Standard Constructor */
    public X_CC_CIE9 (Properties ctx, int CC_CIE9_ID, String trxName)
    {
      super (ctx, CC_CIE9_ID, trxName);
      /** if (CC_CIE9_ID == 0)
        {
			setCC_CIE9_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CC_CIE9 (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_CIE9[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CC_CIE9_ID.
		@param CC_CIE9_ID CC_CIE9_ID	  */
	public void setCC_CIE9_ID (int CC_CIE9_ID)
	{
		if (CC_CIE9_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_CIE9_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_CIE9_ID, Integer.valueOf(CC_CIE9_ID));
	}

	/** Get CC_CIE9_ID.
		@return CC_CIE9_ID	  */
	public int getCC_CIE9_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_CIE9_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** procedimiento-9 AD_Reference_ID=2000093 */
	//public static final int PROCEDIMIENTO-9_AD_Reference_ID=2000093;
	/** prueba 1 = 01 */
	//public static final String PROCEDIMIENTO-9_Prueba1 = "01";
	/** Set procedimiento-9.
		@param procedimiento-9 procedimiento-9	  */
	/*public void setprocedimiento-9 (String procedimiento-9)
	{

		set_Value (COLUMNNAME_procedimiento-9, procedimiento-9);
	}*/

	/** Get procedimiento-9.
		@return procedimiento-9	  */
	/*public String getprocedimiento-9 () 
	{
		return (String)get_Value(COLUMNNAME_procedimiento-9);
	}*/

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
}