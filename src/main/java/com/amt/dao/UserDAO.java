package com.amt.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amt.app.Constants;
import com.amt.app.Constants.enumUserType;
import com.amt.dto.UserDTO;
import com.amt.model.EmployeeRole;
import com.amt.model.User;
import com.amt.model.UserType;

@Repository
public class UserDAO implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(UserDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public User getUserByUsername(String sName) throws Exception {
		String sMethod = csCRT + "getUserByUsername(): sUsername: [" + sName + "]";
		objLogger.trace(csCR + sMethod + "Entered");
		User objUser = new User();

		String sHQL = "FROM User u WHERE u.username = :username";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName + "]");

		Session session = sessionFactory.getCurrentSession();
		try {
			objUser = (User) session.createQuery(sHQL).setParameter("username", sName).getSingleResult();
			objLogger.debug(sMethod + "object from databae: objUser: [" + objUser + "]");
			return objUser;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingUserByUsername);
			objLogger.warn(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName() + "] [" + e.toString() + "]");
			objLogger.warn(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingUserByUsername);
		}
	}
	
	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public User addNewUser(UserDTO objUserDTO) throws Exception {
		String sMethod = csCRT + "addNew(): objUserDTO: [" + objUserDTO.toString() + "]";
		objLogger.trace(csCR + sMethod + "Entered");

		// by this time the service layer would have validated the parameters
		String sUsername = objUserDTO.getUsername();
		String sFirstName = objUserDTO.getFirstName();
		String sLastName = objUserDTO.getLastName();
		String sPassword = objUserDTO.getPassword();
		String sPasswordSalt = objUserDTO.getPasswordSalt();
		String sEmail = 	objUserDTO.getEmail();
		UserType objUserType = objUserDTO.getUserType();
		EmployeeRole objEmployeeRole = objUserDTO.getEmployeeRole();
		
		User objNewUser = new User(sUsername, sPassword, sPasswordSalt, sFirstName, sLastName, sEmail);
		objNewUser.setUserType(objUserType);
		objNewUser.setEmployeeRole(objEmployeeRole);
		
		objLogger.debug(sMethod + "object being added to databae: objNewUser: [" + objNewUser + "]");
		Session session = sessionFactory.getCurrentSession();
		try {			
			session.persist(objNewUser);
			objLogger.debug(sMethod + "new user object returned from database add: objNewUser: [" + objNewUser + "]");
			return objNewUser;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorAddingUser);
			objLogger.warn(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName() + "] [" + e.toString() + "]");
			objLogger.warn(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorAddingUser);
		}

	}

	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public UserType getUserTypeByName(String sName) throws Exception {
		String sMethod = csCRT + "getUserTypeByName(): ";
		objLogger.trace(sMethod + "Entered: sName: [" + sName + "]");

		UserType ojbUserType = new UserType();

		String sHQL = "FROM UserType ut WHERE ut.userType = :userType";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName + "]");

		objLogger.debug(sMethod + "getting sessionFactory.getCurrentSession()");
		Session session;

		try {
			session = sessionFactory.getCurrentSession();
			objLogger.debug(sMethod + "session.isConnected(): [" + session.isConnected() + "]");
		} catch (Exception e) {
			objLogger.warn(sMethod + "Exception: getting sessionFactory.getCurrentSession()");
			/*
			 * objLogger.warn(sMethod + "Exception: cause: [" + e.getCause() +
			 * "] class name [" + e.getClass().getName() + "] [" + e.toString() + "]");
			 * objLogger.warn(sMethod + "Exception: toString: [" + e.toString() +
			 * " message: [" + e.getMessage() + "]"); throw new
			 * Exception(csMsgDB_ErrorGetCurrentSession);
			 */
			objLogger.warn(sMethod + "Attempting sessionFactory.openSession()");
			try {
				session = sessionFactory.openSession();
			} catch (Exception e2) {
				objLogger.warn(sMethod + "Exception: getting sessionFactory.getCurrentSession()");
				objLogger.warn(sMethod + "Exception: cause: [" + e2.getCause() + "] class name ["
						+ e2.getClass().getName() + "] [" + e2.toString() + "]");
				objLogger.warn(
						sMethod + "Exception: toString: [" + e2.toString() + " message: [" + e2.getMessage() + "]");
				throw new Exception(csMsgDB_ErrorOpenSession);
			}
		}		
		

		try {
			ojbUserType = (UserType) session.createQuery(sHQL).setParameter("userType", sName).getSingleResult();
			objLogger.debug(sMethod + "object from databae: ojbUserType: [" + ojbUserType + "]");
			return ojbUserType;
		} catch (Exception e3) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingUserTypeByName + " sName: [" + sName + "]");
			objLogger.warn(sMethod + "Exception: cause: [" + e3.getCause() + "] class name [" + e3.getClass().getName()
					+ "] [" + e3.toString() + "]");
			objLogger.warn(sMethod + "Exception: toString: [" + e3.toString() + " message: [" + e3.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingUserTypeByName);
		}
		
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public EmployeeRole getEmployeeRoleByName(String sName) throws Exception {
		String sMethod = csCRT + "getByName(): ";
		objLogger.trace(csCR + sMethod + "Entered: sName: [" + sName + "]");

		EmployeeRole objEmployeeRole = new EmployeeRole();

		String sHQL = "FROM EmployeeRole er WHERE er.employeeRole = :employeeRole";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName + "]");

		objLogger.debug(sMethod + "getting sessionFactory.getCurrentSession()");
		Session session = getSession();

		try {
			objEmployeeRole = (EmployeeRole) session.createQuery(sHQL).setParameter("employeeRole", sName).getSingleResult();
			objLogger.debug(sMethod + "object from databae: ojbUserType: [" + objEmployeeRole + "]");
			return objEmployeeRole;
		} catch (Exception e3) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingEmployeeRoleByName + " sName: [" + sName + "]");
			objLogger.warn(sMethod + "Exception: cause: [" + e3.getCause() + "] class name [" + e3.getClass().getName()
					+ "] [" + e3.toString() + "]");
			objLogger.warn(sMethod + "Exception: toString: [" + e3.toString() + " message: [" + e3.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingEmployeeRoleByName);
		}
		
	}


	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	private Session getSession() throws Exception {
		String sMethod = csCRT + "getSession(): ";
		objLogger.trace(sMethod + "Entered.");
		
		Session session;

		try {
			session =  sessionFactory.getCurrentSession();
			objLogger.debug(sMethod + "session.isConnected(): [" + session.isConnected() + "]");
		} catch (Exception e) {
			objLogger.warn(sMethod + "Exception: getting sessionFactory.getCurrentSession()");
			objLogger.debug(sMethod + "Attempting sessionFactory.openSession()");
			try {
				session = sessionFactory.openSession();
			} catch (Exception e2) {
				objLogger.debug(sMethod + "Exception: opening sessionFactory.openSession()");
				objLogger.warn(sMethod + "Exception: cause: [" + e2.getCause() + "] class name ["
						+ e2.getClass().getName() + "] [" + e2.toString() + "]");
				objLogger.warn(
						sMethod + "Exception: toString: [" + e2.toString() + " message: [" + e2.getMessage() + "]");
				throw new Exception(csMsgDB_ErrorOpenSession);
			}
		}		
		return session;
	}
	
	
	
}//END Class
