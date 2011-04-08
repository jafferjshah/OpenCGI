package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.AdminAssignedException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.PresentAsSameUserTypeException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class AddAttendeeTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd;
	public AddAttendeeTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd= new ServiceDef();
		sd.setServiceName("addAttendee");
		Set<String> elig = new HashSet<String>();
		elig.add("ADMIN");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.TRUE);
	}
	
	public void testAddAttendee(){
		Integer sessionId=1;
		String email="safia@cgi.com";
		Boolean isAdded=null;
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {
			isAdded=easy.addAttendee(sessionId, email);
			assertEquals(Boolean.TRUE,isAdded);
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (PresentAsOtherUserTypeException e) {
			fail("PresentAsOtherUserTypeException : Should not occur");
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException : Should not occur");
		}
		 catch (PresentAsSameUserTypeException e) {
			fail("PresentAsSameUserTypeException : Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testAddAttendeeForInvalidSession(){
		Integer sessionId=15;
		String email="safia@cgi.com";
		Boolean isAdded=null;
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {
			isAdded=easy.addAttendee(sessionId, email);
			fail("Should raise SessionNotFoundException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			assertTrue("success",true);
		} catch (PresentAsOtherUserTypeException e) {
			fail("PresentAsOtherUserTypeException : Should not occur");
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException : Should not occur");
		} catch (PresentAsSameUserTypeException e) {
			fail("PresentAsSameUserTypeException : Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testAddOtherUserAsAttendee(){
		Integer sessionId=1;
		String email="nishi.kanth";
		Boolean isAdded=null;
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {
			isAdded=easy.addAttendee(sessionId, email);
			fail("Should raise PresentAsOtherUserTypeException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (PresentAsOtherUserTypeException e) {
			assertTrue("success",true);
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException : Should not occur");
		} catch (PresentAsSameUserTypeException e) {
			fail("PresentAsSameUserTypeException : Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	/*public void testAddInvalidUserAsAttendee(){
		Integer sessionId=1;
		String email="divya@cgi.com";
		Boolean isAdded=null;
		try {
			isAdded=easy.addAttendee(sessionId, email);
			fail("Should raise UserNotFoundException");
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (PresentAsOtherUserTypeException e) {
			fail("PresentAsOther	UserTypeException : Should not occur");
		} catch (UserNotFoundException e) {
			assertTrue("success",true);
		} catch (PresentAsSameUserTypeException e) {
			fail("PresentAsSameUserTypeException : Should not occur");
		}
	}*/
	
	public void testAddAttendeeAgain(){
		Integer sessionId=1;
		String email="safia@cgi.com";
		Boolean isAdded=null;
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {
			isAdded=easy.addAttendee(sessionId, email);
			fail("Should raise PresentAsSameUserTypeException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (PresentAsOtherUserTypeException e) {
			fail("PresentAsOther	UserTypeException : Should not occur");
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException : Should not occur");
		} catch (PresentAsSameUserTypeException e) {
			assertTrue("success",true);
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}

}
