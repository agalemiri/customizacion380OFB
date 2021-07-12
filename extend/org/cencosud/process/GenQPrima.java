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
package org.cencosud.process;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.compiere.model.*;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.ofb.model.OFBForward;
/**
 *	
 *	
 *  @author italo niñoles ininoles
 *  @version $Id: GenQPrima.java,v 1.2 2011/06/12 00:51:01  $
 *  
 */
public class GenQPrima extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	private int 			p_ID_Order;
	
	protected void prepare()
	{	
		p_ID_Order = getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		MOrder order = new MOrder(getCtx(), p_ID_Order, get_TrxName());
		//revision de lineas para generar calculos
		MOrderLine[] lines = order.getLines(true, null);	//	Line is default
		BigDecimal ma45 = Env.ZERO;
		BigDecimal ma13 = Env.ZERO;
		BigDecimal ma14 = Env.ZERO;
		BigDecimal ddsProy = null;
		BigDecimal ddsDeseado = null;
		//String StrQtyCamara = ""
		BigDecimal qtyCamara = null;
		BigDecimal ddsAjDeseado = null;
		//BigDecimal ddsAjCamara = Env.ZERO;
		BigDecimal Saturacion = null;
		BigDecimal qpFinal = null;
		BigDecimal qpFinalBox = null;
		String ma13Str = "";
		int cantPromo = 0;
		for (int i = 0; i < lines.length; i++)
		{
			MOrderLine line = lines[i];
			if(line.get_ValueAsInt("M_Locator_ID") > 0
					&& line.get_ValueAsInt("M_product_Group_ID") > 0)
			{
				//quiebre para debug 
				if(line.get_ValueAsInt("M_Locator_ID") == 2000042
						&& line.get_ValueAsInt("M_product_Group_ID") == 2000055)
				{
					log.config("log");
				}

				ma45=DB.getSQLValueBD(get_TrxName(), "SELECT ma45 FROM M_Product_GroupActive " +
						" WHERE M_Locator_ID="+line.get_ValueAsInt("M_Locator_ID")+
						" AND M_product_Group_ID="+line.get_ValueAsInt("M_product_Group_ID"));
				//mfrojas inicializacion: Cuando el primero registro de la oc no tiene ma45, se caia. 
				if(ma45 == null || ma45.compareTo(Env.ZERO) == 0) 
					ma45 = Env.ONE;
				
				//validacion de valores negativos
				ma13Str = DB.getSQLValueString(get_TrxName(), "SELECT ma13 FROM M_Product_GroupActive " +
						" WHERE M_Locator_ID="+line.get_ValueAsInt("M_Locator_ID")+
						" AND M_product_Group_ID="+line.get_ValueAsInt("M_product_Group_ID"));
				if(ma13Str != null && ma13Str.contains("-"))
					ma13Str = "-"+ma13Str.substring(0,ma13Str.length()-1);			
				if(ma13Str != null) 
						ma13=new BigDecimal(ma13Str);
				ma14=DB.getSQLValueBD(get_TrxName(), "SELECT ma14 FROM M_Product_GroupActive " +
						" WHERE M_Locator_ID="+line.get_ValueAsInt("M_Locator_ID")+
						" AND M_product_Group_ID="+line.get_ValueAsInt("M_product_Group_ID"));
				//calculo DDS Disponible(normal)
				BigDecimal qtyStock = (BigDecimal)line.get_Value("q2");				
				if(qtyStock == null)
					qtyStock = ma13;
				BigDecimal qtyTransito = (BigDecimal)line.get_Value("q3");
				if(qtyTransito == null)
					qtyTransito = ma14;
				if(qtyStock != null && ma45 != null && ma45.compareTo(Env.ZERO) != 0)
					line.set_CustomColumn("ddsDisponible", qtyStock.divide(ma45, 0, RoundingMode.HALF_EVEN));					
				//calculo DDS Proyectado
				
				if(qtyStock != null && qtyTransito != null && ma45 != null && ma45.compareTo(Env.ZERO) != 0)
				{	
					ddsProy = qtyStock.add(line.getQtyOrdered());
					ddsProy = ddsProy.add(qtyTransito);
					ddsProy = ddsProy.divide(ma45, 5, RoundingMode.HALF_EVEN);
					line.set_CustomColumn("ddsProy", ddsProy);
				}
				if(ma45 == null)
					ma45 = Env.ZERO;
				//Ajuste con DDS Deseado
				//DDS deseado
				ddsDeseado = DB.getSQLValueBD(get_TrxName(), "SELECT MAX(Qty) FROM M_LocatorSetAmt " +
						" WHERE M_Locator_ID="+line.get_ValueAsInt("M_Locator_ID")+
						" AND M_product_Group_ID="+line.get_ValueAsInt("M_product_Group_ID"));
				//si no encuentra registro lo busca en la bodega
				if(ddsDeseado == null || ddsDeseado.compareTo(Env.ZERO) == 0)
					ddsDeseado = DB.getSQLValueBD(get_TrxName(), "SELECT MAX(mw.Qty) FROM M_Locator ml" +
							" INNER JOIN M_Warehouse mw ON (ml.M_Warehouse_ID = mw.M_Warehouse_ID)" +
							" WHERE M_Locator_ID="+line.get_ValueAsInt("M_Locator_ID"));
				
				if(ddsDeseado != null && ddsProy != null)
				{
					if(ddsDeseado.compareTo(ddsProy) > 0)
					{
						line.set_CustomColumn("ddsAjDeseado", ddsDeseado);
						ddsAjDeseado = ddsDeseado;
					}
					else
					{
						line.set_CustomColumn("ddsAjDeseado", ddsProy);
						ddsAjDeseado = ddsProy;
					}
				}
				else
					ddsAjDeseado = ddsProy;
				//ajuste con capacidad de camara
				String colorCamara = DB.getSQLValueString(get_TrxName(),"SELECT CamaraV FROM M_Locator" +
						" WHERE M_Locator_ID="+line.get_ValueAsInt("M_Locator_ID"));
				//camara color rojo
				if(colorCamara.compareTo("7") == 0)
				{
					if(ddsAjDeseado.compareTo(new BigDecimal(7)) > 0)
						ddsAjDeseado = new BigDecimal(7);
				}
				//camara color amarilloa
				else if(colorCamara.compareTo("14") == 0)
				{
					if(ddsAjDeseado.compareTo(new BigDecimal(14)) > 0)
						ddsAjDeseado = new BigDecimal(14);
				}
				if(ddsAjDeseado == null)
					ddsAjDeseado = Env.ZERO;
				line.set_CustomColumn("ddsAjCamara", ddsAjDeseado);
				
				//se calcula nuevo q prima despues de los ajustes.
				qpFinal = ddsAjDeseado.multiply(ma45);
				qpFinal = qpFinal.subtract(qtyStock);
				qpFinal = qpFinal.subtract(qtyTransito);
				qpFinal = qpFinal.setScale(0, RoundingMode.HALF_UP);
				qpFinalBox = DB.getSQLValueBD(get_TrxName(), "SELECT QtyMultiplier FROM M_Product_Group WHERE M_Product_Group_ID = "+line.get_ValueAsInt("M_Product_Group_ID"));
				
				//se busca si esta saturado
				//saturacion se busca el total de stock de todas las familias
				qtyCamara = DB.getSQLValueBD(get_TrxName(), "SELECT QtyCamaraV FROM M_Locator" +
						" WHERE M_Locator_ID="+line.get_ValueAsInt("M_Locator_ID"));
				//mfrojas
				log.config("linea "+line.getLine());
				if(qtyCamara == null)
						qtyCamara = Env.ZERO;
				Saturacion = DB.getSQLValueBD(get_TrxName(), "SELECT SUM(q2)FROM C_OrderLine WHERE C_Order_ID="+p_ID_Order+
						" AND M_Locator_ID = "+line.get_ValueAsInt("M_Locator_ID"));
				Saturacion = Saturacion.divide(qtyCamara,2, RoundingMode.HALF_EVEN);
				Saturacion = Saturacion.multiply(Env.ONEHUNDRED);
				if(Saturacion.compareTo(OFBForward.CencosudSaturacionPor()) > 0)
				{
					cantPromo = DB.getSQLValue(get_TrxName(), "SELECT COUNT(1) FROM M_WarehousePromo wp" +
							" INNER JOIN M_Locator ml ON (wp.M_Warehouse_ID = ml.M_Warehouse_ID)" +
							" WHERE wp.IsACtive = 'Y' AND M_Locator_ID="+line.get_ValueAsInt("M_Locator_ID")+
							" AND wp.M_Product_Group_ID="+line.get_ValueAsInt("M_product_Group_ID"));
					if(cantPromo <= 0)
					{
						line.set_CustomColumn("qpFinal", Env.ZERO);
						line.set_CustomColumn("qpFinalBox",Env.ZERO);
					}
					else
					{
						line.set_CustomColumn("qpFinalBox",qpFinal.divide(qpFinalBox,0, RoundingMode.HALF_EVEN));
						//se aproxima en base a la cantidad de cajas
						//BigDecimal QtyFBox = ((BigDecimal)line.get_Value("qpFinalBox")).multiply(qpFinalBox);
						line.set_CustomColumn("qpFinal", ((BigDecimal)line.get_Value("qpFinalBox")).multiply(qpFinalBox));
						
					}
				}
				else
				{
					line.set_CustomColumn("qpFinalBox",qpFinal.divide(qpFinalBox,0, RoundingMode.HALF_EVEN));
					//se aproxima en base a la cantidad de cajas
					//BigDecimal QtyFBox = ((BigDecimal)line.get_Value("qpFinalBox")).multiply(qpFinalBox);
					line.set_CustomColumn("qpFinal", ((BigDecimal)line.get_Value("qpFinalBox")).multiply(qpFinalBox));
					
				}
				line.saveEx(get_TrxName());
			}
		}
		return "Q' Calculadas";		
	}	//	doIt	
}	//	

