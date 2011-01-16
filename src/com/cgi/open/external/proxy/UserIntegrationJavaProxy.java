package com.cgi.open.external.proxy;

import com.cgi.open.easyshare.UserNotAvailableException;
import com.cgi.open.easyshare.model.User;
import com.cgi.open.external.UserEntity;
import com.cgi.open.external.UserIntegration;

public class UserIntegrationJavaProxy implements UserIntegration {

	public User getActualUser(String param, UserEntity entity)
			throws UserNotAvailableException {
		User user = null;
		if(entity.equals(UserEntity.EMAIL)) {
			//param = jaffer.shah
			user = new User();
			user.setEmail(param);
			int j = new Double((Math.random() * 100000)).intValue();
			user.setEmpid(j);
			user.setName("DUMMY " + j);
			return user;
		}
		if(entity.equals(UserEntity.EMP_ID)) {
			//param = 077436
			user = new User();
			user.setEmail("dummy." + param);
			user.setEmpid(new Integer(param));
			user.setName("DUMMY " + param);
			return user;
		}
		throw new UserNotAvailableException();
	}
}
