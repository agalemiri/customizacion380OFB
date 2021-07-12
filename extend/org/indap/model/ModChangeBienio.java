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
package org.indap.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_DM_Document;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.eevolution.model.X_HR_Employee;

/**
 *	Validator for INDAP
 *
 *  @author ininoles
 */
public class ModChangeBienio implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModChangeBienio ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModChangeBienio.class);
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
		engine.addModelChange(X_DM_Document.Table_Name, this);
		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		//Se validará que el activo no este en otro proceso actualmente		
		if((type == TYPE_AFTER_NEW) && po.get_Table_ID()==X_DM_Document.Table_ID
				&& po.is_ValueChanged("DocStatus")) 
		{	
			X_DM_Document res = (X_DM_Document)po;
			if(res.getDocStatus().compareTo("CO")==0 && res.getC_DocType_ID() == 2000164)//resolucion cambio de bienios
			{
				int ID_Employee = 0; 
				String sql = "SELECT C_BPartner_ID FROM DM_DocumentLine WHERE DM_Document_ID ="+res.get_ID();
				PreparedStatement pstmt = DB.prepareStatement(sql, po.get_TrxName());
				ResultSet rs = pstmt.executeQuery();			
				while(rs.next())
				{
					ID_Employee = DB.getSQLValue(po.get_TrxName(), "SELECT HR_Employee_ID FROM HR_Employee" +
							" WHERE C_Bpartner_ID ="+rs.getInt("C_BPartner_ID"));
					if(ID_Employee > 0)
					{
						X_HR_Employee emp = new X_HR_Employee(po.getCtx(), ID_Employee, po.get_TrxName());
						String bienioValue = DB.getSQLValueString(po.get_TrxName(), "SELECT Description FROM AD_Ref_List " +						
								" WHERE AD_Reference_ID=2000157 AND Value = '"+emp.get_ValueAsString("benio")+"'");
						int bienioActual = Integer.parseInt(bienioValue);
						bienioActual++;
						String newBienioValue = DB.getSQLValueString(po.get_TrxName(), "SELECT Value FROM AD_Ref_List " +						
								" WHERE AD_Reference_ID=2000157 AND Description = '"+emp.get_ValueAsString("benio")+"'");
						emp.set_CustomColumn("benio", newBienioValue);
						emp.saveEx(po.get_TrxName());
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