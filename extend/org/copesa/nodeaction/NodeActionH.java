package org.copesa.nodeaction;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.compiere.model.MOrderLine;
import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;

public class NodeActionH implements INodeAction {
	
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception 
	{
		extendOrderline(_rline);
	}

	public void extendOrderline(X_R_ServiceRequestLine _rLine) throws Exception
	{
		Properties ctx = _rLine.getCtx();
		String trxName = _rLine.get_TrxName();
		
		MOrderLine oLine = new MOrderLine(ctx, _rLine.get_ValueAsInt("C_OrderLine_ID"), trxName);
		
		Timestamp newdate = _rLine.getDatePromised3New();
		if ( newdate == null )
			return;
		
		Timestamp olddate = (Timestamp)oLine.get_Value("DatePromised3");
		if( newdate.before(olddate) )
		{
			throw new AdempiereUserError("Nueva fecha de término debe ser mayor a fecha actual");
		}
	    
		String strdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newdate); 
		String sql = "UPDATE C_Orderline set DatePromised3 = to_date('" + strdate + "', 'YYYY-MM-DD HH24:MI:SS') " +
		             "WHERE C_Orderline_ID = " + oLine.getC_OrderLine_ID();
		              
		DB.executeUpdate(sql , _rLine.get_TrxName());
	}	
	
}
