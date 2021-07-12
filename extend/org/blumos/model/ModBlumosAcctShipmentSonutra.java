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
package org.blumos.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;

import org.compiere.acct.FactLine;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MInOut;

import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
/**
 *	Validator for Blumos
 *
 *  @author Italo Niñoles
 */
public class ModBlumosAcctShipmentSonutra implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModBlumosAcctShipmentSonutra ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModBlumosAcctShipmentSonutra.class);
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
			log.info("Initializing Model Price Validator: "+this.toString());
		}

		//	Tables to be monitored
		engine.addModelChange(FactLine.Table_Name, this);		
		//	Documents to be monitored
						
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		if(type == TYPE_AFTER_NEW && po.get_Table_ID()==FactLine.Table_ID) 
		{	
			FactLine fact = (FactLine)po;
			if(fact.getAD_Client_ID() == 1000022 && fact.getAD_Table_ID() == 319)
			{
				int schema_ID = DB.getSQLValue(po.get_TrxName(), "select MAX(C_AcctSchema1_ID) FROM AD_ClientInfo " +
						" where AD_Client_ID = "+fact.getAD_Client_ID());
				MInOut inOut = new MInOut(po.getCtx(), fact.getRecord_ID(), po.get_TrxName());
				
				if(schema_ID > 0 && inOut.isSOTrx())
				{
					MAcctSchema schemaPr = new MAcctSchema(po.getCtx(), schema_ID, po.get_TrxName());
					
					if(fact.getC_AcctSchema_ID() != schema_ID)
					{
						//se busca tasa de cambio
						BigDecimal rate = MConversionRate.getRate(schemaPr.getC_Currency_ID(), 
								fact.getC_Currency_ID(), fact.getDateAcct(), 114, fact.getAD_Client_ID(), fact.getAD_Org_ID());
						if(rate == null)
							rate = Env.ONE;
						BigDecimal amtDr = Env.ZERO;
						BigDecimal amtCr = Env.ZERO;					
						if(fact.getAmtSourceDr().compareTo(Env.ZERO) > 0)
						{
							amtDr = fact.getAmtSourceDr().multiply(rate);
							amtDr = amtDr.setScale(schemaPr.getC_Currency().getStdPrecision(), RoundingMode.HALF_EVEN);
						}
						if(fact.getAmtSourceCr().compareTo(Env.ZERO) > 0)
						{
							amtCr = fact.getAmtSourceCr().multiply(rate);
							amtCr = amtCr.setScale(schemaPr.getC_Currency().getStdPrecision(), RoundingMode.HALF_EVEN);
						}
						String sql = "UPDATE Fact_Acct SET amtsourcedr= ?, amtacctdr= ?,amtsourcecr= ?, amtacctcr= ? " +
								" WHERE Fact_Acct_ID ="+fact.get_ID();
						PreparedStatement pstmt = null;
						pstmt = DB.prepareStatement (sql, po.get_TrxName());
						pstmt.setBigDecimal(1,amtDr);
						pstmt.setBigDecimal(2,amtDr);
						pstmt.setBigDecimal(3,amtCr);
						pstmt.setBigDecimal(4,amtCr);
						pstmt.executeQuery();
					}
				}
			}
		}
		return null;
	}	//	modelChange

	public String docValidate (PO po, int timing)
	{
		log.info(po.get_TableName() + " Timing: "+timing);		
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
		StringBuffer sb = new StringBuffer ("ModelPrice");
		return sb.toString ();
	}	//	toString	
}	