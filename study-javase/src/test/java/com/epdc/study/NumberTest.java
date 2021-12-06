package com.epdc.study;

import org.junit.Test;

public class NumberTest {

	@Test
	public void testInteger() {
		System.out.println(String.format("max:%s, min:%s", Integer.MAX_VALUE, Integer.MIN_VALUE));

		int max = Integer.MAX_VALUE;
		System.out.println(max + 1);

		int min = Integer.MIN_VALUE;
		System.out.println(min - 1);

		System.out.println(Math.sqrt(9));
	}

}
