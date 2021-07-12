package org.copesa.process;

import org.compiere.model.X_R_ServiceRequest;
import org.compiere.model.X_R_ServiceRequestLine;

public interface INodeAction 
{
	public void execute(X_R_ServiceRequest _req, X_R_ServiceRequestLine _rline) throws Exception;
}
