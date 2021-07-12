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
//import java.text.DecimalFormat;
//import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.logging.Level;

//import org.compiere.model.*;
import org.compiere.model.X_M_Product_GroupActive;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.ofb.model.OFBForward;
/**
 *	
 *	
 *  @author italo niñoles ininoles
 *  @version $Id: GenMActiva.java,v 1.2 2011/06/12 00:51:01  $
 */
public class GenMActiva extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	//private Timestamp		p_dateTrx;
	//private int 			p_ID_Locator;
	//private int 			p_ID_PCategory;
	private String			p_cadena;
	
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("description"))
				p_cadena = para[i].getParameterAsString();
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
		if(p_cadena == null)
			p_cadena = "5";
		//primero se importan los documentos
		//DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		//symbols.setGroupingSeparator('.');
		//symbols.setDecimalSeparator(',');
		//String pattern = "#,##0.0#";
		//DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		//decimalFormat.setParseBigDecimal(true);
		String rutaArchivo = OFBForward.CencosudRutaMActiva();
		//1-Se importan inventario
		//se borra stock actual
		DB.executeUpdate("DELETE FROM M_Product_GroupActive", get_TrxName());		
		String pFile = rutaArchivo;
		ArrayList<String[]> datos=new ArrayList<String[]>();
		//String[] datos= new String[45];
		int cantLines = 0;
		int cantLinesValidas = 0;
		int cant = 0;
		String logError = "";
	    try 
	    {
	    	FileInputStream fis =new FileInputStream(pFile);
		    InputStreamReader isr = new InputStreamReader(fis, "UTF8");
		    BufferedReader br = new BufferedReader(isr);
		    String linea=br.readLine();
		    int ID_Locator = 0;
		    int ID_Pgroup  = 0;
		    //int flag = 0;
		    
		    //se recorre archivo
		    while(linea!=null)
		    {
		        datos.add(linea.split(";"));
			    linea=br.readLine();
			    cantLines++;
			    if(datos.get(0)[6].compareTo("00"+p_cadena) == 0 
			    		|| datos.get(0)[6].compareTo("0"+p_cadena) == 0
			    		|| datos.get(0)[6].compareTo(p_cadena) == 0)
			    {
			    	//ininoles se saca validacion para pruebas 01-04-2019
			    	//if(datos.get(0)[15].compareTo("YN") == 0)
			    	//{
					    cantLinesValidas++;
			    		ID_Locator = DB.getSQLValue(get_TrxName(), "SELECT M_Locator_ID " +
			    				" FROM M_Locator WHERE Value like '"+datos.get(0)[1]+"'");
			    		ID_Pgroup = DB.getSQLValue(get_TrxName(), "SELECT M_Product_Group_ID " +
			    				" FROM M_product WHERE help like '"+Integer.parseInt(datos.get(0)[3].toString())+"'");
			    		//antes de crear registro se verifica si existe anteriormente
			    		/*flag = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM M_Product_GroupActive " +
			    				" WHERE IsActive = 'Y' AND M_Locator_ID = "+ID_Locator+" " +
			    				" AND M_Product_Group_ID = "+ID_Pgroup+" AND ViaAbast ='"+(datos.get(0)[47].compareTo("C")==0?"1":"3")+"'");
			    		*/
			    		//if(ID_Locator > 0 && ID_Pgroup > 0 && flag < 1)
			    		if(ID_Locator > 0 && ID_Pgroup > 0)
			    		{
			    			X_M_Product_GroupActive act = new X_M_Product_GroupActive(getCtx(), 0, get_TrxName());
			    			act.setM_Locator_ID(ID_Locator);
			    			act.setM_Product_Group_ID(ID_Pgroup);
			    			//act.setViaAbast(datos.get(0)[47].compareTo("C")==0?"1":"3");
			    			act.setViaAbast(devuelveVA(datos.get(0)[2]));
			    			act.set_CustomColumn("MA13",datos.get(0)[12]);
			    			act.set_CustomColumn("MA14",datos.get(0)[13]);
			    			act.set_CustomColumn("MA45",datos.get(0)[44]);
			    			act.set_CustomColumn("MA48",datos.get(0)[47]);
			    			act.set_CustomColumn("MA21",datos.get(0)[20]);
			    			act.set_CustomColumn("MA3",datos.get(0)[2]);
			    			//ininoles nuevos campos 21-01-2019
			    			act.set_CustomColumn("MA1",datos.get(0)[0]);
			    			act.set_CustomColumn("MA2",datos.get(0)[1]);
			    			act.set_CustomColumn("MA4",datos.get(0)[3]);
			    			act.set_CustomColumn("MA6",datos.get(0)[5]);
			    			act.set_CustomColumn("MA7",datos.get(0)[6]);
			    			act.set_CustomColumn("MA16",datos.get(0)[15]);
			    			act.set_CustomColumn("MA17",datos.get(0)[16]);
			    			act.set_CustomColumn("MA19",datos.get(0)[18]);
			    			act.set_CustomColumn("MA20",datos.get(0)[19]);
			    			act.set_CustomColumn("MA23",datos.get(0)[22]);
			    			act.set_CustomColumn("MA24",datos.get(0)[23]);
			    			act.set_CustomColumn("MA28",datos.get(0)[27]);
			    			act.set_CustomColumn("MA32",datos.get(0)[31]);
			    			act.set_CustomColumn("MA42",datos.get(0)[41]);
			    			act.set_CustomColumn("MA43",datos.get(0)[42]);
			    			act.save(get_TrxName());
			    			cant++;
			    		}
			    		else
			    		{
			    			log.config("Stop para analisis");
			    			logError= logError+datos.get(0)[1]+";"+Integer.parseInt(datos.get(0)[3].toString())+"\n";
			    		}
			    	//}
			    }
			    //if(cantLines == 1000000)
			    //	break;
			    datos.clear();
			    log.config("linea: "+cantLines+". Validas:"+cantLinesValidas);
		    }
		    br.close();
		    isr.close();
		    fis.close();
		    //log.config(datos.toString());
		    //se leen campos del archivo
		    /*
		    for (int x = 1; x < cantLines; x++)
		    {	
		    	//se busca valor fijo y se cuenta
		    	if(datos.get(x)[10] != null)
		    	{
		    		if(datos.get(x)[10].compareTo("3.143") == 0)
		    			cant++;
		    	}

	    	}*/		    
	    }
		catch (Exception e) 
		{
			log.config("ERROR: "+e);
		}
		return "se han encontrado "+cant+" coincidencias. Hay un total de "+cantLinesValidas+" lineas validas";		
	}	//	doIt		
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
