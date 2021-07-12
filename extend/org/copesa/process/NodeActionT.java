package org.copesa.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;

import org.compiere.model.X_C_OrderShipCalendar;
import org.compiere.model.X_R_ServiceRLineAction;
import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.copesa.model.MapcityAddress;
import org.copesa.model.ZoneType;
import org.copesa.utils.DateUtils;

public class NodeActionT implements INodeAction {
	private CLogger			log = CLogger.getCLogger (getClass());
	
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception 
	{
		this.changeLocationTemp(_req, _rline);
	}
	
	private boolean validateLocation(X_R_ServiceRequestLine _rline)
	{
		MapcityAddress mp = new MapcityAddress();
		String sql = "select sector || '-' || zone from c_bpartner_location loc " +
		             "where loc.c_bpartner_location_ID = ?";
		
		String sz = DB.getSQLValueString(_rline.get_TrxName(), sql, _rline.getC_BPartner_LocationRef_ID());
		
		if ( !mp.validateSectorZone(sz) )
			return false;
		
		ZoneType zonetype = mp.getZoneType();
		if( zonetype.compareTo(ZoneType.ZONETYPE_SD) != 0)
			return true;
		
		sql = "select cal.isenabledSD from c_calendar_copesa cal " + 
		             "  join c_orderline col on col.c_calendarcopesa_id = cal.c_calendarcopesa_id " +
				     "where col.c_orderline_id = " + _rline.getC_OrderLine_ID();
		
		String isenabledsd = DB.getSQLValueString(_rline.get_TrxName(), sql, (Object)null);
		
		return isenabledsd.equalsIgnoreCase("Y");
		
	}
	
