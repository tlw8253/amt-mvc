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
@Table(name = Constants.csPhoneNumberTable)

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class PhoneNumber implements Constants {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csPhoneNumberTblPhoneNumberId)
	private int phoneNumbereId = 0;

	@Column(name = csPhoneNumberTblPhoneNumberCountryCode, length = 10, nullable = false)
	private String phoneNumberCountryCode = "01";

	@Column(name = csPhoneNumberTblPhoneNumber, length = 15, nullable = false)
	private String phoneNumber = "";

	@ManyToOne
	@JoinColumn(name = csPhoneNumberTypeTblPhoneNumberTypeId, nullable = false)
	private PhoneNumberType phoneNumberType;		//

	
	@ManyToOne
	@JoinColumn(name = csUserTblUserId, nullable = false)
	private User user;		//

	
	public PhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		phoneNumberType = new PhoneNumberType();
	}
	
	public PhoneNumber(String phoneNumber, PhoneNumberType phoneNumberType) {
		this.phoneNumber = phoneNumber;
		this.phoneNumberType = phoneNumberType;		
	}


	
}
