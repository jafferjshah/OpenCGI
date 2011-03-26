package com.cgi.open.invocation.model;

public class Argument {
	public Argument(String argName, String argClass) throws ClassNotFoundException {
		Class argClassType = Class.forName(argClass);
		this.setArgName(argName);
		this.setArgClass(argClass);
	}
	public String getArgName() {
		return argName;
	}
	public void setArgName(String argName) {
		this.argName = argName;
	}
	public String getArgClass() {
		return argClass;
	}
	public void setArgClass(String argClass) {
		this.argClass = argClass;
	}
	private String argName;
	private String argClass;
	public String toString() {
		return getArgClass() + " " + getArgName();
	}
}
