package com.cgi.open.easyshare.model;

//collection of resources uploaded by the trainer.

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ResourcePool {
	private Set<Resource> set;

	public Set<Resource> getSet() {
		return set;
	}

	public void setSet(Set<Resource> set) {
		this.set = set;
	}

	public ResourcePool() {
		set = new HashSet<Resource>();
	}

	public void addResource(Resource resource) {
		set.add(resource);
	}

	public Resource getResource(String name) {
		Resource result = null;
		for (Iterator<Resource> it = set.iterator(); it.hasNext();) {
			result = it.next();
			if (result.getResourceName() == name) {
				break;
			}
		}
		return result;
	}

	public String toString() {
		String resString = null;
		for (Iterator<Resource> it = set.iterator(); it.hasNext();) {
			resString += "\t" + it.next();
		}

		return resString;
	}

	public int hashCode() {
		int hash = 0;
		for (Iterator<Resource> it = set.iterator(); it.hasNext();) {
			hash += it.next().hashCode();
		}
		System.out.println(this + " hashCode called : " + hash);
		return hash;
	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ResourcePool)) {
			return false;
		}
		ResourcePool poolObj = (ResourcePool) obj;
		System.out.println(this + " AND " + poolObj + " equals called");

		return (this.set.equals(poolObj.set));
	}

}
