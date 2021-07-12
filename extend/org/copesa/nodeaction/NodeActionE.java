package org.copesa.nodeaction;

import java.sql.PreparedStatement;
import java.util.logging.Level;

import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

public class NodeActionE implements INodeAction {
	private CLogger			log = CLogger.getCLogger (getClass());
	
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception 
	{
		//desactivamos la linea de la orden
		DB.executeUpdate("UPDATE C_OrderLine SET IsActive = 'N' WHERE C_OrderLine_ID = " + _rline.get_ValueAsInt("C_OrderLine_ID"), _rline.get_TrxName());
		try
		{
			//borrado de cuotas de facturacion despues de la fecha
			String sqlPay = " DELETE FROM C_OrderPayCalendar WHERE C_Order_ID = " + _rline.getR_ServiceRequest().getC_Order_ID() +	" AND DateEnd >= ?";
			PreparedStatement pstmtPay = DB.prepareStatement (sqlPay, _rline.get_TrxName());
			pstmtPay.setTimestamp(1, _rline.getDatePromised2Ref());							
			pstmtPay.executeUpdate();
			//borrado de pauta despues de la fecha
			String sqlShip = " DELETE FROM C_OrderShipCalendar WHERE C_Order_ID = " + _rline.getR_ServiceRequest().getC_Order_ID() + " AND DateTrx >= ?";
			PreparedStatement pstmtShip = DB.prepareStatement (sqlShip, _rline.get_TrxName());
			pstmtShip.setTimestamp(1,_rline.getDatePromised2Ref());							
			pstmtShip.executeUpdate();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE,  e.getMessage());
		}
		
	}

}
