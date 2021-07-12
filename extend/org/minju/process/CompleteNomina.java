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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.eevolution.model.X_HR_Process;

/**
 *	
 *	
 *  @author Italo Niñoles ininoles
 *  @version $Id: ProcessNomina.java $
 */
public class CompleteNomina extends SvrProcess
{
	private String			p_DocStatus = null;
	private int				p_HR_Process = 0; 
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		 	ProcessInfoParameter[] para = getParameter();
			for (int i = 0; i < para.length; i++)
			{
				String name = para[i].getParameterName();
				
				if (name.equals("DocStatus"))
					p_DocStatus = (String) para[i].getParameter();
				else
					log.log(Level.SEVERE, "Unknown Parameter: " + name);
			}
			p_HR_Process=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_HR_Process > 0)
		{
			X_HR_Process pro = new X_HR_Process(getCtx(),p_HR_Process, get_TrxName());
			//ciclo de detalle de proceso
			if(p_DocStatus.compareTo("CO") == 0)
			{
				String sqlBP = "SELECT * FROM HR_ProcessBP bp " +
						" INNER JOIN C_BPartner c ON (bp.C_BPartner_ID = c.C_BPartner_ID)" +
						" WHERE bp.IsActive = 'Y' AND HR_Process_ID = ?";
				PreparedStatement pstmt = DB.prepareStatement(sqlBP, get_TrxName());
				pstmt.setInt(1, p_HR_Process);
				ResultSet rs = pstmt.executeQuery();			
				while(rs.next())
				{
					//se busca si existe algun registro ya existente completo
					int cant = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM HR_ProcessBP bp" +
							" INNER JOIN HR_Process pr ON (bp.HR_Process_ID = pr.HR_Process_ID) " +
							" WHERE bp.C_BPartner_ID="+rs.getInt("C_BPartner_ID")+" AND EXTRACT(month FROM dateAcct)="+(pro.getDateAcct().getMonth()+1)+
							" AND bp.HR_ProcessBP_ID <> "+rs.getInt("HR_ProcessBP_ID")+" AND bp.Processed = 'Y' AND pr.DocStatus IN ('CO')");
					
					if(cant > 0)
						throw new AdempiereException("Error: El funcionario "+rs.getString("Name")+" ya tiene una nomina para el mismo mes");
				}
			}
			//se procesan las lineas de detalle
			DB.executeUpdate("UPDATE HR_ProcessBPDetail SET Processed = 'Y' " +
					" WHERE  HR_ProcessBP_ID IN (SELECT HR_ProcessBP_ID FROM HR_ProcessBP " +
					" WHERE  HR_Process_ID = "+p_HR_Process+")",get_TrxName());
			
			//se procesa pestaña datos proceso
			DB.executeUpdate("UPDATE HR_ProcessBP SET Processed = 'Y' WHERE  HR_Process_ID="+p_HR_Process,get_TrxName());
			pro.setDocStatus(p_DocStatus);
			pro.setProcessed(true);
			pro.save();
		}
		return "Procesado";
	   
	}	//	doIt
}
