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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.soap.SOAPBinding;

import org.compiere.util.*;
import org.compiere.model.*;
import org.compiere.process.SvrProcess;
import org.ofb.model.OFBForward;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.codec.binary.Base64; 

/**
 *	Generate XML Consolidated from Invoice (Generic) 
 *	
 *  @author Italo Ni�oles ininoles
 *  @version $Id: ExportDTEInvoiceCG.java,v 1.2 19/05/2011 $
 */
public class GDERestSendDocument extends SvrProcess
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
		//if(inv.getC_DocType().getName().toLowerCase().contains("boleta"))
		msg=CreateXMLCGBoleta(inv);	
		return msg;
	}	//	doIt
	
	public String CreateXMLCGBoleta(MInvoice invoice)
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
            Attr atr = document.createAttribute("xmlns");
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
            org.w3c.dom.Text IndServTxt = document.createTextNode("1");
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
            MBPartner BP = new MBPartner(invoice.getCtx(), invoice.getC_BPartner_ID(), invoice.get_TrxName());
            MBPartnerLocation bloc = new MBPartnerLocation(invoice.getCtx(), invoice.getC_BPartner_Location_ID(), invoice.get_TrxName());
            Element Receptor = document.createElement("Receptor");
            Encabezado.appendChild(Receptor);
            Element RUTRecep = document.createElement("RUTRecep");
            org.w3c.dom.Text rutc = document.createTextNode((new StringBuilder()).append(BP.getValue()).append("-").append(BP.get_ValueAsString("Digito")).toString());
            RUTRecep.appendChild(rutc);
            Receptor.appendChild(RUTRecep);
            
            String RznSocRecepStr = BP.getName();
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
            if (bloc.getLocation(true).getAddress2() != null)
            	CmnaRecepStr = bloc.getLocation(true).getAddress2();
            else if (bloc.getLocation(true).getC_City_ID()>0)            
            	CmnaRecepStr = MCity.get(invoice.getCtx(), bloc.getLocation(true).getC_City_ID()).getName();
            
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
            
            
	        Element MntNeto = document.createElement("MntNeto");
	        org.w3c.dom.Text neto = document.createTextNode(amountNeto!=null?amountNeto.toString():"0");
	        MntNeto.appendChild(neto);
	        Totales.appendChild(MntNeto);
            
            
            Element MntExe = document.createElement("MntExe");
            org.w3c.dom.Text exe = document.createTextNode(amountex != null ? amountex.toString() : "0");
            MntExe.appendChild(exe);
            Totales.appendChild(MntExe);
          
            Element MntTotal = document.createElement("MntTotal");
            org.w3c.dom.Text total = document.createTextNode(invoice.getGrandTotal().setScale(0, 4).toString());
            MntTotal.appendChild(total);
            Totales.appendChild(MntTotal);
            mylog = "detalle";
            MInvoiceLine iLines[] = invoice.getLines(false);
            int lineInvoice = 0;
            int lineDiscount = 0;
            for(int i = 0; i < iLines.length; i++)
            {	
            	MInvoiceLine iLine = iLines[i];
            	if(iLine.getM_Product_ID()==0 && iLine.getC_Charge_ID()==0)
            		continue;    
            	
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
    		//raiz.setAttribute("xmlns", "http://www.sii.cl/SiiDte");
    		
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
				String invokeURI = "http://192.168.0.200/api/Core.svc/core/SendDocumentAsXML";
				try 
				{
					String request = "<SendDocumentAsXMLRequest xmlns=\"http://gdexpress.cl/api\">"
							+ "<Environment>T</Environment>  "
							//+ "<Content>PERURSB4bWxucz0iaHR0cDovL3d3dy5zaWkuY2wvU2lpRHRlIiB2ZXJzaW9uPSIxLjAiPg0KICAgICAgPERvY3VtZW50byBJRD0iRFRFLTMzIj4NCiAgICAgICAgICAgIDxFbmNhYmV6YWRvPg0KICAgICAgICAgICAgICAgICAgPElkRG9jPg0KICAgICAgICAgICAgICAgICAgICAgICAgPFRpcG9EVEU+MzM8L1RpcG9EVEU+DQogICAgICAgICAgICAgICAgICAgICAgICA8Rm9saW8+MjAyPC9Gb2xpbz4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxGY2hFbWlzPjIwMjAtMDktMjU8L0ZjaEVtaXM+DQogICAgICAgICAgICAgICAgICAgICAgICA8RmNoVmVuYz4yMDIwLTA5LTI1PC9GY2hWZW5jPg0KICAgICAgICAgICAgICAgICAgPC9JZERvYz4NCiAgICAgICAgICAgICAgICAgIDxFbWlzb3I+DQogICAgICAgICAgICAgICAgICAgICAgICA8UlVURW1pc29yPjc5Nzk2MzEwLTA8L1JVVEVtaXNvcj4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxSem5Tb2M+U29jIENvbWVyY2lhbCBBcnRpbGVjIEFydCBFbGVjdHJvbmljb3MgRGUgUHJvdGVjY2lvbiBMdGRhPC9Sem5Tb2M+DQogICAgICAgICAgICAgICAgICAgICAgICA8R2lyb0VtaXM+RWxlY3Ryb25pY2E8L0dpcm9FbWlzPg0KICAgICAgICAgICAgICAgICAgICAgICAgPEFjdGVjbz40NjkwMDA8L0FjdGVjbz4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxEaXJPcmlnZW4+U0FOVEEgTUFSVEEgREUgSFVFQ0hVUkFCQSA2NTcwPC9EaXJPcmlnZW4+DQogICAgICAgICAgICAgICAgICAgICAgICA8Q21uYU9yaWdlbj5IdWVjaHVyYWJhPC9DbW5hT3JpZ2VuPg0KICAgICAgICAgICAgICAgICAgICAgICAgPENpdWRhZE9yaWdlbj5TYW50aWFnbzwvQ2l1ZGFkT3JpZ2VuPg0KICAgICAgICAgICAgICAgICAgPC9FbWlzb3I+DQogICAgICAgICAgICAgICAgICA8UmVjZXB0b3I+DQogICAgICAgICAgICAgICAgICAgICAgICA8UlVUUmVjZXA+NzYxMjk0ODYtNTwvUlVUUmVjZXA+DQogICAgICAgICAgICAgICAgICAgICAgICA8UnpuU29jUmVjZXA+R0RFIFMuQS48L1J6blNvY1JlY2VwPg0KICAgICAgICAgICAgICAgICAgICAgICAgPEdpcm9SZWNlcD5PdHJhcyBhY3RpdmlkYWRlcyBlbXByZXNhcmlhbGVzPC9HaXJvUmVjZXA+DQogICAgICAgICAgICAgICAgICAgICAgICA8RGlyUmVjZXA+U2FudGEgQmVhdHJpeiAxNzAgT2YgNzAyPC9EaXJSZWNlcD4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxDbW5hUmVjZXA+UHJvdmlkZW5jaWE8L0NtbmFSZWNlcD4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxDaXVkYWRSZWNlcD5TYW50aWFnbzwvQ2l1ZGFkUmVjZXA+DQogICAgICAgICAgICAgICAgICA8L1JlY2VwdG9yPg0KICAgICAgICAgICAgICAgICAgPFRvdGFsZXM+DQogICAgICAgICAgICAgICAgICAgICAgICA8TW50TmV0bz44MTk8L01udE5ldG8+DQogICAgICAgICAgICAgICAgICAgICAgICA8TW50RXhlPjA8L01udEV4ZT4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxUYXNhSVZBPjE5PC9UYXNhSVZBPg0KICAgICAgICAgICAgICAgICAgICAgICAgPElWQT4xNTY8L0lWQT4NCiAgICAgICAgICAgICAgICAgICAgICAgIDxNbnRUb3RhbD45NzU8L01udFRvdGFsPg0KICAgICAgICAgICAgICAgICAgPC9Ub3RhbGVzPg0KICAgICAgICAgICAgPC9FbmNhYmV6YWRvPg0KICAgICAgICAgICAgPERldGFsbGU+DQogICAgICAgICAgICAgICAgICA8TnJvTGluRGV0PjE8L05yb0xpbkRldD4NCiAgICAgICAgICAgICAgICAgIDxObWJJdGVtPkNpbnRhIFZIUyAxLzI8L05tYkl0ZW0+DQogICAgICAgICAgICAgICAgICA8RHNjSXRlbT5EZXNjcmlwY2nDs24gY29uIHNhbHRvIGRlIGxpbmVhbGluZWEgMWxpbmVhIDJsaW5lYSAzZmluPC9Ec2NJdGVtPg0KICAgICAgICAgICAgICAgICAgPFF0eUl0ZW0+MS4wPC9RdHlJdGVtPg0KICAgICAgICAgICAgICAgICAgPFByY0l0ZW0+ODE5LjA8L1ByY0l0ZW0+DQogICAgICAgICAgICAgICAgICA8TW9udG9JdGVtPjgxOTwvTW9udG9JdGVtPg0KICAgICAgICAgICAgPC9EZXRhbGxlPg0KICAgICAgPC9Eb2N1bWVudG8+DQo8L0RURT4=</Content>"
							+ "<Content>"+encodedFile+"</Content>"
							+ "<ResolutionDate>2019-01-01</ResolutionDate>"
							+ "<ResolutionNumber>80</ResolutionNumber>"
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
				//este codigo es para guardar la informacion de respuesta en la boleta.           
				invoice.set_CustomColumn("DescriptionGDE",ResponseText);
				invoice.saveEx(invoice.get_TrxName());
			}
	        catch (Exception e)
			{
	        	e.printStackTrace();
			}
		}
        catch(Exception e)
        {
            log.severe((new StringBuilder()).append("CreateXML: ").append(mylog).append("--").append(e.getMessage()).toString());
            return (new StringBuilder()).append("CreateXML: ").append(mylog).append("--").append(e.getMessage()).toString();
        }           
        return "XML CG Generated "+ wsRespuesta;
    }	
}	//	InvoiceCreateInOut
