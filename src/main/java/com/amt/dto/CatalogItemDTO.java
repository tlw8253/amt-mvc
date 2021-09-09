package com.amt.dto;

import com.amt.model.AddressType;
import com.amt.model.CatalogItemType;
import com.amt.model.PhoneNumberType;
import com.amt.model.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//DTO - Data Transfer Object
// This is to be used in the controller class to get parameters by body
@Getter @Setter @EqualsAndHashCode @ToString @NoArgsConstructor
public class CatalogItemDTO {
	private String catalogItemName;
	private String catalogItem;
	private String catalogItemDescription;
	private double catalogItemPrice;
	private int catalogItemInStockQty;
	private CatalogItemType catalogItemType;

	public CatalogItemDTO(String catalogItemName, String catalogItem, String catalogItemDescription, double catalogItemPrice, int catalogItemInStockQty) {
		this.catalogItemName = catalogItemName;
		this.catalogItem = catalogItem;
		this.catalogItemDescription = catalogItemDescription;
		this.catalogItemPrice = catalogItemPrice;
		this.catalogItemInStockQty = catalogItemInStockQty;
	}

	
	
	

}// END Class
