package com.epdc.study.junit;

import com.epdc.study.controller.HelloController;
import com.epdc.study.service.HelloService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2021-12-24.
 */
public class SpringMvcTest {

	private MockMvc mockMvc;

	@Mock
	private HelloService helloService;

	@InjectMocks
	private HelloController helloController;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
	}

	@Test
	public void testHello() throws Exception {
		Mockito.when(helloService.hello()).thenReturn("testHello");

		mockMvc.perform(MockMvcRequestBuilders
				.get("/hello"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("testHello"))
				.andDo(MockMvcResultHandlers.print());
	}
}
