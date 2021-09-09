package com.amt.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//DTO - Data Transfer Object
// This is to be used in the controller class to get parameters by body
@Getter @Setter @EqualsAndHashCode @ToString @NoArgsConstructor
public class AddOrderDTO {
	private String orderAmount = "";
	private List<AddOrderedItemDTO> lstOrderedItem;

	public AddOrderDTO(String orderAmount, List<AddOrderedItemDTO> lstOrderedItem) {
		this.orderAmount = orderAmount;
		this.lstOrderedItem = lstOrderedItem;		
	}

	
	

}// END Class
