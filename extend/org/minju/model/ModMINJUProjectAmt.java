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
import org.compiere.model.X_DM_Document;
import org.compiere.model.X_RH_MedicalLicenses;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.model.MProject;
import org.compiere.model.X_C_SubProjectOFB;
import org.compiere.model.MProject;
import org.compiere.model.MProjectLine;
import org.compiere.model.X_PM_ProjectPay;
import java.math.BigDecimal;
import org.compiere.model.X_C_ProjectSchedule;


/**
 *	Validator for MINJU
 *
 *  @author mfrojas
 */
public class ModMINJUProjectAmt implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModMINJUProjectAmt ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModMINJUProjectAmt.class);
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
		engine.addModelChange(MProject.Table_Name, this);
		engine.addModelChange(X_C_SubProjectOFB.Table_Name, this);
		engine.addModelChange(MProjectLine.Table_Name, this);
//		engine.addModelChange(X_PM_ProjectPay.Table_Name, this);
		engine.addModelChange("C_ProjectSchedule", this); // ID tabla 2000045


		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		if((type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE)&& po.get_Table_ID()==X_C_SubProjectOFB.Table_ID) 
		{	
			X_C_SubProjectOFB spro = (X_C_SubProjectOFB)po;
			//Primero se actualiza los nudos críticos de la cabecera para tener el conteo correcto
			
			//mfrojas 20181121 se comenta updates. Esto porque se cambia la logica. 
			/*DB.executeUpdate("Update c_project set CriticalKnot1 = (select coalesce(count(1),0) from c_subprojectofb where CriticalKnot='MDS' and c_project_id = "+spro.getC_Project_ID()+") where c_project_id =  "+spro.getC_Project_ID(), spro.get_TrxName());
			DB.executeUpdate("Update c_project set CriticalKnot2 = (select coalesce(count(1),0) from c_subprojectofb where CriticalKnot='MOP' and c_project_id = "+spro.getC_Project_ID()+") where c_project_id =  "+spro.getC_Project_ID(), spro.get_TrxName());
			DB.executeUpdate("Update c_project set CriticalKnot3 = (select coalesce(count(1),0) from c_subprojectofb where CriticalKnot='CGR' and c_project_id = "+spro.getC_Project_ID()+") where c_project_id =  "+spro.getC_Project_ID(), spro.get_TrxName());
			DB.executeUpdate("Update c_project set CriticalKnot4 = (select coalesce(count(1),0) from c_subprojectofb where CriticalKnot='OPL' and c_project_id = "+spro.getC_Project_ID()+") where c_project_id =  "+spro.getC_Project_ID(), spro.get_TrxName());
			*/
			//Luego se revisa de qué tipo es la etapa y subetapa asociada al registro, para ver qué se debe hacer.
			
			
			if(spro.getC_Phase_ID2().compareTo("FPI")==0)
			{
				MProjectLine prol = new MProjectLine(po.getCtx(), spro.getC_ProjectLine_ID(), po.get_TrxName());

				String act = spro.getActivitiesList();
				if(act.compareTo("001")==0)
				{
					prol.set_CustomColumn("PlannedAmtLine", spro.getAmtApproval());
					prol.save();
				}
				else if(act.compareTo("002")==0)
				{
					prol.set_CustomColumn("BudgetAmt", spro.getAmtApproval());
					prol.save();
				}
				else if(act.compareTo("003")==0)
				{
					prol.set_CustomColumn("CommittedAmt", prol.getCommittedAmt().add(spro.getAmtApproval()));
					prol.save();

				}
				else if(act.compareTo("004")==0)
				{
					prol.set_CustomColumn("CommitedAmt", prol.getCommittedAmt().subtract(spro.getAmtApproval()));
					prol.save();

				}
				
			}
			
			//Para proyectos UCE, se obtiene el valor del campo TASKLIST. 73 -> Identificado. 74 -> Ejecutado
			
			
			MProjectLine prol2 = new MProjectLine(po.getCtx(), spro.getC_ProjectLine_ID(), po.get_TrxName());
			String sqltask = "SELECT task_list from c_subprojectofb where c_subprojectofb_id = ?";
			String task = DB.getSQLValueString(po.get_TrxName(), sqltask, spro.get_ID());
			
			if(task != null && task.startsWith("P")==true)
			{
				prol2.set_CustomColumn("CommitedAmt", prol2.getCommittedAmt().add(spro.getAmtApproval()));
				prol2.save();
			}
			else if(task != null && task.startsWith("E")==true)
			{
				int amountex = DB.getSQLValue(po.get_TrxName(), "SELECT coalesce(ExecutedAmt,0) from c_projectline where c_projectline_id = "+spro.getC_ProjectLine_ID());
				BigDecimal amountbd = new BigDecimal(amountex);
				
				prol2.set_CustomColumn("ExecutedAmt", amountbd.add(spro.getAmtApproval()));
				prol2.save();
			}

			
		}	
		
		if((type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE)&& po.get_Table_ID()==MProjectLine.Table_ID)
		{
			MProjectLine prol = (MProjectLine)po;
			
			DB.executeUpdate("Update c_project set PlannedAmt = (select coalesce(sum(plannedamtline),0) from c_projectline where c_project_id = "+prol.getC_Project_ID()+") where c_project_id =  "+prol.getC_Project_ID(), prol.get_TrxName());
			DB.executeUpdate("Update c_project set BudgetAmt = (select coalesce(sum(BudgetAmt),0) from c_projectline where c_project_id = "+prol.getC_Project_ID()+") where c_project_id =  "+prol.getC_Project_ID(), prol.get_TrxName());
			DB.executeUpdate("Update c_project set CommittedAmt = (select coalesce(sum(CommittedAmt),0) from c_projectline where c_project_id = "+prol.getC_Project_ID()+") where c_project_id =  "+prol.getC_Project_ID(), prol.get_TrxName());
			DB.executeUpdate("Update c_project set ExecutedAmt = (select coalesce(sum(ExecutedAmt),0) from c_projectline where c_project_id = "+prol.getC_Project_ID()+") where c_project_id =  "+prol.getC_Project_ID(), prol.get_TrxName());
			DB.executeUpdate("Update c_project set PaidAmt = (select coalesce(sum(PaidAmt),0) from c_projectline where c_project_id = "+prol.getC_Project_ID()+") where c_project_id =  "+prol.getC_Project_ID(), prol.get_TrxName());
			DB.executeUpdate("Update c_project set OtherExpenses = (select coalesce(sum(OtherExpenses),0) from c_projectline where c_project_id = "+prol.getC_Project_ID()+") where c_project_id =  "+prol.getC_Project_ID(), prol.get_TrxName());
			
		}
