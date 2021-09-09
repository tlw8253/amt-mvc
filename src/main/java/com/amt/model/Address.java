package com.amt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.amt.app.Constants;

@Entity
@Table(name = Constants.csAddressTable)

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class Address implements Constants {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csAddressTblAddressId)
	private int addressTypeId = 0;

	@Column(name = csAddressTblAddressLine1, length = 100, nullable = false)
	private String addressLine1 = "";

	@Column(name = csAddressTblAddressLine2, length = 100, nullable = true)
	private String addressLine2 = "";
	
	@Column(name = csAddressTblAddressCity, length = 100, nullable = false)
	private String addressCity = "";

	@Column(name = csAddressTblAddressState, length = 5, nullable = false)
	private String addressState = "";

	@Column(name = csAddressTblAddressZipCode, length = 15, nullable = false)
	private String addressZipCode = "";

	@Column(name = csAddressTblAddressCountry, length = 100, nullable = false)
	private String addressCountry = "United States";

	@ManyToOne
	@JoinColumn(name = csAddressTypeTblAddressTypeId, nullable = false)
	private AddressType addressType;		//

	@ManyToOne
	@JoinColumn(name = csUserTblUserId, nullable = false)
	private User user;		//
	

	public Address(String addressLine1, String addressLine2, String addressCity, String addressState, String addressZipCode) {
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressCity = addressCity;
		this.addressState = addressState;
		this.addressZipCode = addressZipCode;
	}



	
	
}
