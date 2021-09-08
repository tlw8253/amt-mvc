package com.amt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amt.app.Constants;
import com.amt.dao.LoginDAO;
import com.amt.dao.UserDAO;
import com.amt.dto.AddCustomerDTO;
import com.amt.dto.LoginDTO;
import com.amt.dto.UserDTO;
import com.amt.exception.AuthenticationFailureException;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.exception.RecordNotFoundException;
import com.amt.model.EmployeeRole;
import com.amt.model.User;
import com.amt.model.UserType;
import com.amt.util.PasswordUtil;
import com.amt.util.Validate;

@Service
public class LoginService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(LoginService.class);
	
	private LoginDAO objLoginDAO;

	@Autowired
	public LoginService(LoginDAO objLoginDAO) {
		String sMethod = csCRT + "LoginService(): ";
		objLogger.trace(sMethod + "Construtor Entered.");
		
		this.objLoginDAO = objLoginDAO;
	}


	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public User login(LoginDTO objLoginDTO)
			throws BadParameterException, AuthenticationFailureException {
		String sMethod = csCRT + "login(): ";
		objLogger.trace(csCR + sMethod + "Entered.");

		String sUsername = objLoginDTO.getUsername();
		String sPassword = objLoginDTO.getPassword();

		boolean bValidUsername = UserService.isValidUsername(sUsername);
		boolean bValidPassword = UserService.isValidPassword(sPassword);

		if (!bValidUsername || !bValidPassword) {
			objLogger.debug(
					sMethod + "Invalid parameters sUsername: [" + sUsername + "] and/ or Password format is invalid.");
			throw new BadParameterException(csMsgBadParamLoginUsernamePwdFormat);
		}

		User objUser;
		try {
			objUser = objLoginDAO.getLogin(sUsername, sPassword);
			if (objUser == null) {
				objLogger.debug(
						sMethod + "Authentication failed with sUsername: [" + sUsername + "] and Password provided.");
				throw new AuthenticationFailureException(csMsgAutenticationFailed);
			}
			return objUser;

		} catch (DatabaseException | RecordNotFoundException | AuthenticationFailureException e) {
			objLogger.debug(sMethod + "Exception caught while authenticating username: [" + sUsername + "]");
			objLogger.debug(sMethod + "Exception message: [" + e.getMessage() + "]");
			throw new AuthenticationFailureException(csMsgAutenticationFailed);
		}
	}

	
	
	////////////////////// Utility Methods for this Class /////////////////////////////////////////
	//

	
	
	
	
}//END Class