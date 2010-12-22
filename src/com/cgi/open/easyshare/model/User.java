package com.cgi.open.easyshare.model;

public abstract class User {

	private String name;
	private Integer empid;
	private String email;

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

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String toString() {
		return ("\nemployee id:" + empid + "\nname:" + name + "\nemail" + email);
	}

	public int hashCode() {
		int hash = empid.hashCode();
		System.out.println(this + " hashCode called : " + hash);
		return hash;
	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof User)) {
			return false;
		}
		User userObj = (User) obj;
		System.out.println(this + " AND " + userObj + " equals called");
		return (this.empid.equals(userObj.empid));
	}

}
