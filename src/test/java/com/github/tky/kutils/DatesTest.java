package com.github.tky.kutils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import com.test.BaseTest;

public class DatesTest extends BaseTest {

    @Test
    public void testParseDate() {
        String str = "2020-01-25";
        String pat = "yyyy-MM-dd";
        String[] pats = new String[]{"yyyy-MM-dd"};

        Date tmp = Dates.parse(str,pat );
        Date tmp1 = null;
        try {
            tmp1 = DateUtils.parseDate(str,pat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date target = null;
        try {
            target = DateUtils.parseDate( str,pats );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Assert.assertEquals( tmp,target );
        Assert.assertEquals( tmp,tmp1 );
    }

    @Test
    public void testFirstDayOfNextMonth() {
        Calendar calendar = Calendar.getInstance() ;
        calendar.set(Calendar.YEAR, 2000);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        
        Date target = Dates.firstDayOfNextMonth(calendar.getTime()) ;
        
        assertEquals("2000-06-01 00:00:00", Dates.format(target, Dates.FMT_YYYY_MM_DD_HH_MM_SS));
    }
    
}
