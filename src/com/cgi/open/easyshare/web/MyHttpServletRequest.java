package com.cgi.open.easyshare.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class MyHttpServletRequest extends HttpServletRequestWrapper{
	
	public MyHttpServletRequest(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}


	public String getParameter(REQUEST_PARAMETERS param){
		String paramValue=null;
		paramValue=super.getParameter(param.toString());
		return paramValue;
	}
	
	
	public Enumeration getParameterNames(){
		List paramNames=new ArrayList();
		for(Enumeration e = super.getParameterNames();
		 e.hasMoreElements(); ){
			paramNames.add(REQUEST_PARAMETERS.get((String)e.nextElement()));
		}
		return Collections.enumeration(paramNames);
	}
	
	public Map<REQUEST_PARAMETERS,String[]>getParameterMap(){
		Map<String,String[]>tempMap=super.getParameterMap();
		Map<REQUEST_PARAMETERS,String[]>paramMap=new HashMap<REQUEST_PARAMETERS,String[]>();
		Set<String>keys=tempMap.keySet();
		for(String key:keys){
			paramMap.put(REQUEST_PARAMETERS.get(key), tempMap.get(key));
		}
		return paramMap;
	}
	
	public String[] getParameterValues(REQUEST_PARAMETERS paramName){
		Map<REQUEST_PARAMETERS,String[]>paramMap=getParameterMap();
		return paramMap.get(paramName);
	}

}
