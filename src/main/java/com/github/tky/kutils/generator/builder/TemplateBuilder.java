package com.github.tky.kutils.generator.builder;

import java.io.File;

import com.github.tky.kutils.generator.loader.DataLoader;

public interface TemplateBuilder{
	
	void generate();
	
	void generate(File file);
	
	public DataLoader getDataLoader() ;
	
	public void setDataLoader(DataLoader dataLoader);
}