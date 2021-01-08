package com.github.tky.kutils.type;

import java.lang.reflect.Type;
import java.sql.ResultSet;

public interface TypeHandler {

	Type getType(ResultSet rs) ;
	
	String getTypeFullName(ResultSet rs) ;
	
	String getTypeSimpleName(ResultSet rs) ;
}
