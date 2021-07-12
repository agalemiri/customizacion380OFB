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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.X_HR_Process;
import org.ofb.model.OFBForward;
import org.compiere.model.MPeriod;
import org.compiere.model.MRequisition;
import org.compiere.model.X_HR_AttendanceHours;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import org.compiere.model.X_HR_AttendanceTemplate;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: GenerateAttendanceHours.java $
 */
public class GenerateAttendanceHoursByBPartner extends SvrProcess
{
	
	private int				p_Period = 0; 
	private int 			p_BPartner = 0;
	private Timestamp  		p_From;
	private Timestamp		p_To;
	private int 			p_Org = 0;

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
				else if(name.equals("AD_OrgRef2_ID"))
					p_Org = ((BigDecimal)para[i].getParameter()).intValue();
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
			if(p_Org > 0)
				sql = sql + " AND c_bpartner_id in (select c_bpartner_id from " +
						" hr_employee where ad_orgref2_id = "+p_Org+")";
			
			log.config("SQL DELETE "+sql);
			
			DB.executeUpdate(sql,get_TrxName());
			/*String sqlgetpartner = "SELECT distinct(c_bpartner_id) from rvofb_reloj_control where fecha " +
					" between '"+p_From+"' AND '"+p_To+"' ";*/
			String sqlgetpartner = "SELECT c_bpartner_id from C_BPartner where isactive='Y' and isemployee='Y' ";
			if(p_BPartner > 0)
				sqlgetpartner = sqlgetpartner + " AND c_bpartner_id = "+p_BPartner;
			if(p_Org > 0)
				sqlgetpartner = sqlgetpartner + " AND c_bpartner_id in (select c_bpartner_id from " +
						" hr_employee where ad_orgref2_id = "+p_Org+")";
			PreparedStatement pstmt2 = DB.prepareStatement(sqlgetpartner, get_TrxName());
			ResultSet rs2 = pstmt2.executeQuery();
			while(rs2.next())
			{
				String sqlgethours = "SELECT fecha, ad_org_id, c_bpartner_id, dia, entrada, salida, horas_trabajadas, " +
					" incidencias, compensadas, atrasobase, horasmediamannana FROM RVOFB_Reloj_Control WHERE fecha " +
					" between '"+p_From+"' AND '"+p_To+"'";
				sqlgethours = sqlgethours + " AND c_bpartner_id = "+rs2.getInt("C_BPartner_ID");
			
			
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
					
					/**
					 * mfrojas 20181106
					 * Se debe validar que la hora de entrada sea mayor a las 7AM 
					 * y la de salida no sea igual a la de entrada. 
					 * Si la hora de entrada es menor a 7AM, entonces se debera buscar la hora siguiente.
					 * Si la hora de salida es igual a la hora de entrada, se deberea buscar la menor del dia siguiente.
					 */
					
					//Campo para calcular atrasobase auxiliar
					
					Date auxdelaybase = actualdate;
					
					//ENTRADAS
					
					Date auxentry = calculateHours(actualdate,7,0); //hora minima de entrada: 6AM
					
					/**
					 * mfrojas 20181123
					 * La hora minima de entrada dependera del template asociado al funcionario.
					 */
					
					int template_id = DB.getSQLValue(get_TrxName(), "SELECT coalesce(hr_attendancetemplate_id,0) from c_bpartner where c_bpartner_id = "+rs.getInt("c_bpartner_id"));
					if(template_id > 0)
					{
						X_HR_AttendanceTemplate temp = new X_HR_AttendanceTemplate(getCtx(),template_id, get_TrxName());
						Date tempminentry = temp.getMinEntryHour();
						
						log.config("tempminentry "+tempminentry);
						if(tempminentry != null)
						{
							int tempminhour = getHoursQty(tempminentry,0);
							int tempminminute = getHoursQty(tempminentry,1);
							if(tempminhour > 0)
							{
								tempminentry = calculateHours(actualdate,tempminhour+1,tempminminute);
								auxentry = tempminentry;
								log.config("tempminentry "+tempminentry);
							}
						}
						//atraso base
						if(temp.getDelayBase() != null)
						{
							int tempminhour = getHoursQty(temp.getDelayBase(),0);
							int tempminminute = getHoursQty(temp.getDelayBase(),1);
							if(tempminhour > 0)
								auxdelaybase = calculateHours(actualdate, tempminhour+1, tempminminute);
						}
					}
					//FIN modificacion 20181123 mfrojas
					
					Date auxzero = calculateHours(actualdate,0,0); // hora cero
					if(auxentry.compareTo(entryhour)>0 && auxzero.compareTo(entryhour)<0)
					{
						//Si la hora minima de entrada es mayor a la hora de entrada definida, entonces 
						//se debe buscar la siguiente. (solo si la de entrada es mayor a la hora cero
						String sqlnewentry = "SELECT min(datetrx) as datetrx from hr_attendanceline where date_trunc('day',datetrx) " +
								" = '"+(new Timestamp(auxzero.getTime()))+"' AND datetrx > '"+(new Timestamp(auxentry.getTime()))+"' and c_bpartner_id = "+rs.getInt("c_bpartner_id");
						log.config("newentry "+sqlnewentry);
						PreparedStatement pstmt3 = DB.prepareStatement(sqlnewentry, get_TrxName());
						ResultSet rs3 = pstmt3.executeQuery();	
						if(rs3.next())
						{
							log.config("resultado "+rs3.getTimestamp(1));
							Timestamp newentry = rs3.getTimestamp(1);

							if(newentry != null && newentry.compareTo(auxentry)>0)
								entryhour = newentry;
						}
						rs3.close();
					}
					
					
					att.setEntryHour(new Timestamp(entryhour.getTime()));
					
					
					//SALIDAS
					
					if(entryhour.compareTo(exithour)==0 && auxzero.compareTo(exithour) < 0)
					{
						//Se debe buscar la hora minima del dia siguiente
/*						String sqlnewexit = "SELECT coalesce(min(datetrx)::timestamp,now()) as datetrx from hr_attendanceline where (datetrx::date - " +
								" cast('1 day' as interval)) =  '"+(new Timestamp(auxzero.getTime()))+"' and (datetrx - cast('1 day' as interval)) < '"+(new Timestamp(auxentry.getTime())) +
								"' and c_bpartner_id = "+rs.getInt("c_bpartner_id");
	*/					
						Date tomorrowzero = addDays(auxzero,1);
						Date tomorrowentry = addDays(auxentry,1);
						String sqlnewexit = "SELECT datetrx from hr_attendanceline where 1=? and  date_trunc('day',datetrx) " +
								" =  '"+(new Timestamp(tomorrowzero.getTime()))+"' and (datetrx) < '"+(new Timestamp(tomorrowentry.getTime())) +
								"' and c_bpartner_id = "+rs.getInt("c_bpartner_id");

						log.config("newexit "+sqlnewexit);
						
						PreparedStatement pstmt3 = DB.prepareStatement(sqlnewexit, get_TrxName());
						pstmt3.setInt(1, 1);
						ResultSet rs3 = pstmt3.executeQuery();
						while(rs3.next())
						{
							Timestamp newexit = rs3.getTimestamp("datetrx");
							log.config("resultado "+rs3.getTimestamp("datetrx"));
							log.config("newexitt "+rs3.getDate("datetrx"));
							log.config("resultado "+rs3.getObject("datetrx").toString());

							if(newexit != null && newexit.compareTo(exithour)>0)
								exithour = newexit;
						
						}
						rs3.close();

					}
						//En este caso, se debe buscar la hora siguiente
					/**
					 * Final validacion entrada salida
					 * 
					 */
					//Validacion de entrada
	/*				String sqllog = "SELECT min(datetrx) as datetrx from hr_Attendanceline where c_bpartner_id = "+p_BPartner+" AND fecha between" +
						" '"+p_From+"' AND '"+p_To+"'";
					PreparedStatement pstmtlog = DB.prepareStatement(sqllog, get_TrxName());
					ResultSet rslog = pstmtlog.executeQuery();
					log.config("entrada "+rslog.next());
					//Validacion cierre
	
	*/				
					int BPartner = OFBForward.getBP(rs.getInt("c_bpartner_id"));

					att.setExitHour(exithour);
					att.setDateTrx(rs.getTimestamp("fecha"));
					att.setAD_Org_ID(rs.getInt("ad_org_id"));
					att.setC_BPartner_ID(BPartner);
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
						//mfrojas 20190610 earlyexitbase dependera de la jornada (template)
						earlyexitbase = calculateHours(actualdate,0,0);
						maxearlyexit = calculateHours(actualdate,0,0);
						endmidmorning = calculateHours(actualdate,0,0);
					}	
					else if(fecha.compareToIgnoreCase("Viernes") == 0)
					{
						workinghours = calculateHours(actualdate,9,0);
						midmorninghours = calculateHours(actualdate,5,0);
						//mfrojas 20190610 earlyexitbase dependera de la jornada (template)
						//earlyexitbase = calculateHours(actualdate,18,30);
						if(template_id > 0)
							earlyexitbase = calculateHours(auxdelaybase,8,0);
						else
							earlyexitbase = calculateHours(actualdate,18,30);
						maxearlyexit = calculateHours(actualdate,17,0);
						endmidmorning = calculateHours(actualdate,14,30);
					}
					else
					{
						workinghours = calculateHours(actualdate,10,0);
						midmorninghours = calculateHours(actualdate,5,30);
						//mfrojas 20190610 earlyexitbase dependera de la jornada (template)
						//earlyexitbase = calculateHours(actualdate,19,30);
						if(template_id > 0)
							earlyexitbase = calculateHours(auxdelaybase,9,0);
						else
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
					int compensadasdecimalentry = 0;
					int compensadasdecimalexit = 0;
					
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
						
						//mfrojas 20190517 se debera determinar la parte decimal de las compensadas (en caso de existir)
						
						if(hoursam.remainder(Env.ONE).equals(Env.ZERO))
							compensadasdecimalentry = 0;
						else
							compensadasdecimalentry = 30;
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
						
						if(hourspm.remainder(Env.ONE).equals(Env.ZERO))
							compensadasdecimalexit = 0;
						else
							compensadasdecimalexit = 30;
				
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
				
					specialdayentry = calculateHours(actualdate, hoursamqty+1,compensadasdecimalentry);
					specialdayexit = calculateHours(actualdate,hourspmqty+1,compensadasdecimalexit);
					
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
							//workedhoursforcalc = new Date(workedhoursforcalc.getTime() - calculateHours(actualdate,5,0).getTime() + actualdate.getTime());
							if(fecha.compareToIgnoreCase("Viernes") == 0)
								workedhoursforcalc = new Date(workedhoursforcalc.getTime() - calculateHours(actualdate,5,0).getTime() + actualdate.getTime());
							else
								workedhoursforcalc = new Date(workedhoursforcalc.getTime() - calculateHours(actualdate,5,30).getTime() + actualdate.getTime());
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
							if(auxdelaybase.compareTo(actualdate) == 0)
								delaybegin = new Date(rs.getTimestamp("atrasobase").getTime() + specialdayentry.getTime() - actualdate.getTime());
							else
								delaybegin = new Date(auxdelaybase.getTime() + specialdayentry.getTime() - actualdate.getTime());
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
					
					//mfrojas
					//Si hay incidencias, el delaycalc es cero (cuando igual exista una marca)
					
					//mfrojas 20190507 se agrega comma y comta
					
					if(incidencias.contains("CMT") || incidencias.contains("LM") || incidencias.contains("FL") || incidencias.contains("COM(DC)") 
							|| incidencias.contains("COM(MA)") || incidencias.contains("COM(TA)"))
					{
						delaycalc = calculateHours(actualdate,0,0);
					}
					//mfrojas 20190415
					//Si existe solo una marca, y no hay incidencias, el atraso es cero. 
					if(entryhour.compareTo(exithour) == 0 && entryhour.compareTo(auxzero) > 0)
					{
						delaycalc = calculateHours(actualdate,0,0);
					}

				
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
					log.config("calcexit "+calcexit);
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
							
							//mfrojas 20190516
							//Agrega modificacion cuando hay PA(TA), verifica que las horas trabajadas sean iguales o mayores que las horas
							//Que se DEBEN trabajar

							//mfrojas 20190527
							//Agrega modificacion: Cuando hay COM(TA), 4 horas y media, tratamiendo similar a las incidencias PA(TA)

							
							if(incidencias.contains("PA(TA)") || incidencias.contains("COM(TA)"))
							{
								Date calcexitaux2 = new Date(entryhour.getTime() + workedhourscalc.getTime() - actualdate.getTime());
								if(calcexitaux2.compareTo(calcexitaux) < 0)
								{
									if(fecha.compareToIgnoreCase("Viernes") == 0)
										calcexit = new Date(calcexitaux.getTime() + calculateHours(actualdate,5,0).getTime());
									else
										calcexit = new Date(calcexitaux.getTime() + calculateHours(actualdate,5,30).getTime());
									calcexit = new Date(calcexit.getTime() - calculateHours(actualdate,0,0).getTime());
								}

							}
							
							

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
						if(fecha.compareToIgnoreCase("Viernes") == 0) 
							earlyexit = new Date(calcexit.getTime() - specialdayexit.getTime() - calculateHours(actualdate,5,0).getTime());
						else
							earlyexit = new Date(calcexit.getTime() - specialdayexit.getTime() - calculateHours(actualdate,5,30).getTime());
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
					
					//mfrojas 20190411
					//Patch: Si hay cometido, exitcalc es 0
					
					int flagexit = 0;
					if(incidencias.contains("CMT") || incidencias.contains("LM") || 
							incidencias.contains("FL") || incidencias.contains("PA(DC)") || incidencias.contains("COM(DC)") ||
							incidencias.contains("F "))
					{
						exitcalc = calculateHours(actualdate,0,0);
						flagexit = 1;
					}
					
					//Patch: Si no hay marca, exitcalc deberá ser 8 o 9 horas dependiendo del dia
					//patch: 20190425: Si NO hay marca, y hay horas compensadas de mañana od e tarde, falta es media jornada
					//20190910 se agrega PA(MA)
					if(entryhour.compareTo(auxzero) == 0 && exithour.compareTo(auxzero) == 0 && flagexit == 0 && compensadas.getTime() == actualdate.getTime())
						if(fecha.compareToIgnoreCase("Viernes") == 0)
							if(incidencias.contains("COM(MA)") || incidencias.contains("COM(TA)") || incidencias.contains("PA(MA)"))
								exitcalc = calculateHours(actualdate,5,0);
							else
								exitcalc = calculateHours(actualdate,9,0);
						else
							if(incidencias.contains("COM(MA)") || incidencias.contains("COM(TA)")  || incidencias.contains("PA(MA)"))
								exitcalc = calculateHours(actualdate,5,30);
							else
								exitcalc = calculateHours(actualdate,10,0);
					

					//Patch: Si hay alguna marca (entry y exit son iguales), exitcalc es la mitad de la jornada
					

					
					if(entryhour.compareTo(exithour) == 0 && entryhour.compareTo(auxzero) > 0 && flagexit == 0)
						if(fecha.compareToIgnoreCase("Viernes") == 0)
							exitcalc = calculateHours(actualdate,5,0);
						else
							exitcalc = calculateHours(actualdate,5,30);
					

					//patch: Se agrega: Si hay alguna marca ((entry y exit son iguales), y hay alguna incidencia, exitcalc es cero
					
					if(entryhour.compareTo(exithour) == 0 && entryhour.compareTo(auxzero) > 0 && flagexit == 0 && incidencias.length() > 1)
						exitcalc = calculateHours(actualdate,0,0);
					
					//patch: 20190425: Si NO hay marca, y hay horas compensadas de mañana od e tarde, falta es media jornada
					
					
					//patch 20190508: Si es un nonbusinessday, exitcalc es cero
					
					int validatenonbusinessday = 0;
					validatenonbusinessday = DB.getSQLValue(get_TrxName(), "select count(1) from c_nonbusinessday where " +
							" date1 = '"+actualdate+"' and (((SELECT hr_employee.ad_orgref2_id from hr_employee where c_bpartner_id = "+p_BPartner+"))= ad_org_id " +
									" OR ad_org_id = 0)");
					
					if(validatenonbusinessday == 1)
						exitcalc = calculateHours(actualdate,0,0);

					

				
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
					

					
					//mfrojas 20181122 horas extra de mañana, durante un PA(MA) NO EXISTEN.
					
					if(dayhourscalc.getTime() > actualdate.getTime())
					{
						//Si tengo horas extras, revisar si tengo permiso de mañana.
						//mfrojas 20190605 se agrega com(ma)
						if(incidencias.contains("PA(MA)") || incidencias.contains("COM(MA)"))
						{
							//Revisar si es dia lun-jue o dia viernes
							if(fecha.compareToIgnoreCase("Domingo") == 0 || fecha.endsWith("bado"))
							{
								;
							}	
							else if(fecha.compareToIgnoreCase("Viernes") == 0)
							{
								Date auxentry2 = calculateHours(actualdate,13,0); //hora de entrada con PA(MA) en viernes
								if(entryhour.compareTo(auxentry2)<0)
								{
									//Si la hora de entrada es menor a la hora minima de entrada, estas horas
									//no deben aparecer en las horas extra.
									dayhourscalc = new Date(dayhourscalc.getTime() - auxentry2.getTime() + entryhour.getTime());
									
								}

								

							}
							else
							{
								Date auxentry2 = calculateHours(actualdate,13,30); //hora de entrada con PA(MA) en lunes-jueves
								if(entryhour.compareTo(auxentry2)<0)
								{
									//Si la hora de entrada es menor a la hora minima de entrada, estas horas
									//no deben aparecer en las horas extra.
									dayhourscalc = new Date(dayhourscalc.getTime()  - auxentry2.getTime() + entryhour.getTime());
									
								}


							}

							
						}
					}
					
					//mfrojas las horas extra seran desde 1 hora en adelante.
					if(dayhourscalc.compareTo(calculateHours(actualdate,2,0)) < 0)
						dayhourscalc = calculateHours(actualdate,0,0);
					
					att.setDayHoursCalc(new Timestamp(dayhourscalc.getTime()));
				
					//fin hora nocturna
				
					Date nighthoursend;
					nighthoursend = calculateHours(actualdate,8,0);
					att.setNightHoursEnd(new Timestamp(nighthoursend.getTime()));
				
				
				
					//Seteo de columnas nuevas apoyo de restas
					
/*					Date entryhournew;
					Date exithournew;
					
					entryhournew = new Date(entryhour.getTime() - actualdate.getTime());
					exithournew = new Date(exithour.getTime() - actualdate.getTime());
					
					att.set_CustomColumn("entryhournew", new Timestamp(entryhournew.getTime()));
					att.set_CustomColumn("exithournew", new Timestamp(exithournew.getTime()));
	*/			

				   //Seteo de nuevo campo horas trabajadas para reporte. 
					
					Date oldexitcalc;
					oldexitcalc = new Date(exithour.getTime() - entryhour.getTime());
					log.config("entry hour"+entryhour);
					log.config("entryhourtime"+entryhour.getTime());
					log.config("worked hours calc oldexitcalc"+oldexitcalc);
					//llevado a año actual
					
					oldexitcalc = new Date (oldexitcalc.getTime() + actualdate.getTime());
					log.config("workedhourscalc (oldexitcalc) updated "+oldexitcalc);
					
					if(entryhour.compareTo(overtime) < 0 && entryhour.compareTo(auxzero) > 0 && entryhour.compareTo(exithour) != 0)
						oldexitcalc = new Date(oldexitcalc.getTime() - overtime.getTime() + entryhour.getTime());
					//mfrojas 20190517 oldexitcalc cuando haya PA(MA)
					
					
					if((incidencias.contains("PA(MA)") || incidencias.contains("COM(MA)")) && entryhour.compareTo(exithour) != 0)
					{
						Date oldexitcalcaux;
						if(fecha.compareToIgnoreCase("Viernes") == 0)
							oldexitcalcaux = calculateHours(actualdate,13,0);
						else
							oldexitcalcaux = calculateHours(actualdate,13,30);
						
						if(entryhour.compareTo(oldexitcalcaux) < 0)
						{
							oldexitcalcaux = new Date(oldexitcalcaux.getTime() - entryhour.getTime() + actualdate.getTime());
							oldexitcalc = new Date(oldexitcalc.getTime() - oldexitcalcaux.getTime() + actualdate.getTime());
						}

					}
					att.setOldExitCalc(new Timestamp(oldexitcalc.getTime()));
					
					
					
				
					att.saveEx(get_TrxName());		
					
				
				}
				rs.close();
			
			}

			rs2.close();
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
	
	public static java.util.Date addDays(Date date, int days)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		
		return calendar.getTime();
		

	}

	public static int getHoursQty(Date startdate, int type)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startdate);
		
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		if(type == 0)
			return hour;
		else if(type == 1)
			return minute;
		else
			return 0;
	}
}
