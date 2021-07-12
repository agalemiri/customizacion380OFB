package org.copesa.model;

import org.compiere.model.X_R_ServiceRequest;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class COPESASROps {
	public static void assignUser(X_R_ServiceRequest sReq, int nodeid) throws AdempiereUserError {
		int userid = DB.getSQLValue(sReq.get_TrxName(), 
		        "select copesa_sr_getassignment(?, ?, ?)", 
		        Env.getAD_User_ID(sReq.getCtx()), Env.getAD_Role_ID(sReq.getCtx()), nodeid);
		
		/*if( userid <= 0 )
			throw new AdempiereUserError("No se pudo obtener usuario a asignar, verifique los accesos definidos para la acción elegida");*/

		sReq.setAD_User_ID(userid);
	}
}
