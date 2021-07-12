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
package org.cencosud.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.model.*;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.ofb.utils.DateUtils;
/**
 *	
 *	
 *  @author italo niñoles ininoles
 *  @version $Id: GenOCCencosud.java,v 1.2 2011/06/12 00:51:01  $
 *  
 */
public class ProcessAllocatorVendor extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	private int 			p_ID_Order;
	
	protected void prepare()
	{	
		p_ID_Order = getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		MOrder order = new MOrder(getCtx(), p_ID_Order, get_TrxName());
		//revision de lineas para generar asignación
		//se crea nueva cabecera con la asignación 
		MOrder orderTo = new MOrder(getCtx(), 0, get_TrxName());
		orderTo = new MOrder (getCtx(), 0, get_TrxName());
		orderTo.setClientOrg (Env.getAD_Client_ID(getCtx()),Env.getAD_Org_ID(getCtx()));
		//order.setC_DocTypeTarget_ID(MDocType.getDocType("POO", Env.getAD_Org_ID(getCtx())));
		orderTo.setC_DocTypeTarget_ID(2000140);				
		orderTo.setIsSOTrx(false);
		orderTo.setC_BPartner_ID(2001562);
		orderTo.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		orderTo.setBill_BPartner_ID(2001562);
		orderTo.setDescription("Asignación generada OC:"+order.getDocumentNo());
		orderTo.setM_PriceList_ID(2000005);							
		orderTo.setM_Warehouse_ID(2000002);
		orderTo.setSalesRep_ID(2000012);
		orderTo.setAD_OrgTrx_ID(Env.getAD_Org_ID(getCtx()));
		orderTo.setDateOrdered(DateUtils.today());
		orderTo.setDatePromised(DateUtils.today());
		orderTo.setDateAcct(DateUtils.today());
		orderTo.setC_ConversionType_ID(114);
		orderTo.saveEx(get_TrxName());
		
		BigDecimal amtCal = null;
		MOrderLine[] lines = order.getLines(true, null);	//	Line is default
		for (int i = 0; i < lines.length; i++)
		{
			amtCal = Env.ZERO;
			MOrderLine line = lines[i];
			if(line.get_ValueAsInt("M_Locator_ID") == 2000183
					&& line.get_ValueAsInt("M_product_Group_ID") == 2000027)
			{
				log.config("log");
			}
			if(line.get_ValueAsInt("M_Locator_ID") > 0
					&& line.get_ValueAsInt("M_product_Group_ID") > 0)
			{
				//solo ciclo 3
				int ID_Category = DB.getSQLValue(get_TrxName(), "SELECT M_Product_Category_ID FROM M_Product_Group" +
						" WHERE M_product_Group_ID="+line.get_ValueAsInt("M_product_Group_ID"));
				if(ID_Category == 2000006)
				{
					//se busca cuota de distribución valida
					int ID_VendorCencosud = DB.getSQLValue(get_TrxName(), "SELECT MAX(vc.M_VendorCencosud_ID)" +
							" FROM M_VendorCencosud vc" +
							" INNER JOIN M_VendorCencosudLine vcl ON (vc.M_VendorCencosud_ID = vcl.M_VendorCencosud_ID)" +
							" WHERE IsValid = 'Y' AND M_Locator_ID="+line.get_ValueAsInt("M_Locator_ID")+
							" AND M_product_Group_ID="+line.get_ValueAsInt("M_product_Group_ID")+
							" AND vcl.DateStart <= ?" +
							" AND vcl.DateEnd >= ?",order.getDateOrdered(),order.getDateOrdered());
					if(ID_VendorCencosud > 0)
					{
						String sql = "SELECT Percentage, RutOT, RutOC FROM M_VendorCencosudLine " +
						" WHERE M_VendorCencosud_ID="+ID_VendorCencosud;			
						PreparedStatement pstmt = null;
						ResultSet rs = null;
						try
						{
							pstmt = DB.prepareStatement (sql, get_TrxName());
							rs = pstmt.executeQuery();
							while (rs.next())
							{ 
								amtCal = (BigDecimal)line.get_Value("qpc");
								amtCal = amtCal.multiply(rs.getBigDecimal("Percentage").divide(Env.ONEHUNDRED, 5, RoundingMode.HALF_EVEN));
								MOrderLine lineTo = new MOrderLine (orderTo);
					    		int NoLine = DB.getSQLValue(get_TrxName(), "SELECT MAX(Line) FROM C_OrderLine WHERE C_Order_ID="+orderTo.get_ID()); 
					    		lineTo.setLine(NoLine+10);
					    		lineTo.setM_Product_ID(2004195);
					    		lineTo.setQty(Env.ONE);
					    		lineTo.setPrice();
					    		lineTo.setTax();
					    		lineTo.setLineNetAmt();						
					    		lineTo.setC_Tax_ID(2000002);//siempre es IVA
					    		lineTo.setDescription("Asignación");
					    		lineTo.set_CustomColumn("M_Locator_ID",line.get_ValueAsInt("M_Locator_ID"));
					    		lineTo.set_CustomColumn("RutOT", rs.getString("RutOT"));
					    		lineTo.set_CustomColumn("RutOC", rs.getString("RutOC"));
					    		lineTo.set_CustomColumn("qtyCalculated",amtCal);
					    		lineTo.set_CustomColumn("M_Product_Group_ID",line.get_ValueAsInt("M_product_Group_ID"));
					    		lineTo.saveEx(get_TrxName());		
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
					}
					else
					{
						//si no existe registro se busca datos de la maestra activa
						int ID_GroupActive = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Product_GroupActive_ID) FROM M_Product_GroupActive" +
								" WHERE M_Locator_ID = "+line.get_ValueAsInt("M_Locator_ID")+
								" AND M_product_Group_ID = "+line.get_ValueAsInt("M_product_Group_ID"));
						if(ID_GroupActive > 0)
						{
							X_M_Product_GroupActive gActive = new X_M_Product_GroupActive(getCtx(), ID_GroupActive, get_TrxName());
							if(gActive.get_ValueAsString("MA3") != null && gActive.get_ValueAsString("MA3").trim().length() > 0
									&& gActive.get_ValueAsString("MA21") != null && gActive.get_ValueAsString("MA21").trim().length() > 0)
							{
								MOrderLine lineTo = new MOrderLine (orderTo);
					    		int NoLine = DB.getSQLValue(get_TrxName(), "SELECT MAX(Line) FROM C_OrderLine WHERE C_Order_ID="+orderTo.get_ID()); 
					    		lineTo.setLine(NoLine+10);
					    		lineTo.setM_Product_ID(2004195);
					    		lineTo.setQty(Env.ONE);
					    		lineTo.setPrice();
					    		lineTo.setTax();
					    		lineTo.setLineNetAmt();						
					    		lineTo.setC_Tax_ID(2000002);//siempre es IVA
					    		lineTo.setDescription("Asignación");
					    		lineTo.set_CustomColumn("M_Locator_ID",line.get_ValueAsInt("M_Locator_ID"));
					    		lineTo.set_CustomColumn("RutOT",gActive.get_ValueAsString("MA3"));
					    		lineTo.set_CustomColumn("RutOC",gActive.get_ValueAsString("MA21"));
					    		lineTo.set_CustomColumn("qtyCalculated",(BigDecimal)line.get_Value("qpc"));
					    		lineTo.set_CustomColumn("M_Product_Group_ID",line.get_ValueAsInt("M_product_Group_ID"));
					    		lineTo.saveEx(get_TrxName());		
							}
						}
					}
				}
				
			}
		}
		order.set_CustomColumn("C_OrderRef_ID", orderTo.get_ID());
		order.saveEx(get_TrxName());		
		return "PROCESADO";		
	}	//	doIt	
}	//	

