package com.cgi.open.easyshare;

import java.util.List;
import java.util.Set;

import com.cgi.open.easyshare.model.Admin;
import com.cgi.open.easyshare.model.Appointment;
import com.cgi.open.easyshare.model.Attendee;
import com.cgi.open.easyshare.model.Facilitator;
import com.cgi.open.easyshare.model.Message;
import com.cgi.open.easyshare.model.Resource;
import com.cgi.open.easyshare.model.Session;
import com.cgi.open.easyshare.model.User;
import com.cgi.open.easyshare.model.UserType;

/**
 *
 */
public interface EasyShareServices {
	/**
	 * The service promotes a user to Admin or Facilitator.
	 * The service has no effect for Attendee users.
	 * @param user
	 * @return
	 */
	public Integer designateUserToAdmin(User user, 
			UserType userType);
	
	/**
	 * The service creates a session with the provided sessionName
	 * and appointments.
	 * @param sessionName
	 * @param appointments
	 * @return
	 */
	public Session createSession(String sessionName, 
			Set<Appointment> appointments) throws DuplicateSessionException;
	
	/**
	 * The service adds/edits/removes appointments to an
	 * existing session. The set should be mapped one to
	 * one with the existing appointments to decide whether
	 * a new appointment is added or an existing appointment
	 * is edited or deleted. Atleast one appointment should remain.
	 * @param sessionId
	 * @param appointments
	 * @return
	 */
	public Set<Appointment> saveAppointments(Integer sessionId, 
			Set<Appointment> appointments);
	
	/**
	 * The service adds the user (ADMIN or FACILITATOR) to an
	 * existing session.
	 * @param sessionId
	 * @param adminId
	 * @param userType
	 * @return
	 */
	public Boolean addUserToSession(Integer sessionId, 
			Integer adminId, UserType userType);
	
	/**
	 * The service removes a facilitator from an existing session. 
	 * Atleast one facilitator should remain.
	 * @param sessionId
	 * @param facilitatorId
	 * @return
	 */
	public Boolean removeFacilitator(Integer sessionId, 
			Integer facilitatorId);
	
	
	/**
	 * The service adds a set of Users as Attendees to an
	 * existing session.
	 * @param sessionId
	 * @param attendees
	 * @return
	 */
	public Set<User> addAttendees(Integer sessionId, 
			Set<User> attendees) throws SessionNotAvailableException;
	
	/**
	 * The service adds a resource to the resource pool 
	 * belonging to an existing session.
	 * @param sessionId
	 * @param resource
	 * @return
	 */
	public Resource addResource(Integer sessionId, 
			Resource resource);
	
	/**
	 * The service removes a resource to the resource pool 
	 * belonging to an existing session.
	 * @param sessionId
	 * @param resourceId
	 * @return
	 */
	public Boolean removeResource(Integer sessionId, 
			Integer resourceId);
	
	/**
	 * The service adds a message to an existing session.
	 * @param sessionId
	 * @param message
	 * @return
	 */
	public Message addMessage(Integer sessionId, 
			Message message);
	
	/**
	 * The service gets the details of an existing session.
	 * @param sessionId
	 * @return
	 */
	public Session getSession(Integer sessionId);
	
	/**
	 * The service gets the users of particular type belonging
	 * to an existing session.
	 * @param sessionId
	 * @return
	 */
	public Set<User> getUsers(Integer sessionId, UserType userType);
	
	/**
	 * The services gets all the resources associated with an
	 * existing session. 
	 * @param sessionId
	 * @return
	 */
	public Set<Resource> getResources(Integer sessionId);
	
	/**
	 * The services gets all the messages associated with an
	 * existing session. 
	 * @param sessionId
	 * @return
	 */
	public List<Message> getMessages(Integer sessionId);
	
	/**
	 * The service gets the User owning the user id. Only used
	 * for Admin or Facilitator.  
	 * @param userId
	 * @return
	 */
	public User getUser(Integer userId);
	
	/**
	 * The service returns all the sessions related to the user.
	 * @return
	 */
	public Set<Session> getAllSessions();
}