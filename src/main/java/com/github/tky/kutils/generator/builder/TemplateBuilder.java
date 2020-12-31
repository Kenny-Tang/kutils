package com.github.tky.kutils.generator.builder;

import java.io.File;

public interface TemplateBuilder{
	
	void generate();
	void generate(File file);
}