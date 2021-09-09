package com.amt.dto;

import java.util.List;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//DTO - Data Transfer Object
// This is to be used in the controller class to get parameters by body
@Getter @Setter @EqualsAndHashCode @ToString @NoArgsConstructor
public class AddressListDTO {
	String username;
	List<AddressDTO> lstAddressDTO;
	
	public AddressListDTO(String username, List<AddressDTO> lstAddressDTO) {
		this.username = username;
		this.lstAddressDTO = lstAddressDTO;
	}

	
	
	

}// END Class
