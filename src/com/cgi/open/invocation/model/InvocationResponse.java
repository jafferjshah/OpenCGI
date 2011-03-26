package com.cgi.open.invocation.model;

public class InvocationResponse {
	public InvocationResponse(InvocationRequest request) {
		this.request = request;
		this.setInvocationResult(Boolean.FALSE);
		this.setResponsePayload(null);
		this.setExceptionThrown(new IllegalStateException("Returning Without Service Invocation")); 
	}
	public InvocationRequest getRequest() {
		return request;
	}
	public Boolean getInvocationResult() {
		return invocationResult;
	}
	public void setInvocationResult(Boolean invocationResult) {
		this.invocationResult = invocationResult;
	}
	public Object getResponsePayload() {
		return responsePayload;
	}
	public void setResponsePayload(Object responsePayload) {
		this.responsePayload = responsePayload;
	}
	public Exception getExceptionThrown() {
		return exceptionThrown;
	}
	public void setExceptionThrown(Exception exceptionThrown) {
		this.exceptionThrown = exceptionThrown;
	}
	public Class getResponsePayloadType() {
		return this.responsePayloadType;
	}
	public void setResponsePayloadType(Class type) {
		this.responsePayloadType = type;
	}
	private InvocationRequest request;
	private Boolean invocationResult;
	private Object responsePayload;
	private Class responsePayloadType;
	private Exception exceptionThrown;
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("Invocation of (")
				.append(request)
				.append(") resulted in ")
				.append(getInvocationResult())
				.append(".");
		if(getInvocationResult().equals(Boolean.FALSE)) {
			strBuilder.append(" Exception: ")
					.append(getExceptionThrown());
		}
		if(getInvocationResult().equals(Boolean.TRUE)) {
			strBuilder.append(" Payload: ")
					.append(getResponsePayload())
					.append(" of type :")
					.append(getResponsePayloadType());
		}
		return strBuilder.toString();
	}
}
