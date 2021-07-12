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

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MOrder;
import org.compiere.model.MProject;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Validator fo artilec
 *
 *  @author Italo Ni�oles
 */
public class ModelArtUpLocator implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelArtUpLocator ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelArtUpLocator.class);
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
		engine.addModelChange(MInOutLine.Table_Name, this);
		//	Documents to be monitored
		engine.addDocValidate(MInOut.Table_Name, this);

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
		if (type == TYPE_AFTER_NEW && po.get_Table_ID()==MInOutLine.Table_ID)
		{
			MInOutLine sLine = (MInOutLine) po;
			MInOut ship = sLine.getParent();
			if(ship.getC_Order_ID() > 0)
			{
				MOrder order = new MOrder(po.getCtx(), ship.getC_Order_ID(), po.get_TrxName());
				if(order.getC_Project_ID() > 0)
				{
					MProject pro = new MProject(po.getCtx(), order.getC_Project_ID(), po.get_TrxName());
					if(pro.get_ValueAsInt("M_Locator_ID") > 0)
					{
						DB.executeUpdate("UPDATE M_InOutLine SET M_Locator_ID = "+pro.get_ValueAsInt("M_Locator_ID")+
								" WHERE M_InOutLine_ID = "+sLine.get_ID(), po.get_TrxName());
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
		//validacion para cuando trata de sacar todos los productos del mismo locator
		if ((timing == TIMING_BEFORE_PREPARE || timing == TIMING_BEFORE_COMPLETE)&& po.get_Table_ID()==MInOut.Table_ID)
		{
			MInOut ship = (MInOut) po;
			if(ship.isSOTrx() && ship.getC_DocType().getDocBaseType().compareTo("MMS") == 0)
			{
				MInOutLine[] sLines = ship.getLines(false);
				for (int i = 0; i < sLines.length; i++)
				{
					MInOutLine sline = sLines[i];
					if(sline.getM_Product_ID() > 0 && sline.getM_Product().getProductType().compareTo("I") == 0)
					{
						//validacion de stock en la linea
						BigDecimal qtyStock = DB.getSQLValueBD(po.get_TrxName(), 
								"SELECT bomqtyonhand("+sline.getM_Product_ID()+",ml.M_Warehouse_ID,ml.M_Locator_ID) " +
								" FROM M_Locator ml WHERE M_Warehouse_ID = "+ship.getM_Warehouse_ID()+
								" AND M_Locator_ID = "+sline.getM_Locator_ID());
						if(qtyStock == null)
							qtyStock = Env.ZERO;							
						if(sline.getQtyEntered().compareTo(qtyStock) > 0 )
						{	
							throw new AdempiereException("ERROR: No se puede procesar despacho por error de " +
									" stock en la ubicaci�n. Producto:"+sline.getM_Product().getName()+
									". Ubicaci�n:"+sline.getM_Locator().getValue());
						}
					}		
				}
			}		
		}
		//validacion cuando se genera mas de un despacho
		if ((timing == TIMING_BEFORE_PREPARE || timing == TIMING_BEFORE_COMPLETE)&& po.get_Table_ID()==MInOut.Table_ID)
		{
			MInOut ship = (MInOut) po;
			if(ship.isSOTrx() && ship.getC_DocType().getDocBaseType().compareTo("MMS") == 0)
			{
				MInOutLine[] sLines = ship.getLines(false);
				for (int i = 0; i < sLines.length; i++)
				{
					MInOutLine sline = sLines[i];
					if(sline.getM_Product_ID() > 0)
					{
						//validacion de stock en la linea
						if(sline.getC_OrderLine_ID() > 0)
						{
							BigDecimal qtyStock = DB.getSQLValueBD(po.get_TrxName(), 
									"SELECT SUM(qtyEntered) FROM M_InOutLine iol" +
									" WHERE C_OrderLine_ID = "+sline.getC_OrderLine_ID());
							if(qtyStock == null)
								qtyStock = Env.ZERO;							
							if(qtyStock.compareTo(sline.getC_OrderLine().getQtyEntered()) > 0 )
							{
								throw new AdempiereException("ERROR: No se puede procesar despacho. Sin saldo pendiente " +
										" en la cotizaci�n. Linea:"+sline.getC_OrderLine().getLine()+
										". Producto:"+sline.getM_Product().getName());
							}
						}
					}		
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