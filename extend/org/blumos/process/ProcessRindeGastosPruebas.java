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

import org.apache.poi.hssf.record.crypto.Biff8DecryptingStream;
import org.compiere.model.MCash;
import org.compiere.model.MCashBook;
import org.compiere.model.MCashLine;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;

/**
 *  Copy Order Lines
 *
 *	@author ininoles
 *	@version $Id: ProcessRindeGastos.java,v 1.2 2020/11/05 
 */
public class ProcessRindeGastosPruebas extends SvrProcess
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
		int cant=0;
		int cantHead=0;
		BigDecimal totalAmtMax = new BigDecimal("5.0");
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
		
	    //se busca id de informe de gastos.
	    HashMap<String,String> paramsGetExpenseReports = new HashMap<>();
	    paramsGetExpenseReports.put("Since",dateFromTxt);
	    paramsGetExpenseReports.put("Until",dateToTxt);
	    paramsGetExpenseReports.put("TypeDateFilter", "1");
	    paramsGetExpenseReports.put("Currency", "CLP");
	    paramsGetExpenseReports.put("Status","1");
	    paramsGetExpenseReports.put("ExpensePolicyId","");
	    paramsGetExpenseReports.put("IntegrationStatus", "");
	    paramsGetExpenseReports.put("IntegrationCode", "");
	    paramsGetExpenseReports.put("IntegrationDate", "");
	    paramsGetExpenseReports.put("UserId","");
	    paramsGetExpenseReports.put("OrderBy","");
	    paramsGetExpenseReports.put("Order","");
	    paramsGetExpenseReports.put("ResultsPerPage","");
	    paramsGetExpenseReports.put("Page","");
        //example calling method getExpenses
        String jsonTextEReport = x.getExpenseReports(paramsGetExpenseReports);
		log.config("jsonText = "+jsonTextEReport);
		JSONObject jsonEReport = new JSONObject(jsonTextEReport);
		JSONArray jsonarrEReport = jsonEReport.getJSONArray("ExpenseReports");
		int qtyRegistrosEReport = jsonarrEReport.length();
	    String IDEReport = "0";
	    String ReportNumber = "-";
	    String Title = "-";
	    int IDEnteroEreport = 0;
	    int IDUsadoEReport = 0;
		String policyID = "";
		String UserId = "";
		int ID_CashBook = 0;
		int existPoli =0;
		MClient client = new MClient(getCtx(), get_TrxName());
		int existJCash = 0;
		MCash cash = null;
		
		//variables usadas en el detalle
		String ID = "0";
		int IDEntero = 0;
		int IDUsado = 0;
		String total = "";
		BigDecimal totalAmt = Env.ZERO;
		String supplier = "";
		String rutProveedor = "";
		String numDoc = "";
		String tipoDoc = "";
		String kilometraje = "";
		BigDecimal kilometrajeNum = Env.ZERO;
		String combustible = "";
		BigDecimal combustibleNum = Env.ZERO;
		String centroCosto = "";
		int ID_Vendor=0;
		int ID_invoice=0;
		int ID_Cargo;
		int ID_Asset;
		MInvoice inv = null;
		
		int ID_Org;
		String group = "";
		String groupCode = "";
		
		if(qtyRegistrosEReport > 0)
		{
			for(int c=0; c<qtyRegistrosEReport;c++)
			{
				IDEReport = jsonarrEReport.getJSONObject(c).get("Id").toString();
				policyID = jsonarrEReport.getJSONObject(c).get("PolicyId").toString();
				UserId = jsonarrEReport.getJSONObject(c).get("EmployeeId").toString();
				ReportNumber = jsonarrEReport.getJSONObject(c).get("ReportNumber").toString();
				Title = jsonarrEReport.getJSONObject(c).get("Title").toString();
				
				if(IDEReport != null && IDEReport.trim().length()>0)
					IDEnteroEreport = Integer.parseInt(IDEReport);
				else
				{
					IDEnteroEreport = 0;
					IDUsadoEReport = 0;
				}
				//if(IDEnteroEreport > 0)
				//	IDUsadoEReport = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_Cash WHERE IDRindegastos="+IDEnteroEreport);

				if(IDUsadoEReport <=0
					&& IDEReport.compareTo("1426159")==0) 
					//	&& ReportNumber.compareTo("1492")==0)
				{
					//se valida que policyID pertenesca al cliente.
					if(policyID == null)
						policyID="0";
					if(UserId == null)
						UserId="-1";
					
					
					HashMap<String,String> paramsGetExpenses = new HashMap<>();
					//paramsGetExpenses.put("Since", "2020-01-01");
					paramsGetExpenses.put("Since","");
					//paramsGetExpenses.put("Until", "2020-02-26");
					paramsGetExpenses.put("Until","");
					paramsGetExpenses.put("Currency", "CLP");
					paramsGetExpenses.put("Status", "1");
					paramsGetExpenses.put("Category","");
					paramsGetExpenses.put("ReportId",IDEReport);					
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
					
					ID = "0";
					IDEntero = 0;
					IDUsado = 0;
					total = "";
					totalAmt = Env.ZERO;
					supplier = "";
					rutProveedor = "";
					numDoc = "";
					tipoDoc = "";
					kilometraje = "";
					kilometrajeNum = Env.ZERO;
					combustible = "";
					combustibleNum = Env.ZERO;
					centroCosto = "";
					ID_Vendor=0;
					ID_invoice=0;
					inv = null;
					ID_Cargo =0;
					ID_Asset =0;
							
					ID_Org=0;
					group = "";
					groupCode = "";
					
					String salida = "";					
					BigDecimal openAmt = Env.ZERO;
					int qtyRegistros = jsonarr.length();
					if(qtyRegistros > 0)
					{
						for(int a=0; a<qtyRegistros;a++)
						{	
							salida = "";
							ID_invoice=0;
							inv=null;
							ID_Cargo =0;
							ID_Asset =0;
							ID_Org =0;
							IDEntero = 0;
							IDUsado = 0;
							existJCash = 0;
							rutProveedor = "";
							totalAmt = Env.ZERO;
							numDoc = "";
							tipoDoc = "";
							kilometraje = "";
							combustible = "";
							
							centroCosto = "";
							ID = jsonarr.getJSONObject(a).get("Id").toString();
							total = jsonarr.getJSONObject(a).get("Total").toString();
							supplier = jsonarr.getJSONObject(a).get("Supplier").toString();
							//policyID = jsonarr.getJSONObject(a).get("ExpensePolicyId").toString();
							//UserId = jsonarr.getJSONObject(a).get("UserId").toString();
							group = jsonarr.getJSONObject(a).get("CategoryGroup").toString();
							groupCode = jsonarr.getJSONObject(a).get("CategoryGroupCode").toString();
							
							if(ID.compareTo("8449935")==0)
								log.config("stop");
							
							//if(policyID == null)
							//	policyID="0";									
							//existPoli = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM RD_ExpensePolicies WHERE AD_Client_ID="+client.get_ID()+
							//		" AND expensepolicyid = "+policyID);				
							//if(existPoli > 0)
							//{
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
									else if(jsonArrDet.getJSONObject(b).get("Name").toString() != null
											&& jsonArrDet.getJSONObject(b).get("Name").toString().compareTo("RUT Proveedor")==0)
									{
										rutProveedor=jsonArrDet.getJSONObject(b).get("Value").toString();
									}
									else if(jsonArrDet.getJSONObject(b).get("Name").toString() != null
											&& jsonArrDet.getJSONObject(b).get("Name").toString().compareTo("Kilometraje")==0)
									{
										kilometraje=jsonArrDet.getJSONObject(b).get("Value").toString();
									}
									else if(jsonArrDet.getJSONObject(b).get("Name").toString() != null
											&& jsonArrDet.getJSONObject(b).get("Name").toString().compareTo("Centro de Costo")==0)
									{
										centroCosto=jsonArrDet.getJSONObject(b).get("Value").toString();
									}
									else if(jsonArrDet.getJSONObject(b).get("Name").toString() != null
											&& jsonArrDet.getJSONObject(b).get("Name").toString().compareTo("Tipo de Documento")==0)
									{
										tipoDoc=jsonArrDet.getJSONObject(b).get("Value").toString();
									}
									else if(jsonArrDet.getJSONObject(b).get("Name").toString() != null
											&& jsonArrDet.getJSONObject(b).get("Name").toString().compareTo("Cantidad de Litros")==0)
									{
										combustible=jsonArrDet.getJSONObject(b).get("Value").toString();
									}
								}
								
							}
						}
					}
				}
			}
		}
		
		return "Se han creado "+cantHead+" cabeceras. Se han agregado "+cant+" lineas de rendición";
	}	//	doIt
	
	
}	//	CopyFromOrder
