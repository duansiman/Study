package com.epdc.study;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-12-29.
 */
public interface BusinessService {

	/**
	 * 用户订购商品
	 *
	 * @param userId        用户ID
	 * @param commodityCode 商品编号
	 * @param orderCount    订购数量
	 */
	void purchase(String userId, String commodityCode, int orderCount);

}
