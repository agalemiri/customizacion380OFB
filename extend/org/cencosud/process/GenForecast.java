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
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.compiere.model.*;
import org.compiere.process.SvrProcess;
import org.compiere.util.*;
import org.ofb.model.OFBForward;
import org.ofb.utils.DateUtils;

/**
 *	
 *	
 *  @author italo niñoles ininoles
 *  @version $Id: ProcessPaymentRequest.java,v 1.2 2011/06/12 00:51:01  $
 */
public class GenForecast extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	//private Timestamp		p_dateTrx;
	//private Timestamp		p_dateInv;
	//private int 			p_ID_Locator;
	//private int 			p_ID_PCategory;
	
	protected void prepare()
	{
		
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		//primero se importan los documentos
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		String rutaArchivo = OFBForward.CencosudRutaStock();
		
		//3-Se importan lineas de forecast		
		rutaArchivo = OFBForward.CencosudRutaForecast();
		String pFileF = rutaArchivo;
		//ArrayList<String[]> datosF=new ArrayList<String[]>();
		String[] datosFStr = new String[24];
		//int cantLines = 0;
	    //try 
	    //{
	    	FileInputStream fis =new FileInputStream(pFileF);
		    InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
		    BufferedReader br = new BufferedReader(isr);
		    String linea=br.readLine();		   
		    //String[] cabecera = new String[24];
		    Timestamp[] fechas = new Timestamp[24];
		    int ind = 1;
		    DB.executeUpdate("DELETE FROM PP_ForecastLineCENCO",get_TrxName());
		    int cantLine = 0;
		    int cantLineC = 0; 
		    //se recorre archivo
		    while(linea!=null)
		    {	
		    	//log.config(linea);
		    	datosFStr = linea.split(";"); 
		        //datosF.add(linea.split(";"));
		        if(ind == 1)
		    	{
		    		for (int a = 0; a < 24; a++)
				    {
	    				fechas[a] = DateUtils.convertDateddMMyyyy(datosFStr[a]);
				    }
		    		ind = 0;
		    	}
		        else
		        {
		        	int ID_Locator = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Locator_ID) FROM M_Locator WHERE IsActive = 'Y' AND value like '"+datosFStr[0]+"'");
			    	int ID_PGroup = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Product_Group_ID) FROM M_Product_Group WHERE IsActive = 'Y' AND value like '"+datosFStr[1]+"'");
			    	
			    	for (int y = 3; y < 24; y++)
					 {
				    	//se crea registro		    		
						X_PP_ForecastLineCENCO line = new X_PP_ForecastLineCENCO(getCtx(), 0, get_TrxName());
						line.setM_Product_Group_ID(ID_PGroup);
						line.setM_Locator_ID(ID_Locator);
						line.setDate1(fechas[y]);
						line.setQty((BigDecimal) decimalFormat.parse(datosFStr[y]));
						line.save(get_TrxName());
						cantLineC++;
					 }
		        }
			    linea=br.readLine();
			    //datosF.clear();
			    cantLine++;
			    if(cantLine == 500 || cantLine == 1000 || cantLine == 1500 || cantLine == 2000 
			    	|| cantLine == 2500 || cantLine == 3000	|| cantLine == 3500 || cantLine == 4000 
			    	|| cantLine == 4500 || cantLine == 5000 || cantLine == 5500 || cantLine == 6000)
			    	commitEx();
		    }
		    br.close();
		    isr.close();
		    fis.close();
		    //log.config(datosF.toString());
	    //}
		//catch (Exception e) 
		//{
		//	log.config("ERROR: "+e);
		//}
		//commitEx();
		return "se han agregado "+cantLineC+" lineas";
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
	
}	//	CopyOrder
