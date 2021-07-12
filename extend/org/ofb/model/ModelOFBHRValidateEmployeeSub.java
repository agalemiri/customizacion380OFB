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
package org.ofb.model;

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.model.X_HR_Contract;
import org.eevolution.model.X_HR_Employee;


/**
 *	Validator for HR, Substitution must be by an employee who has the same degree
 *
 *  @author mfrojas
 */
public class ModelOFBHRValidateEmployeeSub implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelOFBHRValidateEmployeeSub ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelOFBHRValidateEmployeeSub.class);
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
		engine.addModelChange(X_HR_Employee.Table_Name, this);

		//	Documents to be monitored

	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */

	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		if ((type == TYPE_BEFORE_CHANGE || type == TYPE_BEFORE_NEW) && po.get_Table_ID()==X_HR_Employee.Table_ID)
		{
			X_HR_Employee emp = (X_HR_Employee)po;
			//mfrojas se revisa el campo suplencia 
			
			int employeesub = emp.get_ValueAsInt("C_BPartnerRef_ID");
			if(employeesub > 0)
			{
				//String sqlsup = "SELECT coalesce(suplencia,'') from hr_employee where hr_employee_id = ?";
				//String sup = DB.getSQLValueString(po.get_TrxName(), sqlsup, emp.getHR_Employee_ID());
				String sup = emp.get_Value("Suplencia").toString();
				if(sup.compareTo("06") == 0 || sup.compareTo("07") == 0)
				{
					//Obtener hr_employee_id del funcionario a reemplazar
					int origemployee = DB.getSQLValue(po.get_TrxName(), "SELECT coalesce(max(hr_employee_id),0) from hr_employee where " +
							" c_bpartner_id = "+employeesub);
					if(origemployee > 0)
					{
						
					
						String sqlestamentoplantaoriginal = "SELECT coalesce(HR_JobRef_ID,0) from hr_employee where hr_employee_id = ?";
						String sqlgradoplantaoriginal = "SELECT coalesce(graderef,'') from hr_employee where hr_employee_id = ?";
					
						int estamentoplantaoriginal = DB.getSQLValue(po.get_TrxName(), sqlestamentoplantaoriginal, origemployee);
						String gradoplantaoriginal = DB.getSQLValueString(po.get_TrxName(), sqlgradoplantaoriginal, origemployee);
						
						if(estamentoplantaoriginal > 0 && gradoplantaoriginal.compareTo("")!= 0)
						{
							if(estamentoplantaoriginal != emp.get_ValueAsInt("HR_JobRef_ID") || gradoplantaoriginal.compareTo(emp.get_Value("graderef").toString())!=0)
							{
								return "El funcionario a reemplazar no tiene el mismo estamento o grado del funcionario actual";
							}
						}
						else
							return "El funcionario a reemplazar no tiene registro de cargo y estamento.";
						
						
					}
					else
						return "El funcionario a reemplazar no tiene registro de cargo y estamento.";
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