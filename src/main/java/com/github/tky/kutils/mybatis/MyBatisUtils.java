package com.github.tky.kutils.mybatis;

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

import com.github.tky.kutils.KConstants;
import com.github.tky.kutils.Strings;
import com.google.common.base.CaseFormat;

public class MyBatisUtils {


    public static void generateMapperXml(MyConfig config, String projectDir)  {
    	File file = createXmlFile(config, projectDir);
        try {
            Document document = DocumentHelper.createDocument() ;
            document.addDocType(KConstants.DOC_TYPE_NAME, KConstants.DOC_TYPE_PUBLIC_URI, KConstants.DOC_TYPE_SYSTEM_URI) ;
            Element root = document.addElement("mapper") ;
            String namespace = config.getMapperName() ;
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
        StringBuffer sql = new StringBuffer(KConstants.NEW_LINE) ;
        sql.append(KConstants.TAB_SPACE8) ;
        sql.append("select <include refid=\"base_columns\" /> \n") ;
        sql.append(KConstants.TAB_SPACE8).append("from ").append(config.getTable()).append(KConstants.NEW_LINE) ;
        sql.append(KConstants.TAB_SPACE8).append("where 1=1");
        sql.append(KConstants.NEW_LINE).append(KConstants.TAB_SPACE4) ;
        query.setText(sql.toString());
        List<ColumnHandler> handlers = getColumnInfo(config) ;
        for (int i = 0; i < handlers.size(); i++) {
            ColumnHandler handler = handlers.get(i) ;
            if(i==10) {
            	break ;
            }
            if(ColumnType.VARCHAR.code().equals(handler.getTypeName())) {
                Element test = query.addElement("if") ;
                String attr = handler.getFieldName().concat(" != null and ").concat(handler.getFieldName()).concat(" != ''");
                test.addAttribute("test", attr);
                String cSql = " and " + handler.getColumnName() + " = #{"+handler.getFieldName()+"}" ;
                test.setText(cSql); 
            }
        }
    }

    private static void createUpdateElement(Element root, MyConfig config) {
        root.addComment(" 更新操作 ");
        Element update = root.addElement("update") ;
        update.addAttribute("id", "update") ;
        StringBuffer sql = new StringBuffer(KConstants.NEW_LINE) ;
        sql.append(KConstants.TAB_SPACE8) ;
        sql.append("update ").append(config.getTable()).append(KConstants.NEW_LINE) ;
        sql.append(KConstants.TAB_SPACE8).append("set ").append(KConstants.NEW_LINE) ;
        List<ColumnHandler> handlers = getColumnInfo(config) ;
        for (int i = 0; i < handlers.size(); i++) {
            ColumnHandler handler = handlers.get(i) ;
            sql.append(KConstants.TAB_SPACE12).append(handler.getColumnName()) ;
            if(handler.getColumnName().length() < 4 ) {
            	sql.append("\t\t\t") ;
            } else if(handler.getColumnName().length() < 8) {
            	sql.append("\t\t") ;
            } else {
            	sql.append("\t");
            }
            sql.append("=").append("\t").append("#{").append(handler.getFieldName()).append(" } ") ;
            if(i != handlers.size()) {
                sql.append(",") ;
            }
            sql.append(KConstants.NEW_LINE) ;
        }
        sql.append(KConstants.TAB_SPACE8).append("where id = #{id }").append(KConstants.NEW_LINE).append(KConstants.TAB_SPACE4) ;
        
        update.setText(sql.toString()); 
    }

    private static void createQueryByIdElement(Element root, MyConfig config) {
        root.addComment(" queryById ") ;
        Element findById = root.addElement("select") ;
        findById.addAttribute("id", "queryById") ;
        String entity = config.getEntityName() ;
        findById.addAttribute("resultType", entity) ;
        StringBuffer sql = new StringBuffer() ;
        sql.append(KConstants.NEW_LINE) ;
        sql.append(KConstants.TAB_SPACE8) ;
        sql.append("select <include refid=\"base_columns\" /> \n") ;
        sql.append(KConstants.TAB_SPACE8).append("from ").append(config.getTable()).append("\n") ;
        sql.append(KConstants.TAB_SPACE8).append("where  id = #{id } \n").append(KConstants.TAB_SPACE4) ;
        findById.setText(sql.toString()) ;
        
    }

    private static void createInsertElement(Element root, MyConfig config) {
        root.addComment(" save entity ");
        Element insert = root.addElement("insert") ;
        insert.addAttribute("id", "create") ;
        String entity = config.getEntityName() ;
        insert.addAttribute("parameterType",entity);
        List<ColumnHandler> columns = getColumnInfo(config) ;
        StringBuffer values = new StringBuffer("") ;
        for (int i = 0; i < columns.size(); i++) {
            ColumnHandler handler = columns.get(i) ;
            values.append("#{"+handler.getFieldName()+"}, " );
        }
        String insertSql = KConstants.NEW_LINE.concat(KConstants.TAB_SPACE8).concat("insert into ")+config.getTable().concat(KConstants.NEW_LINE)+  
                "            (<include refid=\"base_columns\" />)\r\n".replace("t.", "") + 
                "        values \n".concat(KConstants.TAB_SPACE12).concat("("+values.substring(0, values.length()-2)).concat(")").concat(KConstants.NEW_LINE).concat(KConstants.TAB_SPACE4) ;
        insert.setText(insertSql); 
    }

    private static void createBaseColumnsSql(Element root, MyConfig config) {
        root.addComment(" 表字段 ") ;
        Element sql = root.addElement("sql") ;
        sql.addAttribute("id", KConstants.BASE_COLUMNS_ID) ;
        List<ColumnHandler> columns = getColumnInfo(config) ;
        String baseColumns = getColumns(columns) ;
        baseColumns = KConstants.NEW_LINE.concat(KConstants.TAB_SPACE8).concat(baseColumns).concat(KConstants.NEW_LINE).concat(KConstants.TAB_SPACE4) ;
        sql.addText(baseColumns) ;
        
    }
    private static String getColumns(List<ColumnHandler> columns) {
        return getColumns(columns, "") ; 
    }
    private static String getColumns(List<ColumnHandler> columns, String alias) {
        String baseColumns = "" ;
        for (int i = 0; i < columns.size(); i++) {
            ColumnHandler column = columns.get(i) ;
            if(alias != null && alias == "") {
                baseColumns += alias +"."+ column.getColumnName() + ", " ;
            }else {
                baseColumns += alias +"."+ column.getColumnName() + ", " ;
            }
        }
        return baseColumns.substring(0, baseColumns.length() - 2 );
    }

    private static File createXmlFile(MyConfig config, String projectDir) {
        File xml;
        try {
            String xmlName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, config.getTable()).concat("Mapper.xml") ;
            String abs = projectDir + config.getRelativePathMapperXml() + config.getNamespacePrefix().replace(".", "/") + "/" ;
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

    public static void generateMapperDao(MyConfig config, String projectDir) {
        File mapperFile ;
        String mapper = config.getUpperCamelTable().concat("Mapper.java") ;
        String abs = projectDir + config.getRelativePathJava() + config.getNamespacePrefix().replace(".", "/") + "/mapper/" ;
        File packages = new File( abs ) ;
        packages.mkdirs();
        mapperFile = new File(packages, mapper);
        try {
			if(mapperFile.exists()) {
			    // throw new RuntimeException("") ;
			} else {
				mapperFile.createNewFile() ;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(mapperFile) ;
			String pkg = "package ".concat(config.getNamespacePrefix()).concat(".mapper ; \n\n") ;
			fileOutputStream.write(pkg.getBytes());
			String imports = "import java.util.List;\n" + 
					"\n" + 
					"import "+config.getNamespacePrefix().concat(".bean.")+config.getUpperCamelTable()+";\n" + 
					"import "+config.getNamespacePrefix().concat(".bean.param.")+config.getUpperCamelTable()+"Query;\n" ;
			fileOutputStream.write(imports.getBytes());
			String cls = "public interface " + config.getUpperCamelTable() + "Mapper { \n" ;
			fileOutputStream.write(cls.getBytes());
			String s = "	public void create("+config.getUpperCamelTable()+" "+config.getLowerCamelTable()+") ;\n" + 
					"	\n" + 
					"	public "+config.getUpperCamelTable()+" queryById(String id) ;\n" + 
					"	\n" + 
					"	public int update("+config.getUpperCamelTable()+" "+config.getLowerCamelTable()+") ;\n" + 
					"	\n" + 
					"	public List<"+config.getUpperCamelTable()+"> query("+config.getUpperCamelTable()+"Query query) ; \n\n" ;
			fileOutputStream.write(s.getBytes());
			fileOutputStream.write("}".getBytes());
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
    }
    
    public static void generateQueryParam(MyConfig config, String projectDir) {
        File entityFile;
        try {
            String entity = config.getUpperCamelTable().concat("Query.java") ;
            String abs = projectDir + config.getRelativePathJava() + config.getNamespacePrefix().replace(".", "/") + "/bean/param/" ;
            File packages = new File( abs ) ;
            packages.mkdirs();
            entityFile = new File(packages, entity);
            if(entityFile.exists()) {
                // throw new RuntimeException("") ;
            } else {
                entityFile.createNewFile() ;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(entityFile) ;
            String pkg = "package ".concat(config.getNamespacePrefix()).concat(".bean.param ; \n") ;
            String cls = "public class " + config.getUpperCamelTable() + "Query implements Serializable{ \n" ;
            fileOutputStream.write(pkg.getBytes());
            fileOutputStream.write("import java.io.Serializable;\n".getBytes());
            fileOutputStream.write(cls.getBytes());
            fileOutputStream.write("\tprivate static final long serialVersionUID = 1L;\n".getBytes());
            List<ColumnHandler> handlers = getColumnInfo(config) ;
            for (int i = 0; i < handlers.size(); i++) {
                ColumnHandler handler = handlers.get(i) ;
                if(i==10) {
                	break ;
                }
                if(ColumnType.VARCHAR.code().equals(handler.getTypeName())) {
                    String field  = handler.getFieldDeclare() ;
                    fileOutputStream.write(field.getBytes()) ;
                }
            }
            String pageInfo = "	private int pageNum = 1;\n" + 
            "	private int pageSize = 10;\n" + 
            "	\n" ;
            fileOutputStream.write(pageInfo.getBytes());
            String getPaeInfo = "	public int getPageNum() {\n" + 
            "		return pageNum;\n" + 
            "	}\n" + 
            "	public void setPageNum(int pageNum) {\n" + 
            "		this.pageNum = pageNum;\n" + 
            "	}\n" + 
            "	public int getPageSize() {\n" + 
            "		return pageSize;\n" + 
            "	}\n" + 
            "	public void setPageSize(int pageSize) {\n" + 
            "		this.pageSize = pageSize;\n" + 
            "	}\n" ;
            fileOutputStream.write(getPaeInfo.getBytes());

            for (int i = 0; i < handlers.size(); i++) {
            	ColumnHandler handler = handlers.get(i) ;
                if(i==10) {
                	break ;
                }
                if(ColumnType.VARCHAR.code().equals(handler.getTypeName())) {
	                String setter  = handler.getFieldSetterMethod() ;
	                fileOutputStream.write(setter.getBytes()) ;
	                String getter = handler.getFieldGetterMethod() ;
	                fileOutputStream.write(getter.getBytes());
                }
			}
            fileOutputStream.write("}".getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e) ;
        }
    }
    public static void generateMapperEntity(MyConfig config, String projectDir) {
        File entityFile;
        try {
            String entity = config.getUpperCamelTable().concat(".java") ;
            String abs = projectDir + config.getRelativePathJava() + config.getNamespacePrefix().replace(".", "/") + "/bean/" ;
            File packages = new File( abs ) ;
            packages.mkdirs();
            entityFile = new File(packages, entity);
            if(entityFile.exists()) {
                // throw new RuntimeException("") ;
            } else {
                entityFile.createNewFile() ;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(entityFile) ;
            String pkg = "package ".concat(config.getNamespacePrefix()).concat(".bean ; \n") ;
            String cls = "public class " + config.getUpperCamelTable() + "{ \n" ;
            fileOutputStream.write(pkg.getBytes());
            fileOutputStream.write(cls.getBytes());
            
            List<ColumnHandler> columns = getColumnInfo(config);
            for (int i = 0; i < columns.size() ; i++) {
                ColumnHandler handler = columns.get(i) ;
                String field  = handler.getFieldDeclare() ;
                fileOutputStream.write(field.getBytes()) ;
            }
            for (int i = 0; i < columns.size() ; i++) {
                ColumnHandler handler = columns.get(i) ;
                String setter  = handler.getFieldSetterMethod() ;
                fileOutputStream.write(setter.getBytes()) ;
                String getter = handler.getFieldGetterMethod() ;
                fileOutputStream.write(getter.getBytes());
            }
            fileOutputStream.write("}".getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e) ;
        }
    }

    public static void generateService(MyConfig config, String projectDir) {
        File mapperFile ;
        String mapper = config.getUpperCamelTable().concat("Service.java") ;
        String abs = projectDir + config.getRelativePathJava() + config.getNamespacePrefix().replace(".", "/") + "/service/" ;
        File packages = new File( abs ) ;
        packages.mkdirs();
        mapperFile = new File(packages, mapper);
        try {
            if(mapperFile.exists()) {
                // throw new RuntimeException("") ;
            } else {
                mapperFile.createNewFile() ;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(mapperFile) ;
            String pkg = "package ".concat(config.getNamespacePrefix()).concat(".service ; \n\n") ;
            fileOutputStream.write(pkg.getBytes());
            String imports = "import java.util.List;\n" + 
                    "import org.springframework.beans.factory.annotation.Autowired;\n" + 
                    "import "+config.getNamespacePrefix().concat(".bean.")+config.getUpperCamelTable()+";\n" + 
                    "import "+config.getNamespacePrefix().concat(".bean.param.")+config.getUpperCamelTable()+"Query;\n"
                            + "import com.github.pagehelper.PageHelper;\r\n" + 
                            "import com.github.pagehelper.PageInfo;\r\n" + 
                            "import "+config.getNamespacePrefix().concat(".mapper.")+config.getUpperCamelTable()+"Mapper;" ;
                
            fileOutputStream.write(imports.getBytes());
            String cls = "\npublic class " + config.getUpperCamelTable() + "Service { \n" ;
            fileOutputStream.write(cls.getBytes());
            String s = "    @Autowired\r\n" + 
                    "    "+config.getSimpleMapperName()+" "+Strings.uppperCamelToLowerCamel(config.getSimpleMapperName())+" ;\r\n" + 
                    "    public void create("+config.getUpperCamelTable()+" "+config.getLowerCamelTable()+") {\r\n" + 
                    "        "+Strings.uppperCamelToLowerCamel(config.getSimpleMapperName())+".create("+config.getLowerCamelTable()+");\r\n" + 
                    "    }\r\n" + 
                    "   \r\n" + 
                    "   public "+config.getUpperCamelTable()+" queryById(String id) {\r\n" + 
                    "       return "+config.getLowerCamelTable()+"Mapper.queryById(id) ; \r\n" + 
                    "   }\r\n" + 
                    "   \r\n" + 
                    "   public int update("+config.getUpperCamelTable()+" "+config.getLowerCamelTable()+") {\r\n" + 
                    "       return "+Strings.uppperCamelToLowerCamel(config.getSimpleMapperName())+".update("+config.getLowerCamelTable()+") ;\r\n" + 
                    "   }\r\n" + 
                    "   \r\n" + 
                    "   public PageInfo<"+config.getUpperCamelTable()+"> query("+config.getUpperCamelTable()+"Query query, boolean isPage) {\r\n" + 
                    "       if(isPage) {\r\n" + 
                    "           PageHelper.startPage(query.getPageNum(), query.getPageSize()) ;\r\n" + 
                    "       }\r\n" + 
                    "       List<Customer> list = "+Strings.uppperCamelToLowerCamel(config.getSimpleMapperName())+".query(query) ;\r\n" + 
                    "       return new PageInfo<Customer>(list) ;\r\n" + 
                    "   } \n";
            fileOutputStream.write(s.getBytes());
            fileOutputStream.write("}".getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e) ;
        }
    }

}

