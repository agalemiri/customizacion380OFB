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

import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.DocAction;
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
public class CloseOrderCopesa extends SvrProcess
{
	/** Order Document Action	*/
	private String		p_docAction = DocAction.ACTION_Close;
	private Timestamp	p_FechaTermino = null;
	private String 		p_RazonTermino = null;
	private Object[]	params;
	private Integer			p_C_Order_ID = 0;

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
			else if (name.equals("RazonTermino"))
				p_RazonTermino = (String)para[i].getParameter();
			else if (name.equals("C_Order_ID"))
				p_C_Order_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}

		if (p_FechaTermino == null)
			p_FechaTermino = new Timestamp(System.currentTimeMillis());
		
		params = new Object[3];
		params[0] = p_FechaTermino;
		params[1] = p_RazonTermino;
		params[2] = p_C_Order_ID;

	}	//	prepare

	/**
	 * 	Schedule order closing
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		log.info("FechaTermino=" + p_FechaTermino + ", C_Order_ID=" + p_C_Order_ID + ", DocAction=" + p_docAction);
			
		String sql = "UPDATE C_Order set FechaTermino = ?, RazonTermino = ? WHERE C_Order_ID = ?";
		DB.executeUpdateEx(sql, params, get_TrxName());
		
		if( p_FechaTermino.compareTo(new Timestamp(System.currentTimeMillis())) <= 0 )
			return COPESAOrderOps.closeOrder(p_C_Order_ID, get_TrxName(), getCtx());
		else
			return "Cierre de documento agendado";
	}	//	doIt
		
	
}	//	InvoiceGenerate
