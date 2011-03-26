package com.cgi.open.invocation;

import com.cgi.open.invocation.model.InvocationRequest;
import com.cgi.open.invocation.model.InvocationResponse;

public interface InvocationService {
	/**
	 * Gets an XML representation of an Invocation Definition instance 
	 * (com.cgi.open.invocation.model.InvocationDef), invokes the service defined and returns
	 * the result as an XML. This method is tightly bound with the model, and any change in the
	 * model should be reflected here.
	 * @param inputXML
	 * @return
	 */
	public InvocationResponse invokeService(InvocationRequest request);
}
