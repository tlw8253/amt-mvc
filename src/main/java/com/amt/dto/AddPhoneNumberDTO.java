package com.amt.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//DTO - Data Transfer Object
// This is to be used in the controller class to get parameters by body
@Getter @Setter @EqualsAndHashCode @ToString @NoArgsConstructor
public class AddPhoneNumberDTO {
	private String phoneNumber = "";
	private String phoneNumberType = "";

	public AddPhoneNumberDTO(String phoneNumber, String phoneNumberType) {
		this.phoneNumber = phoneNumber;
		this.phoneNumberType = phoneNumberType;
	}

	
	
	

}// END Class
