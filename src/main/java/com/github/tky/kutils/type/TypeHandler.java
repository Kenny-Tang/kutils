package com.github.tky.kutils.type;

import java.lang.reflect.Type;

public interface TypeHandler {

	Type getType() ;
	
	String getTypeFullName() ;
	
	String getTypeSimpleName() ;
}
