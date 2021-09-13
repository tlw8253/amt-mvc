package com.amt.app;

public interface Constants {

	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// misc constants
	String csCR = "\n";
	String csCRT = csCR + "\t ";
	String csCRCRT = csCR + csCRT;

	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// database constants
	String csDatabaseName = "amt_online_sys"; // database name
	
	String csAWS_DB_URL = "jdbc:mariadb://database-1.cdigirn7b0ro.us-east-2.rds.amazonaws.com:3306/";
	String csLocalHostURL = "jdbc:mariadb://localhost:3306/";

	String csAWS_Username = "admin";
	String csLocalHost_Username = "root";

	String csAWS_Pwd = "Black1357Bear";
	String csLocalHost_Pwd = "tlw8253";
	
	String csCurrentDBURL = csAWS_DB_URL;
	String csCurrentUsername = csAWS_Username;
	String csCurrentPwd = csAWS_Pwd;

//	String csCurrentDBURL = csLocalHostURL;
//	String csCurrentUsername = csLocalHost_Username;
//	String csCurrentPwd = csLocalHost_Pwd;

	
	String csDatabaseConnectionURL = csCurrentDBURL + csDatabaseName;
	String csDatabaseConnectionUsername = csCurrentUsername;
	String csDatabaseConnectionPwd = csCurrentPwd;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// program config constants
	String csPackagesToScan = "com.amt.model";
	//String csCrossOriginHttp = "http://localhost:4201";
	//String csCrossOriginHttp = "http://localhost:8088";
	String csCrossOriginHttp = "*";
	//@CrossOrigin(originPatterns = "*", allowCredentials = "true")

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// End Points
	String csRootEndpoint = "/amt";

	String csRootEndpointLogin = "/amt_login";
	String csRootEndpointLogout = "/amt_logout";
	String csRootEndpointCurrentUser = "/amt_current_user";
	String csRootEndpointSessionValidate = "/amt_current_session";


	// Return status codes
	// source: https://developer.mozilla.org/en-US/docs/Web/HTTP/Status
	int ciStatusCodeSuccess = 200; // The request has succeeded.
	int ciStatusCodeSuccessCreated = 201; // The request has succeeded and a new resource has been created as a result.
	int ciStatusCodeSuccessNoContent = 204; // There is no content to send for this request,

	int ciStatusCodeErrorBadRequest = 400; // The server could not understand the request due to invalid syntax.
	int ciStatusCodeNotFound = 404; // The server can not find the requested resource. In the browser.
	int ciStatusCodeImA_Teapot = 418; // The server refuses the attempt to brew coffee with a teapot.

	int ciStatusCodeInternalServerError = 500; // The server has encountered a situation it doesn't know how to handle.

	// password special characters allowed for this program
	String csPasswordAllowedSpecialChars = "~$^_!#%+-<>";
	String csAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	String csNumeric = "0123456789";
	String csAllPasswordCharacters = csNumeric + csAlphabet + csPasswordAllowedSpecialChars;

	// system parameters
	String csClientStaticFolder = "AMT-Client";
	int ciListingPort = 3025;
	String csSessionCurrentUser = "current_user";

	String csRootEndpointAdminStatus = "/amt_admin_status";

	// HTTP request parameter constants
	String csParamPathUserId = "user_id";
	String csParamUserName = "username";
	String csParamPassword = "password";

	// other constants
	int ciUsernameMinLength = 6;
	int ciUsernameMaxLength = 15;
	int ciReimbRecByIdentifierAuthor = 10;
	int ciReimbRecByIdentifierResolver = 20;
	int ciUserMinPassword = 8;
	int ciUserMaxPassword = 15;
	int ciDescriptionMaxLen = 255;

	
	int ciRoleTypeLen = 50;
	int ciRoleTypeDescLen = 150;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// table constants these must match the table attributes

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// table: Address
	String csAddressTable = "amt_address"; // table name
	String csDBAddressTable = csDatabaseName + "." + csAddressTable; // qualified table name
	String csAddressTblAddressId = "address_id"; // PK primary key
	String csAddressTblAddressLine1 = "address_line_1"; // String
	String csAddressTblAddressLine2 = "address_line_2"; // String
	String csAddressTblAddressCity = "address_city";
	String csAddressTblAddressState = "address_state";
	String csAddressTblAddressZipCode = "address_zip_code";
	String csAddressTblAddressCountry = "address_country";

