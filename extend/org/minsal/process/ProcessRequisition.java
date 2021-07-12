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
package org.minsal.process;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MRequisition;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessRequisition.java $
 *  Procesar Solicitud Materiales/Traspaso MINSAL
 */

public class ProcessRequisition extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_Requisition_ID = 0; 
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
		p_Requisition_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_Requisition_ID > 0)
		{
			MRequisition req = new MRequisition(getCtx(), p_Requisition_ID, get_TrxName());
			
			//seteo de nuevo estado al procesar
			String newStatus = "DR";
			String newAction = "DR";	
			log.config("paction "+p_Action);
			
			//Solicitud de movimiento debe pasar por IP y luego por CO
			if(req.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("CO") == 0 && 
					 req.getC_DocType().getDocBaseType().compareTo("RMV") == 0)
			{
				newStatus = "IP";
				newAction = "IP";
			}
			else if(req.getDocStatus().compareTo("IP") == 0 && p_Action.compareTo("CO") == 0 && 
					req.getC_DocType().getDocBaseType().compareTo("RMV") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}

			else if(req.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("CO") == 0 && 
					req.getC_DocType().getDocBaseType().compareTo("RRC") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}

			//seteo de nuevo estado al anular
			else if(req.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "VO";
				newAction = "VO";
			}
			else if(req.getDocStatus().compareTo("IP") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "VO";
				newAction = "VO";
			}
			
			//seteo de nuevo estado al rechazar
			else if(req.getDocStatus().compareTo("IP") == 0 && p_Action.compareTo("RJ") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}

			
			

			//validacion de rol
			
			int cant = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM AD_Document_Action_Access daa" +
					" INNER JOIN AD_Ref_List rl ON (daa.AD_Ref_List_ID = rl.AD_Ref_List_ID) " +
					" WHERE value = '"+newAction+"' AND AD_Role_ID = "+Env.getAD_Role_ID(getCtx())+
					" AND C_DocType_ID = "+req.getC_DocType_ID());
			
			//procesar
			
			log.config("cantidad"+cant);
			if(cant > 0)
			{
				if(newAction.compareTo("CO") != 0)
				{
					req.setDocStatus(newStatus);
					//ord.save();
					if(newAction.compareTo("VO") == 0)
						req.voidIt();
					
					req.save();

				}
				else if(newAction.compareTo("CO") == 0)
				{
					req.setDocStatus("IP");
					req.processIt("CO");
					
					req.save();
				}
			}
			else
				throw new AdempiereException("Error: Permisos de rol insuficientes");
		}
	   return "Procesado";
	}	//	doIt
}
