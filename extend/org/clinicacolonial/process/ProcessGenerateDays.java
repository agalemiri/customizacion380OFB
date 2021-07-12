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
import org.compiere.process.SvrProcess;
import org.compiere.util.*;
import org.compiere.model.X_MED_Schedule;
import org.compiere.model.X_MED_ScheduleDay;
import org.compiere.model.X_MED_ScheduleTime;
import org.compiere.model.X_MED_Template;
import org.compiere.model.X_MED_TemplateDay;
/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessCCShiftChange.java $
 */

public class ProcessGenerateDays extends SvrProcess
{
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	private int 			m_Record_ID = 0;
	
	
	protected void prepare()
	{
		m_Record_ID = getRecord_ID();
		
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{	
		X_MED_Schedule horario = new X_MED_Schedule(Env.getCtx(),m_Record_ID,get_TrxName());
		if(horario.getMED_Template_ID()<=0)
			throw new IllegalArgumentException("El horario no posee plantilla");
		if(horario.getState().equals("CO"))
			throw new IllegalArgumentException("El horario ya se encuentra completo, no puede ser modificado");		
/*		if(buscarReserva(horario.getMED_Schedule_ID())>0)
			throw new IllegalArgumentException("No puede modificar el horario ya que existen horas con reservas");*/
		//asignar box
		if(horario.get_ValueAsInt("M_Locator_ID") <= 0)
			throw new IllegalArgumentException("Debe asociar un box de atencion");
		
		
		
		X_MED_TemplateDay[] plantillas = getPlantillas(horario.getMED_Template_ID() );
		
		Timestamp dia = horario.getDateFrom();
		int boxatencion = horario.get_ValueAsInt("M_Locator_ID");
		while(dia.compareTo(horario.getDateTo())<=0)
		{
			int pos = getPlantilla (plantillas, dia );
			if(pos<0)
			{
				dia=  TimeUtil.getNextDay(dia);
				continue;
			}
			
			X_MED_ScheduleDay newDia = new X_MED_ScheduleDay(Env.getCtx(),0,get_TrxName());
			newDia.setDay(plantillas[pos].getDays());
			newDia.setDateTrx(dia);
			newDia.setCycleA1(plantillas[pos].getCycleA1());
			newDia.setCycleA2(plantillas[pos].getCycleA2());
			newDia.setCycleB1(plantillas[pos].getCycleB1());
			newDia.setCycleB2(plantillas[pos].getCycleB2());
	/*		newDia.set_CustomColumn("CicloC1", plantillas[pos].get_Value("CicloC1"));
			newDia.set_CustomColumn("CicloC2", plantillas[pos].get_Value("CicloC2"));
			newDia.set_CustomColumn("CicloD1", plantillas[pos].get_Value("CicloD1"));
			newDia.set_CustomColumn("CicloD2", plantillas[pos].get_Value("CicloD2"));
			newDia.set_CustomColumn("CicloE1", plantillas[pos].get_Value("CicloE1"));
			newDia.set_CustomColumn("CicloE2", plantillas[pos].get_Value("CicloE2"));
			newDia.set_CustomColumn("CicloF1", plantillas[pos].get_Value("CicloF1"));
			newDia.set_CustomColumn("CicloF2", plantillas[pos].get_Value("CicloF2"));*/
			newDia.setMED_Schedule_ID(horario.getMED_Schedule_ID());
			newDia.setAD_Org_ID(newDia.getAD_Org_ID());
			newDia.save();			

			//ininoles Generacion de las horas de los dias			
			if(newDia.getCycleA1()!=null && newDia.getCycleA2()!=null)
			{
				Calendar a1= Calendar.getInstance();
				a1.setTimeInMillis(newDia.getCycleA1().getTime());
				
				Calendar a2= Calendar.getInstance();
				a2.setTimeInMillis(newDia.getCycleA2().getTime());
				
				Calendar desde1= Calendar.getInstance();
				desde1.setTimeInMillis(newDia.getDateTrx().getTime());
				desde1.set(Calendar.HOUR_OF_DAY, a1.get(Calendar.HOUR_OF_DAY));
				desde1.set(Calendar.MINUTE, a1.get(Calendar.MINUTE));
				
				Calendar hasta1= Calendar.getInstance();
				hasta1.setTimeInMillis(newDia.getDateTrx().getTime());
				hasta1.set(Calendar.HOUR_OF_DAY, a2.get(Calendar.HOUR_OF_DAY));
				hasta1.set(Calendar.MINUTE, a2.get(Calendar.MINUTE));
				
		
				while(desde1.compareTo(hasta1)<=0)
				{
					//Validacion inicial, no puede estar ocupado el box por otro doctor en uno de los mismos días a la misma hora
					//String sqlvalidate = "SELECT coalesce(max(c_bpartnermed_id),0) from med_scheduletime" +
					//		" where m_locator_id = "+boxatencion+" AND timerequested = '"+desde1.getTimeInMillis()+"' " +
					//				" AND c_bartnermed_id != "+horario.getC_BPartnerMed_ID();
					String sqlvalidate = "SELECT coalesce(max(c_bpartnermed_id),0) from med_schedule " +
							" WHERE c_bpartnermed_id != "+horario.getC_BPartnerMed_ID()+" AND med_schedule_id in (SELECT med_schedule_id from med_scheduleday where med_scheduleday_id in " +
							" (select med_scheduleday_id from med_scheduletime where timerequested = '"+new Timestamp(desde1.getTimeInMillis())+"' " +
									" AND m_locator_id = "+boxatencion+"))";
					log.config(sqlvalidate);
					int medinconflict = DB.getSQLValue(get_TrxName(), sqlvalidate);
					if( medinconflict > 0)
					{
						String bpartnername = DB.getSQLValueString(get_TrxName(), "SELECT name from c_bpartner where c_bpartner_id = ? ", medinconflict);
						throw new AdempiereException ("El doctor "+bpartnername+" utiliza el box solicitado en el horario "+new Timestamp(desde1.getTimeInMillis())+" ");
					}
					X_MED_ScheduleTime hora = new X_MED_ScheduleTime(Env.getCtx(),0,get_TrxName());
					hora.setState("DI");
					hora.setTimeRequested( new Timestamp (desde1.getTimeInMillis()));
					hora.setMED_Specialty_ID(horario.getMED_Specialty_ID());
					hora.setMED_ScheduleDay_ID(newDia.getMED_ScheduleDay_ID());
					hora.setAD_Org_ID(newDia.getAD_Org_ID());
					hora.set_CustomColumn("M_Locator_ID", boxatencion);
					hora.save();
					
					desde1.add(Calendar.MINUTE, horario.getminutes().intValue());
				}
			}
			
			if(newDia.getCycleB1()!=null && newDia.getCycleB2()!=null)
			{
				Calendar b1= Calendar.getInstance();
				b1.setTimeInMillis(newDia.getCycleB1().getTime());
				
				Calendar b2= Calendar.getInstance();
				b2.setTimeInMillis(newDia.getCycleB2().getTime());
				
				Calendar desde2= Calendar.getInstance();
				desde2.setTimeInMillis(newDia.getDateTrx().getTime());
				desde2.set(Calendar.HOUR_OF_DAY, b1.get(Calendar.HOUR_OF_DAY));
				desde2.set(Calendar.MINUTE, b1.get(Calendar.MINUTE));
				
				Calendar hasta2= Calendar.getInstance();
				hasta2.setTimeInMillis(newDia.getDateTrx().getTime());
				hasta2.set(Calendar.HOUR_OF_DAY, b2.get(Calendar.HOUR_OF_DAY));
				hasta2.set(Calendar.MINUTE, b2.get(Calendar.MINUTE));
				
				while(desde2.compareTo(hasta2)<=0)
				{
					//String sqlvalidate = "SELECT coalesce(max(c_bpartnermed_id),0) from med_scheduletime" +
					//		" where m_locator_id = "+boxatencion+" AND timerequested = '"+desde2.getTimeInMillis()+"' " +
					//				" AND c_bartnermed_id != "+horario.getC_BPartnerMed_ID();
					String sqlvalidate = "SELECT coalesce(max(c_bpartnermed_id),0) from med_schedule " +
							" WHERE c_bpartnermed_id != "+horario.getC_BPartnerMed_ID()+" AND med_schedule_id in (SELECT med_schedule_id from med_scheduleday where med_scheduleday_id in " +
							" (select med_scheduleday_id from med_scheduletime where timerequested = '"+new Timestamp(desde2.getTimeInMillis())+"' " +
									" AND m_locator_id = "+boxatencion+"))";
					int medinconflict = DB.getSQLValue(get_TrxName(), sqlvalidate);
					if( medinconflict > 0)
					{
						String bpartnername = DB.getSQLValueString(get_TrxName(), "SELECT name from c_bpartner where c_bpartner_id = ? ", medinconflict);
						throw new AdempiereException ("El doctor "+bpartnername+" utiliza el box solicitado en el horario "+new Timestamp(desde2.getTimeInMillis())+" ");
					}
					X_MED_ScheduleTime hora = new X_MED_ScheduleTime(Env.getCtx(),0,get_TrxName());
					hora.setState("DI");
					hora.setTimeRequested( new Timestamp (desde2.getTimeInMillis()));
					hora.setMED_Specialty_ID(horario.getMED_Specialty_ID());
					hora.setMED_ScheduleDay_ID(newDia.getMED_ScheduleDay_ID());
					hora.setAD_Org_ID(newDia.getAD_Org_ID());
					hora.set_CustomColumn("M_Locator_ID", boxatencion);

					hora.save();
					
					desde2.add(Calendar.MINUTE, horario.getminutes().intValue());
				}
			}
			
/*			if(newDia.get_Value("CycleC1")!=null && newDia.get_Value("CicloC2")!=null)
			{
				Calendar b1= Calendar.getInstance();
				b1.setTimeInMillis(((Timestamp)newDia.get_Value("CicloC1")).getTime());
				
				Calendar b2= Calendar.getInstance();
				b2.setTimeInMillis(((Timestamp)newDia.get_Value("CicloC2")).getTime());
				
				Calendar desde2= Calendar.getInstance();
				desde2.setTimeInMillis(newDia.getFecha().getTime());
				desde2.set(Calendar.HOUR_OF_DAY, b1.get(Calendar.HOUR_OF_DAY));
				desde2.set(Calendar.MINUTE, b1.get(Calendar.MINUTE));
				
				Calendar hasta2= Calendar.getInstance();
				hasta2.setTimeInMillis(newDia.getFecha().getTime());
				hasta2.set(Calendar.HOUR_OF_DAY, b2.get(Calendar.HOUR_OF_DAY));
				hasta2.set(Calendar.MINUTE, b2.get(Calendar.MINUTE));
				
				while(desde2.compareTo(hasta2)<=0)
				{
					X_MED_ScheduleTime hora = new X_MED_ScheduleTime(Env.getCtx(),0,get_TrxName());
					hora.setEstado(X_MED_ScheduleDay.ESTADO_Disponible);
					hora.setTimeRequested( new Timestamp (desde2.getTimeInMillis()));
					hora.setMED_Specialty_ID(horario.getMED_Specialty_ID());
					hora.setMED_ScheduleDay_ID(newDia.getMED_ScheduleDay_ID());
					hora.setAD_Org_ID(newDia.getAD_Org_ID());
					hora.save();
					
					desde2.add(Calendar.MINUTE, horario.getMINUTOS().intValue());
				}
			}
			
			if(newDia.get_Value("CicloD1")!=null && newDia.get_Value("CicloD2")!=null)
			{
				Calendar b1= Calendar.getInstance();
				b1.setTimeInMillis(((Timestamp)newDia.get_Value("CicloD1")).getTime());
				
				Calendar b2= Calendar.getInstance();
				b2.setTimeInMillis(((Timestamp)newDia.get_Value("CicloD2")).getTime());
				
				Calendar desde2= Calendar.getInstance();
				desde2.setTimeInMillis(newDia.getFecha().getTime());
				desde2.set(Calendar.HOUR_OF_DAY, b1.get(Calendar.HOUR_OF_DAY));
				desde2.set(Calendar.MINUTE, b1.get(Calendar.MINUTE));
				
				Calendar hasta2= Calendar.getInstance();
				hasta2.setTimeInMillis(newDia.getFecha().getTime());
				hasta2.set(Calendar.HOUR_OF_DAY, b2.get(Calendar.HOUR_OF_DAY));
				hasta2.set(Calendar.MINUTE, b2.get(Calendar.MINUTE));
				
				while(desde2.compareTo(hasta2)<=0)
				{
					X_MED_ScheduleTime hora = new X_MED_ScheduleTime(Env.getCtx(),0,get_TrxName());
					hora.setEstado(X_MED_ScheduleTime.ESTADO_Disponible);
					hora.setTimeRequested( new Timestamp (desde2.getTimeInMillis()));
					hora.setMED_Specialty_ID(horario.getMED_Specialty_ID());
					hora.setMED_ScheduleDay_ID(newDia.getMED_ScheduleDay_ID());
					hora.setAD_Org_ID(newDia.getAD_Org_ID());
					hora.save();
					
					desde2.add(Calendar.MINUTE, horario.getMINUTOS().intValue());
				}
			}
			
			if(newDia.get_Value("CicloE1")!=null && newDia.get_Value("CicloE2")!=null)
			{
				Calendar b1= Calendar.getInstance();
				b1.setTimeInMillis(((Timestamp)newDia.get_Value("CicloE1")).getTime());
				
				Calendar b2= Calendar.getInstance();
				b2.setTimeInMillis(((Timestamp)newDia.get_Value("CicloE2")).getTime());
				
				Calendar desde2= Calendar.getInstance();
				desde2.setTimeInMillis(newDia.getFecha().getTime());
				desde2.set(Calendar.HOUR_OF_DAY, b1.get(Calendar.HOUR_OF_DAY));
				desde2.set(Calendar.MINUTE, b1.get(Calendar.MINUTE));
				
				Calendar hasta2= Calendar.getInstance();
				hasta2.setTimeInMillis(newDia.getFecha().getTime());
				hasta2.set(Calendar.HOUR_OF_DAY, b2.get(Calendar.HOUR_OF_DAY));
				hasta2.set(Calendar.MINUTE, b2.get(Calendar.MINUTE));
				
				while(desde2.compareTo(hasta2)<=0)
				{
					X_MED_ScheduleTime hora = new X_MED_ScheduleTime(Env.getCtx(),0,get_TrxName());
					hora.setEstado(X_MED_ScheduleTime.ESTADO_Disponible);
					hora.setTimeRequested( new Timestamp (desde2.getTimeInMillis()));
					hora.setMED_Specialty_ID(horario.getMED_Specialty_ID());
					hora.setMED_ScheduleDay_ID(newDia.getMED_ScheduleDay_ID());
					hora.setAD_Org_ID(newDia.getAD_Org_ID());
					hora.save();
					
					desde2.add(Calendar.MINUTE, horario.getMINUTOS().intValue());
				}
			}
			
			if(newDia.get_Value("CicloF1")!=null && newDia.get_Value("CicloF2")!=null)
			{
				Calendar b1= Calendar.getInstance();
				b1.setTimeInMillis(((Timestamp)newDia.get_Value("CicloF1")).getTime());
				
				Calendar b2= Calendar.getInstance();
				b2.setTimeInMillis(((Timestamp)newDia.get_Value("CicloF2")).getTime());
				
				Calendar desde2= Calendar.getInstance();
				desde2.setTimeInMillis(newDia.getFecha().getTime());
				desde2.set(Calendar.HOUR_OF_DAY, b1.get(Calendar.HOUR_OF_DAY));
				desde2.set(Calendar.MINUTE, b1.get(Calendar.MINUTE));
				
				Calendar hasta2= Calendar.getInstance();
				hasta2.setTimeInMillis(newDia.getFecha().getTime());
				hasta2.set(Calendar.HOUR_OF_DAY, b2.get(Calendar.HOUR_OF_DAY));
				hasta2.set(Calendar.MINUTE, b2.get(Calendar.MINUTE));
				
				while(desde2.compareTo(hasta2)<=0)
				{
					X_MED_ScheduleTime hora = new X_MED_ScheduleTime(Env.getCtx(),0,get_TrxName());
					hora.setEstado(X_MED_ScheduleTime.ESTADO_Disponible);
					hora.setTimeRequested( new Timestamp (desde2.getTimeInMillis()));
					hora.setMED_Specialty_ID(horario.getMED_Specialty_ID());
					hora.setMED_ScheduleDay_ID(newDia.getMED_ScheduleDay_ID());
					hora.setAD_Org_ID(newDia.getAD_Org_ID());
					hora.save();
					
					desde2.add(Calendar.MINUTE, horario.getMINUTOS().intValue());
				}
			}	
			*/
			//end ininoles
				 
			 dia=  TimeUtil.getNextDay(dia);
		}
			return "Dias Creados";
	}
	
	private int  getPlantilla (X_MED_TemplateDay[] plantillas, Timestamp dia )
	{
		int pos= -1;
		 Calendar cal= Calendar.getInstance();
		 cal.setTimeInMillis(dia.getTime());
		 String  numeroDia= "0"+(cal.get(Calendar.DAY_OF_WEEK)-1);
		 
		 for(int i =0 ; i<plantillas.length;i++)
		 {
			 if(numeroDia.equals(plantillas[i].getDays()))
				 pos=i;
		 }
		 
		 return pos;
		
	}
	
	private X_MED_TemplateDay[] getPlantillas(int plantilla_ID)
	{
		PreparedStatement pstmt = null;
		ArrayList<X_MED_TemplateDay> lista = new ArrayList<X_MED_TemplateDay>();
		  String mysql="select * from MED_TemplateDay where MED_Template_ID=?";
		   try
			{
				pstmt = DB.prepareStatement(mysql, get_TrxName());
				pstmt.setInt(1, plantilla_ID);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next())
				{
					X_MED_TemplateDay dia = new X_MED_TemplateDay(Env.getCtx(),rs,get_TrxName());
					lista.add(dia);
				}
				rs.close();
				pstmt.close();
				pstmt = null;
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, e.getMessage(), e);
			}
		   
		   return lista.toArray(new X_MED_TemplateDay[lista.size()]);		
	}
	
	
/*	private int buscarReserva(int horario_ID)
	{
		String sql ="select count(1) from JES_CitaHora c" +
				" Inner Join JES_Hora h on (c.JES_Hora_ID=h.JES_Hora_ID) " +
				" Inner Join JES_HorarioDia hd on (h.JES_HorarioDia_ID=hd.JES_HorarioDia_ID)" +
				" Where hd.JES_Horario_ID=?";
		
		return DB.getSQLValue(get_TrxName(), sql, horario_ID);
	}*/
}