	public void changeLocationPermanent(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception
	{
		if( !validateLocation(_rline) )
			throw new AdempiereUserError("La dirección elegida no es válida, verifique cobertura de despacho.");
		
		//actualizamos direccion de la linea de orden
		DB.executeUpdate("UPDATE C_OrderLine SET C_Bpartner_Location_ID = " + _rline.getC_BPartner_LocationRef_ID() + 
				         " WHERE C_OrderLine_ID = " + _rline.get_ValueAsInt("C_OrderLine_ID"), _rline.get_TrxName());
		
		this.changeLocation(_req, _rline, false);
		
	}

	public void changeLocationTemp(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception
	{
		this.changeLocation(_req, _rline, true);
	}
	
	public void deleteUselessLines( X_R_ServiceRequestLine _rline ) throws Exception
	{
		String sql = "delete from c_ordershipcalendar where isactive = 'N' and isshipped = 'N' and C_OrderLine_ID = "+_rline.get_ValueAsInt("C_OrderLine_ID");
		DB.executeUpdate(sql, _rline.get_TrxName());
	}
	
	public void changeLocation(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline, boolean _istemp) throws Exception
	{
		//desactivamos las lineas suspendidas y las agregamos a detalle
		String sqlZoom = " SELECT C_OrderShipCalendar_ID "+
		" FROM C_OrderShipCalendar WHERE IsActive = 'Y' " +
		" AND C_OrderLine_ID = "+_rline.get_ValueAsInt("C_OrderLine_ID")+
		" AND dateTrx >= ? ";

		if( _istemp )
		{
			Date sdate = _rline.getDatePromised2Ref();
			Date edate = _rline.getDatePromised3Ref();
			if( sdate == null || edate == null )
				throw new AdempiereUserError("Cambio dirección temporal requiere que especifique fechas de inicio y término");
			if( DateUtils.getDifferenceDays(sdate, edate) > 60 )
				throw new AdempiereUserError("Cambio dirección temporal no puede ser por un período superior a 60 días");
			sqlZoom += " AND DateTrx <= ?";
		}
		PreparedStatement pstmtZoom = DB.prepareStatement (sqlZoom, _rline.get_TrxName());		
		try
		{
			pstmtZoom.setTimestamp(1,_rline.getDatePromised2Ref());
			if (_istemp )
				pstmtZoom.setTimestamp(2,_rline.getDatePromised3Ref());
			
			ResultSet rsZoom = pstmtZoom.executeQuery ();
			
			while (rsZoom.next())
			{
				X_C_OrderShipCalendar oLineShip = new X_C_OrderShipCalendar(_rline.getCtx(),rsZoom.getInt("C_OrderShipCalendar_ID"), _rline.get_TrxName());
				oLineShip.set_CustomColumn("R_ServiceRequest_ID",_rline.getR_ServiceRequest_ID());
				oLineShip.setIsActive(false);								
				oLineShip.save();
				X_R_ServiceRLineAction rLAction = new X_R_ServiceRLineAction(_rline.getCtx(), 0, _rline.get_TrxName());
				rLAction.setR_ServiceRequest_ID(_rline.getR_ServiceRequest_ID());
				rLAction.setR_ServiceRequestLine_ID(_rline.get_ID());
				rLAction.setAD_Org_ID(_rline.getAD_Org_ID());
				rLAction.setTypeAcction(0);
				rLAction.setC_OrderShipCalendar_ID(oLineShip.get_ID());
				rLAction.save();
			}
		}catch (Exception e)
		{
			log.log(Level.SEVERE, sqlZoom, e);
		}
		//creamos las lineas nuevas de despacho
		String sqlNLine = "SELECT *,(SELECT AD_Org_ID FROM C_Order WHERE C_Order_ID = " + _req.getC_Order_ID()+" ) as AD_Org_ID " +
		" FROM RVOFB_OrderShipFull co WHERE C_Order_ID = " + _req.getC_Order_ID()+
		" AND C_OrderLine_ID = "+_rline.get_ValueAsInt("C_OrderLine_ID")+
		" AND DateTrx >= ?";
		
		if (_istemp )
			sqlNLine += " AND DateTrx <= ?";
		
		//calculamos nuevas fechas de pauta						
		if(_rline.getDatePromised2Ref() != null)
		{
			try 
			{
				PreparedStatement pstmtNLine = DB.prepareStatement(sqlNLine, _req.get_TrxName());
				pstmtNLine.setTimestamp(1,_rline.getDatePromised2Ref());
				if( _istemp )
					pstmtNLine.setTimestamp(2,_rline.getDatePromised3Ref());
					
				ResultSet rsNLine = pstmtNLine.executeQuery();
				while (rsNLine.next()) 
				{
					X_C_OrderShipCalendar sCal = new X_C_OrderShipCalendar(_rline.getCtx(), 0, _rline.get_TrxName());
					sCal.setC_Order_ID(rsNLine.getInt("C_Order_ID"));
					sCal.setAD_Org_ID(rsNLine.getInt("AD_Org_ID"));
					sCal.setIsActive(true);
					sCal.setC_OrderLine_ID(rsNLine.getInt("C_OrderLine_ID"));
					sCal.setC_CalendarCOPESA_ID(rsNLine.getInt("C_CalendarCOPESA_ID"));
					sCal.setC_CalendarCOPESALine_ID(rsNLine.getInt("C_CalendarCOPESALine_ID"));
					sCal.setDateTrx(rsNLine.getTimestamp("DateTrx"));
					sCal.set_CustomColumn("M_Product_ID",rsNLine.getInt("M_Product_ID"));
					sCal.set_CustomColumn("R_ServiceRequest_ID",_rline.getR_ServiceRequest_ID());
					sCal.set_CustomColumn("C_BPartner_Location_ID ",_rline.getC_BPartner_LocationRef_ID());
					sCal.set_CustomColumn("R_Resolution_ID", _req.getR_Resolution_ID() );
					sCal.save();
				}						
			}
			catch (Exception e) 
			{
				log.config(e.toString());
			}
		}
		
		deleteUselessLines(_rline);
	}

}
