package com.cgi.open.EasyShare.model;

import java.util.Iterator;
import java.util.Set;

/**
 * Session details class represents the details of the corresponding session.
 * It contains the session name and a set of appointments.
 * Appointments denote the day wise schedule of the session.
 *
 */
public class SessionDetails {
	/**
	 * Name of the Session
	 */
	private String sessionName;
	
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
	
	/**
	 * Calculates the duration of the entire session spanning across all the appointments.
	 * @return
	 */
	public String duration()
	{
		Integer mins = null;
		Integer hours=null;
		String duration;
		for(Iterator<Appointment>it=appointments.iterator();it.hasNext();)
		{
			Appointment appObj=it.next();
			mins+=appObj.getDurationInMinutes();
			
		}
		
		hours=mins/60;
		mins=mins%60;
		duration=hours+"hours "+mins+"mins";
		return duration;
	}
	
	/**
	 * The String representation of the SessionDetails object
	 * 
	 * @return
	 */
	public String toString() 
	{
		String ret;
		ret="\nSession Name:"+sessionName;
		for(Iterator<Appointment>it=appointments.iterator();it.hasNext();)
		{
			ret+=it.next().toString();
		}
		return ret;
	}
	
	/**
	 * hashCode implementation
	 */
	public int hashcode()
	{
		int hash;
		hash=sessionName.hashCode();
		for(Iterator<Appointment>it=appointments.iterator();it.hasNext();)
		{
			hash+=it.next().hashCode();
			
		}
		hash=hash/101;
		return hash;
	}
	
	public boolean equals(Object obj)
	{
		if(obj==null||!(obj instanceof SessionDetails))
		{
			return false;
		}
		SessionDetails sesObj=(SessionDetails)obj;
		System.out.println(this + " AND " + sesObj + " equals called");

		return(this.sessionName.equals(sesObj.sessionName)&&this.appointments.equals(sesObj.appointments));
	}
}
