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
import org.compiere.util.Env;
import org.compiere.model.X_HR_AdministrativeRequests;
import org.compiere.model.X_HR_AttendanceLine;
import org.ofb.utils.DateUtils;

/**
 *	
 *	
 *  @author mfrojas 
 *  @version $Id: ProcessAdministrativeRequests.java $
 */
//
public class ProcessAdministrativeRequest extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_AdmRequest_ID = 0; 
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
		p_AdmRequest_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_AdmRequest_ID > 0)
		{
			X_HR_AdministrativeRequests req = new X_HR_AdministrativeRequests(getCtx(), p_AdmRequest_ID, get_TrxName());
			//seteo de nuevo estado al procesar
			String newStatus = "DR";
			String newAction = "DR";	
			log.config("paction "+p_Action);
			if(req.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "SS";
				newAction = "SS";
			}
			

			else if(req.getDocStatus().compareTo("SS") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AP";
				newAction = "AP";
			}
			/*else if(req.getDocStatus().compareTo("AP") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AH";
				newAction = "AH";
			}*/
			else if(req.getDocStatus().compareTo("AP") == 0 && p_Action.compareTo("CO") == 0 && req.getRequestType().compareTo("CMT")!=0)
			{
				newStatus = "CO";
				newAction = "CO";
			}	

			else if(req.getDocStatus().compareTo("AP") == 0 && p_Action.compareTo("CO") == 0 && req.getRequestType().compareTo("CMT")==0)
			{
				newStatus = "AD";
				newAction = "AD";
			}	

			else if(req.getDocStatus().compareTo("AD") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}	

			
			/**else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "GA";
				newAction = "GA";
			}	
			else if(req.getDocStatus().compareTo("GA") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}	
			
			**/
			//seteo de nuevo estado al rechazar (RJ)
			else if(req.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("RJ") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(req.getDocStatus().compareTo("SS") == 0 && p_Action.compareTo("RJ") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(req.getDocStatus().compareTo("AP") == 0 && p_Action.compareTo("RJ") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}	
	
			else if(req.getDocStatus().compareTo("AD") == 0 && p_Action.compareTo("RJ") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}	
			
			
			//Anulacion
			else if(req.getDocStatus().compareTo("CO")!=0 && p_Action.compareTo("VO")==0)
			{
				newStatus = "VO";
				newAction = "VO";
			}
			//validacion de rol
			int cant = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM AD_Document_Action_Access daa" +
					" INNER JOIN AD_Ref_List rl ON (daa.AD_Ref_List_ID = rl.AD_Ref_List_ID) " +
					" WHERE value = '"+newAction+"' AND AD_Role_ID = "+Env.getAD_Role_ID(getCtx())+
					" AND C_DocType_ID = "+req.get_ValueAsInt("C_DocType_ID"));
			//procesar
			log.config("cantidad"+cant);
			if(cant > 0)
			{
				if(newAction.compareTo("CO") != 0)
				{
					int BPartner = req.getC_BPartner_ID();
					int HR_Employee = DB.getSQLValue(get_TrxName(), "SELECT max(HR_Employee_ID) " +
							" FROM HR_Employee WHERE C_BPartner_ID = " +BPartner+" and" +
							" IsActive='Y'");	
					
					log.config("HR_Employee "+ HR_Employee);

					if(p_Action.compareTo("RJ") == 0)
					{
						if(req.get_Value("Comments") == null)
							throw new AdempiereException("Debe ingresar un motivo de rechazo");
					}
					if(newAction.compareTo("SS") == 0)
					{
						if(req.getRequestType().compareTo("SVC")==0)
						{
							
							//Validación previa de días disponibles (No se actualiza datos)
							//Definir días disponibles 
							String sqldays = "SELECT VacationDay from HR_Employee where HR_Employee_ID = ?";
					        BigDecimal DaysLeft = DB.getSQLValueBD(get_TrxName(), sqldays, HR_Employee);
					        
					        log.config("hr employee = "+HR_Employee);
					        log.config("days left"+DaysLeft);
					        log.config("partner = "+BPartner);
					        //Definir días solicitados 
					        String sqldaysreq = "SELECT Days from HR_AdministrativeRequestsL where HR_AdministrativeRequests_ID = ? ";
					        BigDecimal DaysReq = DB.getSQLValueBD(get_TrxName(), sqldaysreq, req.get_ID());
					        
					        
					        //Validar si el funcionario tiene días suficientes
							if(DaysLeft.compareTo(DaysReq)>=0)
							{
								
							}
							else
								throw new AdempiereException("Debe revisar la cantidad de días solicitados.");
						}				
						
					}
					req.setDocStatus(newStatus);
					if(newAction.compareTo("VO")==0)
						req.setProcessed(true);
					req.save();
					
				}
				else if(newAction.compareTo("CO") == 0)
				{
					
					int BPartner = req.getC_BPartner_ID();
					int HR_Employee = DB.getSQLValue(get_TrxName(), "SELECT max(HR_Employee_ID) " +
							" FROM HR_Employee WHERE C_BPartner_ID = " +BPartner+" and" +
							" IsActive='Y'");	
					
					log.config("HR_Employee "+ HR_Employee);
					
					//Solicitud Permiso Administrativo
					if(req.getRequestType().compareTo("PAD")==0)
					{
						//X_HR_Employee bp = new X_HR_Employee(getCtx(), HR_Employee, get_TrxName());
						
						//Definir días disponibles 
						String sqldays = "SELECT AdministrativeDay from HR_Employee where HR_Employee_ID = ?";
				        BigDecimal DaysLeft = DB.getSQLValueBD(get_TrxName(), sqldays, HR_Employee);
				        
				        log.config("hr employee = "+HR_Employee);
				        log.config("days left"+DaysLeft);
				        log.config("partner = "+BPartner);
				        //Definir días solicitados 
				        String sqldaysreq = "SELECT count(1) from HR_AdministrativeRequestsL where HR_AdministrativeRequests_ID = ? AND " +
				        		" AdmType like 'DIA'";
				        BigDecimal DaysReq = DB.getSQLValueBD(get_TrxName(), sqldaysreq, req.get_ID());
				        
				        String sqlmiddaysreq = "SELECT count(1) from HR_AdministrativeRequestsL where HR_AdministrativeRequests_ID = ? AND " +
				        		" AdmType in ('MAN','TAR')";
				        BigDecimal MidDaysReq = DB.getSQLValueBD(get_TrxName(), sqlmiddaysreq, req.get_ID());
				        
				        //Validar si el funcionario tiene días suficientes
				        
				        
						MidDaysReq = MidDaysReq.divide(new BigDecimal("2"));
						BigDecimal suma = MidDaysReq.add(DaysReq);
						
						if(DaysLeft.compareTo(suma)>=0)
						{
							DB.executeUpdate("Update HR_Employee set AdministrativeDay = AdministrativeDay - "+suma+" WHERE HR_Employee_ID = "+HR_Employee, get_TrxName());
						}
						else
							throw new AdempiereException("Debe revisar la cantidad de días solicitados.");
					}					
					//Solicitud Vacaciones
					if(req.getRequestType().compareTo("SVC")==0)
					{
						//X_HR_Employee bp = new X_HR_Employee(getCtx(), HR_Employee, get_TrxName());
						
						//Definir días disponibles 
						String sqldays = "SELECT VacationDay from HR_Employee where HR_Employee_ID = ?";
				        BigDecimal DaysLeft = DB.getSQLValueBD(get_TrxName(), sqldays, HR_Employee);
				        
				        log.config("hr employee = "+HR_Employee);
				        log.config("days left"+DaysLeft);
				        log.config("partner = "+BPartner);
				        //Definir días solicitados 
				        String sqldaysreq = "SELECT Days from HR_AdministrativeRequestsL where HR_AdministrativeRequests_ID = ? ";
				        BigDecimal DaysReq = DB.getSQLValueBD(get_TrxName(), sqldaysreq, req.get_ID());
				        
				        
				        //Validar si el funcionario tiene días suficientes
				        
				        
						
						if(DaysLeft.compareTo(DaysReq)>=0)
						{
							DB.executeUpdate("Update HR_Employee set VacationDay = VacationDay - "+DaysReq+" WHERE HR_Employee_ID = "+HR_Employee, get_TrxName());
						}
						else
							throw new AdempiereException("Debe revisar la cantidad de días solicitados.");
					}				
					
					
					
					//Solicitud Traspaso de vacaciones
					if(req.getRequestType().compareTo("SVT")==0)
					{
						//X_HR_Employee bp = new X_HR_Employee(getCtx(), HR_Employee, get_TrxName());
						
						//Revisar si existen más solicitudes de este tipo dentro del año (sólo puede haber una)
						
						String sqlyear = "Select extract (year from datedoc) from hr_administrativerequests where hr_administrativerequests_id = ?";
						BigDecimal yeardoc = DB.getSQLValueBD(get_TrxName(), sqlyear, req.get_ID());
						
						//Contar cantidad de solicitudes con el mismo año
						String sqlcount = "SELECT count(1) from hr_administrativerequests where requesttype = 'SVT' and datedoc " +
								" between '"+yeardoc+"-01-01' and '"+yeardoc+"-12-31' and c_bpartner_id = "+BPartner;
						
						int contador = DB.getSQLValue(get_TrxName(), sqlcount);
						
						if(contador > 1)
							throw new AdempiereException ("Ya tiene una solicitud de traspaso para el año indicado");
						
						
						//Definir días disponibles para traspasar
						String sqldays = "SELECT coalesce(VacationDay,0) from HR_Employee where HR_Employee_ID = ?";
				        BigDecimal DaysLeft = DB.getSQLValueBD(get_TrxName(), sqldays, HR_Employee);
				        
				        log.config("hr employee = "+HR_Employee);
				        log.config("days left"+DaysLeft);
				        log.config("partner = "+BPartner);
				        //Definir días solicitados 
				        String sqldaysreq = "SELECT coalesce(Days,0) from HR_AdministrativeRequestsL where HR_AdministrativeRequests_ID = ? ";
				        BigDecimal DaysReq = DB.getSQLValueBD(get_TrxName(), sqldaysreq, req.get_ID());
				        
				        
				        //Validar si el funcionario tiene días suficientes
				        
				        
						//Si tiene días suficientes y son aprobados, los nuevos días disponibles serán la cantidad de días iniciales, más los días traspasados.
						if(DaysLeft.compareTo(DaysReq)>=0)
						{
							DB.executeUpdate("Update HR_Employee set VacationDay = "+DaysReq+" + VacationDayInitial WHERE HR_Employee_ID = "+HR_Employee, get_TrxName());
						}
						else
							throw new AdempiereException("Debe revisar la cantidad de días solicitados.");
					}				

					//Solicitud Permiso Compensatorio
					if(req.getRequestType().compareTo("COM")==0)
					{
						//X_HR_Employee bp = new X_HR_Employee(getCtx(), HR_Employee, get_TrxName());
						
						//Definir horas disponibles 
						String sqlhours = "SELECT HoursAvailable from HR_Employee where HR_Employee_ID = ?";
				        BigDecimal HoursAvailable = DB.getSQLValueBD(get_TrxName(), sqlhours, HR_Employee);
				        
				        log.config("hr employee = "+HR_Employee);
				        log.config("days left"+HoursAvailable);
				        log.config("partner = "+BPartner);
				        //Definir horas solicitadas 
				        String sqlhoursreq = "SELECT SUM(hours) from HR_AdministrativeRequestsL where IsActive = 'Y' AND HR_AdministrativeRequests_ID = ? ";
				        BigDecimal HoursReq = DB.getSQLValueBD(get_TrxName(), sqlhoursreq, req.get_ID());
				        
				        
				        //Validar si el funcionario tiene horas suficientes
				        
				        
						
						if(HoursAvailable.compareTo(HoursReq)>=0)
						{
							//DB.executeUpdate("Update HR_Employee set HoursAvailable = HoursAvailable + "+HoursReq+" WHERE HR_Employee_ID = "+HR_Employee, get_TrxName());
							// se actualizaran campos nuevos
							DB.executeUpdate("Update HR_Employee set HoursUsed  = HoursUsed + "+HoursReq+" WHERE HR_Employee_ID = "+HR_Employee, get_TrxName());
							DB.executeUpdate("Update HR_Employee set HoursAvailable = HoursEarned - HoursUsed WHERE HR_Employee_ID = "+HR_Employee, get_TrxName());
						}
						else
							throw new AdempiereException("Debe revisar la cantidad de horas solicitadas.");
					}					
					if(req.getRequestType().compareTo("CAP")==0)
					{
						//se busca año
						int yearReq = DateUtils.today().getYear()+1900;
						int yearAD = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_Year_ID) FROM C_Year WHERE FiscalYear = '"+yearReq+"'");
						
						//ID Cabecera 
						int ID_Cab = DB.getSQLValue(get_TrxName(),"SELECT MAX(HR_Attendance_ID) FROM HR_Attendance WHERE C_Year_ID = "+yearAD);
						if(ID_Cab > 0)
						{
							String sql = "SELECT * FROM HR_AdministrativeRequestsL WHERE HR_AdministrativeRequests_ID = "+req.get_ID();
							PreparedStatement pstmt = null;
							ResultSet rs = null;
							try
							{
								pstmt = DB.prepareStatement(sql, get_TrxName());
								rs = pstmt.executeQuery();
								while (rs.next())
								{
									X_HR_AttendanceLine line = new X_HR_AttendanceLine(getCtx(), 0, get_TrxName());
									line.setC_BPartner_ID(req.getC_BPartner_ID());
									line.setHR_Attendance_ID(ID_Cab);
									line.setDateTrx(rs.getTimestamp("DateRequired"));
									line.setRequestType(rs.getString("AttType"));
									line.save();
								}
								rs.close();
								pstmt.close();
								pstmt = null;
							}
							catch (SQLException e)
							{
								throw new DBException(e, sql);
							}
							finally
							{
								DB.close(rs, pstmt);
								rs = null; pstmt = null;
							}
						}
				        
					}	
					if(req.getRequestType().compareTo("PPM")==0)
					{
						log.config("hr employee = "+HR_Employee);
				        log.config("partner = "+BPartner);
				        //Definir días solicitados 
				        String sqldaysreq = "SELECT SUM(Days) from HR_AdministrativeRequestsL where HR_AdministrativeRequests_ID = ? ";
				        BigDecimal DaysReq = DB.getSQLValueBD(get_TrxName(), sqldaysreq, req.get_ID());
				        log.config("days = "+DaysReq);
				        if(DaysReq.compareTo(new BigDecimal("5.0")) > 0)
		        		{
				        	throw new AdempiereException("Solo se pueden solicitar máximo 5 dias.");
		        		}
					}
					if(req.getRequestType().compareTo("PAT")==0)
					{
						log.config("hr employee = "+HR_Employee);
				        log.config("partner = "+BPartner);
				        //Definir días solicitados 
				        String sqldaysreq = "SELECT SUM(Days) from HR_AdministrativeRequestsL where HR_AdministrativeRequests_ID = ? ";
				        BigDecimal DaysReq = DB.getSQLValueBD(get_TrxName(), sqldaysreq, req.get_ID());
				        log.config("days = "+DaysReq);
				        if(DaysReq.compareTo(new BigDecimal("5.0")) > 0)
		        		{
				        	throw new AdempiereException("Solo se pueden solicitar máximo 5 dias.");
		        		}
					}
					//Horas extra a tiempo compensado
					if(req.getRequestType().compareTo("SHT")==0)
					{
						log.config("hr employee = "+HR_Employee);
				        log.config("partner = "+BPartner);
				        //Definir horas solicitados 
				        String sqldaysreq = "SELECT SUM(hours) from HR_AdministrativeRequestsL where IsActive = 'Y' AND HR_AdministrativeRequests_ID = ? ";
				        BigDecimal HoursReq = DB.getSQLValueBD(get_TrxName(), sqldaysreq, req.get_ID());
				        log.config("Hours = "+HoursReq);
				        if(HoursReq.compareTo(Env.ZERO) > 0)
		        		{
							//DB.executeUpdate("Update HR_Employee set HoursAvailable = HoursAvailable + "+HoursReq+" WHERE HR_Employee_ID = "+HR_Employee, get_TrxName());
				        	DB.executeUpdate("Update HR_Employee set HoursEarned  = HoursEarned + "+HoursReq+" WHERE HR_Employee_ID = "+HR_Employee, get_TrxName());
							DB.executeUpdate("Update HR_Employee set HoursAvailable = HoursEarned - HoursUsed WHERE HR_Employee_ID = "+HR_Employee, get_TrxName());
		        		}
					}

					if(req.getRequestType().compareTo("FAL")==0)
					{
						log.config("hr employee = "+HR_Employee);
				        log.config("partner = "+BPartner);
				        //Definir días solicitados 
				        String sqldaysreq = "SELECT SUM(Days) from HR_AdministrativeRequestsL where HR_AdministrativeRequests_ID = ? ";
				        BigDecimal DaysReq = DB.getSQLValueBD(get_TrxName(), sqldaysreq, req.get_ID());
				        log.config("days = "+DaysReq);
				        if(req.get_ValueAsString("RequestType2").compareTo("01") == 0)
						{
							if(DaysReq.compareTo(new BigDecimal("7.0")) > 0)
							{
								throw new AdempiereException("Solo se pueden solicitar máximo 7 dias.");
							}
						}
						if(req.get_ValueAsString("RequestType2").compareTo("02") == 0)
						{
							if(DaysReq.compareTo(new BigDecimal("3.0")) > 0)
							{
								throw new AdempiereException("Solo se pueden solicitar máximo 3 dias.");
							}
						}
				        
					}
					
					req.setDocStatus("CO");
					req.setProcessed(true);
					//req.processIt("CO");
					req.save();
				}
				//envio de correo
				if(newStatus.compareTo("SS") == 0)
				{
					log.config("Correo Enviado solicitud administrativa: "+req.get_ID());
					//mandacorreo
					CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
					cst.setInt(1, Env.getAD_Client_ID(getCtx()));
					cst.setInt(2, 36);//cambiar por el que corresponda ININOLES // Cambiado mfrojas
					cst.setString(3, "-");
					cst.setInt(4,req.get_ID());
					cst.execute();
				}
				else if(newStatus.compareTo("AP") == 0)
				{
					log.config("Correo Enviado solicitud administrativa: "+req.get_ID());
					//mandacorreo
					CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
					cst.setInt(1, Env.getAD_Client_ID(getCtx()));
					cst.setInt(2, 37);//cambiar por el que corresponda ININOLES // Cambiado mfrojas
					cst.setString(3, "-");
					cst.setInt(4,req.get_ID());
					cst.execute();
				}
				//correo al completar
				else if(newStatus.compareTo("CO") == 0)
				{
					log.config("Correo Enviado solicitud administrativa: "+req.get_ID());
					//mandacorreo
					CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
					cst.setInt(1, Env.getAD_Client_ID(getCtx()));
					cst.setInt(2, 38);
					cst.setString(3, "-");
					cst.setInt(4,req.get_ID());
					cst.execute();
				}
				//correo al rechazar
				else if(newStatus.compareTo("DR") == 0)
				{
					log.config("Correo Enviado solicitud administrativa: "+req.get_ID());
					//mandacorreo
					CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
					cst.setInt(1, Env.getAD_Client_ID(getCtx()));
					cst.setInt(2, 39);
					cst.setString(3, "-");
					cst.setInt(4,req.get_ID());
					cst.execute();
				}
				
			}
			else
				throw new AdempiereException("Error: Permisos de rol insuficientes");
		}
	   return "Procesado";
	}	//	doIt
}
