package com.cgi.open.easyshare;

public class UserTypeNotValidException extends Exception {
	public UserTypeNotValidException() {
		super();
	}
	public UserTypeNotValidException(String msg) {
		super(msg);
	}
	public UserTypeNotValidException(Throwable th) {
		super(th);
	}
}
