package com.cgi.open.EasyShare.model;

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
	/**
	 * Name of the Session
	 */
	private String sessionName;
	private Integer sessionId;
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
	 * Holds the details of the session.
	 * Contains set of appointments denoting schedule of a session.
	 */
	private SessionDetails details;
	
	/**
	 * Set of facilitators of the session.
	 */
	private Set<Facilitator> facilitators;
	
	/**
	 * Set of attendees of the session.
	 */
	private Set<Attendee> attendees;
	
	/**
	 * Repository of resources of the session.
	 */
	private ResourcePool pool;
	
	/**
	 * List of messages of the session.
	 */
	private List<Message> messages;

	public Session()
	{
		details=new SessionDetails();
		facilitators=new HashSet<Facilitator>();
		pool=new ResourcePool();
		attendees=new HashSet<Attendee>();
		messages=new ArrayList<Message>();
	}
	
	/**Adds attendee to the set of attendees of the session
	 * @param attendee
	 */
	public void addAttendee(Attendee attendee)
	{
		attendees.add(attendee);
	}
	
	/**Gets Attendee from the set based on attendee's id.
	 * @param id
	 * @return
	 */
	public Attendee getAttendee(int id)
	{
		Attendee result=null;
		
		for(Iterator<Attendee> it=attendees.iterator();it.hasNext();)
		{
			result=it.next();
			if(result.getEmpid()==id)
			{
				
				break;
			}
					
		}
		return result;
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
	public String toString()
	{
		String ret;
		ret="\nSession Details:"+details+"\nFacilitators:"+facilitators+"\nAttendees:"+attendees+"\nMessage:"+messages+"Resource pool:"+pool;
		return ret;
	}
	
	/**
	 * hashCode implementation
	 * 
	 * @return 
	 */
	public int hashCode()
	{
		int hash=details.hashCode();
		System.out.println(this + " hashCode called : " + hash);
		return hash;
	}

	/**
	 * equals implementation
	 */
	public boolean equals(Object obj)
	{
		if(obj==null||!(obj instanceof Session))
		{
			return false;
		}
		Session sessionObj=(Session)obj;
		System.out.println(this + " AND " + sessionObj + " equals called");

		return(this.details.equals(sessionObj.details));
	}
	
}
