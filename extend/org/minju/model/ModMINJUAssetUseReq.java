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
package org.minju.model;

import java.sql.Timestamp;

import org.compiere.model.MClient;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_A_Asset_Use;
import org.compiere.util.CLogger;
import org.compiere.util.DB;


/**
 *	Validator for MINJU
 *
 *  @author mfrojas
 */
public class ModMINJUAssetUseReq implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModMINJUAssetUseReq ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModMINJUAssetUseReq.class);
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
		engine.addDocValidate(MRequisition.Table_Name, this);
	
		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		return null;
	}	//	modelChange

	public String docValidate (PO po, int timing)
	{
		log.info(po.get_TableName() + " Timing: "+timing);
		
		if(timing == TIMING_AFTER_COMPLETE && po.get_Table_ID()==MRequisition.Table_ID) 
		{	
			MRequisition req = (MRequisition)po;
			//@mfrojas
			//Se cambia el if, por el nombre del tipo de documento
			//if(req.getC_DocType_ID() == 2000151)
			if(req.getC_DocType().getName().compareTo("Asignacion de Activos")==0)
			{
				MRequisitionLine[] lines = req.getLines();
				for (int i = 0; i < lines.length; i++)
				{
					MRequisitionLine line = lines[i];
					//validamos si usa asset o asset ref
					int ID_Asset = line.get_ValueAsInt("A_Asset_ID");
					if(ID_Asset <= 0)
						ID_Asset = line.get_ValueAsInt("A_AssetRef_ID");
					if(ID_Asset > 0)
					{
						X_A_Asset_Use  assetuse = new X_A_Asset_Use(po.getCtx(),  0 ,po.get_TrxName());
						assetuse.setA_Asset_ID(ID_Asset);
						assetuse.setUseDate(req.getDateDoc());
						assetuse.setUseUnits(1);
						assetuse.set_CustomColumn("IsClosed", false);
						
						if(req.get_ValueAsInt("AD_UserRef_ID")>0)
							assetuse.set_CustomColumn("AD_User_ID", req.get_ValueAsInt("AD_UserRef_ID"));
						if(req.get_ValueAsInt("S_Resource_ID")>0)
							assetuse.set_CustomColumn("S_Resource_ID", req.get_ValueAsInt("S_Resource_ID"));
						assetuse.save();
					}
				}
			}
			else if(req.getC_DocType().getName().compareTo("Devolucion de Activos")==0)
				//else if(req.getC_DocType_ID() == 2000152)
			{
				MRequisitionLine[] lines = req.getLines();
				for (int i = 0; i < lines.length; i++)
				{
					MRequisitionLine line = lines[i];
					//validamos si usa asset o asset ref
					int ID_Asset = line.get_ValueAsInt("A_Asset_ID");
					if(ID_Asset <= 0)
						ID_Asset = line.get_ValueAsInt("A_AssetRef_ID");
					if(ID_Asset > 0)
					{
						//se busca ultimo asset use para marcar ticket de devuelto
						int ID_AUse = DB.getSQLValue(po.get_TrxName(), "SELECT A_Asset_Use_ID " +
								" FROM A_Asset_Use WHERE A_Asset_ID = "+ID_Asset+" AND IsActive = 'Y'" +
								" ORDER BY UseDate Desc");						
						if(ID_AUse > 0)
						{
							X_A_Asset_Use aUse = new X_A_Asset_Use(po.getCtx(), ID_AUse, po.get_TrxName());
							aUse.set_CustomColumn("IsClosed", true);
							aUse.set_CustomColumn("EndDate", (Timestamp)req.get_Value("DateReturn"));
							aUse.set_CustomColumn("Comments",line.getDescription());
							aUse.save();
							//DB.executeUpdate("UPDATE A_Asset_Use SET IsClosed = 'Y' WHERE A_Asset_Use_ID = "+ID_AUse, po.get_TrxName());
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
		StringBuffer sb = new StringBuffer ("ModelPrice");
		return sb.toString ();
	}	//	toString


	

}	