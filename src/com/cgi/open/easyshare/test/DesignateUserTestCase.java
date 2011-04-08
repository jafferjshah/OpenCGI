package com.cgi.open.easyshare.test;

import org.junit.Test;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidPromotionException;
import com.cgi.open.easyshare.PresentAsSameUserTypeException;
import com.cgi.open.easyshare.UserNotFoundException;
import com.cgi.open.easyshare.model.*;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;

public class DesignateUserTestCase extends TestCase {
	
	EasyShareServices easy;
	public DesignateUserTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();	
	}
	
	@Test
	public void testDesignateUser(){
		String email="jaffer@cgi.com";
		UserType uType=UserType.FACILITATOR;
		try {
			Boolean isDesignated=easy.designateUser(email, uType);
			assertEquals(isDesignated,Boolean.TRUE);
				
		} catch (InvalidPromotionException e) {
			fail("InvalidPromotionException:Should not occur");
			
		} catch (PresentAsSameUserTypeException e) {
			fail("PresentAsSameUserTypeException:Should not occur");
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException:Should not occur");
		}
		
	}
	
	public void testDesignateUserToAttendee(){
		String email="sravani@cgi.com";
		UserType uType=UserType.ATTENDEE;
		try {
			Boolean isDesignated=easy.designateUser(email, uType);
			fail("should raise an InvalidPromotionException");
		}
		catch (InvalidPromotionException e) {
			assertTrue("success", true);
			
		} catch (PresentAsSameUserTypeException e) {
			fail("PresentAsSameUserTypeException:Should not occur");
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException:Should not occur");
		}
		
	}
	
	public void testDesignateUserAsSameUserType(){
		String email="jaffer@cgi.com";
		UserType uType=UserType.FACILITATOR;
		try {
			Boolean isDesignated=easy.designateUser(email, uType);
			fail("should raise PresentAsSameUserTypeException");
				
		} catch (InvalidPromotionException e) {
			fail("InvalidPromotionException:Should not occur");
			
		} catch (PresentAsSameUserTypeException e) {
			assertTrue("success", true);
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException:Should not occur");
		}
		
	}
}
