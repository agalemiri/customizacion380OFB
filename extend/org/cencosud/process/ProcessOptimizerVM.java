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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import org.compiere.model.*;
import org.compiere.process.SvrProcess;
import org.compiere.util.*;
import org.ofb.model.OFBForward;
/**
 *	
 *	
 *  @author italo niñoles ininoles
 *  @version $Id: ProcessOptimizerVM.java,v 1.2 2011/06/12 00:51:01  $
 *  
 */
public class ProcessOptimizerVM extends SvrProcess
{
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	private int 			p_ID_Order;
	

	protected void prepare()
	{
		
		p_ID_Order=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		//primero se importan los documentos
		//MOrder order = null;
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		String rutaArchivo = OFBForward.CencosudRutaStock();
		//1-Se importan stock SD
		//se borra stock actual
		DB.executeUpdate("DELETE FROM I_InventoryCencosudSD", get_TrxName());
		rutaArchivo = OFBForward.CencosudRutaStockSD();
		String pFileO = rutaArchivo;
		ArrayList<String[]> datosO=new ArrayList<String[]>();
		int cantLines = 0;
	    try 
	    {
	    	FileInputStream fis =new FileInputStream(pFileO);
		    InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
		    BufferedReader br = new BufferedReader(isr);
		    String linea=br.readLine();		   
		    //se recorre archivo
		    while(linea!=null)
		    {
		        datosO.add(linea.split(";"));
			    linea=br.readLine();
			    cantLines = cantLines + 1;
		    }
		    br.close();
		    isr.close();
		    fis.close();
		    //log.config(datosO.toString());
		    //se leen campos del archivo
		    for (int x = 1; x < cantLines; x++)
		    {	
		    	//se busca ubicacion, producto, cantidad y fecha
		    	int ID_Product = DB.getSQLValue(get_TrxName(), "SELECT MAX(M_Product_ID) FROM M_Product WHERE IsActive = 'Y' AND help like '"+datosO.get(x)[0]+"'");		    	
		    	if(ID_Product > 0 )
		    	{
		    		BigDecimal qty = (BigDecimal) decimalFormat.parse(datosO.get(x)[3].trim());
			    	BigDecimal qtyBox = (BigDecimal) decimalFormat.parse(datosO.get(x)[5].trim());
			    	BigDecimal DayDelivered = (BigDecimal) decimalFormat.parse(datosO.get(x)[6].trim());
			    	if(qty == null)
			    		qty = Env.ZERO;
			    	if(qtyBox == null)
			    		qtyBox = Env.ZERO;
			    	if(DayDelivered == null)
			    		DayDelivered = Env.ZERO;	
		    		
		    		X_I_InventoryCencosudSD iInventorySD = new X_I_InventoryCencosudSD(getCtx(), 0, get_TrxName());
		    		iInventorySD.setAD_Org_ID(0);
		    		iInventorySD.setM_Product_ID(ID_Product);
		    		iInventorySD.setQty(qty);
		    		iInventorySD.setQtyAvailable(qtyBox);
		    		iInventorySD.setQtyUsed(Env.ZERO);
		    		iInventorySD.setqtyBox(qtyBox);
		    		iInventorySD.setDayDelivered(DayDelivered);
		    		iInventorySD.saveEx(get_TrxName());
		    	}
	    	}
		    datosO = null;
	    }
		catch (Exception e) 
		{
			log.config("ERROR: "+e);
		}
		commitEx();
		
		//se realiza asignación de stock
		//ciclo de prioridades
		String TiCoPro;
		int ID_OLine = 0;
		String sql = "SELECT M_Locator_ID,Type,Qty,M_PriorityCenco_ID FROM M_PriorityCenco WHERE IsActive= 'Y'" +
				" Order By Qty asc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlF = "";
		PreparedStatement pstmtF = null;
		ResultSet rsF = null;
		String sqlP = "";
		PreparedStatement pstmtP = null;
		ResultSet rsP = null;
		BigDecimal qpFinalBox = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if(rs.getInt("M_PriorityCenco_ID") ==1000015)
				{
					log.config("stop de prueba");
				}
				
				//se obtiene ticopro para usar mas adelante
				TiCoPro = DB.getSQLValueString(get_TrxName(), "SELECT string_agg(Name,',') FROM M_TicoPro WHERE IsActive = 'Y'" +
						" AND Type = '"+rs.getString("Type")+"'");
				if(TiCoPro != null)
					TiCoPro = TiCoPro.replace(",", "','");
				
				//se hace un segundo ciclo buscando familias de la tipologia
				sqlF = "SELECT M_product_Group_ID FROM M_TypologyCenco tc WHERE tc.Type = '"+rs.getString("Type")+"'" +
						" AND M_product_Group_ID IS NOT NULL";
				pstmtF = DB.prepareStatement(sqlF, get_TrxName());
				rsF = pstmtF.executeQuery();
				while(rsF.next())
				{
					//se obtiene UM para calculos futuros
					qpFinalBox = DB.getSQLValueBD(get_TrxName(), "SELECT QtyMultiplier FROM M_Product_Group WHERE M_Product_Group_ID = "+rsF.getInt("M_product_Group_ID"));
					
					//se buscan lineas de la OC que correspondan
					ID_OLine = DB.getSQLValue(get_TrxName(), "SELECT MAX(C_OrderLine_ID) FROM C_OrderLine" +
							" WHERE C_Order_ID = "+p_ID_Order+" AND M_Locator_ID = "+rs.getInt("M_Locator_ID")+" AND M_product_Group_ID="+rsF.getInt("M_product_Group_ID"));
					
					if(ID_OLine > 0)
					{
						MOrderLine oLine = new MOrderLine(getCtx(), ID_OLine, get_TrxName());
						//se borran registros antiguos para la Orden Line
						DB.executeUpdate("DELETE FROM M_AllocateStockSD WHERE C_OrderLine_ID="+ID_OLine, get_TrxName());
						//quiebre para debug 
						if(oLine.get_ValueAsInt("M_Locator_ID") == 2000042
								&& oLine.get_ValueAsInt("M_product_Group_ID") == 2000010)
						{
							log.config("log");
						}
						BigDecimal qtyToAsig = (BigDecimal)oLine.get_Value("qpFinalBox");						
						if(qtyToAsig == null)
							qtyToAsig = Env.ZERO;
						if(qtyToAsig.compareTo(Env.ZERO) > 0)
						{
							BigDecimal flag = qtyToAsig;
							BigDecimal qtyCalculatedOP = Env.ZERO;
							if(qtyToAsig.compareTo(Env.ZERO) > 0)
							{
								//se buscan productos de la familia correspondiente y del grupo ticopro
								sqlP = "SELECT I_InventoryCencosudSD_ID  FROM I_InventoryCencosudSD sc" +
										" INNER JOIN M_Product mp ON (sc.M_Product_ID = mp.M_Product_ID)" +
										" WHERE mp.M_product_Group_ID = "+rsF.getInt("M_product_Group_ID")+" AND mp.TiCopro IN ('"+TiCoPro+"')"+ 
										" AND sc.QtyAvailable > 0 "+
										" ORDER BY DayDelivered asc,QtyAvailable desc ";
								pstmtP = DB.prepareStatement(sqlP, get_TrxName());
								rsP = pstmtP.executeQuery();
								while(rsP.next() && flag.compareTo(Env.ZERO) > 0)
								{
									X_I_InventoryCencosudSD insc = new X_I_InventoryCencosudSD(getCtx(), rsP.getInt("I_InventoryCencosudSD_ID"), get_TrxName());
									if(flag.compareTo(insc.getQtyAvailable()) <= 0)
									{
										//cada vez que se usa una linea de stock SD se guarda registro
										X_M_AllocateStockSD allo = new X_M_AllocateStockSD(getCtx(), 0, get_TrxName());
										allo.setC_OrderLine_ID(ID_OLine);
										allo.setAD_Org_ID(oLine.getAD_Org_ID());
										allo.setM_PriorityCenco_ID(rs.getInt("M_PriorityCenco_ID"));
										allo.setI_InventoryCencosudSD_ID(insc.get_ID());
										allo.setCCPPKg(flag.multiply(qpFinalBox));
										allo.setCCPPUMB(flag);
										allo.saveEx(get_TrxName());
										//end
										qtyCalculatedOP = qtyCalculatedOP.add(flag);										
										insc.setQtyAvailable(insc.getQtyAvailable().subtract(flag));
										insc.setQtyUsed(insc.getQtyUsed().add(flag));
										flag = flag.subtract(insc.getqtyBox());
									}
									else
									{
										//cada vez que se usa una linea de stock SD se guarda registro
										X_M_AllocateStockSD allo = new X_M_AllocateStockSD(getCtx(), 0, get_TrxName());
										allo.setC_OrderLine_ID(ID_OLine);
										allo.setAD_Org_ID(oLine.getAD_Org_ID());
										allo.setM_PriorityCenco_ID(rs.getInt("M_PriorityCenco_ID"));
										allo.setI_InventoryCencosudSD_ID(insc.get_ID());
										allo.setCCPPKg(insc.getQtyAvailable().multiply(qpFinalBox));
										allo.setCCPPUMB(insc.getQtyAvailable());
										allo.saveEx(get_TrxName());
										//end
										qtyCalculatedOP = qtyCalculatedOP.add(insc.getQtyAvailable());									
										flag = flag.subtract(insc.getQtyAvailable());
										insc.setQtyAvailable(Env.ZERO);
										insc.setQtyUsed(insc.getQty());
									}
									insc.saveEx(get_TrxName());
								}
								oLine.set_CustomColumn("qtyCalculatedOP", qtyCalculatedOP);
								oLine.set_CustomColumn("type", rs.getString("Type"));
								//oLine.set_CustomColumn("qtyPriority", rs.getString("Qty"));
								if(flag.compareTo(Env.ZERO) > 0)
									oLine.set_CustomColumn("IsPoverty",true);
							}
							oLine.saveEx(get_TrxName());
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			log.config(e.toString());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return "Procesado";		
	}	//	doIt	
}	//	CopyOrder

