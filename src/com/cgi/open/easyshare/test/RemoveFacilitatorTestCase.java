package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class RemoveFacilitatorTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd;
	public RemoveFacilitatorTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();	
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd= new ServiceDef();
		sd.setServiceName("removeFacilitator");
		Set<String> elig = new HashSet<String>();
		elig.add("ADMIN");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.TRUE);
	}
	
	public void testRemoveFacilitator(){
		Integer sessionId=1;
		String email="jaffer@cgi.com";
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {
			Boolean isRemoved=easy.removeFacilitator(sessionId, email);
			assertEquals(Boolean.TRUE,isRemoved);
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException : Should not occur");
		}catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testRemoveFacilitatorForInvalidSession(){
		Integer sessionId=15;
		String email="jaffer@cgi.com";
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {
			Boolean isRemoved=easy.removeFacilitator(sessionId, email);
			fail("should raise a SessionNotFoundException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			assertTrue("success",true);
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException : Should not occur");
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testRemoveInvalidFacilitator(){
		Integer sessionId=1;
		String email="jaffer@cgi.com";
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {
			Boolean isRemoved=easy.removeFacilitator(sessionId, email);
			fail("should raise an UserNotFoundException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (UserNotFoundException e) {
			assertTrue("success",true);
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}

}
