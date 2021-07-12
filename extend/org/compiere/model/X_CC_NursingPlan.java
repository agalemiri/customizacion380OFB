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

/** Generated Model for CC_NursingPlan
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_CC_NursingPlan extends PO implements I_CC_NursingPlan, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170704L;

    /** Standard Constructor */
    public X_CC_NursingPlan (Properties ctx, int CC_NursingPlan_ID, String trxName)
    {
      super (ctx, CC_NursingPlan_ID, trxName);
      /** if (CC_NursingPlan_ID == 0)
        {
			setCC_NursingPlan_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CC_NursingPlan (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CC_NursingPlan[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Boton1.
		@param Boton1 Boton1	  */
	public void setBoton1 (String Boton1)
	{
		set_Value (COLUMNNAME_Boton1, Boton1);
	}

	/** Get Boton1.
		@return Boton1	  */
	public String getBoton1 () 
	{
		return (String)get_Value(COLUMNNAME_Boton1);
	}

	/** Set BotonOFB.
		@param BotonOFB BotonOFB	  */
	public void setBotonOFB (String BotonOFB)
	{
		set_Value (COLUMNNAME_BotonOFB, BotonOFB);
	}

	/** Get BotonOFB.
		@return BotonOFB	  */
	public String getBotonOFB () 
	{
		return (String)get_Value(COLUMNNAME_BotonOFB);
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

	/** Set CC_NursingPlan ID.
		@param CC_NursingPlan_ID CC_NursingPlan ID	  */
	public void setCC_NursingPlan_ID (int CC_NursingPlan_ID)
	{
		if (CC_NursingPlan_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CC_NursingPlan_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CC_NursingPlan_ID, Integer.valueOf(CC_NursingPlan_ID));
	}

	/** Get CC_NursingPlan ID.
		@return CC_NursingPlan ID	  */
	public int getCC_NursingPlan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CC_NursingPlan_ID);
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
}