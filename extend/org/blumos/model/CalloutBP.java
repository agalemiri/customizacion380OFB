/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is Compiere ERP & CRM Smart Business Solution. The Initial
 * Developer of the Original Code is Jorg Janke. Portions created by Jorg Janke
 * are Copyright (C) 1999-2005 Jorg Janke.
 * All parts are Copyright (C) 1999-2005 ComPiere, Inc.  All Rights Reserved.
 * Contributor(s): ______________________________________.
 *****************************************************************************/
package org.blumos.model;
import java.util.*;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.*;

/**
 *	Order Callouts.
 *	
 *  @author Italo Ninoles OFB 
 *  @version $Id: CalloutOrder.java,v 1.34 2006/11/25 21:57:24  Exp $
 */
public class CalloutBP extends CalloutEngine
{
	/**	Debug Steps			*/
	//private boolean steps = false;	
	public String rutSonutra (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(Env.getAD_Client_ID(ctx) == 1000022)
	    {
			if(value==null)
			return "";	
			//String myRut=(String)mTab.getValue("Value");
			String myRut=(String)value;
			/*if(myRut==null)
			{
				mTab.fireDataStatusEEvent ("Validacion de Rut ", "No Valido ", true);
				return "";
			}*/
			if(myRut.equals(""))
			{
				mTab.fireDataStatusEEvent ("Validacion de Rut ", "No Valido ", true);
				return "";
			}
			if(myRut.length()==0)
			{
				mTab.fireDataStatusEEvent ("Validacion de Rut ", "No Valido ", true);
				return "";
			}
			if(myRut.equals("-"))
			{
				mTab.fireDataStatusEEvent ("Validacion de Rut ", "No Valido ", true);
				return "";
			}
			
			if(myRut.equals("%"))
			{
				mTab.fireDataStatusEEvent ("Validacion de Rut ", "No Valido ", true);
				return "";
			}
			
			if(myRut.substring(0, 1).equals("0"))
			{
				mTab.fireDataStatusEEvent ("Validacion de Rut ", "No puede Partir con 0 ", true);
				return "";
			}
			int T;
			try
			{
				T=Integer.parseInt(myRut);
			}
			catch (Exception e)
			{
				mTab.fireDataStatusEEvent ("Validacion de Rut ", "No Valido. ", true);
				return "";
			}
			String flag = DB.getSQLValueString(null,"select damevalidaruc('"+myRut+"')");
			if(flag != null)
				mTab.fireDataStatusEEvent ("Validacion de Rut ", flag, true);
			return "";
	    }
		else
			return "";	
	}	//	charge

}	//	CalloutOrder

