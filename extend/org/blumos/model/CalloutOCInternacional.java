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
import org.compiere.model.MBPartner;
import org.compiere.util.Env;

/**
 *	Requisition Callout
 *	
 *  @author ininoles
 *  @version $Id: CalloutOCInternacional.java,v 2.0 2019/05/10  Exp $
 */
public class CalloutOCInternacional extends CalloutEngine
{
	
	public String CBPartnerData (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value==null)
			return "";
		
		boolean IsSOTrx = "Y".equals(Env.getContext(ctx, WindowNo, "IsSOTrx"));
		
		int ID_Bpartner = (Integer)mTab.getValue("C_BPartner_ID");		
		MBPartner part = new MBPartner(ctx,ID_Bpartner, null);
		if(part.get_ValueAsString("Inco_Term") != null)
			mTab.setValue("Inco_Term", part.get_ValueAsString("Inco_Term"));
		if(part.get_ValueAsString("INCO_LINE") != null)
			mTab.setValue("INCO_LINE", part.get_ValueAsString("INCO_LINE"));
		if(part.get_ValueAsInt("Inco_City_ID") > 0)				
			mTab.setValue("Inco_City_ID", part.get_ValueAsInt("Inco_City_ID"));
		if(part.get_ValueAsInt("Inco_Region_ID") > 0)
			mTab.setValue("Inco_Region_ID", part.get_ValueAsInt("Inco_Region_ID"));
		if(part.get_ValueAsInt("Pais_Origen_ID") > 0)
			mTab.setValue("Pais_Origen_ID", part.get_ValueAsInt("Pais_Origen_ID"));
		if(part.get_ValueAsInt("PAIS_ADQUISICION_ID") > 0)
			mTab.setValue("PAIS_ADQUISICION_ID", part.get_ValueAsInt("PAIS_ADQUISICION_ID"));
		if(part.get_ValueAsString("Insurance") != null)
			mTab.setValue("Insurance", part.get_ValueAsString("Insurance"));				
		if(part.get_ValueAsString("Container") != null)
			mTab.setValue("Container", part.get_ValueAsString("Container"));
		if(part.get_ValueAsInt("COMPRADOR_ID") > 0)
			mTab.setValue("SalesRep_ID", part.get_ValueAsInt("COMPRADOR_ID"));		
		if(part.getPO_PaymentTerm_ID() > 0 && !IsSOTrx)
			mTab.setValue("C_PaymentTerm_ID", part.getPO_PaymentTerm_ID());
		if(part.get_ValueAsString("PaymentFrom") != null)
			mTab.setValue("PaymentFrom", part.get_ValueAsString("PaymentFrom"));
		if(part.get_ValueAsString("Inco_Via") != null)
			mTab.setValue("Inco_Via", part.get_ValueAsString("Inco_Via"));		
		return "";
	}	
}


