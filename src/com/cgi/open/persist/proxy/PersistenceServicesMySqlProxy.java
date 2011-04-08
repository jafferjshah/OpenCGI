package com.cgi.open.persist.proxy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cgi.open.ServicesMapper;
import com.cgi.open.Utilities.Utilities;
import com.cgi.open.easyshare.AppointmentNotFoundException;
import com.cgi.open.easyshare.AttendeeNotFoundException;
import com.cgi.open.easyshare.FacilitatorNotFoundException;
import com.cgi.open.easyshare.InvalidPromotionException;
import com.cgi.open.easyshare.MessageNotFoundException;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.ResourceNotFoundException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserNotFoundException;
import com.cgi.open.easyshare.model.Appointment;
import com.cgi.open.easyshare.model.Message;
import com.cgi.open.easyshare.model.Resource;
import com.cgi.open.easyshare.model.Session;
import com.cgi.open.easyshare.model.User;
import com.cgi.open.easyshare.model.UserType;
import com.cgi.open.external.UserEntity;
import com.cgi.open.external.UserIntegration;
import com.cgi.open.persist.PersistenceServices;
import com.cgi.open.persist.QUERY_CONSTANTS;
import java.sql.PreparedStatement;

public class PersistenceServicesMySqlProxy implements PersistenceServices {

	private static Set<User> usersStore = new HashSet<User>();

	public Set<User> getUsersStore() {
		return usersStore;
	}

	public User getUser(String email, UserType userType)
			throws UserNotFoundException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		User user = null;

		try {
			conn = Utilities.getConnection();
			String uType = null;
			if (userType.equals(UserType.ADMIN)) {
				uType = "ADMIN";
			}
			if (userType.equals(UserType.FACILITATOR)) {
				uType = "FACILITATOR";
			}
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_USER.getQuery());
			pstmt.setString(1, email);
			pstmt.setString(2, uType);
			rs=pstmt.executeQuery();
			if (!rs.next()) {
				throw new UserNotFoundException(
						"User with given ID not available");
			} else {
				user = new User();
				user.setEmpid(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setUserType(userType);

			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			Utilities.closeResources(conn, pstmt, rs);
		}

		return user;

	}

	public Set<User> getUsers(Integer sessionId, UserType userType)
			throws SessionNotFoundException {
		Session thisSession = getSession(sessionId);
		Set<User> users = new HashSet<User>();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		User user;

		try {
			conn = Utilities.getConnection();
			String uType = null;
			if (userType.equals(UserType.ADMIN)) {
				uType = "ADMIN";
			}
			if (userType.equals(UserType.FACILITATOR)) {
				uType = "FACILITATOR";
			}
			if (userType.equals(UserType.ATTENDEE)) {
				uType = "ATTENDEE";
			}
			pstmt = (PreparedStatement) conn
					.prepareStatement(QUERY_CONSTANTS.GET_SESSION_USERS.getQuery());
			pstmt.setInt(1, sessionId);
			pstmt.setString(2, uType);
			rs=pstmt.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setEmpid(rs.getInt(2));
				user.setName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setUserType(userType);
				users.add(user);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			Utilities.closeResources(conn, pstmt, rs);
		}
		return users;

	}

