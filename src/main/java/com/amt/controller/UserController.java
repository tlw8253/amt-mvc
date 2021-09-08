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
import com.amt.dto.AddCustomerDTO;
import com.amt.dto.MessageDto;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.User;
import com.amt.service.UserService;

@RestController
@CrossOrigin("http://localhost:4201")
//this annotation is what the backend will use to tell the browser that the source of the JavaScript code that is sending the request
//  to the backend is a "trusted" source
//@CrossOrigin(Constants.csCrossOriginHttp)
public class UserController implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(UserController.class);

	@Autowired //Singleton scoped bean
	private UserService objUserService;
	
	public UserController(UserService objUserService) {
		this.objUserService = objUserService;
	}


	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@GetMapping(path = "/amt_user/{username}")
	public ResponseEntity<Object> getUserByUsername(@PathVariable("username") String username){
		String sMethod = csCRT + "getUserByUsername(): ";
		objLogger.trace(csCR + sMethod + "Entered: username [" + username + "]");
		
		try {
			User objUser = objUserService.getUserByUsername(username);
			return ResponseEntity.status(200).body(objUser);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
		}		
	}
	

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@PostMapping(path = "/amt_customer")
	public ResponseEntity<Object> addCustomer(@RequestBody AddCustomerDTO addUserDTO){
		String sMethod = csCRT + "addCustomer(): ";
		objLogger.trace(csCR + sMethod + "Entered: addUserDTO [" + addUserDTO + "]");
		
		try {
			User objUser = objUserService.addCustomer(addUserDTO);
			objLogger.debug(sMethod + "object returned from service: objUser: [" + objUser + "]");
			return ResponseEntity.status(200).body(objUser);
		} catch (DatabaseException e) {
			objLogger.debug(sMethod + "DatabaseException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
		} catch (BadParameterException e) {
			objLogger.debug(sMethod + "BadParameterException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
		}		
	}

	
	
	
}//END Class
