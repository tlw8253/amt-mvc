package com.amt.dto;

import com.amt.model.AddressType;
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
public class PhoneNumberDTO {
	private String phoneNumber = "";
	private PhoneNumberType phoneNumberType;
	private User user;

	public PhoneNumberDTO(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
	
	

}// END Class
