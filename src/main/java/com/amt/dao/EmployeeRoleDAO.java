package com.amt.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amt.app.Constants;
import com.amt.dto.UserDTO;
import com.amt.model.EmployeeRole;
import com.amt.model.User;
import com.amt.model.UserType;

@Repository
public class EmployeeRoleDAO implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(EmployeeRoleDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public EmployeeRole getByName(String sName) {
		String sMethod = csCRT + "getByName(): sName: [" + sName + "]";
		objLogger.trace(csCR + sMethod + "Entered");
		EmployeeRole objEmployeeRole = new EmployeeRole();

		String sHQL = "FROM EmployeeRole er WHERE er.employeeRole = :employeeRole";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName + "]");

		Session session = sessionFactory.getCurrentSession();
		try {
			objEmployeeRole = (EmployeeRole) session.createQuery(sHQL).setParameter("employeeRole", sName).getSingleResult();
			objLogger.debug(sMethod + "object from databae: objEmployeeRole: [" + objEmployeeRole + "]");
			return objEmployeeRole;
		} catch (Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName() + "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			return objEmployeeRole;  //return empty
		}
	}
	
	
	@Transactional
	public EmployeeRole addNew() {
		String sMethod = csCRT + "addNew(): "; //objUserDTO: [" + objUserDTO.toString() + "]";
		objLogger.trace(csCR + sMethod + "Entered");
		EmployeeRole objEmployeeRole = new EmployeeRole();
		
		// by this time the service layer would have validated the parameters

		
		return objEmployeeRole;
	}

	
	
	
	
	
	
}//END Class
