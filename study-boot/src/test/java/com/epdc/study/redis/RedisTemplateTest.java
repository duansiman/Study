package com.epdc.study.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2023-02-25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTemplateTest {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private RedissonClient redissonClient;

	@Test
	public void test() {
		String values = stringRedisTemplate.opsForValue().get("test");
		Assert.assertEquals("test", values);
		System.out.println(values);
	}

	@Test
	public void testTransactional() {
		Object list = redisTemplate.execute(new SessionCallback<Object>() {
			@Override
			public Object execute(RedisOperations redisOperations) throws DataAccessException {
				redisOperations.multi();
				redisOperations.opsForValue().set("test7", "test3");
				redisOperations.opsForValue().set("test8", "test4");
				List exec = redisOperations.exec();
				System.out.println(exec.size());
				return null;
			}
		});
		System.out.println(list);
	}

	@Test
	public void testLua() {
		String script = "return redis.call('get', KEYS[1]) == ARGV[1]";
		Boolean result = redisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class), Arrays.asList("test8"), "test4");
		Assert.assertTrue(result);

		// redis存的json必须是双引号的
		script = "local jsonTable = cjson.decode(redis.call('get', KEYS[1]))" +
				"return jsonTable[ARGV[1]]";
		String value = redisTemplate.execute(new DefaultRedisScript<>(script, String.class), Arrays.asList("json"), "key");
		Assert.assertEquals("value", value);
	}

	@Test
	public void testPipeline() {
		List<Object> objects = redisTemplate.executePipelined(new SessionCallback<Object>() {
			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {
				operations.opsForValue().get("test1");
				operations.opsForValue().get("test2");
				return null;
			}
		});
		System.out.println(objects);
	}

	@Test
	public void testWatch() {
		stringRedisTemplate.execute(new SessionCallback<Object>() {
			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {
				String watch = "count";
				operations.watch(watch);
				int i = Integer.parseInt(operations.opsForValue().get(watch).toString());
				System.out.print(String.format("value %d\n", i));
				operations.multi();
				try {
					System.out.println("sleep start");
					TimeUnit.MINUTES.sleep(1);
					System.out.println("sleep end");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				operations.opsForValue().set(watch, String.valueOf(i+1));
				List exec = operations.exec();
				System.out.println(exec);
				return null;
			}
		});
	}

}
