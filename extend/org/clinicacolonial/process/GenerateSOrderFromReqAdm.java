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
 * Contributor(s): Chris Farley - northernbrewer                              *
 *****************************************************************************/
package org.clinicacolonial.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	
 *	
 */
public class GenerateSOrderFromReqAdm extends SvrProcess
{	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	private int p_ID_BPartner ;
	private int p_ID_pList;
	private Timestamp p_dateFrom;
	private Timestamp p_dateTo;
			
	protected void prepare()
	{	
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if(name.equals("C_BPartner_ID"))
				p_ID_BPartner = para[i].getParameterAsInt();
			else if(name.equals("M_PriceList_ID"))
				p_ID_pList = para[i].getParameterAsInt();
			else if(name.equals("DateDoc"))
			{
				p_dateFrom = para[i].getParameterAsTimestamp();
				p_dateTo = para[i].getParameterToAsTimestamp();
			}
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{	
		int cant = 0;
		MOrder order = null;
		String sql = "SELECT rl.M_Product_ID,rl.qty,M_RequisitionLine_ID,0 as A_Locator_Use_ID  FROM M_Requisition r " +
				" INNER JOIN M_RequisitionLine rl ON (r.M_Requisition_ID = rl.M_Requisition_ID ) " +
				" WHERE r.C_Bpartner_ID = "+p_ID_BPartner+" AND r.DateDoc BETWEEN ? AND ? " +
				" UNION " +
				" SELECT M_Product_ID, " +
				" CASE WHEN (date_part('day', dateend -datestart) > 1) THEN date_part('day', dateend -datestart)" +
				" WHEN (date_part('day', dateend -datestart) < 1) AND (date_part('second', dateend -datestart) > 1) THEN 1" +
				" ELSE 0 END as qty,0,A_Locator_Use_ID " +
				" FROM A_Locator_Use lu INNER JOIN M_Locator ml ON (lu.M_Locator_ID = ml.M_Locator_ID)" +
				" WHERE lu.C_Bpartner_ID = "+p_ID_BPartner+" AND datestart BETWEEN ? AND ?"; 
 			
			PreparedStatement pstmt = null;			
			ResultSet rs = null;
			//se generan lineas
			try
			{			
				pstmt = DB.prepareStatement (sql, get_TrxName());
				pstmt.setTimestamp(1, p_dateFrom);
				pstmt.setTimestamp(2, p_dateTo);
				pstmt.setTimestamp(3, p_dateFrom);
				pstmt.setTimestamp(4, p_dateTo);
				rs = pstmt.executeQuery();				
				while (rs.next())
				{
					//se crea cabecera
					if(rs.getInt("M_Product_ID") > 0 && rs.getBigDecimal("qty").compareTo(Env.ZERO) > 0)
					{
						if(order == null)
						{
							order = new MOrder(getCtx(), 0, get_TrxName());
							order.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
							order.setC_DocType_ID(2000122);
							order.setC_BPartner_ID(p_ID_BPartner);
							order.setDateOrdered(p_dateTo);
							int ID_BPLoc = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_BPartner_Location_ID) FROM C_BPartner_Location" +
									" WHERE IsActive = 'Y' AND C_BPartner_ID = "+p_ID_BPartner);
							if(ID_BPLoc > 0)
								order.setC_BPartner_Location_ID(ID_BPLoc);
							order.setM_PriceList_ID(p_ID_pList);
							order.save(get_TrxName());
						}
						MOrderLine oLine = new MOrderLine(order);
						oLine.setM_Product_ID(rs.getInt("M_Product_ID"), true);
						oLine.setQty(rs.getBigDecimal("qty"));
						oLine.setPrice();
						oLine.setTax();
						//seteo de ids ya usados
						if(rs.getInt("M_RequisitionLine_ID") > 0)
							oLine.set_CustomColumn("M_RequisitionLine_ID",rs.getInt("M_RequisitionLine_ID"));
						else if(rs.getInt("A_Locator_Use_ID") > 0)
							oLine.set_CustomColumn("A_Locator_Use_ID",rs.getInt("A_Locator_Use_ID"));
						oLine.save(get_TrxName());
						cant++;
					}
				}
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, e.getMessage(), e);
			}
			finally
			{
				pstmt.close(); rs.close();
				pstmt=null; rs=null;
			}
			return "Se ha generado nota de venta "+order.getDocumentNo()+" con "+cant+" lineas ";
	}	//	doIt
}	//	Replenish

