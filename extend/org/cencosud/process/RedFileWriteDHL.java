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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.compiere.model.X_HR_WorkHours;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
/**
 *	
 *	
 *  @author Italo Niñoles Ininoles
 *  @version $Id: RedFileWriteDHL.java,v 1.2 05/09/2014 $
 */
public class RedFileWriteDHL extends SvrProcess
{
	/** Properties						*/
	private String 			rutaArchivo;
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
			else if (name.equals("rutaArchivo"))
				rutaArchivo = para[i].getParameterAsString();
		}
	}	//	prepare

	
	/**
	 * 	Create Shipment
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{	
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		String pFile = rutaArchivo;
		ArrayList<String[]> datos=new ArrayList<String[]>();
		int cantLines = 0;
	    try 
	    {
	    	FileInputStream fis =new FileInputStream(pFile);
		    InputStreamReader isr = new InputStreamReader(fis, "UTF8");
		    BufferedReader br = new BufferedReader(isr);
		    String linea=br.readLine();		    
		    while(linea!=null)
		    {
		        datos.add(linea.split(";"));
			    linea=br.readLine();
			    cantLines = cantLines + 1;
		    }
		    br.close();
		    isr.close();
		    fis.close();
		    log.config(datos.toString());
		    //llenado de array de fechas
		    String[] cabecera = new String[30];
		    for (int a = 0; a < 30; a++)
		    {
		    	cabecera[a] = (String)datos.get(0)[a];
		    }
		    //fin llenado de cabeceras
		    //lectura de lineas de archivo sin cabecera
		    for (int x = 1; x <= cantLines; x++)
		    {	
		    	try
	    		{
		    		//se leen lineas de archivo
		    		int idCol = 6;
		    		int flag = 1;
		    		String rut = "";
		    		rut = datos.get(x)[0];//se saca rut 
		    		if(rut != null)
		    		{
		    			rut = rut.replace("-","");
		    			rut = rut.substring(0, rut.length()-1);
		    		}
		    		int ID_BPartner = DB.getSQLValue(get_TrxName(), "SELECT C_Bpartner_ID FROM C_Bpartner " +
		    				" WHERE IsActive = 'Y' AND value like '"+rut+"' ");
		    		while (flag > 0)
		    		{	
		    			//se pregunta si la cabecera es fecha		    			
		    			if(isDate(cabecera[idCol]))
		    			{
		    				//se crea registro
		    				X_HR_WorkHours wHour = new X_HR_WorkHours(getCtx(), 0, get_TrxName());
		    				wHour.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
			    			if(ID_BPartner > 0)
			    				wHour.setC_BPartner_ID(ID_BPartner);
			    			else
			    				wHour.setErrorMsg("ERROR: Socio de Negocio Inexistente:"+datos.get(x)[0]+" / "+datos.get(x)[1]);
			    			wHour.setDateTrx(convertDate(cabecera[idCol]));
			    			//si es numero se guarda en cantidad 
			    			if(isNumber(datos.get(x)[idCol]))
			    				wHour.setQty((BigDecimal) decimalFormat.parse(datos.get(x)[idCol]));
		    				else //sino se guarda cadena en description
		    					wHour.setDescription(datos.get(x)[idCol]);
			    			wHour.save(get_TrxName());
			    			idCol++;
			    			if(idCol == 30)
			    				flag = -1;
		    			}		
		    		}
		    		
	    		}catch (Exception e) 
	    		{
	    			log.config("ERROR: "+e);
				}
		    }  	
	    }
		catch (Exception e) 
		{
			log.config("ERROR: "+e);
		}
		return "Se han cargado "+(cantLines-1)+" lineas de archivo";
	}	//	doIt
	
	public String DeleteEjecucion(String files)
	{
		Runtime aplicacion = Runtime.getRuntime(); 
	    try
	    {
	    	aplicacion.exec("cmd.exe /K del "+files); 
	    }
	    catch(Exception e)
	    {
	    	return e.toString();
	    }	    
	    return "Proceso OK";
	}
	public String DeleteEjecucionCompleta(String rutaBatDelete)
	{
		Runtime aplicacion = Runtime.getRuntime(); 
	    try
	    {
	    	aplicacion.exec("cmd.exe /K "+rutaBatDelete); 
	    }
	    catch(Exception e)
	    {
	    	return e.toString();
	    }	    
	    return "Proceso OK";
	}
	
	private boolean  isDate(String dateStr) 
	{
		try 
		{
		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		    Date parsedDate = dateFormat.parse(dateStr);
		    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		    return true;
		} catch(Exception e) 
		{ 
		    return false;
		}
	}
	private Timestamp convertDate(String dateStr) 
	{
		Timestamp timestamp = null;
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		    Date parsedDate = dateFormat.parse(dateStr);
		    timestamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch(Exception e) {
			log.config("ERRRO:"+e);
		} 
		return timestamp;
	}
	private static boolean isNumber(String numberStr) 
	{
		if(numberStr == null || numberStr.isEmpty())
			return false;
		numberStr = numberStr.trim();
		numberStr = numberStr.replace(",","");
		numberStr = numberStr.replace(".","");
		int i = 0;
		if(numberStr.charAt(0) == '-')
		{
			if(numberStr.length() > 1)
				i++;
			else
				return false;				
		}
		for (;i < numberStr.length();i++)
		{
			if(!Character.isDigit(numberStr.charAt(i)))
				return false;
		}
		return true;
	}
}	//	InvoiceCreateInOut
