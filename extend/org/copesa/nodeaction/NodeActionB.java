package org.copesa.nodeaction;

import java.util.logging.Level;

import org.compiere.model.MOrder;
import org.compiere.model.PO;
import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;
import org.compiere.util.CLogger;
import org.copesa.model.*;


/*
 * Acción que genera líneas de SR para dividir una LTFULL en LTLV + LTSD
 */
public class NodeActionB implements INodeAction {
	private CLogger			log = CLogger.getCLogger (getClass());
    //private static final int LTLV = 2000011;
    //private static final int LTSD = 2000012;
    
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception {
		try
		{
				MOrder order = new MOrder(_req.getCtx(), _req.getC_Order_ID(), _req.get_TrxName());
				PO po = (PO) order;
				int ID_LineProdFull = _rline.getC_OrderLine_ID();
				COPESAOrderOps.divideLTFull(po, order, ID_LineProdFull);
		} catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
		}
    }
}