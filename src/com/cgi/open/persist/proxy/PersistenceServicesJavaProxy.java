package com.cgi.open.persist.proxy;

import java.util.HashSet;
import java.util.Set;

import com.cgi.open.easyshare.AppointmentNotAvailableException;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.SessionNotAvailableException;
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

	public Boolean checkForDuplicacy(Session anySession) {
		for (Session session : sessionsStore) {
			if (session.equals(anySession)) {
				return Boolean.TRUE;
			}
		}
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
		return (maxId+1);
	}

	public Boolean deleteSession(Integer sessionId) {
		Session thisSession = null;
		try {
			thisSession = getSession(sessionId);
		} catch (SessionNotAvailableException ex) {
			return Boolean.FALSE;
		}
		sessionsStore.remove(thisSession);
		return Boolean.TRUE;
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

	public Integer saveNewAppointment(Integer sessionId,
			Appointment newAppoinment) throws SessionNotAvailableException {
		Session thisSession = getSession(sessionId);
		Integer maxId = 0;
		for (Appointment appointment : thisSession.getAppointments()) {
			if (appointment.getAppointmentId() > maxId) {
				maxId = appointment.getAppointmentId();
			}
		}
		newAppoinment.setAppointmentId(maxId+1);// Ignoring the id (if already
												// set), making it new
		thisSession.addAppointment(newAppoinment);
		return maxId+1;
	}
	
	public Integer saveNewMessage(Integer sessionId,
			Message newMessage) throws SessionNotAvailableException {
		Session thisSession = getSession(sessionId);
		Integer maxId = 0;
		for (Message message : thisSession.getMessages()) {
			if (message.getMessageId() > maxId) {
				maxId = message.getMessageId();
			}
		}
		newMessage.setMessageId(maxId+1);// Ignoring the id (if already
												// set), making it new
		thisSession.addMessage(newMessage);
		return maxId+1;
	}

	public Integer saveNewResource(Integer sessionId,
			Resource newResource) throws SessionNotAvailableException {
		Session thisSession = getSession(sessionId);
		Integer maxId = 0;
		for (Resource resource : thisSession.getResourcePool()) {
			if (resource.getResourceId() > maxId) {
				maxId = resource.getResourceId();
			}
		}
		newResource.setResourceId(maxId+1);// Ignoring the id (if already
												// set), making it new
		thisSession.addResource(newResource);
		return maxId+1;
	}
	public Boolean addAdmin(Integer sessionId, User admin)
			throws SessionNotAvailableException,
			PresentAsOtherUserTypeException {
		Session thisSession = getSession(sessionId);

		if (thisSession.getAdmin() == null) {
			admin.setUserType(UserType.ADMIN);

			thisSession.setAdmin(admin);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean addFacilitator(Integer sessionId, User facilitator)
			throws SessionNotAvailableException,
			PresentAsOtherUserTypeException {
		Session thisSession = getSession(sessionId);

		if (thisSession.getFacilitators() == null) {
			thisSession.setFacilitators(new HashSet<User>());
		}
		facilitator.setUserType(UserType.FACILITATOR);
		thisSession.addFacilitator(facilitator);
		return Boolean.TRUE;

	}

	public Boolean addNewAttendee(Integer sessionId, User attendee)
			throws SessionNotAvailableException {

		Session thisSession = getSession(sessionId);
		if (thisSession.getAttendees() == null) {
			thisSession.setAttendees(new HashSet<User>());
		}
		attendee.setUserType(UserType.ATTENDEE);
		thisSession.getAttendees().add(attendee);
		return Boolean.TRUE;

	}

	public Boolean checkForDuplicacy(User anyUser, UserType userType) {
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
		//Already present as same user type --START
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
		//Already present as same user type --STOP
		return Boolean.FALSE;

	}

	public Set<User> getUsers(Integer sessionId, UserType userType)
			throws SessionNotAvailableException {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean promoteUser(User anyUser, UserType userType) {
		if (userType.equals(UserType.ADMIN)
				|| userType.equals(UserType.FACILITATOR)) {
			anyUser.setUserType(userType);
			usersStore.add(anyUser);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean removeAdmin(Integer sessionId, Integer userEmpId)
			throws SessionNotAvailableException {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean removeAdmin(Integer sessionId, String userEmailId)
			throws SessionNotAvailableException {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean removeAttendee(Integer sessionId, Integer userEmpId)
			throws SessionNotAvailableException {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean removeAttendee(Integer sessionId, String userEmailId)
			throws SessionNotAvailableException {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean removeFacilitator(Integer sessionId, Integer userEmpId)
			throws SessionNotAvailableException {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean removeFacilitator(Integer sessionId, String userEmailId)
			throws SessionNotAvailableException {
		// TODO Auto-generated method stub
		return null;
	}
}
