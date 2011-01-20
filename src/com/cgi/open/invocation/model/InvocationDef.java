package com.cgi.open.invocation.model;

import java.util.HashMap;
import java.util.Map;

public class InvocationDef {
	private String invokersEmail;

	public String getInvokersEmail() {
		return invokersEmail;
	}

	public void setInvokersEmail(String invokersEmail) {
		this.invokersEmail = invokersEmail;
	}

	private String serviceName;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	private Map<String, String[]> params = new HashMap<String, String[]>();

	public Map<String, String[]> getParams() {
		return params;
	}

	public void setParams(Map<String, String[]> params) {
		this.params = params;
	}
	
	public void addParam(String key, String[] value) {
		params.put(key, value);
	}
}
