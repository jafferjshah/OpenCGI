package com.cgi.open.easyshare.test.composite;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.AdminAssignedException;
import com.cgi.open.easyshare.DuplicateSessionException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidPromotionException;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.PresentAsSameUserTypeException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.easyshare.model.UserType;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class DesignateAssignCreateTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd,sd1,sd2;
	public DesignateAssignCreateTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd= new ServiceDef();
		sd.setServiceName("Create session");
		Set<String> elig = new HashSet<String>();
		elig.add("ADMIN");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.FALSE);
	}

	public void testDesignateAssignCreate(){
		
		String email="shilpa@cgi.com";
		UserType uType=UserType.ADMIN;
		Boolean isDesignated=null;
		Boolean isAssigned=null;
		Boolean actual=null;
		try {
			isDesignated=easy.designateUser(email, uType);
			String sessionName="Annotations";
			String description="features";
			Integer sessionId=null;
			if (sp.isServReqValid(sd, "shilpa@cgi.com", sessionId)){
				sessionId=easy.createSession(sessionName, description);	
			}
			else{
				throw new InvalidServiceInvocationException();
			}
				isAssigned=easy.assignAdmin(sessionId, email);
				actual=isDesignated&&sessionId>0&&isAssigned;
				assertEquals(Boolean.TRUE,actual);
			
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
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
		} catch (InvalidPromotionException e) {
			fail("InvalidPromotionException : Should not occur");
		} catch (DuplicateSessionException e) {
			fail("DuplicateSessionException : Should not occur");
		} catch (AdminAssignedException e) {
			fail("AdminAssignedException : Should not occur");
		}
	}
	
	
	public void testAddFacilitatorForInvalidSession(){
		Integer sessionId=15;
		String email="jaffer@cgi.com";
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)){
			Boolean isAdded=easy.addFacilitator(sessionId, email);
			fail("should raise SessionNotfoundException");
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
	
	/*public void testAddOtherUserAsFacilitator(){
		Integer sessionId=1;
		String email="sanjana@cgi.com";
		try {
			Boolean isAdded=easy.addFacilitator(sessionId, email);
			fail("should raise PresentAsOtherUserTypeException");
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (PresentAsOtherUserTypeException e) {
			assertTrue("success",true);
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException : Should not occur");
		} catch (PresentAsSameUserTypeException e) {
			fail("PresentAsSameUserTypeException : Should not occur");
		}
	}*/
	
	public void testAddInvalidUserAsFacilitator(){
		Integer sessionId=1;
		String email="divya@cgi.com";
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)){
			Boolean isAdded=easy.addFacilitator(sessionId, email);
			fail("should raise UserNotFoundException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (PresentAsOtherUserTypeException e) {
			fail("PresentAsOtherUserTypeException : Should not occur");
		} catch (UserNotFoundException e) {
			assertTrue("success",true);
		} catch (PresentAsSameUserTypeException e) {
			fail("PresentAsSameUserTypeException : Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testAddFacilitatorAgain(){
		Integer sessionId=1;
		String email="jaffer@cgi.com";
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)){
			Boolean isAdded=easy.addFacilitator(sessionId, email);
			fail("should raise PresentAsSameUserTypeException");
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
		} catch (PresentAsSameUserTypeException e) {
			assertTrue("success",true);
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
}
