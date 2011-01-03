package com.cgi.open.persist;

import java.util.Set;

import com.cgi.open.easyshare.AppointmentNotAvailableException;
import com.cgi.open.easyshare.SessionNotAvailableException;
import com.cgi.open.easyshare.model.Appointment;
import com.cgi.open.easyshare.model.Session;

public interface PersistenceServices {
	public Set<Session> getSessionsStore();
	public Session getSession(Integer sessionId) throws SessionNotAvailableException;
	public Boolean checkForDuplicacy(Session anySession);
	public Integer saveNewSession(Session newSession);
	public Boolean deleteSession(Integer sessionId);
	public Appointment getAppointment(Integer sessionId, Integer AppointmentId) 
	throws AppointmentNotAvailableException, SessionNotAvailableException;
	public Boolean checkForDuplicacy(Integer sessionId, Appointment anyAppoinment) throws SessionNotAvailableException;
	public Integer saveNewAppointment(Integer sessionId, Appointment anyAppoinment) throws SessionNotAvailableException;
}
