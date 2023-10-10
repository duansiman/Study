package com.epdc.study;

import com.epdc.study.service.IAccountTblService;
import io.seata.core.context.RootContext;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-12-29.
 */
@DubboService
public class AccountServiceImpl implements AccountService {

	@Autowired
	IAccountTblService accountTblService;

	@Override
	public void debit(String userId, int money) {
		System.out.println("Account Service ... xid: " + RootContext.getXID());
		System.out.println(String.format("Deducting balance SQL: update account_tbl set money = money - %s where user_id = %s", money,
				userId));

		accountTblService.update(money, userId);
		System.out.println("Account Service End ... ");
	}
}
