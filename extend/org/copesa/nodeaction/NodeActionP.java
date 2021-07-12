package org.copesa.nodeaction;

import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;

public class NodeActionP implements INodeAction {
	
	@Override
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception 
	{
		NodeActionT aux = new NodeActionT();
		aux.changeLocationPermanent(_req, _rline);
	}

}
