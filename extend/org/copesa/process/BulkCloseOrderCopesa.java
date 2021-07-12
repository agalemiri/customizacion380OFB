/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
package org.copesa.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.copesa.model.COPESAOrderOps;
/**
 *	Generate Invoices
 *	
 *  @author Jorg Janke
 *  @version $Id: InvoiceGenerate.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class BulkCloseOrderCopesa extends SvrProcess
{
	/** Order Document Action	*/
	private Timestamp	p_FechaTermino = null;

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
			else if (name.equals("FechaTermino"))
				p_FechaTermino = (Timestamp)para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}

		if (p_FechaTermino == null)
			p_FechaTermino = new Timestamp(System.currentTimeMillis());
		
	}	//	prepare

	/**
	 * 	Schedule order closing
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		log.info( "Inicio proceso cierre de contratos (FechaTermino=" + p_FechaTermino + ")");
		
		String sql = "select c_order_id from c_order where docstatus = 'CO' and FechaTermino <= ?";
	    PreparedStatement pstmt = DB.prepareStatement(sql, get_TrxName());
	    pstmt.setTimestamp(1, p_FechaTermino);
	    ResultSet rs = pstmt.executeQuery();
	    int count = 0;
	    while ( rs.next() )
	    {	
	    	int c_order_id = rs.getInt(1);
			log.info(COPESAOrderOps.closeOrder(c_order_id, get_TrxName(), getCtx()));
			count++;
	    }	
	    rs.close();
	    pstmt.close();
	    
		return "Proceso de cierre de documentos finalizado (Cantidad de contratos=" + count + ")";
	}	//	doIt

}	
