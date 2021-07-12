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
package org.ofb.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MAllocationHdr;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.MProductPricing;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Validator default OFB
 *
 *  @author Italo Niñoles
 */
public class ModelOFB implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelOFB ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelOFB.class);
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	

	/**
	 *	Initialize Validation
	 *	@param engine validation engine
	 *	@param client client
	 */
	public void initialize (ModelValidationEngine engine, MClient client)
	{
		//client = null for global validator
		if (client != null) {
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else  {
			log.info("Initializing global validator: "+this.toString());
		}

		//	Tables to be monitored		
		engine.addModelChange(MPayment.Table_Name, this);
		engine.addModelChange(MInvoice.Table_Name, this);		
		engine.addModelChange(MOrderLine.Table_Name, this);
		engine.addModelChange(MPaymentAllocate.Table_Name, this);
		//	Documents to be monitored
		engine.addDocValidate(MRequisition.Table_Name, this);
		engine.addDocValidate(MInvoice.Table_Name, this);
		//engine.addDocValidate(MOrder.Table_Name, this);	

	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public static final String DOCSTATUS_Drafted = "DR";
	public static final String DOCSTATUS_Completed = "CO";
	public static final String DOCSTATUS_InProgress = "IP";
	public static final String DOCSTATUS_Voided = "VO";
	
	
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		if (type == TYPE_AFTER_CHANGE && po.get_Table_ID()==MPayment.Table_ID)
		{
			MPayment pay = (MPayment) po;
			//MPayment Mpay = new MPayment(po.getCtx(), pay.get_ID(), po.get_TrxName());
			
			PreparedStatement pstmt = null;
			String mySql = "select distinct C_AllocationHdr_ID FROM C_AllocationLine WHERE C_Payment_ID = ?";
			
			if (pay.getDocStatus() == DOCSTATUS_Completed)
			{
				try 
				{
					pstmt = DB.prepareStatement(mySql, po.get_TrxName());
					pstmt.setInt(1, pay.get_ID());											
					ResultSet rs = pstmt.executeQuery();
				
					if (rs.next())
					{
						MAllocationHdr hdr = new MAllocationHdr(po.getCtx(), rs.getInt("C_AllocationHdr_ID"), po.get_TrxName());
						if (hdr.getDocStatus().compareTo(DOCSTATUS_Drafted) == 0 || hdr.getDocStatus().compareTo(DOCSTATUS_InProgress) == 0)
						{							
							hdr.setDocStatus(hdr.completeIt());
							hdr.save();
							log.info("Allocation: "+hdr.get_ID()+" Completed");
							
						}
					}
				}
				catch (Exception e)
				{
					log.log(Level.SEVERE, e.getMessage(), e);
				}
			}			
			if (pay.getDocStatus().compareTo(DOCSTATUS_Voided) == 0)
			{
				try 
				{
					pstmt = DB.prepareStatement(mySql, po.get_TrxName());
					pstmt.setInt(1, pay.get_ID());											
					ResultSet rs = pstmt.executeQuery();
					if (rs.next())
					{
						MAllocationHdr hdr = new MAllocationHdr(po.getCtx(), rs.getInt("C_AllocationHdr_ID"), po.get_TrxName());
						if (hdr.getDocStatus().compareTo(DOCSTATUS_Completed) == 0)
						{							
							if (hdr.voidIt())
							{
								hdr.save();
								log.info("Allocation: "+hdr.get_ID()+ " Voided");
							}														
						}
					}
				}
				catch (Exception e)
				{
					log.log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}		
		if ((type == TYPE_BEFORE_CHANGE || type== TYPE_BEFORE_NEW)&& po.get_Table_ID()==MInvoice.Table_ID)
		{
			MInvoice inv = (MInvoice) po;
			if(inv.isSOTrx())
			{
				int cant = 0;
				cant = DB.getSQLValue(po.get_TrxName(), "SELECT COUNT(1) FROM C_Invoice WHERE C_DocTypeTarget_ID = "+inv.getC_DocTypeTarget_ID()+" " +
						"AND DocumentNo = '"+inv.getDocumentNo()+"' AND C_Invoice_ID <> "+inv.get_ID());
				if(cant > 0)
					return "Ya existe una factura con mismo Numero y Tipo de Documento";
			}
		}
		if ((type == TYPE_AFTER_NEW ||  type == TYPE_AFTER_DELETE) && po.get_Table_ID()==MOrderLine.Table_ID)
		{
			MOrderLine oLine = (MOrderLine) po;
			if(oLine.get_ValueAsInt("M_RequisitionLine_ID")>0)
			{
				MRequisitionLine line=new MRequisitionLine(po.getCtx(),oLine.get_ValueAsInt("M_RequisitionLine_ID"), po.get_TrxName());
				String sqlUpRL = "SELECT SUM(QTYENTERED) FROM C_OrderLine col " +
						"INNER JOIN C_Order co ON (col.C_Order_ID = co.C_Order_ID) " +
						" WHERE M_RequisitionLine_ID = "+oLine.get_ValueAsInt("M_RequisitionLine_ID")+" AND co.DocStatus IN ('DR','IP','CO','CL')";
				BigDecimal amt = DB.getSQLValueBD(po.get_TrxName(), sqlUpRL);
				if (amt != null && amt.compareTo(Env.ZERO) > 0)
				{
					line.set_CustomColumn("QtyUsed", amt);
					line.save();	
				}
			}
		}		
		if ((type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE || type == TYPE_AFTER_DELETE)&& po.get_Table_ID()==MPaymentAllocate.Table_ID)
		{
			MPaymentAllocate pAll = (MPaymentAllocate) po;
			MPayment pay = new MPayment(po.getCtx(), pAll.getC_Payment_ID(), po.get_TrxName());
			BigDecimal Amount = DB.getSQLValueBD(po.get_TrxName(), "SELECT SUM(Amount) FROM C_PaymentAllocate WHERE IsActive = 'Y' AND C_Payment_ID = "+pay.get_ID());
			//BigDecimal DAmt = DB.getSQLValueBD(po.get_TrxName(), "SELECT SUM(DiscountAmt) FROM C_PaymentAllocate WHERE IsActive = 'Y' AND C_Payment_ID = "+pay.get_ID());
			//BigDecimal WOffAmt = DB.getSQLValueBD(po.get_TrxName(), "SELECT SUM(WriteOffAmt) FROM C_PaymentAllocate WHERE IsActive = 'Y' AND C_Payment_ID = "+pay.get_ID());
			//BigDecimal OUAmt = DB.getSQLValueBD(po.get_TrxName(), "SELECT SUM(OverUnderAmt) FROM C_PaymentAllocate WHERE IsActive = 'Y' AND C_Payment_ID = "+pay.get_ID());
			pay.setPayAmt(Amount);
			pay.save();
		}
		//ininoles validacion de precio limite
		if ((type == TYPE_BEFORE_NEW ||  type == TYPE_BEFORE_CHANGE) && po.get_Table_ID()==MOrderLine.Table_ID)
		{
			if(!OFBForward.InvalidPriceLimit())
			{	
				MOrderLine oLine = (MOrderLine) po;
				MOrder order = new MOrder(po.getCtx(), oLine.getC_Order_ID(), po.get_TrxName());
				if(order.getDocStatus().compareTo("DR") == 0
						|| order.getDocStatus().compareTo("IP") == 0
						|| order.getDocStatus().compareTo("IN") == 0)
				{
					if(oLine.getC_Order().isSOTrx() && oLine.getM_Product_ID() >0)
					{				
						MProductPricing pp = new MProductPricing (oLine.getM_Product_ID(), oLine.getC_Order().getC_BPartner_ID(),
								oLine.getQtyEntered(),oLine.getC_Order().isSOTrx());
						pp.setM_PriceList_ID(oLine.getC_Order().getM_PriceList_ID());
						pp.setM_PriceList_Version_ID(getPriceListVersion(oLine.getC_Order().getM_PriceList_ID(), oLine.getC_Order().getDateOrdered()));
						pp.setPriceDate(oLine.getC_Order().getDateOrdered());
						if(pp.getPriceLimit() != null && pp.getPriceLimit().compareTo(Env.ZERO) > 0)
						{
							if(oLine.getPriceEntered().compareTo(pp.getPriceLimit()) < 0)
								return "ERROR: Precio bajo precio limite";
						}
					}
				}
			}
		}	
		return null;
	}	//	modelChange

	/**
	 *	Validate Document.
	 *	Called as first step of DocAction.prepareIt
     *	when you called addDocValidate for the table.
     *	Note that totals, etc. may not be correct.
	 *	@param po persistent object
	 *	@param timing see TIMING_ constants
     *	@return error message or null
	 */
	public String docValidate (PO po, int timing)
	{
		log.info(po.get_TableName() + " Timing: "+timing);
		//ininoles validacion para aprobar solicitudes de compra por monto en AD_User
		if (timing == TIMING_BEFORE_COMPLETE && po.get_Table_ID()==MRequisition.Table_ID)			
		{
			MRequisition req = (MRequisition) po;
			MUser userApro = new MUser(po.getCtx(), Env.getAD_User_ID(po.getCtx()), po.get_TrxName());
			BigDecimal amtToCmp = (BigDecimal)userApro.get_Value("AmtApproval");
			if(amtToCmp == null)
				amtToCmp = Env.ZERO;
			if(amtToCmp.compareTo(Env.ZERO) > 0)
			{
				if (amtToCmp.compareTo(req.getTotalLines()) < 0)
				{
					return "Monto solicitud sobrepasa monto de usuario";
				}
			}
		}		
		//ininoles se agrega actualización de correlativos de compra
		if (timing == TIMING_BEFORE_COMPLETE && po.get_Table_ID()==MInvoice.Table_ID)			
		{
			MInvoice inv = (MInvoice) po;			
			log.config("docbase");
			String docbase=inv.getDocBase();
			
			log.config("before update correlativo");
			if(docbase.equals("API") || docbase.equals("APC")) //quitadas APB
			{
				if(inv.isSOTrx()==false)
				{
					String date1=inv.getDateAcct().toString().substring(0,10);
					String mysql="Select nvl(max(I.correlativo),0) FROM C_INVOICE I "
							+" WHere i.AD_CLIENT_ID="+inv.getAD_Client_ID() +" AND "
							+" TRIM(TO_Char(i.DATEacct,'mm'))= '"+ date1.substring(5,7)+"'"
							+" AND	i.ISSOTRX='N' AND TRIM(TO_Char(i.DATEacct,'yyyy'))= '"+ date1.substring(0,4)+"'"
							+" AND i.DOCSTATUS IN ('CO','CL') and Isactive='Y' ";
					if(OFBForward.UseCorrelativeAPForDocType())
						mysql = mysql + "AND C_DocType_ID = "+inv.getC_DocTypeTarget_ID();

					int nextcorr=DB.getSQLValue("C_Invoice",mysql);
					if(nextcorr==0)
					{
						DB.executeUpdate("update c_invoice set correlativo="+1+" where c_invoice_id="+inv.get_ID(),po.get_TrxName());
					}
					else
					{
						nextcorr=nextcorr+1;
						DB.executeUpdate("update c_invoice set correlativo="+nextcorr+" where c_invoice_id="+inv.get_ID(),po.get_TrxName());
					}
				}
			}
			else//Boleta and Protesto and Change
				DB.executeUpdate("update c_invoice set correlativo=0 where c_invoice_id="+inv.get_ID(),po.get_TrxName());
			log.config("after update correlativo");
		}		
		return null;
	}	//	docValidate
	
	private int getPriceListVersion(int pList_ID, Timestamp dateOrdered)
	{
		int valor = 0;
		String sql = "SELECT plv.M_PriceList_Version_ID "
				+ "FROM M_PriceList pl,C_Currency c,M_PriceList_Version plv "
				+ "WHERE pl.C_Currency_ID=c.C_Currency_ID"
				+ " AND pl.M_PriceList_ID=plv.M_PriceList_ID"
				+ " AND pl.M_PriceList_ID=? "						//	1
				+ " AND plv.ValidFrom <= ? "
				+ "ORDER BY plv.ValidFrom DESC";
			//	Use newest price list - may not be future
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, pList_ID);
			pstmt.setTimestamp(2, dateOrdered);				
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				if(rs.getInt(1) > 0)
					valor = rs.getInt(1); 
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		return valor;
	}
	
	/**
	 *	User Login.
	 *	Called when preferences are set
	 *	@param AD_Org_ID org
	 *	@param AD_Role_ID role
	 *	@param AD_User_ID user
	 *	@return error message or null
	 */
	public String login (int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		log.info("AD_User_ID=" + AD_User_ID);

		return null;
	}	//	login


	/**
	 *	Get Client to be monitored
	 *	@return AD_Client_ID client
	 */
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}	//	getAD_Client_ID


	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("QSS_Validator");
		return sb.toString ();
	}	//	toString


	

}	