package com.github.tky.kutils.str;

import java.util.Map;

public class DefaultTokenHandler implements TokenHandler {

	private Map<Object, Object> properties;

	public DefaultTokenHandler(Map<Object, Object> properties) {
		this.properties = properties;
	}

	@Override
	public String handleToken(String content) {
		return properties.get(content.trim()).toString();
	}
}
