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
package org.dpp.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.MAsset;
import org.compiere.model.MDepreciationWorkfile;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MBPartner;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;

/**
 *	Process Netting
 *	
 *  @author mfrojas
 *  @version $Id: ProcessDPPAssetNetting.java,v 1.0 2018/05/07 mfrojas $
 *  
 */
public class CreateOCFromRequisitionDPP extends SvrProcess
{
	/** Warehouse				*/
	private int		p_M_Requisition_ID = 0;
	private String 	p_DocumentNo;
	private int p_C_BPartner_ID = 0;
	private int p_C_DocType_ID = 0;
	/** Return Info				*/

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if(name.equals("M_Requisition_ID"))
				p_M_Requisition_ID = para[i].getParameterAsInt();
			else if(name.equals("DocumentNo"))
				p_DocumentNo = (String)para[i].getParameter();
			else if(name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = para[i].getParameterAsInt();
			else if(name.equals("C_DocType_ID"))
				p_C_DocType_ID = para[i].getParameterAsInt();
				
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
		MRequisition req = new MRequisition(getCtx(), p_M_Requisition_ID, get_TrxName());
		MOrder ord = new MOrder(getCtx(), 0, get_TrxName());
		
		if(p_C_BPartner_ID <= 0)
			throw new AdempiereException("ERROR: No ha ingresado al proveedor");
		if(p_DocumentNo == null)
			throw new AdempiereException("ERROR: No ha ingresado el correlativo");
		if(p_C_DocType_ID <= 0)
			throw new AdempiereException("ERROR: No ha ingresado el tipo de documento");
		
		MBPartner bp = new MBPartner(getCtx(), p_C_BPartner_ID,get_TrxName());
		
		ord.setC_BPartner_ID(bp.get_ID());

		ord.setDocStatus("DR");
		ord.setAD_Org_ID(req.getAD_Org_ID());
		
		ord.setDocumentNo(p_DocumentNo);
		ord.setDateOrdered(req.getDateDoc());
		ord.setDateAcct(req.getDateDoc());
		ord.setDatePromised(req.getDateDoc());
		ord.setC_DocTypeTarget_ID(p_C_DocType_ID);
		ord.setIsSOTrx(false);
		ord.setM_Warehouse_ID(req.getM_Warehouse_ID());
		ord.setM_PriceList_ID(req.getM_PriceList_ID());
		ord.setDescription(req.getDescription());
		ord.setSalesRep_ID(req.get_ValueAsInt("AD_User_ID"));
		ord.setDocAction("CO");
		ord.saveEx();

		//Cuando la cabecera esta creada, momento de agregar las lineas.
		
		//obtener lineas
		String sql = "SELECT m_requisitionline_id from m_requisitionline where "
				+ " isactive='Y' "
				+ " AND m_Requisition_id = "+req.get_ID();
		
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement(sql, get_TrxName());
		ResultSet rs = pstmt.executeQuery();
	
		while (rs.next() )
		{
			MRequisitionLine reql = new MRequisitionLine(getCtx(), rs.getInt("m_requisitionline_id"), get_TrxName());
			MOrderLine ordl = new MOrderLine(getCtx(), 0, get_TrxName());
			if(reql.getM_Product_ID() > 0)
			{
				ordl.setM_Product_ID(reql.getM_Product_ID());
				ordl.setM_AttributeSetInstance_ID(reql.getM_AttributeSetInstance_ID());
				ordl.setPriceActual(reql.getPriceActual());
				ordl.setPriceEntered(reql.getPriceActual());
			}
			else 
			{
				ordl.setC_Charge_ID(reql.getC_Charge_ID());
				ordl.setPriceActual(reql.getPriceActual());
				ordl.setPriceEntered(reql.getPriceActual());

			}
			if(reql.getDescription() != null)
				ordl.setDescription(reql.getDescription());
			ordl.setQtyEntered(reql.getQty());
			ordl.setQtyOrdered(reql.getQty());
			ordl.setAD_Org_ID(reql.getAD_Org_ID());
			log.config("loc "+ord.getC_BPartner_Location_ID());
			//int loc = ord.getC_BPartner_Location_ID();
			ordl.setC_Order_ID(ord.getC_Order_ID());
			//ordl.setC_BPartner_ID(ord.getC_BPartner_ID());
			//ordl.setC_BPartner_Location_ID(loc);
			//if(reql.get_ValueAsBoolean("IsTaxed") == true)
			//{
				//obtener IVA
			int tax = DB.getSQLValue(get_TrxName(), "SELECT max(c_tax_id) from c_tax where isdefault='Y'");
			if(tax > 0)
				ordl.setC_Tax_ID(tax);
			//}
			ordl.saveEx();
			
		}
		return "Orden Generada: "+ord.getDocumentNo();
	}	//	doIt



}	//	Replenish
