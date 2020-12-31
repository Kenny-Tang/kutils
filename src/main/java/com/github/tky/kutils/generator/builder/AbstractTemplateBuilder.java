package com.github.tky.kutils.generator.builder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.github.tky.kutils.Files;
import com.github.tky.kutils.generator.GConfiguration;
import com.github.tky.kutils.generator.loader.DataLoader;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class AbstractTemplateBuilder implements TemplateBuilder{
	
	Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_23) ;
	private GConfiguration configuration ;
	DataLoader dataLoader ;
	
	public AbstractTemplateBuilder(GConfiguration configuration) {
		this.configuration = configuration ;
		this.dataLoader = configuration.getDataLoader() ;
		try {
			freemarkerConfiguration.setDirectoryForTemplateLoading(configuration.getDirectoryForTemplateLoadingFile());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
	}
	
	@Override
	public void generate(File file) {
		
		Object dataModel = dataLoader.load();
		Template template;
		try {
			if(file.isDirectory()) {
				File[] children = file.listFiles() ;
				if(children != null) {
					for (File parent : children) {
						generate(parent);
					}
				}
			}
			else {
				String absPaht = file.getAbsolutePath();
				String ftl = absPaht.substring(configuration.getTemplatesRoot().length()+1+absPaht.indexOf(configuration.getTemplatesRoot()));
				template = freemarkerConfiguration.getTemplate(ftl);
				File outFile = createOutputFile(ftl);
				Writer out  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
				template.process(dataModel, out);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private File createOutputFile(String ftl) {
		
		String templateFilename = ftl.substring(ftl.lastIndexOf(File.separator) + 1);
		
		String suffix = templateFilename.substring(templateFilename.indexOf("_") + 1, templateFilename.lastIndexOf(".")) + "."
				+ templateFilename.substring(0, templateFilename.indexOf("_")).toLowerCase();
		String generateFile = configuration.getUpperCamelEntityName() + suffix;
		File outFile = new File(configuration.getOutputDir() + ftl.replace(templateFilename, generateFile));
		Files.createFile(outFile);
		
		return outFile;
	}
	
	@Override
	public void generate() {
		generate(configuration.getDirectoryForTemplateLoadingFile()) ;
	}

	public DataLoader getDataLoader() {
		return dataLoader;
	}

	public void setDataLoader(DataLoader dataLoader) {
		this.dataLoader = dataLoader;
	}

	public GConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(GConfiguration configuration) {
		this.configuration = configuration;
	}
	
}


