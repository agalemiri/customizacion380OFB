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
package org.junaeb.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

import org.compiere.FA.CreateAssetForecast;
import org.compiere.model.X_A_Asset;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_A_Asset_Use;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.ofb.process.CreateAssetForecastOFB;
import org.compiere.model.MAssetAcct;
import org.compiere.model.X_A_Depreciation_Workfile;

/**
 *	Validator for JUNAEB
 *
 *  @author mfrojas
 */
public class ModJUNAEBDepWorkfile implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModJUNAEBDepWorkfile ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModJUNAEBDepWorkfile.class);
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
		engine.addModelChange(X_A_Asset.Table_Name, this);
		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		//Se validará que el activo no este en otro proceso actualmente
		
		if((type == TYPE_AFTER_NEW) && po.get_Table_ID()==X_A_Asset.Table_ID) 
		{	
			
			X_A_Asset asset = (X_A_Asset)po;
			int aw_id = DB.getSQLValue(asset.get_TrxName(), "SELECT max(a_depreciation_workfile_id) from a_depreciation_workfile where a_Asset_id = "+asset.get_ID());
			X_A_Depreciation_Workfile  aw = new X_A_Depreciation_Workfile(asset.getCtx(),aw_id,asset.get_TrxName());

			asset.set_CustomColumn("AD_OrgRef_ID", asset.getAD_Org_ID());
			asset.save();
			aw.setA_Asset_Cost((BigDecimal)asset.get_Value("GrandTotal"));
			aw.setA_Accumulated_Depr(Env.ZERO);
			
			
			aw.set_CustomColumn("A_Salvage_Value", Env.ONE);
			aw.setPostingType("A");
			aw.setIsDepreciated(true);
			aw.setA_QTY_Current(Env.ONE);
			
			aw.save();
			
			
			//Validar si existe ya cuentas contables de activo (registro en a_asset_acct).
			
			String sql = "SELECT coalesce(a_asset_Acct_id,0) from A_Asset_acct where a_Asset_id = "+asset.get_ID();
			int count = DB.getSQLValue(asset.get_TrxName(), sql);
			if(count == 0)
			{
				//Si no hay registros asociados en a_Asset_acct, entonces se debe crear
			}
			else
			{
				//Se debe obtener la vida util
				int life = DB.getSQLValue(asset.get_TrxName(), "SELECT max(a_Asset_life_years) from a_asset_group " +
						" where a_Asset_group_id in (select a_Asset_group_ref2_id from a_asset where a_Asset_id = "+asset.get_ID()+") ");
				
				
				MAssetAcct acct = new MAssetAcct(asset.getCtx(),count,asset.get_TrxName());
				acct.setA_Period_End(life);
				acct.save();
				
				CreateAssetForecastOFB cast = new CreateAssetForecastOFB();
				//cast.replanningForecast(asset, aw.getA_Asset_Cost().subtract(aw.getA_Accumulated_Depr()), acct, 0,asset.getAssetServiceDate());
				//CreateAssetForecast.createForecast(asset,null, acct, po.get_TrxName());

				log.config("marit");		
			}
				

			

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