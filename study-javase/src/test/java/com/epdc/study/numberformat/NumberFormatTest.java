package com.epdc.study.numberformat;

import org.junit.Test;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by devin on 2021-11-08.
 */
public class NumberFormatTest {

	@Test
	public void test() {
		Locale locale = new Locale("in", "ID");
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		String format = String.format("- %s", numberFormat.format(999999.99));
		System.out.println(format);
	}

}
