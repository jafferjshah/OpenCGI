package com.cgi.open.persist.proxy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.cgi.open.easyshare.AdminAssignedException;
import com.cgi.open.easyshare.AppointmentNotAvailableException;
import com.cgi.open.easyshare.AttendeeAlreadyRegisteredException;
import com.cgi.open.easyshare.AttendeeNotFoundException;
import com.cgi.open.easyshare.FacilitatorNotFoundException;
import com.cgi.open.easyshare.MessageNotFoundException;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.ResourceNotFoundException;
import com.cgi.open.easyshare.SessionNotAvailableException;
import com.cgi.open.easyshare.UserNotAvailableException;
import com.cgi.open.easyshare.model.Appointment;
import com.cgi.open.easyshare.model.Message;
import com.cgi.open.easyshare.model.Resource;
import com.cgi.open.easyshare.model.Session;
import com.cgi.open.easyshare.model.User;
import com.cgi.open.easyshare.model.UserType;
import com.cgi.open.persist.PersistenceServices;

public class PersistenceServicesJavaProxy implements PersistenceServices {

	private static Set<Session> sessionsStore = new HashSet<Session>();

	private static Set<User> usersStore = new HashSet<User>();

	public Set<Session> getSessionsStore() {
		return sessionsStore;
	}

	public Set<User> getUsersStore() {
		return usersStore;
	}

	public User getUser(Integer userId, UserType userType)
			throws UserNotAvailableException {
		Set<User> users = getUsersStore();
		for (User user : users) {
			if (user.getEmpid().equals(userId)) {
				if (user.getUserType().equals(userType)) {
					return user;
				}
			}
		}
		throw new UserNotAvailableException("User with given ID not available");
	}

	public Set<User> getUsers(Integer sessionId, UserType userType)
			throws SessionNotAvailableException {
		Session thisSession = getSession(sessionId);
		Set<User> users = null;
		if (userType.equals(UserType.ADMIN)) {
			users.add(thisSession.getAdmin());
		} else if (userType.equals(UserType.ATTENDEE)) {
			users.addAll(thisSession.getAttendees());
		} else if (userType.equals(UserType.FACILITATOR)) {
			users.addAll(thisSession.getFacilitators());
		}
		return users;

	}

	public Session getSession(Integer sessionId)
			throws SessionNotAvailableException {
		for (Session session : sessionsStore) {
			if (session.getSessionId().equals(sessionId)) {
				return session;
			}
		}
		throw new SessionNotAvailableException(
				"Session with given ID not available");
	}

	public Appointment getAppointment(Integer sessionId, Integer appointmentId)
			throws AppointmentNotAvailableException,
			SessionNotAvailableException {
		Session thisSession = getSession(sessionId);
		for (Appointment appointment : thisSession.getAppointments()) {
			if (appointment.getAppointmentId().equals(appointmentId)) {
				return appointment;
			}
		}
		throw new AppointmentNotAvailableException(
				"Appointment with given ID for the session "
						+ thisSession.getSessionId() + " not available");
	}

	public User getAttendee(Integer sessionId, Integer id)
			throws SessionNotAvailableException, AttendeeNotFoundException {
		Session thisSession = getSession(sessionId);
		User result = null;
		Set<User> attendees = thisSession.getAttendees();
		for (Iterator<User> it = attendees.iterator(); it.hasNext();) {
			result = it.next();
			if (result.getEmpid().equals(id)) {
				return result;
			}
		}
		throw new AttendeeNotFoundException("Attendee not found");
		
	}

	/**
	 * Gets Attendee from the set based on attendee's email id.
	 * 
	 * @param email
	 *            id
	 * @return
	 * @throws SessionNotAvailableException
	 * @throws AttendeeNotFoundException 
	 */
	public User getAttendee(Integer sessionId, String emailId)
			throws SessionNotAvailableException, AttendeeNotFoundException {
		Session thisSession = getSession(sessionId);
		User result = null;
		Set<User> attendees = thisSession.getAttendees();
		for (Iterator<User> it = attendees.iterator(); it.hasNext();) {
			result = it.next();
			if (result.getEmail().equals(emailId)) {
				return result;
			}
		}
		throw new AttendeeNotFoundException("Attendee not found");
	}

