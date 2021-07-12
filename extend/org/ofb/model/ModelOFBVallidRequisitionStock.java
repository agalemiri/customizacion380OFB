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
import java.util.logging.Level;

import javax.swing.JOptionPane;

import org.compiere.model.MAllocationHdr;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MProject;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_C_Payment;
import org.compiere.model.X_C_PaymentRequest;
import org.compiere.model.X_C_PaymentRequestLine;
import org.compiere.model.X_DM_Document;
import org.compiere.model.X_DM_DocumentLine;
import org.compiere.model.X_MP_AssetMeter_Log;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.MProduct;
import org.python.antlr.PythonParser.return_stmt_return;


/**
 *	
 *
 *  @author italo niñoles
 */
public class ModelOFBVallidRequisitionStock implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelOFBVallidRequisitionStock ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelOFBVallidRequisitionStock.class);
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
     *	OFB Consulting Ltda. By Julio Farías
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
		
		if(timing == TIMING_BEFORE_COMPLETE && po.get_Table_ID()==MRequisition.Table_ID)
		{
			MRequisition req = (MRequisition)po;
			if(req.getDocBase().equals("RRC") && !req.isSOTrx()) //solicitud de materiales gore
			{
				MRequisitionLine[] lines = req.getLines();
				for(MRequisitionLine line : lines)
				{
					if(line.getM_Product_ID()>0)
					{
						BigDecimal found = getQtyOnHand (line.getM_AttributeSetInstance_ID(), line.getM_Product_ID(),line.getParent().getM_Warehouse_ID());
						if(found.compareTo(line.getQty())<0)
						{
							return "No existe Stock para Producto "+ line.getM_Product().getName() + " linea " + line.getLine() + " encontrado :" + found.intValue();
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


private BigDecimal getQtyOnHand (int M_AttributeSetInstance_ID, int M_Product_ID, int M_Warehouse_ID) {
		
		
		log.config("M_Warehouse_ID: "+ M_Warehouse_ID + "M_Product_ID: "+M_Product_ID + " M_AttributeSetInstance_ID: "+M_AttributeSetInstance_ID);
		BigDecimal bd = null;
		String sql = "SELECT sum(s.QtyOnHand) FROM M_Storage s " +
				" INNER JOIN M_Locator l on (l.M_Locator_ID=s.M_locator_ID) "
			+ "WHERE s.M_Product_ID=?"	//	1
			+ " AND l.M_Warehouse_ID=?";		//	2
		if (M_AttributeSetInstance_ID > 0)
			sql = "SELECT sum(s.QtyOnHand) FROM M_Storage s " +
			" INNER JOIN M_Locator l on (l.M_Locator_ID=s.M_locator_ID) "
		+ "WHERE s.M_Product_ID=?"	//	1
		+ " AND l.M_Warehouse_ID=?"		//	2
		+ " AND s.M_AttributeSetInstance_ID=?";
		
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, M_Product_ID);
			pstmt.setInt(2, M_Warehouse_ID);
			if (M_AttributeSetInstance_ID > 0)
				pstmt.setInt(3, M_AttributeSetInstance_ID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				
				bd = rs.getBigDecimal(1);
				log.config("QtyOnHand: "+ bd);
				if (bd != null)
					return bd;
			} 
			else {
				
				return new BigDecimal(0);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return new BigDecimal(0);
		}
		
		
		return new BigDecimal(0);
	}

}	