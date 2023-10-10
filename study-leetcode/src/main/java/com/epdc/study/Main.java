package com.epdc.study;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 题目描述
 * 给定一个整数n，输出1-n的全排列。
 * 解答要求时间限制：1000ms, 内存限制：100MB
 * 输入
 * 每个测试文件只有一个数据，输入一个整数n(0<n≤8)。
 * 输出
 * 输出全排列(每个排列中的数字用空格隔开)，且每组排列注意按字典序输出所有排列（即要先输出123才能输出132，而不能先输出132在输出123）。
 * 样例
 * 输入样例 1 复制
 * 3
 * 输出样例 1
 * 1 2 3
 * 1 3 2
 * 2 1 3
 * 2 3 1
 * 3 1 2
 * 3 2 1
 * 提示样例 1
 * @author duansm@akulaku.com
 * Created by devin on 2022-11-24.
 */
public class Main {

	public static void main(String[] args) {
		List<List<Integer>> result = new ArrayList<>();
		fullSort(5, 5, new ArrayList<>(), result);
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i));
		}
	}

	private static List<Integer> fullSort(int n, int count, List<Integer> uesdNums, List<List<Integer>> result) {
		if (count < 1) {
			return new ArrayList<>();
		}

		for (int i = 1; i <= n ; i++) {
			if (uesdNums.contains(i)) {
				continue;
			}

			uesdNums.add(i);
			uesdNums.addAll(fullSort(n, count - 1, new ArrayList<>(uesdNums), result));
			if (count == 1) {
				result.add(new ArrayList<>(uesdNums));
			}
			uesdNums.remove((Object)i);
		}
		return new ArrayList<>();
	}

}
