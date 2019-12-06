package com.github.tky.kutils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.tky.kutils.handler.InPropertyHandler;

public class StringsTest {

    @Test
    public void splitAsInCondationListStr() {
        List<String> list  = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(i+"") ;
        }
        String instr = Strings.splitAsInCondation(list, null) ;
        assertEquals("'0','1','2','3'", instr);
    }
    
    @Test
    public void splitAsInCondationListObj() {
        List<String> list  = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(i+"") ;
        }
        String instr = Strings.splitAsInCondation(list, new InPropertyHandler<String>() {
            public String inItem(String t) {
                return t+t;
            }
        } ) ;
        assertEquals("'00','11','22','33'", instr);
    }
}
