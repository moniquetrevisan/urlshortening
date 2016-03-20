package com.moniquetrevisan.urlshortening.service;

import com.moniquetrevisan.urlshortening.jsonobject.User;
import com.moniquetrevisan.urlshortening.persistence.UserPersistence;

public class UserService {

	private UserPersistence userPersistence;

	public UserService() {
		this.userPersistence = new UserPersistence();
	}

	public User createUser(String userId) {
		User user = new User(userId);
		if (userPersistence.createUser(user)) {
			return user;
		}
		user.setId(null);
		return user;
	}

	public boolean deleteUser(String userId) {
		if (userPersistence.deleteUser(userId)) {
			return true;
		}
		return false;
	}

}
