package com.textserv.framework;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class DaoSupport extends SimpleJdbcDaoSupport {
	private BasicDataSource dataSource = null;
	private static final Logger LOG = Logger.getLogger(DaoSupport.class);

	public DaoSupport( String username, String password, String host, int port, String instance) {
		String url = "jdbc:mysql://"+host+":"+port+"/"+instance+"?autoReconnect=true&amp;zeroDateTimeBehavior=convertToNull";
		dataSource = new BasicDataSource ();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		dataSource.setMaxIdle(9);
		dataSource.setMaxActive(30);
		dataSource.setMaxWait(28800);
		dataSource.setValidationQuery("SELECT 1");
		
		dataSource.setTestOnBorrow(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(20000);
		dataSource.setMinEvictableIdleTimeMillis(20000);
		dataSource.setRemoveAbandoned(true);
		dataSource.setRemoveAbandonedTimeout(10);
		dataSource.setLogAbandoned(true);
		setDataSource(dataSource);
	}
}
