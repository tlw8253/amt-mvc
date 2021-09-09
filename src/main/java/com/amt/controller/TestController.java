package com.amt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amt.dto.LoginDTO;
@RestController // I changed the annotation from @Controller to @RestController
// So I do not need to put @ResponseBody on my methods anymore
// @ResponseBody's purpose is to specify that the return type should be serialized into, for example, JSON and placed into the 
// body of our HTTP response
public class TestController {
	
	@GetMapping(path = "/hello", produces = "application/json")
	public String hello() {
		return "Hello world!";
	}
	
	@GetMapping(path = "/amt_user", produces = "application/json")
	public String amtUser() {
		return "get user by user name request";
	}

	/*
http://localhost:8080/amt-mvc/amt_login
http://localhost:8080/amt-mvc/amt_logout
http://localhost:8080/amt-mvc/amt_current_user

http://localhost:8080/amt-mvc/hello
http://localhost:8080/amt-mvc/amt_user/tw8253
http://localhost:8080/amt-mvc/amt_customer
http://localhost:8080/amt-mvc/amt_user_type/CUSTOMER
http://localhost:8080/amt-mvc/amt_empl_role/CUSTOMER

http://localhost:8080/amt-mvc/amt_adx/tz67895



http://localhost:4201/amt-mvc/amt_user/tw8253
	 */
	
	
//	@PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
//	public LoginDTO login(@RequestBody LoginDTO loginDto) {
//		return loginDto;
//	}

}
