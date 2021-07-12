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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_DM_Document;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.MProjectLine;
import org.compiere.model.X_DM_DocumentLinePM;
/**
 *	Validator for MINJU
 *
 *  @author mfrojas
 */
public class ModMINJUUpdateAmtProjectDM implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModMINJUUpdateAmtProjectDM ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModMINJUUpdateAmtProjectDM.class);
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
		//engine.addDocValidate(X_DM_Document.Table_Name, this);

	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		if(type == TYPE_AFTER_CHANGE && po.get_Table_ID()==X_DM_Document.Table_ID) 
		{	
			X_DM_Document doc = (X_DM_Document)po;
			if(doc.getC_DocType().getDocBaseType().compareTo("DMP")==0 && doc.getDocStatus().compareTo("CO")==0)
			{
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String sql = "SELECT DM_DocumentLinePM_ID from DM_DocumentLinePM where DM_Document_ID = "+doc.get_ID();

				pstmt = DB.prepareStatement (sql, po.get_TrxName());

				try
				{
					rs = pstmt.executeQuery();	
					int docline = 0;
					while(rs.next())
					{
						docline = rs.getInt("DM_DocumentLinePM_ID");
						X_DM_DocumentLinePM dl = new X_DM_DocumentLinePM(po.getCtx(),docline,po.get_TrxName());
						//mfrojas se agrega la etapa
						String etapa = (String)dl.get_Value("C_Phase_ID1");
						int id_project = dl.getC_Project_ID();
					
						if(dl.getAmount().compareTo(Env.ZERO)>0)
						{
							int projectlineid = DB.getSQLValue(po.get_TrxName(), "SELECT max(C_ProjectLine_ID) from C_ProjectLine where assignment_item like '001' and c_phase_id1 like '"+etapa+"' and c_project_id =  "+id_project);
							if(projectlineid > 0)
							{
								MProjectLine pl = new MProjectLine(po.getCtx(),projectlineid,po.get_TrxName());
								pl.setCommittedAmt(pl.getCommittedAmt().add(dl.getAmount()));
								pl.save();
							}
							else
							{
								String sqltranslation = "SELECT max(name) FROM AD_Field_TRL where AD_Field_ID in (" +
										" SELECT ad_field_id from ad_field where ad_column_id in (" +
										" SELECT ad_column_id from ad_column where name like 'Amount' and ad_table_id = ?))";
								String nametrl = DB.getSQLValueString(po.get_TrxName(), sqltranslation, 2000110);
								return "No existe la asignacion "+nametrl+" para el proyecto seleccionado.";
							}
							//DB.executeUpdate("UPDATE C_ProjectLine SET CommittedAmt = CommittedAmt + "+dl.getAmount()+" WHERE Assignment_item like '001' AND C_Project_ID = "+id_project,po.get_TrxName());
						}
						if(dl.getAmount2().compareTo(Env.ZERO)>0)
						{
							int projectlineid = DB.getSQLValue(po.get_TrxName(), "SELECT max(C_ProjectLine_ID) from C_ProjectLine where assignment_item like '002' and  c_phase_id1 like '"+etapa+"' and  c_project_id =  "+id_project);
							if(projectlineid > 0)
							{
								MProjectLine pl = new MProjectLine(po.getCtx(),projectlineid,po.get_TrxName());
								pl.setCommittedAmt(pl.getCommittedAmt().add(dl.getAmount2()));
								pl.save();
							}
							else
							{
								String sqltranslation = "SELECT max(name) FROM AD_Field_TRL where AD_Field_ID in (" +
										" SELECT ad_field_id from ad_field where ad_column_id in (" +
										" SELECT ad_column_id from ad_column where name like 'Amount2' and ad_table_id = ?))";
								String nametrl = DB.getSQLValueString(po.get_TrxName(), sqltranslation, 2000110);
								log.config("sqltranslation");
								return "No existe la asignacion "+nametrl+" para el proyecto seleccionado.";
							}

							//DB.executeUpdate("UPDATE C_ProjectLine SET CommittedAmt = CommittedAmt + "+dl.getAmount2()+" WHERE Assignment_item like '002' AND C_Project_ID = "+id_project,po.get_TrxName());
						}
							
						if(dl.getAmount3().compareTo(Env.ZERO)>0)
						{
							int projectlineid = DB.getSQLValue(po.get_TrxName(), "SELECT max(C_ProjectLine_ID) from C_ProjectLine where assignment_item like '003' and c_phase_id1 like '"+etapa+"' and c_project_id =  "+id_project);
							if(projectlineid > 0)
							{
								MProjectLine pl = new MProjectLine(po.getCtx(),projectlineid,po.get_TrxName());
								pl.setCommittedAmt(pl.getCommittedAmt().add(dl.getAmount3()));
								pl.save();
							}
							else
							{
								String sqltranslation = "SELECT max(name) FROM AD_Field_TRL where AD_Field_ID in (" +
										" SELECT ad_field_id from ad_field where ad_column_id in (" +
										" SELECT ad_column_id from ad_column where name like 'Amount3' and ad_table_id = ?))";
								String nametrl = DB.getSQLValueString(po.get_TrxName(), sqltranslation, 2000110);
								return "No existe la asignacion "+nametrl+" para el proyecto seleccionado.";
							}

							//DB.executeUpdate("UPDATE C_ProjectLine SET CommittedAmt = CommittedAmt + "+dl.getAmount3()+" WHERE Assignment_item like '003' AND C_Project_ID = "+id_project,po.get_TrxName());
						}	
						if(dl.getAmount4().compareTo(Env.ZERO)>0)
						{
							int projectlineid = DB.getSQLValue(po.get_TrxName(), "SELECT max(C_ProjectLine_ID) from C_ProjectLine where assignment_item like '004' and c_phase_id1 like '"+etapa+"' and c_project_id =  "+id_project);
							if(projectlineid > 0)
							{
								MProjectLine pl = new MProjectLine(po.getCtx(),projectlineid,po.get_TrxName());
								pl.setCommittedAmt(pl.getCommittedAmt().add(dl.getAmount4()));
								pl.save();
							}
							else
							{
								String sqltranslation = "SELECT max(name) FROM AD_Field_TRL where AD_Field_ID in (" +
										" SELECT ad_field_id from ad_field where ad_column_id in (" +
										" SELECT ad_column_id from ad_column where name like 'Amount4' and ad_table_id = ?))";
								String nametrl = DB.getSQLValueString(po.get_TrxName(), sqltranslation, 2000110);
								return "No existe la asignacion "+nametrl+" para el proyecto seleccionado.";
							}

							//DB.executeUpdate("UPDATE C_ProjectLine SET CommittedAmt = CommittedAmt + "+dl.getAmount4()+" WHERE Assignment_item like '004' AND C_Project_ID = "+id_project,po.get_TrxName());
						}	
						if(dl.getAmount5().compareTo(Env.ZERO)>0)
						{
							int projectlineid = DB.getSQLValue(po.get_TrxName(), "SELECT max(C_ProjectLine_ID) from C_ProjectLine where assignment_item like '005' and c_phase_id1 like '"+etapa+"' and c_project_id =  "+id_project);
							if(projectlineid > 0)
							{
								MProjectLine pl = new MProjectLine(po.getCtx(),projectlineid,po.get_TrxName());
								pl.setCommittedAmt(pl.getCommittedAmt().add(dl.getAmount5()));
								pl.save();
							}
							else
							{
								String sqltranslation = "SELECT max(name) FROM AD_Field_TRL where AD_Field_ID in (" +
										" SELECT ad_field_id from ad_field where ad_column_id in (" +
										" SELECT ad_column_id from ad_column where name like 'Amount5' and ad_table_id = ?))";
								String nametrl = DB.getSQLValueString(po.get_TrxName(), sqltranslation, 2000110);
								return "No existe la asignacion "+nametrl+" para el proyecto seleccionado.";
							}

							//DB.executeUpdate("UPDATE C_ProjectLine SET CommittedAmt = CommittedAmt + "+dl.getAmount5()+" WHERE Assignment_item like '005' AND C_Project_ID = "+id_project,po.get_TrxName());
						}	
						if(dl.getAmount6().compareTo(Env.ZERO)>0)
						{
							int projectlineid = DB.getSQLValue(po.get_TrxName(), "SELECT max(C_ProjectLine_ID) from C_ProjectLine where assignment_item like '006' and c_phase_id1 like '"+etapa+"' and c_project_id =  "+id_project);
							if(projectlineid > 0)
							{
								MProjectLine pl = new MProjectLine(po.getCtx(),projectlineid,po.get_TrxName());
								pl.setCommittedAmt(pl.getCommittedAmt().add(dl.getAmount6()));
								pl.save();
							}	
							else
							{
								String sqltranslation = "SELECT max(name) FROM AD_Field_TRL where AD_Field_ID in (" +
										" SELECT ad_field_id from ad_field where ad_column_id in (" +
										" SELECT ad_column_id from ad_column where name like 'Amount6' and ad_table_id = ?))";
								String nametrl = DB.getSQLValueString(po.get_TrxName(), sqltranslation, 2000110);
								return "No existe la asignacion "+nametrl+" para el proyecto seleccionado.";
							}

							//DB.executeUpdate("UPDATE C_ProjectLine SET CommittedAmt = CommittedAmt + "+dl.getAmount6()+" WHERE Assignment_item like '006' AND C_Project_ID = "+id_project,po.get_TrxName());
						}	
						if(dl.getAmount7().compareTo(Env.ZERO)>0)
						{
							int projectlineid = DB.getSQLValue(po.get_TrxName(), "SELECT max(C_ProjectLine_ID) from C_ProjectLine where assignment_item like '007' and c_phase_id1 like '"+etapa+"' and c_project_id =  "+id_project);
							if(projectlineid > 0)
							{
								MProjectLine pl = new MProjectLine(po.getCtx(),projectlineid,po.get_TrxName());
								pl.setCommittedAmt(pl.getCommittedAmt().add(dl.getAmount7()));
								pl.save();
							}
							else
							{
								String sqltranslation = "SELECT max(name) FROM AD_Field_TRL where AD_Field_ID in (" +
										" SELECT ad_field_id from ad_field where ad_column_id in (" +
										" SELECT ad_column_id from ad_column where name like 'Amount7' and ad_table_id = ?))";
								String nametrl = DB.getSQLValueString(po.get_TrxName(), sqltranslation, 2000110);
								return "No existe la asignacion "+nametrl+" para el proyecto seleccionado.";
							}

							//DB.executeUpdate("UPDATE C_ProjectLine SET CommittedAmt = CommittedAmt + "+dl.getAmount7()+" WHERE Assignment_item like '007' AND C_Project_ID = "+id_project,po.get_TrxName());
						}	
						if(dl.getAmount8().compareTo(Env.ZERO)>0)
						{
							log.config("mount 8 "+dl.getAmount8());
							int projectlineid = DB.getSQLValue(po.get_TrxName(), "SELECT max(C_ProjectLine_ID) from C_ProjectLine where assignment_item like '008' and c_phase_id1 like '"+etapa+"' and c_project_id =  "+id_project);
							if(projectlineid > 0)
							{
								MProjectLine pl = new MProjectLine(po.getCtx(),projectlineid,po.get_TrxName());
								pl.setCommittedAmt(pl.getCommittedAmt().add(dl.getAmount8()));
								pl.save();
							}
							else
							{
								String sqltranslation = "SELECT max(name) FROM AD_Field_TRL where AD_Field_ID in (" +
										" SELECT ad_field_id from ad_field where ad_column_id in (" +
										" SELECT ad_column_id from ad_column where name like 'Amount8' and ad_table_id = ?))";
								String nametrl = DB.getSQLValueString(po.get_TrxName(), sqltranslation, 2000110);
								return "No existe la asignacion "+nametrl+" para el proyecto seleccionado.";
							}

							//DB.executeUpdate("UPDATE C_ProjectLine SET CommittedAmt = CommittedAmt + "+dl.getAmount8()+" WHERE Assignment_item like '008' AND C_Project_ID = "+id_project,po.get_TrxName());
						}	
						if(dl.getAmount9().compareTo(Env.ZERO)>0)
						{
							int projectlineid = DB.getSQLValue(po.get_TrxName(), "SELECT max(C_ProjectLine_ID) from C_ProjectLine where assignment_item like '009' and c_phase_id1 like '"+etapa+"' and c_project_id =  "+id_project);
							if(projectlineid > 0)
							{
								MProjectLine pl = new MProjectLine(po.getCtx(),projectlineid,po.get_TrxName());
								pl.setCommittedAmt(pl.getCommittedAmt().add(dl.getAmount9()));
								pl.save();
							}
							else
							{
								String sqltranslation = "SELECT max(name) FROM AD_Field_TRL where AD_Field_ID in (" +
										" SELECT ad_field_id from ad_field where ad_column_id in (" +
										" SELECT ad_column_id from ad_column where name like 'Amount9' and ad_table_id = ?))";
								String nametrl = DB.getSQLValueString(po.get_TrxName(), sqltranslation, 2000110);
								return "No existe la asignacion "+nametrl+" para el proyecto seleccionado.";
							}

							//DB.executeUpdate("UPDATE C_ProjectLine SET CommittedAmt = CommittedAmt + "+dl.getAmount9()+" WHERE Assignment_item like '009' AND C_Project_ID = "+id_project,po.get_TrxName());
						}	
					
					}
			
					rs.close ();
					pstmt.close ();
					pstmt = null;
				
				}catch (SQLException e)
				{
					throw new DBException(e, "Documento no tiene lineas");
				}							
				finally
				{
					DB.close(rs, pstmt);
					rs = null; pstmt = null;
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