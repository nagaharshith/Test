package com.hcl.jira.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public final class DataSourceManager {
	
	private static final Log log = LogFactory.getLog(DataSourceManager.class);	
	private static DataSourceManager dsm = null;
	private static ComboPooledDataSource dataSource = null;
	//private static JdbcDataSource dataSource = null;
	
	private DataSourceManager(){
		setUpDataSource();
		//printLogs();
	}
	
	public static DataSourceManager getDSM(){
		if(dsm == null){
			dsm = new DataSourceManager();
		}
		return dsm;
	}
	
	private void setUpDataSource(){
		try{
			dataSource = new ComboPooledDataSource();
			/*Class.forName("com.mysql.jdbc.Driver");
			dataSource.setUser("root");
			dataSource.setPassword("naveen");
			dataSource.setDriverClass("com.mysql.jdbc.Driver");
			dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/ptmt?autoReconnect=true");*/
			/*dataSource = new JdbcDataSource();
			dataSource.setURL("jdbc:h2:E:/Java/workspace/PTMT_REF/Ptmt_Embedded_Database/Ptmt_EDB");
			dataSource.setUser("ptmt");
			dataSource.setPassword("ptmt");*/			
			log.info("Successfull : Datasource Initialsed Successfully");
		}catch(Exception e){
			log.error("Problem : Setting Up DataSource : Problem", e);
		}
	}
	
	public Connection conFromPool(){
		Connection con = null;
		try {
			con = dataSource.getConnection();
			log.info("Successfull : Getting A Connection From Datasource Pool : Successfull");
		} catch (SQLException e) {
			log.error("Problem : Getting A Connection From Datasource Pool. | countRetry :  | Exception : "+e);
		}
		return con;
	}
	
	public void conToPool(Connection con) throws SQLException{		
		try{
			con.close();
			log.info("Successfull : Closing Connection : Successfull");
		}catch(SQLException e){
			log.error("Problem : Closing Connection : Problem");
		}
	}
	
	public void statToPool(Statement ps) throws SQLException{
		try{
			ps.close();
			log.info("Successfull : Closing Statement : Successfull");
		}catch(SQLException e){			
			log.error("Problem : Closing Statement : Problem");			
		}
	}
	
	public void release(ResultSet resultSet,Statement prepStmt,Connection conn) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (Exception sqlE) {
				resultSet = null;
			}
		}
		if (prepStmt != null) {
			try {
				statToPool(prepStmt);
			} catch (Exception sqlE) {
				prepStmt = null;
			}
		}
		if (conn != null) {
			try {
				conToPool(conn);
			} catch (Exception sqlE) {
				conn = null;
			}
		}
		log.info("Prepared stmt and Connections released successfully !");
	}
	
	/*private void printLogs() {
		log.info("");
		log.info("Database Credentials :- ");
		log.info("********************************************************************");
		log.info("User : "+dataSource.getUser());
		log.info("Password : "+dataSource.getPassword());			
		log.info("Jdbc Url : "+dataSource.getJdbcUrl());
		log.info("Driver Class : "+dataSource.getDriverClass());
		log.info("********************************************************************");
		log.info("");
		log.info("DataSource Properties For Connection And Statement Pooling :- ");
		log.info("--------------------------------------------------------------------");
		dataSource.setDataSourceName("||CQ--C3P0--POOL||");
		log.info("DataSource Name : "+dataSource.getDataSourceName());
		log.info("Initial Pool Size : "+dataSource.getInitialPoolSize());
		log.info("Minimum Pool Size : "+ dataSource.getMinPoolSize());
		log.info("Maximum Pool Size : "+ dataSource.getMaxPoolSize());
		log.info("Maximum Statements Per Connection : "+dataSource.getMaxStatementsPerConnection());
		log.info("Acquire Increment : "+dataSource.getAcquireIncrement());
		log.info("Maximum Idle Time (Secs) : "+dataSource.getMaxIdleTime());
		log.info("Maximum Idle Time For Excess Connections (Secs) : "+dataSource.getMaxIdleTimeExcessConnections());
		log.info("Check OutTimeout (MilliSecs) : "+dataSource.getCheckoutTimeout());
		log.info("Idle Connection Test Period (Secs) : "+dataSource.getIdleConnectionTestPeriod());
		log.info("Unreturned Connection Timeout (Secs) : "+dataSource.getUnreturnedConnectionTimeout());			
		log.info("--------------------------------------------------------------------");
		log.info("");
	}*/
	
	public static void main(String... as){
		//DataSourceManager ds = new DataSourceManager();
		System.out.println(DataSourceManager.getDSM().conFromPool());
	}
	
}