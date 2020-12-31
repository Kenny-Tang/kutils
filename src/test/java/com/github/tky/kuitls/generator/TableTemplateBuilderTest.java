package com.github.tky.kuitls.generator;

import org.junit.Test;

import com.github.tky.kutils.generator.GConfiguration;
import com.github.tky.kutils.generator.builder.TableTemplateBuilder;
import com.github.tky.kutils.generator.builder.TemplateBuilderFactory;

public class TableTemplateBuilderTest {
	
	@Test
	public void testGConfiguration() {
		TableTemplateBuilder builder = (TableTemplateBuilder) new TemplateBuilderFactory().build() ;
		GConfiguration configuration = builder.getConfiguration() ;
		System.err.println(configuration.getProperties());
	}

}

	