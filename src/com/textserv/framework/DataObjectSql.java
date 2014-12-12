package com.textserv.framework;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

public class DataObjectSql {
	private static DaoSupport daoSupport = null;
	
	public static void initialize(String username, String password, String host, int port, String instance) {
		DataObjectSql.daoSupport = new DaoSupport(username, password, host, port, instance);
	}
	
	public static List<DataObject> read( String query ) throws Exception {
	     org.springframework.jdbc.support.rowset.SqlRowSet rs = daoSupport.getJdbcTemplate().queryForRowSet( query );
	     return fromResultSet(rs);
	}

	public static List<DataObject> read( String query, Object[] params ) throws Exception {
	     org.springframework.jdbc.support.rowset.SqlRowSet rs = daoSupport.getJdbcTemplate().queryForRowSet( query, params );
	     return fromResultSet(rs);
	}
	
	public static List<DataObject> fromResultSet(SqlRowSet rs) throws DataObjectException {
		List<DataObject> lstDataObject = new ArrayList<DataObject>();
		try {
			while(rs.next()) {
				DataObject returnDO = new DataObject();
				SqlRowSetMetaData rsMetaData = rs.getMetaData();
				int numberOfColumns = rsMetaData.getColumnCount();
				for (int i = 1; i <= numberOfColumns; i++) {
			      String columnName = rsMetaData.getColumnName(i);
			      String columnType = (rsMetaData.getColumnTypeName(i)).toUpperCase();
			      if (columnType.startsWith("BIGINT") || 
			          columnType.startsWith("SMALLINT") ||
			          columnType.startsWith("INTEGER") ||
			          columnType.startsWith("INT")
			         )
			      {
			    	  returnDO.setInt(columnName, rs.getInt(i));
			   	  }else if (columnType.startsWith("VARCHAR") || 
					         columnType.startsWith("CHAR") || columnType.startsWith("LONGTEXT")){
			   		  
			   		  returnDO.setString(columnName, rs.getString(i)); 
			   	  }else if (columnType.startsWith("DATE") || 
			   			    columnType.startsWith("TIME") || 
			   			    columnType.startsWith("TIMESTAMP")  ) {
			   		  returnDO.setDate(columnName, rs.getTimestamp(i)); 
			   	  }else if ( columnType.startsWith("TINYINT") )
			   	  {
			   		  returnDO.setBoolean(columnName, rs.getBoolean(i));
			   	  }else if ( columnType.startsWith("DECIMAL") )
			   	  {
			   		  returnDO.setDouble(columnName, rs.getDouble(i));
			   	  }
				}
				lstDataObject.add(returnDO);
			}
		}catch( Exception e) {
        	throw new DataObjectException(e);
        }
		return lstDataObject;
	}
}
