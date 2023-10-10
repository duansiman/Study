package com.epdc.study;

import com.epdc.study.service.IOrderTblService;
import io.seata.core.context.RootContext;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-12-29.
 */
@DubboService
public class OrderServiceImpl implements OrderService {

	@DubboReference
	private AccountService accountService;
	@Autowired
	private IOrderTblService orderTblService;

	@Override
	public Order create(String userId, String commodityCode, int orderCount) {
		System.out.println("Order Service Begin ... xid: " + RootContext.getXID());

		// 计算订单金额
		int orderMoney = calculate(commodityCode, orderCount);

		// 从账户余额扣款
		accountService.debit(userId, orderMoney);

		final Order order = new Order();
		order.userId = userId;
		order.commodityCode = commodityCode;
		order.count = orderCount;
		order.money = orderMoney;

		orderTblService.save(order);

		System.out.println(String.format(
				"Order Service SQL: insert into order_tbl (user_id, commodity_code, count, money) values (%s, %s, %s, %s)",
				userId, commodityCode, orderCount, orderMoney));

		System.out.println("Order Service End ... Created " + order);
		return order;
	}

	private int calculate(String commodityId, int orderCount) {
		return 200 * orderCount;
	}

}
