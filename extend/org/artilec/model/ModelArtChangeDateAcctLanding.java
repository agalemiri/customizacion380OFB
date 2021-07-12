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


import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

/**
 *	Validator for artilec
 *
 *  @author mfrojas
 */
public class ModelArtChangeDateAcctLanding implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelArtChangeDateAcctLanding ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelArtChangeDateAcctLanding.class);
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
		//engine.addModelChange(MInOutLine.Table_Name, this);
		//	Documents to be monitored
		engine.addDocValidate(MInvoice.Table_Name, this);

	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public static final String DOCSTATUS_Drafted = "DR";
	public static final String DOCSTATUS_Completed = "CO";
	public static final String DOCSTATUS_InProgress = "IP";
	public static final String DOCSTATUS_Voided = "VO";
	
	
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		if (type == TYPE_AFTER_CHANGE && po.get_Table_ID()==MInvoice.Table_ID  && po.is_ValueChanged("posted"))
		{
			MInvoice inv = (MInvoice) po;
			String sqlexistslanding = "SELECT count(1) from c_landedcost where c_invoiceline_id in (select " +
					" c_invoiceline_id from c_invoiceline where c_invoice_id = "+inv.get_ID()+")";
			int counter = DB.getSQLValue(po.get_TrxName(), sqlexistslanding);
			//Si el contador es mayor que cero, entonces la factura es de landing. 
			if(counter > 0 && inv.get_Value("posted").toString().compareTo("Y") == 0)
			{
				//Si el contador es mayor a cero, y el nuevo valor de posted es Y, entonces la factura se acaba de contabilizar
				//En cuyo caso, se debe buscar en fact_acct cuales son los registros asociados, y poner la fecha de transaccion
				//del documento.
				
				//update 20200401 Primero se debe obtener el periodo				
				//String sqlgetperiod = "SELECT coalesce(max(c_period_id),0) from c_period where "+inv.getDateInvoiced()+" between startdate and enddate";
				//log.config("sqlgetperiod "+sqlgetperiod);
				
				//int periodo = DB.getSQLValue(po.get_TrxName(), sqlgetperiod);
				int periodo = MPeriod.getC_Period_ID(po.getCtx(), inv.getDateInvoiced(), inv.getAD_Org_ID());
				String sqlupdatefactacct = "";				
				if(periodo > 0)
					sqlupdatefactacct = "UPDATE fact_Acct SET dateacct = '"+inv.getDateInvoiced()+"', " +
							" c_period_id = "+periodo+" WHERE record_id = "+inv.get_ID()+" and " +
							" ad_table_id = 318";
				else
					sqlupdatefactacct = "UPDATE fact_Acct SET dateacct = '"+inv.getDateInvoiced()+"' WHERE record_id = "+inv.get_ID()+" and " +
							" ad_table_id = 318";
				DB.executeUpdate(sqlupdatefactacct, po.get_TrxName());
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
		log.info(po.get_TableName() + " Timing: "+timing);
		//Validacion para cambiar la fecha de completar por la fecha del momento. 
		if (timing == TIMING_AFTER_COMPLETE && po.get_Table_ID()==MInvoice.Table_ID)
		{
			MInvoice inv = (MInvoice) po;
			String sqlexistslanding = "SELECT count(1) from c_landedcost where c_invoiceline_id in (select " +
					" c_invoiceline_id from c_invoiceline where c_invoice_id = "+inv.get_ID()+")";
			int counter = DB.getSQLValue(po.get_TrxName(), sqlexistslanding);
			//Si el contador es mayor que cero, entonces la factura es de landing. 
			if(counter > 0)
				inv.setDateAcct(po.getUpdated());
		}
	
				
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