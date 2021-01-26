package com.github.tky.kutils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
}
