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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.model.X_CC_ShiftChange;
import org.compiere.model.X_CC_Evaluation;
import org.compiere.model.X_CC_Hospitalization;
/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessCCShiftChange.java $
 */

public class ProcessCCShiftChange extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_AdmDis_ID = 0; 
	private String				p_Action = "PR";
	private int 			p_user_id = 0;
	private int 			p_user2_id = 0;
	private Timestamp 		p_date;
 
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
			else if(name.equals("AD_User_ID"))
				p_user_id = para[i].getParameterAsInt();		
			else if(name.equals("AD_User2_ID"))
				p_user2_id = para[i].getParameterAsInt();
			else if(name.equals("Date1"))
				p_date = para[i].getParameterAsTimestamp();
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
		int counter = 0;

		if (p_user_id > 0 && p_user2_id > 0)
		{
			String sql = "SELECT CC_Hospitalization_ID from CC_Hospitalization WHERE processed='N' and isreadonly1='N'";
			
			PreparedStatement pstmt = null;			
			ResultSet rs = null;

			try
			{	
				pstmt = DB.prepareStatement (sql, get_TrxName());

				rs = pstmt.executeQuery();
				
				while (rs.next())
				{
					if(rs.getInt("CC_Hospitalization_ID") > 0)
					{
						int hosp_id = rs.getInt("CC_Hospitalization_ID");
						String sqlevolution = "SELECT coalesce(max(cc_Evaluation_id),0) FROM CC_Evaluation " +
								" WHERE cc_hospitalization_id = ?";
						int eval_id = DB.getSQLValue(get_TrxName(), sqlevolution, hosp_id);
						
						if(eval_id > 0)
						{
							X_CC_Evaluation eval = new X_CC_Evaluation(getCtx(),eval_id, get_TrxName());
							X_CC_ShiftChange shift = new X_CC_ShiftChange(getCtx(),0,get_TrxName());
							
							if(eval.getAD_User_ID() != p_user_id)
								continue;
							shift.setDate1(p_date);
							shift.setAD_User_ID(p_user_id);
							shift.setAD_User2_ID(p_user2_id);
							shift.setC_BPartner_ID(eval.getC_BPartner_ID());
							shift.setCC_Hospitalization_ID(hosp_id);
							shift.setPlanningHorizon(eval.getPlanningHorizon());
							shift.setDescription1(eval.getDescription());
							shift.setType("ME");
							shift.saveEx();
							counter++;
							
						}
						else
						{
							
							X_CC_Hospitalization hosp = new X_CC_Hospitalization(getCtx(), hosp_id, get_TrxName());
							X_CC_ShiftChange shift = new X_CC_ShiftChange(getCtx(),0,get_TrxName());
							
							if(hosp.getAD_User_ID() != p_user_id)
								continue;
							shift.setDate1(p_date);
							shift.setAD_User_ID(p_user_id);
							shift.setAD_User2_ID(p_user2_id);
							shift.setC_BPartner_ID(hosp.getC_BPartner_ID());
							shift.setCC_Hospitalization_ID(hosp_id);
							shift.setPlanningHorizon(hosp.getLocationComment());
							shift.setDescription1(hosp.getDescriptionURL());
							shift.setType("ME");

							shift.saveEx();
							counter++;
							
						}
					
					}
				}

			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, e.getMessage(), e);
			}
			finally
			{
				pstmt.close(); rs.close();
				pstmt=null; rs=null;
			}

	

			
		}	
	   return "Se ha generado "+counter+" entrega(s) de turno";
	}	//	doIt
}
