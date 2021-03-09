package com.github.tky.kutils.type;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.tky.kutils.Strings;
import com.github.tky.kutils.jdbc.ResultSetColumnKeys;

/**
 * 
 * @author Kenny
 *
 */
public class ColumnInfo {

	private String columnName;
	private String typeName;
	private int dataType;
	private String javaTypeSimpleName;
	private String javaTypeFullName;
	private JdbcType jdbcType;
	private String remarks;
	private int columnSize;
	private Integer decimalDigits;
	private String nullable;

	public ColumnInfo() {
		super();
	}

	public ColumnInfo(ResultSet rs) {
		try {
			columnName = rs.getString(ResultSetColumnKeys.COLUMN_NAME.name());
			typeName = rs.getString(ResultSetColumnKeys.TYPE_NAME.name());
			dataType = rs.getInt(ResultSetColumnKeys.DATA_TYPE.name());
			remarks = rs.getString(ResultSetColumnKeys.REMARKS.name());
			columnSize = rs.getInt(ResultSetColumnKeys.COLUMN_SIZE.name());
			decimalDigits = rs.getInt(ResultSetColumnKeys.DECIMAL_DIGITS.name());
			nullable = rs.getString(ResultSetColumnKeys.IS_NULLABLE.name());
			this.jdbcType = JdbcType.forCode(dataType);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "ColumnInfo [columnName=" + columnName + ", typeName=" + typeName + ", dataType=" + dataType + ", javaTypeSimpleName=" + javaTypeSimpleName + ", javaTypeFullName=" + javaTypeFullName + ", jdbcType=" + jdbcType + ", remarks=" + remarks + ", columnSize=" + columnSize
				+ ", decimalDigits=" + decimalDigits + ", nullable=" + nullable + "]";
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public Integer getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(Integer decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public ColumnInfo(String columnName) {
		super();
		this.columnName = columnName;
	}

	public ColumnInfo(String columnName, String typeName) {
		super();
		this.typeName = typeName;
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
		return Strings.lowerUnderscoreToUpperCamel(columnName);
	}

	public String getLowerCamelColumn() {
		return Strings.lowerUnderscoreToLowerCamel(columnName);
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

}
