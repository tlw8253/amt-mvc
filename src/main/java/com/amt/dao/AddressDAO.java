package com.amt.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
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
import com.amt.dto.CatalogItemDTO;
import com.amt.dto.OrderDTO;
import com.amt.dto.PhoneNumberDTO;
import com.amt.dto.PhoneNumberListDTO;
import com.amt.dto.UserDTO;
import com.amt.model.Address;
import com.amt.model.AddressType;
import com.amt.model.CatalogItem;
import com.amt.model.CatalogItemType;
import com.amt.model.EmployeeRole;
import com.amt.model.Order;
import com.amt.model.OrderStatus;
import com.amt.model.OrderedItem;
import com.amt.model.PhoneNumber;
import com.amt.model.PhoneNumberType;
import com.amt.model.User;
import com.amt.model.UserType;

@Repository
public class AddressDAO implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(AddressDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public List<Address> getAddressForUser(User objUser) throws Exception {
		String sMethod = csCRT + "getAddressForUser(): ";
		objLogger.trace(sMethod + "Entered: objUser: [" + objUser.toString() + "]" + csCR);

		String sHQL = "FROM Address a";
		sHQL = "FROM Address a WHERE a.user.userId = :userId";
		int iUserId = objUser.getUserId();
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: iUserId: [" + iUserId + "]");

		Session session = getSession();
		try {
			List<Address> lstAddress = (List<Address>) session.createQuery(sHQL).setParameter("userId", iUserId).getResultList();
//			List<Address> lstAddress = (ArrayList<Address>) session.createQuery(sHQL).getResultList();
			objLogger.debug(sMethod + "object from databae: lstAddress: [" + lstAddress + "]");
			return lstAddress;
		} catch (Exception e) {
			objLogger.warn(sMethod + "Exception getting address list from database.");
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception("Exception getting address list from database.");
		}
	}	


	
	////////////////////// Utility Methods for this Class
	////////////////////// /////////////////////////////////////////
	//
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	private Session getSession() throws Exception {
		String sMethod = csCRT + "getSession(): ";
		objLogger.trace(sMethod + "Entered." + csCR);

		Session session;

		try {
			session = sessionFactory.getCurrentSession();
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

}// END Class
