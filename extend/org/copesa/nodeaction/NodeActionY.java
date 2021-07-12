package org.copesa.nodeaction;

import org.compiere.model.MOrder;
import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;

public class NodeActionY implements INodeAction {
	
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception 
	{
		int orderid = _req.getC_Order_ID();
		if ( orderid <= 0 )
			throw new AdempiereUserError("Debe seleccionar contrato");
		
		MOrder order = new MOrder(_req.getCtx(), orderid , _req.get_TrxName());
		
        int ccold = order.get_ValueAsInt("C_BP_BankAccount_ID");
		int ccnew = _req.get_ValueAsInt("SR_BankAccount_ID");
		
		if (ccnew <= 0 )
			throw new AdempiereUserError("Debe seleccionar la tarjeta o cuenta del cliente");
		
		if (ccnew == ccold )
			throw new AdempiereUserError("Debe seleccionar una tarjeta o cuenta distinta a la original del contrato");
		
		DB.executeUpdate("UPDATE C_Order set C_BP_BankAccount_ID = " + ccnew + " where C_Order_ID = " + orderid, _req.get_TrxName());
		
	}

}
