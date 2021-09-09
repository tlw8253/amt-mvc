package com.amt.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amt.app.Constants;
import com.amt.dao.UserDAO;
import com.amt.dto.AddAddressDTO;
import com.amt.dto.AddAddressListDTO;
import com.amt.dto.AddressDTO;
import com.amt.dto.AddressListDTO;
import com.amt.dto.UserDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.AddressType;
import com.amt.model.User;
import com.amt.util.Validate;

@Service
public class AddressService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(AddressService.class);
	
	private UserDAO objUserDAO;

	@Autowired
	public AddressService(UserDAO objUserDAO) {
		String sMethod = csCRT + "AddressService(): ";
		objLogger.trace(sMethod + "Construtor Entered.");
		
		this.objUserDAO = objUserDAO;
	}


	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public User addAddress(String sUsername, AddAddressListDTO objAddAddressListDTO) throws DatabaseException, BadParameterException {
		String sMethod = csCRT + "addAddress(): ";
		objLogger.trace(sMethod + "Entered: objAddAddressListDTO: [" + objAddAddressListDTO.toString() + "]");

		if (!UserService.isValidUsername(sUsername)) {
			objLogger.debug(sMethod + "Invalid sUsername: [" + sUsername + "]");
			throw new BadParameterException(csMsgBadParamnUsernameFormat);
		}
		
		
		if (isValidAddAddressListDTO(objAddAddressListDTO)) {
			try {
				objLogger.debug(sMethod + "Validated AddAddressListDTO: [" + objAddAddressListDTO.toString() + "]");				
				
				UserDTO objUserDTO = new UserDTO();
				AddressListDTO objAddressListDTO = new AddressListDTO();
				objAddressListDTO.setUsername(sUsername);
				
				List<AddressDTO> lstAddressDTO = new ArrayList<AddressDTO>();
				
				for (int iCtr=0; iCtr<objAddAddressListDTO.getLstAddAddressDTO().size(); iCtr++) {
					objLogger.debug(sMethod + "Creating AddressListDTO for the DAO.");
					AddAddressDTO objAddAddressDTO = objAddAddressListDTO.getLstAddAddressDTO().get(iCtr);
					objLogger.debug(sMethod + "Creating AddressDTO from objAddAddressDTO: [" + objAddAddressDTO.toString() + "]");
					
					String sAddressType = objAddAddressDTO.getAddressType();
					AddressType objAddressType = objUserDAO.getAddressTypeByName(sAddressType);			
					
					String sAddressLine1 = objAddAddressDTO.getAddressLine1();
					String sAddressLine2 = objAddAddressDTO.getAddressLine2();
					String sAddressCity = objAddAddressDTO.getAddressCity();
					String sAddressState = objAddAddressDTO.getAddressState();
					String sAddressZipCode = objAddAddressDTO.getAddressType();

					AddressDTO objAddressDTO = new AddressDTO(sAddressLine1, sAddressLine2, sAddressCity, sAddressState, sAddressZipCode);
					objAddressDTO.setAddressType(objAddressType);
					User objUser = objUserDAO.getUserByUsername(sUsername);
					objAddressDTO.setUser(objUser);
					
					lstAddressDTO.add(objAddressDTO);
					
				}

				objAddressListDTO.setLstAddressDTO(lstAddressDTO);				
				
				objLogger.debug(sMethod + "calling addUserAddress with objAddressListDTO: [" + objAddressListDTO.toString() + "]");	
				User objUser = objUserDAO.addUserAddress(objAddressListDTO);
				objLogger.debug(sMethod + csCR + "user object returned from DAO call objUser: [" + objUser.toString() + "]");
				return objUser;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + csCR + "Exception adding Address List: [" + objAddAddressListDTO.toString() + "] to sUsername: [" + sUsername+ "]");
				objLogger.warn(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorAddingUser);
			}

		} else {
			objLogger.warn(sMethod + "objAddUserDTO is not valid: [" + objAddAddressListDTO.toString() + "] for sUsername: [" + sUsername + "]");
			throw new BadParameterException(csMsgBadParamAddAddress);
		}
	}

	
	
	////////////////////// Utility Methods for this Class /////////////////////////////////////////
	//
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	private boolean isValidAddAddressListDTO(AddAddressListDTO objAddAddressListDTO) {
		String sMethod = csCRT + "isValidAddressDTO(): ";
		objLogger.trace(sMethod + "Entered");
		boolean isValid = true;


		for (int iCtr=0; iCtr<objAddAddressListDTO.getLstAddAddressDTO().size(); iCtr++) {
			AddAddressDTO objAddAddressDTO = objAddAddressListDTO.getLstAddAddressDTO().get(iCtr);
			objLogger.debug(sMethod + "Sending address from list to validation objAddAddressDTO: [" + objAddAddressDTO.toString() + "]");
			isValid = isValid && isValidAddAddressDTO(objAddAddressDTO);
		}
		
		if (!isValid) {
			objLogger.debug(sMethod + "One or more addresses in list failed validation.");
		}
		
		return isValid;
	}

	private boolean isValidAddAddressDTO(AddAddressDTO objAddressDTO) {
		String sMethod = csCRT + "isValidAddAddressDTO(): ";
		objLogger.trace(csCR + sMethod + "Entered: objAddAddressDTO: [" + objAddressDTO.toString() + "]");
		boolean bValid = false;
		
		String sAdx1 = objAddressDTO.getAddressLine1();
		String sCity = objAddressDTO.getAddressCity();
		String sState = objAddressDTO.getAddressState();
		String sZip = objAddressDTO.getAddressZipCode();
		String sType = objAddressDTO.getAddressType();
		
		boolean bAdx1Valid = (sAdx1.length() > 0);
		boolean bCityValid = Validate.isAlpha(sCity);
		boolean bStateValid = Validate.isValidStateCode(sState);
		boolean bZipValid = Validate.isInt(sZip);
		boolean bTypeValid = Validate.isValidValueInArray(sType, carrAddressType);
		
		if (bAdx1Valid && bCityValid && bStateValid && bZipValid && bTypeValid) {
			bValid = true;
		}else {
			objLogger.trace(csCR + sMethod + "One or more add Address Parameters did not pass validation.:");
			objLogger.warn(sMethod + "\t address line 1: [" + sAdx1 + "] is valid: [" + bAdx1Valid + "]");
			objLogger.warn(sMethod + "\t city: [" + sCity + "] is valid: [" + bCityValid + "]");
			objLogger.warn(sMethod + "\t state: [" + sState + "] is valid: [" + bStateValid + "]");
			objLogger.warn(sMethod + "\t zip: [" + sZip + "] is valid: [" + bZipValid + "]");
			objLogger.warn(sMethod + "\t address type: [" + sType + "] is valid: [" + bTypeValid + "]");
		}		
		
		return bValid;
	}
	
	

	
	
	
	
}//END Class