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

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.eevolution.model.X_HR_Employee;
import org.compiere.model.X_DM_Document;
/**
 *	
 *	
 *  @author ininoles 
 *  @version $Id: ProcessDMDocument.java $
 */
//
public class ProcessDMDocument extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_DMDoc_ID = 0; 
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{		
		p_DMDoc_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_DMDoc_ID > 0)
		{
			X_DM_Document doc = new X_DM_Document(getCtx(),p_DMDoc_ID, get_TrxName());
			
			if(doc.getC_DocType_ID() == 2000164)//resolucion cambio de bienios
			{
				String sqlDet = "SELECT * FROM DM_DocumentLine WHERE DM_Document_ID="+doc.get_ID();
				PreparedStatement pstmt = DB.prepareStatement(sqlDet, get_TrxName());
				ResultSet rs = pstmt.executeQuery();
				while(rs.next())
				{
					if(rs.getInt("C_BPartner_ID") > 0)
					{	
						//se busca registro a modificar
						int ID_Employee = DB.getSQLValue(get_TrxName(), "SELECT MAX(HR_Employee_ID) FROM HR_Employee" +
								" WHERE IsActive = 'Y' AND C_Bpartner_ID = "+rs.getInt("C_BPartner_ID"));						
						if(ID_Employee > 0)
						{
							X_HR_Employee emp = new X_HR_Employee(getCtx(), ID_Employee, get_TrxName());
							//se busca nuevo bienio
							if(emp.get_ValueAsString("Benio") != null && emp.get_ValueAsString("Benio").trim().length() > 0)
							{
								int bienioOld = Integer.parseInt(DB.getSQLValueString(get_TrxName(), "SELECT description FROM AD_Ref_List "
										+ " WHERE value like '"+emp.get_ValueAsString("Benio")+"' AND AD_Reference_ID=2000157")); 						
								String newBienio = DB.getSQLValueString(get_TrxName(), "SELECT MAX(value) FROM AD_Ref_List "
										+ " WHERE AD_Reference_ID=2000157 AND description = '"+(bienioOld+1)+"'");
								if(newBienio != null && newBienio.trim().length()>0)
								{
									try {
										emp.set_CustomColumn("benio",newBienio);
										emp.saveEx(get_TrxName());										
									}catch (Exception e) {
										log.config(e.toString());
									}
									
								}
							}
						}
						
					}
				}
			}
			doc.setDocStatus("CO");
			doc.setProcessed(true);
			doc.saveEx(get_TrxName());
			//se actualizan lineas
			DB.executeUpdate("UPDATE DM_DocumentLine SET Processed='Y' WHERE DM_Document_ID="+doc.get_ID(), get_TrxName());
		}
	   return "Procesado";
	}	//	doIt
}
