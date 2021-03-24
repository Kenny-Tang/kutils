package com.github.tky.kutils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.github.tky.kutils.str.DefaultTokenHandler;
import com.github.tky.kutils.str.InPropertyHandler;

public class StringsTest {

	@Test
	public void splitAsInCondationListStr() {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			list.add(i + "");
		}
		String instr = Strings.splitAsInCondation(list, null);
		assertEquals("'0','1','2','3'", instr);
	}

	@Test
	public void splitAsInCondationListObj() {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			list.add(i + "");
		}
		String instr = Strings.splitAsInCondation(list, new InPropertyHandler<String>() {
			public String inItem(String t) {
				return t + t;
			}
		});
		assertEquals("'00','11','22','33'", instr);
	}

	@Test
	public void testCaseChange() {
		String string = "aaBBcc";
		assertEquals("AaBBcc", Strings.lowerCamelToUpperCamel(string));
		string = "AaBBcc";
		assertEquals("aaBBcc", Strings.uppperCamelToLowerCamel(string));
		string = "aa_bb_cc";
		assertEquals("aaBbCc", Strings.lowerUnderscoreToLowerCamel(string));
		assertEquals("AaBbCc", Strings.lowerUnderscoreToUpperCamel(string));
		string = "go_and_done";
		assertEquals("GoAndDone", Strings.lowerUnderscoreToUpperCamel(string));
		assertEquals("goAndDone", Strings.lowerUnderscoreToLowerCamel(string));
	}

	@Test
	public void testParse() {
		Properties properties;

		properties = new Properties();
		properties.setProperty("groupId", "com.github.kenny");
		properties.setProperty("artifactId", "kutils");
		String res = Strings.parse("KPATH:src/main/java/${groupId }.${artifactId }.model", "${", "}", new DefaultTokenHandler(properties));
		assertEquals("KPATH:src/main/java/com.github.kenny.kutils.model", res);
	}
}
