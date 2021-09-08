package com.amt.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amt.config.ORMConfig;
import com.amt.dao.UserTypeDAO;
import com.amt.model.UserType;

public class Driver implements Constants {
	private static Logger objLogger = LoggerFactory.getLogger(Driver.class);

	public static void main(String[] args) {
		String sMethod = csCRT + "(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		ORMConfig obj = new ORMConfig();
		UserTypeDAO objUserTypeDAO = new UserTypeDAO();
		UserType objUserType = objUserTypeDAO.getByName(csarUserType[enumUserType.CUSTOMER.pos]);
		objLogger.debug(sMethod + "objUserType: [" + objUserType + "]");
	}

}
