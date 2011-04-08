package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.easyshare.model.Session;
import com.cgi.open.easyshare.model.UserType;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class GetMySessionsTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd;
	public GetMySessionsTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();	
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd= new ServiceDef();
		sd.setServiceName("getMySessions");
		Set<String> elig = new HashSet<String>();
		elig.add("ALL");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.FALSE);
	}
	
	public void testGetMySessionsByFacilitator(){
		String email="jaffer@cgi.com";
		Map<UserType, Set<Session>> sessions = null;
		try{
		if (sp.isServReqValid(sd, "jaffer.shah", null)) {
			sessions = easy.getMySessions("jaffer.shah");
			assertTrue("my sessions retrieved",true);
		}
		else{
			throw new InvalidServiceInvocationException();
		}
		}catch(SessionNotFoundException e){
			fail("SessionNotFoundException : Should not occur");
			
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation :Should not occur");
		}
	
	}
	
	public void testGetMySessionsByAttendee(){
		String email="safia@cgi.com";
		Map<UserType, Set<Session>> sessions = null;
		try{
		if (sp.isServReqValid(sd, "safia@cgi.com", null)) {
			sessions = easy.getMySessions("safia@cgi.com");
			assertTrue("my sessions retrieved",true);
		}
		else{
			throw new InvalidServiceInvocationException();
		}
		}catch(SessionNotFoundException e){
			fail("SessionNotFoundException : Should not occur");
			
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation : Should not occur");
		}
	
	}
	
	public void testGetMySessionsByAdmin(){
		String email="anupama.charles";
		Map<UserType, Set<Session>> sessions = null;
		try{
		if (sp.isServReqValid(sd, "anupama.charles", null)) {
			sessions = easy.getMySessions("anupama.charles");
			assertTrue("my sessions retrieved",true);
		}
		else{
			throw new InvalidServiceInvocationException();
		}
		}catch(SessionNotFoundException e){
			fail("SessionNotFoundException : Should not occur");
			
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation : Should not occur");
		}
	
	}
}
