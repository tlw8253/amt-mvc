package com.amt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amt.app.Constants;
import com.amt.dto.AddAddressListDTO;
import com.amt.dto.AddCustomerDTO;
import com.amt.dto.AddPhoneNumberDTO;
import com.amt.dto.AddPhoneNumberListDTO;
import com.amt.dto.MessageDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.EmployeeRole;
import com.amt.model.User;
import com.amt.model.UserType;
import com.amt.service.AddressService;
import com.amt.service.PhoneNumberService;
import com.amt.service.UserService;

@RestController
//@CrossOrigin("http://localhost:4201")
//this annotation is what the backend will use to tell the browser that the source of the JavaScript code that is sending the request
//  to the backend is a "trusted" source
@CrossOrigin(originPatterns = Constants.csCrossOriginHttp, allowCredentials = "true")
public class PhoneNumberController implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(PhoneNumberController.class);

	@Autowired //Singleton scoped bean
	private PhoneNumberService objPhoneNumberService;
	
	public PhoneNumberController(PhoneNumberService objPhoneNumberService) {
		this.objPhoneNumberService = objPhoneNumberService;
	}


	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@PostMapping(path = "/amt_list_phone/{username}")
	public ResponseEntity<Object> addPhoneNumberByList(@PathVariable("username") String username, @RequestBody AddPhoneNumberListDTO objAddPhoneNumberListDTO){
		String sMethod = "addPhoneNumber(): ";
		objLogger.trace(sMethod + "Entered: username: [" + username + "] objAddPhoneNumberListDTO: [" + objAddPhoneNumberListDTO.toString() + "]" + csCR);
		
		try {
			
			User objUser = objPhoneNumberService.addPhoneNumberByList(username, objAddPhoneNumberListDTO);
			objLogger.debug(csCRT + sMethod + "object returned from service: objUser: [" + objUser.toString() + "]");
			return ResponseEntity.status(200).body(objUser);
			
		} catch (DatabaseException e) {
			objLogger.debug(csCRT + sMethod + "DatabaseException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (BadParameterException e) {
			objLogger.debug(csCRT + sMethod + "BadParameterException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}		
	}

	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@PostMapping(path = "/amt_phone/{username}")
	public ResponseEntity<Object> addPhoneNumber(@PathVariable("username") String username, @RequestBody AddPhoneNumberDTO objAddPhoneNumberDTO){
		String sMethod = "addPhoneNumber(): ";
		objLogger.trace(sMethod + "Entered: username: [" + username + "] objAddPhoneNumberDTO: [" + objAddPhoneNumberDTO.toString() + "]" + csCR);
		
		try {
			
			User objUser = objPhoneNumberService.addPhoneNumber(username, objAddPhoneNumberDTO);
			objLogger.debug(csCRT + sMethod + "object returned from service: objUser: [" + objUser.toString() + "]");
			return ResponseEntity.status(200).body(objUser);
			
		} catch (DatabaseException e) {
			objLogger.debug(csCRT + sMethod + "DatabaseException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (BadParameterException e) {
			objLogger.debug(csCRT + sMethod + "BadParameterException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}		
	}

	
	
}//END Class
