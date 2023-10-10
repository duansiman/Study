package com.epdc.study;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-12-29.
 */
public interface AccountService {

	/**
	 * 余额扣款
	 *
	 * @param userId 用户ID
	 * @param money  扣款金额
	 */
	void debit(String userId, int money);

}
