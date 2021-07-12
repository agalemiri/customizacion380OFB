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

import java.util.logging.Level;

import org.compiere.model.X_DM_Document;
import org.compiere.model.X_DM_DocumentLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
//import org.compiere.util.CLogger;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 *	
 *	
 *  @author Italo Niñoles ininoles
 *  @version $Id: GenerateLineResolution.java $
 */
public class GenerateLineResolution extends SvrProcess
{
	private int				p_ID_Resolution = 0; 
	private int				p_C_BPartner_ID= 0;
	private int				p_AD_Org_ID= 0;
	private int				p_Parent_Org_ID= 0;
	private String			p_RequestType= "";
	private Timestamp		p_DateFrom = null;
	private Timestamp		p_DateTo = null;
	
	//private static CLogger		s_log = CLogger.getCLogger (MConversionRate.class);
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		 ProcessInfoParameter[] para = getParameter();
			for (int i = 0; i < para.length; i++)
			{
				String name = para[i].getParameterName();				
				if (name.equals("C_BPartner_ID"))
					p_C_BPartner_ID = para[i].getParameterAsInt();
				else if (name.equals("AD_Org_ID"))
					p_AD_Org_ID = para[i].getParameterAsInt();
				else if (name.equals("Parent_Org_ID"))
					p_Parent_Org_ID = para[i].getParameterAsInt();
				else if (name.equals("RequestType"))
					p_RequestType = para[i].getParameterAsString();
				else if (name.equals("DateTrx"))
				{
					p_DateFrom = para[i].getParameterAsTimestamp();
					p_DateTo =  para[i].getParameterToAsTimestamp();
				}
				else
					log.log(Level.SEVERE, "Unknown Parameter: " + name);
			}
			p_ID_Resolution=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{	
		//String logM = "";
		int cant = 0;
		if (p_ID_Resolution > 0)
		{	
			X_DM_Document res = new X_DM_Document(getCtx(),p_ID_Resolution, get_TrxName());
			Timestamp dateNew = new Timestamp(res.getDateTrx().getTime());
			dateNew.setYear(res.getDateTrx().getYear()-2);
			String sql = "";
			if(res.getC_DocType_ID() == 2000138)//resolucion licencias medicas
			{
				sql = "SELECT RH_MedicalLicenses_ID FROM RH_MedicalLicenses b " +
					" WHERE b.DocStatus IN ('CO') AND b.RH_MedicalLicenses_ID NOT IN (SELECT RH_MedicalLicenses_ID FROM DM_DocumentLine WHERE IsActive = 'Y'" +
					" AND RH_MedicalLicenses_ID IS NOT NULL)";
				if(p_DateFrom != null && p_DateTo != null)
					sql+= " AND Date1 >= ? AND Date1 <=? ";
			}
			else if(res.getC_DocType_ID() == 2000180)//resolucion solicitud administrativa
			{
				sql = "SELECT HR_AdministrativeRequests_ID,b.C_BPartner_ID  FROM HR_AdministrativeRequests b " +
					" WHERE b.DocStatus IN ('CO') AND HR_AdministrativeRequests_ID NOT IN (SELECT HR_AdministrativeRequests_ID FROM DM_DocumentLine " +
					" WHERE IsActive = 'Y' AND HR_AdministrativeRequests_ID IS NOT NULL)";
				if(p_DateFrom != null && p_DateTo != null)
					sql+= " AND DateDoc >= ? AND DateDoc <=? ";
				if(p_RequestType != null && p_RequestType.trim().length() > 0)
					sql+= " AND RequestType = '"+p_RequestType+"'";
			}
			else if(res.getC_DocType_ID() == 2000164)//resolucion cambio de bienios
			{
				sql = "SELECT b.C_BPartner_ID FROM HR_Employee b "
					+ " WHERE b.joinDateAP IS NOT NULL "
					+ " AND TRUNC(extract(days from((?)::timestamp - (joinDateAP+qtyDays)))/365)::integer%2 = 0"
					+ " AND TRUNC(extract(days from((?)::timestamp - (joinDateAP+qtyDays)))/365) > 0"
					+ " AND C_BPartner_ID NOT IN (SELECT dl.C_BPartner_ID FROM DM_DocumentLine dl"
					+ " INNER JOIN DM_Document d ON (dl.DM_Document_ID = d.DM_Document_ID)"
					+ " WHERE dl.C_BPartner_ID IS NOT NULL"
					+ " AND DateTrx > ?)";				
			}
			if(p_AD_Org_ID > 0)
				sql+= " AND b.AD_Org_ID = "+p_AD_Org_ID;
			if(p_C_BPartner_ID > 0)
				sql+= " AND b.C_Bpartner_ID = "+p_C_BPartner_ID;			
			//validacion de parametro org padre
			if(p_Parent_Org_ID > 0)
				sql+= " AND b.AD_Org_ID IN (SELECT AD_Org_ID FROM AD_OrgInfo WHERE Parent_Org_ID = "+p_Parent_Org_ID+") ";			
			PreparedStatement pstmt = DB.prepareStatement(sql, get_TrxName());
			if(res.getC_DocType_ID() == 2000164)
			{
				pstmt.setTimestamp(1,res.getDateTrx());
				pstmt.setTimestamp(2,res.getDateTrx());
				pstmt.setTimestamp(3,dateNew);
			}
			else if(p_DateFrom != null && p_DateTo != null)
			{	
				pstmt.setTimestamp(1, p_DateFrom);
				pstmt.setTimestamp(2, p_DateTo);
			}
			ResultSet rs = pstmt.executeQuery();			
			while(rs.next())
			{
				//se generan lineas de detalle en base a sql anterior
				X_DM_DocumentLine rLine = new X_DM_DocumentLine(getCtx(), 0, get_TrxName());
				rLine.setAD_Org_ID(res.getAD_Org_ID());
				rLine.setDM_Document_ID(res.get_ID());
				if(res.getC_DocType_ID() == 2000138)//resolucion licencias medicas
					rLine.set_CustomColumn("RH_MedicalLicenses_ID", rs.getInt("RH_MedicalLicenses_ID"));
				if(res.getC_DocType_ID() == 2000180)//resolucion solicitud administrativa
				{
					rLine.set_CustomColumn("HR_AdministrativeRequests_ID", rs.getInt("HR_AdministrativeRequests_ID"));
					rLine.set_CustomColumn("C_BPartner_ID", rs.getInt("C_BPartner_ID"));
				}
				if(res.getC_DocType_ID() == 2000164)
					rLine.set_CustomColumn("C_BPartner_ID", rs.getInt("C_BPartner_ID"));
				rLine.saveEx(get_TrxName());
				cant++;
			}
		}
		return "Se han agregado "+cant+" lineas";
	   
	}	//	doIt
}
