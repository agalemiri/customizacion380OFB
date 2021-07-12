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
package org.junaeb.process;

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
 *  @author Italo Niñoles ininoles 
 *  @version $Id: ProcessSalesOrder.java $
 */
//public class ProcessBudgetControl extends SvrProcess
public class ProcessRequisition extends SvrProcess
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
			String modality = req.get_Value("Modality").toString();
			
			//mfrojas
			
			
			log.config("modality "+modality);
			
			
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
	
			
			//cambio 20180305 mfrojas
			/**
			 * Hasta este punto se mantiene el código. Desde ahora se diferenciará el tipo de la compra, 
			 * dependiendo del campo "modality" de la solicitud de compra. Para distintas compras, se utilizará
			 * distintos estados. Pero todos llegan hasta el analista presupuesto.
			 * 
			 */
			
			//Para convenio marco y contrato de arrastre
			else if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("CAR") == 0 || modality.compareTo("MAR") == 0))
			{
				newStatus = "AB";
				newAction = "AB";
			}
			else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("CAR") == 0 || modality.compareTo("MAR") == 0))
			{
				newStatus = "GA";
				newAction = "GA";
			}	
			else if(req.getDocStatus().compareTo("GA") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("CAR") == 0 || modality.compareTo("MAR") == 0))
			{
				newStatus = "CO";
				newAction = "CO";
			}	

			//Para gran compra, licitación > 1000 UTM
			else if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "AB";
				newAction = "AB";
			}
			
			else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QA";
				newAction = "QA";
			}	
			else if(req.getDocStatus().compareTo("QA") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QB";
				newAction = "QB";
			}	
			else if(req.getDocStatus().compareTo("QB") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "AL";
				newAction = "AL";
			}	
			else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QC";
				newAction = "QC";
			}	
			else if(req.getDocStatus().compareTo("QC") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QD";
				newAction = "QD";
			}	
			else if(req.getDocStatus().compareTo("QD") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "AX";
				newAction = "AX";
			}	
			else if(req.getDocStatus().compareTo("AX") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QE";
				newAction = "QE";
			}	
			else if(req.getDocStatus().compareTo("QE") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QF";
				newAction = "QF";
			}	
			else if(req.getDocStatus().compareTo("QF") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "Q8";
				newAction = "Q8";
			}	
			else if(req.getDocStatus().compareTo("Q8") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QH";
				newAction = "QH";
			}	
			else if(req.getDocStatus().compareTo("QH") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QO";
				newAction = "QO";
			}	
			else if(req.getDocStatus().compareTo("QO") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QI";
				newAction = "QI";
			}	
			else if(req.getDocStatus().compareTo("QI") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QJ";
				newAction = "QJ";
			}	
			else if(req.getDocStatus().compareTo("QJ") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QK";
				newAction = "QK";
			}	
			else if(req.getDocStatus().compareTo("QK") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QL";
				newAction = "QL";
			}	
			else if(req.getDocStatus().compareTo("QL") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "Q9";
				newAction = "Q9";
			}
			else if(req.getDocStatus().compareTo("Q9") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "Q0";
				newAction = "Q0";
			}	

			else if(req.getDocStatus().compareTo("Q0") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QN";
				newAction = "QN";
			}	
			else if(req.getDocStatus().compareTo("QN") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QU";
				newAction = "QU";
			}	
			else if(req.getDocStatus().compareTo("QU") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QP";
				newAction = "QP";
			}	
			else if(req.getDocStatus().compareTo("QP") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QQ";
				newAction = "QQ";
			}	
			else if(req.getDocStatus().compareTo("QQ") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QR";
				newAction = "QR";
			}	
			else if(req.getDocStatus().compareTo("QR") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QS";
				newAction = "QS";
			}	
			else if(req.getDocStatus().compareTo("QS") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "AM";
				newAction = "AM";
			}	
			else if(req.getDocStatus().compareTo("AM") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "QT";
				newAction = "QT";
			}	
			else if(req.getDocStatus().compareTo("QT") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "CO";
				newAction = "CO";
			}	

			
		//OLD gran compra	
/*			else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "AL";
				newAction = "AL";
			}	
			else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "AX";
				newAction = "AX";
			}	
			else if(req.getDocStatus().compareTo("AX") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "AM";
				newAction = "AM";
			}	
			else if(req.getDocStatus().compareTo("AM") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("GC") == 0))
			{
				newStatus = "CO";
				newAction = "CO";
			}	

	*/		
			//Licitaciones < 100 UTM
