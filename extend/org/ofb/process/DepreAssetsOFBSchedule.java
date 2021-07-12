/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is Compiere ERP & CRM Smart Business Solution. The Initial
 * Developer of the Original Code is Jorg Janke. Portions created by Jorg Janke
 * are Copyright (C) 1999-2005 Jorg Janke.
 * All parts are Copyright (C) 1999-2005 ComPiere, Inc.  All Rights Reserved.
 * Contributor(s): ______________________________________.
 *****************************************************************************/
package org.ofb.process;

import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.model.*;
import org.compiere.util.*;

/**
 * 	Special Process
 *	
 *  @author mfrojas
 *  @version $Id: DepreAssets.java,v 1.3 2006/02/24 07:15:43 jjanke Exp $
 */
public class DepreAssetsOFBSchedule extends SvrProcess
{

	/** Properties						*/
	private Properties 		m_ctx;
	/*Order ID*/
	private int 			Record_ID;
	
	X_A_Asset_Dep DepDoc = null;
	
	
	protected void prepare()
	{ 
		m_ctx = Env.getCtx();
	//	Record_ID=getRecord_ID();
	//	DepDoc= new X_A_Asset_Dep(m_ctx,Record_ID,get_TrxName());		
	}	//	prepare

	/**
	 * 	Proccess
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt() throws Exception
	{
	/*Timestamp datefrom=null;
	Ti*/
	
		String sqlobtaindep = "SELECT count(1) from a_Asset_dep where " +
			" docstatus='DR' AND isvalid='Y'";
		int qty = DB.getSQLValue(get_TrxName(), sqlobtaindep);
		if(qty == 0)
			return "No hay procesos pendientes";
		/*if(qty > 3)
			return "Se espera a lo mas 3 procesos listos para procesar";*/
	
		String sqlobtain = "SELECT a_Asset_Dep_id from a_Asset_Dep where " +
			" docstatus='DR' and isvalid='Y' and deptype like 'DDP' order by created";
		PreparedStatement pstmt2 = null;
		pstmt2 = DB.prepareStatement(sqlobtain, get_TrxName());
		ResultSet rs2 = pstmt2.executeQuery();
	
		while (rs2.next() )
		{
			DepDoc= new X_A_Asset_Dep(m_ctx,rs2.getInt(1) ,get_TrxName());		

			String sql = "";
	
			if(DepDoc.isSameYear()){
				Calendar cal=Calendar.getInstance();
				cal.set(Calendar.DAY_OF_YEAR, 1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				//datefrom= new Timestamp(cal.getTimeInMillis());
				cal.set(Calendar.MONTH, 11);
				cal.set(Calendar.DAY_OF_MONTH, 31);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				//dateto= new Timestamp(cal.getTimeInMillis());
			}else{
				Calendar cal=Calendar.getInstance();
				cal.set(Calendar.DAY_OF_YEAR, 1);
				cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)-1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				//datefrom= new Timestamp(cal.getTimeInMillis());
				cal.set(Calendar.MONTH, 11);
				cal.set(Calendar.DAY_OF_MONTH, 31);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				//dateto= new Timestamp(cal.getTimeInMillis());
			}
			String ID_grupo = "";
			try
			{
				int id_GrupoPadre = DepDoc.get_ValueAsInt("A_Asset_Group_Ref_ID");
				if (id_GrupoPadre > 0)
					ID_grupo = " a.A_Asset_Group_Ref_ID = "+DepDoc.get_ValueAsInt("A_Asset_Group_Ref_ID");
				else
					ID_grupo = " a.a_asset_group_id = "+DepDoc.getA_Asset_Group_ID();
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE, sql.toString(), e);
				ID_grupo = " a.a_asset_group_id="+DepDoc.getA_Asset_Group_ID();
			}
			if (ID_grupo == null || ID_grupo == "")
				ID_grupo = " a.a_asset_group_id="+DepDoc.getA_Asset_Group_ID();
	
			MOrg orgP = new MOrg(getCtx(), DepDoc.getAD_Org_ID(), get_TrxName());
			if(orgP.getValue()!= null)
				if(!orgP.getValue().contains("*"))
					sql+= " and a.AD_OrgRef_ID="+DepDoc.getAD_Org_ID();
	
