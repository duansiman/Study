package com.epdc.study;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-12-29.
 */
@Service
public class BusinessServiceImpl implements BusinessService {

	@DubboReference
	private StockService stockService;
	@DubboReference
	private OrderService orderService;
	private Random random = new Random();

	@Override
	@GlobalTransactional(timeoutMills = 300000, name = "dubbo-demo-tx")
	public void purchase(String userId, String commodityCode, int orderCount) {
		System.out.println("purchase begin ... xid: " + RootContext.getXID());
		stockService.deduct(commodityCode, orderCount);
		// just test batch update
		//stockService.batchDeduct(commodityCode, orderCount);
		orderService.create(userId, commodityCode, orderCount);
//		if (random.nextBoolean()) {
//			throw new RuntimeException("random exception mock!");
//		}
	}
}
