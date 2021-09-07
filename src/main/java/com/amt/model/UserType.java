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
@Table(name = Constants.csUserTypeTable)

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class UserType implements Constants {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csUserTypeTblUserTypeId)
	private int userTypeId = 0;

	@Column(name = csUserTypeTblUserType, length = 50, nullable = false, unique = true)
	private String userType = "";

	@Column(name = csUserTypeTblUserTypeDesc, length = 150, nullable = false)
	private String userTypeDesc = "";

	public UserType(String userType, String userTypeDesc) {
		this.userType = userType;
		this.userTypeDesc = userTypeDesc;
	}


	
	
	
}
