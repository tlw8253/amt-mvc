package com.amt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amt.app.Constants;
import com.amt.dto.LoginDTO;
import com.amt.dto.MessageDTO;
import com.amt.exception.AuthenticationFailureException;
import com.amt.exception.BadParameterException;
import com.amt.model.User;
import com.amt.service.LoginService;

@RestController
//@CrossOrigin("http://localhost:4201")
//this annotation is what the backend will use to tell the browser that the source of the JavaScript code that is sending the request
//  to the backend is a "trusted" source
@CrossOrigin(originPatterns = Constants.csCrossOriginHttp, allowCredentials = "true")
public class LoginController implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(LoginController.class);

	@Autowired // Singleton scoped bean
	private LoginService objLoginService;

	@Autowired
	private HttpServletRequest request;

	public LoginController(LoginService objLoginService) {
		this.objLoginService = objLoginService;
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@PostMapping(path = csRootEndpointLogin)
	public ResponseEntity<Object> login(@RequestBody LoginDTO objLoginDTO) {
		String sMethod = csCRT + "addCustomer(): ";
		objLogger.trace(csCR + sMethod + "Entered: objLoginDTO [" + objLoginDTO + "]");

		try {

			User objUser = objLoginService.login(objLoginDTO);
			objLogger.debug(sMethod + "objUser: [" + objUser.toString() + "]");

			HttpSession session = request.getSession(true);
			if (session.getAttribute("currentUser") != null) {
				return ResponseEntity.status(400).body(new MessageDTO(csMsgSessionAlreadyLoggedIn));
			}
			
			session.setAttribute(csSessionCurrentUser, objUser);

			return ResponseEntity.status(200).body(objUser);

		} catch (BadParameterException e) {
			objLogger.debug(sMethod + "BadParameterException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (AuthenticationFailureException e) {
			objLogger.debug(sMethod + "AuthenticationFailureException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@GetMapping(path = csRootEndpointCurrentUser)
	public ResponseEntity<Object> getCurrentUser() {
		String sMethod = csCRT + "getCurrentUser(): ";
		objLogger.trace(csCR + sMethod + "Entered.");

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute(csSessionCurrentUser) == null) {
			objLogger.debug(sMethod + "there is no active session for any user.");
			return ResponseEntity.status(400).body(new MessageDTO(csMsgSessionUserNotActive));
		}

		User objUser = (User) session.getAttribute(csSessionCurrentUser);
		objLogger.debug(sMethod + "Active user in session: objUser: [" + objUser + "]");
		return ResponseEntity.status(200).body(objUser);

	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@PostMapping(path = csRootEndpointLogout)
	public ResponseEntity<Object> logout() {
		String sMethod = csCRT + "logout(): ";
		objLogger.trace(csCR + sMethod + "Entered.");

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute(csSessionCurrentUser) == null) {
			objLogger.debug(sMethod + "there is no active session for any user.");
			return ResponseEntity.status(400).body(new MessageDTO(csMsgSessionUserNotActive));
		} else {

			User objUser = (User) session.getAttribute(csSessionCurrentUser);
			objLogger.debug(sMethod + "username: [" + objUser.getUsername() + "] is being logged out.");
			session.setAttribute(csSessionCurrentUser, null);			
			
			return ResponseEntity.status(200).body(new MessageDTO(csMsgSessionUserLoggedOut));
		}

	}

}// END Class
