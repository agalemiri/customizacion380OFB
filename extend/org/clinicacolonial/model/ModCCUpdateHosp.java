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
package org.clinicacolonial.model;

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_CC_Hospitalization;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
/**
 *	Validator for CC
 *
 *  @author ininoles
 */
public class ModCCUpdateHosp implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModCCUpdateHosp ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModCCUpdateHosp.class);
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
		engine.addModelChange(X_CC_Hospitalization.Table_Name, this);
		//engine.addModelChange(MAsset.Table_Name, this);		

		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		if((type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE)&& po.get_Table_ID()==X_CC_Hospitalization.Table_ID) 
		{	
			X_CC_Hospitalization hosp = (X_CC_Hospitalization)po;
			StringBuilder text = new StringBuilder(); 
			String textAux;
			//Signature1
			textAux = DB.getSQLValueString(po.get_TrxName(), "SELECT Help FROM " +
					" AD_Column WHERE AD_Table_ID = "+X_CC_Hospitalization.Table_ID+
					" AND ColumnName = 'Signature1'");
			if(hosp.isSignature1())
				textAux = textAux+": Normal.";
			else
				textAux = textAux+": "+hosp.getDescription1()+".";
			text.append(textAux);
			//signature 2
			//mfrojas: Siguiente es signature7
			text = text.append("\n");
			textAux = "";
			textAux = DB.getSQLValueString(po.get_TrxName(), "SELECT Help FROM " +
					" AD_Column WHERE AD_Table_ID = "+X_CC_Hospitalization.Table_ID+
					" AND ColumnName = 'Signature7'");
			//if(hosp.isSignature2())
			if(hosp.get_ValueAsBoolean("Signature7"))
				textAux = textAux+": Normal.";
			else
				//textAux = textAux+": "+hosp.getDescription2()+".";
				textAux = textAux +": "+hosp.get_Value("Description7");

			text.append(textAux);
			//signature 3
			//mfrojas siguiente es signature2
			text = text.append("\n");
			textAux = "";
			textAux = DB.getSQLValueString(po.get_TrxName(), "SELECT Help FROM " +
					" AD_Column WHERE AD_Table_ID = "+X_CC_Hospitalization.Table_ID+
					" AND ColumnName = 'Signature2'");
			if(hosp.isSignature2())
				textAux = textAux+": Normal.";
			else
				textAux = textAux+": "+hosp.getDescription2()+".";
			text.append(textAux);
			//signature 4
			//mfrojas siguiente es signature8
			text = text.append("\n");
			textAux = "";
			textAux = DB.getSQLValueString(po.get_TrxName(), "SELECT Help FROM " +
					" AD_Column WHERE AD_Table_ID = "+X_CC_Hospitalization.Table_ID+
					" AND ColumnName = 'Signature8'");
			//if(hosp.isSignature4())
			if(hosp.get_ValueAsBoolean("Signature8"))	
				textAux = textAux+": Normal.";
			else
				//textAux = textAux+": "+hosp.getDescription4()+".";
				textAux = textAux +": "+hosp.get_Value("Description8");
			text.append(textAux);
			//signature 5
			//mfrojas siguiente es signature3
			text = text.append("\n");
			textAux = "";
			textAux = DB.getSQLValueString(po.get_TrxName(), "SELECT Help FROM " +
					" AD_Column WHERE AD_Table_ID = "+X_CC_Hospitalization.Table_ID+
					" AND ColumnName = 'Signature3'");
			if(hosp.isSignature3())
				textAux = textAux+": Normal.";
			else
				textAux = textAux+": "+hosp.getDescription3()+".";
			text.append(textAux);
			//signature 6
			//mfrojas siguiente es signature5
			text = text.append("\n");
			textAux = "";
			textAux = DB.getSQLValueString(po.get_TrxName(), "SELECT Help FROM " +
					" AD_Column WHERE AD_Table_ID = "+X_CC_Hospitalization.Table_ID+
					" AND ColumnName = 'Signature5'");
			if(hosp.isSignature5())
				textAux = textAux+": Normal.";
			else
				textAux = textAux+": "+hosp.getDescription5()+".";
			text.append(textAux);			
			//signature 7
			//mfrojas siguiente es signature6
			text = text.append("\n");
			textAux = "";
			textAux = DB.getSQLValueString(po.get_TrxName(), "SELECT Help FROM " +
					" AD_Column WHERE AD_Table_ID = "+X_CC_Hospitalization.Table_ID+
					" AND ColumnName = 'Signature6'");
			//if(hosp.isSignature7())
			if(hosp.get_ValueAsBoolean("Signature6"))	
				textAux = textAux+": Normal.";
			else
				//textAux = textAux+": "+hosp.getDescription7()+".";
				textAux = textAux +": "+hosp.get_Value("Description6");
			text.append(textAux);			
			//signature 8
			//mfrojas siguiente es signature4
			text = text.append("\n");
			textAux = "";
			textAux = DB.getSQLValueString(po.get_TrxName(), "SELECT Help FROM " +
					" AD_Column WHERE AD_Table_ID = "+X_CC_Hospitalization.Table_ID+
					" AND ColumnName = 'Signature4'");
			//if(hosp.isSignature8())
			if(hosp.get_ValueAsBoolean("Signature4"))	
				textAux = textAux+": Normal.";
			else
				//textAux = textAux+": "+hosp.getDescription8()+".";
				textAux = textAux +": "+hosp.get_Value("Description4");
			text.append(textAux);			

			//mfrojas siguiente es signature9
			text = text.append("\n");
			textAux = "";
			textAux = DB.getSQLValueString(po.get_TrxName(), "SELECT Help FROM " +
					" AD_Column WHERE AD_Table_ID = "+X_CC_Hospitalization.Table_ID+
					" AND ColumnName = 'Signature9'");
			//if(hosp.isSignature8())
			if(hosp.get_ValueAsBoolean("Signature9"))	
				textAux = textAux+": Normal.";
			else
				//textAux = textAux+": "+hosp.getDescription8()+".";
				textAux = textAux +": "+hosp.get_Value("Description9");
			text.append(textAux);			

			//mfrojas siguiente es signature10
			text = text.append("\n");
			textAux = "";
			textAux = DB.getSQLValueString(po.get_TrxName(), "SELECT Help FROM " +
					" AD_Column WHERE AD_Table_ID = "+X_CC_Hospitalization.Table_ID+
					" AND ColumnName = 'Signature10'");
			//if(hosp.isSignature8())
			if(hosp.get_ValueAsBoolean("Signature10"))	
				textAux = textAux+": Normal.";
			else
				//textAux = textAux+": "+hosp.getDescription8()+".";
				textAux = textAux +": "+hosp.get_Value("Description10");
			text.append(textAux);			

			//mfrojas siguiente es signature11
			text = text.append("\n");
			textAux = "";
			textAux = DB.getSQLValueString(po.get_TrxName(), "SELECT Help FROM " +
					" AD_Column WHERE AD_Table_ID = "+X_CC_Hospitalization.Table_ID+
					" AND ColumnName = 'Signature11'");
			//if(hosp.isSignature8())
			if(hosp.get_ValueAsBoolean("Signature11"))	
				textAux = textAux+": Normal.";
			else
				//textAux = textAux+": "+hosp.getDescription8()+".";
				textAux = textAux +": "+hosp.get_Value("Description11");
			text.append(textAux);			

			if(text != null)
				hosp.setDocumentNote(text.toString());
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