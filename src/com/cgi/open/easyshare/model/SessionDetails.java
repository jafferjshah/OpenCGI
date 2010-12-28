package com.cgi.open.easyshare.model;

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
	 * Holds the day wise schedule details of the session.
	 */
	private Set<Appointment> appointments;
	
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
	
	/**
	 * The String representation of the SessionDetails object
	 * 
	 * @return
	 */
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		for(Appointment appObj : getAppointments()) {
			sb
				.append(appObj)
				.append("\n")
			;
		}
		return sb.toString();
	}
	
	/**
	 * hashCode implementation
	 */
	public int hashcode() {
		int hash = 0;
		for(Appointment appObj : getAppointments()) {
			hash += appObj.hashCode();
			
		}
		hash = hash/101;
		return hash;
	}
	
	public boolean equals(Object obj)
	{
		if(obj==null||!(obj instanceof SessionDetails)) {
			return false;
		}
		SessionDetails sesObj=(SessionDetails)obj;
		System.out.println(this + " AND " + sesObj + " equals called");

		return(this.appointments.equals(sesObj.appointments));
	}
}
