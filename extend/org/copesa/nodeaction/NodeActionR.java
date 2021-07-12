package org.copesa.nodeaction;

import java.sql.PreparedStatement;
import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
/* 
 * ACCION DE SR: REPOSICION
 */
public class NodeActionR implements INodeAction {
	
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception 
	{
		checkLines(_req, _rline);
        String sql = "select copesa_dump_reposicion(?, ?)";
        		
	    PreparedStatement pstmt = DB.prepareStatement(sql, _req.get_TrxName());
	    pstmt.setInt(1, _req.getR_ServiceRequest_ID());
	    pstmt.setInt(2, _rline.getR_ServiceRequestLine_ID());
	    pstmt.execute();
	    pstmt.close();
	}
	
	private void checkLines(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception 
	{
		String sql = "select count(*) from r_servicerequest req " +
                     "join r_servicerequestline rline on req.r_servicerequest_id = rline.r_servicerequest_id " +
                     "left join c_ordershipcalendar osc on rline.c_orderline_id = osc.c_orderline_id " +
                     "                                     and date_trunc('day', osc.datetrx) = date_trunc('day', req.SR_date) " +
                     "left join c_orderline col on rline.c_orderline_id = col.c_orderline_id and col.datepromised3 >= date_trunc('day', req.SR_date) " +
                     "where req.r_servicerequest_id = " + _req.getR_ServiceRequest_ID() +
                     "  and (osc.c_ordershipcalendar_id is null or col.c_orderline_id is null)";

		int cuenta = DB.getSQLValue(_req.get_TrxName(), sql);
		
		if( cuenta > 0 )
			throw new AdempiereUserError("Existen " + cuenta + " líneas en el SR que no tienen reparto para el día solicitado. Corrija y reintente.");
	}

}
