package com.github.tky.kutils.generator.builder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

import com.github.tky.kutils.generator.GConfiguration;
import com.github.tky.kutils.type.JdbcType;
import com.github.tky.kutils.type.TypeHandler;

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
		} catch (IOException e) {
			throw new RuntimeException("Resource application.properties not found!") ;
		}
		configuration.addProperties(properties) ;
		URL root = classLoader.getResource(configuration.getTemplatesRoot());
		if(root == null) {
			throw new RuntimeException("template root dir not exists or is empty! default dir name is ftls.") ;
		}
		File directoryForTemplateLoadingFile = new File(root.getFile()) ;
		configuration.setDirectoryForTemplateLoadingFile(directoryForTemplateLoadingFile);
		setTypeHandler();
		TemplateBuilder builder = null;
		try {
			builder = builderClass.getConstructor(GConfiguration.class).newInstance(configuration);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
		
		return builder ;
	}

	private void setTypeHandler() {
		Properties properties = configuration.getProperties() ;
		Set<Object> keys = properties.keySet() ;
		for (Object key : keys) {
			if(key instanceof String) {
				try {
					String keyString = ((String) key);
					String keyPrefix = keyString.substring(0, keyString.lastIndexOf('.'));
					
					if ("k.generator.jdbc.typehandler".equals(keyPrefix)) {
						JdbcType jdbcType = JdbcType.valueOf(keyString.substring(keyString.lastIndexOf('.')+1).toUpperCase());
						String clazz = properties.getProperty(keyString);
						TypeHandler typeHandler = (TypeHandler) Class.forName(clazz).newInstance();
						configuration.registerTypeHandler(jdbcType, typeHandler);
					} 
				} catch (Exception e) {
					e.printStackTrace(); 
					throw new RuntimeException("jdbcType parse exception"+key, e) ;
				}
			}
		}
	}
	
}