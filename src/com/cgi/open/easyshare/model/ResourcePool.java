package com.cgi.open.EasyShare.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Resource pool class represents the collection of resources uploaded by the facilitator.
 * A facilitator can add or delete resources from the resource pool.
 * Facilitator pulls resource from the resource pool when he/she wants to share the resource with the attendees.
 *
 */

public class ResourcePool 
{
	/**
	 * hold the set of resources uploaded by the facilitator.
	 *
	 */
	private Set<Resource> set;

	/**Get set
	 * @return set
	 */
	public Set<Resource> getSet() {
		return set;
	}

	/**Set set
	 * @param set
	 */
	public void setSet(Set<Resource> set) {
		this.set = set;
	}

	public ResourcePool() {
		set = new HashSet<Resource>();
	}
	
	/**This method adds resource to the resourcepool
	 * @param resource
	 */
	public void addResource(Resource resource) {
		set.add(resource);
	}
	
	
	/**This method returns resource searching by name
	 * @param name
	 * @return
	 */
	public Resource getResource(String name) {
		Resource result = null;
		for(
				Iterator<Resource> it = set.iterator();
				it.hasNext();) {
			result = it.next();
			if(result.getResourceName() == name) {
				break;
			}
		}
		return result;
	}
	
	/**
	 * The String representation of the ResourcePool object
	 * 
	 * @return
	 */	
	public String toString()
	{
		String resString=null;
		for(Iterator<Resource> it = set.iterator();
				it.hasNext();)
		{
			resString+="\t"+it.next();
		}
		
		return resString;
	}
	
	/**
	 * hashCode implementation
	 * 
	 * @return 
	 */
	public int hashCode()
	{
		int hash=0;
		for(Iterator<Resource> it = set.iterator();
		it.hasNext();)
		{
			hash+=it.next().hashCode();
		}
		System.out.println(this + " hashCode called : " + hash);
		return hash;
	}
	
	/**
	 * equals implementation
	 * 
	 * @return boolean
	 */
	
	public boolean equals(Object obj)
	{
		if(obj==null||!(obj instanceof ResourcePool))
		{
			return false;
		}
		ResourcePool poolObj=(ResourcePool)obj;
		System.out.println(this + " AND " + poolObj + " equals called");

		return(this.set.equals(poolObj.set));
	}
	
	
}
