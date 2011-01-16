package com.cgi.open.external;

import com.cgi.open.easyshare.UserNotAvailableException;
import com.cgi.open.easyshare.model.User;

public interface UserIntegration {
	public User getActualUser(String param, UserEntity entity)
	throws UserNotAvailableException;
}
