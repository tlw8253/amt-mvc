package com.amt.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amt.app.Constants;
import com.amt.dao.UserDAO;
import com.amt.dto.AddPhoneNumberDTO;
import com.amt.dto.AddPhoneNumberListDTO;
import com.amt.dto.PhoneNumberDTO;
import com.amt.dto.PhoneNumberListDTO;
import com.amt.dto.UserDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.PhoneNumberType;
import com.amt.model.User;
import com.amt.util.Validate;

@Service
public class PhoneNumberService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(PhoneNumberService.class);
	
	private UserDAO objUserDAO;

	@Autowired
	public PhoneNumberService(UserDAO objUserDAO) {
		String sMethod = csCRT + "PhoneNumberService(): ";
		objLogger.trace(sMethod + "Construtor Entered.");		
		this.objUserDAO = objUserDAO;
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public User addPhoneNumber(String sUsername, AddPhoneNumberDTO objAddPhoneNumberDTO) throws DatabaseException, BadParameterException {
		String sMethod = csCRT + "addPhoneNumber(): ";
		objLogger.trace(sMethod + "Entered: objAddPhoneNumberDTO: [" + objAddPhoneNumberDTO.toString() + "]" + csCR);

		List<AddPhoneNumberDTO> lstAddPhoneNumberDTO = new ArrayList<AddPhoneNumberDTO>();
		lstAddPhoneNumberDTO.add(objAddPhoneNumberDTO);
		AddPhoneNumberListDTO objAddPhoneNumberListDTO = new AddPhoneNumberListDTO(lstAddPhoneNumberDTO);
		
		return addPhoneNumberByList(sUsername, objAddPhoneNumberListDTO);		

	}

	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public User addPhoneNumberByList(String sUsername, AddPhoneNumberListDTO objAddPhoneNumberListDTO) throws DatabaseException, BadParameterException {
		String sMethod = csCRT + "addPhoneNumberByList(): ";
		objLogger.trace(sMethod + "Entered: objPhoneNumberListDTO: [" + objAddPhoneNumberListDTO.toString() + "]" + csCR);

		if (!UserService.isValidUsername(sUsername)) {
			objLogger.debug(sMethod + "Invalid sUsername: [" + sUsername + "]");
			throw new BadParameterException(csMsgBadParamnUsernameFormat);
		}		
		
		if (isValidAddPhoneNumberListDTO(objAddPhoneNumberListDTO)) {
			try {
				objLogger.debug(sMethod + "Validated objAddPhoneNumberListDTO: [" + objAddPhoneNumberListDTO.toString() + "]");				
				
				UserDTO objUserDTO = new UserDTO();
				PhoneNumberListDTO objPhoneNumberListDTO = new PhoneNumberListDTO();
				objPhoneNumberListDTO.setUsername(sUsername);
				
				List<PhoneNumberDTO> lstPhoneNumberDTO = new ArrayList<PhoneNumberDTO>();
				
				for (int iCtr=0; iCtr<objAddPhoneNumberListDTO.getLstAddPhoneNumberDTO().size(); iCtr++) {
					objLogger.debug(sMethod + "Creating PhoneNumberListDTO for the DAO.");
					AddPhoneNumberDTO objAddPhoneNumberDTO = objAddPhoneNumberListDTO.getLstAddPhoneNumberDTO().get(iCtr);
					objLogger.debug(sMethod + "Creating PhoneNumberDTO from objAddPhoneNumberDTO: [" + objAddPhoneNumberDTO.toString() + "]");
					
					String sPhoneNumberType = objAddPhoneNumberDTO.getPhoneNumberType();
					PhoneNumberType objPhoneNumberType = objUserDAO.getPhoneNumberTypeByName(sPhoneNumberType);			
					
					String sPhoneNumber = objAddPhoneNumberDTO.getPhoneNumber();

					PhoneNumberDTO objPhoneNumberDTO = new PhoneNumberDTO(sPhoneNumber);
					objPhoneNumberDTO.setPhoneNumberType(objPhoneNumberType);
					User objUser = objUserDAO.getUserByUsername(sUsername);
					objPhoneNumberDTO.setUser(objUser);
					
					lstPhoneNumberDTO.add(objPhoneNumberDTO);
					
				}

				objPhoneNumberListDTO.setLstPhoneNumberDTO(lstPhoneNumberDTO);				
				
				objLogger.debug(sMethod + "calling addUserPhoneNumber with objPhoneNumberListDTO: [" + objPhoneNumberListDTO.toString() + "]");	
				User objUser = objUserDAO.addUserPhoneNumber(objPhoneNumberListDTO);
				objLogger.debug(sMethod + csCR + "user object returned from DAO call objUser: [" + objUser.toString() + "]");
				return objUser;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + csCR + "Exception adding PhoneNumber List: [" + objAddPhoneNumberListDTO.toString() + "] to sUsername: [" + sUsername+ "]");
				objLogger.warn(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorAddingPhoneNumber);
			}

		} else {
			objLogger.warn(sMethod + "objAddPhoneNumberListDTO is not valid: [" + objAddPhoneNumberListDTO.toString() + "] for sUsername: [" + sUsername + "]");
			throw new BadParameterException(csMsgBadParamAddPhoneNumber);
		}
	}

	
	
	////////////////////// Utility Methods for this Class /////////////////////////////////////////
	//
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	private boolean isValidAddPhoneNumberListDTO(AddPhoneNumberListDTO objAddPhoneNumberListDTO) {
		String sMethod = csCRT + "isValidPhoneNumberDTO(): ";
		objLogger.trace(sMethod + "Entered");
		boolean isValid = true;


		for (int iCtr=0; iCtr<objAddPhoneNumberListDTO.getLstAddPhoneNumberDTO().size(); iCtr++) {
			AddPhoneNumberDTO objAddPhoneNumberDTO = objAddPhoneNumberListDTO.getLstAddPhoneNumberDTO().get(iCtr);
			objLogger.debug(sMethod + "Sending PhoneNumber from list to validation objAddPhoneNumberDTO: [" + objAddPhoneNumberDTO.toString() + "]");
			isValid = isValid && isValidAddPhoneNumberDTO(objAddPhoneNumberDTO);
		}
		
		if (!isValid) {
			objLogger.debug(sMethod + "One or more PhoneNumberes in list failed validation.");
		}
		
		return isValid;
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	private boolean isValidAddPhoneNumberDTO(AddPhoneNumberDTO objPhoneNumberDTO) {
		String sMethod = csCRT + "isValidAddPhoneNumberDTO(): ";
		objLogger.trace(csCR + sMethod + "Entered: objAddPhoneNumberDTO: [" + objPhoneNumberDTO.toString() + "]");
		boolean bValid = false;
		
		String sPhoneNumber = objPhoneNumberDTO.getPhoneNumber();
		String sPhoneNumberType = objPhoneNumberDTO.getPhoneNumberType();
		
		boolean bPhoneNumberValid = (sPhoneNumber.length() == 10) && Validate.isNumeric(sPhoneNumber);
		boolean bPhoneNumberTypeValid = Validate.isValidValueInArray(sPhoneNumberType, csarrPhoneNumberType);
		
		if (bPhoneNumberValid && bPhoneNumberTypeValid) {
			bValid = true;
		}else {
			objLogger.trace(csCR + sMethod + "One or more add Phone Number Parameters did not pass validation.:");
			objLogger.warn(sMethod + "\t phone number: [" + sPhoneNumber + "] is valid: [" + bPhoneNumberValid + "]");
			objLogger.warn(sMethod + "\t phone number type: [" + sPhoneNumberType + "] is valid: [" + bPhoneNumberTypeValid + "]");
		}
		
		return bValid;
	}
	
	

	
	
	
	
}//END Class