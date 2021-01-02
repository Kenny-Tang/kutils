package com.github.tky.kuitls.generator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.github.tky.kutils.jdbc.ConnectionUtil;
import com.github.tky.kutils.jdbc.DataSourceInfo;
import com.github.tky.kutils.type.ColumnInfo;
import com.github.tky.kutils.type.TableInfo;

public class TableInfoLoaderTest {

	private Connection connection ;
	@Before
	public void before() {
		DataSourceInfo dataSource = new DataSourceInfo() ;
		dataSource.setDriver("com.mysql.cj.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("12345Qwert");
		dataSource.setUrl("jdbc:mysql://localhost:3306/kenny?useSSL=false&serverTimezone=UTC");
		connection = ConnectionUtil.getConnection(dataSource) ;
	}
	
	@Test
	public void testQueryDataInfo() {
		String table = "user" ;
		ResultSet rs = null ;
		TableInfo tableInfo = new TableInfo(table) ;
		try {
			DatabaseMetaData metaData = connection.getMetaData() ;
			rs = metaData.getColumns(connection.getCatalog(), "%", table, "%") ;
			List<ColumnInfo> columns = new ArrayList<ColumnInfo>() ;
			while(rs.next()) {
				System.out.println(rs);
			}
			tableInfo.setColumns(columns);
			rs.close();
			Properties properties = new Properties() ;
			properties.put("table", tableInfo);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("表信息抽取异常！", e) ;
		}
        System.out.println(tableInfo);
	
	}
}
