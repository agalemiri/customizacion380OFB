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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.*;

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
public class GenNVACHS extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	private int 			p_ID_BPartner;
	
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_BPartner_ID"))
				p_ID_BPartner = para[i].getParameterAsInt();
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
		StringBuffer sql = new StringBuffer(" SELECT * FROM i_achsfile1 WHERE C_BPartner_ID > 0 "
				+ " AND IsActive = 'Y' AND I_ACHSFile1_ID NOT IN(SELECT I_ACHSFile1_ID FROM C_OrderLine WHERE I_ACHSFile1_ID IS NOT NULL)");
		if(p_ID_BPartner > 0)
			sql.append(" AND C_BPartner_ID = "+p_ID_BPartner);
		sql.append(" ORDER BY C_BPartner_ID DESC");
		
		String sql3 = "";
		int noInsert = 0;
		int cantTotal =0;
		int cantAusente =0;
		BigDecimal Total = Env.ZERO;
		try
		{
			PreparedStatement pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery ();
			//
			int oldC_BPartner_ID = 0;
			//
			MOrder order = null;
			int lineNo = 0;
						
			while (rs.next ())
			{
				//	New Order
				if (oldC_BPartner_ID != rs.getInt("C_Bpartner_ID"))
				{
					//antes de crear nueca orden se calcula ausentismo
					if(order != null)
					{
						cantTotal = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_OrderLine "
								+ " WHERE C_Order_ID="+order.get_ID()+" AND status NOT IN ('L')");
						cantAusente = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_OrderLine "
								+ " WHERE C_Order_ID="+order.get_ID()+" AND status IN ('N')");
						Total = Env.ONEHUNDRED.multiply(new BigDecimal(cantAusente));
						Total = Total.divide(new BigDecimal(cantTotal),0,RoundingMode.HALF_EVEN);
						DB.executeUpdate("UPDATE C_Order SET Description=Description||' - Ausentismo"+Total.intValue()+"%' "
								+ " WHERE C_Order_ID = "+order.get_ID(), get_TrxName());
					}						
					//END
					order = new MOrder (getCtx(), 0, get_TrxName());
					order.setClientOrg (Env.getAD_Client_ID(getCtx()),Env.getAD_Org_ID(getCtx()));
					//order.setClientOrg (Env.getAD_Client_ID(getCtx()),2000011);
					//order.setAD_Org_ID(2000011);
					order.setC_DocTypeTarget_ID(MDocType.getDocType("SOO", Env.getAD_Org_ID(getCtx())));
					order.setIsSOTrx(true);
					order.setC_BPartner_ID(rs.getInt("C_Bpartner_ID"));
					order.setAD_User_ID(Env.getAD_User_ID(getCtx()));
					//	Bill Partner
					order.setBill_BPartner_ID(rs.getInt("C_Bpartner_ID"));
							//
					order.setDescription("Generado automaticamente");
					order.setM_PriceList_ID(2000030);							
					order.setM_Warehouse_ID(2000015);
					order.setSalesRep_ID(2000012);
					//
					order.setAD_OrgTrx_ID(Env.getAD_Org_ID(getCtx()));
					order.setDateOrdered(DateUtils.today());
					order.setDatePromised(DateUtils.today());
					order.setDateAcct(DateUtils.today());
					order.setC_ConversionType_ID(114);
					//
					order.saveEx(get_TrxName());
					noInsert++;
					lineNo = 10;
				}
				//misma orden
				//New OrderLine
				MOrderLine line = new MOrderLine (order);
				line.setLine(lineNo);
				lineNo += 10;
				if(rs.getInt("M_Product_ID") > 0)
					line.setM_Product_ID(rs.getInt("M_Product_ID"), true);
				else 
					line.setC_Charge_ID(2000006);				
				line.setQty(Env.ONE);
							//
				line.setPrice();
				line.setTax();
				line.setLineNetAmt();						
				line.setC_Tax_ID(2000002);//siempre es IVA
				
				//se agregan campos ACHS1
				line.set_CustomColumn("Episodio", rs.getString("Episodio"));
				line.set_CustomColumn("Prestacion", rs.getString("Prestacion"));
				line.set_CustomColumn("PrestacionGlosa", rs.getString("PrestacionGlosa"));
				line.set_CustomColumn("Rut_Paciente", rs.getString("Rut_Paciente"));
				line.set_CustomColumn("Name", rs.getString("Name"));
				line.set_CustomColumn("BP_Empresa", rs.getString("BP_Empresa"));
				line.set_CustomColumn("NombreEmpresa", rs.getString("NombreEmpresa"));				
				//se agregan campos ACHS3
				sql3 = "SELECT * FROM i_achsfile3 WHERE Rut_Empresa='"+rs.getString("Rut_Empresa")+"'"+
						" AND Rut_Paciente='"+rs.getString("Rut_Paciente")+"' AND FechaAtencion =?";
				PreparedStatement pstmt3 = DB.prepareStatement (sql3.toString(), get_TrxName());
				pstmt3.setTimestamp(1,rs.getTimestamp("FechaAtencion"));
				ResultSet rs3 = pstmt3.executeQuery ();
				if(rs3.next ())
				{
					if(rs3.getString("Status").compareToIgnoreCase("Atendido") == 0
							|| rs3.getString("Status").compareToIgnoreCase("Atendiéndose") == 0	
							|| rs3.getString("Status").compareToIgnoreCase("En sala de espera") == 0
							|| rs3.getString("Status").compareToIgnoreCase("No confirmado") == 0)
						line.set_CustomColumn("Status","A");
					else if (rs3.getString("Status").compareToIgnoreCase("Anulado") == 0)
					{
						line.set_CustomColumn("Status","L");
						line.setQty(Env.ZERO);
						line.setPrice(Env.ZERO);
					}
					else if (rs3.getString("Status").compareToIgnoreCase("No asiste") == 0)
						line.set_CustomColumn("Status","N");
					else
						line.set_CustomColumn("Status","A");
					line.set_CustomColumn("FechaAtencion",rs3.getTimestamp("FechaAtencion"));
					line.set_CustomColumn("Sucursal",rs3.getString("Sucursal"));
					line.set_CustomColumn("Nombre_Solicitante",rs3.getString("Nombre_Solicitante"));
					line.set_CustomColumn("CentroDeCosto",rs3.getString("CentroDeCosto"));
					line.set_CustomColumn("Mail_Solicitante",rs3.getString("Mail_Solicitante"));
				}
				else
				{
					line.set_CustomColumn("Status","N");
				}
				line.set_CustomColumn("I_ACHSFile1_ID",rs.getString("I_ACHSFile1_ID"));
				line.saveEx(get_TrxName());
				oldC_BPartner_ID = rs.getInt("C_Bpartner_ID");
			}
			//se calcula ausentismo para ultima NV
			cantTotal = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_OrderLine "
					+ " WHERE C_Order_ID="+order.get_ID()+" AND status NOT IN ('L')");
			cantAusente = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_OrderLine "
					+ " WHERE C_Order_ID="+order.get_ID()+" AND status IN ('N')");
			Total = Env.ONEHUNDRED.multiply(new BigDecimal(cantAusente));
			Total = Total.divide(new BigDecimal(cantTotal),0,RoundingMode.HALF_EVEN);
			DB.executeUpdate("UPDATE C_Order SET Description=Description||' - Ausentismo"+Total.intValue()+"%' "
					+ " WHERE C_Order_ID = "+order.get_ID(), get_TrxName());
			//END
			rs.close();
			pstmt.close();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Order - " + sql.toString(), e);
		}
		return "Se han generado "+noInsert+" notas";
	}	//	doIt
}	//	CopyOrder
