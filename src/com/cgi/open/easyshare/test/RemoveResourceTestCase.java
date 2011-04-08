package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.ResourceNotFoundException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class RemoveResourceTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd;
	public RemoveResourceTestCase(String name){
		super(name);
	}
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd= new ServiceDef();
		sd.setServiceName("removeResource");
		Set<String> elig = new HashSet<String>();
		elig.add("FACILITATOR");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.TRUE);
	}
	
	public void testRemoveResource(){
		Integer sessionId=1;
		Integer resourceId=1;
		try {
			if (sp.isServReqValid(sd, "jaffer@cgi.com", sessionId)) {
			Boolean isRemoved=easy.removeResource(sessionId, resourceId);
			assertEquals(Boolean.TRUE,isRemoved);
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (ResourceNotFoundException e) {
			fail("ResourceNotFoundException : Should not occur");
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testRemoveResourceForInvalidSession(){
		Integer sessionId=15;
		Integer resourceId=3;
		try {
			if (sp.isServReqValid(sd, "jaffer@cgi.com", sessionId)) {
			Boolean isRemoved=easy.removeResource(sessionId, resourceId);
			fail("should raise SessionNotFoundException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			assertTrue("success",true);
		} catch (ResourceNotFoundException e) {
			fail("ResourceNotFoundException : Should not occur");
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testRemoveResourceNotFound(){
		Integer sessionId=1;
		Integer resourceId=1;
		try {
			if (sp.isServReqValid(sd, "jaffer@cgi.com", sessionId)) {
			Boolean isRemoved=easy.removeResource(sessionId, resourceId);
			fail("should raise ResourceNotFoundException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (ResourceNotFoundException e) {
			assertTrue("success",true);
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}

}
