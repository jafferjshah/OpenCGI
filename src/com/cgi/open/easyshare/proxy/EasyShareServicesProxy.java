package com.cgi.open.easyshare.proxy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.AdminAssignedException;
import com.cgi.open.easyshare.AppointmentNotAvailableException;
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
import com.cgi.open.easyshare.model.Appointment;
import com.cgi.open.easyshare.model.Message;
import com.cgi.open.easyshare.model.Resource;
import com.cgi.open.easyshare.model.Session;
import com.cgi.open.easyshare.model.User;
import com.cgi.open.easyshare.model.UserType;
import com.cgi.open.external.UserIntegration;
import com.cgi.open.persist.PersistenceServices;

public class EasyShareServicesProxy implements EasyShareServices {

	private PersistenceServices persistent = ServicesMapper
			.getPersistenceServicesProxyInstance();
	private UserIntegration uint = ServicesMapper
			.getUserIntegrationProxyInstance();

	/**
	 * This method will be called only at the first time. For altering, the
	 * attendees set, call add/remove attendee services.
	 * 
	 * @throws AdminAssignedException
	 * @throws AttendeeAlreadyRegisteredException
	 * @throws UserNotAvailableException
	 * @throws PresentAsOtherUserTypeException
	 */
	public Boolean addAttendees(Integer sessionId, Set<String> attendeesEmail)
			throws SessionNotAvailableException,
			PresentAsOtherUserTypeException, UserNotAvailableException,
			AttendeeAlreadyRegisteredException, AdminAssignedException {
		Boolean flag = Boolean.TRUE;
		for (String userEmail : attendeesEmail) {
			flag = flag
					&& persistent.addUserToSession(sessionId, userEmail,
							UserType.ATTENDEE);
		}
		return flag;
	}

	public Boolean assignAdmin(Integer sessionId, String email)
			throws SessionNotAvailableException,
			PresentAsOtherUserTypeException, UserNotAvailableException,
			AttendeeAlreadyRegisteredException, AdminAssignedException {
		return (persistent.addUserToSession(sessionId, email, UserType.ADMIN));
	}

	public Boolean addAttendee(Integer sessionId, String email)
			throws SessionNotAvailableException,
			PresentAsOtherUserTypeException, UserNotAvailableException,
			AttendeeAlreadyRegisteredException, AdminAssignedException {
		return (persistent.addUserToSession(sessionId, email,
				UserType.ATTENDEE));
	}

	public Boolean addFacilitator(Integer sessionId, String email)
			throws SessionNotAvailableException,
			PresentAsOtherUserTypeException, UserNotAvailableException,
			AttendeeAlreadyRegisteredException, AdminAssignedException {
		return (persistent.addUserToSession(sessionId, email,
				UserType.FACILITATOR));
	}

	public Integer addMessage(Integer sessionId, String subject, String text)
			throws SessionNotAvailableException {
		Integer messageId;
		Message message = new Message();
		message.setSubject(subject);
		message.setText(text);
		messageId = persistent.saveNewMessage(sessionId, message);
		return messageId;
	}

	public Integer addResource(Integer sessionId, String resourceName,
			String url) throws SessionNotAvailableException {
		Integer resourceId;
		Resource resource = new Resource();
		resource.setResourceName(resourceName);
		resource.setUrl(url);
		resourceId = persistent.saveNewResource(sessionId, resource);
		return resourceId;

	}

	public Integer addAppointment(Integer sessionId, String date,
			String fromTime, String toTime) throws SessionNotAvailableException {
		Integer appointmentId;
		Appointment newAppointment = new Appointment();
		newAppointment.setDate(date);
		newAppointment.setFromTime(fromTime);
		newAppointment.setToTime(toTime);
		appointmentId = persistent
				.saveNewAppointment(sessionId, newAppointment);
		return appointmentId;
	}

	public boolean removeAppointment(Integer sessionId, Integer appointmentId)
			throws SessionNotAvailableException,
			AppointmentNotAvailableException {
		return (persistent.removeAppointment(sessionId, appointmentId));
	}

	public Integer createSession(String sessionName)
			throws DuplicateSessionException {
		Session newSession = new Session();
		newSession.setSessionName(sessionName);
		if (persistent.checkForDuplicacy(newSession)) {
			throw new DuplicateSessionException(
					"Session with the given details already present");
		}
		Integer sessionId = persistent.saveNewSession(newSession);
		newSession.setSessionId(sessionId);
		return sessionId;
	}

