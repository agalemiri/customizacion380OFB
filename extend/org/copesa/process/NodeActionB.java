package org.copesa.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;


/*
 * Acción que genera líneas de SR para dividir una LTFULL en LTLV + LTSD
 */
public class NodeActionB implements INodeAction {
	private CLogger			log = CLogger.getCLogger (getClass());
    private static final int LTLV = 2000011;
    private static final int LTSD = 2000012;
    
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception {
		String sql = "SELECT ol.Line, ol.C_BPartner_ID, ol.C_BPartnerRef_ID,ol.M_Product_ID," +
				" ol.DatePromised2, ol.DatePromised3, ol.C_BPartner_Location_ID,ol.QtyEntered, ol.C_OrderLine_ID, loc.Sector, loc.Zone " +
				" FROM C_OrderLine ol " +
				" INNER JOIN M_Product mp ON (ol.M_Product_ID = mp.M_Product_ID)" +
				" INNER JOIN M_Product_Category pc ON (mp.M_Product_Category_ID = pc.M_Product_Category_ID) " +
				" INNER JOIN C_BPartner_Location loc ON (ol.c_bpartner_location_id = loc.c_bpartner_location_id) " +
				" WHERE pc.description like 'EDITORIAL' " +
				" AND ol.IsActive = 'Y' AND ol.M_Product_ID > 0 AND ol.datepromised3 is not null" +
				" AND ol.C_Order_ID = "+_req.get_ValueAsInt("C_Order_ID") +
				" AND mp.value = 'G_LA_TERCERA_FULL'";
		if(_req.get_ValueAsInt("C_BPartner_ID") > 0)
			sql = sql + " AND ol.C_BPartnerRef_ID = " + _req.get_ValueAsInt("C_BPartner_ID");
		
		if(_req.get_ValueAsInt("C_BPartner_Location_ID") > 0)
			sql = sql + " AND ol.C_BPartner_Location_ID = " + _req.get_ValueAsInt("C_BPartner_Location_ID");
		
		if(_req.get_ValueAsInt("C_OrderLine_ID") > 0)
			sql = sql + " AND ol.C_OrderLine_ID = " + _req.get_ValueAsInt("C_OrderLine_ID");
		sql = sql + " ORDER BY ol.Line";
			
		PreparedStatement pstmt = DB.prepareStatement (sql, _req.get_TrxName());		
		try
		{
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next())
			{
				createLinefromRs(_req, rs, LTLV).save();
				createLinefromRs(_req, rs, LTSD).save();
			}
		}catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
    }
	
	private X_R_ServiceRequestLine createLinefromRs( X_R_ServiceRequest _req, ResultSet _rs, int _product) throws Exception
	{
		X_R_ServiceRequestLine rLine = new X_R_ServiceRequestLine(_req.getCtx(), 0, _req.get_TrxName());
		rLine.setR_ServiceRequest_ID(_req.get_ID());
		rLine.setAD_Org_ID(_req.getAD_Org_ID());
		rLine.setLine(_rs.getInt("Line"));
		rLine.setC_BPartner_ID(_rs.getInt("C_BPartner_ID"));
		rLine.setC_BPartnerRef_ID(_rs.getInt("C_BPartnerRef_ID"));
		rLine.setM_Product_ID(_product);
		rLine.setDatePromised2(_rs.getTimestamp("DatePromised2"));
		rLine.setDatePromised3(_rs.getTimestamp("DatePromised3"));
		rLine.setC_BPartner_Location_ID(_rs.getInt("C_BPartner_Location_ID"));
		rLine.setQtyEntered(_rs.getBigDecimal("QtyEntered"));
		rLine.set_CustomColumn("C_OrderLine_ID", _rs.getInt("C_OrderLine_ID"));
		rLine.set_CustomColumn("Sector", _rs.getString("Sector"));
		rLine.set_CustomColumn("Zone", _rs.getString("Zone"));
		Timestamp inicio = (Timestamp)_req.get_Value("StartDate");
		Timestamp fin = (Timestamp)_req.get_Value("EndDate");
		rLine.setRequestAcction("B");
		if(inicio != null)
			rLine.setDatePromised2Ref(inicio);
		if(fin != null)
			rLine.setDatePromised3Ref(fin);
		if(_req.get_ValueAsInt("C_BPartner_LocationRef_ID") > 0)
			rLine.set_CustomColumn("C_BPartner_LocationRef_ID", _req.get_ValueAsInt("C_BPartner_LocationRef_ID"));
		return rLine;
	}
}