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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

import org.compiere.model.MAsset;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MAssetGroup;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.ofb.utils.DateUtils;

/**
 *	Validator for JUNAEB
 *
 *  @author mfrojas
 */
public class ModJUNAEBValidateAmtInvoiceLine implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModJUNAEBValidateAmtInvoiceLine ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModJUNAEBValidateAmtInvoiceLine.class);
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
		engine.addModelChange(MInvoice.Table_Name, this);		
		engine.addModelChange(MInvoiceLine.Table_Name, this);
		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		if((type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE) && po.get_Table_ID()==MInvoiceLine.Table_ID) 
		{	
			MInvoiceLine iLine = (MInvoiceLine)po;
			MInvoice inv = new MInvoice(po.getCtx(), iLine.getC_Invoice_ID(), po.get_TrxName());
			MDocType dType = new MDocType(po.getCtx(), inv.getC_DocTypeTarget_ID(), po.get_TrxName());

			
			//Primero se revisa que el tipo de documento sea de alta, para poder generar las validaciones.
			if(inv.getDocStatus().compareTo("DR") == 0 && (dType.get_ValueAsString("Type1").compareTo("ALT") == 0))
			{	
				//Se debe validar que, dependiendo del grupo de activos seleccionado, el monto deberá ser estandarizado. 
				
				if(dType.getName().toLowerCase().contains("alta por adq"))
				{
					//Para grupos 44 y 42, el valor debe ser mayor o igual a 30 UTM.
					
					int group = iLine.get_ValueAsInt("A_Asset_Group_Ref_ID");
					MAssetGroup mag = new MAssetGroup(po.getCtx(), group, po.get_TrxName());
					
					if(mag.get_ValueAsString("Value").compareTo("42") == 0 || mag.get_ValueAsString("Value").compareTo("44") == 0)
					{
						//Obtener valor de UTM.
						
						int id_utm = DB.getSQLValue(po.get_TrxName(), "SELECT MAX(C_Currency_ID) FROM C_Currency WHERE Description like 'UTM' ");
						BigDecimal totalAmtLineCLP = iLine.getPriceEntered();

						if (id_utm > 0)
						{
							BigDecimal amt30UTMclp = MConversionRate.convert(po.getCtx(), new BigDecimal("30.0"), id_utm, inv.getC_Currency_ID(),
							inv.getDateAcct(), 0, inv.getAD_Client_ID(), inv.getAD_Org_ID());
								
							if (inv.getC_Currency_ID() != 228)
								totalAmtLineCLP = MConversionRate.convert(po.getCtx(), iLine.getPriceEntered(), inv.getC_Currency_ID(), 228,
									inv.getDateAcct(), 0, inv.getAD_Client_ID(), inv.getAD_Org_ID());
								
							try 
							{
								if(totalAmtLineCLP.compareTo(amt30UTMclp) >= 0)									
									;
								else
									return "ERROR! Los bienes de los grupos 42 y 44 deben tener un valor mayor a 30UTM en las altas por adquisición";
									
							}
							catch (Exception e) 
							{
									log.config("No se pudo hacer comparación con moneda UTM"+e.toString());
									return "No se pudo hacer comparación con moneda UTM. Recuerde ingresar la tasa de cambio";
									
							}
								
						}	

					}
					//Si es del grupo 36, no tiene restricciones de UTM.
					else if(mag.get_ValueAsString("Value").compareTo("36") == 0)
					{
						BigDecimal totalAmtLineCLP = iLine.getPriceEntered();
						if(totalAmtLineCLP.compareTo(Env.ONE) < 0 )
							return "El valor del bien debe ser mayor a 1 peso";
					}
					//Para todos los otros grupos, en un alta por adquisición, el valor de alta debe ser mayor o igual a 1 UTM.
					
					else
					{
						int id_utm = DB.getSQLValue(po.get_TrxName(), "SELECT MAX(C_Currency_ID) FROM C_Currency WHERE Description like 'UTM' ");
					
						BigDecimal totalAmtLineCLP = iLine.getPriceEntered();

						if (id_utm > 0)
						{
							BigDecimal amt1UTMclp = MConversionRate.convert(po.getCtx(), new BigDecimal("1.0"), id_utm, inv.getC_Currency_ID(),
							inv.getDateAcct(), 0, inv.getAD_Client_ID(), inv.getAD_Org_ID());
								
							if (inv.getC_Currency_ID() != 228)
								totalAmtLineCLP = MConversionRate.convert(po.getCtx(), iLine.getPriceEntered(), inv.getC_Currency_ID(), 228,
									inv.getDateAcct(), 0, inv.getAD_Client_ID(), inv.getAD_Org_ID());
								
							try 
							{
								if(totalAmtLineCLP.compareTo(amt1UTMclp) >= 0)									
									;
								else
									return "ERROR! Los bienes deben tener un valor mayor a 1UTM en las altas por adquisición";
									
							}
							catch (Exception e) 
							{
									log.config("No se pudo hacer comparación con moneda UTM"+e.toString());
									
							}
								
						}	
	
					}
					//
				}
				//Para todos los otros tipos de alta, el valor debe ser mayor a 1 peso.
				
				else
				{
					BigDecimal totalAmtLineCLP = iLine.getPriceEntered();
					if(totalAmtLineCLP.compareTo(Env.ONE) < 0 )
						return "El valor del bien debe ser mayor a 1 peso";

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