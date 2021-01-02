package com.github.tky.kutils.type;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class BigDecimalTypeHandler  extends DefaultTypeHandler {

	@Override
	public Type getType() {
		return BigDecimal.class;
	}


}
