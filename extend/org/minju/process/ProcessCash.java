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
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat; 




import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.X_HR_AdministrativeRequests;
import org.compiere.model.X_HR_AttendanceLine;
import org.ofb.utils.DateUtils;
import org.compiere.model.MCash;
import org.compiere.model.MCashLine;
import org.compiere.model.MConversionRate;
import org.compiere.model.X_GL_BudgetControlLine;
import org.compiere.model.MElementValue;
/**
 *	
 *	
 *  @author mfrojas 
 *  @version $Id: ProcessCash.java $
 */
//
public class ProcessCash extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_Cash_ID = 0; 
	private String				p_Action = "PR";
	private int 			flag = 0;
	private static CLogger		s_log = CLogger.getCLogger (MElementValue.class);

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
		p_Cash_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_Cash_ID > 0)
		{
			MCash cash = new MCash(getCtx(),p_Cash_ID, get_TrxName());
			
			//X_HR_AdministrativeRequests req = new X_HR_AdministrativeRequests(getCtx(), p_AdmRequest_ID, get_TrxName());
			//seteo de nuevo estado al procesar
			String newStatus = "DR";
			String newAction = "DR";	
			log.config("paction "+p_Action);
			if(cash.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "SS";
				newAction = "SS";
			}
			
			else if(cash.getDocStatus().compareTo("SS") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AJ";
				newAction = "AJ";
			}
			else if(cash.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AV";
				newAction = "AV";
			}	

			else if(cash.getDocStatus().compareTo("AV") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AN";
				newAction = "AN";
			}	

			else if(cash.getDocStatus().compareTo("AN") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "GA";
				newAction = "GA";
			}	

			
			else if(cash.getDocStatus().compareTo("GA") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AB";
				newAction = "AB";
			}			
			else if(cash.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AY";
				newAction = "AY";
			}
			else if(cash.getDocStatus().compareTo("AY") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AO";
				newAction = "AO";
			}
			else if(cash.getDocStatus().compareTo("AO") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AR";
				newAction = "AR";
			}
			else if(cash.getDocStatus().compareTo("AR") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AA";
				newAction = "AA";
			}
			else if(cash.getDocStatus().compareTo("AA") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "EF";
				newAction = "EF";
			}
			else if(cash.getDocStatus().compareTo("EF") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "RR";
				newAction = "RR";
			}
			else if(cash.getDocStatus().compareTo("RR") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AZ";
				newAction = "AZ";
			}
			else if(cash.getDocStatus().compareTo("AZ") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}
			//FLUJO DE DEVOLUCION
			
			else if(cash.getDocStatus().compareTo("AY") == 0 && (p_Action.compareTo("AN") == 0 || p_Action.compareTo("AB") == 0))
			{
				newStatus = p_Action;
				newAction = p_Action;
				flag = 1;
			}
			else if(cash.getDocStatus().compareTo("AO") == 0 && (p_Action.compareTo("AN") == 0 || p_Action.compareTo("AB") == 0))
			{
				newStatus = p_Action;
				newAction = p_Action;
				flag = 1;
			}
			else if(cash.getDocStatus().compareTo("AR") == 0 && (p_Action.compareTo("AN") == 0 || p_Action.compareTo("AB") == 0))
			{
				newStatus = p_Action;
				newAction = p_Action;
				flag = 1;
			}
			else if(cash.getDocStatus().compareTo("AA") == 0 && (p_Action.compareTo("AN") == 0 || p_Action.compareTo("AB") == 0))
			{
				newStatus = p_Action;
				newAction = p_Action;
				flag = 1;
			}
			else if(cash.getDocStatus().compareTo("EF") == 0 && (p_Action.compareTo("AN") == 0 || p_Action.compareTo("AB") == 0))
			{
				newStatus = p_Action;
				newAction = p_Action;
				flag = 1;
			}
			else if(cash.getDocStatus().compareTo("RR") == 0 && (p_Action.compareTo("AN") == 0 || p_Action.compareTo("AB") == 0))
			{
				newStatus = p_Action;
				newAction = p_Action;
				flag = 1;
			}
			else if(cash.getDocStatus().compareTo("AZ") == 0 && (p_Action.compareTo("AN") == 0 || p_Action.compareTo("AB") == 0))
			{
				newStatus = p_Action;
				newAction = p_Action;
				flag = 1;
			}

			/**else if(req.getDocStatus().compareTo("GA") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}	
			
			**/
			//seteo de nuevo estado al rechazar
			/**else if(req.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(req.getDocStatus().compareTo("SS") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(req.getDocStatus().compareTo("AP") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "SS";
				newAction = "SS";
			}	
	
			else if(req.getDocStatus().compareTo("AH") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "AP";
				newAction = "AP";
			}	**/
			
			//validacion de rol
			int cant = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM AD_Document_Action_Access daa" +
					" INNER JOIN AD_Ref_List rl ON (daa.AD_Ref_List_ID = rl.AD_Ref_List_ID) " +
					" WHERE value = '"+newAction+"' AND AD_Role_ID = "+Env.getAD_Role_ID(getCtx())+
					" AND C_DocType_ID = "+cash.get_ValueAsInt("C_DocType_ID"));
			//procesar
			log.config("cantidad"+cant);
			if(cant > 0)
			{
				if(newAction.compareTo("CO") != 0)
				{
					/**mfrojas
					 * 20180509
					 * Se valida que cada linea asociada al diario tenga su propio adjunto.
					 * C_Cash->407
					 * C_CashLine->410
					 */
					/**
					 * mfrojas 20180710
					 * Se cambia validacion anterior. No es necesario que cada linea tenga un 
					 * adjunto, pero si que la cabecera tenga adjunto
					 */
					if(newAction.compareTo("SS") == 0)
					{
						/*MCashLine[] lines = cash.getLines(true);	
					
						for (int i = 0; i < lines.length; i++)
						{
							MCashLine cLine = lines[i];
							String records = "select ad_attachment_id from ad_attachment where ad_table_id = 410 and record_id = "+cLine.get_ID();
							PreparedStatement pstmt = DB.prepareStatement(records, get_TrxName());
							ResultSet rs = pstmt.executeQuery();
						
							int att = 0;
					
							while (rs.next())
								att++;
							if(att <= 0)
								throw new AdempiereException("Debe revisar que cada linea tenga al menos un adjunto");
						
							rs.close ();
							pstmt.close ();
							pstmt = null;
						}
							*/
						
						String records = "SELECT count(1) from ad_attachment where ad_table_id = 407 and record_id = "+cash.get_ID();
						int recordsqty = DB.getSQLValue(get_TrxName(), records);
						if(recordsqty < 1)
							throw new AdempiereException("Debe revisar que exista al menos un adjunto en cabecera");
						
						//Se debe validar que no debe existir un monto
						//menor a 1 UTM en cuentas que no sean de servicios 
						//basicos ni gastos menores
						
						
						//Primero: Obtener monto igual a 1 UTM
						
						String sqlobtainutm = "SELECT coalesce(MultiplyRate,0) FROM C_Conversion_Rate WHERE C_Currency_ID = ? " +
								" AND ValidFrom <= '"+cash.getStatementDate()+"' AND ValidTo >= '"+cash.getStatementDate()+"'";
						int utmid = 2000001;
						log.config("sqlobtainutm "+sqlobtainutm);
						BigDecimal utmvalue = DB.getSQLValueBD(get_TrxName(), sqlobtainutm, utmid);
						
						if(utmvalue == null || utmvalue.compareTo(Env.ZERO)<=0)
						{
							throw new AdempiereException("La UTM no esta definida");

						}

						
						
						//
						MCashLine[] lines = cash.getLines(true);	
						
						
						for (int i = 0; i < lines.length; i++)
						{
							MCashLine cLine = lines[i];
							int validcombination = cLine.get_ValueAsInt("C_ValidCombination_ID");
							String sqlaccountname = "SELECT Combination from C_ValidCombination where " +
									" c_validcombination_id = ?";
							String accountname = DB.getSQLValueString(get_TrxName(), sqlaccountname, validcombination);
							String sqlgetaccount = "SELECT IsAccountBasic FROM C_ElementValue WHERE " +
									" C_ElementValue_ID in (SELECT Account_ID from C_ValidCombination " +
									" WHERE C_ValidCombination_ID = ?)";
							PreparedStatement pstmt = null;
							pstmt = DB.prepareStatement(sqlgetaccount, get_TrxName());
							pstmt.setInt(1, validcombination);
							log.config("config: "+sqlgetaccount);
							ResultSet rs = pstmt.executeQuery();
							rs.next();
							String accountst = rs.getString("IsAccountBasic");
							if(accountst.compareTo("N") == 0 && cLine.getCashType().compareTo("E") == 0)
							{
								String sumofvalues = "SELECT sum(abs(Amount)) from C_CashLine " +
										" WHERE  C_ValidCombination_ID = ? AND C_Cash_ID = "+cLine.getC_Cash_ID()+" and CashType like 'E'";
								BigDecimal sum = DB.getSQLValueBD(get_TrxName(), sumofvalues, validcombination);
								if(sum.compareTo(utmvalue)<0)
									throw new AdempiereException("El monto total de la cuenta "+accountname+" no debe ser menor a 1 UTM.");
							}
								
								

							if(cLine.getCashType().compareTo("T")!=0)
							{
								int id_budget = cLine.get_ValueAsInt("GL_BudgetControlLine_ID");
								BigDecimal amount = cLine.getAmount().abs();
							
								X_GL_BudgetControlLine gl = new X_GL_BudgetControlLine(getCtx(),id_budget,get_TrxName());
							
								
								BigDecimal amount2 = gl.getAmount2();
								if(amount2 == null)
									amount2 = Env.ZERO;
								gl.setAmount2(amount2.subtract(amount));
								gl.save();
								
								
							}
					
						}

						
						
						
						
					}
					//Revisar presupuesto por línea y actualizar monto comprometido.
					
					if(newAction.compareTo("AB") == 0 && flag == 0)
					{
						if(cash.getDocStatus().compareTo("DR") == 0 || cash.getDocStatus().compareTo("SS") == 0 || cash.getDocStatus().compareTo("AJ") == 0 || cash.getDocStatus().compareTo("AV") == 0 || cash.getDocStatus().compareTo("AN") == 0 || cash.getDocStatus().compareTo("GA") == 0)
						{
							MCashLine[] lines = cash.getLines(true);	
						
							for (int i = 0; i < lines.length; i++)
							{
								MCashLine cLine = lines[i];
								/**
								 * 20180619 MFROJAS
								 * Cambio solicitado. No poner nivel 4 de presupuesto. Poner todas las cuentas, 
								 * aunque éstas no tengan presupuesto. De esta manera, se deberá validar que la cuenta 
								 * posea un registro en el nivel 4. Además, que este registro tenga presupuesto. 
								 */
								if(cLine.getCashType().compareTo("T")!=0)
								{
									/*int id_budget = cLine.get_ValueAsInt("GL_BudgetControlLine_ID");
									BigDecimal amount = cLine.getAmount().abs();
								
									X_GL_BudgetControlLine gl = new X_GL_BudgetControlLine(getCtx(),id_budget,get_TrxName());
								
									BigDecimal available = gl.getAmount().subtract(gl.getAmount3());
									if(available.compareTo(amount)>=0)
									{
										BigDecimal amount2 = gl.getAmount2();
										if(amount2 == null)
											amount2 = Env.ZERO;
										gl.setAmount2(amount2.add(amount));
										gl.save();
									}
									else
									{									
										throw new AdempiereException("No hay monto suficiente. Disponible: "+available.setScale(0, BigDecimal.ROUND_HALF_UP));
									}
									*/
									int validcomb = cLine.get_ValueAsInt("C_ValidCombination_ID");
									
									String cuenta = DB.getSQLValueString(cash.get_TrxName(), "SELECT alias FROM C_validcombination where c_validcombination_id = ?", validcomb); 
									int adorg = cash.getAD_Org_ID();
									Date date = new Date();
									date = cash.getDateAcct();
									log.config("date "+date);
									int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
									//int year = DB.getSQLValue(cash.get_TrxName(), "SELECT extract")
									String sql = "SELECT coalesce(max(gl_budgetcontrolline_id),0) from gl_budgetcontrolline where ad_org_id = "+adorg+" " +
											" and c_validcombination_id = "+validcomb+" AND GL_BudgetDetail_ID in ( SELECT " +
													" gl_budgetdetail_id from gl_budgetdetail where gl_budgetcontrol_id in " +
													"(SELECT gl_budgetcontrol_id from gl_budgetcontrol where gl_budgetcontrolheader_id in " +
													"(select gl_budgetcontrolheader_id from gl_budgetcontrolheader where c_year_id in " +
													"(SELECT c_year_id from c_year where fiscalyear like '"+year+"'))))";
									int count = DB.getSQLValue(cash.get_TrxName(), sql);
									
									if(count <= 0)
										throw new AdempiereException("No hay asignacion para la cuenta "+cuenta);
									else
									{	
										BigDecimal amount = cLine.getAmount().abs();
										X_GL_BudgetControlLine gl = new X_GL_BudgetControlLine(getCtx(),count,get_TrxName());
										BigDecimal available = gl.getAmount().subtract(gl.getAmount3());
										if(available.compareTo(amount)>=0)
										{
											BigDecimal amount2 = gl.getAmount2();
											if(amount2 == null)
												amount2 = Env.ZERO;
											gl.setAmount2(amount2.add(amount));
											gl.save();
											cLine.set_CustomColumn("GL_BudgetControlLine_ID", count);
											cLine.save();
										}
										else
										{									
											throw new AdempiereException("No hay monto suficiente. Disponible: "+available.setScale(0, BigDecimal.ROUND_HALF_UP));
										}

									}
									
								}
						
							}
						}
					}
					if(newAction.compareTo("AB") == 0 && flag == 1)
						cash.set_CustomColumn("ForBudget", false);
					//En este caso, corresponderia a una devolucion al estado previo al de presupuesto, por 
					//lo que el presupuesto se debe reversar.
					
					if(newAction.compareTo("AN") == 0 && flag == 1)
					{
							cash.set_CustomColumn("ForBudget", false);
							MCashLine[] lines = cash.getLines(true);	
						
							for (int i = 0; i < lines.length; i++)
							{
								MCashLine cLine = lines[i];
								/**
								 * Reverso de presupuesto
								 * */
								if(cLine.getCashType().compareTo("T")!=0)
								{
									int id_budget = cLine.get_ValueAsInt("GL_BudgetControlLine_ID");
									BigDecimal amount = cLine.getAmount().abs();
								
									X_GL_BudgetControlLine gl = new X_GL_BudgetControlLine(getCtx(),id_budget,get_TrxName());
								
									
									BigDecimal amount2 = gl.getAmount2();
									if(amount2 == null)
										amount2 = Env.ZERO;
									gl.setAmount2(amount2.subtract(amount));
									gl.save();
									
									
								}
						
							}

					}
					cash.setDocStatus(newStatus);
					

					cash.save();
				}
				else if(newAction.compareTo("CO") == 0)
				{
					MCashLine[] lines = cash.getLines(true);	
					
					for (int i = 0; i < lines.length; i++)
					{
						MCashLine cLine = lines[i];
						if(cLine.getCashType().compareTo("T")!=0)
						{
							int id_budget = cLine.get_ValueAsInt("GL_BudgetControlLine_ID");
							BigDecimal amount = cLine.getAmount().abs();
							
							X_GL_BudgetControlLine gl = new X_GL_BudgetControlLine(getCtx(),id_budget,get_TrxName());
							
							BigDecimal available = gl.getAmount().subtract(gl.getAmount3());
							if(available.compareTo(amount)>=0)
							{
								BigDecimal amount3 = gl.getAmount3();
								if(amount3 == null)
									amount3 = Env.ZERO;
								gl.setAmount3(amount3.add(amount));
								BigDecimal amount8 = (BigDecimal)gl.get_Value("Amount8");
								if(amount8==null)
									amount8 = Env.ZERO;
								gl.set_CustomColumn("Amount8", amount8.add(amount));
								gl.save();
							}
							else
								throw new AdempiereException("No hay monto suficiente: Disponible "+available);
						}
					
					}


					cash.setDocStatus("IP");
					cash.processIt("CO");
					cash.save();

				}
			}
			else
				throw new AdempiereException("Error: Permisos de rol insuficientes");
		}
	   return "Procesado";
	}	//	doIt
	

}
