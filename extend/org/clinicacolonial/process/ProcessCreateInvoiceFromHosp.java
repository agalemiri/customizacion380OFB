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

import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.X_CC_Hospitalization;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.X_MED_LabDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.adempiere.exceptions.AdempiereException;

/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessCreateInvoiceFromHosp.java $
 */

public class ProcessCreateInvoiceFromHosp extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_Hospitalization_ID = 0; 
	private String				p_Action = "PR";
	private int 			p_DocType_ID = 0;
	private int 			p_Tax_ID = 0;
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if (name.equals("C_DocType_ID"))
				p_DocType_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Tax_ID"))
				p_Tax_ID = para[i].getParameterAsInt();
			
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_Hospitalization_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_Hospitalization_ID > 0)
		{
			//Si existe hospitalizacion, se debe generar la boleta.
			X_CC_Hospitalization hosp = new X_CC_Hospitalization(getCtx(), p_Hospitalization_ID, get_TrxName());
			//seteo de nuevo estado al procesar
			
			log.config("processd"+hosp.isProcessed());
			
			//validate address
			String sqlvalidateaddress = "SELECT coalesce(max(c_bpartner_location_id),0) from c_bpartner_location where c_bpartner_id = "+hosp.getC_BPartner_ID();
			int add = DB.getSQLValue(get_TrxName(), sqlvalidateaddress);
			if(add == 0)
				throw new AdempiereException("El paciente no tiene direccion asociada");
			if(hosp.get_ValueAsInt("C_Invoice_ID") > 0)
				throw new AdempiereException("Ya existe una boleta asociada");
			MInvoice inv = new MInvoice(getCtx(), 0, get_TrxName());
			inv.setC_DocTypeTarget_ID(p_DocType_ID);
			inv.setC_BPartner_ID(hosp.getC_BPartner_ID());
			inv.setDateInvoiced(hosp.getUpdated());
			inv.setDateAcct(hosp.getUpdated());
			inv.save();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sqlgetlabdetail = "SELECT med_labdetail_id from med_labdetail where cc_hospitalization_id = "+p_Hospitalization_ID;
			
			pstmt = DB.prepareStatement(sqlgetlabdetail, get_TrxName());
			rs = pstmt.executeQuery();
			X_MED_LabDetail lab = null;

			while(rs.next())
			{
				
				lab = new X_MED_LabDetail(getCtx(), rs.getInt(1), get_TrxName());
				if(lab.getQty().compareTo(Env.ZERO) <= 0)
					continue;
				MInvoiceLine invl = new MInvoiceLine(getCtx(), 0, get_TrxName());
				invl.setC_Invoice_ID(inv.get_ID());
				if(lab.getM_Product_ID() > 0)
					invl.setM_Product_ID(lab.getM_Product_ID());
				else
					invl.setC_Charge_ID(2000000);
				invl.setQtyEntered(lab.getQty());
				invl.setPriceActual(lab.getAmount().divide(lab.getQty()));
				invl.setPriceEntered(lab.getAmount().divide(lab.getQty()));
				invl.setQtyInvoiced(lab.getQty());
				invl.setLineNetAmt(lab.getAmount());
				
				if(p_Tax_ID > 0)
					invl.setC_Tax_ID(p_Tax_ID);
				invl.save();
				
				
			}
			
			hosp.set_CustomColumn("C_Invoice_ID", inv.get_ID());
			hosp.save();
			return "Procesado: Nro de Documento "+inv.getDocumentNo();
			
		}	
	   return "Procesado con errores";
	}	//	doIt
}
