package com.cgi.open.easyshare.proxy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cgi.open.ServicesMapper;
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
import com.cgi.open.easyshare.model.Appointment;
import com.cgi.open.easyshare.model.Message;
import com.cgi.open.easyshare.model.Resource;
import com.cgi.open.easyshare.model.Session;
import com.cgi.open.easyshare.model.User;
import com.cgi.open.easyshare.model.UserType;
import com.cgi.open.external.UserEntity;
import com.cgi.open.external.UserIntegration;
import com.cgi.open.persist.PersistenceServices;

public class EasyShareServicesProxy implements EasyShareServices {
	private final static String ILLEGAL_SERVICE_ACCESS = "";
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
	 * @throws UserNotFoundException
	 * @throws PresentAsOtherUserTypeException
	 */
	public Boolean addAttendees(Integer sessionId, Set<String> attendeesEmail)
			throws SessionNotFoundException,
			PresentAsOtherUserTypeException, UserNotFoundException,PresentAsSameUserTypeException
			 {
		Boolean flag = Boolean.TRUE;
		for (String userEmail : attendeesEmail) {
			flag = flag
					&& addAttendee(sessionId,userEmail);
		}
		return flag;
	}

	public Boolean assignAdmin(Integer sessionId, String email)
			throws SessionNotFoundException,
			PresentAsOtherUserTypeException, UserNotFoundException,
			AdminAssignedException, PresentAsSameUserTypeException {
		
		Session thisSession=persistent.getSession(sessionId);
		if(thisSession.getAdmin()!=null){
			throw new AdminAssignedException("Admin already assigned to this session");
		}
		User user=persistent.getUser(email, UserType.ADMIN);
		if(!persistent.checkForDuplicacy(sessionId,user,UserType.ADMIN)){
		return (persistent.addUserToSession(sessionId,user));
	}
		throw new PresentAsSameUserTypeException("The user is already an admin of this session");
	}

	public Boolean addAttendee(Integer sessionId, String email)
			throws SessionNotFoundException,
			PresentAsOtherUserTypeException, UserNotFoundException,
			PresentAsSameUserTypeException {
		UserIntegration uint = ServicesMapper
		.getUserIntegrationProxyInstance();
		User user = uint.getActualUser(email, UserEntity.EMAIL);
		user.setUserType(UserType.ATTENDEE);
		if(!persistent.checkForDuplicacy(sessionId,user,UserType.ATTENDEE)){	
		return (persistent.addUserToSession(sessionId,user));
	}
		throw new PresentAsSameUserTypeException("The user is already an attendee of this session");
	}

	public Boolean addFacilitator(Integer sessionId, String email)
			throws SessionNotFoundException,
			PresentAsOtherUserTypeException, UserNotFoundException,
			PresentAsSameUserTypeException {
		User user=persistent.getUser(email, UserType.FACILITATOR);
		if(!persistent.checkForDuplicacy(sessionId,user,UserType.FACILITATOR)){
		return (persistent.addUserToSession(sessionId,user));
		}
		throw new PresentAsSameUserTypeException("The user is already facilitating the session");
		
	}

	public Integer addMessage(Integer sessionId, String subject, String text,String date,String post_time,String post_by)
			throws SessionNotFoundException {
		Integer messageId;
		Message message = new Message();
		message.setSubject(subject);
		message.setText(text);
		message.setPostDate(date);
		message.setPostTime(post_time);
		message.setPostBy(post_by);
		messageId = persistent.saveNewMessage(sessionId, message);
		return messageId;
	}

	public Integer addResource(Integer sessionId, String resourceName,
			String url) throws SessionNotFoundException, DuplicateResourceException {
		Integer resourceId;
		Resource resource = new Resource();
		resource.setResourceName(resourceName);
		resource.setUrl(url);
		if(!persistent.checkForDuplicacy(sessionId,resource)){
		resourceId = persistent.saveNewResource(sessionId, resource);
		return resourceId;
		}
		throw new DuplicateResourceException("Resource is already added to the pool");
	}

	public Integer addAppointment(Integer sessionId, String date,
			String fromTime, String toTime,String location) throws SessionNotFoundException, DuplicateAppointmentException {
		Integer appointmentId;
		Appointment newAppointment = new Appointment();
		newAppointment.setDate(date);
		newAppointment.setFromTime(fromTime);
		newAppointment.setToTime(toTime);
		newAppointment.setLocation(location);
		if(!persistent.checkForDuplicacy(sessionId,newAppointment)){
		appointmentId = persistent
				.saveNewAppointment(sessionId, newAppointment);
		return appointmentId;
		}
		throw new DuplicateAppointmentException("Appointment with the given details is already added");
	}

	public boolean removeAppointment(Integer sessionId, Integer appointmentId)
			throws SessionNotFoundException,
			AppointmentNotFoundException {
		return (persistent.removeAppointment(sessionId, appointmentId));
	}

	public Integer createSession(String sessionName,String description)
			throws DuplicateSessionException {
		Session newSession = new Session();
		newSession.setSessionName(sessionName);
		newSession.setDiscription(description);
		if (persistent.checkForDuplicacy(newSession)) {
			throw new DuplicateSessionException(
					"Session with the given details already present");
		}
		Integer sessionId = persistent.saveNewSession(newSession);
		newSession.setSessionId(sessionId);
		return sessionId;
	}

	public Boolean designateUser(String email, UserType userType)
			throws PresentAsSameUserTypeException, UserNotFoundException, InvalidPromotionException {

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
			throws SessionNotFoundException {
		List<Message> messageList;
		Session thisSession = persistent.getSession(sessionId);
		messageList = thisSession.getMessages();
		return messageList;
	}

	public Set<Resource> getResources(Integer sessionId)
			throws SessionNotFoundException {
		Set<Resource> resources;
		Session thisSession = persistent.getSession(sessionId);
		resources = thisSession.getResourcePool();
		return resources;
	}

	public Session getSession(Integer sessionId)
			throws SessionNotFoundException {
		Session thisSession;
		thisSession = persistent.getSession(sessionId);
		return thisSession;

	}

	public User getUser(String email, UserType userType)
			throws UserNotFoundException {
		User user = persistent.getUser(email, userType);
		return user;
	}

	public Set<User> getUsers(Integer sessionId, UserType userType)
			throws SessionNotFoundException {
		return (persistent.getUsers(sessionId, userType));
	}

	public Boolean removeFacilitator(Integer sessionId, String email)
			throws SessionNotFoundException, UserNotFoundException {

		return (persistent.removeFacilitator(sessionId, email));
	}

	public Boolean removeResource(Integer sessionId, Integer resourceId)
			throws SessionNotFoundException, ResourceNotFoundException {
		return (persistent.removeResource(sessionId, resourceId));
	}

	public Boolean removeAttendee(Integer sessionId, String email)
			throws SessionNotFoundException, UserNotFoundException {
		return (persistent.removeAttendee(sessionId, email));
	}

	public Boolean replaceAdmin(Integer sessionId, String email)
			throws SessionNotFoundException,
			PresentAsOtherUserTypeException, UserNotFoundException {

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

	public Set<String> getAllUsersLight(Integer sessionId,UserType userType)
			throws SessionNotFoundException {
		Set<String> userEmails = persistent.getSessionUserEmails(sessionId,userType);
		return userEmails;
	}
}
