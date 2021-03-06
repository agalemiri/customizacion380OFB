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

import java.math.BigDecimal;

import org.compiere.model.MClient;
import org.compiere.model.MMovementLine;
import org.compiere.model.MStorage;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.python.antlr.ast.GeneratorExp.generators_descriptor;

/**
 *	Validator fo artilec
 *
 *  @author Italo Ni?oles
 */
public class ModelArtLineMovement implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelArtLineMovement ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelArtLineMovement.class);
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
		engine.addModelChange(MMovementLine.Table_Name, this);
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
		if ((type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE) && po.get_Table_ID()==MMovementLine.Table_ID)
		{
			MMovementLine mLine = (MMovementLine) po;
			/*int flag = DB.getSQLValue(po.get_TrxName(), "SELECT COUNT(1) FROM M_MovementLine "+
					" WHERE IsActive = 'Y' AND M_Movement_ID ="+mLine.getM_Movement_ID()+
					" AND M_Product_ID = "+mLine.getM_Product_ID()+" AND M_Locator_ID = "+mLine.getM_Locator_ID()+
					" AND  M_MovementLine_ID <> "+mLine.get_ID());
			if(flag > 0)
			{
				return "ERROR: Ya existe una linea para el mismo producto y ubicaci?n";
			}*/
			BigDecimal available = MStorage.getQtyAvailable(0, mLine.getM_Locator_ID(), mLine.getM_Product_ID(), mLine.getM_AttributeSetInstance_ID(), mLine.get_TrxName());
			if(available == null)
				available = Env.ZERO;
			BigDecimal amt = DB.getSQLValueBD(po.get_TrxName(), "SELECT SUM(MovementQty) FROM M_MovementLine WHERE M_Movement_ID ="+mLine.getM_Movement_ID()+
					" AND IsActive = 'Y' AND M_MovementLine_ID <> "+mLine.get_ID()+" AND M_Product_ID = "+mLine.getM_Product_ID()+
					" AND M_Locator_ID = "+mLine.getM_Locator_ID());
			if(amt == null)
				amt = Env.ZERO;
			amt = amt.add(mLine.getMovementQty());
			if(amt.compareTo(available)>0)
				return "ERROR: Stock insuficiente";
			
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