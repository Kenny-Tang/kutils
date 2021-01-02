package com.github.tky.kutils.generator.builder;

import com.github.tky.kutils.Strings;
import com.github.tky.kutils.generator.GConfiguration;
import com.github.tky.kutils.generator.loader.TableInfoLoader;

public class TableTemplateBuilder extends AbstractTemplateBuilder{
	
	private TableInfoLoader tableInfoLoader ;
	
	public TableTemplateBuilder(GConfiguration configuration) {
		super(configuration);
		this.dataLoader = new TableInfoLoader(configuration); 
		this.tableInfoLoader = (TableInfoLoader) this.dataLoader ; 
	}
	
	@Override
	public String getOutputFilename() {
		return Strings.lowerUnderscoreToUpperCamel(tableInfoLoader.getTable());
	}

	public TableTemplateBuilder(GConfiguration configuration, String tableName) throws Exception {
		super(configuration);
		this.dataLoader = new TableInfoLoader(configuration, tableName);
	}

	public TableInfoLoader getTableInfoLoader() {
		return tableInfoLoader;
	}

	public void setTableInfoLoader(TableInfoLoader tableInfoLoader) {
		this.tableInfoLoader = tableInfoLoader;
	}

	
}