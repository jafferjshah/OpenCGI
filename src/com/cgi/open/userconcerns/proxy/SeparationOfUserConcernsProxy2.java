package com.cgi.open.userconcerns.proxy;

import java.util.Set;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.easyshare.model.UserType;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;

public class SeparationOfUserConcernsProxy2 implements SeparationOfUserConcerns {
	/**
	 * 
	 * @param serviceDef
	 * @throws SessionNotFoundException
	 */
	public Boolean isServReqValid(ServiceDef _serviceDef, String _email,
			Integer _sessionId) 
	throws UserTypeNotValidException, SessionNotFoundException {
		EasyShareServices easyShare = ServicesMapper
				.getEasyShareServicesProxyInstance();
		/*
		 * Whether the user has valid user type to access the service
		 */
		Boolean validUser = Boolean.TRUE;
		Boolean validSessionAccess = Boolean.FALSE;
		UserType userType=null;
		
		
		if (_email == null) {
					/*
					 * User is needed for any service to be accessed.
					 */
					return Boolean.FALSE;
				}
		if (_serviceDef.getSessionRelationExists()) {
			if (_sessionId == null) {
				/*
				 * Session Id is required to check whether user is related to
				 * the session.
				 */
				return Boolean.FALSE;
			} 
			else{
				Set<UserType> eligUserTypes = UserType.getUserType(_serviceDef.getEligUserTypes());
						for (UserType uType : eligUserTypes) {
							if (uType.equals(UserType.ADMIN)){
								Set<String>admins=easyShare.getAllUsersLight(_sessionId, UserType.ADMIN);
								try {
										easyShare.getUser(_email, uType);
										validSessionAccess=admins.contains(_email);
								} catch (UserNotFoundException e) {
										validUser = Boolean.FALSE;
								}
					
								if(validSessionAccess&&validUser){
									break;
								}
										
							}
							else if(uType.equals(UserType.FACILITATOR)) {
								Set<String>facilitators=easyShare.getAllUsersLight(_sessionId, UserType.FACILITATOR);
								try {
										easyShare.getUser(_email, uType);
										validSessionAccess=facilitators.contains(_email);
								} catch (UserNotFoundException e) {
										validUser = Boolean.FALSE;
								}
					
								if(validSessionAccess&&validUser){
										break;
								}
							}
							else if (uType.equals(UserType.ATTENDEE)){
						
								Set<String>attendees=easyShare.getAllUsersLight(_sessionId, UserType.ATTENDEE);
								validSessionAccess=attendees.contains(_email);
								validUser=Boolean.TRUE;
								if(validSessionAccess&&validUser){
										break;
								}
							}
							else if(uType.equals(UserType.ALL)) {
								Set<String>users=easyShare.getAllUsersLight(_sessionId, UserType.ALL);
								validSessionAccess=users.contains(_email);
								validUser=Boolean.TRUE;
								if(validSessionAccess&&validUser){
										break;
								}
							}
				
						}//end of for
			}//end of inner else
		}//end of if (_serviceDef.getSessionRelationExists()) 
			else{
				validSessionAccess=Boolean.TRUE;
			}
			
		
		//System.out.println("validUserType : " + validUserType);
		/*
		 * Whether the user is valid to access the session
		 */
		
		return (validSessionAccess&&validUser);
	

}//end of method
}//end of class
