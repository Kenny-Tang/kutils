package com.github.tky.kutils.type;

public abstract class DefaultTypeHandler implements TypeHandler{

	@Override
	public String getTypeFullName() {
		return getType().getTypeName();
	}

	@Override
	public String getTypeSimpleName() {
		return ((Class<?>)getType()).getSimpleName();
	}

}
