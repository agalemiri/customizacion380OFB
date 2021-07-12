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
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MInvoice;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessInvoice.java $
 *  Genera id__usuario_reloj para un c_bpartner dado.
 */

public class GenerateIDUsuarioReloj extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_BPartner = 0; 

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if (name.equals("C_BPartner"))
				p_BPartner = ((BigDecimal)para[i].getParameter()).intValue();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_BPartner=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_BPartner > 0)
		{
			MBPartner bp = new MBPartner(getCtx(), p_BPartner, get_TrxName());
			String idactual = bp.get_ValueAsString("ID_Usuario_Reloj");
			
			if(idactual == null || idactual.compareTo("")==0)
			{
				String lastid = "SELECT max(cast(id_usuario_reloj as integer)) as idusuarioreloj from c_bpartner where id_usuario_reloj is not null";
				log.config("last id "+lastid);
				int idactualint = 0;
				
				PreparedStatement pstmt = DB.prepareStatement(lastid, get_TrxName());
				ResultSet rs = pstmt.executeQuery();
			
				while (rs.next())
				{
					if(rs.getString("idusuarioreloj") == null)
						idactualint = 0;
					else
						idactualint = Integer.parseInt(rs.getString("idusuarioreloj"));
					log.config("id varchar "+rs.getInt("idusuarioreloj"));
					log.config("id numer "+idactualint);
				}
				

				idactualint++;
				String newid = Integer.toString(idactualint);
				bp.set_CustomColumn("ID_Usuario_Reloj", newid);
				bp.saveEx();

			}
		}
	   return "Procesado";
	}	//	doIt
}
