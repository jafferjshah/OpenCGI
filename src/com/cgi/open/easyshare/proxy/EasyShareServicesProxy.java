package com.cgi.open.easyshare.proxy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.DuplicateSessionException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.SessionNotAvailableException;
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
	public Set<User> addAttendees(Integer sessionId, Set<User> attendees)
	throws SessionNotAvailableException {
		Session thisSession = persistent.getSession(sessionId);
		thisSession.setAttendees(attendees);
		return thisSession.getAttendees();
	}

	public Message addMessage(Integer sessionId, Message message) {
		try {
			Session thisSession=persistent.getSession(sessionId);
			thisSession.addMessage(message);
		} catch (SessionNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Resource addResource(Integer sessionId, Resource resource) {
		try {
			Session thisSession=persistent.getSession(sessionId);
			thisSession.addResource(resource);
		} catch (SessionNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Boolean addUserToSession(Integer sessionId, Integer adminId,
			UserType userType) {
		
		return null;
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

	public Integer designateUserToAdmin(User user, UserType userType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Session> getAllSessions() 
	{
		Set<Session>sessions=null;
		sessions=persistent.getSessionsStore();
		return sessions;
	}

	public List<Message> getMessages(Integer sessionId) {
		List<Message> messageList=null;
		try {
			Session thisSession=persistent.getSession(sessionId);
			 messageList=thisSession.getMessages();
		} catch (SessionNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageList;
	}

	public Set<Resource> getResources(Integer sessionId)
	{
		Set<Resource> resources=null; 
		try {
			Session thisSession=persistent.getSession(sessionId);
			 resources=thisSession.getResourcePool();
		} catch (SessionNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resources;
	}

	public Session getSession(Integer sessionId) 
	{
		Session thisSession=null;
		try 
		{
			 thisSession=persistent.getSession(sessionId);
			 
		} 
		catch (SessionNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisSession;
		
	}

	public User getUser(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<User> getUsers(Integer sessionId, UserType userType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean removeFacilitator(Integer sessionId, Integer facilitatorId) {
		
		try {
			Session thisSession=persistent.getSession(sessionId);
			 thisSession.removeFacilitator(facilitatorId);
		} catch (SessionNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Boolean removeResource(Integer sessionId, Integer resourceId) {
		try {
			Session thisSession=persistent.getSession(sessionId);
			 thisSession.removeResource(resourceId);
		} catch (SessionNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public Set<Appointment> saveAppointments(Integer sessionId,
			Set<Appointment> appointments) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
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


