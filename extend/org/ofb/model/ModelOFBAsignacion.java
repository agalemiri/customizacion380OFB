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
package org.ofb.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MInvoice;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Validator default OFB
 *
 *  @author Italo Niñoles
 */
public class ModelOFBAsignacion implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelOFBAsignacion ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelOFBAsignacion.class);
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	

	/**
	 *	Initialize Validation
	 *	@param engine validation engine
	 *	@param client client
	 */
	public void initialize (ModelValidationEngine engine, MClient client)
	{
		//client = null for global validator
		if (client != null) {
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else  {
			log.info("Initializing global validator: "+this.toString());
		}

		//	Tables to be monitored
		//engine.addModelChange(MPaymentAllocate.Table_Name, this);
		//	Documents to be monitored
		engine.addDocValidate(MAllocationHdr.Table_Name, this);


	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	
     */
	public static final String DOCSTATUS_Drafted = "DR";
	public static final String DOCSTATUS_Completed = "CO";
	public static final String DOCSTATUS_InProgress = "IP";
	public static final String DOCSTATUS_Voided = "VO";
	
	
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		
		return null;
	}	//	modelChange

	/**
	 *	Validate Document.
	 *	Called as first step of DocAction.prepareIt
     *	when you called addDocValidate for the table.
     *	Note that totals, etc. may not be correct.
	 *	@param po persistent object
	 *	@param timing see TIMING_ constants
     *	@return error message or null
	 */
	public String docValidate (PO po, int timing)
	{
		log.info(po.get_TableName() + " Timing: "+timing);
		//ininoles validacion para aprobar solicitudes de compra por monto en AD_User
		if (timing == TIMING_AFTER_COMPLETE && po.get_Table_ID()==MAllocationHdr.Table_ID)			
		{
			MAllocationHdr allo = (MAllocationHdr) po;
			MAllocationLine[] lines = allo.getLines(false);
			String sql = "SELECT C_AllocationLine_ID FROM C_AllocationLine al"
					+ " INNER JOIN C_AllocationHdr a ON (al.C_AllocationHdr_ID = a.C_AllocationHdr_ID) "
					+ " WHERE a.DocStatus IN ('CO','CL') AND al.C_Invoice_ID =?";
			BigDecimal amountinvoice = Env.ZERO;
			BigDecimal amountAlloLine = Env.ZERO;
			BigDecimal sumAmtAllocation = Env.ZERO;
			for (int i = 0; i < lines.length; i++)
			{
				amountinvoice = Env.ZERO;
				sumAmtAllocation = Env.ZERO;
				amountAlloLine = Env.ZERO;
				MAllocationLine line = lines[i];
				if(line.getC_Invoice_ID() > 0)
				{	
					MInvoice inv = new MInvoice(po.getCtx(), line.getC_Invoice_ID(),po.get_TrxName());
					if(inv.getC_Currency_ID() != 228)
					{
						amountinvoice = MConversionRate.convert(po.getCtx(),inv.getGrandTotal(), inv.getC_Currency_ID(), 228,
								inv.getDateAcct(),inv.getC_ConversionType_ID(), inv.getAD_Client_ID(), inv.getAD_Org_ID());
					}
					else
						amountinvoice = inv.getGrandTotal();
					//se buscan lineas de asignaciones para saldar monto
					try 
					{
						//se buscan lineas de asignacion de la factura 
						PreparedStatement pstmt = DB.prepareStatement(sql, null);
						pstmt.setInt(1, inv.get_ID());
						ResultSet rs = pstmt.executeQuery();
						while (rs.next())
						{
							MAllocationLine aLine = new MAllocationLine(po.getCtx(), rs.getInt("C_AllocationLine_ID"), po.get_TrxName());
							//se pregunta moneda de asignacion para convertir en caso que sea necesario
							if(aLine.getC_AllocationHdr().getC_Currency_ID() != 228)
								amountAlloLine=MConversionRate.convert(po.getCtx(),aLine.getAmount(), aLine.getC_AllocationHdr().getC_Currency_ID(), 228,
										aLine.getC_AllocationHdr().getDateAcct(),inv.getC_ConversionType_ID(), aLine.getAD_Client_ID(), aLine.getAD_Org_ID());
							else
								amountAlloLine=aLine.getAmount();
							sumAmtAllocation = amountAlloLine;
						}
						//despues de validar asignaciones comparar con monto de factura
						if(sumAmtAllocation.abs().compareTo(amountinvoice.abs()) >=0)
						{
							//se marca factura como pagada
							inv.setIsPaid(true);
							inv.saveEx(po.get_TrxName());
						}						
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}		
		if (timing == TIMING_AFTER_VOID && po.get_Table_ID()==MAllocationHdr.Table_ID)			
		{
			MAllocationHdr allo = (MAllocationHdr) po;
			MAllocationLine[] lines = allo.getLines(false);
			String sql = "SELECT C_AllocationLine_ID FROM C_AllocationLine al"
					+ " INNER JOIN C_AllocationHdr a ON (al.C_AllocationHdr_ID = a.C_AllocationHdr_ID) "
					+ " WHERE a.DocStatus IN ('CO','CL') AND al.C_Invoice_ID =?";
			BigDecimal amountinvoice = Env.ZERO;
			BigDecimal amountAlloLine = Env.ZERO;
			BigDecimal sumAmtAllocation = Env.ZERO;
			for (int i = 0; i < lines.length; i++)
			{
				amountinvoice = Env.ZERO;
				sumAmtAllocation = Env.ZERO;
				amountAlloLine = Env.ZERO;
				MAllocationLine line = lines[i];
				if(line.getC_Invoice_ID() > 0)
				{	
					MInvoice inv = new MInvoice(po.getCtx(), line.getC_Invoice_ID(),po.get_TrxName());
					if(inv.getC_Currency_ID() != 228)
					{
						amountinvoice = MConversionRate.convert(po.getCtx(),inv.getGrandTotal(), inv.getC_Currency_ID(), 228,
								inv.getDateAcct(),inv.getC_ConversionType_ID(), inv.getAD_Client_ID(), inv.getAD_Org_ID());
					}
					else
						amountinvoice = inv.getGrandTotal();
					//se buscan lineas de asignaciones para saldar monto
					try 
					{
						//se buscan lineas de asignacion de la factura 
						PreparedStatement pstmt = DB.prepareStatement(sql, null);
						pstmt.setInt(1, inv.get_ID());
						ResultSet rs = pstmt.executeQuery();
						while (rs.next())
						{
							MAllocationLine aLine = new MAllocationLine(po.getCtx(), rs.getInt("C_AllocationLine_ID"), po.get_TrxName());
							//se pregunta moneda de asignacion para convertir en caso que sea necesario
							if(aLine.getC_AllocationHdr().getC_Currency_ID() != 228)
								amountAlloLine=MConversionRate.convert(po.getCtx(),aLine.getAmount(), aLine.getC_AllocationHdr().getC_Currency_ID(), 228,
										aLine.getC_AllocationHdr().getDateAcct(),inv.getC_ConversionType_ID(), aLine.getAD_Client_ID(), aLine.getAD_Org_ID());
							else
								amountAlloLine=aLine.getAmount();
							sumAmtAllocation = amountAlloLine;
						}
						//despues de validar asignaciones comparar con monto de factura
						if(sumAmtAllocation.abs().compareTo(amountinvoice.abs()) < 0)
						{
							//se marca factura como NO pagada
							inv.setIsPaid(false);
							inv.saveEx(po.get_TrxName());
						}						
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}	
		return null;
	}	//	docValidate

	/**
	 *	User Login.
	 *	Called when preferences are set
	 *	@param AD_Org_ID org
	 *	@param AD_Role_ID role
	 *	@param AD_User_ID user
	 *	@return error message or null
	 */
	public String login (int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		log.info("AD_User_ID=" + AD_User_ID);

		return null;
	}	//	login


	/**
	 *	Get Client to be monitored
	 *	@return AD_Client_ID client
	 */
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}	//	getAD_Client_ID


	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("QSS_Validator");
		return sb.toString ();
	}	//	toString


	

}	