/*			else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("LIB") == 0))
			{
				newStatus = "GA";
				newAction = "GA";
			}	
			else if(req.getDocStatus().compareTo("GA") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("LIB") == 0))
			{
				newStatus = "PP";
				newAction = "PP";
			}	

			else if(req.getDocStatus().compareTo("PP") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("LIB") == 0))
			{
				newStatus = "AL";
				newAction = "AL";
			}	
			else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("LIB") == 0))
			{
				newStatus = "AA";
				newAction = "AA";
			}	
			else if(req.getDocStatus().compareTo("AA") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("LIB") == 0))
			{
				newStatus = "CO";
				newAction = "CO";
			}	

	*/		
			
			// Esto será para cuando se cambie el flujo, donde LIB ahora será "Licitaciones Públicas" y luego 
			// dependerá de otro campo
			else if(modality.compareTo("LIB")==0)
			{
				String bidding = req.get_Value("bidding_types").toString();
				
				//primero, si es < 100
				if(bidding.compareTo("L1")==0)
				{
					
					if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "GA";
						newAction = "GA";
					}

					else if(req.getDocStatus().compareTo("GA") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "PP";
						newAction = "PP";
					}	
					else if(req.getDocStatus().compareTo("PP") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AB";
						newAction = "AB";
					}	
					else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QV";
						newAction = "QV";
					}	
					else if(req.getDocStatus().compareTo("QV") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AR";
						newAction = "AR";
					}	
					else if(req.getDocStatus().compareTo("AR") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QB";
						newAction = "QB";
					}	

					else if(req.getDocStatus().compareTo("QB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AL";
						newAction = "AL";
					}	
					else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AA";
						newAction = "AA";
					}	
					else if(req.getDocStatus().compareTo("AA") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QE";
						newAction = "QE";
					}
					else if(req.getDocStatus().compareTo("QE") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QW";
						newAction = "QW";
					}
					else if(req.getDocStatus().compareTo("QW") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q5";
						newAction = "Q5";
					}

					else if(req.getDocStatus().compareTo("Q5") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QG";
						newAction = "QG";
					}
	/*				else if(req.getDocStatus().compareTo("QG") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QX";
						newAction = "QX";
					}*/
					else if(req.getDocStatus().compareTo("QG") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QY";
						newAction = "QY";
					}
					else if(req.getDocStatus().compareTo("QY") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q6";
						newAction = "Q6";
					}
					else if(req.getDocStatus().compareTo("Q6") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QH";
						newAction = "QH";
					}
					else if(req.getDocStatus().compareTo("QH") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QO";
						newAction = "QO";
					}
					else if(req.getDocStatus().compareTo("QO") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q1";
						newAction = "Q1";
					}
					else if(req.getDocStatus().compareTo("Q1") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QL";
						newAction = "QL";
					}
					else if(req.getDocStatus().compareTo("QL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "CO";
						newAction = "CO";
					}
				}
				//Entre 100 y 1000
				else if(bidding.compareTo("LE")==0)
				{
					if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AB";
						newAction = "AB";
					}

					else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QA";
						newAction = "QA";
					}	
					else if(req.getDocStatus().compareTo("QA") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QB";
						newAction = "QB";
					}	
					else if(req.getDocStatus().compareTo("QB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AL";
						newAction = "AL";
					}	
					else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QC";
						newAction = "QC";
					}	
					else if(req.getDocStatus().compareTo("QC") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QD";
						newAction = "QD";
					}	
					else if(req.getDocStatus().compareTo("QD") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AX";
						newAction = "AX";
					}	
					else if(req.getDocStatus().compareTo("AX") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QE";
						newAction = "QE";
					}	
					else if(req.getDocStatus().compareTo("QE") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q2";
						newAction = "Q2";
					}	
					else if(req.getDocStatus().compareTo("Q2") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QG";
						newAction = "QG";
					}	
					else if(req.getDocStatus().compareTo("QG") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QH";
						newAction = "QH";
					}	
					else if(req.getDocStatus().compareTo("QH") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QO";
						newAction = "QO";
					}	
					else if(req.getDocStatus().compareTo("QO") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q3";
						newAction = "Q3";
					}
					else if(req.getDocStatus().compareTo("Q3") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QJ";
						newAction = "QJ";
					}	
					else if(req.getDocStatus().compareTo("QJ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QK";
						newAction = "QK";
					}	
					else if(req.getDocStatus().compareTo("QK") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QL";
						newAction = "QL";
					}
					else if(req.getDocStatus().compareTo("QL") == 0 && p_Action.compareTo("CO") == 0)
					{
						//mfrojas 20180321
						//Se deberá validar en este punto. Si está marcado un check de que genera contrato
						//Sigue con el flujo original. Si no está marcado, pasa a completo
						if(req.get_ValueAsBoolean("GenerateContract"))
						{
							newStatus = "Q7";
							newAction = "Q7";
							
						}
						else if(!req.get_ValueAsBoolean("GenerateContract"))
						{
							newStatus = "CO";
							newAction = "CO";
						}
					}	
					else if(req.getDocStatus().compareTo("Q7") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QM";
						newAction = "QM";
					}	
					else if(req.getDocStatus().compareTo("QM") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QN";
						newAction = "QN";
					}	
					else if(req.getDocStatus().compareTo("QN") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QU";
						newAction = "QU";
					}	
					else if(req.getDocStatus().compareTo("QU") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QP";
						newAction = "QP";
					}	
					else if(req.getDocStatus().compareTo("QP") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QQ";
						newAction = "QQ";
					}	
					else if(req.getDocStatus().compareTo("QQ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QR";
						newAction = "QR";
					}	
					else if(req.getDocStatus().compareTo("QR") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QS";
						newAction = "QS";
					}	
					else if(req.getDocStatus().compareTo("QS") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AM";
						newAction = "AM";
					}	
					else if(req.getDocStatus().compareTo("AM") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QT";
						newAction = "QT";
					}	
					else if(req.getDocStatus().compareTo("QT") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "CO";
						newAction = "CO";
					}	


				}
				//mayor a 1000
				else
				{

					if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AB";
						newAction = "AB";
					}

					else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QA";
						newAction = "QA";
					}	
					else if(req.getDocStatus().compareTo("QA") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QB";
						newAction = "QB";
					}	
					else if(req.getDocStatus().compareTo("QB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AL";
						newAction = "AL";
					}	
					else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QC";
						newAction = "QC";
					}	
					else if(req.getDocStatus().compareTo("QC") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QD";
						newAction = "QD";
					}	
					else if(req.getDocStatus().compareTo("QD") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AX";
						newAction = "AX";
					}	
					else if(req.getDocStatus().compareTo("AX") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QE";
						newAction = "QE";
					}	
					else if(req.getDocStatus().compareTo("QE") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q2";
						newAction = "Q2";
					}	
					else if(req.getDocStatus().compareTo("Q2") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QG";
						newAction = "QG";
					}	
					else if(req.getDocStatus().compareTo("QG") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QH";
						newAction = "QH";
					}	
					else if(req.getDocStatus().compareTo("QH") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QO";
						newAction = "QO";
					}	
					else if(req.getDocStatus().compareTo("QO") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q3";
						newAction = "Q3";
					}	
					else if(req.getDocStatus().compareTo("Q3") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QJ";
						newAction = "QJ";
					}	
					else if(req.getDocStatus().compareTo("QJ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QK";
						newAction = "QK";
					}	
					else if(req.getDocStatus().compareTo("QK") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QL";
						newAction = "QL";
					}	
					else if(req.getDocStatus().compareTo("QL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q7";
						newAction = "Q7";
					}	
					else if(req.getDocStatus().compareTo("Q7") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QM";
						newAction = "QM";
					}	
					else if(req.getDocStatus().compareTo("QM") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QN";
						newAction = "QN";
					}	
					else if(req.getDocStatus().compareTo("QN") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QU";
						newAction = "QU";
					}	
					else if(req.getDocStatus().compareTo("QU") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QP";
						newAction = "QP";
					}	
					else if(req.getDocStatus().compareTo("QP") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QQ";
						newAction = "QQ";
					}	
					else if(req.getDocStatus().compareTo("QQ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QR";
						newAction = "QR";
					}	
					else if(req.getDocStatus().compareTo("QR") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QS";
						newAction = "QS";
					}	
					else if(req.getDocStatus().compareTo("QS") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AM";
						newAction = "AM";
					}	
					else if(req.getDocStatus().compareTo("AM") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QT";
						newAction = "QT";
					}	
					else if(req.getDocStatus().compareTo("QT") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "CO";
						newAction = "CO";
					}	

/*					if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AB";
						newAction = "AB";
					}
					
					else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QA";
						newAction = "QA";
					}	
					else if(req.getDocStatus().compareTo("QA") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QB";
						newAction = "QB";
					}	
					else if(req.getDocStatus().compareTo("QB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AL";
						newAction = "AL";
					}	
					else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QC";
						newAction = "QC";
					}	
					else if(req.getDocStatus().compareTo("QC") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QD";
						newAction = "QD";
					}	
					else if(req.getDocStatus().compareTo("QD") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AX";
						newAction = "AX";
					}	
					else if(req.getDocStatus().compareTo("AX") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QE";
						newAction = "QE";
					}	
					else if(req.getDocStatus().compareTo("QE") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QF";
						newAction = "QF";
					}	
					else if(req.getDocStatus().compareTo("QF") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QG";
						newAction = "QG";
					}	
					else if(req.getDocStatus().compareTo("QG") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QH";
						newAction = "QH";
					}	
					else if(req.getDocStatus().compareTo("QH") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QO";
						newAction = "QO";
					}	
					else if(req.getDocStatus().compareTo("QO") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QI";
						newAction = "QI";
					}	
					else if(req.getDocStatus().compareTo("QI") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QJ";
						newAction = "QJ";
					}	
					else if(req.getDocStatus().compareTo("QJ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QK";
						newAction = "QK";
					}	
					else if(req.getDocStatus().compareTo("QK") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QL";
						newAction = "QL";
					}	
					else if(req.getDocStatus().compareTo("QL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QM";
						newAction = "QM";
					}	
					else if(req.getDocStatus().compareTo("QM") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QN";
						newAction = "QN";
					}	
					else if(req.getDocStatus().compareTo("QN") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QU";
						newAction = "QU";
					}	
					else if(req.getDocStatus().compareTo("QU") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QP";
						newAction = "QP";
					}	
					else if(req.getDocStatus().compareTo("QP") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QQ";
						newAction = "QQ";
					}	
					else if(req.getDocStatus().compareTo("QQ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QR";
						newAction = "QR";
					}	
					else if(req.getDocStatus().compareTo("QR") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QS";
						newAction = "QS";
					}	
					else if(req.getDocStatus().compareTo("QS") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AM";
						newAction = "AM";
					}	
					else if(req.getDocStatus().compareTo("AM") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QT";
						newAction = "QT";
					}	
					else if(req.getDocStatus().compareTo("QT") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "CO";
						newAction = "CO";
					}	
		
	*/				
					/*if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AL";
						newAction = "AL";
					}	
					else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AX";
						newAction = "AX";
					}	
					else if(req.getDocStatus().compareTo("AX") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AM";
						newAction = "AM";
					}	
					else if(req.getDocStatus().compareTo("AM") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "CO";
						newAction = "CO";
					}	
		*/
				}
					
				

			}

			//Licitaciones privadas
			else if(modality.compareTo("LIC")==0)
			{
				String bidding = req.get_Value("bidding_types1").toString();
				
				//primero, si es < 100
				if(bidding.compareTo("E2")==0)
				{
					if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "GA";
						newAction = "GA";
					}

					else if(req.getDocStatus().compareTo("GA") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "PP";
						newAction = "PP";
					}	
					else if(req.getDocStatus().compareTo("PP") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AB";
						newAction = "AB";
					}	
					else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QV";
						newAction = "QV";
					}	
					else if(req.getDocStatus().compareTo("QV") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AR";
						newAction = "AR";
					}	
					else if(req.getDocStatus().compareTo("AR") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QB";
						newAction = "QB";
					}	

					else if(req.getDocStatus().compareTo("QB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AL";
						newAction = "AL";
					}	
					else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AA";
						newAction = "AA";
					}	
					else if(req.getDocStatus().compareTo("AA") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QE";
						newAction = "QE";
					}
					else if(req.getDocStatus().compareTo("QE") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QW";
						newAction = "QW";
					}
					else if(req.getDocStatus().compareTo("QW") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q5";
						newAction = "Q5";
					}

					else if(req.getDocStatus().compareTo("Q5") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QG";
						newAction = "QG";
					}
	/*				else if(req.getDocStatus().compareTo("QG") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QX";
						newAction = "QX";
					}*/
					else if(req.getDocStatus().compareTo("QG") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QY";
						newAction = "QY";
					}
					else if(req.getDocStatus().compareTo("QY") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q6";
						newAction = "Q6";
					}
					else if(req.getDocStatus().compareTo("Q6") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QH";
						newAction = "QH";
					}
					else if(req.getDocStatus().compareTo("QH") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QO";
						newAction = "QO";
					}
					else if(req.getDocStatus().compareTo("QO") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q1";
						newAction = "Q1";
					}
					else if(req.getDocStatus().compareTo("Q1") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QL";
						newAction = "QL";
					}
					else if(req.getDocStatus().compareTo("QL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "CO";
						newAction = "CO";
					}

				}
				//Entre 100 y 1000
				else if(bidding.compareTo("CO")==0)
				{
					if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AB";
						newAction = "AB";
					}

					else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QA";
						newAction = "QA";
					}	
					else if(req.getDocStatus().compareTo("QA") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QB";
						newAction = "QB";
					}	
					else if(req.getDocStatus().compareTo("QB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AL";
						newAction = "AL";
					}	
					else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QC";
						newAction = "QC";
					}	
					else if(req.getDocStatus().compareTo("QC") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QD";
						newAction = "QD";
					}	
					else if(req.getDocStatus().compareTo("QD") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AX";
						newAction = "AX";
					}	
					else if(req.getDocStatus().compareTo("AX") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QE";
						newAction = "QE";
					}	
					else if(req.getDocStatus().compareTo("QE") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q2";
						newAction = "Q2";
					}	
					else if(req.getDocStatus().compareTo("Q2") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QG";
						newAction = "QG";
					}	
					else if(req.getDocStatus().compareTo("QG") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QH";
						newAction = "QH";
					}	
					else if(req.getDocStatus().compareTo("QH") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QO";
						newAction = "QO";
					}	
					else if(req.getDocStatus().compareTo("QO") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q3";
						newAction = "Q3";
					}	
					else if(req.getDocStatus().compareTo("Q3") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QJ";
						newAction = "QJ";
					}	
					else if(req.getDocStatus().compareTo("QJ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QK";
						newAction = "QK";
					}	
					else if(req.getDocStatus().compareTo("QK") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QL";
						newAction = "QL";
					}	
					else if(req.getDocStatus().compareTo("QL") == 0 && p_Action.compareTo("CO") == 0)
					{
						//mfrojas 20180321
						//Se deberá validar en este punto. Si está marcado un check de que genera contrato
						//Sigue con el flujo original. Si no está marcado, pasa a completo
						if(req.get_ValueAsBoolean("GenerateContract"))
						{
							newStatus = "Q7";
							newAction = "Q7";
							
						}
						else if(!req.get_ValueAsBoolean("GenerateContract"))
						{
							newStatus = "CO";
							newAction = "CO";
						}
					}	
					else if(req.getDocStatus().compareTo("Q7") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QM";
						newAction = "QM";
					}	
					else if(req.getDocStatus().compareTo("QM") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QN";
						newAction = "QN";
					}	
					else if(req.getDocStatus().compareTo("QN") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QU";
						newAction = "QU";
					}	
					else if(req.getDocStatus().compareTo("QU") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QP";
						newAction = "QP";
					}	
					else if(req.getDocStatus().compareTo("QP") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QQ";
						newAction = "QQ";
					}	
					else if(req.getDocStatus().compareTo("QQ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QR";
						newAction = "QR";
					}	
					else if(req.getDocStatus().compareTo("QR") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QS";
						newAction = "QS";
					}	
					else if(req.getDocStatus().compareTo("QS") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AM";
						newAction = "AM";
					}	
					else if(req.getDocStatus().compareTo("AM") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QT";
						newAction = "QT";
					}	
					else if(req.getDocStatus().compareTo("QT") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "CO";
						newAction = "CO";
					}	

				}
				//mayor a 1000
				else
				{
					if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AB";
						newAction = "AB";
					}

					else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QA";
						newAction = "QA";
					}	
					else if(req.getDocStatus().compareTo("QA") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QB";
						newAction = "QB";
					}	
					else if(req.getDocStatus().compareTo("QB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AL";
						newAction = "AL";
					}	
					else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QC";
						newAction = "QC";
					}	
					else if(req.getDocStatus().compareTo("QC") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QD";
						newAction = "QD";
					}	
					else if(req.getDocStatus().compareTo("QD") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AX";
						newAction = "AX";
					}	
					else if(req.getDocStatus().compareTo("AX") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QE";
						newAction = "QE";
					}	
					else if(req.getDocStatus().compareTo("QE") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q2";
						newAction = "Q2";
					}	
					else if(req.getDocStatus().compareTo("Q2") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QG";
						newAction = "QG";
					}	
					else if(req.getDocStatus().compareTo("QG") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QH";
						newAction = "QH";
					}	
					else if(req.getDocStatus().compareTo("QH") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QO";
						newAction = "QO";
					}	
					else if(req.getDocStatus().compareTo("QO") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q3";
						newAction = "Q3";
					}	
					else if(req.getDocStatus().compareTo("Q3") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QJ";
						newAction = "QJ";
					}	
					else if(req.getDocStatus().compareTo("QJ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QK";
						newAction = "QK";
					}	
					else if(req.getDocStatus().compareTo("QK") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QL";
						newAction = "QL";
					}	
					else if(req.getDocStatus().compareTo("QL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "Q7";
						newAction = "Q7";
					}	
					else if(req.getDocStatus().compareTo("Q7") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QM";
						newAction = "QM";
					}	
					else if(req.getDocStatus().compareTo("QM") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QN";
						newAction = "QN";
					}	
					else if(req.getDocStatus().compareTo("QN") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QU";
						newAction = "QU";
					}	
					else if(req.getDocStatus().compareTo("QU") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QP";
						newAction = "QP";
					}	
					else if(req.getDocStatus().compareTo("QP") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QQ";
						newAction = "QQ";
					}	
					else if(req.getDocStatus().compareTo("QQ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QR";
						newAction = "QR";
					}	
					else if(req.getDocStatus().compareTo("QR") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QS";
						newAction = "QS";
					}	
					else if(req.getDocStatus().compareTo("QS") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AM";
						newAction = "AM";
					}	
					else if(req.getDocStatus().compareTo("AM") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QT";
						newAction = "QT";
					}	
					else if(req.getDocStatus().compareTo("QT") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "CO";
						newAction = "CO";
					}	

/*					if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AB";
						newAction = "AB";
					}
					
					else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QA";
						newAction = "QA";
					}	
					else if(req.getDocStatus().compareTo("QA") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QB";
						newAction = "QB";
					}	
					else if(req.getDocStatus().compareTo("QB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AL";
						newAction = "AL";
					}	
					else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QC";
						newAction = "QC";
					}	
					else if(req.getDocStatus().compareTo("QC") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QD";
						newAction = "QD";
					}	
					else if(req.getDocStatus().compareTo("QD") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AX";
						newAction = "AX";
					}	
					else if(req.getDocStatus().compareTo("AX") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QE";
						newAction = "QE";
					}	
					else if(req.getDocStatus().compareTo("QE") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QF";
						newAction = "QF";
					}	
					else if(req.getDocStatus().compareTo("QF") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QG";
						newAction = "QG";
					}	
					else if(req.getDocStatus().compareTo("QG") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QH";
						newAction = "QH";
					}	
					else if(req.getDocStatus().compareTo("QH") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QO";
						newAction = "QO";
					}	
					else if(req.getDocStatus().compareTo("QO") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QI";
						newAction = "QI";
					}	
					else if(req.getDocStatus().compareTo("QI") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QJ";
						newAction = "QJ";
					}	
					else if(req.getDocStatus().compareTo("QJ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QK";
						newAction = "QK";
					}	
					else if(req.getDocStatus().compareTo("QK") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QL";
						newAction = "QL";
					}	
					else if(req.getDocStatus().compareTo("QL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QM";
						newAction = "QM";
					}	
					else if(req.getDocStatus().compareTo("QM") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QN";
						newAction = "QN";
					}	
					else if(req.getDocStatus().compareTo("QN") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QU";
						newAction = "QU";
					}	
					else if(req.getDocStatus().compareTo("QU") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QP";
						newAction = "QP";
					}	
					else if(req.getDocStatus().compareTo("QP") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QQ";
						newAction = "QQ";
					}	
					else if(req.getDocStatus().compareTo("QQ") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QR";
						newAction = "QR";
					}	
					else if(req.getDocStatus().compareTo("QR") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QS";
						newAction = "QS";
					}	
					else if(req.getDocStatus().compareTo("QS") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AM";
						newAction = "AM";
					}	
					else if(req.getDocStatus().compareTo("AM") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "QT";
						newAction = "QT";
					}	
					else if(req.getDocStatus().compareTo("QT") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "CO";
						newAction = "CO";
					}	

	*/				
					
/*					if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AL";
						newAction = "AL";
					}	
					else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AX";
						newAction = "AX";
					}	
					else if(req.getDocStatus().compareTo("AX") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "AM";
						newAction = "AM";
					}	
					else if(req.getDocStatus().compareTo("AM") == 0 && p_Action.compareTo("CO") == 0)
					{
						newStatus = "CO";
						newAction = "CO";
					}	
*/
				}
					
				

			}


			//trato directo > 100 y < 1000 

			else if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0 && modality.compareTo("DIR") == 0)
			{
				newStatus = "AB";
				newAction = "AB";
			}
			else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0 && modality.compareTo("DIR") == 0)
			{
				newStatus = "QA";
				newAction = "QA";
			}	
			else if(req.getDocStatus().compareTo("QA") == 0 && p_Action.compareTo("CO") == 0 && modality.compareTo("DIR") == 0)
			{
				newStatus = "QB";
				newAction = "QB";
			}	
			else if(req.getDocStatus().compareTo("QB") == 0 && p_Action.compareTo("CO") == 0 &&  modality.compareTo("DIR") == 0)
			{
				newStatus = "AL";
				newAction = "AL";
			}	
			else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0 &&  modality.compareTo("DIR") == 0)
			{
				newStatus = "QC";
				newAction = "QC";
			}
			else if(req.getDocStatus().compareTo("QC") == 0 && p_Action.compareTo("CO") == 0 &&  modality.compareTo("DIR") == 0)
			{
				newStatus = "QD";
				newAction = "QD";
			}
			else if(req.getDocStatus().compareTo("QD") == 0 && p_Action.compareTo("CO") == 0 &&  modality.compareTo("DIR") == 0)
			{
				newStatus = "AX";
				newAction = "AX";
			}
			else if(req.getDocStatus().compareTo("AX") == 0 && p_Action.compareTo("CO") == 0 &&  modality.compareTo("DIR") == 0)
			{
				newStatus = "QE";
				newAction = "QE";
			}
			else if(req.getDocStatus().compareTo("QE") == 0 && p_Action.compareTo("CO") == 0 &&  modality.compareTo("DIR") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}

			//Trato directo < 100 UTM

			else if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0 && modality.compareTo("3ME") == 0)
			{
				newStatus = "AB";
				newAction = "AB";
			}
			else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("3ME") == 0))
			{
				newStatus = "GA";
				newAction = "GA";
			}	
			else if(req.getDocStatus().compareTo("GA") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("3ME") == 0))
			{
				newStatus = "PP";
				newAction = "PP";
			}	
			else if(req.getDocStatus().compareTo("PP") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("3ME") == 0))
			{
				newStatus = "QV";
				newAction = "QV";
			}	
			else if(req.getDocStatus().compareTo("QV") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("3ME") == 0))
			{
				newStatus = "AR";
				newAction = "AR";
			}	
			else if(req.getDocStatus().compareTo("AR") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("3ME") == 0))
			{
				newStatus = "QB";
				newAction = "QB";
			}	
			else if(req.getDocStatus().compareTo("QB") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("3ME") == 0))
			{
				newStatus = "AL";
				newAction = "AL";
			}	
			else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("3ME") == 0))
			{
				newStatus = "Q4";
				newAction = "Q4";
			}	
			else if(req.getDocStatus().compareTo("Q4") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("3ME") == 0))
			{
				newStatus = "AA";
				newAction = "AA";
			}	
			else if(req.getDocStatus().compareTo("AA") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("3ME") == 0))
			{
				newStatus = "QE";
				newAction = "QE";
			}	
			else if(req.getDocStatus().compareTo("QE") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("3ME") == 0))
			{
				newStatus = "CO";
				newAction = "CO";
			}	


			//COMPRA MENOR A 3UTM
			
			else if(req.getDocStatus().compareTo("AJ") == 0 && p_Action.compareTo("CO") == 0 && modality.compareTo("ME3") == 0)
			{
				newStatus = "AB";
				newAction = "AB";
			}
			else if(req.getDocStatus().compareTo("AB") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("ME3") == 0))
			{
				newStatus = "GA";
				newAction = "GA";
			}	
			else if(req.getDocStatus().compareTo("GA") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("ME3") == 0))
			{
				newStatus = "PP";
				newAction = "PP";
			}	
			else if(req.getDocStatus().compareTo("PP") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("ME3") == 0))
			{
				newStatus = "QV";
				newAction = "QV";
			}	
			else if(req.getDocStatus().compareTo("QV") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("ME3") == 0))
			{
				newStatus = "AR";
				newAction = "AR";
			}	
			else if(req.getDocStatus().compareTo("AR") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("ME3") == 0))
			{
				newStatus = "AA";
				newAction = "AA";
			}	
			else if(req.getDocStatus().compareTo("AA") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("ME3") == 0))
			{
				newStatus = "QE";
				newAction = "QE";
			}	
			else if(req.getDocStatus().compareTo("QE") == 0 && p_Action.compareTo("CO") == 0 && (modality.compareTo("ME3") == 0))
			{
				newStatus = "CO";
				newAction = "CO";
			}	

			
