package com.github.tky.kutils;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.github.tky.kutils.mybatis.ColumnHandler;
import com.github.tky.kutils.mybatis.ColumnType;
import com.github.tky.kutils.mybatis.MyConfig;
import com.github.tky.kutils.mybatis.XmlMapperHandler;
import com.google.common.base.CaseFormat;

public class MyBatisUtils {

    
    public static void generateMapperXml(MyConfig config, String projectDir)  {
        File xml = createXmlFile(config, projectDir);
        writeMapperXml(xml, config);
    }
    
    private static void writeMapperXml(File file, MyConfig config) {
        try {
            Document document = DocumentHelper.createDocument() ;
            document.addDocType(XmlMapperHandler.DOC_TYPE_NAME, XmlMapperHandler.DOC_TYPE_PUBLIC_ID, XmlMapperHandler.DOC_TYPE_SYSTEM_ID) ;
            Element root = document.addElement("mapper") ;
            String namespace = config.getNamespacePrefix().concat(".mapper.").concat(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, config.getTable())).concat(XmlMapperHandler.MAPPER_SUFFIX) ;
            root.addAttribute("namespace", namespace);
           
            createBaseColumnsSql(root, config) ;
            
            createInsertElement(root, config) ;
            
            createQueryByIdElement(root, config) ;
            
            createUpdateElement(root, config) ;
            
            createQueryElement(root, config) ;
            // set output style
            OutputFormat format =  OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            format.setIndentSize(4);
            format.setNewLineAfterNTags(1);
            format.setTrimText(false);
            
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format) ;
            writer.setEscapeText(false); 
            writer.write(document);
            writer.close(); 
        } catch (Exception e) {
            e.printStackTrace();
           throw new RuntimeException(e) ;
        }
        
    }
    
    private static void createQueryElement(Element root, MyConfig config) {
        root.addComment(" list query ") ;
        Element query = root.addElement("select") ;
        query.addAttribute("id", "query") ;
        String queryName =  config.getQueryName() ;
        query.addAttribute("parameterType", queryName) ;
        StringBuffer sql = new StringBuffer(XmlMapperHandler.NEW_LINE) ;
        sql.append(XmlMapperHandler.TAB_SPACE8) ;
        sql.append("select <include refid=\"base_columns\" /> \n") ;
        sql.append(XmlMapperHandler.TAB_SPACE8).append("from ").append(config.getTable()).append(XmlMapperHandler.NEW_LINE) ;
        sql.append(XmlMapperHandler.TAB_SPACE8).append("where 1=1");
        sql.append(XmlMapperHandler.NEW_LINE).append(XmlMapperHandler.TAB_SPACE4) ;
        query.setText(sql.toString());
        List<ColumnHandler> handlers = getColumnInfo(config) ;
        for (int i = 0; i < handlers.size(); i++) {
            ColumnHandler handler = handlers.get(i) ;
            if(ColumnType.VARCHAR.code().equals(handler.getTypeName())) {
                Element test = query.addElement("if") ;
                String attr = handler.getField().concat(" != null and ").concat(handler.getField()).concat(" != ''");
                test.addAttribute("test", attr);
                String cSql = " and " + handler.getColumnName() + " = #{"+handler.getField()+"}" ;
                test.setText(cSql); 
            }
        }
    }

    private static void createUpdateElement(Element root, MyConfig config) {
        root.addComment(" 更新操作 ");
        Element update = root.addElement("update") ;
        StringBuffer sql = new StringBuffer(XmlMapperHandler.NEW_LINE) ;
        sql.append(XmlMapperHandler.TAB_SPACE8) ;
        sql.append("update ").append(config.getTable()).append(XmlMapperHandler.NEW_LINE) ;
        sql.append(XmlMapperHandler.TAB_SPACE8).append("set ").append(XmlMapperHandler.NEW_LINE) ;
        List<ColumnHandler> handlers = getColumnInfo(config) ;
        for (int i = 0; i < handlers.size(); i++) {
            ColumnHandler handler = handlers.get(i) ;
            sql.append(XmlMapperHandler.TAB_SPACE12).append(handler.getColumnName()).append(" = ").append("#{").append(handler.getField()).append(" } ") ;
            if(i != handlers.size()) {
                sql.append(",") ;
            }
            sql.append(XmlMapperHandler.NEW_LINE) ;
        }
        sql.append(XmlMapperHandler.TAB_SPACE8).append("where id = #{id }").append(XmlMapperHandler.NEW_LINE).append(XmlMapperHandler.TAB_SPACE4) ;
        
        update.setText(sql.toString()); 
    }

    private static void createQueryByIdElement(Element root, MyConfig config) {
        root.addComment(" queryById ") ;
        Element findById = root.addElement("select") ;
        findById.addAttribute("id", "queryById") ;
        String entity = config.getEntityFullName() ;
        findById.addAttribute("resultType", entity) ;
        StringBuffer sql = new StringBuffer() ;
        sql.append(XmlMapperHandler.NEW_LINE) ;
        sql.append(XmlMapperHandler.TAB_SPACE8) ;
        sql.append("select <include refid=\"base_columns\" /> \n") ;
        sql.append(XmlMapperHandler.TAB_SPACE8).append("from ").append(config.getTable()).append("\n") ;
        sql.append(XmlMapperHandler.TAB_SPACE8).append("where  id = #{id } \n").append(XmlMapperHandler.TAB_SPACE4) ;
        findById.setText(sql.toString()) ;
        
    }

    private static void createInsertElement(Element root, MyConfig config) {
        root.addComment(" save entity ");
        Element insert = root.addElement("insert") ;
        insert.addAttribute("id", "create") ;
        String entity = config.getEntityFullName() ;
        insert.addAttribute("parameterType",entity);
        List<ColumnHandler> columns = getColumnInfo(config) ;
        StringBuffer values = new StringBuffer("") ;
        for (int i = 0; i < columns.size(); i++) {
            ColumnHandler handler = columns.get(i) ;
            values.append("#{"+handler.getField()+"}, " );
        }
        String insertSql = XmlMapperHandler.NEW_LINE.concat(XmlMapperHandler.TAB_SPACE8).concat("insert into ")+config.getTable().concat(XmlMapperHandler.NEW_LINE)+  
                "            (<include refid=\"base_columns\" />)\r\n" + 
                "        values \n".concat(XmlMapperHandler.TAB_SPACE12).concat("("+values.substring(0, values.length()-2)).concat(")").concat(XmlMapperHandler.NEW_LINE).concat(XmlMapperHandler.TAB_SPACE4) ;
        insert.setText(insertSql); 
    }

    private static void createBaseColumnsSql(Element root, MyConfig config) {
        root.addComment(" 表字段 ") ;
        Element sql = root.addElement("sql") ;
        sql.addAttribute("id", XmlMapperHandler.BASE_COLUMNS_ID) ;
        List<ColumnHandler> columns = getColumnInfo(config) ;
        String baseColumns = getColumns(columns) ;
        baseColumns = XmlMapperHandler.NEW_LINE.concat(XmlMapperHandler.TAB_SPACE8).concat(baseColumns).concat(XmlMapperHandler.NEW_LINE).concat(XmlMapperHandler.TAB_SPACE4) ;
        sql.addText(baseColumns) ;
        
    }

    private static String getColumns(List<ColumnHandler> columns) {
        String baseColumns = "" ;
        for (int i = 0; i < columns.size(); i++) {
            ColumnHandler column = columns.get(i) ;
            baseColumns += column.getColumnName() + ", " ;
        }
        return baseColumns.substring(0, baseColumns.length() - 2 );
    }

    private static File createXmlFile(MyConfig config, String projectDir) {
        File xml;
        try {
            String xmlName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, config.getTable()).concat("Mapper.xml") ;
            String abs = projectDir + config.getRelativePath() + config.getNamespacePrefix().replace(".", "/") + "/" ;
            File packages = new File( abs ) ;
            packages.mkdirs();
            xml = new File(packages, xmlName);
            if(xml.exists()) {
                // throw new RuntimeException("") ;
            }
            // xml.createNewFile() ;
        } catch (Exception e) {
            throw new RuntimeException(e) ;
        }
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
