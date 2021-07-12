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

import java.util.Calendar;
import java.util.logging.Level;

import org.compiere.model.X_DM_PerformanceBond;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import java.sql.Timestamp;

/**
 *	
 *	
 *  @author Italo Niñoles ininoles
 *  @version $Id: ProcessExtension.java $
 */
public class ProcessExtension extends SvrProcess
{
	private Timestamp			p_DateExtension = null;
	private int				p_IDDocument = 0; 
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		 	ProcessInfoParameter[] para = getParameter();
			for (int i = 0; i < para.length; i++)
			{
				String name = para[i].getParameterName();
				
				if (name.equals("DateExtension"))
					p_DateExtension =para[i].getParameterAsTimestamp();
				else
					log.log(Level.SEVERE, "Unknown Parameter: " + name);
			}
			p_IDDocument = getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		String logM = "";
		if (p_IDDocument > 0)
		{
			X_DM_PerformanceBond doc = new X_DM_PerformanceBond(getCtx(), p_IDDocument, get_TrxName());
			
			if(p_DateExtension != null)
			{
				doc.setdateexpiration(p_DateExtension);
				doc.setDocStatus("BG10");
				//
				Calendar cal = Calendar.getInstance();
				cal.setTime(p_DateExtension);
				cal.add(Calendar.DATE,-30);
				doc.set_CustomColumn("SecondDateReminder", new Timestamp(cal.getTimeInMillis()));
				cal.add(Calendar.DATE,-30);
				doc.set_CustomColumn("FirstDateReminder", new Timestamp(cal.getTimeInMillis()));				
				doc.save(get_TrxName());
			}
		}
	   return "Procesado "+logM;
	   
	}	//	doIt	
}
