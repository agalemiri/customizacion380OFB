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
package org.indap.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.Calendar;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.X_HR_AdministrativeRequests;
import org.compiere.model.X_HR_AdministrativeRequestsL;

import org.compiere.model.X_HR_AttendanceLine;
import org.ofb.model.OFBForward;
import org.ofb.utils.DateUtils;
import org.compiere.model.X_HR_HoursUsedDetail;
import org.compiere.model.X_HR_HourPlanningLine;
import org.compiere.model.X_HR_HourPlanningControl;


/**
 *	
 *	
 *  @author mfrojas 
 *  @version $Id: ProcessHourPlanning.java $
 */
//
public class ProcessHourPlanning extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_HourPlanningControl_ID = 0; 
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
		p_HourPlanningControl_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_HourPlanningControl_ID > 0)
		{
			X_HR_HourPlanningControl planning = new X_HR_HourPlanningControl(getCtx(), p_HourPlanningControl_ID, get_TrxName());
			//seteo de nuevo estado al procesar
			String newStatus = "DR";
			String newAction = "DR";	
			log.config("paction "+p_Action);
			if(planning.getDocStatus().compareTo("CO") == 0)
				return "Documento ya completo";
			if(planning.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}
			

			else if(planning.getDocStatus().compareTo("SS") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}
			/*else if(req.getDocStatus().compareTo("AP") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AH";
				newAction = "AH";
			}*/
			else if(planning.getDocStatus().compareTo("AP") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}	

	
			//validacion de rol
			int cant = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM AD_Document_Action_Access daa" +
					" INNER JOIN AD_Ref_List rl ON (daa.AD_Ref_List_ID = rl.AD_Ref_List_ID) " +
					" WHERE value = '"+newAction+"' AND AD_Role_ID = "+Env.getAD_Role_ID(getCtx())+
					" AND C_DocType_ID = "+planning.get_ValueAsInt("C_DocType_ID"));
			//procesar
			log.config("cantidad"+cant);
			if(cant > 0)
			{
				if(newAction.compareTo("CO") != 0)
				{
					planning.setDocStatus(newStatus);

					planning.save();
					
				}
				
				else if(newAction.compareTo("CO") == 0)
				{
					
					
					planning.setDocStatus("CO");
					planning.set_CustomColumn("IsReadOnly", true);
					//req.processIt("CO");
					planning.save();
					/*String sqlupdate = "UPDATE HR_HourPlanningLine SET DayTime1 = DayTime1*60, NightTime1 = NightTime1*60, " +
							" IsReadOnly='Y' WHERE HR_HourPlanningControl_ID = "+planning.get_ID();*/
					
					String sqlupdate = "UPDATE HR_HourPlanningLine SET DayTimeReal1 = (DayTime1*60)*1.25, NightTimeReal1 = (NightTime1*60)*1.5, " +
							" IsReadOnly='Y' WHERE HR_HourPlanningControl_ID = "+planning.get_ID();
					log.config("sqlupdate "+sqlupdate);
					DB.executeUpdate(sqlupdate, get_TrxName());
					
				}
				
			}
			else
				throw new AdempiereException("Error: Permisos de rol insuficientes");
		}
	   return "Procesado";
	}	//	doIt
}
