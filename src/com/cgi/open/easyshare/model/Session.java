package com.cgi.open.easyshare.model;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * Session class represents the session conducted by the facilitator.
 * It has session details,set of facilitators,set of attendees,list of messages and a resource pool.
 *
 */
public class Session 
{
	public Session()
	{
		appointments=new HashSet<Appointment>();
		facilitators=new HashSet<User>();
		resourcePool=new HashSet<Resource>();
		attendees=new HashSet<User>();
		messages=new ArrayList<Message>();
	}
	
	private Integer sessionId;
	/**
	 * Name of the Session
	 */
	private String sessionName;
	
	/**
	 * Set of facilitators of the session.
	 */
	private Set<User> facilitators;
	
	/**
	 * Set of attendees of the session.
	 */
	private Set<User> attendees;
	
	/**
	 * The user who created this session.
	 */
	private User admin;
	
	/**
	 * Repository of resources of the session.
	 */
	private Set<Resource>resourcePool;
	
	/**
	 * List of messages of the session.
	 */
	private List<Message> messages;

	/**
	 * Holds the day wise schedule details of the session.
	 */
	private Set<Appointment> appointments;
	
	/**
	 * Get session name
	 * @return sessionName
	 */
	public String getSessionName() {
		return sessionName;
	}
	
	/**
	 * Set session name
	 * @param sessionName
	 */
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public Set<User> getFacilitators() {
		return facilitators;
	}

	public void setFacilitators(Set<User> facilitators) {
		this.facilitators = facilitators;
	}
	
	public void addFacilitator(Facilitator facilitator)
	{
		facilitators.add(facilitator);
	}
	
	public void removeFacilitator(Integer id)
	{
		facilitators.remove(getFacilitator(id));
		
	}
	
	public User getFacilitator(Integer id)
	{
		User result = null;
		for(
				Iterator<User> it = facilitators.iterator();
				it.hasNext();
			) {
			result = it.next();
			if(result.getEmpid() == id) {
				break;
			}
		}
		return result;
	}
	/**
	 * Get session id
	 * @return sessionId
	 */
	public Integer getSessionId() {
		return sessionId;
	}

	/**
	 * Set session id
	 * @param sessionId
	 */
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Get User who created this session
	 * @return
	 */
	public User getAdmin() {
		return admin;
	}

	/**
	 * Set User who created this session.
	 * @param admin
	 */
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	
	/**
	 * Gets the resource pool of the session
	 * @return
	 */
	public Set<Resource> getResourcePool() {
		return resourcePool;
	}
	/**
	 * Sets the resource pool
	 * 
	 */
	public void setResourcePool(Set<Resource> resourcePool) {
		this.resourcePool = resourcePool;
	}
	
	/**This method adds resource to the resourcepool
	 * @param resource
	 */
	public void addResource(Resource resource) {
		resourcePool.add(resource);
	}
	
	public void removeResource(Integer resourceId)
	{
		resourcePool.remove(getResource(resourceId));
	}
	
	/**This method returns resource searching by name
	 * @param name
	 * @return
	 */
	public Resource getResource(Integer id) {
		Resource result = null;
		for(
				Iterator<Resource> it = resourcePool.iterator();
				it.hasNext();
			) {
			result = it.next();
			if(result.getResourceId() == id) {
				break;
			}
		}
		return result;
	}

		
	/**
	 * Get Appointment
	 * @return appointments
	 */
	public Set<Appointment> getAppointments() {
		return appointments;
	}
	
	/**
	 * Set Appointment
	 * @param appointments
	 */
	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	/**
	 * Adds an appointment to the set of appointments of the session.
	 * @param appointment
	 */
	public void addAppointment(Appointment appointment)
	{
		appointments.add(appointment);
	}
	
	public void removeAppointment(Integer appointmentId)
	{
		appointments.remove(getAppointment(appointmentId));
	}
	
	public Appointment getAppointment(Integer id)
	{
		Appointment result = null;
		for(
				Iterator<Appointment> it = appointments.iterator();
				it.hasNext();
			) {
			result = it.next();
			if(result.getAppointmentId() == id) {
				break;
			}
		}
		return result;
	}
	
	public Set<User> getAttendees() {
		return attendees;
	}

	public void setAttendees(Set<User> attendees) {
		this.attendees = attendees;
	}

	/**Adds attendee to the set of attendees of the session
	 * @param attendee
	 */
	public void addAttendees(Set<User> attendees)
	{
		attendees.addAll(attendees);
	}
	
	public void removeAttendees(Integer id)
	{
		attendees.remove(getAttendee(id));
	}
	
	/**
	 * Gets Attendee from the set based on attendee's id.
	 * @param id
	 * @return
	 */
	public User getAttendee(int id) {
		User result=null;
		for(Iterator<User> it= attendees.iterator(); it.hasNext();)
		{
			result=it.next();
			if(result.getEmpid()==id) {
				break;
			}
		}
		return result;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	/**
	 * Adds message to the list of messages.
	 * @param message
	 */
	public void addMessage(Message message)
	{
		messages.add(message);
	}
	
	
	/**
	 * The String representation of the Session object
	 * 
	 * @return
	 */		
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Appointment appObj : getAppointments()) {
			sb
				.append(appObj)
				.append("\n")
			;
		}
		sb
			.append("Created by: ")
			.append(getAdmin())
		;
		sb
			.append("Facilitated by: ")
		;
		for(User userObj : getFacilitators()) {
			sb
				.append(userObj.getEmail())
				.append(", ")
			;
		}
		sb
			.append("Attended by: ")
		;
		for(User userObj : getAttendees()) {
			sb
				.append(userObj.getEmail())
				.append(", ")
			;
		}
		return sb.toString();
	}
	
	/**
	 * hashCode implementation
	 * 
	 * @return 
	 */
	public int hashCode() {
		int hash = getSessionName().hashCode();
		for(Appointment appObj : getAppointments()) {
			hash += appObj.hashCode();
			
		}
		hash = hash/101;
		return hash;
	}

	/**
	 * equals implementation
	 */
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Session)) {
			return false;
		}
		Session sessionObj=(Session)obj;
		System.out.println(this + " AND " + sessionObj + " equals called");
		return(this.getSessionName().equals(sessionObj.getSessionName())
				&& this.getAppointments().equals(sessionObj.getAppointments()));
	}

	
	
	/**
	 * Calculates the duration of the entire session spanning across all the appointments.
	 * @return
	 */
	public String duration() {
		Integer mins = null;
		Integer hours=null;
		String duration;
		for(Appointment appObj : getAppointments()) {
			mins += appObj.getDurationInMinutes();
			
		}
		hours=mins/60;
		mins=mins%60;
		duration=hours + "hours " + mins + "mins!";
		return duration;
	}
}
