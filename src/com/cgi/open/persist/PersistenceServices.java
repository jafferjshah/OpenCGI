package com.cgi.open.persist;

import java.util.Set;

import com.cgi.open.easyshare.AppointmentNotAvailableException;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.SessionNotAvailableException;
import com.cgi.open.easyshare.model.Appointment;
import com.cgi.open.easyshare.model.Session;
import com.cgi.open.easyshare.model.User;
import com.cgi.open.easyshare.model.UserType;

public interface PersistenceServices {
	public Set<Session> getSessionsStore();

	public Session getSession(Integer sessionId)
			throws SessionNotAvailableException;

	public Boolean checkForDuplicacy(Session anySession);

	public Integer saveNewSession(Session newSession);

	public Boolean deleteSession(Integer sessionId);

	public Appointment getAppointment(Integer sessionId, Integer AppointmentId)
			throws AppointmentNotAvailableException,
			SessionNotAvailableException;

	public Boolean checkForDuplicacy(Integer sessionId,
			Appointment anyAppoinment) throws SessionNotAvailableException;

	public Integer saveNewAppointment(Integer sessionId,
			Appointment anyAppoinment) throws SessionNotAvailableException;

	public Boolean checkForDuplicacy(User anyUser, UserType userType);

	public Boolean promoteUser(User anyUser, UserType userType);

	public Boolean checkForDuplicacy(Integer sessionId, User anyUser,
			UserType userType) throws SessionNotAvailableException,
			PresentAsOtherUserTypeException;

	public Set<User> getUsers(Integer sessionId, UserType userType)
			throws SessionNotAvailableException;

	public Boolean addNewAttendee(Integer sessionId, User attendee)
			throws SessionNotAvailableException;

	public Boolean removeAttendee(Integer sessionId, Integer userEmpId)
			throws SessionNotAvailableException;

	public Boolean removeAttendee(Integer sessionId, String userEmailId)
			throws SessionNotAvailableException;

	public Boolean addAdmin(Integer sessionId, User admin)
			throws SessionNotAvailableException,
			PresentAsOtherUserTypeException;

	public Boolean removeAdmin(Integer sessionId, Integer userEmpId)
			throws SessionNotAvailableException;

	public Boolean removeAdmin(Integer sessionId, String userEmailId)
			throws SessionNotAvailableException;

	public Boolean addFacilitator(Integer sessionId, User facilitator)
			throws SessionNotAvailableException,
			PresentAsOtherUserTypeException;

	public Boolean removeFacilitator(Integer sessionId, Integer userEmpId)
			throws SessionNotAvailableException;

	public Boolean removeFacilitator(Integer sessionId, String userEmailId)
			throws SessionNotAvailableException;
}
