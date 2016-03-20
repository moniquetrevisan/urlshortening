package com.moniquetrevisan.urlshortening.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.moniquetrevisan.urlshortening.jsonobject.User;
import com.moniquetrevisan.urlshortening.persistence.UserPersistence;

public class UserService {
	
	private static Logger LOGGER = Logger.getLogger(UserService.class.getName());
	
	private UserPersistence userPersistence;

	public UserService() throws Exception {
		this.userPersistence = new UserPersistence();
	}

	public User createUser(String userId) {
		User user = new User(userId);
		try {
			if(userPersistence.createUser(user)){
				return user;
			} 
			user.setId(null);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
		}
		return user;
	}
}
