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

import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.X_CC_Evaluation;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 *	
 *	
 */
public class GenerateReqFromMedIndications extends SvrProcess
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
		//X_CC_MedicalIndications mind = new X_CC_MedicalIndications(getCtx(), p_MedIndications_ID, get_TrxName());				
		X_CC_Evaluation ev = new X_CC_Evaluation(getCtx(), p_Evaluation_ID, get_TrxName());
		int cant =0;		
		int canthead =0;
		
		//se revisa si  existen farmacos no controlados(comunes)
		int cantfnc = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM CC_MedicalIndications mi" 
				+" INNER JOIN M_Product mp ON (mi.M_Product_ID = mp.M_Product_ID) "
				+" WHERE mi.IsActive='Y' AND mp.M_Product_Group_ID <> 2000028 "
				+" AND mi.CC_Evaluation_ID="+p_Evaluation_ID);
		
		if(cantfnc > 0)
		{
			//se crea cargo y consumo de fármacos comunes
			MRequisition req = new MRequisition(getCtx(), 0, get_TrxName());
			req.setAD_Org_ID(2000006);
			req.setC_DocType_ID(2000149);
			req.set_CustomColumn("C_Bpartner_ID", ev.getC_BPartner_ID());
			req.set_CustomColumn("CC_Hospitalization_ID", ev.getCC_Hospitalization_ID());
			req.setAD_User_ID(ev.getCreatedBy());
			req.setDateDoc(ev.getCreated());
			req.setM_Warehouse_ID(2000015);
			req.setM_PriceList_ID(2000005);
			req.save(get_TrxName());
			canthead++;
			
			String sql = "SELECT mp.M_product_ID,mi.Qty,mi.DeliveryViaRule,mi.Frecuency,mi.Description,mi.Description1 "
					+" FROM CC_MedicalIndications mi" 
					+" INNER JOIN M_Product mp ON (mi.M_Product_ID = mp.M_Product_ID) "
					+" WHERE mi.IsActive='Y' AND mp.M_Product_Group_ID <> 2000028 "
					+" AND mi.CC_Evaluation_ID="+p_Evaluation_ID;
				
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			//se generan lineas
			try
			{
				pstmt = DB.prepareStatement (sql, get_TrxName());
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					MRequisitionLine rLine =new MRequisitionLine(req);
					rLine.setM_Product_ID(rs.getInt("M_product_ID"));
					rLine.setQty(rs.getBigDecimal("Qty"));
					//nuevos campos 
					rLine.set_CustomColumn("DeliveryViaRule",rs.getString("DeliveryViaRule"));
					rLine.set_CustomColumn("Frecuency",rs.getString("Frecuency"));
					rLine.setDescription(rs.getString("Description"));
					rLine.set_CustomColumn("Description1",rs.getString("Description1"));
					rLine.save();
					cant++;
				}
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
			if(req.get_ID() > 0)
				DB.executeUpdate("UPDATE M_Requisition SET CC_Evaluation_ID = "+p_Evaluation_ID+" WHERE M_Requisition_ID="+req.get_ID(),get_TrxName());
			
		}
		//
		//se revisa si  existen farmacos controlados
		int cantfc = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM CC_MedicalIndications mi" 
				+" INNER JOIN M_Product mp ON (mi.M_Product_ID = mp.M_Product_ID) "
				+" WHERE mi.IsActive='Y' AND mp.M_Product_Group_ID = 2000028 "
				+" AND mi.CC_Evaluation_ID="+p_Evaluation_ID);
				
		if(cantfc > 0)
		{
			String sql = "SELECT mp.M_product_ID,mi.Qty,mi.DeliveryViaRule,mi.Frecuency,mi.Description,mi.Description1 "
					+" FROM CC_MedicalIndications mi" 
					+" INNER JOIN M_Product mp ON (mi.M_Product_ID = mp.M_Product_ID) "
					+" WHERE mi.IsActive='Y' AND mp.M_Product_Group_ID = 2000028 "
					+" AND mi.CC_Evaluation_ID="+p_Evaluation_ID;
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			//se generan lineas
			try
			{
				pstmt = DB.prepareStatement (sql, get_TrxName());
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					//se crea cargo y consumo de fármacos controlados
					MRequisition req = new MRequisition(getCtx(), 0, get_TrxName());
					req.setAD_Org_ID(2000006);
					req.setC_DocType_ID(2000176);
					req.set_CustomColumn("C_Bpartner_ID", ev.getC_BPartner_ID());
					req.set_CustomColumn("CC_Hospitalization_ID", ev.getCC_Hospitalization_ID());
					req.setAD_User_ID(ev.getCreatedBy());
					req.setDateDoc(ev.getCreated());
					req.setM_Warehouse_ID(2000015);
					req.setM_PriceList_ID(2000005);
					req.save(get_TrxName());
					canthead++;
					
					MRequisitionLine rLine =new MRequisitionLine(req);
					rLine.setM_Product_ID(rs.getInt("M_product_ID"));
					rLine.setQty(rs.getBigDecimal("Qty"));
					//nuevos campos 
					rLine.set_CustomColumn("DeliveryViaRule",rs.getString("DeliveryViaRule"));
					rLine.set_CustomColumn("Frecuency",rs.getString("Frecuency"));
					rLine.setDescription(rs.getString("Description"));
					rLine.set_CustomColumn("Description1",rs.getString("Description1"));
					rLine.save();
					cant++;
					
					//se guarda ID
					if(req.get_ID() > 0)
						DB.executeUpdate("UPDATE M_Requisition SET CC_Evaluation_ID = "+p_Evaluation_ID+" WHERE M_Requisition_ID="+req.get_ID(),get_TrxName());
				}
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
		return "Se ha generado "+canthead+" cargos/consumos y "+cant+" lineas ";
	}	//	doIt
}	//	Replenish
