package com.amt.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amt.app.Constants;
import com.amt.dao.UserDAO;
import com.amt.dto.AddCustomerDTO;
import com.amt.dto.UserDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.CatalogItem;
import com.amt.model.EmployeeRole;
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
		String sMethod = csCRT + "UserService(): ";
		objLogger.trace(sMethod + "Construtor Entered.");
		
		this.objUserDAO = objUserDAO;
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public List<User> getUsersByType(String sType) throws DatabaseException, BadParameterException {
		String sMethod = csCRT + "getUsersByType(): ";
		objLogger.trace(sMethod + "Entered: sType: [" + sType + "]" + csCR);
		List<User> lstUser = new ArrayList<User>();
		
		try {
			lstUser = objUserDAO.getUsersByType(sType);
			objLogger.debug(sMethod + "list object retrieved lstUser: [" + lstUser.toString() + "]");
		} catch (Exception e) {
			objLogger.warn(sMethod + csCR + "Exception getting the User List.");
			objLogger.warn(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingUserList);
		}

		return lstUser;
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public List<User> getAllUsers() throws DatabaseException, BadParameterException {
		String sMethod = csCRT + "getAllUser(): ";
		objLogger.trace(sMethod + "Entered." + csCR);
		List<User> lstUser = new ArrayList<User>();
		
		try {
			lstUser = objUserDAO.getUsers();
			objLogger.debug(sMethod + "list object retrieved lstUser: [" + lstUser.toString() + "]");
		} catch (Exception e) {
			objLogger.warn(sMethod + csCR + "Exception getting the User List.");
			objLogger.warn(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingUserList);
		}

		return lstUser;
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public User getUserByUsername(String sUsername) throws DatabaseException {
		String sMethod = csCRT + "getUserByUsername(): ";
		objLogger.trace(sMethod + "Entered: sUsername [" + sUsername + "]");

		//objUserDAO = new UserDAO();
		try {
			User objUser = objUserDAO.getUserByUsername(sUsername);
			return objUser;
		} catch (Exception e) {
			objLogger.warn(sMethod + csCR + "Exception: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingUserByUsername);
		}
		
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public UserType getUserTypeByName(String sName) throws DatabaseException {
		String sMethod = csCRT + "getUserTypeByName(): ";
		objLogger.trace(sMethod + "Entered: sUsername [" + sName + "]");
		
	
		try {
			objLogger.debug(sMethod + "calling objUserDAO.getByUserTypeName(" + sName + ")");
			UserType objUserType = objUserDAO.getUserTypeByName(sName);
			objLogger.debug(sMethod + csCR + "return user type: [" + objUserType.toString() + "]");
			return objUserType;
		} catch (Exception e) {
			objLogger.warn(sMethod + csCR + csMsgDB_ErrorGettingUserTypeByName + " : [" + sName + "]");
			objLogger.warn(sMethod + "Exception: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingUserTypeByName);
		}		
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public EmployeeRole getEmployeeRoleByName(String sName) throws DatabaseException {
		String sMethod = csCRT + "getEmployeeRoleByName(): ";
		objLogger.trace(sMethod + "Entered: sUsername [" + sName + "]");
		
		try {
			objLogger.debug(sMethod + "calling objUserDAO.getByUserTypeName(" + sName + ")");
			EmployeeRole objEmployeeRole = objUserDAO.getEmployeeRoleByName(sName);
			objLogger.debug(sMethod + csCR + "return user type: [" + objEmployeeRole.toString() + "]");
			return objEmployeeRole;
		} catch (Exception e) {
			objLogger.warn(sMethod + csCR + csMsgDB_ErrorGettingEmployeeRoleByName + " : [" + sName + "]");
			objLogger.warn(sMethod + "Exception: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingEmployeeRoleByName);
		}		
	}

	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public User addCustomer(AddCustomerDTO objAddDTO) throws DatabaseException, BadParameterException {
		String sMethod = csCRT + "addCustomer(): ";
		objLogger.trace(sMethod + "Entered: objAddDTO: [" + objAddDTO.toString() + "]");

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

				
				
				objLogger.debug(sMethod + "Calling local User Service to get customer User Type.");
				UserType objUserType = getUserTypeByName(csarUserType[enumUserType.CUSTOMER.pos]);
				objLogger.debug(sMethod + csCR +"objUserType: [" + objUserType + "]");
				objUserDTO.setUserType(objUserType);
				
				objLogger.debug(sMethod + "Calling local User Service to get customer Employee Role.");
				EmployeeRole objEmployeeRole = getEmployeeRoleByName(csarEmployeeRoles[enumUserEmployee.CUSTOMER.pos]);
				objLogger.debug(sMethod + csCR +"objUserType: [" + objUserType + "]");
				objUserDTO.setEmployeeRole(objEmployeeRole);


				
				objLogger.debug(sMethod + "calling addNew with objUserDTO: [" + objUserDTO.toString() + "]");	
				User objUser = objUserDAO.addNewUser(objUserDTO);
				objLogger.debug(sMethod + csCR + "user object returned from DAO call objUser: [" + objUser.toString() + "]");
				return objUser;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + csCR + "Exception adding User record with username: [" + objAddDTO.getUsername()	+ "]");
				objLogger.warn(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");
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
		
		sUsername = sUsername.trim();		
		bValid = Validate.isAlphaNumeric(sUsername) 
				&& sUsername.length() >= ciUsernameMinLength 
				&& sUsername.length() <= ciUsernameMaxLength;

		return bValid;
	}

	public static boolean isValidPassword(String sPassword) {
		
		sPassword = sPassword.trim();
		return Validate.isPasswordFormat(sPassword, ciUserMinPassword, ciUserMaxPassword);		
	}


	
	
	
	
}//END Class