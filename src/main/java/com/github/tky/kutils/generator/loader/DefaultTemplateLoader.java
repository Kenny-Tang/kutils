package com.github.tky.kutils.generator.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.tky.kutils.generator.GConfiguration;

public class DefaultTemplateLoader implements SourceFileLoader {

	private GConfiguration configuration;

	public DefaultTemplateLoader(GConfiguration gConfiguration) {
		setConfiguration(gConfiguration);
	}

	public DefaultTemplateLoader(GConfiguration gConfiguration, String table) {
		setConfiguration(gConfiguration);
	}

	public List<File> load() {
		List<File> templates = load(configuration.getDirectoryForTemplateLoadingFile());
		templates.addAll(loadDefaultSourceFiles());
		return templates;
	}

	public List<File> load(File file) {

		List<File> templates = new ArrayList<>();
		if (file == null) {
			return templates;
		}
		if (file.isDirectory()) {
			File[] children = file.listFiles();
			if (children != null) {
				for (File parent : children) {
					templates.addAll(load(parent));
				}
			}
		}
		if (file.getAbsolutePath().endsWith(".ftl")) {
			templates.add(file);
		}
		return templates;
	}

	private List<File> loadDefaultSourceFiles() {
		List<File> templates = new ArrayList<>();
		templates.add(new File(GConfiguration.class.getClassLoader().getResource("default_ftls/JAVA_Mapper.ftl").getFile()));
		templates.add(new File(GConfiguration.class.getClassLoader().getResource("default_ftls/XML_Mapper.ftl").getFile()));
		return templates;
	}

	public GConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(GConfiguration configuration) {
		this.configuration = configuration;
	}

}
