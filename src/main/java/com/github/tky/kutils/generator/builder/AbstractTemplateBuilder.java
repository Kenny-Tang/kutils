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

public abstract class AbstractTemplateBuilder implements TemplateBuilder{
	
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
				Object dataModel = dataLoader.load();
				String absPaht = file.getAbsolutePath();
				String ftl = absPaht.substring(configuration.getTemplatesRoot().length()+1+absPaht.indexOf(configuration.getTemplatesRoot()));
				Template template = freemarkerConfiguration.getTemplate(ftl);
				File outFile = createOutputFile(ftl);
				Writer out  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
				template.process(dataModel, out);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private File createOutputFile(String ftl) {
		
		if(!ftl.endsWith("ftl")) {
			return null ;
		}
		
		String templateFilename = ftl.substring(ftl.lastIndexOf(File.separator) + 1);
		
		int suffixIndex = templateFilename.indexOf("_") ;
		String suffix = templateFilename.substring(suffixIndex + 1, templateFilename.lastIndexOf(".")) ;
		if(suffixIndex >= 0) {
			suffix += "." + templateFilename.substring(0, suffixIndex).toLowerCase();
		}
		String generateFile = getOutputFilename() + suffix;
		
		File outFile = new File(configuration.getOutputDir() + ftl.replace(templateFilename, generateFile));
		Files.createFile(outFile);
		
		return outFile;
	}
	
	/**
	 * get the output filename without suffix
	 * @return
	 * 		the filename without suffix
	 */
	public abstract String getOutputFilename() ;
	
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