	// table: Address Type
	String csAddressTypeTable = "amt_address_type"; // table name
	String csDBAddressTypeTable = csDatabaseName + "." + csAddressTypeTable; // qualified table name
	String csAddressTypeTblAddressTypeId = "address_type_id"; // PK primary key
	String csAddressTypeTblAddressType = "address_type"; // String
	String csAddressTypeTblAddressTypeDesc = "address_type_desc"; // String
	String[] carrAddressType = { "BILLING", "SHIPPING", "MAILING"}; //
	String[] carrAddressTypeDesc = {"BILLING: The address where the bill of credit card used is mailed.",
									"SHIPPING: The address where the customer wants the merchandise shipped.",
									"MAILING: The home or mailing address where the customer resides."};
	enum enumAddressType {
		BILLING(0), SHIPPING(1), MAILING(2);
		public int pos;
		enumAddressType(int pos) {
			this.pos = pos;
		}
		int pos() {
			return pos;
		}
	};

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// table: Catalog Item
	String csCatalogItemTable = "amt_catalog_item"; // table name
	String csDBCatalogItemTable = csDatabaseName + "." + csCatalogItemTable; // qualified table name
	String csCatalogItemTblCatalogItemId = "catalog_id"; // PK primary key
	String cscsCatalogItemTblCatalogItemName = "catalog_item_name";		//alternate key, unique
	String csCatalogItemTblCatalogItem = "catalog_item";
	String csCatalogItemTblCatalogItemDesc = "catalog_item_description";
	String csCatalogItemTblCatalogItemPrice = "catalog_item_price";
	String csCatalogItemTblCatalogItemInStockQty = "catalog_item_in_stock_qty";

	// table: Catalog Item Type
	String csCatalogItemTypeTable = "amt_catalog_item_type"; // table name
	String csDBCatalogTypeTable = csDatabaseName + "." + csCatalogItemTypeTable; // qualified table name
	String csCatalogItemTypeTblCatalogItemTypeId = "catalog_item_type_id"; // PK primary key
	String csCatalogItemTypeTblCatalogItemType = "catalog_item_type"; // String
	String csCatalogItemTypeTblCatalogItemTypeDesc = "catalog_item_type_desc"; // String
	String[] csarrCatalogItemType = { "BEANS", "GROUND", "SYRUP", "ACCESSORY", "APPAREL" }; 
	String[] csarrCatalogItemTypeDesc = { "BEANS: coffee beans to grind for coffee makers and espresso machines.", 
										"GROUND: already ground coffee for coffee makers and espresso machines.", 
										"SYRUP: sugar and sugar free liquid added to brewed coffee to sweeten", 
										"ACCESSORY: coffee cups, makers, and other items related to coffee.", 
										"APPAREL: t-shirts, hats, and other apparel items related to coffee and company logos." }; 

	enum enumCatalogItemType {
		BEANS(0), GROUND(1), SYRUP(2), ACCESSORY(3), APPAREL(4);
		public int pos;
		enumCatalogItemType(int pos) {
			this.pos = pos;
		}
		int pos() {
			return pos;
		}
	};


	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// table: order
	String csOrderTable = "amt_order"; // table name
	String csDBOrderTable = csDatabaseName + "." + csOrderTable; // qualified table name
	String csOrderTblOrderId = "order_id"; // PK primary key
	String csOrderTblSubmitted = "order_submitted";
	String csOrderTblSent = "order_sent";
	String csOrderTblAmount = "order_amount";
	String csOrderTblOrderItems = "order_items";
	
