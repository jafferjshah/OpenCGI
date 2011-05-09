package com.cgi.open.easyshare.web;

import java.util.Set;

public class ParamValue {
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public Set<String> getValues() {
		return values;
	}
	public void setValues(Set<String> values) {
		this.values = values;
	}
	private String parameter;
	private Set<String> values;
}
