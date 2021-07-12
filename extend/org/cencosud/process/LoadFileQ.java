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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.logging.*;

import org.compiere.model.*;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.*;
/**
 *	
 *	
 *  @author italo niñoles ininoles
 *  @version $Id: GenOCCencosud.java,v 1.2 2011/06/12 00:51:01  $
 *  Este metodo realiza un recalculo del stock en base al pronostico 
 *  y el stock cargado en cierta fecha.
 *  
 */
public class LoadFileQ extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	private int 			p_ID_Order;
	private String 			p_PathFile;
	
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("archivo"))
				p_PathFile = para[i].getParameterAsString();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
		p_ID_Order = getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		//primero se importan los documentos
		MOrder order = new MOrder(getCtx(), p_ID_Order, get_TrxName());
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		String rutaArchivo = p_PathFile;
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
		    for (int x = 0; x < cantLines; x++)
		    {	
		    	//se busca ubicacion, familia y cantidad
		    	int ID_Locator = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Locator_ID) FROM M_Locator WHERE IsActive = 'Y' AND value like '"+datos.get(x)[2]+"'");
		    	int ID_PGroup = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Product_Group_ID) FROM M_Product_Group WHERE IsActive = 'Y' AND value like '"+datos.get(x)[0]+"'");		    	
		    	//
		    	//Se busca si existe linea
		    	if(ID_Locator > 0 && ID_PGroup>0)
		    	{
		    		BigDecimal qty = (BigDecimal) decimalFormat.parse(datos.get(x)[13].trim());
			    	int ID_OLine = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_OrderLine_ID) FROM C_OrderLine WHERE IsActive = 'Y'" +
			    			" AND C_Order_ID="+p_ID_Order+" AND M_Locator_ID="+ID_Locator+" AND M_Product_Group_ID="+ID_PGroup);
			    	BigDecimal qtyBox = DB.getSQLValueBD(get_TrxName(), "SELECT Qty FROM M_Product_Group WHERE M_Product_Group_ID="+ID_PGroup);
			    	if(qtyBox == null)
			    		qtyBox = Env.ONE;
			    	qtyBox = qty.divide(qtyBox,0,RoundingMode.HALF_EVEN);
			    	if(ID_OLine > 0)
			    	{	
			    		//ininoles se carga nuevo campo
			    		//DB.executeUpdate("UPDATE C_OrderLine SET qpc="+qty+" WHERE C_OrderLine_ID = "+ID_OLine, get_TrxName());
			    		DB.executeUpdate("UPDATE C_OrderLine SET qpFinal="+qty+" WHERE C_OrderLine_ID = "+ID_OLine, get_TrxName());
			    		DB.executeUpdate("UPDATE C_OrderLine SET qpFinalBox="+qtyBox+" WHERE C_OrderLine_ID = "+ID_OLine, get_TrxName());
			    	}
			    	else
			    	{
			    		MOrderLine line = new MOrderLine (order);
			    		int NoLine = DB.getSQLValue(get_TrxName(), "SELECT MAX(Line) FROM C_OrderLine WHERE C_Order_ID="+p_ID_Order); 
						line.setLine(NoLine+10);
						line.setM_Product_ID(2004195);
						line.setQty(qty);
						line.setPrice();
						line.setTax();
						line.setLineNetAmt();						
						line.setC_Tax_ID(2000002);//siempre es IVA
						line.setDescription("Linea Creada por importacion de q");
						line.set_CustomColumn("M_Locator_ID",ID_Locator);
						line.set_CustomColumn("qpc",qty);
						line.set_CustomColumn("M_Product_Group_ID",ID_PGroup);
						line.saveEx(get_TrxName());		
			    	}
		    	}
	    	}
		    datos = null;
	    }
		catch (Exception e) 
		{
			log.config("ERROR: "+e);
		}
		return "PROCESADO";		
	}	//	doIt	
}	//	

