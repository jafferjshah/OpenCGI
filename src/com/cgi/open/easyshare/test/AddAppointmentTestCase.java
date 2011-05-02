package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.DuplicateAppointmentException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class AddAppointmentTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd;
	public AddAppointmentTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd= new ServiceDef();
		sd.setServiceName("addAppointment");
		Set<String> elig = new HashSet<String>();
		elig.add("ADMIN");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.TRUE);
	}
	
	public void testAddAppointment(){
		Integer sessionId=1;
		String date="03/28/2011";
		String fromTime="1230";
		String toTime="1530";
		String location="ecity";
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {
			Integer appointmentId=easy.addAppointment(sessionId, date, fromTime, toTime, location);
			assertTrue("appointment created",sessionId>0);
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("invalid exception!!");
		} catch (DuplicateAppointmentException e) {
			fail("invalid exception!!");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
		
	}
	
	public void testAddAppForSessionNotFound(){
		Integer sessionId=15;
		String date="03/28/2011";
		String fromTime="1230";
		String toTime="1530";
		String location="ecity";
		try {
			if (sp.isServReqValid(sd, "anupama@charles", sessionId)) {
			Integer appointmentId=easy.addAppointment(sessionId, date, fromTime, toTime, location);
			fail("should raise SessionNotFoundException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			assertTrue("success",true);
		} catch (DuplicateAppointmentException e) {
			fail("DuplicateAppointmentException:Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
		
	}
	
	public void testAddDuplicateAppointment(){
		Integer sessionId=1;
		String date="03/28/2011";
		String fromTime="1230";
		String toTime="1530";
		String location="ecity";
		try {
			if (sp.isServReqValid(sd, "anupama.charles", sessionId)) {
			Integer appointmentId=easy.addAppointment(sessionId, date, fromTime, toTime, location);
			fail("should raise DuplicateAppointmentException");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException:Should not occur");
			
		} catch (DuplicateAppointmentException e) {
			assertTrue("success",true);
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}
		
	}
	
	

}
