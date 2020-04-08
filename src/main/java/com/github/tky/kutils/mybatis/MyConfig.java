package com.github.tky.kutils.mybatis;

import com.google.common.base.CaseFormat;

public class MyConfig {
    
    private JdbcDriver driver;
    private String url ;
    private String username ;
    private String password ;
    private String table ;
    private String namespacePrefix ;
    private String relativePathMapperXml = "/src/main/resources/" ;
    private String relativePathJava= "/src/main/java/" ;
    // 默认Mapper 后缀
    public static String MAPPER_SUFFIX = "Mapper" ;
    @Override
    public String toString() {
        return "MyConfig [driver=" + driver + ", url=" + url + ", username=" + username + ", password=" + password
                + ", table=" + table + ", namespacePrefix=" + namespacePrefix + "]";
    }
    public MyConfig(JdbcDriver driver, String url, String username, String password, String table,
            String namespacePrefix) {
        super();
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.table = table;
        this.namespacePrefix = namespacePrefix;
    }

    public String getUpperCamelTable() {
    	return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.getTable()) ;
    }
    public String getLowerCamelTable() {
    	return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.getTable()) ;
    }
    public String getSimpleMapperName() {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.getTable()) + "Mapper" ;
    }
    public String getMapperName() {
        String mapperName = this.namespacePrefix 
                + ".mapper."
                + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.getTable()) + "Mapper" ;
        
        return mapperName ;
    }
    public String getSimpleServiceName() {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.getTable()) + "Service" ;
    }
    public String getSimpleEntityName() {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table) ;
    }
    public String getEntityName() {
        return namespacePrefix.concat(".bean.entity.").concat(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table)) ;
    }
    public String getBeanName() {
        return namespacePrefix.concat(".bean.").concat(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table)) ;
    }
    public String getQueryName() {
        return namespacePrefix.concat(".bean.param.").concat(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, getTable())).concat("Query") ;
    }
    /**
     * eg. EntityDao --> entityDao
     * @param simpleClassName
     * @return
     */
    public String getLowerCamel(String simpleClassName) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, simpleClassName);
    }
    
    
    public String getRelativePathJava() {
        return relativePathJava;
    }
    public void setRelativePathJava(String relativePathJava) {
        this.relativePathJava = relativePathJava;
    }
    public String getRelativePathMapperXml() {
        return relativePathMapperXml;
    }
    public void setRelativePathMapperXml(String relativePathMapperXml) {
        this.relativePathMapperXml = relativePathMapperXml;
    }
    public String getDriver() {
        return driver.getDriver();
    }
    public void setDriver(JdbcDriver driver) {
        this.driver = driver;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getTable() {
        return table;
    }
    public void setTable(String table) {
        this.table = table;
    }
    public String getNamespacePrefix() {
        return namespacePrefix;
    }
    public void setNamespacePrefix(String namespacePrefix) {
        this.namespacePrefix = namespacePrefix;
    }
}
