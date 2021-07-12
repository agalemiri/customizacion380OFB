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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.compiere.model.MOrg;
import org.compiere.model.MRequisition;
import org.compiere.model.MUser;
import org.compiere.model.MWarehouse;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.model.MOrgInfo;

/**
 *	Validator for JUNAEB
 *
 *  @author ininoles
 */
public class ModJUNAEBSendMail implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModJUNAEBSendMail ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModJUNAEBSendMail.class);
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
		//engine.addModelChange(MRequisition.Table_Name, this);
		engine.addDocValidate(MRequisition.Table_Name, this);
		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		//Se validará que el activo no este en otro proceso actualmente
		
		/*if((type == TYPE_AFTER_NEW) && po.get_Table_ID()==X_A_Asset.Table_ID) 
		{	
			
			X_A_Asset asset = (X_A_Asset)po;
			int aw_id = DB.getSQLValue(asset.get_TrxName(), "SELECT max(a_depreciation_workfile_id) from a_depreciation_workfile where a_Asset_id = "+asset.get_ID());
			X_A_Depreciation_Workfile  aw = new X_A_Depreciation_Workfile(asset.getCtx(),aw_id,asset.get_TrxName());

			asset.set_CustomColumn("AD_OrgRef_ID", asset.getAD_Org_ID());
			asset.save();
			aw.setA_Asset_Cost((BigDecimal)asset.get_Value("GrandTotal"));
			aw.setA_Accumulated_Depr(Env.ZERO);
			
			
			aw.set_CustomColumn("A_Salvage_Value", Env.ONE);
			aw.setPostingType("A");
			aw.setIsDepreciated(true);
			aw.setA_QTY_Current(Env.ONE);
			
			aw.save();
			
			
			//Validar si existe ya cuentas contables de activo (registro en a_asset_acct).
			
			String sql = "SELECT coalesce(a_asset_Acct_id,0) from A_Asset_acct where a_Asset_id = "+asset.get_ID();
			int count = DB.getSQLValue(asset.get_TrxName(), sql);
			if(count == 0)
			{
				//Si no hay registros asociados en a_Asset_acct, entonces se debe crear
			}
			else
			{
				//Se debe obtener la vida util
				int life = DB.getSQLValue(asset.get_TrxName(), "SELECT max(a_Asset_life_years) from a_asset_group " +
						" where a_Asset_group_id in (select a_Asset_group_ref2_id from a_asset where a_Asset_id = "+asset.get_ID()+") ");
				
				
				MAssetAcct acct = new MAssetAcct(asset.getCtx(),count,asset.get_TrxName());
				acct.setA_Period_End(life);
				acct.save();
				
				CreateAssetForecastOFB cast = new CreateAssetForecastOFB();
				//cast.replanningForecast(asset, aw.getA_Asset_Cost().subtract(aw.getA_Accumulated_Depr()), acct, 0,asset.getAssetServiceDate());
				//CreateAssetForecast.createForecast(asset,null, acct, po.get_TrxName());

				log.config("marit");		
			}
				

			

		}*/
		
		return null;
	}	//	modelChange

	public String docValidate (PO po, int timing)
	{
		log.info(po.get_TableName() + " Timing: "+timing);
		
		if(timing == TIMING_AFTER_COMPLETE && po.get_Table_ID()==MRequisition.Table_ID) 
		{				
			MRequisition req = (MRequisition)po;
			MClient client = new MClient(po.getCtx(), req.getAD_Client_ID(), po.get_TrxName());
			MUser user = new MUser(po.getCtx(), Env.getAD_User_ID(po.getCtx()), po.get_TrxName());
			MWarehouse war = new MWarehouse(po.getCtx(), req.getM_Warehouse_ID(), po.get_TrxName());
			MOrg org = new MOrg(po.getCtx(), req.getAD_Org_ID(), po.get_TrxName());
			String destino = user.getEMail(); 
			String destinoCC = "";
			
			/*if(user.getSupervisor_ID() > 0)
			{
				destinoCC = user.getSupervisor().getEMail();
			}*/
			
			//mfrojas 20190620 
			//El correo CC, en vez de ser el supervisor del usuario, sera el supervisor de la org de la req
			
			int SuperOrg = DB.getSQLValue(po.get_TrxName(), "SELECT coalesce(max(supervisor_id),0) from ad_orginfo where ad_org_id = "+org.getAD_Org_ID());
			if(SuperOrg > 0)
			{
				MUser user2 = new MUser(po.getCtx(), SuperOrg, po.get_TrxName());
				destinoCC = user2.getEMail();
			}		

			//solicitud de consumo de materiales
			if(req.getC_DocType().getDocBaseType().compareTo("RRC") == 0)
			{
				//generamos mensaje completo
				String ln = System.getProperty("line.separator");
				StringBuilder str = new StringBuilder();
				str.append("Estimado Usuario:");
				str.append(ln);
				str.append(ln);
				str.append("Se ha completado la solicitud de consumo N "+req.getDocumentNo()+" del funcionario "+user.getName()+
						" Division/Dpto./Unidad "+org.getName()+" con los siguientes Productos:");
				str.append(ln);
				str.append(ln);
				//se agrega detalle
				try
				{
					String sqlDet = "SELECT 'Nombre producto '||mp.name||', Cantidad solicitada:'||rl.qty as descri " +
							" FROM m_requisitionline rl" +
							" INNER JOIN M_Product mp ON (mp.M_Product_ID = rl.M_Product_ID)" +
							" WHERE rl.M_Requisition_ID="+req.get_ID();
					log.config("sql detalle:"+sqlDet);
					PreparedStatement pstmtDet = DB.prepareStatement(sqlDet, po.get_TrxName());					
					ResultSet rsDet = pstmtDet.executeQuery();
					while(rsDet.next())
					{
						str.append(rsDet.getString("descri"));
						str.append(ln);	
					}
				}
				catch (SQLException e) 
				{
					log.config(e.toString());
				}
				//str.append(ln);
				str.append(ln);
				str.append("ATTE Adempiere");					
				//creamos email
				EMail mail = new EMail(client, client.getRequestEMail(),destino,
						"AD-Solicitud de consumo completada N "+req.getDocumentNo(), str.toString());
				if(war.get_ValueAsInt("AD_User_ID") > 0)
				{
					MUser user2 = new MUser(po.getCtx(), war.get_ValueAsInt("AD_User_ID"), po.get_TrxName());
					mail.addCc(user2.getEMail());
				}
				//seteamos parametros de autenticacion
				mail.createAuthenticator(client.getRequestUser(),client.getRequestUserPW());
				//enviamos correo
				//mail.send();					
				log.config("Correo Enviado a "+user.getEMail());
				log.config("Errores Correo: "+mail.getSentMsg());
				//ininoles nuevo envio de correos por DB
				String correoDB = "BEGIN" +
						" send_mail(p_to          => '"+destino+"',";				
				//if(user.getSupervisor_ID() > 0)
				if(SuperOrg > 0)
				{
					correoDB = correoDB+"            p_cc          => '"+destinoCC+"',";
				}
				correoDB = correoDB+
						"            p_from        => 'adempiere@junaeb.cl'," +
						"            p_subject     => 'Solicitud de consumo completada N "+req.getDocumentNo()+"'," +
						"            p_text_msg    => '"+str.toString()+"'," +
						"            p_smtp_host   => 'smtp2.junaeb.local');"+
						"END;";
				log.config("cadena de correo:"+correoDB);
				PreparedStatement pstmt = DB.prepareStatement(correoDB,po.get_TrxName());					
				try 
				{
					pstmt.execute();
				} catch (SQLException e) {
					log.config(e.toString());
				}	
				//ininoles end
			}			
			//solicitud de movimiento
			if(req.getC_DocType().getDocBaseType().compareTo("RMV") == 0)
			{
				//generamos mensaje completo
				String ln = System.getProperty("line.separator");
				StringBuilder str = new StringBuilder();
				str.append("Estimado Usuario:");
				str.append(ln);
				str.append(ln);
				str.append("Se ha completado la solicitud de movimiento N "+req.getDocumentNo()+" del funcionario "+user.getName()+
						" Division/Dpto./Unidad "+org.getName()+" con los siguientes Productos:");
				str.append(ln);
				str.append(ln);
				//se agrega detalle
				try
				{
					String sqlDet = "SELECT 'Nombre producto: '||mp.name||', Cantidad solicitada:'||rl.qty as descri" +
							" FROM m_requisitionline rl" +
							" INNER JOIN M_Product mp ON (mp.M_Product_ID = rl.M_Product_ID)" +
							" WHERE rl.M_Requisition_ID="+req.get_ID();
					PreparedStatement pstmtDet = DB.prepareStatement(sqlDet, po.get_TrxName());					
					ResultSet rsDet = pstmtDet.executeQuery();
					while (rsDet.next())
					{
						str.append(rsDet.getString("descri"));
						str.append(ln);	
					}
				}
				catch (SQLException e) 
				{
					log.config(e.toString());
				}				
				str.append(ln);
				str.append(ln);
				str.append("ATTE Adempiere");					
				//creamos email
				EMail mail = new EMail(client, client.getRequestEMail(), destino,
						"AD-Solicitud de movimiento completada N "+req.getDocumentNo(), str.toString());
				if(war.get_ValueAsInt("AD_User_ID") > 0)
				{
					MUser user2 = new MUser(po.getCtx(), war.get_ValueAsInt("AD_User_ID"), po.get_TrxName());
					mail.addCc(user2.getEMail());
				}
				//seteamos parametros de autenticacion
				mail.createAuthenticator(client.getRequestUser(),client.getRequestUserPW());
				//enviamos correo
				//mail.send();					
				log.config("Correo Enviado a "+user.getEMail());
				log.config("Errores Correo: "+mail.getSentMsg());
				//ininoles nuevo envio de correos por DB
				String correoDB = "BEGIN" +
						" send_mail(p_to          => '"+destino+"',";
				//if(user.getSupervisor_ID() > 0)
				if(SuperOrg > 0)
				{
					correoDB = correoDB+"            p_cc          => '"+destinoCC+"',";
				}
				correoDB = correoDB+
						"            p_from        => 'adempiere@junaeb.cl'," +
						"            p_subject     => 'Solicitud de movimiento completada N "+req.getDocumentNo()+"'," +
						"            p_text_msg    => '"+str.toString()+"'," +
						"            p_smtp_host   => 'smtp2.junaeb.local');"+
						"END;";
				PreparedStatement pstmt = DB.prepareStatement(correoDB,po.get_TrxName());					
				try 
				{
					pstmt.execute();
				} catch (SQLException e) {
					log.config(e.toString());
				}	
				log.config("cadena de correo:"+correoDB);
				//ininoles end				
				//nuevo correo 
				int cantP = DB.getSQLValue(po.get_TrxName(), "SELECT COUNT(1) " +
						" FROM m_requisitionline rl" +
						" INNER JOIN M_Requisition mr ON(rl.M_Requisition_ID = mr.M_Requisition_ID)" +
						" INNER JOIN M_Product mp ON (mp.M_Product_ID = rl.M_Product_ID)" +
						" INNER JOIN M_Replenish r ON (rl.M_product_ID = r.M_product_ID)" +
						" WHERE (bomqtyavailable(rl.m_product_id,mr.M_Warehouse_ID,null)-rl.qty) < level_min" +
						" AND rl.M_Requisition_ID="+req.get_ID());
				if(cantP > 0)
				{
					StringBuilder strDet = new StringBuilder();
					strDet.append("Estimado Usuario:");
					strDet.append(ln);
					strDet.append(ln);
					strDet.append("Se ha completado la solicitud de movimiento N "+req.getDocumentNo()+".");
					strDet.append(ln);
					strDet.append("Los siguientes productos presentan quiebre de stock:");
					strDet.append(ln);
					strDet.append(ln);
					//se agrega detalle
					try
					{
						String sqlDet = "SELECT mp.name || ' Cantidad despues del movimiento:'||(bomqtyavailable(rl.m_product_id,mr.M_Warehouse_ID,null)-rl.qty)||'. Cantidad Minima Configurada:'||level_min as descri" +
								" FROM m_requisitionline rl" +
								" INNER JOIN M_Requisition mr ON(rl.M_Requisition_ID = mr.M_Requisition_ID)" +
								" INNER JOIN M_Product mp ON (mp.M_Product_ID = rl.M_Product_ID)" +
								" INNER JOIN M_Replenish r ON (rl.M_product_ID = r.M_product_ID)" +
								" WHERE (bomqtyavailable(rl.m_product_id,mr.M_Warehouse_ID,null)-rl.qty) < level_min" +
								" AND rl.M_Requisition_ID="+req.get_ID();
						PreparedStatement pstmtDet = DB.prepareStatement(sqlDet, po.get_TrxName());					
						ResultSet rsDet = pstmtDet.executeQuery();
						while (rsDet.next())
						{
							str.append(rsDet.getString("descri"));
							strDet.append(ln);	
						}
						str.append("ATTE Adempiere");
						//ininoles nuevo envio de correos por DB
						String correoDBDet = "BEGIN" +
								" send_mail(p_to          => '"+destino+"'," ;
						if(user.getSupervisor_ID() > 0)
						{
							correoDB = correoDB+"            p_cc          => '"+destinoCC+"',";
						}
						correoDB = correoDB+
								"            p_from        => 'adempiere@junaeb.cl'," +
								"            p_subject     => 'Solicitud de movimiento completada N "+req.getDocumentNo()+"'," +
								"            p_text_msg    => '"+str.toString()+"'," +
								"            p_smtp_host   => 'smtp2.junaeb.local');"+
								"END;";
						PreparedStatement pstmtDBDet = DB.prepareStatement(correoDBDet,po.get_TrxName());
						pstmtDBDet.execute();
					} 
					catch (SQLException e) 
					{
						log.config(e.toString());
					}	
					log.config("cadena de correo:"+correoDB);
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