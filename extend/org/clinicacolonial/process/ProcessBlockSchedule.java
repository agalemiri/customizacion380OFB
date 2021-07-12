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

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.*;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPInstance;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.*;
import org.compiere.model.X_MED_Schedule;
import org.compiere.model.X_MED_ScheduleDay;
import org.compiere.model.X_MED_ScheduleTime;
import org.compiere.model.X_MED_Template;
import org.compiere.model.X_MED_TemplateDay;
import org.compiere.process.ProcessInfoParameter;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessCCShiftChange.java $
 */

public class ProcessBlockSchedule extends SvrProcess
{
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	private int 			m_Record_ID = 0;
	private int 			p_BPartnerMed_ID = 0;
	private Timestamp 		p_DateFrom;
	private Timestamp 		p_DateTo;
	private Timestamp 		p_TimeFrom;
	private Timestamp 		p_TimeTo;
	
	protected void prepare()
	{
		m_Record_ID = getRecord_ID();
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			

			if(name.equals("C_BPartnerMed_ID"))
				p_BPartnerMed_ID = para[i].getParameterAsInt();		
			else if(name.equals("DateFrom"))
				p_DateFrom = para[i].getParameterAsTimestamp();
			else if(name.equals("DateTo"))
				p_DateTo = para[i].getParameterAsTimestamp();
			else if(name.equals("TimeFrom"))
				p_TimeFrom = para[i].getParameterAsTimestamp();
			else if(name.equals("TimeTo"))
				p_TimeTo = para[i].getParameterAsTimestamp();

			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{	
		if(p_BPartnerMed_ID == 0)
			throw new IllegalArgumentException("No ha seleccionado un medico");
		if(p_DateFrom == null)
			throw new IllegalArgumentException("No ha seleccionado una fecha/hora desde");
		if(p_DateTo == null)
			throw new IllegalArgumentException("No ha seleccionado una fecha/hora hasta");
		if(p_TimeFrom == null)
			throw new IllegalArgumentException("No ha seleccionado una fecha/hora desde");
		if(p_TimeTo == null)
			throw new IllegalArgumentException("No ha seleccionado una fecha/hora hasta");
		

		String sqlcount = "SELECT count(1)  FROM MED_ScheduleTime  WHERE isactive='Y' AND State='DI' " +
				" AND timerequested::date >= '"+p_DateFrom+"' " +
				" AND timerequested::date <= '"+p_DateTo+"'::date " +
				" AND timerequested::time >= '"+p_TimeFrom+"' " +
				" AND timerequested::time <= '"+p_TimeTo+"' " +
				" AND MED_ScheduleDay_ID in (" +
				" SELECT MED_ScheduleDay_ID from MED_ScheduleDay WHERE MED_Schedule_ID in " +
				" ( SELECT MED_Schedule_ID from MED_Schedule WHERE C_BPartnerMed_ID = "+p_BPartnerMed_ID+"))";
		
		String sqlupdate = "UPDATE MED_ScheduleTime SET State = 'BL' WHERE isactive='Y' AND State='DI' " +
				" AND timerequested::date >= '"+p_DateFrom+"' " +
				" AND timerequested::date <= '"+p_DateTo+"'::date " +
				" AND timerequested::time >= '"+p_TimeFrom+"' " +
				" AND timerequested::time <= '"+p_TimeTo+"' " +
				" AND MED_ScheduleDay_ID in (" +
				" SELECT MED_ScheduleDay_ID from MED_ScheduleDay WHERE MED_Schedule_ID in " +
				" ( SELECT MED_Schedule_ID from MED_Schedule WHERE C_BPartnerMed_ID = "+p_BPartnerMed_ID+"))";
		log.config(sqlcount);
		int counter = DB.getSQLValue(get_TrxName(), sqlcount);
		if(counter > 0)
			DB.executeUpdate(sqlupdate, get_TrxName());
		return "Actualizados: "+counter+" registros";
		
	}
	
}
