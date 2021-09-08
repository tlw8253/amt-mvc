package com.amt.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amt.app.Constants;
import com.amt.exception.*;
import com.amt.model.User;
import com.amt.util.PasswordUtil;

@Repository
public class LoginDAO implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(LoginDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public User getLogin(String sUsername, String sPassword) throws DatabaseException, RecordNotFoundException, AuthenticationFailureException {
		String sMethod = csCRT + "getLogin(): sUsername: [" + sUsername + "]";
		objLogger.trace(sMethod + "Entered");
		User objUser = new User();

		String sHQL = "FROM User u WHERE u.username = :username";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sUsername + "]");

		Session session = sessionFactory.getCurrentSession();
		try {
			objLogger.debug(sMethod + "Authenticating username: [" + sUsername + "] with password provided.");
			objUser = (User) session.createQuery(sHQL).setParameter("username", sUsername).getSingleResult();

			objLogger.debug(sMethod + "found user: [" + objUser.toString() + "] in database now validate password.");
			String sEncryptedPwd = objUser.getPassword();
			String sSalt = objUser.getPasswordSalt();
			boolean bPasswordValid = PasswordUtil.verifyUserPassword(sPassword, sEncryptedPwd, sSalt);

			if (bPasswordValid) {
				objLogger.debug(sMethod + "user: [" + objUser.toString() + "]" + csCRT + "DID pass encrypted password validation with salt key.");
				return objUser;
			} else {
				objLogger.debug(sMethod + csMsgAutenticationFailed);
				objLogger.warn(sMethod + "user: [" + objUser.toString() + "]" + csCRT + "DID NOT pass encrypted password validation with salt key.");
				throw new AuthenticationFailureException(csMsgAutenticationFailed);
			}
			
			
		} catch (NoResultException e) {
			objLogger.debug(sMethod + "NoResultException: [" + e.getMessage() + "]");
			throw new RecordNotFoundException(csMsgDB_NotFoundGettingUserByUsername);			
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingUserByUsername);
			objLogger.warn(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName() + "] [" + e.toString() + "]");
			objLogger.warn(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingUserByUsername);
		}
	}
	
	

	
	
}//END Class
