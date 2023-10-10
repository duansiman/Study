package com.epdc.study.huawei;

import java.util.Scanner;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-10-17.
 */
public class HJ1 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String str = in.nextLine();
		int lastIndexOfBlackSpace = str.lastIndexOf(" ");
		System.out.println(lastIndexOfBlackSpace);
		if (lastIndexOfBlackSpace == -1) {
			System.out.println(str.length());
		} else {
			System.out.println(str.length() - lastIndexOfBlackSpace - 1);
		}
	}

}
