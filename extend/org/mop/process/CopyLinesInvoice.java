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
package org.mop.process;

import java.util.logging.Level;

import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
/**
 *	
 *	
 *  @author Italo Niñoles ininoles 
 *  @version $Id: CopyLinesInvoice.java $
 */
public class CopyLinesInvoice extends SvrProcess
{
	private int				p_ID_InvoiceLine = 0; 
	private int				p_qtyLines = 0;
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if (name.equals("qtyLine"))
				p_qtyLines = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		 p_ID_InvoiceLine=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_ID_InvoiceLine > 0)
		{	
			MInvoiceLine iLine = new MInvoiceLine(getCtx(), p_ID_InvoiceLine, get_TrxName());
			MInvoice inv = new MInvoice(getCtx(), iLine.getC_Invoice_ID(), get_TrxName());
			//int cantOldLines = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_InvoiceLine WHERE C_Invoice_ID ="+iLine.getC_Invoice_ID());
			int lineNo = DB.getSQLValue(get_TrxName(), "SELECT MAX(Line) FROM C_InvoiceLine WHERE C_Invoice_ID ="+iLine.getC_Invoice_ID());
			int count = 0;
			//se genera ciclo de lineas nuevas
			for(int a = 0; a<p_qtyLines; a++)
			{
				lineNo = lineNo+10; 
				MInvoiceLine iLineNew = new MInvoiceLine(inv);
				PO.copyValues (iLine, iLineNew);
				iLineNew.setC_Invoice_ID(inv.getC_Invoice_ID());
				iLineNew.setLine(lineNo);
				iLineNew.setInvoice(inv);
				iLineNew.setM_AttributeSetInstance_ID(0);
				iLineNew.setS_ResourceAssignment_ID(0);
				iLineNew.setProcessed(false);
				iLineNew.setAD_Org_ID(iLine.getAD_Org_ID());
				//cantOldLines++;
				if(iLine.getDescription() != null)					
					iLineNew.setDescription(iLine.getDescription());
				if (iLineNew.save(get_TrxName()))
					count++;
			}
			
			inv.saveEx();
		}
		return "Procesado";
	}	//	doIt
}
