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
import org.compiere.model.X_GL_Reassignment;
import org.compiere.model.X_GL_ReassignmentLine;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessReassignment.java $
 *  Procesar Reasignacion MINJU
 */

public class ProcessReassignment extends SvrProcess
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
			X_GL_Reassignment re = new X_GL_Reassignment(getCtx(),p_Budget_ID,get_TrxName());
			
			X_GL_BudgetControlLine bu = new X_GL_BudgetControlLine(getCtx(), p_Budget_ID, get_TrxName());
			//seteo de nuevo estado al procesar
			//String newStatus = "DR";
			String newAction = "DR";	
			log.config("paction "+p_Action);
			/**
			 * 20181023 mfrojas
			 * Se cambia flujo. Borrador -> completo
			 */
			if(re.getDocStatus().compareTo("DR")==0 && p_Action.compareTo("CO") == 0)
			{
				//newAction = "SE";
				newAction = "CO";
			}
			else if(re.getDocStatus().compareTo("SE")==0 && p_Action.compareTo("CO") == 0)
			{
				//newAction = "AJ";
				newAction = "CO";
			}
			else if(re.getDocStatus().compareTo("AJ")==0 && p_Action.compareTo("CO") == 0)
			{
				//newAction = "AN";
				newAction = "CO";
			}
			else if(re.getDocStatus().compareTo("AN")==0 && p_Action.compareTo("CO") == 0)
			{
				newAction = "CO";
			}
			//validacion de rol
			int cant = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM AD_Document_Action_Access daa" +
					" INNER JOIN AD_Ref_List rl ON (daa.AD_Ref_List_ID = rl.AD_Ref_List_ID) " +
					" WHERE value = '"+newAction+"' AND AD_Role_ID = "+Env.getAD_Role_ID(getCtx())+
					" AND C_DocType_ID = "+re.get_ValueAsInt("C_DocType_ID"));
			//procesar
			log.config("cantidad"+cant);
			if(cant > 0)
			{
				if(newAction.compareTo("CO") != 0)
				{
					re.setDocStatus(newAction);
					if(newAction.compareTo("SE")==0)
					{
						re.set_CustomColumn("Amount2", re.getAmt());
					}
				}
				if(newAction.compareTo("CO") == 0)
				{	
					//validacion antes de completar
					
					//Definir si es una nueva linea o no lo es.
					re.set_CustomColumn("Amount2", re.getAmt());

					if(!re.get_ValueAsBoolean("IsNewLine"))
					{
						String sql = "UPDATE GL_BudgetControlLine SET Amount = Amount + "+re.getAmt()+", Amount6 = Amount6 + "+re.getAmt()+" WHERE " +
								" GL_BudgetControlLine_ID = "+re.getGL_BudgetControlLine_ID();
						DB.executeUpdate(sql,get_TrxName());
						
						
					}
					
					else
					{
						X_GL_BudgetControlLine bulineheader = new X_GL_BudgetControlLine(getCtx(),0,get_TrxName());
						bulineheader.setAmount(re.getAmt());
						if(re.getMode()!= null)
							bulineheader.setMode(re.getMode());
						if(re.getDescription()!=null)
							bulineheader.setDescription(re.getDescription());
						if(re.getProgram()!=null)
							bulineheader.setProgram(re.getProgram());
						bulineheader.setGL_BudgetDetail_ID(re.getGL_BudgetDetail_ID());
						bulineheader.setGL_BudgetControl_ID(re.getGL_BudgetControl_ID());
						bulineheader.setAmount(re.getAmt());
						bulineheader.setDescription(re.getDescription());
						bulineheader.setAmount2(Env.ZERO);
						bulineheader.setAD_Org_ID(re.getAD_Org_ID());
						//bulineheader.setC_ValidCombination_ID(re.getC_ValidCombination_ID());
						bulineheader.setC_ValidCombination_ID(re.getGL_BudgetDetail().getC_ValidCombination_ID());
						bulineheader.setAmount3(Env.ZERO);
						bulineheader.set_CustomColumn("Amount8", Env.ZERO);
						bulineheader.set_CustomColumn("M_Product_Category_ID", re.getM_Product_Category_ID());
						bulineheader.set_CustomColumn("Amount6", re.getAmt());
						bulineheader.saveEx();
						
						
					}
					
					//Obtener lineas de la reasignacion
					
					String sqllines = "SELECT GL_BudgetControlLineRef_ID, amt FROM GL_ReassignmentLine WHERE " +
							" GL_Reassignment_ID = "+re.get_ID();
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					pstmt = DB.prepareStatement(sqllines, get_TrxName());
					rs = pstmt.executeQuery();

					while(rs.next())
					{
						if(rs.getInt("GL_BudgetControlLineRef_ID")>0)
						{
							log.config("budget control line ref "+rs.getInt("GL_BudgetControlLineRef_ID"));
							X_GL_BudgetControlLine buline = new X_GL_BudgetControlLine(getCtx(),rs.getInt("GL_BudgetControlLineRef_ID"),get_TrxName());
							BigDecimal monto = Env.ZERO;
							monto = buline.getAmount();
							log.config("monto "+monto);
							buline.setAmount(buline.getAmount().subtract(rs.getBigDecimal("Amt")));
							BigDecimal assignment = Env.ZERO;
							assignment = (BigDecimal)buline.get_Value("Amount6");
							if(assignment == null)
								assignment = Env.ZERO;
							log.config("rs big decimal "+rs.getBigDecimal("Amt"));
							log.config("assignment "+assignment);
							BigDecimal montonuevo = assignment.add(rs.getBigDecimal("Amt"));
							log.config("monto nuevo "+montonuevo);
							buline.set_CustomColumn("Amount6", assignment.subtract(rs.getBigDecimal("Amt")));
							buline.saveEx();
						}
					}
					
					rs.close();
					pstmt.close();
					pstmt = null;
					re.setDocStatus("CO");
					re.setProcessed(true);
				}
				re.saveEx();

			}

				
			else
				throw new AdempiereException("Error: Permisos de rol insuficientes");
		}
	   return "Procesado";
	}	//	doIt
	
}
