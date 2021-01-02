package com.github.tky.kuitls.generator;

import java.sql.Connection;

import org.junit.Test;

import com.github.tky.kutils.jdbc.ConnectionUtil;
import com.github.tky.kutils.jdbc.DataSourceInfo;
import com.test.BaseTest;

public class ConnectionTest extends BaseTest {

	@Test
	public void testMysql8() {
		DataSourceInfo dataSource = new DataSourceInfo() ;
		dataSource.setDriver("com.mysql.cj.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("12345Qwert");
		dataSource.setUrl("jdbc:mysql://localhost:3306/kenny?useSSL=false&serverTimezone=UTC");
		Connection connection = ConnectionUtil.getConnection(dataSource) ;
		System.out.println(connection);
		assertNotNull(connection);
	}
	
}