	public User getFacilitator(Integer sessionId, Integer id)
			throws SessionNotAvailableException, FacilitatorNotFoundException {
		Session thisSession = getSession(sessionId);
		User result = null;
		Set<User> facilitators = thisSession.getFacilitators();
		for (Iterator<User> it = facilitators.iterator(); it.hasNext();) {
			result = it.next();
			if (result.getEmpid().equals(id)) {
				return result;
			}
		}
		throw new FacilitatorNotFoundException("Facilitator not found");
	}

	public User getFacilitator(Integer sessionId, String emailId)
			throws SessionNotAvailableException, FacilitatorNotFoundException {
		Session thisSession = getSession(sessionId);
		User result = null;
		Set<User> facilitators = thisSession.getFacilitators();
		for (Iterator<User> it = facilitators.iterator(); it.hasNext();) {
			result = it.next();
			if (result.getEmpid().equals(emailId)) {
				return result;
			}
		}
		throw new FacilitatorNotFoundException("Facilitator not found");
		
	}

	/**
	 * This method returns resource corresponding to a sessionsearching by its
	 * id
	 * 
	 * @param name
	 * @return
	 * @throws SessionNotAvailableException
	 * @throws ResourceNotFoundException 
	 */
	public Resource getResource(Integer sessionId, Integer id)
			throws SessionNotAvailableException, ResourceNotFoundException {
		Session thisSession = getSession(sessionId);
		Resource result = null;
		for (Iterator<Resource> it = thisSession.getResourcePool().iterator(); it
				.hasNext();) {
			result = it.next();
			if (result.getResourceId() == id) {
				return result;
			}
		}
		throw new ResourceNotFoundException("Resource not found");
	}

	/**
	 * This method returns message corresponding to a session searching by its
	 * id
	 * 
	 * @param name
	 * @return
	 * @throws SessionNotAvailableException
	 * @throws MessageNotFoundException 
	 */
	public Message getMessage(Integer sessionId, Integer id)
			throws SessionNotAvailableException, MessageNotFoundException {
		Session thisSession = getSession(sessionId);
		Message result = null;
		for (Iterator<Message> it = thisSession.getMessages().iterator(); it
				.hasNext();) {
			result = it.next();
			if (result.getMessageId().equals(id)) {
				return result;
			}
		}
		throw new MessageNotFoundException("Message Not Found");
		
	}

