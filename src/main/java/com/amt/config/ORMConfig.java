package com.amt.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.amt.app.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration
@EnableTransactionManagement
@PropertySource("classpath:springorm.properties")
public class ORMConfig implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(ORMConfig.class);

	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		String sMethod = csCRT + "dataSource(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
		
		dataSource.setUrl(csDatabaseConnectionURL);
		dataSource.setUsername(csDatabaseConnectionUsername);
		dataSource.setPassword(csDatabaseConnectionPwd);

/*		
		dataSource.setUrl(env.getProperty("db_url"));
		dataSource.setUsername(env.getProperty("db_username"));
		dataSource.setPassword(env.getProperty("db_password"));
	
*/	
		return dataSource;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		String sMethod = csCRT + "sessionFactory(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		sessionFactory.setDataSource(dataSource()); // setter injection
		sessionFactory.setPackagesToScan(csPackagesToScan);
		
		// Create the properties object
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hbm2ddl")); // create and validate
		hibernateProperties.setProperty("hibernate.dialect", env.getProperty("dialect"));
		
		// Configure hibernate properties
		sessionFactory.setHibernateProperties(hibernateProperties);
		
		return sessionFactory;
	}
	
	@Bean
	public PlatformTransactionManager hibernateTransactionManager() {
		String sMethod = csCRT + "hibernateTransactionManager(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		
		transactionManager.setSessionFactory(sessionFactory().getObject());
		
		return transactionManager;
	}

}
