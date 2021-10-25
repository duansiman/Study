package com.epdc.study;

import java.util.HashSet;

public class LeetCode0003 {

    /**
     * 3.无重复字符的最长子串
     * 滑动窗口，在窗口的字串已经是重复的，可以减少不重复字符串的判断
     */
    public int lengthOfLongestSubstring(String s) {
        HashSet<Character> set = new HashSet<>();
        int i=0, j=i+1, result = 1;
        int length = s.length();
        if (length == 0) {
            return 0;
        }
        set.add(s.charAt(i));
        while (i < length - 2 && j < length - 1) {
            if (set.contains(s.charAt(j))) {
                set.remove(s.charAt(i++));
            } else {
                set.add(s.charAt(j++));
                result = Math.max(set.size(), result);
            }
        }
        return result;
    }
}
