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
import org.compiere.util.KeyNamePair;

/** Generated Model for MP_Team
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_MP_Team extends PO implements I_MP_Team, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170809L;

    /** Standard Constructor */
    public X_MP_Team (Properties ctx, int MP_Team_ID, String trxName)
    {
      super (ctx, MP_Team_ID, trxName);
      /** if (MP_Team_ID == 0)
        {
			setMP_Team_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MP_Team (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MP_Team[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AD_OrgRef_ID.
		@param AD_OrgRef_ID AD_OrgRef_ID	  */
	public void setAD_OrgRef_ID (int AD_OrgRef_ID)
	{
		if (AD_OrgRef_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgRef_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgRef_ID, Integer.valueOf(AD_OrgRef_ID));
	}

	/** Get AD_OrgRef_ID.
		@return AD_OrgRef_ID	  */
	public int getAD_OrgRef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgRef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set descrip1.
		@param descrip1 descrip1	  */
	public void setdescrip1 (String descrip1)
	{
		set_Value (COLUMNNAME_descrip1, descrip1);
	}

	/** Get descrip1.
		@return descrip1	  */
	public String getdescrip1 () 
	{
		return (String)get_Value(COLUMNNAME_descrip1);
	}

	/** Set descrip2.
		@param descrip2 descrip2	  */
	public void setdescrip2 (String descrip2)
	{
		set_Value (COLUMNNAME_descrip2, descrip2);
	}

	/** Get descrip2.
		@return descrip2	  */
	public String getdescrip2 () 
	{
		return (String)get_Value(COLUMNNAME_descrip2);
	}

	public I_MP_Instrument getMP_Instrument() throws RuntimeException
    {
		return (I_MP_Instrument)MTable.get(getCtx(), I_MP_Instrument.Table_Name)
			.getPO(getMP_Instrument_ID(), get_TrxName());	}

	/** Set MP_Instrument_ID.
		@param MP_Instrument_ID MP_Instrument_ID	  */
	public void setMP_Instrument_ID (int MP_Instrument_ID)
	{
		if (MP_Instrument_ID < 1) 
			set_Value (COLUMNNAME_MP_Instrument_ID, null);
		else 
			set_Value (COLUMNNAME_MP_Instrument_ID, Integer.valueOf(MP_Instrument_ID));
	}

	/** Get MP_Instrument_ID.
		@return MP_Instrument_ID	  */
	public int getMP_Instrument_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MP_Instrument_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MP_Team ID.
		@param MP_Team_ID MP_Team ID	  */
	public void setMP_Team_ID (int MP_Team_ID)
	{
		if (MP_Team_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MP_Team_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MP_Team_ID, Integer.valueOf(MP_Team_ID));
	}

	/** Get MP_Team ID.
		@return MP_Team ID	  */
	public int getMP_Team_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MP_Team_ID);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set team.
		@param team team	  */
	public void setteam (String team)
	{
		set_Value (COLUMNNAME_team, team);
	}

	/** Get team.
		@return team	  */
	public String getteam () 
	{
		return (String)get_Value(COLUMNNAME_team);
	}
}