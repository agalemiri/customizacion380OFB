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
package org.blumos.model;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.acct.Doc;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
/**
 *	Validator for Blumos
 *
 *  @author Italo Niñoles
 */
public class ModBlumosAcctAllocation implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModBlumosAcctAllocation ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModBlumosAcctAllocation.class);
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
		engine.addModelChange(MAllocationHdr.Table_Name, this);		
		//	Documents to be monitored
		engine.addDocValidate(MAllocationHdr.Table_Name, this);
				
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		if((type == TYPE_AFTER_CHANGE || type == TYPE_AFTER_NEW) && po.get_Table_ID()==MAllocationHdr.Table_ID) 
		{	
			MAllocationHdr allo = (MAllocationHdr)po;
			if(allo.getAD_Client_ID() == 1000015 && allo.get_ID() == 1208618)
			{
				//se borra contabilidad existente
				DB.executeUpdate("DELETE FROM Fact_Acct WHERE AD_Table_ID="+MAllocationHdr.Table_ID+
						" AND Record_ID="+allo.get_ID(),po.get_TrxName());
				
				String sqlAllo = "SELECT * FROM C_AllocationHdr WHERE C_AllocationHdr_ID = "+allo.get_ID();
				PreparedStatement pstmtAllo = null;
				pstmtAllo = DB.prepareStatement (sqlAllo, po.get_TrxName());					
				ResultSet rsAllo = pstmtAllo.executeQuery();
				if(rsAllo.next())
				{
					//ciclo de esquemas contables
					String sql = "SELECT C_AcctSchema_ID FROM C_AcctSchema WHERE " +
							" IsActive = 'Y' AND AD_CLient_ID = "+allo.getAD_Client_ID();
					PreparedStatement pstmt = null;
					try
					{
						pstmt = DB.prepareStatement (sql, po.get_TrxName());					
						ResultSet rs = pstmt.executeQuery();
						log.config("dentro de try antes de ciclo");
						while(rs.next ())
						{
							log.config("dentro de ciclo while 1");
							MAcctSchema as = new MAcctSchema(po.getCtx(), rs.getInt("C_AcctSchema_ID"), po.get_TrxName());
							MAllocationLine[] lines = allo.getLines(true);	//	Line is default
							for (int i = 0; i < lines.length; i++)
							{
								log.config("dentro de for");
								MAllocationLine line = lines[i];	
								if(line.getC_Invoice_ID() > 0 && line.getC_Payment_ID() > 0)
								{
									log.config("dentro de IF");
									MPayment pay = new MPayment(po.getCtx(), line.getC_Payment_ID(), po.get_TrxName());
									MInvoice inv = new MInvoice(po.getCtx(), line.getC_Invoice_ID(), po.get_TrxName());
									BigDecimal ratePay = MConversionRate.getRate(pay.getC_Currency_ID(),as.getC_Currency_ID(),pay.getDateAcct(),114,pay.getAD_Client_ID(), pay.getAD_Org_ID());
									BigDecimal rateInv = MConversionRate.getRate(inv.getC_Currency_ID(),as.getC_Currency_ID(),inv.getDateAcct(),114,inv.getAD_Client_ID(), inv.getAD_Org_ID());
									BigDecimal amountLine = line.getAmount().abs();
									if(inv.getC_DocType().getDocBaseType().compareTo("API")==0)
									{
										log.config("dentro de if docbase");
										//linea factura
										log.config("Factura de compra");
										String sqlLiability = "SELECT V_Liability_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID="+inv.getC_BPartner_ID()+" AND C_AcctSchema_ID="+as.get_ID();
										int id_vCombinationL = DB.getSQLValue(po.get_TrxName(), sqlLiability);
										int id_acctL =DB.getSQLValue(po.get_TrxName(),"SELECT account_id FROM c_validcombination where c_validcombination_id = "+id_vCombinationL);
										//MAccount accountL = new MAccount(po.getCtx(), id_vCombinationL, po.get_TrxName());
										/*MAcctSchema ass[] = new MAcctSchema[1];
										ass[0] = as;
										Doc_AllocationHdr doc = new Doc_AllocationHdr(ass,rsAllo, po.get_TrxName());
										FactLine fLine = new FactLine(po.getCtx(), allo.get_Table_ID(), allo.get_ID(), line.get_ID(), po.get_TrxName());
										fLine.setDocumentInfo(doc, null);
										fLine.setPostingType(Fact.POST_Actual);
										fLine.setAccount(as, accountL);
										fLine.setC_Currency_ID(allo.getC_Currency_ID());
										fLine.setAmtAcct(allo.getC_Currency_ID(), line.getAmount().multiply(rateInv), null);
										fLine.setAmtSource(allo.getC_Currency_ID(), line.getAmount().multiply(rateInv), null);
										fLine.saveEx(po.get_TrxName());*/
										// se inseta registro por db
										String sqlInsert1 ="";
										//linea 1
										sqlInsert1 ="INSERT INTO fact_acct (fact_acct_id," +
												" ad_client_id,ad_org_id,isactive,created,createdby,updated,updatedby," +
												" c_acctschema_id,account_id,datetrx,dateacct,c_period_id,ad_table_id,record_id,line_id," +
												" gl_category_id,postingtype,c_currency_id,amtsourcedr,amtsourcecr,amtacctdr,amtacctcr," +
												" c_bpartner_id,description) " +
												" VALUES ((select max(fact_acct_id)+1 from fact_acct)," +
												allo.getAD_Client_ID()+","+inv.getAD_Org_ID()+",'Y',now(),0,now(),0," +
												as.get_ID()+","+id_acctL+",?,?,"+MPeriod.getC_Period_ID(po.getCtx(),allo.getDateAcct())+","+allo.Table_ID+","+allo.get_ID()+","+line.get_ID()+"," +
												" 1000173,'A',"+as.getC_Currency_ID()+","+amountLine.multiply(rateInv).setScale(0,RoundingMode.HALF_DOWN)+",0,"+amountLine.multiply(rateInv).setScale(0,RoundingMode.HALF_DOWN)+",0," +
												inv.getC_BPartner_ID()+",'"+allo.getDocumentNo()+" #0 "+allo.getDescription()+"' )";
										log.config("SQL LINEA 1:"+ sqlInsert1);
										PreparedStatement pstmtInv = DB.prepareStatement (sqlInsert1, po.get_TrxName());;
										pstmtInv.setTimestamp(1, allo.getDateAcct());
										pstmtInv.setTimestamp(2, allo.getDateAcct());
										pstmtInv.execute();
										
										//linea pago
										/*FactLine fLine2 = new FactLine(po.getCtx(), allo.get_Table_ID(), allo.get_ID(), line.get_ID(), po.get_TrxName());
										fLine2.setDocumentInfo(doc, null);
										fLine2.setPostingType(Fact.POST_Actual);
										fLine2.setAccount(as, getPaymentAcct(as, line.getC_Payment_ID(), po, pay.getC_BPartner_ID()));
										fLine2.setC_Currency_ID(allo.getC_Currency_ID());
										fLine2.setAmtAcct(allo.getC_Currency_ID(),null,line.getAmount().multiply(ratePay));
										fLine2.setAmtSource(allo.getC_Currency_ID(),null, line.getAmount().multiply(ratePay));
										fLine2.saveEx(po.get_TrxName());
										log.config(fLine2.toString());*/
										// se inseta registro por db
										//linea 2
										int ID_accountPay = getPaymentAcct(as, line.getC_Payment_ID(), po, pay.getC_BPartner_ID());
										sqlInsert1 ="INSERT INTO fact_acct (fact_acct_id," +
												" ad_client_id,ad_org_id,isactive,created,createdby,updated,updatedby," +
												" c_acctschema_id,account_id,datetrx,dateacct,c_period_id,ad_table_id,record_id,line_id," +
												" gl_category_id,postingtype,c_currency_id,amtsourcedr,amtsourcecr,amtacctdr,amtacctcr," +
												" c_bpartner_id,description) " +
												" VALUES ((select max(fact_acct_id)+1 from fact_acct)," +
												allo.getAD_Client_ID()+","+pay.getAD_Org_ID()+",'Y',now(),0,now(),0," +
												as.get_ID()+","+ID_accountPay+",?,?,"+MPeriod.getC_Period_ID(po.getCtx(),allo.getDateAcct())+","+allo.Table_ID+","+allo.get_ID()+","+line.get_ID()+"," +
												" 1000173,'A',"+as.getC_Currency_ID()+",0,"+amountLine.multiply(rateInv).setScale(0,RoundingMode.HALF_DOWN)+",0,"+amountLine.multiply(rateInv).setScale(0,RoundingMode.HALF_DOWN)+"," +
												pay.getC_BPartner_ID()+",'"+allo.getDocumentNo()+" #0 "+allo.getDescription()+"' )";
										log.config("SQL LINEA 2:"+ sqlInsert1);
										PreparedStatement pstmtPay = DB.prepareStatement (sqlInsert1, po.get_TrxName());;
										pstmtPay.setTimestamp(1, allo.getDateAcct());
										pstmtPay.setTimestamp(2, allo.getDateAcct());
										pstmtPay.execute();
										
										//se agrega linea de diferencia de cambio
										BigDecimal FlagDiff = amountLine.multiply(rateInv).subtract(amountLine.multiply(ratePay));
										FlagDiff = FlagDiff.setScale(0, RoundingMode.HALF_EVEN);
										if(FlagDiff.compareTo(Env.ZERO) != 0)
										{
											String sqlDif = "SELECT CurrencyBalancing_Acct FROM C_AcctSchema_GL WHERE C_AcctSchema_ID="+as.get_ID();
											int id_vCombinationD = DB.getSQLValue(po.get_TrxName(), sqlDif);
											//MAccount accountD = new MAccount(po.getCtx(), id_vCombinationD, po.get_TrxName());
											int id_acctD =DB.getSQLValue(po.get_TrxName(),"SELECT account_id FROM c_validcombination where c_validcombination_id = "+id_vCombinationD);
											/*FactLine fLine3 = new FactLine(po.getCtx(), allo.get_Table_ID(), allo.get_ID(), line.get_ID(), po.get_TrxName());
											fLine3.setDocumentInfo(doc, null);
											fLine3.setPostingType(Fact.POST_Actual);
											fLine3.setAccount(as,accountD);
											fLine3.setC_Currency_ID(allo.getC_Currency_ID());*/
											BigDecimal DebitDif = Env.ZERO;
											BigDecimal CreditDif = Env.ZERO;
											//factura mayor a pago
											if(amountLine.multiply(rateInv).compareTo(amountLine.multiply(ratePay)) > 0)
											{
												CreditDif = amountLine.multiply(rateInv).subtract(amountLine.multiply(ratePay)).setScale(0, RoundingMode.HALF_EVEN);
												//fLine3.setAmtAcct(allo.getC_Currency_ID(),null,line.getAmount().multiply(rateInv).subtract(line.getAmount().multiply(ratePay)).setScale(0, RoundingMode.HALF_EVEN));
												//fLine3.setAmtSource(allo.getC_Currency_ID(),null,line.getAmount().multiply(rateInv).subtract(line.getAmount().multiply(ratePay)).setScale(0, RoundingMode.HALF_EVEN));
											}
											else
											{
												DebitDif = amountLine.multiply(rateInv).subtract(amountLine.multiply(ratePay)).setScale(0, RoundingMode.HALF_EVEN);
												//fLine3.setAmtAcct(allo.getC_Currency_ID(),line.getAmount().multiply(ratePay).subtract(line.getAmount().multiply(rateInv)).setScale(0, RoundingMode.HALF_EVEN),null);
												//fLine3.setAmtSource(allo.getC_Currency_ID(),line.getAmount().multiply(ratePay).subtract(line.getAmount().multiply(rateInv)).setScale(0, RoundingMode.HALF_EVEN),null);
											}
											/*fLine3.saveEx(po.get_TrxName());
											log.config(fLine3.toString());*/
											//linea 3 
											sqlInsert1 ="INSERT INTO fact_acct (fact_acct_id," +
													" ad_client_id,ad_org_id,isactive,created,createdby,updated,updatedby," +
													" c_acctschema_id,account_id,datetrx,dateacct,c_period_id,ad_table_id,record_id,line_id," +
													" gl_category_id,postingtype,c_currency_id,amtsourcedr,amtsourcecr,amtacctdr,amtacctcr," +
													" c_bpartner_id,description) " +
													" VALUES ((select max(fact_acct_id)+1 from fact_acct)," +
													allo.getAD_Client_ID()+","+pay.getAD_Org_ID()+",'Y',now(),0,now(),0," +
													as.get_ID()+","+id_acctD+",?,?,"+MPeriod.getC_Period_ID(po.getCtx(),allo.getDateAcct())+","+allo.Table_ID+","+allo.get_ID()+","+line.get_ID()+"," +
													" 1000173,'A',"+as.getC_Currency_ID()+","+DebitDif+","+CreditDif+","+DebitDif+","+CreditDif+"," +
													pay.getC_BPartner_ID()+",'"+allo.getDocumentNo()+" #0 "+allo.getDescription()+"' )";
											log.config("SQL LINEA 3:"+ sqlInsert1);
											PreparedStatement pstmtDif = DB.prepareStatement (sqlInsert1, po.get_TrxName());;
											pstmtDif.setTimestamp(1, allo.getDateAcct());
											pstmtDif.setTimestamp(2, allo.getDateAcct());
											pstmtDif.execute();
											
										}
										
									}
								}
							}	
							
						}
					}catch (Exception e)
					{
						pstmt = null;
					}
					//se actualiza aignación
					DB.executeUpdate("UPDATE C_AllocationHdr SET Posted='Y' WHERE C_AllocationHdr_ID = "+allo.get_ID(), po.get_TrxName());
					//se actualiza secuencia de fact_acct
					int id_Seq = DB.getSQLValue(po.get_TrxName(),"select MAX(ad_sequence_id) from ad_sequence where name like 'Fact_Acct'");
					int next_ID = DB.getSQLValue(po.get_TrxName(), "select max(fact_acct_id)+1 from fact_acct");
					if(id_Seq > 0 && next_ID > 0)
					{
						DB.executeUpdate("UPDATE ad_sequence SET currentnext = "+next_ID+" WHERE ad_sequence_id = "+id_Seq,po.get_TrxName());
					}
				}
			}
		}			
		return null;
	}	//	modelChange

	public String docValidate (PO po, int timing)
	{
		log.info(po.get_TableName() + " Timing: "+timing);
		if(timing == TIMING_AFTER_COMPLETE && po.get_Table_ID()==MAllocationHdr.Table_ID) 
		{	
			MAllocationHdr allo = (MAllocationHdr)po;
			if(allo.getAD_Client_ID() == 1000015)
			{
				//se borra contabilidad existente
				DB.executeUpdate("DELETE FROM Fact_Acct WHERE AD_Table_ID="+MAllocationHdr.Table_ID+
						" AND Record_ID="+allo.get_ID(),po.get_TrxName());
				
				try
				{
					String sqlAllo = "SELECT * FROM C_AllocationHdr WHERE C_AllocationHdr_ID = "+allo.get_ID();
					PreparedStatement pstmtAllo = null;
					pstmtAllo = DB.prepareStatement (sqlAllo, po.get_TrxName());					
					ResultSet rsAllo = pstmtAllo.executeQuery();
					if(rsAllo.next())
					{
						//ciclo de esquemas contables
						String sql = "SELECT C_AcctSchema_ID FROM C_AcctSchema WHERE " +
								" IsActive = 'Y' AND AD_CLient_ID = "+allo.getAD_Client_ID();
						PreparedStatement pstmt = null;
						try
						{
							pstmt = DB.prepareStatement (sql, po.get_TrxName());					
							ResultSet rs = pstmt.executeQuery();
							log.config("dentro de try antes de ciclo");
							while(rs.next ())
							{
								log.config("dentro de ciclo while 1");
								MAcctSchema as = new MAcctSchema(po.getCtx(), rs.getInt("C_AcctSchema_ID"), po.get_TrxName());
								MAllocationLine[] lines = allo.getLines(true);	//	Line is default
								for (int i = 0; i < lines.length; i++)
								{
									log.config("dentro de for");
									MAllocationLine line = lines[i];	
									if(line.getC_Invoice_ID() > 0 && line.getC_Payment_ID() > 0)
									{
										log.config("dentro de IF");
										MPayment pay = new MPayment(po.getCtx(), line.getC_Payment_ID(), po.get_TrxName());
										MInvoice inv = new MInvoice(po.getCtx(), line.getC_Invoice_ID(), po.get_TrxName());
										BigDecimal ratePay = MConversionRate.getRate(pay.getC_Currency_ID(),as.getC_Currency_ID(),pay.getDateAcct(),114,pay.getAD_Client_ID(), pay.getAD_Org_ID());
										BigDecimal rateInv = MConversionRate.getRate(inv.getC_Currency_ID(),as.getC_Currency_ID(),inv.getDateAcct(),114,inv.getAD_Client_ID(), inv.getAD_Org_ID());
										BigDecimal amountLine = line.getAmount().abs();
										if(inv.getC_DocType().getDocBaseType().compareTo("API")==0)
										{
											log.config("dentro de if docbase");
											//linea factura
											log.config("Factura de compra");
											String sqlLiability = "SELECT V_Liability_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID="+inv.getC_BPartner_ID()+" AND C_AcctSchema_ID="+as.get_ID();
											int id_vCombinationL = DB.getSQLValue(po.get_TrxName(), sqlLiability);
											int id_acctL =DB.getSQLValue(po.get_TrxName(),"SELECT account_id FROM c_validcombination where c_validcombination_id = "+id_vCombinationL);										
											// se inseta registro por db
											String sqlInsert1 ="";
											//linea 1
											sqlInsert1 ="INSERT INTO fact_acct (fact_acct_id," +
													" ad_client_id,ad_org_id,isactive,created,createdby,updated,updatedby," +
													" c_acctschema_id,account_id,datetrx,dateacct,c_period_id,ad_table_id,record_id,line_id," +
													" gl_category_id,postingtype,c_currency_id,amtsourcedr,amtsourcecr,amtacctdr,amtacctcr," +
													" c_bpartner_id,description) " +
													" VALUES ((select max(fact_acct_id)+1 from fact_acct)," +
													allo.getAD_Client_ID()+","+inv.getAD_Org_ID()+",'Y',now(),0,now(),0," +
													as.get_ID()+","+id_acctL+",?,?,"+MPeriod.getC_Period_ID(po.getCtx(),allo.getDateAcct())+","+allo.Table_ID+","+allo.get_ID()+","+line.get_ID()+"," +
													" 1000173,'A',"+as.getC_Currency_ID()+","+amountLine.multiply(rateInv).setScale(0,RoundingMode.HALF_DOWN)+",0,"+amountLine.multiply(rateInv).setScale(0,RoundingMode.HALF_DOWN)+",0," +
													inv.getC_BPartner_ID()+",'"+allo.getDocumentNo()+" #0 "+allo.getDescription()+"' )";
											log.config("SQL LINEA 1:"+ sqlInsert1);
											PreparedStatement pstmtInv = DB.prepareStatement (sqlInsert1, po.get_TrxName());;
											pstmtInv.setTimestamp(1, allo.getDateAcct());
											pstmtInv.setTimestamp(2, allo.getDateAcct());
											pstmtInv.execute();
											//linea pago
											// se inseta registro por db
											//linea 2
											int ID_accountPay = getPaymentAcct(as, line.getC_Payment_ID(), po, pay.getC_BPartner_ID());
											sqlInsert1 ="INSERT INTO fact_acct (fact_acct_id," +
													" ad_client_id,ad_org_id,isactive,created,createdby,updated,updatedby," +
													" c_acctschema_id,account_id,datetrx,dateacct,c_period_id,ad_table_id,record_id,line_id," +
													" gl_category_id,postingtype,c_currency_id,amtsourcedr,amtsourcecr,amtacctdr,amtacctcr," +
													" c_bpartner_id,description) " +
													" VALUES ((select max(fact_acct_id)+1 from fact_acct)," +
													allo.getAD_Client_ID()+","+pay.getAD_Org_ID()+",'Y',now(),0,now(),0," +
													as.get_ID()+","+ID_accountPay+",?,?,"+MPeriod.getC_Period_ID(po.getCtx(),allo.getDateAcct())+","+allo.Table_ID+","+allo.get_ID()+","+line.get_ID()+"," +
													" 1000173,'A',"+as.getC_Currency_ID()+",0,"+amountLine.multiply(rateInv).setScale(0,RoundingMode.HALF_DOWN)+",0,"+amountLine.multiply(rateInv).setScale(0,RoundingMode.HALF_DOWN)+"," +
													pay.getC_BPartner_ID()+",'"+allo.getDocumentNo()+" #0 "+allo.getDescription()+"' )";
											log.config("SQL LINEA 2:"+ sqlInsert1);
											PreparedStatement pstmtPay = DB.prepareStatement (sqlInsert1, po.get_TrxName());;
											pstmtPay.setTimestamp(1, allo.getDateAcct());
											pstmtPay.setTimestamp(2, allo.getDateAcct());
											pstmtPay.execute();
											
											//se agrega linea de diferencia de cambio
											BigDecimal FlagDiff = amountLine.multiply(rateInv).subtract(amountLine.multiply(ratePay));
											FlagDiff = FlagDiff.setScale(0, RoundingMode.HALF_EVEN);
											if(FlagDiff.compareTo(Env.ZERO) != 0)
											{
												String sqlDif = "SELECT CurrencyBalancing_Acct FROM C_AcctSchema_GL WHERE C_AcctSchema_ID="+as.get_ID();
												int id_vCombinationD = DB.getSQLValue(po.get_TrxName(), sqlDif);
												int id_acctD =DB.getSQLValue(po.get_TrxName(),"SELECT account_id FROM c_validcombination where c_validcombination_id = "+id_vCombinationD);
												BigDecimal DebitDif = Env.ZERO;
												BigDecimal CreditDif = Env.ZERO;
												//factura mayor a pago
												if(amountLine.multiply(rateInv).compareTo(amountLine.multiply(ratePay)) > 0)
													CreditDif = amountLine.multiply(rateInv).subtract(amountLine.multiply(ratePay)).setScale(0, RoundingMode.HALF_EVEN);
												else
													DebitDif = amountLine.multiply(rateInv).subtract(amountLine.multiply(ratePay)).setScale(0, RoundingMode.HALF_EVEN);
												//linea 3 
												sqlInsert1 ="INSERT INTO fact_acct (fact_acct_id," +
														" ad_client_id,ad_org_id,isactive,created,createdby,updated,updatedby," +
														" c_acctschema_id,account_id,datetrx,dateacct,c_period_id,ad_table_id,record_id,line_id," +
														" gl_category_id,postingtype,c_currency_id,amtsourcedr,amtsourcecr,amtacctdr,amtacctcr," +
														" c_bpartner_id,description) " +
														" VALUES ((select max(fact_acct_id)+1 from fact_acct)," +
														allo.getAD_Client_ID()+","+pay.getAD_Org_ID()+",'Y',now(),0,now(),0," +
														as.get_ID()+","+id_acctD+",?,?,"+MPeriod.getC_Period_ID(po.getCtx(),allo.getDateAcct())+","+allo.Table_ID+","+allo.get_ID()+","+line.get_ID()+"," +
														" 1000173,'A',"+as.getC_Currency_ID()+","+DebitDif+","+CreditDif+","+DebitDif+","+CreditDif+"," +
														pay.getC_BPartner_ID()+",'"+allo.getDocumentNo()+" #0 "+allo.getDescription()+"' )";
												log.config("SQL LINEA 3:"+ sqlInsert1);
												PreparedStatement pstmtDif = DB.prepareStatement (sqlInsert1, po.get_TrxName());;
												pstmtDif.setTimestamp(1, allo.getDateAcct());
												pstmtDif.setTimestamp(2, allo.getDateAcct());
												pstmtDif.execute();
												
											}
											
										}
									}
								}	
								
							}
						}catch (Exception e)
						{
							pstmt = null;
						}
						//se actualiza aignación
						DB.executeUpdate("UPDATE C_AllocationHdr SET Posted='Y' WHERE C_AllocationHdr_ID = "+allo.get_ID(), po.get_TrxName());
						//se actualiza secuencia de fact_acct
						int id_Seq = DB.getSQLValue(po.get_TrxName(),"select MAX(ad_sequence_id) from ad_sequence where name like 'Fact_Acct'");
						int next_ID = DB.getSQLValue(po.get_TrxName(), "select max(fact_acct_id)+1 from fact_acct");
						if(id_Seq > 0 && next_ID > 0)
						{
							DB.executeUpdate("UPDATE ad_sequence SET currentnext = "+next_ID+" WHERE ad_sequence_id = "+id_Seq,po.get_TrxName());
						}
					}
				}
				catch (Exception e)
				{
					log.config(e.toString());
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

	private int getPaymentAcct (MAcctSchema as, int C_Payment_ID, PO po,int ID_Bpartner)
	{
		int ID_validCom = 0;
		String sqlVC = "";
		//
		String sql = "SELECT p.C_BankAccount_ID, d.DocBaseType, p.IsReceipt, p.IsPrepayment "
			+ "FROM C_Payment p INNER JOIN C_DocType d ON (p.C_DocType_ID=d.C_DocType_ID) "
			+ "WHERE C_Payment_ID=?";		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, po.get_TrxName());
			pstmt.setInt (1, C_Payment_ID);
			rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				if (Doc.DOCTYPE_APPayment.equals(rs.getString(2)))
					sqlVC = "SELECT B_PaymentSelect_Acct FROM C_BankAccount_Acct WHERE C_BankAccount_ID="+rs.getInt(1)+" AND C_AcctSchema_ID="+as.get_ID();
				//	Prepayment
				if ("Y".equals(rs.getString(4)))		//	Prepayment
				{
					if ("Y".equals(rs.getString(3)))	//	Receipt
						sqlVC = "SELECT C_Prepayment_Acct FROM C_BP_Customer_Acct WHERE C_BPartner_ID="+ID_Bpartner+" AND C_AcctSchema_ID="+as.get_ID();
					else
						sqlVC = "SELECT V_Prepayment_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID="+ID_Bpartner+" AND C_AcctSchema_ID="+as.get_ID();
				}
			}
			ID_validCom = DB.getSQLValue(po.get_TrxName(), sqlVC);
 		} 
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
		int id_acctL =DB.getSQLValue(po.get_TrxName(),"SELECT account_id FROM c_validcombination where c_validcombination_id = "+ID_validCom);
		//
		return id_acctL;
	}	//	getPaymentAcct
}	