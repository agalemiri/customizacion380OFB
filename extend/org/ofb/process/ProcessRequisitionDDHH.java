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

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.X_GL_BudgetControlLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: Process Requisition DDHH.java $
 */
public class ProcessRequisitionDDHH extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_Requisition_ID = 0; 
	private String				p_Action = "PR";
	//mfrojas Se agrega nuevo parametro para recibir mensaje al momento de anular.
	private String 			p_Message = "";
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if (name.equals("Action"))
				p_Action = para[i].getParameterAsString();
			else if (name.equals("Message"))
				p_Message = para[i].getParameterAsString();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_Requisition_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_Requisition_ID > 0)
		{
			MRequisition req = new MRequisition(getCtx(), p_Requisition_ID, get_TrxName());
			//seteo de nuevo estado al procesar
			String newStatus = "DR";
			String newAction = "DR";	
			String mode = req.get_Value("Mode").toString();
			
			//mfrojas
			
			
			log.config("mode "+mode);
			
			
			log.config("paction "+p_Action);
			if(req.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "SR";
				newAction = "SR";
			}
			else if(req.getDocStatus().compareTo("SR") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AJ";
				newAction = "AJ";
			}
	
			else if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "AB";
				newAction = "AB";
			}
			else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}	
					
			//seteo de nuevo estado al rechazar
			else if(req.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(req.getDocStatus().compareTo("SR") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}	
			else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}	
			//validacion de rol
			int cant = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM AD_Document_Action_Access daa" +
					" INNER JOIN AD_Ref_List rl ON (daa.AD_Ref_List_ID = rl.AD_Ref_List_ID) " +
					" WHERE value = '"+newAction+"' AND AD_Role_ID = "+Env.getAD_Role_ID(getCtx())+
					" AND C_DocType_ID = "+req.getC_DocType_ID());
			//procesar
			log.config("cantidad"+cant);
			if(cant > 0)
			{
				if(newAction.compareTo("CO") != 0)
				{
					req.setDocStatus(newStatus);
					if(newAction.compareTo("DR") == 0)
					//req.setDescription(req.getDescription()+" *** Solicitud devuelta");
					{
						
						req.set_CustomColumn("Comments2", "*** Solicitud devuelta ***");
						//mfrojas obtenemos mensaje anterior
						String prevmessage = DB.getSQLValueString(get_TrxName(), "SELECT comments3" +
								" from m_requisition where m_requisition_id = ? ", req.get_ID());
						if(prevmessage != null)
							req.set_CustomColumn("Comments3", prevmessage +" - " +p_Message);
						else
							req.set_CustomColumn("Comments3", p_Message);
						
						//@mfrojas enviar correo cuando sea devuelta la solicitud.
						req.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+req.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 8);
						cst.setString(3, "-");
						cst.setInt(4, req.get_ID());
						cst.execute();

					}
					else if(newAction.compareTo("SR") == 0)
					{
						
						//validar adjuntos
						String sqlatt = "select ad_attachment_id from ad_attachment where ad_table_id = 702 and record_id = "+req.get_ID();
						PreparedStatement pstmt = DB.prepareStatement(sqlatt, get_TrxName());
						
						ResultSet rs = pstmt.executeQuery();
						
						int att = 0;
					
						while (rs.next())
							att++;

						if(att <= 0)
						{	
							throw new AdempiereException ("No se ha encontrado adjuntos");
							//return "No se ha encontrado adjuntos";
						}
						req.set_CustomColumn("Comments2", "");
						
						//@mfrojas validar si la solicitud tiene líneas. Si no tiene, no debe poder completarse.
						//begin
						int countlines = DB.getSQLValue(req.get_TrxName(), "SELECT count(1) FROM m_requisitionline where m_requisition_id = "+req.get_ID());

						if(countlines == 0)
							throw new AdempiereException("La solicitud no tiene líneas");
						//end

						//Se agrega validación. Si es que es una solicitud de compra de flujo de trabajo, entonces 
						//se debe cambiar la ad_org_id por la de recursos físicos.
						
						/** Cambio 20171025: La organizacion ya no será recursos físicos. Será ingresada por el usuario.**/
						
						String wf = req.get_ValueAsString("IsWorkflow");
						log.config("isworkflow = "+wf);
						
						if(wf.compareTo("true") == 0)
						{
							//int ID_Org = DB.getSQLValue(req.get_TrxName(), "SELECT MAX(ad_org_id) FROM ad_org where value like 'rrff'");
							int ID_Org = req.get_ValueAsInt("AD_OrgRef2_ID");
							req.set_CustomColumn("AD_OrgRef_ID", req.getAD_Org_ID());
							req.setAD_Org_ID(ID_Org);
							DB.executeUpdate("UPDATE m_requisitionline set ad_org_id = "+ID_Org+" where m_requisition_id = "+req.get_ID(),get_TrxName());
									
						}
						
						
						//Envío de correos para estado SR
						req.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+req.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 9);
						cst.setString(3, "-");
						cst.setInt(4, req.get_ID());
						cst.execute();

					}
					else if(newAction.compareTo("AJ")==0)
					{
						//obtener fecha/hora desde BD
						req.set_CustomColumn("DateReturn", new Timestamp(System.currentTimeMillis()));
						

						req.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+req.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 10);
						cst.setString(3, "-");
						cst.setInt(4, req.get_ID());
						cst.execute();
						//buscamos tasa de cambio
						MCurrency curreTo = new MCurrency(getCtx(), 228, get_TrxName());
						BigDecimal MultiplyRate = null;
						if(req.get_ValueAsInt("C_Currency_ID") != curreTo.get_ID())
						{	
							if(MultiplyRate==null || MultiplyRate.signum()==0)
								MultiplyRate=MConversionRate.getRate(req.get_ValueAsInt("C_Currency_ID"),curreTo.get_ID(),req.getDateDoc(),req.get_ValueAsInt("C_ConversionType_ID"),  
									req.getAD_Client_ID(), req.getAD_Org_ID());
							log.config("currency = "+req.get_ValueAsInt("C_Currency_ID")+" - To_Currency="+curreTo.get_ID()+"- Date="+req.getDateDoc()+"-Conversion="+req.get_ValueAsInt("C_ConversionType_ID"));
							if(MultiplyRate==null || MultiplyRate.compareTo(Env.ZERO)<=0)
								throw new AdempiereUserError("Por favor definir el tipo de cambio", "Conversion Monetaria");
						}
						else // si es moneda base debe multiplicar por 1
						{
							MultiplyRate = Env.ONE;
						}
						//se actualiza monto comprometido
						MRequisitionLine[] lines = req.getLines();	//	Line is default
						for (int i = 0; i < lines.length; i++)
						{
							MRequisitionLine rLine = lines[i];						
							if(rLine.get_ValueAsInt("GL_BudgetControlLine_ID") > 0)
							{
								X_GL_BudgetControlLine bcLine = new X_GL_BudgetControlLine(getCtx(), rLine.get_ValueAsInt("GL_BudgetControlLine_ID"),get_TrxName());
								BigDecimal usedAmt = (BigDecimal)bcLine.get_Value("Amount2");
								if(usedAmt == null)
									usedAmt = Env.ZERO;
								BigDecimal amtConvert = (rLine.getLineNetAmt().multiply(MultiplyRate)).setScale(curreTo.getStdPrecision(),BigDecimal.ROUND_HALF_EVEN);
								//mfrojas el impuesto será trabajado directamente en la línea. Es por esto que se cambia el if
								
								//if(req.get_ValueAsBoolean("IsTaxed"))
								if(rLine.get_ValueAsBoolean("IsTaxed"))
									amtConvert = amtConvert.multiply(new BigDecimal("1.19"));
								usedAmt = usedAmt.add(amtConvert);	 
								bcLine.set_CustomColumn("Amount2", usedAmt);
								bcLine.save();
							}
						}						
					}
					else if(newAction.compareTo("AB")==0)
					{
						req.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+req.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 11);
						cst.setString(3, "-");
						cst.setInt(4, req.get_ID());
						cst.execute();
						
					}

					else
						req.save();
					
					//req.save();
				}
				else if(newAction.compareTo("CO") == 0)
				{
/*					//@mfrojas validar si la solicitud tiene líneas. Si no tiene, no debe poder completarse.
					//begin
					int countlines = DB.getSQLValue(req.get_TrxName(), "SELECT count(1) FROM m_requisitionline where m_requisition_id = "+req.get_ID());

					if(countlines == 0)
						throw new AdempiereException("La solicitud no tiene líneas");
					//end*/
					req.setDocStatus("IP");
					req.processIt("CO");
					req.save();
				}
			}
			else
				throw new AdempiereException("Error: Permisos de rol insuficientes");
		}
	   return "Procesado";
	}	//	doIt
}