/*			else if(req.getDocStatus().compareTo("GA") == 0 && p_Action.compareTo("CO") == 0)
			{
				newStatus = "CO";
				newAction = "CO";
			}	
*/			//seteo de nuevo estado al rechazar
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
			else if(req.getDocStatus().compareTo("GA") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(req.getDocStatus().compareTo("AL") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(req.getDocStatus().compareTo("AA") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(req.getDocStatus().compareTo("AX") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(req.getDocStatus().compareTo("PP") == 0 && p_Action.compareTo("VO") == 0)
			{
				newStatus = "DR";
				newAction = "DR";
			}
			else if(req.getDocStatus().compareTo("AM") == 0 && p_Action.compareTo("VO") == 0)
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
						
						
						//20180524 mfrojas se agrega cambio, que cuando la solicitud es devuelta
						//se debe recuperar el flujo de trabajo original
						
						String wf = req.get_ValueAsString("IsWorkflow");
						log.config("isworkflow = "+wf);
						
						if(wf.compareTo("true") == 0)
						{
							//int ID_Org = DB.getSQLValue(req.get_TrxName(), "SELECT MAX(ad_org_id) FROM ad_org where value like 'rrff'");
							int ID_Org_Original = req.get_ValueAsInt("AD_OrgRef_ID");
							//req.set_CustomColumn("AD_OrgRef_ID", req.getAD_Org_ID());
							if(ID_Org_Original > 0)
							{
								req.setAD_Org_ID(ID_Org_Original);
								DB.executeUpdate("UPDATE m_requisitionline set ad_org_id = "+ID_Org_Original+" where m_requisition_id = "+req.get_ID(),get_TrxName());
							}
									
						}
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
					else if(newAction.compareTo("GA")==0)
					{
						req.save();
						CallableStatement cst = DB.prepareCall("{call pa_envia_mail_auto2(?,?,?,?)}");
						log.config("id "+req.get_ID());
						cst.setInt(1, Env.getAD_Client_ID(getCtx()));
						//mfrojas. Si es un contrato de arrastre, el correo debe ser distinto
						String hascontract = req.get_ValueAsString("HasContract");
						log.config("hascontract = "+hascontract);

						if(modality.compareTo("CAR") == 0 && hascontract.compareTo("true") == 0)
							cst.setInt(2, 40);
						else
							cst.setInt(2, 12);
						cst.setString(3, "-");
						cst.setInt(4, req.get_ID());
						cst.execute();
						
					}
					else if (newAction.compareTo("AX") == 0 || newAction.compareTo("PP") == 0 || newAction.compareTo("AL") == 0 || newAction.compareTo("AA")==0 || newAction.compareTo("AM") == 0)
						req.save();
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
