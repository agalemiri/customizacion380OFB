/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
import java.sql.SQLException;
import java.util.logging.Level;

import org.adempiere.exceptions.DBException;
import org.compiere.model.X_HR_HourPlanning;
import org.compiere.model.X_HR_HourPlanningControl;
import org.compiere.model.X_HR_HourPlanningLine;
import org.compiere.process.*;
import org.compiere.util.DB;

/**
 *  Copy Order Lines
 *
 *	@author mfrojas
 *	@version $Id: ProcessCreateHourPlanning.java,v 1.2 2006/07/30 00:51:02 mfrojas Exp $
 */
public class ProcessCreateHourPlanning extends SvrProcess
{
	/**	The Order				*/
	private int		p_HourPlanning_ID;
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
			else if (name.equals("HR_HourPlanningControl_ID"))
				p_HourPlanning_ID = ((BigDecimal)para[i].getParameter()).intValue();
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
		
		if (p_HourPlanning_ID == 0)
			throw new IllegalArgumentException("Parametro No Valido");

		X_HR_HourPlanningControl planning = new X_HR_HourPlanningControl (getCtx(), p_HourPlanning_ID, get_TrxName());

		//
		int no = createLines (planning);		//	no Attributes
		//
		return "Lineas creadas" + no;
	}	//	doIt
	
	public int createLines (X_HR_HourPlanningControl planning)
	{
		if (planning == null)
			return 0;
		int count = 0;
		String sql = "SELECT C_BPartner_ID from C_BPartner where " +
				" IsActive='Y' and " +
				" Suplencia in ('01','02', '05', '06', '07', '08', '03') and " +
				" AD_OrgRef_ID = "+planning.getAD_Org_ID();
		log.config("sql = "+sql);
		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				X_HR_HourPlanningLine line = new X_HR_HourPlanningLine (getCtx(), 0, get_TrxName());
				line.setAD_Org_ID(planning.getAD_Org_ID());
				line.setC_BPartner_ID(rs.getInt("C_BPartner_ID"));
				line.setHR_HourPlanningControl_ID(planning.get_ID());
				line.save();
				count++;
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (SQLException e)
		{
			throw new DBException(e, "Error en query");
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	
		return count;
	}	

}	
