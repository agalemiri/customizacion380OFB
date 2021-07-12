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
import org.compiere.util.KeyNamePair;

/** Generated Model for MP_IndicatorDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_MP_IndicatorDetail extends PO implements I_MP_IndicatorDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170809L;

    /** Standard Constructor */
    public X_MP_IndicatorDetail (Properties ctx, int MP_IndicatorDetail_ID, String trxName)
    {
      super (ctx, MP_IndicatorDetail_ID, trxName);
      /** if (MP_IndicatorDetail_ID == 0)
        {
			setMP_IndicatorDetail_ID (0);
			setMP_Indicator_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MP_IndicatorDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MP_IndicatorDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set answers.
		@param answers answers	  */
	public void setanswers (String answers)
	{
		set_Value (COLUMNNAME_answers, answers);
	}

	/** Get answers.
		@return answers	  */
	public String getanswers () 
	{
		return (String)get_Value(COLUMNNAME_answers);
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
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

	/** Set MP_IndicatorDetail ID.
		@param MP_IndicatorDetail_ID MP_IndicatorDetail ID	  */
	public void setMP_IndicatorDetail_ID (int MP_IndicatorDetail_ID)
	{
		if (MP_IndicatorDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MP_IndicatorDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MP_IndicatorDetail_ID, Integer.valueOf(MP_IndicatorDetail_ID));
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

	public I_MP_Indicator getMP_Indicator() throws RuntimeException
    {
		return (I_MP_Indicator)MTable.get(getCtx(), I_MP_Indicator.Table_Name)
			.getPO(getMP_Indicator_ID(), get_TrxName());	}

	/** Set MP_Indicator ID.
		@param MP_Indicator_ID MP_Indicator ID	  */
	public void setMP_Indicator_ID (int MP_Indicator_ID)
	{
		if (MP_Indicator_ID < 1) 
			set_Value (COLUMNNAME_MP_Indicator_ID, null);
		else 
			set_Value (COLUMNNAME_MP_Indicator_ID, Integer.valueOf(MP_Indicator_ID));
	}

	/** Get MP_Indicator ID.
		@return MP_Indicator ID	  */
	public int getMP_Indicator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MP_Indicator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_MP_Team getMP_Team() throws RuntimeException
    {
		return (I_MP_Team)MTable.get(getCtx(), I_MP_Team.Table_Name)
			.getPO(getMP_Team_ID(), get_TrxName());	}

	/** Set MP_Team ID.
		@param MP_Team_ID MP_Team ID	  */
	public void setMP_Team_ID (int MP_Team_ID)
	{
		if (MP_Team_ID < 1) 
			set_Value (COLUMNNAME_MP_Team_ID, null);
		else 
			set_Value (COLUMNNAME_MP_Team_ID, Integer.valueOf(MP_Team_ID));
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

	/** Set Note.
		@param Note 
		Optional additional user defined information
	  */
	public void setNote (String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Note.
		@return Optional additional user defined information
	  */
	public String getNote () 
	{
		return (String)get_Value(COLUMNNAME_Note);
	}

	/** Set ponderator.
		@param ponderator ponderator	  */
	public void setponderator (BigDecimal ponderator)
	{
		set_Value (COLUMNNAME_ponderator, ponderator);
	}

	/** Get ponderator.
		@return ponderator	  */
	public BigDecimal getponderator () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ponderator);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set questions.
		@param questions questions	  */
	public void setquestions (String questions)
	{
		set_Value (COLUMNNAME_questions, questions);
	}

	/** Get questions.
		@return questions	  */
	public String getquestions () 
	{
		return (String)get_Value(COLUMNNAME_questions);
	}

	/** Set Result.
		@param Result 
		Result of the action taken
	  */
	public void setResult (String Result)
	{
		set_Value (COLUMNNAME_Result, Result);
	}

	/** Get Result.
		@return Result of the action taken
	  */
	public String getResult () 
	{
		return (String)get_Value(COLUMNNAME_Result);
	}

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

	/** Set weighting.
		@param weighting weighting	  */
	public void setweighting (BigDecimal weighting)
	{
		set_Value (COLUMNNAME_weighting, weighting);
	}

	/** Get weighting.
		@return weighting	  */
	public BigDecimal getweighting () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_weighting);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}