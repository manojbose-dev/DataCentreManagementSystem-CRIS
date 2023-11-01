package com.metro.dcms.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class connectHCP_ASA {
	
	private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    
    static {
        
        try {
			
			  ResourceBundle rb = null; 
			  rb = ResourceBundle.getBundle("application");
			  config.setJdbcUrl(rb.getString("SybaseASAjdbcUrl"));
			  config.setUsername(rb.getString("SybaseASAUsername"));
			  config.setPassword(rb.getString("SybaseASAPassword"));
			  
			  config.addDataSourceProperty("cachePrepStmts", "true");
		      config.addDataSourceProperty("prepStmtCacheSize", "250");
		      config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            }catch(Exception ex)
            {
            	System.out.println("Exception in getting Database Connection.. "+ex.getMessage());
            }
    

		        ds = new HikariDataSource(config);
    }
    
      
    
    
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    private connectHCP_ASA(){}


}
