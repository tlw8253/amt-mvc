package com.amt.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amt.app.Constants;
import com.amt.dto.UserDTO;
import com.amt.model.User;
import com.amt.model.UserType;

@Repository
public class UserTypeDAO implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(UserTypeDAO.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public UserType getByName(String sName) {
		String sMethod = csCRT + "getByName(): ";
		objLogger.trace(csCR + sMethod + "Entered: sName: [" + sName + "]");
		
		UserType ojbUserType = new UserType();
		
		String sHQL = "FROM UserType ut WHERE ut.userType = :userType";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName + "]");

		objLogger.debug(sMethod + "getting sessionFactory.getCurrentSession()");
		Session session = sessionFactory.getCurrentSession();
		objLogger.debug(sMethod + "session.isConnected(): [" + session.isConnected() + "]");

		
		
		try {
			ojbUserType = (UserType) session.createQuery(sHQL).setParameter("userType", sName).getSingleResult();
			objLogger.debug(sMethod + "object from databae: ojbUserType: [" + ojbUserType + "]");
			return ojbUserType;
		} catch (Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName() + "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			return ojbUserType;  //return empty
		}
	}
	
	
	@Transactional
	public UserType addNew() {
		String sMethod = csCRT + "addNew(): "; //objUserDTO: [" + objUserDTO.toString() + "]";
		objLogger.trace(csCR + sMethod + "Entered");
		UserType objUserType = new UserType();
		
		// by this time the service layer would have validated the parameters

		
		return objUserType;
	}

	
	
	
	
	
	
}//END Class
