package com.cgi.open.easyshare.model;

import java.util.HashSet;
import java.util.Set;

import com.cgi.open.easyshare.UserTypeNotValidException;

public enum UserType {
	ADMIN,
	FACILITATOR,
	ATTENDEE,
	ALL;
	public static UserType getUserType(String userType)
	throws UserTypeNotValidException {
		if(userType.equals("ADMIN")) {
			return UserType.ADMIN;
		}
		if(userType.equals("FACILITATOR")) {
			return UserType.FACILITATOR;
		}
		if(userType.equals("ATTENDEE")) {
			return UserType.ATTENDEE;
		}
		if(userType.equals("ALL")) {
			return UserType.ALL;
		}
		throw new UserTypeNotValidException();
	}
	public static Set<UserType> getUserType(Set<String> userTypes) 
	throws UserTypeNotValidException {
		Set<UserType> retUserTypes = new HashSet<UserType>();
		for(String userType : userTypes) {
			retUserTypes.add(getUserType(userType));
		}
		return retUserTypes;
	}
}
