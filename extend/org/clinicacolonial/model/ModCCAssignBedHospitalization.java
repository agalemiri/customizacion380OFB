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
//import org.compiere.model.X_A_Asset_Use;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
//import org.compiere.model.MAsset;
import org.compiere.model.X_CC_Hospitalization;
import org.compiere.model.X_CC_Operation;

/**
 *	Validator for CC
 *
 *  @author mfrojas
 *  Assign bed on new registry
 */
public class ModCCAssignBedHospitalization implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModCCAssignBedHospitalization ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModCCAssignBedHospitalization.class);
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
		engine.addModelChange(X_CC_Hospitalization.Table_Name, this);
				

		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		if(type == TYPE_BEFORE_NEW && po.get_Table_ID()==X_CC_Hospitalization.Table_ID) 
		{	
			X_CC_Hospitalization hosp = (X_CC_Hospitalization)po;
			
			//Se valida que si el ingreso tiene una cama asociada, entonces se debe asociar inmediatamente
			//al paciente.
			
			int bed = hosp.get_ValueAsInt("M_Locator1_ID");
			if(bed > 0)
			{
				//Asociar la cama al paciente
				int patient = hosp.getC_BPartner_ID();
				//Se debe crear un registro en gestion cama  (Cc_operation) asociado al paciente
				
				X_CC_Operation op = new X_CC_Operation(po.getCtx(), 0, po.get_TrxName());
				op.setC_BPartner_ID(patient);
				op.setAD_Org_ID(hosp.getAD_Org_ID());
				op.setAD_User_ID(hosp.getAD_User_ID());
				op.setCC_Hospitalization_ID(hosp.get_ID());
				op.setM_Locator1_ID(bed);
				op.setStartDate(hosp.getCreated());
				if(hosp.get_Value("Patient_Complexity") != null)
					op.set_CustomColumn("Patient_Complexity", hosp.get_Value("Patient_Complexity").toString());
				log.config("lala");

				op.save();
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