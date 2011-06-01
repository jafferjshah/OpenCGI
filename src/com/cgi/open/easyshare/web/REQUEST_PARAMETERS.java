package com.cgi.open.easyshare.web;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum REQUEST_PARAMETERS {
	
	SESSION_NAME("sessionName"),
	DESCRIPTION("description"),
	SESSION_ID("sessionId"),
	EMAIL("email"),
	SUBJECT("subject"),
	TEXT("text"),
	DATE("date"),
	POST_TIME("postTime"),
	POST_BY("postedBy"),
	RESOURCE_ID("resourceId"),
	RESOURCE_NAME("resourceName"),
	RESOURCE_URL("resourceUrl"),
	APPOINTMENT_ID("appointmentId"),
	FROM_TIME("fromTime"),
	TO_TIME("toTime"),
	LOCATION("location"),
	
	USERTYPE("userType");
	
	private static final Map<String,REQUEST_PARAMETERS> lookup 
    = new HashMap<String,REQUEST_PARAMETERS>();
	static {
	    for(REQUEST_PARAMETERS p : EnumSet.allOf(REQUEST_PARAMETERS.class))
	         lookup.put(p.getParamName(), p);
		}
	public String paramName;
	private REQUEST_PARAMETERS(String paramName){
		this.paramName=paramName;
	}
	
	public String toString(){
		return this.paramName;
	}
	
	public String getParamName(){
		return this.paramName;
	}
	
	public static REQUEST_PARAMETERS get(String paramName) { 
	          return lookup.get(paramName); 
	     }
	}


