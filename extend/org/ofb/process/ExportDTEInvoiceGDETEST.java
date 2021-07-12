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
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.compiere.util.*;
import org.compiere.model.*;
import org.compiere.process.SvrProcess;

import com.liquid_technologies.ltxmllib12.exceptions.LtException;



/**
 *	Generate XML Consolidated from Invoice (Generic) 
 *	
 *  @author mfrojas
 *  @version $Id: ExportDTEInvoiceCG.java,v 1.2 19/05/2011 $
 */
public class ExportDTEInvoiceGDETEST extends SvrProcess
{	
	/** Properties						*/
	private Properties 		m_ctx;	
	private int p_C_Invoice_ID = 0;
	public String urlPdf = "";
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		p_C_Invoice_ID=getRecord_ID();
		m_ctx = Env.getCtx();
	}	//	prepare

	
	/**
	 * 	Create Shipment
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		MInvoice inv=new MInvoice(m_ctx,p_C_Invoice_ID,get_TrxName());
		String msg = "";
		msg=CreateXMLCG(inv);		
		return msg;
	}	//	doIt
	
	public String CreateXMLCG(MInvoice invoice)
    {
		String error ="";
		String ted = "";
		String pdf417 = "";
		try 
		{		
			DocumentoDTE.SiiDte.DTEDefType dte = new DocumentoDTE.SiiDte.DTEDefType();
			dte.setDTE_Choice(new DocumentoDTE.SiiDte.DTE_Choice());
			MOrg company = MOrg.get(invoice.getCtx(), invoice.getAD_Org_ID());
	
			//Documento
			dte.getDTE_Choice().setDocumento(new DocumentoDTE.SiiDte.Documento());
			DocumentoDTE.SiiDte.Documento doc = dte.getDTE_Choice().getDocumento();
	
			//Documento/Encabezado
			doc.setEncabezado(new DocumentoDTE.SiiDte.Encabezado());
	
			//Documento/Encabezado/IdDoc
			DocumentoDTE.SiiDte.IdDoc idDoc = new DocumentoDTE.SiiDte.IdDoc();
			idDoc.setTipoDTE(DocumentoDTE.SiiDte.DTEType.n33);
			idDoc.setFolio(BigInteger.valueOf(Integer.parseInt(invoice.getDocumentNo())));
			//idDoc.setFolio(BigInteger.valueOf(2255));
			Timestamp fchemis = invoice.getDateInvoiced();
			idDoc.setFchEmis(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date,ConverDateToString(invoice.getDateInvoiced())));
			String dateReturn = "";
			//dateReturn = (fchemis.getYear()+1900)+"-"+(fchemis.getMonth()+1)+"-"+fchemis.getDate();
			dateReturn = ConverDateToString(fchemis);
			//idDoc.setFchEmis(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date,"2010-01-01"));
            idDoc.setFmaPago(DocumentoDTE.SiiDte.IdDoc_FmaPago.n1);
            doc.getEncabezado().setIdDoc(idDoc);

			//idDoc.setFchEmis(fchemis);
			//ininoles calculo de fecha de vencimiento
	        int cantdias = 0;
	        Timestamp fchVencCal = DB.getSQLValueTS(invoice.get_TrxName(), "SELECT MAX(duedate) FROM C_Invoice_V WHERE C_Invoice_ID = ?",invoice.get_ID());
	        //primero calculamos los dias a sumar
	        if (fchVencCal == null && invoice.getC_PaymentTerm_ID() > 0)
	        {
	            MPaymentTerm tpago = new MPaymentTerm(invoice.getCtx(), invoice.getC_PaymentTerm_ID(), invoice.get_TrxName());
	            int cantDet = DB.getSQLValue(invoice.get_TrxName(), "SELECT MIN(NetDays) FROM C_PaySchedule WHERE C_PaymentTerm_ID = "+invoice.getC_PaymentTerm_ID());
	            if(tpago.getNetDays() > 0)
	            	cantdias = tpago.getNetDays();
	            else if (cantDet > 0)
	            	cantdias = cantDet;
	            else
	            	cantdias = 0;             
	            if (cantdias > 0)
	            {
	            	Calendar calFchVenc = Calendar.getInstance();
	                calFchVenc.setTimeInMillis(invoice.getDateInvoiced().getTime());     
	            	calFchVenc.add(Calendar.DATE, cantdias);
	            	fchVencCal = new Timestamp(calFchVenc.getTimeInMillis());
	            }   
	        }     
			idDoc.setFchVenc(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date,ConverDateToString(fchVencCal)));
	       
			doc.getEncabezado().setIdDoc(idDoc);

			//Documento/Encabezado/IdDoc/MntPagos
			DocumentoDTE.SiiDte.MntPagos mntPago = new DocumentoDTE.SiiDte.MntPagos();
			mntPago.setFchPago(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date, ConverDateToString(invoice.getDateInvoiced())));
			//mntPago.setFchPago(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date, "2010-01-01"));
			mntPago.setMntPago(invoice.getGrandTotal().toBigInteger());
			//mntPago.setMntPago(BigInteger.valueOf(12345));
			//ininoles termino de pago y regla de pago 
	        String GlosaP = DB.getSQLValueString(invoice.get_TrxName(), "SELECT MAX(rlt.name) " +
	        		" FROM AD_Ref_List rl " +
	        		" INNER JOIN AD_Ref_List_Trl rlt ON (rl.AD_Ref_List_ID = rlt.AD_Ref_List_ID) " +
	        		" WHERE AD_Reference_ID=195 " +
	        		" AND rlt.AD_Language='es_MX' AND rl.value = '"+invoice.getPaymentRule()+"'");
	        String GlosaPFull = invoice.getC_PaymentTerm().getName();
	        if(GlosaP != null && GlosaP.trim().length() > 0)
	        	GlosaPFull = GlosaPFull+"-"+GlosaP;
			mntPago.setGlosaPagos(GlosaPFull);
			//mntPago.setGlosaPagos("Glosa de pagos");
			idDoc.getMntPagos().add(mntPago);
			doc.getEncabezado().setIdDoc(idDoc);
            
			//Documento/Encabezado/Emisor
			DocumentoDTE.SiiDte.Emisor emisor = new DocumentoDTE.SiiDte.Emisor();
			//emisor.setRUTEmisor((String)company.get_Value("Rut")); para las pruebas se emite con rut gde
			emisor.setRUTEmisor("76129486-5");
			String nameRzn = company.getDescription();
	        if (nameRzn == null)
	        {
	        	nameRzn = " ";
	        }
	        nameRzn = nameRzn.trim();
	        if (nameRzn.length() < 2)
	        	nameRzn = company.getName();
			emisor.setRznSoc(nameRzn);
			//emisor.setRznSoc("Razon social");
			String giroEmisStr = (String)company.get_Value("Giro");
	        giroEmisStr = giroEmisStr.replace("'", "");
	        giroEmisStr = giroEmisStr.replace("\"", "");
	        emisor.setGiroEmis(giroEmisStr);
	        //emisor.setGiroEmis("Giro Emisor");
			emisor.getActeco().add(BigInteger.valueOf(Integer.parseInt((String)company.get_Value("Acteco"))));;
			//emisor.getActeco().add(BigInteger.valueOf(1234));
			emisor.setDirOrigen((String)company.get_Value("Address1"));
			emisor.setCmnaOrigen((String)company.get_Value("Comuna"));
			emisor.setCiudadOrigen((String)company.get_Value("City"));
			if (invoice.getSalesRep_ID() > 0)
				emisor.setCdgVendedor(invoice.getSalesRep().getName());
			doc.getEncabezado().setEmisor(emisor);
	
			//Documento/Encabezado/Receptor
			MBPartner BP = new MBPartner(invoice.getCtx(), invoice.getC_BPartner_ID(), invoice.get_TrxName());
	        MBPartnerLocation bloc = new MBPartnerLocation(invoice.getCtx(), invoice.getC_BPartner_Location_ID(), invoice.get_TrxName());
			DocumentoDTE.SiiDte.Receptor receptor = new DocumentoDTE.SiiDte.Receptor();
			receptor.setRUTRecep((new StringBuilder()).append(BP.getValue()).append("-").append(BP.get_ValueAsString("Digito")).toString());
			//receptor.setRUTRecep("11111111-1");
			String RznSocRecepStr = BP.getName();
	        RznSocRecepStr = RznSocRecepStr.replace("'", "");
	        RznSocRecepStr = RznSocRecepStr.replace("\"", "");
			receptor.setRznSocRecep(RznSocRecepStr);

			String dirRecepStr = bloc.get_ValueAsString("Address1");
	        dirRecepStr = dirRecepStr.replace("'", "");
	        dirRecepStr = dirRecepStr.replace("\"", "");
			receptor.setDirRecep(dirRecepStr);
			
	        //receptor.setDirRecep("direccion");
			String CmnaRecepStr = null;
	        if(bloc.get_ValueAsInt("C_City_ID") > 0)
	        {
	        	MCity dCity = new MCity(invoice.getCtx(),bloc.get_ValueAsInt("C_City_ID"), invoice.get_TrxName());
	            CmnaRecepStr = dCity.getName();
	        }		
			receptor.setCmnaRecep(CmnaRecepStr);
	        
			String ciudadTxt = "";       
	        if(bloc.get_ValueAsInt("C_Province_ID") > 0)
	        {
		        X_C_Province prov = new X_C_Province(invoice.getCtx(), bloc.get_ValueAsInt("C_Province_ID"), invoice.get_TrxName());
		        ciudadTxt = prov.getName();
	        }		
			receptor.setCiudadRecep(ciudadTxt != null?ciudadTxt:"Santiago");
	        //receptor.setCiudadRecep("Ciudad");
			receptor.setGiroRecep((String)BP.get_Value("Giro"));
	        //receptor.setGiroRecep("Giro");

			doc.getEncabezado().setReceptor(receptor);
			
			//Documento/Encabezado/Totales
			BigDecimal amountex = DB.getSQLValueBD(invoice.get_TrxName(), (new StringBuilder()).append("select Round(COALESCE(SUM(il.LineNetAmt),0),0) from C_InvoiceLine il  inner join C_Tax t on (il.C_Tax_ID=t.C_Tax_ID) and t.istaxexempt='Y' and il.C_Invoice_ID=").append(invoice.getC_Invoice_ID()).toString());
	        BigDecimal amountNeto = DB.getSQLValueBD(invoice.get_TrxName(), (new StringBuilder()).append("select Round(COALESCE(SUM(il.LineNetAmt),0),0) from C_InvoiceLine il  inner join C_Tax t on (il.C_Tax_ID=t.C_Tax_ID) and t.istaxexempt='N' and il.C_Invoice_ID=").append(invoice.getC_Invoice_ID()).toString());
			DocumentoDTE.SiiDte.Totales totales = new DocumentoDTE.SiiDte.Totales();
			totales.setMntTotal(invoice.getGrandTotal().toBigInteger());

			totales.setMntNeto(amountNeto.toBigInteger());
			totales.setMntExe(amountex.toBigInteger());

			if(amountNeto.signum()>0)
				totales.setTasaIVA(BigDecimal.valueOf(19));
			BigDecimal ivaamt= Env.ZERO;
	        if(amountex.intValue()!=invoice.getGrandTotal().intValue())
	        	ivaamt=invoice.getGrandTotal().subtract(invoice.getTotalLines()).setScale(0, 4);
			totales.setIVA(ivaamt.toBigInteger());
			doc.getEncabezado().setTotales(totales);
	
			
			//Documento/Detalle

	        MInvoiceLine iLines[] = invoice.getLines(false);
			int lineInvoice = 0;
	        int lineDiscount = 0;
	        for(int i = 0; i < iLines.length; i++)
	        {	
	        	MInvoiceLine iLine = iLines[i];
	        	if(iLine.getM_Product_ID()==0 && iLine.getC_Charge_ID()==0)
	        		continue;
	        	if(iLine.getTaxAmt().compareTo(Env.ZERO) >=0)
	        	{
	        		lineInvoice = lineInvoice+1;   
	        		DocumentoDTE.SiiDte.Detalle det = new DocumentoDTE.SiiDte.Detalle();
					det.setNroLinDet(BigInteger.valueOf(lineInvoice));
					String pname="";
	                if(iLine.getProduct()!=null )
	                {
	                	pname=iLine.getProduct().getValue()+"-"+iLine.getProduct().getName();
	                	if(iLine.getM_Product().getUPC() != null && iLine.getM_Product().getUPC().trim().length() > 0)
	                		pname = pname+iLine.getM_Product().getUPC();
	                }
	                else
	                	pname=iLine.getC_Charge().getName();
	                
	                pname = pname.replace("'", "");
	                pname = pname.replace("\"", "");
					det.setNmbItem(pname);
					det.setQtyItem(iLine.getQtyInvoiced());
					//det.setQtyItem(BigDecimal.valueOf(10));
					det.setPrcItem(iLine.getPriceActual().setScale(0, 4));
					//det.setPrcItem(BigDecimal.valueOf(5000));
					det.setMontoItem(iLine.getLineNetAmt().setScale(0, 4).toBigInteger());
					//det.setMontoItem(BigInteger.valueOf(5000));
					if (iLine.getDescription() != null && iLine.getDescription() != "" && invoice.getC_DocType().getDocBaseType().compareTo("ARC") != 0)
						det.setDscItem(iLine.getDescription()==null?" ":iLine.getDescription());
					String unmdItemStr = "";
	                if(iLine.getM_Product_ID() > 0)
	                	unmdItemStr = iLine.getM_Product().getC_UOM().getUOMSymbol();
	                else
	                	unmdItemStr = "UN";                
	                if (unmdItemStr == null)
	                	unmdItemStr = "UN";  
					det.setUnmdItem(unmdItemStr);
					doc.getDetalle().add(det);
	        	}
	        	else
	        	{
	        		//Documento/DscRcgGlobal
	        		lineDiscount = lineDiscount+1;
	        		DocumentoDTE.SiiDte.DscRcgGlobal dscRcgGlobal = new DocumentoDTE.SiiDte.DscRcgGlobal();
	        		dscRcgGlobal.setNroLinDR(BigInteger.valueOf(lineDiscount));
	        		dscRcgGlobal.setTpoMov(DocumentoDTE.SiiDte.DscRcgGlobal_TpoMov.D);
	        		dscRcgGlobal.setTpoValor(DocumentoDTE.SiiDte.DineroPorcentajeType.Dollar);
	        		dscRcgGlobal.setValorDR(iLine.getLineNetAmt().abs().setScale(0, 4));
	        		String pname="";
		            if(iLine.getProduct()!=null )
		            {
		            	pname=iLine.getProduct().getValue()+"-"+iLine.getProduct().getName();
		            	if(iLine.getM_Product().getUPC() != null && iLine.getM_Product().getUPC().trim().length() > 0)
		            		pname = pname+iLine.getM_Product().getUPC();
	            	}
		            else
		            	pname=iLine.getC_Charge().getName();
		                
	                pname = pname.replace("'", "");
	                pname = pname.replace("\"", "");
	        		dscRcgGlobal.setGlosaDR(pname);
	        		doc.getDscRcgGlobal().add(dscRcgGlobal);
	        	}
	        }
	        
        


			
	        String tiporeferencia = new String();
	        String folioreferencia  = new String();
	        String fechareferencia = new String();
	        int tipo_Ref =0;
	        
	        if(invoice.get_ValueAsInt("C_RefDoc_ID") > 0)//referencia factura
	        {
	            MInvoice refdoc = new MInvoice(invoice.getCtx(), ((Integer)invoice.get_Value("C_RefDoc_ID")).intValue(), invoice.get_TrxName());
	            MDocType Refdoctype = new MDocType(invoice.getCtx(), refdoc.getC_DocType_ID(), invoice.get_TrxName());
	            tiporeferencia = (String) Refdoctype.get_Value("DocumentNo");
	            folioreferencia = (String) refdoc.getDocumentNo();
	            fechareferencia = ConverDateToString(refdoc.getDateInvoiced());
	            tipo_Ref = 1; //factura
	        } 
	        //la referencia orden solo se usara si no es nota de credito
	        if(invoice.getPOReference() != null && invoice.getPOReference().length() > 0 && invoice.getC_DocType().getDocBaseType().compareTo("ARC") != 0)//referencia orden
	        {
	        	 //MOrder refdoc = new MOrder(invoice.getCtx(), ((Integer)get_Value("C_RefOrder_ID")).intValue(), invoice.get_TrxName()); 
	        	 tiporeferencia = "801";
	             folioreferencia = invoice.getPOReference();
	             fechareferencia = ConverDateToString(invoice.getDateOrdered());
	        	 tipo_Ref = 2; //Orden
	        }        
	        if(invoice.get_Value("C_RefInOut_ID") != null && ((Integer)invoice.get_Value("C_RefInOut_ID")).intValue() > 0)//referencia despacho
	        {
	        	 MInOut refdoc = new MInOut(invoice.getCtx(), ((Integer)invoice.get_Value("C_RefInOut_ID")).intValue(), invoice.get_TrxName()); 
	        	 tiporeferencia = "52";
	             folioreferencia = (String) refdoc.getDocumentNo();
	             fechareferencia = ConverDateToString(refdoc.getMovementDate());
	        	 tipo_Ref = 3; //despacho
	        }
	        int indice = 0;
	        if(tipo_Ref>0)
	        {        
	        	
	            indice = indice+1;        	
				//Documento/Referencia
				DocumentoDTE.SiiDte.Referencia reference = new DocumentoDTE.SiiDte.Referencia();
				try {
					reference.setNroLinRef(BigInteger.valueOf(1));
					reference.setTpoDocRef(tiporeferencia); //1-3 char
					reference.setFolioRef(folioreferencia);
					reference.setFchRef(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date,fechareferencia));
					reference.setCodRef(DocumentoDTE.SiiDte.Referencia_CodRef.n1);
					doc.getReferencia().add(reference);
				} catch (LtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
	        }
	
			// Llamada al servicio
			String ambiente = dteboxcliente.Ambiente.Homologacion;
			//String fechaResolucion = "2010-01-01";
			String fechaResolucion = "2010-01-01";
			//int numeroResolucion = 10;
			int numeroResolucion = 10;
			int tipoPdf417 = dteboxcliente.TipoPDF417.Fuente;
			        
			String apiURL = "http://200.6.99.206/api/Core.svc/core";
			String apiAuth = "2a84137d-a654-4f46-b873-2be446dadba7";
			        
			dteboxcliente.Servicio servicio = new dteboxcliente.Servicio(apiURL, apiAuth);
			dteboxcliente.ResultadoEnvioDocumento resultado = servicio.EnviarDocumento(dte, ambiente, fechaResolucion, numeroResolucion, tipoPdf417);
			dte.toXml();
			log.config("dte "+dte.toXml());
			//Procesar resultado
			log.config("resultado "+resultado.getResultadoServicio().getEstado());
			
			if (resultado.getResultadoServicio().getEstado() == 0){
			            
			    ted = resultado.getTED();
			    pdf417 = resultado.getPDF417();
			            
			}
			else
			{			            
			    error = resultado.getResultadoServicio().getDescripcion();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
        //return "XML CG Generated "+ted+" "+pdf417+" "+error;
		return error;
    }        
	public String ConverDateToString(Timestamp fecha)
    {
		String dateReturn = "";
		log.config("month"+fecha.getMonth()+1);
		log.config("day"+fecha.getDate());
		int month = fecha.getMonth()+1;
		int day = fecha.getDate();
		if(fecha.getMonth()+1 < 10 && fecha.getDate()<10)
			dateReturn = (fecha.getYear()+1900)+"-0"+(fecha.getMonth()+1)+"-0"+fecha.getDate();
		else if(fecha.getMonth()+1 < 10 && fecha.getDate()>=10)
			dateReturn = (fecha.getYear()+1900)+"-0"+(fecha.getMonth()+1)+"-"+fecha.getDate();
		else if(fecha.getMonth()+1 >= 10 && fecha.getDate()<10)
			dateReturn = (fecha.getYear()+1900)+"-"+(fecha.getMonth()+1)+"-0"+fecha.getDate();
		else
			dateReturn = (fecha.getYear()+1900)+"-"+(fecha.getMonth()+1)+"-"+fecha.getDate();
		return dateReturn;
    }
	
}	//	InvoiceCreateInOut
