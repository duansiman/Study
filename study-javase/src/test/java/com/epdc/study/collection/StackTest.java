package com.epdc.study.collection;

import org.junit.Test;

import java.util.Stack;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2023-01-31.
 */
public class StackTest {

	@Test
	public void test() {
		Stack<Integer> stack = new Stack<>();
		stack.push(2);
		stack.push(1);
		stack.push(3);
		stack.push(5);
		for (Integer integer : stack) {
			System.out.println(integer);
		}
	}

}
