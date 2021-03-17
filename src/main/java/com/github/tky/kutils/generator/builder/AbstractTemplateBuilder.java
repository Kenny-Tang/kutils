package com.github.tky.kutils.generator.builder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tky.kutils.Files;
import com.github.tky.kutils.Strings;
import com.github.tky.kutils.generator.GConfiguration;
import com.github.tky.kutils.generator.loader.DataLoader;
import com.github.tky.kutils.generator.loader.DefaultTemplateLoader;
import com.github.tky.kutils.generator.loader.SourceFileLoader;
import com.github.tky.kutils.str.DefaultTokenHandler;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class AbstractTemplateBuilder implements TemplateBuilder {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_23);
	private GConfiguration configuration;
	DataLoader dataLoader;
	SourceFileLoader sourceFileLoader;

	public AbstractTemplateBuilder(GConfiguration configuration) {
		this.configuration = configuration;
		this.dataLoader = configuration.getDataLoader();
		sourceFileLoader = new DefaultTemplateLoader(configuration);
	}

	@Override
	public void generate() {
		Map<Object, Object> dataModel = dataLoader.load();
		List<File> sourceFiles = sourceFileLoader.load();

		if (sourceFiles != null) {
			for (File file : sourceFiles) {
				generate(file, dataModel);
			}
		}
	}

	@Override
	public void generate(File file, Map<Object, Object> dataModel) {
		BufferedReader reader = null;
		try {
			if (file.isDirectory()) {
				return;
			}
			String absPath = file.getAbsolutePath();
			if (!absPath.endsWith(".ftl")) {
				return;
			}

			String path = null;
			if (absPath.contains("jar!") && absPath.contains("default_ftls")) {
				String resource = absPath.substring(absPath.lastIndexOf("jar!") + 4).replace(File.separator, "/");
				reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(resource)));
			} else {
				reader = new BufferedReader(new FileReader(file));
			}

			path = getOutputPath(reader.readLine(), dataModel);
			if (Strings.isEmpty(path)) {
				reader = new BufferedReader(new FileReader(file));
			}
			File outFile = createOutputFile(absPath, path);
			process(file, dataModel, reader, outFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void process(File source, Map<Object, Object> dataModel, BufferedReader reader, File outFile) throws IOException, FileNotFoundException, TemplateException {
		Template template = new Template(source.getName(), reader, freemarkerConfiguration);
		if (outFile.exists() && !configuration.getFileOverride()) {
			logger.info("File {} already exists!", outFile.getName());
			return;
		}
		Files.createFile(outFile);
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
		template.process(dataModel, out);
	}

	protected String getOutputPath(String path, Map<Object, Object> dataModel) {
		if (path.startsWith("KPATH:")) {
			path = path.replace("KPATH:", "").replace(" ", "");
			path = Strings.parse(path, "${", "}", new DefaultTokenHandler(dataModel));
			logger.debug("getOutputPath: {}", path);
			return path.replace("-", ".");
		}
		return null;
	}

	private File createOutputFile(String absPath, String path) throws IOException {

		String ftl = absPath.substring(configuration.getTemplatesRoot().length() + 1 + absPath.indexOf(configuration.getTemplatesRoot()));
		if (!ftl.endsWith("ftl")) {
			return null;
		}

		String templateFilename = ftl.substring(ftl.lastIndexOf(File.separator) + 1);

		int suffixIndex = templateFilename.indexOf("_");
		String suffix = templateFilename.substring(suffixIndex + 1, templateFilename.lastIndexOf("."));
		if (suffixIndex >= 0) {
			suffix += "." + templateFilename.substring(0, suffixIndex).toLowerCase();
		}
		String generateFile = getOutputFilename() + suffix;

		File outFile = null;
		if (Strings.isNotEmpty(path)) {
			outFile = new File(path.replace(".", File.separator) + File.separator + generateFile);
		} else {
			outFile = new File(ftl.replace(templateFilename, generateFile));
		}

		return outFile;
	}

	/**
	 * get the output filename without suffix
	 * 
	 * @return the filename without suffix
	 */
	public abstract String getOutputFilename();

	public DataLoader getDataLoader() {
		return dataLoader;
	}

	public void setDataLoader(DataLoader dataLoader) {
		this.dataLoader = dataLoader;
	}

	public SourceFileLoader getSourceFileLoader() {
		return sourceFileLoader;
	}

	public void setSourceFileLoader(SourceFileLoader sourceFileLoader) {
		this.sourceFileLoader = sourceFileLoader;
	}

	public GConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(GConfiguration configuration) {
		this.configuration = configuration;
	}

}
