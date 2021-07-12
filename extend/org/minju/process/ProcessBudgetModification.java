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
import java.sql.SQLException;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.model.X_GL_BudgetControlLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessPayment.java $
 *  Procesar Pago MINJU
 */

public class ProcessBudgetModification extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_Budget_ID = 0; 
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
		p_Budget_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_Budget_ID > 0)
		{
			X_GL_BudgetControlLine bu = new X_GL_BudgetControlLine(getCtx(), p_Budget_ID, get_TrxName());
			//seteo de nuevo estado al procesar
			//String newStatus = "DR";
			String newAction = "DR";	
			log.config("paction "+p_Action);
			if(bu.get_ValueAsString("DocStatus2").compareTo("DR") == 0 && p_Action.compareTo("CO") == 0
					&& IsDirectComplete(bu))
			{
				//newStatus = "CO";
				newAction = "CO";
			}
			else if(bu.get_Value("DocStatus2").toString().compareTo("DR") == 0 && p_Action.compareTo("CO") == 0
				&& !IsDirectComplete(bu))
			{
				//newStatus = "DP";
				newAction = "SE";
			}
			else if(bu.get_Value("DocStatus2").toString().compareTo("SE") == 0 && p_Action.compareTo("CO") == 0)
			{
				newAction = "AJ";
			}
			else if(bu.get_Value("DocStatus2").toString().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0)
			{
				newAction = "AN";
			}
			else if(bu.get_Value("DocStatus2").toString().compareTo("AN") == 0 && p_Action.compareTo("CO") == 0)
			{
				newAction = "CO";
			}
			else if(bu.get_Value("DocStatus2").toString().compareTo("SE") == 0 && p_Action.compareTo("VO") == 0)
			{
				newAction = "RJ";
			}
			else if(bu.get_Value("DocStatus2").toString().compareTo("AJ") == 0 && p_Action.compareTo("VO") == 0)
			{
				newAction = "RJ";
			}
			else if(bu.get_Value("DocStatus2").toString().compareTo("AN") == 0 && p_Action.compareTo("VO") == 0)
			{
				newAction = "RJ";
			}
			//validacion de rol
			int cant = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM AD_Document_Action_Access daa" +
					" INNER JOIN AD_Ref_List rl ON (daa.AD_Ref_List_ID = rl.AD_Ref_List_ID) " +
					" WHERE value = '"+newAction+"' AND AD_Role_ID = "+Env.getAD_Role_ID(getCtx())+
					" AND C_DocType_ID = "+bu.get_ValueAsInt("C_DocType_ID"));
			//procesar
			log.config("cantidad"+cant);
			if(cant > 0)
			{
				if(newAction.compareTo("CO") == 0)
				{	
					//validacion antes de completar
					BigDecimal amtR = (BigDecimal)bu.get_Value("Amount5");
					if(amtR == null || amtR.compareTo(Env.ZERO) != 0)
					{
						throw new AdempiereException("ERROR: Los montos solicitados y reasignados deben ser iguales");
					}
					//si ticket esta marcado funciona de forma antigua
					if(bu.get_ValueAsBoolean("UseDescription"))
					{
						bu.setGL_BudgetDetail_ID(bu.get_ValueAsInt("GL_BudgetDetailRef_ID"));
					}
					else
					{
						/**nueva forma, se busca otro registro con misma cuenta y categoria 
						para actualizar los montos**/
						int id_BCLine = DB.getSQLValue(get_TrxName(), "SELECT MAX(GL_BudgetControlLine_ID) FROM GL_BudgetControlLine " +
								" WHERE GL_BudgetDetail_ID="+bu.get_ValueAsInt("GL_BudgetDetailRef_ID")+
								" AND M_Product_Category_ID ="+bu.get_ValueAsInt("M_Product_Category_ID"));
						if(id_BCLine > 0)
						{
							X_GL_BudgetControlLine budLineBase = new X_GL_BudgetControlLine(getCtx(), id_BCLine, get_TrxName());
							//ininoles 27-11-2017 ya no se recalculara el monto planificado. Solo se actualiza el replanificado
							//budLineBase.setAmount(budLineBase.getAmount().add(bu.getAmount()));
							BigDecimal amt6 = (BigDecimal)budLineBase.get_Value("Amount6");
							if(amt6 == null)
								amt6 = Env.ZERO;							
							
							log.config(" amount6 es "+amt6.add(bu.getAmount()));
							budLineBase.set_CustomColumn("Amount6", amt6.add(bu.getAmount()));
							BigDecimal amt = budLineBase.getAmount();
							if(amt == null)
								amt = Env.ZERO;
							budLineBase.setAmount(amt.add(bu.getAmount()));
							budLineBase.save(get_TrxName());
							//se les resta a los presupuestos donde se saca
							String sql = "SELECT GL_BudgetControlLine_ID FROM GL_BudgetControlLine " +
									"WHERE GL_BudgetControlLineRef_ID = ?";
							PreparedStatement pstmt = null;
							ResultSet rs = null;
							try
							{
								pstmt = DB.prepareStatement(sql, get_TrxName());
								pstmt.setInt(1, bu.get_ID());
								rs = pstmt.executeQuery();
								while (rs.next())
								{
									X_GL_BudgetControlLine budLineBaseRes = new X_GL_BudgetControlLine(getCtx(), rs.getInt("GL_BudgetControlLine_ID"), get_TrxName());
									//se busca registro para la misma categoria de producto
									int id_BCLineRes = DB.getSQLValue(get_TrxName(), "SELECT MAX(GL_BudgetControlLine_ID) FROM GL_BudgetControlLine " +
											" WHERE GL_BudgetDetail_ID="+budLineBaseRes.get_ValueAsInt("GL_BudgetDetailRef_ID")+
											" AND M_Product_Category_ID ="+budLineBaseRes.get_ValueAsInt("M_Product_Category_ID"));
									
									if(id_BCLineRes > 0)
									{
										X_GL_BudgetControlLine budLRestar = new X_GL_BudgetControlLine(getCtx(), id_BCLineRes, get_TrxName());
										BigDecimal amt6r = (BigDecimal)budLRestar.get_Value("Amount6");
										if(amt6r == null)
											amt6r = Env.ZERO;
										log.config("sql de lineres SELECT MAX(GL_BudgetControlLine_ID) FROM GL_BudgetControlLine " +
											" WHERE GL_BudgetDetail_ID="+budLineBaseRes.get_ValueAsInt("GL_BudgetDetailRef_ID")+
											" AND M_Product_Category_ID ="+budLineBaseRes.get_ValueAsInt("M_Product_Category_ID"));
										log.config("amt6r es "+amt6r);
										log.config("amt6r "+amt6r.add(budLineBaseRes.getAmount()));
										budLRestar.set_CustomColumn("Amount6", amt6r.add(budLineBaseRes.getAmount()));
										BigDecimal amtr = budLRestar.getAmount();
										if(amtr == null)
											amtr = Env.ZERO;
										
										budLRestar.setAmount(amtr.add(budLineBaseRes.getAmount()));
										budLRestar.save(get_TrxName());
									}
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
						else// si no existe registro que use la forma antigua
						{
							bu.setGL_BudgetDetail_ID(bu.get_ValueAsInt("GL_BudgetDetailRef_ID"));
							BigDecimal amt6 = (BigDecimal)bu.get_Value("Amount6");
							if(amt6 == null)
								amt6 = Env.ZERO;
							bu.set_CustomColumn("Amount6", amt6.add(bu.getAmount()));						
						}
					}
					bu.set_CustomColumn("DocStatus2", newAction);
				}		
				else
				{
					if(newAction.compareTo("DR") != 0)
						bu.set_CustomColumn("DocStatus2", newAction);

				}
				bu.set_CustomColumn("DocStatus2", newAction);
				bu.save();
			}
			
			else
				throw new AdempiereException("Error: Permisos de rol insuficientes");
		}
	   return "Procesado";
	}	//	doIt
	private Boolean IsDirectComplete(X_GL_BudgetControlLine budget) 
	{
		int cantLine = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM GL_BudgetControlLine WHERE GL_BudgetControlLineRef_ID ="+budget.get_ID()
				+" AND IsActive = 'Y' AND GL_BudgetControlLine_ID <> "+budget.get_ID());
		if(cantLine > 0)
			return false;
		else
			return false;			
	}
	
}
