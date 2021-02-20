package com.github.tky.kutils.generator.loader;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.model.Model;

import com.github.tky.kutils.Dates;
import com.github.tky.kutils.Mavens;
import com.github.tky.kutils.Strings;
import com.github.tky.kutils.generator.GConfiguration;

public abstract class AbstractDataLoader implements DataLoader {

	private GConfiguration configuration;

	public Map<Object, Object> load() {
		Properties properties = new Properties();
		if (configuration.getProperties() != null) {
			properties.putAll(configuration.getProperties());
		}
		properties.put("curentTime", Dates.format(new Date(), Dates.FMT_YYYY_MM_DD_HH_MM_SS));
		properties.put("author", System.getProperty("user.name"));
		Model model = Mavens.getMaven();
		properties.put("artifactId", model.getArtifactId().replace("-", "."));
		String groupId = model.getGroupId();
		if(Strings.isEmpty(groupId)) {
			groupId = model.getParent().getGroupId();
		}
		groupId = groupId.replace("-", ".") ;
		properties.put("groupId", groupId);
		Map<Object, Object> info = queryDataInfo();
		if (info != null) {
			properties.putAll(info);
		}
		return properties;
	}

	public abstract Map<Object, Object> queryDataInfo();

	public GConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(GConfiguration configuration) {
		this.configuration = configuration;
	}

}