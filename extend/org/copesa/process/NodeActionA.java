package org.copesa.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MOrderLine;
import org.compiere.model.X_C_OrderShipCalendar;
import org.compiere.model.X_R_ServiceRLineAction;
import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

public class NodeActionA implements INodeAction {
	private CLogger			log = CLogger.getCLogger (getClass());
	
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception {
		//activamos las lineas suspendidas y las agregamos a detale como baja
		String sqlZoom = " SELECT C_OrderShipCalendar_ID "+
		" FROM C_OrderShipCalendar WHERE IsActive = 'N' " +
		" AND C_OrderLine_ID = "+_rline.get_ValueAsInt("C_OrderLine_ID")+
		" AND dateTrx >= ? AND dateTrx <= ? AND R_Request_ID > 0";
	
		PreparedStatement pstmtZoom = DB.prepareStatement (sqlZoom, _rline.get_TrxName());		
		try
		{
			pstmtZoom.setTimestamp(1,_rline.getDatePromised2Ref());
			pstmtZoom.setTimestamp(2,_rline.getDatePromised3Ref());
			ResultSet rsZoom = pstmtZoom.executeQuery ();
			while (rsZoom.next())
			{
				X_C_OrderShipCalendar oLineShip = new X_C_OrderShipCalendar(_rline.getCtx(),rsZoom.getInt("C_OrderShipCalendar_ID"), _rline.get_TrxName());
				//oLineShip.set_CustomColumn("R_Request_ID",rLine.getR_Request_ID());
				oLineShip.set_CustomColumn("R_ServiceRequest_ID",_rline.getR_ServiceRequest_ID());
				oLineShip.setIsActive(true);								
				oLineShip.save();
				X_R_ServiceRLineAction rLAction = new X_R_ServiceRLineAction(_rline.getCtx(), 0, _rline.get_TrxName());
				rLAction.setR_ServiceRequest_ID(_rline.getR_ServiceRequest_ID());
				rLAction.setR_ServiceRequestLine_ID(_rline.get_ID());
				rLAction.setAD_Org_ID(_rline.getAD_Org_ID());
				rLAction.setTypeAcction(1);
				rLAction.setC_OrderShipCalendar_ID(oLineShip.get_ID());
				rLAction.save();
			}
		}catch (Exception e)
		{
			log.log(Level.SEVERE, sqlZoom, e);
		}
		//calculamos nuevas fechas 
		MOrderLine oLine = new MOrderLine(_rline.getCtx(), _rline.get_ValueAsInt("C_OrderLine_ID"), _rline.get_TrxName());
		Timestamp DatePromise2New = (Timestamp)oLine.get_Value("DatePromised3");
		long timeDif = _rline.getDatePromised3Ref().getTime() - _rline.getDatePromised2Ref().getTime();
		Timestamp DatePromise3New = new Timestamp(DatePromise2New.getTime()-timeDif);
		
		//se desactivan lineas de solicitud antigua						
		String sqlUp = " UPDATE C_OrderShipCalendar SET IsActive = 'N' " +
		" WHERE C_OrderShipCalendar_ID IN (SELECT C_OrderShipCalendar_ID "+
		" FROM C_OrderShipCalendar WHERE IsActive = 'Y' " +
		" AND C_OrderLine_ID = "+_rline.get_ValueAsInt("C_OrderLine_ID")+
		" AND dateTrx > ? )";
	
		PreparedStatement pstmtUp = DB.prepareStatement (sqlUp, _rline.get_TrxName());		
		try
		{
			pstmtUp.setTimestamp(1,DatePromise3New);
			//pstmtUp.setTimestamp(2,rLine.getDatePromised3Ref());
			pstmtUp.executeUpdate();
		}catch (Exception e)
		{
			log.log(Level.SEVERE, sqlZoom, e);
		}
							
		//actualizamos fecha fin orden con update directo para que no modifique precios ni nada
		//extencion de contrato
		if(DatePromise3New != null)
		{
			String sqlUpdate = "UPDATE C_OrderLine SET DatePromised3 = ? WHERE C_OrderLine_ID = "+_rline.get_ValueAsInt("C_OrderLine_ID");
			PreparedStatement pstmtUpdate = DB.prepareStatement(sqlUpdate,_rline.get_TrxName());
			pstmtUpdate.setTimestamp(1,DatePromise3New);
			pstmtUpdate.executeUpdate();
		}
		
	}

}
