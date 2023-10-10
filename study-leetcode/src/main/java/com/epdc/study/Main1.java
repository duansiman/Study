package com.epdc.study;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-11-30.
 */
public class Main1 {

	public static void main(String[] args) {
		System.out.println(removeDuplicateLetters("cbacdcbc"));
	}

	public static String removeDuplicateLetters(String s) {
		List<StringBuilder> result = new ArrayList<>();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (result.isEmpty()) {
				result.add(new StringBuilder(String.valueOf(ch)));
				continue;
			}

			List<StringBuilder> tempResult = new ArrayList<>();
			Iterator<StringBuilder> iterator = result.iterator();
			while (iterator.hasNext()) {
				String temp = iterator.next().toString();

				int indexOf = temp.lastIndexOf(String.valueOf(ch));
				String temp2;
				if (indexOf > -1) {
					String temp1 = temp.toString().replace(String.valueOf(ch), "");
					temp2 = String.format("%s%s", temp1, ch);
					tempResult.add(new StringBuilder(temp));
					tempResult.add(new StringBuilder(temp2));
				} else {
					temp2 = String.format("%s%s", temp, ch);
					tempResult.add(new StringBuilder(temp2));
				}
			}
			result = tempResult;
		}
		System.out.println(result.size());
		return result.stream().sorted(Comparator.comparing(StringBuilder::toString)).findFirst().get().toString();
	}

}
