package com.github.tky.kutils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tky.kutils.str.InPropertyHandler;

public class StringsTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass()) ;
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
    
    @Test
    public void testCaseChange() {
    	String string = "aaBBcc" ;
    	logger.info("Strings.lowerCamelToUpperCamel()\t: {}\t-> {}", string, Strings.lowerCamelToUpperCamel(string));
    	string = "AaBBcc" ;
    	logger.info("Strings.uppperCamelToLowerCamel()\t: {}\t-> {}", string, Strings.uppperCamelToLowerCamel(string));
    	string = "aa_bb_cc" ;
    	logger.info("Strings.lowerUnderscoreToLowerCamel(): {}\t-> {}", string, Strings.lowerUnderscoreToLowerCamel(string));
    	logger.info("Strings.lowerUnderscoreToUpperCamel(): {}\t-> {}", string, Strings.lowerUnderscoreToUpperCamel(string));
    }
}
