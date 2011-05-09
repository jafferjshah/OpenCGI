package com.cgi.open.easyshare.servlets;

public enum REQUEST_PARAMETERS {
	
	SESSION_NAME("sessionName"),
	DESCRIPTION("description"),
	SESSION_ID("sessionId"),
	EMAIL("email"),
	SUBJECT("subject"),
	TEXT("text"),
	DATE("date"),
	POST_TIME("postTime"),
	POST_BY("postBy"),
	RESOURCE_ID("resourceId"),
	RESOURCE_NAME("resourceName"),
	RESOURCE_URL("resourceUrl"),
	APPOINTMENT_ID("appointmentId"),
	FROM_TIME("fromTime"),
	TO_TIME("toTime"),
	LOCATION("location"),
	
	USERTYPE("userType"),
	
	
	public String paramName;
	private REQUEST_PARAMETERS(String paramName){
		this.paramName=paramName;
	}
	
	public String toString(){
		return this.param;
	}

}
