package com.cgi.open.EasyShare.model;


/**
 * Resource class represents a single unit of training material uploaded by the facilitator.
 *
 */
public class Resource
{
	/**
	 * uniquely identifies a resource 
	 */	
	private Integer resourceId;
	/**
	 * The name of the resource
	 *  
	 */
	private String resourceName;
	/**
	 * The url of the resource located on the server.
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
	public String toString()
	{
		String ret;
		ret="\nURL:"+url+"\nResource Name:"+resourceName;
		return ret;
	}
	
	/**
	 * hashCode implementation
	 * 
	 * @return 
	 */
	public int hashCode()
	{
		int hash=url.hashCode();
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
		if(obj==null||!(obj instanceof Resource))
		{
			return false;
		}
		Resource resObj=(Resource)obj;
		System.out.println(this + " AND " + resObj + " equals called");

		return(this.url.equals(resObj.url));
	}

}
