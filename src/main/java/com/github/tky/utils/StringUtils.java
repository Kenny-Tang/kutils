package com.github.tky.utils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    
    /**
     * 将字符串调整成可以在 in 条件中使用的形式<br/>
     *  <pre>
     *      eg: a,b,c 将转化成 'a','b','c'
     *  </pre>
     * @param inList
     * @return
     */
    public static String splitAsInCondation(String inList) {
        String[] list = inList.split(",");
        StringBuffer condation = new StringBuffer(" ");
        for (int i = 0; i < list.length; i++) {
            if (!StringUtils.isBlank(list[i]))
                condation.append("'").append(list[i].trim()).append("',");
        }
        condation.replace(condation.length() - 1, condation.length(), " ");
        return condation.toString();
    }
}
