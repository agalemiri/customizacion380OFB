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
package org.ofb.model;

import java.math.BigDecimal;

//import org.compiere.FA.CreateAssetForecast;
import org.compiere.model.MClient;
//import org.compiere.model.MDepreciationWorkfile;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.MCost;
import org.compiere.model.MCostDetail;

/**
 *	Validator for cumulatedqty on costs
 *
 *  @author mfrojas 
 */
public class ModelOFBCostQty implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelOFBCostQty ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelOFBCostQty.class);
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	

	/**
	 *	Initialize Validation
	 *	@param engine validation engine
	 *	@param client client
	 */
	public void initialize (ModelValidationEngine engine, MClient client)
	{
		//client = null for global validator
		if (client != null) {
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else  {
			log.info("Initializing global validator: "+this.toString());
		}

		//	Tables to be monitored
		engine.addModelChange(MCost.Table_Name, this);
		engine.addModelChange(MCostDetail.Table_Name, this);
		//	Documents to be monitored
		//engine.addDocValidate(MInOut.Table_Name, this);
		//engine.addDocValidate(MInvoice.Table_Name, this);

	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	
	
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		if((type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE) && po.get_Table_ID()==MCost.Table_ID)
		{
			MCost cost = (MCost) po;
			
			
			String sqlqty = "SELECT SUM(qtyonhand) from m_storage where m_product_id = "+cost.getM_Product_ID()+"" +
					"AND AD_Client_ID = ?";
			BigDecimal qty = DB.getSQLValueBD(po.get_TrxName(), sqlqty, cost.getAD_Client_ID());
			if(qty != null)
			{
				//cost.setCumulatedQty(qty);
				//cost.setCumulatedAmt(qty.multiply(cost.getCurrentCostPrice()));
				//cost.save();
				
				//mfrojas se agrega un select para traer currentqty del max costdetail procesado.
				String sqlmax = "SELECT coalesce(currentqty,0) from m_costdetail where m_product_id =  "+cost.getM_Product_ID()+" and m_costdetail_id in " +
						"(SELECT max(m_costdetail_id) from m_Costdetail where processed='Y' and m_product_id = "+cost.getM_Product_ID()+" and ad_client_id = ?)";
				BigDecimal qty2 = DB.getSQLValueBD(po.get_TrxName(), sqlmax, cost.getAD_Client_ID());
				//DB.executeUpdate("UPDATE M_Cost set currentqty = "+qty+", cumulatedqty = "+qty+", cumulatedamt = "+qty.multiply(cost.getCurrentCostPrice())+
				//		" WHERE AD_Client_ID = "+cost.getAD_Client_ID()+" AND m_product_id = "+cost.getM_Product_ID(),po.get_TrxName());
				if(qty2 == null)
					qty2 = Env.ZERO;
					DB.executeUpdate("UPDATE M_Cost set currentqty = "+qty2+", cumulatedqty = "+qty2+", cumulatedamt = "+qty2.multiply(cost.getCurrentCostPrice())+
						" WHERE AD_Client_ID = "+cost.getAD_Client_ID()+" AND m_product_id = "+cost.getM_Product_ID(),po.get_TrxName());

				
			}
		}
		
	

		if((type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE) && po.get_Table_ID()==MCostDetail.Table_ID)
		{
			MCostDetail cost = (MCostDetail) po;
			
			
			String sqlqty = "SELECT SUM(qtyonhand) from m_storage where m_product_id = "+cost.getM_Product_ID()+"" +
					"AND AD_Client_ID = ?";
			BigDecimal qty = DB.getSQLValueBD(po.get_TrxName(), sqlqty, cost.getAD_Client_ID());
			if(qty != null)
			{
				//cost.setCumulatedQty(qty);
				//cost.setCumulatedAmt(qty.multiply(cost.getCurrentCostPrice()));
				//cost.save();
				if(cost.getM_MovementLine_ID() == 0 || cost.getM_InventoryLine_ID() == 0)
				DB.executeUpdate("UPDATE M_Costdetail set currentqty = "+qty+", cumulatedqty = "+qty+", cumulatedamt = "+qty.multiply(cost.getCurrentCostPrice())+
						" WHERE M_CostDetail_ID = "+cost.get_ID(),po.get_TrxName());
				else
					DB.executeUpdate("UPDATE M_Costdetail set currentqty = "+qty+", cumulatedqty = "+qty+", currentcostprice = "+cost.getAmt().divide(cost.getQty()).abs()+",  cumulatedamt = "+qty.multiply((cost.getAmt().divide(cost.getQty()).abs()))+
							" WHERE M_CostDetail_ID = "+cost.get_ID(),po.get_TrxName());


			}

		}
					
		return null;
	}	//	modelChange

	/**
	 *	Validate Document.
	 *	Called as first step of DocAction.prepareIt
     *	when you called addDocValidate for the table.
     *	Note that totals, etc. may not be correct.
	 *	@param po persistent object
	 *	@param timing see TIMING_ constants
     *	@return error message or null
	 */
	public String docValidate (PO po, int timing)
	{
				return null;
	}	//	docValidate

	/**
	 *	User Login.
	 *	Called when preferences are set
	 *	@param AD_Org_ID org
	 *	@param AD_Role_ID role
	 *	@param AD_User_ID user
	 *	@return error message or null
	 */
	public String login (int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		log.info("AD_User_ID=" + AD_User_ID);

		return null;
	}	//	login


	/**
	 *	Get Client to be monitored
	 *	@return AD_Client_ID client
	 */
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}	//	getAD_Client_ID


	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("QSS_Validator");
		return sb.toString ();
	}	//	toString


}	