/**		if((type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE)&& po.get_Table_ID()==X_PM_ProjectPay.Table_ID)
		{
			X_PM_ProjectPay pp = (X_PM_ProjectPay)po;
			//f(pp.getpay_type().compareTo("FA")==0)
			//{
				int projectline_id = DB.getSQLValue(pp.get_TrxName(), "SELECT c_projectline_id from dm_document where dm_document_id in (select dm_document_id from c_projectschedule where c_projectschedule_id in (select c_projectschedule_id from pm_projectpay where pm_projectpay_id = "+pp.get_ID()+"))");
				MProjectLine prol = new MProjectLine(po.getCtx(), projectline_id, po.get_TrxName());
				//int originalvalue = prol.get_ValueAsInt("ExecutedAmt");
				//log.config("executed"+originalvalue);
				int sumexecuted = DB.getSQLValue(pp.get_TrxName(),"SELECT sum(amt) from pm_projectpay where isactive='Y' and pay_type='FA' and pay_type is not null and c_projectschedule_id in (select c_projectschedule_id from c_projectschedule where dm_document_id in (select dm_document_id from dm_document where c_projectline_id = "+projectline_id+"))");
				BigDecimal bdl = new BigDecimal(sumexecuted);
				prol.set_CustomColumn("ExecutedAmt",bdl);
				prol.save();
				
				//DB.executeUpdate("Update c_projectline set executedamt = executedamt + "+pp.getAmt()+" where c_projectline_id in (select c_projectline_id from dm_document where dm_document_id in (select dm_document_id from c_projectschedule where c_projectschedule_id in (select c_projectschedule_id from pm_projectpay where pm_projectpay_id = "+pp.get_ID()+")))",pp.get_TrxName());
				
			//}
		}
		**/
		if((type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE)&& po.get_Table_ID()==2000045)
		{
			//if(po.get_ValueAsInt("C_Payment_ID")>0)
			//{
				//int projectline_id = DB.getSQLValue(po.get_TrxName(), "SELECT c_projectline_id from dm_document where dm_document_id = "+po.get_ValueAsInt("DM_Document_ID"));
				int projectline_id = po.get_ValueAsInt("C_ProjectLine_ID");
				log.config("projectline id "+projectline_id);
				int sumpaid = DB.getSQLValue(po.get_TrxName(), "SELECT sum(dueamt) from c_projectschedule where isactive='Y' and C_ProjectLine_ID = "+projectline_id);
				BigDecimal valor = new BigDecimal(sumpaid);
				MProjectLine prol = new MProjectLine(po.getCtx(), projectline_id, po.get_TrxName());
				prol.set_CustomColumn("PaidAmt", valor);
				prol.save();
				
			//}
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