package com.cgi.open.userconcerns.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

import com.thoughtworks.xstream.MarshallingStrategy;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ServiceGroupDef {
	/**
	 * The group of service definitions.
	 */
	private Set<ServiceDef> serviceDefinitions = new HashSet<ServiceDef>();

	public Set<ServiceDef> getServiceDefinitions() {
		return serviceDefinitions;
	}

	public void setServiceDefinitions(Set<ServiceDef> serviceDefinitions) {
		this.serviceDefinitions = serviceDefinitions;
	}
	
	public void addServiceDefinition(ServiceDef serviceDef) {
		serviceDefinitions.add(serviceDef);
	}
	
	public static void main(String ... args) {
		ServiceGroupDef g = new ServiceGroupDef();
		ServiceDef sd; 
		sd = new ServiceDef();
		sd.setServiceName("createSession");
		Set<String> elig = new HashSet<String>();
		elig.add("ADMIN");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.FALSE);
		g.addServiceDefinition(sd);
		sd = new ServiceDef();
		sd.setServiceName("addResource");
		elig = new HashSet<String>();
		elig.add("FACILITATOR");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.TRUE);
		g.addServiceDefinition(sd);
		sd = new ServiceDef();
		sd.setServiceName("getMySessions");
		elig = new HashSet<String>();
		elig.add("ADMIN");
		elig.add("FACILITATOR");
		elig.add("ATTENDEE");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.TRUE);
		g.addServiceDefinition(sd);
		
		XStream x = new XStream(new DomDriver());
		x.alias("ServiceDefinitionGroup", ServiceGroupDef.class);
		x.alias("ServiceDefinition", ServiceDef.class);
		x.addImplicitCollection(ServiceGroupDef.class, "serviceDefinitions");
		System.out.println(x.toXML(g));
		File file = new File("UserConcerns.xml");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			x.toXML(g, fos);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}