			if(DepDoc.getDepType().equalsIgnoreCase("DDP")) //Depreciacion OFB
			{
				//Se sobreescribe: Los periodos a depreciar ahora corresponderan a aquellos que correspondan al mismo año del periodo que aca se obtiene. 
				sql="select a.a_asset_id,a.name,a.value,acct.c_acctschema_id, acct.a_asset_acct, "
						+" acct.a_depreciation_acct, fore.amount,fore.a_asset_forecast_id,"
						+" acct.a_accumdepreciation_acct,acct.A_Disposal_Gain_Acct,wk.a_asset_cost,acct.A_Disposal_RevenueC_Acct,"
						+" acct.A_Disposal_RevenueD_Acct,acct.A_Disposal_Loss_Acct, wk.a_accumulated_depr, acct.A_AssetComplement_Acct"
						+" from a_asset a"
						+" inner join a_asset_acct acct on (a.a_asset_id=acct.a_asset_id)"
						+" inner join A_Depreciation_Workfile wk on (a.a_asset_id=wk.a_asset_id)"
						+" inner join a_asset_forecast fore on (a.a_asset_id=fore.a_asset_id)"
						//+" inner join c_period p on (fore.datedoc between p.startdate and p.enddate)"
						+" where a.ad_client_id=? ";// and p.c_period_id=? ";
		
				sql += " AND extract(year from fore.datedoc) = (select to_number(fiscalyear,'9999') " +
						" from c_year where c_year_id in (select c_year_id from c_period where c_period_id = "+DepDoc.getC_Period_ID()+")) AND fore.processed='N' AND InTransit = 'N' ";
				sql += " and a.isinposession='Y' And "+ID_grupo;
				sql += " and a.isactive='Y'";

				//MOrg orgP = new MOrg(getCtx(), DepDoc.getAD_Org_ID(), get_TrxName());
				if(orgP.getValue()!= null)
					if(!orgP.getValue().contains("*"))
						sql+= " and a.AD_OrgRef_ID="+DepDoc.getAD_Org_ID();

		
				}
				log.config("SQL Activos: "+sql);
				PreparedStatement pstmt = null;
	
