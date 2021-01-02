package com.github.tky.kutils.type;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.tky.kutils.Strings;

public class ColumnInfo {

	private String columnName ;
	private String typeName ;
	private int dataType ;
	private String javaTypeSimpleName ;
	private String javaTypeFullName ;
	private JdbcType jdbcType ;
	public ColumnInfo(ResultSet rs) {
		try {
			columnName = rs.getString("COLUMN_NAME") ;
			typeName = rs.getString("TYPE_NAME") ;
			dataType = rs.getInt("DATA_TYPE");
			this.jdbcType = JdbcType.forCode(dataType) ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ColumnInfo(String columnName) {
		super();
		this.columnName = columnName;
	}

	public ColumnInfo(String columnName, String typeName) {
		super();
		this.typeName = typeName ;
		this.columnName = columnName;
	}

	
	public JdbcType getJdbcType() {
		return jdbcType;
	}
	public void setJdbcType(JdbcType jdbcType) {
		this.jdbcType = jdbcType;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	
	public String getJavaTypeSimpleName() {
		return javaTypeSimpleName;
	}
	public void setJavaTypeSimpleName(String javaTypeSimpleName) {
		this.javaTypeSimpleName = javaTypeSimpleName;
	}
	public String getJavaTypeFullName() {
		return javaTypeFullName;
	}
	public void setJavaTypeFullName(String javaTypeFullName) {
		this.javaTypeFullName = javaTypeFullName;
	}
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getColumnName() {
		return columnName;
	}
	
	public String getUpperCamelColumn() {
		return Strings.lowerUnderscoreToUpperCamel(columnName) ;
	}
	
	public String getLowerCamelColumn() {
		return Strings.lowerUnderscoreToLowerCamel(columnName) ;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	@Override
	public String toString() {
		return "ColumnInfo [columnName=" + columnName + ", typeName=" + typeName + ", dataType=" + dataType
				+ ", javaTypeSimpleName=" + javaTypeSimpleName + ", javaTypeFullName=" + javaTypeFullName
				+ ", jdbcType=" + jdbcType + "]";
	}
	
	
}
