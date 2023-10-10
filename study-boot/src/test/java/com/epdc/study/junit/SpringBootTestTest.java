package com.epdc.study.junit;

import com.epdc.study.service.HelloService;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-08-10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@ActiveProfiles("dev")
public class SpringBootTestTest {

	@Autowired
	private HelloService helloService;

	@Test
	public void testHello() throws Exception {
		Assert.assertThat(helloService.hello(), Is.is("hello rose"));
	}

}
