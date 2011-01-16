package com.cgi.open.userconcerns.proxy;

import java.util.Set;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.SessionNotAvailableException;
import com.cgi.open.easyshare.UserNotAvailableException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.easyshare.model.UserType;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;

public class SeparationOfUserConcernsProxy implements SeparationOfUserConcerns {
	/**
	 * 
	 * @param serviceDef
	 * @throws SessionNotAvailableException
	 */
	public Boolean isServReqValid(ServiceDef _serviceDef, String _email,
			Integer _sessionId) 
	throws UserTypeNotValidException, SessionNotAvailableException {
		EasyShareServices easyShare = ServicesMapper
				.getEasyShareServicesProxyInstance();
		/*
		 * Whether the user has valid user type to access the service
		 */
		Boolean validUserType = Boolean.TRUE;
		Boolean validSessionAccess = Boolean.TRUE;
		if (_email == null) {
			/*
			 * User is needed for any service to be accessed.
			 */
			return Boolean.FALSE;
		} else {
			Set<UserType> eligUserTypes = UserType.getUserType(_serviceDef
					.getEligUserTypes());
			for (UserType uType : eligUserTypes) {
				if (uType.equals(UserType.ADMIN)
						|| uType.equals(UserType.FACILITATOR)) {
					try {
						easyShare.getUser(_email, uType);
						validUserType = Boolean.TRUE;
						break;
					} catch (UserNotAvailableException ex) {
						validUserType = Boolean.FALSE;
					}
				}
				if (uType.equals(UserType.ATTENDEE)
						|| uType.equals(UserType.ALL)) {
					validUserType = Boolean.TRUE;
					break;
				}
			}
			if (!validUserType) {
				return Boolean.FALSE;
			}
		}
		System.out.println("validUserType : " + validUserType);
		/*
		 * Whether the user is valid to access the session
		 */
		if (_serviceDef.getSessionRelationExists()) {
			if (_sessionId == null) {
				/*
				 * Session Id is required to check whether user is related to
				 * the session.
				 */
				return Boolean.FALSE;
			} 
			else {
				Set<String> userEmails = easyShare.getAllUsersLight(_sessionId);
				validSessionAccess = userEmails.contains(_email);
			}
		}
		return (validUserType && validSessionAccess);
	}

}
