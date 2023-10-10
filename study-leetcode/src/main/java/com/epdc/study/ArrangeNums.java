package com.epdc.study;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-10-17.
 */
public class ArrangeNums {

	public static void main(String[] args) {
		List<String> nums = Arrays.asList("3","30","34","5","9");
	}

	private static String arrangeResult(List<String> nums) {
		nums.stream().sorted((o1, o2) -> {
			return o1.compareTo(o2);
		});

		return null;
	}

	private static int compare(String num1, String num2) {
		boolean firstCharEqual = Objects.equals(num1.charAt(0), num2.charAt(0));
		boolean isSameLength = Objects.equals(num1.length(), num2.length());
		if (!firstCharEqual || isSameLength) {
			return num1.compareTo(num2);
		}
		return 0;
	}

}
