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
@Table(name = Constants.csPhoneNumberTypeTable)


@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class PhoneNumberType implements Constants {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csPhoneNumberTypeTblPhoneNumberTypeId)
	private int phoneNumberTypeId = 0;

	@Column(name = csPhoneNumberTypeTblPhoneNumberType, length = ciRoleTypeLen, nullable = false, unique = true)
	private String phoneNumberType = "";

	@Column(name = csPhoneNumberTypeTblPhoneNumberTypeDesc, length = ciRoleTypeDescLen, nullable = false)
	private String phoneNumberTypeDesc = "";

	public PhoneNumberType(String phoneNumberType, String phoneNumberTypeDesc) {
		this.phoneNumberType = phoneNumberType;
		this.phoneNumberTypeDesc = phoneNumberTypeDesc;		
	}


	
	
	
}
