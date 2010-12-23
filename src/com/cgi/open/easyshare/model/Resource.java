package com.cgi.open.easyshare.model;
//represents training material uploaded by the trainer.

public class Resource
{
	private String resourceName;
	private String url;
	
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String toString()
	{
		return("\nURL:"+url+"\nResource Name:"+resourceName);
	}
	
	public int hashCode()
	{
		int hash=url.hashCode();
		System.out.println(this + " hashCode called : " + hash);
		return hash;
	}
	
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
