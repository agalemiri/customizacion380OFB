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
package org.junaeb.model;

import java.util.logging.Level;

import org.compiere.model.MClient;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;

/**
 *	Validator default OFB
 *
 *  @author mfrojas
 */
public class ModelJUNAEBWarehouseMovement implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelJUNAEBWarehouseMovement ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelJUNAEBWarehouseMovement.class);
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
		//	Documents to be monitored
		engine.addDocValidate(MRequisition.Table_Name, this);

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
		if(timing == TIMING_AFTER_COMPLETE && po.get_Table_ID()==MRequisition.Table_ID)
		{
			MRequisition req = (MRequisition) po;
			//Si el tipo de documento base es de solicitud de movimiento de materiales. 
			if(req.getC_DocType().getDocBaseType().equals("RMV"))
			{
				MRequisitionLine[] lines = req.getLines();
				MMovement mov = null;
				
				MInventory inv = null;
				MWarehouse whfrom = MWarehouse.get(req.getCtx(), req.getM_Warehouse_ID(), req.get_TrxName());
				MWarehouse whto = MWarehouse.get(req.getCtx(), req.get_ValueAsInt("M_WarehouseSource_ID"));
				for (int i = 0; i < lines.length; i++)
				{
					MRequisitionLine line = lines[i];
					if (line.getM_Product_ID() >0)
					{
						if(mov == null)
						{
							mov = new MMovement(req.getCtx(),0,req.get_TrxName());
							mov.setAD_Org_ID(req.getAD_Org_ID());
							mov.setMovementDate(req.getDateDoc());
							mov.setDescription("Generado automaticamente desde solicitud "+req.getDocumentNo());
							mov.save();
							try{
								mov.set_CustomColumn("M_Requisition_ID",req.get_ID());
								mov.save();
							}
							catch (Exception e)
							{
								log.log(Level.SEVERE,"No se pudo asignar la vvariable M_Requisition_ID en M_Movement ", e);
							}
							mov.save();
						}
/*						if(inv == null)
						{  
							inv = new MInventory(req.getCtx(),0,req.get_TrxName());
							inv.setAD_Org_ID(req.getAD_Org_ID());
							inv.setM_Warehouse_ID(req.getM_Warehouse_ID());						
							inv.setC_DocType_ID(2000115);
							inv.setDescription("Generado automaticamente desde solicitud "+req.getDocumentNo());
							//inv.set_CustomColumn("AD_User_ID", req.getAD_User_ID());
							//ininoles se setea nuevo campo en cabecera de m_inventory
							inv.save();
							try{
								inv.set_CustomColumn("M_Requisition_ID", req.get_ID());
								inv.save();
							}
							catch (Exception e)
							{
								log.log(Level.SEVERE,"No se pudo asignar la variable M_Requisition_ID en M_Inventory",e);							
							}
							//end ininoles							
							//inv.set_CustomColumn("AD_User_ID", getAD_User_ID());						

						}*/
						MMovementLine mline = new MMovementLine(req.getCtx(),0,req.get_TrxName());
						mline.setM_Movement_ID(mov.getM_Movement_ID());
						mline.setAD_Org_ID(mov.getAD_Org_ID());
						mline.setM_Product_ID(line.getM_Product_ID());
						mline.setMovementQty(line.getQty());
						//locator from debe ser locator de materiales minju
						//locator to debe ser locator default de la solicitud de materiales.
						mline.setM_Locator_ID(whfrom.getDefaultLocator().getM_Locator_ID());
						mline.setM_LocatorTo_ID(whto.getDefaultLocator().getM_Locator_ID());
/*						MInventoryLine il = new MInventoryLine(req.getCtx(),0,req.get_TrxName());
						il.setM_Inventory_ID(inv.getM_Inventory_ID() );
						il.setAD_Org_ID(line.getAD_Org_ID());
						il.setM_Product_ID(line.getM_Product_ID());
						//il.setM_Locator_ID(wh.getDefaultLocator().getM_Locator_ID());
						il.setQtyInternalUse(line.getQty());*/
/*						int ID_Charge = -1;
						try{
							ID_Charge = req.get_ValueAsInt("C_Charge_ID");
						}
						catch (Exception e)
						{
							log.log(Level.SEVERE,"No se pudo asignar la variable M_Inventory_ID en M_Requisition",e);
							ID_Charge = DB.getSQLValue(po.get_TrxName(),"SELECT MAX(C_Charge_ID) " +
									" FROM C_Charge WHERE IsActive = 'Y' AND AD_Client_ID = "+Env.getAD_Client_ID(po.getCtx()));
						}	
						if(ID_Charge <= 0)
						{
							ID_Charge = DB.getSQLValue(po.get_TrxName(),"SELECT MAX(C_Charge_ID) " +
									" FROM C_Charge WHERE IsActive = 'Y' AND AD_Client_ID = "+Env.getAD_Client_ID(po.getCtx()));
						}
						if(ID_Charge > 0)
							mline.setC_Charge_ID(req.get_ValueAsInt("C_Charge_ID"));
						else
							mline.setC_Charge_ID(2000000);
							*/
						mline.save();						
					}
				}				
				if(mov!=null)
				{
					try{
						req.set_CustomColumn("M_Movement_ID", mov.getM_Movement_ID());
					}
					catch (Exception e)
					{
						log.log(Level.SEVERE,"No se pudo asignar la variable M_Inventory_ID en M_Requisition",e);							
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