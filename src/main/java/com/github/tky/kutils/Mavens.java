package com.github.tky.kutils;

import java.io.File;
import java.io.FileReader;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

public class Mavens {

	/**
	 * 获取指定POM 得版本信息
	 * 
	 * @param pomFile
	 * @return
	 */
	public static Model getMaven(File pomFile) {
		try {
			MavenXpp3Reader reader = new MavenXpp3Reader();
			if (pomFile != null) {
				Model model = reader.read(new FileReader(pomFile));
				return model;
			}
			String rootPath = System.getProperty("user.dir");
			String pom = rootPath + File.separator + "pom.xml";
			Model model = reader.read(new FileReader(pom));
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static Model getMaven() {
		return getMaven(null);
	}
}
