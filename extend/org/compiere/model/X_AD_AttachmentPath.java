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

/** Generated Model for AD_AttachmentPath
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_AD_AttachmentPath extends PO implements I_AD_AttachmentPath, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170803L;

    /** Standard Constructor */
    public X_AD_AttachmentPath (Properties ctx, int AD_AttachmentPath_ID, String trxName)
    {
      super (ctx, AD_AttachmentPath_ID, trxName);
      /** if (AD_AttachmentPath_ID == 0)
        {
			setAD_AttachmentPath_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_AttachmentPath (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
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
      StringBuffer sb = new StringBuffer ("X_AD_AttachmentPath[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AD_AttachmentPath ID.
		@param AD_AttachmentPath_ID AD_AttachmentPath ID	  */
	public void setAD_AttachmentPath_ID (int AD_AttachmentPath_ID)
	{
		if (AD_AttachmentPath_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_AttachmentPath_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_AttachmentPath_ID, Integer.valueOf(AD_AttachmentPath_ID));
	}

	/** Get AD_AttachmentPath ID.
		@return AD_AttachmentPath ID	  */
	public int getAD_AttachmentPath_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_AttachmentPath_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
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

	/** Set File Path or Name.
		@param FilePathOrName 
		Path of directory or name of the local file or URL
	  */
	public void setFilePathOrName (String FilePathOrName)
	{
		set_Value (COLUMNNAME_FilePathOrName, FilePathOrName);
	}

	/** Get File Path or Name.
		@return Path of directory or name of the local file or URL
	  */
	public String getFilePathOrName () 
	{
		return (String)get_Value(COLUMNNAME_FilePathOrName);
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Value.
		@param ValueNumber 
		Numeric Value
	  */
	public void setValueNumber (int ValueNumber)
	{
		set_Value (COLUMNNAME_ValueNumber, Integer.valueOf(ValueNumber));
	}

	/** Get Value.
		@return Numeric Value
	  */
	public int getValueNumber () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ValueNumber);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}