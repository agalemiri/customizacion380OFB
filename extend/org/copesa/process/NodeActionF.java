package org.copesa.process;

import java.util.Properties;

import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MOrderLine;
import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;

public class NodeActionF implements INodeAction {
	
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception 
	{
		updateZone(_rline);
	}

	public void updateZone(X_R_ServiceRequestLine _rLine) throws Exception
	{
		Properties ctx = _rLine.getCtx();
		String trxName = _rLine.get_TrxName();
		
		MOrderLine oLine = new MOrderLine(ctx, _rLine.get_ValueAsInt("C_OrderLine_ID"), trxName);
		MBPartnerLocation bparL = new MBPartnerLocation(ctx,oLine.getC_BPartner_Location_ID(),trxName);
		if( _rLine.get_ValueAsString("Zone") != null && _rLine.get_ValueAsString("Zone").trim().length() > 0)
			bparL.set_CustomColumn("Zone", _rLine.get_ValueAsString("Zone"));
		if( _rLine.get_ValueAsInt("Sector") > 0 )
			bparL.set_CustomColumn("Sector", _rLine.get_ValueAsInt("Sector"));
		bparL.save();
	}	
	
}
