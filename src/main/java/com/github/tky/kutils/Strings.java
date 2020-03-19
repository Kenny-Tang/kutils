package com.github.tky.kutils;

import com.github.tky.kutils.handler.InPropertyHandler;

/**
 * 
 * @author Kenny
 *
 */
public class Strings extends org.apache.commons.lang3.StringUtils {
    
    private Strings() {}
    /**
     * 将字符串调整成可以在 in 条件中使用的形式<br/>
     *  <pre>
     *      eg: a,b,c 将转化成 'a','b','c'
     *  </pre>
     * @param inList
     * @return string can append to a sql
     */
    public static String splitAsInCondation(String inList) {
        String[] list = inList.split(",");
        StringBuffer condation = new StringBuffer(" ");
        for (int i = 0; i < list.length; i++) {
            if (!isBlank(list[i]))
                condation.append("'").append(list[i].trim()).append("',");
        }
        condation.replace(condation.length() - 1, condation.length(), " ");
        return condation.toString();
    }
    
    /**
     * 
     * @param <T>
     * @param inList
     * @param handler
     * @return
     */
    public static  <T> String splitAsInCondation(java.util.List<T> inList, InPropertyHandler<T> handler) {
        StringBuffer condation = new StringBuffer("");
        for (int i = 0; i < inList.size(); i++) {
            String inItem = null ;
            if(handler != null) {
                inItem = handler.inItem(inList.get(i)) ;
            }else {
                inItem = inList.toString() ;
            }
            if (!isBlank(inItem))
                condation.append("'").append(inItem.trim()).append("',");
        }
        condation.replace(condation.length() - 1, condation.length(), "");
        return condation.toString();
    }
    
    
}