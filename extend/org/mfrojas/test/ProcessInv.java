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
package org.mfrojas.test;

import java.sql.CallableStatement;
import java.util.logging.*;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.*;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessOT.java,v 1.2 2008/06/12 00:51:01  $
 */
public class ProcessInv extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	private String P_DocAction;
	/*OT ID*/
	private int 			Record_ID;
	
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("DocAction"))
				P_DocAction = (String)para[i].getParameter();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
		
		Record_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		
		X_DM_Document doc=new X_DM_Document(Env.getCtx(), Record_ID,get_TrxName());
		
		//Validación para boletas de gaarantía
		if(doc.getdm_document_type().compareTo("PB") == 0)
		{
			String newStatus = "DR";
			String newAction = "DR";	
			log.config("paction "+P_DocAction);
			if(doc.getDocStatus().compareTo("DR") == 0 && P_DocAction.compareTo("CO") == 0)
			{
				newStatus = "A1";
				newAction = "A1";
			}
			else if(doc.getDocStatus().compareTo("IP") == 0 && P_DocAction.compareTo("CO") == 0)
			{
				newStatus = "A1";
				newAction = "A1";
			}

			else if(doc.getDocStatus().compareTo("A1") == 0 && P_DocAction.compareTo("CO") == 0)
			{
				newStatus = "A2";
				newAction = "A2";
			}
			else if(doc.getDocStatus().compareTo("A2") == 0 && P_DocAction.compareTo("CO") == 0)
			{
				newStatus = "A3";
				newAction = "A3";
			}	
			else if(doc.getDocStatus().compareTo("A3") == 0 && P_DocAction.compareTo("CO") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}	
			else if(doc.getDocStatus().compareTo("IN") == 0 && P_DocAction.compareTo("CO") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}	

			//seteo de nuevo estado al rechazar
			else if(doc.getDocStatus().compareTo("DR") == 0 && P_DocAction.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(doc.getDocStatus().compareTo("A1") == 0 && P_DocAction.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(doc.getDocStatus().compareTo("A2") == 0 && P_DocAction.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}	
			else if(doc.getDocStatus().compareTo("A3") == 0 && P_DocAction.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}	

			else if(doc.getDocStatus().compareTo("IN") == 0 && P_DocAction.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}	
			
			
			int cant = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM AD_Document_Action_Access daa" +
					" INNER JOIN AD_Ref_List rl ON (daa.AD_Ref_List_ID = rl.AD_Ref_List_ID) " +
					" WHERE value = '"+newAction+"' AND AD_Role_ID = "+Env.getAD_Role_ID(getCtx())+
					" AND C_DocType_ID = "+doc.getC_DocType_ID());
			//procesar
			log.config("cantidad"+cant);
			if(cant > 0)
			{
				if(newAction.compareTo("CO") != 0)
				{
					
					doc.setDocStatus(newStatus);
					doc.save();

					if(newStatus.compareTo("A1") == 0)
					{
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+doc.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 23);
						cst.setString(3, "-");
						cst.setInt(4, doc.get_ID());
						cst.execute();

					}
					if(newStatus.compareTo("A2") == 0)
					{
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+doc.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 24);
						cst.setString(3, "-");
						cst.setInt(4, doc.get_ID());
						cst.execute();

					}
					if(newStatus.compareTo("A3") == 0)
					{
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+doc.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 25);
						cst.setString(3, "-");
						cst.setInt(4, doc.get_ID());
						cst.execute();

					}

					//ord.save();

				}
				else if(newAction.compareTo("CO") == 0)
				{
					doc.setProcessed(true);
					doc.setDocStatus("CO");
					doc.save();
					CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
					log.config("id "+doc.get_ID());
					cst.setInt(1, Env.getAD_Client_ID(getCtx()));
					cst.setInt(2, 26);
					cst.setString(3, "-");
					cst.setInt(4, doc.get_ID());
					cst.execute();

					
				}
			}
			else
				throw new AdempiereException("Error: Permisos de rol insuficientes");

			

	
		}
		
		if(P_DocAction.equals("CO") && !doc.isProcessed() && doc.getdm_document_type().compareTo("PB") != 0)
		{

			doc.setProcessed(true);
			doc.setDocStatus("CO");
			doc.save();			
			return "Confirmado";
		}
		
		
		
		if(P_DocAction.equals("VO") && doc.isProcessed() && doc.getDocStatus().equals("CO")){
			doc.setProcessed(false);
			doc.setDocStatus("VO");
			doc.save();
			return "Anulado";
		}

			return "No es posible Cumplir la Accion ";
		
	}	//	doIt

	

	
}	//	CopyOrder
