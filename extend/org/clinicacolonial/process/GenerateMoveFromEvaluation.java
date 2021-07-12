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
 * Contributor(s): Chris Farley - northernbrewer                              *
 *****************************************************************************/
package org.clinicacolonial.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.model.MDocType;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 *	
 *	
 */
public class GenerateMoveFromEvaluation extends SvrProcess
{	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	private int p_Evaluation_ID ;
			
	protected void prepare()
	{	
		p_Evaluation_ID = getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{	
		//MRequisition req = new MRequisition(getCtx(), p_Evaluation_ID, get_TrxName());				
		int cant = 0;
		int id_BPart = DB.getSQLValue(get_TrxName(), "SELECT C_Bpartner_ID FROM CC_Evaluation WHERE CC_Evaluation_ID ="+p_Evaluation_ID);
		//generacion de movimiento
		int id_LocatorFrom = DB.getSQLValue(get_TrxName(), " SELECT MAX(M_Locator_ID) FROM M_Locator ml "+ 
			" WHERE UPPER(ml.value) like '%ENFERMER%' AND IsActive = 'Y'");
		int id_LocatorTo = DB.getSQLValue(get_TrxName(), "SELECT M_Locator_ID " +
				" FROM CC_Operation op WHERE C_Bpartner_ID = "+id_BPart+" ORDER BY startdate");
		String DocNo = "--";
		if(id_LocatorFrom > 0 && id_LocatorTo > 0)
		{
			MMovement mov = new MMovement(getCtx(), 0, get_TrxName());
			mov.setC_DocType_ID(MDocType.getDocType("MMM"));
			mov.setC_BPartner_ID(id_BPart);
			mov.saveEx();		
			DocNo = mov.getDocumentNo();			
			String sql = "SELECT M_product_ID,Qty FROM CC_MedicalIndications mi" +
					" WHERE CC_Evaluation_ID="+p_Evaluation_ID;
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			//se generan lineas
			try
			{
				pstmt = DB.prepareStatement (sql, get_TrxName());
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					MMovementLine mLine =new MMovementLine(mov);
					mLine.setM_Product_ID(rs.getInt("M_product_ID"));
					mLine.setM_Locator_ID(id_LocatorFrom);
					mLine.setM_LocatorTo_ID(id_LocatorTo);
					mLine.setMovementQty(rs.getBigDecimal("Qty"));
					mLine.save();
					cant++;
				}
				if(mov.get_ID() > 0)
					DB.executeUpdate("UPDATE CC_Evaluation SET M_Movement_ID = "+mov.get_ID()+" WHERE CC_Evaluation_ID="+p_Evaluation_ID,get_TrxName());
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, e.getMessage(), e);
			}
			finally
			{
				pstmt.close(); rs.close();
				pstmt=null; rs=null;
			}
		}
		else
			return "No se ha podido generar el movimiento ";
		return "Se ha generado el movimiento numero:"+DocNo+". Y se han agregado "+cant+" lineas ";
	}	//	doIt
}	//	Replenish
