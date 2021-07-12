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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.model.MOrder;
import org.compiere.model.X_I_OrderMasive;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
/**
 *	Import Order from I_Order
 *  @author Oscar Gomez
 * 			<li>BF [ 2936629 ] Error when creating bpartner in the importation order
 * 			<li>https://sourceforge.net/tracker/?func=detail&aid=2936629&group_id=176962&atid=879332
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ImportOrder.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class ImportVoidOrder extends SvrProcess
{
	/**	Client to be imported to		*/
	private int				m_AD_Client_ID = 0;
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		
	}	//	prepare


	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		StringBuffer sql = null;
		int no = 0;
		String clientCheck = " AND AD_Client_ID=" + m_AD_Client_ID;
		//	Go through Order Records w/o
		sql = new StringBuffer ("SELECT * FROM I_OrderMasive "
			  + "WHERE I_IsImported='N' AND IsError = 'N'").append (clientCheck)
			.append(" ORDER BY DocumentNo");
		try
		{
			PreparedStatement pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery ();
			
			MOrder order = null;
			while (rs.next ())
			{
				X_I_OrderMasive imp = new X_I_OrderMasive (getCtx (), rs.getInt("I_OrderMasive_ID"), get_TrxName());
				int ID_Order = 0;
				if(imp.getDocumentNo()!= null)
				{
					ID_Order = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_Order_ID) FROM C_Order WHERE DocumentNo like '"+imp.getDocumentNo()+"'");
					if(ID_Order > 0)
					{
						order = new MOrder(getCtx(), ID_Order, get_TrxName());
						order.setDocAction("VO");
						if(order.processIt ("VO"))
							no++;
						order.saveEx(get_TrxName());
						//actualizamos numero de documento
						int cant = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_Order WHERE DocumentNo like '"+order.getDocumentNo()+"_1' ");
						if(cant > 0)
							DB.executeUpdate("UPDATE C_Order SET DocumentNo = '"+order.getDocumentNo()+"_2' WHERE C_Order_ID = "+order.get_ID(),get_TrxName());
						else
							DB.executeUpdate("UPDATE C_Order SET DocumentNo = '"+order.getDocumentNo()+"_1' WHERE C_Order_ID = "+order.get_ID(),get_TrxName());
					}
				}				
			}			
			rs.close();
			pstmt.close();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Order - " + sql.toString(), e);
		}
		addLog (0, null, new BigDecimal (no), "Ordenes: Anuladas");		
		return "#" + no;
	}	//	doIt
}	//	ImportOrder
