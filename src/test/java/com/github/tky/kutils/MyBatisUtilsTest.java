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

    @Test
    public void testGenerateMapper() {
        MyConfig config = new MyConfig(JdbcDriver.MySQL, "jdbc:Mysql://localhost:3306/kenny", "root", "123456", "order", "") ;
        List<ColumnHandler> columns = MyBatisUtils.getColumnInfo(config) ;
        assertTrue(columns.size() > 0);
    }
}
