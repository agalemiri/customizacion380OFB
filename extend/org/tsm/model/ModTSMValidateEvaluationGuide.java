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
package org.tsm.model;

import org.compiere.model.MClient;

import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.X_RH_EvaluationGuide;
import java.math.BigDecimal;
import org.adempiere.exceptions.AdempiereException;


/**
 *	Validator for company TSM
 *
 *  @author mfrojas
 */
public class ModTSMValidateEvaluationGuide implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModTSMValidateEvaluationGuide ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModTSMValidateEvaluationGuide.class);
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
		//	Documents to be monitored
		engine.addModelChange(X_RH_EvaluationGuide.Table_Name, this);
				

	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	OFB Consulting Ltda. By mfrojas
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		//validacion fecha mismo a�o cabecera
		if((type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE) && po.get_Table_ID()==X_RH_EvaluationGuide.Table_ID)  
		{
			X_RH_EvaluationGuide eg = (X_RH_EvaluationGuide) po;
			BigDecimal amount = Env.ZERO;
			if(eg.getAnswer1() != null)
				amount = amount.add(eg.getAnswer1());
			if(eg.getAnswer2() != null)
				amount = amount.add(eg.getAnswer2());
			if(eg.getAnswer3() != null)
				amount = amount.add(eg.getAnswer3());
			if(eg.getAnswer4() != null)
				amount = amount.add(eg.getAnswer4());
			if(eg.getAnswer5() != null)
				amount = amount.add(eg.getAnswer5());
			if(eg.getAnswer6() != null)
				amount = amount.add(eg.getAnswer6());
			if(eg.getAnswer7() != null)
				amount = amount.add(eg.getAnswer7());
			if(eg.getAnswer8() != null)
				amount = amount.add(eg.getAnswer8());
			if(eg.getAnswer9() != null)
				amount = amount.add(eg.getAnswer9());
			if(eg.getAnswer10() != null)
				amount = amount.add(eg.getAnswer10());

			if(amount.compareTo(Env.ONEHUNDRED)>0)
				throw new AdempiereException ("El resultado no debe ser mayor a 100");
			else
				DB.executeUpdate("Update rh_evaluationguide set SumOfAnswers = "+amount+" where rh_evaluationguide_id = "+eg.get_ID(), po.get_TrxName());
			
				//eg.setSumOfAnswers(amount);
			//eg.save();
		}
	return null;
	}	//	modelChange
	
	public static String rtrim(String s, char c) {
	    int i = s.length()-1;
	    while (i >= 0 && s.charAt(i) == c)
	    {
	        i--;
	    }
	    return s.substring(0,i+1);
	}

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