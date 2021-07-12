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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;

import org.compiere.model.X_HR_AdministrativeRequests;
import org.compiere.model.X_HR_AdministrativeRequestsL;
import org.compiere.model.X_I_AdministrativeRequests;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.ofb.utils.DateUtils;
 
/**
 *	
 *	
 *  @author Italo Niñoles Ininoles
 *  @version $Id: ImportCometidos.java,v 1.2 05/11/2019 $
 */
public class ImportCometidos extends SvrProcess
{
	/** Properties						*/
	
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
			else if (name.equals("archivo"))
				p_PathFile = para[i].getParameterAsString();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	
	/**
	 * 	Create Shipment
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
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
		int cantLines = 0;
	    try 
	    {
	    	
	    	//se borra tabla temporal
	    	DB.executeUpdate("DELETE FROM I_AdministrativeRequests ", get_TrxName());
	    	FileInputStream fis =new FileInputStream(pFile);
		    InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
		    BufferedReader br = new BufferedReader(isr);
		    String linea=br.readLine();		   
		    //se recorre archivo
		    while(linea!=null)
		    {
		        datos.add(linea.split(";"));
			    linea=br.readLine();
			
			    if(datos.get(0)[0] != null && datos.get(0)[1] != null
			    	&& datos.get(0)[4] != null && datos.get(0)[5] != null)
			    {
			    	//se crea registro en tabla temporal
			    	X_I_AdministrativeRequests iReq = new X_I_AdministrativeRequests(getCtx(), 0, get_TrxName());
			    	iReq.setBPValue(datos.get(0)[1]);
			    	iReq.setOrgValue(datos.get(0)[0]);
			    	if(DateUtils.isDateddMMyyyy(datos.get(0)[4]))
			    		iReq.setStartDate(DateUtils.convertDateddMMyyyy(datos.get(0)[4]));
			    	if(DateUtils.isDateddMMyyyy(datos.get(0)[5]))
			    		iReq.setEndDate(DateUtils.convertDateddMMyyyy(datos.get(0)[5]));
			    	iReq.setProcessed(false);
			    	iReq.saveEx(get_TrxName());			    	
			    }
		    	cantLines = cantLines + 1;
		    	datos.clear();
		    }
		    br.close();
		    isr.close();
		    fis.close();
		    log.config("Se han leído "+cantLines+" lineas de archivo");
	    }
		catch (Exception e) 
		{
			log.config("ERROR: "+e);
		}
		//se actualizan id 
		//socio de negocio
		DB.executeUpdate("UPDATE I_AdministrativeRequests i SET C_BPartner_ID =" 
				+" (SELECT bp.C_BPartner_ID FROM C_BPartner bp WHERE bp.Value = i.BPValue) "
				+" WHERE C_BPartner_ID IS NULL AND Processed <> 'Y'", get_TrxName());
		
		//organización
		DB.executeUpdate("UPDATE I_AdministrativeRequests i SET AD_OrgRef_ID =" 
				+" (SELECT o.AD_Org_ID FROM AD_Org o WHERE o.Name = i.OrgValue) "
				+" WHERE AD_OrgRef_ID IS NULL AND Processed <> 'Y'", get_TrxName());
		
		commitEx();
		    
		//se recorre tabla temporal y se generan cometidos
		String sql = "SELECT * FROM I_AdministrativeRequests WHERE Processed <> 'Y' " +
		    		" AND C_BPartner_ID > 0 AND AD_OrgRef_ID > 0";
		int cant = 0; 
		try
		{
			
			PreparedStatement pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				//se crea cometido
				if(rs.getTimestamp("StartDate") != null && rs.getTimestamp("EndDate") != null)
				{
					X_HR_AdministrativeRequests req = new X_HR_AdministrativeRequests(getCtx(), 0, get_TrxName());
					req.setAD_Org_ID(rs.getInt("AD_OrgRef_ID"));
					req.setC_BPartner_ID(rs.getInt("C_Bpartner_ID"));
					req.setDateDoc(rs.getTimestamp("StartDate"));
					req.setRequestType("CMT");
					req.saveEx(get_TrxName());
					cant++;
					
					//se crea detalle de cometido
					//ininoles se genera 1 incidencia por dia
					Timestamp fechaEmision = rs.getTimestamp("StartDate");					
					Timestamp fechaVencimiento = rs.getTimestamp("EndDate");
					Calendar calCalendario = Calendar.getInstance();
					if(fechaEmision != null && fechaVencimiento != null)
					{	
						while (fechaEmision.compareTo(fechaVencimiento) <= 0)
						{	
							X_HR_AdministrativeRequestsL line = new X_HR_AdministrativeRequestsL(getCtx(), 0, get_TrxName());
							line.setHR_AdministrativeRequests_ID(req.get_ID());
							line.setAD_Org_ID(req.getAD_Org_ID());
							line.setAdmType("DIA");
							line.setdatestartrequest(fechaEmision);
							line.set_CustomColumn("date2", fechaEmision);
							line.sethours(new BigDecimal("8.0"));
							line.saveEx(get_TrxName());
							
							//se le suma un dia a la fecha de emision
							calCalendario.setTimeInMillis(fechaEmision.getTime());
							calCalendario.add(Calendar.DATE,1);
							fechaEmision = new Timestamp(calCalendario.getTimeInMillis());
						}
					}	
					//se completa cometido
					req.setDocStatus("CO");
					req.saveEx(get_TrxName());
				}
			}			
			rs.close ();
			pstmt.close ();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "BP - " + sql.toString(), e);
		}		
		return "Se gan generado "+cant+" cometidos";
	}	//	doIt
	
	
	
}	//	InvoiceCreateInOut
