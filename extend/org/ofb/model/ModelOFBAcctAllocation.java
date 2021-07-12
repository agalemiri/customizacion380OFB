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

import org.compiere.acct.FactLine;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MClient;
import org.compiere.model.MClientInfo;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *
 *  @author italo niñoles
 */
public class ModelOFBAcctAllocation implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelOFBAcctAllocation ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelOFBAcctAllocation.class);
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
		//	Documents to be monitored
		
		engine.addModelChange(FactLine.Table_Name, this);		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	OFB Consulting Ltda. By Italo Niñoles
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		if((type == TYPE_AFTER_CHANGE || type == TYPE_AFTER_NEW) && po.get_Table_ID()==FactLine.Table_ID) 
		{
			FactLine fLine = (FactLine)po;
			if(fLine.getAD_Table_ID() == MAllocationHdr.Table_ID)
			{
				String sqlTo = "SELECT IntercompanyDueTo_Acct FROM C_AcctSchema_GL WHERE C_AcctSchema_ID=?";
				String sqlFrom = "SELECT IntercompanyDueFrom_Acct FROM C_AcctSchema_GL WHERE C_AcctSchema_ID=?";
				int ID_CombTo = DB.getSQLValue(fLine.get_TrxName(), sqlTo,MClientInfo.get(fLine.getCtx(), fLine.getAD_Client_ID()).getC_AcctSchema1_ID());
				int ID_CombFrom = DB.getSQLValue(fLine.get_TrxName(), sqlFrom,MClientInfo.get(fLine.getCtx(), fLine.getAD_Client_ID()).getC_AcctSchema1_ID());
				int ID_acctTo =DB.getSQLValue(po.get_TrxName(),"SELECT account_id FROM c_validcombination where c_validcombination_id = "+ID_CombTo);
				int ID_acctFrom =DB.getSQLValue(po.get_TrxName(),"SELECT account_id FROM c_validcombination where c_validcombination_id = "+ID_CombFrom);
				if(fLine.getAccount_ID() == ID_acctTo || fLine.getAccount_ID() == ID_acctFrom)
				{
					if(fLine.getAmtAcctDr().compareTo(Env.ZERO) > 0)
					{
						BigDecimal sum = DB.getSQLValueBD(fLine.get_TrxName(), 
						 "SELECT SUM(AmtAcctDr) FROM Fact_Acct WHERE Record_ID="+fLine.getRecord_ID()+" AND AD_Table_ID="+fLine.getAD_Table_ID()+
						 " AND Account_id NOT IN ("+ID_acctTo+","+ID_acctFrom+")");
						DB.executeUpdateEx("UPDATE Fact_Acct SET AmtAcctDr="+sum+", AmtSourceDr="+sum+" WHERE fact_acct_id = "+fLine.get_ID(), po.get_TrxName());
					}
					else if(fLine.getAmtAcctCr().compareTo(Env.ZERO) > 0)
					{
						BigDecimal sum = DB.getSQLValueBD(fLine.get_TrxName(), 
						 "SELECT SUM(AmtAcctCr) FROM Fact_Acct WHERE Record_ID="+fLine.getRecord_ID()+" AND AD_Table_ID="+fLine.getAD_Table_ID()+
						 " AND Account_id NOT IN ("+ID_acctTo+","+ID_acctFrom+")");
						DB.executeUpdateEx("UPDATE Fact_Acct SET AmtAcctCr="+sum+", AmtSourceDr="+sum+" WHERE fact_acct_id = "+fLine.get_ID(), po.get_TrxName());
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