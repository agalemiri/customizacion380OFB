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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.model.MBPartner;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MCash;
import org.compiere.model.MCashLine;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MPayment;
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
public class ModelOFBSpecialDocs implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelOFBSpecialDocs ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelOFBSpecialDocs.class);
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	String m_processMsg;

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
		//engine.addModelChange(MInOutLine.Table_Name, this);
		//	Documents to be monitored
		engine.addDocValidate(MInvoice.Table_Name, this);

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
		if(timing == TIMING_BEFORE_COMPLETE && po.get_Table_ID()==MInvoice.Table_ID)
		{
			MInvoice inv = (MInvoice) po;
			updateSpecialDocs(inv);
		}
		if(timing == TIMING_AFTER_VOID && po.get_Table_ID()==MInvoice.Table_ID)
		{
			MInvoice inv = (MInvoice) po;
			if(inv.getDocBase().equals("PTK"))
			{
				if(isBankReverse(inv))
				{
					return "Existe un Movimiento Bancario de Reversa que debe ser Anulado Primero";
				}
			}
			voidSpecialDocs(inv);
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
	
	public void voidSpecialDocs(MInvoice inv)
	{	
		if(inv.getDocBase().equals("PTK"))
		{			
				String mysql="Update C_Payment set IsProtested='N', C_InvoiceLine_ID=null "
						+" Where C_Payment_ID IN (select C_Payment_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
				DB.executeUpdate(mysql,inv.get_TrxName());
		}
		else if(inv.getDocBase().equals("CDC") || inv.getDocBase().equals("VDC"))
		{
			    MInvoiceLine[] iLines = inv.getLines(false);
				String mysql="Update C_Payment set Custodio=null, C_InvoiceLine_ID="+iLines[0].getC_InvoiceLine_ID()
							+" Where C_Payment_ID IN (select C_Payment_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
				log.config("update cdc vdc");
				DB.executeUpdate(mysql,inv.get_TrxName());
				//actualizacion de facturas
				String mysqlIn="Update C_Invoice set IsPaid = 'N' Where C_Invoice_ID IN (select C_Invoicefac_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
				log.config("update cdc vdc");
				DB.executeUpdate(mysqlIn,inv.get_TrxName());
						    
			
		}
		else if(inv.getDocBase().equals("FAT") && inv.isSOTrx ())
		{
			MInvoiceLine[] iLines = inv.getLines(false);
			String mysql="Update C_Payment set Custodio=null, C_InvoiceLine_ID="+iLines[0].getC_InvoiceLine_ID()
					+" Where C_Payment_ID IN (select C_Payment_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
			log.config("update fac");
			DB.executeUpdate(mysql,inv.get_TrxName());
						    
		    mysql="Update C_Invoice set ISFACTORING='N', ispaid='N' "
		    		+" Where C_Invoice_ID IN (select C_InvoiceFac_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
		    log.config("update fac");
			DB.executeUpdate(mysql,inv.get_TrxName());
		}
		else if(inv.getDocBase().equals("FAT") && !inv.isSOTrx ()){
			MInvoiceLine[] iLines = inv.getLines(false);
			String mysql="Update C_Payment set Custodio='F', C_InvoiceLine_ID="+iLines[0].getC_InvoiceLine_ID()
					+" Where C_Payment_ID IN (select C_Payment_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
			log.config("update fac");
			DB.executeUpdate(mysql,inv.get_TrxName());
						    
		    mysql="Update C_Invoice set ISFACTORING='Y' "
		    		+" Where C_Invoice_ID IN (select C_InvoiceFac_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
		    log.config("update fac");
			DB.executeUpdate(mysql,inv.get_TrxName());
		}
	}

	/**faaguilar OFB
	 * deja en 0 la linea de un cashbook al anular la factura si la factura fue pagada en efectivo*/
	public boolean voidCashBook(MInvoice inv)
	{
		int cash_id =DB.getSQLValue(inv.get_TrxName(),"select c_cash_id from c_cashline where c_invoice_id="+inv.getC_Invoice_ID());
		if(cash_id>0)
		{
			MCash mycash= new MCash(inv.getCtx(),cash_id,inv.get_TrxName());
			if(mycash.getDocStatus().equals("DR"))
			{
				MCashLine[] lines =mycash.getLines(true);
				for (int i=0;i<lines.length;i++)
					if(lines[i].getC_Invoice_ID()==inv.getC_Invoice_ID())
					{
						lines[i].setAmount(Env.ZERO);
						lines[i].setDescription("**Anulada");
						lines[i].save();
					}
						
			}
			else if(mycash.getDocStatus().equals("CO"))
			{
				MCash currentCash= MCash.getDefault(inv.getCtx(), getAD_Client_ID(), mycash.getC_CashBook_ID(), inv.get_TrxName());
				if(currentCash==null)
				{
					m_processMsg = "@No existe un libro de efectivo Activo Actualmente@";
					return false;
				}
				
				MCashLine[] lines =mycash.getLines(true);
				for (int i=0;i<lines.length;i++)
					if(lines[i].getC_Invoice_ID()==inv.getC_Invoice_ID())
					{
						MCashLine newline=new MCashLine(currentCash);
						newline.setC_Invoice_ID(lines[i].getC_Invoice_ID());
						newline.setDescription("Descuento por Anulacion Factura:"+inv.getDocumentNo());
						newline.setCashType(lines[i].getCashType());
						newline.setAmount(lines[i].getAmount().negate());
						if(!newline.save())
							m_processMsg = "@No se puede descontar la Factura en la caja actual@";
					}
			}
		}
		return true;
	}
	/**
	 *  OFB
	 * actualiza correlativo en facturas de compra 
	 * -si es protesto de cheques, reversa el banco y protesta los pagos
	 * -si es factoring realiza los movimientos segun corresponda
	 * -si es cambio de documentos, deja en custodio C los documentos de pago del detalle
	 * */
	public void updateSpecialDocs(MInvoice inv)
	{
		log.config("docbase");
		String docbase=inv.getDocBase();
		// OFB Protestos, Changes , Factoring
		if(docbase.equals("PTK"))
		{
			MInvoiceLine[] iLines = inv.getLines(false);
			String mysql="Update C_Payment set IsProtested='Y', C_InvoiceLine_ID="+iLines[0].getC_InvoiceLine_ID()
					+" Where C_Payment_ID IN (select C_Payment_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
			log.config("update ptk");
			DB.executeUpdate(mysql,inv.get_TrxName());
						   
			MDocType docPtk = new MDocType(inv.getCtx(), inv.getC_DocTypeTarget_ID(), inv.get_TrxName());			
			String valuePTK = "BA";
			Boolean generateBS = true;
			try
			{
				valuePTK = docPtk.get_ValueAsString("ptkType");
				//para evitar errores leemos variable como string y luego se setea el booleano
				//generateBS = docPtk.get_ValueAsBoolean("generateBS");
				String genBSTxt = docPtk.get_ValueAsString("generateBS");
				if(genBSTxt != null && genBSTxt.compareTo("N") == 0)
					generateBS = false;
				else
					generateBS = true;
			}
			catch(Exception e)
	        {
				valuePTK = "BA";
				generateBS = true;
				log.log(Level.SEVERE, "No se pudo setear variable ptkType", e);
				log.log(Level.SEVERE, "No se pudo setear variable generateBS", e);
	        }
			
			if (valuePTK.compareToIgnoreCase("NB")==0)
			{
				String mysqlUPPtkNB="Update C_Payment set IsReconciled = 'Y' "
					+" Where C_Payment_ID IN (select C_Payment_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
				log.config("update ptk NB");
				DB.executeUpdate(mysqlUPPtkNB,inv.get_TrxName());
			}	
			else
			{
				if(generateBS == false)
				{
					String mysqlUPPtkB="Update C_Payment set IsReconciled = 'Y' "
							+" Where C_Payment_ID IN (select C_Payment_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
					log.config("update ptk Bancario");
					DB.executeUpdate(mysqlUPPtkB,inv.get_TrxName());
				}	
				else if (generateBS)
					reverseBank(inv);
				else
					reverseBank(inv);
			}		
		}
		else if(docbase.equals("CDC") || docbase.equals("VDC"))
		{
			MInvoiceLine[] iLines = inv.getLines(false);
			for(MInvoiceLine il:iLines)
			{
				if(il.get_ValueAsInt("C_Payment_ID")>0)
				{
						String mysql="Update C_Payment set Custodio='C', C_InvoiceLine_ID="+il.getC_InvoiceLine_ID()
							+" Where C_Payment_ID ="+il.get_ValueAsInt("C_Payment_ID");
						log.config("update cdc vdc");
						DB.executeUpdate(mysql,inv.get_TrxName());
				}
				if(il.get_ValueAsInt("C_InvoiceFac_ID")>0)
				{
			    		String mysql="Update C_Invoice set isPaid='Y' "
			    				+" Where C_Invoice_ID="+il.get_ValueAsInt("C_InvoiceFac_ID");
			    		log.config("update cdc vdc");
			    		DB.executeUpdate(mysql,inv.get_TrxName());
				}
			}
			
		}
		else if(docbase.equals("FAT"))
		{
			if(inv.get_ValueAsBoolean("PutInFactoring"))
			{
				MInvoiceLine[] iLines = inv.getLines(false);
				String mysql="Update C_Payment set Custodio='F', C_InvoiceLine_ID="+iLines[0].getC_InvoiceLine_ID()
						+" Where C_Payment_ID IN (select C_Payment_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
				log.config("update fac");
				DB.executeUpdate(mysql,inv.get_TrxName());
							    
			    mysql="Update C_Invoice set ISFACTORING='Y', ispaid='Y' "
			    		+" Where C_Invoice_ID IN (select C_InvoiceFac_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
			    log.config("update fac");
			    DB.executeUpdate(mysql,inv.get_TrxName());
			}
			else if(!inv.get_ValueAsBoolean("Extinguir") && !inv.get_ValueAsBoolean("PutInFactoring"))
			{
				MInvoiceLine[] iLines = inv.getLines(false);
				String mysql = "";
				String sqlCantRef = "SELECT COUNT(1) FROM AD_Ref_List WHERE AD_Reference_ID=1000006 AND value = 'D'";
				int cantRef = DB.getSQLValue(inv.get_TrxName(), sqlCantRef);
				if (cantRef > 0)
				{
					mysql="Update C_Payment set Custodio='D', C_InvoiceLine_ID="+iLines[0].getC_InvoiceLine_ID()
						+" Where C_Payment_ID IN (select C_Payment_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
					log.config("update fac");
					DB.executeUpdate(mysql,inv.get_TrxName());
				}
				else
				{
					mysql="Update C_Payment set Custodio=null, C_InvoiceLine_ID="+iLines[0].getC_InvoiceLine_ID()
							+" Where C_Payment_ID IN (select C_Payment_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
					log.config("update fac");
					DB.executeUpdate(mysql,inv.get_TrxName());
				}
							    
			    mysql="Update C_Invoice set ISFACTORING='N', ispaid='N' "
			    		+" Where C_Invoice_ID IN (select C_InvoiceFac_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
			    log.config("update fac");
			    DB.executeUpdate(mysql,inv.get_TrxName());
			}
			else if(inv.get_ValueAsBoolean("Extinguir") && !inv.get_ValueAsBoolean("PutInFactoring"))
			{
				MInvoiceLine[] iLines = inv.getLines(false);
				String mysql="Update C_Payment set Custodio='E', C_InvoiceLine_ID="+iLines[0].getC_InvoiceLine_ID()
						+" Where C_Payment_ID IN (select C_Payment_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
				log.config("update fac");
				DB.executeUpdate(mysql,inv.get_TrxName());
							    
			    mysql="Update C_Invoice set Extinta='Y' "
			    		+" Where C_Invoice_ID IN (select C_InvoiceFac_ID from C_InvoiceLine where C_Invoice_ID="+inv.getC_Invoice_ID()+")";
			    log.config("update fac");
			    DB.executeUpdate(mysql,inv.get_TrxName());
							    
				mysql="Update C_Invoice set Extinta='Y' "
						+" Where C_Invoice_ID="+inv.getC_Invoice_ID();
				log.config("update fac2");
				DB.executeUpdate(mysql,inv.get_TrxName());
								    
				for(int i=0;i<iLines.length;i++)
				{
					if(iLines[i].get_ValueAsInt("C_InvoiceFac_ID")!=0)
					{
						MInvoice inv2=new MInvoice(inv.getCtx(),iLines[i].get_ValueAsInt("C_InvoiceFac_ID"),inv.get_TrxName());
						MBPartner bp = new MBPartner (inv.getCtx(), inv2.getC_BPartner_ID(), inv.get_TrxName());
						bp.setTotalOpenBalance();
						bp.save();
					}
				}
			}
		}
	}
	/**
	 * faaguilar OFB
	 * si la invoice es un documento de protesto
	 * se reversan los movimientos bancarios relacionados con los pagos
	 * protestados
	 * */
	public boolean reverseBank(MInvoice inv)
	{
		if (inv.getDocBase().equals("PTK")==false)
			return true;
		boolean result=false;
		String mysql="select p.c_payment_id,p.c_bankaccount_id,p.payamt "+
				"from C_payment p inner join C_Invoiceline il on (p.C_Payment_ID=il.C_Payment_ID) "+
				" and il.C_Invoice_ID=? order by p.c_bankaccount_id";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(mysql, inv.get_TrxName());
			pstmt.setInt(1,inv.getC_Invoice_ID());
			ResultSet rs = pstmt.executeQuery();
			MBankStatement bankstmt=null;
			int currentaccount=0;
			while (rs.next())
			{	
				if(currentaccount!=rs.getInt(2))
				{
					currentaccount=rs.getInt(2);
					if(bankstmt!=null)
					{	
						bankstmt.setDocAction("CO");
						bankstmt.processIt ("CO");
						bankstmt.save();
					}
					bankstmt=new MBankStatement(inv.getCtx(),0,inv.get_TrxName());
					bankstmt.setC_BankAccount_ID(rs.getInt(2));
					bankstmt.setName("Reverso por Protesto de Documento:"+ inv.getDocumentNo());
					bankstmt.setDescription("Reverso Protesto");
					bankstmt.set_CustomColumn("C_Invoice_ID", inv.get_ID());
					bankstmt.setAD_Org_ID(inv.getAD_Org_ID());
					bankstmt.save();					
				}
				MBankStatementLine line=new MBankStatementLine(bankstmt);
				line.setPayment(new MPayment(inv.getCtx(),rs.getInt(1),inv.get_TrxName()));
				line.setTrxAmt(line.getTrxAmt().negate());
				line.setStmtAmt(line.getStmtAmt().negate());				
				line.setAD_Org_ID(inv.getAD_Org_ID());
				line.save();
			}
			if(currentaccount!=0)
			{
				bankstmt.setDocAction("CO");
				bankstmt.processIt ("CO");
				bankstmt.save();
			}
			result=true;
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			//s_log.log(Level.SEVERE, mysql, e);
			return false;
		}	
		return result;
	}
	/**OFB
	 * busca si el documento protesto posee movimientos bancarios de reversa*/
	public boolean isBankReverse(MInvoice inv)
	{
		int banks=DB.getSQLValue(inv.get_TrxName(),"select count(1) from c_bankstatement where docstatus<>'VO'"
		+ " and c_invoice_id=" + inv.getC_Invoice_ID());
		
		if(banks>0)
			return true;
		else
			return false;
	}
		
}	