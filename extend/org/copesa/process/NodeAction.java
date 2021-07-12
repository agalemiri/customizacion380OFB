package org.copesa.process;

import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;

public class NodeAction {
	private INodeAction nodeaction;
	
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline, String _action) throws Exception
	{
		Class<?> clazz = Class.forName("org.copesa.process.NodeAction" + _action.trim() );
		nodeaction = (INodeAction) clazz.newInstance();
		nodeaction.execute(_req, _rline);
	}
}

