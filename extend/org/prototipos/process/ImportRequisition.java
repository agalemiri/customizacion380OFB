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

import java.math.BigDecimal;
import java.sql.*;
import java.util.logging.*;

import org.compiere.model.*;
import org.compiere.process.SvrProcess;
import org.compiere.util.*;

/**
 *		
 *  @author Fabian Aguilar faaguilar
 *  @version $Id: CreateCombinations.java,v 1.2 2008/06/12 00:51:01  $
 */
public class ImportRequisition extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		String clientCheck = " AND AD_Client_ID=" + Env.getAD_Client_ID(getCtx());
		
		//actualizamos paciente
		StringBuffer sqlf = new StringBuffer ("UPDATE M_RequisitionWS dc "
				+ " SET C_BPartner_ID = (SELECT MAX(C_BPartner_ID) FROM C_BPartner aa"
				+ " WHERE upper(dc.BPartnerValue) like upper(aa.Value) AND dc.AD_Client_ID = aa.AD_Client_ID) "
				+ " WHERE dc.C_BPartner_ID IS NULL AND dc.BPartnerValue IS NOT NULL"
				+ " AND Processed <> 'Y'").append (clientCheck);
		DB.executeUpdate(sqlf.toString(), get_TrxName());
		
		//actualizamos producto
		sqlf = new StringBuffer ("UPDATE M_RequisitionWS dc "
				+ " SET M_Product_ID = (SELECT MAX(M_Product_ID) FROM M_Product aa"
				+ " WHERE upper(dc.ProductValue) like upper(aa.Value) AND dc.AD_Client_ID = aa.AD_Client_ID) "
				+ " WHERE dc.M_Product_ID IS NULL AND dc.ProductValue IS NOT NULL"
				+ " AND Processed <> 'Y'").append (clientCheck);
		DB.executeUpdate(sqlf.toString(), get_TrxName());
		
		/*actualizar errores*/
		/*
		if (DB.isPostgreSQL())
		{	
			sqlf = new StringBuffer ("UPDATE I_Ssomac "	// no asset
					  + " SET I_IsImported='N', I_ErrorMsg=Coalesce(I_ErrorMsg,'')||'ERR=No Asset, ' "
					  + " WHERE A_Asset_ID IS NULL"
					  + " AND I_IsImported <> 'Y'").append (clientCheck);
			DB.executeUpdate(sqlf.toString(), get_TrxName());
		}
		else
		{				
			sqlf = new StringBuffer ("UPDATE I_Ssomac "	// no asset
					  + " SET I_IsImported='N', I_ErrorMsg=Coalesce(I_ErrorMsg,n'')||'ERR=No Asset, ' "
					  + " WHERE A_Asset_ID IS NULL"
					  + " AND I_IsImported <> 'Y'").append (clientCheck);
			DB.executeUpdate(sqlf.toString(), get_TrxName());
		}*/	
	    /*fin actualizar errores*/		
		commitEx();
		
		String ID_sqlUpdate = "0";
		int cant = 0;
		String sqlProd = "SELECT * FROM M_RequisitionWS WHERE Processed <> 'Y' " +
				" AND C_Bpartner_ID IS NOT NULL ";
				//" AND I_IsImported='N' AND I_ErrorMsg IS NULL ORDER BY A_Asset_ID ASC";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlProd, this.get_TrxName());
			rs = pstmt.executeQuery ();
			String oldDocumentNo = "";
			MRequisition req = null;
			int lineNo = 0;
			while (rs.next ())
			{
				if(rs.getInt("C_Bpartner_ID") > 0 )
				{						
					String docNo = rs.getString("DocumentNo1");
					if(docNo.compareTo(oldDocumentNo) != 0)
					{
						//creacion de cabecera
						req = new MRequisition(getCtx(), 0, get_TrxName());
						if(docNo != null)
							req.setDocumentNo(docNo);
						req.setAD_Org_ID(rs.getInt("AD_Org_ID"));
						req.setC_DocType_ID(rs.getInt("C_DocType_ID"));
						req.set_CustomColumn("C_BPartner_ID", rs.getInt("C_BPartner_ID"));
						req.setAD_User_ID(Env.getAD_User_ID(getCtx()));
						req.setDescription("Generado Automaticamente");
						req.setM_Warehouse_ID(rs.getInt("M_Warehouse_ID"));
						req.setDocStatus("DR");
						req.setDocAction("CO");
						req.save();
					}
					lineNo = lineNo+10;
					MRequisitionLine reqLine = new MRequisitionLine(req);
					reqLine.setM_Product_ID(rs.getInt("M_Product_ID"));
					reqLine.setQty(rs.getBigDecimal("Qty"));
					reqLine.setLine(lineNo);
					reqLine.save(get_TrxName());
					cant++;
					oldDocumentNo = docNo;					
					ID_sqlUpdate = ID_sqlUpdate+","+rs.getInt("M_RequisitionWS_ID");
					DB.executeUpdate("UPDATE M_RequisitionWS SET M_Requisition_ID = "+req.get_ID()+", M_RequisitionLine_ID = "+reqLine.get_ID()+ 
							" WHERE M_RequisitionWS_ID = "+rs.getInt("M_RequisitionWS_ID"), get_TrxName());
				}
			}			
		}catch (Exception e)
		{
			log.severe(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs=null;pstmt=null;
		}
		addLog (0, null, new BigDecimal (cant), "Registros Importados");
		DB.executeUpdate("UPDATE M_RequisitionWS SET Processed = 'Y' WHERE M_RequisitionWS_ID IN ("+ID_sqlUpdate+")", get_TrxName());
		
		return "Procesado ";
	}	//	doIt
}	//	CopyOrder
