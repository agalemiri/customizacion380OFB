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
package org.artilec.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MOrder;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 *	Validator fo artilec
 *
 *  @author Italo Niñoles
 */
public class ModelArtCOrder implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelArtCOrder ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelArtCOrder.class);
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
		//engine.addModelChange(MOrderLine.Table_Name, this);
		//	Documents to be monitored
		engine.addDocValidate(MOrder.Table_Name, this);

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
		/*if (type == TYPE_BEFORE_NEW && po.get_Table_ID()==MOrderLine.Table_ID)
		{
			MOrderLine oLine = (MOrderLine) po;
			MOrder order = oLine.getParent();
			if(!order.isSOTrx() && oLine.getM_Product_ID() > 0)
			{
				BigDecimal lastPrice = DB.getSQLValueBD(po.get_TrxName(), "SELECT PriceEntered" +
						" FROM C_OrderLine col " +
						" INNER JOIN C_Order co ON (co.C_Order_ID = col.C_Order_ID) " +
						" WHERE co.IssoTrx = 'N' AND DocStatus IN ('CO','CL') " +
						" AND M_Product_ID = ? ORDER BY col.created DESC", oLine.getM_Product_ID());
				if(lastPrice != null && lastPrice.compareTo(Env.ZERO) > 0)
				{
					oLine.setPrice(lastPrice);
					oLine.setLineNetAmt();
					oLine.setTax();
				}
			}
		}
		if ((type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE ) && po.get_Table_ID()==MOrderLine.Table_ID)
		{
			MOrderLine oLine = (MOrderLine) po;
			MOrder order = oLine.getParent();
			if(!order.isSOTrx() && oLine.getM_Product_ID() > 0)
			{
				//validacion de fecha en la prometida en la linea
				if(oLine.getDatePromised() != null && oLine.getDatePromised().compareTo(order.getDateOrdered()) < 0)
				{
					return "ERROR: Fecha estimada de linea erronea";
				}
			}
		}*/
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
		
		/*if ((timing == TIMING_BEFORE_PREPARE)&& po.get_Table_ID()==MOrder.Table_ID)
		{
			MOrder order = (MOrder) po;
			if(order.isSOTrx())
			{
				MOrderLine[] oLines = order.getLines(false, null);
				String log = "";
				int cant = 0;
				for (int i = 0; i < oLines.length; i++)
				{
					MOrderLine line = oLines[i];
					if(line.getM_Product_ID() > 0 && line.getM_Product().getProductType().compareTo("I") == 0)
					{
						BigDecimal qtyAvailable = DB.getSQLValueBD(po.get_TrxName(), 
								"SELECT SUM(bomqtyavailable("+line.getM_Product_ID()+",ml.M_Warehouse_ID,ml.M_Locator_ID))" +
								" FROM M_Locator ml WHERE M_Warehouse_ID = "+order.getM_Warehouse_ID());
						if(qtyAvailable == null)
							qtyAvailable = Env.ZERO;
						//disponible sera el disponible menos la cantidad actual
						qtyAvailable = qtyAvailable.subtract(line.getQtyEntered());
						if(qtyAvailable.compareTo(Env.ZERO) < 0 )
						{
							log = log+line.getM_Product().getName()+". ";
							cant++;
						}
					}		
				}
				if(cant > 0)
					throw new AdempiereException(Msg.getMsg(Env.getCtx(), "ARTILEC_NoStockNV", true)+". Productos :"+log);
			}	
		}*/
		/*if (timing == TIMING_BEFORE_PREPARE && po.get_Table_ID()==MOrder.Table_ID)
		{
			MOrder order = (MOrder) po;
			if(!order.isSOTrx())
			{
				int cant = DB.getSQLValue(po.get_TrxName(), "SELECT COUNT(1) FROM C_Orderline col" +
						" INNER JOIN C_Order co ON (co.C_Order_ID = col.C_Order_ID) " +
						" WHERE co.C_Order_ID="+order.get_ID()+" AND col.DateDelivered > co.DateOrdered ");
				if(cant > 0)
					return "ERROR: Fecha de entrega de linea superior a fecha de orden";
			}
		}*/
		/*if ((timing == TIMING_BEFORE_PREPARE || timing == TIMING_BEFORE_COMPLETE)&& po.get_Table_ID()==MOrder.Table_ID)
		{
			MOrder order = (MOrder) po;
			if(order.isSOTrx())
			{
				MOrderLine[] oLines = order.getLines(false, null);
				for (int i = 0; i < oLines.length; i++)
				{
					MOrderLine oline = oLines[i];
					if(oline.getM_Product_ID() > 0)
					{
						//validacion de stock en la linea
						if(oline.getQtyEntered().compareTo(oline.getQtyOrdered()) != 0)
						{
							throw new AdempiereException("ERROR: No se puede procesar cotización. Cantidad no concuerda." +
									" Linea:"+oline.getLine()+ ". Producto:"+oline.getM_Product().getName());
						}
					}		
				}
			}		
		}*/
		//validacion de stock 05-09-2018 @ininoles
		if (timing == TIMING_BEFORE_PREPARE && po.get_Table_ID()==MOrder.Table_ID)
		{
			MOrder order = (MOrder) po;
			if(order.isSOTrx())
			{
				//ininoles nueva validacion pedida por michael credito de cliente
				//int flag = DB.getSQLValue(po.get_TrxName(), "SELECT artilec_funcion_comprobacion_credito(co.C_Order_ID) FROM C_Order co " +
				//		" WHERE co.C_Order_ID = "+order.get_ID());
				int flag = DB.getSQLValue(po.get_TrxName(), "SELECT artilec_funcion_comprobacion_credito("+order.get_ID()+")");
				if(flag > 0)
					throw new AdempiereException(Msg.getMsg(Env.getCtx(), "ARTILEC_NoCreditBPNV", true));
				//validacion para NV normales
				int cant = DB.getSQLValue(po.get_TrxName(), "select count(1) from rvofb_stocklineartilec" +
								" WHERE qtyordered > 0 AND  c_order_id="+order.get_ID()+" and dif < 0");
				if(cant > 0)
					throw new AdempiereException(Msg.getMsg(Env.getCtx(), "ARTILEC_NoStockNV", true));
				
				//validacion para NV con proyecto
				if(order.getC_Project_ID() > 0)
				{
					int cantP = DB.getSQLValue(po.get_TrxName(), "select count(1) from rvofb_stocklineartilecProy" +
							" where c_order_id="+order.get_ID()+" and dif < 0");
					if(cantP > 0)
						throw new AdempiereException(Msg.getMsg(Env.getCtx(), "ARTILEC_NoStockNV", true));
				}
			}
		}
		return null;
	}	//	docValidate

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