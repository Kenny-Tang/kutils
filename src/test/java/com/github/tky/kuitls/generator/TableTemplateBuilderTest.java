package com.github.tky.kuitls.generator;

import org.junit.Test;

import com.github.tky.kutils.generator.builder.TemplateBuilder;
import com.github.tky.kutils.generator.builder.TemplateBuilderFactory;
import com.github.tky.kutils.generator.loader.TableInfoLoader;

public class TableTemplateBuilderTest {
	
	@Test
	public void testGenerate() {
		TemplateBuilder builder = new TemplateBuilderFactory().build() ;
		TableInfoLoader dataLoader = (TableInfoLoader) builder.getDataLoader() ;
		dataLoader.setTable("SALES_BILL_ACTIVITY_STATUS", "AgentActivityRelation");
		builder.generate();
	}

}

	