package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.DuplicateResourceException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class AddResourceTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd;
	public AddResourceTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();	
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd= new ServiceDef();
		sd.setServiceName("addResource");
		Set<String> elig = new HashSet<String>();
		elig.add("FACILITATOR");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.TRUE);
	}
	
	public void testAddResource(){
		Integer sessionId=1;
		String resourceName="e-books";
		String url="Books/e-books";
		try {
			if (sp.isServReqValid(sd, "jaffer@cgi.com", sessionId)) {
			Integer resourceId=easy.addResource(sessionId, resourceName, url);
			assertTrue("resource added",resourceId>0);
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (DuplicateResourceException e) {
			fail("DuplicateResourceException : Should not occur");
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testAddResourceByInvalidUser(){
		Integer sessionId=1;
		String resourceName="e-books1";
		String url="Books/e-books1";
		try {
			if (sp.isServReqValid(sd, "divya@cgi.com", sessionId)) {
			Integer resourceId=easy.addResource(sessionId, resourceName, url);
			fail("should raise InvalidServiceInvocationException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (DuplicateResourceException e) {
			fail("DuplicateResourceException : Should not occur");
		} catch (InvalidServiceInvocationException e) {
			assertTrue("success",true);
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testAddResourceForInvalidSession(){
		Integer sessionId=15;
		String resourceName="e-books";
		String url="Books/e-books";
		try {
			if (sp.isServReqValid(sd, "jaffer@cgi.com", sessionId)) {
			Integer resourceId=easy.addResource(sessionId, resourceName, url);
			fail("should raise SessionNotFoundException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			assertTrue("success",true);
		} catch (DuplicateResourceException e) {
			fail("DuplicateResourceException : Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testAddResourceAgain(){
		Integer sessionId=1;
		String resourceName="e-books";
		String url="Books/e-books";
		try {
			if (sp.isServReqValid(sd, "jaffer@cgi.com", sessionId)) {
			Integer resourceId=easy.addResource(sessionId, resourceName, url);
			fail("should raise DuplicateResourceException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (DuplicateResourceException e) {
			assertTrue("success",true);
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}

}
