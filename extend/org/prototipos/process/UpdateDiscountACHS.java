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

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.compiere.model.MBPartner;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	
 *	
 *  @author Italo Niñoles ininoles
 *  @version $Id: UpdateDiscountOrderLine.java $
 */
public class UpdateDiscountACHS extends SvrProcess
{	
	private int			ID_Order = 0;
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
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
		MOrderLine[] lines = order.getLines();
		MBPartner bpart = new MBPartner(getCtx(), order.getC_BPartner_ID(), get_TrxName());
		BigDecimal discount = (BigDecimal)bpart.get_Value("PotentialLifeTimeValue");
		BigDecimal limit = (BigDecimal)bpart.get_Value("AcqusitionCost");
		//se calcula ausentismo
		int cantTotal = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_OrderLine "
				+ " WHERE C_Order_ID="+order.get_ID()+" AND status NOT IN ('L')");
		int cantAusente = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_OrderLine "
				+ " WHERE C_Order_ID="+order.get_ID()+" AND status IN ('N')");
		BigDecimal Total = Env.ONEHUNDRED.multiply(new BigDecimal(cantAusente));
		Total = Total.divide(new BigDecimal(cantTotal),0,RoundingMode.HALF_EVEN);
		DB.executeUpdate("UPDATE C_Order SET Description=Description||' - Ausentismo"+Total.intValue()+"%' "
				+ " WHERE C_Order_ID = "+order.get_ID(), get_TrxName());
		
		int cant = 0;
		if(Total.compareTo(limit) <=0)
		{
			if(discount != null && discount.compareTo(Env.ZERO) != 0)
			{	
				for (int i = 0; i < lines.length; i++)
				{
					MOrderLine line = lines[i];
					BigDecimal fPrice = (BigDecimal)line.get_Value("ForeignPrice");
					BigDecimal amtDis = Env.ZERO;
					BigDecimal newPrice  = Env.ZERO;
					if(fPrice != null && fPrice.compareTo(Env.ZERO) != 0)
					{
						amtDis = fPrice.divide(Env.ONEHUNDRED);
						newPrice = fPrice;
					}
					else if(discount != null && discount.compareTo(Env.ZERO) == 0 
							&& fPrice != null && fPrice.compareTo(Env.ZERO) != 0)
					{
						line.set_CustomColumn("ForeignPrice", line.getPriceEntered());
						amtDis = line.getPriceEntered().divide(Env.ONEHUNDRED);
						newPrice = line.getPriceEntered();
					}
					else
					{
						line.set_CustomColumn("ForeignPrice", line.getPriceEntered());
						amtDis = line.getPriceEntered().divide(Env.ONEHUNDRED);
						newPrice = line.getPriceEntered();
					}
					amtDis = amtDis.multiply(discount);
					line.setPrice(newPrice.subtract(amtDis));
					line.setLineNetAmt();
					//line.setTax(); //mfrojas se comenta esta línea de código pues está sobreescribiendo (y cambiando) el impuesto.
					line.save();
					cant++;
				}
			}
		}
	    return "Se han actualizado "+cant+ " Lineas";
	}	//	doIt
}
