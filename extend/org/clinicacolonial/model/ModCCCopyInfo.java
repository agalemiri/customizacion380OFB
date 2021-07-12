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

import org.compiere.model.MClient;
import org.compiere.model.MColumn;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_CC_CollectiveIntelligence;
import org.compiere.model.X_CC_Epicrisis;
import org.compiere.model.X_CC_Evaluation;
import org.compiere.model.X_CC_Evaluation_nursing;
import org.compiere.model.X_CC_MedicalIndications;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
/**
 *	Validator for CC
 *
 *  @author ininoles
 */
public class ModCCCopyInfo implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModCCCopyInfo ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModCCCopyInfo.class);
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
		engine.addModelChange(X_CC_Evaluation.Table_Name, this);
		engine.addModelChange(X_CC_Epicrisis.Table_Name, this);		
		engine.addModelChange(X_CC_MedicalIndications.Table_Name, this);
		engine.addModelChange(X_CC_Evaluation_nursing.Table_Name, this);
		engine.addModelChange(X_CC_CollectiveIntelligence.Table_Name, this);

		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		//ahora se ocupa nuevo metodo consolidado
		if(type == TYPE_BEFORE_NEW && po.get_Table_ID()==X_CC_Evaluation.Table_ID) 
		{	
			X_CC_Evaluation eval = (X_CC_Evaluation)po;
			int ID_Eval = DB.getSQLValue(po.get_TrxName(), "SELECT MAX(CC_Evaluation_ID) FROM CC_Evaluation " +
					" WHERE IsActive = 'Y' AND CC_Hospitalization_ID = "+eval.getCC_Hospitalization_ID()+" AND CC_Evaluation_ID <>"+eval.get_ID());
			if(ID_Eval > 0)
			{
				X_CC_Evaluation evalOld = new X_CC_Evaluation(po.getCtx(), ID_Eval, po.get_TrxName());
				eval.setPlanningHorizon(evalOld.getPlanningHorizon());
				//eval.setProcedureName(evalOld.getProcedureName());
				//eval.setDescription(evalOld.getDescription());
				/*String desc = "";
				String query = "select description from cc_collectiveintelligence where cc_collectiveintelligence_id in (select max(cc_collectiveintelligence_id) from cc_collectiveintelligence where cc_hospitalization_id = "+eval.getCC_Hospitalization_ID()+")";
				PreparedStatement ps1 = DB.prepareStatement(query,po.get_TrxName());
				ResultSet rs1 = ps1.executeQuery();
				while(rs1.next())
					desc = rs1.getString("description");*/
				//if(desc != null)
					//eval.set_CustomColumn("description1", desc);
				
				//mfrojas 20191118
				//Se agrega campos de otras indicaciones.
				eval.setRepose(evalOld.getRepose());
				eval.setComments(evalOld.getComments());
				eval.setDietFood(evalOld.getDietFood());
				eval.setHelp(evalOld.getHelp());
			}
		}
		if(type == TYPE_AFTER_NEW && po.get_Table_ID()==X_CC_Evaluation.Table_ID) 
		{	
			X_CC_Evaluation eval = (X_CC_Evaluation)po;
			int ID_Eval = DB.getSQLValue(po.get_TrxName(), "SELECT coalesce(MAX(CC_Evaluation_ID),0) FROM CC_Evaluation " +
					" WHERE IsActive = 'Y' AND CC_Hospitalization_ID = "+eval.getCC_Hospitalization_ID()+" AND CC_Evaluation_ID <>"+eval.get_ID());
			if(ID_Eval > 0)
			{
				//se crean medicamentos
				String sql = "SELECT M_Product_ID, Qty, C_UOM_ID, Medicines_Status,DeliveryViaRule,Description, frecuency, medicine " +
						" FROM CC_MedicalIndications WHERE IsActive = 'Y' AND CC_Evaluation_ID = ?";
				PreparedStatement ps = DB.prepareStatement(sql, po.get_TrxName());
				ps.setInt(1, ID_Eval);
				ResultSet rs = ps.executeQuery();				
				while (rs.next()) 
				{
					X_CC_MedicalIndications ind = new X_CC_MedicalIndications(po.getCtx(), 0, po.get_TrxName());
					ind.setCC_Evaluation_ID(eval.get_ID());
					ind.setC_BPartner_ID(eval.getC_BPartner_ID());
					ind.setM_Product_ID(rs.getInt("M_Product_ID"));
					ind.setQty(rs.getBigDecimal("Qty"));
					ind.setC_UOM_ID(rs.getInt("C_UOM_ID"));
					ind.setMedicines_Status(rs.getString("Medicines_Status"));
					ind.setDeliveryViaRule(rs.getString("DeliveryViaRule"));
					ind.setDescription(rs.getString("Description"));
					//mfrojas
					ind.setIsActive(true);
					//end mfrojas
					//20191118
					ind.set_CustomColumn("Frecuency", rs.getString("Frecuency"));
					ind.set_CustomColumn("Medicine", rs.getString("Medicine"));
					ind.saveEx(po.get_TrxName());

				}
				rs.close();
				ps.close();				
			}
			
			/**
			 * mfrojas 20190121
			 * si id_eval = 0, entonces es la primera evolucion. En este caso se debe traer medicalindications
			 * del ingreso (hospitalization), en caso de existir
			 */
			else
			{
				//se crean medicamentos
				String sql = "SELECT M_Product_ID, Qty, C_UOM_ID, Medicines_Status,DeliveryViaRule,Description, frecuency, medicine " +
						" FROM CC_MedicalIndications WHERE IsActive = 'Y' AND CC_Hospitalization_ID = ?";
				PreparedStatement ps = DB.prepareStatement(sql, po.get_TrxName());
				ps.setInt(1, eval.getCC_Hospitalization_ID());
				ResultSet rs = ps.executeQuery();				
				while (rs.next()) 
				{
					X_CC_MedicalIndications ind = new X_CC_MedicalIndications(po.getCtx(), 0, po.get_TrxName());
					ind.setCC_Evaluation_ID(eval.get_ID());
					ind.setC_BPartner_ID(eval.getC_BPartner_ID());
					ind.setM_Product_ID(rs.getInt("M_Product_ID"));
					ind.setQty(rs.getBigDecimal("Qty"));
					ind.setC_UOM_ID(rs.getInt("C_UOM_ID"));
					ind.setMedicines_Status(rs.getString("Medicines_Status"));
					ind.setDeliveryViaRule(rs.getString("DeliveryViaRule"));
					ind.setDescription(rs.getString("Description"));
					//mfrojas
					ind.setIsActive(true);
					//end mfrojas
					//20191118
					ind.set_CustomColumn("Frecuency", rs.getString("Frecuency"));
					ind.set_CustomColumn("Medicine", rs.getString("Medicine"));
					ind.saveEx(po.get_TrxName());

				}
				rs.close();
				ps.close();				
				
			}
		}
		if(type == TYPE_BEFORE_NEW && po.get_Table_ID()==X_CC_Epicrisis.Table_ID) 
		{	
			X_CC_Epicrisis epi = (X_CC_Epicrisis)po;
			epi.setHelp(epi.getCC_Hospitalization().getDescriptionURL());
			int ID_Eval = DB.getSQLValue(po.get_TrxName(), "SELECT MAX(CC_Evaluation_ID) FROM CC_Evaluation " +
					" WHERE IsActive = 'Y' AND CC_Hospitalization_ID = "+epi.getCC_Hospitalization_ID());
			if(ID_Eval > 0)
			{
				X_CC_Evaluation lastEval = new X_CC_Evaluation(po.getCtx(), ID_Eval, po.get_TrxName());
				epi.setNote(lastEval.getDescription());
			}			
		}
		if((type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE) && po.get_Table_ID()==X_CC_MedicalIndications.Table_ID) 
		{	
			X_CC_MedicalIndications mInd = (X_CC_MedicalIndications)po;
			int cantMatch = DB.getSQLValue(po.get_TrxName(), "SELECT COUNT(1) FROM CC_Background " +
					" WHERE (C_BPartner_ID = "+mInd.getC_BPartner_ID()+" OR CC_Hospitalization_ID=" +mInd.getCC_Hospitalization_ID()+")"+
					" AND upper(Description1) like upper('%"+mInd.getM_Product().getHelp()+"%')");
			if(cantMatch > 0)
				return Msg.getMsg(po.getCtx(), "Revisar Alergias");						
		}
		if(type == TYPE_BEFORE_NEW && po.get_Table_ID()==X_CC_Evaluation_nursing.Table_ID) 
		{	
			X_CC_Evaluation_nursing evN = (X_CC_Evaluation_nursing)po;
			// se setea campo con informacion anterior
			int ID_EvalN = DB.getSQLValue(po.get_TrxName(), "SELECT MAX(CC_Evaluation_nursing_ID) FROM CC_Evaluation_nursing " +
					" WHERE IsActive = 'Y' AND CC_Hospitalization_ID = "+evN.getCC_Hospitalization_ID()+
					" AND CC_Evaluation_nursing_ID <>"+evN.get_ID());
			if(ID_EvalN > 0)
			{
				X_CC_Evaluation_nursing evalOld = new X_CC_Evaluation_nursing(po.getCtx(), ID_EvalN, po.get_TrxName());
				//mfrojas a solicitud de CC (20200518) no ira la descripcion de enfermeria
				//evN.setDescription(evalOld.getDescription());				
			}
			//se agrega info de inteligencia colectiva
			/*int ID_ci = DB.getSQLValue(po.get_TrxName(), "SELECT MAX(CC_CollectiveIntelligence_ID) FROM CC_CollectiveIntelligence " +
					" WHERE IsActive = 'Y' AND CC_Hospitalization_ID = "+evN.getCC_Hospitalization_ID());
			if(ID_ci > 0)
			{
				X_CC_CollectiveIntelligence ci = new X_CC_CollectiveIntelligence(po.getCtx(), ID_ci, po.get_TrxName());
				//ev.setDescription1(ci.getDescription()); //aun no habilitar  
			}*/			
		}
		if(type == TYPE_BEFORE_NEW && po.get_Table_ID()==X_CC_Evaluation_nursing.Table_ID) 
		{	
			X_CC_Evaluation_nursing eval = (X_CC_Evaluation_nursing)po;
			int ID_Eval = DB.getSQLValue(po.get_TrxName(), "SELECT MAX(CC_Evaluation_nursing_ID) FROM CC_Evaluation_nursing " +
					" WHERE IsActive = 'Y' AND CC_Hospitalization_ID = "+eval.getCC_Hospitalization_ID()+" AND CC_Evaluation_nursing_ID <>"+eval.get_ID());
			
			log.config("id_Eval "+ID_Eval);
			if(ID_Eval > 0)
			{
				X_CC_Evaluation_nursing evalOld = new X_CC_Evaluation_nursing(po.getCtx(), ID_Eval, po.get_TrxName());
				eval.set_CustomColumn("Weight", evalOld.get_Value("Weight1"));
				log.config("diuresis1 = "+evalOld.getDiuresis());
				eval.setDiuresis1(evalOld.getDiuresis());
				eval.set_CustomColumn("Diuresis2", evalOld.get_Value("Diuresis3"));

				//Comentado mfrojas 20190806
				/*String desc = "";
				String query = "select description from cc_collectiveintelligence where cc_collectiveintelligence_id in (select max(cc_collectiveintelligence_id) from cc_collectiveintelligence where cc_hospitalization_id = "+eval.getCC_Hospitalization_ID()+")";
				PreparedStatement ps1 = DB.prepareStatement(query,po.get_TrxName());
				ResultSet rs1 = ps1.executeQuery();
				while(rs1.next())
					desc = rs1.getString("description");
				if(desc != null)
					eval.set_CustomColumn("description", desc);*/
				
			}
		}
		if((type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE) && po.get_Table_ID()==X_CC_CollectiveIntelligence.Table_ID) 
		{	
			X_CC_CollectiveIntelligence cInt = (X_CC_CollectiveIntelligence)po;
			int idSummary = DB.getSQLValue(po.get_TrxName(), "SELECT MAX(CC_CollectiveIntelligence_ID) FROM CC_CollectiveIntelligence " +
					" WHERE CC_Evaluation_ID = "+cInt.get_ValueAsInt("CC_Evaluation_ID")+" AND IsSummary = 'Y' AND Type = '"+cInt.get_ValueAsString("Type")+"'");
			
			String fieldsCI[]= {"CC_CI01","CC_CI02","CC_CI03","CC_CI04","CC_CI05","CC_CI06","CC_CI07","CC_CI08","CC_CI09","CC_CI10",
					"CC_CI11","CC_CI12","CC_CI13","CC_CI14","CC_CI15","CC_CI16","CC_CI17","CC_CI18","CC_CI19","CC_CI20",
					"CC_CI21","CC_CI22","CC_CI23","CC_CI24","CC_CI25","CC_CI26","CC_CI27","CC_CI28","CC_CI29","CC_CI30",
					"CC_CI31","CC_CI32","CC_CI33","CC_CI34","CC_CI35","CC_CI36","CC_CI37","CC_CI38","CC_CI39","CC_CI40",
					"CC_CI41","CC_CI42","CC_CI43","CC_CI44","CC_CI45","CC_CI46","CC_CI47","CC_CI48","CC_CI49","CC_CI50",
					"CC_CI51","CC_CI52","CC_CI53","CC_CI54","CC_CI55","CC_CI56","CC_CI57","CC_CI58","CC_CI59","CC_CI60"};
			//existe resumen
			if(idSummary > 0)
			{
				X_CC_CollectiveIntelligence summ = new X_CC_CollectiveIntelligence(po.getCtx(), idSummary, po.get_TrxName());
				//ciclo de campos
				for(int a=0; a<fieldsCI.length; a++)
				{	
					if(cInt.get_ValueAsBoolean(fieldsCI[a]))
					{	
						summ.set_CustomColumn(fieldsCI[a], true);
					}	
				}
				summ.saveEx(po.get_TrxName());
			}
			else //no existe resumen, se debe crear
			{
				X_CC_CollectiveIntelligence summ = new X_CC_CollectiveIntelligence(po.getCtx(), 0, po.get_TrxName());
				summ.setCC_Hospitalization_ID(cInt.getCC_Hospitalization_ID());
				summ.set_CustomColumn("CC_Evaluation_ID", cInt.get_ValueAsInt("CC_Evaluation_ID"));				
				summ.set_CustomColumn("IsSummary", true);
				summ.set_CustomColumn("Type", cInt.get_ValueAsString("Type"));
				for(int a=0; a<fieldsCI.length; a++)
				{	
					if(cInt.get_ValueAsBoolean(fieldsCI[a]))
					{	
						summ.set_CustomColumn(fieldsCI[a], true);
					}	
				}
				summ.saveEx(po.get_TrxName());
			}		
			if(cInt.get_ValueAsBoolean("IsSummary"))
			{
				StringBuilder Desc = new StringBuilder();
				//se genera descripcion
				for(int a=0; a<fieldsCI.length; a++)
				{	
					if(cInt.get_ValueAsBoolean(fieldsCI[a]))
					{
						int ID_Column = DB.getSQLValue(null, "SELECT AD_Column_ID FROM " +
								" AD_Column WHERE AD_Table_ID = "+X_CC_CollectiveIntelligence.Table_ID+
								" AND ColumnName = '"+fieldsCI[a]+"'");
						if(ID_Column > 0)
						{
							MColumn colum = new MColumn(Env.getCtx(),ID_Column,null);
							String textAux = "";			
							if(colum.getDescription() != null && !Desc.toString().contains(colum.getDescription()))
								textAux = textAux+colum.getDescription()+":"+"\n";
							if(colum.getHelp() != null)
								textAux=textAux+colum.getHelp();
							if(textAux != null && textAux.trim().length() > 1)
							{
								Desc.append(textAux);
								Desc.append(" - ");
								Desc.append("\n");
							}
						}
					}	
				}
				if(Desc != null && Desc.toString().trim().length() > 1)
				{
					if(cInt.get_ValueAsInt("CC_Evaluation_ID") > 0)
					{
						X_CC_Evaluation eval = new X_CC_Evaluation(po.getCtx(), cInt.get_ValueAsInt("CC_Evaluation_ID"), po.get_TrxName());
						eval.set_CustomColumn("Description1", Desc.toString());
						eval.saveEx(po.get_TrxName());
					}
				}
			}
		}
		if(type == TYPE_BEFORE_NEW && po.get_Table_ID()==X_CC_Evaluation.Table_ID) 
		{	
			X_CC_Evaluation eval = (X_CC_Evaluation)po;
			int ID_Eval = DB.getSQLValue(po.get_TrxName(), "SELECT MAX(CC_Evaluation_ID) FROM CC_Evaluation " +
					" WHERE IsActive = 'Y' AND CC_Hospitalization_ID = "+eval.getCC_Hospitalization_ID()+" AND CC_Evaluation_ID <>"+eval.get_ID());
			if(ID_Eval > 0)
			{
				int ID_ciSumm = DB.getSQLValue(po.get_TrxName(), "SELECT MAX(CC_CollectiveIntelligence_ID) FROM CC_CollectiveIntelligence " +
						" WHERE IsActive = 'Y' AND CC_Evaluation_ID = "+ID_Eval+" AND IsSummary = 'Y'");
				if(ID_ciSumm > 0)
				{					
					String fieldsCI[]= {"CC_CI01","CC_CI02","CC_CI03","CC_CI04","CC_CI05","CC_CI06","CC_CI07","CC_CI08","CC_CI09","CC_CI10",
							"CC_CI11","CC_CI12","CC_CI13","CC_CI14","CC_CI15","CC_CI16","CC_CI17","CC_CI18","CC_CI19","CC_CI20",
							"CC_CI21","CC_CI22","CC_CI23","CC_CI24","CC_CI25","CC_CI26","CC_CI27","CC_CI28","CC_CI29","CC_CI30",
							"CC_CI31","CC_CI32","CC_CI33","CC_CI34","CC_CI35","CC_CI36","CC_CI37","CC_CI38","CC_CI39","CC_CI40",
							"CC_CI41","CC_CI42","CC_CI43","CC_CI44","CC_CI45","CC_CI46","CC_CI47","CC_CI48","CC_CI49","CC_CI50",
							"CC_CI51","CC_CI52","CC_CI53","CC_CI54","CC_CI55","CC_CI56","CC_CI57","CC_CI58","CC_CI59","CC_CI60"};
					StringBuilder Desc = new StringBuilder();
					X_CC_CollectiveIntelligence summ = new X_CC_CollectiveIntelligence(po.getCtx(), ID_ciSumm, po.get_TrxName());
					//se genera descripcion
					for(int a=0; a<fieldsCI.length; a++)
					{	
						if(summ.get_ValueAsBoolean(fieldsCI[a]))
						{
							int ID_Column = DB.getSQLValue(null, "SELECT AD_Column_ID FROM " +
									" AD_Column WHERE AD_Table_ID = "+X_CC_CollectiveIntelligence.Table_ID+
									" AND ColumnName = '"+fieldsCI[a]+"'");
							if(ID_Column > 0)
							{
								MColumn colum = new MColumn(Env.getCtx(),ID_Column,null);
								String textAux = "";			
								if(colum.getDescription() != null && !Desc.toString().contains(colum.getDescription()))
									textAux = textAux+colum.getDescription()+":"+"\n";
								if(colum.getHelp() != null)
									textAux=textAux+colum.getHelp();
								if(textAux != null && textAux.trim().length() > 1)
								{
									Desc.append(textAux);
									Desc.append(" - ");
									Desc.append("\n");
								}
							}
						}	
					}
					if(Desc != null && Desc.toString().trim().length() > 1)
						eval.set_CustomColumn("Description1", Desc.toString());
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