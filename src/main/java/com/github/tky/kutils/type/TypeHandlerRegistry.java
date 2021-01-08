package com.github.tky.kutils.type;

import java.util.EnumMap;
import java.util.Map;

public class TypeHandlerRegistry {
	
	private final Map<JdbcType, TypeHandler>  jdbcTypeHandlerMap = new EnumMap<>(JdbcType.class);

	public TypeHandlerRegistry() {
		super();
		register(JdbcType.TINYINT, new DefaultTypeHandler());
		register(JdbcType.SMALLINT, new DefaultTypeHandler());
		register(JdbcType.INTEGER, new DefaultTypeHandler());
		register(JdbcType.BIGINT, new DefaultTypeHandler());
		register(JdbcType.VARCHAR, new DefaultTypeHandler());
		register(JdbcType.TIMESTAMP, new DefaultTypeHandler());
		register(JdbcType.DECIMAL, new DefaultTypeHandler());
	    register(JdbcType.NUMERIC, new DefaultTypeHandler());
	}

	public void register(JdbcType jdbcType, TypeHandler typeHandler) {
		jdbcTypeHandlerMap.put(jdbcType, typeHandler) ;
	}

	public TypeHandler getTypeHandler(JdbcType jdbcType) {
		TypeHandler typeHandler = jdbcTypeHandlerMap.get(jdbcType) ;
		if(typeHandler == null) {
			throw new RuntimeException("No TypeHandler found for JdbcType : "+jdbcType);
		}
		return typeHandler ;
	}
	  
}
