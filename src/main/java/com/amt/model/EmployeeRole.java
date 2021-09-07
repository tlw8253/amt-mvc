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
@Table(name = Constants.csEmployeeRolesTable)

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class EmployeeRole implements Constants {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csEmployeeRolesTblEmployeeRoleId)
	private int employeeRoleId = 0;

	@Column(name = csEmployeeRolesTblEmployeeRole, length = 50, nullable = false, unique = true)
	private String employeeRole = "";
	
	@Column(name = csEmployeeRolesTblEmployeeRoleDesc, length = 150, nullable = false)
	private String employeeRoleDesc = "";

	
	public EmployeeRole(String employeeRole, String employeeRoleDesc) {
		this.employeeRole = employeeRole;
		this.employeeRoleDesc = employeeRoleDesc;
	}


	
	
	
	
}
