package com.github.tky.utils;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.test.BaseTest;

public class DateUtilsTest extends BaseTest {

    @Test
    public void testFirstDayOfNextMonth() {
        Calendar calendar = Calendar.getInstance() ;
        calendar.set(Calendar.YEAR, 2000);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        
        Date target = DateUtils.firstDayOfNextMonth(calendar.getTime()) ;
        
        assertEquals("2000-06-01 00:00:00", DateUtils.format(target, DateUtils.DATE_YYYY_MM_DD_HH_MM_SS));
    }
    
}
