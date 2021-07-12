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

import org.compiere.model.MProductPricing;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.ofb.utils.DateUtils;

/**
 *	
 *	
 */
public class GenerateRequisitionToday extends SvrProcess
{	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	private int p_ID_BPartner ;
	//private int p_ID_pList;
	//private int p_ID_Hosp;
	private Timestamp p_UseDateFrom;
	private Timestamp p_UseDateTo;
	
	protected void prepare()
	{	
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if(name.equals("C_BPartner_ID"))
				p_ID_BPartner = para[i].getParameterAsInt();
			/*else if(name.equals("M_PriceList_ID"))
				p_ID_pList = para[i].getParameterAsInt();*/		
			/*else if(name.equals("UseDate"))
			{
				p_UseDateFrom = para[i].getParameterAsTimestamp();
				p_UseDateTo = para[i].getParameterToAsTimestamp();
			}*/
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
		MRequisition req = null;
		int ID_lastBPartner = 0;
		String sql = "SELECT M_Product_ID, 1 as qty,A_Locator_Use_ID, dateend, datestart,lu.C_Bpartner_ID"
				+ " FROM A_Locator_Use lu"
				+ " INNER JOIN M_Locator ml ON (lu.M_Locator_ID = ml.M_Locator_ID)"
				+ " WHERE lu.C_Bpartner_ID IS NOT NULL "
				+ " AND dateStart < ? AND (dateEnd > ?  OR dateEnd IS NULL)"; 
 		if(p_ID_BPartner > 0)
 			sql = sql+" AND lu.C_Bpartner_ID = "+p_ID_BPartner;
 		sql+=" ORDER BY C_Bpartner_ID DESC";
 		PreparedStatement pstmt = null;			
		ResultSet rs = null;
 		
		p_UseDateFrom = DateUtils.today();
		p_UseDateTo = DateUtils.today();
 		Timestamp dateTrx = new Timestamp(p_UseDateFrom.getTime());
		while (dateTrx.compareTo(p_UseDateTo) <= 0)
		{
			pstmt = null;
			rs = null;
			req = null;
			ID_lastBPartner = 0;
			//se generan lineas
			try
			{			
				pstmt = DB.prepareStatement (sql, get_TrxName());
				pstmt.setTimestamp(1, dateTrx);
				pstmt.setTimestamp(2, dateTrx);
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
						if(ID_lastBPartner != rs.getInt("C_Bpartner_ID"))
						{
							req = new MRequisition(getCtx(), 0, get_TrxName());
							req.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
							req.setC_DocType_ID(2000154);
							req.set_CustomColumn("C_Bpartner_ID",rs.getInt("C_Bpartner_ID"));
							req.setDateDoc(dateTrx);
							req.setDateRequired(dateTrx);
							req.setM_PriceList_ID(2000005);
							req.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
							req.setHelp("Generado automaticamente");
							req.save(get_TrxName());
							cant++;
						}
						//se genera linea con iva
						MRequisitionLine rLine = new MRequisitionLine(req);
						rLine.setM_Product_ID(rs.getInt("M_Product_ID"));
						rLine.setQty(rs.getBigDecimal("qty"));
						rLine.setPrice();
						//rLine.setC_Tax_ID(ID_Iva);
						//seteo de ids ya usados
						rLine.set_CustomColumn("A_Locator_Use_ID",rs.getInt("A_Locator_Use_ID"));
						rLine.setLineNetAmt();
						rLine.save(get_TrxName());						
						//Se genera linea exenta
						MProductPricing pp = getProductPricing(rLine.getM_Product_ID(), req.get_ValueAsInt("C_BPartner_ID"),
								rLine.getQtyOrdered(), req.isSOTrx(),req.getM_PriceList_ID(),req.getDateDoc());
						if(pp.getPriceLimit().compareTo(Env.ZERO) > 0)
						{
							MRequisitionLine rLine2 = new MRequisitionLine(req);
							rLine2.setM_Product_ID(rs.getInt("M_Product_ID"));
							rLine2.setQty(rs.getBigDecimal("qty"));
							rLine2.setPrice();
							rLine2.setPriceActual(pp.getPriceLimit());
							//rLine2.setC_Tax_ID(ID_Exe);
							//seteo de ids ya usados
							rLine2.set_CustomColumn("A_Locator_Use_ID",rs.getInt("A_Locator_Use_ID"));
							rLine2.save(get_TrxName());
							rLine2.setLineNetAmt();
							cant++;
						}
					}
					ID_lastBPartner = rs.getInt("C_Bpartner_ID");
				}
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, e.getMessage(), e);
			}
			/*finally
			{
				pstmt.close(); rs.close();
				pstmt=null; rs=null;
			}*/
			//se suma 1 dia a fecha
			dateTrx = DateUtils.SumarDias(dateTrx, 1);
		}	
		return "Se ha generado "+cant+" solicitudes";
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

