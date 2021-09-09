package com.amt.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//DTO - Data Transfer Object
// This is to be used in the controller class to get parameters by body
@Getter @Setter @EqualsAndHashCode @ToString @NoArgsConstructor
public class AddOrderedItemDTO {
	private String catalogItemName;
	private String orderItemPrice;
	private String orderItemQty;

	public AddOrderedItemDTO(String catalogItemName, String orderItemPrice, String orderItemQty) {
		this.catalogItemName = catalogItemName;
		this.orderItemPrice = orderItemPrice;
		this.orderItemQty = orderItemQty;
	}

	
	

}// END Class
