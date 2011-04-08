package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.AppointmentNotFoundException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class RemoveAppointmentTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd;
	public RemoveAppointmentTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();	
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd= new ServiceDef();
		sd.setServiceName("removeAppointment");
		Set<String> elig = new HashSet<String>();
		elig.add("ADMIN");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.TRUE);
	}

	public void testRemoveAppointment(){
		Integer sessionId=1;
		Integer appId=1;
		
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {
			boolean isRemoved=easy.removeAppointment(sessionId, appId);
			assertEquals(true, isRemoved);
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException:Should not occur");
		} catch (AppointmentNotFoundException e) {
			fail("AppointmentNotFoundException:Should not occur");
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testRemoveAppointmentFromInvalidSession(){
		Integer sessionId=15;
		Integer appId=1;
		
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {
			boolean isRemoved=easy.removeAppointment(sessionId, appId);
			fail("should raise SessionNotFoundException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			assertTrue("success", true);
		} catch (AppointmentNotFoundException e) {
			fail("AppointmentNotFoundException:Should not occur");
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testRemoveInvalidAppointment(){
		Integer sessionId=1;
		Integer appId=1;
		
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {
			boolean isRemoved=easy.removeAppointment(sessionId, appId);
			fail("should raise AppointmentNotFoundException e");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException:Should not occur");
			
		} catch (AppointmentNotFoundException e) {
			assertTrue("success", true);
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
}
