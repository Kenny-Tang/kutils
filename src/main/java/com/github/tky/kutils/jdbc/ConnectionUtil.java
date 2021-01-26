package com.github.tky.kutils.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	private static ThreadLocal<Connection> holder = new ThreadLocal<Connection>();
	
	public static Connection getConnection(DataSourceInfo dataSource) {
		
		if(holder.get() != null) { return holder.get() ; }
        try {
			Class.forName(dataSource.getDriver());
			Connection connection = DriverManager.getConnection(dataSource.getUrl(),dataSource.getUsername() ,dataSource.getPassword());
			holder.set(connection);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new RuntimeException("database connect failed.", e) ;
		}
		return holder.get() ;
	}
}
