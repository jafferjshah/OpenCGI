package com.cgi.open.persist;

public enum QUERY_CONSTANTS {

	GET_USER("select * from users where user_email=? and user_type=?"),
	GET_SESSION_USERS("select * from session_users where session_id=? and user_type=?"),
	GET_SESSION("select * from sessions where session_id=?"),
	GET_SESSIONS("select * from sessions"),
	GET_SESSION_BY_DETAILS("select * from sessions where session_name=?"),
	GET_APPOINTMENTS("select * from appointments where session_id=?"),
	GET_APPOINTMENT_BY_DETAILS("select * from appointments where session_id=? and date=? and from_time=? and to_time=? and location=?"),
	GET_RESOURCES("select * from resources where session_id=?"),
	GET_RESOURCE_BY_DETAILS("select * from resources where session_id=? and resource_name=? and url=?"),
	GET_MESSAGES("select * from messages where session_id=?"),
	GET_MESSAGE_RESOURCES("select * from message_resources where session_id=? and message_id=?"),
	GET_MAX_SESSION_ID("select max(session_id) from sessions"),
	GET_MAX_APPOINTMENT_ID("select max(appointment_id) from appointments where session_id=?"),
	GET_MAX_MESSAGE_ID("select max(message_id) from messages where session_id=?"),
	GET_MAX_RESOURCE_ID("select max(resource_id) from resources where session_id=?"),
	SAVE_NEW_SESSION("insert into sessions values(?,?,?)"),
	SAVE_NEW_APPOINTMENT("insert into appointments values(?,?,?,?,?,?)"),
	SAVE_NEW_MESSAGE("insert into messages values(?,?,?,?,?,?,?)"),
	SAVE_NEW_RESOURCE("insert into resources values(?,?,?,?)"),
	ADD_USER_TO_SESSION("insert into session_users values(?,?,?,?,?)"),
	PROMOTE_USER("insert into users values(?,?,?,?)"),
	DELETE_SESSION_RESOURCES("delete from resources where session_id=?"),
	DELETE_MESSAGE_RESOURCES("delete from message_resources where session_id=?"),
	DELETE_SESSION_MESSAGES("delete from messages where session_id=?"),
	DELETE_SESSION_APPOINTMENTS("delete from appointments where session_id=?"),
	DELETE_SESSION_USERS("delete from session_users where session_id=?"),
	DELETE_SESSION("delete from sessions where session_id=?"),
	REMOVE_USER("delete from session_users where session_id=? and user_email=?"),
	REMOVE_RESOURCE("delete from resources where session_id=? and resource_id=?"),
	REMOVE_APPOINTMENT("delete from appointments where session_id=? and appointment_id=?"),
	REPLACE_ADMIN("update session_users set user_id=?,user_name=?,user_email=? where session_id=? and user_type='ADMIN'");
	
	public String query;

	private QUERY_CONSTANTS(String q) {
		this.query = q;
	}

	public String getQuery() {
		return this.query;
	}

}
