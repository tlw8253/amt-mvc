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
import com.amt.dto.AddAddressDTO;
import com.amt.dto.AddAddressListDTO;
import com.amt.dto.AddCustomerDTO;
import com.amt.dto.AddOrderDTO;
import com.amt.dto.MessageDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.EmployeeRole;
import com.amt.model.Order;
import com.amt.model.User;
import com.amt.model.UserType;
import com.amt.service.AddressService;
import com.amt.service.OrderService;
import com.amt.service.UserService;

@RestController
//@CrossOrigin("http://localhost:4201")
//this annotation is what the backend will use to tell the browser that the source of the JavaScript code that is sending the request
//  to the backend is a "trusted" source
@CrossOrigin(originPatterns = Constants.csCrossOriginHttp, allowCredentials = "true")
public class OrderController implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(OrderController.class);

	@Autowired //Singleton scoped bean
	private OrderService objOrderService;
	
	public OrderController(OrderService objOrderService) {
		this.objOrderService = objOrderService;
	}


	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@PostMapping(path = "/amt_order/{username}")
	public ResponseEntity<Object> addCustomerOrder(@PathVariable("username") String username, @RequestBody AddOrderDTO objAddOrderDTO){
		String sMethod = "addAddressByList(): ";
		objLogger.trace(sMethod + "Entered: username: [" + username + "] objAddOrderDTO: [" + objAddOrderDTO.toString() + "]" + csCR);
		
		try {
			
			Order objOrder = objOrderService.addOrder(username, objAddOrderDTO);
			objLogger.debug(csCR + sMethod + "object returned from service: objOrder: [" + objOrder.toString() + "]");
			return ResponseEntity.status(200).body(objOrder);
			
		} catch (DatabaseException e) {
			objLogger.debug(csCR + sMethod + "DatabaseException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (BadParameterException e) {
			objLogger.debug(csCR + sMethod + "BadParameterException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}		
	}

	
	
	
}//END Class
