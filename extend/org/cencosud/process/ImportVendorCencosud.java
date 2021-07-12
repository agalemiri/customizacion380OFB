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
package org.cencosud.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MProduct;
import org.compiere.model.X_I_Inventory;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.compiere.model.X_I_VendorCencosud;
import org.compiere.model.X_M_VendorCencosud;
import org.compiere.model.X_M_VendorCencosudLine;

/**
 *	Import Physical Inventory from I_Inventory
 *
 * 	@author 	mfrojas
 * 	@version 	$Id: ImportVendorCencosud.java,v 1.2 2019/03/19 00:51:01 mfrojas Exp $
 */
public class ImportVendorCencosud extends SvrProcess
{
	/**	Client to be imported to		*/
	private int				p_AD_Client_ID = 0;
	/**	Organization to be imported to	*/
	private int				p_AD_Org_ID = 0;
	/**	Delete old Imported				*/
	private boolean			p_DeleteOldImported = false;
	
	//@Trifon
	/**	Update Costing					*/
	
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
			else if (name.equals("AD_Client_ID"))
				p_AD_Client_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();

			else if (name.equals("DeleteOldImported"))
				p_DeleteOldImported = "Y".equals(para[i].getParameter());
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}	//	prepare


	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		//log.info("M_Locator_ID=" + p_M_Locator_ID + ",MovementDate=" + p_MovementDate);
		
	/*	if (p_UpdateCosting) {
			if (p_C_AcctSchema_ID <= 0) {
				throw new IllegalArgumentException("Accounting Schema required!");
			}
			if (p_M_CostType_ID <= 0) {
				throw new IllegalArgumentException("Cost Type required!");
			}
			if (p_M_CostElement_ID <= 0 ) {
				throw new IllegalArgumentException("Cost Element required!");
			}
			if (p_AD_OrgTrx_ID < 0 ) {
				throw new IllegalArgumentException("AD_OrgTrx required!");
			}
			 acctSchema = MAcctSchema.get(getCtx(), p_C_AcctSchema_ID, get_TrxName());
		}
		*/
		
		StringBuffer sql = null;
		int no = 0;
		String clientCheck = " AND AD_Client_ID=" + p_AD_Client_ID;

		//	****	Prepare	****

		//	Delete Old Imported
		if (p_DeleteOldImported)
		{
			sql = new StringBuffer ("DELETE I_VendorCencosud "
				  + "WHERE I_IsImported='Y'").append (clientCheck);
			no = DB.executeUpdate (sql.toString (), get_TrxName());
			log.fine("Delete Old Imported=" + no);
		}

		//	Set Client, Org, Location, IsActive, Created/Updated
		sql = new StringBuffer ("UPDATE I_VendorCencosud "
			  + "SET AD_Client_ID = COALESCE (AD_Client_ID,").append (p_AD_Client_ID).append ("),"
			  + " AD_Org_ID = COALESCE (AD_Org_ID,").append (p_AD_Org_ID).append ("),");
		
		sql.append(" IsActive = COALESCE (IsActive, 'Y'),"
			  + " Created = COALESCE (Created, SysDate),"
			  + " CreatedBy = COALESCE (CreatedBy, 0),"
			  + " Updated = COALESCE (Updated, SysDate),"
			  + " UpdatedBy = COALESCE (UpdatedBy, 0),"
			  + " I_ErrorMsg = ' ',"
			 // + " M_Warehouse_ID = NULL,"	//	reset
			  + " I_IsImported = 'N' "
			  + "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		no = DB.executeUpdate (sql.toString (), get_TrxName());
		log.info ("Reset=" + no);

		sql = new StringBuffer ("UPDATE I_VendorCencosud o "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Org, '"
			+ "WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0"
			+ " OR EXISTS (SELECT * FROM AD_Org oo WHERE o.AD_Org_ID=oo.AD_Org_ID AND (oo.IsSummary='Y' OR oo.IsActive='N')))"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (), get_TrxName());
		if (no != 0)
			log.warning ("Invalid Org=" + no);


		//	Location
		sql = new StringBuffer ("UPDATE I_VendorCencosud i "
			+ "SET M_Locator_ID=(SELECT MAX(M_Locator_ID) FROM M_Locator l"
			+ " WHERE i.LocatorValue=l.Value AND i.AD_Client_ID=l.AD_Client_ID) "
			+ "WHERE M_Locator_ID IS NULL AND LocatorValue IS NOT NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (), get_TrxName());
		log.fine("Set Locator from Value =" + no);

		log.fine("Set Locator from X,Y,Z =" + no);

		sql = new StringBuffer ("UPDATE I_VendorCencosud "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Location, ' "
			+ "WHERE M_Locator_ID IS NULL"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (), get_TrxName());
		if (no != 0)
			log.warning ("No Location=" + no);




		//	ProductGroup
		sql = new StringBuffer ("UPDATE I_VendorCencosud i "
			  + "SET M_Product_Group_ID=(SELECT MAX(M_Product_Group_ID) FROM M_Product_Group p"
			  + " WHERE i.ProductGroupValue=p.Value AND i.AD_Client_ID=p.AD_Client_ID) "
			  + "WHERE M_Product_Group_ID IS NULL AND ProductGroupValue IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate (sql.toString (), get_TrxName());
		log.fine("Set Product Group from Value=" + no);

		if (no != 0)
			log.warning ("No Product Group=" + no);



		commitEx();
		
		/*********************************************************************/

		MInventory inventory = null;
        X_M_VendorCencosud vcenco = null;
        
		int noInsert = 0;
		int noInsertLine = 0;

		//	Go through VendorCencosud Records
		sql = new StringBuffer ("SELECT * FROM I_VendorCencosud "
			+ "WHERE I_IsImported='N'").append (clientCheck)
			.append(" ORDER BY M_Locator_ID, TRUNC(Datetrx), I_VendorCencosud_ID");
		try
		{
			PreparedStatement pstmt = DB.prepareStatement (sql.toString (), get_TrxName());
			ResultSet rs = pstmt.executeQuery ();
			//
			int x_M_Locator_ID = -1;
			Timestamp x_Datetrx = null;
			while (rs.next())
			{
				X_I_VendorCencosud imp = new X_I_VendorCencosud (getCtx (), rs, get_TrxName());
				Timestamp Datetrx = TimeUtil.getDay(imp.getDateTrx());

				if (vcenco == null
					|| imp.getM_Locator_ID() != x_M_Locator_ID
					|| !Datetrx.equals(x_Datetrx))
				{
					
					String sqlgetid = "SELECT coalesce(max(m_vendorcencosud_id),0) from m_vendorcencosud " +
							" where m_locator_id = "+imp.getM_Locator_ID()+ " and " +
							" m_product_group_id = "+imp.getM_Product_Group_ID()+" and " +
							" datetrx = '"+imp.getDateTrx()+"'";
					int id = DB.getSQLValue(get_TrxName(), sqlgetid);
					if (id > 0)
						vcenco = new X_M_VendorCencosud (getCtx(), id, get_TrxName());
					else
						vcenco = new X_M_VendorCencosud (getCtx(), 0, get_TrxName());
					
					vcenco.setAD_Org_ID(imp.getAD_Org_ID());
					
					vcenco.setM_Locator_ID(imp.getM_Locator_ID());
					vcenco.setDateTrx(Datetrx);
					vcenco.setM_Product_Group_ID(imp.getM_Product_Group_ID());
					
					//
					if (!vcenco.save())
					{
						log.log(Level.SEVERE, "Vcenco not saved");
						break;
					}
					x_M_Locator_ID = imp.getM_Locator_ID();
					x_Datetrx = Datetrx;
					noInsert++;
				}

				//	Line


				
				X_M_VendorCencosudLine line = new X_M_VendorCencosudLine(getCtx(),0,get_TrxName());
				
				
				line.setM_VendorCencosud_ID(vcenco.getM_VendorCencosud_ID());
				line.setDateStart(imp.getDateStart());
				line.setDateEnd(imp.getDateEnd());
				line.setRutOC(imp.getRutOC());
				line.setRutOT(imp.getRutOT());
				line.setPercentage(imp.getPercentage());
				line.setWeight(imp.getWeight());
				line.setWeightedAmt(imp.getWeightedAmt());
				line.saveEx();
				
				line.saveEx();

				imp.setI_IsImported(true);
				imp.setM_VendorCencosud_ID(line.getM_VendorCencosud_ID());
				//imp.setM_VendorCencosudLine_ID(line.getM_VendorCencosudLine_ID());
				//imp.set_CustomColumn("M_VendorCencosudLine_ID", line.getM_VendorCencosudLine_ID());
				imp.setProcessed(true);
				imp.saveEx();
				
				noInsertLine++;
					//@Trifon update Product cost record if Update costing is enabled
		
			}
			rs.close();
			pstmt.close();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}

		//	Set Error to indicator to not imported
		sql = new StringBuffer ("UPDATE I_VendorCencosud "
			+ "SET I_IsImported='N', Updated=SysDate "
			+ "WHERE I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		addLog (0, null, new BigDecimal (no), "@Errors@");
		//
		addLog (0, null, new BigDecimal (noInsert), "@M_VendorCencosud_ID@: @Inserted@");
		addLog (0, null, new BigDecimal (noInsertLine), "@M_VendorCencosudLine_ID@: @Inserted@");
		return "";
	}	//	doIt

}	//	ImportVendorCencosud
