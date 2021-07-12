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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
/**
 *	
 *	
 *  @author italo niñoles ininoles
 *  @version $Id: GenMActivaSales.java,v 1.2 2011/06/12 00:51:01  $
 */
public class GenMActivaSales extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	//private Timestamp		p_dateTrx;
	//private int 			p_ID_Locator;
	//private int 			p_ID_PCategory;
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
		String rutaArchivo = p_PathFile;
		String pFile = rutaArchivo;
		ArrayList<String[]> datos=new ArrayList<String[]>();
		//String[] datos= new String[45];
		int cantLines = 0;
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
			    ID_Locator = DB.getSQLValue(get_TrxName(), "SELECT M_Locator_ID " +
			    		" FROM M_Locator WHERE Value like '"+datos.get(0)[0]+"'");
			    ID_Pgroup = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Product_Group_ID) " +
			    		"FROM M_Product_Group WHERE IsActive = 'Y' AND value like '"+datos.get(0)[1].toString()+"'");
			    if(ID_Locator > 0 && ID_Pgroup > 0)
			    {
			    	BigDecimal qty = (BigDecimal) decimalFormat.parse(datos.get(0)[3].trim());
			    	DB.executeUpdate("UPDATE M_Product_GroupActive SET MA45="+qty+
			    			" WHERE M_Locator_ID="+ID_Locator+" AND M_Product_Group_ID="+ID_Pgroup, get_TrxName());
				    cantLines++;
			    }
			    datos.clear();
			    log.config("Actualizados "+cantLines+" registros");
		    }
	    }
		catch (Exception e) 
		{
			log.config("ERROR: "+e);
		}
		return "se han Actualizados "+cantLines+" registros";		
	}	//	doIt
}	//	CopyOrder
