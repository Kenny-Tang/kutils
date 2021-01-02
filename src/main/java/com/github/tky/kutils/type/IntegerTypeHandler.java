package com.github.tky.kutils.type;

import java.lang.reflect.Type;

public class IntegerTypeHandler extends DefaultTypeHandler {

	@Override
	public Type getType() {
		return Integer.class;
	}

}
