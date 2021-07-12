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
package org.ofb.process;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.json.JSONObject;
import org.json.JSONArray;

import org.compiere.process.*;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.compiere.model.MConversionRate;
import org.compiere.model.X_I_OrderMC;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;



/**
 *  Copy Order Lines
 *
 *	@author mfrojas
 *	@version $Id: JSonChileCompra.java,v 1.2 2006/07/30 00:51:02 mfrojas Exp $
 */
public class JSonChileCompra extends SvrProcess
{
	/**	The Order				*/
	private String	p_DocNo;
	private String  p_Token;
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
			else if (name.equals("DocNo"))
				p_DocNo = para[i].getParameterAsString();
			else if(name.equals("Token"))
				p_Token = para[i].getParameterAsString();
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
		
		
		
		log.config("TOKEN = "+p_Token);
		log.config("ORDEN = "+p_DocNo);
		//JSONParser parser = new JSONParser();
	    //JsonParser jp = new JsonParser();
		//String date_s = "01-01-2017";
		//log.config("date_s "+date_s);
		//String website = "http://mindicador.cl/api/"+moneda+"/"+date_s+"";
		String website = "http://api.mercadopublico.cl/servicios/v1/publico/ordenesdecompra.json?codigo="+p_DocNo+"&ticket="+p_Token;
		InputStream is = new URL(website).openStream();
		log.config("website = "+website);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(is,Charset.forName("UTF-8")));
		
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		
		while ((counter = rd.read()) != -1)
			sb.append((char)counter);
		
		String jsonText = sb.toString();
		log.config("jsonText = "+jsonText);
		
	    JSONObject json = new JSONObject(jsonText);
	    JSONArray jsonarr = json.getJSONArray("Listado");
	    
	    int lenght = jsonarr.length();
	    
	    //Se obtiene el nombre, codigo y descripción de la OC
	    
	    String Codigo = jsonarr.getJSONObject(0).get("Codigo").toString();
	    String Nombre = jsonarr.getJSONObject(0).get("Nombre").toString();
	    String Descripcion = jsonarr.getJSONObject(0).get("Descripcion").toString();
	    
		log.config("Valor : "+json.toString());
		log.config("Codigo "+Codigo);
		log.config("Nombre "+Nombre);

		
		//Obtener proveedor
		
		String Proveedor = jsonarr.getJSONObject(0).get("Proveedor").toString();
		log.config("Proveedor "+Proveedor);
		
	    
	    JSONObject Prov = new JSONObject(jsonarr.getJSONObject(0).get("Proveedor").toString());
	    
	    
	    String Nombre2 = Prov.getString("Nombre");
	    String Rut = Prov.getString("RutSucursal");
	    
	    //Se quitan los puntos y el digito verificador del rut
	    String Rut2 = Rut.replace(".", "");
	    Rut2 = Rut2.substring(0, Rut2.length()-2);

		log.config("Proveedor "+Proveedor);
		log.config("Proveedor "+Nombre2);
		log.config("Rut Proveedor "+Rut2);
		
		
		//Traer producto, precio, descripcion
	    JSONObject line = new JSONObject(jsonarr.getJSONObject(0).get("Items").toString());
	    JSONArray jsonarrline = line.getJSONArray("Listado");
	    
	    BigDecimal PrecioNeto = Env.ZERO;
	    String Producto = null;
	    String EspProveedor = null;

		for (int i = 0; i < jsonarrline.length(); i++)
		{
		    PrecioNeto = new BigDecimal(jsonarrline.getJSONObject(i).get("PrecioNeto").toString());
		    
		    Producto = jsonarrline.getJSONObject(i).get("Producto").toString();
		    EspProveedor = jsonarrline.getJSONObject(i).get("EspecificacionProveedor").toString();

		    log.config("Precio neto "+PrecioNeto);
		    log.config("Producto "+Producto);
		    log.config("EspProveedor "+EspProveedor);
		    
		    X_I_OrderMC omc = new X_I_OrderMC(getCtx(), 0, get_TrxName());
		    
		    omc.setDocumentNo(Codigo);
		    omc.setBPartnerValue(Rut2);
		    omc.setDescription(Descripcion);
		    omc.setLineDescription(EspProveedor);
		    omc.setPriceActual(PrecioNeto);
		    omc.setProductName(Producto);
		    
		    omc.save();
		}

		//mfrojas Ahora que los registros ya están guardados, se debe traer la información de chile compra y 
		//cruzarla con la OC.
		//Se parte por la cabecera
		
		int c_order_id = DB.getSQLValue(get_TrxName(), "SELECT max(C_Order_ID) from c_order where documentno like '%"+Codigo+"%'");
		
		MOrder ord = new MOrder(getCtx(), c_order_id, get_TrxName());
		
		ord.set_CustomColumn("ProveedorCC", Rut2);
		ord.set_CustomColumn("DescriptionCC",Descripcion);
		
		//Se debe ver si el proveedor de la OC ADempiere es el mismo de CC.
		
		int c_bpartner_id = DB.getSQLValue(get_TrxName(), "SELECT max(c_bpartner_id) from c_bpartner where value like '"+Rut2+"'");
		if(c_bpartner_id > 0)
			ord.setC_BPartner_ID(c_bpartner_id);
		
		ord.save();
		//Ahora se debe buscar las líneas. 
		
		String sql = "SELECT priceactual, productname, linedescription from i_ordermc where " +
				" documentno like '%"+Codigo+"%'";
		
		PreparedStatement ps = DB.prepareStatement(sql, ord.get_TrxName());
		
		ResultSet rs = ps.executeQuery();				
		while (rs.next()) 
		{
			BigDecimal precioactual = rs.getBigDecimal("priceactual");
			
			//Se busca la linea de OC donde el precio sea el indicado
			
			String sqlline = "SELECT max(c_orderline_id) from c_orderline where c_order_id = "+ord.get_ID()+" and " +
					" priceentered = "+precioactual;
			
			int c_orderline = DB.getSQLValue(get_TrxName(), sqlline);
			
			if(c_orderline > 0)
			{
				MOrderLine ordl = new MOrderLine(getCtx(), c_orderline, get_TrxName());
				
				ordl.set_CustomColumn("LineDescriptionCC", EspProveedor);
				ordl.set_CustomColumn("PriceActualCC", PrecioNeto);
				ordl.set_CustomColumn("ProductNameCC", Producto);
				
				ordl.save();
				

			}
			
		}


		return "";
	}	//	doIt
	
	
}	//	CopyFromOrder
