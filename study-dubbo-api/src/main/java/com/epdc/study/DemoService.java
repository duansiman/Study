package com.epdc.study;

import java.util.concurrent.CompletableFuture;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-12-29.
 */
public interface DemoService {

	String sayHello(String name);

	default CompletableFuture<String> sayHelloAsync(String name) {
		return CompletableFuture.completedFuture(sayHello(name));
	}

}