	public Boolean designateUser(String email, UserType userType)
			throws PresentAsSameUserTypeException, UserNotAvailableException {

		if (persistent.checkForDuplicacy(email, userType)) {
			throw new PresentAsSameUserTypeException(
					"User already present in the store as same UserType");
		}

		return (persistent.promoteUser(email, userType));
	}

	public Set<Session> getAllSessions() {
		Set<Session> sessions = null;
		sessions = persistent.getSessionsStore();
		return sessions;
	}

	public List<Message> getMessages(Integer sessionId)
			throws SessionNotAvailableException {
		List<Message> messageList;
		Session thisSession = persistent.getSession(sessionId);
		messageList = thisSession.getMessages();
		return messageList;
	}

	public Set<Resource> getResources(Integer sessionId)
			throws SessionNotAvailableException {
		Set<Resource> resources;
		Session thisSession = persistent.getSession(sessionId);
		resources = thisSession.getResourcePool();
		return resources;
	}

	public Session getSession(Integer sessionId)
			throws SessionNotAvailableException {
		Session thisSession;
		thisSession = persistent.getSession(sessionId);
		return thisSession;

	}

	public User getUser(String email, UserType userType)
			throws UserNotAvailableException {
		User user = persistent.getUser(email, userType);
		return user;
	}

	public Set<User> getUsers(Integer sessionId, UserType userType)
			throws SessionNotAvailableException {
		return (persistent.getUsers(sessionId, userType));
	}

	public Boolean removeFacilitator(Integer sessionId, Integer facilitatorId)
			throws SessionNotAvailableException, FacilitatorNotFoundException {

		return (persistent.removeFacilitator(sessionId, facilitatorId));
	}

	public Boolean removeResource(Integer sessionId, Integer resourceId)
			throws SessionNotAvailableException, ResourceNotFoundException {
		return (persistent.removeResource(sessionId, resourceId));
	}

	public Boolean removeAttendee(Integer sessionId, Integer attendeeId)
			throws SessionNotAvailableException, AttendeeNotFoundException {
		return (persistent.removeAttendee(sessionId, attendeeId));
	}

	public Boolean replaceAdmin(Integer sessionId, String email)
			throws SessionNotAvailableException,
			PresentAsOtherUserTypeException, UserNotAvailableException {

		User user = persistent.getUser(email, UserType.ADMIN);
		return (persistent.replaceAdmin(sessionId, user));

	}

	public Set<Appointment> saveAppointments(Integer sessionId,
			Set<Appointment> appointments) {
		return null;

	}

	/**
	 * 
	 * Coded by Sanjana
	 */
	public Map<UserType, Set<Session>> getMySessions(String email) {
		Map<UserType, Set<Session>> mySessions = new HashMap<UserType, Set<Session>>();
		Set<Session> adminSessions = new HashSet<Session>();
		Set<Session> facilitatorSessions = new HashSet<Session>();
		Set<Session> attendeeSessions = new HashSet<Session>();

		if (persistent.checkForDuplicacy(email, UserType.ADMIN)) {
			for (Session thisSession : getAllSessions()) {
				if (thisSession.getAdmin().getEmail().equals(email)) {
					adminSessions.add(thisSession);
				}
			}
		}
		if (persistent.checkForDuplicacy(email, UserType.FACILITATOR)) {
			for (Session thisSession : getAllSessions()) {
				for (User thisFacilitator : thisSession.getFacilitators()) {
					if (thisFacilitator.getEmail().equals(email)) {
						facilitatorSessions.add(thisSession);
					}
				}
			}

		}
		if (persistent.checkForDuplicacy(email, UserType.ATTENDEE)) {
			for (Session thisSession : getAllSessions()) {
				for (User thisAttendee : thisSession.getAttendees()) {
					if (thisAttendee.getEmail().equals(email)) {
						attendeeSessions.add(thisSession);
					}
				}
			}
		}

		mySessions.put(UserType.ADMIN, adminSessions);
		mySessions.put(UserType.FACILITATOR, facilitatorSessions);
		mySessions.put(UserType.ATTENDEE, attendeeSessions);

		return mySessions;
	}

	public Set<String> getAllUsersLight(Integer sessionId)
			throws SessionNotAvailableException {
		Set<String> userEmails = persistent.getSessionUserEmails(sessionId);
		return userEmails;
	}
}
