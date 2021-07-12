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
package org.acies.process;

import java.util.Properties;

import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.model.MProduct;



import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.ws.soap.SOAPBinding;


import org.ofb.process.CXFConnector;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *	
 *	
 *  @author mfrojas
 *  @version $Id: ProcessACIESsetProduct.java $
 *  set Producto on ACIES
 */

public class ProcessACIESsetProduct extends SvrProcess
{
	//private String			p_DocStatus = null;
	private int				p_Product_ID = 0; 
	private String				p_Action = "PR";
	private Properties 		m_ctx;	

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	 protected void prepare()
	{
			p_Product_ID=getRecord_ID();
			m_ctx = Env.getCtx();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (p_Product_ID > 0)
		{
	    	String token = "";	
	    	MProduct prod = new MProduct(getCtx(),p_Product_ID, get_TrxName());
	    	final String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.ws.server.sinu.acies.com/\"> "
	    	   + "<soapenv:Header/>"
	    	   + "<soapenv:Body>"
	    	   + "   <ser:setFinConcepto>"
	    	   + "      <nitEmpresa>UTC9903183C7</nitEmpresa>"
	    	   + "      <codConcepto>"+prod.getValue()+"</codConcepto>"
	    	   + "      <nomConcepto>"+prod.getName()+"</nomConcepto>"
	    	   + "      <tipConcepto>1</tipConcepto>"
	    	   + "      <indFinanciable>1</indFinanciable>"
	    	   + "      <!--Optional:-->"
	    	   + "      <obsConcepto></obsConcepto>"
	    	   + "   </ser:setFinConcepto>"
	    	   + "</soapenv:Body>"
	    	   + "</soapenv:Envelope>";

	    			Source response = null;
	    					
	    			try
	    			{				
	    				final CXFConnector wsc = new CXFConnector();					
	    				wsc.setSoapAction("http://mantis.acies.com.co:80/sinuWSFin/FinConceptoService");
	    				wsc.setRequest(request);
	    				wsc.setBinding(SOAPBinding.SOAP11HTTP_BINDING);
	    				wsc.setEndpointAddress("http://mantis.acies.com.co:80/sinuWSFin/FinConceptoService");
	    				wsc.setServiceName("FinConceptoService");
	    				wsc.setPortName("FinConceptoService");
	    				wsc.setTargetNS("http://service.ws.server.sinu.acies.com/");
	    				wsc.executeConnector();
	    				response = wsc.getResponse(); 
	    				
	    			}
	    			catch(Exception e)
	    			{
	    				throw new Exception("No se ha podido establecer conexion con el Servicio");
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

		}

		return "no";
	}	//	doIt
	
	public Node findReturn(Node node) {
		Node value = null;
		NodeList list = node.getChildNodes();
		for (int i=0; i<list.getLength(); i++) {
			// Get child node
			Node childNode = list.item(i);
			if(childNode.getNodeName().equals(("return")))
			{
				value = childNode;
				break;
			}

			value=findReturn(childNode);

		}
		return value;
	}
}
