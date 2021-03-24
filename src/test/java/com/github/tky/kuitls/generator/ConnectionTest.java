package com.github.tky.kuitls.generator;

import java.sql.Connection;

import org.junit.Test;

import com.github.tky.kutils.jdbc.ConnectionUtil;
import com.github.tky.kutils.jdbc.DataSourceInfo;
import com.test.BaseTest;

public class ConnectionTest extends BaseTest {

	@Test
	public void tests() {
		try {
			throw new RuntimeException("sss");
		} catch (Exception e) {
			e.printStackTrace();
			String err = "";
			StackTraceElement[] trace = e.getStackTrace();
			for (int i = 0; i < trace.length; i++) {
				if (i > 10) {
					break;
				}
				StackTraceElement stackTraceElement = trace[i];
				err += stackTraceElement.toString() + "\n ";
			}
			System.out.println(err);
		}
	}

	@Test
	public void testMysql8() {
		DataSourceInfo dataSource = new DataSourceInfo();
		dataSource.setDriver("com.mysql.cj.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("12345Qwert");
		dataSource.setUrl("jdbc:mysql://localhost:3306/kenny?useSSL=false&serverTimezone=UTC");
		Connection connection = ConnectionUtil.getConnection(dataSource);
		System.out.println(connection);
		assertNotNull(connection);
	}

}
