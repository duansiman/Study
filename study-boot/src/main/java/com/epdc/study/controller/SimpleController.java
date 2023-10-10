package com.epdc.study.controller;

import com.epdc.study.service.HelloService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-08-10.
 */
@RestController
public class SimpleController {

	@GetMapping("/simple")
	public String hello(){
		return "hello simple";
	}
}
