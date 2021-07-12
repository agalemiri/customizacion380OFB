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

import java.math.BigDecimal;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.ws.soap.SOAPBinding;

import org.compiere.util.*;
import org.ofb.process.CXFConnector;
import org.compiere.model.*;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *	Generate XML Consolidated from Invoice (Generic) 
 *	
 *  @author Italo Ni�oles ininoles
 *  @version $Id: WSTecPedidoEntrada.java,v 1.2 15/06/2020 $
 */
public class WSTecReposicion extends SvrProcess
{	
	/** Properties						*/
	//private Properties 		m_ctx;	
	//private int p_MWarehouse = 0;
	private int p_MLocator = 0;
	private int p_MLocatorTo = 0;
	public String urlPdf = "";
	public MMovement mov = null;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("M_Locator_ID"))
			{
				p_MLocator = para[i].getParameterAsInt();
				p_MLocatorTo = para[i].getParameter_ToAsInt();
			}
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		//p_M_Requisition_ID=getRecord_ID();
		//m_ctx = Env.getCtx();
	}	//	prepare

	
	/**
	 * 	Create Shipment
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		String msg = "";
		mov = new MMovement(getCtx(), 0, get_TrxName());
		mov.setC_DocType_ID(2000114);
		mov.saveEx(get_TrxName());
		msg = llamarWSReposicion();
		return msg;
	}	//	doIt
	 
    private String llamarWSReposicion() throws Exception
    {
     	String codigo="";
     	String qtyStr="1";
     	int ID_Product=0;
     	BigDecimal qty =Env.ZERO;
     	String obs = "";
     	MMovementLine line = null;
     	//boolean flag = false;
    	/*String request = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns=\"http://ecentia.cl\">"+
    			"<soapenv:Body>"+
    				"<ecen:Reposicion>"+
    					"<ecen:sToken>123456</ecen:sToken>"+
    				"</ecen:Reposicion>"+
    			"</soapenv:Body>"+
    			"</soap:Envelope>";*/
     	String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ecen=\"http://ecentia.cl\">"+
    			"<soapenv:Header/>"+
    			"<soapenv:Body>"+
    				"<ecen:Reposicion>"+
    					"<!--Optional:-->"+
    					"<ecen:sToken>123456</ecen:sToken>"+
    				"</ecen:Reposicion>"+
				"</soapenv:Body>"+
				"</soapenv:Envelope>";     	

		Source response = null;		
		log.config("LLAMADA: "+request);
		try
		{				
			final CXFConnector wsc = new CXFConnector();					
			wsc.setSoapAction("http://ecentia.cl/Reposicion");
			wsc.setRequest(request);
			wsc.setBinding(SOAPBinding.SOAP11HTTP_BINDING);
			wsc.setEndpointAddress("http://192.168.15.160:8081/sga.asmx");
			//wsc.setEndpointAddress("http://200.113.48.218:8081/sga.asmx");
			wsc.setServiceName("sga");
			wsc.setPortName("sgaSoap");
			wsc.setTargetNS("http://ecentia.cl");
			wsc.executeConnector();
			response = wsc.getResponse(); 			
		}
		catch(Exception e)
		{
			throw new Exception("No se ha podido establecer conexion con el Servicio. "+e.toString());
		}				
		try
		{
			DocumentBuilderFactory.newInstance().newDocumentBuilder();
			SAXSource output = (SAXSource) response;
			Transformer tf = TransformerFactory.newInstance().newTransformer();
		
			DOMResult result = new DOMResult();
			tf.transform(output, result);
			Document doc = (Document) result.getNode();
			log.config("Antes de datos"+doc.toString());
			Node datos = findReturn(doc.getChildNodes().item(0)); 
			log.config("Antes de datos nulos"+datos.toString());
			if(datos!=null)
			{
				NodeList att = datos.getChildNodes(); 
				for(int x=0;x<att.getLength();x++)
				{	
					codigo = "";
					qtyStr = "1.0";
					ID_Product=0;
					qty = Env.ZERO;
					log.config("nombres items nodos "+att.item(x).getLocalName());
					if(att.item(x).getLocalName().equals("Reposicion") || att.item(x).getNodeName().equals("Reposicion"))
					{	
						//Node detalle = findReturnLevel2(att.item(x));
						Node detalle = att.item(x);
						if(detalle != null)
						{
							NodeList attDet = detalle.getChildNodes(); 
							for(int a=0;a<attDet.getLength();a++)
							{	
								log.config("nombres items nodos2 "+attDet.item(a).getLocalName());
								if(attDet.item(a).getLocalName().equals("Codigo") || attDet.item(a).getNodeName().equals("Codigo"))
								{
									codigo = attDet.item(a).getFirstChild().getNodeValue();;
									ID_Product = DB.getSQLValue(get_TrxName(), 
											"SELECT M_Product_ID FROM M_Product WHERE Value like '"+codigo+"'");
									log.config("codigo producto = "+codigo+"ID: "+ID_Product);
								}
								else if(attDet.item(a).getLocalName().equals("Cantidad") || attDet.item(a).getNodeName().equals("Cantidad"))
								{
									qtyStr=attDet.item(a).getFirstChild().getNodeValue();
									if(qtyStr == null)
										qtyStr = "1.0";	
									try 
									{
										qty = new BigDecimal(qtyStr);
									} catch (Exception e) {
										qty = Env.ONE;
									}	
									log.config("cantidad calculada: "+qty);
								}
								else if(attDet.item(a).getLocalName().equals("Descripcion") || attDet.item(a).getNodeName().equals("Descripcion"))
								{
									obs = attDet.item(a).getFirstChild().getNodeValue();
									log.config("OBSERVACION: "+obs);
								}
							}	
							log.config("antes de validar producto");
							if(ID_Product > 0)
							{
								log.config("dentro de if de producto ");
								line = new MMovementLine(mov);
								line.setAD_Org_ID(mov.getAD_Org_ID());
								line.setM_Locator_ID(p_MLocator);
								line.setM_LocatorTo_ID(p_MLocatorTo);
								line.setM_Product_ID(ID_Product);
								line.setMovementQty(qty);
								line.setDescription(obs);
								line.saveEx(get_TrxName());
							}	
						}
					}
				} // fin for return
			}//FIN DATOS
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "PROCESADO";    			
    }  
    public Node findReturn(Node node) 
    {
    	log.config("dentro de findreturn");
		Node value = null;
		NodeList list = node.getChildNodes();
		log.config("cantidad de nodos hijos: "+list.getLength());
		for (int i=0; i<list.getLength(); i++) {
			// Get child node
			Node childNode = list.item(i);
			log.config("nombre elemento: "+childNode.getNodeName());
			if(childNode.getNodeName().equals(("ReposicionResult")))
			{
				value = childNode;
				break;
			}
			value=findReturn(childNode);
		}
		return value;
    }
    public Node findReturnLevel2(Node node) 
    {
    	log.config("dentro de findreturnLevel2 ");
		Node value = null;
		NodeList list = node.getChildNodes();
		log.config("cantidad de nodos hijosL2: "+list.getLength());
		for (int i=0; i<list.getLength(); i++) 
		{
			// Get child node
			Node childNode = list.item(i);
			log.config("nombre elementol2: "+childNode.getNodeName());
			if(childNode.getNodeName().equals(("Reposicion")))
			{
				value = childNode;
				break;
			}
			value=findReturnLevel2(childNode);
		}
		return value;
    }
}	//	InvoiceCreateInOut
