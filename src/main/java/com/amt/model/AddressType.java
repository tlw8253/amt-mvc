package com.amt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.amt.app.Constants;

@Entity
@Table(name = Constants.csAddressTypeTable)

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class AddressType implements Constants {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csAddressTypeTblAddressTypeId)
	private int addressTypeId = 0;

	@Column(name = csAddressTypeTblAddressType, length = 50, nullable = false, unique = true)
	private String addressType = "";

	@Column(name = csAddressTypeTblAddressTypeDesc, length = 150, nullable = false)
	private String addressTypeDesc = "";


	public AddressType(String addressType, String addressTypeDesc) {
		this.addressType = addressType;
		this.addressTypeDesc = addressTypeDesc;
	}


	
	
	
}
