package com.amt.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amt.config.ORMConfig;
import com.amt.model.UserType;

public class Driver implements Constants {
	private static Logger objLogger = LoggerFactory.getLogger(Driver.class);

	public static void main(String[] args) {
		String sMethod = csCRT + "(): ";
		objLogger.trace(csCR + sMethod + "Entered");
	}

}
