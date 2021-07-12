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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.ProductNotOnPriceListException;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProductPricing;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	
 *	
 */
public class GenerateSalesOrderFromReq extends SvrProcess
{	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	private int p_ID_BPartner ;
	private int p_ID_pList;
	private int p_ID_Hosp;
	
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
			else if(name.equals("CC_Hospitalization_ID"))
				p_ID_Hosp = para[i].getParameterAsInt();
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
		MProductPricing	pp1 = null;
		String sql = "SELECT rl.M_Product_ID,rl.qty,M_RequisitionLine_ID,0 as A_Locator_Use_ID,r.c_doctype_id FROM M_Requisition r " +
				" INNER JOIN M_RequisitionLine rl ON (r.M_Requisition_ID = rl.M_Requisition_ID )"+ 
				" WHERE r.C_Bpartner_ID = "+p_ID_BPartner+" AND CC_Hospitalization_ID = " +p_ID_Hosp+
				" AND rl.m_product_ID NOT IN (select m_product_ID from m_product where Chargeable = 'N')"+// ininoles se agrega validacion en base a campo Chargeable
				" UNION " +
				" SELECT M_Product_ID, " +
				" CASE WHEN (date_part('day', dateend -datestart) > 1) THEN date_part('day', dateend -datestart)" +
				" WHEN (date_part('day', dateend -datestart) < 1) AND (date_part('second', dateend -datestart) > 1) THEN 1" +
				" ELSE 0 END as qty,0,A_Locator_Use_ID,0 as c_doctype_id " +
				" FROM A_Locator_Use lu INNER JOIN M_Locator ml ON (lu.M_Locator_ID = ml.M_Locator_ID)" +
				" WHERE lu.C_Bpartner_ID = "+p_ID_BPartner+" AND CC_Hospitalization_ID = " +p_ID_Hosp; 
 			
			PreparedStatement pstmt = null;			
			ResultSet rs = null;
			//se generan lineas
			try
			{			
				pstmt = DB.prepareStatement (sql, get_TrxName());
				rs = pstmt.executeQuery();			
				int ID_Iva = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_Tax_ID) FROM C_Tax WHERE UPPER(Name) like '%IVA%'");
				if(ID_Iva < 0)
					ID_Iva = 2000002;
				int ID_Exe = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_Tax_ID) FROM C_Tax WHERE UPPER(Name) like '%EXENTO%'");
				if(ID_Exe < 0)
					ID_Exe = 2000003;
				while (rs.next())
				{
					//se crea cabecera
					if(rs.getInt("M_Product_ID") > 0 && rs.getBigDecimal("qty").compareTo(Env.ZERO) > 0)
					{
						Timestamp today= new Timestamp(System.currentTimeMillis());
						if(order == null)
						{
							order = new MOrder(getCtx(), 0, get_TrxName());
							order.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
							order.setC_DocType_ID(2000122);
							order.setC_BPartner_ID(p_ID_BPartner);
							order.setDateOrdered(today);
							int ID_BPLoc = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_BPartner_Location_ID) FROM C_BPartner_Location" +
									" WHERE IsActive = 'Y' AND C_BPartner_ID = "+p_ID_BPartner);
							if(ID_BPLoc > 0)
								order.setC_BPartner_Location_ID(ID_BPLoc);
							order.setM_PriceList_ID(p_ID_pList);
							order.save(get_TrxName());
						}
						//se genera linea con iva
						//validacion que exista producto en lista de precios
						//antes de guardar se valida si existe en lista de precios
//						Check if on Price list
						
						MOrderLine oLine = new MOrderLine(order);
						oLine.setM_Product_ID(rs.getInt("M_Product_ID"), true);
						oLine.setQty(rs.getBigDecimal("qty"));
						oLine.setPrice();
						oLine.setC_Tax_ID(ID_Iva);
						//seteo de ids ya usados
						if(rs.getInt("M_RequisitionLine_ID") > 0)
							oLine.set_CustomColumn("M_RequisitionLine_ID",rs.getInt("M_RequisitionLine_ID"));
						else if(rs.getInt("A_Locator_Use_ID") > 0)
							oLine.set_CustomColumn("A_Locator_Use_ID",rs.getInt("A_Locator_Use_ID"));
						oLine.setLineNetAmt();
						//se valida si existe producto en lista de precios
						pp1  = getProductPricing(oLine.getM_Product_ID(), order.getC_BPartner_ID(),
								oLine.getQtyEntered(), order.isSOTrx(),order.getM_PriceList_ID(),order.getDateOrdered());
						if (!pp1.isCalculated())
						{
							throw new AdempiereException("PRODUCTO "+oLine.getM_Product().getValue()+
									"-"+oLine.getM_Product().getName()+". NO EXISTE EN LISTA DE PRECIOS ");
						}
						
						oLine.save(get_TrxName());
						// solicitud de devolucion se cambia precio a negativo
						if(rs.getInt("C_DocType_ID") == 2000174)
						{
							//oLine.setPrice(oLine.getPriceEntered().negate());
							oLine.setQty(oLine.getQtyEntered().negate());
							oLine.saveEx(get_TrxName());
						}
						cant++;
						//Se genera linea exenta
						MProductPricing pp = getProductPricing(oLine.getM_Product_ID(), order.getC_BPartner_ID(),
								oLine.getQtyEntered(), order.isSOTrx(),order.getM_PriceList_ID(),order.getDateOrdered());
						if(pp.getPriceLimit().compareTo(Env.ZERO) > 0)
						{
							MOrderLine oLine2 = new MOrderLine(order);
							oLine2.setM_Product_ID(rs.getInt("M_Product_ID"), true);
							oLine2.setQty(rs.getBigDecimal("qty"));
							oLine2.setPrice();
							oLine2.setPrice(pp.getPriceLimit());
							oLine2.setC_Tax_ID(ID_Exe);
							//seteo de ids ya usados
							if(rs.getInt("M_RequisitionLine_ID") > 0)
								oLine2.set_CustomColumn("M_RequisitionLine_ID",rs.getInt("M_RequisitionLine_ID"));
							else if(rs.getInt("A_Locator_Use_ID") > 0)
								oLine2.set_CustomColumn("A_Locator_Use_ID",rs.getInt("A_Locator_Use_ID"));
							oLine2.save(get_TrxName());
							oLine2.setLineNetAmt();
							if(rs.getInt("C_DocType_ID") == 2000174)
							{
								//oLine2.setPrice(oLine2.getPriceEntered().negate());
								oLine.setQty(oLine.getQtyEntered().negate());
								oLine2.saveEx(get_TrxName());
							}
							cant++;
						}
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
	
	private MProductPricing getProductPricing (int ID_product,int ID_BPartner,BigDecimal qtyOrdered,
			boolean IssoTrx, int M_PriceList_ID, Timestamp date)
	{
		MProductPricing m_productPrice = new MProductPricing (ID_product, 
				ID_BPartner, qtyOrdered, IssoTrx);
		m_productPrice.setM_PriceList_ID(M_PriceList_ID);
		m_productPrice.setPriceDate(date);
		m_productPrice.calculatePrice();
		return m_productPrice;
	}	//	getProductPrice
}	//	Replenish

