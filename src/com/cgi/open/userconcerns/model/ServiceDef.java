package com.cgi.open.userconcerns.model;

import java.util.Set;

public class ServiceDef {
	/**
	 * The service name to be invoked
	 */
	private String serviceName;
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	/**
	 * Eligible user types (ADMIN, FACILITATOR, ATTENDEE)
	 */
	private Set<String> eligUserTypes;
	public Set<String> getEligUserTypes() {
		return eligUserTypes;
	}
	public void setEligUserTypes(Set<String> eligUserTypes) {
		this.eligUserTypes = eligUserTypes;
	}
	/**
	 * Whether session-user relation exists (TRUE, FALSE)
	 * If TRUE, the user id is authenticated against the
	 * session whose services are requested for.
	 */
	private Boolean sessionRelationExists;
	public Boolean getSessionRelationExists() {
		return sessionRelationExists;
	}
	public void setSessionRelationExists(Boolean sessionRelationExists) {
		this.sessionRelationExists = sessionRelationExists;
	}
}
