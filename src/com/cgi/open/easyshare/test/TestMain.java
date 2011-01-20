package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.Map;
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
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.external.UserIntegration;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;

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
	/**
	 * @param args
	 * @throws SessionNotAvailableException
	 * @throws UserTypeNotValidException
	 * @throws UserNotAvailableException
	 * @throws PresentAsSameUserTypeException
	 * @throws DuplicateSessionException
	 * @throws SessionNotAvailableException
	 * @throws PresentAsOtherUserTypeException
	 * @throws PresentAsSameUserTypeException
	 * @throws UserNotAvailableException
	 * @throws AttendeeAlreadyRegisteredException
	 * @throws AdminAssignedException
	 * @throws FacilitatorNotFoundException
	 * @throws AttendeeNotFoundException
	 * @throws ResourceNotFoundException
	 */
	public static void main(String[] args)
			throws PresentAsSameUserTypeException, UserNotAvailableException,
			UserTypeNotValidException, SessionNotAvailableException {
		jafferTest();
	}

	public static void sanjanaTest() throws DuplicateSessionException,
			SessionNotAvailableException, PresentAsOtherUserTypeException,
			PresentAsSameUserTypeException, UserNotAvailableException,
			AttendeeAlreadyRegisteredException, AdminAssignedException,
			FacilitatorNotFoundException, AttendeeNotFoundException,
			ResourceNotFoundException {
		EasyShareServices service = ServicesMapper
				.getEasyShareServicesProxyInstance();

		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		User user4 = new User();
		User userRef, facilRef;
		/*
		 * user1.setEmail("admin1@cgi.com"); user1.setEmpid(1000);
		 * user1.setName("Admin"); user4.setEmail("admin2@cgi.com");
		 * user4.setEmpid(1200); user4.setName("Admin2");
		 * user2.setEmail("jaffer@cgi.com"); user2.setEmpid(1500);
		 * user2.setName("Jaffer"); user3.setEmail("safiya@cgi.com");
		 * user3.setEmpid(2500); user3.setName("safiya");
		 */
		service.designateUser("", UserType.ADMIN);
		service.designateUser("", UserType.ADMIN);
		service.designateUser("", UserType.FACILITATOR);

		userRef = service.getUser("", UserType.ADMIN);

		Integer sessionId1 = service.createSession("advanced Hibernate");
		Session session1 = service.getSession(sessionId1);
		service.addAppointment(sessionId1, "17/01/2011", "1600", "1800");
		service.addAppointment(sessionId1, "18/01/2011", "1600", "1800");
		service.assignAdmin(sessionId1, "");
		service.addFacilitator(sessionId1, "");
		service.addMessage(sessionId1, "hello", "welcome");
		service.addMessage(sessionId1, "babye", "good night!!");
		List<Message> mlist = service.getMessages(sessionId1);
		service.addResource(sessionId1, "resourceName1", "url1");
		service.addResource(sessionId1, "resourceName2", "url2");

		service.addAttendee(sessionId1, "");
		Set<Resource> rset = service.getResources(sessionId1);

		// System.out.println(session1);
		// System.out.println(mlist);
		// System.out.println(rset);
		service.removeResource(sessionId1, 2);
		// System.out.println(rset);
		// service.removeFacilitator(sessionId1, 1500);
		// service.removeAttendee(sessionId1, 2500);
		Integer sessionId2 = service.createSession("Advanced Java");
		service.assignAdmin(sessionId2, "");
		service.addAppointment(sessionId2, "20/01/2011", "1500", "1700");
		service.addAppointment(sessionId2, "21/01/2011", "1500", "1700");
		service.addAppointment(sessionId2, "23/01/2011", "1500", "1700");
		service.addFacilitator(sessionId2, "");
		// System.out.println(session1);
		Map<UserType, Set<Session>> sessions = service.getMySessions("");
		System.out.println(sessions);
	}

	public static void jafferTest() throws PresentAsSameUserTypeException,
			UserNotAvailableException, UserTypeNotValidException,
			SessionNotAvailableException {
		UserIntegration uint = ServicesMapper.getUserIntegrationProxyInstance();
		EasyShareServices easy = ServicesMapper
				.getEasyShareServicesProxyInstance();
		easy.designateUser("jaffer.shah", UserType.ADMIN);
		System.out.println(easy.getUser("jaffer.shah", UserType.ADMIN));
		SeparationOfUserConcerns sp = ServicesMapper
				.getSeparationOfUserConcernsProxyInstance();
		ServiceDef sd = new ServiceDef();
		sd.setServiceName("createSession");
		Set<String> elig = new HashSet<String>();
		elig.add("ADMIN");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.TRUE);
		System.out.println(sp.isServReqValid(sd, "jaffer.shah", null));
	}
}
