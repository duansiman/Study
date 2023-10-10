package com.epdc.study;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-12-11.
 */
public class StringMain {

	public static void main(String[] args) {
		testIntern();
	}

	public static void testIntern() {
		String s = new String("1");
		String s2 = "1";
		s.intern();
		System.out.println(s == s2);

		String s3 = new String("1") + new String("1");
		s3.intern();
		String s4 = "11";
		System.out.println(s3 == s4);
	}
}
