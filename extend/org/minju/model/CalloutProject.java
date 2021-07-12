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
package org.minju.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartner;
import org.compiere.util.Env;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;

/**
 *	Order Callouts.
 *	
 *  @author mfrojas
 *  @version $Id: CalloutProject.java,v 1.0 2017/08/02  
 */
public class CalloutProject extends CalloutEngine
{		
	public String setInvoicedAmt (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) throws ParseException
	{
		if(value==null)
			return "";
		int invoice_id = (Integer)value;
		if(invoice_id > 0)
		{
			MInvoice inv = new MInvoice(ctx,invoice_id,null);
			mTab.setValue("Amt",inv.getGrandTotal());
		}
		
		return "";
	}
	public String setPaidAmt (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) throws ParseException
	{
		if(value==null)
			return "";
		
		int payment_id = (Integer)value;
		if(payment_id > 0)
		{
			MPayment pay = new MPayment(ctx,payment_id,null);
			mTab.setValue("DueAmt", pay.getPayAmt());

		}
		return "";
	}
	
	public String setApprovalPercent (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) throws ParseException
	{
		if(value == null)
			return "";
		
		String valor = (String)value;
		log.config("valor "+valor);
		String hundred = "100";
		String zero = "0";
		if(valor.compareTo("Y")==0)
			mTab.setValue("Task_percentage", hundred);
		if(valor.compareTo("N")==0)
			mTab.setValue("Task_percentage", zero);
		
		return "";
	}
	
}


