/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.minju.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.compiere.model.*;

/**
 *	MINJU Callout.
 *	
 *  @author Italo Niñoles
 *  @version $Id: CalloutBoletaGarantia.java,v 1.5 2015/12/09 
 */
public class CalloutBoletaGarantia extends CalloutEngine
{
	/**
	 *
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 *  @return Error message or ""
	 */
	public String dates (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value==null)
			return "";

		Timestamp DueDate = (Timestamp)value;
		Calendar calendario = Calendar.getInstance();
        calendario.setTimeInMillis(DueDate.getTime());
        calendario.add(Calendar.DATE, -30);
        mTab.setValue("SecondDateReminder",new Timestamp(calendario.getTimeInMillis()));        
        calendario.add(Calendar.DATE, -30);
        mTab.setValue("FirstDateReminder",new Timestamp(calendario.getTimeInMillis()));
		return "";
    }	
	
	public String extensionDate (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value == null)
			return "";
		Timestamp newdate = (Timestamp)value;
		mTab.setValue("dateexpiration", newdate);
		
		return "";
	}
}	//	CalloutOrder

