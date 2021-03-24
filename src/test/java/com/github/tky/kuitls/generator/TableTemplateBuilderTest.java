package com.github.tky.kuitls.generator;

import org.junit.Test;

import com.github.tky.kutils.generator.GConfiguration;
import com.github.tky.kutils.generator.builder.TemplateBuilder;
import com.github.tky.kutils.generator.builder.TemplateBuilderFactory;
import com.github.tky.kutils.generator.loader.TableInfoLoader;

public class TableTemplateBuilderTest {

	@Test
	public void testGenerate() {
		TemplateBuilder builder = new TemplateBuilderFactory().build();
		GConfiguration configuration = builder.getConfiguration();
		configuration.setUserDefaultTemplates(false);
		configuration.addProperties("modelPackage", "com.github.kenny.model");
		configuration.addProperties("addColumnDefine", false);
		TableInfoLoader dataLoader = (TableInfoLoader) builder.getDataLoader();
		dataLoader.setTable("product_info");
		builder.generate();
	}

	@Test
	public void dateCenter() {
		TemplateBuilder builder = new TemplateBuilderFactory().build();
		GConfiguration configuration = builder.getConfiguration();
		configuration.setUserDefaultTemplates(false);
		configuration.addProperties("modelPackage", "com.jf.data.persistence.activity.model");
		configuration.addProperties("daoPackage", "com.jf.data.persistence.activity.mapper");
		configuration.addProperties("servicePackage", "com.jf.data.application.activity");
		configuration.addProperties("addColumnDefine", false);
		TableInfoLoader dataLoader = (TableInfoLoader) builder.getDataLoader();
		dataLoader.setTable("activity_info_chg");
		builder.generate();
	}

}
