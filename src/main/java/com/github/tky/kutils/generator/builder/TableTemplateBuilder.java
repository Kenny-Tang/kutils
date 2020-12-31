package com.github.tky.kutils.generator.builder;

import com.github.tky.kutils.generator.GConfiguration;
import com.github.tky.kutils.generator.loader.TableInfoLoader;

public class TableTemplateBuilder extends AbstractTemplateBuilder{
	
	private String tableName ;
	public TableTemplateBuilder(GConfiguration configuration) {
		super(configuration);
		this.dataLoader = new TableInfoLoader(configuration); 
	}
	
	public TableTemplateBuilder(GConfiguration configuration, String tableName) throws Exception {
		super(configuration);
		this.dataLoader = new TableInfoLoader(configuration, tableName);
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
		((TableInfoLoader)getDataLoader()).setTable(tableName);
	}
	
}