package com.amt.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//DTO - Data Transfer Object
// This is to be used in the controller class to get parameters by body
@Getter @Setter @EqualsAndHashCode @ToString @NoArgsConstructor
public class AddCatalogItemDTO {
	private String loginUsername = "";
	private String loginPassword = "";
	private String catalogItemName;
	private String catalogItem;
	private String catalogItemDescription;
	private String catalogItemPrice;
	private String catalogItemInStockQty;
	private String catalogItemType;

	public AddCatalogItemDTO(String catalogItemName, String catalogItem,
			String catalogItemDescription, String catalogItemPrice, String catalogItemInStockQty,
			String catalogItemType) {
		this.catalogItemName = catalogItemName;
		this.catalogItem = catalogItem;
		this.catalogItemDescription = catalogItemDescription;
		this.catalogItemPrice = catalogItemPrice;
		this.catalogItemInStockQty = catalogItemInStockQty;
		this.catalogItemType = catalogItemType;
	}

	
	
	

}// END Class
