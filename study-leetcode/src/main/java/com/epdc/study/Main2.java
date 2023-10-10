package com.epdc.study;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
 * 换句话说，第一个字符串的排列之一是第二个字符串的子串。
 *
 * 输入: s1 = "ab" s2 = "eidbaooo"
 * 输出: True
 * 解释: s2 包含 s1 的排列之一 ("ba").
 *
 */
public class Main2 {

	public static void main(String[] args) {
		boolean[] result = new boolean[]{Boolean.FALSE};
		String s1 = "ab";
		Map<Character, Integer> allChar = new HashMap<>();
		char[] chars = s1.toCharArray();
		for (Character c : chars) {
			Integer count = allChar.getOrDefault(c, 0);
			count++;
			allChar.put(c, count);
		}
		isContain(s1, "eidbaooo", 1, new StringBuilder(""), new HashMap<>(), allChar,  result);
		System.out.println(result[0]);
	}

	public static void isContain(String s1, String s2, int index, StringBuilder allowStr,
			Map<Character, Integer> allowedChar, Map<Character, Integer> allChar, boolean[] result) {
		if (result[0]) {
			return;
		}

		for (Character ch : s1.toCharArray()) {
			if (allChar.get(ch) > allowedChar.getOrDefault(ch, 0)) {
				allowStr.append(ch);
				Integer count = allowedChar.getOrDefault(ch, 0);
				allowedChar.put(ch, ++count);

				if (s1.length() == index) {
					if (s2.contains(allowStr.toString())) {
						result[0]=true;
					}
					return;
				}
				isContain(s1, s2, ++index, allowStr, new HashMap<>(allowedChar), allChar, result);
			}
		}


	}

}