	// table: order items
	String csOrderItemsTable = "amt_order_items"; // table name
	String csDBOrderItemsTable = csDatabaseName + "." + csOrderItemsTable; // qualified table name
	String csOrderItemsTblOrderItemsId = "order_item_id"; // PK primary key
	String csOrderItemsTblOrderPrice = "order_item_price";
	String csOrderItemsTblItemQty = "order_item_qty";

	
	// table: order Status
	String csOrderStatusTable = "amt_order_status"; // table name
	String csDBOrderStatusTable = csDatabaseName + "." + csOrderStatusTable; // qualified table name
	String csOrderStatusTblOrderStatusId = "order_status_id"; // PK primary key
	String csOrderStatusTblOrderStatus = "order_status"; // String
	String csOrderStatusTblOrderStatusDesc = "order_status_desc"; // String
	String[] csarrOrderStatus = { "NEW", "HOLD", "PENDING", "PAID", "FINAL" };
	String[] csarrOrderStatusDesc = { "NEW: The order has just been created and saved for future processing.", 
									"HOLD: The order is on hold pending further customer action.", 
									"PENDING: The order is pending waiting on some processing", 
									"PAID: The order has been paid.", 
									"FINAL: The order has been paid and sent to fullfilment." };
	
	enum enumOrderStatus {
		NEW(0), HOLD(1), PENDING(2), PAID(3), FINAL(4);
		public int pos;
		enumOrderStatus(int pos) {
			this.pos = pos;
		}
		int pos() {
			return pos;
		}
	};

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// table: PhoneNumber
	String csPhoneNumberTable = "amt_phone_numbers"; // table name
	String csDBPhoneNumberTable = csDatabaseName + "." + csPhoneNumberTable; // qualified table name
	String csPhoneNumberTblPhoneNumberId = "phone_number_id"; // PK primary key
	String csPhoneNumberTblPhoneNumberCountryCode = "phone_number_country_code"; // String
	String csPhoneNumberTblPhoneNumber = "phone_number"; // String	


	// table: PhoneNumberType
	String csPhoneNumberTypeTable = "amt_phone_number_type"; // table name
	String csDBPhoneNumberTypeTable = csDatabaseName + "." + csPhoneNumberTypeTable; // qualified table name
	String csPhoneNumberTypeTblPhoneNumberTypeId = "phone_number_type_id"; // PK primary key
	String csPhoneNumberTypeTblPhoneNumberType = "phone_number_type"; // String
	String csPhoneNumberTypeTblPhoneNumberTypeDesc = "phone_number_type_desc"; // String
	String[] csarrPhoneNumberType = { "MOBILE", "HOME", "WORK", "OTHER" };
	String[] csarrPhoneNumberTypeDesc = { "MOBILE: Phone number belongs to a mobile device.", 
									"HOME: Phone number belongs to a traditional landline or device considered their home number.", 
									"WORK: Phone number belongs to their work location.", 
									"OTHER: Phone number belongsto some other location or device not listed."};
	
	enum enumPhoneNumberType {
		MOBILE(0), HOME(1), WORK(2), OTHER(3);
		public int pos;
		enumPhoneNumberType(int pos) {
			this.pos = pos;
		}
		int pos() {
			return pos;
		}
	};

	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// table: User Type
	String csUserTypeTable = "amt_user_type"; // table name
	String csDBUserTypeTable = csDatabaseName + "." + csUserTypeTable; // qualified table name

	String csUserTypeTblUserTypeId = "user_type_id"; // PK primary key
	String csUserTypeTblUserType = "user_type"; // String
	String csUserTypeTblUserTypeDesc = "user_type_desc"; // String
	String[] csarUserType = { "EMPLOYEE", "CUSTOMER" };
	String[] csarUserTypeDesc = { "EMPLOYEE: This user type is an active employee of the AMT system.",
								"CUSTOMER: This user type is an external customer viewing the catalog and buying merchandise." };
	enum enumUserType {
		EMPLOYEE(0), CUSTOMER(1);
		public int pos;
		enumUserType(int pos) {
			this.pos = pos;
		}
		int pos() {
			return pos;
		}
	};

