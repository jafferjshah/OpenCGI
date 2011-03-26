package com.cgi.open.persist;

import java.util.Set;

import com.cgi.open.easyshare.AdminAssignedException;
import com.cgi.open.easyshare.AppointmentNotFoundException;
import com.cgi.open.easyshare.AttendeeNotFoundException;
import com.cgi.open.easyshare.FacilitatorNotFoundException;
import com.cgi.open.easyshare.InvalidPromotionException;
import com.cgi.open.easyshare.MessageNotFoundException;
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

public interface PersistenceServices {
	public Set<Session> getSessionsStore();
	
	public Set<User> getUsersStore();

	public User getUser(String email, UserType userType)
			throws UserNotFoundException;

	public Set<User> getUsers(Integer sessionId, UserType userType)
			throws SessionNotFoundException;

	public Session getSession(Integer sessionId)
			throws SessionNotFoundException;

	public Appointment getAppointment(Integer sessionId, Integer appointmentId)
			throws AppointmentNotFoundException,
			SessionNotFoundException;

	public User getAttendee(Integer sessionId, Integer id)
			throws SessionNotFoundException, AttendeeNotFoundException;

	public User getAttendee(Integer sessionId, String emailId)
			throws SessionNotFoundException, AttendeeNotFoundException;

	public User getFacilitator(Integer sessionId, Integer id)
			throws SessionNotFoundException, FacilitatorNotFoundException;

	public User getFacilitator(Integer sessionId, String emailId)
			throws SessionNotFoundException, FacilitatorNotFoundException;

	public Resource getResource(Integer sessionId, Integer id)
			throws SessionNotFoundException, ResourceNotFoundException;

	public Message getMessage(Integer sessionId, Integer id)
			throws SessionNotFoundException, MessageNotFoundException;

	public Boolean addUserToSession(Integer sessionId, User user) throws SessionNotFoundException;
			 

	public Boolean checkForDuplicacy(Session anySession);

	public Boolean checkForDuplicacy(Integer sessionId,
			Appointment anyAppoinment) throws SessionNotFoundException;
	
	public Boolean checkForDuplicacy(Integer sessionId,
			Resource anyResource) throws SessionNotFoundException;

	public Boolean checkForDuplicacy(String email, UserType userType);

	public Boolean checkForDuplicacy(Integer sessionId, User anyUser,
			UserType userType) throws SessionNotFoundException,
			PresentAsOtherUserTypeException;

	public Integer saveNewSession(Session newSession);

	public Integer saveNewAppointment(Integer sessionId,
			Appointment anyAppoinment) throws SessionNotFoundException;

	public Integer saveNewMessage(Integer sessionId, Message newMessage)
			throws SessionNotFoundException;

	public Integer saveNewResource(Integer sessionId, Resource newResource)
			throws SessionNotFoundException;

	public Boolean deleteSession(Integer sessionId)
			throws SessionNotFoundException;

	public Boolean promoteUser(String email, UserType userType)
			throws UserNotFoundException, InvalidPromotionException;

	public Boolean replaceAdmin(Integer sessionId, User user)
			throws SessionNotFoundException,
			PresentAsOtherUserTypeException;

	public Boolean removeFacilitator(Integer sessionId, String userEmailId)
			throws SessionNotFoundException, FacilitatorNotFoundException;

	public Boolean removeAttendee(Integer sessionId, String userEmailId)
			throws SessionNotFoundException, AttendeeNotFoundException;

	public Boolean removeResource(Integer sessionId, Integer resourceId)
			throws SessionNotFoundException, ResourceNotFoundException;

	public boolean removeAppointment(Integer sessionId, Integer appointmentId)
			throws SessionNotFoundException,
			AppointmentNotFoundException;

	public Set<String> getSessionUserEmails(Integer sessionId,UserType userType)
			throws SessionNotFoundException;

	
}
