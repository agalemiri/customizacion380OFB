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

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
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
import javax.xml.transform.OutputKeys;

import javax.xml.transform.TransformerException;

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
import java.io.BufferedInputStream;
import java.io.StringWriter;

 

import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.codec.binary.Base64; 
import org.apache.commons.httpclient.HttpClient;
import org.ofb.process.CXFConnector;


import org.compiere.model.MOrg;
import org.compiere.model.X_CC_Hospitalization;
import org.compiere.model.X_MED_LabDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


//url
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashMap;





/**
 *	Generate XML Consolidated from Invoice (Generic) 
 *	
 *  @author Italo Ni�oles ininoles
 *  @version $Id: ExportDTEInvoiceCG.java,v 1.2 19/05/2011 $
 */
public class XMLVentaBonoInterfaz extends SvrProcess
{	
	/** Properties						*/
	private Properties 		m_ctx;	
	private int p_CC_Hosp_ID = 0;
	public String urlPdf = "";
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		p_CC_Hosp_ID=getRecord_ID();
		m_ctx = Env.getCtx();
	}	//	prepare

	
	/**
	 * 	Create Shipment
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		X_CC_Hospitalization hosp=new X_CC_Hospitalization(m_ctx,p_CC_Hosp_ID,get_TrxName());
		String msg = "";
		msg=CreateXMLVentaBoleta(hosp);		
		return msg;
	}	//	doIt
	
	public String CreateXMLVentaBoleta(X_CC_Hospitalization hosp)
    {	
		String wsRespuesta = "";
        /*MDocType doc = new MDocType(invoice.getCtx(), invoice.getC_DocTypeTarget_ID(), invoice.get_TrxName());
        if(doc.get_Value("CreateXML") == null)
            return "";
        if(!((Boolean)doc.get_Value("CreateXML")).booleanValue())
            return "";
        int typeDoc = Integer.parseInt((String)doc.get_Value("DocumentNo"));
        if(typeDoc == 0)
            return "";*/
        String mylog = new String();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try
        {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            //Document document = implementation.createDocument(null, "Envelope", null);
            Document document = implementation.createDocument("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Envelope", null);
            //document.
            document.setXmlVersion("1.0");            
            document.setTextContent("text/xml");
            Element root = document.getDocumentElement();
            root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
            root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:soapenc", "http://schemas.xmlsoap.org/soap/encoding/");
            root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:urn", "WsInterfaz");
            Attr atr = document.createAttribute("xmlns:xsi");
            atr.setValue("http://www.w3.org/2001/XMLSchema-instance");     
            Attr atr2 = document.createAttribute("xmlns:xsd");
            atr2.setValue("http://www.w3.org/2001/XMLSchema");
            Attr atr3 = document.createAttribute("xmlns:soapenv");
            atr3.setValue("http://schemas.xmlsoap.org/soap/envelope/");
            Attr atr4 = document.createAttribute("xmlns:urn");
            atr4.setValue("urn:WsInterfaz");
            Attr atr5 = document.createAttribute("xmlns:soapenc");
            atr5.setValue("http://schemas.xmlsoap.org/soap/encoding/");
            
            MOrg company = MOrg.get(hosp.getCtx(), hosp.getAD_Org_ID());

            Element Encabezado = document.createElement("soapenv:Header");
            document.getDocumentElement().appendChild(Encabezado);
            

            Element Body = document.createElement("soapenv:Body");
            document.getDocumentElement().appendChild(Body);
            
            Element Documento = document.createElement("urn:VtaBonInterMul");
            //document.getDocumentElement().appendChild(Documento);
            Body.appendChild(Documento);
            Documento.setAttribute("encodingStyle", "http://schemas.xmlsoap.org/soap/encoding/");
            //Element IdDoc = document.createElement("IdDoc");
            //Encabezado.appendChild(IdDoc);
            mylog = "IdDoc";
            
            //mfrojas
            
            int user_id = Env.getAD_User_ID(hosp.getCtx());
            MUser user = new MUser(hosp.getCtx(), user_id, hosp.get_TrxName());
            MBPartner bpuser = new MBPartner(hosp.getCtx(),user.getC_BPartner_ID(),hosp.get_TrxName());
            String valueuser = bpuser.getValue().concat("-").concat(bpuser.get_Value("digito").toString());
            //String valueuser = "15753162-K";
            log.config("valueuser "+valueuser);
            
            Element CodUsuario = document.createElement("CodUsuario");
            org.w3c.dom.Text text = document.createTextNode("96790040-0");
            CodUsuario.appendChild(text);
            Documento.appendChild(CodUsuario);
            
            Element CodClave = document.createElement("CodClave");
            CodClave.setAttribute("type", "xsd:string");
            org.w3c.dom.Text cl = document.createTextNode("96790040");
            CodClave.appendChild(cl);
            Documento.appendChild(CodClave);
            
            Element CodFinanciador = document.createElement("CodFinanciador");
            org.w3c.dom.Text financ = document.createTextNode("1");
            CodFinanciador.setAttribute("type", "xsd:int");
            CodFinanciador.appendChild(financ);
            Documento.appendChild(CodFinanciador);
          
            Element CodLugar = document.createElement("CodLugar");
            org.w3c.dom.Text lugar = document.createTextNode("19086");
            CodLugar.setAttribute("type", "xsd:int");
            CodLugar.appendChild(lugar);
            Documento.appendChild(CodLugar);

            Element UrlRetExito = document.createElement("UrlRetExito");
            //org.w3c.dom.Text urlexito = document.createTextNode("http://interfaz4.bonoelectronico.cl/UrlRetExito.php");
            org.w3c.dom.Text urlexito = document.createTextNode("http://interfaz4pre.bonoelectronico.cl/UrlRetExito.php");
            UrlRetExito.appendChild(urlexito);
            Documento.appendChild(UrlRetExito);
            
            Element UrlRetError = document.createElement("UrlRetError");
            //org.w3c.dom.Text urlerror = document.createTextNode("http://interfaz4.bonoelectronico.cl/UrlRetError.php");
            org.w3c.dom.Text urlerror = document.createTextNode("http://interfaz4pre.bonoelectronico.cl/UrlRetError.php");
            UrlRetError.appendChild(urlerror);
            Documento.appendChild(UrlRetError);
            //Listado de convenios.
            
            Element LisConvenios = document.createElement("ListConvenios");
            org.w3c.dom.Text listconv = document.createTextNode("");
            LisConvenios.appendChild(listconv);
            Documento.appendChild(LisConvenios);

            
        	Element LisConveniosType = document.createElement("ListConveniosType");
        	LisConvenios.appendChild(LisConveniosType);
            
            Element RutConvenio = document.createElement("RutConvenio");
            org.w3c.dom.Text rconv = document.createTextNode("96790040-0");
            RutConvenio.setAttribute("type", "xsd:string");
            RutConvenio.appendChild(rconv);
            LisConveniosType.appendChild(RutConvenio);
            
            Element CorrConvenio = document.createElement("CorrConvenio");
            org.w3c.dom.Text cconv = document.createTextNode("0");
            CorrConvenio.appendChild(cconv);
            LisConveniosType.appendChild(CorrConvenio);
             
            //rut tratante.
            
            int idusertratante = hosp.get_ValueAsInt("AD_User2_ID");
            MUser usertratante = new MUser(hosp.getCtx(),idusertratante,hosp.get_TrxName());
            MBPartner bptratante = new MBPartner(hosp.getCtx(),usertratante.getC_BPartner_ID(), hosp.get_TrxName());
            String ruttratante = bptratante.getValue();
            ruttratante = ruttratante.concat("-").concat(bptratante.get_Value("digito").toString());

            //solicitante
            MBPartner bpsolicitante = new MBPartner(hosp.getCtx(),hosp.getC_BPartner_ID(), hosp.get_TrxName());
            String rutsolicitante = bpsolicitante.getValue().concat("-").concat(bpsolicitante.get_Value("digito").toString());

            Element RutTratante = document.createElement("RutTratante");
            org.w3c.dom.Text rutt = document.createTextNode(ruttratante);
            RutTratante.appendChild(rutt);
            LisConveniosType.appendChild(RutTratante);

            Element RutSolicitante = document.createElement("RutSolic");
            org.w3c.dom.Text ruts = document.createTextNode(rutsolicitante);
            RutSolicitante.appendChild(ruts);
            LisConveniosType.appendChild(RutSolicitante);
            
            Element NomSolic = document.createElement("NomSolic");
            org.w3c.dom.Text noms = document.createTextNode(bpsolicitante.getName());
            NomSolic.appendChild(noms);
            LisConveniosType.appendChild(NomSolic);
            
            Element CodEspecia = document.createElement("CodEspecia");
            org.w3c.dom.Text cods = document.createTextNode("1");
            CodEspecia.appendChild(cods);
            LisConveniosType.appendChild(CodEspecia);
            
            Element RutBenef = document.createElement("RutBenef");
            org.w3c.dom.Text rutb = document.createTextNode(rutsolicitante);
            RutBenef.appendChild(rutb);
            LisConveniosType.appendChild(RutBenef);
            
            Element RutCajero = document.createElement("RutCajero");
            org.w3c.dom.Text rutc = document.createTextNode(valueuser);
            RutCajero.appendChild(rutc);
            LisConveniosType.appendChild(RutCajero);
            
            Element IndUrgencia = document.createElement("IndUrgencia");
            org.w3c.dom.Text indu = document.createTextNode("N");
            IndUrgencia.appendChild(indu);
            LisConveniosType.appendChild(IndUrgencia);
            
            Element CodTipoTratamiento = document.createElement("CodTipoTratamiento");
            org.w3c.dom.Text codtt = document.createTextNode("0");
            CodTipoTratamiento.appendChild(codtt);
            LisConveniosType.appendChild(CodTipoTratamiento);
            
            Element FecIniTratamiento = document.createElement("FecIniTratamiento");
            org.w3c.dom.Text fecit = document.createTextNode("19000101");
            FecIniTratamiento.appendChild(fecit);
            LisConveniosType.appendChild(FecIniTratamiento);
        
            Element FecTerTratamiento = document.createElement("FecTerTratamiento");
            org.w3c.dom.Text fectt = document.createTextNode("19000101");
            FecTerTratamiento.appendChild(fectt);
            LisConveniosType.appendChild(FecTerTratamiento);

            Element CantDias = document.createElement("CantDias");
            org.w3c.dom.Text cant = document.createTextNode("1");
            CantDias.appendChild(cant);
            LisConveniosType.appendChild(CantDias);
            
            Element FolioAntecedente = document.createElement("FolioAntecedente");
            org.w3c.dom.Text folio = document.createTextNode("0");
            FolioAntecedente.appendChild(folio);
            LisConveniosType.appendChild(FolioAntecedente);
            
            mylog = "Prestaciones";
            String sqlmedlab = "SELECT MED_LabDetail_ID from MED_LabDetail WHERE CC_Hospitalization_ID = "+hosp.get_ID();
            
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            
            pstmt = DB.prepareStatement(sqlmedlab, hosp.get_TrxName());
            rs = pstmt.executeQuery();
            
            Element LisPrestAut = document.createElement("LisPrestAut");
            org.w3c.dom.Text listpaut = document.createTextNode("");
            LisPrestAut.appendChild(listpaut);
            LisConveniosType.appendChild(LisPrestAut);
            
            while(rs.next())
            {
            	X_MED_LabDetail medlab = new X_MED_LabDetail (hosp.getCtx(),rs.getInt("MED_LabDetail_ID"),hosp.get_TrxName());
            	Element LisPrestAutType = document.createElement("LisPrestAutMulType");
            	LisPrestAut.appendChild(LisPrestAutType);
            	
            	Element CodPrestacion = document.createElement("CodPrestacion");
                org.w3c.dom.Text codpres = document.createTextNode(medlab.getM_Product().getValue());
                CodPrestacion.appendChild(codpres);
                LisPrestAutType.appendChild(CodPrestacion);
            	
            	Element CodItem = document.createElement("CodItem");
                org.w3c.dom.Text coditem = document.createTextNode("0");
                CodItem.appendChild(coditem);
                LisPrestAutType.appendChild(CodItem);
                
            	Element Cantidad = document.createElement("Cantidad");
                org.w3c.dom.Text cantidad = document.createTextNode(medlab.getQty().toString());
                Cantidad.appendChild(cantidad);
                LisPrestAutType.appendChild(Cantidad);
                
            	Element RecargoHora = document.createElement("RecargoHora");
                org.w3c.dom.Text recargo = document.createTextNode(Env.ZERO.toString());
                RecargoHora.appendChild(recargo);
                LisPrestAutType.appendChild(RecargoHora);
                
            	Element MtoTotal = document.createElement("MontoPrest");
                org.w3c.dom.Text montot = document.createTextNode(String.valueOf(medlab.getAmount().intValue()));
                MtoTotal.appendChild(montot);
                LisPrestAutType.appendChild(MtoTotal);
                
            	Element InfAdicional = document.createElement("InfAdicional");
                org.w3c.dom.Text infoa = document.createTextNode(medlab.getM_Product().getValue());
                InfAdicional.appendChild(infoa);
                LisPrestAutType.appendChild(InfAdicional);
            	
            }
            //ininoles nueva referencia de guia de despacho
      
            
	         /*String ExportDir = (String)company.get_Value("ExportDir");
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
	          */
	          //ExportDir = ExportDir.replace("\\", "/");
	          javax.xml.transform.Source source = new DOMSource(document);
	          //javax.xml.transform.Result result = new StreamResult(new File(ExportDir, (new StringBuilder()).append("1").append(".xml").toString()));
	          javax.xml.transform.Result console = new StreamResult(System.out);
	          Transformer transformer = TransformerFactory.newInstance().newTransformer();
	          transformer.setOutputProperty("indent", "yes");
	          transformer.setOutputProperty("encoding", "UTF-8");
	          //transformer.transform(source, result);
	          transformer.transform(source, console);
           
            
            //convertir a base 64                       
    		//File file = new File(ExportDir, (new StringBuilder()).append("1").append(".xml").toString());

    		//setear atributos de cabecera
    		//Document docValid = builder.parse(file);
    		//Element raiz = docValid.getDocumentElement();
    		//raiz.setAttribute("version", "1.0");

    		
    		
    		/*raiz.setAttribute("xmlns:soapenc", "http://schemas.xmlsoap.org/soap/encoding/");
    		raiz.setAttribute("xmlns:urn", "urn:WsInterfaz");
    		raiz.setAttribute("xmlns:soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
    		raiz.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
    		raiz.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
*/
    		
    		//se guarda nuevo xml
  /*          source = new DOMSource(docValid);
            result = new StreamResult(new File(ExportDir, (new StringBuilder()).append("1").append(".xml").toString()));
            console = new StreamResult(System.out);
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("encoding", "UTF-8");
            transformer.transform(source, result);
            transformer.transform(source, console);
    */        
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));
            
            log.config("veamos "+writer.getBuffer().toString());
            
            String xmlstring = writer.getBuffer().toString();
            
    		
            //codificacion a base64
    	/*	byte[] fileArray = new byte[(int) file.length()];
    		InputStream inputStream;
    		inputStream = new FileInputStream(file);
			inputStream.read(fileArray);    		
			
			log.config("filearray string "+raiz);
    		byte[] encoded = Base64.encodeBase64(fileArray);     
    		String encodedFile = "";
    		try 
    		{	
    			encodedFile = new String(encoded);
    		} catch (Exception e) {
    		}          
    		*/
   
    		//mfrojas
    		//Enlace con interfaz4 bonoelectronico
    		
    		
   			Source response = null;
   			//log.config("result "+result.toString());
			
   			log.config("preconn");
			try
			{				
				final CXFConnector wsc = new CXFConnector();					
				wsc.setSoapAction("urn:WsInterfaz");
				wsc.setRequest(xmlstring);
				wsc.setBinding(SOAPBinding.SOAP11HTTP_BINDING);
				//wsc.setEndpointAddress("http://interfaz4.bonoelectronico.cl/WsInterfaz.php");
				wsc.setEndpointAddress("http://interfaz4pre.bonoelectronico.cl/WsInterfaz.php");
				wsc.setServiceName("WsInterfaz");
				wsc.setPortName("WsInterfazSOAP");
				wsc.setTargetNS("urn:WsInterfaz");
				wsc.executeConnector();
				log.config("supprecon");
				response = wsc.getResponse(); 
				
			}
			catch(Exception e)
			{
				log.config("exception");
				throw new Exception("No se pudo conectar");
			}
				
				
			log.config("PASE");
			DocumentBuilderFactory.newInstance().newDocumentBuilder();
			SAXSource output = (SAXSource) response;
			Transformer tf = TransformerFactory.newInstance().newTransformer();
		
			log.config("PASE2");
			DOMResult resultmsg = new DOMResult();
			tf.transform(output, resultmsg);
			Document doc = (Document) resultmsg.getNode();
			
			log.config("PASE3");
			Node datospre = doc.getDocumentElement();
			//Node to string
			
			StringWriter sw = new StringWriter();
			
			  try {
			      Transformer t = TransformerFactory.newInstance().newTransformer();
			      t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			      t.setOutputProperty(OutputKeys.INDENT, "yes");
			      t.transform(new DOMSource(datospre), new StreamResult(sw));
			    } catch (TransformerException te) {
			      System.out.println("nodeToString Transformer Exception");
			    }
			  
			  log.config("stringggg "+sw.toString());
			  
			//Desde ahora, verificar si el error es 0 o es 1.
			String codError = findText(sw.toString(), "<CodError xsi:type=\"xsd:int\">", "</CodError>");
			log.config("coderror "+codError);
			
			if(codError.compareTo("1") == 0)
			{
				//Hay error, se debe indicar el error
				return findText(sw.toString(), "<GloError xsi:type=\"xsd:string\">", "</GloError>");
				
			}
			else
			{
				//El error es 1, por lo que tenemos cod  auditoria y nro transaccion
				
				String audit =  findText(sw.toString(), "<CodAuditoria xsi:type=\"xsd:string\">", "</CodAuditoria>");
				String transaccion =  findText(sw.toString(), "<NumTransac xsi:type=\"xsd:int\">", "</NumTransac>");
				log.config("audit "+audit+" transac "+transaccion);
				
				//String post_url = "http://interfaz4.bonoelectronico.cl/login.php";
				String post_url = "http://interfaz4pre.bonoelectronico.cl/login.php";
				String user_agent = "Mozilla/5.0";
				String params = "NumTransac="+transaccion+"&NroAuditoria="+audit;
				log.config("parametros "+params);
				
				//SEND POST
			    
			    //	mfrojas apache
			    
	/*			String stringhttpresponse = "";
				CloseableHttpClient httpclient = HttpClients.createDefault();
				try {

					HttpUriRequest httppost = RequestBuilder.post()
							.setUri(new URI("http://interfaz4.bonoelectronico.cl/login.php"))
							.addParameter("NroAuditoria", audit)
							.addParameter("NumTransac", transaccion)
							.build();

					CloseableHttpResponse responsehttp = httpclient.execute(httppost);
					log.config("responsehttp "+responsehttp.toString());
					
					stringhttpresponse = responsehttp.toString();
					try {
						System.out.println(EntityUtils.toString(responsehttp.getEntity()));
					} finally {
						responsehttp.close();
					}
				} finally {
					httpclient.close();
				}
		    
				
				int subsap = stringhttpresponse.toString().indexOf("index.php");
				log.config("subsint "+subsap);
				if(subsap != -1)
				{
					String subsapst = stringhttpresponse.toString().substring(subsap);
				
					log.config("substring = "+subsapst);
				
					//se busca donde esta la coma
				
					int subsapcoma = subsapst.indexOf(",");
					String subs2apst = subsapst.substring(0,subsapcoma);
				
					log.config("subs2apst "+subs2apst);

					if(subs2apst.compareTo("") != 0)
						post_url = "http://interfaz4.bonoelectronico.cl/"+subs2apst;
					
				}
			    //mfrojas apache
*/
				
				URL obj = new URL(post_url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("User-Agent", user_agent);
                con.setRequestProperty("Accept", "application/xml");
                con.setRequestProperty("Content-Type","application/xml; charset=utf-8");
				
				con.setDoOutput(true);
				con.setUseCaches(false);
				OutputStream os = con.getOutputStream();
				os.write(params.getBytes());
				
				
				os.flush();
				os.close();
				
				

			    
				int responseCode = con.getResponseCode();
				log.config("response code "+responseCode);
				String subs3 = "";
				if (responseCode == HttpURLConnection.HTTP_OK) 
				{ //success
					BufferedReader in = new BufferedReader(new InputStreamReader(
							con.getInputStream()));
					String inputLine;
					StringBuffer responsebuf = new StringBuffer();
	                          

	                				
					while ((inputLine = in.readLine()) != null) 
					{
						responsebuf.append(inputLine);
					}
					in.close();

					// print result
					//System.out.println(responsebuf.toString());
					log.config("responsebuf "+responsebuf.toString());
					
					//Aca esta el indice desde donde empieza transactionname
					//Ahora necesitamos que el string sea desde ahi hasta el final.
					
	/*				int subsint = responsebuf.toString().indexOf("transactionName");
					log.config("subsint "+subsint);
					if(subsint != -1)
					{String subs = responsebuf.toString().substring(subsint);
					log.config("substring = "+subs);
					
					//se busca donde estan los dos puntos
					
					int subsintptos = subs.indexOf(":");
					String subs2 = subs.substring(subsintptos);
					
					//en subs2 parte desde los 2 puntos. Se necesita quitar las comillas
					subs2 = subs2.replace("\"", "");
					log.config("subs2 ="+subs2);
					 
					//cortar en queueTime
					
					int subsintqueue = subs2.indexOf("queueTime");
					subs3 = subs2.substring(0,subsintqueue);
					
					log.config("subs3 = "+subs3);
					
					//quitar los : y la ,
					
					subs3 = subs3.replace(",","");
					subs3 = subs3.replace(":","");
					
					log.config("subs3 final = "+subs3);
					}
					*/
					String updatesql = "UPDATE cc_hospitalization SET nrotransac = '"+transaccion+"', NroAudit='"+audit+"' WHERE " +
							" cc_hospitalization_id = "+p_CC_Hosp_ID;		
					DB.executeUpdate(updatesql, get_TrxName());
					
					
					
					//reemplazamos post_url

					
					//desktop support
				/*	if(Desktop.isDesktopSupported())
					{
						Desktop desk = Desktop.getDesktop();
						try
						{
							desk.browse(new URI(post_url));
							
							
						}catch(IOException | URISyntaxException e)
						{
							e.printStackTrace();
						}
					}
					
					*/
					try
					{
						Process p = Runtime.getRuntime().exec("D:\\OFBHuella.exe \""+audit+"\" \""+transaccion+"\" \"D:\\descarga.htm\"");
					}
					catch(Exception exc)
					{
						throw new AdempiereException("No se encuentra ejecutable / ruta");
					}
				} 
				else 
				{
					System.out.println("POST no funciona");
					log.config("no funciona");
				}
						
			}
			

    		
    	}
        catch(Exception e)
        {
            log.severe((new StringBuilder()).append("CreateXML: ").append(mylog).append("--").append(e.getMessage()).toString());
            return (new StringBuilder()).append("CreateXML: ").append(mylog).append("--").append(e.getMessage()).toString();
        }       
        
    
        
        return "Generado "+ wsRespuesta;
    }	
        
   /* private String llamarWSToken(String rutEmpresa,String rutPersona,String password) throws Exception
    {
    	String token = "";	
    	final String request = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wst=\"/var/www//WsTest/\"> " +
    			"<soapenv:Header/>"+
    			"<soapenv:Body>"+    			
    				"<wst:SolicitarSesion soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"+
    					"<rutEmpresa xsi:type=\"xsd:string\">"+rutEmpresa+"</rutEmpresa>"+
    					"<rutPersona xsi:type=\"xsd:string\">"+rutPersona+"</rutPersona>"+
    					"<password xsi:type=\"xsd:string\">"+password+"</password>"+
    				"</wst:SolicitarSesion>"+
    			"</soapenv:Body>"+
    			"</soapenv:Envelope>";

    			Source response = null;
    					
    			try
    			{				
    				final CXFConnector wsc = new CXFConnector();					
    				wsc.setSoapAction("http://clientes2.dtefacturaenlinea.cl/WsFEL/wsFEL.php/SolicitarSesion");
    				wsc.setRequest(request);
    				wsc.setBinding(SOAPBinding.SOAP11HTTP_BINDING);
    				wsc.setEndpointAddress("http://clientes2.dtefacturaenlinea.cl/WsFEL/wsFEL.php");
    				wsc.setServiceName("DTElectronico");
    				wsc.setPortName("DTElectronicoPort");
    				wsc.setTargetNS("/var/www//WsTest/");
    				wsc.executeConnector();
    				response = wsc.getResponse(); 
    				
    			}
    			catch(Exception e)
    			{
    				throw new Exception("No se ha podido establecer conexion con el Servicio de Facturacion");
    			}
    				
    				
    			try
    			{
    				//comienza la lectura del xml recibido
    				DocumentBuilderFactory.newInstance().newDocumentBuilder();
    				SAXSource output = (SAXSource) response;
    				Transformer tf = TransformerFactory.newInstance().newTransformer();
    			
    				DOMResult result = new DOMResult();
    				tf.transform(output, result);
    				Document doc = (Document) result.getNode();
    				
    				Node datos = findReturn(doc.getChildNodes().item(0)); 
    									
    				if(datos!=null){
    					NodeList att = datos.getChildNodes(); 
    					for(int x=0;x<att.getLength();x++)
    					{	
    						log.config(att.item(x).getLocalName());
    						if(att.item(x).getLocalName().equals("return") || att.item(x).getNodeName().equals("return"))
    							token=att.item(x).getFirstChild().getNodeValue();
    					} // fin for return
    				}//FIN DATOS
    			}
    			catch(Exception e)
    			{
    				e.printStackTrace();
    			}
    		return token;    			
    }  */
  
    public Node findReturn(Node node) {
		Node value = null;
		NodeList list = node.getChildNodes();
		for (int i=0; i<list.getLength(); i++) {
			// Get child node
			Node childNode = list.item(i);
			if(childNode.getNodeName().equals(("ns1:SolicitarSesionResponse")))
			{
				value = childNode;
				break;
			}

			value=findReturn(childNode);

		}
		return value;
    }
    public Node findError(Node node) {
		Node value = null;
		log.config("finderror-1");

		NodeList list = node.getChildNodes();
		log.config("finderror");
		for (int i=0; i<list.getLength(); i++) {
			// Get child node
			Node childNode = list.item(i);
			log.config("finderror2");
			log.config("childnode "+childNode.getNodeName());
			if(childNode.getNodeName().contains(("CodError")))
			{
				value = childNode;
				log.config("nodename "+childNode.getNodeName());
				break;
			}

			value=findError(childNode);

		}
		
		log.config("termine ");
		log.config("ultimo node "+value.getNodeName() +" node blabla "+value.getNodeValue());
		return value;
    }

    public Node findReturnEnviarDTE(Node node) {
		Node value = null;
		NodeList list = node.getChildNodes();
		for (int i=0; i<list.getLength(); i++) {
			// Get child node
			Node childNode = list.item(i);
			if(childNode.getNodeName().equals(("ns1:EnviarDTEResponse")))
			{
				value = childNode;
				break;
			}

			value=findReturnEnviarDTE(childNode);

		}
		return value;
    }
    public String findText(String cadena, String fragmentoIni, String fragmentoFin)
    {
    	String newText = "";
    	boolean flag;
    	int indiceIni = 0;
    	int indiceFin = 0;
    	
    	flag = cadena.contains(fragmentoIni);
    	
    	if (flag)    		
    	{
    		indiceIni = cadena.indexOf(fragmentoIni);
    		indiceIni = indiceIni + fragmentoIni.length();    		
    		indiceFin = cadena.indexOf(fragmentoFin);
    		newText = cadena.substring(indiceIni, indiceFin);
    	}
    	else
    		newText = "Error - No existe Cadena Especificada";    	
    	
    	return newText;
    }
    
    
	
}	//	InvoiceCreateInOut
