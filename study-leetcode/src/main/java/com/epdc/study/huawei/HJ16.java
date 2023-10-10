package com.epdc.study.huawei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-11-04.
 */
public class HJ16 {

	public static void main(String[] args) {
//		goods.put(1, Arrays.asList(800, 2, 0));
//		goods.put(2, Arrays.asList(400, 5, 1));
//		goods.put(3, Arrays.asList(300, 5, 1));
//		goods.put(4, Arrays.asList(400, 3, 0));
//		goods.put(5, Arrays.asList(500, 2, 0));
//		System.out.println(maxSatisfaction(1000, 5, goods, new HashMap<>()));

//		Map<Integer, List<Integer>> masterGoods = new HashMap<>();
//		Map<Integer, List<Integer>> slaverGoods = new HashMap<>();
//
//		masterGoods.put(1, Arrays.asList(500, 1, 0));
//		masterGoods.put(2, Arrays.asList(400, 4, 0));
//		masterGoods.put(5, Arrays.asList(200, 5, 0));
//		masterGoods.put(6, Arrays.asList(500, 4, 0));
//		masterGoods.put(7, Arrays.asList(400, 4, 0));
//
//		slaverGoods.put(3, Arrays.asList(300, 5, 1));
//		slaverGoods.put(4, Arrays.asList(400, 5, 1));
//		System.out.println(maxSatisfaction(1500, 7, masterGoods, slaverGoods));

		List<List<Integer>> result = new ArrayList<>();
		combine(Arrays.asList(1,2,3,4), 2, 1, result, new LinkedList<Integer>());
		System.out.println(result);
	}

	private static int maxSatisfaction(Integer amount, Integer count, Map<Integer, List<Integer>> masterGoods, Map<Integer, List<Integer>> slaverGoods) {
		if (count <= 0) {
			return 0;
		}

		int[] max = new int[]{0};
		masterGoods.forEach((key, good) -> {
			Integer v = good.get(0);
			Integer p = good.get(1);
			Integer q = good.get(2);
		});
		return max[0];
	}

	private static void combine(List<Integer> nums, int k, int start, List<List<Integer>> result, LinkedList<Integer> select) {
		if (select.size() == k) {
			result.add(new ArrayList<>(select));
			return;
		}
		for (int i = start; i <= nums.size(); i++) {
			select.add(nums.get(i-1));
			combine(nums, k, i+1, result, select);
			select.removeLast();
		}
	}

}
