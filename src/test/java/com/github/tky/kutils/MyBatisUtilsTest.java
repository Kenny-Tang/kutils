package com.github.tky.kutils;

import java.util.List;

import javax.persistence.Table;

import org.junit.Test;

import com.github.tky.kutils.mybatis.ColumnHandler;
import com.github.tky.kutils.mybatis.JdbcDriver;
import com.github.tky.kutils.mybatis.MyConfig;
import com.test.BaseTest;

@Table(name = "test_table")
public class MyBatisUtilsTest extends BaseTest {

	MyConfig config = new MyConfig(JdbcDriver.MySQL, "jdbc:Mysql://192.168.6.48:3306/customer", "customer", "haoshikisses", "customer", "com.github.tky") ;
	String projectDir = "/Users/kenny/dev/workspace/kutils" ;
    @Test
    public void testGenerateMapper() {
        List<ColumnHandler> columns = MyBatisUtils.getColumnInfo(config) ;
        assertTrue(columns.size() > 0);
        MyBatisUtils.generateMapperXml(config, projectDir);
    }
    
    @Test
    public void testGenerateDao() {
        MyBatisUtils.generateMapperDao(config, projectDir);
    }
    
    @Test
    public void generateMapperEntity() {
    	MyBatisUtils.generateMapperEntity(config, projectDir);
    }
    
    @Test
    public void generateQueryParam() {
    	MyBatisUtils.generateQueryParam(config, projectDir);
    }
    
}

