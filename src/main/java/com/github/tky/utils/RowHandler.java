package com.github.tky.utils;

import java.util.Date;

public abstract class RowHandler<T> {
    
    public abstract Object handler(T t, int i) ;

    public abstract String[] getTitle();

    public  String getFilename(){
        return DateUtils.format(new Date(), DateUtils.DATE_YYYYMMDDHHMMSS);
    }
    
    public boolean textOnly(){
        return true ;
    }

}