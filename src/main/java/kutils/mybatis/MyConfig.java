package com.github.tky.kutils.mybatis;

public class MyConfig {
    
    private JdbcDriver driver;
    private String url ;
    private String username ;
    private String password ;
    private String table ;
    private String namespacePrefix ;
    private String relativePath = "/src/main/resources/" ;
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

     
    public String getRelativePath() {
        return relativePath;
    }
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
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
