package com.github.tky.kutils.type;

import java.util.ArrayList;

public class TypeList extends ArrayList<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2304221877296267085L;
	
	@Override
	public boolean add(String e) {
		switch (e) {
		case "long":
		case "java.lang.Long":
		case "int":
		case "java.lang.Integer":
		case "short":
		case "java.lang.Short":
		case "float":
		case "java.lang.Float":
		case "double":
		case "java.lang.Double":
		case "boolean":
		case "java.lang.Boolean":
		case "java.lang.String":
			return false ;
		}
		return super.add(e);
	}
	
}
