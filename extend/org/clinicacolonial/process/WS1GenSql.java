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
package org.clinicacolonial.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.X_i_ws1appointment;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 *  @author ininoles
 *  @version $Id: WS1GenSql.java $
 */

public class WS1GenSql extends SvrProcess
{
	private Timestamp			p_DateTo_ID;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if (name.equals("DateTo"))
				p_DateTo_ID = para[i].getParameterAsTimestamp();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if(p_DateTo_ID != null)
		{
			String sql = "select sc.ad_client_id,sc.ad_org_id,sc.isactive,"
					+ " sc.created,sc.createdby,sc.updated,sc.updatedby,"
					+ " (bp.value::text || '-'::text) || COALESCE(bp.digito, bp.duns::character varying, ''::character varying)::text AS rut,"
					+ " coalesce(bp.name4,bp.name) AS name,sp.name AS specialty, now() as timerequested from MED_Schedule sc"
					+ " inner join C_BPartner bp ON (sc.C_BPartnerMed_ID = bp.C_BPartner_ID)"
					+ " inner JOIN med_specialty sp ON sc.med_specialty_id = sp.med_specialty_id"
					+ " WHERE ? BETWEEN dateFrom AND dateTo";

			log.config("SQL Principal: "+sql);
			PreparedStatement pstmt = null;
			PreparedStatement pstmtIn = null;
			ResultSet rs = null;
			//se generan lineas
//			try
	//		{	
				String sqlIn="";
				int last_ID=0;
				pstmt = DB.prepareStatement (sql, get_TrxName());
				pstmt.setTimestamp(1, p_DateTo_ID);
				rs = pstmt.executeQuery ();
				log.config("Parametro antes de ciclo: "+p_DateTo_ID);
				while (rs.next())
				{
					/*last_ID = DB.getNextID(getAD_Client_ID(), "i_ws1appointment", get_TrxName());
					sqlIn= "INSERT INTO i_ws1appointment(ad_client_id,ad_org_id,"
							+ "ad_pinstance_id,created,createdby,i_ws1appointment_id,isactive,"
							+ "name,rut,specialty,timerequested,updated,updatedby,pInstance)"
							+ " VALUES"
							+ "("+rs.getInt("ad_client_id")+","+rs.getInt("ad_org_id")+","
							+ getAD_PInstance_ID()+",now(),100,"+last_ID+",'Y',"
							+ "'"+rs.getString("name")+"','"+rs.getString("rut")+"','"+rs.getString("specialty")+"',"
							+ "?,now(),100,'"+getAD_PInstance_ID()+"')";
					pstmtIn = DB.prepareStatement (sqlIn, get_TrxName());
					pstmtIn.setTimestamp(1, rs.getTimestamp("timerequested"));
					pstmtIn.execute();*/
					X_i_ws1appointment ws1 = new X_i_ws1appointment(getCtx(), 0, get_TrxName());
					ws1.setAD_PInstance_ID(getAD_PInstance_ID());
					ws1.setName(rs.getString("name"));
					ws1.setRut(rs.getString("rut"));
					ws1.setspecialty(rs.getString("specialty"));
					ws1.setTimeRequested(rs.getTimestamp("timerequested"));
					ws1.setpInstance(Integer.toString(getAD_PInstance_ID()));
					if(ws1.save())
						log.config("guardado");
					else
						log.config("ERROR no se pudo guardar");
				}
//			}
/*			catch (Exception e)
			{
				log.log(Level.SEVERE, e.getMessage(), e);
			}
			/*finally
			{
				if(pstmt != null)
					pstmt.close(); 
				if(rs != null)
					rs.close();
				pstmt=null; rs=null;
			}*/
			//commitEx();			
		}	
		return Integer.toString(getAD_PInstance_ID());
	}	//	doIt
}
