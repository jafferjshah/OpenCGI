package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.easyshare.model.Message;
import com.cgi.open.easyshare.model.Resource;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class GetResourcesTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd;
	public GetResourcesTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();	
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd= new ServiceDef();
		sd.setServiceName("getResources");
		Set<String> elig = new HashSet<String>();
		elig.add("FACILITATOR");
		elig.add("ATTENDEE");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.TRUE);
	}
	
	public void testGetResourcesByAttendee(){
		Integer sessionId=1;
		try{
		if (sp.isServReqValid(sd, "safia@cgi.com", sessionId)) {		
			Set<Resource>resources=easy.getResources(sessionId);
			assertTrue("resources retrieved",true);
			}
		else{
			throw new InvalidServiceInvocationException();
		}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException :Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation : Should not occur");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
		
	}
	
	public void testGetResourcesByFacilitator(){
		Integer sessionId=1;
		try{
		if (sp.isServReqValid(sd, "jaffer@cgi.com", sessionId)) {		
			Set<Resource>resources=easy.getResources(sessionId);
			assertTrue("Resources retrieved",true);
			}
		else{
			throw new InvalidServiceInvocationException();
		}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation : Should not occur");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
		
	}
	
	
	public void testGetResourcesByInvalidUser(){
		Integer sessionId=1;
		try{
		if (sp.isServReqValid(sd, "divya@cgi.com", sessionId)) {		
			Set<Resource>resources=easy.getResources(sessionId);
			fail("should raise InvalidServiceInvocationException");
			}
		else{
			throw new InvalidServiceInvocationException();
		}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException :Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			assertTrue("success",true);
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
		
	}
	
	public void testGetResourcesByUnauthorisedUser(){
		Integer sessionId=1;
		try{
		if (sp.isServReqValid(sd, "surabhi@cgi.com", sessionId)) {		
			Set<Resource>resources=easy.getResources(sessionId);
			fail("should raise InvalidServiceInvocationException");
			}
		else{
			throw new InvalidServiceInvocationException();
		}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException :Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			assertTrue("success",true);
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
		
	}
	
	public void testGetResourcesForInvalidSession(){
		Integer sessionId=15;
		try{
		if (sp.isServReqValid(sd, "shamim.aziz", sessionId)) {		
			Set<Resource>resources=easy.getResources(sessionId);
			fail("Should raise SessionNotFoundException");
			}
		else{
			throw new InvalidServiceInvocationException();
		}
		} catch (SessionNotFoundException e) {
			assertTrue("success",true);
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation : Should not occur");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
		
	}

}
