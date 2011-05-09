package com.cgi.open.easyshare.web;

import java.util.Set;

public class ServiceResponse {
	public ServicePath getService() {
		return service;
	}
	public void setService(ServicePath service) {
		this.service = service;
	}
	public Set<ParamValue> getRequest() {
		return request;
	}
	public void setRequest(Set<ParamValue> request) {
		this.request = request;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Set<ServicePath> getNext() {
		return next;
	}
	public void setNext(Set<ServicePath> next) {
		this.next = next;
	}
	private ServicePath service;
	private Set<ParamValue> request;
	private String code;
	private String message;
	private Object data;
	private Set<ServicePath> next;
}
