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
	 * The service promotes a user to Admin or Facilitator. The service has no
	 * effect for Attendee users.
	 * 
	 * @param user
	 * @return
	 * @throws PresentAsSameUserTypeException
	 * @throws InvalidPromotionException 
	 */
	public Boolean designateUser(String email, UserType userType)
			throws PresentAsSameUserTypeException, UserNotFoundException, InvalidPromotionException;

	/**
	 * The service creates a session with the provided sessionName and
	 * appointments.
	 * 
	 * @param sessionName
	 * @param description 
	 * @param appointments
	 * @return
	 */
	public Integer createSession(String sessionName, String description)
			throws DuplicateSessionException;

	/**
	 * The service adds appointment to an existing session.
	 * 
	 * @param sessionId
	 * @param appointment
	 * @return
	 * @throws SessionNotFoundException
	 * @throws DuplicateAppointmentException 
	 */
	public Integer addAppointment(Integer sessionId, String date,
			String fromTime, String toTime,String location) throws SessionNotFoundException, DuplicateAppointmentException;

	public boolean removeAppointment(Integer sessionId, Integer appointmentId)
			throws SessionNotFoundException,
			AppointmentNotFoundException;

	public Boolean addFacilitator(Integer sessionId, String email)
			throws SessionNotFoundException,
			PresentAsOtherUserTypeException, UserNotFoundException,
			PresentAsSameUserTypeException;

	/**
	 * The service removes a facilitator from an existing session. Atleast one
	 * facilitator should remain.
	 * 
	 * @param sessionId
	 * @param facilitatorId
	 * @return
	 * @throws SessionNotFoundException
	 * @throws UserNotFoundException 
	 */
	public Boolean removeFacilitator(Integer sessionId,String email )
			throws SessionNotFoundException, FacilitatorNotFoundException;

	/**
	 * The service adds a set of Users as Attendees to an existing session.
	 * 
	 * @param sessionId
	 * @param attendees
	 * @return
	 * @throws AdminAssignedException
	 * @throws AttendeeAlreadyRegisteredException
	 * @throws UserNotFoundException
	 * @throws PresentAsOtherUserTypeException
	 */
	public Boolean addAttendees(Integer sessionId, Set<String> attendeesEmail)
			throws SessionNotFoundException,
			PresentAsOtherUserTypeException, UserNotFoundException,PresentAsSameUserTypeException;
			

	public Boolean assignAdmin(Integer sessionId, String email)
			throws SessionNotFoundException,
			PresentAsOtherUserTypeException, UserNotFoundException,
			 AdminAssignedException, PresentAsSameUserTypeException;

	public Boolean addAttendee(Integer sessionId, String email)
			throws SessionNotFoundException,
			PresentAsOtherUserTypeException, UserNotFoundException,
			PresentAsSameUserTypeException;

	public Boolean removeAttendee(Integer sessionId, String email)
			throws SessionNotFoundException, AttendeeNotFoundException;

	/**
	 * The service adds a resource to the resource pool belonging to an existing
	 * session.
	 * 
	 * @param sessionId
	 * @param resource
	 * @return
	 * @throws DuplicateResourceException 
	 */
	public Integer addResource(Integer sessionId, String resourceName,
			String url) throws SessionNotFoundException, DuplicateResourceException;

	/**
	 * The service removes a resource to the resource pool belonging to an
	 * existing session.
	 * 
	 * @param sessionId
	 * @param resourceId
	 * @return
	 * @throws SessionNotFoundException
	 * @throws ResourceNotFoundException
	 */
	public Boolean removeResource(Integer sessionId, Integer resourceId)
			throws SessionNotFoundException, ResourceNotFoundException;

	/**
	 * The service adds a message to an existing session.
	 * 
	 * @param sessionId
	 * @param message
	 * @return
	 */
	public Integer addMessage(Integer sessionId, String subject, String text,String date,String post_time,String post_by)
			throws SessionNotFoundException;

	/**
	 * The service gets the details of an existing session.
	 * 
	 * @param sessionId
	 * @return
	 */
	public Session getSession(Integer sessionId)
			throws SessionNotFoundException;

	/**
	 * The service gets the users of particular type belonging to an existing
	 * session.
	 * 
	 * @param sessionId
	 * @return
	 * @throws SessionNotFoundException
	 */
	public Set<User> getUsers(Integer sessionId, UserType userType)
			throws SessionNotFoundException;

	/**
	 * The services gets all the resources associated with an existing session.
	 * 
	 * @param sessionId
	 * @return
	 */
	public Set<Resource> getResources(Integer sessionId)
			throws SessionNotFoundException;

	/**
	 * The services gets all the messages associated with an existing session.
	 * 
	 * @param sessionId
	 * @return
	 */
	public List<Message> getMessages(Integer sessionId)
			throws SessionNotFoundException;

	/**
	 * The service gets the User owning the user id. Only used for Admin or
	 * Facilitator.
	 * 
	 * @param userId
	 * @return
	 * @throws UserNotFoundException
	 */
	public User getUser(String email, UserType userType)
			throws UserNotFoundException;

	/**
	 * The service returns all the sessions related to the user.
	 * 
	 * @return
	 */
	public Set<Session> getAllSessions();

	public Map<UserType, Set<Session>> getMySessions(String email);

	public Set<String> getAllUsersLight(Integer sessionId,UserType userType)
			throws SessionNotFoundException;
}