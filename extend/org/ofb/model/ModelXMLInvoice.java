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

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCity;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrg;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MTax;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_AD_Client;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.ofb.process.ExportDTEInvoiceCG;
import org.ofb.process.ExportDTEInvoiceCGBaskakow;
import org.ofb.process.ExportDTEInvoiceCGGemV2;
import org.ofb.process.ExportDTEInvoiceCGProvectis;
import org.ofb.process.ExportDTEInvoiceFOL;
import org.ofb.process.ExportDTEInvoiceFOLCGGem;
import org.ofb.process.ExportDTEInvoiceFOLNGem;
import org.ofb.process.ExportDTEInvoiceFOLPA;
import org.ofb.process.ExportDTEInvoiceFOLQDC;
import org.ofb.process.ExportDTEInvoiceITSA;
import org.ofb.process.ExportDTEInvoiceNBaskakow;
import org.ofb.process.ExportDTEInvoiceNGem;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



/**
 *	
 *
 *  @author ininoles
 */
public class ModelXMLInvoice implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ModelXMLInvoice ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ModelXMLInvoice.class);
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	

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
			log.info("Initializing Model Price Validator: "+this.toString());
		}
		
		//	Documents to be monitored		
		engine.addDocValidate(MInvoice.Table_Name, this);
		
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	OFB Consulting Ltda. By Julio Far�as
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
				
		return null;
	}	//	modelChange

	/**
	 *	Validate Document.
	 *	Called as first step of DocAction.prepareIt
     *	when you called addDocValidate for the table.x
     *	Note that totals, etc. may not be correct.
	 *	@param po persistent object
	 *	@param timing see TIMING_ constants
     *	@return error message or null
	 */
	public String docValidate (PO po, int timing)
	{
		log.info(po.get_TableName() + " Timing: "+timing);
				
		if(timing == TIMING_AFTER_COMPLETE && po.get_Table_ID()==MInvoice.Table_ID)
		{
			MInvoice inv = (MInvoice)po;
			X_AD_Client client = new X_AD_Client(po.getCtx(), inv.getAD_Client_ID(), po.get_TrxName());
			//ininoles validacion para generacion de xml generico
			String DTEType = "";
			
			try 
			{
				DTEType = client.get_ValueAsString("invoiceDTEType");
			}
			catch (Exception e)
			{			
				DTEType = "";
				log.log(Level.SEVERE, "No se pudo setear variable invoiceDTEType", e);
			}
			
			try 
			{
				if(DTEType.compareToIgnoreCase("N") == 0)
				{
					CreateXML(inv);//faaguilar OFB			
				}		
				else if(DTEType.compareToIgnoreCase("CGN") == 0)
				{
					ExportDTEInvoiceCG cg = new ExportDTEInvoiceCG();
					//MInvoice inv = new MInvoice(getCtx(), get_ID(), get_TrxName());
					cg.CreateXMLCG(inv);
				}
				else if(DTEType.compareToIgnoreCase("NGEM") == 0)
				{
					ExportDTEInvoiceNGem cg = new ExportDTEInvoiceNGem();
					//MInvoice inv = new MInvoice(getCtx(), get_ID(), get_TrxName());
					cg.CreateXML(inv);
				}
				else if(DTEType.compareToIgnoreCase("CGGEM") == 0)
				{
					ExportDTEInvoiceCGGemV2 cg = new ExportDTEInvoiceCGGemV2();
					//MInvoice inv = new MInvoice(getCtx(), get_ID(), get_TrxName());
					cg.CreateXML(inv);
				}
				else if(DTEType.compareToIgnoreCase("CGPRO") == 0)
				{
					ExportDTEInvoiceCGProvectis cg = new ExportDTEInvoiceCGProvectis();
					//MInvoice inv = new MInvoice(getCtx(), get_ID(), get_TrxName());
					cg.CreateXMLCG(inv);
				}		
				else if(DTEType.compareToIgnoreCase("NBK") == 0)
				{
					ExportDTEInvoiceNBaskakow cg = new ExportDTEInvoiceNBaskakow();
					//MInvoice inv = new MInvoice(getCtx(), get_ID(), get_TrxName());
					cg.CreateXML(inv);
				}	
				else if(DTEType.compareToIgnoreCase("CGBK") == 0)
				{
					ExportDTEInvoiceCGBaskakow cg = new ExportDTEInvoiceCGBaskakow();
					//ExportDTEInvoiceCGBaskakowPRUEBA cg = new ExportDTEInvoiceCGBaskakowPRUEBA();
					//MInvoice inv = new MInvoice(getCtx(), get_ID(), get_TrxName());
					cg.CreateXMLCG(inv);
				}
				else if(DTEType.compareToIgnoreCase("FELQDC") == 0)
				{
					ExportDTEInvoiceFOLQDC cg = new ExportDTEInvoiceFOLQDC();
					if(inv.getC_DocTypeTarget().getName().toLowerCase().contains("boleta"))
						cg.CreateXMLCGBoleta(inv);
					else
						cg.CreateXMLCG(inv);
				}	
				else if(DTEType.compareToIgnoreCase("FELNG") == 0)
				{
					ExportDTEInvoiceFOLNGem cg = new ExportDTEInvoiceFOLNGem();
					//MInvoice inv = new MInvoice(getCtx(), get_ID(), get_TrxName());
					cg.CreateXMLCG(inv);
				}
				else if(DTEType.compareToIgnoreCase("FELCGG") == 0)
				{
					ExportDTEInvoiceFOLCGGem cg = new ExportDTEInvoiceFOLCGGem();
					//MInvoice inv = new MInvoice(getCtx(), get_ID(), get_TrxName());
					cg.CreateXMLCG(inv);
				}
				else if(DTEType.compareToIgnoreCase("ITSA") == 0)
				{
					ExportDTEInvoiceITSA cg = new ExportDTEInvoiceITSA();
					//MInvoice inv = new MInvoice(getCtx(), get_ID(), get_TrxName());
					cg.CreateXMLCGITSA(inv);
				}
				else if(DTEType.compareToIgnoreCase("FELN") == 0)
				{
					ExportDTEInvoiceFOL cg = new ExportDTEInvoiceFOL();
					if(inv.getC_DocTypeTarget().getName().toLowerCase().contains("boleta"))
						cg.CreateXMLCGBoleta(inv);
					else
						cg.CreateXMLCG(inv);
					//MInvoice inv = new MInvoice(getCtx(), get_ID(), get_TrxName());
					//cg.CreateXMLCG(this);
				}
				else if(DTEType.compareToIgnoreCase("FELPA") == 0)
				{
					ExportDTEInvoiceFOLPA cg = new ExportDTEInvoiceFOLPA();
					if(inv.getC_DocTypeTarget().getName().toLowerCase().contains("boleta"))
						cg.CreateXMLBoleta(inv);
					else
						cg.CreateXML(inv);
					//MInvoice inv = new MInvoice(getCtx(), get_ID(), get_TrxName());
					//cg.CreateXMLCG(this);
				}
				
			} catch (Exception e) 
			{
				log.config("Error al tratar de generar xml: "+e.toString());
			}
							
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
		StringBuffer sb = new StringBuffer ("ModelPrice");
		return sb.toString ();
	}	//	toString
	
	public String CreateXML(MInvoice inv)
    {
        MDocType doc = new MDocType(inv.getCtx(), inv.getC_DocTypeTarget_ID(), inv.get_TrxName());
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
            Element Documento = document.createElement("Documento");
            document.getDocumentElement().appendChild(Documento);
            Documento.setAttribute("ID", (new StringBuilder()).append("DTE-").append(inv.getDocumentNo()).toString());
            Element Encabezado = document.createElement("Encabezado");
            Documento.appendChild(Encabezado);
            Element IdDoc = document.createElement("IdDoc");
            Encabezado.appendChild(IdDoc);
            mylog = "IdDoc";
            Element TipoDTE = document.createElement("TipoDTE");
            org.w3c.dom.Text text = document.createTextNode(Integer.toString(typeDoc));
            TipoDTE.appendChild(text);
            IdDoc.appendChild(TipoDTE);
            Element Folio = document.createElement("Folio");
            org.w3c.dom.Text fo = document.createTextNode(inv.getDocumentNo());
            Folio.appendChild(fo);
            IdDoc.appendChild(Folio);
            Element FchEmis = document.createElement("FchEmis");
            org.w3c.dom.Text emis = document.createTextNode(inv.getDateInvoiced().toString().substring(0, 10));
            FchEmis.appendChild(emis);
            IdDoc.appendChild(FchEmis);
            Element FchCancel = document.createElement("FchCancel");
            org.w3c.dom.Text cancel = document.createTextNode(inv.getDateInvoiced().toString().substring(0, 10));
            FchCancel.appendChild(cancel);
            IdDoc.appendChild(FchCancel);
            Element FchVenc = document.createElement("FchVenc");
            org.w3c.dom.Text venc = document.createTextNode(inv.getDateInvoiced().toString().substring(0, 10));
            FchVenc.appendChild(venc);
            IdDoc.appendChild(FchVenc);
            
            //ininoles nuevo campo termino de pago
            MPaymentTerm pterm = new MPaymentTerm(inv.getCtx(), inv.getC_PaymentTerm_ID(), inv.get_TrxName());
            Element PayTerm = document.createElement("PayTerm");
            org.w3c.dom.Text term = document.createTextNode(pterm.getName());
            PayTerm.appendChild(term);
            IdDoc.appendChild(PayTerm);            
            //ininoles nuevo campo vendedor            
            MUser salesUser = new MUser(inv.getCtx(), inv.getSalesRep_ID(),inv.get_TrxName());
            Element SalesRep = document.createElement("SalesRep");
            org.w3c.dom.Text sales = document.createTextNode(salesUser.getName());
            SalesRep.appendChild(sales);
            IdDoc.appendChild(SalesRep);
            //ininoles nuevo campo descripcion cabecera                        
            Element HDescription = document.createElement("HeaderDescription");
            org.w3c.dom.Text Hdesc = document.createTextNode(inv.getDescription()==null?" ":inv.getDescription());
            HDescription.appendChild(Hdesc);
            IdDoc.appendChild(HDescription);
            //end ininoles
                        
            Element Emisor = document.createElement("Emisor");
            Encabezado.appendChild(Emisor);
            mylog = "Emisor";
            MOrg company = MOrg.get(inv.getCtx(), inv.getAD_Org_ID());
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
            Element RznSoc = document.createElement("RznSoc");
            org.w3c.dom.Text rzn = document.createTextNode(nameRzn);
            RznSoc.appendChild(rzn);
            Emisor.appendChild(RznSoc);
            Element GiroEmis = document.createElement("GiroEmis");
            org.w3c.dom.Text gi = document.createTextNode((String)company.get_Value("Giro"));
            GiroEmis.appendChild(gi);
            Emisor.appendChild(GiroEmis);
            Element Acteco = document.createElement("Acteco");
            org.w3c.dom.Text teco = document.createTextNode((String)company.get_Value("Acteco"));
            Acteco.appendChild(teco);
            Emisor.appendChild(Acteco);
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
            MBPartner BP = new MBPartner(inv.getCtx(), inv.getC_BPartner_ID(), inv.get_TrxName());
            MBPartnerLocation bloc = new MBPartnerLocation(inv.getCtx(), inv.getC_BPartner_Location_ID(), inv.get_TrxName());
            Element Receptor = document.createElement("Receptor");
            Encabezado.appendChild(Receptor);
            Element RUTRecep = document.createElement("RUTRecep");
            org.w3c.dom.Text rutc = document.createTextNode((new StringBuilder()).append(BP.getValue()).append("-").append(BP.get_ValueAsString("Digito")).toString());
            RUTRecep.appendChild(rutc);
            Receptor.appendChild(RUTRecep);
            Element RznSocRecep = document.createElement("RznSocRecep");
            org.w3c.dom.Text RznSocR = document.createTextNode(BP.getName());
            RznSocRecep.appendChild(RznSocR);
            Receptor.appendChild(RznSocRecep);
            Element GiroRecep = document.createElement("GiroRecep");
            org.w3c.dom.Text giro = document.createTextNode((String)BP.get_Value("Giro"));
            GiroRecep.appendChild(giro);
            Receptor.appendChild(GiroRecep);
            
            Element ContactoRecep = document.createElement("Contacto");
            org.w3c.dom.Text contacto = document.createTextNode(inv.getAD_User_ID()>0?inv.getAD_User().getName():" "); //nombre completo contacto
            ContactoRecep.appendChild(contacto);
            Receptor.appendChild(ContactoRecep);
            
            Element CorreoRecep = document.createElement("CorreoRecep");
            org.w3c.dom.Text corrRecep = document.createTextNode(inv.getAD_User().getEMail()==null?" ":inv.getAD_User().getEMail()); //mail del contacto
            CorreoRecep.appendChild(corrRecep);
            Receptor.appendChild(CorreoRecep);
            
            
            Element DirRecep = document.createElement("DirRecep");
            org.w3c.dom.Text dirr = document.createTextNode(bloc.getLocation(true).getAddress1());
            DirRecep.appendChild(dirr);
            Receptor.appendChild(DirRecep);
            if(bloc.getLocation(true).getAddress2()!=null && bloc.getLocation(true).getAddress2().length()>0 ){
	            Element CmnaRecep = document.createElement("CmnaRecep");
	            org.w3c.dom.Text Cmna = document.createTextNode(bloc.getLocation(true).getAddress2());
	            CmnaRecep.appendChild(Cmna);
	            Receptor.appendChild(CmnaRecep);
            }
            
            Element CiudadRecep = document.createElement("CiudadRecep");
            org.w3c.dom.Text reg = document.createTextNode(bloc.getLocation(true).getC_City_ID()>0?MCity.get(inv.getCtx(), bloc.getLocation(true).getC_City_ID()).getName():"Santiago");
            CiudadRecep.appendChild(reg);
            Receptor.appendChild(CiudadRecep);
            
            mylog = "Totales";
            Element Totales = document.createElement("Totales");
            Encabezado.appendChild(Totales);
            BigDecimal amountex = DB.getSQLValueBD(inv.get_TrxName(), (new StringBuilder()).append("select Round(COALESCE(SUM(il.LineNetAmt),0),0) from C_InvoiceLine il  inner join C_Tax t on (il.C_Tax_ID=t.C_Tax_ID) and t.istaxexempt='Y' and il.C_Invoice_ID=").append(inv.getC_Invoice_ID()).toString());
            BigDecimal amountNeto = DB.getSQLValueBD(inv.get_TrxName(), (new StringBuilder()).append("select Round(COALESCE(SUM(il.LineNetAmt),0),0) from C_InvoiceLine il  inner join C_Tax t on (il.C_Tax_ID=t.C_Tax_ID) and t.istaxexempt='N' and il.C_Invoice_ID=").append(inv.getC_Invoice_ID()).toString());
            
            
	        Element MntNeto = document.createElement("MntNeto");
	        org.w3c.dom.Text neto = document.createTextNode(amountNeto!=null?amountNeto.toString():"0");
	        MntNeto.appendChild(neto);
	        Totales.appendChild(MntNeto);
            
            
            Element MntExe = document.createElement("MntExe");
            org.w3c.dom.Text exe = document.createTextNode(amountex != null ? amountex.toString() : "0");
            MntExe.appendChild(exe);
            Totales.appendChild(MntExe);
            
            if(amountNeto.signum()>0){
	            Element TasaIVA = document.createElement("TasaIVA");
	            org.w3c.dom.Text tiva = document.createTextNode("19");
	            TasaIVA.appendChild(tiva);
	            Totales.appendChild(TasaIVA);
	        }
	        
	            
	            Element IVA = document.createElement("IVA");
	            BigDecimal ivaamt= Env.ZERO;
	            if(amountex.intValue()!=inv.getGrandTotal().intValue())
	            	ivaamt=inv.getGrandTotal().subtract(inv.getTotalLines()).setScale(0, 4);
	            org.w3c.dom.Text iva = document.createTextNode(ivaamt.toString());
	            IVA.appendChild(iva);
	            Totales.appendChild(IVA);
            
            Element MntTotal = document.createElement("MntTotal");
            org.w3c.dom.Text total = document.createTextNode(inv.getGrandTotal().setScale(0, 4).toString());
            MntTotal.appendChild(total);
            Totales.appendChild(MntTotal);
            /*Element MontoPeriodo = document.createElement("MontoPeriodo");
            org.w3c.dom.Text mnt = document.createTextNode(getTotalLines().setScale(0, 4).toString());
            MontoPeriodo.appendChild(mnt);
            Totales.appendChild(MontoPeriodo);*/
            mylog = "detalle";
            MInvoiceLine iLines[] = inv.getLines(false);
            for(int i = 0; i < iLines.length; i++)
            {
            	MInvoiceLine iLine = iLines[i];
            	if(iLine.getM_Product_ID()==0 && iLine.getC_Charge_ID()==0)
            		continue;
            	
                Element Detalle = document.createElement("Detalle");
                Documento.appendChild(Detalle);
                
                MTax tax=new MTax(inv.getCtx() ,iLine.getC_Tax_ID(),inv.get_TrxName() );
                
                if(tax.isTaxExempt()){
                	 Element IndEx = document.createElement("IndExe");
                     org.w3c.dom.Text lineE = document.createTextNode("1");
                     IndEx.appendChild(lineE);
                     Detalle.appendChild(IndEx);	
                }
              
                Element NroLinDet = document.createElement("NroLinDet");
                org.w3c.dom.Text line = document.createTextNode(Integer.toString(iLine.getLine() / 10));
                NroLinDet.appendChild(line);
                Detalle.appendChild(NroLinDet);
                Element NmbItem = document.createElement("NmbItem");
                String pname="";
                if(iLine.getProduct()!=null )
                	pname=iLine.getProduct().getName();
                else
                	pname=iLine.getC_Charge().getName();
                org.w3c.dom.Text Item = document.createTextNode(pname);
                NmbItem.appendChild(Item);
                Detalle.appendChild(NmbItem);
                
                Element DscItem = document.createElement("DscItem");
                org.w3c.dom.Text desc = document.createTextNode(iLine.getDescription()==null?" ":iLine.getDescription());
                DscItem.appendChild(desc);
                Detalle.appendChild(DscItem);
                
                Element QtyRef = document.createElement("QtyRef");
                org.w3c.dom.Text qty = document.createTextNode(iLine.getQtyEntered().toString());
                QtyRef.appendChild(qty);
                Detalle.appendChild(QtyRef);
                Element PrcRef = document.createElement("PrcRef");
                org.w3c.dom.Text pl = document.createTextNode(iLine.getPriceList().toString());
                PrcRef.appendChild(pl);
                Detalle.appendChild(PrcRef);
                Element QtyItem = document.createElement("QtyItem");
                org.w3c.dom.Text qt = document.createTextNode(iLine.getQtyInvoiced().toString());
                QtyItem.appendChild(qt);
                Detalle.appendChild(QtyItem);
                Element PrcItem = document.createElement("PrcItem");
                org.w3c.dom.Text pa = document.createTextNode(iLine.getPriceActual().setScale(0, 4).toString());
                PrcItem.appendChild(pa);
                Detalle.appendChild(PrcItem);
                Element MontoItem = document.createElement("MontoItem");
                org.w3c.dom.Text tl = document.createTextNode(iLine.getLineNetAmt().setScale(0, 4).toString());
                MontoItem.appendChild(tl);
                Detalle.appendChild(MontoItem);
            }

            mylog = "referencia";
            String tiporeferencia = new String();
            String folioreferencia  = new String();
            String fechareferencia = new String();
            int tipo_Ref =0;
            
            if(inv.get_Value("C_RefDoc_ID") != null && ((Integer)inv.get_Value("C_RefDoc_ID")).intValue() > 0)//referencia factura
            {
                mylog = "referencia:invoice";
                MInvoice refdoc = new MInvoice(inv.getCtx(), ((Integer)inv.get_Value("C_RefDoc_ID")).intValue(), inv.get_TrxName());
                MDocType Refdoctype = new MDocType(inv.getCtx(), refdoc.getC_DocType_ID(), inv.get_TrxName());
                tiporeferencia = (String) Refdoctype.get_Value("DocumentNo");
                folioreferencia = (String) refdoc.getDocumentNo();
                fechareferencia = refdoc.getDateInvoiced().toString().substring(0, 10);
                tipo_Ref = 1; //factura
            } 
            
            if(inv.getPOReference() != null && inv.getPOReference().length() > 0)//referencia orden
            {
            	 mylog = "referencia:order";
            	 //MOrder refdoc = new MOrder(getCtx(), ((Integer)get_Value("C_RefOrder_ID")).intValue(), get_TrxName()); 
            	 tiporeferencia = "801";
                 folioreferencia = inv.getPOReference();
                 fechareferencia = inv.getDateOrdered().toString().substring(0, 10);
            	 tipo_Ref = 2; //Orden
            }
            
            if(inv.get_Value("C_RefInOut_ID") != null && ((Integer)inv.get_Value("C_RefInOut_ID")).intValue() > 0)//referencia despacho
            {
            	 mylog = "referencia:despacho";
            	 MInOut refdoc = new MInOut(inv.getCtx(), ((Integer)inv.get_Value("C_RefInOut_ID")).intValue(), inv.get_TrxName()); 
            	 tiporeferencia = "52";
                 folioreferencia = (String) refdoc.getDocumentNo();
                 fechareferencia = refdoc.getMovementDate().toString().substring(0, 10);
            	 tipo_Ref = 3; //despacho
            }
            
            if(tipo_Ref>0){
                Element Referencia = document.createElement("Referencia");
                Documento.appendChild(Referencia);
                Element NroLinRef = document.createElement("NroLinRef");
                org.w3c.dom.Text Nro = document.createTextNode("1");
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
               /* Element RUTOtr = document.createElement("RUTOtr");
                org.w3c.dom.Text rutot = document.createTextNode(BPref.getValue()+"-"+BPref.getDigito());
                RUTOtr.appendChild(rutot);
                Referencia.appendChild(RUTOtr);*/
                Element FchRef = document.createElement("FchRef");
                org.w3c.dom.Text fchref = document.createTextNode(fechareferencia);
                FchRef.appendChild(fchref);
                Referencia.appendChild(FchRef);
                Element CodRef = document.createElement("CodRef");
                org.w3c.dom.Text codref = document.createTextNode(inv.get_ValueAsString("CodRef"));
                CodRef.appendChild(codref);
                Referencia.appendChild(CodRef);
                
                //nuevos campos de referencia geminis. ininoles
                /*
                String sqlDN = "Select o.DocumentNo FROM C_Order o INNER JOIN C_Invoice i ON (o.C_Order_ID = i.C_Order_ID) WHERE i.C_Invoice_ID= ?";
                String docNoRef = DB.getSQLValueString(get_TrxName(), sqlDN, get_ID()); 
                
                if (docNoRef != null)
                {
                	Element Referencia2 = document.createElement("Referencia");
                    Documento.appendChild(Referencia2);
                    Element NroLinRef2 = document.createElement("NroLinRef");
                    org.w3c.dom.Text Nro2 = document.createTextNode("2");
                    NroLinRef2.appendChild(Nro2);
                    Referencia2.appendChild(NroLinRef2);
                    
                    Element TpoDocRef2 = document.createElement("TpoDocRef");
                    org.w3c.dom.Text tpo2 = document.createTextNode("HES");
                    TpoDocRef2.appendChild(tpo2);
                    Referencia2.appendChild(TpoDocRef2);
                    
                    Element FolioRef2 = document.createElement("FolioRef");
                    org.w3c.dom.Text ref2 = document.createTextNode(docNoRef);
                    FolioRef2.appendChild(ref2);
                    Referencia2.appendChild(FolioRef2);
                                      
                    Element FchRef2 = document.createElement("FchRef");
                    org.w3c.dom.Text fchref2 = document.createTextNode(fechareferencia);
                    FchRef2.appendChild(fchref2);
                    Referencia2.appendChild(FchRef2);
                    
                    Element CodRef2 = document.createElement("CodRef");
                    org.w3c.dom.Text codref2 = document.createTextNode(get_ValueAsString("CodRef"));
                    CodRef2.appendChild(codref2);
                    Referencia2.appendChild(CodRef2);
                    
                }*/
            }
            //fin referencia
            mylog = "firma";
            Element Firma = document.createElement("TmstFirma");
            Timestamp today = new Timestamp(TimeUtil.getToday().getTimeInMillis());
            org.w3c.dom.Text Ftext = document.createTextNode((new StringBuilder()).append(today.toString().substring(0, 10)).append("T").append(today.toString().substring(11, 19)).toString());
            Firma.appendChild(Ftext);
            Documento.appendChild(Firma);
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
	            javax.xml.transform.Result result = new StreamResult(new File(ExportDir, (new StringBuilder()).append(inv.getDocumentNo()).append(".xml").toString()));
	            javax.xml.transform.Result console = new StreamResult(System.out);
	            Transformer transformer = TransformerFactory.newInstance().newTransformer();
	            transformer.setOutputProperty("indent", "yes");
	            transformer.setOutputProperty("encoding", "ISO-8859-1");
	            transformer.transform(source, result);
	            transformer.transform(source, console);
           
        }
        catch(Exception e)
        {
            log.severe((new StringBuilder()).append("CreateXML: ").append(mylog).append("--").append(e.getMessage()).toString());
            return (new StringBuilder()).append("CreateXML: ").append(mylog).append("--").append(e.getMessage()).toString();
        }
        return "XML Generated";
    }
	
}	