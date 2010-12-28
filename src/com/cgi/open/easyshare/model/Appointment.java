package com.cgi.open.easyshare.model;

/**
 * The class Appointment represents time units (day wise) of a particular event. It has
 * a date, fromTime and toTime to denote a particular event. There is no logic in the system to
 * check whether fromTime falls before toTime, and it is left to the user of the object to
 * do the verification.
 */
public class Appointment {
	/**
	 * uniquely identifies an appointment if a session
	 */
	private Integer appointmentId;
	/**
	 * The day of the session. Should be represented in the format:
	 * mm/dd/yyyy.
	 */
	private String date;
	/**
	 * The starting time of an event, does not include the minute specified.
	 * 4 digit representation of time. The first two digits represent
	 * the hour and the other two digits represent the minutes, in the
	 * 24 hour time format.
	 * For example: if the fromTime is 1230, the session begins from
	 * 12:31PM in the given day.
	 */
	private String fromTime;
	/**
	 * The ending time of an event, includes the minute specified.
	 * 4 digit representation of time. The first two digits represent
	 * the hour and the other two digits represent the minutes, in the
	 * 24 hour time format.
	 */
	private String toTime;
	
	public Appointment() {
		super();
	}
	
	public Appointment(String date, String fromTime, String toTime) {
		setDate(date);
		setFromTime(fromTime);
		setToTime(toTime);
	}
	
	/**
	 * The method returns the duration of time between fromTime
	 * and toTime in minutes. It is also assumed that the toTime 
	 * will always follow fromTime.
	 * 
	 * @return The duration of the time
	 */
	public Integer getDurationInMinutes() {
		return (getTimeInMinutes(getToTime()) - getTimeInMinutes(getFromTime()));
	}
	/**
	 * Get appointment id
	 * @return appointmentId
	 */
	public Integer getAppointmentId() {
		return appointmentId;
	}

	/**
	 * Set appointment id
	 * @param appointmentId
	 */
	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

	/**
	 * Get date
	 * 
	 * @return date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Set date
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Get fromTime
	 * 
	 * @return fromTime
	 */
	public String getFromTime() {
		return fromTime;
	}

	/**
	 * Set fromTime
	 * 
	 * @param fromTime
	 */
	public void setFromTime(String fromTime) {
		checkTime(fromTime);
		this.fromTime = fromTime;
	}

	/**
	 * Get toTime
	 * 
	 * @return toTime
	 */
	public String getToTime() {
		return toTime;
	}

	/**
	 * Set toTime
	 * 
	 * @param toTime
	 */
	public void setToTime(String toTime) {
		checkTime(toTime);
		this.toTime = toTime;
	}
	
	/**
	 * The String representation of the Appointment object
	 * 
	 * @return
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("#")
			.append(getAppointmentId())
			.append(": ")
			.append(getDate())
			.append(" (")
			.append(getFromTime())
			.append("-")
			.append(getToTime())
			.append(")")
		;
		return sb.toString();
	}
	
	/**
	 * The method checks whether the format of time is valid. It uses the following steps
	 * for validation:
	 * 1. The length of the time string is 4
	 * 2. The time string could be parsed to an Integer
	 * 3. The minute and the hour lay within their boundaries
	 * 
	 * @param time
	 * @throws NumberFormatException
	 * @throws IllegalArgumentException
	 */
	private void checkTime(String time) 
	throws NumberFormatException, IllegalArgumentException {
		if((time != null) && (time.length() != 4)) {
			throw new IllegalArgumentException("Time: Not in prescribed format");
		}
		Integer.parseInt(time);
		Integer mins = Integer.parseInt(time.substring(2));
		Integer hrs = Integer.parseInt(time.substring(0,2));
		if(mins < 0 || mins > 59) {
			throw new IllegalArgumentException("Time: Minutes should be between 0 & 59");
		}
		if(hrs < 0 || hrs > 23) {
			throw new IllegalArgumentException("Time: Hours should be between 0 & 23");
		}
	}
	
	/**
	 * The method returns the minute representation of the given time in a day.
	 * 
	 * @param time
	 * @return The time in minutes of the day
	 * @throws NumberFormatException
	 */
	private Integer getTimeInMinutes(String time)
	throws NumberFormatException {
		Integer mins = Integer.parseInt(time.substring(2));
		Integer hrs = Integer.parseInt(time.substring(0,2));
		return (hrs * 60) + mins;
	}
	
	/**
	 * equals implementation
	 */
	public boolean equals(Object anotherAppointment) {
		//check anotherAppointment is really an Appointment
		if(anotherAppointment == null || 
				!(anotherAppointment instanceof Appointment)) {
			return false;
		}
		//casting anotherAppointment to Appointment
		Appointment toBeChecked = (Appointment)anotherAppointment;
		//every instance variable should be the same for the two
		//objects to be equal
		return (this.getDate().equals(toBeChecked.getDate())
				&& this.getFromTime().equals(toBeChecked.getFromTime())
				&& this.getToTime().equals(toBeChecked.getToTime()));
	}
	
	/**
	 * hashCode implementation
	 */
	public int hashCode() {
		return (31 * this.getDate().hashCode()) +
				(31 * this.getFromTime().hashCode()) +
				(31 * this.getToTime().hashCode());
	}
	
	/*public static void main(String[] args) {
		Appointment a = new Appointment();
		a.setDate("12/12/2010");
		a.setFromTime("1230");
		a.setToTime("1420");
		System.out.println(a.getDurationInMinutes());
	}*/
}