package org.copesa.nodeaction;

import org.compiere.model.MOrder;
import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.copesa.model.COPESAOrderOps;

/* 
 * ACCION DE SR: CAMBIO DE ATENCION
 */
public class NodeActionN implements INodeAction {
	
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception 
	{
		changeCBPartnerRef(_req, _rline);
	}

	public void changeCBPartnerRef(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rLine) throws Exception
	{
		int c_order_id = _req.getC_Order_ID();
		int c_orderline_id = _rLine.getC_OrderLine_ID();
		int c_bpartnerref_id = _rLine.getC_BPartnerRef_ID();
		int c_bpartner_location_id = _rLine.getC_BPartner_Location_ID();

		if ( c_order_id <= 0 )
			throw new AdempiereUserError("Debe seleccionar contrato para ejecutar solicitud de servicio");
		if ( c_bpartnerref_id <= 0 )
			throw new AdempiereUserError("Debe seleccionar al socio de entrega (suscriptor)");
		if ( c_bpartner_location_id <= 0 )
			throw new AdempiereUserError("Debe seleccionar la dirección de despacho");
		
		String sql = "UPDATE C_Orderline set C_BPartnerRef_ID =  " + c_bpartnerref_id + ", c_bpartner_location_id = " + c_bpartner_location_id +
		             " WHERE C_Orderline_ID = " + c_orderline_id;
		              
		DB.executeUpdate(sql , _rLine.get_TrxName());
		COPESAOrderOps.create_osc(new MOrder(_req.getCtx(), c_order_id, _req.get_TrxName()));
	}	
	
}
