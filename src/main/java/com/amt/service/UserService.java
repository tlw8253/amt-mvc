package com.amt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.amt.app.Constants;
import com.amt.model.User;

@Service
public class UserService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(UserService.class);


	public User getUserByUsername(String sUsername) {
		String sMethod = csCRT + "getUserByUsername(): ";
		objLogger.trace(csCR + sMethod + "Entered: sUsername [" + sUsername + "]");

		
		return new User();

	}
}
