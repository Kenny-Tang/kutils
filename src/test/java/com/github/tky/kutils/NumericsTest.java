package com.github.tky.kutils;

import static org.junit.Assert.assertEquals;

import java.math.RoundingMode;

import org.junit.Test;

public class NumericsTest {

    @Test
    public void testGt() {
        boolean result  = Numerics.gt(0.5 , 0.612, 2, RoundingMode.HALF_UP) ;
        assertEquals(result, false);
        
        result = Numerics.gt(0.50002, 0.5, 2, RoundingMode.HALF_UP) ;
        assertEquals(result, false);
    }
    
    @Test
    public void testEq() {
        boolean result  = Numerics.eq(0.5 , 0.612, 2, RoundingMode.HALF_UP) ;
        assertEquals(result, false);
        
        result = Numerics.eq(0.50002, 0.5, 2, RoundingMode.HALF_UP) ;
        assertEquals(result, true);
    }
}
