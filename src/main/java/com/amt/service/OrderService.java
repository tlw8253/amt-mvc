package com.amt.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amt.app.Constants;
import com.amt.app.Constants.enumOrderStatus;
import com.amt.dao.OrderDAO;
import com.amt.dao.UserDAO;
import com.amt.dto.AddAddressDTO;
import com.amt.dto.AddAddressListDTO;
import com.amt.dto.AddOrderDTO;
import com.amt.dto.AddOrderedItemDTO;
import com.amt.dto.AddressDTO;
import com.amt.dto.AddressListDTO;
import com.amt.dto.OrderDTO;
import com.amt.dto.UserDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.AddressType;
import com.amt.model.CatalogItem;
import com.amt.model.Order;
import com.amt.model.OrderStatus;
import com.amt.model.OrderedItem;
import com.amt.model.User;
import com.amt.util.Validate;

@Service
public class OrderService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(OrderService.class);
	
	private OrderDAO objOrderDAO;

	@Autowired
	public OrderService(OrderDAO objOrderDAO) {
		String sMethod = csCRT + "OrderService(): ";
		objLogger.trace(sMethod + "Construtor Entered.");
		
		this.objOrderDAO = objOrderDAO;
	}

	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public Order addOrder(String sUsername, AddOrderDTO objAddOrderDTO) throws DatabaseException, BadParameterException {
		String sMethod = csCRT + "addOrder(): ";
		objLogger.trace(sMethod + "Entered: objAddOrderDTO: [" + objAddOrderDTO.toString() + "]");

		if (!UserService.isValidUsername(sUsername)) {
			objLogger.debug(sMethod + "Invalid sUsername: [" + sUsername + "]");
			throw new BadParameterException(csMsgBadParamnUsernameFormat);
		}		
		
		if (isValidAddOrderDTO(objAddOrderDTO)) {
			try {
				objLogger.debug(sMethod + "Validated objAddOrderDTO: [" + objAddOrderDTO.toString() + "]");		
				
				List<OrderedItem> lstOrderedItem = new ArrayList<OrderedItem>();
				List<AddOrderedItemDTO> lstAddOrderedItemDTO = objAddOrderDTO.getLstOrderedItem();
				
				for (int iCtr=0; iCtr < lstAddOrderedItemDTO.size(); iCtr++) {
					AddOrderedItemDTO objAddOrderedItemDTO = lstAddOrderedItemDTO.get(iCtr);
					objLogger.debug(sMethod + "parseing objAddOrderedItemDTO: [" + objAddOrderedItemDTO.toString() + "]");
				
					String sCatalogItemName = objAddOrderedItemDTO.getCatalogItemName();
					Double dOrderItemPrice = Double.parseDouble(objAddOrderedItemDTO.getOrderItemPrice());
					int iOrderItemQty = Integer.parseInt(objAddOrderedItemDTO.getOrderItemQty());
					
					objLogger.debug(sMethod + "getting object for catalog item sCatalogItemName: [" + sCatalogItemName + "]");
					CatalogItem objCatalogItem = objOrderDAO.getCatalogItemByName(sCatalogItemName);
					objLogger.debug(sMethod + "object returned for catalog item objCatalogItem: [" + objCatalogItem.toString() + "]");
					
					//need to finish this code
					OrderedItem objOrderedItem = new OrderedItem(dOrderItemPrice, iOrderItemQty, objCatalogItem);
					objLogger.debug(sMethod + "adding to ordered list objOrderedItem: [" + objOrderedItem.toString() + "]");					
					lstOrderedItem.add(objOrderedItem);
				}		

				Double dAmount = Double.parseDouble(objAddOrderDTO.getOrderAmount());
				Timestamp objOrderSubmitted = Timestamp.valueOf(LocalDateTime.now());				
				Timestamp objOrderSent = new Timestamp(0);
				OrderStatus objOrderStatus = objOrderDAO.getOrderStatusByName(csarrOrderStatus[enumOrderStatus.NEW.pos]);
		
				User objCustomer = objOrderDAO.getCustomerByUsername(sUsername);
						
				OrderDTO objOrderDTO = new OrderDTO(dAmount, objOrderSubmitted, objOrderSent);
				objOrderDTO.setCustomer(objCustomer);
				objOrderDTO.setOrderStatus(objOrderStatus);
				objOrderDTO.setLstOrderedItem(lstOrderedItem);
				
				Order objOrder = objOrderDAO.addOrder(objOrderDTO);
				
				
				return objOrder;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.debug(sMethod + csCR + "While adding order for sUsername: [" + sUsername + "]");
				objLogger.debug(sMethod + csMsgDB_ErrorAddingOrder);
				objLogger.warn(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorAddingOrder);
			}

		} else {
			objLogger.warn(sMethod + "objAddOrderDTO is not valid: [" + objAddOrderDTO.toString() + "] for sUsername: [" + sUsername + "]");
			throw new BadParameterException(csMsgBadParamAddOrder);
		}
	}

	
	
	////////////////////// Utility Methods for this Class /////////////////////////////////////////
	//
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public boolean isValidAddOrderDTO(AddOrderDTO objAddOrderDTO) {
		String sMethod = "isValidOrderDTO(): ";
		objLogger.trace(sMethod + "Entered: objAddOrderDTO: [" + objAddOrderDTO.toString() + "]" + csCR);
		boolean bValid = false;
		
		String sOrderAmount = objAddOrderDTO.getOrderAmount();
		List<AddOrderedItemDTO> lstOrderedItem = objAddOrderDTO.getLstOrderedItem();
		
		boolean bOrderAmount = Validate.isDouble(sOrderAmount);
		
		if (bOrderAmount) {
			bValid = true;
			
			for (int iCtr=0; iCtr < lstOrderedItem.size(); iCtr++) {
				AddOrderedItemDTO objAddOrderedItemDTO = lstOrderedItem.get(iCtr);
				bValid = bValid && isValidAddOrderedItemDTO(objAddOrderedItemDTO);
			}		
			
		}
		
		if(!bValid) {
			objLogger.trace(csCR + sMethod + "One or more add Order Parameters did not pass validation.:");
			objLogger.warn(sMethod + "\t order amount: [" + sOrderAmount + "] is valid: [" + bOrderAmount + "]");
		}
		
		
		return bValid;
	}


	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean isValidAddOrderedItemDTO(AddOrderedItemDTO objAddOrderedItemDTO) {
		String sMethod = "isValidAddOrderedItemDTO(): ";
		objLogger.trace(sMethod + "Entered: objAddOrderedItemDTO: [" + objAddOrderedItemDTO.toString() + "]" + csCR);
		boolean bValid = false;

		String sCatalogItemName = objAddOrderedItemDTO.getCatalogItemName();
		String sOrderItemPrice = objAddOrderedItemDTO.getOrderItemPrice();
		String sOrderItemQty = objAddOrderedItemDTO.getOrderItemQty();
		
		boolean bCatalogItemName = Validate.isAlphaNumeric(sCatalogItemName);
		boolean bOrderItemPrice = Validate.isDouble(sOrderItemPrice);
		boolean bOrderItemQty = Validate.isInt(sOrderItemQty);
		
		if (bCatalogItemName && bOrderItemPrice && bOrderItemQty) {
			bValid = true;
		} else {
			objLogger.trace(csCR + sMethod + "One or more add Order Item Parameters did not pass validation.:");
			objLogger.warn(sMethod + "\t catalog item name: [" + sCatalogItemName + "] is valid: [" + bCatalogItemName + "]");			
			objLogger.warn(sMethod + "\t order item price: [" + sOrderItemPrice + "] is valid: [" + bOrderItemPrice + "]");			
			objLogger.warn(sMethod + "\t order item qty: [" + sOrderItemQty + "] is valid: [" + bOrderItemQty + "]");			
		}	
		return bValid;
	}
	
	
}//END Class