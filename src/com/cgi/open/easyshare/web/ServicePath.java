package com.cgi.open.easyshare.web;

public class ServicePath {
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ServicePath(String service, String url) {
		this.service = service;
		this.url = url;
	}
	private String service;
	private String url;
}
