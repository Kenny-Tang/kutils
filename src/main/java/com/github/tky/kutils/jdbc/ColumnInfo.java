package com.github.tky.kutils.jdbc;

public class ColumnInfo {

	private String columnName ;

	public ColumnInfo(String columnName) {
		super();
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
}
