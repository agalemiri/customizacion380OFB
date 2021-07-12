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
package org.copesa.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;

import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MOrderLine;
import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestChange;
import org.compiere.model.X_R_ServiceRequestLine;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.wf.MWFNode;
import org.copesa.model.COPESASROps;
import org.copesa.nodeaction.NodeAction;


/**
 * 	COPESA
 *	
 *  @author Italo Niñoles
 */
public class ProcessServiceRequest extends SvrProcess
{
	/** Product					*/
	//private String DocAction;
	/**	ID COPESA Calendar				*/
	private int 		ID_Request;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare ()
	{
		ID_Request = getRecord_ID();
	}	//	prepare

	
	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		try
		{
			log.fine("Process Service Request");
			X_R_ServiceRequest sReq = new X_R_ServiceRequest(getCtx(), ID_Request, get_TrxName());
			//antes de procesar se genera historial
			X_R_ServiceRequestChange srChange = new X_R_ServiceRequestChange(getCtx(), 0, get_TrxName());
			srChange.setR_ServiceRequest_ID(sReq.get_ID());
			srChange.setSR_AccountType(sReq.getSR_AccountType());
			srChange.setSR_Amt(sReq.getSR_Amt());
			srChange.setSR_BPartner_Loaction_ID(sReq.getSR_BPartner_Loaction_ID());
			srChange.setSR_ChargeDate(sReq.getSR_ChargeDate());
			srChange.setSR_Comments(sReq.getSR_Comments());
			srChange.setSR_CorrectValue(sReq.getSR_CorrectValue());
			srChange.setSR_CreditCardDueDate(sReq.getSR_CreditCardDueDate());
			//srChange.setSR_CreditCardNo(sReq.getSR_CreditCardNo());
			srChange.setSR_Date(sReq.getSR_Date());		
			srChange.setSR_Description(sReq.getSR_Description());
			srChange.setSR_DueDate(sReq.getSR_DueDate());
			srChange.setSR_EditionNo(sReq.getSR_EditionNo());
			srChange.setSR_EMail(sReq.getSR_EMail());
			srChange.setSR_InvoiceNo(sReq.getSR_InvoiceNo());
			srChange.setSR_Last4Digits(sReq.getSR_Last4Digits());
			srChange.setSR_Name(sReq.getSR_Name());
			srChange.setSR_OperationNo(sReq.getSR_OperationNo());
			srChange.setSR_PartialOrTotal(sReq.getSR_PartialOrTotal());
			srChange.setSR_Phone(sReq.getSR_Phone());
			srChange.setSR_Reason(sReq.getSR_Reason());
			srChange.setSR_Reference(sReq.getSR_Reference());
			srChange.setSR_User_ID(sReq.getSR_User_ID());		
			srChange.setZone(sReq.getZone());
			srChange.setSector(sReq.getSector());
			//campos especiales
			srChange.setDate1(new Timestamp(new Date().getTime()));
			srChange.setHours1(new Timestamp(new Date().getTime()));
			srChange.setAD_UserRef_ID(sReq.getAD_User_ID());
			srChange.setDocStatus(sReq.getDocStatus());
			//seteo de rol
			MWFNode nodeOld = new MWFNode(getCtx(), sReq.get_ValueAsInt("AD_WF_Node_ID"), get_TrxName());
			int ID_Role = DB.getSQLValue(get_TrxName(),"SELECT MAX(AD_Role_ID) FROM AD_WF_Node_AccessCOPESA " +
					" WHERE AD_WF_Node_ID = "+nodeOld.get_ID()+" AND IsActive = 'Y'");
			if(ID_Role > 0)
				srChange.setAD_Role_ID(ID_Role);
			srChange.setAD_User_ID(Env.getAD_User_ID(getCtx()));
			srChange.setAD_WF_Node_ID(sReq.getAD_WF_NextNode_ID());
			srChange.setAD_WF_OldNode_ID(sReq.getAD_WF_Node_ID());
			Timestamp lastDate = DB.getSQLValueTS(get_TrxName(), "SELECT MAX(created) FROM R_ServiceRequestChange WHERE R_ServiceRequest_ID = ? AND IsActive = 'Y'",sReq.get_ID());
			if(lastDate != null)//calculo diferencia si existe 
			{
				long dif =  new Timestamp(new Date().getTime()).getTime()-lastDate.getTime();
				//dif = dif / (24 * 60 * 60 * 1000);
				dif = dif / (60 * 1000);
				srChange.setDifferenceStatus(new BigDecimal(dif));
			}		
			//ininoles end
			int nodeid = sReq.get_ValueAsInt("AD_WF_NextNode_ID");
			MWFNode node = new MWFNode(getCtx(), sReq.get_ValueAsInt("AD_WF_NextNode_ID"), get_TrxName());
			sReq.set_CustomColumn("AD_WF_Node_ID", nodeid);		
			sReq.set_CustomColumn("AD_WF_NextNode_ID", null);
			sReq.set_CustomColumn("Help", node.getHelp());
			
			//actualizacion de accion
			if(node.get_ValueAsString("RequestAcction") != null && node.get_ValueAsString("RequestAcction").trim().length() > 0)//nodo con accion
				generateAction(sReq,node.get_ValueAsString("RequestAcction"));
			//actualizacion de estado
			if(node.get_ValueAsString("Status") != null && node.get_ValueAsString("Status").trim().length() > 0)//nodo final
			{
				//
				sReq.setDocStatus(node.get_ValueAsString("Status"));
				sReq.setProcessed(true);
				srChange.setCloseDate(new Timestamp(new Date().getTime()));
			}
			
			COPESASROps.assignUser(sReq, nodeid);

			srChange.save();
			sReq.save();
			return "Procesado";
		}
		catch(Exception e)
		{
			log.warning(e.getMessage());
			throw new AdempiereUserError("No se pudo procesar SR correctamente. " + e.getMessage());
		}
	}	//	doIt


	public void generateAction(X_R_ServiceRequest req, String action) throws Exception
	{
		NodeAction nodeaction = new NodeAction();
		//accion generación de lineas
		if(action.compareTo("G")==0 || action.compareTo("B")==0 || action.compareTo("Y")==0 || action.compareTo("M")==0)
			nodeaction.execute(req, null, action);
		else // demas acciones
		{
			
			String sql = " SELECT R_ServiceRequestLine_ID "+
			" FROM R_ServiceRequestLine WHERE IsActive = 'Y' " +
			" AND R_ServiceRequest_ID = "+req.get_ID();
		
			PreparedStatement pstmt = DB.prepareStatement (sql, get_TrxName());		
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next())
			{
				X_R_ServiceRequestLine rLine = new X_R_ServiceRequestLine(getCtx(), rs.getInt("R_ServiceRequestLine_ID"), get_TrxName());					
				nodeaction.execute(req, rLine, action);
			}
		}
	}

	
}	//	
