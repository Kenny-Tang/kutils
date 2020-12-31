package com.github.tky.kutils.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	private static ThreadLocal<Connection> holder;
	
	public static Connection getConnection(DataSourceInfo dataSource) {
		
		if(holder.get() != null) { return holder.get() ; }
        try {
        	// 注册 JDBC 驱动
			Class.forName(dataSource.getDriver());
			// 获取数据库连接
			Connection connection = DriverManager.getConnection(dataSource.getUrl(),dataSource.getUsername() ,dataSource.getPassword());
			holder.set(connection);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return holder.get() ;
	}
}
