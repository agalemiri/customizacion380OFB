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

import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.model.X_CC_Hospitalization;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessSalesOrder.java $
 */

public class ProcessHospitalization extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_Hospitalization_ID = 0; 
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
		p_Hospitalization_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_Hospitalization_ID > 0)
		{
			X_CC_Hospitalization hosp = new X_CC_Hospitalization(getCtx(), p_Hospitalization_ID, get_TrxName());
			//seteo de nuevo estado al procesar
			log.config("paction "+p_Action);
			log.config("processd"+hosp.isProcessed());
			
			if(hosp.isProcessed() == false && p_Action.compareTo("AL") == 0)
			{
				int bpartneralta = DB.getSQLValue(get_TrxName(), "SELECT max(c_bpartner_id) from c_bpartner where name like 'ALTA'");
				DB.executeUpdate("UPDATE M_Locator set C_BPartner_ID = "+bpartneralta+" WHERE C_Bpartner_ID = "+hosp.getC_BPartner_ID(),get_TrxName());
				
				//Se actualiza las asignaciones de cama a processed='Y'
				
				DB.executeUpdate("UPDATE CC_Operation set processed='Y' where cc_hospitalization_id = "+hosp.get_ID(),get_TrxName());
				
				log.config("lalala");
				hosp.setProcessed(true);
				log.config("Procesado");
				hosp.save();

			}
		}	
	   return "Procesado";
	}	//	doIt
}
