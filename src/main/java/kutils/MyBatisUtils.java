package com.github.tky.kutils;

import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.jstl.core.Config;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.github.tky.kutils.mybatis.ColumnHandler;
import com.github.tky.kutils.mybatis.JdbcDriver;
import com.github.tky.kutils.mybatis.MyConfig;
import com.github.tky.kutils.mybatis.XmlMapperHandler;
import com.google.common.base.CaseFormat;

public class MyBatisUtils {

    
    public static void generateMapperXml(MyConfig config, String projectDir) throws Exception {
        File xml = createXmlFile(config, projectDir);
    }
    
    private void writeMapperXml(File file, MyConfig config) {
        Document document = DocumentHelper.createDocument() ;
        document.addDocType(XmlMapperHandler.DOC_TYPE_NAME, XmlMapperHandler.DOC_TYPE_PUBLIC_ID, XmlMapperHandler.DOC_TYPE_SYSTEM_ID) ;
        Element root = DocumentHelper.createElement("mapper") ;
        root.addAttribute("namespace", config.getNamespacePrefix()) ;
        document.addElement(arg0)
    }
    
    private static File createXmlFile(MyConfig config, String projectDir) throws IOException {
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
