package com.cgi.open.easyshare.model;
//represents a message unit of a discussion.might include resources.
public class Message 
{
	private String subject;
	private String text;
	private Resource resource;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	public String toString()
	{
		return("text:"+text+"\nResource:"+resource);
	}
	
	public int hashCode()
	{
		int hash=31*subject.hashCode()+31*text.hashCode()+31*resource.hashCode();
		hash=hash/101;
		System.out.println(this + " hashCode called : " + hash);

		return hash;
	}
	
	public boolean equals(Object obj)
	{
		if(obj==null||!(obj instanceof Message))
		{
			return false;
		}
		Message msgObj=(Message)obj;
		System.out.println(this + " AND " + msgObj + " equals called");

		return(this.subject.equals(msgObj.subject));
	}

}
