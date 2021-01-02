package com.github.tky.kutils.type;

import java.util.EnumMap;
import java.util.Map;

public class TypeHandlerRegistry {
	
	private final Map<JdbcType, TypeHandler>  jdbcTypeHandlerMap = new EnumMap<>(JdbcType.class);

	public TypeHandlerRegistry() {
		super();
		register(JdbcType.TINYINT, new IntegerTypeHandler());
		register(JdbcType.SMALLINT, new IntegerTypeHandler());
		register(JdbcType.BIGINT, new LongTypeHandler());
		register(JdbcType.VARCHAR, new StringTypeHandler());
		register(JdbcType.TIMESTAMP, new DateTypeHandler());
		register(JdbcType.DECIMAL, new BigDecimalTypeHandler());
	    register(JdbcType.NUMERIC, new BigDecimalTypeHandler());
	}

	public void register(JdbcType jdbcType, TypeHandler typeHandler) {
		jdbcTypeHandlerMap.put(jdbcType, typeHandler) ;
	}

	public TypeHandler getTypeHandler(JdbcType jdbcType) {
		return jdbcTypeHandlerMap.get(jdbcType) ;
	}
	  
}
