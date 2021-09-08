package com.amt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amt.app.Constants;
import com.amt.app.Constants.enumUserType;
import com.amt.dao.UserDAO;
import com.amt.dao.UserTypeDAO;
import com.amt.dto.AddCustomerDTO;
import com.amt.dto.UserDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.User;
import com.amt.model.UserType;
import com.amt.util.PasswordUtil;
import com.amt.util.Validate;

@Service
public class UserService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(UserService.class);
	
	private UserDAO objUserDAO;

	@Autowired
	public UserService(UserDAO objUserDAO) {
		this.objUserDAO = objUserDAO;
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public User getUserByUsername(String sUsername) throws DatabaseException {
		String sMethod = csCRT + "getUserByUsername(): ";
		objLogger.trace(csCR + sMethod + "Entered: sUsername [" + sUsername + "]");

		User objUser = objUserDAO.getByName(sUsername);
		
		return objUser;
	}
	
	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public User addCustomer(AddCustomerDTO objAddDTO) throws DatabaseException, BadParameterException {
		String sMethod = csCRT + "addCustomer(): ";
		objLogger.trace(csCR + sMethod + "Entered: objAddDTO: [" + objAddDTO.toString() + "]");

		if (isValidAddCustomerDTO(objAddDTO)) {
			try {
				objLogger.debug(sMethod + "Validated objAddDTO: [" + objAddDTO.toString() + "]");				
				
				UserDTO objUserDTO = new UserDTO();
				
				//Encrypt and set password values
				String sPwdSalt = PasswordUtil.getSalt(30);
				String sSecurePassword = PasswordUtil.generateSecurePassword(objAddDTO.getPassword(), sPwdSalt);				

				//transfer from the body dto to the dto for the DAO
				objUserDTO.setUsername(objAddDTO.getUsername());
				objUserDTO.setPassword(sSecurePassword);
				objUserDTO.setPasswordSalt(sPwdSalt);
				objUserDTO.setFirstName(objAddDTO.getFirstName());
				objUserDTO.setLastName(objAddDTO.getLastName());
				objUserDTO.setEmail(objAddDTO.getEmail());

				
				objUserDTO.setUserType(csarUserType[enumUserType.CUSTOMER.pos]);
				objUserDTO.setEmployeeRole(csarEmployeeRoles[enumUserEmployee.CUSTOMER.pos]);

				objLogger.debug(sMethod + "Calling DAO to get customer User Type.");
				UserTypeDAO objUserTypeDAO = new UserTypeDAO();
				UserType objUserType = objUserTypeDAO.getByName(csarUserType[enumUserType.CUSTOMER.pos]);
				objLogger.debug(sMethod + "objUserType: [" + objUserType + "]");

				
				objLogger.debug(sMethod + "calling addNew with objUserDTO: [" + objUserDTO.toString() + "]");	
//				User objUser = objUserDAO.addNew(objUserDTO);
//				objLogger.debug(sMethod + "objUser: [" + objUser.toString() + "]");
				return null; //return objUser;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + "Exception adding User record with username: [" + objAddDTO.getUsername()
						+ "] Exception: [" + e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorAddingUser);
			}

		} else {
			objLogger.warn(sMethod + "objAddUserDTO is not valid: [" + objAddDTO.toString() + "]");
			throw new BadParameterException(csMsgBadParamAddUser);
		}
	}

	
	
	////////////////////// Utility Methods for this Class /////////////////////////////////////////
	//
	public boolean isValidAddCustomerDTO(AddCustomerDTO objUserDTO) {
		String sMethod = csCRT + "isValidUserDTO(): ";
		objLogger.trace(csCR + sMethod + "Entered");
		boolean isValid = false;

		String sUsername = objUserDTO.getUsername();
		String sPassword = objUserDTO.getPassword();
		String sFirstName = objUserDTO.getFirstName();
		String sLastName = objUserDTO.getLastName();
		String sEmail = objUserDTO.getEmail();
		
		boolean bUsernameIsAlphaNumeric = isValidUsername(sUsername);
		boolean bPasswordIsInFormat = isValidPassword(sPassword);
		boolean bFirstNameIsAlpha = Validate.isAlpha(sFirstName);
		boolean bLastNameIsAlpha = Validate.isAlphaPlusLastname(sLastName);
		boolean bEmailIsInFormat = Validate.isValidEmailAddress(sEmail);

		if (bUsernameIsAlphaNumeric &&  bPasswordIsInFormat && bFirstNameIsAlpha && bLastNameIsAlpha &&	bEmailIsInFormat) {
			isValid = true;
		} else {
			objLogger.warn(csCR + sMethod + "One or more add User Parameters did not pass validation.:");
			objLogger.warn(sMethod + "\t username: [" + sUsername + "] is valid: [" + bUsernameIsAlphaNumeric + "]");
			objLogger.warn(sMethod + "\t password correct format: [" + bPasswordIsInFormat + "]");
			objLogger.warn(sMethod + "\t first name: [" + sFirstName + "] is valid: [" + bFirstNameIsAlpha + "]");
			objLogger.warn(sMethod + "\t last name: [" + sLastName + "] is valid: [" + bLastNameIsAlpha + "]");
			objLogger.warn(sMethod + "\t email: [" + sEmail + "] correct format: [" + bEmailIsInFormat + "]");
		}
		return isValid;
	}

	public static boolean isValidUsername(String sUsername) {
		boolean bValid = true;
		
		bValid = Validate.isAlphaNumeric(sUsername) 
				&& sUsername.length() >= ciUsernameMinLength 
				&& sUsername.length() <= ciUsernameMaxLength;

		return bValid;
	}

	public static boolean isValidPassword(String sPassword) {
		
		return Validate.isPasswordFormat(sPassword, ciUserMinPassword, ciUserMaxPassword);		
	}


	
	
	
	
}//END Class