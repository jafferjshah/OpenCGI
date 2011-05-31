package com.cgi.open.easyshare.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class ServiceResponse {
	public ServiceResponse(){
		this.request=new HashMap<REQUEST_PARAMETERS,String[]>();
			}
	public void initServiceResponse(MyHttpServletRequest request,String servicePath){
		this.service = new ServicePath(servicePath,request.getServletPath());
		this.request=request.getParameterMap();
	}
		
	public ServicePath getService() {
		return service;
	}
	public void setService(ServicePath service) {
		this.service = service;
	}
	
	public Map<REQUEST_PARAMETERS, String[]> getRequest() {
		return request;
	}
	public void setRequest(Map<REQUEST_PARAMETERS, String[]> request) {
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
	private Map<REQUEST_PARAMETERS,String[]> request;
	private String code;
	private String message;
	private Object data;
	private Set<ServicePath> next;
}
