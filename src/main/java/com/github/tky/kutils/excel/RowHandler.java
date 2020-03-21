package com.github.tky.kutils.excel;

import java.util.Date;

import com.github.tky.kutils.Dates;

public abstract class RowHandler<T> {
    
    public abstract Object handler(T t, int i) ;

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