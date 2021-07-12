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
public class WSTecPedidoEntrada extends SvrProcess
{	
	/** Properties						*/
	//private Properties 		m_ctx;	
	private int p_M_Movement_ID = 0;
	public String urlPdf = "";
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		p_M_Movement_ID=getRecord_ID();
		//m_ctx = Env.getCtx();
	}	//	prepare

	
	/**
	 * 	Create Shipment
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		MMovement mov = new MMovement(Env.getCtx(),p_M_Movement_ID,get_TrxName());
		String msg = "";
		msg = llamarWSPedidoEntrada(mov);
		return msg;
	}	//	doIt
	 
    private String llamarWSPedidoEntrada(MMovement mov) throws Exception
    {
    	String DateStr = mov.getMovementDate().getDate()+"/"+(mov.getMovementDate().getMonth()+1)+"/"+
    			(mov.getMovementDate().getYear()+1900)+" "+mov.getMovementDate().getHours()+":"+mov.getMovementDate().getMinutes();
    	String mensaje = "";	
    	String request = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns=\"http://ecentia.cl\">"+
    			"<soap:Body>"+
	    			"<PedidoEntrada>"+
			        "<idPedidoEntrada>2</idPedidoEntrada>"+
			        "<codigoOperacion>1</codigoOperacion>"+
			        "<folio>"+mov.getDocumentNo()+"</folio>"+
			        "<fechaHora>"+DateStr+"</fechaHora>"+
			        "<idSistema>SISTEMA HOSPITAL</idSistema>"+
			        "<detallePedidoEntrada>";
    	//se crea ciclo de lectura de lineas
    	
    	MMovementLine[] lines = mov.getLines(true);
		for (MMovementLine line : lines)
		{
			if (line.getM_Product_ID()>0)
			{
				if(line.getM_LocatorTo().getM_Warehouse().getDescription() != null
						&& line.getM_LocatorTo().getM_Warehouse().getDescription().compareToIgnoreCase("carrusel")==0)
				{
					request = request+"<DetalleEntrada>"+
			              "<idArticulo>"+line.getM_Product().getValue()+"</idArticulo>"+
			              "<cantidad>"+line.getMovementQty().intValue()+"</cantidad>"+
			              "<descArticulo>"+line.getM_Product().getName()+"</descArticulo>"+
			           "</DetalleEntrada>";
				}
			}
		}		
		request = request+"</detallePedidoEntrada>"+
			        "<Token>123456</Token>"+
			     "</PedidoEntrada>"+
			   "</soap:Body>"+ 
			   "</soap:Envelope>";

		Source response = null;		
		log.config("LLAMADA: "+request);
		try
		{				
			final CXFConnector wsc = new CXFConnector();					
			wsc.setSoapAction("http://ecentia.cl/PedidoEntrada");
			wsc.setRequest(request);
			wsc.setBinding(SOAPBinding.SOAP11HTTP_BINDING);
			wsc.setEndpointAddress("http://192.168.15.160:8081/sga.asmx");
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
					log.config("nombres items nodos"+att.item(x).getLocalName());
					if(att.item(x).getLocalName().equals("Estado") || att.item(x).getNodeName().equals("Estado"))
						mensaje=att.item(x).getFirstChild().getNodeValue();
				} // fin for return
			}//FIN DATOS
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return mensaje;    			
    }  
    public Node findReturn(Node node) 
    {
    	log.config("dentro de findreturn");
		Node value = null;
		NodeList list = node.getChildNodes();
		log.config("cantidad de nodos hijos:"+list.getLength());
		for (int i=0; i<list.getLength(); i++) {
			// Get child node
			Node childNode = list.item(i);
			log.config("nombre elemento: "+childNode.getNodeName());
			if(childNode.getNodeName().equals(("PedidoEntradaResult")))
			{
				value = childNode;
				break;
			}
			value=findReturn(childNode);
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
