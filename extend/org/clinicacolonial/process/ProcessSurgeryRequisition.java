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
import org.compiere.model.X_MED_Appointment;
import org.compiere.model.X_MED_Schedule;
import org.compiere.model.X_MED_ScheduleDay;
import org.compiere.model.X_MED_ScheduleTime;
import org.compiere.model.X_MED_Template;
import org.compiere.model.X_MED_TemplateDay;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.model.X_MED_SurgeryRequisition;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessSurgeryRequisition.java $
 */

public class ProcessSurgeryRequisition extends SvrProcess
{
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	private int 			m_Record_ID = 0;
	private String 			p_action = "";
	
	protected void prepare()
	{
		m_Record_ID = getRecord_ID();
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			

			if(name.equals("Action"))
				p_action = para[i].getParameterAsString();		


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

		//Procesar solicitud de pabellon

		//Se debe crear un registro en horario
		if(p_action.compareTo("CO") != 0)
			return "Estado debe ser completo";
		X_MED_SurgeryRequisition req = new X_MED_SurgeryRequisition(getCtx(),m_Record_ID,get_TrxName());
		
		//Primero se validara que esta solicitud no tenga cita medica
		int app = req.get_ValueAsInt("MED_Appointment_ID");
		if(app > 0)
			return "Esta solicitud ya tiene una cita asociada";
		
		//Validar que no exista una cita de pabellon en el mismo horario.
		int specpabellon = DB.getSQLValue(get_TrxName(), "SELECT coalesce(max(med_specialty_id),0) from med_specialty where " +
				" lower(name) like '%pabell%'");
		int flag = 0;
		String sqlgetapp = "SELECT coalesce(max(med_appointment_ID),0) from med_appointment where med_specialty_id = "+specpabellon+" AND " +
				" Attentiontime::date = '"+req.get_Value("DateTrx")+"'";
		log.config("sqlgetapp "+sqlgetapp);
		int medapp = DB.getSQLValue(get_TrxName(), sqlgetapp);
		if(medapp > 0)
		{
			//si medapp > 0 entonces existe una cita de pabellon para el mismo dia
			//se debera verificar si la hora tambien coincide
			String sqlgetapp2 = "SELECT coalesce(max(med_appointment_ID),0) from med_appointment where med_specialty_id = "+specpabellon+" AND " +
					" Attentiontime + interval '"+req.get_ValueAsInt("Duration")+" hour' > '"+req.get_Value("DateStart")+"'";
			log.config("sqlgetapp2 "+sqlgetapp2);
			int medapp2 = DB.getSQLValue(get_TrxName(), sqlgetapp2);
			if(medapp2 > 0)
			{
				return "Existe bloqueo de horario. Existe una cita pabellon para el mismo dia en horario similar";
			}
		}
		String sqlschedule = "SELECT coalesce(MED_Schedule_ID,0) FROM MED_Schedule " +
				" where C_BPartnerMed_ID = "+req.getC_BPartnerMed_ID()+" AND isactive='Y' AND DateFrom <= '"+req.get_Value("DateTrx")+"' AND " +
				" DateTo >= '"+req.get_Value("DateTrx")+"'";
		log.config("sql "+sqlschedule);
		int schedule_id = DB.getSQLValue(get_TrxName(), sqlschedule);
		if(schedule_id > 0)
		{
			int scheduleday_id = DB.getSQLValue(get_TrxName(), "SELECT coalesce(MED_ScheduleDay_ID,0) FROM MED_ScheduleDay " +
					" WHERE MED_Schedule_ID = "+schedule_id+" AND DateTrx = '"+req.get_Value("DateTrx")+"' ");
			if(scheduleday_id > 0)
			{
				X_MED_ScheduleTime time = new X_MED_ScheduleTime(getCtx(),0,get_TrxName());
				time.setMED_ScheduleDay_ID(scheduleday_id);
				time.setState("ND");
				//time.set_CustomColumn("M_Locator_ID", 0);
				time.setDescription("Hora creada para pabellon");
				time.setTimeRequested(req.getDateStart());
				
				time.save();
				
				X_MED_Appointment cita = new X_MED_Appointment(getCtx(), 0 ,get_TrxName());
				cita.setAD_Org_ID(req.getAD_Org_ID());
				cita.setState("RN");
				cita.setDescription("Hora Pabellon");
				//cita.setFECHARESERVA((Timestamp)dateField);
				//ininoles se cambia porque fecha de ventana java es modiifcable y crea horas erroneas 
				cita.setReservationDate(Env.getContextAsDate(Env.getCtx(), get_TrxName()));

				cita.setMED_Specialty_ID(specpabellon);
				/*cita.set_CustomColumn("Academia", (String)academia );
				cita.set_CustomColumn("ACargo", (String)acargo );
				cita.set_CustomColumn("Curso", (String)curso);
				cita.set_CustomColumn("JES_Tratante_ID", (Integer)tratante);
				*/
				//if((Integer)tipoHoraPick==1000000 || (Integer)tipoHoraPick==1000001 || (Integer)tipoHoraPick==1000006 || (Integer)tipoHoraPick==1000007 || (Integer)tipoHoraPick==1000008)
				cita.setC_BPartner_ID(req.getC_BPartner_ID());
				int tipoat = DB.getSQLValue(get_TrxName(), "SELECT coalesce(max(med_attentiontype_id),0) FROM MED_AttentionType WHERE " +
						" lower(name) like '%pabell%'");
				cita.setMED_AttentionType_ID(tipoat);					
				cita.save();
				cita.set_CustomColumn("MED_ScheduleTime_ID", time.getMED_ScheduleTime_ID());
				cita.setAttentionTime((Timestamp)time.getTimeRequested());
				log.config("1.Tratante ID = "+req.getC_BPartnerMed_ID());
				cita.set_CustomColumn("C_BPartnerMED_ID", req.getC_BPartnerMed_ID());
				
				cita.save();

				req.setDocStatus("CO");
				req.set_CustomColumn("MED_Appointment_ID", cita.get_ID());
				req.save();

			}
		}
		else
			return "Debe ingresar una cabecera horaria para el medico "+req.getC_BPartnerMed().getName()+" en el horario indicado";
		return "Realizado";
		
	}
	
}
