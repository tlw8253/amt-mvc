package com.amt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.amt.app.Constants;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = Constants.csUserTable)
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @ToString
public class User implements Constants {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csUserTblUserId)
	private int userId = 0;

	@Column(name = csUserTblUsername, length = 50, nullable = false, unique = true)
	private String username = "";

	@Column(name = csUserTblPassword, length = 50, nullable = false)
	private String password = "";

	@Column(name = csUserTblPasswordSalt, length = 50, nullable = false)
	private String passwordSalt = "";

	@Column(name = csUserTblFirstName, length = 100, nullable = false)
	private String firstName = "";

	@Column(name = csUsrTblLastName, length = 100, nullable = false)
	private String lastName = "";

	@Column(name = csUserTblEmail, length = 150, nullable = false, unique = true)
	private String email = "";

	@ManyToOne
	@JoinColumn(name = csEmployeeRolesTblEmployeeRoleId, nullable = false)
	private EmployeeRole employeeRole;

	@ManyToOne
	@JoinColumn(name = csUserTypeTblUserTypeId, nullable = false)
	private UserType userType;		//employee or customer

	
	public User(String sUsername, String sPassword, String sFirstName, String sLastName, String sEmail) {
		this.username = sUsername;
		this.password = sPassword;
		this.firstName = sFirstName;
		this.lastName = sLastName;
		this.email = sEmail;
	}

	public User(String username, String password, String passwordSalt, String firstName, String lastName, String email) {
		this.username = username;
		this.password = password;
		this.passwordSalt = passwordSalt;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}


	
}
