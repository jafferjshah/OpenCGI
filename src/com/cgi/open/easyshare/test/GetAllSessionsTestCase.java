package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.DuplicateSessionException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.easyshare.model.Session;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class GetAllSessionsTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd;
	public GetAllSessionsTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd= new ServiceDef();
		sd.setServiceName("createSession");
		Set<String> elig = new HashSet<String>();
		elig.add("ALL");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.FALSE);
	}
	
	public void testGetAllSessionsByAttendee(){
		Set<Session>sessions=null;
		try {
			if (sp.isServReqValid(sd, "safia@cgi.com", null)) {
			sessions = easy.getAllSessions();
			assertTrue("sessions retrieved",true);
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} 
	}
	
	public void testGetAllSessionsByFacilitator(){
		Set<Session>sessions=null;
		try {
			if (sp.isServReqValid(sd, "jaffer@cgi.com", null)) {
			sessions = easy.getAllSessions();
			assertTrue("sessions retrieved",true);
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} 
	}
	
	public void testGetAllSessionsByAdmin(){
		Set<Session>sessions=null;
		try {
			if (sp.isServReqValid(sd, "anupama.charles", null)) {
			sessions = easy.getAllSessions();
			assertTrue("sessions retrieved",true);
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} 
	}
}
