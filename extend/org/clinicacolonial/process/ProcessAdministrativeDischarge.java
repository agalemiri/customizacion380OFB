/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
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
package org.clinicacolonial.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.model.X_CC_AdministrativeDischarge;
/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessSalesOrder.java $
 */

public class ProcessAdministrativeDischarge extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_AdmDis_ID = 0; 
	private String				p_Action = "PR";
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if (name.equals("Action"))
				p_Action = para[i].getParameterAsString();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_AdmDis_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_AdmDis_ID > 0)
		{
			X_CC_AdministrativeDischarge adm = new X_CC_AdministrativeDischarge(getCtx(), p_AdmDis_ID, get_TrxName());
			//seteo de nuevo estado al procesar
			log.config("paction "+p_Action);
			log.config("processd"+adm.isProcessed());
			
			if(adm.isProcessed() == false && p_Action.compareTo("AL") == 0)
			{
				int bpartneralta = DB.getSQLValue(get_TrxName(), "SELECT max(c_bpartner_id) from c_bpartner where name like 'ALTA'");
				
				//Se actualiza el ingreso medico (tabla cc_hospitalization)
				//Se obtiene el c_bpartner_id del ingreso médico.
				String sqlpartner = "SELECT c_bpartner_id from cc_hospitalization where cc_hospitalization_id = "+adm.getCC_Hospitalization_ID();
				int partnerhosp = DB.getSQLValue(get_TrxName(), sqlpartner);
				
				//ininoles se actualiza nuevo campo y se deja processed en N
				//DB.executeUpdate("Update cc_hospitalization set processed='Y' where cc_hospitalization_id = "+adm.getCC_Hospitalization_ID(), get_TrxName());
				DB.executeUpdate("Update cc_hospitalization set IsReadOnly1='Y' where cc_hospitalization_id = "+adm.getCC_Hospitalization_ID(), get_TrxName());
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());

				DB.executeUpdate("Update A_Locator_Use set DateEnd = '"+timestamp+"' where dateend is null and c_bpartner_id = "+partnerhosp,get_TrxName());
				DB.executeUpdate("UPDATE M_Locator set C_BPartner_ID = "+bpartneralta+" WHERE C_Bpartner_ID = "+partnerhosp,get_TrxName());
				
				//Se actualiza las asignaciones de cama a processed='Y'
				
				DB.executeUpdate("UPDATE CC_Operation set processed='Y' where cc_hospitalization_id = "+adm.getCC_Hospitalization_ID(),get_TrxName());
				DB.executeUpdate("UPDATE CC_Evaluation set processed='Y' where cc_hospitalization_id = "+adm.getCC_Hospitalization_ID(),get_TrxName());
				DB.executeUpdate("UPDATE CC_MedicalOrder set processed='Y' where cc_hospitalization_id = "+adm.getCC_Hospitalization_ID(),get_TrxName());
				DB.executeUpdate("UPDATE CC_CIE10 set processed='Y' where c_bpartner_id = "+partnerhosp,get_TrxName());
				DB.executeUpdate("UPDATE CC_MedicalIndications set processed='Y' where c_bpartner_id = "+partnerhosp,get_TrxName());
				DB.executeUpdate("UPDATE CC_ShiftChange set processed='Y' where cc_hospitalization_id = "+adm.getCC_Hospitalization_ID(),get_TrxName());
				DB.executeUpdate("UPDATE CC_Epicrisis set processed='Y' where cc_hospitalization_id = "+adm.getCC_Hospitalization_ID(),get_TrxName());
				DB.executeUpdate("UPDATE CC_Other_Indications set processed='Y' where c_bpartner_id = "+partnerhosp,get_TrxName());

				log.config("lalala");
				adm.setProcessed(true);
				adm.setDocStatus("CO");
				log.config("Procesado");
				adm.save();

			}
		}	
	   return "Procesado";
	}	//	doIt
}
