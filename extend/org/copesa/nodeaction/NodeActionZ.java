package org.copesa.nodeaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrderLine;
import org.compiere.model.X_C_OrderShipCalendar;
import org.compiere.model.X_R_ServiceRLineAction;
import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

public class NodeActionZ implements INodeAction {
	private CLogger			log = CLogger.getCLogger (getClass());
	
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception 
	{
		//desactivamos las lineas suspendidas y las agregamos a detale
		String sqlZoom = " SELECT C_OrderShipCalendar_ID "+
		" FROM C_OrderShipCalendar WHERE IsActive = 'Y' " +
		" AND C_OrderLine_ID = " + _rline.get_ValueAsInt("C_OrderLine_ID")+
		" AND dateTrx >= ? AND dateTrx <= ? ";
	
		PreparedStatement pstmtZoom = DB.prepareStatement (sqlZoom, _req.get_TrxName());		
		try
		{
			if( _rline.getDatePromised2Ref() == null || _rline.getDatePromised3Ref() == null )
				throw new AdempiereUserError("La acción de suspensión requiere que indique una fecha de inicio y término");
			
			pstmtZoom.setTimestamp(1,_rline.getDatePromised2Ref());
			pstmtZoom.setTimestamp(2,_rline.getDatePromised3Ref());
			ResultSet rsZoom = pstmtZoom.executeQuery ();
			while (rsZoom.next())
			{
				X_C_OrderShipCalendar oLineShip = new X_C_OrderShipCalendar(_rline.getCtx(),rsZoom.getInt("C_OrderShipCalendar_ID"), _rline.get_TrxName());
				//oLineShip.set_CustomColumn("R_Request_ID",rLine.getR_Request_ID());
				oLineShip.set_CustomColumn("R_ServiceRequest_ID",_rline.getR_ServiceRequest_ID());
				oLineShip.set_CustomColumn("R_Resolution_ID", _req.getR_Resolution_ID() );
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
		String sqlNLine = "SELECT *,(SELECT AD_Org_ID FROM C_Order WHERE C_Order_ID = "+_req.getC_Order_ID()+" )as AD_Org_ID " +
		" FROM RVOFB_OrderShipFull co WHERE C_Order_ID = " + _req.getC_Order_ID()+
		" AND C_OrderLine_ID = " + _rline.get_ValueAsInt("C_OrderLine_ID")+
		" AND DateTrx >= ? AND DateTrx <= ?";
		//calculamos nuevas fechas de pauta
		MOrderLine oLine = new MOrderLine(_rline.getCtx(), _rline.getC_OrderLine_ID(), _rline.get_TrxName());
		Timestamp DatePromise2New = (Timestamp)oLine.get_Value("DatePromised3");
		long timeDif = _rline.getDatePromised3Ref().getTime() - _rline.getDatePromised2Ref().getTime();
		Timestamp DatePromise3New = new Timestamp(DatePromise2New.getTime()+timeDif);
		
		if(DatePromise2New != null && DatePromise3New != null)
		{
			try 
			{
				PreparedStatement pstmtNLine = DB.prepareStatement( sqlNLine, _rline.get_TrxName());
				pstmtNLine.setTimestamp(1,DatePromise2New);
				pstmtNLine.setTimestamp(2,DatePromise3New);
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
					sCal.set_CustomColumn("R_ServiceRequest_ID", _rline.getR_ServiceRequest_ID());
					sCal.save();
				}						
			}
			catch (Exception e) 
			{
				log.config(e.toString());
			}
		}
		//actualizamos fecha fin orden con update directo para que no modifique precios ni nada
		if(DatePromise3New != null)
		{
			String sqlUpdate = "UPDATE C_OrderLine SET DatePromised3 = ? WHERE C_OrderLine_ID = " + _rline.getC_OrderLine_ID();
			PreparedStatement pstmtUpdate = DB.prepareStatement(sqlUpdate,_rline.get_TrxName());
			pstmtUpdate.setTimestamp(1,DatePromise3New);
			pstmtUpdate.executeUpdate();
		}

	}

}
