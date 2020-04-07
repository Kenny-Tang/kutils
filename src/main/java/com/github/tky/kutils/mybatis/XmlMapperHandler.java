package com.github.tky.kutils.mybatis;

public class XmlMapperHandler {
    
    /** 文档类型声明 */
    public static String DOC_TYPE_NAME = "mapper"; 
    public static String DOC_TYPE_PUBLIC_URI = "-//mybatis.org//DTD Mapper 3.0//EN" ;
    public static String DOC_TYPE_SYSTEM_URI =  "http://mybatis.org/dtd/mybatis-3-mapper.dtd" ;
    /** table columms sql */
    public static String BASE_COLUMNS_ID = "base_columns" ;
    
    /** method elements */
    public static String create ;
    public static String queryById ;
    public static String updateById ;
    public static String query ;
    public static String mappertail ;
}
