package com.github.tky.kutils;

import java.util.List;

import javax.persistence.Table;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tky.kutils.mybatis.ColumnHandler;
import com.github.tky.kutils.mybatis.JdbcDriver;
import com.github.tky.kutils.mybatis.MyConfig;
import com.test.BaseTest;

@Table(name = "test_table")
public class MyBatisUtilsTest extends BaseTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass()) ;
    
	MyConfig config = new MyConfig(JdbcDriver.MySQL, "jdbc:Mysql://192.168.6.48:3306/customer", "customer", "haoshikisses", "customer", "com.github.tky") ;
	// String projectDir = "/Users/kenny/dev/workspace/kutils" ;
	String projectDir = "E:/Kenny/GitHub/ares" ;
	
	@Test
	public void testMyConfig() {
	    logger.info("MyConfig.getSimpleMapperName()\t: {} ",config.getSimpleMapperName());
	    logger.info("MyConfig.getMapperName()\t: {} ", config.getMapperName());
	    logger.info("MyConfig.getSimpleEntityName()\t: {} ", config.getSimpleEntityName());
	    logger.info("MyConfig.getEntityName()\t: {} ", config.getEntityName());
	    logger.info("MyConfig.getLowerCamelTable()\t: {} ",config.getLowerCamelTable());
	    logger.info("MyConfig.getSimpleServiceName()\t: {} ",config.getSimpleServiceName());
	    logger.info("MyConfig.getMapperName()\t: {} ", config.getMapperName());
	}
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
    public void testGenerateService() {
        MyBatisUtils.generateService(config, projectDir);
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

