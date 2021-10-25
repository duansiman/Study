package com.epdc.study;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LeetCode0001 {

    /**
     * 1. 两数之和
     * 快速定位一个元素是否存在，可以使用hash table 数据结构
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length/2);
        for(int i=0; i<nums.length; i++) {
            int remaining = target - nums[i];
            if(!isOverflow(target, nums[i], remaining)
                    && map.containsKey(remaining)) {
                return new int[]{map.get(remaining), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("not found");
    }

    /**
     * 数字溢出问题判断
     */
    private boolean isOverflow(int sum1, int sum2, int result){
        if (sum1 > 0 && sum2 < 0 && result < 0) {
            return true;
        } else if (sum1 < 0 && sum2 > 0 && result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new LeetCode0001().twoSum(new int[]{1, 2, 5, 4}, 9)));
    }
}
