package com.cgi.open.easyshare.test;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.AdminAssignedException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.PresentAsSameUserTypeException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserNotFoundException;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class AssignAdminTestCase extends TestCase{
	EasyShareServices easy;
	
	public AssignAdminTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();
	}
	
	public void testAssignAdmin(){
		Integer sessionId=1;
		String email="anupama.charles";
		try {
			Boolean isAssigned=easy.assignAdmin(sessionId, email);
			assertEquals(Boolean.TRUE,isAssigned);
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (PresentAsOtherUserTypeException e) {
			fail("PresentAsOtherUserTypeException :Should not occur");
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException :Should not occur");
		} catch (AdminAssignedException e) {
			fail("AdminAssignedException :Should not occur");
		} catch (PresentAsSameUserTypeException e) {
			fail("PresentAsSameUserTypeException :Should not occur");
		}
	}
	
	public void testAssignAdminForInvalidSession(){
		Integer sessionId=15;
		String email="surabhi@cgi.com";
		try {
			Boolean isAssigned=easy.assignAdmin(sessionId, email);
			fail("should raise SessionNotFoundException");
		} catch (SessionNotFoundException e) {
			assertTrue("success",true);
		} catch (PresentAsOtherUserTypeException e) {
			fail("PresentAsOtherUserTypeException :Should not occur");
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException :Should not occur");
		} catch (AdminAssignedException e) {
			fail("AdminAssignedException :Should not occur");
		} catch (PresentAsSameUserTypeException e) {
			fail("PresentAsSameUserTypeException :Should not occur");
		}
	}
	
//	public void testAssignOtherUserAsAdmin(){
//		Integer sessionId=1;
//		String email="jaffer@cgi.com";
//		try {
//			Boolean isAssigned=easy.assignAdmin(sessionId, email);
//			fail("should raise PresentAsOtherUserTypeException");
//		} catch (SessionNotFoundException e) {
//			fail("SessionNotFoundException : Should not occur");
//		} catch (PresentAsOtherUserTypeException e) {
//			assertTrue("success",true);
//		} catch (UserNotFoundException e) {
//			fail("UserNotFoundException :Should not occur");
//		} catch (AdminAssignedException e) {
//			fail("AdminAssignedException :Should not occur");
//		} catch (PresentAsSameUserTypeException e) {
//			fail("PresentAsSameUserTypeException :Should not occur");
//		}
//	}
	
	
	public void testAssignInvalidUserAsAdmin(){
		Integer sessionId=4;
		String email="divya@cgi.com";
		try {
			Boolean isAssigned=easy.assignAdmin(sessionId, email);
			fail("should raise UserNotFoundException");
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (PresentAsOtherUserTypeException e) {
			fail("PresentAsOtherUserTypeException :Should not occur");
		} catch (UserNotFoundException e) {
			assertTrue("success",true);
		} catch (AdminAssignedException e) {
			fail("AdminAssignedException :Should not occur");
		} catch (PresentAsSameUserTypeException e) {
			fail("PresentAsSameUserTypeException :Should not occur");
		}
	}
	
	public void testAssignAdminAgain(){
		Integer sessionId=1;
		String email="anupama.charles";
		try {
			Boolean isAssigned=easy.assignAdmin(sessionId, email);
			fail("should raise AdminAssignedException");
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (PresentAsOtherUserTypeException e) {
			fail("PresentAsOtherUserTypeException :Should not occur");
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException :Should not occur");
		} catch (AdminAssignedException e) {
			assertTrue("success",true);
		} catch (PresentAsSameUserTypeException e) {
			fail("PresentAsSameUserTypeException :Should not occur");
		}
	}
	
	/*public void testAssignSameAdminAgain(){
		Integer sessionId=2;
		String email="surabhi@cgi.com";
		try {
			Boolean isAssigned=easy.assignAdmin(sessionId, email);
			fail("should raise PresentAsSameUserTypeException");
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (PresentAsOtherUserTypeException e) {
			fail("PresentAsOtherUserTypeException :Should not occur");
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException :Should not occur");
		} catch (AdminAssignedException e) {
			fail("AdminAssignedException :Should not occur");
		} catch (PresentAsSameUserTypeException e) {
			assertTrue("success",true);
		}
	}*/

}
