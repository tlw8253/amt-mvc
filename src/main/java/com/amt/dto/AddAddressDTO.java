package com.amt.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//DTO - Data Transfer Object
// This is to be used in the controller class to get parameters by body
@Getter @Setter @EqualsAndHashCode @ToString @NoArgsConstructor
public class AddAddressDTO {
	private String addressLine1 = "";
	private String addressLine2 = "";
	private String addressCity = "";
	private String addressState = "";
	private String addressZipCode = "";
	private String addressType;	

	public AddAddressDTO(String addressLine1, String addressLine2, String addressCity, String addressState, String addressZipCode, String addressType) {
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressCity = addressCity;
		this.addressState = addressState;
		this.addressZipCode = addressZipCode;
		this.addressType = addressType;
	}

	
	
	

}// END Class
