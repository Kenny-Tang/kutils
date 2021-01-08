package com.github.tky.kutils.generator.loader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import com.github.tky.kutils.generator.GConfiguration;
import com.github.tky.kutils.jdbc.ConnectionUtil;
import com.github.tky.kutils.type.ColumnInfo;
import com.github.tky.kutils.type.TableInfo;
import com.github.tky.kutils.type.TypeHandler;

public class TableInfoLoader extends AbstractDataLoader{
	
	private String table ;
	private String alias ;
	public TableInfoLoader() {
		super();
	}

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
			rs = metaData.getColumns(connection.getCatalog(), "%", table, "%") ;
			TableInfo tableInfo = new TableInfo(table, alias) ;
			while(rs.next()) {
				ColumnInfo columnInfo = new ColumnInfo(rs) ;
				TypeHandler typeHandler = this.getConfiguration().getTypeHandlerRegistry().getTypeHandler(columnInfo.getJdbcType());
				columnInfo.setJavaTypeSimpleName(typeHandler.getTypeSimpleName(rs));
				columnInfo.setJavaTypeFullName(typeHandler.getTypeFullName(rs));
			    tableInfo.addColumnInfo(columnInfo) ;
			    tableInfo.addImport(typeHandler.getTypeFullName(rs));
			}
			rs.close();
			System.out.println(tableInfo);
			Properties properties = new Properties() ;
			properties.put("table", tableInfo);
			return properties ;
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
	}
	
	public void setTable(String table) {
		this.table = table;
	}

	public void setTable(String table, String alias) {
		this.table = table;
		this.alias = alias ;
	}

    public String getAlias() {
      return alias;
    }
  
    public void setAlias(String alias) {
      this.alias = alias;
    }
  
    public String getTable() {
      return table;
    }
	
}