	public Session getSession(Integer sessionId)
			throws SessionNotFoundException {
		Connection conn = null;
		ResultSet rs = null, rs1 = null, rs2 = null, rs3 = null, rs4 = null;
		PreparedStatement pstmt=null,pstmt1=null,pstmt2=null;
		Session session = new Session();
		Set<Appointment> appointments = new HashSet<Appointment>();
		Set<Resource> resources = new HashSet<Resource>();
		Set<User> facilitators = new HashSet<User>();
		Set<User> attendees = new HashSet<User>();
		Set<User> admin = new HashSet<User>();
		List<Message> messages = new ArrayList();
		Message message = new Message();
		try {
			conn = Utilities.getConnection();
			pstmt=(PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_SESSION.getQuery());
			pstmt.setInt(1, sessionId);
			rs=pstmt.executeQuery();
			if (!rs.next()) {
				throw new SessionNotFoundException(
						"session with the given id is not available");
			} else {
				session.setSessionId(sessionId);
				session.setSessionName(rs.getString(2));
				session.setDiscription(rs.getString(3));
			}
			pstmt1=(PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_APPOINTMENTS.getQuery());
			pstmt1.setInt(1, sessionId);
			rs1 = pstmt1
					.executeQuery();
			while (rs1.next()) {
				Appointment appointment = new Appointment();
				appointment.setAppointmentId(rs1.getInt(2));
				appointment.setDate(rs1.getString(3));
				appointment.setFromTime(rs1.getString(4));
				appointment.setToTime(rs1.getString(5));
				appointment.setLocation(rs1.getString(6));
				appointments.add(appointment);
			}

			pstmt1=(PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_RESOURCES.getQuery());
			pstmt1.setInt(1, sessionId);
			rs1 = pstmt1
					.executeQuery();
			while (rs1.next()) {
				Resource resource = new Resource();
				resource.setResourceId(rs1.getInt(2));
				resource.setResourceName(rs1.getString(3));
				resource.setUrl(rs1.getString(4));
				resources.add(resource);
			}

			pstmt1=(PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_SESSION_USERS.getQuery());
			pstmt1.setInt(1, sessionId);
			pstmt1.setString(2,"ATTENDEE");
			rs1=pstmt1.executeQuery();
			while(rs1.next()){
					User user=new User();
					user.setEmpid(rs1.getInt(2));
					user.setName(rs1.getString(3));
					user.setEmail(rs1.getString(4));
					user.setUserType(UserType.ATTENDEE);					
					attendees.add(user);				
			}
			pstmt1=(PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_SESSION_USERS.getQuery());
			pstmt1.setInt(1, sessionId);
			pstmt1.setString(2,"FACILITATOR");
			rs1=pstmt1.executeQuery();
			while(rs1.next()){
					User user=new User();
					user.setEmpid(rs1.getInt(2));
					user.setName(rs1.getString(3));
					user.setEmail(rs1.getString(4));
					user.setUserType(UserType.FACILITATOR);					
					facilitators.add(user);				
			}
			
			pstmt1=(PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_SESSION_USERS.getQuery());
			pstmt1.setInt(1, sessionId);
			pstmt1.setString(2,"ADMIN");
			rs1=pstmt1.executeQuery();
			if(rs1.next()){
					User user=new User();
					user.setEmpid(rs1.getInt(2));
					user.setName(rs1.getString(3));
					user.setEmail(rs1.getString(4));
					user.setUserType(UserType.ADMIN);					
					session.setAdmin(user);
			}
			pstmt1=(PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_MESSAGES.getQuery());
			pstmt1.setInt(1, sessionId);
			rs1=pstmt1.executeQuery();
			while (rs1.next()) {
				message.setMessageId(rs1.getInt(2));
				message.setSubject(rs1.getString(3));
				message.setText(rs1.getString(4));
				message.setPostDate(rs1.getString(5));
				message.setPostTime(rs1.getString(6));
				message.setPostBy(rs1.getString(7));
				pstmt2=(PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_MESSAGE_RESOURCES.getQuery());
				pstmt2.setInt(1, sessionId);
				pstmt2.setInt(2, rs1.getInt(2));
				rs2 = pstmt2.executeQuery();
				while (rs2.next()) {
					Resource resource = new Resource();
					try {
						resource = getResource(sessionId, rs2.getInt(3));
					} catch (ResourceNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					message.getResources().add(resource);
				}
				messages.add(message);
			}
			session.setAppointments(appointments);
			session.setFacilitators(facilitators);
			session.setAttendees(attendees);
			session.setResourcePool(resources);
			session.setMessages(messages);
			rs1.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			Utilities.closeResources(conn, pstmt, rs);
			
		}
		System.out.println(session);
		return session;
	}

	public Appointment getAppointment(Integer session_id, Integer appointment_id)
			throws AppointmentNotFoundException, SessionNotFoundException {
		Set<Appointment> appointments = null;
		Appointment appointment = null;
		Session thisSession;
		thisSession = getSession(session_id);
		appointments = thisSession.getAppointments();
		for (Iterator<Appointment> it = appointments.iterator(); it.hasNext();) {
			appointment = it.next();
			if (appointment.getAppointmentId().equals(appointment_id)) {
				break;
			} else {
				appointment = null;
			}
		}
		if (appointment == null) {
			throw new AppointmentNotFoundException(
					"appointment with the given id is not found");
		}

		return appointment;

	}

	public User getAttendee(Integer sessionId, Integer userId)
			throws SessionNotFoundException, AttendeeNotFoundException {
		Set<User> attendees = null;
		User attendee = null;
		Session thisSession;
		thisSession = getSession(sessionId);
		attendees = thisSession.getAttendees();
		for (Iterator<User> it = attendees.iterator(); it.hasNext();) {
			attendee = it.next();
			if (attendee.getEmpid().equals(userId)) {
				break;
			} else {
				attendee = null;
			}
		}
		if (attendee == null) {
			throw new AttendeeNotFoundException(
					"facilitator with the given emailid is not found");
		}

		return attendee;

	}

	/**
	 * Gets Attendee from the set based on attendee's email id.
	 * 
	 * @param email
	 *            id
	 * @return
	 * @throws SessionNotFoundException
	 * @throws AttendeeNotFoundException
	 */
	public User getAttendee(Integer sessionId, String emailId)
			throws SessionNotFoundException, AttendeeNotFoundException {
		Set<User> attendees = null;
		User attendee = null;
		Session thisSession;
		thisSession = getSession(sessionId);
		attendees = thisSession.getAttendees();
		for (Iterator<User> it = attendees.iterator(); it.hasNext();) {
			attendee = it.next();
			if (attendee.getEmail().equals(emailId)) {
				break;
			} else {
				attendee = null;
			}
		}
		if (attendee == null) {
			throw new AttendeeNotFoundException(
					"facilitator with the given emailid is not found");
		}

		return attendee;

	}

	public User getFacilitator(Integer session_id, Integer user_id)
			throws FacilitatorNotFoundException, SessionNotFoundException {
		Set<User> facilitators = null;
		User facilitator = null;
		Session thisSession;
		thisSession = getSession(session_id);
		facilitators = thisSession.getFacilitators();
		for (Iterator<User> it = facilitators.iterator(); it.hasNext();) {
			facilitator = it.next();
			if (facilitator.getEmpid().equals(user_id)) {
				break;
			} else {
				facilitator = null;
			}
		}
		if (facilitator == null) {
			throw new FacilitatorNotFoundException(
					"facilitator with the given emailid is not found");
		}

		return facilitator;

	}

	public User getFacilitator(Integer sessionId, String emailId)
			throws SessionNotFoundException, FacilitatorNotFoundException {
		Set<User> facilitators = null;
		User facilitator = null;
		Session thisSession;
		thisSession = getSession(sessionId);
		facilitators = thisSession.getFacilitators();
		for (Iterator<User> it = facilitators.iterator(); it.hasNext();) {
			facilitator = it.next();
			if (facilitator.getEmail().equals(emailId)) {
				break;
			} else {
				facilitator = null;
			}
		}
		if (facilitator == null) {
			throw new FacilitatorNotFoundException(
					"facilitator with the given emailid is not found");
		}

		return facilitator;

	}

	/**
	 * This method returns resource corresponding to a sessionsearching by its
	 * id
	 * 
	 * @param name
	 * @return
	 * @throws SessionNotFoundException
	 * @throws ResourceNotFoundException
	 */
	public Resource getResource(Integer session_id, Integer resource_id)
			throws ResourceNotFoundException, SessionNotFoundException {

		Set<Resource> resources = null;
		Resource resource = null;
		Session thisSession;
		thisSession = getSession(session_id);
		resources = thisSession.getResourcePool();
		for (Iterator<Resource> it = resources.iterator(); it.hasNext();) {
			resource = it.next();
			if (resource.getResourceId().equals(resource_id)) {
				break;
			} else {
				resource = null;
			}
		}
		if (resource == null) {
			throw new ResourceNotFoundException(
					"resource with the given id is not found");
		}
		return resource;
	}

	/**
	 * This method returns message corresponding to a session searching by its
	 * id
	 * 
	 * @param name
	 * @return
	 * @throws SessionNotFoundException
	 * @throws MessageNotFoundException
	 * @throws SessionNotFoundException
	 */
	public Message getMessage(Integer session_id, Integer message_id)
			throws MessageNotFoundException, SessionNotFoundException {

		Session thisSession = null;
		Message message = null;
		List<Message> messages = null;
		thisSession = getSession(session_id);
		messages = thisSession.getMessages();
		for (Iterator<Message> it = messages.iterator(); it.hasNext();) {
			message = it.next();
			if (message.getMessageId().equals(message_id)) {
				break;
			} else {
				message = null;
			}
		}
		if (message == null) {
			throw new MessageNotFoundException(
					"message with the given id is not found");
		}
		return message;

	}

	public Boolean checkForDuplicacy(Session anySession) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Boolean dupFlag=null;
		try {
			conn = Utilities.getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_SESSION_BY_DETAILS.getQuery());
			pstmt.setString(1,anySession.getSessionName());
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				dupFlag=Boolean.FALSE;
			}
			else{
				dupFlag=Boolean.TRUE;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			Utilities.closeResources(conn,pstmt, rs);
		}
		return dupFlag;
	}

	public Boolean checkForDuplicacy(Integer sessionId,
			Appointment anyAppointment) throws SessionNotFoundException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Boolean dupFlag=null;
		Session checkSession=null;
		try {
			conn = Utilities.getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_APPOINTMENT_BY_DETAILS.getQuery());
			pstmt.setInt(1,sessionId);
			pstmt.setString(2,anyAppointment.getDate());
			pstmt.setString(3,anyAppointment.getFromTime());
			pstmt.setString(4,anyAppointment.getToTime());
			pstmt.setString(5,anyAppointment.getLocation());
			checkSession=getSession(sessionId);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				dupFlag= Boolean.FALSE;
			}
			else{
				dupFlag=Boolean.TRUE;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			Utilities.closeResources(conn, pstmt, rs);

		}

		return dupFlag;
	}

	public Boolean checkForDuplicacy(Integer sessionId, Resource anyResource)
			throws SessionNotFoundException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Boolean dupFlag=null;
		Session checkSession=null;
		

		try {
			conn = Utilities.getConnection();
			checkSession=getSession(sessionId);
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_RESOURCE_BY_DETAILS.getQuery());
			pstmt.setInt(1, sessionId);
			pstmt.setString(2,anyResource.getResourceName());
			pstmt.setString(3,anyResource.getUrl());
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				dupFlag= Boolean.FALSE;
			}
			else{
				dupFlag=Boolean.TRUE;
			}
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dupFlag;
	}

	public Boolean checkForDuplicacy(String email, UserType userType) {

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Boolean dupFlag=null;
		try {
			conn = Utilities.getConnection();
			String uType = null;
			if (userType.equals(UserType.ADMIN)) {
				uType = "ADMIN";
			}
			if (userType.equals(UserType.FACILITATOR)) {
				uType = "FACILITATOR";
			}
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_USER.getQuery());
			pstmt.setString(1,email);
			pstmt.setString(2,uType);
			rs=pstmt.executeQuery();
			if (!rs.next()) {
				dupFlag= Boolean.FALSE;
			}
			else{
				dupFlag=Boolean.TRUE;
			}
		 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dupFlag;
	}

	public Boolean checkForDuplicacy(Integer sessionId, User anyUser,
			UserType userType) throws SessionNotFoundException,
			PresentAsOtherUserTypeException {

		Session thisSession = getSession(sessionId);
		// ADMIN = ATTENDEE, FACILITATOR (alone) -- START
		if (userType.equals(UserType.ADMIN)) {
			for (User user : thisSession.getFacilitators()) {
				if (user.equals(anyUser)) {
					throw new PresentAsOtherUserTypeException(
							"The user cant be an admin, because the user is already a facilitator for this session!");
				}
			}
		}
		if (userType.equals(UserType.ATTENDEE)) {
			for (User user : thisSession.getFacilitators()) {
				if (user.equals(anyUser)) {
					throw new PresentAsOtherUserTypeException(
							"The user cant be an attendee, because the user is already a facilitator for this session!");
				}
			}
		}
		if (userType.equals(UserType.FACILITATOR)) {
			User admin;
			if ((admin = thisSession.getAdmin()) != null
					&& admin.equals(anyUser)) {
				throw new PresentAsOtherUserTypeException(
						"The user cant be a facilitator, because the user is already an admin for this session!");
			}
			for (User user : thisSession.getAttendees()) {
				if (user.equals(anyUser)) {
					throw new PresentAsOtherUserTypeException(
							"The user cant be a facilitator, because the user is already an attendee for this session!");
				}
			}
		}
		// ADMIN = ATTENDEE, FACILITATOR (alone) -- STOP
		// Already present as same user type --START
		if (userType.equals(UserType.ADMIN)) {
			User admin;
			if ((admin = thisSession.getAdmin()) != null
					&& admin.equals(anyUser)) {
				return Boolean.TRUE;
			}
		}
		if (userType.equals(UserType.ATTENDEE)) {
			for (User user : thisSession.getAttendees()) {
				if (user.equals(anyUser)) {
					return Boolean.TRUE;
				}
			}
		}
		if (userType.equals(UserType.FACILITATOR)) {
			for (User user : thisSession.getFacilitators()) {
				if (user.equals(anyUser)) {
					return Boolean.TRUE;
				}
			}
		}
		// Already present as same user type --STOP
		return Boolean.FALSE;

	}

	public Integer saveNewSession(Session newSession) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null,pstmt1=null;
		Integer maxId = null, newId = null;
		
		try {
			conn = Utilities.getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_MAX_SESSION_ID.getQuery());
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				newId = 1;
			} else {
				maxId = rs.getInt(1);
				newId = maxId + 1;
			}
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.SAVE_NEW_SESSION.getQuery());
			pstmt.setInt(1, newId);
			pstmt.setString(2, newSession.getSessionName());
			pstmt.setString(3, newSession.getDiscription());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			Utilities.closeResources(conn, pstmt, rs);
		}
		return newId;
	}

	/**
	 * saves an appointment to the set of appointments of the session.
	 * 
	 * @param appointment
	 * @throws SessionNotFoundException 
	 * @throws SessionNotFoundException
	 */

	public Integer saveNewAppointment(Integer session_id,
			Appointment newAppointment) throws SessionNotFoundException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Integer maxId = null, newId = null;
		Session checkSession=null;
		checkSession=getSession(session_id);
		try {
			conn = Utilities.getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_MAX_APPOINTMENT_ID.getQuery());
			pstmt.setInt(1, session_id);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				newId = 1;
			} else {
				maxId = rs.getInt(1);
				newId = maxId + 1;
			}
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.SAVE_NEW_APPOINTMENT.getQuery());
			pstmt.setInt(1,session_id);
			pstmt.setInt(2, newId);
			pstmt.setString(3, newAppointment.getDate());
			pstmt.setString(4, newAppointment.getFromTime());
			pstmt.setString(5,newAppointment.getToTime());
			pstmt.setString(6,newAppointment.getLocation());
			pstmt.executeUpdate();
			

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			Utilities.closeResources(conn, pstmt, rs);
		}
		return newId;
	}

	/**
	 *saves message to the list of messages of the session.
	 * 
	 * @param message
	 * @throws SessionNotFoundException 
	 * @throws SessionNotFoundException
	 */

	public Integer saveNewMessage(Integer sessionId, Message newMessage) throws SessionNotFoundException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Integer maxId = null, newId = null;
		Session checkSession=null;
		checkSession=getSession(sessionId);
		try {
			conn = Utilities.getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_MAX_MESSAGE_ID.getQuery());
			pstmt.setInt(1, sessionId);
			rs=pstmt.executeQuery();
			if (!rs.next()) {
				newId = 1;
			} else {
				maxId = rs.getInt(1);
				newId = maxId + 1;
			}
			pstmt=(PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.SAVE_NEW_MESSAGE.getQuery());
			pstmt.setInt(1, sessionId);
			pstmt.setInt(2, newId);
			pstmt.setString(3,newMessage.getSubject());
			pstmt.setString(4,newMessage.getText());
			pstmt.setString(5,newMessage.getPostDate());
			pstmt.setString(6,newMessage.getPostTime());
			pstmt.setString(7,newMessage.getPostBy());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			Utilities.closeResources(conn, pstmt, rs);
		}
		return newId;
	}

	/**
	 * This method adds resource to the resourcepool of the corresponding
	 * session
	 * 
	 * @param resource
	 * @throws SessionNotFoundException 
	 * @throws SessionNotFoundException
	 */

	public Integer saveNewResource(Integer sessionId, Resource newResource) throws SessionNotFoundException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Integer maxId = null, newId = null;
		Session checkSession=null;
		checkSession=getSession(sessionId);
		try {
			conn = Utilities.getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_MAX_RESOURCE_ID.getQuery());
			pstmt.setInt(1, sessionId);
			rs=pstmt.executeQuery();
			if (!rs.next()) {
				newId = 1;
			} else {
				maxId = rs.getInt(1);
				newId = maxId + 1;
			}
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.SAVE_NEW_RESOURCE.getQuery());
			pstmt.setInt(1, sessionId);
			pstmt.setInt(2, newId);
			pstmt.setString(3,newResource.getResourceName());
			pstmt.setString(4,newResource.getUrl());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			Utilities.closeResources(conn, pstmt, rs);
		}
		return newId;
	}

	public Boolean deleteSession(Integer sessionId)
			throws SessionNotFoundException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Session checkSession=null;
		checkSession=getSession(sessionId);
		Integer r;
		Boolean isDeleted=null;
		try {
			conn = Utilities.getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.DELETE_SESSION_RESOURCES.getQuery());
			pstmt.setInt(1, sessionId);
			pstmt.executeUpdate();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.DELETE_MESSAGE_RESOURCES.getQuery());
			pstmt.setInt(1, sessionId);
			pstmt.executeUpdate();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.DELETE_SESSION_MESSAGES.getQuery());
			pstmt.setInt(1, sessionId);
			pstmt.executeUpdate();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.DELETE_SESSION_APPOINTMENTS.getQuery());
			pstmt.setInt(1, sessionId);
			pstmt.executeUpdate();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.DELETE_SESSION_USERS.getQuery());
			pstmt.setInt(1, sessionId);
			pstmt.executeUpdate();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.DELETE_SESSION.getQuery());
			pstmt.setInt(1, sessionId);
			pstmt.executeUpdate();
			
			isDeleted=Boolean.TRUE;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			isDeleted=Boolean.FALSE;
		} finally {
			Utilities.closeResources(conn, pstmt, rs);
		}
		return isDeleted;
	}

	public Boolean addUserToSession(Integer sessionId, User user)
			throws SessionNotFoundException {

		Connection conn = null;
		ResultSet rs = null;
		int r;
		PreparedStatement pstmt = null;
		Boolean isAdded=null;
		try {
			conn = Utilities.getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.ADD_USER_TO_SESSION.getQuery());
			Session checkSession = getSession(sessionId);
			String uType = null;
			
			if (user.getUserType().equals(UserType.ADMIN)) {
				uType = "ADMIN";
			}
			if (user.getUserType().equals(UserType.FACILITATOR)) {
				uType = "FACILITATOR";
			}
			if (user.getUserType().equals(UserType.ATTENDEE)) {
				uType = "ATTENDEE";
			}
			pstmt.setInt(1, sessionId);
			pstmt.setInt(2, user.getEmpid());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, uType);
			r=pstmt.executeUpdate();
	
			if (r == 0) {
				isAdded=Boolean.FALSE;
			} else {
				isAdded=Boolean.TRUE;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			isAdded=Boolean.FALSE;
		} finally {
			Utilities.closeResources(conn, pstmt, null);
		}
		return isAdded;
	}

	public Boolean promoteUser(String email, UserType userType)
			throws UserNotFoundException, InvalidPromotionException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Boolean isPromoted=null;
		int r;
		try {
			conn = Utilities.getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.PROMOTE_USER.getQuery());
			if (userType.equals(UserType.ADMIN)
					|| userType.equals(UserType.FACILITATOR)) {
				UserIntegration uint = ServicesMapper
						.getUserIntegrationProxyInstance();
				User anyUser = uint.getActualUser(email, UserEntity.EMAIL);
				String uType = null;
				if (userType.equals(UserType.ADMIN)) {
					uType = "ADMIN";
				}
				if (userType.equals(UserType.FACILITATOR)) {
					uType = "FACILITATOR";
				}
				
				pstmt.setInt(1,anyUser.getEmpid());
				pstmt.setString(2,anyUser.getName());
				pstmt.setString(3,anyUser.getEmail());
				pstmt.setString(4,uType);
				r = pstmt.executeUpdate();
				if (r == 0) {
					isPromoted=Boolean.FALSE;
				}
				else{
				isPromoted=Boolean.TRUE;
				}
			} else {
				throw new InvalidPromotionException("user can be promoted only to admin or facilitator");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
		} finally {
			Utilities.closeResources(conn, pstmt, null);
		}
		return isPromoted;
	}

	public Boolean removeAttendee(Integer sessionId, String userEmailId)
			throws SessionNotFoundException, AttendeeNotFoundException {
		Connection conn = null;
		int r;
		PreparedStatement pstmt = null;
		Session thisSession = getSession(sessionId);
		Boolean isRemoved=null;
		try {
			conn = Utilities.getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.REMOVE_USER.getQuery());
			pstmt.setInt(1, sessionId);
			pstmt.setString(2,userEmailId);
			r = pstmt.executeUpdate();
			if (r == 0) {
				throw new AttendeeNotFoundException("attendee not found");
			} else {
				isRemoved=Boolean.TRUE;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			isRemoved= Boolean.FALSE;
		} finally {
			Utilities.closeResources(conn, pstmt, null);
		}
		return isRemoved;

	}

	public Boolean removeFacilitator(Integer sessionId, String userEmailId)
			throws SessionNotFoundException, FacilitatorNotFoundException {
		Connection conn = null;
		ResultSet rs = null;
		int r;
		PreparedStatement pstmt = null;
		Session thisSession = getSession(sessionId);
		Boolean isRemoved=null;
		try {
			conn = Utilities.getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.REMOVE_USER.getQuery());
			pstmt.setInt(1, sessionId);
			pstmt.setString(2,userEmailId);
			r = pstmt.executeUpdate();
			if (r == 0) {
				throw new FacilitatorNotFoundException("facilitator not found");
			} else {
				isRemoved=Boolean.TRUE;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			isRemoved= Boolean.FALSE;
		} finally {
			Utilities.closeResources(conn,pstmt, null);
		}
		return isRemoved;
	}

	public Boolean removeResource(Integer sessionId, Integer resourceId)
			throws SessionNotFoundException, ResourceNotFoundException {
		Connection conn = null;
		int r;
		PreparedStatement pstmt = null;
		Boolean isRemoved=null;
		Session thisSession = getSession(sessionId);
		try {
			conn = Utilities.getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.REMOVE_RESOURCE.getQuery());
			pstmt.setInt(1, sessionId);
			pstmt.setInt(2, resourceId);
			r = pstmt.executeUpdate();
			if (r == 0) {
				throw new ResourceNotFoundException(
						"the resource with the given id is not found");
			} else {
				isRemoved=Boolean.TRUE;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			isRemoved=Boolean.FALSE;
		} finally {
			Utilities.closeResources(conn, pstmt, null);
		}
		return isRemoved;

	}

	public boolean removeAppointment(Integer sessionId, Integer appointmentId)
			throws SessionNotFoundException, AppointmentNotFoundException {
		Connection conn = null;
		int r;
		PreparedStatement pstmt = null;
		Session thisSession = getSession(sessionId);
		Boolean isRemoved=null;
		try {
			conn = Utilities.getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.REMOVE_APPOINTMENT.getQuery());
			pstmt.setInt(1, sessionId);
			pstmt.setInt(2, appointmentId);
			r = pstmt.executeUpdate();
			if (r == 0) {
				throw new AppointmentNotFoundException(
						"the appointment with the given id is not found");
			} else {
				isRemoved=Boolean.TRUE;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			isRemoved=Boolean.FALSE;
		} finally {
			Utilities.closeResources(conn,pstmt, null);
		}
		return isRemoved;
	}

	public Set<String> getSessionUserEmails(Integer sessionId, UserType userType)
			throws SessionNotFoundException {
		Session thisSession = getSession(sessionId);
		Set<String> userIds = new HashSet<String>();
		// UserType:ADMIN
		if (userType.equals(UserType.ALL) || userType.equals(UserType.ADMIN)) {
			userIds.add(thisSession.getAdmin().getEmail());
		}
		// UserType:FACILITATOR
		if (userType.equals(UserType.ALL)
				|| userType.equals(UserType.FACILITATOR)) {
			for (User facilitator : thisSession.getFacilitators()) {
				userIds.add(facilitator.getEmail());
			}
		}
		// UserType:ATTENDEE
		if (userType.equals(UserType.ALL) || userType.equals(UserType.ATTENDEE)) {
			for (User attendee : thisSession.getAttendees()) {
				userIds.add(attendee.getEmail());
			}
		}
		return userIds;
	}

	public Set<Session> getSessionsStore()  {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Set<Session> sessions=new HashSet<Session>();
		Session session=null;
		Integer id;
		try {
			conn = Utilities.getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(QUERY_CONSTANTS.GET_SESSIONS.getQuery());
			rs=pstmt.executeQuery();
			while(rs.next()){
				id=rs.getInt(1);
				session=getSession(id);
				sessions.add(session);
			}
		}
			catch (SQLException e) {
				System.out.println(e.getMessage());
				
			} catch (SessionNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				Utilities.closeResources(conn,pstmt, rs);
			}
			return sessions;
}
}
