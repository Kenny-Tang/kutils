package com.github.tky.kutils.type;

import java.lang.reflect.Type;
import java.util.Date;

public class DateTypeHandler extends DefaultTypeHandler {

	@Override
	public Type getType() {
		return Date.class;
	}

}
