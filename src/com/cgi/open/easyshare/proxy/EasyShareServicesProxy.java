package com.cgi.open.easyshare.proxy;

import java.util.List;
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

	public Set<User> addAttendees(Integer sessionId, Set<User> attendees) 
	{
		try {
			Session thisSession=persistent.getSession(sessionId);
			thisSession.addAttendees(attendees);
		} catch (SessionNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Message addMessage(Integer sessionId, Message message) 
	{
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
			UserType userType) 
	{
		
		
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

}
