package com.github.tky.kutils.type;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class DefaultTypeHandler implements TypeHandler{

	@Override
	public String getTypeFullName(ResultSet rs) {
		return getType(rs).getTypeName();
	}

	@Override
	public String getTypeSimpleName(ResultSet rs) {
		return ((Class<?>)getType(rs)).getSimpleName();
	}
	
	@Override
	public Type getType(ResultSet rs) {
		Integer dataType = 0;
		try {
			dataType = rs.getInt("DATA_TYPE");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		switch (dataType) {
		case Types.TINYINT:
		case Types.SMALLINT:
		case Types.INTEGER:
			return Integer.class ;
		case Types.BIGINT:
			return Long.class ;
		case Types.NUMERIC:
		case Types.DECIMAL:
			return BigDecimal.class ;
		case Types.FLOAT :
		case Types.DOUBLE :
			return Double.class ;
		case Types.DATE:
		case Types.TIMESTAMP:
			return Date.class ;
		case Types.VARCHAR:
		case Types.NVARCHAR :
		case Types.LONGNVARCHAR :
			return String.class ;
		case Types.BOOLEAN :
			return Boolean.class ;
		}
		return null;
	}

}
