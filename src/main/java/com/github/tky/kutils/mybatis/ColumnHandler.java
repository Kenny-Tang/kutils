package com.github.tky.kutils.mybatis;

import com.google.common.base.CaseFormat;

public class ColumnHandler{
    
    private String columnName ;
    private String typeName ;
    
    public ColumnHandler(String columnName, String typeName) {
        super();
        this.columnName = columnName;
        this.typeName = typeName;
    }
    
    @Override
    public String toString() {
        return "ColumnHandler [columnName=" + columnName + ", typeName=" + typeName + "]";
    }

    public String getColumnName() {
        return columnName;
    }
    public String getTypeName() {
        return typeName;
    }
    public String getField() {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.columnName) ;
    }
    
}