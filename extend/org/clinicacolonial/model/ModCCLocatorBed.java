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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.Timestamp;


import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_CC_Operation;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.model.MAsset;
import org.compiere.model.X_A_Locator_Use;

/**
 *	Validator for CC
 *
 *  @author mfrojas
 */
public class ModCCLocatorBed implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModCCLocatorBed ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModCCLocatorBed.class);
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
		engine.addModelChange(X_CC_Operation.Table_Name, this);
		engine.addModelChange(MAsset.Table_Name, this);		

		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		if((type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE)&& po.get_Table_ID()==X_CC_Operation.Table_ID) 
		{	
			X_CC_Operation useregister = (X_CC_Operation)po;
			int actualbed = useregister.getM_Locator_ID();
			log.config("cama actual "+actualbed);
			
			int newbed = useregister.getM_Locator1_ID();
			log.config("cama nueva "+newbed);
			if(newbed == 0)
				return null;
			
			
			
			//Validar que cama seleccionada no se encuentra entregada a alguien.
			int bpartneralta = DB.getSQLValue(po.get_TrxName(), "SELECT max(c_bpartner_id) from c_bpartner where name like 'ALTA'");
			String sqlvalidatepartner = "SELECT c_bpartner_id from m_locator where m_locator_id = "+newbed;
			int idpart = DB.getSQLValue(po.get_TrxName(),sqlvalidatepartner);
			if(idpart != bpartneralta)
				return "Esta cama ya se encuentra asignada";
			
			//Validar que la fecha de ingreso sea menor a la asignaciones anteriores.
			PreparedStatement pstmt1 = null;
			Date fecha1;
			String sqlvalidatedate = "SELECT coalesce(max(datestart),'2000-01-01 00:00:00') as datestart from a_locator_use where dateend is null and C_BPartner_ID = "+useregister.getC_BPartner_ID();
			pstmt1 = DB.prepareStatement(sqlvalidatedate, po.get_TrxName());
			
			ResultSet r1 = pstmt1.executeQuery();
			if(r1.next())
				fecha1 = r1.getDate("datestart");
			else
				fecha1 = null;
			log.config("fecha "+r1.getTimestamp("datestart"));
			
			//Valida que la fecha de asignación actual no sea menor a las fechas existentes en las asignaciones
			//anteriores
			log.config("fecha ahora "+useregister.getStartDate());
			int var = useregister.getStartDate().compareTo(r1.getTimestamp("datestart"));
			log.config("resta "+var);
			if(useregister.getStartDate().compareTo(r1.getTimestamp("datestart"))<0)
				return "La fecha de esta asignación es menor a las ya existentes ";
				
			
			//Valida que la fecha de asignación actual no sea mayor a la fecha del día de hoy
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			if(useregister.getStartDate().compareTo(timestamp)>0)
				return "La fecha de esta asignación es mayor a la de hoy";
			
			//Primero se ingresa en la misma ventana el registro actual (cambio de cama)

			DB.executeUpdate("UPDATE CC_Operation set M_Locator_ID = "+newbed+", M_Locator1_ID = null where CC_Operation_ID = "+useregister.get_ID(),po.get_TrxName());
			
			//ingresar registro nuevo en a_locator_use
			String sqlmaxid = "SELECT max(a_locator_use_id) from a_locator_use";
			int idmax = DB.getSQLValue(po.get_TrxName(), sqlmaxid);

			X_A_Locator_Use loc = new X_A_Locator_Use(po.getCtx(),0,po.get_TrxName());
			loc.setA_Locator_Use_ID(idmax);

			//setear los campos que se guardaran. 


			loc.set_CustomColumn("M_Locator_ID", newbed);
			//@mfrojas se cambia desde fecha y hora actual de sistema a campo fecha de asignación de cama
			
			//loc.setDateStart(timestamp);
			loc.setDateStart(useregister.getStartDate());
			loc.setC_BPartner_ID(useregister.getC_BPartner_ID());
			
			String sqlingresomedico = "SELECT documentno from cc_hospitalization where cc_hospitalization_id = "+useregister.getCC_Hospitalization_ID();
			PreparedStatement pstmt = DB.prepareStatement(sqlingresomedico, po.get_TrxName());

			//String docno = DB.getSQLValueString(po.get_TrxName(), sqlingresomedico, 0);
			ResultSet rs = pstmt.executeQuery();
			String docno = "";
			while (rs.next())
				docno = rs.getString("documentno");
			log.config("docno "+docno);
			loc.setDocumentNo(docno);
			//loc.setUseDate(useregister.getStartDate());
			loc.setUseDate(timestamp);
			loc.setUseUnits(0);
			
			//agregar fecha de término en registros anteriores
			DB.executeUpdate("UPDATE a_locator_use set  dateend= '"+useregister.getStartDate()+"' where dateend is null and C_BPartner_ID = "+useregister.getC_BPartner_ID(),po.get_TrxName());			

			loc.set_CustomColumn("CC_Hospitalization_ID", useregister.getCC_Hospitalization_ID());
			//agregar patient_complexity
			if(useregister.get_Value("Patient_Complexity") != null)
				loc.set_CustomColumn("Patient_Complexity",useregister.get_Value("Patient_Complexity").toString());
			loc.save();
						
			//actualizar socio de negocio en el locator correspondiente
			

			
			DB.executeUpdate("UPDATE M_Locator set C_BPartner_ID = "+useregister.getC_BPartner_ID()+" where m_locator_id = "+newbed,po.get_TrxName());
			DB.executeUpdate("UPDATE M_Locator set C_BPartner_ID = "+bpartneralta+" where m_locator_id = "+actualbed,po.get_TrxName());
			
		}
		
/*		if(type == TYPE_BEFORE_CHANGE && po.get_Table_ID()==X_CC_Operation.Table_ID) 
		{	
			X_CC_Operation useregister = (X_CC_Operation)po;
			int actualpartner = useregister.get_ValueAsInt("c_bpartner_id");
			log.config("usuario actual "+actualpartner);
			
			int actualasset = useregister.getCC_Operation_ID();
			log.config("activo actual "+actualasset);
			
			String alta = useregister.get_ValueAsString("alta");
			log.config("alta? "+alta);
			
			if(alta.compareToIgnoreCase("Y")==0)
			{
				String sqlupdate = "Update a_asset_use set isclosed='Y' where a_asset_id = "+actualasset;
				DB.executeUpdate(sqlupdate,po.get_TrxName());

				int partneralta = DB.getSQLValue(po.get_TrxName(), "select max(c_bpartner_id) from c_bpartner where name like 'ALTA%'");
				String sqlupdate2 = "Update a_Asset set c_bpartner_id = "+partneralta+" where a_asset_id = "+actualasset;
				DB.executeUpdate(sqlupdate2,po.get_TrxName());
			
			}
			
			
		}		
*/
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