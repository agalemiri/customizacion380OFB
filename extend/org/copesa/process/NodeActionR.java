package org.copesa.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.model.X_C_OrderShipCalendar;
import org.compiere.model.X_R_ServiceRLineAction;
import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

public class NodeActionR implements INodeAction {
	private CLogger			log = CLogger.getCLogger (getClass());
	
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception 
	{
		//desactivamos las lineas suspendidas y las agregamos a detale
		String sqlZoom = " SELECT C_OrderShipCalendar_ID "+
		" FROM C_OrderShipCalendar WHERE IsActive = 'Y' " +
		" AND C_OrderLine_ID = " + _rline.get_ValueAsInt("C_OrderLine_ID")+
		" AND dateTrx = ? ";
	
		PreparedStatement pstmtZoom = DB.prepareStatement (sqlZoom, _rline.get_TrxName());		
		try
		{
			pstmtZoom.setTimestamp(1,_rline.getDatePromised2Ref());
			ResultSet rsZoom = pstmtZoom.executeQuery ();
			while (rsZoom.next())
			{
				X_C_OrderShipCalendar oLineShip = new X_C_OrderShipCalendar(_rline.getCtx(),rsZoom.getInt("C_OrderShipCalendar_ID"), _rline.get_TrxName());
				//oLineShip.set_CustomColumn("R_Request_ID",rLine.getR_Request_ID());
				oLineShip.set_CustomColumn("R_ServiceRequest_ID",_rline.getR_ServiceRequest_ID());
				oLineShip.setIsActive(false);								
				oLineShip.save();
				X_R_ServiceRLineAction rLAction = new X_R_ServiceRLineAction(_rline.getCtx(), 0, _rline.get_TrxName());
				rLAction.setR_ServiceRequest_ID(_rline.getR_ServiceRequest_ID());
				rLAction.setR_ServiceRequestLine_ID(_rline.get_ID());
				rLAction.setAD_Org_ID(_rline.getAD_Org_ID());
				rLAction.setTypeAcction(3);
				rLAction.setC_OrderShipCalendar_ID(oLineShip.get_ID());
				rLAction.save();
			}
		}catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
		}
		//creamos las lineas nuevas de despacho
		String sqlNLine = "SELECT *,(SELECT AD_Org_ID FROM C_Order WHERE C_Order_ID = " + _req.getC_Order_ID()+" ) as AD_Org_ID " +
		" FROM RVOFB_OrderShipFull co WHERE C_Order_ID = " + _req.getC_Order_ID()+
		" AND C_OrderLine_ID = "+_rline.get_ValueAsInt("C_OrderLine_ID")+
		" AND DateTrx = ? ";
		//calculamos nuevas fechas de pauta						
		try 
		{
			PreparedStatement pstmtNLine = DB.prepareStatement(sqlNLine, _rline.get_TrxName());
			pstmtNLine.setTimestamp(1,_rline.getDatePromised2Ref());
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
				sCal.save();
			}						
		}
		catch (Exception e) 
		{
			log.config(e.toString());
		}
		

	}

}
