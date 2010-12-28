package com.cgi.open.EasyShare.model;

import java.util.*;


/**
 * Message class represents the communication object of the system.
 * Attendees,Facilitators,Admin can post messages to communicate with each other.
 * 
 *
 */

public class Message 
{
	/**
	 * uniquely identifies a message
	 */
	private Integer messageId;
	/**
	 * The overview of the message communicated.
	 *
	 */
	private String subject;
	/**
	 * The actual message to be conveyed.
	 *
	 */
	private String text;
	/**
	 * The set of resources uploaded by the facilitator for the attendees,specific to a message.
	 * It is a subset of the resource pool.
	 * Resources are added to the message from the resource pool
	 *
	 */
	private Set<Resource> resources;
	
	public Message()
	{
		resources=new HashSet<Resource>();
	}
	/**
	 * Get message id
	 * @return messageId
	 */
	public Integer getMessageId() {
		return messageId;
	}
	
	/**
	 * Set message id
	 * @param messageId
	 */
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	/**
	 * Get Subject
	 *
	 *@return subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * set Subject
	 *
	 *@param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * Get Text
	 *
	 *@return text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * set text
	 *
	 *@param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Get Resource
	 *
	 *@return resources
	 */
	public Set<Resource> getResource() {
		return resources;
	}
	
	/**
	 * adds Resource to the set of resources specific to the message.
	 *
	 *@param resource
	 */
	public void addResource(Resource resource) {
		resources.add(resource);
	}
	
	/**
	 * The String representation of the Message object
	 * 
	 * @return
	 */
	public String toString()
	{
		String ret;
		ret="text:"+text+"\nResource:"+resources;
		return ret;
	}
	
	/**
	 * hashCode implementation
	 */
	public int hashCode()
	{
		int hash=31*subject.hashCode()+31*text.hashCode()+31*resources.hashCode();
		hash=hash/101;
		System.out.println(this + " hashCode called : " + hash);

		return hash;
	}
	
	/**
	 * equals implementation
	 */
	
	public boolean equals(Object obj)
	{
		if(obj==null||!(obj instanceof Message))
		{
			return false;
		}
		Message msgObj=(Message)obj;
		System.out.println(this + " AND " + msgObj + " equals called");

		return(this.subject.equals(msgObj.subject));
	}

}
