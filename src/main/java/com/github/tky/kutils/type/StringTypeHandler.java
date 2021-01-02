package com.github.tky.kutils.type;

import java.lang.reflect.Type;

public class StringTypeHandler extends DefaultTypeHandler {

	@Override
	public Type getType() {
		return String.class;
	}

}
