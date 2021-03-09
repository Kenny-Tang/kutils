package com.github.tky.kutils.generator.loader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tky.kutils.generator.GConfiguration;
import com.github.tky.kutils.jdbc.ConnectionUtil;
import com.github.tky.kutils.type.ColumnInfo;
import com.github.tky.kutils.type.TableInfo;
import com.github.tky.kutils.type.TypeHandler;

public class TableInfoLoader extends AbstractDataLoader {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private String table;

	public TableInfoLoader() {
		super();
	}

	public TableInfoLoader(GConfiguration gConfiguration) {
		setConfiguration(gConfiguration);
	}

	public TableInfoLoader(GConfiguration gConfiguration, String table) {
		setConfiguration(gConfiguration);
		this.table = table;
	}

	public Map<Object, Object> queryDataInfo() {
		return queryTableInfo(table);
	}

	public Map<Object, Object> queryTableInfo(String table) {
		if (table == null || "".equals(table)) {
			throw new RuntimeException("tableName not setted, please check !");
		}
		Connection connection = ConnectionUtil.getConnection(this.getConfiguration().getDataSourceInfo());
		ResultSet rs = null;
		try {
			DatabaseMetaData metaData = connection.getMetaData();

			String tableRemark = "";
			ResultSet tableResultSet = metaData.getTables(null, null, table, new String[] { "TABLE" });
			while (tableResultSet.next()) {
				tableRemark = tableResultSet.getString("REMARKS");
			}
			rs = metaData.getColumns(connection.getCatalog(), "%", table, "%");
			TableInfo tableInfo = new TableInfo(table, tableRemark);

			while (rs.next()) {
				ColumnInfo columnInfo = new ColumnInfo(rs);
				TypeHandler typeHandler = this.getConfiguration().getTypeHandlerRegistry().getTypeHandler(columnInfo.getJdbcType());
				columnInfo.setJavaTypeSimpleName(typeHandler.getTypeSimpleName(rs));
				columnInfo.setJavaTypeFullName(typeHandler.getTypeFullName(rs));
				tableInfo.addColumnInfo(columnInfo);
				tableInfo.addImport(typeHandler.getTypeFullName(rs));
			}
			rs.close();
			logger.info("表信息：{}", tableInfo);
			if (tableInfo.getColumns().size() == 0) {
				logger.error("table {} does not exists !", table);
			}
			Properties properties = new Properties();
			properties.put("table", tableInfo);
			return properties;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("table info fetch exception！", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getTable() {
		return table;
	}

}