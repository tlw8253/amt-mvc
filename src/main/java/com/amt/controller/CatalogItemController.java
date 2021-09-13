package com.amt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amt.app.Constants;
import com.amt.dto.AddCatalogItemDTO;
import com.amt.dto.MessageDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.CatalogItem;
import com.amt.model.User;
import com.amt.service.CatalogItemService;

@RestController
//@CrossOrigin("http://localhost:4201")
//this annotation is what the backend will use to tell the browser that the source of the JavaScript code that is sending the request
//  to the backend is a "trusted" source
@CrossOrigin(originPatterns = Constants.csCrossOriginHttp, allowCredentials = "true")
public class CatalogItemController implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(CatalogItemController.class);

	@Autowired //Singleton scoped bean
	private CatalogItemService objCatalogItemService;

	@Autowired
	private HttpServletRequest request;
	
	public CatalogItemController(CatalogItemService objCatalogItemService) {
		this.objCatalogItemService = objCatalogItemService;
	}


	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@PostMapping(path = "/amt_catalog_item")
	public ResponseEntity<Object> addPhoneNumber(@RequestBody AddCatalogItemDTO objAddCatalogItemDTO){
		String sMethod = csCRT + "addPhoneNumber(): ";
		objLogger.trace(csCR + sMethod + "Entered: objAddCatalogItemDTO: [" + objAddCatalogItemDTO.toString() + "]");
		
		//protected end point, check if this user is the session user
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute(csSessionCurrentUser) == null) {
			objLogger.debug(sMethod + "there is no active session for any user.");
			return ResponseEntity.status(400).body(new MessageDTO(csMsgSessionUserNotActive));
		}

		User objUser = (User) session.getAttribute(csSessionCurrentUser);
		objLogger.debug(sMethod + "Active user in session: objUser: [" + objUser + "]");
		
		String sUsername = objAddCatalogItemDTO.getLoginUsername();
		
		if (!sUsername.equalsIgnoreCase(objUser.getUsername())) {
			objLogger.debug(sMethod + "this session is not for this username: " + sUsername + "]");
			return ResponseEntity.status(400).body(new MessageDTO(csMsgSessionNotForThisUser));		
		}
		
		String sEmployeeRole = objUser.getEmployeeRole().getEmployeeRole();
		boolean bValidEmpRole = sEmployeeRole.equalsIgnoreCase(csarEmployeeRoles[enumUserEmployee.CATALOG_ADMIN.pos])
				|| sEmployeeRole.equalsIgnoreCase(csarEmployeeRoles[enumUserEmployee.CATALOG_EMPLOYEE.pos]);

		if (!bValidEmpRole) {
			objLogger.debug(sMethod + "this username: [" + sUsername + "] with role: [" + sEmployeeRole + "] not authorize to add catalog items.");
			return ResponseEntity.status(400).body(new MessageDTO(csMsgRoleNotAuthorizeCatalog));		
			
		}
		
		try {
			
			CatalogItem objCatalogItem = objCatalogItemService.addCatalogItem(objAddCatalogItemDTO);
			objLogger.debug(sMethod + "object returned from service: objUser: [" + objCatalogItem.toString() + "]");
			return ResponseEntity.status(200).body(objCatalogItem);
			
		} catch (DatabaseException e) {
			objLogger.debug(sMethod + "DatabaseException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (BadParameterException e) {
			objLogger.debug(sMethod + "BadParameterException: [" + e.getMessage() + "]");
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}		
	}

	
	
	
	
}//END Class
