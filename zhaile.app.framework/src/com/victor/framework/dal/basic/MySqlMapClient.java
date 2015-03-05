package com.victor.framework.dal.basic;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class MySqlMapClient{
	private static SqlMapClient sqlMapClient;
	
	public void setDataSource(String dataSource) {
		try {
	      Reader reader = Resources.getResourceAsReader(dataSource);
	      sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
	      reader.close(); 
	    } catch (IOException e) {
	      // Fail fast.
	      throw new RuntimeException("Something bad happened while building the SqlMapClient instance." + e, e);
	    }
	}

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}
	
	public void startTransaction() throws SQLException{
		sqlMapClient.startTransaction();
	}
	
	public void commitTransaction() throws SQLException{
		sqlMapClient.commitTransaction();
	}
	
	public void endTransaction() throws SQLException{
		sqlMapClient.endTransaction();
	}
}
