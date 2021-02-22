package com.github.tky.kutils.generator.builder;

import java.io.File;
import java.util.Map;

import com.github.tky.kutils.generator.GConfiguration;
import com.github.tky.kutils.generator.loader.DataLoader;
import com.github.tky.kutils.generator.loader.SourceFileLoader;

public interface TemplateBuilder {

	void generate();

	public DataLoader getDataLoader();

	public void setDataLoader(DataLoader dataLoader);

	public SourceFileLoader getSourceFileLoader();

	public void setSourceFileLoader(SourceFileLoader SourceFileLoader);

	void generate(File file, Map<Object, Object> dataModel);
	
	GConfiguration getConfiguration();
}