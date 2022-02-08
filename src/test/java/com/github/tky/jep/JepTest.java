package com.github.tky.jep;

import com.test.BaseTest;
import org.junit.Test;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

public class JepTest extends BaseTest {

    @Test
    public void test() throws ParseException {
        JEP jep = new JEP() ;
        jep.addStandardFunctions();
        jep.addVariable("a", 1) ;
        jep.addVariable("b", 2) ;
        jep.addVariable("c", 4) ;
        String exp = "((a+b)*(c+b))/(c+a)/b" ; // 1.8
        Node parse = jep.parse(exp);
        Object evaluate = jep.evaluate(parse);
        System.out.println(evaluate);

        String exp2 = "a + abs(b) + sqrt(c) - ceil(d)" ; // 6.0

        jep.addVariable("a" , 5) ;
        jep.addVariable("b" , -2) ;
        jep.addVariable("c", 4) ;
        jep.addVariable("d", 2.1) ;
        Node node = jep.parse(exp2);
        Object result = jep.evaluate(node);
        System.out.println(result);

        Node booleanExp = jep.parse("a > 10");
        Object evaluate1 = jep.evaluate(booleanExp);
        System.out.println(evaluate1);

    }
}
