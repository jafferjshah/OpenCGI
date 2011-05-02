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
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class GetMessageTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd;
	public GetMessageTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();	
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd= new ServiceDef();
		sd.setServiceName("getMessages");
		Set<String> elig = new HashSet<String>();
		elig.add("ALL");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.TRUE);
	}
	
	public void testGetMessagesByAttendee(){
		Integer sessionId=1;
		try{
		if (sp.isServReqValid(sd, "safia@cgi.com", sessionId)) {		
			List<Message>messages=easy.getMessages(sessionId);
			assertTrue("messages retrieved",true);
			}
		else{
			throw new InvalidServiceInvocationException();
		}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException :Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
		
	}
	
	public void testGetMessagesByFacilitator(){
		Integer sessionId=1;
		try{
		if (sp.isServReqValid(sd, "jaffer@cgi.com", sessionId)) {		
			List<Message>messages=easy.getMessages(sessionId);
			assertTrue("messages retrieved",true);
			}
		else{
			throw new InvalidServiceInvocationException();
		}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException :Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
		
	}
	
	public void testGetMessagesByAdmin(){
		Integer sessionId=1;
		try{
		if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {		
			List<Message>messages=easy.getMessages(sessionId);
			assertTrue("messages retrieved",true);
			}
		else{
			throw new InvalidServiceInvocationException();
		}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException :Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
		
	}
	
	public void testGetMessagesByInvalidUser(){
		Integer sessionId=1;
		try{
		if (sp.isServReqValid(sd, "divya@cgi.com", sessionId)) {		
			List<Message>messages=easy.getMessages(sessionId);
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
	
	public void testGetMessagesByUnauthorisedUser(){
		Integer sessionId=1;
		try{
		if (sp.isServReqValid(sd, "surabhi@cgi.com", sessionId)) {		
			List<Message>messages=easy.getMessages(sessionId);
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
	
	public void testGetMessagesForInvalidSession(){
		Integer sessionId=15;
		try{
		if (sp.isServReqValid(sd, "shamim.aziz", sessionId)) {		
			List<Message>messages=easy.getMessages(sessionId);
			fail("Should raise SessionNotFoundException");
			}
		else{
			throw new InvalidServiceInvocationException();
		}
		} catch (SessionNotFoundException e) {
			assertTrue("success",true);
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
		
	}

}
