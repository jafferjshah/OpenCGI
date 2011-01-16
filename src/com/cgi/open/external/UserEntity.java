package com.cgi.open.external;

public enum UserEntity {
	EMAIL,
	EMP_ID;
	public UserEntity getUserEntity(String uEn) {
		UserEntity ret = null;
		if(uEn.equals(UserEntity.EMAIL)) {
			ret = UserEntity.EMAIL;
		}
		if(uEn.equals(UserEntity.EMP_ID)) {
			ret = UserEntity.EMP_ID;
		}
		return ret;
	}
}
