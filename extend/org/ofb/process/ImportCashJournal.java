/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is                  Compiere  ERP & CRM  Business Solution
 * The Initial Developer of the Original Code is Jorg Janke  and ComPiere, Inc.
 *
 * Copyright (C) 2005 Robert KLEIN. robeklein@gmail.com * 
 * Contributor(s): ______________________________________.
 *****************************************************************************/
package org.ofb.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.compiere.model.MCash;
import org.compiere.model.MCashLine;
import org.compiere.model.MPayment;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
/**
 *	Import c_cash
 *
 * 	@author 	Italo Niñoles
 * 	@version 	$Id: ImportCashJournal.java,v 1.0 $
 */
public class ImportCashJournal extends SvrProcess 
{
	/**
	 * 	Import Asset
	 */
	public ImportCashJournal()
	{
		super();
		
	}	//	ImportAsset

	/**	Client to be imported to		*/
	private int				m_AD_Client_ID = 0;
	/**	Delete old Imported				*/
	private boolean			m_deleteOldImported = false;

	/** Organization to be imported to	*/
	private String				p_DosStatus = "";
	/** Effective						*/
//	private Timestamp	m_DateValue = null;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("DocStatus"))
				p_DosStatus = (para[i].getParameterAsString());
			else
				log.info("ImportAsset.prepare - Unknown Parameter: " + name);
		}
	}	//	prepare


	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		StringBuffer sql = null;
		int no = 0;
		m_AD_Client_ID = Env.getAD_Client_ID(getCtx());
		String clientCheck = " AND AD_Client_ID=" + m_AD_Client_ID;

		//	****	Prepare	****

		//	Delete Old Imported
		if (m_deleteOldImported)
		{
			sql = new StringBuffer ("DELETE I_Cash "
				+ " WHERE I_IsImported='Y' ").append(clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());			
		}		
		//	Set Client, Org, IaActive, Created/Updated, 	ProductType
		sql = new StringBuffer ("UPDATE I_Cash "
				+ "SET AD_Client_ID = COALESCE (AD_Client_ID, ").append(m_AD_Client_ID).append("),"
				+ " AD_Org_ID = COALESCE (AD_Org_ID, 0),"
				+ " IsActive = COALESCE (IsActive, 'Y'),"			
				+ " CreatedBy = COALESCE (CreatedBy, 0),"
				+ " UpdatedBy = COALESCE (UpdatedBy, 0),"
				+ " I_ErrorMsg = NULL,"
				+ " I_IsImported = 'N' "
				+ " WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		no = DB.executeUpdate(sql.toString(),get_TrxName());
		
		// set ORG
		sql = new StringBuffer ("UPDATE I_Cash o "
				+ "SET AD_Org_ID=(SELECT MAX(AD_Org_ID) FROM AD_Org org"
				+ " WHERE o.OrgValue=org.Value AND o.AD_Client_ID=org.AD_Client_ID) "
				+ " WHERE AD_Org_ID IS NULL AND OrgValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set org from Value=" + no);				
		
				// set BP
		sql = new StringBuffer ("UPDATE I_Cash o "
				 + "SET C_BPartner_ID=(SELECT MAX(C_BPartner_ID) FROM C_BPartner bp"
				 + " WHERE o.BPartnerValue=bp.Value AND o.AD_Client_ID=bp.AD_Client_ID) "
				 + "WHERE C_BPartner_ID IS NULL AND BPartnerValue IS NOT NULL"
				 + " AND I_IsImported<>'Y'").append (clientCheck);
		
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set bp from Value=" + no);
			
//			Invalid BP
		sql = new StringBuffer ("UPDATE I_Cash "
				+ "SET I_ErrorMsg=I_ErrorMsg||'ERR=Invalid BP,' "
				+ " WHERE C_BPartner_ID IS NULL AND BPartnerValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		
		//se actualiza factura de compra
		sql = new StringBuffer ("UPDATE I_Cash o "
				 + " SET C_Invoice_ID=(SELECT MAX(C_Invoice_ID) FROM C_Invoice i "
				 + " WHERE o.C_BPartner_ID=i.C_BPartner_ID AND o.documentnoinvoice=i.documentno "
				 + " AND i.Issotrx='N' AND o.AD_Client_ID=i.AD_Client_ID) "
				 + " WHERE C_Invoice_ID IS NULL AND C_BPartner_ID IS NOT NULL AND documentnoinvoice IS NOT NULL "
				 + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());		
		
		//se actualiza cargo
		sql = new StringBuffer ("UPDATE I_Cash o "
				 + "SET C_Charge_ID=(SELECT MAX(C_Charge_ID) FROM C_Charge bp"
				 + " WHERE o.chargename=bp.name AND o.AD_Client_ID=bp.AD_Client_ID) "
				 + " WHERE C_Charge_ID IS NULL AND chargename IS NOT NULL"
				 + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		
		//se actualiza bp de pago		
		sql = new StringBuffer ("UPDATE I_Cash o "
				 + " SET C_BPartnerPayment_ID =(SELECT MAX(C_BPartner_ID) FROM C_BPartner bp"
				 + " WHERE o.BPartnerValuePay=bp.Value AND o.AD_Client_ID=bp.AD_Client_ID) "
				 + " WHERE C_BPartnerPayment_ID IS NULL AND BPartnerValuePay IS NOT NULL"
				 + " AND I_IsImported<>'Y'").append (clientCheck);		
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		
		//se actualiza pago
		sql = new StringBuffer ("UPDATE I_Cash o "
				 + " SET C_Payment_ID=(SELECT MAX(C_Payment_ID) FROM C_Payment p "
				 + " WHERE o.C_BPartnerPayment_ID=p.C_BPartner_ID AND o.documentnopayment=p.documentno "
				 + " AND o.AD_Client_ID=p.AD_Client_ID) "
				 + " WHERE C_Payment_ID IS NULL AND C_BPartnerPayment_ID IS NOT NULL AND documentnopayment IS NOT NULL "
				 + " AND I_IsImported<>'Y'").append (clientCheck);
		
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		
		//int noInsert = 0;
		//int noUpdate = 0;
		//int noInsertPO = 0;
		//int noUpdatePO = 0;

		//	Go through Records
		sql = new StringBuffer ("SELECT * FROM I_Cash "
				+ " WHERE I_IsImported= 'N' AND "
				+ " (C_Invoice_ID > 0 OR C_Charge_ID > 0 OR C_Payment_ID > 0)").append(clientCheck).append(" Order By c_cashbook_id");		    
//		Connection conn = DB.createConnection(false, Connection.TRANSACTION_READ_COMMITTED);
		//String noOld = "";
		int ID_cashOld = 0;
		MCash cash = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		try
		{			
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				//if(noOld.compareTo(rs.getString("DocumentNo"))!=0)//se crea cabecera
				if(ID_cashOld != rs.getInt("c_cashbook_id"))
				{
					//se completa diario antes de crear uno nuevo
					//solo se completara si parametro es CO
					if(cash != null && p_DosStatus != null 
							&& p_DosStatus.compareTo("CO") == 0)
					{
						cash.processIt("CO");
						cash.saveEx(get_TrxName());
					}
					cash = new MCash(getCtx(), 0, get_TrxName());
					cash.setAD_Org_ID(rs.getInt("AD_Org_ID"));
					cash.setC_CashBook_ID(rs.getInt("C_CashBook_ID"));
					cash.setDescription(rs.getString("Description"));
					cash.setStatementDate(rs.getTimestamp("StatementDate"));
					cash.setDateAcct(rs.getTimestamp("DateAcct"));
					cash.save(get_TrxName());
					//noOld = rs.getString("DocumentNo");
					ID_cashOld = rs.getInt("c_cashbook_id");
				}
				if(cash != null)
				{
					MCashLine cLine = new MCashLine(cash);
					cLine.set_CustomColumn("C_Bpartner_ID", rs.getInt("C_Bpartner_ID"));
					cLine.setC_Cash_ID(cash.get_ID());
					cLine.setDescription(rs.getString("DescriptionLine"));
					cLine.setCashType(rs.getString("CashType"));
					cLine.setC_Currency_ID(228);
					cLine.setAmount(rs.getBigDecimal("Amount"));
					//cLine.set_CustomColumn("DocumentNo", rs.getString("DocumentNo"));
					if(rs.getInt("C_Invoice_ID") > 0)
						cLine.setC_Invoice_ID(rs.getInt("C_Invoice_ID"));
					if(rs.getInt("C_Charge_ID") > 0)
						cLine.setC_Charge_ID(rs.getInt("C_Charge_ID"));
					if(rs.getInt("C_Payment_ID") > 0)//si es pago se setea cuenta bancaria y socio de negocio
					{
						MPayment pay = new MPayment(getCtx(), rs.getInt("C_Payment_ID"), get_TrxName());
						cLine.setC_Payment_ID(rs.getInt("C_Payment_ID"));
						cLine.setC_BankAccount_ID(pay.getC_BankAccount_ID());
						cLine.set_CustomColumn("C_BPartner_ID", pay.getC_BPartner_ID());
						cLine.set_CustomColumn("TenderType", pay.getTenderType());
						//si 
						
					}
					if(cLine.save(get_TrxName()))
					{
						DB.executeUpdate("UPDATE I_Cash SET C_Cash_ID="+cash.get_ID()+", C_CashLine_ID="+cLine.get_ID()+","
								+" Processed='Y', I_IsImported='Y' WHERE I_Cash_ID="+rs.getInt("I_Cash_ID"), get_TrxName());
					}
				}	
			}	
			//se completa ultimo diario
			if(cash != null && cash.getDocStatus().compareTo("CO")!=0
					&& p_DosStatus != null 
					&& p_DosStatus.compareTo("CO") == 0)
			{
				cash.processIt("CO");
				cash.saveEx(get_TrxName());
			}
		}
		catch (SQLException e)
		{			
			throw new Exception ("ImportCashJournal.doIt:"+e.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			pstmt = null;
		}
		//	Set Error to indicator to not imported
		sql = new StringBuffer ("UPDATE I_Cash "
			+ "SET I_IsImported='N' WHERE I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(),get_TrxName());		
		this.commitEx();
		return "";
	}	//	doIt

}	//	ImportAsset
