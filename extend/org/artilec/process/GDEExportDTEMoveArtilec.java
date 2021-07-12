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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.util.*;
import org.ofb.model.OFBForward;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.compiere.model.*;
import org.compiere.process.SvrProcess;

import com.liquid_technologies.ltxmllib12.exceptions.LtException;



/**
 *	Generate XML Consolidated from Invoice (Generic) 
 *	
 *  @author ininoles
 *  @version $Id: GDEExportDTEInOutArtilec.java,v 1.2 19/05/2011 $
 */
public class GDEExportDTEMoveArtilec extends SvrProcess
{	
	/** Properties						*/
	private Properties 		m_ctx;	
	private int p_M_Inout_ID = 0;
	public String urlPdf = "";
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		p_M_Inout_ID=getRecord_ID();
		m_ctx = Env.getCtx();
	}	//	prepare

	
	/**
	 * 	Create Shipment
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		MMovement mov=new MMovement(m_ctx,p_M_Inout_ID,get_TrxName());
		String msg = "";
		msg=CreateXML(mov);
		return msg;
	}	//	doIt
	
	public String CreateXML(MMovement mov)
    {
		String error ="";
		String ted = "";
		String pdf417 = "";
		try 
		{		
			DocumentoDTE.SiiDte.DTEDefType dte = new DocumentoDTE.SiiDte.DTEDefType();
			dte.setDTE_Choice(new DocumentoDTE.SiiDte.DTE_Choice());
			MOrg org = MOrg.get(mov.getCtx(), mov.getAD_Org_ID());
			MDocType docType = new MDocType(mov.getCtx(), mov.getC_DocType_ID(), mov.get_TrxName());
			
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
			else if(docType.get_ValueAsString("DocumentNo").compareTo("52") == 0)
				idDoc.setTipoDTE(DocumentoDTE.SiiDte.DTEType.n52);			
			else
				idDoc.setTipoDTE(DocumentoDTE.SiiDte.DTEType.n52);
			idDoc.setFolio(BigInteger.valueOf(Integer.parseInt(mov.getDocumentNo())));
			//idDoc.setFolio(BigInteger.valueOf(2255));
			//Timestamp fchemis = inOut.getDateInvoiced();
			idDoc.setFchEmis(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date,ConverDateToString(mov.getMovementDate())));
			//String dateReturn = "";
			//dateReturn = (fchemis.getYear()+1900)+"-"+(fchemis.getMonth()+1)+"-"+fchemis.getDate();
			//dateReturn = ConverDateToString(fchemis);
			//idDoc.setFchEmis(new com.liquid_technologies.ltxmllib12.DateTime(com.liquid_technologies.ltxmllib12.DateTimeType.date,"2010-01-01"));
            //idDoc.setFmaPago(DocumentoDTE.SiiDte.IdDoc_FmaPago.n3);
            //campos de traslado
            idDoc.setTipoDespacho(DocumentoDTE.SiiDte.IdDoc_TipoDespacho.n2);
            //IndTraslado para Guía de traslado interno es siempre 5
            String IndTrl = "5";
            /*try {
            	IndTrl = mov.get_ValueAsString("IndTraslado");
            }
            catch (Exception e) {
            	IndTrl = "1";
			}
            if (IndTrl == null || IndTrl == "" || IndTrl == " ")
            	IndTrl = "1";*/
            if(IndTrl.compareTo("1") ==0)
            	idDoc.setIndTraslado(DocumentoDTE.SiiDte.IdDoc_IndTraslado.n1);
            else if(IndTrl.compareTo("2") ==0)
            	idDoc.setIndTraslado(DocumentoDTE.SiiDte.IdDoc_IndTraslado.n2);
            else if(IndTrl.compareTo("3") ==0)
            	idDoc.setIndTraslado(DocumentoDTE.SiiDte.IdDoc_IndTraslado.n3);
            else if(IndTrl.compareTo("4") ==0)
            	idDoc.setIndTraslado(DocumentoDTE.SiiDte.IdDoc_IndTraslado.n4);
            else if(IndTrl.compareTo("5") ==0)
            	idDoc.setIndTraslado(DocumentoDTE.SiiDte.IdDoc_IndTraslado.n5);
            else if(IndTrl.compareTo("6") ==0)
            	idDoc.setIndTraslado(DocumentoDTE.SiiDte.IdDoc_IndTraslado.n6);
            else if(IndTrl.compareTo("7") ==0)
            	idDoc.setIndTraslado(DocumentoDTE.SiiDte.IdDoc_IndTraslado.n7);
            else if(IndTrl.compareTo("8") ==0)
            	idDoc.setIndTraslado(DocumentoDTE.SiiDte.IdDoc_IndTraslado.n8);
            else if(IndTrl.compareTo("9") ==0)
            	idDoc.setIndTraslado(DocumentoDTE.SiiDte.IdDoc_IndTraslado.n9);
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
			if (mov.getSalesRep_ID() > 0)
				emisor.setCdgVendedor(mov.getSalesRep().getName());
			doc.getEncabezado().setEmisor(emisor);
	
			//Documento/Encabezado/Receptor
			MBPartner BP = new MBPartner(mov.getCtx(), mov.getC_BPartner_ID(), mov.get_TrxName());
	        MBPartnerLocation bloc = new MBPartnerLocation(mov.getCtx(), mov.getC_BPartner_Location_ID(), mov.get_TrxName());
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
	        	MCity dCity = new MCity(mov.getCtx(),bloc.get_ValueAsInt("C_City_ID"), mov.get_TrxName());
	            CmnaRecepStr = dCity.getName();
	        }		
			receptor.setCmnaRecep(CmnaRecepStr);
	        
			String ciudadTxt = "";       
	        if(bloc.get_ValueAsInt("C_Province_ID") > 0)
	        {
		        X_C_Province prov = new X_C_Province(mov.getCtx(), bloc.get_ValueAsInt("C_Province_ID"), mov.get_TrxName());
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
			BigDecimal amountGrandT = Env.ZERO;            
            BigDecimal priceT = Env.ZERO;
            //BigDecimal taxAmt = Env.ZERO;
            //BigDecimal taxIVAAmt = Env.ZERO;
            BigDecimal taxIVAAmtAcu = Env.ZERO;
            BigDecimal taxExeAmtAcu = Env.ZERO;
            BigDecimal taxNetoAmtAcu = Env.ZERO;
            BigDecimal amtAcum = Env.ZERO;
            MMovementLine iLines[] = mov.getLines(true);
            //se calculan totales
            for(int i = 0; i < iLines.length; i++)
            {
            	MMovementLine iLine = iLines[i];
            	if(iLine.getM_Product_ID()==0)
            		continue;
            	
            	if(iLine.getM_Product_ID() > 0)
            	{
	            	priceT = DB.getSQLValueBD(mov.get_TrxName(), "SELECT PriceStd FROM M_ProductPrice WHERE M_PriceList_Version_ID=2000004 AND M_Product_ID="+iLine.getM_Product_ID());
	            	if(priceT == null)
	            		priceT = new BigDecimal("9990.0");
	            	amtAcum = amtAcum.add(priceT.multiply(iLine.getMovementQty()));
            	}
            }
            taxNetoAmtAcu = amtAcum;
            taxIVAAmtAcu = taxNetoAmtAcu.multiply(new BigDecimal("0.19"));
            taxIVAAmtAcu = taxIVAAmtAcu.setScale(0, RoundingMode.HALF_EVEN);
            amountGrandT = taxNetoAmtAcu.add(taxIVAAmtAcu);
            
			DocumentoDTE.SiiDte.Totales totales = new DocumentoDTE.SiiDte.Totales();
			totales.setMntTotal(amountGrandT.toBigInteger());

			totales.setMntNeto(taxNetoAmtAcu.toBigInteger());
			totales.setMntExe(taxExeAmtAcu.toBigInteger());

			if(taxNetoAmtAcu.signum()>0)
				totales.setTasaIVA(BigDecimal.valueOf(19));
			//BigDecimal ivaamt= Env.ZERO;
			totales.setIVA(taxIVAAmtAcu.toBigInteger());
			doc.getEncabezado().setTotales(totales);
			
			//Documento/Detalle

	        //MMovementLine iLines[] = mov.getLines(false);
			int lineInvoice = 0;
	        //int lineDiscount = 0;
	        for(int i = 0; i < iLines.length; i++)
	        {	
	        	MMovementLine iLine = iLines[i];
	        	if(iLine.getM_Product_ID()==0)
	        		continue;
	        	
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
	        		
	                pname = pname.replace("'", "");
	                pname = pname.replace("\"", "");
					det.setNmbItem(pname);
					det.setQtyItem(iLine.getMovementQty());

	                //ininoles seteo de monto
	                priceT = DB.getSQLValueBD(mov.get_TrxName(), "SELECT PriceStd FROM M_ProductPrice WHERE M_PriceList_Version_ID=2000004 AND M_Product_ID="+iLine.getM_Product_ID());
	                if(priceT == null)
	            		priceT = new BigDecimal("9990.0");
	                BigDecimal prcRefMnt = priceT;
	                BigDecimal mtoItem = prcRefMnt.multiply(iLine.getMovementQty());
	                
					det.setPrcItem(prcRefMnt.setScale(0, 4));
					det.setMontoItem(mtoItem.setScale(0, 4).toBigInteger());
					if (iLine.getDescription() != null && iLine.getDescription() != "" && mov.getC_DocType().getDocBaseType().compareTo("ARC") != 0)
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
	        }
	    			
	        String tiporeferencia = new String();
	        String folioreferencia  = new String();
	        String fechareferencia = new String();
	        int tipo_Ref =0;
	        
	        //la referencia orden solo se usara si no es nota de credito
	        if(mov.getPOReference() != null && mov.getPOReference().length() > 0 
	        		&& mov.getC_DocType().getDocBaseType().compareTo("ARC") != 0
	        		&& mov.getPOReference().compareTo("0") != 0)//referencia orden
	        {
	        	 //MOrder refdoc = new MOrder(inOut.getCtx(), ((Integer)get_Value("C_RefOrder_ID")).intValue(), inOut.get_TrxName()); 
	        	 tiporeferencia = "801";
	             folioreferencia = mov.getPOReference();
	             if(mov.getDateReceived() != null)
	            	 fechareferencia = ConverDateToString(mov.getDateReceived());
	             else
	            	 fechareferencia = ConverDateToString(mov.getMovementDate());
	        	 tipo_Ref = 2; //Orden
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
					String razonRefTxt = mov.get_ValueAsString("RazonRef");
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
	        //se agrega campo adicional
            //Personalizados
            DocumentoDTE.SiiDte.PersonalizadosA personalizados = new DocumentoDTE.SiiDte.PersonalizadosA();
            dte.setPersonalizados(personalizados);

            if(mov.getDescription() != null)
            {
            	personalizados.setDocPersonalizado(new DocumentoDTE.SiiDte.DocPersonalizadoA()); 
	            DocumentoDTE.SiiDte.CampoStringA campoString = new DocumentoDTE.SiiDte.CampoStringA();
	            campoString.setName("Notas");
	            campoString.setPrimitiveValue(mov.getDescription());  // Aquí deben escribir la nota específica y aparecerá en el documento impreso.
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
			
			if (resultado.getResultadoServicio().getEstado() == 0)
			{			            
			    ted = resultado.getTED();
			    pdf417 = resultado.getPDF417();	
			    error = resultado.getResultadoServicio().getDescripcion();;
			}
			else
			{			            
			    error = resultado.getResultadoServicio().getDescripcion();
			}
			mov.set_CustomColumn("DescriptionGDE", error);
			mov.saveEx(mov.get_TrxName());
			
			
			//se agrega delay de 4 segundos
			Thread.sleep(4000);
			//se rescata pdf
			dteboxcliente.GrupoBusqueda grupo = dteboxcliente.GrupoBusqueda.Emitidos;
			int tipoDTE = dteboxcliente.TipoDocumento.TIPO_33;
			if(docType.get_ValueAsString("DocumentNo").compareTo("33") == 0)
				 tipoDTE = dteboxcliente.TipoDocumento.TIPO_33;
			else if(docType.get_ValueAsString("DocumentNo").compareTo("34") == 0)
				tipoDTE = dteboxcliente.TipoDocumento.TIPO_34;
			else if(docType.get_ValueAsString("DocumentNo").compareTo("56") == 0)
				tipoDTE = dteboxcliente.TipoDocumento.TIPO_56;
			else if(docType.get_ValueAsString("DocumentNo").compareTo("52") == 0)
				tipoDTE = dteboxcliente.TipoDocumento.TIPO_52;		
			else if(docType.get_ValueAsString("DocumentNo").compareTo("61") == 0)
				tipoDTE = dteboxcliente.TipoDocumento.TIPO_61;
			else
				tipoDTE = dteboxcliente.TipoDocumento.TIPO_52;
			long folio = Long.parseLong(mov.getDocumentNo());
			dteboxcliente.ResultadoRecuperarPDF resultadoPDF = servicio.RecuperarPdf(ambiente, grupo, (String)org.get_Value("Rut"), tipoDTE, folio);
			// Procesar respuesta
			if (resultadoPDF.getResultadoServicio().getEstado() == 0
					|| resultadoPDF.getResultadoServicio().getEstado() == 1) 
			{
				log.config("datos Usados: apiURL="+apiURL+" - apiAuth="+apiAuth+" - ambiente="+ambiente+
						" - grupo="+grupo+" - rut="+(String)org.get_Value("Rut")+" - tipoDTE="+tipoDTE+" - folio="+folio); 
			    //Usar los datos que vienen en el resultado de la llamada
				log.config("log resultado: "+resultadoPDF.getResultadoServicio().getDescripcion()+" - "+
						resultadoPDF.getResultadoServicio().getExcepcionOriginal()+ " - "+
						resultadoPDF.getResultadoServicio().getEstado());
			    byte[] pdf = resultadoPDF.getDatos();
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
					    	    	mov.set_CustomColumn("UrlGde",finalURL);
					    	    	mov.saveEx(get_TrxName());
					    	    }			    	    	
					    	} catch (Exception eFTPClient) 
					    	{
					    	    // Gestionar el error, mostrar pantalla, reescalar excepcion... etc...
					    		log.config("ERROR: "+eFTPClient.toString());
					    	} finally {
					    	    fis.close();
					    	}
				    	}
					} catch (IOException e) 
				    {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			} 
			else 
			{
				//DescripciÃ³n del error, actuar acorde
				error = error+resultado.getResultadoServicio().getDescripcion();
				//return error;
			}
			
		} 
		catch (Exception e) {
			// TODO: handle exception
			log.config(e.toString());
			error=e.toString();
		}
        //return "XML CG Generated "+ted+" "+pdf417+" "+error;
		
		return "Procesado"+error+" - "+ted+" - "+pdf417;
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
