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


/**
 *	Validator for CC
 *
 *  @author mfrojas
 *  Validate Only One Hospitalization
 */
public class ModCCValidateHospitalization implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModCCValidateHospitalization ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModCCValidateHospitalization.class);
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
			
			//Se valida que el ingreso sólo sea de tipo "No Enfermería"
			//Primero se valida ingresos no ambulatorios.
			String sql = "";
			int counter = 0;
			//if(!hosp.get_ValueAsBoolean("IsAmbulatory"))
			if(hosp.get_ValueAsInt("MED_Specialty_ID") <= 0)
			{
				//no es ambulatorio
				sql = "Select count(1) from cc_hospitalization where (med_specialty_id is null or med_specialty_id = 0)  and c_bpartner_id = "+hosp.getC_BPartner_ID()+" " +
					" and cc_hospitalization_id not in (select cc_hospitalization_id " +
					"from cc_administrativedischarge where DocStatus='CO') ";
			
			counter = DB.getSQLValue(po.get_TrxName(), sql);
			
			if (counter > 0 && hosp.getC_DocType_ID() != 2000138)
				 return "Ya existe un ingreso sin alta asociada";
			
			}
			else
			{
			//Ahora se valida ingresos ambulatorios
			
				sql = "Select count(1) from cc_hospitalization where med_specialty_id > 0 and c_bpartner_id = "+hosp.getC_BPartner_ID()+" " +
					" and ispayable='N'";
			
				counter = DB.getSQLValue(po.get_TrxName(), sql);
			
				if (counter > 0 && hosp.getC_DocType_ID() != 2000138)
					return "Ya existe un ingreso ambulatorio sin pago asociada";
			//Validar ingresos que corresponden a enfermería
			//String sql2 = "Select count(1) from cc_hospitalization where c_bpartner_id = "+hosp.getC_BPartner_ID()+" and " +
			//		" c_doctype_id = 2000138 and cc_hospitalization_id not in (select cc_hospitalization_id " +
			//		"from cc_administrativedischarge where DocStatus='CO') ";
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