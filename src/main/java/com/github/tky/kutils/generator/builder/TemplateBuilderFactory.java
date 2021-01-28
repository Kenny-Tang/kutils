package com.github.tky.kutils.generator.builder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tky.kutils.generator.GConfiguration;
import com.github.tky.kutils.jdbc.DataSourceInfo;
import com.github.tky.kutils.type.JdbcType;
import com.github.tky.kutils.type.TypeHandler;

public class TemplateBuilderFactory {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	GConfiguration configuration = new GConfiguration();

	public TemplateBuilder build() {
		return build(TableTemplateBuilder.class);
	}

	public TemplateBuilder build(Class<? extends TemplateBuilder> builderClass) {

		Properties properties = new Properties();
		ClassLoader classLoader = this.getClass().getClassLoader();
		try {
			properties.load(classLoader.getResourceAsStream("application.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Resource application.properties not found!");
		}
		configuration.addProperties(properties);

		setDirectoryForTemplateLoadingFile();
		setTypeHandler();
		setPackageSub();
		setOutputDir();
		setDataSourceInfo(properties);

		TemplateBuilder builder = null;
		try {
			builder = builderClass.getConstructor(GConfiguration.class).newInstance(configuration);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}

		return builder;
	}

	private void setDataSourceInfo(Properties properties) {
		configuration.setDataSourceInfo(new DataSourceInfo(properties.getProperty("k.generator.jdbc.driver"), properties.getProperty("k.generator.jdbc.url"),
				properties.getProperty("k.generator.jdbc.username"), properties.getProperty("k.generator.jdbc.password")));
	}

	private void setDirectoryForTemplateLoadingFile() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL root = classLoader.getResource(configuration.getTemplatesRoot());
		if (root == null) {
			logger.warn("template root dir not exists ! default dir name is ftls.");
		} else {
			File directoryForTemplateLoadingFile = new File(root.getFile());
			configuration.setDirectoryForTemplateLoadingFile(directoryForTemplateLoadingFile);
		}
	}

	private void setOutputDir() {
		Properties properties = configuration.getProperties();
		String outputDir = properties.getProperty("k.generator.output.dir", "src/main/java/");
		configuration.setOutputDir(outputDir);
	}

	private void setPackageSub() {
		Properties properties = configuration.getProperties();
		String packageSub = properties.getProperty("k.generator.package.sub", "false");
		boolean sub = Boolean.parseBoolean(packageSub);
		configuration.setPackageSub(sub);
	}

	private void setTypeHandler() {
		Properties properties = configuration.getProperties();
		Set<Object> keys = properties.keySet();
		for (Object key : keys) {
			if (key instanceof String) {
				try {
					String keyString = ((String) key);
					String keyPrefix = keyString.substring(0, keyString.lastIndexOf('.'));

					if ("k.generator.jdbc.typehandler".equals(keyPrefix)) {
						JdbcType jdbcType = JdbcType.valueOf(keyString.substring(keyString.lastIndexOf('.') + 1).toUpperCase());
						String clazz = properties.getProperty(keyString);
						TypeHandler typeHandler = (TypeHandler) Class.forName(clazz).newInstance();
						configuration.registerTypeHandler(jdbcType, typeHandler);
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("jdbcType parse exception" + key, e);
				}
			}
		}
	}

}