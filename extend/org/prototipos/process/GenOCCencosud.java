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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.*;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.*;
import org.ofb.utils.DateUtils;

/**
 *	
 *	
 *  @author italo niñoles ininoles
 *  @version $Id: ProcessPaymentRequest.java,v 1.2 2011/06/12 00:51:01  $
 */
public class GenOCCencosud extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	private Timestamp		p_dateTrx;
	private int 			p_ID_Locator;
	
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("DateTrx"))
				p_dateTrx = para[i].getParameterAsTimestamp();
			else if (name.equals("M_Locator_ID"))
				p_ID_Locator = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		//validacion si dia del proceso existe en la ubicacion
		MLocator loc = new MLocator(getCtx(), p_ID_Locator, get_TrxName());
		int noInsert = 0;
		if(p_dateTrx != null)
		{
			int dayWeek =  p_dateTrx.getDay()+1;
			String dayW = "0"+dayWeek;
			int exist = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM M_Inventory " +
					" WHERE IsActive = 'Y' AND M_Locator_ID = "+p_ID_Locator+" AND DayOrder = '"+dayW+"'");
			if(exist > 0)//proceso sigue
			{
//				Go through Order Records w/o
				StringBuffer sql = new StringBuffer("SELECT r.M_product_ID,r.Level_Min,p.C_BPartner_ID" +
						" FROM M_Replenish r" +
						" LEFT JOIN M_Product_PO p ON (r.M_Product_ID = p.M_Product_ID)" +
						" WHERE r.IsActive = 'Y' AND r.IsActive='Y' AND M_Locator_ID = "+p_ID_Locator+" ORDER BY p.C_BPartner_ID DESC");
				try
				{
					PreparedStatement pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
					ResultSet rs = pstmt.executeQuery ();
					//
					int oldC_BPartner_ID = 0;
					//
					MOrder order = null;
					int lineNo = 0;
					//se le suman dias a la orden
					int cantD = DB.getSQLValue(get_TrxName(), "SELECT MAX(Days) FROM M_Inventory " +
							" WHERE IsActive = 'Y' AND M_Locator_ID = "+p_ID_Locator+" AND DayOrder = '"+dayW+"'");
					Timestamp newDate = p_dateTrx;
					if(cantD > 0)
					{
						Calendar cal = Calendar.getInstance();
						cal.setTimeInMillis(p_dateTrx.getTime());
						cal.add(Calendar.DATE,cantD);
						newDate = new Timestamp(cal.getTimeInMillis());	
					}	
					while (rs.next ())
					{
						//	New Order
						if (oldC_BPartner_ID != rs.getInt("C_Bpartner_ID"))
						{
							order = new MOrder (getCtx(), 0, get_TrxName());
							order.setClientOrg (Env.getAD_Client_ID(getCtx()),Env.getAD_Org_ID(getCtx()));
							//order.setClientOrg (Env.getAD_Client_ID(getCtx()),2000011);
							//order.setAD_Org_ID(2000011);
							order.setC_DocTypeTarget_ID(MDocType.getDocType("POO", Env.getAD_Org_ID(getCtx())));
							order.setIsSOTrx(false);
							order.setC_BPartner_ID(rs.getInt("C_Bpartner_ID"));
							order.setAD_User_ID(Env.getAD_User_ID(getCtx()));
							//	Bill Partner
							order.setBill_BPartner_ID(rs.getInt("C_Bpartner_ID"));
							//
							order.setDescription("Pedido Generado automaticamente");
							order.setM_PriceList_ID(2000005);							
							order.setM_Warehouse_ID(loc.getM_Warehouse_ID());
							order.setSalesRep_ID(2000012);
							//
							order.setAD_OrgTrx_ID(Env.getAD_Org_ID(getCtx()));
													
							order.setDateOrdered(newDate);
							order.setDatePromised(newDate);
							order.setDateAcct(newDate);
							order.setC_ConversionType_ID(114);
							order.set_CustomColumn("M_Locator_ID", loc.get_ID());
							//
							order.saveEx(get_TrxName());
							noInsert++;
							lineNo = 10;
						}
						//misma orden
//						New OrderLine
						//calculo de cantidad
						//solo se crea orden si cantidad es > a 0
						BigDecimal qtyTotal = Env.ZERO;
						BigDecimal qtyMin = rs.getBigDecimal("level_min");
						BigDecimal qtyActual = DB.getSQLValueBD(get_TrxName(), "select bomqtyonhand(M_Product_ID,"+loc.getM_Warehouse_ID()+","+loc.get_ID()+") FROM M_Product WHERE M_Product_ID = "+rs.getInt("M_Product_ID"));
						BigDecimal qtyReserved = DB.getSQLValueBD(get_TrxName(),"SELECT SUM(ol.qtyReserved) FROM C_OrderLine ol" +
								" INNER JOIN C_Order co ON (ol.C_Order_ID = co.C_Order_ID)" +
								" WHERE ol.IsActive = 'Y' AND M_Product_ID = "+rs.getInt("M_Product_ID")+
								" AND co.M_Locator_ID = "+loc.get_ID()+
								" AND co.DateOrdered < ? ",newDate);
						BigDecimal qtyFore = DB.getSQLValueBD(get_TrxName(),"SELECT COALESCE(SUM(qty),0) FROM PP_ForecastDefinitionLine f" +
								" WHERE f.IsActive = 'Y' AND f.M_Product_ID = "+rs.getInt("M_Product_ID")+
								" AND f.M_Locator_ID = "+loc.get_ID()+
								" AND f.Date1 BETWEEN ? AND ?",DateUtils.today(), p_dateTrx);
						if(qtyReserved == null)
							qtyReserved = Env.ZERO;
						if(qtyFore == null)
							qtyFore = Env.ZERO;
						if(qtyMin == null)
							qtyMin = Env.ZERO;
						qtyTotal = qtyMin;
						qtyTotal = qtyTotal.add(qtyFore);
						qtyTotal = qtyTotal.subtract(qtyReserved);						
						qtyTotal = qtyTotal.subtract(qtyActual);
						
						if(qtyTotal.compareTo(Env.ZERO) <0)
							qtyTotal = Env.ZERO;
						if(qtyTotal.compareTo(Env.ZERO) > 0)
						{
							MOrderLine line = new MOrderLine (order);
							line.setLine(lineNo);
							lineNo += 10;
							line.setM_Product_ID(rs.getInt("M_Product_ID"), true);
							
							line.setQty(qtyTotal);
							//
							line.setPrice();
							line.setTax();
							line.setLineNetAmt();						
							line.setC_Tax_ID(2000002);//siempre es IVA				
							line.saveEx(get_TrxName());
						}
						oldC_BPartner_ID = rs.getInt("C_Bpartner_ID");
					}
					rs.close();
					pstmt.close();
				}
				catch (Exception e)
				{
					log.log(Level.SEVERE, "Order - " + sql.toString(), e);
				}
			}
			else
				throw new AdempiereException("ERROR: Ubicación no puede hacer pedido el dia señalado");
			
			
		}
		return "Se han generado "+noInsert+" ordenes de compra";
		
	}	//	doIt


	
}	//	CopyOrder
