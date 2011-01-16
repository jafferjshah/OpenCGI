package com.cgi.open.persist;

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

public interface PersistenceServices {
	public Set<Session> getSessionsStore();

	public Set<User> getUsersStore();

	public User getUser(Integer userId, UserType userType)
			throws UserNotAvailableException;

	public Set<User> getUsers(Integer sessionId, UserType userType)
			throws SessionNotAvailableException;

	public Session getSession(Integer sessionId)
			throws SessionNotAvailableException;

	public Appointment getAppointment(Integer sessionId, Integer appointmentId)
			throws AppointmentNotAvailableException,
			SessionNotAvailableException;

	public User getAttendee(Integer sessionId, Integer id)
			throws SessionNotAvailableException, AttendeeNotFoundException;

	public User getAttendee(Integer sessionId, String emailId)
			throws SessionNotAvailableException, AttendeeNotFoundException;

	public User getFacilitator(Integer sessionId, Integer id)
			throws SessionNotAvailableException, FacilitatorNotFoundException;

	public User getFacilitator(Integer sessionId, String emailId)
			throws SessionNotAvailableException, FacilitatorNotFoundException;

	public Resource getResource(Integer sessionId, Integer id)
			throws SessionNotAvailableException, ResourceNotFoundException;

	public Message getMessage(Integer sessionId, Integer id)
			throws SessionNotAvailableException, MessageNotFoundException;

	public Boolean addUserToSession(Integer sessionId, Integer userId,
			UserType userType) throws SessionNotAvailableException,
			PresentAsOtherUserTypeException, UserNotAvailableException,
			AttendeeAlreadyRegisteredException, AdminAssignedException;

	public Boolean checkForDuplicacy(Session anySession);

	public Boolean checkForDuplicacy(Integer sessionId,
			Appointment anyAppoinment) throws SessionNotAvailableException;

	public Boolean checkForDuplicacy(Integer userId, UserType userType);

	public Boolean checkForDuplicacy(Integer sessionId, User anyUser,
			UserType userType) throws SessionNotAvailableException,
			PresentAsOtherUserTypeException;

	public Integer saveNewSession(Session newSession);

	public Integer saveNewAppointment(Integer sessionId,
			Appointment anyAppoinment) throws SessionNotAvailableException;

	public Integer saveNewMessage(Integer sessionId, Message newMessage)
			throws SessionNotAvailableException;

	public Integer saveNewResource(Integer sessionId, Resource newResource)
			throws SessionNotAvailableException;

	public Boolean deleteSession(Integer sessionId)
			throws SessionNotAvailableException;

	public Boolean promoteUser(Integer userId, UserType userType);

	public Boolean replaceAdmin(Integer sessionId, User user)
			throws SessionNotAvailableException,
			PresentAsOtherUserTypeException;

	public Boolean removeFacilitator(Integer sessionId, Integer facilitatorId)
			throws SessionNotAvailableException, FacilitatorNotFoundException;

	public Boolean removeFacilitator(Integer sessionId, String userEmailId)
			throws SessionNotAvailableException, FacilitatorNotFoundException;

	public Boolean removeAttendee(Integer sessionId, Integer userEmpId)
			throws SessionNotAvailableException, AttendeeNotFoundException;

	public Boolean removeAttendee(Integer sessionId, String userEmailId)
			throws SessionNotAvailableException, AttendeeNotFoundException;

	public Boolean removeResource(Integer sessionId, Integer resourceId)
			throws SessionNotAvailableException, ResourceNotFoundException;

	public boolean removeAppointment(Integer sessionId, Integer appointmentId)
			throws SessionNotAvailableException,
			AppointmentNotAvailableException;

	public Set<Integer> getSessionUserIds(Integer sessionId)
			throws SessionNotAvailableException;
}
