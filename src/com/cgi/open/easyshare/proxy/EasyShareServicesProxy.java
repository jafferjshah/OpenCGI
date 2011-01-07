package com.cgi.open.easyshare.proxy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.DuplicateSessionException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.PresentAsSameUserTypeException;
import com.cgi.open.easyshare.SessionNotAvailableException;
import com.cgi.open.easyshare.UserNotAvailableException;
import com.cgi.open.easyshare.model.Appointment;
import com.cgi.open.easyshare.model.Message;
import com.cgi.open.easyshare.model.Resource;
import com.cgi.open.easyshare.model.Session;
import com.cgi.open.easyshare.model.User;
import com.cgi.open.easyshare.model.UserType;
import com.cgi.open.persist.PersistenceServices;

public class EasyShareServicesProxy implements EasyShareServices {
	
	private PersistenceServices persistent = ServicesMapper.getPersistenceServicesProxyInstance();

	/**
	 * This method will be called only at the first time. For altering,
	 * the attendees set, call add/remove attendee services.
	 */
	public Boolean addAttendees(Integer sessionId, Set<User> attendees)
	throws SessionNotAvailableException {
		Boolean flag=Boolean.TRUE;
		for(User user:attendees)
		{
			flag=flag&&persistent.addAttendee(sessionId, user);
		}
		return flag;
	}
	public Boolean addAttendee(Integer sessionId, User user)
	throws SessionNotAvailableException {
		return(persistent.addAttendee(sessionId, user));
	}

	public Integer addMessage(Integer sessionId, Message message)
	throws SessionNotAvailableException
	{
		Integer messageId;
		
			 messageId=persistent.saveNewMessage(sessionId,message);
			 return messageId;	
	}

	public Integer addResource(Integer sessionId, Resource resource)
	throws SessionNotAvailableException
	{
		Integer resourceId;
		resourceId=persistent.saveNewResource(sessionId,resource);
		return resourceId;
		
	}
	
	public Integer addAppointment(Integer sessionId, Appointment appointment)
	throws SessionNotAvailableException
	{
		Integer appointmentId;
		
		appointmentId=persistent.saveNewAppointment(sessionId,appointment);
			 return appointmentId;	
	}

	public Boolean addUserToSession(Integer sessionId, Integer userId,
			UserType userType) throws SessionNotAvailableException, PresentAsOtherUserTypeException, UserNotAvailableException
	{

			User user=persistent.getUser(userId,userType);
			
			if(userType.equals(UserType.FACILITATOR))
			{
				return(persistent.addFacilitator(sessionId,user));
			}
			if(userType.equals(UserType.ATTENDEE))
			{
				return(persistent.addAttendee(sessionId,user));
			}
			return Boolean.FALSE;
			
		
		
	}

	public Session createSession(String sessionName,
			Set<Appointment> appointments) throws DuplicateSessionException {
		Session newSession = new Session();
		newSession.setSessionName(sessionName);
		newSession.setAppointments(appointments);
		if(persistent.checkForDuplicacy(newSession)) {
			throw new DuplicateSessionException("Session with the given details already present");
		}
		Integer sessionId = persistent.saveNewSession(newSession);
		newSession.setSessionId(sessionId);
		return newSession;
	}

	public Boolean designateUser(User user, UserType userType) throws PresentAsSameUserTypeException {
		
			if(persistent.checkForDuplicacy(user, userType))
			{
				throw new PresentAsSameUserTypeException("User already present in the store as same UserType");
			}
			else
			{
				user.setUserType(userType);
			}
		
		return(persistent.promoteUser(user, userType));
	}

	public Set<Session> getAllSessions() 
	{
		Set<Session>sessions=null;
		sessions=persistent.getSessionsStore();
		return sessions;
	}

	public List<Message> getMessages(Integer sessionId)throws SessionNotAvailableException
	{
		List<Message> messageList;
		Session thisSession=persistent.getSession(sessionId);
		messageList=thisSession.getMessages();		
		return messageList;
	}

	public Set<Resource> getResources(Integer sessionId)throws SessionNotAvailableException
	{
		Set<Resource> resources; 
		Session thisSession=persistent.getSession(sessionId);
		resources=thisSession.getResourcePool();
		return resources;
	}

	public Session getSession(Integer sessionId)throws SessionNotAvailableException 
	{
		Session thisSession;
		thisSession=persistent.getSession(sessionId);
		return thisSession;
		
	}

	public User getUser(Integer userId,UserType userType) throws UserNotAvailableException 
	{
		User user=persistent.getUser(userId,userType);
		return user;
	}

	public Set<User> getUsers(Integer sessionId, UserType userType) throws SessionNotAvailableException {
		return(persistent.getUsers(sessionId, userType));
	}

	public Boolean removeFacilitator(Integer sessionId, Integer facilitatorId) throws SessionNotAvailableException {
		
		return(persistent.removeFacilitator(sessionId, facilitatorId));
	}

	public Boolean removeResource(Integer sessionId, Integer resourceId) throws SessionNotAvailableException {
		return(persistent.removeResource(sessionId, resourceId));
		}
	public Boolean removeMessage(Integer sessionId, Integer messageId) throws SessionNotAvailableException {
		return(persistent.removeMessage(sessionId, messageId));
		}
	public Boolean removeAttendee(Integer sessionId, Integer attendeeId) throws SessionNotAvailableException {
		return(persistent.removeAttendee(sessionId, attendeeId));
		}
		
		
	

	public Set<Appointment> saveAppointments(Integer sessionId,
			Set<Appointment> appointments) {
		return null;
		
	}
	
	
	/**
	 * 
	 * Coded by Sanjana
	 */
	public Map<UserType,Set<Session>> getMySessions(User anyUser)
	{
		Map<UserType, Set<Session>> mySessions = new HashMap<UserType, Set<Session>>();
		Set<Session> adminSessions=new HashSet<Session>();
		Set<Session> facilitatorSessions=new HashSet<Session>();
		Set<Session> attendeeSessions=new HashSet<Session>();
		
		if(persistent.checkForDuplicacy(anyUser,UserType.ADMIN))
		{
			for(Session thisSession:getAllSessions())
			{
				if(thisSession.getAdmin().getEmpid()==anyUser.getEmpid())
				{
					adminSessions.add(thisSession);
				}
			}
		}
		if(persistent.checkForDuplicacy(anyUser,UserType.FACILITATOR))
		{
			for(Session thisSession:getAllSessions())
			{
				for(User thisFacilitator:thisSession.getFacilitators())
				{
					if(thisFacilitator.getEmpid()==anyUser.getEmpid())
					{
						facilitatorSessions.add(thisSession);
					}
				}
			}
			
		}
		if(persistent.checkForDuplicacy(anyUser,UserType.ATTENDEE))
		{
			for(Session thisSession:getAllSessions())
			{
				for(User thisAttendee:thisSession.getAttendees())
				{
					if(thisAttendee.getEmpid()==anyUser.getEmpid())
					{
						attendeeSessions.add(thisSession);
					}
				}
			}
		}
		
		mySessions.put(UserType.ADMIN,adminSessions);
		mySessions.put(UserType.FACILITATOR,facilitatorSessions);
		mySessions.put(UserType.ATTENDEE,attendeeSessions);

		return mySessions;
	}
	}


