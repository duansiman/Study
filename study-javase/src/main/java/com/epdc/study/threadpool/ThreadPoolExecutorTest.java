package com.epdc.study.threadpool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-11-10.
 */
public class ThreadPoolExecutorTest {

	public static void main(String[] args) {
//		ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 100, 1, TimeUnit.MINUTES, new LinkedBlockingDeque<>());
//		executor.execute(() -> System.out.println("run ..."));
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
}
