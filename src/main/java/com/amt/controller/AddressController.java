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
import com.amt.dto.MessageDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.EmployeeRole;
import com.amt.model.User;
import com.amt.model.UserType;
import com.amt.service.AddressService;
import com.amt.service.UserService;

@RestController
//@CrossOrigin("http://localhost:4201")
//this annotation is what the backend will use to tell the browser that the source of the JavaScript code that is sending the request
//  to the backend is a "trusted" source
@CrossOrigin(Constants.csCrossOriginHttp)
public class AddressController implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(AddressController.class);

	@Autowired //Singleton scoped bean
	private AddressService objAddressService;
	
	public AddressController(AddressService objAddressService) {
		this.objAddressService = objAddressService;
	}


	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@PostMapping(path = "/amt_adx/{username}")
	public ResponseEntity<Object> addAddress(@PathVariable("username") String username, @RequestBody AddAddressListDTO objAddAddressListDTO){
		String sMethod = csCRT + "addAddress(): ";
		objLogger.trace(csCR + sMethod + "Entered: username: [" + username + "] addUserDTO: [" + objAddAddressListDTO.toString() + "]");
		
		try {
			
			User objUser = objAddressService.addAddress(username, objAddAddressListDTO);
			objLogger.debug(sMethod + "object returned from service: objUser: [" + objUser + "]");
			return ResponseEntity.status(200).body(objUser);
			
		} catch (DatabaseException e) {
			objLogger.debug(sMethod + "DatabaseException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (BadParameterException e) {
			objLogger.debug(sMethod + "BadParameterException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}		
	}

	
	
	
	
}//END Class
