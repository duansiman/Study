package com.epdc.study;

import com.epdc.study.entity.AccountTbl;
import com.epdc.study.service.IAccountTblService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-12-29.
 */
@DubboService
public class DemoServiceImpl implements DemoService {

	@Autowired
	IAccountTblService accountTblService;

	@Override
	public String sayHello(String name) {
		AccountTbl accountTbl = accountTblService.getById(1);
		System.out.println("Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
		return String.format("%s -> say hello: %s", name, accountTbl.getUserId());
	}
}
