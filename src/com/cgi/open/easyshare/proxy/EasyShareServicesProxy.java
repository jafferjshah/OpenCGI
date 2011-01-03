package com.cgi.open.easyshare.proxy;

import java.util.List;
import java.util.Set;

import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.model.Appointment;
import com.cgi.open.easyshare.model.Message;
import com.cgi.open.easyshare.model.Resource;
import com.cgi.open.easyshare.model.Session;
import com.cgi.open.easyshare.model.User;
import com.cgi.open.easyshare.model.UserType;

public class EasyShareServicesProxy implements EasyShareServices {

	public Set<User> addAttendees(Integer sessionId, Set<User> attendees) {
		// TODO Auto-generated method stub
		return null;
	}

	public Message addMessage(Integer sessionId, Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	public Resource addResource(Integer sessionId, Resource resource) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean addUserToSession(Integer sessionId, Integer adminId,
			UserType userType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Session createSession(String sessionName,
			Set<Appointment> appointments) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer designateUserToAdmin(User user, UserType userType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Session> getAllSessions() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Message> getMessages(Integer sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Resource> getResources(Integer sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Session getSession(Integer sessionId) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean removeResource(Integer sessionId, Integer resourceId) {
		// TODO Auto-generated method stub
		return false;
	}

	public Set<Appointment> saveAppointments(Integer sessionId,
			Set<Appointment> appointments) {
		// TODO Auto-generated method stub
		return null;
	}

}
