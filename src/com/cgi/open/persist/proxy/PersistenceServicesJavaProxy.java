package com.cgi.open.persist.proxy;

import java.util.HashSet;
import java.util.Set;

import com.cgi.open.easyshare.AppointmentNotAvailableException;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.SessionNotAvailableException;
import com.cgi.open.easyshare.model.Appointment;
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
		return maxId;
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
		newAppoinment.setAppointmentId(maxId);// Ignoring the id (if already
												// set), making it new
		thisSession.addAppointment(newAppoinment);
		return maxId;
	}

	public Boolean addAdmin(Integer sessionId, User admin)
			throws SessionNotAvailableException,
			PresentAsOtherUserTypeException {
		Session thisSession = getSession(sessionId);
		if (thisSession.getAdmin() == null
				&& checkForDuplicacy(admin, UserType.ADMIN)
				&& checkForDuplicacy(sessionId, admin, UserType.ADMIN)) {
			thisSession.setAdmin(admin);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean addFacilitator(Integer sessionId, User facilitator)
			throws SessionNotAvailableException,
			PresentAsOtherUserTypeException {
		Session thisSession = getSession(sessionId);
		if (checkForDuplicacy(facilitator, UserType.FACILITATOR)
				&& checkForDuplicacy(sessionId, facilitator,
						UserType.FACILITATOR)) {
			if (thisSession.getFacilitators() == null) {
				thisSession.setFacilitators(new HashSet<User>());
			}
			thisSession.addFacilitator(facilitator);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean addNewAttendee(Integer sessionId, User attendee)
			throws SessionNotAvailableException {
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
