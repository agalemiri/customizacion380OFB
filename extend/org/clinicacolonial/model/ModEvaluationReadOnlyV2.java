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

import java.sql.Timestamp;

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_A_Asset_Use;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.MAsset;
import org.compiere.model.X_CC_Evaluation;
import org.compiere.model.X_CC_Evaluation_nursing;
import org.compiere.model.X_CC_Evolution_Kine;
import org.compiere.model.X_MH_WoundManagement;
import org.compiere.model.X_CC_MedicalOrder;
import org.compiere.model.X_CC_Other_Indications;
import org.compiere.model.X_CC_MedicalIndications;

/**
 *	Validator for CC
 *
 *  @author mfrojas
 */
public class ModEvaluationReadOnlyV2 implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModEvaluationReadOnlyV2 ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModEvaluationReadOnlyV2.class);
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
		engine.addModelChange("CC_Evaluation", this); // 2000014
		engine.addModelChange("CC_Evaluation_nursing", this); // 2000048
		engine.addModelChange("CC_Evolution_Kine", this);
		engine.addModelChange("MH_WoundManagement", this);
		engine.addModelChange("CC_MedicalOrder", this);
		engine.addModelChange("CC_MedicalIndications", this);
		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		//mfrojas no se puede modificar una registro
		
		if(type == TYPE_BEFORE_CHANGE && po.get_Table_ID()==X_CC_Evaluation.Table_ID) 
		{	
			
			int idevaluation = po.get_ValueAsInt("CC_Evaluation_ID");
			int idrole = Env.getAD_Role_ID(Env.getCtx());
			int iduser = Env.getAD_User_ID(Env.getCtx());
			log.config("rol: "+idrole);
			log.config("usuario "+iduser);
			int iduserofeval = po.get_ValueAsInt("AD_User_ID");
			log.config("usuario del registro: "+iduserofeval);
			
			//Si es el superusuario no hay problema. Si es distinto, se debe ver que el logueado sea el mismo
			//Y que el registro sea del día.
			if(iduser != 100)
			{
				if(iduser != iduserofeval)
					return "No puede modificar esta evolucion";
				String datenow = "SELECT date_Trunc('day',now()) from ad_system where 1=?";
				String dateregistry = "SELECT date_Trunc('day',created) from cc_evaluation where cc_evaluation_id = ?";
				
				Timestamp datenowd = DB.getSQLValueTS(po.get_TrxName(), datenow, 1);
				Timestamp dateregistryd = DB.getSQLValueTS(po.get_TrxName(), dateregistry, idevaluation);
				
				
				//mfrojas modificacion: se tomara 24 horas posteriores al created de la evolucion
				String datenow24 = "SELECT now() from ad_system where 1=?";
				String dateregistry24 = "SELECT (created + interval '1 day') from cc_evaluation where cc_evaluation_id = ?";
				
				Timestamp datenow24t = DB.getSQLValueTS(po.get_TrxName(), datenow24, 1);
				Timestamp dateregistry24t = DB.getSQLValueTS(po.get_TrxName(), dateregistry24, idevaluation);
				
				
				
				/*if(datenowd.compareTo(dateregistryd) != 0)
					return "No se puede modificar un registro de una fecha distinta a la actual";*/
				
				if(datenow24t.compareTo(dateregistry24t) > 0)
					return "No se puede modificar un registro de una fecha distinta a la actual";
			}
			


		}

		if(type == TYPE_BEFORE_CHANGE && po.get_Table_ID()==X_CC_Evaluation_nursing.Table_ID) 
		{	
			
			int idevaluation = po.get_ValueAsInt("CC_Evaluation_nursing_ID");
			int idrole = Env.getAD_Role_ID(Env.getCtx());
			int iduser = Env.getAD_User_ID(Env.getCtx());
			log.config("rol: "+idrole);
			log.config("usuario "+iduser);
			int iduserofeval = po.get_ValueAsInt("AD_User_ID");
			log.config("usuario del registro: "+iduserofeval);
			
			//Si es el superusuario no hay problema. Si es distinto, se debe ver que el logueado sea el mismo
			//Y que el registro sea del día.
			if(iduser != 100)
			{
				if(iduser != iduserofeval)
					return "No puede modificar esta evolucion";
				String datenow = "SELECT date_Trunc('day',now()) from ad_system where 1=?";
				String dateregistry = "SELECT date_Trunc('day',created) from cc_evaluation_nursing where cc_evaluation_nursing_id = ?";
				
				Timestamp datenowd = DB.getSQLValueTS(po.get_TrxName(), datenow, 1);
				Timestamp dateregistryd = DB.getSQLValueTS(po.get_TrxName(), dateregistry, idevaluation);
				
				//mfrojas modificacion: se tomara 24 horas posteriores al created de la evolucion
				String datenow24 = "SELECT now() from ad_system where 1=?";
				String dateregistry24 = "SELECT (created + interval '1 day') from cc_evaluation_nursing where cc_evaluation_nursing_id = ?";
				
				Timestamp datenow24t = DB.getSQLValueTS(po.get_TrxName(), datenow24, 1);
				Timestamp dateregistry24t = DB.getSQLValueTS(po.get_TrxName(), dateregistry24, idevaluation);

				if(datenow24t.compareTo(dateregistry24t) > 0)
					return "No se puede modificar un registro de una fecha distinta a la actual";

				
/*				if(datenowd.compareTo(dateregistryd) != 0)
					return "No se puede modificar un registro de una fecha distinta a la actual";*/
			}
			


		}

		if(type == TYPE_BEFORE_CHANGE && po.get_Table_ID()==X_CC_Evolution_Kine.Table_ID) 
		{	
			
			int idevaluation = po.get_ValueAsInt("CC_Evolution_kine_ID");
			int idrole = Env.getAD_Role_ID(Env.getCtx());
			int iduser = Env.getAD_User_ID(Env.getCtx());
			log.config("rol: "+idrole);
			log.config("usuario "+iduser);
			int iduserofeval = po.get_ValueAsInt("AD_User_ID");
			log.config("usuario del registro: "+iduserofeval);
			
			//Si es el superusuario no hay problema. Si es distinto, se debe ver que el logueado sea el mismo
			//Y que el registro sea del día.
			if(iduser != 100)
			{
				if(iduser != iduserofeval)
					return "No puede modificar esta evolucion";
				String datenow = "SELECT date_Trunc('day',now()) from ad_system where 1=?";
				String dateregistry = "SELECT date_Trunc('day',created) from cc_evolution_kine where cc_evolution_kine_id = ?";
				
				Timestamp datenowd = DB.getSQLValueTS(po.get_TrxName(), datenow, 1);
				Timestamp dateregistryd = DB.getSQLValueTS(po.get_TrxName(), dateregistry, idevaluation);

				//mfrojas modificacion: se tomara 24 horas posteriores al created de la evolucion
				String datenow24 = "SELECT now() from ad_system where 1=?";
				String dateregistry24 = "SELECT (created + interval '1 day') from cc_evolution_kine where cc_evolution_kine_id = ?";
				
				Timestamp datenow24t = DB.getSQLValueTS(po.get_TrxName(), datenow24, 1);
				Timestamp dateregistry24t = DB.getSQLValueTS(po.get_TrxName(), dateregistry24, idevaluation);

				if(datenow24t.compareTo(dateregistry24t) > 0)
					return "No se puede modificar un registro de una fecha distinta a la actual";

/*				if(datenowd.compareTo(dateregistryd) != 0)
					return "No se puede modificar un registro de una fecha distinta a la actual";*/
			}
			


		}
		
		if(type == TYPE_BEFORE_CHANGE && po.get_Table_ID()==X_MH_WoundManagement.Table_ID) 
		{	
			
			int idevaluation = po.get_ValueAsInt("MH_WoundManagement_ID");
			int idrole = Env.getAD_Role_ID(Env.getCtx());
			int iduser = Env.getAD_User_ID(Env.getCtx());
			log.config("rol: "+idrole);
			log.config("usuario "+iduser);
			int iduserofeval = po.get_ValueAsInt("AD_User_ID");
			log.config("usuario del registro: "+iduserofeval);
			
			//Si es el superusuario no hay problema. Si es distinto, se debe ver que el logueado sea el mismo
			//Y que el registro sea del día.
			if(iduser != 100)
			{
				if(iduser != iduserofeval)
					return "No puede modificar esta evolucion";
				String datenow = "SELECT date_Trunc('day',now()) from ad_system where 1=?";
				String dateregistry = "SELECT date_Trunc('day',created) from MH_WoundManagement where MH_WoundManagement_id = ?";
				
				Timestamp datenowd = DB.getSQLValueTS(po.get_TrxName(), datenow, 1);
				Timestamp dateregistryd = DB.getSQLValueTS(po.get_TrxName(), dateregistry, idevaluation);
				
				if(datenowd.compareTo(dateregistryd) != 0)
					return "No se puede modificar un registro de una fecha distinta a la actual";
			}
			


		}
		if(type == TYPE_BEFORE_CHANGE && po.get_Table_ID()==X_CC_MedicalOrder.Table_ID) 
		{	
			
			int idevaluation = po.get_ValueAsInt("CC_MedicalOrder_ID");
			int idrole = Env.getAD_Role_ID(Env.getCtx());
			int iduser = Env.getAD_User_ID(Env.getCtx());
			log.config("rol: "+idrole);
			log.config("usuario "+iduser);
			int iduserofeval = po.get_ValueAsInt("AD_User_ID");
			log.config("usuario del registro: "+iduserofeval);
			
			//Si es el superusuario no hay problema. Si es distinto, se debe ver que el logueado sea el mismo
			//Y que el registro sea del día.
			if(iduser != 100)
			{
				if(iduser != iduserofeval)
					return "No puede modificar esta evolucion";
				String datenow = "SELECT date_Trunc('day',now()) from ad_system where 1=?";
				String dateregistry = "SELECT date_Trunc('day',created) from CC_MedicalOrder where cc_medicalorder_id = ?";
				
				Timestamp datenowd = DB.getSQLValueTS(po.get_TrxName(), datenow, 1);
				Timestamp dateregistryd = DB.getSQLValueTS(po.get_TrxName(), dateregistry, idevaluation);
				
				if(datenowd.compareTo(dateregistryd) != 0)
					return "No se puede modificar un registro de una fecha distinta a la actual";
			}
			


		}

		if(type == TYPE_BEFORE_CHANGE && po.get_Table_ID()==X_CC_MedicalIndications.Table_ID) 
		{	
			
			int idevaluation = po.get_ValueAsInt("CC_MedicalIndications_ID");
			int idrole = Env.getAD_Role_ID(Env.getCtx());
			int iduser = Env.getAD_User_ID(Env.getCtx());
			log.config("rol: "+idrole);
			log.config("usuario "+iduser);
			int iduserofeval = po.get_ValueAsInt("CreatedBy");
			log.config("usuario del registro: "+iduserofeval);
			
			//Si es el superusuario no hay problema. Si es distinto, se debe ver que el logueado sea el mismo
			//Y que el registro sea del día.
			if(iduser != 100)
			{
				/*if(iduser != iduserofeval)
					return "No puede modificar esta evolucion";
				*/
				String datenow = "SELECT date_Trunc('day',now()) from ad_system where 1=?";
				String dateregistry = "SELECT date_Trunc('day',created) from CC_MedicalIndications where CC_MedicalIndications_id = ?";
				
				Timestamp datenowd = DB.getSQLValueTS(po.get_TrxName(), datenow, 1);
				Timestamp dateregistryd = DB.getSQLValueTS(po.get_TrxName(), dateregistry, idevaluation);
				
				if(datenowd.compareTo(dateregistryd) != 0)
				{
					String datenowyesterday = "SELECT date_Trunc('day',now())-1 from ad_system where 1=?";
					String dateregistryyesterday = "SELECT date_Trunc('day',created)-1 from CC_MedicalIndications where CC_MedicalIndications_id = ?";
					
					Timestamp datenowyesterdayd = DB.getSQLValueTS(po.get_TrxName(),datenowyesterday,1);
					Timestamp dateregistryyesterdayd = DB.getSQLValueTS(po.get_TrxName(),dateregistryyesterday,idevaluation);
					log.config("datenowyesterday "+datenowyesterdayd);
					log.config("dateregistryyesterday "+dateregistryyesterdayd);
					if(datenowyesterdayd.compareTo(dateregistryd) != 0)
						return "No se puede modificar un registro de una fecha distinta a la actual";
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