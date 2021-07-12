/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
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
package org.cencosud.process;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.*;

import org.compiere.model.*;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.*;
//import org.eevolution.model.MPPForecastDefinition;
//import org.eevolution.model.MPPForecastDefinitionLine;
import org.ofb.model.OFBForward;
import org.ofb.utils.DateUtils;

/**
 *	
 *	
 *  @author italo niñoles ininoles
 *  @version $Id: GenOCCencosud.java,v 1.2 2011/06/12 00:51:01  $
 *  Este metodo realiza un recalculo del stock en base al pronostico 
 *  y el stock cargado en cierta fecha.
 *  
 */
public class GenOCCencosud extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	private Timestamp		p_dateTrx;
	private Timestamp		p_dateInv;
	//private int 			p_ID_Locator;
	private int 			p_ID_PCategory;
	private int 			p_ID_Warehouse;
	
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("DateTrx"))
				p_dateTrx = para[i].getParameterAsTimestamp();
			else if (name.equals("DateAcct"))
				p_dateInv = para[i].getParameterAsTimestamp();
			/*else if (name.equals("M_Locator_ID"))
				p_ID_Locator = para[i].getParameterAsInt();*/
			/*else if (name.equals("M_Product_Group_ID"))
				p_ID_PGroup = para[i].getParameterAsInt();*/
			else if (name.equals("M_Product_Category_ID"))
				p_ID_PCategory = para[i].getParameterAsInt();
			else if (name.equals("M_Warehouse_ID"))
				p_ID_Warehouse = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		//primero se importan los documentos
		MOrder order = null;
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		String rutaArchivo = OFBForward.CencosudRutaStock();
		//1-Se importan inventario
		//se borra stock actual
		DB.executeUpdate("DELETE FROM I_InventoryCencosud", get_TrxName());		
		String pFile = rutaArchivo;
		ArrayList<String[]> datos=new ArrayList<String[]>();
		int cantLines = 0;
	    try 
	    {
	    	FileInputStream fis =new FileInputStream(pFile);
		    InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
		    BufferedReader br = new BufferedReader(isr);
		    String linea=br.readLine();		   
		    //se recorre archivo
		    while(linea!=null)
		    {
		        datos.add(linea.split(";"));
			    linea=br.readLine();
			    cantLines = cantLines + 1;
		    }
		    br.close();
		    isr.close();
		    fis.close();
		    //log.config(datos.toString());
		    //se leen campos del archivo
		    for (int x = 1; x < cantLines; x++)
		    {	
		    	//se busca ubicacion, familia y cantidad
		    	int ID_Locator = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Locator_ID) FROM M_Locator WHERE IsActive = 'Y' AND value like '"+datos.get(x)[0]+"'");
		    	int ID_PGroup = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Product_Group_ID) FROM M_Product_Group WHERE IsActive = 'Y' AND value like '"+datos.get(x)[1]+"'");		    	
		    	BigDecimal qty = (BigDecimal) decimalFormat.parse(datos.get(x)[3].trim());
		    	if(ID_Locator > 0 && ID_PGroup > 0 && qty != null)
		    	{
		    		X_I_InventoryCencosud inv = new X_I_InventoryCencosud(getCtx(), 0, get_TrxName());
		    		inv.setAD_Org_ID(0);
		    		inv.setM_Locator_ID(ID_Locator);
		    		inv.setM_Product_Group_ID(ID_PGroup);
		    		inv.setQtyOnHand(qty);
		    		inv.save(get_TrxName());
		    	}
	    	}
		    datos = null;
	    }
		catch (Exception e) 
		{
			log.config("ERROR: "+e);
		}
		//2-Se importan ordenes en transito
		//se borra stock actual
		DB.executeUpdate("DELETE FROM I_OrderCencosud", get_TrxName());
		rutaArchivo = OFBForward.CencosudRutaEnTransito();
		String pFileO = rutaArchivo;
		ArrayList<String[]> datosO=new ArrayList<String[]>();
		cantLines = 0;
	    try 
	    {
	    	FileInputStream fis =new FileInputStream(pFileO);
		    InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
		    BufferedReader br = new BufferedReader(isr);
		    String linea=br.readLine();		   
		    //se recorre archivo
		    while(linea!=null)
		    {
		        datosO.add(linea.split(";"));
			    linea=br.readLine();
			    cantLines = cantLines + 1;
		    }
		    br.close();
		    isr.close();
		    fis.close();
		    //log.config(datosO.toString());
		    //se leen campos del archivo
		    for (int x = 1; x < cantLines; x++)
		    {	
		    	if(x == 515)
		    		log.config("stop de debug");
		    	//se busca ubicacion, producto, cantidad y fecha
		    	int ID_Locator = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Locator_ID) FROM M_Locator WHERE IsActive = 'Y' AND value like '"+datosO.get(x)[1]+"'");
		    	//int ID_Product = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Product_ID) FROM M_Product WHERE IsActive = 'Y' AND value like '"+datosO.get(x)[4]+"'");		    	
		    	int ID_Product = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Product_ID) FROM M_Product WHERE IsActive = 'Y' AND help like '"+datosO.get(x)[4]+"'");
		    	BigDecimal qty = (BigDecimal) decimalFormat.parse(datosO.get(x)[10].trim());
		    	BigDecimal qty9 = (BigDecimal) decimalFormat.parse(datosO.get(x)[9].trim());
		    	BigDecimal qty8 = (BigDecimal) decimalFormat.parse(datosO.get(x)[8].trim());
		    	BigDecimal qty7 = (BigDecimal) decimalFormat.parse(datosO.get(x)[7].trim());
		    	if(qty == null)
		    		qty = Env.ZERO;
		    	if(qty.compareTo(Env.ZERO) > 0)
		    		qty = Env.ZERO;
		    	else if(qty.compareTo(Env.ZERO) == 0)
		    	{
		    		if(qty9.compareTo(Env.ZERO) > 0)
		    			qty = qty9;
			    	else if(qty9.compareTo(Env.ZERO) == 0)			    		
			    	{
			    		if(qty8.compareTo(Env.ZERO) > 0)
			    			qty = qty8;
				    	else if(qty8.compareTo(Env.ZERO) == 0)
				    	{
				    		qty = qty7;
				    	}
			    	}
		    	}
		    	
		    	Timestamp fePedido = null;
		    	Timestamp feEntrega = null;
		    	if(DateUtils.isDateddMMyyyy(datosO.get(x)[11].trim().toString()))
		    		fePedido = DateUtils.convertDateddMMyyyy(datosO.get(x)[11].trim());
		    	else if(DateUtils.isDateddMMyyyy(datosO.get(x)[11].trim().toString().replace(".", "-")))
		    		fePedido = DateUtils.convertDateddMMyyyy(datosO.get(x)[11].trim().replace(".", "-"));
		    	if(DateUtils.isDateddMMyyyy(datosO.get(x)[12].trim().toString()))
		    		feEntrega = DateUtils.convertDateddMMyyyy(datosO.get(x)[12].trim());
		    	else if(DateUtils.isDateddMMyyyy(datosO.get(x)[12].trim().toString().replace(".", "-")))
		    		feEntrega = DateUtils.convertDateddMMyyyy(datosO.get(x)[12].trim().replace(".", "-"));
		    	if(ID_Locator > 0 && ID_Product > 0 && qty != null 
		    			&& fePedido != null && feEntrega != null)
		    	{
		    		X_I_OrderCencosud iOrder = new X_I_OrderCencosud(getCtx(), 0, get_TrxName());
		    		iOrder.setAD_Org_ID(0);
		    		iOrder.setDocumentNo(datosO.get(x)[0]);
		    		iOrder.setM_Locator_ID(ID_Locator);
		    		iOrder.setM_Product_ID(ID_Product);
		    		iOrder.setMovementQty(qty);
		    		iOrder.setDateAcct(fePedido);
		    		iOrder.setDateTrx(feEntrega);
		    		iOrder.save(get_TrxName());
		    	}
	    	}
		    datosO = null;
	    }
		catch (Exception e) 
		{
			log.config("ERROR: "+e);
		}
		//3-Se importan lineas de forecast		
		/*rutaArchivo = OFBForward.CencosudRutaForecast();
		String pFileF = rutaArchivo;
		ArrayList<String[]> datosF=new ArrayList<String[]>();
		cantLines = 0;
	    try 
	    {
	    	FileInputStream fis =new FileInputStream(pFileF);
		    InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
		    BufferedReader br = new BufferedReader(isr);
		    String linea=br.readLine();		   
		    //se recorre archivo
		    while(linea!=null)
		    {
		        datosF.add(linea.split(";"));
			    linea=br.readLine();
			    cantLines = cantLines + 1;
		    }
		    br.close();
		    isr.close();
		    fis.close();
		    log.config(datosF.toString());
		    //llenado de array de fechas
		    String[] cabecera = new String[12];
		    for (int a = 0; a < 12; a++)
		    {
		    	cabecera[a] = (String)datosF.get(0)[a];
		    }
		    //fin llenado de cabeceras		    
		    //se leen campos del archivo
		    for (int x = 1; x < cantLines; x++)
		    {	
		    	//se busca ubicacion, familia, cantidad
		    	int ID_Locator = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Locator_ID) FROM M_Locator WHERE IsActive = 'Y' AND value like '"+datosF.get(x)[0]+"'");
		    	int ID_PGroup = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Product_Group_ID) FROM M_Product_Group WHERE IsActive = 'Y' AND value like '"+datosF.get(x)[1]+"'");		    	
		    	//se borra stock actual por familia y ubicacion
		    	//if(ID_Locator==2000030)
	    		//{
	    		//	log.config("centro");
	    		//}
		    	if(ID_Locator > 0 && ID_PGroup > 0)
		    	{	
		    		DB.executeUpdate("DELETE FROM PP_ForecastDefinitionLine " +
		    				"WHERE IsActive = 'Y' AND M_Locator_ID = "+ID_Locator+" AND M_Product_Group_ID="+ID_PGroup, get_TrxName());
		    		
		    		//se leen lineas de archivo
		    		int idCol = 3;
		    		int flag = 1;
		    		int seqNo = 10;
		    		while (flag > 0)
		    		{	
		    			//se pregunta si la cabecera es fecha		    			
		    			if(DateUtils.isDateddMMyyyy(cabecera[idCol]))
		    			{
		    				//se busca registro cabecera de pronostico
		    				int id_Fore = DB.getSQLValue(get_TrxName(), "SELECT MAX(PP_ForecastDefinition_ID) FROM PP_ForecastDefinition" +
		    						" WHERE IsActive = 'Y' AND M_Locator_ID = "+ID_Locator);
		    				if(id_Fore < 1)
		    				{
		    					MPPForecastDefinition def = new MPPForecastDefinition(getCtx(), 0, get_TrxName());
		    					def.setAD_Org_ID(0);
		    					def.setName("Pronostico"+datosF.get(x)[0]);
		    					def.set_CustomColumn("M_Locator_ID", ID_Locator);
		    					def.save(get_TrxName());
		    					id_Fore = def.get_ID();
		    					seqNo= 10;
		    				}		    				
		    				//se crea registro		    				
		    				MPPForecastDefinitionLine line = new MPPForecastDefinitionLine(getCtx(), 0, get_TrxName());
		    				line.setPP_ForecastDefinition_ID(id_Fore);
		    				line.setM_Product_Group_ID(ID_PGroup);
		    				line.set_CustomColumn("M_Locator_ID", ID_Locator);
		    				line.set_CustomColumn("Date1", DateUtils.convertDateddMMyyyy(cabecera[idCol]));
		    				line.set_CustomColumn("Qty", (BigDecimal) decimalFormat.parse(datosF.get(x)[idCol]));
		    				line.setSeqNo(seqNo);
		    				line.save(get_TrxName());
		    				idCol++;
		    				seqNo = seqNo+1;
			    			if(idCol == 12)
			    				flag = -1;
		    			}
		    		}
		    	}
		    }
	    }
		catch (Exception e) 
		{
			log.config("ERROR: "+e);
		}*/
		commitEx();
		//4-Se importan minimos por familia		
		rutaArchivo = OFBForward.CencosudRutaMin();
		String pFileM = rutaArchivo;
		ArrayList<String[]> datosM=new ArrayList<String[]>();
		cantLines = 0;
	    try 
	    {
	    	FileInputStream fis =new FileInputStream(pFileM);
		    InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
		    BufferedReader br = new BufferedReader(isr);
		    String linea=br.readLine();
		    //se recorre archivo
		    while(linea!=null)
		    {
		        datosM.add(linea.split(";"));
			    linea=br.readLine();
			    cantLines = cantLines + 1;
		    }
		    br.close();
		    isr.close();
		    fis.close();
		    //log.config(datosM.toString());
		    //se leen campos del archivo
		    for (int x = 1; x < cantLines; x++)
		    {	
		    	//se busca ubicacion, producto, cantidad y fecha
		    	int ID_Locator = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Locator_ID) FROM M_Locator WHERE IsActive = 'Y' AND value like '"+datosM.get(x)[0]+"'");
		    	int ID_PGroup = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Product_Group_ID) FROM M_Product_Group WHERE IsActive = 'Y' AND upper(value) like '"+datosM.get(x)[1].toUpperCase()+"'");		    	
		    	BigDecimal qty = (BigDecimal) decimalFormat.parse(datosM.get(x)[3].trim());
		    	if(ID_Locator > 0 && ID_PGroup > 0 && qty != null)
		    	{
		    		//se borran registros antiguos
		    		DB.executeUpdate("DELETE FROM M_Product_GroupReplenish " +
		    				"WHERE IsActive = 'Y' AND M_Locator_ID = "+ID_Locator+" AND M_Product_Group_ID="+ID_PGroup, get_TrxName());
		    		X_M_Product_GroupReplenish rep = new X_M_Product_GroupReplenish(getCtx(), 0, get_TrxName());
		    		rep.setAD_Org_ID(0);
		    		rep.setM_Locator_ID(ID_Locator);
		    		rep.setM_Product_Group_ID(ID_PGroup);
		    		rep.setLevel_Min(qty);
		    		rep.save(get_TrxName());
		    	}
		    }
	    }
		catch (Exception e) 
		{
			log.config("ERROR: "+e);
		}		
		//5- Se importan calendarios (tetris)
		rutaArchivo = OFBForward.CencosudRutaTetrisV();
		String pFileTV = rutaArchivo;
		ArrayList<String[]> datosTV=new ArrayList<String[]>();
		cantLines = 0;
	    //try 
	    //{
	    	FileInputStream fis =new FileInputStream(pFileTV);
		    InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
		    BufferedReader br = new BufferedReader(isr);
		    String linea=br.readLine();
		    //se recorre archivo
		    while(linea!=null)
		    {
		    	if(linea.substring(linea.length()-1).compareTo(";") == 0)
		    		linea = linea+"-";
		    	datosTV.add(linea.split(";"));
			    linea=br.readLine();
			    cantLines = cantLines + 1;
		    }
		    br.close();
		    isr.close();
		    fis.close();
		    //log.config(datosTV.toString());
		    //se leen campos del archivo
		    DB.executeUpdate("DELETE FROM M_Inventory WHERE IsActive = 'Y' AND  docstatus IN ('DR')", get_TrxName());
		    for (int x = 2; x < cantLines; x++)
		    {	
		    	//se busca ubicacion, producto, cantidad y fecha
		    	int ID_Locator = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Locator_ID) FROM M_Locator WHERE IsActive = 'Y' AND value like '"+datosTV.get(x)[0]+"'");
		    	if(ID_Locator > 0)
		    	{
		    		MLocator loc = new MLocator(getCtx(), ID_Locator, get_TrxName());
		    		//ciclo que recorre los dias de llegada
		    		for (int y = 8; y <= 15; y++)
				    {
		    			if(datosTV.get(x)[y] != null && datosTV.get(x)[y].trim().length() > 0
		    					&& datosTV.get(x)[y].trim().compareTo("-") != 0)
		    			{
		    				MInventory inv = new MInventory(getCtx(), 0, get_TrxName());
		    				inv.set_CustomColumn("M_Locator_ID", ID_Locator);
		    				inv.set_CustomColumn("ViaAbast", devuelveVA(datosTV.get(x)[1]));
		    				inv.set_CustomColumn("DayOrder", devuelveDia(datosTV.get(x)[y]));
		    				inv.set_CustomColumn("DayReception", devuelveDia(datosTV.get(1)[y]));
		    				int dayOrder = Integer.parseInt(devuelveDia(datosTV.get(x)[y]));
		    				int dayReception = Integer.parseInt(devuelveDia(datosTV.get(1)[y]));
		    				int dif = 0;
		    				if(dayOrder < dayReception)
		    					dif = dayReception - dayOrder;
		    				else if (dayReception < dayOrder)
		    				{
		    					dif = 8 - dayOrder;
		    					dif = dif +(dayReception-1);
		    				}
		    				inv.set_CustomColumn("Days",dif);
		    				//campos obligatorios de tabla M_Inventory
		    				inv.setM_Warehouse_ID(loc.getM_Warehouse_ID());
		    				inv.saveEx(get_TrxName());
		    			}
				    }
		    		//parte 2 de carga. se actualiza campo secuencia
				    String sqlSec = "select M_Inventory_ID,viaAbast from M_Inventory where m_locator_id = "+ID_Locator+" order by viaAbast, dayorder";
				    PreparedStatement pstmtSec = null;
				    pstmtSec = DB.prepareStatement(sqlSec, get_TrxName());
					ResultSet rsSec = null;
					rsSec = pstmtSec.executeQuery();
					int ind = 1;
					String viaAbastValid = "";
					while(rsSec.next())
					{
						if(viaAbastValid.compareTo(rsSec.getString("viaAbast"))!= 0)
							ind = 1;
						DB.executeUpdate("UPDATE M_Inventory SET sequence = "+ind+" WHERE M_Inventory_ID = "+rsSec.getInt("M_Inventory_ID"), get_TrxName());
						ind++;
						viaAbastValid = rsSec.getString("viaAbast");
					}
					rsSec.close();pstmtSec.close();
					rsSec = null;pstmtSec=null;
					
		    	}
		    }
				
	    //}
		//catch (Exception e) 
		//{
		//	log.config("ERROR: "+e);
		//}		
		commitEx();
		
		
		//PARTE 2 GENERACION DE OC CON CALCULOS Q
		//SE GENERA SOLO 1 ORDEN 
		MProductCategory pCat = new MProductCategory(getCtx(), p_ID_PCategory, get_TrxName());
		order = new MOrder (getCtx(), 0, get_TrxName());
		order.setClientOrg (Env.getAD_Client_ID(getCtx()),Env.getAD_Org_ID(getCtx()));
		//order.setC_DocTypeTarget_ID(MDocType.getDocType("POO", Env.getAD_Org_ID(getCtx())));
		order.setC_DocTypeTarget_ID(2000140);				
		order.setIsSOTrx(false);
		order.setC_BPartner_ID(2001562);
		order.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		order.setBill_BPartner_ID(2001562);
		order.setDescription("Pedido Generado automaticamente. "+pCat.getName());
		order.setM_PriceList_ID(2000005);							
		order.setM_Warehouse_ID(2000002);
		order.setSalesRep_ID(2000012);
		order.setAD_OrgTrx_ID(Env.getAD_Org_ID(getCtx()));
		order.setDateOrdered(p_dateTrx);
		order.setDatePromised(p_dateTrx);
		order.setDateAcct(p_dateInv);
		order.setC_ConversionType_ID(114);
		//order.set_CustomColumn("M_Locator_ID", loc.get_ID());
		order.saveEx(get_TrxName());
		int cantLine = 10;
		
		//ininoles se agrega filtro por warehouse
		//ciclo de familias 
		/*String sql = "SELECT M_Locator_ID, pg.M_Product_Group_ID" +
				" FROM M_Product_Group pg" +
				" INNER JOIN M_Product_GroupReplenish mi ON (pg.M_Product_Group_ID = mi.M_Product_Group_ID )" +
				" WHERE M_Product_Category_ID = "+p_ID_PCategory+
				" GROUP BY M_Locator_ID, pg.M_Product_Group_ID";*/
		String sql = "SELECT mi.M_Locator_ID, pg.M_Product_Group_ID" +
		" FROM M_Product_Group pg" +
		" INNER JOIN M_Product_GroupReplenish mi ON (pg.M_Product_Group_ID = mi.M_Product_Group_ID ) "+
		" INNER JOIN M_Locator ml ON (mi.M_Locator_ID = ml.M_Locator_ID) "+
		" WHERE M_Product_Category_ID = "+p_ID_PCategory;
		if(p_ID_Warehouse > 0)
			sql = sql+ " AND ml.M_Warehouse_ID = "+p_ID_Warehouse;
		sql = sql+" GROUP BY mi.M_Locator_ID, pg.M_Product_Group_ID";
	
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement(sql, get_TrxName());
		ResultSet rs = null;
		rs = pstmt.executeQuery();
		while(rs.next())
		{	
			MLocator loc = new MLocator(getCtx(), rs.getInt("M_Locator_ID"), get_TrxName());
			if(p_dateTrx != null)
			{
				int dayWeek =  p_dateTrx.getDay()+1;
				String dayW = "0"+dayWeek;
				//se obtiene via de abatecimiento
				String viaAbast = DB.getSQLValueString(get_TrxName(), "SELECT MAX(ViaAbast) FROM M_Product_GroupActive " +
						" WHERE M_Locator_ID = "+loc.get_ID()+" AND M_Product_Group_ID = ?", rs.getInt("M_Product_Group_ID"));
				//end
				int exist = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM M_Inventory " +
						" WHERE IsActive = 'Y' AND M_Locator_ID = "+loc.get_ID()+
						" AND DayOrder = '"+dayW+"' AND ViaAbast = '"+viaAbast+"'");
				if(exist > 0)//proceso sigue
				{	
					if(rs.getInt("M_Product_Group_ID") == 2000048 && loc.get_ID() == 2000042)
					{
						log.config("log debug");
					}
					//se le suman dias a la orden
					int cantD = DB.getSQLValue(get_TrxName(), "SELECT MAX(Days) FROM M_Inventory " +
							" WHERE IsActive = 'Y' AND M_Locator_ID = "+loc.get_ID()+
							" AND DayOrder = '"+dayW+"' AND ViaAbast = '"+viaAbast+"'");
					Timestamp newDate = p_dateTrx;
					if(cantD > 0)
					{
						Calendar cal = Calendar.getInstance();
						cal.setTimeInMillis(p_dateTrx.getTime());
						cal.add(Calendar.DATE,cantD);
						newDate = new Timestamp(cal.getTimeInMillis());	
					}
					/***OBS 
					 * Variable p_dateTrx es fecha inicio periodo actual
					 * Variable newDate es fecha fin periodo actual
					 */					
					//SE CALCULAN VALORE Q					
					BigDecimal kgCaja = DB.getSQLValueBD(get_TrxName(), "SELECT Qty FROM M_Product_Group WHERE M_Product_Group_ID = "+rs.getInt("M_Product_Group_ID"));					
					if(kgCaja == null)
						kgCaja = Env.ONE;					
					BigDecimal qtyInv = DB.getSQLValueBD(get_TrxName(), "SELECT QtyMultiplier FROM M_Product_Group WHERE M_Product_Group_ID = "+rs.getInt("M_Product_Group_ID"));
					if(qtyInv == null)
						qtyInv = Env.ONE;
					//Q5 STOCK MINIMO
					BigDecimal q5 = DB.getSQLValueBD(get_TrxName(), "SELECT MAX(Level_Min) FROM M_Product_GroupReplenish WHERE IsActive='Y' " +
							" AND M_Locator_ID="+loc.get_ID()+" AND M_Product_Group_ID="+rs.getInt("M_Product_Group_ID"));
					//se sobreescribe si es ciclo 3
					if(q5 == null)
						q5 = Env.ZERO;
					//Q4 Pronostico
					BigDecimal q4 = DB.getSQLValueBD(get_TrxName(), "SELECT SUM(Qty) FROM PP_ForecastLineCENCO WHERE IsActive='Y' " +
							" AND M_Locator_ID="+loc.get_ID()+" AND M_Product_Group_ID="+rs.getInt("M_Product_Group_ID")+" AND Date1 Between ? AND ?",p_dateTrx,newDate);
					if(q4 == null)
						q4 = Env.ZERO;
					//Q3 EN TRANSITO
					BigDecimal q3 = DB.getSQLValueBD(get_TrxName(), "SELECT SUM(MovementQty)" +
							" FROM I_OrderCencosud o INNER JOIN M_Product mp ON (o.M_Product_ID = mp.M_Product_ID)" +
							" WHERE o.IsActive = 'Y' AND o.M_Locator_ID="+loc.get_ID()+" AND mp.M_Product_Group_ID="+rs.getInt("M_Product_Group_ID")+
							" AND o.DateTrx Between ? AND ?",p_dateTrx,newDate);
					if(q3 == null)
						q3 = Env.ZERO;
					//Q2 STOCK ES EN CAJA SE DEBE MULTIPLICAR POR KILOS o factor
					BigDecimal q2 = DB.getSQLValueBD(get_TrxName(), "SELECT SUM(QtyOnHand) FROM I_InventoryCencosud WHERE IsActive='Y' " +
							" AND M_Locator_ID="+loc.get_ID()+" AND M_Product_Group_ID="+rs.getInt("M_Product_Group_ID"));
					if(q2 == null)
						q2 = Env.ZERO;
					q2 = q2.multiply(qtyInv);
					//ininoles se calcula nuevo stock en base a nuevas fechas
					//se suma q3 temporal, es decir entre la fecha de inventaro y la fecha de proceso
					Timestamp p_dateTrxTemp = DateUtils.addDays(p_dateTrx, -1);
					BigDecimal q3Temp = DB.getSQLValueBD(get_TrxName(), "SELECT SUM(MovementQty)" +
							" FROM I_OrderCencosud o INNER JOIN M_Product mp ON (o.M_Product_ID = mp.M_Product_ID)" +
							" WHERE o.IsActive = 'Y' AND o.M_Locator_ID="+loc.get_ID()+" AND mp.M_Product_Group_ID="+rs.getInt("M_Product_Group_ID")+
							" AND o.DateTrx Between ? AND ?",p_dateInv,p_dateTrxTemp);
					if(q3Temp == null)
						q3Temp = Env.ZERO;
					q2 = q2.add(q3Temp);
					//se resta el foreast temporal
					BigDecimal q4Temp = DB.getSQLValueBD(get_TrxName(), "SELECT SUM(Qty) FROM PP_ForecastLineCENCO WHERE IsActive='Y' " +
							" AND M_Locator_ID="+loc.get_ID()+" AND M_Product_Group_ID="+rs.getInt("M_Product_Group_ID")+" AND Date1 Between ? AND ?",p_dateInv,p_dateTrxTemp);
					if(q4Temp == null)
						q4Temp = Env.ZERO;
					q2 = q2.subtract(q4Temp);
					//end calculo nuevo stock					
					//Q1 CALCULO MAS ENREDADO QUE PELEA DE PULPOS
					//se saca dia siguiente a final del periodo 
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(newDate.getTime());
					cal.add(Calendar.DATE,1);
					Timestamp startDateP2 = new Timestamp(cal.getTimeInMillis());
					//se define dia final del periodo siguiente
					int seq = DB.getSQLValue(get_TrxName(), "SELECT MAX(Sequence) FROM M_Inventory " +
							" WHERE IsActive = 'Y' AND M_Locator_ID="+loc.get_ID()+" AND DayOrder = '"+dayW+"'"+
							" AND ViaAbast = '"+viaAbast+"'");
					int stop = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM M_Inventory " +
							" WHERE IsActive = 'Y' AND M_Locator_ID="+loc.get_ID()+" AND ViaAbast = '"+viaAbast+"'");
					if(seq == stop)
						seq = 0;
					seq++;
					//Se trae dia final de la siguente secuencia logica
					/*String dayFinP2Str = DB.getSQLValueString(get_TrxName(), "SELECT MAX(DayReception) FROM M_Inventory " +
							" WHERE IsActive = 'Y' AND M_Product_Group_ID="+rs.getInt("M_Product_Group_ID")+" AND M_Locator_ID="+loc.get_ID()+
							" AND Sequence = "+seq);*/
					String dayFinP2Str = DB.getSQLValueString(get_TrxName(), "SELECT MAX(DayReception) FROM M_Inventory " +
							" WHERE IsActive = 'Y' AND M_Locator_ID="+loc.get_ID()+" AND Sequence = "+seq+
							" AND ViaAbast = '"+viaAbast+"'");
					int dayFinP2 = 0;
					if(dayFinP2Str != null)
					{
						dayFinP2 = Integer.parseInt(dayFinP2Str);
						dayWeek = startDateP2.getDay()+1;
						cal = Calendar.getInstance();
						cal.setTimeInMillis(startDateP2.getTime());
						while(dayWeek != dayFinP2)
						{
							cal.add(Calendar.DATE,1);					
							dayWeek++;
							if(dayWeek == 9)
								dayWeek = 2;					
						}
						Timestamp endDateP2 = new Timestamp(cal.getTimeInMillis());
						BigDecimal q1 = DB.getSQLValueBD(get_TrxName(), "SELECT SUM(Qty) FROM PP_ForecastLineCENCO WHERE IsActive='Y' " +
								" AND M_Locator_ID="+loc.get_ID()+" AND M_Product_Group_ID="+rs.getInt("M_Product_Group_ID")+" AND Date1 Between ? AND ?",startDateP2,endDateP2);
						if(q1 == null)
							q1 = Env.ZERO;
						BigDecimal aux = q2.add(q3).subtract(q4);
						if(aux == null || aux.compareTo(Env.ZERO) <= 0)
							aux = Env.ZERO;
						BigDecimal qtyTotal = q1;
						qtyTotal = qtyTotal.subtract(aux);
						qtyTotal = qtyTotal.add(q5);
						//se genera linea de OC
						//calculo debe ser positivo y tener forecast
						//if(qtyTotal.compareTo(Env.ZERO) > 0 && q4.compareTo(Env.ZERO)!=0 && 
						//		q2.compareTo(Env.ZERO)!=0)
						//if(qtyTotal.compareTo(Env.ZERO) > 0)
						//{
						if(qtyTotal.compareTo(Env.ZERO) <=0)
							qtyTotal = Env.ZERO;
						MOrderLine line = new MOrderLine (order);
						line.setLine(cantLine);
						line.setM_Product_ID(2004195);
						line.setQty(qtyTotal);
						line.setPrice();
						line.setTax();
						line.setLineNetAmt();						
						line.setC_Tax_ID(2000002);//siempre es IVA
						line.setDescription("Cantidades: Q1="+q1+" / Q2="+q2+" / Q3="+q3+" / Q4="+q4+" / Q5="+q5+" / Pedido="+qtyTotal.divide(kgCaja,0, RoundingMode.HALF_EVEN)+" cajas."+" valores temporales: q3Temp="+q3Temp+" q4Temp="+q4Temp);
						line.set_CustomColumn("M_Locator_ID", loc.get_ID());
						line.set_CustomColumn("Q1",q1);
						line.set_CustomColumn("Q2",q2);//stock
						line.set_CustomColumn("Q2Box",q2.divide(qtyInv,0, RoundingMode.HALF_EVEN));
						line.set_CustomColumn("Q3",q3);//transito
						line.set_CustomColumn("Q4",q4);//forecast
						line.set_CustomColumn("Q5",q5);//stock minimo
						line.set_CustomColumn("qty",qtyTotal.divide(kgCaja,0, RoundingMode.HALF_EVEN));
						line.setDatePromised(newDate);
						line.set_CustomColumn("M_Product_Group_ID", rs.getInt("M_Product_Group_ID"));
						line.saveEx(get_TrxName());		
						cantLine = cantLine+10;
						//}
					}
				}
			}
		}
		rs.close(); rs = null;
		pstmt.close(); pstmt = null;
		return "ORDEN GENERADA "+order.getDocumentNo();		
	}	//	doIt

	public String devuelveDia(String valor)
	{
		if(valor == null)
			return "";
		if(valor.compareToIgnoreCase("LU") == 0)
			return "02";
		else if(valor.compareToIgnoreCase("MA") == 0)
			return "03";
		else if(valor.compareToIgnoreCase("W") == 0
				|| valor.compareToIgnoreCase("MI") == 0)
			return "04";
		else if(valor.compareToIgnoreCase("JU") == 0)
			return "05";
		else if(valor.compareToIgnoreCase("VI") == 0
				|| valor.compareToIgnoreCase("SAI") == 0)
			return "06";
		else if(valor.compareToIgnoreCase("SA") == 0)
			return "07";
		else if(valor.compareToIgnoreCase("DO") == 0)
			return "08";
		return "";
	}
	private String devuelveVA(String via) 
	{
		String va= "1";
		if(via != null)
		{
			String valor = DB.getSQLValueString(get_TrxName(), "SELECT value FROM AD_Ref_List " +
					" WHERE AD_Reference_ID=2000050 AND name like '"+via+"'");
			if(valor != null)
				va=valor;
		}		
		return va;
	}
}	//	CopyOrder

