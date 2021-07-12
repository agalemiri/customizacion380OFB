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

import java.math.BigDecimal;

import org.compiere.model.MClient;

import org.compiere.model.MRequisitionLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.MBPartner;
import org.eevolution.model.X_HR_Employee;


/**
 *	Validator for MINJU
 *
 *  @author mfrojas
 */
public class ModMINJUCopyInfoBPartner implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModMINJUCopyInfoBPartner ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModMINJUCopyInfoBPartner.class);
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
		engine.addModelChange(MBPartner.Table_Name, this);		
		engine.addModelChange(X_HR_Employee.Table_Name, this);
		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		if((type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE) && po.get_Table_ID()==X_HR_Employee.Table_ID) 
		{	
			X_HR_Employee emp = (X_HR_Employee)po;
			MBPartner bp = new MBPartner(po.getCtx(),emp.getC_BPartner_ID(),po.get_TrxName());
			if(emp.get_Value("Occupation") != null)
			{
				bp.set_CustomColumn("Occupation", emp.get_Value("Occupation"));
				bp.set_CustomColumn("Profession", emp.get_Value("Occupation"));
			}
			
			if(emp.get_ValueAsInt("Children")>=0)
				bp.set_CustomColumn("Children", emp.get_ValueAsInt("Children"));
			
			if(emp.get_Value("JoinDateAP") != null)
				bp.set_CustomColumn("JoinDateAP", emp.get_Value("JoinDateAP"));
			if(emp.get_Value("JoinDateS") != null)
				bp.set_CustomColumn("JoinDateS", emp.get_Value("JoinDateS"));
			if(emp.get_Value("Estate") != null)
				bp.set_CustomColumn("Estate", emp.get_Value("Estate"));
			if(emp.get_Value("EstadoVinculacion") != null)
				bp.set_CustomColumn("EstadoVinculacion", emp.get_Value("EstadoVinculacion"));
			if(emp.get_ValueAsBoolean("Profesional_Externo"))
				bp.set_CustomColumn("Profesional_Externo", emp.get_ValueAsBoolean("Profesional_Externo"));
			if(emp.get_ValueAsBoolean("NoEnviaCorreo"))
				bp.set_CustomColumn("NoEnviaCorreo", emp.get_ValueAsBoolean("NoEnviaCorreo"));
			if(emp.get_Value("ID_Usuario_Reloj") != null)
				bp.set_CustomColumn("ID_Usuario_Reloj", emp.get_Value("ID_Usuario_Reloj"));
			if(emp.get_Value("ISAPRE") != null)
				bp.set_CustomColumn("ISAPRE", emp.get_Value("ISAPRE"));
			if(emp.get_Value("AFP") != null)
				bp.set_CustomColumn("AFP", emp.get_Value("AFP"));
			if(emp.get_Value("Nationality") != null)
				bp.set_CustomColumn("Nationality", emp.get_Value("Nationality"));
			if(emp.get_Value("MaritalStatus") != null)
				bp.set_CustomColumn("MaritalStatus", emp.get_Value("MaritalStatus"));
			if(emp.get_Value("Position") != null)
				bp.set_CustomColumn("Position", emp.get_Value("Position"));
			if(emp.get_Value("Gender") != null)
				bp.set_CustomColumn("Gender", emp.get_Value("Gender"));
			if(emp.get_Value("Suplencia") != null)
				bp.set_CustomColumn("Suplencia", emp.get_Value("Suplencia"));
			
			//mfrojas 20181029
			//copiar hr_job a estate de socio de negocio.
			
			if(emp.get_ValueAsInt("HR_Job_ID") > 0)
			{
				int Job_ID = emp.get_ValueAsInt("HR_Job_ID");
				String sqljob = "SELECT lower(name) from hr_job where hr_job_id = ?";
				String name = DB.getSQLValueString(po.get_TrxName(), sqljob, Job_ID);
				
				if(name != null)
				{
					String sqlstatevalue = "SELECT value from ad_Ref_list where ad_reference_id = ? and " +
							" lower(name) like '"+name+"'";
					String value = DB.getSQLValueString(po.get_TrxName(),sqlstatevalue,2000030);
					if(value != null && value.compareTo("")!= 0)
					{
						bp.set_CustomColumn("Estate", value);
					}
					
				}
			}
				
			
			bp.save();
				
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