package com.github.tky.kutils.generator.loader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.github.tky.kutils.generator.GConfiguration;
import com.github.tky.kutils.jdbc.ColumnInfo;
import com.github.tky.kutils.jdbc.ConnectionUtil;
import com.github.tky.kutils.jdbc.TableInfo;

public class TableInfoLoader extends AbstractDataLoader{
	
	private String table ;
	
	public TableInfoLoader(GConfiguration gConfiguration) {
		setConfiguration(gConfiguration) ;
	}
	
	public TableInfoLoader(GConfiguration gConfiguration, String table) {
		setConfiguration(gConfiguration) ;
		this.table = table ;
	}

	public Map<Object, Object> queryDataInfo() {
		return queryTableInfo(table) ;
	}
	
	public Map<Object, Object> queryTableInfo(String table) {
		if(table == null || "".equals(table)) {
			throw new RuntimeException("tableName not setted, please check !") ;
		}
		Connection connection = ConnectionUtil.getConnection(this.getConfiguration().getDataSourceInfo());
		ResultSet rs = null ;
		try {
			DatabaseMetaData metaData = connection.getMetaData() ;
			rs = metaData.getColumns(null, "%", table, "%") ;
			TableInfo tableInfo = new TableInfo(table) ;
			List<ColumnInfo> columns = new ArrayList<ColumnInfo>() ;
			while(rs.next()) {
			    columns.add(new ColumnInfo(rs.getString("COLUMN_NAME"))) ;
			}
			tableInfo.setColumns(columns);
			rs.close();
			Properties properties = new Properties() ;
			properties.put("table", tableInfo);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("表信息抽取异常！", e) ;
		} finally {
			try {
				if(rs != null ) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
        
		return null ;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}
	
	
}