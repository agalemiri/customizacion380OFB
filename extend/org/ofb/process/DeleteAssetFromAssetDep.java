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
package org.ofb.process;

import java.sql.CallableStatement;
import java.util.logging.*;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.*;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: DeleteAssetFromAssetDep.java,v 1.2 2008/06/12 00:51:01  $
 */
public class DeleteAssetFromAssetDep extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	private String P_DocAction;
	/*OT ID*/
	private int 			Record_ID;
	
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("DocAction"))
				P_DocAction = (String)para[i].getParameter();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
		
		Record_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  find journals associated to this asset, in assetdep IP or DR
	 *  Delete journals
	 *  Mark intransit = N to this forecast
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		
		//MAsset asset = new MAsset(Env.getCtx(), Record_ID,get_TrxName());
		
		
		String sqlupdate = "UPDATE a_Asset_forecast set intransit='N' where a_asset_id = ? and a_asset_id in " +
				" (select a_asset_id from gl_journal where gl_journalbatch_id in " +
				" (select gl_journalbatch_id " +
				" from a_asset_dep where docstatus in ('IP','DR')))";
		
		DB.executeUpdate(sqlupdate, Record_ID, get_TrxName());
		String sqldelete = "DELETE  from gl_journal where a_Asset_Id = ? and gl_journalbatch_Id in " +
				" (select gl_journalbatch_id " +
				" from a_asset_dep where docstatus in ('IP','DR'))";
		
		DB.executeUpdate(sqldelete, Record_ID, get_TrxName());
		
		
		return "Proceso actualizado";		
	}	//	doIt

	

	
}	//	CopyOrder
