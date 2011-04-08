package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class AddMessageTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd;
	public AddMessageTestCase(String name){
		super(name);
	}
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd= new ServiceDef();
		sd.setServiceName("addMessage");
		Set<String> elig = new HashSet<String>();
		elig.add("FACILITATOR");
		elig.add("ATTENDEE");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.TRUE);
	}
	
	public void testAddMessageByAttendee(){
		Integer sessionId=1;
		String subject="discussion";
		String text="how was the session";
		String date="03/26/2011";
		String post_time="1530";
		String post_by="safia@cgi.com";
		try {
			if (sp.isServReqValid(sd, "safia@cgi.com", sessionId)) {
			Integer msgId=easy.addMessage(sessionId, subject, text, date, post_time, post_by);
			assertTrue("Message added",msgId>0);
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testAddMessageByFacilitator(){
		Integer sessionId=1;
		String subject="discussion";
		String text="how was the session";
		String date="03/26/2011";
		String post_time="1530";
		String post_by="jaffer@cgi.com";
		try {
			if (sp.isServReqValid(sd, "jaffer@cgi.com", sessionId)) {
			Integer msgId=easy.addMessage(sessionId, subject, text, date, post_time, post_by);
			assertTrue("Message added",msgId>0);
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testAddMessageByInvalidUser(){
		Integer sessionId=1;
		String subject="discussion";
		String text="how was the session";
		String date="03/26/2011";
		String post_time="1530";
		String post_by="jaffer@cgi.com";
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {
			Integer msgId=easy.addMessage(sessionId, subject, text, date, post_time, post_by);
			fail("should raise invalid service invocation");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			assertTrue("success",true);
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testAddMessageForInvalidSession(){
		Integer sessionId=15;
		String subject="discussion";
		String text="how was the session";
		String date="03/26/2011";
		String post_time="1530";
		String post_by="jaffer@cgi.com";
		try {
			if (sp.isServReqValid(sd, "jaffer.shah", sessionId)) {
			Integer msgId=easy.addMessage(sessionId, subject, text, date, post_time, post_by);
			fail("Should raise SessionNotFoundException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			assertTrue("success",true);
		}
		 catch (InvalidServiceInvocationException e) {
				fail("Invalid service invocation");
			} catch (UserTypeNotValidException e) {
				fail("UserTypeNotValidException : Should not occur");
			}
	}

}
