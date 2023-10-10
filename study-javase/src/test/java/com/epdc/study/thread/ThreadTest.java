package com.epdc.study.thread;

import org.junit.Test;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2023-02-07.
 */
public class ThreadTest {

	public static class Value {
		public volatile int i;
	}

	@Test
	public void test() throws InterruptedException {
		Value value = new Value();
		Thread thread1 = new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			value.i++;
			Thread.yield();
			System.out.println(String.format("thread1 i:%s", value.i));
		});

		Thread thread2 = new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			value.i++;
			Thread.yield();
			System.out.println(String.format("thread2 i:%s", value.i));
		});
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
	}

}
