package com.cgi.open.invocation.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.invocation.InvocationService;
import com.cgi.open.invocation.model.InvocationRequest;
import com.cgi.open.invocation.model.InvocationResponse;

public class InvocationServiceJavaProxy implements InvocationService {
	public InvocationResponse invokeService(InvocationRequest request) {
		InvocationResponse response = new InvocationResponse(request);
		EasyShareServices easyShare = ServicesMapper.getEasyShareServicesProxyInstance();
		Class easyShareClass = easyShare.getClass();
		Method method2BInvoked = null;
		for(Method method : easyShareClass.getMethods()) {
			if(method.getName().equals(request.getServiceName())) {
				System.out.println("Method found");
				method2BInvoked = method;
				break;
			}
		}
		if(method2BInvoked == null) {
			response.setExceptionThrown(new NoSuchMethodException());
		}
		else {
			Object[] pmArray = new Object[1];
			pmArray[0] = "Jaffer";
			try {
				response.setResponsePayloadType(method2BInvoked.getReturnType());
				System.out.println(method2BInvoked.getParameterTypes());
				Object result = method2BInvoked.invoke(easyShare, pmArray);
				response.setInvocationResult(Boolean.TRUE);
				response.setResponsePayload(result);
			} catch (IllegalArgumentException e) {
				response.setInvocationResult(Boolean.FALSE);
				response.setExceptionThrown(e);
			} catch (IllegalAccessException e) {
				response.setInvocationResult(Boolean.FALSE);
				response.setExceptionThrown(e);
			} catch (InvocationTargetException e) {
				response.setInvocationResult(Boolean.FALSE);
				response.setExceptionThrown(e);
			}
		}
		return response;
	}
	public static void main(String[] args) {
		InvocationRequest def = new InvocationRequest();
		def.setInvokersEmail("jaffer.shah");
		def.setServiceName("createSession");
		def.addValue("sessionName", "JAVA BASICS");
		def.addValue("attendee", "sanjana.bayya");
		def.addValue("attendee", "safiya.fathima");
		System.out.println(def);
		System.out.println(new InvocationServiceJavaProxy().invokeService(def));
	}
}
