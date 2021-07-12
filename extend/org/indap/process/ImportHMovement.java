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
package org.indap.process;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.logging.*;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.*;
import org.eevolution.model.X_HR_Movement;
import org.eevolution.model.X_I_HR_Movement;
import org.ofb.utils.DateUtils;
/**
 *	
 *	
 *  @author italo niñoles ininoles
 *  @version $Id: ImportHMovement.java,v 1.2 2019/01/07 
 *  Carga masiva de incidentes
 *  
 */
public class ImportHMovement extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	private String 			p_PathFile;
	
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("FilePathOrName"))
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
		//carga de archivo
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
		int cant = 0;
		//antes de cargar archivo se actualizan datos anteriores
		DB.executeUpdate("UPDATE I_HR_Movement SET Processed = 'Y' ", get_TrxName());		
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
		    log.config(datos.toString());
		    //se leen campos del archivo y se cargan en tabla
		    for (int x = 1; x < cantLines; x++)
		    {	
		    	BigDecimal Amt = (BigDecimal) decimalFormat.parse(datos.get(x)[1].trim());
		    	BigDecimal Qty = Env.ZERO;
		    	if(decimalFormat.parse(datos.get(x)[5].trim()) != null)
		    		Qty = (BigDecimal) decimalFormat.parse(datos.get(x)[5].trim());
		    	X_I_HR_Movement iMov = new X_I_HR_Movement(getCtx(), 0, get_TrxName());
		    	iMov.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		    	iMov.setConceptValue(datos.get(x)[0]);
		    	if(Amt != null)
		    		iMov.setAmount(Amt);
		    	else
		    		iMov.setAmount(Env.ZERO);
		    	iMov.setBPartner_Value(datos.get(x)[2]);
		    	if(datos.get(x)[3] != null)
		    		iMov.setValidFrom(DateUtils.convertDateddMMyyyy(datos.get(x)[3]));
		    	iMov.set_CustomColumn("Type",datos.get(x)[4]);
		    	iMov.set_CustomColumn("Days", Qty);
		    	iMov.saveEx(get_TrxName());
		    }
		    commitEx();
		    datos = null;
		    //updates de ID's
		    StringBuffer sql = null;
		    String clientCheck = " AND AD_Client_ID=" + Env.getAD_Client_ID(getCtx());
		    int no = 0;
		    //actualizacion de socio de negocio
		    sql = new StringBuffer ("UPDATE I_HR_Movement o SET C_BPartner_ID = " +
					" (SELECT MAX(C_BPartner_ID) FROM C_BPartner p WHERE value LIKE o.BPartner_Value AND o.AD_Client_ID=p.AD_Client_ID)" +
					" WHERE C_BPartner_ID IS NULL AND Processed<>'Y'").append (clientCheck); 
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.info ("C_BPartner =" + no);
			//error
			sql = new StringBuffer ("UPDATE I_HR_Movement "	//	Error Invalid Doc Type Name
					  + " SET I_IsImported='N', I_ErrorMsg=I_ErrorMsg||'ERR=NO BPartner, ' "
					  + " WHERE C_BPartner_ID IS NULL AND BPartner_Value IS NOT NULL"
					  + " AND I_IsImported<>'Y'").append (clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
				
			//actualizacion de concepto
		    sql = new StringBuffer ("UPDATE I_HR_Movement o SET HR_Concept_ID = " +
					" (SELECT MAX(HR_Concept_ID) FROM HR_Concept p WHERE value LIKE o.ConceptValue AND o.AD_Client_ID=p.AD_Client_ID)" +
					" WHERE HR_Concept_ID IS NULL AND Processed<>'Y'").append (clientCheck); 
			no = DB.executeUpdate(sql.toString(), get_TrxName());
				log.info ("HR_Concept =" + no);
			//error
			sql = new StringBuffer ("UPDATE I_HR_Movement "	//	Error Invalid Doc Type Name
					  + " SET I_IsImported='N', I_ErrorMsg=I_ErrorMsg||'ERR=NO HR_Concept, ' "
					  + " WHERE C_BPartner_ID IS NULL AND BPartner_Value IS NOT NULL"
					  + " AND I_IsImported<>'Y'").append (clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
				
			//se lee tabla temporal y se crean registros
			sql = new StringBuffer ("SELECT * FROM I_HR_Movement "
					  + " WHERE Processed = 'N' AND I_IsImported<>'Y' "
					  + " AND C_BPartner_ID > 0 AND HR_Concept_ID > 0 ").append (clientCheck);
			PreparedStatement pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				X_HR_Movement mov = new X_HR_Movement(getCtx(), 0, get_TrxName());
				mov.setAD_Org_ID(rs.getInt("AD_Org_ID"));
				mov.setHR_Concept_ID(rs.getInt("HR_Concept_ID"));
				mov.setAmount(rs.getBigDecimal("Amount"));
				mov.setC_BPartner_ID(rs.getInt("C_BPartner_ID"));
				if(rs.getTimestamp("ValidFrom") != null)					
					mov.setValidFrom(rs.getTimestamp("ValidFrom"));
				mov.set_CustomColumn("Type", rs.getString("Type"));
				mov.set_CustomColumn("Days", rs.getBigDecimal("Days"));
				mov.saveEx(get_TrxName());
				cant++;
			}
	    }
		catch (Exception e) 
		{
			log.config("ERROR: "+e);
		}
		return "SE HAN GENERADO "+cant+" REGISTROS";		
	}	//	doIt	
}	//	

