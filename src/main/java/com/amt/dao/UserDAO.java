package com.amt.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amt.app.Constants;
import com.amt.dto.AddressDTO;
import com.amt.dto.AddressListDTO;
import com.amt.dto.PhoneNumberDTO;
import com.amt.dto.PhoneNumberListDTO;
import com.amt.dto.UserDTO;
import com.amt.model.Address;
import com.amt.model.AddressType;
import com.amt.model.CatalogItem;
import com.amt.model.EmployeeRole;
import com.amt.model.PhoneNumber;
import com.amt.model.PhoneNumberType;
import com.amt.model.User;
import com.amt.model.UserType;

@Repository
public class UserDAO implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(UserDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public List<User> getUsersByType(String sType) throws Exception {
		String sMethod = csCRT + "getUsersByType(): ";
		objLogger.trace(sMethod + "Entered.");
		List<User> lstUser = new ArrayList<User>();

		String sHQL = "FROM User u WHERE u.userType.userType = :userType";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sType: [" + sType + "]");

		Session session = getSession();
		try {
			lstUser = (List<User>) session.createQuery(sHQL).setParameter("userType", sType).getResultList();
			objLogger.debug(sMethod + "object from databae: lstUser: [" + lstUser.toString() + "]");
			return lstUser;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingUserList);
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingUserList);
		}
	}	

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public List<User> getUsers() throws Exception {
		String sMethod = csCRT + "getUsers(): ";
		objLogger.trace(sMethod + "Entered.");
		List<User> lstUser = new ArrayList<User>();

		String sHQL = "FROM User u";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]");

		Session session = getSession();
		try {
			lstUser = (List<User>) session.createQuery(sHQL).getResultList();
			objLogger.debug(sMethod + "object from databae: lstUser: [" + lstUser.toString() + "]");
			return lstUser;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingUserList);
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingUserList);
		}
	}	

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public User getUserByUsername(String sName) throws Exception {
		String sMethod = csCRT + "getUserByUsername(): ";
		objLogger.trace(sMethod + "Entered: sName: [" + sName + "]");
		User objUser = new User();

		String sHQL = "FROM User u WHERE u.username = :username";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName + "]");

		Session session = sessionFactory.getCurrentSession();
		try {
			objUser = (User) session.createQuery(sHQL).setParameter("username", sName).getSingleResult();
			objLogger.debug(sMethod + "object from databae: objUser: [" + objUser + "]");
			return objUser;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingUserByUsername);
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingUserByUsername);
		}
	}	
	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public User addNewUser(UserDTO objUserDTO) throws Exception {
		String sMethod = csCRT + "addNewUser(): ";
		objLogger.trace(sMethod + "Entered: objUserDTO: [" + objUserDTO.toString() + "]");

		// by this time the service layer would have validated the parameters
		String sUsername = objUserDTO.getUsername();
		String sFirstName = objUserDTO.getFirstName();
		String sLastName = objUserDTO.getLastName();
		String sPassword = objUserDTO.getPassword();
		String sPasswordSalt = objUserDTO.getPasswordSalt();
		String sEmail = 	objUserDTO.getEmail();
		UserType objUserType = objUserDTO.getUserType();
		EmployeeRole objEmployeeRole = objUserDTO.getEmployeeRole();
		
		User objNewUser = new User(sUsername, sPassword, sPasswordSalt, sFirstName, sLastName, sEmail);
		objNewUser.setUserType(objUserType);
		objNewUser.setEmployeeRole(objEmployeeRole);
		
		objLogger.debug(sMethod + "object being added to databae: objNewUser: [" + objNewUser.toString() + "]");
		Session session = sessionFactory.getCurrentSession();
		try {			
			session.persist(objNewUser);
			objLogger.debug(sMethod + "new user object returned from database add: objNewUser: [" + objNewUser.toString() + "]");
			return objNewUser;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorAddingUser);
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorAddingUser);
		}

	}
	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public UserType getUserTypeByName(String sName) throws Exception {
		String sMethod = csCRT + "getUserTypeByName(): ";
		objLogger.trace(sMethod + "Entered: sName: [" + sName + "]");

		UserType ojbUserType = new UserType();

		String sHQL = "FROM UserType ut WHERE ut.userType = :userType";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName + "]");

		objLogger.debug(sMethod + "getting sessionFactory.getCurrentSession()");
		Session session = getSession();
		
		

		try {
			ojbUserType = (UserType) session.createQuery(sHQL).setParameter("userType", sName).getSingleResult();
			objLogger.debug(sMethod + "object from databae: ojbUserType: [" + ojbUserType + "]");
			return ojbUserType;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingUserTypeByName + " sName: [" + sName + "]");
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingUserTypeByName);
		}
		
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public EmployeeRole getEmployeeRoleByName(String sName) throws Exception {
		String sMethod = csCRT + "getEmployeeRoleByName(): ";
		objLogger.trace(sMethod + "Entered: sName: [" + sName + "]");

		EmployeeRole objEmployeeRole = new EmployeeRole();

		String sHQL = "FROM EmployeeRole er WHERE er.employeeRole = :employeeRole";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName + "]");

		objLogger.debug(sMethod + "getting sessionFactory.getCurrentSession()");
		Session session = getSession();

		try {
			objEmployeeRole = (EmployeeRole) session.createQuery(sHQL).setParameter("employeeRole", sName).getSingleResult();
			objLogger.debug(sMethod + "object from databae: ojbUserType: [" + objEmployeeRole + "]");
			return objEmployeeRole;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingEmployeeRoleByName + " sName: [" + sName + "]");
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingEmployeeRoleByName);
		}
		
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public AddressType getAddressTypeByName(String sName) throws Exception {
		String sMethod = csCRT + "getAddressTypeByName(): ";
		objLogger.trace(sMethod + "Entered: sName: [" + sName + "]");

		AddressType objAddressType = new AddressType();

		String sHQL = "FROM AddressType at WHERE at.addressType = :addressType";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName + "]");

		objLogger.debug(sMethod + "getting sessionFactory.getCurrentSession()");
		Session session = getSession();

		try {
			objAddressType = (AddressType) session.createQuery(sHQL).setParameter("addressType", sName).getSingleResult();
			objLogger.debug(sMethod + "object from databae: objAddressType: [" + objAddressType + "]");
			return objAddressType;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingAddressTypeByName + " sName: [" + sName + "]");
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingAddressTypeByName);
		}
		
	}
	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public User addUserAddress(AddressListDTO objAddressListDTO) throws Exception {
		String sMethod = csCRT + "addUserAddress(): ";
		objLogger.trace(sMethod + "Entered: objAddressListDTO: [" + objAddressListDTO.toString() + "]");
		
		Session session = sessionFactory.getCurrentSession();
		for (int iCtr=0; iCtr<objAddressListDTO.getLstAddressDTO().size(); iCtr++) {
			AddressDTO objAddressDTO = objAddressListDTO.getLstAddressDTO().get(iCtr);
			
			String sAddressLine1 = objAddressDTO.getAddressLine1();
			String sAddressLine2 = objAddressDTO.getAddressLine2();
			String sAddressCity = objAddressDTO.getAddressCity();
			String sAddressState = objAddressDTO.getAddressState();
			String sAddressZipCode = objAddressDTO.getAddressZipCode();
			
			Address objAddress = new Address(sAddressLine1, sAddressLine2, sAddressCity, sAddressState, sAddressZipCode);
			objAddress.setAddressType(objAddressDTO.getAddressType());
			objAddress.setUser(objAddressDTO.getUser());
			
			try {			
				objLogger.debug(sMethod + "for username: [" + objAddress.getUser().getUsername() + "]");
				objLogger.debug(sMethod + "\tadding address: [" + objAddress.toString() + "]");
				session.persist(objAddress);
				objLogger.debug(sMethod + "new address returned from database add: objAddress: [" + objAddress.toString() + "]");
			} catch (Exception e) {
				objLogger.warn(sMethod + csMsgDB_ErrorAddingAddress);
				objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
				objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
				objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
				throw new Exception(csMsgDB_ErrorAddingAddress);
			}			
		}

		String sUsername = objAddressListDTO.getUsername();
		
		String sHQL = "FROM User u WHERE u.username = :username";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sUsername: [" + sUsername + "]");

		try {
			User objUser = (User) session.createQuery(sHQL).setParameter("username", sUsername).getSingleResult();
			objLogger.debug(sMethod + "object from databae: objUser: [" + objUser + "]");
			return objUser;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingUserByUsername);
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingUserByUsername);
		}
		

	}	
	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public PhoneNumberType getPhoneNumberTypeByName(String sName) throws Exception {
		String sMethod = csCRT + "getPhoneNumberTypeByName(): ";
		objLogger.trace(sMethod + "Entered: sName: [" + sName + "]");

		PhoneNumberType objPhoneNumberType = new PhoneNumberType();

		String sHQL = "FROM PhoneNumberType pnt WHERE pnt.phoneNumberType = :phoneNumberType";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName + "]");

		objLogger.debug(sMethod + "getting sessionFactory.getCurrentSession()");
		Session session = getSession();

		try {
			objPhoneNumberType = (PhoneNumberType) session.createQuery(sHQL).setParameter("phoneNumberType", sName).getSingleResult();
			objLogger.debug(sMethod + "object from databae: objAddressType: [" + objPhoneNumberType + "]");
			return objPhoneNumberType;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingPhoneNumberTypeByName + " sName: [" + sName + "]");
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingPhoneNumberTypeByName);
		}
		
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public User addUserPhoneNumber(PhoneNumberListDTO ojbPhoneNumberListDTO) throws Exception {
		String sMethod = csCRT + "addUserPhoneNumber(): ";
		objLogger.trace(sMethod + "Entered: ojbPhoneNumberListDTO: [" + ojbPhoneNumberListDTO.toString() + "]");
		
		Session session = sessionFactory.getCurrentSession();
		for (int iCtr=0; iCtr < ojbPhoneNumberListDTO.getLstPhoneNumberDTO().size(); iCtr++) {
			PhoneNumberDTO objPhoneNumberDTO = ojbPhoneNumberListDTO.getLstPhoneNumberDTO().get(iCtr);
			
			String sPhoneNumber = objPhoneNumberDTO.getPhoneNumber();
			
			PhoneNumber objPhoneNumber = new PhoneNumber(sPhoneNumber);
			objPhoneNumber.setPhoneNumberType(objPhoneNumberDTO.getPhoneNumberType());
			objPhoneNumber.setUser(objPhoneNumberDTO.getUser());
			
			try {			
				objLogger.debug(sMethod + "for username: [" + objPhoneNumber.getUser().getUsername() + "]");
				objLogger.debug(sMethod + "\tadding phone number: [" + objPhoneNumber.toString() + "]");
				session.persist(objPhoneNumber);
				objLogger.debug(sMethod + "new address returned from database add: objPhoneNumber: [" + objPhoneNumber.toString() + "]");
			} catch (Exception e) {
				objLogger.warn(sMethod + csMsgDB_ErrorAddingPhoneNumber);
				objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
				objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
				objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
				throw new Exception(csMsgDB_ErrorAddingPhoneNumber);
			}			
		}

		String sUsername = ojbPhoneNumberListDTO.getUsername();
		
		String sHQL = "FROM User u WHERE u.username = :username";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sUsername: [" + sUsername + "]");

		try {
			User objUser = (User) session.createQuery(sHQL).setParameter("username", sUsername).getSingleResult();
			objLogger.debug(sMethod + "object from databae: objUser: [" + objUser + "]");
			return objUser;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingUserByUsername);
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingUserByUsername);
		}
		

	}	

	
	
	////////////////////// Utility Methods for this Class /////////////////////////////////////////
	//
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	private Session getSession() throws Exception {
		String sMethod = csCRT + "getSession(): ";
		objLogger.trace(sMethod + "Entered.");
		
		Session session;

		try {
			session =  sessionFactory.getCurrentSession();
			objLogger.debug(sMethod + "session.isConnected(): [" + session.isConnected() + "]");
		} catch (Exception e) {
			objLogger.warn(sMethod + "Exception: getting sessionFactory.getCurrentSession()");
			objLogger.debug(sMethod + "Attempting sessionFactory.openSession()");
			try {
				session = sessionFactory.openSession();
			} catch (Exception e1) {
				objLogger.debug(sMethod + "Exception: opening sessionFactory.openSession()");
				objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e1.getCause() + "]");
				objLogger.warn(sMethod + csCRT + "Exception: class name [" + e1.getClass().getName() + "]");
				objLogger.warn(sMethod + csCRT + "Exception: message: [" + e1.getMessage() + "]");
				throw new Exception(csMsgDB_ErrorOpenSession);
			}
		}		
		return session;
	}
	
	
	
}//END Class
