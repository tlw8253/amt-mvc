package com.amt.dto;

import com.amt.app.Constants;
import com.amt.model.EmployeeRole;
import com.amt.model.UserType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @EqualsAndHashCode @ToString @NoArgsConstructor
public class UserDTO implements Constants {

	private int userId = 0;
	private String username = "";
	private String password = "";
	private String passwordSalt = "";
	private String firstName = "";
	private String lastName = "";
	private String email = "";	
	private UserType userType;
	private EmployeeRole employeeRole;

}
