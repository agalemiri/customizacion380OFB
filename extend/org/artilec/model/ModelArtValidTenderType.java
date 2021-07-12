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
import org.compiere.model.MPaymentTerm;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

/**
 *	Validator for artilec
 *
 *  @author Italo Niñoles
 */
public class ModelArtValidTenderType implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelArtValidTenderType ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelArtValidTenderType.class);
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
		engine.addModelChange(MInvoice.Table_Name, this);
		//	Documents to be monitored


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
		if (type == TYPE_AFTER_CHANGE & po.get_Table_ID()==MInvoice.Table_ID
				&& (po.is_ValueChanged("PaymentRule") || po.is_ValueChanged("C_PaymentTerm_ID")))
		{
			MInvoice inv = (MInvoice) po;
			if(inv.isSOTrx())
			{
				if(inv.getDocStatus().compareTo("CO") != 0
						&& inv.getDocStatus().compareTo("CL") != 0)
				{
					//validacion de regla de pago
					//se cambia valor antiguo por valor del socio de negocio
					//String OldValueStr = DB.getSQLValueString(po.get_TrxName(), "SELECT Description FROM AD_Ref_List "
					//		+ " WHERE  AD_Reference_ID=195 AND Value = '"+po.get_ValueOld("PaymentRule")+"'");
					String OldValueStr = DB.getSQLValueString(po.get_TrxName(), "SELECT Description FROM AD_Ref_List "
							+ " WHERE  AD_Reference_ID=195 AND Value = '"+inv.getC_BPartner().getPaymentRule()+"'");
					
					String NewValueStr = DB.getSQLValueString(po.get_TrxName(), "SELECT Description FROM AD_Ref_List "
							+ " WHERE  AD_Reference_ID=195 AND Value = '"+po.get_Value("PaymentRule")+"'");
					
					int oldValue = 0;						
					int newValue = 0;
					
					if(OldValueStr != null && OldValueStr.trim().length() > 0
							&& NewValueStr != null && NewValueStr.trim().length() > 0)
					{
						oldValue = Integer.parseInt(OldValueStr);
						newValue = Integer.parseInt(NewValueStr);
						
						if(newValue > oldValue)
							return "ERROR: Regla de pago no valida";
					}
					
					//validacion termino de pago
					
					int tPagoNew = po.get_ValueAsInt("C_PaymentTerm_ID");
					//int tPagoOld = po.get_ValueOldAsInt("C_PaymentTerm_ID");
					int tPagoOld = inv.getC_BPartner().getC_PaymentTerm_ID();
					
					if(tPagoNew>0 && tPagoOld>0)
					{
						MPaymentTerm ptnew = new MPaymentTerm(po.getCtx(), tPagoNew, po.get_TrxName());
						MPaymentTerm ptold = new MPaymentTerm(po.getCtx(), tPagoOld, po.get_TrxName());
						
						int ptOldValue = Integer.parseInt(ptold.getDescription());
						int ptNewValue = Integer.parseInt(ptnew.getDescription());
						
						if(ptNewValue > ptOldValue)
							return "ERROR: Término de pago no permitido";
					}
				}
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