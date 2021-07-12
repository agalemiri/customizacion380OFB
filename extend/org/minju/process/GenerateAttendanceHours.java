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
package org.minju.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.X_HR_Process;
import org.compiere.model.MPeriod;
import org.compiere.model.MRequisition;
import org.compiere.model.X_HR_AttendanceHours;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: GenerateAttendanceHours.java $
 */
public class GenerateAttendanceHours extends SvrProcess
{
	
	private int				p_Period = 0; 
	private int 			p_BPartner = 0;
	private Timestamp  		p_From;
	private Timestamp		p_To;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		 	ProcessInfoParameter[] para = getParameter();
			for (int i = 0; i < para.length; i++)
			{
				String name = para[i].getParameterName();
				
				if (name.equals("DateTo"))
					p_To = ((Timestamp)para[i].getParameter());
				else if (name.equals("C_BPartner_ID"))
					p_BPartner = ((BigDecimal)para[i].getParameter()).intValue();
				else if(name.equals("DateFrom"))
					p_From = ((Timestamp)para[i].getParameter());
				else
					log.log(Level.SEVERE, "Unknown Parameter: " + name);
			}
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	 //El proceso borrara los registros en hr_attendancehours y luego reescribira los datos.
	protected String doIt() throws Exception
	{
		if (p_From != null && p_To != null)
		{
			MPeriod period = new MPeriod (getCtx(), p_Period, get_TrxName());

			String sql = "DELETE from HR_AttendanceHours WHERE datetrx " +
					" between '"+p_From+"' AND '"+p_To+"'";
			if(p_BPartner > 0)
				sql = sql + " AND c_bpartner_id = "+p_BPartner;
			
			log.config("SQL DELETE "+sql);
			
			DB.executeUpdate(sql,get_TrxName());
			
			String sqlgethours = "SELECT fecha, ad_org_id, c_bpartner_id, dia, entrada, salida, horas_trabajadas, " +
					" incidencias, compensadas, atrasobase, horasmediamannana FROM RVOFB_Reloj_Control WHERE fecha " +
					" between '"+p_From+"' AND '"+p_To+"'";
			if(p_BPartner > 0)
				sqlgethours = sqlgethours + " AND c_bpartner_id = "+p_BPartner;
			
			
			log.config("SQL obtener desde vista "+sqlgethours);
			PreparedStatement pstmt = DB.prepareStatement(sqlgethours, get_TrxName());
			ResultSet rs = pstmt.executeQuery();			
			
			while(rs.next())
			{
				X_HR_AttendanceHours att = new X_HR_AttendanceHours(getCtx(),0,get_TrxName());
				int hoursqty = rs.getInt("compensadas");
				Date actualdate = (java.util.Date)rs.getTimestamp("fecha");
				Timestamp time = new Timestamp(hoursqty);
				Date entryhour= new Date(rs.getTimestamp("entrada").getTime());
				Timestamp exithour = rs.getTimestamp("salida");
				Date compensadas = calculateHours(actualdate, hoursqty,0);		
				Timestamp cff = new Timestamp(compensadas.getTime());	
				String incidencias = rs.getString("incidencias");
				
				log.config("entry "+rs.getTimestamp("entrada"));
				att.setEntryHour(new Timestamp(entryhour.getTime()));
				//Validacion de entrada
	/*			String sqllog = "SELECT min(datetrx) as datetrx from hr_Attendanceline where c_bpartner_id = "+p_BPartner+" AND fecha between" +
						" '"+p_From+"' AND '"+p_To+"'";
				PreparedStatement pstmtlog = DB.prepareStatement(sqllog, get_TrxName());
				ResultSet rslog = pstmtlog.executeQuery();
				log.config("entrada "+rslog.next());
				//Validacion cierre
	*/			att.setExitHour(exithour);
				att.setDateTrx(rs.getTimestamp("fecha"));
				att.setAD_Org_ID(rs.getInt("ad_org_id"));
				att.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				att.setDayOfWeek(rs.getString("dia"));
				att.setDescription(incidencias);
				//att.setWorkedHoursCalc(rs.getTimestamp("horas_trabajadas"));
				//att.setCompensatedTime(cff);
				att.setDelayBase(rs.getTimestamp("atrasobase"));
				
				att.saveEx(get_TrxName());
				//Ahora que los campos iniciales ya han sido agregados, lo que falta es que se comience a hacer
				//cada uno de los calculos en los campos.
				
				//Horas laborales base, horas media mañana, fin media mañana, inicio salida anticipada base

				Date workinghours;
				Date midmorninghours;
				Date earlyexitbase;
				Date maxearlyexit;
				Date endmidmorning;
				String fecha = rs.getString("dia");
				
				if(fecha.compareToIgnoreCase("Domingo") == 0 || fecha.endsWith("bado"))
				{
					workinghours = calculateHours(actualdate, 0,0);
					midmorninghours = calculateHours(actualdate,0,0);
					earlyexitbase = calculateHours(actualdate,0,0);
					maxearlyexit = calculateHours(actualdate,0,0);
					endmidmorning = calculateHours(actualdate,0,0);
				}
				else if(fecha.compareToIgnoreCase("Viernes") == 0)
				{
					workinghours = calculateHours(actualdate,9,0);
					midmorninghours = calculateHours(actualdate,5,0);
					earlyexitbase = calculateHours(actualdate,18,30);
					maxearlyexit = calculateHours(actualdate,17,0);
					endmidmorning = calculateHours(actualdate,14,30);
				}
				else
				{
					workinghours = calculateHours(actualdate,10,0);
					midmorninghours = calculateHours(actualdate,5,30);
					earlyexitbase = calculateHours(actualdate,19,30);
					maxearlyexit = calculateHours(actualdate,18,0);
					endmidmorning = new Timestamp (calculateHours(actualdate,15,0).getTime());	
				}
				
				
				log.config("working hours "+workinghours);
				log.config("midmorninghours "+midmorninghours);
				log.config("earlyexitbase "+earlyexitbase);
				log.config("working hours "+new Timestamp(calculateHours(actualdate,14,0).getTime()));
				log.config("maearlyexit "+maxearlyexit);
				//endmidmorning = new Timestamp (calculateHours(actualdate,15,0).getTime());
				
				att.setWorkingHours(new Timestamp(workinghours.getTime()));
				att.setMidMorningHours(new Timestamp(midmorninghours.getTime()));
				att.setEndMidMorning(new Timestamp(endmidmorning.getTime()));
				att.setEarlyExitBase(new Timestamp(earlyexitbase.getTime()));
				att.setMaxEarlyExit(new Timestamp(maxearlyexit.getTime()));
				
				
				
				//nuevas columnas de horas y jornadas especiales.
				
				Date specialdayentry;
				Date specialdayexit;
				int hoursamqty = 0;
				int hourspmqty = 0;
				int compensatedhours = 0;
				
				//Primero se deberá analizar las incidencias.
				//Ver si tiene permiso de horas compensatorias (de mañana)
				
				if(incidencias.contains("COM(MA)"))
				{
					/*String sqlam = "SELECT coalesce(sum(hours),0) from hr_administrativerequestsl where date01 = '"+actualdate+"' and " +
							" hr_administrativerequests_id in (select hr_Administrativerequests_id from hr_Administrativerequests " +
							" where requesttype like 'COM' and c_bpartner_id = ? " +
							" AND comments like '%AM%') and isactive='Y'";*/
					String sqlam = "SELECT coalesce(sum(hours),0) from hr_administrativerequestsl where date01 = '"+actualdate+"' and " +
							" AMPM like '%AM%' AND hr_administrativerequests_id in (select hr_Administrativerequests_id from hr_Administrativerequests " +
							" where requesttype like 'COM' and c_bpartner_id = ? " +
							" ) and isactive='Y'";
							
					BigDecimal hoursam = DB.getSQLValueBD(get_TrxName(), sqlam, rs.getInt("C_BPartner_ID"));
					hoursamqty = hoursamqty + hoursam.intValue();
					compensatedhours = compensatedhours+hoursam.intValue();
				}
				
				if(incidencias.contains("COM(TA)"))
				{	
					/*String sqlpm = "SELECT coalesce(sum(hours),0) from hr_administrativerequestsl where date01 = '"+actualdate+"' and " +
							" hr_administrativerequests_id in (select hr_Administrativerequests_id from hr_Administrativerequests " +
							" where requesttype like 'COM' and c_bpartner_id = ? " +
							" AND comments like '%PM%') and isactive='Y'";
 */
					String sqlpm = "SELECT coalesce(sum(hours),0) from hr_administrativerequestsl where date01 = '"+actualdate+"' and " +
							" AMPM like '%PM%' AND hr_administrativerequests_id in (select hr_Administrativerequests_id from hr_Administrativerequests " +
							" where requesttype like 'COM' and c_bpartner_id = ? " +
							" ) and isactive='Y'";
					BigDecimal hourspm = DB.getSQLValueBD(get_TrxName(), sqlpm, rs.getInt("C_BPartner_ID"));
					//int hourspm = DB.getSQLValue(get_TrxName(), sqlpm);
					log.config("hours pm config "+hourspm);
					hourspmqty = hourspmqty + hourspm.intValue();
					//hourspmqty = hourspmqty + hourspm;
					compensatedhours = compensatedhours+hourspm.intValue();


				
				}
				if(incidencias.contains("COM(DC)"))
				{	
					/*String sqlpm = "SELECT coalesce(sum(hours),0) from hr_administrativerequestsl where date01 = '"+actualdate+"' and " +
							" hr_administrativerequests_id in (select hr_Administrativerequests_id from hr_Administrativerequests " +
							" where requesttype like 'COM' and c_bpartner_id = ? " +
							" AND comments like '%PM%') and isactive='Y'";
 */
					String sqlpm = "SELECT coalesce(sum(hours),0) from hr_administrativerequestsl where date01 = '"+actualdate+"' and " +
							" AMPM like '%EN%' AND hr_administrativerequests_id in (select hr_Administrativerequests_id from hr_Administrativerequests " +
							" where requesttype like 'COM' and c_bpartner_id = ? " +
							" ) and isactive='Y'";
					BigDecimal hourspm = DB.getSQLValueBD(get_TrxName(), sqlpm, rs.getInt("C_BPartner_ID"));
					//int hourspm = DB.getSQLValue(get_TrxName(), sqlpm);
					log.config("hours pm config "+hourspm);
					hourspmqty = hourspmqty + hourspm.intValue();
					//hourspmqty = hourspmqty + hourspm;
					compensatedhours = compensatedhours+hourspm.intValue();
				}
				Date compensatedhoursdate = calculateHours(actualdate, compensatedhours+1,0);		
				att.setCompensatedTime(new Timestamp(compensatedhoursdate.getTime()));
				
				if(incidencias.contains("DA(AM)"))
				{
					/*String sqlamda = "SELECT coalesce(sum(hours),0) from hr_administrativerequestsl where datestartrequest = '"+actualdate+"' and " +
							" hr_administrativerequests_id in (select hr_Administrativerequests_id from hr_Administrativerequests " +
							" where requesttype like 'DAA' and c_bpartner_id = ? " +
							" AND comments like '%AM%') and isactive='Y'";
					*/
					String sqlamda = "SELECT coalesce(sum(hours),0) from hr_administrativerequestsl where datestartrequest = '"+actualdate+"' and " +
							" AMPM like '%AM%' AND hr_administrativerequests_id in (select hr_Administrativerequests_id from hr_Administrativerequests " +
							" where requesttype like 'DAA' and c_bpartner_id = ? " +
							" ) and isactive='Y'";
					BigDecimal hoursamda = DB.getSQLValueBD(get_TrxName(), sqlamda, rs.getInt("C_BPartner_ID"));
					hoursamqty = hoursamqty + hoursamda.intValue();

					
				}
				if(incidencias.contains("DA(PM)"))
				{
					/*String sqlpmda = "SELECT coalesce(sum(hours),0) from hr_administrativerequestsl where datestartrequest = '"+actualdate+"' and " +
							" hr_administrativerequests_id in (select hr_Administrativerequests_id from hr_Administrativerequests " +
							" where requesttype like 'DAA' and c_bpartner_id = ? " +
							" AND comments like '%PM%') and isactive='Y'";*/
					String sqlpmda = "SELECT coalesce(sum(hours),0) from hr_administrativerequestsl where datestartrequest = '"+actualdate+"' and " +
							" AMPM like '%PM%' AND hr_administrativerequests_id in (select hr_Administrativerequests_id from hr_Administrativerequests " +
							" where requesttype like 'DAA' and c_bpartner_id = ? " +
							" ) and isactive='Y'";
					BigDecimal hourspmda = DB.getSQLValueBD(get_TrxName(), sqlpmda, rs.getInt("C_BPartner_ID"));
					hourspmqty = hourspmqty + hourspmda.intValue();

				}
				if(incidencias.contains("CAP(AM)"))
				{
					/*String sqlamcap = "SELECT coalesce(sum(hours),0) from hr_administrativerequestsl where datestartrequest = '"+actualdate+"' and " +
							" hr_administrativerequests_id in (select hr_Administrativerequests_id from hr_Administrativerequests " +
							" where requesttype like 'CTN' and c_bpartner_id = ? " +
							" AND comments like '%AM%') and isactive='Y'";
					*/
					
					//mfrojas 20180806
					//Se comenta sección de código, pues 
					/*String sqlamcap = "SELECT coalesce(sum(hours),0) from hr_administrativerequestsl where datestartrequest = '"+actualdate+"' and " +
							" AMPM like '%AM%' AND hr_administrativerequests_id in (select hr_Administrativerequests_id from hr_Administrativerequests " +
							" where requesttype like 'CTN' and c_bpartner_id = ? " +
							" ) and isactive='Y'";
					BigDecimal hoursamcap = DB.getSQLValueBD(get_TrxName(), sqlamcap, rs.getInt("C_BPartner_ID"));
					hoursamqty = hoursamqty + hoursamcap.intValue();
					*/
					
				}
				if(incidencias.contains("CAP(PM)"))
				{
					/*String sqlpmcap = "SELECT coalesce(sum(hours),0) from hr_administrativerequestsl where datestartrequest = '"+actualdate+"' and " +
							" hr_administrativerequests_id in (select hr_Administrativerequests_id from hr_Administrativerequests " +
							" where requesttype like 'CTN' and c_bpartner_id = ? " +
							" AND comments like '%P	M%') and isactive='Y'";
					*/
					/*String sqlpmcap = "SELECT coalesce(sum(hours),0) from hr_administrativerequestsl where datestartrequest = '"+actualdate+"' and " +
							" AMPM like '%PM%' AND hr_administrativerequests_id in (select hr_Administrativerequests_id from hr_Administrativerequests " +
							" where requesttype like 'CTN' and c_bpartner_id = ? " +
							" ) and isactive='Y'";
					BigDecimal hourspmcap = DB.getSQLValueBD(get_TrxName(), sqlpmcap, rs.getInt("C_BPartner_ID"));
					hourspmqty = hourspmqty + hourspmcap.intValue();
					*/
				}
				
				specialdayentry = calculateHours(actualdate, hoursamqty+1,0);
				specialdayexit = calculateHours(actualdate,hourspmqty+1,0);
				
				att.setSpecialDayEntry(new Timestamp(specialdayentry.getTime()));
				att.setSpecialDayExit(new Timestamp(specialdayexit.getTime()));
				//Timestamp cff = new Timestamp(compensadas.getTime());	

				//specialdayentry
				
				
				
				//horas laborales para calculo
				
				Date workedhoursforcalc;
				if(incidencias.contains("PA(MA)") || incidencias.contains("PA(TA)"))
				{
					//workedhoursforcalc = new Date(midmorninghours.getTime());
					//att.setWorkedHoursForCalc(new Timestamp(midmorninghours.getTime()));
					//mfrojas 20180723 cambio en formula
					if((workinghours.getTime() - specialdayentry.getTime() + actualdate.getTime() - specialdayexit.getTime() + actualdate.getTime())< actualdate.getTime())
						workedhoursforcalc = calculateHours(workinghours,0,0);
					else
					{
						workedhoursforcalc = new Date(workinghours.getTime() - specialdayentry.getTime() + actualdate.getTime() - specialdayexit.getTime() + actualdate.getTime());
						workedhoursforcalc = new Date(workedhoursforcalc.getTime() - calculateHours(actualdate,5,0).getTime() + actualdate.getTime());
					}
				}
				else
				{	//workedhoursforcalc = calculateHours(workinghours,hoursqty,0);
					//att.setWorkedHoursForCalc(new Timestamp(calculateHours(workinghours,hoursqty,0).getTime()));
					if((workinghours.getTime() - specialdayentry.getTime() + actualdate.getTime() - specialdayexit.getTime() + actualdate.getTime())< actualdate.getTime())
						workedhoursforcalc = calculateHours(workinghours,0,0);
					else
						workedhoursforcalc = new Date(workinghours.getTime() - specialdayentry.getTime() + actualdate.getTime() - specialdayexit.getTime() + actualdate.getTime());
						//workedhoursforcalc = calculateHours(workinghours,hoursamqty+hourspmqty+1,0);
				}
				att.setWorkedHoursForCalc(new Timestamp(workedhoursforcalc.getTime()));
				log.config("bla "+hoursqty);
				
				//calculo horas trabajadas
				
				Date workedhourscalc;
				workedhourscalc = new Date(exithour.getTime() - entryhour.getTime());
				log.config("entry hour"+entryhour);
				log.config("entryhourtime"+entryhour.getTime());
				log.config("worked hours calc "+workedhourscalc);
				
				//llevado a año actual
				workedhourscalc = new Date (workedhourscalc.getTime() + actualdate.getTime());
				log.config("workedhourscalc updated "+workedhourscalc);
				att.setWorkedHoursCalc(new Timestamp(workedhourscalc.getTime()));
				
				//inicio atraso calculado
				
				Timestamp atrasocalculado;
				Date delaybegin;
				if(fecha.compareToIgnoreCase("Domingo") == 0 || fecha.endsWith("bado"))
				{
					delaybegin = calculateHours(actualdate,23,59);
					atrasocalculado = new Timestamp(calculateHours(actualdate,23,59).getTime());
				}
				else
				{
					if(incidencias.contains("PA(MA)"))
					{
						//20180719 mfrojas cambio. Se iguala a endmidmorning el delaybegin.
						//delaybegin = calculateHours(actualdate,14,0);
						delaybegin = endmidmorning;
						atrasocalculado = new Timestamp(calculateHours(actualdate,14,0).getTime());
					}
						
					else
					{
						//delaybegin = new Date(rs.getTimestamp("atrasobase").getTime());
						delaybegin = new Date(rs.getTimestamp("atrasobase").getTime() + specialdayentry.getTime() - actualdate.getTime());
						atrasocalculado = rs.getTimestamp("atrasobase");
					}
						//att.setDelayBegin(rs.getTimestamp("atraso_base"));
					
				}
				att.setDelayBegin(new Timestamp(delaybegin.getTime()));


				//horas media mañana
				
				//att.setMidMorningHours(rs.getTimestamp("horasmediamannana"));
				
				
				//calculo atraso 
				
				Date delaycalc;
				Date entryhourdate = new Date(entryhour.getTime());
				Date atrasocalculadodate = new Date(atrasocalculado.getTime());
				
				
				if(entryhour.compareTo(delaybegin)>0)
					if(hoursqty > 0)//revisar
						if((entryhour.getTime() - delaybegin.getTime() - cff.getTime()) + actualdate.getTime() + actualdate.getTime() < actualdate.getTime())
							delaycalc = calculateHours(actualdate,0,0);
						else
							delaycalc = new Date(entryhour.getTime() - delaybegin.getTime() - cff.getTime() + actualdate.getTime() + actualdate.getTime());
					else
						delaycalc = new Date(entryhour.getTime() - delaybegin.getTime() + actualdate.getTime());
				else
					delaycalc = calculateHours(actualdate,0,0);
				
				att.setDelayCalc(new Timestamp(delaycalc.getTime()));
				
				//pre calculo salida
				
				Date precalcexit;
				
				if(entryhour.compareTo(delaybegin)<0)
				{
					precalcexit = new Date (entryhour.getTime() + workedhoursforcalc.getTime());
					precalcexit = new Date (precalcexit.getTime() - actualdate.getTime());
				}
				else
					precalcexit = earlyexitbase;
				
				att.setPreCalcExit(new Timestamp(precalcexit.getTime()));
				
				
				//Calculo Salida
				
				Date calcexit = new Date();
				Date calcexitaux;
				calcexitaux = new Date(entryhour.getTime() + workedhoursforcalc.getTime());
				log.config("calcexitaux "+calcexitaux);
				calcexitaux = new Date(calcexitaux.getTime() - actualdate.getTime());

				if(entryhour.compareTo(delaybegin)<0)
				{
					log.config("calcexitaux "+calcexitaux);

					if(calcexitaux.compareTo(maxearlyexit)<0)
					{
						log.config("calcexitaux "+calcexitaux);
						calcexit = maxearlyexit;
					}
					else
					{	
						//calcexitaux = new Date(calcexitaux.getTime() - actualdate.getTime());
						calcexit = calcexitaux;
					}
				}
				else
				{
					log.config("calcexitaux "+calcexitaux);
					calcexit = earlyexitbase;
				}
				
				att.setCalcExit(new Timestamp(calcexit.getTime()));
				
				//inicio salida anticipada
				
				Date earlyexit;
				
				if(incidencias.contains("PA(TA)"))
				{
					//earlyexit = endmidmorning;
					//mfrojas 20180723 cambio en formula de calculo
					earlyexit = new Date(calcexit.getTime() - specialdayexit.getTime() - calculateHours(actualdate,5,0).getTime());
					earlyexit = new Date(earlyexit.getTime() + actualdate.getTime() + actualdate.getTime());
				}
				else
				{
					earlyexit = new Date(calcexit.getTime() - specialdayexit.getTime());
					earlyexit = new Date(earlyexit.getTime() + actualdate.getTime());
				}
				
				att.setEarlyExit(new Timestamp(earlyexit.getTime()));
				
				
				//Precalculo salida 1
				
				Date precalcexit1;
				Date precalcaux;
				/*if(exithour.getTime() > actualdate.getTime())
					if(entryhour.compareTo(earlyexit) <= 0)
						if(compensadas.getTime() > actualdate.getTime())
						{
							precalcaux = new Date(earlyexit.getTime() - exithour.getTime() - compensadas.getTime());
							precalcaux = new Date(precalcaux.getTime() + actualdate.getTime() + actualdate.getTime());
							if(precalcaux.getTime() < actualdate.getTime())
								precalcexit1 = calculateHours(actualdate,0,0);
							else
								precalcexit1 = precalcaux;
						}
						else
						{
							precalcexit1 = new Date(earlyexit.getTime() - exithour.getTime());
							precalcexit1 = new Date(precalcexit1.getTime() + actualdate.getTime());
						}
					else
						precalcexit1 = calculateHours(actualdate,0,0);
				else
					precalcexit1 = calculateHours(actualdate,0,0);
				*/

				if(exithour.getTime() > actualdate.getTime())
					if(exithour.compareTo(earlyexit) < 0)
						if(compensadas.getTime() > actualdate.getTime())
						{
							precalcaux = new Date(earlyexit.getTime() - exithour.getTime() - compensadas.getTime());
							precalcaux = new Date(precalcaux.getTime() + actualdate.getTime() + actualdate.getTime());
							if(precalcaux.getTime() < actualdate.getTime())
								precalcexit1 = calculateHours(actualdate,0,0);
							else
								precalcexit1 = precalcaux;
						}
						else
						{
							precalcexit1 = new Date(earlyexit.getTime() - exithour.getTime());
							precalcexit1 = new Date(precalcexit1.getTime() + actualdate.getTime());
						}
					else
						precalcexit1 = calculateHours(actualdate,0,0);
				else
					precalcexit1 = calculateHours(actualdate,0,0);
				
				att.setPreCalcExit1(new Timestamp(precalcexit1.getTime()));
				
				//pracalculo salida 2
				
				Date precalcexit2;
				
				if(entryhour.getTime() > actualdate.getTime())
					if(delaybegin.compareTo(entryhour) > 0)
					{
						precalcexit2 = new Date(delaybegin.getTime() - entryhour.getTime());
						precalcexit2 = new Date(precalcexit2.getTime() + actualdate.getTime());
					}
					else
						precalcexit2 = calculateHours(actualdate,0,0);
				else
					precalcexit2 = calculateHours(actualdate,0,0);
				
				att.setPreCalcExit2(new Timestamp(precalcexit2.getTime()));
				
				
				//Calculo salida anticipada
				
				Date exitcalc;
				
				if(fecha.compareToIgnoreCase("Domingo") == 0 || fecha.endsWith("bado"))
					exitcalc = calculateHours(actualdate,0,0);
				else
					exitcalc = precalcexit1;
				
				att.setExitCalc(new Timestamp(exitcalc.getTime()));
				
				//suma atraso + antes + trabajadas
				
				Date sumdelayearlyworked;
				
				sumdelayearlyworked = new Date(precalcexit1.getTime() + delaycalc.getTime() + workedhourscalc.getTime());
				log.config("sumdelayearlyworked "+sumdelayearlyworked);
				//Se resta 2 veces la fecha actual, para llegar al dia actual (la suma acumula los annos)
				sumdelayearlyworked = new Date(sumdelayearlyworked.getTime() - actualdate.getTime() - actualdate.getTime());
				log.config("sumdelayearlyworked "+sumdelayearlyworked);

				att.setSumDelayEarlyWorked(new Timestamp(sumdelayearlyworked.getTime()));
				
				
				//inicio horas extra
				
				Date overtime;
				
				overtime = calculateHours(actualdate,9,0);
				att.setOverTime(new Timestamp(overtime.getTime()));
				
				//pre calculo he1 diurna
				
				Date precalcovertime1;
				
				if(workedhourscalc.compareTo(workedhoursforcalc) > 0)
				{
					precalcovertime1 = new Date(workedhourscalc.getTime() - workedhoursforcalc.getTime());
					precalcovertime1 = new Date(precalcovertime1.getTime() + actualdate.getTime());
				}
				else
					precalcovertime1 = calculateHours(actualdate,0,0);
				
				att.setPreCalcOvertime1(new Timestamp(precalcovertime1.getTime()));
				
				//pre calculo he2 diurna
				
				Date precalcovertime2;
				
				if(exithour.compareTo(earlyexit) > 0)
				{
					precalcovertime2 = new Date(exithour.getTime() - earlyexit.getTime());
					precalcovertime2 = new Date(precalcovertime2.getTime() + actualdate.getTime());
				}
				else
					precalcovertime2 = calculateHours(actualdate,0,0);
				
				att.setPreCalcOvertime2(new Timestamp(precalcovertime2.getTime()));
				
				
				
				// maximo horas diurnas
				
				Date maxdaytime;
				
				if(fecha.compareToIgnoreCase("Domingo") == 0 || fecha.endsWith("bado"))
				{
					maxdaytime = calculateHours(actualdate,0,0);
				}
				else if(fecha.compareToIgnoreCase("Viernes") == 0)
					maxdaytime = calculateHours(actualdate,6,0);
				else
					maxdaytime = calculateHours(actualdate,5,0);
				
				att.setMaxDayTime(new Timestamp(maxdaytime.getTime()));
				
				//resta de horas nocturnas llegada
				
				Date differencehours;
				
				if(entryhour.getTime() > actualdate.getTime())
					if(entryhour.compareTo(overtime) < 0)
					{
						differencehours = new Date(overtime.getTime() - entryhour.getTime());
						differencehours = new Date(differencehours.getTime() + actualdate.getTime());
					}
					else
						differencehours = calculateHours(actualdate,0,0);
				else
					differencehours = calculateHours(actualdate,0,0);
				
				att.setDifferenceHours(new Timestamp(differencehours.getTime()));
				
				
				// inicio hora nocturna
				
				Date nighthoursstart;
				
				nighthoursstart = calculateHours(actualdate,22,0);
				att.setNightHoursStart(new Timestamp(nighthoursstart.getTime()));
				
				// calculo horas nocturna
				
				Date nighthourscalc;
				
				if(fecha.compareToIgnoreCase("Domingo") == 0 || fecha.endsWith("bado"))
					nighthourscalc = workedhourscalc;
				else
					//se agrega if para ver las horas trabajadas
					if(workedhourscalc.getTime() > actualdate.getTime())
					{
						if(exithour.compareTo(nighthoursstart) > 0)
						{
							nighthourscalc = new Date(exithour.getTime() - nighthoursstart.getTime());
							nighthourscalc = new Date(nighthourscalc.getTime() + actualdate.getTime());
						}
						else
							nighthourscalc = calculateHours(actualdate,0,0);
					}
					else
						nighthourscalc = calculateHours(actualdate,0,0);
				log.config("nighthourscalc "+nighthourscalc);
				att.setNightHoursCalc(new Timestamp(nighthourscalc.getTime()));
				
				
				// calculo horas diurnas
				
				Date dayhourscalc = new Date();
				Date dayhourscalcaux;
				
				if(fecha.compareToIgnoreCase("Domingo") == 0 || fecha.endsWith("bado"))
					dayhourscalc = calculateHours(actualdate,0,0);
				else
				{
					//if(entryhour.getTime() > actualdate.getTime())
					if(workedhourscalc.getTime() > actualdate.getTime())
					{
						dayhourscalcaux = new Date(sumdelayearlyworked.getTime() - workedhoursforcalc.getTime() - differencehours.getTime() - nighthourscalc.getTime());
						dayhourscalcaux = new Date(dayhourscalcaux.getTime() + actualdate.getTime() + actualdate.getTime() + actualdate.getTime());
						if(dayhourscalcaux.getTime() <= actualdate.getTime())
							dayhourscalc = calculateHours(actualdate,0,0);
						else
							dayhourscalc = dayhourscalcaux;
					}
					else
						dayhourscalc = calculateHours(actualdate,0,0);

				}
				att.setDayHoursCalc(new Timestamp(dayhourscalc.getTime()));
				
				
				
				//fin hora nocturna
				
				Date nighthoursend;
				nighthoursend = calculateHours(actualdate,8,0);
				att.setNightHoursEnd(new Timestamp(nighthoursend.getTime()));
				
				
				
				//Seteo de columnas nuevas apoyo de restas
				
/*				Date entryhournew;
				Date exithournew;
				
				entryhournew = new Date(entryhour.getTime() - actualdate.getTime());
				exithournew = new Date(exithour.getTime() - actualdate.getTime());
				
				att.set_CustomColumn("entryhournew", new Timestamp(entryhournew.getTime()));
				att.set_CustomColumn("exithournew", new Timestamp(exithournew.getTime()));
	*/			

				
				
				att.saveEx(get_TrxName());		
					
				
			}
			
			

		}
		return "Procesado";
	   
	}	//	doIt
	
	public static java.util.Date calculateHours(Date startDate, int duration, int minutes)
	{		
	  Calendar startCal = Calendar.getInstance();
	 
	  startCal.setTime(startDate);
			
	  for (int i = 1; i < duration; i++)
	  {
	    startCal.add(Calendar.HOUR_OF_DAY, 1);
	  }
	  if(minutes != 0)
		  startCal.add(Calendar.MINUTE,minutes);
	  return startCal.getTime();
	}

}
