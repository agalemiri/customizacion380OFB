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
package org.mop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

import org.compiere.model.MAsset;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.ofb.utils.DateUtils;

/**
 *	Validator for MOP
 *
 *  @author mfrojas
 */
public class ModMOPAssetOnProcess implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModMOPAssetOnProcess ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModMOPAssetOnProcess.class);
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
		engine.addModelChange(MInvoiceLine.Table_Name, this);
		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		//Se validará que el activo no este en otro proceso actualmente
		
		if((type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE) && po.get_Table_ID()==MInvoiceLine.Table_ID) 
		{	
			MInvoiceLine invl = (MInvoiceLine)po;
			//Primero se obtiene el tipo de documento. 
			int doctype_id = invl.getC_Invoice().getC_DocTypeTarget_ID();
			String sqltypemov = "SELECT type1 from c_doctype where c_doctype_id = ? ";
			String typemov = DB.getSQLValueString(po.get_TrxName(), sqltypemov, doctype_id);
			
			if(typemov.compareTo("ALT")!=0)
			{
				int asset = invl.getA_Asset_ID();
				String sqlsituation = "SELECT coalesce(max(c_invoiceline_id),0) from c_invoiceline WHERE c_invoiceline_Id != "+invl.get_ID()+" AND " +
						" A_Asset_id = "+asset+" AND C_Invoice_ID in ( SELECT " +
						" C_Invoice_ID FROM C_Invoice where Docstatus not in ('CO','CL','VO'))";
				
				int assetsituation = DB.getSQLValue(po.get_TrxName(), sqlsituation);
				
				if(assetsituation > 0)
				{
					int idinvoice = DB.getSQLValue(po.get_TrxName(), "SELECT C_Invoice_ID from C_Invoiceline where C_InvoiceLine_ID = "+assetsituation);
					MInvoice inv = new MInvoice(po.getCtx(),idinvoice,po.get_TrxName());
					return "No se puede guardar activo. Revise el documento "+inv.getDocumentNo()+", "+inv.getC_DocTypeTarget().getName();
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