package com.github.tky.kutils.jdbc;

import java.util.List;

public class TableInfo {
	
	
	public TableInfo() {
		super();
	}
	
	public TableInfo(String tableName, List<ColumnInfo> columns) {
		super();
		this.tableName = tableName;
		this.columns = columns;
	}
	public TableInfo(String tableName) {
		super();
		this.tableName = tableName;
	}
	private String tableName ;
	private List<ColumnInfo> columns;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<ColumnInfo> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnInfo> columns) {
		this.columns = columns;
	}
	
	
}
