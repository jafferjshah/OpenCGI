package com.cgi.open.easyshare.model;

//represents the training session conducted by the trainer.

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Session {
	private SessionDetails details;
	private Set<Facilitator> facilitators;
	private Set<Attendee> attendees;
	private ResourcePool pool;
	private List<Message> messages;

	public Session() {
		details = new SessionDetails();
		facilitators = new HashSet<Facilitator>();
		pool = new ResourcePool();
		attendees = new HashSet<Attendee>();
		messages = new ArrayList<Message>();
	}

	public void addAttendee(Attendee attendee) {
		attendees.add(attendee);
	}

	public Attendee getAttendee(int id) {
		Attendee result = null;

		for (Iterator<Attendee> it = attendees.iterator(); it.hasNext();) {
			result = it.next();
			if (result.getEmpid() == id) {

				break;
			}

		}
		return result;
	}

	public void addMessage(Message message) {
		messages.add(message);
	}

	public String toString() {
		return ("Session Details:" + details + "\nFacilitators:" + facilitators
				+ "\nAttendees:" + attendees + "\nMessage" + messages
				+ "Resource pool" + pool);
	}

	public int hashCode() {
		int hash = details.hashCode();
		System.out.println(this + " hashCode called : " + hash);
		return hash;
	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Session)) {
			return false;
		}
		Session sessionObj = (Session) obj;
		System.out.println(this + " AND " + sessionObj + " equals called");

		return (this.details.equals(sessionObj.details));
	}

}
