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
import org.compiere.model.MOrderLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.MOrder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import org.compiere.model.MRole;

/**
 *	Validator fo artilec
 *
 *  @author mfrojas
 *  
 */
public class ModelArtPriceLimitOnComplete implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelArtPriceLimitOnComplete ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelArtPriceLimitOnComplete.class);
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
		//Si el precio es menor al precio limite, entonces el precio debe ser el precio limite.
		log.info(po.get_TableName() + " Type: "+type);
	/*	if ((type == TYPE_BEFORE_CHANGE || type == TYPE_BEFORE_NEW) && po.get_Table_ID()==MOrderLine.Table_ID)
		{
			MOrderLine ordl = (MOrderLine) po;
			if(ordl.getC_Order().isSOTrx())
			{
				if(ordl.getPriceLimit().compareTo(Env.ZERO) > 0)
				{
					if(ordl.getPriceEntered().compareTo(ordl.getPriceLimit()) < 0)
					{
						ordl.setPriceEntered(ordl.getPriceLimit());
						ordl.setLineNetAmt(ordl.getPriceLimit().multiply(ordl.getQtyEntered()));
						ordl.setPriceActual(ordl.getPriceLimit());
						ordl.save();
						
						
					}
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
		if ((timing == TIMING_BEFORE_PREPARE ) && po.get_Table_ID()==MOrder.Table_ID)
		{
			int flag = 0;
			String message = "";
			MOrder ord = (MOrder) po;			
			
			MRole role = new MRole(po.getCtx(),Env.getAD_Role_ID(po.getCtx()),po.get_TrxName());
			
			String descrole = null;
			if(role.getDescription() == null )
				descrole = "-";
			else
				descrole = role.getDescription().toLowerCase();
			boolean issotrx = ord.isSOTrx();
			if(issotrx && !descrole.contains("aprueba ov"))
			{
				String sql = "SELECT c_orderline_id from c_orderline where c_order_id = ?";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try
				{
					pstmt = DB.prepareStatement(sql.toString(), null);
					pstmt.setInt(1, ord.get_ID());
					rs = pstmt.executeQuery();
				
					while(rs.next())
					{
						MOrderLine ordl = new MOrderLine(po.getCtx(), rs.getInt("C_Orderline_ID"), po.get_TrxName());
						if(ordl.getPriceLimit().compareTo(ordl.getPriceActual()) > 0)
						{
							flag = 1;
							message = message.concat(ordl.getProduct().getName()).concat(" ");
						}
					}
				
					if(flag == 1)
					{
						throw new AdempiereException("Existe lineas con diferencia en el precio limite. Productos "+message+". Debe " +
								" solicitar aprobacion");
					}

				}
				catch(SQLException e)
				{
					log.log(Level.SEVERE, sql.toString(), e);
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