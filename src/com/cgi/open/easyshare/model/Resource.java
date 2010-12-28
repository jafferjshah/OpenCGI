package com.cgi.open.easyshare.model;


/**
 * Resource class represents a single unit of training material uploaded by the facilitator.
 *
 */
public class Resource
{
	/**
	 * uniquely identifies a resource  (Mandatory) 
	 */	
	private Integer resourceId;
	/**
	 * The name of the resource (Mandatory)
	 *  
	 */
	private String resourceName;
	/**
	 * The url of the resource located on the server. (Mandatory)
	 */
	
	private String url;
	/**
	 * Get resource id
	 * @return resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}

	/**
	 * Set resource id
	 * @param resourceId
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * Get resourceName
	 * 
	 * @return resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}
	
	/**
	 * Set resourceName
	 * 
	 * @param resourceName
	 */
	
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	/**Get url
	 * @return url
	 */
	public String getUrl() {
		return url;
	}
	
	/**Set url
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * The String representation of the Resource object
	 * 
	 * @return
	 */	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("#")
			.append(getResourceId())
			.append(": ")
			.append(getResourceName())
			.append(" (")
			.append(getUrl())
			.append(")")
		;
		return sb.toString();
	}
	
	/**
	 * hashCode implementation
	 * 
	 * @return 
	 */
	public int hashCode() {
		int hash = getUrl().hashCode();
		System.out.println(this + " hashCode called : " + hash);
		return hash;
	}
	
	/**
	 * equals implementation
	 * 
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if(obj==null||!(obj instanceof Resource)) {
			return false;
		}
		Resource resObj=(Resource)obj;
		System.out.println(this + " AND " + resObj + " equals called");

		return(this.getUrl().equals(resObj.getUrl()));
	}

}
