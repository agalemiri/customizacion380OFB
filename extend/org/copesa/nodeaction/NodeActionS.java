package org.copesa.nodeaction;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;
import org.compiere.util.DB;
import org.compiere.util.Env;
/*
 * Acción que divide una linea con LTFULL en dos con LV y SD.
 * 
 */
public class NodeActionS implements INodeAction {
	
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception 
	{
		int oline = _rline.getC_OrderLine_ID();
		int userid = Env.getAD_User_ID(_rline.getCtx());
		String sql = "select copesa_splitLTFULL(" + oline + ", " + userid + ")";
		int result = DB.getSQLValue(_rline.get_TrxName(), sql);
		
		if ( result < 0 )
			throw new AdempiereException("No se pudo dividir línea");
	}

}
