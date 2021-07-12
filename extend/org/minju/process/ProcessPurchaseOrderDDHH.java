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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrder;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessPurchaseOrderDDHH.java $
 *  Procesar OC MINJU
 */

public class ProcessPurchaseOrderDDHH extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_Order_ID = 0; 
	private String				p_Action = "PR";
	private String 			p_Message = "";

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
		p_Order_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_Order_ID > 0)
		{
			MOrder ord = new MOrder(getCtx(), p_Order_ID, get_TrxName());
			//seteo de nuevo estado al procesar
			String newStatus = "DR";
			String newAction = "DR";	
			log.config("paction "+p_Action);
			if(ord.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AE";
				newAction = "AE";
			}
			else if(ord.getDocStatus().compareTo("IP") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AE";
				newAction = "AE";
			}

			else if(ord.getDocStatus().compareTo("AE") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AB";
				newAction = "AB";
			}
			else if(ord.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}	
			else if(ord.getDocStatus().compareTo("IN") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}	

			//seteo de nuevo estado al rechazar
			else if(ord.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(ord.getDocStatus().compareTo("AE") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(ord.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}	

			else if(ord.getDocStatus().compareTo("IN") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}	

			else if(ord.getDocStatus().compareTo("IP") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}	

			//validacion de rol
			int cant = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM AD_Document_Action_Access daa" +
					" INNER JOIN AD_Ref_List rl ON (daa.AD_Ref_List_ID = rl.AD_Ref_List_ID) " +
					" WHERE value = '"+newAction+"' AND AD_Role_ID = "+Env.getAD_Role_ID(getCtx())+
					" AND C_DocType_ID = "+ord.getC_DocTypeTarget_ID());
			//procesar
			log.config("cantidad"+cant);
			if(cant > 0)
			{
				if(newAction.compareTo("CO") != 0)
				{
					ord.setDocStatus(newStatus);
					//ord.save();
					if(newAction.compareTo("DR") == 0)
					//req.setDescription(req.getDescription()+" *** Solicitud devuelta");
					{
						
						ord.set_CustomColumn("Comments2", "*** Solicitud devuelta ***");
						String prevmessage = DB.getSQLValueString(get_TrxName(), "SELECT comments3" +
								" from c_order where m_requisition_id = ? ", ord.get_ID());
						if(prevmessage != null)
							ord.set_CustomColumn("Comments3", prevmessage +" - " +p_Message);
						else
							ord.set_CustomColumn("Comments3", p_Message);

						//@mfrojas enviar correo cuando sea devuelta la solicitud.
						ord.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+ord.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 13);
						cst.setString(3, "-");
						cst.setInt(4, ord.get_ID());
						cst.execute();

					}
/*					else if(newAction.compareTo("SO") == 0)
					{
						
						
						ord.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+ord.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 25);
						cst.setString(3, "-");
						cst.setInt(4, ord.get_ID());
						cst.execute();

					}
					else if(newAction.compareTo("AC") == 0)
					{
						
						
						ord.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+ord.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 26);
						cst.setString(3, "-");
						cst.setInt(4, ord.get_ID());
						cst.execute();

					}
					else if(newAction.compareTo("AR") == 0)
					{
						
						
						ord.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+ord.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 27);
						cst.setString(3, "-");
						cst.setInt(4, ord.get_ID());
						cst.execute();

					}
					else if(newAction.compareTo("AA") == 0)
					{
						
						
						ord.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+ord.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 28);
						cst.setString(3, "-");
						cst.setInt(4, ord.get_ID());
						cst.execute();

					}*/
					else
						ord.save();

				}
				else if(newAction.compareTo("CO") == 0)
				{
					ord.setDocStatus("IP");
					ord.processIt("CO");
					
					//Validar si corresponde a una OC de una solicitud de compra de flujo de trabajo. 
					
					String queryworkflow = "SELECT isworkflow from m_requisition where m_Requisition_id in (select m_Requisition_id"
					+ " from m_requisitionline where m_requisitionline_id in (select m_Requisitionline_id from c_orderline where c_order_id = "+ord.get_ID()+"))";
					PreparedStatement ps = DB.prepareStatement(queryworkflow, ord.get_TrxName());
					
					ResultSet rs = ps.executeQuery();				
					int flag = 0;
					while (rs.next()) 
						if(rs.getString("isworkflow").compareTo("Y") == 0)
							flag = 1;
					
					if(flag==1)
					{
						int ID_Org = DB.getSQLValue(ord.get_TrxName(),"SELECT max(AD_OrgRef_ID) from m_requisition where m_Requisition_id in (select m_Requisition_id"
								+ " from m_requisitionline where m_requisitionline_id in (select m_Requisitionline_id from c_orderline where c_order_id = "+ord.get_ID()+"))");
						
						ord.setAD_Org_ID(ID_Org);
						DB.executeUpdate("UPDATE c_orderline set ad_org_id = "+ID_Org+" where c_order_id = "+ord.get_ID(),get_TrxName());


					}
					
					ord.save();
					//envío de correos.
					
					CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
					log.config("id "+ord.get_ID());
					cst.setInt(1, Env.getAD_Client_ID(getCtx()));
					cst.setInt(2, 7);
					cst.setString(3, "-");
					cst.setInt(4, ord.get_ID());
					cst.execute();
					
					
				}
			}
			else
				throw new AdempiereException("Error: Permisos de rol insuficientes");
		}
	   return "Procesado";
	}	//	doIt
}
