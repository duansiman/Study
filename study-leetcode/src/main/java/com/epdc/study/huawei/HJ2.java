package com.epdc.study.huawei;

import java.util.Objects;
import java.util.Scanner;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-10-18.
 */
public class HJ2 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String str = in.nextLine();
		char ch = in.nextLine().charAt(0);
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (Objects.equals(Character.toUpperCase(str.charAt(i)), Character.toUpperCase(ch))) {
				count++;
			}
		}
		System.out.println(count);
	}

}
