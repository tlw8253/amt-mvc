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

	@Transactional
	public User getByName(String sName) {
		String sMethod = csCRT + "getByName(): sUsername: [" + sName + "]";
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
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName() + "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			return objUser;  //return empty
		}
	}
	
	
	@Transactional
	public User addNew(UserDTO objUserDTO) {
		String sMethod = csCRT + "addNew(): objUserDTO: [" + objUserDTO.toString() + "]";
		objLogger.trace(csCR + sMethod + "Entered");
		User objUser = new User();
		
		// by this time the service layer would have validated the parameters
		String sUsername = objUserDTO.getUsername();
		String sFirstName = objUserDTO.getFirstName();
		String sLastName = objUserDTO.getLastName();
		String sPassword = objUserDTO.getPassword();
		String sPasswordSalt = objUserDTO.getPasswordSalt();
		String sEmail = 	objUserDTO.getEmail();
		String sUserType = objUserDTO.getUserType();
		String sEmployeeRole = objUserDTO.getEmployeeRole();
		
		User objNewUser = new User(sUsername, sPassword, sPasswordSalt, sFirstName, sLastName, sEmail);
		objLogger.debug(sMethod + "objNewUser: [" + objNewUser.toString() + "]");
/*
		// get UserType object
		UserTypeDAO objUserTypeDAO = new UserTypeDAO();
		UserType objUserType = objUserTypeDAO.getByName(sUserType);
		objNewUser.setUserType(objUserType);
		objLogger.debug(sMethod + "objUserType: [" + objUserType.toString() + "]");

		if (objUserType.getUserType().equalsIgnoreCase(csarUserType[enumUserType.EMPLOYEE.pos])) {
			// get EmployeeRole object
			EmployeeRoleDAO objEmployeeRoleDAO = new EmployeeRoleDAO();
			EmployeeRole objEmployeeRole = objEmployeeRoleDAO.getByName(sEmployeeRole);
			objNewUser.setEmployeeRole(objEmployeeRole);
			objLogger.debug(sMethod + "objEmployeeRole: [" + objEmployeeRole.toString() + "]");				
		} else {
			objNewUser.setEmployeeRole(new EmployeeRole());
		}
*/

		
		objLogger.debug(sMethod + "object being added to databae: objNewUser: [" + objNewUser + "]");
		Session session = sessionFactory.getCurrentSession();
		try {			
			session.persist(objNewUser);
			objLogger.debug(sMethod + "object from databae: objUser: [" + objUser + "]");
			return objUser;
		} catch (Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName() + "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			return objUser;  //return empty
		}

	}

	
	
	
	
	
	
}//END Class
