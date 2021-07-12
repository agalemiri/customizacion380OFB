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

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.X_HR_Process;
import org.ofb.model.OFBForward;
import org.compiere.model.MPeriod;
import org.compiere.model.MRequisition;
import org.compiere.model.X_HR_AttendanceHours;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import org.compiere.model.X_HR_AttendanceTemplate;
import org.compiere.model.X_OFB_Reloj_Control;
import org.compiere.model.X_OFB_GenerateSeries;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessFillGenerateSeries.java $
 */
public class ProcessFillGenerateSeries extends SvrProcess
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
				{
					if(para[i].getParameter() != null)
						p_BPartner = ((BigDecimal)para[i].getParameter()).intValue();
				}
					
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
	 //Llena la tabla ofb_generateseries en base a la información obtenida desde la union de la generacion
	 //de fechas y socios de negocio
	 
	protected String doIt() throws Exception
	{
		if (p_From != null && p_To != null)
		{
			MPeriod period = new MPeriod (getCtx(), p_Period, get_TrxName());

			String sql = "DELETE from ofb_generateseries WHERE fecha " +
					" between '"+p_From+"' AND '"+p_To+"'";
			if(p_BPartner > 0)
				sql = sql + " AND c_bpartner_id = "+p_BPartner;
			
			log.config("SQL DELETE "+sql);
			
			DB.executeUpdate(sql,get_TrxName());
			/*String sqlgetpartner = "SELECT distinct(c_bpartner_id) from rvofb_reloj_control where fecha " +
					" between '"+p_From+"' AND '"+p_To+"' ";*/
		/*	String sqlgetpartner = "SELECT c_bpartner_id from C_BPartner where isactive='Y' and isemployee='Y' ";
			if(p_BPartner > 0)
				sqlgetpartner = sqlgetpartner + " AND c_bpartner_id = "+p_BPartner;
			if(p_Org > 0)
				sqlgetpartner = sqlgetpartner + " AND c_bpartner_id in (select c_bpartner_id from " +
						" hr_employee where ad_orgref2_id = "+p_Org+")";
			PreparedStatement pstmt2 = DB.prepareStatement(sqlgetpartner, get_TrxName());
			ResultSet rs2 = pstmt2.executeQuery();
			while(rs2.next())
			{
		*/	
			String sqlgethours = "SELECT a.a::date as fecha, to_char(a.a::date::timestamp with time zone, 'TMDay'::text) as  dia," +
					" cbp.c_bpartner_id, cbp.ad_org_id " +
					" FROM generate_series('"+p_From+"'::timestamp with time zone, " +
					" '"+p_To+"'::timestamp with time zone + '1 day'::interval, '1 day'::interval) a(a), " +
					" c_bpartner cbp WHERE cbp.isemployee='Y' " ;
			if(p_BPartner > 0)
				sqlgethours = sqlgethours + " AND cbp.c_bpartner_id = "+p_BPartner;
			
			log.config("query "+sqlgethours);
			
			PreparedStatement pstmt = DB.prepareStatement(sqlgethours, get_TrxName());
			ResultSet rs = pstmt.executeQuery();			
			int counter = 0;
			while(rs.next())
			{
				X_OFB_GenerateSeries serie = new X_OFB_GenerateSeries(getCtx(),0,get_TrxName());
				serie.setfecha(rs.getTimestamp("fecha"));
				serie.setAD_Org_ID(rs.getInt("AD_Org_ID"));
				serie.setC_BPartner_ID(rs.getInt("C_BPartner_ID"));
				serie.setdia(rs.getString("dia"));
				serie.save();
				counter++;
				if(counter == 50 || counter%50 == 0)
					log.config("contador = "+counter);
			}
			rs.close();
			
			//}

			//rs2.close();
		}
		return "Procesado";
	   
	}	//	doIt
	

}
