package com.cgi.open.easyshare.model;

import java.util.Set;

public class SessionDetails {
	private String sessionName;
	private Set<Appointment> appointments;
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public Set<Appointment> getAppointments() {
		return appointments;
	}
	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
}