				MJournalBatch batch= null;
				MJournal journal = null;
				MClient client= MClient.get(m_ctx,Env.getAD_Client_ID(m_ctx));
				int count=0;
				int countJournal=0;
				int flag = 0;
				int flag_batch = 0;
				int flag_return = 0;
				if(DepDoc.getDocStatus().equals("DR") && DepDoc.getDepType().equalsIgnoreCase("ANC")== false)
				{		
					try
					{
						pstmt = DB.prepareStatement(sql, get_TrxName());
						pstmt.setInt(1, DepDoc.getAD_Client_ID());
						//pstmt.setInt(2, DepDoc.getC_Period_ID());			
						/*if(DepDoc.getDepType().equalsIgnoreCase((X_A_Asset_Dep.DEPTYPE_CorrecionMonetaria ))){
					pstmt.setTimestamp(3, datefrom);
				pstmt.setTimestamp(4, dateto);
				}*/
						ResultSet rs = pstmt.executeQuery();
						while (rs.next() )
						{
							int ID_Org = 0;
							
							ID_Org = DepDoc.getAD_Org_ID();
							
							if( flag_batch == 0 && DepDoc.getGL_JournalBatch_ID() > 0 )
							{
								batch = new MJournalBatch(m_ctx, DepDoc.getGL_JournalBatch_ID(), get_TrxName());
								flag_batch = 1;
							}

							if(batch==null)
							{
								batch = new MJournalBatch(m_ctx,0,get_TrxName());
								batch.setAD_Org_ID(ID_Org);
								batch.setDescription("Assets Depreciation Process");
								batch.setPostingType("A");
								batch.setC_DocType_ID(MDocType.getOfDocBaseType(m_ctx, "GLJ")[0].getC_DocType_ID());
								batch.setGL_Category_ID(MGLCategory.getDefault(m_ctx, MGLCategory.CATEGORYTYPE_Manual).getGL_Category_ID());
								batch.setDateAcct(DepDoc.getDateDoc());
								batch.setDateDoc(DepDoc.getDateDoc());
								batch.setC_Period_ID(MPeriod.getC_Period_ID(m_ctx, DepDoc.getDateAcct()));
								batch.setC_Currency_ID(client.getC_Currency_ID());
								if(!batch.save())
								{
									return "la transaccion no puede ser generara para *";
								}
							}
							count++;
							
							if(count>0 && flag == 0)
							{
								DepDoc.setGL_JournalBatch_ID(batch.getGL_JournalBatch_ID());
								DepDoc.save();
								flag = 1;
								//ininoles se saca update de lineas de journal para pruebas de rendimiento
								//DB.executeUpdate("Update GL_JournalLine set A_Asset_Dep_ID="+DepDoc.getA_Asset_Dep_ID()
								//		+ " Where GL_Journal_ID IN (select GL_Journal_ID from GL_Journal where GL_JournalBatch_ID="+batch.getGL_JournalBatch_ID()+" )", get_TrxName());
							}
							/*else
						{
						if(batch!=null && count == 0)
							batch.delete(true, get_TrxName());
						}
							 */
							MAsset assetTemp = new MAsset(getCtx(), rs.getInt("a_asset_id"), get_TrxName());
							journal= new MJournal(batch);
							journal.setDescription(rs.getString("value")+"-"+rs.getString("name"));
							journal.setC_AcctSchema_ID(MAcctSchema.getClientAcctSchema(m_ctx, Env.getAD_Client_ID(m_ctx))[0].getC_AcctSchema_ID() );
							journal.setGL_Category_ID(MGLCategory.getDefault(m_ctx, MGLCategory.CATEGORYTYPE_Document).getGL_Category_ID() );
							journal.setC_ConversionType_ID(114);
							journal.set_ValueOfColumn("A_Asset_ID", rs.getInt("a_asset_id"));
				 
							journal.save();
							//ininoles se setea org con campo OrgRef de Activo
							journal.setAD_Org_ID(assetTemp.get_ValueAsInt("AD_OrgRef_ID"));
							journal.save();
				
							if(DepDoc.getDepType().equalsIgnoreCase("DDP")){ //depreciacion OFB
								MJournalLine line1= new MJournalLine(journal);
								line1.setA_Asset_ID(rs.getInt("a_asset_id"));
								line1.setAmtSourceDr(rs.getBigDecimal("amount"));
								line1.setAmtSourceCr(Env.ZERO);
								//line1.setAmtAcct(rs.getBigDecimal("amount"), Env.ZERO);
								line1.setAmtAcctDr(rs.getBigDecimal("amount"));
								line1.setAmtAcctCr(Env.ZERO);					
								line1.setC_ValidCombination_ID(rs.getInt("a_depreciation_acct"));
								line1.save();
								line1.setAD_Org_ID(assetTemp.get_ValueAsInt("AD_OrgRef_ID"));
								line1.save();
					
								MJournalLine line2= new MJournalLine(journal);
								line2.setA_Asset_ID(rs.getInt("a_asset_id"));
								line2.setAmtSourceDr(Env.ZERO);
								line2.setAmtSourceCr(rs.getBigDecimal("amount"));
								line2.setAmtAcctDr(Env.ZERO);
								line2.setAmtAcctCr(rs.getBigDecimal("amount"));
								//line2.setAmtAcct(Env.ZERO, rs.getBigDecimal("amount"));
								//line2.setC_ValidCombination_ID(rs.getInt("a_asset_acct")); // old
								line2.setC_ValidCombination_ID(rs.getInt("A_AssetComplement_Acct")); //new
								line2.save();
								line2.setAD_Org_ID(assetTemp.get_ValueAsInt("AD_OrgRef_ID"));
								line2.save();		
					
								X_A_Asset_Forecast forec = new X_A_Asset_Forecast(m_ctx, rs.getInt("a_asset_forecast_id"), get_TrxName());
								forec.set_CustomColumn("InTransit", "Y");
								forec.saveEx();
							}
							countJournal++;
							if(countJournal == 150)
							{
								commitEx();//se hara commit despues de crear las lineas
								countJournal = 0;
								flag_return++;
								if(flag_return > 20)
									return "";
							}
						}
						rs.close();
						pstmt.close();
					}
					catch(Exception e)
					{
						log.log(Level.SEVERE, sql.toString(), e);
					}
		

					DepDoc.setDocStatus("IP");
					DepDoc.save();	
				}
	
	
			}
		this.commitEx();
		return "";
		}	//	doIt		
}	//	ImportPayment

