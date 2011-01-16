package com.cgi.open.userconcerns.model;

import java.util.Set;

public class ServiceGroupDef {
	/**
	 * The group of service definitions.
	 */
	private Set<ServiceDef> serviceDefinitions;

	public Set<ServiceDef> getServiceDefinitions() {
		return serviceDefinitions;
	}

	public void setServiceDefinitions(Set<ServiceDef> serviceDefinitions) {
		this.serviceDefinitions = serviceDefinitions;
	}
}