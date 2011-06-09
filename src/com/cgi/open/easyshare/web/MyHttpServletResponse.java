package com.cgi.open.easyshare.web;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class MyHttpServletResponse extends HttpServletResponseWrapper{

	public MyHttpServletResponse(HttpServletResponse response) {
		super(response);
		// TODO Auto-generated constructor stub
	}
	ServiceResponse serviceResponse;
	String validAccess;
	public ServiceResponse getServiceResponse() {
		return serviceResponse;
	}
	public void setServiceResponse(ServiceResponse serviceResponse) {
		this.serviceResponse = serviceResponse;
	}
	public String getValidAccess() {
		return validAccess;
	}
	public void setValidAccess(String validAccess) {
		this.validAccess = validAccess;
	}

}
