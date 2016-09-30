package com.idividends.vault.config;


import java.net.URI;
import java.net.URISyntaxException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Database configuration class. It will be dependant of the environment
 * variable DATABASE_URL, which contain all the information regarding the
 * connection to the Database: user/password/url/protocol
 *
 */
@Configuration
@Profile("default")
public class DatabaseConfig {

	private final static Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

	private final static int USER_PASSWORD_PARAMS = 2;

	@Bean
	public DataSource postgresDataSource() {
		// Changed to a shared DB in projec ad-crm-salesforce
		String databaseUrl = System.getenv("DATABASE_URL");
		URI dbUri;
		try {
			dbUri = new URI(databaseUrl);
		} catch (URISyntaxException e) {
			logger.error("databaseUrl cannot be null");
			return null;
		}

		// For travis, as DB has no password.
		String username = dbUri.getUserInfo().split(":")[0];
		String password = null;
		if (USER_PASSWORD_PARAMS == dbUri.getUserInfo().split(":").length) {
			password = dbUri.getUserInfo().split(":")[1];
		}

		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

		logger.debug("Data base properties --> dbUrl: " + dbUrl + " username: " + username);
		logger.debug("SPRING_JPA_HIBERNATE_DDL_AUTO: " + System.getenv("SPRING_JPA_HIBERNATE_DDL_AUTO"));
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		String driverClassName = System.getenv("DRIVER_CLASS_NAME") != null ? System.getenv("DRIVER_CLASS_NAME")
				: "org.postgresql.Driver";
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(username);
		if (password != null && !password.isEmpty()) {
			dataSource.setPassword(password);
		}
		dataSource.setTestOnBorrow(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnReturn(true);
		dataSource.setValidationQuery("SELECT 1");
		return dataSource;
	}

}