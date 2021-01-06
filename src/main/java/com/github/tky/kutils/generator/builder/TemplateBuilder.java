package com.github.tky.kutils.generator.builder;

import java.io.File;
import java.util.Map;

import com.github.tky.kutils.generator.loader.DataLoader;

public interface TemplateBuilder{
	
	void generate();
	
	public DataLoader getDataLoader() ;
	
	public void setDataLoader(DataLoader dataLoader);

	void generate(File file, Map<Object, Object> dataModel);
}