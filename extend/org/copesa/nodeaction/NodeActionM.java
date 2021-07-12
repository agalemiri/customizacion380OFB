package org.copesa.nodeaction;

import org.compiere.model.MOrder;
import org.compiere.model.X_R_ServiceRequest;
import org.copesa.model.COPESAOrderOps;
import org.compiere.model.X_R_ServiceRequestLine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;

/* 
 * ACCION DE SR: CAMBIO DE CONTRATANTE
 */
public class NodeActionM implements INodeAction {
	
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception 
	{
		changeCBPartner(_req);
	}

	public void changeCBPartner(X_R_ServiceRequest _req) throws Exception
	{
		int c_order_id = _req.getC_Order_ID();
		int c_bpartnernew_id = _req.get_ValueAsInt("SR_BPartnerNew_ID");
		int c_bpartner_location_id = _req.getSR_BPartner_Loaction_ID();

		if ( c_bpartnernew_id <= 0 )
			throw new AdempiereUserError("Debe seleccionar al nuevo socio de negocio (contratante)");
		if ( c_bpartner_location_id <= 0 )
			throw new AdempiereUserError("Debe seleccionar direccion de socio de negocio (contratante)");
		
		String sql = "UPDATE C_Order set C_BPartner_ID =  " + c_bpartnernew_id + ", c_bpartner_location_id = " + c_bpartner_location_id +
		             " WHERE C_Order_ID = " + c_order_id;
		              
		DB.executeUpdate(sql , _req.get_TrxName());
	}	
	
}
