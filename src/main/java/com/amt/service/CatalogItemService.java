package com.amt.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amt.app.Constants;
import com.amt.dao.CatalogItemDAO;
import com.amt.dao.UserDAO;
import com.amt.dto.AddCatalogItemDTO;
import com.amt.dto.AddPhoneNumberDTO;
import com.amt.dto.AddPhoneNumberListDTO;
import com.amt.dto.CatalogItemDTO;
import com.amt.dto.PhoneNumberDTO;
import com.amt.dto.PhoneNumberListDTO;
import com.amt.dto.UserDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.Address;
import com.amt.model.CatalogItem;
import com.amt.model.CatalogItemType;
import com.amt.model.PhoneNumberType;
import com.amt.model.User;
import com.amt.util.Validate;

@Service
public class CatalogItemService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(CatalogItemService.class);
	
	private CatalogItemDAO objCatalogItemDAO;

	@Autowired
	public CatalogItemService(CatalogItemDAO objCatalogItemDAO) {
		String sMethod = csCRT + "CatalogItemService(): ";
		objLogger.trace(sMethod + "Constructor Entered.");		
		this.objCatalogItemDAO = objCatalogItemDAO;
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public List<CatalogItem> getAllCatalogItems() throws DatabaseException, BadParameterException {
		String sMethod = csCRT + "getAllCatalogItems(): ";
		objLogger.trace(sMethod + "Entered." + csCR);
		List<CatalogItem> lstCatalogItems = new ArrayList<CatalogItem>();
		
		try {
			lstCatalogItems = objCatalogItemDAO.getCatalogList();
			objLogger.debug(sMethod + "list object retrieved lstCatalogItems: [" + lstCatalogItems.toString() + "]");
		} catch (Exception e) {
			objLogger.warn(sMethod + csCR + "Exception getting the Catalog List.");
			objLogger.warn(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingCatalogList);
		}

		return lstCatalogItems;
	}

	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public CatalogItem addCatalogItem(AddCatalogItemDTO objAddCatalogItemDTO) throws DatabaseException, BadParameterException {
		String sMethod = csCRT + "addCatalogItem(): ";
		objLogger.trace(sMethod + "Entered: objAddCatalogItemDTO: [" + objAddCatalogItemDTO.toString() + "]");

		if (isValidCatalogItemDTO(objAddCatalogItemDTO)) {
			try {
				objLogger.debug(sMethod + "Validated objAddCatalogItemDTO: [" + objAddCatalogItemDTO.toString() + "]");	
				
				//create the DAO layer DTO
				CatalogItemDTO objCatalogItemDTO = new CatalogItemDTO();
				objCatalogItemDTO.setCatalogItemName(objAddCatalogItemDTO.getCatalogItemName());
				objCatalogItemDTO.setCatalogItem(objAddCatalogItemDTO.getCatalogItem());
				objCatalogItemDTO.setCatalogItemDescription(objAddCatalogItemDTO.getCatalogItemDescription());
				objCatalogItemDTO.setCatalogItemPrice(Double.parseDouble(objAddCatalogItemDTO.getCatalogItemPrice()));
				objCatalogItemDTO.setCatalogItemInStockQty(Integer.parseInt(objAddCatalogItemDTO.getCatalogItemInStockQty()));
				
				String sCatalogItemType = objAddCatalogItemDTO.getCatalogItemType();
				CatalogItemType objCatalogItemType = objCatalogItemDAO.getCatalogItemTypeByName(sCatalogItemType);								
				objCatalogItemDTO.setCatalogItemType(objCatalogItemType);
								
				objLogger.debug(sMethod + "calling add dao with objCatalogItemDTO: [" + objCatalogItemDTO.toString() + "]");	
				CatalogItem objCatalogItem = objCatalogItemDAO.addNewCatalogItem(objCatalogItemDTO);
				objLogger.debug(sMethod + "objCatalogItem: [" + objCatalogItem.toString() + "]");
				
				return objCatalogItem;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + "Exception adding catalog item: [" + objAddCatalogItemDTO.getCatalogItem()
						+ "] Exception: [" + e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorAddingCatalogItem);
			}

		} else {
			objLogger.warn(sMethod + "objAddCatalogItemDTO is not valid: [" + objAddCatalogItemDTO.toString() + "]");
			throw new BadParameterException(csMsgBadParamAddCatalogItem);
		}


	}

	
	
	////////////////////// Utility Methods for this Class /////////////////////////////////////////
	//
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	public boolean isValidCatalogItemDTO(AddCatalogItemDTO objAddCatalogItemDTO) {
		String sMethod = csCRT + "isValidCatalogItemDTO(): ";
		objLogger.trace(csCR + sMethod + "Entered: objAddCatalogItemDTO: [" + objAddCatalogItemDTO.toString() + "]");
		boolean bValid = false;
		
		String sCatItemName = objAddCatalogItemDTO.getCatalogItemName();
		String sCatItem = objAddCatalogItemDTO.getCatalogItem().trim();
		String sCatItemDesc = objAddCatalogItemDTO.getCatalogItemDescription().trim();
		String sCatItemPrice = objAddCatalogItemDTO.getCatalogItemPrice();
		String sCatItemInStock = objAddCatalogItemDTO.getCatalogItemInStockQty();
		String sCatItemType = objAddCatalogItemDTO.getCatalogItemType().trim();
		
		boolean bCatItemName = Validate.isAlphaNumeric(sCatItemName) && sCatItemName.length() >= 6 && sCatItemName.length() < 20;
		boolean bCatItemValid = sCatItem.length() > 0;
		boolean bCatItemDescValid = sCatItem.length() > 0 && sCatItem.length() < ciDescriptionMaxLen;
		boolean bCatItemPriceValid = Validate.isDouble(sCatItemPrice) && Double.parseDouble(sCatItemPrice) > 0;
		boolean bCatItemInStockValid = Validate.isInt(sCatItemInStock);
		boolean bCatItemTypeValid = Validate.isValidValueInArray(sCatItemType, csarrCatalogItemType);
		
		
		if (bCatItemName && bCatItemValid && bCatItemDescValid && bCatItemPriceValid && bCatItemInStockValid && bCatItemTypeValid) {
			bValid = true;
		}else {
			objLogger.trace(csCR + sMethod + "One or more add Catalog Item Parameters did not pass validation.:");
			objLogger.warn(sMethod + "\t catalog item name: [" + sCatItemName + "] is valid: [" + bCatItemName + "]");
			objLogger.warn(sMethod + "\t catalog item: [" + sCatItem + "] is valid: [" + bCatItemValid + "]");
			objLogger.warn(sMethod + "\t catalog item description: [" + sCatItemDesc + "] is valid: [" + bCatItemDescValid + "]");
			objLogger.warn(sMethod + "\t catalog item price: [" + sCatItemPrice + "] is valid: [" + bCatItemPriceValid + "]");
			objLogger.warn(sMethod + "\t catalog item in stock qty: [" + sCatItemInStock + "] is valid: [" + bCatItemInStockValid + "]");
			objLogger.warn(sMethod + "\t catalog item type: [" + sCatItemType + "] is valid: [" + bCatItemTypeValid + "]");
		}
		
		
		return bValid;
	}


	
	
	
	
}//END Class