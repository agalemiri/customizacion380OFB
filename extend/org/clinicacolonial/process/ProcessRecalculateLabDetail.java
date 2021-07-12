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

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.*;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPInstance;
import org.compiere.process.ProcessInfo;
import org.compiere.process.SvrProcess;
import org.compiere.util.*;
import org.compiere.model.MBPartner;
import org.compiere.model.X_CC_Hospitalization;
import org.compiere.model.X_MED_LabDetail;
import org.compiere.model.X_MED_Schedule;
import org.compiere.model.X_MED_ScheduleDay;
import org.compiere.model.X_MED_ScheduleTime;
import org.compiere.model.X_MED_Template;
import org.compiere.model.X_MED_TemplateDay;
/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessRecalculateLabDetail.java $
 */

public class ProcessRecalculateLabDetail extends SvrProcess
{
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	private int 			m_Record_ID = 0;
	
	
	protected void prepare()
	{
		m_Record_ID = getRecord_ID();
		
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 *  
	 *  Recalcular registros en med_labdetail. 
	 */
	
	protected String doIt() throws Exception
	{	
		
		int hosp_id = m_Record_ID;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int counter = 0;

		if(hosp_id > 0)
		{
			X_CC_Hospitalization hosp = new X_CC_Hospitalization(Env.getCtx(), hosp_id, get_TrxName());

			String sqlgetlabdetail = "SELECT med_labdetail_id from med_labdetail " +
					" WHERE cc_hospitalization_id = "+hosp_id+" AND isactive='Y' ";
			pstmt = DB.prepareStatement(sqlgetlabdetail, get_TrxName());
			rs = pstmt.executeQuery();
			X_MED_LabDetail lab = null;
			while(rs.next())
			{
				lab = new X_MED_LabDetail(Env.getCtx(),rs.getInt("med_labdetail_id"), get_TrxName());
				MBPartner bp = new MBPartner(Env.getCtx(), hosp.getC_BPartner_ID(), get_TrxName());
			
				int pricelist = bp.getM_PriceList_ID();
				int version = DB.getSQLValue(get_TrxName(), "SELECT coalesce(max(m_pricelist_version_id),0) from m_pricelist_version" +
					" WHERE isactive='Y' AND m_pricelist_id = "+pricelist);
				BigDecimal amountproduct = Env.ZERO;
				if(version > 0)
				{
					int prod = lab.getM_Product_ID();
					amountproduct = DB.getSQLValueBD(get_TrxName(), "SELECT coalesce(max(pricelist),0) from m_productprice" +
						" WHERE m_product_id = ? and m_pricelist_version_id = "+version+"", prod);
				
				}
				log.config("amount "+amountproduct);
				lab.setAmount(amountproduct.multiply(lab.getQty()));
				lab.saveEx();
				counter++;
			}
		
		}
			return "Registros actualizados: "+counter;
	}
	

	
	

}
