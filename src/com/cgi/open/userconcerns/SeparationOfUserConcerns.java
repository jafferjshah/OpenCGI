package com.cgi.open.userconcerns;

import com.cgi.open.easyshare.SessionNotAvailableException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.userconcerns.model.ServiceDef;

public interface SeparationOfUserConcerns {
	/**
	 * The service checked the validity of the service request using the service
	 * definition provided.
	 * 
	 * @param serviceDef
	 * @param userId
	 * @param sessionId
	 * @param userType
	 * @return Boolean.TRUE if valid, Boolean.FALSE otherwise
	 */
	public Boolean isServReqValid(ServiceDef serviceDef, String email,
			Integer sessionId) 
	throws UserTypeNotValidException, SessionNotAvailableException;
}
