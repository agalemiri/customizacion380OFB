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
package org.copesa.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

/**
 *	COPESA Callouts valida dirección
 *	
 *  @author Humberto Chacón
 *  @version $Id: CalloutCOPESABPLocation.java, 2017/06/16 
 *  
 */
public class CalloutCOPESABPLocation extends CalloutEngine
{
	private CLogger			log = CLogger.getCLogger (getClass());
	/**
	 *
	 */
	public String checkAddress (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value==null || value.toString().trim().compareTo("") == 0)
			return "";
		
		MapcityAddress mp = new MapcityAddress();
		if ( !mp.normalize((String)value) )
			return "No se pudo normalizar dirección";

		boolean hascoverage = mp.checkCoverage();
		
		mTab.setValue("AddressNumber", mp.getAltura());
		mTab.setValue("StreetType", mp.getTipo_via_largo().substring(0, 1) );
		mTab.setValue("ZipCode", mp.getCodigo_postal() );
		mTab.setValue("HomeNumber", mp.getAnexo());
        mTab.setValue("latitud", Double.toString(mp.getLatitud()));		
        mTab.setValue("longitud", Double.toString(mp.getLongitud()));		
        mTab.setValue("gse", mp.getGse());
        mTab.setValue("geocoding", mp.getGeocoding());
        
		try
		{
			int[] addresdata = this.getAddressIds(mp, null);
			mTab.setValue("C_City_ID", addresdata[0]);
			mTab.setValue("C_Province_ID", addresdata[1]);
			mTab.setValue("C_Region_ID", addresdata[2]);
			mTab.setValue("C_Street_ID", addresdata[3]);
		}
		catch( Exception e )
		{
			log.warning("Error normalizando dirección " + value.toString() + ": " + e.getMessage());
		}

		if( mp.isChecked() )
		{
			mTab.setValue("Sector", mp.getSector());
			mTab.setValue("Zone", mp.getZona());
		}
		else
		{	
			mTab.setValue("Sector", "");
			mTab.setValue("Zone", "");
			return "No se pudo determinar sector/zona de dirección";
		}
		if( !hascoverage )
			return "Dirección no tiene cobertura de reparto";

		//mField.setValue();
		return "";
    }
	
	
	private int[] getAddressIds(MapcityAddress _mp, String _trxname) throws Exception
	{
		int[] addressdata = new int[4];
		String sql = "SELECT c_city_id, c_province_id, c_region_id from c_city where lower(name) = lower(?)";
	    
	    PreparedStatement pstmt = DB.prepareStatement(sql, _trxname);
	    pstmt.setString(1, _mp.getComuna_largo());
	    
	    ResultSet rs = pstmt.executeQuery();
	    
	    if( rs.next() )
	    {	
	    	addressdata[0] = rs.getInt(1);
	    	addressdata[1] = rs.getInt(2);
	    	addressdata[2] = rs.getInt(3);
	    }	
	    pstmt.close();
	    
		sql = "SELECT c_street_id from c_street where lower(name) = lower(?) and c_city_id = ?" ;
	    
	    pstmt = DB.prepareStatement(sql, _trxname);
	    pstmt.setString(1, _mp.getNombre_via_largo());
	    pstmt.setInt(2, addressdata[0]);
	    
	    rs = pstmt.executeQuery();
	    
	    if( rs.next() )
	    {	
	    	addressdata[3] = rs.getInt(1);
	    }	

	    pstmt.close();
	    
	    return addressdata;
	}
	
}	

