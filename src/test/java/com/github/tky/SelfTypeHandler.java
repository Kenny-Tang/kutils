package com.github.tky;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.tky.kutils.type.DefaultTypeHandler;

public class SelfTypeHandler extends DefaultTypeHandler{

	@Override
	public Type getType(ResultSet rs) {
	  try {
        String columnName = rs.getString("COLUMN_NAME") ;
        if("ID".equalsIgnoreCase(columnName) || "OPTIMISTIC".equalsIgnoreCase(columnName)) {
          return Long.class ;
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
	  return super.getType(rs);
	}

}
