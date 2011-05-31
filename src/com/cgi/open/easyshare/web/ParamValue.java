package com.cgi.open.easyshare.web;

import java.util.List;
import java.util.Set;

public class ParamValue {
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	private String parameter;
	private List<String> values;
}
