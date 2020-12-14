package com.github.tky.kutils.excel;

import java.util.Date;

import com.github.tky.kutils.Dates;

public abstract class RowHandler<T> {
    
    /**
     * 获取对象中在指定列对应的值
     * @param t 需要保存到Excel中的对象
     * @param i 列对应的序列号
     * @return
     */
    public abstract Object handler(T t, int i) ;

    /**
     * get the excel title columns
     * @return the title array
     */
    public abstract String[] getTitle();

    /**
     * Use the current time as the default filename 
     * @return current tiem as string 
     */
    public  String getFilename(){
        return Dates.format(new Date(), Dates.FMT_YYYYMMDDHHMMSS);
    }
    
    public boolean textOnly(){
        return true ;
    }

}