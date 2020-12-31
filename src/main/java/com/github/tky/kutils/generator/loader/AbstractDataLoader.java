package com.github.tky.kutils.generator.loader;

import java.util.Map;
import java.util.Properties;

import com.github.tky.kutils.generator.GConfiguration;

public abstract class AbstractDataLoader implements DataLoader{
	
	private GConfiguration configuration ;
	
	public Map<Object, Object> load() {
		Properties properties = new Properties() ;
		if(configuration.getProperties() != null) {
			properties.putAll(configuration.getProperties());
		}
		Map<Object, Object> info = queryDataInfo() ;
		if(info != null) {
			properties.putAll(info);
		}
		return properties ;
	}
		
	public abstract Map<Object, Object> queryDataInfo() ;

	public GConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(GConfiguration configuration) {
		this.configuration = configuration;
	}
	
}