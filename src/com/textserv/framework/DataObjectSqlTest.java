package com.textserv.framework;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class DataObjectSqlTest {
    private static final Logger LOG = Logger.getLogger(DataObjectSqlTest.class);

	public static void  main(String[] args) {
		BasicConfigurator.configure();
       	try {
       		DataObjectSql.initialize("textserv", "textserv", "localhost", 3306, "textserv");
       		List<DataObject> results = DataObjectSql.read("select * from outbound_campaigns");
       		if ( results != null ) {
       			for ( DataObject result : results ) {
       				LOG.info(result.getDate("scheduled_delivery_date").toString());
       			}
       		}
       	} catch (Exception ex) {
       		LOG.error(ex);
       	}

	 }       
}
