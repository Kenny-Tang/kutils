package com.github.tky.kutils.mybatis;

import java.util.Date;

import com.github.tky.kutils.KConstants;
import com.google.common.base.CaseFormat;

public class ColumnHandler{
    
	private String columnName ;
    private String typeName ;
    
    private String fieldType ;
    private Class<?> fieldClass ;
    private String fieldName ;
    
    public ColumnHandler(String columnName, String typeName) {
        super();
        this.columnName = columnName;
        this.typeName = typeName;
        this.fieldName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.columnName) ;
        this.fieldClass = getFieldClass(typeName) ;
        this.fieldType = this.fieldClass.getSimpleName() ;
    }
    
    private Class<?> getFieldClass(String typeName) {
//        if("DECIMAL".equals(typeName)) {
//            return Long.class ;
//        }
        if("DATETIME".equals(typeName)) {
            return Date.class ;
        }
        return String.class ;
    }

    //  
    
    @Override
    public String toString() {
        return "ColumnHandler [columnName=" + columnName + ", typeName=" + typeName + "]";
    }

    public  Class<?> getFieldClass() {
        return fieldClass;
    }

    public void setFieldClass(Class<?> fieldClass) {
        this.fieldClass = fieldClass;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getColumnName() {
        return columnName;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName ;
    }
    
    public String getFieldDeclare() {
        String dec = KConstants.TAB_SPACE4 + "private "+this.fieldType+" " + getFieldName() + " ; \n" ;
        return dec;
    }
    
    public String getFieldSetterMethod() {
        String setter = KConstants.TAB_SPACE4 + "public void set" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.columnName) 
            + "("+this.fieldType+" " + getFieldName() + ") { \n"
            + KConstants.TAB_SPACE8 + " this."+getFieldName() + " = " + getFieldName() + ";\n" 
            + KConstants.TAB_SPACE4 + "}\n\n";
        return setter ;
    }

    public String getFieldGetterMethod() {
        String getter = KConstants.TAB_SPACE4 + "public "+this.fieldType+" get" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.columnName) + "() { \n"
            + KConstants.TAB_SPACE8 + "return this." + getFieldName() + " ; \n"
            + KConstants.TAB_SPACE4 + "}\n\n";
        return getter;
    }
}