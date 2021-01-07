package com.github.tky.kutils.type;

import java.util.ArrayList;
import java.util.List;

import com.github.tky.kutils.Strings;

public class TableInfo {
	
	
	private String tableName ;
	private List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
	private TypeSet imports = new TypeSet();
	
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
	
	public void addImport(String importType) {
		this.imports.add(importType) ;
	}
	
	public void addColumnInfo(ColumnInfo columnInfo) {
		this.columns.add(columnInfo) ;
	}

	public TypeSet getImports() {
		return imports;
	}

	public void setImports(TypeSet imports) {
		this.imports = imports;
	}

	public String getTableName() {
		return tableName;
	}
	
	public String getUpperCamelTable () {
		return Strings.lowerUnderscoreToUpperCamel(tableName) ;
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

	@Override
	public String toString() {
		return "TableInfo [tableName=" + tableName + ", columns=" + columns + "]";
	}
	
}
