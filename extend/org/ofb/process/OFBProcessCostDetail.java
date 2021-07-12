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
package org.ofb.process;

import java.sql.*;
import java.util.logging.*;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.*;
import org.compiere.model.OFBProductCost;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: OFBProcessCostDetail.java,v 1.2 2008/07/30 00:51:01 jjanke Exp $
 *  
 *  Process Cost Detail according to OFBProductCost
 */
public class OFBProcessCostDetail extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 
	/**	Product				*/
	private int 	p_M_Product_ID = 0; 
	
	private int 	p_M_Product_Category_ID=0;
	
	private int 	p_MInOut_ID = 0;
	
	private Timestamp p_from;
	
	private String p_CostingLevel="C";
	 
	 protected void prepare()
	{
		
		ProcessInfoParameter[] para = getParameter();
		/*for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("M_Product_ID"))
				p_M_Product_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Product_Category_ID"))
				p_M_Product_Category_ID = para[i].getParameterAsInt();
			else if (name.equals("DateFrom"))
				p_from = (Timestamp)para[i].getParameter();
			else if (name.equals("CostingLevel"))
				p_CostingLevel = (String)para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}*/
		
		
		p_MInOut_ID = getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		
		MInOut min = new MInOut(getCtx(), p_MInOut_ID, get_TrxName());
		MInOutLine[] minl = min.getLines();
		
		for(int i = 0; i< minl.length; i++)
		{
			MInOutLine ml = minl[i];
			OFBProductCost.processCost(ml.getM_Product_ID(), get_TrxName(), getCtx());
		}
	  	  
	  this.commitEx();
		
	   return "Procesado";
	}	//	doIt


	
}	//	ResetAcct