	// table: User Roles
	String csEmployeeRolesTable = "amt_employee_roles"; // table name
	String csDBEmployeeRolesTable = csDatabaseName + "." + csEmployeeRolesTable; // qualified table name
	String csEmployeeRolesTblEmployeeRoleId = "employee_role_id"; // PK primary key
	String csEmployeeRolesTblEmployeeRole = "employee_role"; // String
	String csEmployeeRolesTblEmployeeRoleDesc = "employee_role_desc"; // String
	String[] csarEmployeeRoles = { "EMPLOYEE", "CATALOG_ADMIN", "CATALOG_EMPLOYEE", "CUSTOMER" };
	String[] csarEmployeeRolesDesc = { "EMPLOYEE: Any person actively employeed by the company with a valid username.",
			"CATALOG ADMIN: A Catalog Admin controls publishing of Employee catalog items and pages.",
			"CATALOG EMPLOYEE: A Catalog Employee creates catalog items and pages.",
			 "CUSTOMER: False role to aid in validation processing." };
	enum enumUserEmployee {
		EMPLOYEE(0), CATALOG_ADMIN(1), CATALOG_EMPLOYEE(2), CUSTOMER(3);
		public int pos;
		enumUserEmployee(int pos) {
			this.pos = pos;
		}
		int pos() {
			return pos;
		}
	};

		
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// table: user
	String csUserTable = "amt_users"; // table name
	String csDBUserTable = csDatabaseName + "." + csUserTable; // if using JDBC

	String csUserTblUserId = "user_id";
	String csUserTblUsername = "username";
	String csUserTblPassword = "password";
	String csUserTblPasswordSalt = "password_salt";
	String csUserTblFirstName = "first_name";
	String csUsrTblLastName = "last_name";
	String csUserTblEmail = "email";
	String csUserTblUserTypeId = "user_type_id";
	String csUserTblUserType = "user_type";

	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	// HQL fully qualified class names
	String csHQL_ModelPackage = "com.amt.model";

	String csHQL_ModelAddress = csHQL_ModelPackage + ".Address";
	String csHQL_ModelAddressType = csHQL_ModelPackage + ".AddressType";
	String csHQL_ModelCatalogItem = csHQL_ModelPackage + ".CatalogItem";
	String csHQL_ModelCatalogItemType = csHQL_ModelPackage + ".CatalogItemType";
	String csHQL_ModelOrder = csHQL_ModelPackage + ".Order";
	String csHQL_ModelOrderStatus = csHQL_ModelPackage + ".OrderStatus";
	String csHQL_ModelOrderedItem = csHQL_ModelPackage + ".OrderedItem";
	String csHQL_ModelPhoneNumber = csHQL_ModelPackage + ".PhoneNumber";
	String csHQL_ModelPhoneNumberType = csHQL_ModelPackage + ".PhoneNumberType";	
	String csHQL_ModelClassUser = csHQL_ModelPackage + ".User";
	String csHQL_ModelClassUserType = csHQL_ModelPackage + ".UserType";
	String csHQL_ModelClassEmployeeRole = csHQL_ModelPackage + ".EmployeeRole";	
	
	
	

