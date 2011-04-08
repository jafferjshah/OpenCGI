package com.cgi.open.easyshare.test;

import java.sql.ResultSet;
import java.util.HashSet;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.cgi.open.ServicesMapper;
import com.cgi.open.Utilities.*;
import com.cgi.open.easyshare.model.*;

import java.util.*;
import com.cgi.open.easyshare.AdminAssignedException;
import com.cgi.open.easyshare.DuplicateAppointmentException;
import com.cgi.open.easyshare.AppointmentNotFoundException;
import com.cgi.open.easyshare.AttendeeNotFoundException;
import com.cgi.open.easyshare.DuplicateResourceException;
import com.cgi.open.easyshare.DuplicateSessionException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.FacilitatorNotFoundException;
import com.cgi.open.easyshare.InvalidPromotionException;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.PresentAsSameUserTypeException;
import com.cgi.open.easyshare.ResourceNotFoundException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.external.UserIntegration;
import com.cgi.open.persist.QUERY_CONSTANTS;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;
import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestMain {

	/**
	 * @param args
	 * @throws PresentAsSameUserTypeException
	 * @throws UserNotFoundException
	 * @throws UserTypeNotValidException
	 * @throws SessionNotFoundException
	 * @throws DuplicateSessionException
	 * @throws PresentAsOtherUserTypeException
	 * @throws AdminAssignedException
	 * @throws ResourceNotFoundException
	 * @throws AppointmentNotFoundException
	 * @throws DuplicateAppointmentException
	 * @throws DuplicateResourceException
	 * @throws InvalidPromotionException 
	 */
	public static void main(String[] args)
			throws PresentAsSameUserTypeException, UserNotFoundException,
			UserTypeNotValidException, SessionNotFoundException,
			DuplicateSessionException, PresentAsOtherUserTypeException,
			AdminAssignedException, ResourceNotFoundException,
			AttendeeNotFoundException, FacilitatorNotFoundException,
			AppointmentNotFoundException, DuplicateAppointmentException,
			DuplicateResourceException, InvalidPromotionException {
		jafferTest();
		//DBTest();
		
	}

	public static void DBTest() throws AppointmentNotFoundException,
			ResourceNotFoundException, SessionNotFoundException,
			UserNotFoundException, PresentAsOtherUserTypeException {
		
	}
	

//	public static void sanjanaTest() throws DuplicateSessionException,
//			SessionNotFoundException, PresentAsOtherUserTypeException,
//			PresentAsSameUserTypeException, UserNotFoundException,
//			AdminAssignedException, ResourceNotFoundException,
//			DuplicateAppointmentException, DuplicateResourceException {
//		EasyShareServices service = ServicesMapper
//				.getEasyShareServicesProxyInstance();
//
//		User user1 = new User();
//		User user2 = new User();
//		User user3 = new User();
//		User user4 = new User();
//		User userRef, facilRef;
//		/*
//		 * user1.setEmail("admin1@cgi.com"); user1.setEmpid(1000);
//		 * user1.setName("Admin"); user4.setEmail("admin2@cgi.com");
//		 * user4.setEmpid(1200); user4.setName("Admin2");
//		 * user2.setEmail("jaffer@cgi.com"); user2.setEmpid(1500);
//		 * user2.setName("Jaffer"); user3.setEmail("safiya@cgi.com");
//		 * user3.setEmpid(2500); user3.setName("safiya");
//		 */
//		service.designateUser("", UserType.ADMIN);
//		service.designateUser("", UserType.ADMIN);
//		service.designateUser("", UserType.FACILITATOR);
//
//		userRef = service.getUser("", UserType.ADMIN);
//
//		Integer sessionId1 = service.createSession("advanced Hibernate");
//		Session session1 = service.getSession(sessionId1);
//		service.addAppointment(sessionId1, "17/01/2011", "1600", "1800");
//		service.addAppointment(sessionId1, "18/01/2011", "1600", "1800");
//		service.assignAdmin(sessionId1, "");
//		service.addFacilitator(sessionId1, "");
//		service.addMessage(sessionId1, "hello", "welcome");
//		service.addMessage(sessionId1, "babye", "good night!!");
//		List<Message> mlist = service.getMessages(sessionId1);
//		service.addResource(sessionId1, "resourceName1", "url1");
//		service.addResource(sessionId1, "resourceName2", "url2");
//
//		service.addAttendee(sessionId1, "");
//		Set<Resource> rset = service.getResources(sessionId1);
//
//		// System.out.println(session1);
//		// System.out.println(mlist);
//		// System.out.println(rset);
//		service.removeResource(sessionId1, 2);
//		// System.out.println(rset);
//		// service.removeFacilitator(sessionId1, 1500);
//		// service.removeAttendee(sessionId1, 2500);
//		Integer sessionId2 = service.createSession("Advanced Java");
//		service.assignAdmin(sessionId2, "");
//		service.addAppointment(sessionId2, "20/01/2011", "1500", "1700");
//		service.addAppointment(sessionId2, "21/01/2011", "1500", "1700");
//		service.addAppointment(sessionId2, "23/01/2011", "1500", "1700");
//		service.addFacilitator(sessionId2, "");
//		// System.out.println(session1);
//		Map<UserType, Set<Session>> sessions = service.getMySessions("");
//		System.out.println(sessions);
//	}

	public static void jafferTest() throws PresentAsSameUserTypeException,
			UserNotFoundException, UserTypeNotValidException,
			SessionNotFoundException, DuplicateSessionException,
			PresentAsOtherUserTypeException,

			AdminAssignedException, ResourceNotFoundException,
			AttendeeNotFoundException, FacilitatorNotFoundException,
			AppointmentNotFoundException, DuplicateAppointmentException,
			DuplicateResourceException, InvalidPromotionException {
		Integer sessionId = null, sessionId2 = null;
		Map<UserType, Set<Session>> sessions = null;
		UserIntegration uint = ServicesMapper.getUserIntegrationProxyInstance();
		EasyShareServices easy = ServicesMapper
				.getEasyShareServicesProxyInstance();
		easy.designateUser("anupama.charles", UserType.ADMIN);
		easy.designateUser("surabhi", UserType.ADMIN);
		easy.designateUser("jaffer.shah", UserType.FACILITATOR);
		easy.designateUser("nishi.kanth", UserType.FACILITATOR);
		easy.designateUser("shamim.aziz", UserType.FACILITATOR);
		SeparationOfUserConcerns sp = ServicesMapper
				.getSeparationOfUserConcernsProxyInstance();

		// create session
		ServiceDef sd = new ServiceDef();
		sd.setServiceName("createSession");
		Set<String> elig = new HashSet<String>();
		elig.add("ADMIN");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.FALSE);

		// ADD FACILITATOR
		ServiceDef sd1 = new ServiceDef();
		sd1.setServiceName("addFacilitator");
		Set<String> elig1 = new HashSet<String>();
		elig1.add("ADMIN");
		sd1.setEligUserTypes(elig1);
		sd1.setSessionRelationExists(Boolean.TRUE);

		// REMOVE FACILITATOR
		ServiceDef sd10 = new ServiceDef();
		sd10.setServiceName("removeFacilitator");
		Set<String> elig10 = new HashSet<String>();
		elig10.add("ADMIN");
		sd10.setEligUserTypes(elig10);
		sd10.setSessionRelationExists(Boolean.TRUE);

		// ADD APPOINTMENT
		ServiceDef sd6 = new ServiceDef();
		sd6.setServiceName("addAppointment");
		Set<String> elig6 = new HashSet<String>();
		elig6.add("ADMIN");
		sd6.setEligUserTypes(elig6);
		sd6.setSessionRelationExists(Boolean.TRUE);

		// REMOVE APPOINTMENT
		ServiceDef sd11 = new ServiceDef();
		sd11.setServiceName("removeAppointment");
		Set<String> elig11 = new HashSet<String>();
		elig11.add("ADMIN");
		sd11.setEligUserTypes(elig11);
		sd11.setSessionRelationExists(Boolean.TRUE);

		// ADD RESOURCE
		ServiceDef sd2 = new ServiceDef();
		sd2.setServiceName("addResource");
		Set<String> elig2 = new HashSet<String>();
		elig2.add("FACILITATOR");
		sd2.setEligUserTypes(elig2);
		sd2.setSessionRelationExists(Boolean.TRUE);

		// ADD MESSAGE
		ServiceDef sd3 = new ServiceDef();
		sd3.setServiceName("addMessage");
		Set<String> elig3 = new HashSet<String>();
		elig3.add("FACILITATOR");
		elig3.add("ATTENDEE");
		sd3.setEligUserTypes(elig3);
		sd3.setSessionRelationExists(Boolean.TRUE);

		// GET MESSAGE
		ServiceDef sd4 = new ServiceDef();
		sd4.setServiceName("getMessages");
		Set<String> elig4 = new HashSet<String>();
		elig4.add("ALL");
		sd4.setEligUserTypes(elig4);
		sd4.setSessionRelationExists(Boolean.TRUE);

		// REMOVE RESOURCE
		ServiceDef sd5 = new ServiceDef();
		sd5.setServiceName("removeResource");
		Set<String> elig5 = new HashSet<String>();
		elig5.add("FACILITATOR");
		sd5.setEligUserTypes(elig5);
		sd5.setSessionRelationExists(Boolean.TRUE);

		// REPLACE ADMIN
		ServiceDef sd7 = new ServiceDef();
		sd7.setServiceName("replaceAdmin");
		Set<String> elig7 = new HashSet<String>();
		elig7.add("ADMIN");
		sd7.setEligUserTypes(elig7);
		sd7.setSessionRelationExists(Boolean.TRUE);

		// ADD ATTENDEE
		ServiceDef sd8 = new ServiceDef();
		sd8.setServiceName("addAttendee");
		Set<String> elig8 = new HashSet<String>();
		elig8.add("ADMIN");
		sd8.setEligUserTypes(elig8);
		sd8.setSessionRelationExists(Boolean.TRUE);

		// REMOVE ATTENDEE
		ServiceDef sd9 = new ServiceDef();
		sd9.setServiceName("removeAttendee");
		Set<String> elig9 = new HashSet<String>();
		elig9.add("ADMIN");
		sd9.setEligUserTypes(elig9);
		sd9.setSessionRelationExists(Boolean.TRUE);

		// GET MY SESSIONS
		ServiceDef sd12 = new ServiceDef();
		sd12.setServiceName("getMySessions");
		Set<String> elig12 = new HashSet<String>();
		elig12.add("ALL");
		sd12.setEligUserTypes(elig12);
		sd12.setSessionRelationExists(Boolean.FALSE);

		// GET RESOURCES
		ServiceDef sd13 = new ServiceDef();
		sd12.setServiceName("getResources");
		Set<String> elig13 = new HashSet<String>();
		elig13.add("FACILITATOR");
		elig13.add("ATTENDEE");
		sd13.setEligUserTypes(elig13);
		sd13.setSessionRelationExists(Boolean.FALSE);
		// INVOCATIONS
//		if (sp.isServReqValid(sd, "anupama.charles", null)) {
//			sessionId = easy.createSession("Core java","basics of java");
//			easy.assignAdmin(sessionId, "anupama.charles");
//			// easy.assignAdmin(sessionId, "anupama.charles");
//
//		}
//		if (sp.isServReqValid(sd, "surabhi", null)) {
//			sessionId2 = easy.createSession("Advanced Hibernate","complicated");
//			easy.assignAdmin(sessionId2, "surabhi");
//
//		}
//		if (sp.isServReqValid(sd1, "anupama.charles", sessionId)) {
//
//			easy.addFacilitator(sessionId, "jaffer.shah");
//			easy.addFacilitator(sessionId, "nishi.kanth");
//			//easy.addFacilitator(sessionId, "jaffer.shah");
//		}
//
//		if (sp.isServReqValid(sd1, "surabhi", sessionId2)) {
//			easy.addFacilitator(sessionId2, "jaffer.shah");
//			// easy.addFacilitator(sessionId2, "jaffer.shah");
//		}
//
//		if (sp.isServReqValid(sd6, "anupama.charles", sessionId)) {
//
//			easy.addAppointment(sessionId, "17/01/2011", "1500", "1800","Ecity");
//			easy.addAppointment(sessionId, "18/01/2011", "1500", "1800","Ecity");
//			//easy.addAppointment(sessionId, "17/01/2011","1500","1800");
//		}
//		if (sp.isServReqValid(sd8, "anupama.charles", sessionId)) {
//
//			easy.addAttendee(sessionId, "sanjana.bayya");
//			easy.addAttendee(sessionId, "safiya.fathima");
//			easy.addAttendee(sessionId, "shamim.aziz");
//			//easy.addAttendee(sessionId, "sanjana.bayya");
//		}

		if (sp.isServReqValid(sd2, "jaffer.shah", sessionId)) {

			easy.addResource(sessionId, "resourceName1", "url1");
			easy.addResource(sessionId, "resourceName2", "url2");
			//easy.addResource(sessionId, "resourceName1", "url1");
		}

//		if (sp.isServReqValid(sd3, "jaffer.shah", sessionId)) {
//			easy.addMessage(sessionId, "subject1", "text1","17/01/2011","1830","jaffer");
//		}
//
//		if (sp.isServReqValid(sd4, "shamim.aziz", sessionId)) {
//
//			System.out.println("Messages:" + easy.getMessages(sessionId));
//		}
//
//		if (sp.isServReqValid(sd5, "jaffer.shah", sessionId)) {
//
//			easy.removeResource(sessionId, 1);
//			//easy.removeResource(sessionId, 1);
//		}
//
//		if (sp.isServReqValid(sd9, "anupama.charles", sessionId)) {
//
//			//easy.removeAttendee(sessionId, "sanjana.bayya");
//		}
//
//		if (sp.isServReqValid(sd10, "anupama.charles", sessionId)) {
//
//			//easy.removeFacilitator(sessionId, "nishi.kanth");
//		}
//
//		if (sp.isServReqValid(sd11, "anupama.charles", sessionId)) {
//
//			//easy.removeAppointment(sessionId, 2);
//			// easy.removeAppointment(sessionId,2);
//		}
//		if (sp.isServReqValid(sd12, "jaffer.shah", sessionId)) {
//
//			sessions = easy.getMySessions("jaffer.shah");
//		}
//
//		if (sp.isServReqValid(sd13, "jaffer.shah", sessionId)) {
//
//			Set<Resource> resources = easy.getResources(sessionId);
//			System.out.println("Resources:" + resources);
//		}
//
//		Session session = easy.getSession(sessionId);
//		Session session2 = easy.getSession(sessionId2);
//		System.out.println(session);
//		System.out.println(session2);
//		System.out.println("my sessions:" + sessions);
	}
}
