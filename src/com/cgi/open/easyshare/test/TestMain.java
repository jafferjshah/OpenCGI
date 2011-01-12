package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.Set;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.model.*;

import com.cgi.open.easyshare.AdminAssignedException;
import com.cgi.open.easyshare.AttendeeAlreadyRegisteredException;
import com.cgi.open.easyshare.DuplicateSessionException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.PresentAsSameUserTypeException;
import com.cgi.open.easyshare.SessionNotAvailableException;
import com.cgi.open.easyshare.UserNotAvailableException;

public class TestMain {

	/**
	 * @param args
	 * @throws DuplicateSessionException 
	 * @throws SessionNotAvailableException 
	 * @throws PresentAsOtherUserTypeException 
	 * @throws PresentAsSameUserTypeException 
	 * @throws UserNotAvailableException 
	 * @throws AdminAssignedException 
	 * @throws AttendeeAlreadyRegisteredException 
	 */
	public static void main(String[] args) throws DuplicateSessionException, SessionNotAvailableException, PresentAsOtherUserTypeException, PresentAsSameUserTypeException, UserNotAvailableException, AttendeeAlreadyRegisteredException, AdminAssignedException {
		EasyShareServices service= ServicesMapper.getEasyShareServicesProxyInstance();
		Set<Appointment>appointments=new HashSet();
		Appointment a1=new Appointment();
		Appointment a2=new Appointment();
		a1.setDate("17/1/2010");
		a1.setFromTime("1600");
		a1.setToTime("1800");
		a2.setDate("18/1/2010");
		a2.setFromTime("1600");
		a2.setToTime("1800");
		appointments.add(a1);
		appointments.add(a2);
		User user1=new User();
		User user2=new User();
		User user3=new User();
		User userRef,facilRef;
		user1.setEmail("admin@cgi.com");
		user1.setEmpid(1000);
		user1.setName("Admin");
		user2.setEmail("jaffer@cgi.com");
		user2.setEmpid(1500);
		user3.setName("Jaffer");
		user3.setEmail("safiya@cgi.com");
		user3.setEmpid(2500);
		user2.setName("safiya");
		service.designateUser(user1, UserType.ADMIN);
		service.designateUser(user2, UserType.FACILITATOR);
		
		userRef=service.getUser(1000, UserType.ADMIN);
		
		Integer sessionId=service.createSession("advanced Hibernate", appointments);
		Session session1=service.getSession(sessionId);
		
		service.assignAdmin(sessionId,1000);
		service.addFacilitator(sessionId,1500);
		service.addAttendee(sessionId,2500);
		
		
		
		System.out.println(session1);
		
		

	}

}
