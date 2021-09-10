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
import com.amt.dto.CatalogItemDTO;
import com.amt.dto.PhoneNumberDTO;
import com.amt.dto.PhoneNumberListDTO;
import com.amt.dto.UserDTO;
import com.amt.model.Address;
import com.amt.model.AddressType;
import com.amt.model.CatalogItem;
import com.amt.model.CatalogItemType;
import com.amt.model.EmployeeRole;
import com.amt.model.PhoneNumber;
import com.amt.model.PhoneNumberType;
import com.amt.model.User;
import com.amt.model.UserType;

@Repository
public class CatalogItemDAO implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(CatalogItemDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public List<CatalogItem> getCatalogList() throws Exception {
		String sMethod = csCRT + "getCatalogList(): ";
		objLogger.trace(sMethod + "Entered.");
		List<CatalogItem> lstCatalogItem = new ArrayList<CatalogItem>();

		String sHQL = "FROM CatalogItem ci";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]");

		Session session = getSession();
		try {
			lstCatalogItem = (List<CatalogItem>) session.createQuery(sHQL).getResultList();
			objLogger.debug(sMethod + "object from databae: lstCatalogItem: [" + lstCatalogItem.toString() + "]");
			return lstCatalogItem;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorGettingCatalogList);
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorGettingCatalogList);
		}
	}	

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Transactional
	public CatalogItem getCatalogItemByName(String sName) throws Exception {
		String sMethod = csCRT + "getCatalogItemByName(): ";
		objLogger.trace(sMethod + "Entered: sName: [" + sName + "]");
		CatalogItem objCatalogItem = new CatalogItem();

		String sHQL = "FROM CatalogItem ci WHERE ci.catalogItemName = :catalogItemName";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName + "]");

		Session session = getSession();
		try {
			objCatalogItem = (CatalogItem) session.createQuery(sHQL).setParameter("catalogItem", sName).getSingleResult();
			objLogger.debug(sMethod + "object from databae: objUser: [" + objCatalogItem + "]");
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
	public CatalogItemType getCatalogItemTypeByName(String sName) throws Exception {
		String sMethod = csCRT + "getCatalogItemTypeByName(): ";
		objLogger.trace(sMethod + "Entered: sName: [" + sName + "]");
		CatalogItemType objCatalogItemType = new CatalogItemType();

		String sHQL = "FROM CatalogItemType cit WHERE cit.catalogItemType = :catalogItemType";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName + "]");

		Session session = getSession();
		try {
			objCatalogItemType = (CatalogItemType) session.createQuery(sHQL).setParameter("catalogItemType", sName).getSingleResult();
			objLogger.debug(sMethod + "object from databae: objCatalogItemType: [" + objCatalogItemType + "]");
			return objCatalogItemType;
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
	public CatalogItem addNewCatalogItem(CatalogItemDTO objCatalogItemDTO) throws Exception {
		String sMethod = csCRT + "addNewCatalogItem(): ";
		objLogger.trace(sMethod + "Entered: objCatalogItemDTO: [" + objCatalogItemDTO.toString() + "]");

		// by this time the service layer would have validated the parameters
		String sCatalogItemName = objCatalogItemDTO.getCatalogItemName();
		String sCatalogItem = objCatalogItemDTO.getCatalogItem();
		String sCatalogItemDescription = objCatalogItemDTO.getCatalogItemDescription();
		double dCatalogItemPrice = objCatalogItemDTO.getCatalogItemPrice();
		int iCatalogItemInStockQty = objCatalogItemDTO.getCatalogItemInStockQty();

		CatalogItemType objCatalogItemType = objCatalogItemDTO.getCatalogItemType();
		
		CatalogItem objCatalogItem = new CatalogItem(sCatalogItemName, sCatalogItem, sCatalogItemDescription, dCatalogItemPrice, iCatalogItemInStockQty);
		objCatalogItem.setCatalogItemType(objCatalogItemType);
		
		objLogger.debug(sMethod + "object being added to databae: objCatalogItem: [" + objCatalogItem.toString() + "]");
		Session session = getSession();
		try {			
			session.persist(objCatalogItem);
			objLogger.debug(sMethod + "new user object returned from database add: objCatalogItem: [" + objCatalogItem.toString() + "]");
			return objCatalogItem;
		} catch (Exception e) {
			objLogger.warn(sMethod + csMsgDB_ErrorAddingUser);
			objLogger.warn(sMethod + csCRT + "Exception: cause: [" + e.getCause() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: class name [" + e.getClass().getName() + "]");
			objLogger.warn(sMethod + csCRT + "Exception: message: [" + e.getMessage() + "]");
			throw new Exception(csMsgDB_ErrorAddingUser);
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
