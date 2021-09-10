package com.amt.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
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
public class OrderDAO implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(OrderDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public Order addOrder(OrderDTO objOrderDTO) throws Exception {
		String sMethod = csCRT + "addOrder(): ";
		objLogger.trace(sMethod + "Entered: objOrderDTO: [" + objOrderDTO.toString() + "]" + csCR);

		// by this time the service layer would have validated the parameters
		User objUser = objOrderDTO.getCustomer();
		double dOrderAmount = objOrderDTO.getOrderAmount();
		Timestamp tsOrderSubmitted = objOrderDTO.getOrderSubmitted();
		Timestamp tsOrderSent = objOrderDTO.getOrderSent();
		OrderStatus objOrderStatus = objOrderDTO.getOrderStatus();
		List<OrderedItem> lstOrderedItem = objOrderDTO.getLstOrderedItem();

		Order objOrder = new Order(dOrderAmount, tsOrderSubmitted, tsOrderSent, objOrderStatus, objUser);

		objLogger.debug(sMethod + "object being added to database: objOrder: [" + objOrder.toString() + "]");
		Session session = getSession();
		try {
			session.persist(objOrder);
			objLogger.debug(
					sMethod + "new object returned from database add: objOrder: [" + objOrder.toString() + "]");
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorAddingOrder);
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorAddingOrder);
		}

		try {
			// now add each ordered items
			for (int iCtr = 0; iCtr < lstOrderedItem.size(); iCtr++) {
				OrderedItem objOrderedItem = lstOrderedItem.get(iCtr);
				objOrderedItem.setOrder(objOrder);
				objLogger.debug(sMethod + "order item being added to database: [" + objOrderedItem.toString() + "]");
				session.persist(objOrderedItem);
				objLogger.debug(sMethod + "order item returned from database: [" + objOrderedItem.toString() + "]");
			}

		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorAddingOrderedItem);
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorAddingOrderedItem);
		}

		return objOrder;

	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public CatalogItem getCatalogItemByName(String sName) throws Exception {
		String sMethod = csCRT + "getCatalogItemByName(): ";
		objLogger.trace(sMethod + "Entered: sName: [" + sName + "]" + csCR);
		CatalogItem objCatalogItem = new CatalogItem();

		String sHQL = "FROM CatalogItem ci WHERE ci.catalogItemName = :catalogItemName";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName + "]");

		Session session = getSession();
		try {
			objCatalogItem = (CatalogItem) session.createQuery(sHQL).setParameter("catalogItemName", sName).getSingleResult();
			objLogger.debug(sMethod + "object from databae: objCatalogItem: [" + objCatalogItem + "]");
			return objCatalogItem;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingCatalogItemByName);
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingCatalogItemByName);
		}
	}	


	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public OrderStatus getOrderStatusByName(String sName) throws Exception {
		String sMethod = csCRT + "getCatalogItemByName(): ";
		objLogger.trace(sMethod + "Entered: sName: [" + sName + "]" + csCR);
		OrderStatus objOrderStatus = new OrderStatus();

		String sHQL = "FROM OrderStatus os WHERE os.orderStatus = :orderStatus";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName + "]");

		Session session = getSession();
		try {
			objOrderStatus = (OrderStatus) session.createQuery(sHQL).setParameter("orderStatus", sName).getSingleResult();
			objLogger.debug(sMethod + "object from databae: objOrderStatus: [" + objOrderStatus + "]");
			return objOrderStatus;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingOrderStatusByName);
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingOrderStatusByName);
		}
	}	

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public User getCustomerByUsername(String sName) throws Exception {
		String sMethod = csCRT + "getCustomerByUsername(): ";
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
