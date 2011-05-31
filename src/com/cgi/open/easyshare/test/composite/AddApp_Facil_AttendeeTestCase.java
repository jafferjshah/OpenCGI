package com.cgi.open.easyshare.test.composite;

import java.util.HashSet;
import java.util.Set;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.AppointmentNotFoundException;
import com.cgi.open.easyshare.DuplicateAppointmentException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.PresentAsSameUserTypeException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.easyshare.model.User;
import com.cgi.open.easyshare.model.UserType;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;

import junit.framework.TestCase;


public class AddApp_Facil_AttendeeTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd1,sd2,sd3,sd4,sd5,sd6,sd7,sd8;
	public AddApp_Facil_AttendeeTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd1= new ServiceDef();
		sd1.setServiceName("Add appointment");
		Set<String> elig = new HashSet<String>();
		elig.add("ADMIN");
		sd1.setEligUserTypes(elig);
		
		sd2= new ServiceDef();
		sd2.setServiceName("Add facilitator");
		sd2.setEligUserTypes(elig);
		
		sd3= new ServiceDef();
		sd3.setServiceName("Add attendee");
		sd3.setEligUserTypes(elig);
		
		//to be done
		sd4= new ServiceDef();
		sd4.setServiceName("Get Appointment");
		sd4.setEligUserTypes(elig);
		
		sd5= new ServiceDef();
		sd5.setServiceName("Get Users");
		sd5.setEligUserTypes(elig);
		
		sd6= new ServiceDef();
		sd6.setServiceName("Remove Appointment");
		sd6.setEligUserTypes(elig);
		
		sd7= new ServiceDef();
		sd7.setServiceName("Remove Facilitator");
		sd7.setEligUserTypes(elig);
		
		sd8= new ServiceDef();
		sd8.setServiceName("Remove Attendee");
		sd8.setEligUserTypes(elig);
		
		sd1.setSessionRelationExists(Boolean.TRUE);
		sd2.setSessionRelationExists(Boolean.TRUE);
		sd3.setSessionRelationExists(Boolean.TRUE);
		sd5.setSessionRelationExists(Boolean.FALSE);
		sd6.setSessionRelationExists(Boolean.TRUE);
		sd7.setSessionRelationExists(Boolean.TRUE);
		sd8.setSessionRelationExists(Boolean.TRUE);
	}
	
	public void testAddAppFacilAttendee(){
		Integer sessionId=7;
		String date="03/28/2011";
		String fromTime="1230";
		String toTime="1530";
		String location="ecity";
		Integer appointmentId=null;
		Boolean isAppAdded,isFacilAdded,isAttenAdded,actual,isRemoved,isRetrieved=null;
		String facilEmail="jaffer@cgi.com";
		Set<String> attendeesEmail=new HashSet();
		Set<String>retrievedEmails;
		try {
			if (sp.isServReqValid(sd1, "shilpa@cgi.com", sessionId)) {
				appointmentId=easy.addAppointment(sessionId, date, fromTime, toTime, location);
				isAppAdded=appointmentId>0;
				actual=isAppAdded;
				isRemoved=easy.removeAppointment(sessionId, appointmentId);
				actual=actual&&isRemoved;
			}
			else{
				throw new InvalidServiceInvocationException();
			}
			
			if (sp.isServReqValid(sd2, "shilpa@cgi.com", sessionId)) {
				isFacilAdded=easy.addFacilitator(sessionId, facilEmail);
				actual=actual&&isFacilAdded;
				retrievedEmails=easy.getAllUsersLight(sessionId, UserType.FACILITATOR);
				if(retrievedEmails.contains(facilEmail)){
					isRetrieved=Boolean.TRUE;
				}
				else{
					isRetrieved=Boolean.FALSE;
				}
				actual=actual&&isRetrieved;
				isRemoved=easy.removeFacilitator(sessionId,facilEmail);
				actual=actual&&isRemoved;
				retrievedEmails=easy.getAllUsersLight(sessionId, UserType.FACILITATOR);
				if(retrievedEmails.contains(facilEmail)){
					isRetrieved=Boolean.FALSE;
				}
				else{
					isRetrieved=Boolean.TRUE;
				}
				actual=actual&&isRetrieved;
			}
			else{
					throw new InvalidServiceInvocationException();
			}
			
			if (sp.isServReqValid(sd3, "shilpa@cgi.com", sessionId)) {
				attendeesEmail.add("sanjana@cgi.com");
				attendeesEmail.add("safia@cgi.com");
				attendeesEmail.add("javed@cgi.com");
				isAttenAdded=easy.addAttendees(sessionId, attendeesEmail);
				actual=actual&&isAttenAdded;
				System.out.println(easy.getAllUsersLight(sessionId, UserType.ATTENDEE));
				isRemoved=easy.removeAttendee(sessionId,"sanjana@cgi.com");
				actual=actual&&isRemoved;
				System.out.println(easy.getAllUsersLight(sessionId, UserType.ATTENDEE));
			}
			else{
					throw new InvalidServiceInvocationException();
			}
			assertEquals(Boolean.TRUE,actual);
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (DuplicateAppointmentException e) {
			fail("DuplicateAppointmentException : Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation : Should not occur");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		} catch (PresentAsOtherUserTypeException e) {
			fail("PresentAsOtherUserTypeException : Should not occur");
		} catch (UserNotFoundException e) {
			fail("UserNotFoundException : Should not occur");
		} catch (PresentAsSameUserTypeException e) {
			fail("PresentAsSameUserTypeException : Should not occur");
		} catch (AppointmentNotFoundException e) {
			fail("AppointmentNotFoundException : Should not occur");
		}
	}

}