	public Boolean checkForDuplicacy(Session anySession) {
		for (Session session : sessionsStore) {
			if (session.equals(anySession)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public Boolean checkForDuplicacy(Integer sessionId,
			Appointment anyAppoinment) throws SessionNotAvailableException {
		Session thisSession = getSession(sessionId);
		for (Appointment appointment : thisSession.getAppointments()) {
			if (appointment.equals(anyAppoinment)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public Boolean checkForDuplicacy(Integer userId, UserType userType) {
		User anyUser=new User();
		anyUser.setEmpid(userId);
		anyUser.setUserType(userType);
		for (User user : usersStore) {
			if (user.equals(anyUser)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public Boolean checkForDuplicacy(Integer sessionId, User anyUser,
			UserType userType) throws SessionNotAvailableException,
			PresentAsOtherUserTypeException {

		Session thisSession = getSession(sessionId);
		// ADMIN = ATTENDEE, FACILITATOR (alone) -- START
		if (userType.equals(UserType.ADMIN)) {
			for (User user : thisSession.getFacilitators()) {
				if (user.equals(anyUser)) {
					throw new PresentAsOtherUserTypeException(
							"The user cant be an admin, because the user is already a facilitator for this session!");
				}
			}
		}
		if (userType.equals(UserType.ATTENDEE)) {
			for (User user : thisSession.getFacilitators()) {
				if (user.equals(anyUser)) {
					throw new PresentAsOtherUserTypeException(
							"The user cant be an attendee, because the user is already a facilitator for this session!");
				}
			}
		}
		if (userType.equals(UserType.FACILITATOR)) {
			User admin;
			if ((admin = thisSession.getAdmin()) != null
					&& admin.equals(anyUser)) {
				throw new PresentAsOtherUserTypeException(
						"The user cant be a facilitator, because the user is already an admin for this session!");
			}
			for (User user : thisSession.getAttendees()) {
				if (user.equals(anyUser)) {
					throw new PresentAsOtherUserTypeException(
							"The user cant be a facilitator, because the user is already an attendee for this session!");
				}
			}
		}
		// ADMIN = ATTENDEE, FACILITATOR (alone) -- STOP
		// Already present as same user type --START
		if (userType.equals(UserType.ADMIN)) {
			User admin;
			if ((admin = thisSession.getAdmin()) != null
					&& admin.equals(anyUser)) {
				return Boolean.TRUE;
			}
		}
		if (userType.equals(UserType.ATTENDEE)) {
			for (User user : thisSession.getAttendees()) {
				if (user.equals(anyUser)) {
					return Boolean.TRUE;
				}
			}
		}
		if (userType.equals(UserType.FACILITATOR)) {
			for (User user : thisSession.getFacilitators()) {
				if (user.equals(anyUser)) {
					return Boolean.TRUE;
				}
			}
		}
		// Already present as same user type --STOP
		return Boolean.FALSE;

	}

	public Integer saveNewSession(Session newSession) {
		Integer maxId = 0;
		for (Session session : sessionsStore) {
			if (session.getSessionId() > maxId) {
				maxId = session.getSessionId();
			}
		}
		newSession.setSessionId(maxId + 1);// Ignoring the id (if already set),
		// making it new
		sessionsStore.add(newSession);
		return (maxId + 1);
	}

	/**
	 * saves an appointment to the set of appointments of the session.
	 * 
	 * @param appointment
	 * @throws SessionNotAvailableException
	 */

	public Integer saveNewAppointment(Integer sessionId,
			Appointment newAppoinment) throws SessionNotAvailableException {
		Session thisSession = getSession(sessionId);
		Integer maxId = 0;
		for (Appointment appointment : thisSession.getAppointments()) {
			if (appointment.getAppointmentId() > maxId) {
				maxId = appointment.getAppointmentId();
			}
		}
		newAppoinment.setAppointmentId(maxId + 1);// Ignoring the id (if already
		// set), making it new
		thisSession.getAppointments().add(newAppoinment);
		return maxId + 1;
	}

	/**
	 *saves message to the list of messages of the session.
	 * 
	 * @param message
	 * @throws SessionNotAvailableException
	 */

	public Integer saveNewMessage(Integer sessionId, Message newMessage)
			throws SessionNotAvailableException {
		Session thisSession = getSession(sessionId);
		Integer maxId = 0;
		for (Message message : thisSession.getMessages()) {
			if (message.getMessageId() > maxId) {
				maxId = message.getMessageId();
			}
		}
		newMessage.setMessageId(maxId + 1);// Ignoring the id (if already
		// set), making it new
		thisSession.getMessages().add(newMessage);
		return maxId + 1;
	}

	/**
	 * This method adds resource to the resourcepool of the corresponding
	 * session
	 * 
	 * @param resource
	 * @throws SessionNotAvailableException
	 */

	public Integer saveNewResource(Integer sessionId, Resource newResource)
			throws SessionNotAvailableException {
		Session thisSession = getSession(sessionId);
		Integer maxId = 0;
		for (Resource resource : thisSession.getResourcePool()) {
			if (resource.getResourceId() > maxId) {
				maxId = resource.getResourceId();
			}
		}
		newResource.setResourceId(maxId + 1);// Ignoring the id (if already
		// set), making it new
		thisSession.getResourcePool().add(newResource);
		return maxId + 1;
	}

	public Boolean deleteSession(Integer sessionId)
			throws SessionNotAvailableException {
		Session thisSession;
		thisSession = getSession(sessionId);
		return (sessionsStore.remove(thisSession));

	}


	public Boolean addUserToSession(Integer sessionId, Integer userId,
			UserType userType) throws SessionNotAvailableException,
			PresentAsOtherUserTypeException, UserNotAvailableException,
			AttendeeAlreadyRegisteredException, AdminAssignedException {
		Session thisSession = getSession(sessionId);
		
		if (userType.equals(UserType.ATTENDEE)) {
			System.out.println("h1");
			for (User user1 : thisSession.getAttendees()) {
				if (user1.getEmpid() == userId) {
					System.out.println("h2");
					throw new AttendeeAlreadyRegisteredException(
							"the user has already registered for the session");
				}
			}
			User user = new User();
			user.setEmpid(userId);
			user.setEmail(userId + "@cgi.com");
			user.setUserType(UserType.ATTENDEE);
			return (thisSession.getAttendees().add(user));
		}
		if (userType.equals(UserType.FACILITATOR)) {
			User user = getUser(userId, userType);
			user.setUserType(UserType.FACILITATOR);
			return (thisSession.getFacilitators().add(user));
		}
		if (userType.equals(UserType.ADMIN)) {
			User user = getUser(userId, userType);
			if (thisSession.getAdmin() == null) {
				user.setUserType(UserType.ADMIN);
				thisSession.setAdmin(user);
				return Boolean.TRUE;
			} else
				throw new AdminAssignedException(
						"Admin is already assigned for the session");
		}
		return Boolean.FALSE;

	}

	public Boolean promoteUser(Integer userId, UserType userType) {
		if (userType.equals(UserType.ADMIN)
				|| userType.equals(UserType.FACILITATOR)) {
			User anyUser=new User();
			anyUser.setEmpid(userId);
			anyUser.setUserType(userType);
			usersStore.add(anyUser);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean removeAttendee(Integer sessionId, Integer userEmpId)
			throws SessionNotAvailableException, AttendeeNotFoundException {
		Session thisSession = getSession(sessionId);
		return (thisSession.getAttendees().remove(getAttendee(sessionId,
				userEmpId)));

	}

	public Boolean removeAttendee(Integer sessionId, String userEmailId)
			throws SessionNotAvailableException, AttendeeNotFoundException {
		Session thisSession = getSession(sessionId);
		return (thisSession.getAttendees().remove(getAttendee(sessionId,
				userEmailId)));

	}

	/**
	 * Gets Attendee from the set based on attendee's id.
	 * 
	 * @param id
	 * @return
	 * @throws SessionNotAvailableException
	 * @throws FacilitatorNotFoundException 
	 */

	public Boolean removeFacilitator(Integer sessionId, Integer facilitatorId)
			throws SessionNotAvailableException, FacilitatorNotFoundException {
		Session thisSession = getSession(sessionId);
		return (thisSession.getFacilitators().remove(getFacilitator(sessionId,
				facilitatorId)));
	}

	public Boolean removeFacilitator(Integer sessionId, String userEmailId)
			throws SessionNotAvailableException, FacilitatorNotFoundException {
		Session thisSession = getSession(sessionId);
		return (thisSession.getFacilitators().remove(getFacilitator(sessionId,
				userEmailId)));
	}

	public Boolean replaceAdmin(Integer sessionId, User user)
			throws SessionNotAvailableException,
			PresentAsOtherUserTypeException {
		Session thisSession = getSession(sessionId);
		user.setUserType(UserType.ADMIN);
		thisSession.setAdmin(user);
		return Boolean.TRUE;
	}

	public Boolean removeResource(Integer sessionId, Integer resourceId)
			throws SessionNotAvailableException, ResourceNotFoundException {

		Session thisSession = getSession(sessionId);
		return (thisSession.getResourcePool().remove(getResource(sessionId,
				resourceId)));

	}

	public boolean removeAppointment(Integer sessionId, Integer appointmentId)
			throws SessionNotAvailableException,
			AppointmentNotAvailableException {
		Session thisSession = getSession(sessionId);
		return (thisSession.getAppointments().remove(getAppointment(sessionId,
				appointmentId)));
	}

	

}
