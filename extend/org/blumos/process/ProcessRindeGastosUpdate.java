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

import java.util.HashMap;
import org.json.JSONObject;
import org.rindegastos.api.SdkRindegastosReturn;
import org.json.JSONArray;
import org.compiere.process.*;
import org.compiere.util.DB;
import org.compiere.model.MCashBook;
import org.compiere.model.MClient;
import org.compiere.model.X_RD_ExpensePolicies;

/**
 *  Copy Order Lines
 *
 *	@author ininoles
 *	@version $Id: ProcessRindeGastos.java,v 1.2 2020/11/05 
 */
public class ProcessRindeGastosUpdate extends SvrProcess
{
	/**	The Order				*/
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		/*ProcessInfoParameter[] para = getParameter();
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
		}*/
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		MClient client = MClient.get(getCtx());
		String TokenPass = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiODYwNTQiLCJjb21wYW55X2lkIjoiMTk2OTAiLCJyYW5kb20iOiJyYW5kQVBJNWY2MjM3YjE5OGQ3YzUuNzYwNzkzMjgifQ.y5QUA5yVZIn3fxs5QsrZAFU4ZZoQqeFjgwMJ7mTYUKs";
	    SdkRindegastosReturn x = new SdkRindegastosReturn(TokenPass);
		int Policies = 0;
		int users = 0;
	    //actualizacion de politicas de gasto
        HashMap<String,String> paramsGetExpenses = new HashMap<>();
        paramsGetExpenses.put("Status", "1");
        paramsGetExpenses.put("OrderBy","");
        paramsGetExpenses.put("Order","");
        paramsGetExpenses.put("ResultsPerPage","");
        paramsGetExpenses.put("Page","");
        //example calling method getExpenses
        String jsonText = x.getExpensePolicies(paramsGetExpenses);
		log.config("jsonText = "+jsonText);
		JSONObject json = new JSONObject(jsonText);
		//se crean registros.
		//int qtyExpenseP= Integer.parseInt((String)json.get("TotalRecords"));
		JSONArray jsonarr = json.getJSONArray("Policies");
		int qtyRegistros = jsonarr.length();
		if(qtyRegistros > 0)
		{
			
			String ID ="";
			String name="";
			int exist = 0;
			for(int a=0; a<qtyRegistros;a++)
			{
				ID = jsonarr.getJSONObject(a).get("Id").toString();
				name = jsonarr.getJSONObject(a).get("Name").toString();				
				//se revisa si corresponde a la compañía adecuada
				if(name != null && name .trim().length()>0)
				{
					if(name.substring(0, 3).compareTo(client.get_ValueAsString("sigla"))==0)
					{
						//se busca que no exista registro
						exist= DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM RD_ExpensePolicies WHERE AD_Client_ID="+client.get_ID()+
								" AND expensepolicyid = "+ID);
						// se crea registro
						if(exist <= 0)
						{
							X_RD_ExpensePolicies ep = new X_RD_ExpensePolicies(getCtx(), 0, get_TrxName());
							ep.setexpensepolicyid(Integer.parseInt(ID));
							ep.set_CustomColumn("name", name);
							ep.save();
							Policies++;
						}
					}
				}
			}
		}
		
		//actualizacion de usuario(libros de efectivo)
		HashMap<String,String> paramsGetUser = new HashMap<>();
		paramsGetUser.put("OrderBy","");
		paramsGetUser.put("Order","");
		paramsGetUser.put("ResultsPerPage","");
		paramsGetUser.put("Page","");
        //example calling method getExpenses
        String jsonTextUser = x.getUsers(paramsGetUser);
		log.config("jsonTextUsers = "+jsonTextUser);
		JSONObject jsonUser = new JSONObject(jsonTextUser);
		//se actualizan registros de usuario
		JSONArray jsonarrUser = jsonUser.getJSONArray("Users");
		int qtyRegistrosUser = jsonarrUser.length();
		if(qtyRegistrosUser > 0)
		{
			String ID ="";
			String iden="";
			int ID_Book = 0;
			for(int a=0; a<qtyRegistrosUser;a++)
			{
				ID = jsonarrUser.getJSONObject(a).get("Id").toString();
				iden = jsonarrUser.getJSONObject(a).get("Identification").toString();				
				//se busca si existe libro de efectivo para actualizar ID
				if(iden != null && iden.trim().length()>0)
				{
					//se busca que no exista registro
					ID_Book= DB.getSQLValue(get_TrxName(), "SELECT MAX(C_CashBook_ID) FROM C_CashBook WHERE AD_Client_ID="+client.get_ID()+
							" AND name like '"+iden+"'");
					// se crea registro
					if(ID_Book > 0)
					{
						MCashBook book = new MCashBook(getCtx(), ID_Book, get_TrxName());
						book.set_CustomColumn("IDRindegastos", Integer.parseInt(ID));
						book.save();
						users++;
					}
				}
			}
		}
		return "se han creado "+Policies+" politicas de gastos. \nSe han Actualizado "+users+" usuarios";
	}	//	doIt
	
	
}	//	CopyFromOrder
