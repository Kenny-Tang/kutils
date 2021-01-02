package com.github.tky.kutils.type;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author kenny
 *
 */
public enum JdbcType {

	TINYINT(Types.TINYINT),
	SMALLINT(Types.SMALLINT),
	INTEGER(Types.INTEGER),
	BIGINT(Types.BIGINT),
	FLOAT(Types.FLOAT),
	DOUBLE(Types.DOUBLE),
	NUMERIC(Types.NUMERIC),
	DECIMAL(Types.DECIMAL),
	CHAR(Types.CHAR),
	VARCHAR(Types.VARCHAR),
	LONGVARCHAR(Types.LONGVARCHAR),
	DATE(Types.DATE),
	TIME(Types.TIME),
	TIMESTAMP(Types.TIMESTAMP),
	NULL(Types.NULL),
	OTHER(Types.OTHER),
	BLOB(Types.BLOB),
	CLOB(Types.CLOB),
	BOOLEAN(Types.BOOLEAN);

	public final int TYPE_CODE;
	private static Map<Integer,JdbcType> codeLookup = new HashMap<>();

	static {
		for (JdbcType type : JdbcType.values()) {
			codeLookup.put(type.TYPE_CODE, type);
		}
	}

	JdbcType(int code) {
		this.TYPE_CODE = code;
	}

	public static JdbcType forCode(int code)	{
		return codeLookup.get(code);
	}

}