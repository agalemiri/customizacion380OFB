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
package org.blumos.process;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.logging.Level;

import org.json.JSONObject;
import org.ofb.utils.DateUtils;
//import org.rindegastos.api.SdkRindegastos;
import org.rindegastos.api.SdkRindegastosReturn;

//import rindegastosapi.SdkRindegastos;

import org.json.JSONArray;

import org.compiere.process.*;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.sql.Timestamp;

import org.compiere.model.MCash;
import org.compiere.model.MCashLine;
import org.compiere.model.MClient;

/**
 *  Copy Order Lines
 *
 *	@author ininoles
 *	@version $Id: ProcessRindeGastos.java,v 1.2 2020/11/05 
 */
public class ProcessRindeGastosOld extends SvrProcess
{
	/**	The Order				*/
	private Timestamp dateFrom;
	private Timestamp dateTo;
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("DateTrx"))
			{
				dateFrom = para[i].getParameterAsTimestamp();
				dateTo = para[i].getParameterToAsTimestamp();
			}
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		//String result ="";
		if(dateFrom == null)
			dateFrom = DateUtils.today();
		if(dateTo == null)
			dateTo = DateUtils.today();
		String dateFromTxt=(dateFrom.getYear()+1900)+"-";
		if((dateFrom.getMonth()+1) < 10)
			dateFromTxt = dateFromTxt+"0"+(dateFrom.getMonth()+1)+"-";
		else
			dateFromTxt = dateFromTxt+(dateFrom.getMonth()+1)+"-";
		if(dateFrom.getDate()< 10)
			dateFromTxt = dateFromTxt+"0"+dateFrom.getDate();
		else
			dateFromTxt = dateFromTxt+dateFrom.getDate();			
			
		String dateToTxt =(dateTo.getYear()+1900)+"-";
		if((dateTo.getMonth()+1) < 10)
			dateToTxt = dateToTxt+"0"+(dateTo.getMonth()+1)+"-";
		else
			dateToTxt = dateToTxt+(dateTo.getMonth()+1)+"-";
		if(dateTo.getDate()< 10)
			dateToTxt = dateToTxt+"0"+dateTo.getDate();
		else
			dateToTxt = dateToTxt+dateTo.getDate();
		String TokenPass = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiODYwNTQiLCJjb21wYW55X2lkIjoiMTk2OTAiLCJyYW5kb20iOiJyYW5kQVBJNWY2MjM3YjE5OGQ3YzUuNzYwNzkzMjgifQ.y5QUA5yVZIn3fxs5QsrZAFU4ZZoQqeFjgwMJ7mTYUKs";
	    SdkRindegastosReturn x = new SdkRindegastosReturn(TokenPass);
		
        HashMap<String,String> paramsGetExpenses = new HashMap<>();
        //paramsGetExpenses.put("Since", "2020-01-01");
        paramsGetExpenses.put("Since",dateFromTxt);
        //paramsGetExpenses.put("Until", "2020-02-26");
        paramsGetExpenses.put("Until",dateToTxt);
        paramsGetExpenses.put("Currency", "CLP");
        paramsGetExpenses.put("Status", "1");
        paramsGetExpenses.put("Category","");
        paramsGetExpenses.put("ReportId","");
        paramsGetExpenses.put("ExpensePolicyId", "");
        paramsGetExpenses.put("IntegrationStatus", "");
        paramsGetExpenses.put("IntegrationCode", "");
        paramsGetExpenses.put("IntegrationDate", "");
        paramsGetExpenses.put("UserId","");
        paramsGetExpenses.put("OrderBy","");
        paramsGetExpenses.put("Order","");
        paramsGetExpenses.put("ResultsPerPage","");
        paramsGetExpenses.put("Page","");
        //example calling method getExpenses
        String jsonText = x.getExpenses(paramsGetExpenses);
		log.config("jsonText = "+jsonText);
		JSONObject json = new JSONObject(jsonText);
		//se busca cantidad de registros
		//int qtyRegistros = Integer.parseInt((String)json.get("TotalRecords"));
		JSONArray jsonarr = json.getJSONArray("Expenses");
		int qtyRegistros = jsonarr.length();
		String ID = "0";
		int IDEntero = 0;
		int IDUsado = 0;
		String total = "";
		BigDecimal totalAmt = Env.ZERO;
		String supplier = "";
		String rutProveedor = "";
		String numDoc = "";
		String kilometraje = "";
		String centroCosto = "";
		int ID_Vendor=0;
		int ID_invoice=0;
		int ID_Cargo;
		int ID_Asset;
		int cant=0;
		int ID_Org;
		String policyID = "";
		String UserId = "";
		String group = "";
		String groupCode = "";
		MClient client = new MClient(getCtx(), get_TrxName());
		//String sigla = client.get_ValueAsString("sigla");
		String salida = "";
		int ID_CashBook = 0;
		int existPoli =0;
		BigDecimal openAmt = Env.ZERO;
		if(qtyRegistros > 0)
		{
			//se genera solo 1 diario por ahora
			/*MCash cash = new MCash(getCtx(), 0, get_TrxName());
			cash.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
			cash.setC_CashBook_ID(1000009);
			cash.setDescription("GENERADO DESDE RINDEGASTOS");
			cash.setStatementDate(DateUtils.today());
			cash.setDateAcct(DateUtils.today());
			cash.save(get_TrxName());*/
			int existJCash = 0;
			MCash cash = null;
			
			for(int a=0; a<qtyRegistros;a++)
			{	
				salida = "";
				ID_invoice=0;
				ID_Cargo =0;
				ID_Asset =0;
				ID_Org =0;
				IDEntero = 0;
				IDUsado = 0;
				existJCash = 0;
				rutProveedor = "";
				totalAmt = Env.ZERO;
				numDoc = "";
				kilometraje = "";
				centroCosto = "";
				ID = jsonarr.getJSONObject(a).get("Id").toString();
				total = jsonarr.getJSONObject(a).get("Total").toString();
				supplier = jsonarr.getJSONObject(a).get("Supplier").toString();
				policyID = jsonarr.getJSONObject(a).get("ExpensePolicyId").toString();
				UserId = jsonarr.getJSONObject(a).get("UserId").toString();
				group = jsonarr.getJSONObject(a).get("CategoryGroup").toString();
				groupCode = jsonarr.getJSONObject(a).get("CategoryGroupCode").toString();
				//se valida que policyID pertenesca al cliente.
				if(policyID == null)
					policyID="0";
				if(UserId == null)
					UserId="-1";
				existPoli = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM RD_ExpensePolicies WHERE AD_Client_ID="+client.get_ID()+
						" AND expensepolicyid = "+policyID);				
				if(existPoli > 0)
				{	
					//se busca si existe diario abierto
					existJCash = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_Cash_ID) "
							+ " FROM C_Cash cc "
							+ " INNER JOIN C_CashBook cb ON (cc.C_CashBook_ID = cb.C_CashBook_ID)"
							+ " WHERE cc.docStatus IN ('DR','IP') AND cb.IDRindegastos="+UserId);
					if(existJCash > 0)//existe diario abierto
					{	
						cash = new MCash(getCtx(),existJCash, get_TrxName());
					}
					else//sino se crea diario
					{
						//se busca cashbook
						ID_CashBook = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_CashBook_ID) FROM C_CashBook "
								+ " WHERE IDRindegastos ="+UserId+" AND IsActive = 'Y' AND AD_Client_ID="+Env.getAD_Client_ID(getCtx()));
						if(ID_CashBook > 0)//si existe se genera diario
						{
							cash = new MCash(getCtx(), 0, get_TrxName());
							cash.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
							cash.setC_CashBook_ID(ID_CashBook);
							cash.setDescription("GENERADO DESDE RINDEGASTOS");
							cash.setStatementDate(DateUtils.today());
							cash.setDateAcct(DateUtils.today());
							cash.save(get_TrxName());
						}
					}
					if(cash != null)
					{
						//se lee detalle
						if(total != null && total.trim().length() > 0)
							totalAmt = new BigDecimal(total);
						if(totalAmt == null)
							totalAmt=Env.ZERO;
						JSONArray jsonArrDet = jsonarr.getJSONObject(a).getJSONArray("ExtraFields");
						int lenght = jsonArrDet.length();
						for(int b=0; b<lenght; b++)
						{
							if(jsonArrDet.getJSONObject(b).get("Name").toString() != null
									&& jsonArrDet.getJSONObject(b).get("Name").toString().compareTo("N\u00b0 Documento")==0)
							{
								numDoc=jsonArrDet.getJSONObject(b).get("Value").toString();
							}
							if(jsonArrDet.getJSONObject(b).get("Name").toString() != null
									&& jsonArrDet.getJSONObject(b).get("Name").toString().compareTo("RUT Proveedor")==0)
							{
								rutProveedor=jsonArrDet.getJSONObject(b).get("Value").toString();
							}
							if(jsonArrDet.getJSONObject(b).get("Name").toString() != null
									&& jsonArrDet.getJSONObject(b).get("Name").toString().compareTo("Kilometraje")==0)
							{
								kilometraje=jsonArrDet.getJSONObject(b).get("Value").toString();
							}
							if(jsonArrDet.getJSONObject(b).get("Name").toString() != null
									&& jsonArrDet.getJSONObject(b).get("Name").toString().compareTo("Centro de Costo")==0)
							{
								centroCosto=jsonArrDet.getJSONObject(b).get("Value").toString();
							}
						}
						//se busca proveedor
						ID_Vendor = DB.getSQLValue(get_TrxName(), "SELECT C_BPartner_ID FROM C_BPartner "
								+ " WHERE value||'-'||digito like '"+rutProveedor+"'");
						if(ID_Vendor > 0)
						{
							//se busca factura
							ID_invoice = DB.getSQLValue(get_TrxName(), "SELECT C_Invoice_ID FROM C_Invoice "
								+ " WHERE IssoTrx='N' AND IsActive='Y' AND C_Bpartner_ID = "+ID_Vendor
								+ " AND DocumentNo = '"+numDoc+"'");
						}
						else
							salida = salida+" NO Existe Proveedor rut:"+rutProveedor;
						
						//se crea linea
						if(cash != null)
						{
							if(ID != null && ID.trim().length()>0)
								IDEntero = Integer.parseInt(ID);
							else
							{
								IDEntero = 0;
								IDUsado = 0;
							}
							if(IDEntero > 0)
								IDUsado = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_CashLine WHERE IDRindegastos="+IDEntero);
	
							if(IDUsado <=0)
							{
								MCashLine cLine = new MCashLine(cash);
								cLine.set_CustomColumn("C_Bpartner_ID",ID_Vendor);
								cLine.setC_Cash_ID(cash.get_ID());
								if(ID_invoice > 0)
								{
									//se valida que factura tenga monto abierto
									openAmt = DB.getSQLValueBD(get_TrxName(), "SELECT invoiceopen(C_invoice_ID,0) FROM C_Invoice WHERE C_Invoice_ID = "+ID_invoice);
									if(openAmt == null)
										openAmt = Env.ZERO;
									if(openAmt.compareTo(Env.ZERO) > 0)
									{
										cLine.setCashType("I");
										cLine.setC_Invoice_ID(ID_invoice);
									}
									else
									{
										salida = salida+" Factura YA Pagada.";
										cLine.setCashType("C");
										cLine.setC_Charge_ID(1000197);//
									}
								}
								else
								{
									//si existe numero de documento y no existe factura
									if(numDoc != null && numDoc.trim().length()>0)
										salida = salida+" No existe factura asociada.";
										
									cLine.setCashType("C");
									//se busca cargo
									ID_Cargo = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_Charge_ID) FROM C_Charge "
											+ " WHERE name like '"+group+"' AND IsActive='Y' AND AD_Client_ID = "+client.get_ID());
									if(ID_Cargo<=0)
									{
										ID_Cargo=1000197;
										salida = salida+" No existe cargo asociado:"+group;
									}										
									cLine.setC_Charge_ID(ID_Cargo);//
								}
								//se busca recurso
								ID_Asset = DB.getSQLValue(get_TrxName(), "SELECT MAX(A_Asset_ID) FROM A_Asset "
										+ " WHERE name like '"+groupCode+"' AND IsActive='Y' AND AD_Client_ID = "+client.get_ID());
								if(ID_Asset > 0)
									cLine.set_CustomColumn("A_AssetRef_ID", ID_Asset);	
								else
									salida=salida+" No se encontro Recurso:"+groupCode;
								
								//se busca centro de costo
								if(centroCosto != null)
								{
									ID_Org = DB.getSQLValue(get_TrxName(), "SELECT MAX(AD_Org_ID) FROM AD_Org "
											+ " WHERE name like '"+centroCosto+"' AND IsActive='Y' AND AD_Client_ID = "+client.get_ID());
									if(ID_Org > 0)
										cLine.setAD_Org_ID(ID_Org);	
									else
										salida=salida+" No se encontro Centro de costo:"+centroCosto;
								}									
								
								cLine.setDescription("ID:"+ID+" total:"+total+" rut proveedor:"+rutProveedor+" numero doc:"+numDoc+salida);
								cLine.setC_Currency_ID(228);
								cLine.setAmount(totalAmt);						
								if(IDEntero > 0)
									cLine.set_CustomColumn("IDRindegastos",IDEntero);
								//kilometraje
								if(kilometraje != null && kilometraje.trim().length()>0)
									cLine.set_CustomColumn("KILOMETRAJE",new BigDecimal(kilometraje));
								cLine.saveEx(get_TrxName());
								cant++;
							}
						}
					}
				}
			}
		}
		log.config("json : "+json.toString());
		log.config("ID "+ID);
		log.config("total "+total);
		log.config("supplier "+supplier);
		log.config("rutProveedor "+rutProveedor);
		log.config("numDoc "+numDoc);
		
		return "se han agregado "+cant+" lineas de rendición";
	}	//	doIt
	
	
}	//	CopyFromOrder
