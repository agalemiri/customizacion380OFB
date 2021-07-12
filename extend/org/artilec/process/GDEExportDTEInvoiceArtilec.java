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
package org.artilec.process;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.compiere.util.*;
import org.ofb.model.OFBForward;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.compiere.model.*;
import org.compiere.process.SvrProcess;

import com.liquid_technologies.ltxmllib12.exceptions.LtException;



/**
 *	Generate XML Consolidated from Invoice (Generic) 
 *	
 *  @author mfrojas
 *  @version $Id: ExportDTEInvoiceCG.java,v 1.2 19/05/2011 $
 */
public class GDEExportDTEInvoiceArtilec extends SvrProcess
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
		MDocType docTypeCab = new MDocType(inv.getCtx(), inv.getC_DocTypeTarget_ID(), inv.get_TrxName());
		if(docTypeCab.get_ValueAsString("DocumentNo").compareTo("39") == 0
				||  docTypeCab.get_ValueAsString("DocumentNo").compareTo("41") == 0)
		{
			msg=CreateXMLBoleta(inv);
			//enviarBoletaArtilec(inv);
		}
		else
			msg=CreateXML(inv);
		//se agrega delay de 4 segundos
		Thread.sleep(5000);
		commitEx();
		msg = msg + GetPDF(inv);
		//se busca pdf
		
		return msg;
	}	//	doIt
	
	public String CreateXML(MInvoice invoice)
    {
		String error ="";
		String ted = "";
		String pdf417 = "";
		try 
		{		
			DocumentoDTE.SiiDte.DTEDefType dte = new DocumentoDTE.SiiDte.DTEDefType();
			dte.setDTE_Choice(new DocumentoDTE.SiiDte.DTE_Choice());
			MOrg org = MOrg.get(invoice.getCtx(), invoice.getAD_Org_ID());
			MDocType docType = new MDocType(invoice.getCtx(), invoice.getC_DocTypeTarget_ID(), invoice.get_TrxName());
			
			//Documento
			dte.getDTE_Choice().setDocumento(new DocumentoDTE.SiiDte.Documento());
			DocumentoDTE.SiiDte.Documento doc = dte.getDTE_Choice().getDocumento();
	
			//Documento/Encabezado
			doc.setEncabezado(new DocumentoDTE.SiiDte.Encabezado());
	
			//Documento/Encabezado/IdDoc
			DocumentoDTE.SiiDte.IdDoc idDoc = new DocumentoDTE.SiiDte.IdDoc();
			if(docType.get_ValueAsString("DocumentNo").compareTo("33") == 0)
				idDoc.setTipoDTE(DocumentoDTE.SiiDte.DTEType.n33);
			else if(docType.get_ValueAsString("DocumentNo").compareTo("34") == 0)
				idDoc.setTipoDTE(DocumentoDTE.SiiDte.DTEType.n34);
			else if(docType.get_ValueAsString("DocumentNo").compareTo("56") == 0)
				idDoc.setTipoDTE(DocumentoDTE.SiiDte.DTEType.n56);
			else if(docType.get_ValueAsString("DocumentNo").compareTo("61") == 0)
				idDoc.setTipoDTE(DocumentoDTE.SiiDte.DTEType.n61);
			else
				idDoc.setTipoDTE(DocumentoDTE.SiiDte.DTEType.n33);
			idDoc.setFolio(BigInteger.valueOf(Integer.parseInt(invoice.getDocumentNo())));
			//idDoc.setFolio(BigInteger.valueOf(2255));
			//Timestamp fchemis = invoice.getDateInvoiced();
			idDoc.setFchEmis(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date,ConverDateToString(invoice.getDateInvoiced())));
			//String dateReturn = "";
			//dateReturn = (fchemis.getYear()+1900)+"-"+(fchemis.getMonth()+1)+"-"+fchemis.getDate();
			//dateReturn = ConverDateToString(fchemis);
			//idDoc.setFchEmis(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date,"2010-01-01"));
            if(invoice.getPaymentRule().compareToIgnoreCase("M")==0)
            	idDoc.setFmaPago(DocumentoDTE.SiiDte.IdDoc_FmaPago.n1);
            else
            	idDoc.setFmaPago(DocumentoDTE.SiiDte.IdDoc_FmaPago.n2);
            
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
	        //idDoc.setFmaPago;
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
            idDoc.setTermPagoGlosa(GlosaPFull);
			idDoc.getMntPagos().add(mntPago);
			doc.getEncabezado().setIdDoc(idDoc);
            
			//Documento/Encabezado/Emisor
			DocumentoDTE.SiiDte.Emisor emisor = new DocumentoDTE.SiiDte.Emisor();
			emisor.setRUTEmisor((String)org.get_Value("Rut")); 
			String nameRzn = org.getDescription();
	        if (nameRzn == null)
	        {
	        	nameRzn = " ";
	        }
	        nameRzn = nameRzn.trim();
	        if (nameRzn.length() < 2)
	        	nameRzn = org.getName();
			emisor.setRznSoc(nameRzn);
			//emisor.setRznSoc("Razon social");
			String giroEmisStr = (String)org.get_Value("Giro");
	        giroEmisStr = giroEmisStr.replace("'", "");
	        giroEmisStr = giroEmisStr.replace("\"", "");
	        emisor.setGiroEmis(giroEmisStr);
	        //emisor.setGiroEmis("Giro Emisor");
			emisor.getActeco().add(BigInteger.valueOf(Integer.parseInt((String)org.get_Value("Acteco"))));;
			//emisor.getActeco().add(BigInteger.valueOf(1234));
			emisor.setDirOrigen((String)org.get_Value("Address1"));
			emisor.setCmnaOrigen((String)org.get_Value("Comuna"));
			emisor.setCiudadOrigen((String)org.get_Value("City"));
			if (invoice.getSalesRep_ID() > 0)
				emisor.setCdgVendedor(invoice.getSalesRep().getName());
			doc.getEncabezado().setEmisor(emisor);
	
			//Documento/Encabezado/Receptor
			MBPartner BP = new MBPartner(invoice.getCtx(), invoice.getC_BPartner_ID(), invoice.get_TrxName());
	        MBPartnerLocation bloc = new MBPartnerLocation(invoice.getCtx(), invoice.getC_BPartner_Location_ID(), invoice.get_TrxName());
			DocumentoDTE.SiiDte.Receptor receptor = new DocumentoDTE.SiiDte.Receptor();
			receptor.setRUTRecep((new StringBuilder()).append(BP.getValue()).append("-").append(BP.get_ValueAsString("Digito")).toString());
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
			receptor.setGiroRecep((String)BP.get_Value("Giro"));
			//contacto
			String strContacto = bloc.get_ValueAsString("Phone");       
            dirRecepStr = dirRecepStr.replace("'", "");
            dirRecepStr = dirRecepStr.replace("\"", "");
			receptor.setContacto(strContacto);			
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
	        		
	        		//codigo del item
	        		DocumentoDTE.SiiDte.CdgItem cdgItem = new DocumentoDTE.SiiDte.CdgItem();
	        		cdgItem.setTpoCodigo("INT1");
	        		cdgItem.setVlrCodigo(iLine.getProduct().getValue());
	        		det.getCdgItem().add(cdgItem);
	        		
					det.setNroLinDet(BigInteger.valueOf(lineInvoice));
					//det.
					String pname="";
	                if(iLine.getProduct()!=null )
	                {
	                	pname=iLine.getProduct().getName();
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
	        if(invoice.getPOReference() != null && invoice.getPOReference().length() > 0 && invoice.getC_DocType().getDocBaseType().compareTo("ARC") != 0
	        		&& invoice.getPOReference().compareTo("0") != 0)//referencia orden
	        {
	        	 //MOrder refdoc = new MOrder(invoice.getCtx(), ((Integer)get_Value("C_RefOrder_ID")).intValue(), invoice.get_TrxName()); 
	        	 tiporeferencia = "801";
	             folioreferencia = invoice.getPOReference();
	             if(invoice.getDateOrdered() != null)
	            	 fechareferencia = ConverDateToString(invoice.getDateOrdered());
	             else
	            	 fechareferencia = ConverDateToString(invoice.getDateInvoiced());
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
					reference.setNroLinRef(BigInteger.valueOf(indice));
					reference.setTpoDocRef(tiporeferencia); //1-3 char
					reference.setFolioRef(folioreferencia);
					reference.setFchRef(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date,fechareferencia));
					
					reference.setCodRef(DocumentoDTE.SiiDte.Referencia_CodRef.n1);
					String razonRefTxt = invoice.get_ValueAsString("RazonRef");
	                if(razonRefTxt != null && razonRefTxt.trim().length() > 1)
	                {
	                	reference.setRazonRef(razonRefTxt);
	                }
					doc.getReferencia().add(reference);
				} catch (LtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
	        }
	
	        int ID_Ship = 0;
            try            	
            {
            	if(invoice.getC_Order_ID() > 0)
            	{
            		ID_Ship = DB.getSQLValue(invoice.get_TrxName(), "SELECT COALESCE((MAX(M_InOut_ID)),0) " +
            				"FROM M_InOut mi INNER JOIN C_Order co ON (mi.C_Order_ID = co.C_Order_ID) " +
            				"WHERE mi.docstatus IN ('CO','CL','VO') AND mi.C_Order_ID = "+invoice.getC_Order_ID());
            	}
            }catch (Exception e)
    		{
            	ID_Ship = 0;
    			log.log(Level.SEVERE, e.getMessage(), e);
    		}
            if(ID_Ship>0)
            {
            	indice = indice+1;
            	String  docRef = "52";            	
            	MInOut inOutref = new MInOut(invoice.getCtx(), ID_Ship, invoice.get_TrxName());
            	MDocType docTShip = new MDocType(invoice.getCtx(), inOutref.getC_DocType_ID(), invoice.get_TrxName());
            	if(docTShip.get_ValueAsBoolean("CreateXML"))
            		docRef = "52";
            	else
            		docRef = "52";
            	//Documento/Referencia
				DocumentoDTE.SiiDte.Referencia reference2 = new DocumentoDTE.SiiDte.Referencia();
            	try {
					reference2.setNroLinRef(BigInteger.valueOf(indice));
					reference2.setTpoDocRef(docRef); //1-3 char
					reference2.setFolioRef(inOutref.getDocumentNo());
					reference2.setFchRef(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date,ConverDateToString(inOutref.getMovementDate())));
					//reference2.setCodRef(DocumentoDTE.SiiDte.Referencia_CodRef);
					doc.getReferencia().add(reference2);
				} catch (LtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
            }
            //referencia HES
            
            String DocNoHES = invoice.get_ValueAsString("POReferenceHES");
            if(DocNoHES != null && DocNoHES.trim().length() > 0 && DocNoHES.compareTo("0") !=0)
            {
            	indice = indice+1;
            	String  docRef = "HES";            	
	        	//Documento/Referencia
				DocumentoDTE.SiiDte.Referencia reference3 = new DocumentoDTE.SiiDte.Referencia();
	        	try {
	        		reference3.setNroLinRef(BigInteger.valueOf(indice));
	        		reference3.setTpoDocRef(docRef); //1-3 char
	        		reference3.setFolioRef(DocNoHES);
	        		Timestamp dateHES = (Timestamp)invoice.get_Value("DateHES");
	        		if(dateHES == null)
	        			dateHES = invoice.getDateInvoiced();	        			
	        		reference3.setFchRef(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date,ConverDateToString(dateHES)));
					doc.getReferencia().add(reference3);
				} catch (LtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
	        }
	        //se agrega campo adicional
            //Personalizados
            if(invoice.getDescription() != null && invoice.getDescription().trim().length() > 0)
            {
	            DocumentoDTE.SiiDte.PersonalizadosA personalizados = new DocumentoDTE.SiiDte.PersonalizadosA();
	            dte.setPersonalizados(personalizados);
	
	            personalizados.setDocPersonalizado(new DocumentoDTE.SiiDte.DocPersonalizadoA());
	
	            DocumentoDTE.SiiDte.CampoStringA campoString = new DocumentoDTE.SiiDte.CampoStringA();
	            campoString.setName("Notas");
	            campoString.setPrimitiveValue(invoice.getDescription());  // Aquí deben escribir la nota específica y aparecerá en el documento impreso.
	            personalizados.getDocPersonalizado().getCampoString().add(campoString);
            }
			// Llamada al servicio
	        String ambiente = dteboxcliente.Ambiente.Homologacion;
	        if(OFBForward.AmbienteGDE().compareToIgnoreCase("H") ==0)
	        	ambiente = dteboxcliente.Ambiente.Homologacion;
	        else if (OFBForward.AmbienteGDE().compareToIgnoreCase("P") ==0)
	        	ambiente = dteboxcliente.Ambiente.Produccion;
			//String fechaResolucion = "2010-01-01";
			String fechaResolucion = org.get_ValueAsString("FchResol") ;
			//int numeroResolucion = 10;
			int numeroResolucion = Integer.parseInt(org.get_ValueAsString("NroResol"));
			int tipoPdf417 = dteboxcliente.TipoPDF417.Fuente;
			        
			//String apiURL = "http://200.6.99.206/api/Core.svc/core";
			String apiURL = "http://192.168.0.200/api/Core.svc/Core";
			//String apiAuth = "2a84137d-a654-4f46-b873-2be446dadba7"; //pruebas			
			//String apiAuth = "9fbafbd5-62ac-4fb0-b0a0-d6e5d17fa19c"; //primer intento			
			//String apiAuth = "2ec64694-829f-4a6e-b369-dc8936320e09"; //segundo intento
			String apiAuth = OFBForward.GDEapiAuth();
			
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
			invoice.set_CustomColumn("DescriptionGDE", error);
			invoice.saveEx(invoice.get_TrxName());
			
		} 
		catch (Exception e) {
			// TODO: handle exception
			log.config(e.toString());
			error=e.toString();
		}
        //return "XML CG Generated "+ted+" "+pdf417+" "+error;
		return "Procesado"+ted+" - "+pdf417+" - "+error;
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
	
	public String CreateXMLBoleta(MInvoice invoice)
    {
		String wsRespuesta = "";
        MDocType doc = new MDocType(invoice.getCtx(), invoice.getC_DocTypeTarget_ID(), invoice.get_TrxName());
        MOrg org = new MOrg(invoice.getCtx(), invoice.getAD_Org_ID(), invoice.get_TrxName());
        String fechaResolucion = org.get_ValueAsString("FchResol") ;
		int numeroResolucion = Integer.parseInt(org.get_ValueAsString("NroResol"));
		
        if(doc.get_Value("CreateXML") == null)
            return "";
        if(!((Boolean)doc.get_Value("CreateXML")).booleanValue())
            return "";
        int typeDoc = Integer.parseInt((String)doc.get_Value("DocumentNo"));
        if(typeDoc == 0)
            return "";
        String mylog = new String();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try
        {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "DTE", null);
            document.setXmlVersion("1.0");            
            document.setTextContent("text/xml");
            //Attr atr = document.createAttribute("xmlns");
            //atr.setValue("http://www.sii.cl/SiiDte");            
            
            Element Documento = document.createElement("Documento");
            document.getDocumentElement().appendChild(Documento);
            Documento.setAttribute("ID", (new StringBuilder()).append("ID_").append(invoice.getDocumentNo()).toString());
            Element Encabezado = document.createElement("Encabezado");
            Documento.appendChild(Encabezado);
            
            Element FechaResolEle = document.createElement("FechaResol");
            org.w3c.dom.Text textFchResol = document.createTextNode(fechaResolucion);
            FechaResolEle.appendChild(textFchResol);
            Encabezado.appendChild(FechaResolEle);
            Element NroResolEle = document.createElement("NroResol");
            org.w3c.dom.Text textNroResol = document.createTextNode(Integer.toString(numeroResolucion));
            NroResolEle.appendChild(textNroResol);
            Encabezado.appendChild(NroResolEle);
            
            Element IdDoc = document.createElement("IdDoc");
            Encabezado.appendChild(IdDoc);
            mylog = "IdDoc";
            Element TipoDTE = document.createElement("TipoDTE");
            org.w3c.dom.Text text = document.createTextNode(Integer.toString(typeDoc));
            TipoDTE.appendChild(text);
            IdDoc.appendChild(TipoDTE);
            Element Folio = document.createElement("Folio");
            org.w3c.dom.Text fo = document.createTextNode(invoice.getDocumentNo());
            Folio.appendChild(fo);
            IdDoc.appendChild(Folio);
            Element FchEmis = document.createElement("FchEmis");
            org.w3c.dom.Text emis = document.createTextNode(invoice.getDateInvoiced().toString().substring(0, 10));
            FchEmis.appendChild(emis);
            IdDoc.appendChild(FchEmis);
            //indicaciones del servicio
            Element IndServicio = document.createElement("IndServicio");
            org.w3c.dom.Text IndServTxt = document.createTextNode("3");
            IndServicio.appendChild(IndServTxt);
            IdDoc.appendChild(IndServicio);
            
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
            Element FchVenc = document.createElement("FchVenc");
            org.w3c.dom.Text venc = document.createTextNode(fchVencCal.toString().substring(0, 10));
            FchVenc.appendChild(venc);
            IdDoc.appendChild(FchVenc); 
            Element Emisor = document.createElement("Emisor");
            Encabezado.appendChild(Emisor);
            mylog = "Emisor";
            MOrg company = MOrg.get(invoice.getCtx(), invoice.getAD_Org_ID());
            Element Rut = document.createElement("RUTEmisor");
            org.w3c.dom.Text rut = document.createTextNode((String)company.get_Value("Rut"));
            Rut.appendChild(rut);
            Emisor.appendChild(Rut);
            //ininoles validacion nuevo nombre razon social
            String nameRzn = company.getDescription();
            if (nameRzn == null)
            {
            	nameRzn = " ";
            }
            nameRzn = nameRzn.trim();
            if (nameRzn.length() < 2)
            	nameRzn = company.getName();
            //ininoles end            
            nameRzn = nameRzn.replace("'", "");
            nameRzn = nameRzn.replace("\"", "");
            Element RznSoc = document.createElement("RznSocEmisor");
            org.w3c.dom.Text rzn = document.createTextNode(nameRzn);
            RznSoc.appendChild(rzn);
            Emisor.appendChild(RznSoc);            
            String giroEmisStr = (String)company.get_Value("Giro");
            giroEmisStr = giroEmisStr.replace("'", "");
            giroEmisStr = giroEmisStr.replace("\"", "");
            Element GiroEmis = document.createElement("GiroEmisor");
            org.w3c.dom.Text gi = document.createTextNode(giroEmisStr);
            GiroEmis.appendChild(gi);
            Emisor.appendChild(GiroEmis);
            Element DirOrigen = document.createElement("DirOrigen");
            org.w3c.dom.Text dir = document.createTextNode((String)company.get_Value("Address1"));
            DirOrigen.appendChild(dir);
            Emisor.appendChild(DirOrigen);
            
            Element CmnaOrigen = document.createElement("CmnaOrigen");
            org.w3c.dom.Text com = document.createTextNode((String)company.get_Value("Comuna"));
            CmnaOrigen.appendChild(com);
            Emisor.appendChild(CmnaOrigen);
            Element CiudadOrigen = document.createElement("CiudadOrigen");
            org.w3c.dom.Text city = document.createTextNode((String)company.get_Value("City"));
            CiudadOrigen.appendChild(city);
            Emisor.appendChild(CiudadOrigen);
                        
            mylog = "receptor";
            //MBPartner BP = new MBPartner(invoice.getCtx(), invoice.getC_BPartner_ID(), invoice.get_TrxName());
            MBPartnerLocation bloc = new MBPartnerLocation(invoice.getCtx(), invoice.getC_BPartner_Location_ID(), invoice.get_TrxName());
            Element Receptor = document.createElement("Receptor");
            Encabezado.appendChild(Receptor);
            Element RUTRecep = document.createElement("RUTRecep");
            //org.w3c.dom.Text rutc = document.createTextNode((new StringBuilder()).append(BP.getValue()).append("-").append(BP.get_ValueAsString("Digito")).toString());
            org.w3c.dom.Text rutc = document.createTextNode("66666666-6");
            RUTRecep.appendChild(rutc);
            Receptor.appendChild(RUTRecep);
            
            //String RznSocRecepStr = BP.getName();
            String RznSocRecepStr = "Cliente Boletas";
            RznSocRecepStr = RznSocRecepStr.replace("'", "");
            RznSocRecepStr = RznSocRecepStr.replace("\"", "");
            Element RznSocRecep = document.createElement("RznSocRecep");
            org.w3c.dom.Text RznSocR = document.createTextNode(RznSocRecepStr);
            RznSocRecep.appendChild(RznSocR);
            Receptor.appendChild(RznSocRecep);
            
            String dirRecepStr = bloc.getLocation(true).getAddress1()+"-"+bloc.getPhone();
            dirRecepStr = dirRecepStr.replace("'", "");
            dirRecepStr = dirRecepStr.replace("\"", "");
            Element DirRecep = document.createElement("DirRecep");
            org.w3c.dom.Text dirr = document.createTextNode(dirRecepStr);
            DirRecep.appendChild(dirr);
            Receptor.appendChild(DirRecep);
              
            String CmnaRecepStr = null;
	        if(bloc.get_ValueAsInt("C_City_ID") > 0)
	        {
	        	MCity dCity = new MCity(invoice.getCtx(),bloc.get_ValueAsInt("C_City_ID"), invoice.get_TrxName());
	            CmnaRecepStr = dCity.getName();
	        }
	        if(CmnaRecepStr == null)
	        	CmnaRecepStr = "Santiago";
	        if(CmnaRecepStr.length() > 20)
	        	CmnaRecepStr = CmnaRecepStr.substring(0,20);
            //if (bloc.getLocation(true).getAddress2() != null)
            //	CmnaRecepStr = bloc.getLocation(true).getAddress2();
            //else if (bloc.getLocation(true).getC_City_ID()>0)            
            //	CmnaRecepStr = MCity.get(invoice.getCtx(), bloc.getLocation(true).getC_City_ID()).getName();
            log.config("CmnaRecep = "+CmnaRecepStr);
            Element CmnaRecep = document.createElement("CmnaRecep");
	        org.w3c.dom.Text Cmna = document.createTextNode(CmnaRecepStr);
	        CmnaRecep.appendChild(Cmna);
	        Receptor.appendChild(CmnaRecep);
            
	        String ciudadTxt = "";
	        if(bloc.getLocation(true).getC_Region_ID() == 1000001 || bloc.getLocation(true).getC_City_ID() <=0)
	        	ciudadTxt = "SANTIAGO";
	        else
	        	ciudadTxt =  bloc.getC_Location().getCity();
            Element CiudadRecep = document.createElement("CiudadRecep");
            org.w3c.dom.Text reg = document.createTextNode(ciudadTxt != null?ciudadTxt:"Santiago");
            CiudadRecep.appendChild(reg);
            Receptor.appendChild(CiudadRecep);
            
            mylog = "Totales";
            Element Totales = document.createElement("Totales");
            Encabezado.appendChild(Totales);
            
            //nueva forma de calcular los totales
            BigDecimal amountex = DB.getSQLValueBD(invoice.get_TrxName(), (new StringBuilder()).append("select Round(COALESCE(SUM(il.LineNetAmt),0),0) from C_InvoiceLine il  inner join C_Tax t on (il.C_Tax_ID=t.C_Tax_ID) and t.istaxexempt='Y' and il.C_Invoice_ID=").append(invoice.getC_Invoice_ID()).toString());
            BigDecimal amountNeto = DB.getSQLValueBD(invoice.get_TrxName(), (new StringBuilder()).append("select Round(COALESCE(SUM(il.LineNetAmt),0),0) from C_InvoiceLine il  inner join C_Tax t on (il.C_Tax_ID=t.C_Tax_ID) and t.istaxexempt='N' and il.C_Invoice_ID=").append(invoice.getC_Invoice_ID()).toString());
            //BigDecimal amountIVA =  invoice.getGrandTotal().subtract(amountNeto).subtract(amountex);
            
	        Element MntNeto = document.createElement("MntNeto");
	        org.w3c.dom.Text neto = document.createTextNode(amountNeto!=null?amountNeto.toString():"0");
	        MntNeto.appendChild(neto);
	        Totales.appendChild(MntNeto);
            
            Element MntExe = document.createElement("MntExe");
            org.w3c.dom.Text exe = document.createTextNode(amountex != null ? amountex.toString() : "0");
            MntExe.appendChild(exe);
            Totales.appendChild(MntExe);
            
            /*if(amountNeto.signum()>0){
	            Element TasaIVA = document.createElement("TasaIVA");
	            org.w3c.dom.Text tiva = document.createTextNode("19");
	            TasaIVA.appendChild(tiva);
	            Totales.appendChild(TasaIVA);
	        }*/
            
            Element IVA = document.createElement("IVA");
            BigDecimal ivaamt= Env.ZERO;
            if(amountex.intValue()!=invoice.getGrandTotal().intValue())
            	ivaamt=invoice.getGrandTotal().subtract(invoice.getTotalLines()).setScale(0, 4);
            org.w3c.dom.Text iva = document.createTextNode(ivaamt.toString());
            IVA.appendChild(iva);
            Totales.appendChild(IVA);
            
            Element MntTotal = document.createElement("MntTotal");
            org.w3c.dom.Text total = document.createTextNode(invoice.getGrandTotal().setScale(0, 4).toString());
            MntTotal.appendChild(total);
            Totales.appendChild(MntTotal);
            mylog = "detalle";
            MInvoiceLine iLines[] = invoice.getLines(false);
            int lineInvoice = 0;
            int lineDiscount = 0;
            //int lineDiscount = 0;
            for(int i = 0; i < iLines.length; i++)
            {	
            	MInvoiceLine iLine = iLines[i];
            	if(iLine.getM_Product_ID()==0 && iLine.getC_Charge_ID()==0)
            		continue;    
            	
            	if(iLine.getLineNetAmt().compareTo(Env.ZERO) >=0)
	        	{
	                Element Detalle = document.createElement("Detalle");
	                Documento.appendChild(Detalle);
	                
	                lineInvoice = lineInvoice+1;              
	                Element NroLinDet = document.createElement("NroLinDet");
	                org.w3c.dom.Text line = document.createTextNode(Integer.toString(lineInvoice));
	                NroLinDet.appendChild(line);
	                Detalle.appendChild(NroLinDet);
	                //codigo del item
	                Element cdgItem = document.createElement("CdgItem");
	                Detalle.appendChild(cdgItem);
	                
	                Element TpoCodigo = document.createElement("TpoCodigo");
	                org.w3c.dom.Text descTpoCod = document.createTextNode("INT");
	                TpoCodigo.appendChild(descTpoCod);
	                cdgItem.appendChild(TpoCodigo);
	                
	                Element VlrCodigo = document.createElement("VlrCodigo");
	                org.w3c.dom.Text descVlrCod = document.createTextNode(iLine.getProduct().getValue());
	                VlrCodigo.appendChild(descVlrCod);
	                cdgItem.appendChild(VlrCodigo);
	                
	                Element NmbItem = document.createElement("NmbItem");
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
	                org.w3c.dom.Text Item = document.createTextNode(pname);
	                NmbItem.appendChild(Item);
	                Detalle.appendChild(NmbItem);
	                
	                Element QtyItem = document.createElement("QtyItem");
	                org.w3c.dom.Text qt = document.createTextNode(iLine.getQtyInvoiced().toString());
	                QtyItem.appendChild(qt);
	                Detalle.appendChild(QtyItem);
	                                
	                String unmdItemStr = "";
	                if(iLine.getM_Product_ID() > 0)
	                	unmdItemStr = iLine.getM_Product().getC_UOM().getUOMSymbol();
	                else
	                	unmdItemStr = "UN";                
	                if (unmdItemStr == null)
	                	unmdItemStr = "UN";                
	                Element UnmdItem = document.createElement("UnmdItem");
	                org.w3c.dom.Text UM = document.createTextNode(unmdItemStr);
	                UnmdItem.appendChild(UM);
	                Detalle.appendChild(UnmdItem);
	                
	                BigDecimal prcItem = null;
	                BigDecimal mntItem = null;
	                BigDecimal prcItemTax = null;
	                BigDecimal mntItemTax = null;
	                
	                if(invoice.getC_DocType().getDocBaseType().compareToIgnoreCase("ARI") == 0
	                		&& invoice.getC_DocType().getName().toLowerCase().contains("boleta") 
	                		&& iLine.getC_Tax().getRate().compareTo(Env.ZERO) != 0)
	                {
	                	prcItemTax = iLine.getPriceActual().multiply(iLine.getC_Tax().getRate());
	                	prcItemTax = prcItemTax.divide(Env.ONEHUNDRED);
	                	prcItem = iLine.getPriceActual().add(prcItemTax);
	                	
	                	mntItemTax = iLine.getLineNetAmt().multiply(iLine.getC_Tax().getRate());
	                	mntItemTax = mntItemTax.divide(Env.ONEHUNDRED);
	                	mntItem = iLine.getLineNetAmt().add(mntItemTax);
	                }
	                else
	                {
	                	prcItem = iLine.getPriceActual();
	                	mntItem = iLine.getLineNetAmt();
	                }
	                
	                Element PrcItem = document.createElement("PrcItem");
	                org.w3c.dom.Text pa = document.createTextNode(prcItem.setScale(0, 4).toString());
	                PrcItem.appendChild(pa);
	                Detalle.appendChild(PrcItem);
	                Element MontoItem = document.createElement("MontoItem");
	                org.w3c.dom.Text tl = document.createTextNode(mntItem.setScale(0, 4).toString());
	                MontoItem.appendChild(tl);
	                Detalle.appendChild(MontoItem);
	        	}
            	else//tag de descuentos
            	{
            		Element Descuento = document.createElement("DscRcgGlobal");
            		Documento.appendChild(Descuento);

            		lineDiscount++;
            		
            		Element NroLinDis = document.createElement("NroLinDR");
	                org.w3c.dom.Text lineDis = document.createTextNode(Integer.toString(lineDiscount));
	                NroLinDis.appendChild(lineDis);
	                Descuento.appendChild(NroLinDis);
	                
	                Element Tpomov = document.createElement("TpoMov");
	                org.w3c.dom.Text descMov = document.createTextNode("D");
	                Tpomov.appendChild(descMov);
	                Descuento.appendChild(Tpomov);
	                
	                String pnameDis="";
		            if(iLine.getProduct()!=null )
		            {
		            	pnameDis=iLine.getProduct().getValue()+"-"+iLine.getProduct().getName();
		            	if(iLine.getM_Product().getUPC() != null && iLine.getM_Product().getUPC().trim().length() > 0)
		            		pnameDis = pnameDis+iLine.getM_Product().getUPC();
	            	}
		            else
		            	pnameDis=iLine.getC_Charge().getName();
		            Element GlosaDR = document.createElement("GlosaDR");
	                org.w3c.dom.Text descGlosaDR = document.createTextNode(pnameDis);
	                GlosaDR.appendChild(descGlosaDR);
	                Descuento.appendChild(GlosaDR);
	                
	                Element TpoValor = document.createElement("TpoValor");
	                org.w3c.dom.Text descTpoValor = document.createTextNode("$");
	                TpoValor.appendChild(descTpoValor);
	                Descuento.appendChild(TpoValor);
	                
	                Element TpoValorDRValor = document.createElement("ValorDR");
	                org.w3c.dom.Text descValorDR = document.createTextNode(iLine.getLineNetAmt().abs().setScale(0, 4).toString());
	                TpoValorDRValor.appendChild(descValorDR);
	                Descuento.appendChild(TpoValorDRValor);
            	}
        	}
            //ininoles 17-11 se agregan referencias
            /*String tiporeferencia = new String();
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
	        if(invoice.getPOReference() != null && invoice.getPOReference().length() > 0 && invoice.getC_DocType().getDocBaseType().compareTo("ARC") != 0
	        		&& invoice.getPOReference().compareTo("0") != 0)//referencia orden
	        {
	        	 //MOrder refdoc = new MOrder(invoice.getCtx(), ((Integer)get_Value("C_RefOrder_ID")).intValue(), invoice.get_TrxName()); 
	        	 tiporeferencia = "801";
	             folioreferencia = invoice.getPOReference();
	             if(invoice.getDateOrdered() != null)
	            	 fechareferencia = ConverDateToString(invoice.getDateOrdered());
	             else
	            	 fechareferencia = ConverDateToString(invoice.getDateInvoiced());
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
	            Element Referencia = document.createElement("Referencia");
                Documento.appendChild(Referencia);
                Element NroLinRef = document.createElement("NroLinRef");
                org.w3c.dom.Text Nro = document.createTextNode(Integer.toString(indice));
                NroLinRef.appendChild(Nro);
                Referencia.appendChild(NroLinRef);
                Element TpoDocRef = document.createElement("TpoDocRef");
                org.w3c.dom.Text tpo = document.createTextNode(tiporeferencia);
                TpoDocRef.appendChild(tpo);
                Referencia.appendChild(TpoDocRef);
                Element FolioRef = document.createElement("FolioRef");
                org.w3c.dom.Text ref = document.createTextNode(folioreferencia);
                FolioRef.appendChild(ref);
                Referencia.appendChild(FolioRef);
                Element FchRef = document.createElement("FchRef");
                org.w3c.dom.Text fchref = document.createTextNode(fechareferencia);
                FchRef.appendChild(fchref);
                Referencia.appendChild(FchRef);
                
                Element CodRef = document.createElement("CodRef");
                org.w3c.dom.Text codref = document.createTextNode(Integer.toString(tipo_Ref));
                CodRef.appendChild(codref);
                Referencia.appendChild(CodRef); 
				DocumentoDTE.SiiDte.Referencia reference = new DocumentoDTE.SiiDte.Referencia();
				try 
				{
					Element PrcItem = document.createElement("PrcItem");
					org.w3c.dom.Text pa = document.createTextNode(prcItem.setScale(0, 4).toString());
					PrcItem.appendChild(pa);
					Detalle.appendChild(PrcItem);
					reference.setNroLinRef(BigInteger.valueOf(indice));
					reference.setTpoDocRef(tiporeferencia); //1-3 char
					reference.setFolioRef(folioreferencia);
					reference.setFchRef(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date,fechareferencia));
					
					reference.setCodRef(DocumentoDTE.SiiDte.Referencia_CodRef.n1);
					String razonRefTxt = invoice.get_ValueAsString("RazonRef");
	                if(razonRefTxt != null && razonRefTxt.trim().length() > 1)
	                {
	                	reference.setRazonRef(razonRefTxt);
	                }
					doc.getReferencia().add(reference);
				} catch (LtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
	        }
	
	        int ID_Ship = 0;
            try            	
            {
            	if(invoice.getC_Order_ID() > 0)
            	{
            		ID_Ship = DB.getSQLValue(invoice.get_TrxName(), "SELECT COALESCE((MAX(M_InOut_ID)),0) " +
            				"FROM M_InOut mi INNER JOIN C_Order co ON (mi.C_Order_ID = co.C_Order_ID) " +
            				"WHERE mi.docstatus IN ('CO','CL','VO') AND mi.C_Order_ID = "+invoice.getC_Order_ID());
            	}
            }catch (Exception e)
    		{
            	ID_Ship = 0;
    			log.log(Level.SEVERE, e.getMessage(), e);
    		}
            if(ID_Ship>0)
            {
            	indice = indice+1;
            	String  docRef = "52";            	
            	MInOut inOutref = new MInOut(invoice.getCtx(), ID_Ship, invoice.get_TrxName());
            	MDocType docTShip = new MDocType(invoice.getCtx(), inOutref.getC_DocType_ID(), invoice.get_TrxName());
            	if(docTShip.get_ValueAsBoolean("CreateXML"))
            		docRef = "52";
            	else
            		docRef = "52";
            	//Documento/Referencia
				DocumentoDTE.SiiDte.Referencia reference2 = new DocumentoDTE.SiiDte.Referencia();
            	try {
					reference2.setNroLinRef(BigInteger.valueOf(indice));
					reference2.setTpoDocRef(docRef); //1-3 char
					reference2.setFolioRef(inOutref.getDocumentNo());
					reference2.setFchRef(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date,ConverDateToString(inOutref.getMovementDate())));
					//reference2.setCodRef(DocumentoDTE.SiiDte.Referencia_CodRef);
					doc.getReferencia().add(reference2);
				} catch (LtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
            }
            */
            
            mylog = "archivo";
            String ExportDir = (String)company.get_Value("ExportDir");
            try
            {
            	File theDir = new File(ExportDir);
            	if (!theDir.exists())
            		ExportDir = (String)company.get_Value("ExportDir2"); 
            }
            catch(Exception e)
            {
            	throw new AdempiereException("no existe directorio");
            }
	          
            ExportDir = ExportDir.replace("\\", "/");
            javax.xml.transform.Source source = new DOMSource(document);
            javax.xml.transform.Result result = new StreamResult(new File(ExportDir, (new StringBuilder()).append(invoice.getDocumentNo()).append(".xml").toString()));
            javax.xml.transform.Result console = new StreamResult(System.out);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("encoding", "ISO-8859-1");
            transformer.transform(source, result);
            transformer.transform(source, console);
            
            //convertir a base 64                       
    		File file = new File(ExportDir, (new StringBuilder()).append(invoice.getDocumentNo()).append(".xml").toString());

    		//setear atributos de cabecera
    		Document docValid = builder.parse(file);
    		Element raiz = docValid.getDocumentElement();
    		raiz.setAttribute("version", "1.0");
    		
    		//se guarda nuevo xml
            source = new DOMSource(docValid);
            result = new StreamResult(new File(ExportDir, (new StringBuilder()).append(invoice.getDocumentNo()).append(".xml").toString()));
            console = new StreamResult(System.out);
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("encoding", "ISO-8859-1");
            transformer.transform(source, result);
            transformer.transform(source, console);
    		
            //codificacion a base64
    		byte[] fileArray = new byte[(int) file.length()];
    		InputStream inputStream;
    		inputStream = new FileInputStream(file);
			inputStream.read(fileArray);    		
			
			log.config("XML enviado: "+file.toString());
    		byte[] encoded = Base64.encodeBase64(fileArray);     
    		String encodedFile = "";
    		try 
    		{	
    			encodedFile = new String(encoded);
    		} 
    		catch (Exception e) {
    		}
    		log.config("XML enviado base64: "+encodedFile.toString());
    		
    		//enlace con GDE
    		// Llamada al servicio
	        String ambiente = dteboxcliente.Ambiente.Homologacion;
	        if(OFBForward.AmbienteGDE().compareToIgnoreCase("H") ==0)
	        	ambiente = dteboxcliente.Ambiente.Homologacion;
	        else if (OFBForward.AmbienteGDE().compareToIgnoreCase("P") ==0)
	        	ambiente = dteboxcliente.Ambiente.Produccion;
			//String fechaResolucion = "2010-01-01";
			int tipoPdf417 = dteboxcliente.TipoPDF417.Fuente;
			        
			String apiURL = "http://192.168.0.200/api/Core.svc/Core";
			String apiAuth = OFBForward.GDEapiAuth();
			
			String ResponseText = "";
			try 
			{
		        //	String invokeURI = "http://25.109.131.6/api/Core.svc/core/SendDocumentAsXML";
				String invokeURI = "http://192.168.0.200/api/Core.svc/core/SendDocumentAsXML";
				try 
				{
					String request = "<SendDocumentAsXMLRequest xmlns=\"http://gdexpress.cl/api\">"
							+ "<Environment>"+ambiente+"</Environment>  "
							//+ "<Content>PERURSB4bWxucz0iaHR0cDovL3d3dy5zaWkuY2wvU2lpRHRlIiB2ZXJzaW9uPSIxLjAiPg0KICAgICAgPERvY3VtZW50byBJRD0iRFRFLTMzIj4NCiAgICAgICAgICAgIDxFbmNhYmV6YWRvPg0KICAgICAgICAgICAgICAgICAgPElkRG9jPg0KICAgICAgICAgICAgICAgICAgICAgICAgPFRpcG9EVEU+MzM8L1RpcG9EVEU+DQogICAgICAgICAgICAgICAgICAgICAgICA8Rm9saW8+MjAyPC9Gb2xpbz4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxGY2hFbWlzPjIwMjAtMDktMjU8L0ZjaEVtaXM+DQogICAgICAgICAgICAgICAgICAgICAgICA8RmNoVmVuYz4yMDIwLTA5LTI1PC9GY2hWZW5jPg0KICAgICAgICAgICAgICAgICAgPC9JZERvYz4NCiAgICAgICAgICAgICAgICAgIDxFbWlzb3I+DQogICAgICAgICAgICAgICAgICAgICAgICA8UlVURW1pc29yPjc5Nzk2MzEwLTA8L1JVVEVtaXNvcj4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxSem5Tb2M+U29jIENvbWVyY2lhbCBBcnRpbGVjIEFydCBFbGVjdHJvbmljb3MgRGUgUHJvdGVjY2lvbiBMdGRhPC9Sem5Tb2M+DQogICAgICAgICAgICAgICAgICAgICAgICA8R2lyb0VtaXM+RWxlY3Ryb25pY2E8L0dpcm9FbWlzPg0KICAgICAgICAgICAgICAgICAgICAgICAgPEFjdGVjbz40NjkwMDA8L0FjdGVjbz4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxEaXJPcmlnZW4+U0FOVEEgTUFSVEEgREUgSFVFQ0hVUkFCQSA2NTcwPC9EaXJPcmlnZW4+DQogICAgICAgICAgICAgICAgICAgICAgICA8Q21uYU9yaWdlbj5IdWVjaHVyYWJhPC9DbW5hT3JpZ2VuPg0KICAgICAgICAgICAgICAgICAgICAgICAgPENpdWRhZE9yaWdlbj5TYW50aWFnbzwvQ2l1ZGFkT3JpZ2VuPg0KICAgICAgICAgICAgICAgICAgPC9FbWlzb3I+DQogICAgICAgICAgICAgICAgICA8UmVjZXB0b3I+DQogICAgICAgICAgICAgICAgICAgICAgICA8UlVUUmVjZXA+NzYxMjk0ODYtNTwvUlVUUmVjZXA+DQogICAgICAgICAgICAgICAgICAgICAgICA8UnpuU29jUmVjZXA+R0RFIFMuQS48L1J6blNvY1JlY2VwPg0KICAgICAgICAgICAgICAgICAgICAgICAgPEdpcm9SZWNlcD5PdHJhcyBhY3RpdmlkYWRlcyBlbXByZXNhcmlhbGVzPC9HaXJvUmVjZXA+DQogICAgICAgICAgICAgICAgICAgICAgICA8RGlyUmVjZXA+U2FudGEgQmVhdHJpeiAxNzAgT2YgNzAyPC9EaXJSZWNlcD4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxDbW5hUmVjZXA+UHJvdmlkZW5jaWE8L0NtbmFSZWNlcD4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxDaXVkYWRSZWNlcD5TYW50aWFnbzwvQ2l1ZGFkUmVjZXA+DQogICAgICAgICAgICAgICAgICA8L1JlY2VwdG9yPg0KICAgICAgICAgICAgICAgICAgPFRvdGFsZXM+DQogICAgICAgICAgICAgICAgICAgICAgICA8TW50TmV0bz44MTk8L01udE5ldG8+DQogICAgICAgICAgICAgICAgICAgICAgICA8TW50RXhlPjA8L01udEV4ZT4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxUYXNhSVZBPjE5PC9UYXNhSVZBPg0KICAgICAgICAgICAgICAgICAgICAgICAgPElWQT4xNTY8L0lWQT4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxNbnRUb3RhbD45NzU8L01udFRvdGFsPg0KICAgICAgICAgICAgICAgICAgPC9Ub3RhbGVzPg0KICAgICAgICAgICAgPC9FbmNhYmV6YWRvPg0KICAgICAgICAgICAgPERldGFsbGU+DQogICAgICAgICAgICAgICAgICA8TnJvTGluRGV0PjE8L05yb0xpbkRldD4NCiAgICAgICAgICAgICAgICAgIDxObWJJdGVtPkNpbnRhIFZIUyAxLzI8L05tYkl0ZW0+DQogICAgICAgICAgICAgICAgICA8RHNjSXRlbT5EZXNjcmlwY2nDs24gY29uIHNhbHRvIGRlIGxpbmVhbGluZWEgMWxpbmVhIDJsaW5lYSAzZmluPC9Ec2NJdGVtPg0KICAgICAgICAgICAgICAgICAgPFF0eUl0ZW0+MS4wPC9RdHlJdGVtPg0KICAgICAgICAgICAgICAgICAgPFByY0l0ZW0+ODE5LjA8L1ByY0l0ZW0+DQogICAgICAgICAgICAgICAgICA8TW9udG9JdGVtPjgxOTwvTW9udG9JdGVtPg0KICAgICAgICAgICAgPC9EZXRhbGxlPg0KICAgICAgPC9Eb2N1bWVudG8+DQo8L0RURT4=</Content>"
							+ "<Content>"+encodedFile+"</Content>"
							+ "<ResolutionDate>"+fechaResolucion+"</ResolutionDate>"
							+ "<ResolutionNumber>"+numeroResolucion+"</ResolutionNumber>"
							+ "<PDF417Columns></PDF417Columns>"
							+ "<PDF417Level></PDF417Level>"
							+ "<PDF417Type></PDF417Type>"
							+ "<TED></TED>"
							+ "</SendDocumentAsXMLRequest>";		                                              

					URL url = new URL(invokeURI);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("POST");
					conn.setRequestProperty("AuthKey",apiAuth);
					conn.setRequestProperty("Accept", "application/xml");
					conn.setRequestProperty("Content-Type","application/xml; charset=utf-8");
					// Send post request
					conn.setDoOutput(true);
					conn.setDoInput(true);
					conn.setUseCaches(false);
					DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
					wr.write(request.getBytes("UTF-8"));
					wr.flush();
					wr.close();

					// read & parse the response
					InputStream is = conn.getInputStream();
					DataInputStream dis = new DataInputStream(is);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[2028];
					int read = 0;
					// Leer hasta que el server cierre la conexion
					while ((read = dis.read(buffer)) != -1) {
						baos.write(buffer, 0, read);
					}
					byte[] ResponseBytes = baos.toByteArray();
					ResponseText = new String(ResponseBytes);
					System.out.println(ResponseText);
				}
				catch(Exception ex) 
				{
					ex.printStackTrace();
				}
			}
	        catch (Exception e)
			{
	        	e.printStackTrace();
			}
			invoice.set_CustomColumn("DescriptionGDE",ResponseText);
			invoice.saveEx(invoice.get_TrxName());
			if(ResponseText.contains("<Result>0</Result>"))
			{
				wsRespuesta = "DTE Enviado OK";
				log.config(wsRespuesta);
			}
			else
			{
				wsRespuesta = "ERROR en DTE Enviado";
				log.config(wsRespuesta);
			}				
    	}
        catch(Exception e)
        {
            log.severe((new StringBuilder()).append("CreateXML: ").append(mylog).append("--").append(e.getMessage()).toString());
            return (new StringBuilder()).append("CreateXML: ").append(mylog).append("--").append(e.getMessage()).toString();
        }   
        return "XML CG Generated "+ wsRespuesta;
    }        
	
	
	public String GetPDF(MInvoice invoice)
    {
		// ï»¿Recuperar el PDF de un documento
		// Recuperar PDF
		// Dependencias
		// - DTEBOXClienteJDK142.jar

		String ambiente = dteboxcliente.Ambiente.Homologacion;
        if(OFBForward.AmbienteGDE().compareToIgnoreCase("H") ==0)
        	ambiente = dteboxcliente.Ambiente.Homologacion;
        else if (OFBForward.AmbienteGDE().compareToIgnoreCase("P") ==0)
        	ambiente = dteboxcliente.Ambiente.Produccion;

		dteboxcliente.GrupoBusqueda grupo = dteboxcliente.GrupoBusqueda.Emitidos;

		MOrg org = MOrg.get(invoice.getCtx(), invoice.getAD_Org_ID());
		String rut = (String)org.get_Value("Rut");

		MDocType docType = new MDocType(invoice.getCtx(), invoice.getC_DocTypeTarget_ID(), invoice.get_TrxName());
		int tipoDTE = dteboxcliente.TipoDocumento.TIPO_33;
		if(docType.get_ValueAsString("DocumentNo").compareTo("33") == 0)
			 tipoDTE = dteboxcliente.TipoDocumento.TIPO_33;
		else if(docType.get_ValueAsString("DocumentNo").compareTo("34") == 0)
			tipoDTE = dteboxcliente.TipoDocumento.TIPO_34;
		else if(docType.get_ValueAsString("DocumentNo").compareTo("56") == 0)
			tipoDTE = dteboxcliente.TipoDocumento.TIPO_56;
		else if(docType.get_ValueAsString("DocumentNo").compareTo("61") == 0)
			tipoDTE = dteboxcliente.TipoDocumento.TIPO_61;
		else if(docType.get_ValueAsString("DocumentNo").compareTo("39") == 0)
			tipoDTE = dteboxcliente.TipoDocumento.TIPO_39;
		else if(docType.get_ValueAsString("DocumentNo").compareTo("41") == 0)
			tipoDTE = dteboxcliente.TipoDocumento.TIPO_41;
		else
			tipoDTE = dteboxcliente.TipoDocumento.TIPO_33;
				
		long folio = Long.parseLong(invoice.getDocumentNo());
		String apiURL = "http://192.168.0.200/api/Core.svc/Core";
		String apiAuth = OFBForward.GDEapiAuth();
		
		dteboxcliente.Servicio servicio = new dteboxcliente.Servicio(apiURL, apiAuth);
		dteboxcliente.ResultadoRecuperarPDF resultado = servicio.RecuperarPdf(ambiente, grupo, rut, tipoDTE, folio);
		// Procesar respuesta
		if (resultado.getResultadoServicio().getEstado() == 0
				|| resultado.getResultadoServicio().getEstado() == 1) 
		{
			log.config("datos Usados: apiURL="+apiURL+" - apiAuth="+apiAuth+" - ambiente="+ambiente+
					" - grupo="+grupo+" - rut="+rut+" - tipoDTE="+tipoDTE+" - folio="+folio); 
		    //Usar los datos que vienen en el resultado de la llamada
			log.config("log resultado: "+resultado.getResultadoServicio().getDescripcion()+" - "+
					resultado.getResultadoServicio().getExcepcionOriginal()+ " - "+
					resultado.getResultadoServicio().getEstado());
		    byte[] pdf = resultado.getDatos();
		    if(pdf != null)
		    {
			    String ExportDir = "";
			    //mfrojas
			    try 
			    {
			    	String pathServer = OFBForward.PathGDEServer();
			    	String pathCliente = OFBForward.PathGDEClient();
			    	if(pathServer != null && pathCliente != null
			    			&& pathServer .trim().length() > 0
			    			&& pathCliente .trim().length() > 0)
			    	{
			    		File theDir = new File(pathServer);
		  	        	if (!theDir.exists())
		  	        		ExportDir = pathCliente; 
		  	          
			    		log.config("Archivo a guardar: "+ExportDir+folio+".pdf");
				    	OutputStream out = new FileOutputStream(ExportDir+folio+".pdf");
				    	out.write(pdf);
				    	out.close();
				    	
				    	//se sube pdf a ftp
				    	String remote_working_dir_path = OFBForward.GDEDirPath();
				    	String local_filepath = ExportDir+folio+".pdf";
				    	String remote_filename = folio+".pdf";
				    	//String remoteURL = OFBForward.GDERemoteURL();
				    	String finalURL = OFBForward.GDERemoteURL()+folio+".pdf";
	
				    	FileInputStream fis = new FileInputStream(local_filepath);
				    	FTPClient client = new FTPClient();
				    	try {
				    	    client.setBufferSize(512); // Opcional para definir Buffer size en bytes
				    	    client.connect(OFBForward.GDEFTPServer(),OFBForward.GDEFTPServerPort()); // no el puerto es por defecto, podemos usar client.connect("servidor.ftp.com");
				    	    client.login(OFBForward.GDEFTPUser(),OFBForward.GDEFTPPass());
				    	    client.enterLocalPassiveMode(); // IMPORTANTE!!!! 
				    	    client.setFileType(FTP.BINARY_FILE_TYPE);
				    	    client.changeWorkingDirectory(remote_working_dir_path);
				    	    boolean uploadFile = client.storeFile(remote_filename,fis);
				    	    client.logout();
				    	    client.disconnect();
	
				    	    if ( uploadFile == false ) 
				    	        throw new Exception("Error al subir el fichero");
				    	    else// se guarda url
				    	    {
				    	    	invoice.set_CustomColumn("UrlGde",finalURL);
						    	invoice.saveEx(get_TrxName());
				    	    }			    	    	
				    	} catch (Exception eFTPClient) 
				    	{
				    	    // Gestionar el error, mostrar pantalla, reescalar excepcion... etc...
				    		log.config("ERROR: "+eFTPClient.toString());
				    	} finally {
				    	    fis.close();
				    	}
				    	
				    	/*
				    	log.config("Archivo a abrir: "+pathCliente+folio+".pdf");
				    	invoice.setDescription(pathCliente+folio+".pdf");
				    	invoice.saveEx(get_TrxName());
				    	Process p = Runtime.getRuntime().exec ("rundll32 SHELL32.DLL,ShellExec_RunDLL "+pathCliente+folio+".pdf");
				    	File path = new File(pathCliente+folio+".pdf");
					    Desktop.getDesktop().open(path);
					    */
			    	}
				} catch (IOException e) 
			    {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
		    }
		    //Guardar en adjunto ?
		    /*log.config("pdf byte "+pdf);
		    MAttachment att = new MAttachment(getCtx(), 0, get_TrxName());
		    att.setAD_Table_ID(318);
		    att.setBinaryData(pdf);
		    att.setRecord_ID(p_C_Invoice_ID);
		    att.setTitle("pdf");
		    att.save();*/
		    
		} 
		else 
		{
			//DescripciÃ³n del error, actuar acorde
			String error = resultado.getResultadoServicio().getDescripcion();
			return error;
		}


		return null;
    }
	
	public void enviarBoletaArtilec(MInvoice invoice) throws Exception{

        DocumentoBoleta.SiiDte.BOLETADefType boleta = new DocumentoBoleta.SiiDte.BOLETADefType();
        DocumentoBoleta.SiiDte.Documento doc = new DocumentoBoleta.SiiDte.Documento();
        boleta.setDocumento(doc);
        //Documento/Encabezado

        doc.setEncabezado(new DocumentoBoleta.SiiDte.Encabezado());
        //Documento/Encabezado/IdDoc2

        DocumentoBoleta.SiiDte.IdDoc idDoc = new DocumentoBoleta.SiiDte.IdDoc();

        doc.getEncabezado().setIdDoc(idDoc);

        idDoc.setTipoDTE(DocumentoBoleta.SiiDte.DTEType.n39);

        idDoc.setFolio(BigInteger.valueOf(2));

        idDoc.setFchEmis(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date, "2020-10-21"));

        idDoc.setIndServicio(DocumentoBoleta.SiiDte.IdDoc_IndServicio.n3);

 

        //Documento/Encabezado/Emisor

        DocumentoBoleta.SiiDte.Emisor emisor = new DocumentoBoleta.SiiDte.Emisor();

        doc.getEncabezado().setEmisor(emisor);

        emisor.setRUTEmisor("79796310-0");

        emisor.setRznSocEmisor("Soc Comercial Artilec Art Electronicos De Proteccion Ltda");

        emisor.setGiroEmisor("Prod Impy Exp de Alarmas Art Electricos ");

        emisor.setDirOrigen("Santa Marta de Huechuraba 6570");

        emisor.setCmnaOrigen("HUECHURABA");

 

        //Documento/Encabezado/Receptor

        DocumentoBoleta.SiiDte.Receptor receptor = new DocumentoBoleta.SiiDte.Receptor();

        doc.getEncabezado().setReceptor(receptor);

        receptor.setRUTRecep("66666666-6");

        receptor.setRznSocRecep("Cliente Boletas");

 

        //Boleta/Documento/Encabezado/Totales

        DocumentoBoleta.SiiDte.Totales totales = new DocumentoBoleta.SiiDte.Totales();

        doc.getEncabezado().setTotales(totales);

        totales.setMntTotal(BigInteger.valueOf(1190));

        totales.setMntNeto(BigInteger.valueOf(1000));

        totales.setIVA(BigInteger.valueOf(190));

 

        //Boleta/Documento/Detalle

        DocumentoBoleta.SiiDte.Detalle det = new DocumentoBoleta.SiiDte.Detalle();

        doc.getDetalle().add(det);

        det.setNroLinDet(BigInteger.valueOf(1));

        det.setNmbItem("Nombre del detalle");

        det.setMontoItem(BigInteger.valueOf(1190));

        det.setQtyItem(BigDecimal.valueOf(1));

        det.setPrcItem(BigDecimal.valueOf(1190));

 

        // Llamada al servicio

        String ambiente = dteboxcliente.Ambiente.Homologacion;

        String fechaResolucion = "2013-04-01";

        int numeroResolucion = 0;

        int tipoPdf417 = dteboxcliente.TipoPDF417.Fuente;;

 

        //String apiURL = "http://25.109.131.6/api/Core.svc/Core";
        String apiURL = "http://192.168.0.200/api/Core.svc/Core";
        String apiAuth = "2ec64694-829f-4a6e-b369-dc8936320e09";

        dteboxcliente.Servicio servicio = new dteboxcliente.Servicio(apiURL, apiAuth);

        dteboxcliente.ResultadoEnvioDocumento resultado = servicio.EnviarDocumento(boleta, ambiente, fechaResolucion, numeroResolucion, tipoPdf417);
        log.config("metodo nuevo: "+boleta.toXml());
 

        //Procesar resultado

        if (resultado.getResultadoServicio().getEstado() == 0){

            String ted = resultado.getTED();

            String pdf417 = resultado.getPDF417();
            //System.out.println("Envio OK");
            invoice.set_CustomColumn("DescriptionGDE", "Envio OK");
			invoice.saveEx(invoice.get_TrxName());
 

        }else{

 

            String error = resultado.getResultadoServicio().getDescripcion();

            System.out.println("Error: " + error);
            invoice.set_CustomColumn("DescriptionGDE", error);
			invoice.saveEx(invoice.get_TrxName());
            resultado.getResultadoServicio().getExcepcionOriginal().printStackTrace();

           

        }

 

    }
}	//	InvoiceCreateInOut
