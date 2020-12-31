package com.github.tky.kutils.generator.builder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Properties;

import com.github.tky.kutils.generator.GConfiguration;

public class TemplateBuilderFactory{
	
	GConfiguration configuration = new GConfiguration();
	
	public TemplateBuilder build() {
		return build(TableTemplateBuilder.class) ;
	}
	
	public TemplateBuilder build(Class<? extends TemplateBuilder> builderClass) {
		
		Properties properties = new Properties() ;
		ClassLoader classLoader = this.getClass().getClassLoader() ;
		try {
			properties.load(classLoader.getResourceAsStream("application.properties"));
			System.out.println(properties);
		} catch (IOException e) {
			throw new RuntimeException("Resource application.properties not found!") ;
		}
		configuration.addProperties(properties) ;
		URL root = classLoader.getResource("ftls");
		if(root == null) {
			throw new RuntimeException("template root dir not exists or is empty! default dir name is ftls.") ;
		}
		File directoryForTemplateLoadingFile = new File(root.getFile()) ;
		configuration.setDirectoryForTemplateLoadingFile(directoryForTemplateLoadingFile);
		TemplateBuilder builder = null;
		try {
			builder = builderClass.getConstructor(GConfiguration.class).newInstance(configuration);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
		
		return builder ;
	}
	
}