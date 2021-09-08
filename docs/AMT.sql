-- amt_online_sys
/*
Hibernate: drop table if exists amt_address
Hibernate: drop table if exists amt_address_type

Hibernate: drop table if exists amt_catalog_item
Hibernate: drop table if exists amt_catalog_item_type

Hibernate: drop table if exists amt_employee_roles

Hibernate: drop table if exists amt_order
Hibernate: drop table if exists amt_order_items
Hibernate: drop table if exists amt_order_status

Hibernate: drop table if exists amt_phone_number_type
Hibernate: drop table if exists amt_phone_numbers

Hibernate: drop table if exists amt_user_type
Hibernate: drop table if exists amt_users
 */
--
DROP TABLE IF EXISTS amt_address;
DROP TABLE IF EXISTS amt_address_type;
--
DROP TABLE IF EXISTS amt_order;
DROP TABLE IF EXISTS amt_order_items;
DROP TABLE IF EXISTS amt_order_status;
--
DROP TABLE IF EXISTS amt_phone_numbers;
DROP TABLE IF EXISTS amt_phone_number_type;
--
DROP TABLE IF EXISTS amt_catalog_item;
DROP TABLE IF EXISTS amt_catalog_item_type;
--
--
DROP TABLE IF EXISTS amt_users;
DROP TABLE IF EXISTS amt_user_type;
DROP TABLE IF EXISTS amt_employee_roles;
--
--
--
SELECT * FROM amt_address_type;
SELECT * FROM amt_catalog_item_type;
SELECT * FROM amt_employee_roles;
SELECT * FROM amt_order_status;
SELECT * FROM amt_phone_number_type;
SELECT * FROM amt_user_type;

SELECT * FROM amt_users;




