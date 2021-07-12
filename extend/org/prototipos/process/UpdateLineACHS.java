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
package org.prototipos.process;

import java.sql.PreparedStatement;
import java.util.logging.Level;

import org.compiere.model.MOrder;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 *	
 *	
 *  @author Italo Niñoles ininoles
 *  @version $Id: UpdateLineACHS.java $
 */
public class UpdateLineACHS extends SvrProcess
{	
	private int			ID_Order = 0;
	private String 		Rut_Paciente;
	private String 		P_name;
	private String 		Nombre_Solicitante;
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		 ProcessInfoParameter[] para = getParameter();
			for (int i = 0; i < para.length; i++)
			{
				String name = para[i].getParameterName();
				if (para[i].getParameter() == null)
					;
				else if (name.equals("Rut_Paciente"))
					Rut_Paciente = para[i].getParameterAsString();
				else if (name.equals("Name"))
					P_name = para[i].getParameterAsString();
				else if (name.equals("Nombre_Solicitante"))
					Nombre_Solicitante = para[i].getParameterAsString();
				else
					log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
			}
		 ID_Order = getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{			
		MOrder order = new MOrder(getCtx(), ID_Order, get_TrxName());
		//borrado por rut paciente
		if(Rut_Paciente != null && Rut_Paciente.trim().length() > 0)
		{
			PreparedStatement pstmt = null;
			pstmt = DB.prepareStatement("DELETE FROM C_OrderLine WHERE C_Order_ID="+order.get_ID()+
					" AND UPPER(Rut_Paciente) like '%"+Rut_Paciente.toUpperCase()+"%'", get_TrxName());
			pstmt.execute();
		}
		//borrado por nombre paciente
		if(P_name != null && P_name.trim().length() > 0)
		{
			PreparedStatement pstmt2 = null;
			pstmt2 = DB.prepareStatement("DELETE FROM C_OrderLine WHERE C_Order_ID="+order.get_ID()+
					" AND UPPER(name) like '%"+P_name.toUpperCase()+"%'", get_TrxName());
			pstmt2.execute();
		}
		if(Nombre_Solicitante != null && Nombre_Solicitante.trim().length() > 0)
		{
			PreparedStatement pstmt3 = null;
			pstmt3 = DB.prepareStatement("DELETE FROM C_OrderLine WHERE C_Order_ID="+order.get_ID()+
					" AND UPPER(Nombre_Solicitante) like '%"+Nombre_Solicitante.toUpperCase()+"%'", get_TrxName());
			pstmt3.execute();
		}		
		order.calculateTaxTotal();
		order.save(get_TrxName());
		
	    return "Actualiazo";
	}	//	doIt
}
