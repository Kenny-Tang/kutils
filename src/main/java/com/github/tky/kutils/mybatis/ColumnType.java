package com.github.tky.kutils.mybatis;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kenny 
 * @ClassName: DbDataType   
 * 
 * @date 2020年3月21日 
 */
public enum ColumnType {

	VARCHAR("VARCHAR", "变长字符串");

	private ColumnType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private String code;
	private String desc;
	public static Map<String, ColumnType> map = null;
	static {
		map = new HashMap<String, ColumnType>();
		for (int i = 0; i < ColumnType.values().length; i++) {
			map.put(ColumnType.values()[i].code, ColumnType.values()[i]);
		}
	}

	public static ColumnType code(String code) {
		if (map.get(code) == null) {
			return null;
		}
		return map.get(code);
	}

	public String code() {
		return this.code;
	}

	public String label() {
		return this.desc;
	}
}
