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
	public Integer createSession(String sessionName, 
			Set<Appointment> appointments) throws DuplicateSessionException;
	
	/**
	 * The service adds appointment to an
	 * existing session. 
	 * @param sessionId
	 * @param appointment
	 * @return
	 * @throws SessionNotAvailableException 
	 */
	public Integer addAppointment(Integer sessionId, 
			String date,String fromDate,String toDate) throws SessionNotAvailableException;
	public boolean removeAppointment(Integer sessionId, Integer appointmentId)
	throws SessionNotAvailableException,
	AppointmentNotAvailableException;
	
	public Boolean addFacilitator(Integer sessionId,Integer userId) throws SessionNotAvailableException, PresentAsOtherUserTypeException, UserNotAvailableException, AttendeeAlreadyRegisteredException, AdminAssignedException;
	
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
	 * @throws AdminAssignedException 
	 * @throws AttendeeAlreadyRegisteredException 
	 * @throws UserNotAvailableException 
	 * @throws PresentAsOtherUserTypeException 
	 */
	public Boolean addAttendees(Integer sessionId, 
			Set<User> attendees) throws SessionNotAvailableException, PresentAsOtherUserTypeException, UserNotAvailableException, AttendeeAlreadyRegisteredException, AdminAssignedException;
	public Boolean assignAdmin(Integer sessionId,Integer userId) throws SessionNotAvailableException, PresentAsOtherUserTypeException, UserNotAvailableException, AttendeeAlreadyRegisteredException, AdminAssignedException;
	public Boolean addAttendee(Integer sessionId,Integer userId) throws SessionNotAvailableException, PresentAsOtherUserTypeException, UserNotAvailableException, AttendeeAlreadyRegisteredException, AdminAssignedException;
	
	public Boolean removeAttendee(Integer sessionId, Integer attendeeId) throws SessionNotAvailableException ;
	
	
	/**
	 * The service adds a resource to the resource pool 
	 * belonging to an existing session.
	 * @param sessionId
	 * @param resource
	 * @return
	 */
	public Integer addResource(Integer sessionId, 
			String resourceName,String url)throws SessionNotAvailableException;
	
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
	
	
}