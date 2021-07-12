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
package org.prototipos.process;

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

import org.compiere.model.X_I_ACHSFile1;
import org.compiere.model.X_I_ACHSFile3;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
/**
 *	
 *	
 *  @author Italo Niñoles Ininoles
 *  @version $Id: ImportFileACHS.java,v 1.2 14/08/2020 $
 */
public class ImportFileACHS extends SvrProcess
{
	/** Properties						*/
	private String 			rutaArchivo;
	private String 			rutaArchivo3;
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
			else if (name.equals("rutaArchivo3"))
				rutaArchivo3 = para[i].getParameterAsString();
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
		//borrado de tablas 
		DB.executeUpdate("DELETE FROM I_ACHSFile1 WHERE I_ACHSFile1_ID NOT IN(SELECT I_ACHSFile1_ID FROM C_OrderLine WHERE I_ACHSFile1_ID IS NOT NULL)", get_TrxName());
		DB.executeUpdate("DELETE FROM I_ACHSFile3", get_TrxName());
		
		//lectura archivo 1
		String pFile = rutaArchivo;
		String pFile3 = rutaArchivo3;
		ArrayList<String[]> datos=new ArrayList<String[]>();
		ArrayList<String[]> datos3=new ArrayList<String[]>();
		int cantLines = 0;
		int cantLines3 = 0;
		int ID_Bpartner = 0;
		int ID_product = 0;
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
			    if(cantLines > 5)
			    {
			    	//SE CRE REGISTRO EN TABLA TEMPORAL
			    	X_I_ACHSFile1 f1 = new X_I_ACHSFile1(getCtx(), 0, get_TrxName());
			    	f1.setRut_Empresa(datos.get(0)[3].toString());
			    	//seteo de c_bpartner_id
			    	ID_Bpartner = DB.getSQLValue(get_TrxName(), "SELECT C_BPartner_ID FROM C_BPartner "
			    			+ " WHERE IsActive='Y' AND value like '"+datos.get(0)[3].toString().substring(0,8)+"'");
			    	if(ID_Bpartner > 0)
			    		f1.setC_BPartner_ID(ID_Bpartner);
			    	f1.setEpisodio(datos.get(0)[5].toString());
			    	f1.setRut_Paciente(datos.get(0)[20].toString());
			    	f1.setBP_Empresa(datos.get(0)[21].toString());
			    	f1.setPrestacion(datos.get(0)[27].toString());
			    	//seteo de producto
			    	ID_product = DB.getSQLValue(get_TrxName(), "SELECT M_Product_ID FROM M_Product "
			    			+ " WHERE IsActive='Y' AND Value like '"+datos.get(0)[27].toString()+"'");
			    	if(ID_product > 0)
			    		f1.setM_Product_ID(ID_product);
			    	f1.setPrestacionGlosa(datos.get(0)[28].toString());
			    	if(isNumber(datos.get(0)[6].toString().replace("$", "")))
			    	{
			    		f1.setLineNetAmt((BigDecimal) decimalFormat.parse(datos.get(0)[6].replace("$", "").trim()));
			    	}
			    	f1.setName(datos.get(0)[15].toString()+" "+datos.get(0)[16].toString()
			    			+" "+datos.get(0)[17].toString());
			    	if(isDate(datos.get(0)[4].toString()))
			    		f1.setFechaAtencion(convertDate(datos.get(0)[4].toString()));
			    	f1.saveEx(get_TrxName());
			    }
			    datos.clear();
			    log.config("linea: "+cantLines+".");
		   
		    }
		    br.close();
		    isr.close();
		    fis.close();
		    log.config(datos.toString());
		   
	    }
		catch (Exception e) 
		{
			log.config("ERROR: "+e);
		}
	    
	    //se carga archivo numero 3
	    try 
	    {
	    	FileInputStream fis2 =new FileInputStream(pFile3);
		    InputStreamReader isr2 = new InputStreamReader(fis2, "UTF8");
		    BufferedReader br2 = new BufferedReader(isr2);
		    String linea=br2.readLine();		    
		    while(linea!=null)
		    {
		        datos3.add(linea.split(";"));
			    linea=br2.readLine();
			    cantLines3 = cantLines3 + 1;
			    if(cantLines3 > 1)
			    {
			    	//SE CRE REGISTRO EN TABLA TEMPORAL
			    	X_I_ACHSFile3 f3 = new X_I_ACHSFile3(getCtx(), 0, get_TrxName());
			    	f3.setRut_Empresa(datos3.get(0)[2].toString());
			    	f3.setNombreEmpresa(datos3.get(0)[4].toString());
			    	f3.setCentroDeCosto(datos3.get(0)[8].toString());
			    	if(isDate(datos3.get(0)[9].toString()))
			    		f3.setFechaAtencion(convertDate(datos3.get(0)[9].toString()));
			    	f3.setStatus(datos3.get(0)[11].toString());
			    	f3.setRut_Paciente(datos3.get(0)[13].toString());
			    	f3.setNombre_Paciente(datos3.get(0)[14].toString());
			    	f3.setNombre_Solicitante(datos3.get(0)[15].toString());
			    	f3.setRut_Solicitante(datos3.get(0)[16].toString());
			    	f3.setMail_Solicitante(datos3.get(0)[19].toString());
			    	f3.setSucursal(datos3.get(0)[24].toString());
			    	f3.saveEx(get_TrxName());
			    }
			    datos3.clear();
			    log.config("linea: "+cantLines+".");
		   
		    }
		    br2.close();
		    isr2.close();
		    fis2.close();
		    log.config(datos.toString());		   
	    }
		catch (Exception e) 
		{
			log.config("ERROR: "+e);
		}
	    
		return "procesado";
	}	//	doIt
	
	private boolean  isDate(String dateStr) 
	{
		try 
		{
		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
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
		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
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
}	//	
