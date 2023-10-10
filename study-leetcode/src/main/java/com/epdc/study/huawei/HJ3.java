package com.epdc.study.huawei;

import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-10-18.
 */
public class HJ3 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int count = in.nextInt();
		TreeSet<Integer> treeSet = new TreeSet<>();
		for (int i = 0; i < count; i++) {
			int num = in.nextInt();
			treeSet.add(num);
		}
		Iterator<Integer> iterator = treeSet.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

}
