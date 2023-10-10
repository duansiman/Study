package com.epdc.study.junit;

import com.epdc.study.StudyBootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-08-10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest
public class SpringMvc2Test {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testHello() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/hello"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("hello rose"))
				.andDo(MockMvcResultHandlers.print());
	}
}
