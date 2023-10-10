package com.epdc.study.collection;

import org.junit.Test;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2023-01-31.
 */
public class PriorityBlockingQueueTest {

	@Test
	public void test() {
		PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
		queue.put(2);
		queue.put(3);
		queue.put(1);
		System.out.println(queue.poll());
	}

}
