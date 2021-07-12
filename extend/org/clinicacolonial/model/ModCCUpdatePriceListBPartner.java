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
package org.clinicacolonial.model;

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.ofb.model.OFBForward;
import org.compiere.model.MBPartner;

/**
 *	Validator for CC
 *
 *  @author mfrojas
 */
public class ModCCUpdatePriceListBPartner implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModCCUpdatePriceListBPartner ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModCCUpdatePriceListBPartner.class);
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
			log.info("Initializing Model Price Validator: "+this.toString());
		}

		//	Tables to be monitored
		engine.addModelChange(MBPartner.Table_Name, this);
		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
	/*	if((type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE) && po.get_Table_ID()==MBPartner.Table_ID) 
		{	
			MBPartner bPart = (MBPartner)po;
			if(bPart.getM_PriceList_ID() <= 0)
			{
				if(bPart.get_ValueAsInt("CC_Agreement_ID") > 0)
				{
					String sqlgetpricelist = "SELECT coalesce(max(m_pricelist_id),0) from cc_Agreement where " +
							" cc_Agreement_id = "+bPart.get_ValueAsInt("CC_Agreement_ID");
					int pricelist = DB.getSQLValue(po.get_TrxName(), sqlgetpricelist);
					if(pricelist > 0)
						bPart.setM_PriceList_ID(pricelist);
				}
			}
*/
		if((type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE) && po.get_Table_ID()==MBPartner.Table_ID) 
		{	
			MBPartner bPart = (MBPartner)po;
			
			//mfrojas 20200423 Si se modifica el convenio, siempre se modificara la lista de precios.
			
			//if(bPart.getM_PriceList_ID() <= 0)
			//{
				if(bPart.get_ValueAsInt("CC_Agreement_ID") > 0)
				{
					String sqlgetpricelist = "SELECT coalesce(max(m_pricelist_id),0) from cc_Agreement where " +
							" cc_Agreement_id = "+bPart.get_ValueAsInt("CC_Agreement_ID");
					int pricelist = DB.getSQLValue(po.get_TrxName(), sqlgetpricelist);
					log.config("pricelist "+pricelist);
					if(pricelist > 0)
					{
						//bPart.setM_PriceList_ID(pricelist);
						String update = "UPDATE c_bpartner SET m_pricelist_id = "+pricelist+" WHERE " +
								" c_bpartner_id = "+bPart.get_ID();
						log.config("update "+update);
						DB.executeUpdate(update,po.get_TrxName());
					}
						
				}
			//}
		
		}
		return null;
	}	//	modelChange

	public String docValidate (PO po, int timing)
	{
		log.info(po.get_TableName() + " Timing: "+timing);
		
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
		StringBuffer sb = new StringBuffer ("ModelPrice");
		return sb.toString ();
	}	//	toString


	

}	