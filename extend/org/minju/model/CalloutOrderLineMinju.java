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
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.MBPartner;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPricing;
import org.compiere.model.MStorage;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 *	Order Callouts.
 *	
 *  @author Italo Niñoles OFB ininoles.
 *  @version $Id: CalloutRHAdministrativeRequests.java,v 2.0 2012/12/03  Exp $
 */
public class CalloutOrderLineMinju extends CalloutEngine
{		
		
	//@mfrojas Se agrega callout de producto, igual al callout original de product, 
	//pero sin setear los precios. 
	//
	public String product (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer M_Product_ID = (Integer)value;
		Integer M_AttributeSetInstance_ID = 0;
		//
		if (M_Product_ID == null || M_Product_ID.intValue() == 0)
		{
			//  If the product information is deleted, zero the other items as well
			mTab.setValue("M_AttributeSetInstance_ID", null);
			mTab.setValue("C_Currency_ID", null);
			mTab.setValue("Discount", new BigDecimal(0));
			mTab.setValue("C_UOM_ID", null);
			return "";
		}

		MProduct product = MProduct.get (ctx, M_Product_ID.intValue());
		I_M_AttributeSetInstance asi = product.getM_AttributeSetInstance();
		//
		mTab.setValue("C_Charge_ID", null);
		//	Set Attribute from context or, if null, from the Product
		//	Get Model and check the Attribute Set Instance from the context
		MProduct m_product = MProduct.get(Env.getCtx(), M_Product_ID);
		mTab.setValue("M_AttributeSetInstance_ID", m_product.getEnvAttributeSetInstance(ctx, WindowNo));
		if (Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_Product_ID") == M_Product_ID.intValue()
			&& Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID") != 0)
			mTab.setValue("M_AttributeSetInstance_ID", Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID"));
		else {
			mTab.setValue("M_AttributeSetInstance_ID", asi.getM_AttributeSetInstance_ID());
		}
		/*****	Price Calculation see also qty	****/
		int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
		BigDecimal Qty = (BigDecimal)mTab.getValue("QtyOrdered");
		boolean IsSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
		MProductPricing pp = new MProductPricing (M_Product_ID.intValue(), C_BPartner_ID, Qty, IsSOTrx);
		//
		int M_PriceList_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");
		pp.setM_PriceList_ID(M_PriceList_ID);
		Timestamp orderDate = (Timestamp)mTab.getValue("DateOrdered");
		/** PLV is only accurate if PL selected in header */
		int M_PriceList_Version_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
		if ( M_PriceList_Version_ID == 0 && M_PriceList_ID > 0)
		{
			String sql = "SELECT plv.M_PriceList_Version_ID "
				+ "FROM M_PriceList_Version plv "
				+ "WHERE plv.M_PriceList_ID=? "						//	1
				+ " AND plv.ValidFrom <= ? "
				+ "ORDER BY plv.ValidFrom DESC";
			//	Use newest price list - may not be future
			
			M_PriceList_Version_ID = DB.getSQLValueEx(null, sql, M_PriceList_ID, orderDate);
			if ( M_PriceList_Version_ID > 0 )
				Env.setContext(ctx, WindowNo, "M_PriceList_Version_ID", M_PriceList_Version_ID );
		}
		pp.setM_PriceList_Version_ID(M_PriceList_Version_ID); 
		pp.setPriceDate(orderDate);
		//		
		mTab.setValue("PriceList", pp.getPriceList());
		mTab.setValue("C_Currency_ID", new Integer(pp.getC_Currency_ID()));
		mTab.setValue("Discount", pp.getDiscount());
		mTab.setValue("C_UOM_ID", new Integer(pp.getC_UOM_ID()));
		mTab.setValue("QtyOrdered", mTab.getValue("QtyEntered"));
		Env.setContext(ctx, WindowNo, "EnforcePriceLimit", pp.isEnforcePriceLimit() ? "Y" : "N");
		Env.setContext(ctx, WindowNo, "DiscountSchema", pp.isDiscountSchema() ? "Y" : "N");
		
		//	Check/Update Warehouse Setting
		//	int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo, "M_Warehouse_ID");
		//	Integer wh = (Integer)mTab.getValue("M_Warehouse_ID");
		//	if (wh.intValue() != M_Warehouse_ID)
		//	{
		//		mTab.setValue("M_Warehouse_ID", new Integer(M_Warehouse_ID));
		//		ADialog.warn(,WindowNo, "WarehouseChanged");
		//	}

		
		if (Env.isSOTrx(ctx, WindowNo))
		{
			if (product.isStocked())
			{
				BigDecimal QtyOrdered = (BigDecimal)mTab.getValue("QtyOrdered");
				int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo, "M_Warehouse_ID");
				M_AttributeSetInstance_ID = Env.getContextAsInt(ctx, WindowNo, "M_AttributeSetInstance_ID");
				BigDecimal available = MStorage.getQtyAvailable
					(M_Warehouse_ID, M_Product_ID.intValue(), M_AttributeSetInstance_ID, null);
				if (available == null)
					available = Env.ZERO;
				if (available.signum() == 0)
					mTab.fireDataStatusEEvent ("NoQtyAvailable", "0", false);
				else if (available.compareTo(QtyOrdered) < 0)
					mTab.fireDataStatusEEvent ("InsufficientQtyAvailable", available.toString(), false);
				else
				{
					Integer C_OrderLine_ID = (Integer)mTab.getValue("C_OrderLine_ID");
					if (C_OrderLine_ID == null)
						C_OrderLine_ID = new Integer(0);
					BigDecimal notReserved = MOrderLine.getNotReserved(ctx, 
						M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID,
						C_OrderLine_ID.intValue());
					if (notReserved == null)
						notReserved = Env.ZERO;
					BigDecimal total = available.subtract(notReserved);
					if (total.compareTo(QtyOrdered) < 0)
					{
						String info = Msg.parseTranslation(ctx, "@QtyAvailable@=" + available 
							+ " - @QtyNotReserved@=" + notReserved + " = " + total);
						mTab.fireDataStatusEEvent ("InsufficientQtyAvailable", 
							info, false);
					}
				}
			}
		}
		//
		return "";
	}	//	product

}


