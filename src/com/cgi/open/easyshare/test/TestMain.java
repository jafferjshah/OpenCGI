package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.Set;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.model.*;
import java.util.*;
import com.cgi.open.easyshare.AdminAssignedException;
import com.cgi.open.easyshare.AttendeeAlreadyRegisteredException;
import com.cgi.open.easyshare.AttendeeNotFoundException;
import com.cgi.open.easyshare.DuplicateSessionException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.FacilitatorNotFoundException;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.PresentAsSameUserTypeException;
import com.cgi.open.easyshare.ResourceNotFoundException;
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
	 * @throws FacilitatorNotFoundException 
	 * @throws AttendeeNotFoundException 
	 * @throws ResourceNotFoundException 
	 */
	public static void main(String[] args) throws DuplicateSessionException, SessionNotAvailableException, PresentAsOtherUserTypeException, PresentAsSameUserTypeException, UserNotAvailableException, AttendeeAlreadyRegisteredException, AdminAssignedException, FacilitatorNotFoundException, AttendeeNotFoundException, ResourceNotFoundException {
		EasyShareServices service= ServicesMapper.getEasyShareServicesProxyInstance();
		
		User user1=new User();
		User user2=new User();
		User user3=new User();
		User userRef,facilRef;
		user1.setEmail("admin@cgi.com");
		user1.setEmpid(1000);
		user1.setName("Admin");
		user2.setEmail("jaffer@cgi.com");
		user2.setEmpid(1500);
		user2.setName("Jaffer");
		user3.setEmail("safiya@cgi.com");
		user3.setEmpid(2500);
		user3.setName("safiya");
		service.designateUser(user1, UserType.ADMIN);
		service.designateUser(user2, UserType.FACILITATOR);
		
		userRef=service.getUser(1000, UserType.ADMIN);
		
		Integer sessionId=service.createSession("advanced Hibernate");
		Session session1=service.getSession(sessionId);
		service.addAppointment(sessionId, "17/01/2011", "1600", "1800");
		service.addAppointment(sessionId, "18/01/2011", "1600", "1800");
		service.assignAdmin(sessionId,1000);
		service.addFacilitator(sessionId,1500);
		service.addMessage(sessionId, "hello", "welcome");
		service.addMessage(sessionId, "babye", "good night!!");
		List<Message> mlist=service.getMessages(sessionId);
		service.addResource(sessionId, "resourceName1", "url1");
		service.addResource(sessionId, "resourceName2", "url2");
	
		service.addAttendee(sessionId,2500);
		Set<Resource> rset=service.getResources(sessionId);
		
		
		System.out.println(session1);
		System.out.println(mlist);
		System.out.println(rset);
		service.removeResource(sessionId, 2);
		System.out.println(rset);
		service.removeFacilitator(sessionId, 1500);
		service.removeAttendee(sessionId, 2500);
		
		System.out.println(session1);
		
		

	}

}
