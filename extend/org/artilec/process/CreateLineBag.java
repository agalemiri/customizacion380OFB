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
package org.artilec.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.EMail;
import org.compiere.model.MUser;

/**
 *	
 *	
 *  @author ininoles
 *  @version $Id: CreateLineBag.java $
 *  Procesar OC ARTILEC
 */

public class CreateLineBag extends SvrProcess
{
	private int					p_Order_ID = 0; 
	private BigDecimal			qty = Env.ONE;
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if (name.equals("Qty"))
				qty = para[i].getParameterAsBigDecimal();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_Order_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_Order_ID > 0)
		{
			MOrder order = new MOrder(getCtx(), p_Order_ID, get_TrxName());
			
			//20190603 mfrojas
			//modelo artilec solo es con productos (sin cargo)
			int ID_Prod = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Product_ID) " +
					" FROM M_Product WHERE Description like '%BOLSA%' and lower(description) not like '%descuento%'");
			int ID_Charge = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Product_ID) " +
					" FROM M_Product WHERE Description  like '%BOLSA%' and lower(description) like '%descuento%'");
			
			
			if(ID_Prod > 0 && ID_Charge > 0)
			{
				//se agrega linea con bolsa 
				MOrderLine oLineB = new MOrderLine(order); 
				oLineB.setM_Product_ID(ID_Prod);
				oLineB.setQty(qty);
				oLineB.setPrice();
				oLineB.setTax();
				oLineB.saveEx(get_TrxName());
				
				//se agrega linea con cargo
				MOrderLine oLineC = new MOrderLine(order); 
				//oLineC.setC_Charge_ID(ID_Charge);
				oLineC.setM_Product_ID(ID_Charge);
				oLineC.setQty(qty);
				oLineC.setPrice();
				oLineC.setTax();
				oLineC.saveEx(get_TrxName());
			}
		}
	   return "Procesado";
	}	//	doIt
	

}
