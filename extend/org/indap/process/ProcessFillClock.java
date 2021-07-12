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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.model.MPeriod;
import java.sql.Timestamp;
import org.compiere.model.X_OFB_Reloj_Control;


/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessFillClock.java $
 */
public class ProcessFillClock extends SvrProcess
{
	
	private int				p_Period = 0; 
	private int 			p_BPartner = 0;
	private Timestamp  		p_From;
	private Timestamp		p_To;
	private int 			p_Org = 0;

	/**
	 *  Prepare - e.g., get Parameters.
	 *  
	 */
	 protected void prepare()
	{
		 	ProcessInfoParameter[] para = getParameter();
			for (int i = 0; i < para.length; i++)
			{
				String name = para[i].getParameterName();
				
				if (name.equals("DateTo"))
					p_To = ((Timestamp)para[i].getParameter());
				else if (name.equals("C_BPartner_ID"))
					p_BPartner = ((BigDecimal)para[i].getParameter()).intValue();
				else if(name.equals("DateFrom"))
					p_From = ((Timestamp)para[i].getParameter());
				else if(name.equals("AD_OrgRef2_ID"))
					p_Org = ((BigDecimal)para[i].getParameter()).intValue();
				else
					log.log(Level.SEVERE, "Unknown Parameter: " + name);
			}
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	 //Llena la tabla ofb_reloj_control en base a la vista rvofb_Reloj_control
	 
	protected String doIt() throws Exception
	{
		if (p_From != null && p_To != null)
		{
			MPeriod period = new MPeriod (getCtx(), p_Period, get_TrxName());

			String sql = "DELETE from ofb_Reloj_control WHERE fecha " +
					" between '"+p_From+"' AND '"+p_To+"'";
			if(p_BPartner > 0)
				sql = sql + " AND c_bpartner_id = "+p_BPartner;
			
			log.config("SQL DELETE "+sql);
			
			DB.executeUpdate(sql,get_TrxName());
			/*String sqlgetpartner = "SELECT distinct(c_bpartner_id) from rvofb_reloj_control where fecha " +
					" between '"+p_From+"' AND '"+p_To+"' ";*/
			String sqlgetpartner = "SELECT c_bpartner_id from C_BPartner where isactive='Y' and isemployee='Y' ";
			if(p_BPartner > 0)
				sqlgetpartner = sqlgetpartner + " AND c_bpartner_id = "+p_BPartner;
			if(p_Org > 0)
				sqlgetpartner = sqlgetpartner + " AND c_bpartner_id in (select c_bpartner_id from " +
						" hr_employee where ad_orgref2_id = "+p_Org+")";
			PreparedStatement pstmt2 = DB.prepareStatement(sqlgetpartner, get_TrxName());
			ResultSet rs2 = pstmt2.executeQuery();
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			while(rs2.next())
			{
				String sqlgethours = "SELECT fecha, ad_org_id, c_bpartner_id, dia, entrada, salida, horas_trabajadas, " +
					" incidencias, compensadas, atrasobase, horasmediamannana FROM RVOFB_Reloj_Control WHERE fecha " +
					" between '"+p_From+"' AND '"+p_To+"'";
				sqlgethours = sqlgethours + " AND c_bpartner_id = "+rs2.getInt("C_BPartner_ID");
	
				log.config("SQL obtener desde vista "+sqlgethours);
				pstmt = DB.prepareStatement(sqlgethours, get_TrxName());
				rs = pstmt.executeQuery();			
				while(rs.next())
				{
					X_OFB_Reloj_Control reloj = new X_OFB_Reloj_Control(getCtx(),0,get_TrxName());
					reloj.setfecha(rs.getTimestamp("fecha"));
					reloj.setAD_Org_ID(rs.getInt("AD_Org_ID"));
					reloj.setC_BPartner_ID(rs.getInt("C_BPartner_ID"));
					reloj.setdia(rs.getString("dia"));
					reloj.setentrada(rs.getTimestamp("entrada"));
					reloj.setsalida(rs.getTimestamp("salida"));
					reloj.sethoras_trabajadas(rs.getTimestamp("horas_trabajadas"));
					String incid = rs.getString("incidencias");
					if(rs.getString("incidencias") == null || rs.getString("incidencias").compareTo("") == 0)
						reloj.setincidencias(" ");
					else
						reloj.setincidencias(rs.getString("incidencias"));
					reloj.setcompensadas(rs.getInt("compensadas"));
					reloj.setatrasobase(rs.getTimestamp("atrasobase"));
					reloj.sethorasmediamannana(rs.getTimestamp("horasmediamannana"));
					reloj.save();
				}
				commitEx();
				rs.close();pstmt.close();
				rs = null;pstmt = null;
			}
			rs2.close();pstmt2.close();
			rs2 = null;pstmt2 = null;
		}
		return "Procesado";
	   
	}	//	doIt
	

}
