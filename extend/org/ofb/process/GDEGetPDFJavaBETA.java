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

import java.util.Properties;

import org.compiere.util.*;
import org.ofb.model.OFBForward;
import org.apache.commons.net.ftp.FTPClient;
import org.compiere.model.*;
import org.compiere.process.SvrProcess;
import java.io.OutputStream;
import java.awt.Desktop;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.net.ftp.FTP; // Nos permite indicar si transfer BINARY o ASCII



/**
 *	Generate XML Consolidated from Invoice (Generic) 
 *	
 *  @author ininoles
 *  @version $Id: GDEGetPDF.java,v 1.2 19/05/2011 $
 */
public class GDEGetPDFJavaBETA extends SvrProcess
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
		msg=GetPDF(inv);		
		return msg;
	}	//	doIt
	
	public String GetPDF(MInvoice invoice)
    {
		// ﻿Recuperar el PDF de un documento
		// Recuperar PDF
		// Dependencias
		// - DTEBOXClienteJDK142.jar

		String ambiente = dteboxcliente.Ambiente.Homologacion;

		dteboxcliente.GrupoBusqueda grupo = dteboxcliente.GrupoBusqueda.Emitidos;

		String rut = "76129486-5";

		int tipoDTE = dteboxcliente.TipoDocumento.TIPO_33;
		long folio = 20;
		String apiURL = "http://200.6.99.206/api/Core.svc/core";
		String apiAuth = "2a84137d-a654-4f46-b873-2be446dadba7";
		
		dteboxcliente.Servicio servicio = new dteboxcliente.Servicio(apiURL, apiAuth);
		dteboxcliente.ResultadoRecuperarPDF resultado = servicio.RecuperarPdf(ambiente, grupo, rut, tipoDTE, folio);
		// Procesar respuesta

		if (resultado.getResultadoServicio().getEstado() == 0) 
		{
		    //Usar los datos que vienen en el resultado de la llamada
		    byte[] pdf = resultado.getDatos();
		    
		    //mfrojas
		    try 
		    {
		    	String pathServer = OFBForward.PathGDEServer();
		    	//String pathCliente = OFBForward.PathGDEServer();
		    	String pathCliente = OFBForward.PathGDEClient();
		    	if(pathServer != null && pathCliente != null
		    			&& pathServer .trim().length() > 0
		    			&& pathCliente .trim().length() > 0)
		    	{
		    		log.config("Archivo a guardar: "+pathServer+folio+".pdf");
			    	OutputStream out = new FileOutputStream(pathServer+folio+".pdf");
			    	out.write(pdf);
			    	out.close();
			    	
			    	//se sube pdf a ftp
			    	String remote_working_dir_path = "/www/OFBInterno2/Otros/recursos";
			    	String local_filepath = pathServer+folio+".pdf";
			    	String remote_filename = folio+".pdf";

			    	FileInputStream fis = new FileInputStream(local_filepath);
			    	FTPClient client = new FTPClient();
			    	try {
			    	    client.setBufferSize(512); // Opcional para definir Buffer size en bytes
			    	    client.connect("www.ofbconsulting.com",21); // no el puerto es por defecto, podemos usar client.connect("servidor.ftp.com");
			    	    client.login("ofb","ofb2014");
			    	    client.enterLocalPassiveMode(); // IMPORTANTE!!!! 
			    	    client.setFileType(FTP.BINARY_FILE_TYPE);
			    	    client.changeWorkingDirectory(remote_working_dir_path);
			    	    boolean uploadFile = client.storeFile(remote_filename,fis);
			    	    client.logout();
			    	    client.disconnect();

			    	    if ( uploadFile == false ) {
			    	        throw new Exception("Error al subir el fichero");
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
			//Descripción del error, actuar acorde
			String error = resultado.getResultadoServicio().getDescripcion();
			return error;
		}


		return null;
    }        
	
}	//	InvoiceCreateInOut
