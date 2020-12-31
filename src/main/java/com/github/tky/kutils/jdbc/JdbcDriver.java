package com.github.tky.kutils.jdbc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kenny 
 * @ClassName: JdbcDriver   
 * 
 * @date 2020年3月16日 
 */
public enum JdbcDriver {
    MySQL("MySQL", "com.mysql.jdbc.Driver"), 
    ORALCE("ORACLE", "oracle.jdbc.driver.OracleDriver");

    private JdbcDriver(String code, String driver) {
        this.code = code;
        this.driver = driver;
    }

    private String code;
    private String driver;
    public static Map<String, JdbcDriver> map = null;
    static {
        map = new HashMap<String, JdbcDriver>();
        for (int i = 0; i < JdbcDriver.values().length; i++) {
            map.put(JdbcDriver.values()[i].code, JdbcDriver.values()[i]);
        }
    }

    public static JdbcDriver code(String code) {
        if (map.get(code) == null) {
            return null;
        }
        return map.get(code);
    }

    public String code() {
        return this.code;
    }

    public String getDriver() {
        return driver;
    }

}
