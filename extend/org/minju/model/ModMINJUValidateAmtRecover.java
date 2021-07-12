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

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_RH_MedicalLicenses;
import org.compiere.model.X_RH_MedicalLicensesLine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;

/**
 *	Validator for MINJU
 *
 *  @author mfrojas
 */
public class ModMINJUValidateAmtRecover implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModMINJUValidateAmtRecover ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModMINJUValidateAmtRecover.class);
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
		engine.addModelChange(X_RH_MedicalLicenses.Table_Name, this);		
		engine.addModelChange(X_RH_MedicalLicensesLine.Table_Name, this);
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		if((type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE)&& po.get_Table_ID()==X_RH_MedicalLicensesLine.Table_ID) 
		{	
			X_RH_MedicalLicensesLine line = (X_RH_MedicalLicensesLine)po;
			int id_license = line.getRH_MedicalLicenses_ID();
			X_RH_MedicalLicenses rh = new X_RH_MedicalLicenses(po.getCtx(),id_license,po.get_TrxName());
			if(rh.getDocStatus().compareTo("RE")==0)
			{
				String sqlDE = "SELECT count(1) FROM rh_medicallicensesline WHERE RH_MedicalLicenses_ID = "+id_license+" " +
						" AND PaymentType like 'DE'";
				int countDE = DB.getSQLValue(po.get_TrxName(), sqlDE);
				String sqlIN = "SELECT count(1) FROM rh_medicallicensesline WHERE RH_MedicalLicenses_ID = "+id_license+" " +
						" AND PaymentType like 'IN'";
				int countIN = DB.getSQLValue(po.get_TrxName(), sqlIN);
				
				if(countIN > 0 && countDE > 0)
				{
					String sqldifde = "SELECT coalesce(sum(amount2),0) FROM rh_medicallicensesline WHERE " +
							" PaymentType like 'DE' AND RH_MedicalLicenses_ID = ?" ;
					String sqldifin = "SELECT coalesce(sum(amount2),0) FROM rh_medicallicensesline WHERE " +
							" PaymentType like 'IN' and RH_MedicalLicenses_ID = ?";
					log.config("sql dif "+sqldifde);
					log.config("sql dif "+sqldifin);
					BigDecimal de = DB.getSQLValueBD(po.get_TrxName(), sqldifde, id_license);
					BigDecimal in = DB.getSQLValueBD(po.get_TrxName(), sqldifin, id_license);
					BigDecimal diff = de.subtract(in);
					if(diff.compareTo(Env.ZERO) == 0)
					{
						rh.setDocStatus("CO");
						rh.setProcessed(true);
						rh.save();
					}
				}
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