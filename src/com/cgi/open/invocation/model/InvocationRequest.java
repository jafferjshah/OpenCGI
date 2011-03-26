package com.cgi.open.invocation.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InvocationRequest {
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

	private Map<String, Set<String>> params = new HashMap<String, Set<String>>();

	public Map<String, Set<String>> getParams() {
		return params;
	}

	public void setParams(Map<String, Set<String>> params) {
		this.params = params;
	}
	public void addValue(String key, String value) {
		Set<String> values = params.get(key);
		if(values == null) {
			values = new HashSet<String>();
		}
		values.add(value);
		params.put(key, values);
	}
	public void addValues(String key, Set<String> values) {
		Set<String> existingValues = params.get(key);
		if(existingValues == null) {
			existingValues = new HashSet<String>();
		}
		existingValues.addAll(values);
		params.put(key, existingValues);
	}
	public void clearValues(String key) {
		Set<String> existingValues = params.get(key);
		if(existingValues != null) {
			existingValues.clear();
		}
		else {
			existingValues = new HashSet<String>();
		}
		params.put(key, existingValues);
	}
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("Service: ")
				.append(this.getServiceName())
				.append(", invoked by: ")
				.append(this.getInvokersEmail())
				.append(" using ")
				.append(this.getParams());
		return strBuilder.toString();
	}
}
