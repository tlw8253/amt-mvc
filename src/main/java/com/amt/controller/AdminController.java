package com.amt.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amt.app.Constants;
import com.amt.dto.MessageDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.Address;
import com.amt.model.CatalogItem;
import com.amt.model.User;
import com.amt.service.AddressService;
import com.amt.service.CatalogItemService;
import com.amt.service.UserService;

@RestController
//@CrossOrigin("http://localhost:4201")
//this annotation is what the backend will use to tell the browser that the source of the JavaScript code that is sending the request
//  to the backend is a "trusted" source
//@CrossOrigin(Constants.csCrossOriginHttp, allowCredentials = "true")
@CrossOrigin(originPatterns = Constants.csCrossOriginHttp, allowCredentials = "true")
public class AdminController implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(AdminController.class);

	@Autowired //Singleton scoped bean
	private AddressService objAddressService;
	
	@Autowired //Singleton scoped bean
	private CatalogItemService objCatalogItemService;

	@Autowired //Singleton scoped bean
	private UserService objUserService;

	public AdminController(AddressService objAddressService, CatalogItemService objCatalogItemService, UserService objUserService) {
		this.objAddressService = objAddressService;
		this.objCatalogItemService = objCatalogItemService;
		this.objUserService = objUserService;
	}
	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@GetMapping(path = "/get_users/{type}")
	public ResponseEntity<Object> getUsersByType(@PathVariable("type") String type){
		String sMethod = "addAddressByList(): ";
		objLogger.trace(sMethod + "Entered: username: [" + type + "]" + csCR);
		
		try {
			
			List<User> lstUsers = objUserService.getUsersByType(type);
			objLogger.debug(csCR + sMethod + "object returned from service: lstUsers: [" + lstUsers.toString() + "]");
			return ResponseEntity.status(200).body(lstUsers);
			
		} catch (DatabaseException e) {
			objLogger.debug(csCR + sMethod + "DatabaseException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (BadParameterException e) {
			objLogger.debug(csCR + sMethod + "BadParameterException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}		
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@GetMapping(path = "/get_users")
	public ResponseEntity<Object> getUsers(){
		String sMethod = "getUsers(): ";
		objLogger.trace(sMethod + "Entered." + csCR);
		
		try {
			
			List<User> lstUsers = objUserService.getAllUsers();
			objLogger.debug(csCR + sMethod + "object returned from service: lstUsers: [" + lstUsers.toString() + "]");
			return ResponseEntity.status(200).body(lstUsers);
			
		} catch (DatabaseException e) {
			objLogger.debug(csCR + sMethod + "DatabaseException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (BadParameterException e) {
			objLogger.debug(csCR + sMethod + "BadParameterException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}		
	}


	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@GetMapping(path = "/get_cat_items")
	public ResponseEntity<Object> getCatalogItems(){
		String sMethod = "getCatalogItems(): ";
		objLogger.trace(sMethod + "Entered." + csCR);
		
		try {
			
			List<CatalogItem> lstCatalogItems = objCatalogItemService.getAllCatalogItems();
			objLogger.debug(csCR + sMethod + "object returned from service: lstCatalogItems: [" + lstCatalogItems.toString() + "]");
			return ResponseEntity.status(200).body(lstCatalogItems);
			
		} catch (DatabaseException e) {
			objLogger.debug(csCR + sMethod + "DatabaseException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (BadParameterException e) {
			objLogger.debug(csCR + sMethod + "BadParameterException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}		
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@GetMapping(path = "/get_adx/{username}")
	public ResponseEntity<Object> getAddressForUser(@PathVariable("username") String username){
		String sMethod = "addAddressByList(): ";
		objLogger.trace(sMethod + "Entered: username: [" + username + "]" + csCR);
		
		try {
			
			List<Address> lstAdress = objAddressService.getAddressForUser(username);
			objLogger.debug(csCR + sMethod + "object returned from service: lstAdress: [" + lstAdress.toString() + "]");
			return ResponseEntity.status(200).body(lstAdress);
			
		} catch (DatabaseException e) {
			objLogger.debug(csCR + sMethod + "DatabaseException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (BadParameterException e) {
			objLogger.debug(csCR + sMethod + "BadParameterException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}		
	}

	

	
	
}//END Class
