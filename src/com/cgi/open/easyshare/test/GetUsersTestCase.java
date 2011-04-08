package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.easyshare.model.User;
import com.cgi.open.easyshare.model.UserType;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;

public class GetUsersTestCase extends TestCase {
	EasyShareServices easy;
	SeparationOfUserConcerns sp;
	ServiceDef sd;

	public GetUsersTestCase(String name) {
		super(name);
	}

	public void setUp() {
		easy = ServicesMapper.getEasyShareServicesProxyInstance();
		sp = ServicesMapper.getSeparationOfUserConcernsProxyInstance();
		sd = new ServiceDef();
		Set<String> elig = new HashSet<String>();
		elig.add("FACILITATOR");
		elig.add("ADMIN");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.TRUE);
	}

	public void testGetUsersByFacilitator() {
		Integer sessionId = 1;
		UserType uType = UserType.ALL;
		try {
			if (sp.isServReqValid(sd, "jaffer@cgi.com", sessionId)) {
				Set<User> Users = easy.getUsers(sessionId, uType);
				assertTrue("retrieved", true);
			} else {
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}
	
	public void testGetUsersByAdmin() {
		Integer sessionId = 1;
		UserType uType = UserType.ALL;
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {
				Set<User> Users = easy.getUsers(sessionId, uType);
				assertTrue("retrieved", true);
			} else {
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}

	public void testGetUsersForInvalidSession() {
		Integer sessionId = 15;
		UserType uType = UserType.ALL;
		try {
			if (sp.isServReqValid(sd, "jaffer@cgi.com", sessionId)) {
				Set<User> Users = easy.getUsers(sessionId, uType);
				fail("Should raise SessionNotFoundException");
			} else {
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			assertTrue("success", true);
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
	}

}
