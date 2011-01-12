package com.cgi.open.easyshare.model;

/**
 * The User class. 
 */
public class User {
	private String name;
	private Integer empid;
	private String email;
	private UserType userType;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getEmpid() {
		return empid;
	}
	public void setEmpid(int empid) {
		this.empid = empid;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb
			.append(getUserType())
			.append(": ")
			.append(getName())
			.append(" (")
			.append(getEmpid())
			.append("-")
			.append(getEmail())
			.append(")")
		;
		return sb.toString();
	}
	public int hashCode() {
		int hash = getEmpid().hashCode()+getUserType().hashCode();
		//System.out.println(this + " hashCode called : " + hash);
		return hash;
	}
	public boolean equals(Object obj) {
		if(obj==null||!(obj instanceof User)) {
			return false;
		}
		User userObj=(User)obj;
		//System.out.println(this + " AND " + userObj + " equals called");
		return(this.getEmpid()==userObj.getEmpid())&&(this.getUserType().equals(userObj.getUserType()));
	}
}
