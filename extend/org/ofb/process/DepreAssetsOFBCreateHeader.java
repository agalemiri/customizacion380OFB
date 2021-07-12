/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is Compiere ERP & CRM Smart Business Solution. The Initial
 * Developer of the Original Code is Jorg Janke. Portions created by Jorg Janke
 * are Copyright (C) 1999-2005 Jorg Janke.
 * All parts are Copyright (C) 1999-2005 ComPiere, Inc.  All Rights Reserved.
 * Contributor(s): ______________________________________.
 *****************************************************************************/
package org.ofb.process;

import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.model.*;
import org.compiere.util.*;

/**
 * 	Special Process
 *	
 *  @author mfrojas
 *  @version $Id: DepreAssetsOFBCreateHeader.java,v 1.3 2006/02/24 07:15:43 jjanke Exp $
 */
public class DepreAssetsOFBCreateHeader extends SvrProcess
{

	/** Properties						*/
	private Properties 		m_ctx;
	/*Order ID*/
	private int 			Record_ID;
	private int 			p_Period;
	private Timestamp  		p_Date;

	X_A_Asset_Dep DepDoc = null;
	
	
	protected void prepare()
	{ 
		m_ctx = Env.getCtx();
	 	ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if (name.equals("C_Period_ID"))
				p_Period = ((BigDecimal)para[i].getParameter()).intValue();
			else if(name.equals("DateAcct"))
				p_Date = ((Timestamp)para[i].getParameter());
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}

	//	Record_ID=getRecord_ID();
	//	DepDoc= new X_A_Asset_Dep(m_ctx,Record_ID,get_TrxName());		
	}	//	prepare

	/**
	 * 	Proccess
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt() throws Exception
	{
	/*Proceso para crear las cabeceras de asset dep 
	 * Relacionados con las depreciaciones del año */
		
	
		String sqlobtainorg = "SELECT ad_org_id from ad_org where " +
			" isactive='Y' and upper(name) like 'SEREM%'";
	
		PreparedStatement pstmt1 = null;
		log.config("obtain org "+sqlobtainorg);
		pstmt1 = DB.prepareStatement(sqlobtainorg, get_TrxName());
		ResultSet rs1 = pstmt1.executeQuery();
	
		while (rs1.next() )
		{
			int org_id = 0;
			org_id = rs1.getInt(1);
			
			String sqlobtaingroup = "SELECT A_Asset_Group_ID from A_Asset_Group where isactive='Y' AND " +
					" IsParent='Y'";
			PreparedStatement pstmt2 = null;
			pstmt2 = DB.prepareStatement(sqlobtaingroup, get_TrxName());
			ResultSet rs2 = pstmt2.executeQuery();
			
			while(rs2.next())
			{
				int group_id = 0;
				group_id = rs2.getInt(1);
				
				//validar que no exista un assetdep ya creado para el periodo, grupo y org
				String sqlvalidate = "SELECT count(1) from a_Asset_dep WHERE A_Asset_Group_Ref_ID = "+group_id+" AND " +
						" AD_Org_ID = "+org_id+" and c_period_id = "+p_Period+" AND isactive='Y'";
				int validate = DB.getSQLValue(get_TrxName(), sqlvalidate);
				
				//si validate es > 0, ya existe un proceso para el periodo, grupo y organizacion.
				if(validate > 0)
					continue;
				else
				{
					DepDoc= new X_A_Asset_Dep(m_ctx, 0 ,get_TrxName());
					DepDoc.setAD_Org_ID(org_id);
					DepDoc.set_CustomColumn("A_Asset_Group_Ref_ID", group_id);
					DepDoc.setDepType("DDP");
					DepDoc.setC_Period_ID(p_Period);
					DepDoc.setDateAcct(p_Date);
					DepDoc.setDateDoc(p_Date);
					DepDoc.setDescription("Realizado Automatico");
					DepDoc.setDocStatus("DR");
					DepDoc.set_CustomColumn("IsValid", false);
					DepDoc.saveEx();
					
				
				}
			}
					

		}

		return "";
	}	//	doIt		
}	//	ImportPayment

