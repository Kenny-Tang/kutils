package com.github.tky.kutils.mybatis;

public class XmlMapperHandler {
    /** 声明部分  */
    public static String declare = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" + 
            "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n" ;
    /** Mapper 主体开始 */
    public static String mapper_head ;
    /** table columms  */
    public static String base_clumns ;
    
    /** method elements */
    public static String create ;
    public static String queryById ;
    public static String updateById ;
    public static String query ;
    public static String mappertail ;
}
