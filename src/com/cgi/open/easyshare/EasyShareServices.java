package com.cgi.open.easyshare;

import java.util.List;
import java.util.Map;
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
	 * @throws PresentAsSameUserTypeException 
	 */
	public Boolean designateUser(User user, 
			UserType userType) throws PresentAsSameUserTypeException;
	
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
	 * @param userId
	 * @param userType
	 * @return
	 * @throws PresentAsOtherUserTypeException 
	 * @throws UserNotAvailableException 
	 */
	public Boolean addUserToSession(Integer sessionId, 
			Integer userId, UserType userType)throws SessionNotAvailableException, PresentAsOtherUserTypeException, UserNotAvailableException;
	
	/**
	 * The service removes a facilitator from an existing session. 
	 * Atleast one facilitator should remain.
	 * @param sessionId
	 * @param facilitatorId
	 * @return
	 * @throws SessionNotAvailableException 
	 */
	public Boolean removeFacilitator(Integer sessionId, 
			Integer facilitatorId) throws SessionNotAvailableException;
	
	
	/**
	 * The service adds a set of Users as Attendees to an
	 * existing session.
	 * @param sessionId
	 * @param attendees
	 * @return
	 */
	public Boolean addAttendees(Integer sessionId, 
			Set<User> attendees) throws SessionNotAvailableException;
	public Boolean addAttendee(Integer sessionId, User user)
	throws SessionNotAvailableException;
	public Boolean removeAttendee(Integer sessionId, Integer attendeeId) throws SessionNotAvailableException ;
	
	/**
	 * The service adds a resource to the resource pool 
	 * belonging to an existing session.
	 * @param sessionId
	 * @param resource
	 * @return
	 */
	public Integer addResource(Integer sessionId, 
			Resource resource)throws SessionNotAvailableException;
	
	/**
	 * The service removes a resource to the resource pool 
	 * belonging to an existing session.
	 * @param sessionId
	 * @param resourceId
	 * @return
	 * @throws SessionNotAvailableException 
	 */
	public Boolean removeResource(Integer sessionId, 
			Integer resourceId) throws SessionNotAvailableException;
	
	/**
	 * The service adds a message to an existing session.
	 * @param sessionId
	 * @param message
	 * @return
	 */
	public Integer addMessage(Integer sessionId, 
			Message message)throws SessionNotAvailableException;
	public Boolean removeMessage(Integer sessionId, Integer messageId) throws SessionNotAvailableException ;
	
	/**
	 * The service gets the details of an existing session.
	 * @param sessionId
	 * @return
	 */
	public Session getSession(Integer sessionId)throws SessionNotAvailableException;
	
	/**
	 * The service gets the users of particular type belonging
	 * to an existing session.
	 * @param sessionId
	 * @return
	 * @throws SessionNotAvailableException 
	 */
	public Set<User> getUsers(Integer sessionId, UserType userType) throws SessionNotAvailableException;
	
	/**
	 * The services gets all the resources associated with an
	 * existing session. 
	 * @param sessionId
	 * @return
	 */
	public Set<Resource> getResources(Integer sessionId)throws SessionNotAvailableException;
	
	/**
	 * The services gets all the messages associated with an
	 * existing session. 
	 * @param sessionId
	 * @return
	 */
	public List<Message> getMessages(Integer sessionId)throws SessionNotAvailableException;
	
	/**
	 * The service gets the User owning the user id. Only used
	 * for Admin or Facilitator.  
	 * @param userId
	 * @return
	 * @throws UserNotAvailableException 
	 */
	public User getUser(Integer userId,UserType userType) throws UserNotAvailableException;
	
	/**
	 * The service returns all the sessions related to the user.
	 * @return
	 */
	public Set<Session> getAllSessions();
	public Map<UserType,Set<Session>> getMySessions(User anyUser);
	public Integer addAppointment(Integer sessionId, Appointment appointment)
	throws SessionNotAvailableException;
}