	// Define program messages to use in the program and for testing
	////////////////////////////////////////////////////////////////////////////////////
	String csMsgDB_ErrorAddingAddressType = "Error with database when adding Address Type.";
	String csMsgDB_ErrorAddingCatalogItemType = "Error with database when adding Catalog Item Type.";
	String csMsgDB_ErrorAddingEmployeeRole = "Error with database when adding Employee Role.";
	String csMsgDB_ErrorAddingOrderStatus = "Error with database when adding Order Status.";
	String csMsgDB_ErrorAddingPhoneNumberType = "Error with database when adding Phone Number Type.";
	String csMsgDB_ErrorAddingUserType = "Error with database when adding User Type.";
	String csMsgDB_ErrorGettingUserTypeByName = "Error with database when getting User Type by name.";
	String csMsgDB_ErrorGettingOrderStatusByName = "Error with database when getting Order Status by name.";
	String csMsgDB_ErrorGettingEmployeeRoleByName = "Error with database when getting Employee Role by name.";
	String csMsgDB_ErrorGettingAddressTypeByName = "Error with database when getting Address Type by name.";
	String csMsgDB_ErrorGettingPhoneNumberTypeByName = "Error with database when getting Phone Number Type by name.";
	String csMsgDB_ErrorGettingCatalogItemByName = "Error with database when getting Catalog Item by name.";
	String csMsgDB_ErrorAddingUser = "Database error when adding a new user.";
	String csMsgDB_ErrorAddingAddress = "Database error when adding a new address.";
	String csMsgDB_ErrorAddingPhoneNumber = "Error with database when adding Phone Number.";
	String csMsgDB_ErrorAddingCatalogItem = "Error with database when adding Catalog Item.";
	String csMsgDB_ErrorGettingUserByUsername = "Database error when getting an user by username.";
	String csMsgDB_ErrorAuthenticatingUsername = "Database error authenticating a username.";
	String csMsgDB_ErrorAddingOrder = "Error with database when adding Order.";
	String csMsgDB_ErrorAddingOrderedItem = "Error with database when adding an Ordered Item.";
	String csMsgDB_ErrorGetCurrentSession = "Error getting a current database session.";
	String csMsgDB_ErrorOpenSession = "Error opening a current database session.";
	String csMsgDB_NotFoundGettingUserByUsername = "Record not found when getting user by username.";
	String csMsgDB_ErrorGettingUserAddressList = "Error getting address list for the username.";
	String csMsgDB_ErrorGettingCatalogList = "Error getting the catalog list.";
	String csMsgDB_ErrorGettingUserList = "Error getting the user list.";
	
	////////////////////////////////////////////////////////////////////////////////////
	String csMsgBadParamUserType = "Invalid User Type parameters received.";
	String csMsgBadParamAddressType = "Invalid Address Type parameters received.";
	String csMsgBadParamCatalogItemType = "Invalid Catalog Item Type parameters received.";
	String csMsgBadParamEmployeeRole = "Invalid Employee Role parameters received.";
	String csMsgBadParamOrderStatus = "Invalid Order Status parameters received.";
	String csMsgBadParamPhoneNumberType = "Invalid Phone Number Type parameters received.";
	String csMsgBadParamCustomerBodyAsClass = "Failed to parse customer body parameters.";
	String csMsgBadParamAddUser = "One or more add User parameters are invalid.";
	String csMsgBadParamAddAddress = "One or more add Address parameters are invalid.";
	String csMsgBadParamLoginUsernamePwdBlank = "Username and password must contain values.";
	String csMsgBadParamLoginUsernamePwdFormat = "Username and/or password format is invalid.";
	String csMsgBadParamnUsernameFormat = "Username format is invalid.";
	String csMsgBadParamAddPhoneNumber = "One or more add Phone Number parameters are invalid.";
	String csMsgBadParamAddCatalogItem = "One or more add Catalog Item parameters are invalid.";
	String csMsgBadParamGetUserByUsername = "The user name provided was not alpha numeric or length was invalid.";
	String csMsgBadParamAddOrder = "One or more add Order parameters are invalid.";
	String csMsgBadParamOrderBodyAsClass = "Failed to parse order body parameters.";
	
	
	String csMsgBadParamNoPathParm = "Parmeter(s) expected. No Path Parameter(s) Received.";
	String csMsgBadParamNoBodyParm = "Parmeter(s) expected. No Body Parameter(s) Received.";
	String csMsgBadParamPathParmNotRightNumber = "Parmeter(s) expected. Not right number of Path Parameter(s) received.";
	String csMsgBadParamPathParmNotRightParam = "Parmeter(s) expected. Not right name for Path Parameter(s) received.";
	String csMsgBadParamNoQueryParm = "Parmeter(s) expected. No Query Parameter(s) Received.";
	String csMsgBadParamQueryParm = "Parmeter(s) expected. Not right number of Query Parameter(s) received.";


	String csMsgRoleNotAuthorizeCatalog = "This user's role is not authorize to create catalog items.";
	String csMsgAutenticationFailed = "Autentication failed for username and password provided.";
	String csMsgSessionUserNotActive = "There is no active login session.";
	String csMsgSessionUserLoggedOut = "The session is logged out.";
	String csMsgSessionAlreadyLoggedIn = "There is already a login session.";
	String csMsgSessionNotForThisUser = "The session does not belong to this user.";

	
}
