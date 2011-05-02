package com.cgi.open.invocation.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ServiceDefinition {
	private String serviceName;
	private String serviceMockName;
	private List<Argument> arguments = new ArrayList<Argument>();

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public String getServiceMockName() {
		if (serviceMockName == null) {
			return serviceName;
		}
		return serviceMockName;
	}

	public void setServiceMockName(String serviceMockName) {
		this.serviceMockName = serviceMockName;
	}

	public void addArgument(String argName, String argClass)
			throws ClassNotFoundException {
		Argument arg = new Argument(argName, argClass);
		arguments.add(arg);
	}

	public List<Argument> getArguments() {
		return this.arguments;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("Service: ").append(this.getServiceName())
				.append(this.getArguments());
		return strBuilder.toString();
	}

	public static Set<ServiceDefinition> getDefinitions(String fileNameWithPath)
			throws FileNotFoundException {
		XStream xstream = new XStream(new DomDriver());
		ServiceDefinition.applyAliases(xstream);
		File serviceDef = new File(fileNameWithPath);
		Set<ServiceDefinition> servDefs = (Set<ServiceDefinition>) xstream
				.fromXML(new FileInputStream(serviceDef));
		return servDefs;
	}
	
	public static void applyAliases(XStream xstream) {
		xstream.aliasType("Service", ServiceDefinition.class);
		xstream.aliasType("Argument", Argument.class);
	}
	
	public static void setDefinitions(String fileNameWithPath) throws ClassNotFoundException, IOException {
		ServiceDefinition serv;
		Set<ServiceDefinition> servDefs = new HashSet<ServiceDefinition>();
		serv = new ServiceDefinition();
		serv.setServiceName("createSession");
		serv.addArgument("sessionName", "java.lang.String");
		servDefs.add(serv);
		XStream xstream = new XStream(new DomDriver());
		ServiceDefinition.applyAliases(xstream);
		FileOutputStream fos = new FileOutputStream(new File(fileNameWithPath));
		xstream.toXML(servDefs, fos);
		fos.close();
	}

	public static void main(String... args) throws ClassNotFoundException,
			IOException {
		ServiceDefinition.setDefinitions("config\\ServiceDefinition.xml");
		System.out.println(ServiceDefinition.getDefinitions("config\\ServiceDefinition.xml"));
	}
}
