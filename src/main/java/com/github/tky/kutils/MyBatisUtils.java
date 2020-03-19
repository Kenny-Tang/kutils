package com.github.tky.kutils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.github.tky.kutils.mybatis.ColumnHandler;
import com.github.tky.kutils.mybatis.JdbcDriver;
import com.github.tky.kutils.mybatis.MyConfig;
import com.google.common.base.CaseFormat;

public class MyBatisUtils {

    
    public static void generateMapperXml(MyConfig config, String projectDir) throws Exception {
        File xml = createXmlMapper(config, projectDir);
    }
    
    private void writeMapperXml(File file) {
        
    }
    
    private static File createXmlMapper(MyConfig config, String projectDir) throws IOException {
        String xmlName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, config.getTable()).concat("Mapper.xml") ;
        String abs = projectDir + config.getRelativePath() + config.getNamespacePrefix().replace(".", "/") + "/" ;
        File packages = new File( abs ) ;
        packages.mkdirs();
        File xml = new File(packages, xmlName) ;
        if(xml.exists()) {
            throw new RuntimeException("") ;
        }
        xml.createNewFile() ;
        return xml;
    }
    
    public static void main(String[] args) throws Exception {
        MyConfig config = new MyConfig(JdbcDriver.MySQL, "jdbc:Mysql://localhost:3306/kenny", "root", "123456", "order", "com.tky.trans") ;
        List<ColumnHandler> columns = getColumnInfo(config) ;
        System.out.println(columns);
        generateMapperXml(config, "E:/Kenny/GitHub/ares");
    }
    
    public static List<ColumnHandler> getColumnInfo(MyConfig config) {

        List<ColumnHandler> columns = new ArrayList<ColumnHandler>() ;
        try{
            // 注册 JDBC 驱动
            Class.forName(config.getDriver());
            // 打开链接
            Connection conn = DriverManager.getConnection(config.getUrl(),config.getUsername() ,config.getPassword());
            DatabaseMetaData metaData = conn.getMetaData() ;
            ResultSet rs = metaData.getColumns(null, "%", config.getTable(), "%") ;
            // 展开结果集数据库
            while(rs.next()) {
                columns.add(new ColumnHandler(rs.getString("COLUMN_NAME"), rs.getString("TYPE_NAME"))) ;
            }
            // 完成后关闭
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columns ;
    }

}
