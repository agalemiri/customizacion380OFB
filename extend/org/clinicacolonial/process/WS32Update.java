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

import org.compiere.model.MBPartner;
import org.compiere.model.X_MED_Appointment;
import org.compiere.model.X_MED_Schedule;
import org.compiere.model.X_MED_ScheduleDay;
import org.compiere.model.X_MED_ScheduleTime;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 *	
 *	
 *  @author ininoles
 *  @version $Id: WS21GenSql2.java $
 */

public class WS32Update extends SvrProcess
{
	private Timestamp			p_ATime;
	private String				p_rutmedic;
	private String				p_name;
	private String				p_Rut;
	private String				p_Gender;
	private String				p_Mail;
	private String				p_PPhone;
	private String				p_Region;
	private String				p_Comuna;
	private String				p_Address;
	private String				p_ATimeStr;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if (name.equals("rutmedic"))
				p_rutmedic = para[i].getParameterAsString();
			else if (name.equals("AttentionTime"))
				p_ATime = para[i].getParameterAsTimestamp();
			else if (name.equals("Name"))
				p_name = para[i].getParameterAsString();
			else if (name.equals("Rut"))
				p_Rut = para[i].getParameterAsString();
			else if (name.equals("Gender"))
				p_Gender = para[i].getParameterAsString();
			else if (name.equals("Mail"))
				p_Mail = para[i].getParameterAsString();
			else if (name.equals("PatientPhone"))
				p_PPhone = para[i].getParameterAsString();
			else if (name.equals("region"))
				p_Region = para[i].getParameterAsString();
			else if (name.equals("comuna"))
				p_Comuna = para[i].getParameterAsString();
			else if (name.equals("Address1"))
				p_Address = para[i].getParameterAsString();
			else if (name.equals("attentiontimestr"))
				p_ATimeStr = para[i].getParameterAsString();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		String ret = "NO SE HA PODIDO AGENDAR HORA";

