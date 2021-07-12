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
package org.artilec.process;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.json.JSONObject;
import org.json.JSONArray;

import org.compiere.process.*;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.compiere.model.MConversionRate;
import java.util.Date;
import org.compiere.model.MInvoice;
import java.io.ByteArrayInputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;


/**
 *  GDExpress
 *  Obtener timbre
 *  
 *	@author mfrojas
 *	@version $Id: GDExpressObtenerTimbre.java,v 1.2 2019/06/06 00:00:00
 */
public class GDExpressObtenerTimbre extends SvrProcess
{
	/**	The Order				*/
	private int		p_C_Currency_ID;
	private Timestamp  p_DateTrx;
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_Currency_ID"))
				p_C_Currency_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if(name.equals("DateTrx"))
				p_DateTrx = ((Timestamp)para[i].getParameter());
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		
		try {
            
		    
			//Datos del TED
		    
			String rutEmisor = "16141808-0";
		   	 
			//gdegateway.timbre.TipoDocumento tipoDTE = gdegateway.timbre.TipoDocumento.BoletaElectronica;
			gdegateway.timbre.TipoDocumento tipoDTE = gdegateway.timbre.TipoDocumento.FacturaElectronica;
		    
			Date fechaEmision = new Date();
		    	int folio = 90;
		    
			String rutReceptor = "11111111-1";
		    
			String nombreReceptor = "Nombre Receptor";
		    
			int montoTotal = 100;
		    
			String descripcionPrimeraLinea = "Primer detalle";
		    
			//byte[] caf = gdegateway.timbre.Generador.leerBytes("C:\\Caf.xml");
			byte[] caf = gdegateway.timbre.Generador.leerBytes("D:\\PEGAS\\GDExpress\\Caf.xml");

		    

			//Pdf417
		    
			int tipoPdf417 = gdegateway.timbre.Enums.TipoPdf417.GRAFICO;
		    
			int pdfColumas = 15;
		    
			int pdfNivelCorreccion = 5;
		    
			boolean pdfTruncado = false;
		    
			String pdfFormato = gdegateway.timbre.Enums.FormatoPdf417.JPEG;
		    
			String directorioTemporal = "C:\\temp\\";
		            
		    

			//Generar TED
		    
			gdegateway.timbre.TED tedGenerado = gdegateway.timbre.Generador.GenerarTimbre(
		rutEmisor, tipoDTE, fechaEmision, folio, rutReceptor, nombreReceptor, montoTotal, 
		        descripcionPrimeraLinea, 
		caf, tipoPdf417, pdfColumas, pdfNivelCorreccion, pdfTruncado, pdfFormato, directorioTemporal);

		    

			//Valor del TED (xml)
		    
			String valor = tedGenerado.getValor();

		    

			//Bytes de la imagen generado, guardarla a disco o en una BD...
		    


			byte[] imagen = tedGenerado.codigoComoBytes();
			
		     ByteArrayInputStream bis = new ByteArrayInputStream(imagen);
		      BufferedImage bImage2 = ImageIO.read(bis);
		      ImageIO.write(bImage2, "jpg", new File("D://output2-15.jpg") );
		      
		      System.out.println("image created");
		      
		      
			MInvoice inv = new MInvoice(getCtx(),2005813,get_TrxName());
			inv.set_CustomColumn("xmltimbreprueba", valor);
			inv.set_CustomColumn("imagendeprueba", bImage2);
			inv.save();


		} 

			catch (Exception ex) 
			{
		    
				ex.printStackTrace();

			}
				
		return "";
	}	//	doIt
	
	
}	//	CopyFromOrder
