package com.cgi.open.persist.proxy;

import java.util.HashSet;
import java.util.Set;

import com.cgi.open.easyshare.AppointmentNotAvailableException;
import com.cgi.open.easyshare.SessionNotAvailableException;
import com.cgi.open.easyshare.model.Appointment;
import com.cgi.open.easyshare.model.Session;
import com.cgi.open.persist.PersistenceServices;

public class PersistenceServicesJavaProxy implements PersistenceServices {

	private static Set<Session> sessionsStore = new HashSet<Session>();
	
	public Session getSession(Integer sessionId)
			throws SessionNotAvailableException {
		for(Session session : sessionsStore) {
			if(session.getSessionId().equals(sessionId)) {
				return session;
			}
		}
		throw new SessionNotAvailableException("Session with given ID not available");
	}

	public Boolean checkForDuplicacy(Session anySession) {
		for(Session session : sessionsStore) {
			if(session.equals(anySession)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public Integer saveNewSession(Session newSession) {
		Integer maxId = 0;
		for(Session session : sessionsStore) {
			if(session.getSessionId() > maxId) {
				maxId = session.getSessionId();
			}
		}
		newSession.setSessionId(maxId + 1);//Ignoring the id (if already set), making it new
		sessionsStore.add(newSession);
		return maxId;
	}

	public Boolean deleteSession(Integer sessionId) {
		Session thisSession = null;
		try {
			thisSession = getSession(sessionId);
		}
		catch(SessionNotAvailableException ex) {
			return Boolean.FALSE;
		}
		sessionsStore.remove(thisSession);
		return Boolean.TRUE;
	}

	public Appointment getAppointment(Integer sessionId, Integer appointmentId)
			throws AppointmentNotAvailableException, SessionNotAvailableException {
		Session thisSession = getSession(sessionId);
		for(Appointment appointment : thisSession.getAppointments()) {
			if(appointment.getAppointmentId().equals(appointmentId)) {
				return appointment;
			}
		}
		throw new AppointmentNotAvailableException("Appointment with given ID for the session " 
				+ thisSession.getSessionId() + " not available");
	}

	public Boolean checkForDuplicacy(Integer sessionId,
			Appointment anyAppoinment) throws SessionNotAvailableException {
		Session thisSession = getSession(sessionId);
		for(Appointment appointment : thisSession.getAppointments()) {
			if(appointment.equals(anyAppoinment)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public Integer saveNewAppointment(Integer sessionId,
			Appointment newAppoinment) throws SessionNotAvailableException {
		Session thisSession = getSession(sessionId);
		Integer maxId = 0;
		for(Appointment appointment : thisSession.getAppointments()) {
			if(appointment.getAppointmentId() > maxId) {
				maxId = appointment.getAppointmentId();
			}
		}
		newAppoinment.setAppointmentId(maxId);//Ignoring the id (if already set), making it new
		thisSession.addAppointment(newAppoinment);
		return maxId;
	}
}
