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
import org.compiere.model.*;
import org.compiere.process.SvrProcess;
import org.ofb.process.CXFConnector;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *	Generate XML Consolidated from Invoice (Generic) 
 *	
 *  @author Italo Ni�oles ininoles
 *  @version $Id: WSTecPedidoEntrada.java,v 1.2 15/06/2020 $
 */
public class WSTecPedidoSalida extends SvrProcess
{	
	/** Properties						*/
	//private Properties 		m_ctx;	
	private int p_M_Inventory_ID = 0;
	public String urlPdf = "";
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		p_M_Inventory_ID=getRecord_ID();
		//m_ctx = Env.getCtx();
	}	//	prepare

	
	/**
	 * 	Create Shipment
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		MInventory rec = new MInventory(Env.getCtx(),p_M_Inventory_ID,get_TrxName());
		String msg = "";
		msg = llamarWSPedidoSalida(rec);
		return msg;

	}	//	doIt
	 
    private String llamarWSPedidoSalida(MInventory inv) throws Exception
    {
    	String mensaje = "OK";	
    	if(inv.get_ValueAsInt("C_BPartner_ID") > 0)
    	{
	    	MBPartner bp = new MBPartner(getCtx(), inv.get_ValueAsInt("C_BPartner_ID"), get_TrxName());
	    	String DateStr = inv.getMovementDate().getDate()+"/"+(inv.getMovementDate().getMonth()+1)+"/"+
	    			(inv.getMovementDate().getYear()+1900)+" "+inv.getMovementDate().getHours()+":"+inv.getMovementDate().getMinutes();
	    	/*String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ecen=\"http://ecentia.cl\">"+
	    			"<soapenv:Header/>"+
	    			"<soapenv:Body>"+
	    			"<PedidoSalida>"+
	    			"<idPedidoSalida>"+inv.getDocumentNo()+"</idPedidoSalida>"+
	    			"<idPaciente>"+bp.getValue()+"</idPaciente>"+
	    			"<nombrePaciente>"+bp.getName()+"</nombrePaciente>"+
	    			"<folio>"+inv.getDocumentNo()+"</folio>"+
	    			"<medico>"+bp.getValue()+"</medico>"+
	    			"<fechaHora>"+DateStr+"</fechaHora>"+
	    			"<unidadDestino>1</unidadDestino>"+
	    			"<idSistema>SISTEMA HOSPITAL</idSistema>"+
	    			"<detallePedidoSalida>";
	    	//se crea ciclo de lectura de lineas
	    	
	    	MInventoryLine[] lines = inv.getLines(false);
			for (MInventoryLine line : lines)
			{
				if (line.getM_Product_ID()>0)
				{
					request = request+"<Prescripcion>"+
								"<idArticulo>"+line.getM_Product().getValue()+"</idArticulo>"+
								"<cantidad>"+line.getQtyInternalUse().intValue()+"</cantidad>"+
							"</Prescripcion>";
				}
			}		
			request = request+"</detallePedidoSalida>"+
					"<Token>123456</Token>"+
					"</PedidoSalida>"+
					"</soapenv:Body>"+
					"</soapenv:Envelope>";
			*/
	    	String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ecen=\"http://ecentia.cl\">"+
	    			"<soapenv:Header/>"+
	    			"<soapenv:Body>"+
	    			"<ecen:PedidoSalida>"+
	    			"<ecen:idPedidoSalida>"+inv.getDocumentNo()+"</ecen:idPedidoSalida>"+
	    			"<ecen:idPaciente>"+bp.getValue()+"</ecen:idPaciente>"+
	    			"<ecen:nombrePaciente>"+bp.getName()+"</ecen:nombrePaciente>"+
	    			"<ecen:folio>"+inv.getDocumentNo()+"</ecen:folio>"+
	    			"<ecen:medico>"+bp.getValue()+"</ecen:medico>"+
	    			"<ecen:fechaHora>"+DateStr+"</ecen:fechaHora>"+
	    			"<ecen:unidadDestino>1</ecen:unidadDestino>"+
	    			"<ecen:idSistema>SISTEMA HOSPITAL</ecen:idSistema>"+
	    			"<ecen:detallePedidoSalida>";
	    	//se crea ciclo de lectura de lineas
	    	
	    	MInventoryLine[] lines = inv.getLines(false);
			for (MInventoryLine line : lines)
			{
				if (line.getM_Product_ID()>0)
				{
					request = request+"<ecen:Prescripcion>"+
								"<ecen:idArticulo>"+line.getM_Product().getValue()+"</ecen:idArticulo>"+
								"<ecen:cantidad>"+line.getQtyInternalUse().intValue()+"</ecen:cantidad>"+
							"</ecen:Prescripcion>";
				}
			}		
			request = request+"</ecen:detallePedidoSalida>"+
					"<ecen:Token>123456</ecen:Token>"+
					"</ecen:PedidoSalida>"+
					"</soapenv:Body>"+
					"</soapenv:Envelope>";
			log.config("LLAMADA: "+request);
			Source response = null;				
			try
			{				
				final CXFConnector wsc = new CXFConnector();					
				wsc.setSoapAction("http://ecentia.cl/PedidoSalida");
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
				
				Node datos = findReturn(doc.getChildNodes().item(0)); 
									
				if(datos!=null)
				{
					NodeList att = datos.getChildNodes(); 
					for(int x=0;x<att.getLength();x++)
					{	
						log.config(att.item(x).getLocalName());
						if(att.item(x).getLocalName().equals("Estado") || att.item(x).getNodeName().equals("Estado"))
							mensaje=att.item(x).getFirstChild().getNodeValue();
						else if(att.item(x).getLocalName().equals("Descripcion") || att.item(x).getNodeName().equals("Estado"))
							mensaje=mensaje+att.item(x).getFirstChild().getNodeValue();
					} // fin for return
				}//FIN DATOS
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
    	}
		return mensaje;   
    }  
    public Node findReturn(Node node) 
    {
		Node value = null;
		NodeList list = node.getChildNodes();
		for (int i=0; i<list.getLength(); i++) {
			// Get child node
			Node childNode = list.item(i);
			if(childNode.getNodeName().equals(("PedidoSalidaResult")))
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
