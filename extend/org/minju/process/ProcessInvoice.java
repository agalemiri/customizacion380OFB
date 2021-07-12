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
package org.minju.process;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MCashLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessInvoice.java $
 *  Procesar INV MINJU
 */

public class ProcessInvoice extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_Invoice_ID = 0; 
	private String				p_Action = "PR";
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
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_Invoice_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_Invoice_ID > 0)
		{
			MInvoice inv = new MInvoice(getCtx(), p_Invoice_ID, get_TrxName());
			//seteo de nuevo estado al procesar
			String newStatus = "DR";
			String newAction = "DR";	
			log.config("paction "+p_Action);
			String flowtype;
			if(inv.get_Value("FlowType") == null)
				flowtype = "FAC";
			else
				flowtype = inv.get_Value("FlowType").toString();
			
			if(inv.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("CO") == 0 &&  flowtype.compareTo("REE") == 0)
			{
				newStatus = "SS";
				newAction = "SS";
			}
			else if(inv.getDocStatus().compareTo("IP") == 0 && p_Action.compareTo("CO") == 0 &&   flowtype.compareTo("REE") == 0)
			{
				newStatus = "SS";
				newAction = "SS";
			}

			else if(inv.getDocStatus().compareTo("SS") == 0 && p_Action.compareTo("CO") == 0 &&    flowtype.compareTo("REE") == 0)
			{
				newStatus = "AV";
				newAction = "AV";
			}

			else if(inv.getDocStatus().compareTo("AV") == 0 && p_Action.compareTo("CO") == 0 &&    flowtype.compareTo("REE") == 0)
			{
				newStatus = "AS";
				newAction = "AS";
			}
			else if(inv.getDocStatus().compareTo("AS") == 0 && p_Action.compareTo("CO") == 0 &&    flowtype.compareTo("REE") == 0)
			{
				newStatus = "GA";
				newAction = "GA";
			}	
			else if(inv.getDocStatus().compareTo("GA") == 0 && p_Action.compareTo("CO") == 0 &&    flowtype.compareTo("REE") == 0)
			{
				newStatus = "AB";
				newAction = "AB";
			}
			else if(inv.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0 &&    flowtype.compareTo("REE") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}	

			//seteo de nuevo estado al rechazar anular un reembolso
			else if(inv.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("VO") == 0 &&    flowtype.compareTo("REE") == 0)
			{
				newStatus = "VO";
				newAction = "VO";
			}
			else if(inv.getDocStatus().compareTo("AV") == 0 && p_Action.compareTo("VO") == 0 &&    flowtype.compareTo("REE") == 0)
			{
				newStatus = "VO";
				newAction = "VO";
			}
			/*else if(inv.getDocStatus().compareTo("GA") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}	
			else if(inv.getDocStatus().compareTo("IN") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}	
			else if(inv.getDocStatus().compareTo("IP") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}*/
			//Desde acá funciona
			if(inv.getDocStatus().compareTo("DR") == 0 && p_Action.compareTo("CO") == 0 &&    flowtype.compareTo("REE") != 0)
			{
				newStatus = "FA";
				newAction = "FA";
			}
			else if(inv.getDocStatus().compareTo("IP") == 0 && p_Action.compareTo("CO") == 0 &&    flowtype.compareTo("REE") != 0) 
			{
				newStatus = "FA";
				newAction = "FA";
			}

			else if(inv.getDocStatus().compareTo("FA") == 0 && p_Action.compareTo("CO") == 0 &&    flowtype.compareTo("REE") != 0)
			{
				newStatus = "AS";
				newAction = "AS";
			}
			
			//mfrojas Se agrega estado intermedio de Autorizado Presupuesto para facturas directas
			else if(inv.getDocStatus().compareTo("AS") == 0 && p_Action.compareTo("CO") == 0 &&   flowtype.compareTo("FAC") == 0)
			{	
				newStatus = "DC";
				newAction = "DC";
			}	
			/*else if(inv.getDocStatus().compareTo("AS") == 0 && p_Action.compareTo("CO") == 0 && inv.getC_DocTypeTarget().getName().compareTo("Factura Directa") == 0)
			{	
				newStatus = "AB";
				newAction = "AB";
			}*/
			else if(inv.getDocStatus().compareTo("AS") == 0 && p_Action.compareTo("CO") == 0 &&   flowtype.compareTo("FDT") == 0)
			{	
				newStatus = "AB";
				newAction = "AB";
			}
			else if(inv.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0 &&   flowtype.compareTo("REE") != 0)
			{	
				newStatus = "DC";
				newAction = "DC";
			}	

			else if(inv.getDocStatus().compareTo("DC") == 0 && p_Action.compareTo("CO") == 0 &&    flowtype.compareTo("REE") != 0)
			{
				newStatus = "AE";
				newAction = "AE";
			}	
			else if(inv.getDocStatus().compareTo("AE") == 0 && p_Action.compareTo("CO") == 0 &&   flowtype.compareTo("REE") != 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}
			//anulaciones
			else if(inv.getDocStatus().compareTo("AE") == 0 && p_Action.compareTo("VO") == 0 &&    flowtype.compareTo("REE") != 0)
			{
				newStatus = "DC";
				newAction = "DC";
			}
			else if(inv.getDocStatus().compareTo("DC") == 0 && p_Action.compareTo("VO") == 0 &&    flowtype.compareTo("REE") != 0)
			{
				newStatus = "AS";
				newAction = "AS";
			}
			else if(inv.getDocStatus().compareTo("AS") == 0 && p_Action.compareTo("VO") == 0 &&    flowtype.compareTo("REE") != 0)
			{
				newStatus = "FA";
				newAction = "FA";
			}
			else if(inv.getDocStatus().compareTo("FA") == 0 && p_Action.compareTo("VO") == 0 &&    flowtype.compareTo("REE") != 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(inv.getDocStatus().compareTo("IP") == 0 && p_Action.compareTo("VO") == 0 &&    flowtype.compareTo("REE") != 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}

			//validacion de rol
			int cant = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM AD_Document_Action_Access daa" +
					" INNER JOIN AD_Ref_List rl ON (daa.AD_Ref_List_ID = rl.AD_Ref_List_ID) " +
					" WHERE value = '"+newAction+"' AND AD_Role_ID = "+Env.getAD_Role_ID(getCtx())+
					" AND C_DocType_ID = "+inv.getC_DocTypeTarget_ID());
			//procesar
			log.config("cantidad"+cant);
			if(cant > 0)
			{	
				if(newAction.compareTo("CO") != 0 &&    flowtype.compareTo("REE") != 0)
				{
					//ininoles flujo de 
					if(inv.getDocStatus().compareTo("AS") == 0)//ininoles validacion
					{
						//solo facturas directas
						if(inv.getC_Order_ID() <=0)
						{
							//traer informacion de BP
							
							MBPartner partner = new MBPartner(getCtx(), inv.getC_BPartner_ID(), get_TrxName());
							if(partner.get_ValueAsString("BudgetReference") != null && partner.get_ValueAsString("BudgetReference").trim().length() > 0
								&&	partner.get_ValueAsString("CodeBudget") != null && partner.get_ValueAsString("CodeBudget").trim().length() > 0)
							{
								inv.set_CustomColumn("BudgetReference",partner.get_ValueAsString("BudgetReference"));
								inv.set_CustomColumn("CodeBudget",partner.get_ValueAsString("CodeBudget"));
								//seteo de estados
								newStatus = "DC";
								newAction = "DC";
							}
						}
					}
					if(newAction.compareTo("DC") == 0)//ininoles validacion
					{
						int cantL = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_InvoiceLine " +
								" WHERE C_Invoice_ID = "+inv.get_ID());
						if(cantL < 1)
							throw new AdempiereException("ERROR: Factura no posee lineas");						
					}
					//@mfrojas Validacion: No se puede pasar al estado de aprobado encargado si alguna linea no tiene asociada una recepcion
					//Pero se debe validar que sea factura directa. 
					
					//@mfrojas Se comenta validacion anterior. Ya no es necesario que una factura este creada desde un recibo
					//20180814
					
					/*if(newAction.compareTo("AE") == 0 &&   flowtype.compareTo("FAC") == 0)
					{
						int recep = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM C_InvoiceLine " +
								" WHERE (M_InOutLine_ID is null or M_InOutLine_ID = 0) AND C_Invoice_ID = "+inv.get_ID());
						if(recep > 0)
							throw new AdempiereException("ERROR: Las lineas de factura deben estar creadas desde un recibo");						
					}
					*/
					inv.setDocStatus(newStatus);
					//ord.save();
					if(newAction.compareTo("FA") == 0)
					{
						//@mfrojas enviar correo de asignación de analista
						inv.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+inv.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 15);
						cst.setString(3, "-");
						cst.setInt(4, inv.get_ID());
						//cst.execute();
					}					
					if(newAction.compareTo("AS") == 0)
					//req.setDescription(req.getDescription()+" *** Solicitud devuelta");
					{
						//@mfrojas enviar correo de asignación de analista
						inv.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+inv.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 16);
						cst.setString(3, "-");
						cst.setInt(4, inv.get_ID());
						//cst.execute();
					}
					if(newAction.compareTo("DC") == 0)
					//req.setDescription(req.getDescription()+" *** Solicitud devuelta");
					{
						//@mfrojas enviar correo de asignación de analista
						inv.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+inv.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 19); // Se cambia de 27 a 19
						cst.setString(3, "-");
						cst.setInt(4, inv.get_ID());
						//cst.execute();
					}
					if(newAction.compareTo("AE") == 0)
					{
						
						
						//@mfrojas enviar correo de asignación de analista
						inv.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+inv.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 20);  // Se cambia de 28 a 20
						cst.setString(3, "-");
						cst.setInt(4, inv.get_ID());
						//cst.execute();
					}
					if(newAction.compareTo("AB") == 0)
					{
						
						
						//@mfrojas enviar correo de Aprobación presupuesto para facturas directas.
						inv.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+inv.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 40);  // Se cambia de 28 a 20
						cst.setString(3, "-");
						cst.setInt(4, inv.get_ID());
						//cst.execute();
					}

					if(newAction.compareTo("CO") == 0)
					{
						//@mfrojas enviar correo de asignación de analista
						//ACA NUNNCA ENTRARÄ
						inv.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+inv.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 29);
						cst.setString(3, "-");
						cst.setInt(4, inv.get_ID());
						//cst.execute();
					}
					if(newAction.compareTo("DR") == 0)
					//req.setDescription(req.getDescription()+" *** Solicitud devuelta");
					{	
						//@mfrojas enviar correo de anulacion
						inv.save();
						String prevmessage = DB.getSQLValueString(get_TrxName(), "SELECT comments3" +
								" from m_requisition where m_requisition_id = ? ", inv.get_ID());
						if(prevmessage != null)
							inv.set_CustomColumn("Comments3", prevmessage +" - " +p_Message);
						else
							inv.set_CustomColumn("Comments3", p_Message);

						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+inv.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						cst.setInt(2, 18);  // Se cambia de 30 a 18
						cst.setString(3, "-");
						cst.setInt(4, inv.get_ID());
						//cst.execute();
					}
					//else
					inv.save();
				}
				else if(newAction.compareTo("CO") == 0 &&    flowtype.compareTo("REE") != 0)
				{
					
					//validar adjuntos
					/** mfrojas
					 * Se comenta validación de código según solicitud 20180220
					 */
					
					/*String sqlatt = "select ad_attachment_id from ad_attachment where ad_table_id = 318 and record_id = "+inv.get_ID();
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
					*/
					/*inv.setDocStatus("IP");
					inv.processIt("CO");
					inv.save();
					*/
					//envío de correos.
					/** mfrojas
					 * Se crea estado intermedio de aprobacion. La factura sera completada
					 * en el pago 20180809
					 */
					inv.setDocStatus("ZZ");
					inv.save();

					CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
					log.config("id "+inv.get_ID());
					cst.setInt(1, Env.getAD_Client_ID(getCtx()));
					cst.setInt(2, 17);
					cst.setString(3, "-");
					cst.setInt(4, inv.get_ID());
					//cst.execute();
				}
				else if(newAction.compareTo("CO") != 0 &&    flowtype.compareTo("REE") == 0)
				{
					if(newAction.compareTo("VO")==0)
					{
						inv.setDocStatus("DR");
						inv.saveEx();
						inv.voidIt();
						inv.setDocStatus("VO");
						inv.saveEx();
					}
					else
					{
	/*					if(newAction.compareTo("AB") == 0)
						{
							MInvoiceLine[] lines = inv.getLines(true);	
							
							for (int i = 0; i < lines.length; i++)
							{
								MInvoiceLine invl = lines[i];
								int validcomb = invl.get_ValueAsInt("C_ValidCombination_ID");

							}
						}
		*/
						inv.setDocStatus(newStatus);
						inv.save();
					}
				}
				else if(newAction.compareTo("CO") == 0 &&   flowtype.compareTo("REE") == 0)
				{
					inv.setDocStatus("IP");
					inv.processIt("CO");
					inv.save();
					
				}
			}
			else
				throw new AdempiereException("Error: Permisos de rol insuficientes");
		}
	   return "Procesado";
	}	//	doIt
}
