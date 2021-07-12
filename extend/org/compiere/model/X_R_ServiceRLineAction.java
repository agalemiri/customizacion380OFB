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

/** Generated Model for R_ServiceRLineAction
 *  @author Adempiere (generated) 
 *  @version Release 3.8.0 - $Id$ */
public class X_R_ServiceRLineAction extends PO implements I_R_ServiceRLineAction, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170525L;

    /** Standard Constructor */
    public X_R_ServiceRLineAction (Properties ctx, int R_ServiceRLineAction_ID, String trxName)
    {
      super (ctx, R_ServiceRLineAction_ID, trxName);
      /** if (R_ServiceRLineAction_ID == 0)
        {
			setR_ServiceRLineAction_ID (0);
        } */
    }

    /** Load Constructor */
    public X_R_ServiceRLineAction (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_R_ServiceRLineAction[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_OrderShipCalendar getC_OrderShipCalendar() throws RuntimeException
    {
		return (I_C_OrderShipCalendar)MTable.get(getCtx(), I_C_OrderShipCalendar.Table_Name)
			.getPO(getC_OrderShipCalendar_ID(), get_TrxName());	}

	/** Set C_OrderShipCalendar ID.
		@param C_OrderShipCalendar_ID C_OrderShipCalendar ID	  */
	public void setC_OrderShipCalendar_ID (int C_OrderShipCalendar_ID)
	{
		if (C_OrderShipCalendar_ID < 1) 
			set_Value (COLUMNNAME_C_OrderShipCalendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderShipCalendar_ID, Integer.valueOf(C_OrderShipCalendar_ID));
	}

	/** Get C_OrderShipCalendar ID.
		@return C_OrderShipCalendar ID	  */
	public int getC_OrderShipCalendar_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderShipCalendar_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_R_ServiceRequest getR_ServiceRequest() throws RuntimeException
    {
		return (I_R_ServiceRequest)MTable.get(getCtx(), I_R_ServiceRequest.Table_Name)
			.getPO(getR_ServiceRequest_ID(), get_TrxName());	}

	/** Set ServiceRequest ID.
		@param R_ServiceRequest_ID ServiceRequest ID	  */
	public void setR_ServiceRequest_ID (int R_ServiceRequest_ID)
	{
		if (R_ServiceRequest_ID < 1) 
			set_Value (COLUMNNAME_R_ServiceRequest_ID, null);
		else 
			set_Value (COLUMNNAME_R_ServiceRequest_ID, Integer.valueOf(R_ServiceRequest_ID));
	}

	/** Get ServiceRequest ID.
		@return ServiceRequest ID	  */
	public int getR_ServiceRequest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_ServiceRequest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_R_ServiceRequestLine getR_ServiceRequestLine() throws RuntimeException
    {
		return (I_R_ServiceRequestLine)MTable.get(getCtx(), I_R_ServiceRequestLine.Table_Name)
			.getPO(getR_ServiceRequestLine_ID(), get_TrxName());	}

	/** Set ServiceRequestLine ID.
		@param R_ServiceRequestLine_ID ServiceRequestLine ID	  */
	public void setR_ServiceRequestLine_ID (int R_ServiceRequestLine_ID)
	{
		if (R_ServiceRequestLine_ID < 1) 
			set_Value (COLUMNNAME_R_ServiceRequestLine_ID, null);
		else 
			set_Value (COLUMNNAME_R_ServiceRequestLine_ID, Integer.valueOf(R_ServiceRequestLine_ID));
	}

	/** Get ServiceRequestLine ID.
		@return ServiceRequestLine ID	  */
	public int getR_ServiceRequestLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_ServiceRequestLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set R_ServiceRLineAction ID.
		@param R_ServiceRLineAction_ID R_ServiceRLineAction ID	  */
	public void setR_ServiceRLineAction_ID (int R_ServiceRLineAction_ID)
	{
		if (R_ServiceRLineAction_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_R_ServiceRLineAction_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_ServiceRLineAction_ID, Integer.valueOf(R_ServiceRLineAction_ID));
	}

	/** Get R_ServiceRLineAction ID.
		@return R_ServiceRLineAction ID	  */
	public int getR_ServiceRLineAction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_ServiceRLineAction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set TypeAcction.
		@param TypeAcction TypeAcction	  */
	public void setTypeAcction (int TypeAcction)
	{
		set_Value (COLUMNNAME_TypeAcction, Integer.valueOf(TypeAcction));
	}

	/** Get TypeAcction.
		@return TypeAcction	  */
	public int getTypeAcction () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TypeAcction);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}