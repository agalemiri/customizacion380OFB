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
package org.artilec.model;

import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;




/**
 *	artilec .
 *	
 *  @author ininoles
 *  @version $Id: 20170518 
 *  
 */
public class CalloutArtilecOrder extends CalloutEngine{


	public String validDatePromise (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value==null || value=="")
			return "";
		
		Timestamp dateOrder = (Timestamp)mTab.getValue("DateOrdered");
		dateOrder.setHours(0);
		dateOrder.setMinutes(0);
		dateOrder.setSeconds(0);
		
		Timestamp dateP = (Timestamp)mTab.getValue("DatePromised");		
		dateP.setHours(0);
		dateP.setMinutes(0);
		dateP.setSeconds(0);
		
		if(dateOrder != null && dateP != null)
		{
			if(dateP.compareTo(dateOrder) < 0)
			{
				mTab.fireDataStatusEEvent ("Fecha entrega no puede ser anterior a fecha de order", "No Valido ", true);
				mTab.setValue("DatePromised",null);
			}	
		}
		return "";
    }
}	//	CalloutOrder