		if(p_rutmedic != null && p_ATime != null && p_Rut != null)
		{	
			log.config("Parametros: "+p_rutmedic+"-"+p_ATime+"-"+p_Rut);
			//se convierte cadena a fecha
			log.config("PARA 1: "+p_ATimeStr.substring(11, 13));
			log.config("PARA 1: "+p_ATimeStr.substring(14, 16));
			p_ATime.setHours(Integer.parseInt(p_ATimeStr.substring(11, 13)));
			p_ATime.setMinutes(Integer.parseInt(p_ATimeStr.substring(14, 16)));
			log.config("NUEVA FECHA: "+p_ATime);
			
			//se busca médico
			int ID_Med = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_BPartner_ID) "
					+ " FROM C_BPartner WHERE value||'-'||digito ='"+p_rutmedic+"'");
			
			MBPartner paci = null;
			
			//se actualiza o crea datos paciente
			int ID_Pac = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_BPartner_ID) "
					+ " FROM C_BPartner WHERE value||'-'||digito ='"+p_Rut+"'");			
			if(ID_Pac > 0)//existe paciente se actualiza
			{
				log.config("Existe paciente");
				paci = new MBPartner(getCtx(), ID_Pac, get_TrxName());
				if(p_name != null && p_name.trim().length() > 0)
					paci.setName(p_name);
				if(p_Gender != null && p_Gender.trim().length() > 0)
				{
					if(p_Gender.toUpperCase().compareTo("MASCULINO") == 0
							|| p_Gender.toUpperCase().compareTo("HOMBRE") == 0)
						paci.set_CustomColumn("Gender", "01");
					else if(p_Gender.toUpperCase().compareTo("FEMENINO") == 0
							|| p_Gender.toUpperCase().compareTo("MUJER") == 0)
						paci.set_CustomColumn("Gender", "02");
					else if(p_Gender.toUpperCase().compareTo("INDETERMINADO") == 0)
						paci.set_CustomColumn("Gender", "03");
					else if(p_Gender.toUpperCase().compareTo("DESCONOCIDO") == 0)
						paci.set_CustomColumn("Gender", "99");
				}
				if(p_Mail != null && p_Mail.trim().length() > 0)
					paci.set_CustomColumn("Mail", p_Mail);
				if(p_PPhone != null && p_PPhone.trim().length() > 0)
					paci.set_CustomColumn("PatientPhone", p_PPhone);
				if(p_Region != null && p_Region.trim().length() > 0)
				{
					int id_region = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_Region_ID) "
							+ " FROM C_Region WHERE upper(name) like '"+p_Region.toUpperCase()+"'");
					if(id_region > 0)
						paci.set_CustomColumn("C_Region_ID", id_region);
				}
				if(p_Comuna != null && p_Comuna.trim().length() > 0)
				{
					int id_comuna = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_City_ID) "
							+ " FROM C_City WHERE upper(name) like '"+p_Comuna.toUpperCase()+"'");
					if(id_comuna > 0)
						paci.set_CustomColumn("C_City_ID", id_comuna);
				}
				if(p_Address != null && p_Address.trim().length() > 0)
					paci.set_CustomColumn("Address1", p_Address);
				paci.saveEx(get_TrxName());
			}
			else//Se crea paciente
			{
				log.config("NO Existe paciente");
				paci = new MBPartner(getCtx(), 0, get_TrxName());
				if(p_Rut != null && p_Rut.trim().length() > 0 )
				{
					paci.setValue(p_Rut.substring(0,p_Rut.length()-2));
					paci.set_CustomColumn("digito",p_Rut.substring(p_Rut.length()-1,p_Rut.length()));
				}
				if(p_name != null && p_name.trim().length() > 0)
					paci.setName(p_name);
				if(p_Gender != null && p_Gender.trim().length() > 0)
				{
					if(p_Gender.toUpperCase().compareTo("MASCULINO") == 0
							|| p_Gender.toUpperCase().compareTo("HOMBRE") == 0)
						paci.set_CustomColumn("Gender", "01");
					else if(p_Gender.toUpperCase().compareTo("FEMENINO") == 0
							|| p_Gender.toUpperCase().compareTo("MUJER") == 0)
						paci.set_CustomColumn("Gender", "02");
					else if(p_Gender.toUpperCase().compareTo("INDETERMINADO") == 0)
						paci.set_CustomColumn("Gender", "03");
					else if(p_Gender.toUpperCase().compareTo("DESCONOCIDO") == 0)
						paci.set_CustomColumn("Gender", "99");
				}
				if(p_Mail != null && p_Mail.trim().length() > 0)
					paci.set_CustomColumn("Mail", p_Mail);
				if(p_PPhone != null && p_PPhone.trim().length() > 0)
					paci.set_CustomColumn("PatientPhone", p_PPhone);
				if(p_Region != null && p_Region.trim().length() > 0)
				{
					int id_region = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_Region_ID) "
							+ " FROM C_Region WHERE upper(name) like '"+p_Region.toUpperCase()+"'");
					if(id_region > 0)
						paci.set_CustomColumn("C_Region_ID", id_region);
				}
				if(p_Comuna != null && p_Comuna.trim().length() > 0)
				{
					int id_comuna = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_City_ID) "
							+ " FROM C_City WHERE upper(name) like '"+p_Comuna.toUpperCase()+"'");
					if(id_comuna > 0)
						paci.set_CustomColumn("C_City_ID", id_comuna);
				}
				if(p_Address != null && p_Address.trim().length() > 0)
					paci.set_CustomColumn("Address1", p_Address);
				paci.saveEx(get_TrxName());
			}
			//SE CREA CITA
			if(p_ATime != null && paci != null)
			{
				int ID_aTime = DB.getSQLValue(get_TrxName(), "SELECT MAX(MED_ScheduleTime_ID) "
						+ " FROM MED_ScheduleTime st"
						+ " INNER JOIN MED_ScheduleDay sd ON (st.MED_ScheduleDay_ID = sd.MED_ScheduleDay_ID)"
						+ " INNER JOIN MED_Schedule s ON (s.MED_Schedule_ID = sd.MED_Schedule_ID)"
						+ " WHERE C_BpartnerMed_ID = "+ID_Med+" AND TimeRequested = ? AND st.state = 'DI'", p_ATime);
				log.config("ID_Cita: "+ID_aTime);
				
				X_MED_ScheduleTime st = new X_MED_ScheduleTime(getCtx(), ID_aTime, get_TrxName());
				X_MED_ScheduleDay sd = new X_MED_ScheduleDay(getCtx(), st.getMED_ScheduleTime_ID(), get_TrxName());
				X_MED_Schedule sh = new X_MED_Schedule(getCtx(), sd.getMED_Schedule_ID(), get_TrxName());
				
				if(ID_aTime > 0)
				{
					//se actualiza estado de hora
					DB.executeUpdate("UPDATE MED_ScheduleTime SET state='RN' WHERE MED_ScheduleTime_ID = "+ID_aTime, get_TrxName());
					//se crea cita
					X_MED_Appointment med = new X_MED_Appointment(getCtx(), 0, get_TrxName());
					med.setReservationDate(p_ATime);
					if(sh.getMED_Specialty_ID() > 0)
						med.setMED_Specialty_ID(sh.getMED_Specialty_ID());
					med.setC_BPartner_ID(paci.getC_BPartner_ID());
					med.setC_BPartnerMed_ID(ID_Med);
					med.setMED_AttentionType_ID(sh.get_ValueAsInt("MED_AttentionType_ID"));
					med.setAttentionTime(p_ATime);
					if(med.save(get_TrxName()))
						ret = "HORA AGENDADA SATISFACTORIAMENTE";
				}
			}
		}	
		return ret;
	}	//	doIt
}
