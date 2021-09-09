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
		objLogger.trace(sMethod + "Entered: objOrderDTO: [" + objOrderDTO.toString() + "]");

		// by this time the service layer would have validated the parameters
		User objUser = objOrderDTO.getUser();
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

	////////////////////// Utility Methods for this Class
	////////////////////// /////////////////////////////////////////
	//
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	private Session getSession() throws Exception {
		String sMethod = csCRT + "getSession(): ";
		objLogger.trace(sMethod + "Entered.");

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
