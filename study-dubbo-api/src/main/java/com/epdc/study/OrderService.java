package com.epdc.study;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-12-29.
 */
public interface OrderService {

	/**
	 * 创建订单
	 *
	 * @param userId        用户ID
	 * @param commodityCode 商品编号
	 * @param orderCount    订购数量
	 * @return 生成的订单 order
	 */
	Order create(String userId, String commodityCode, int orderCount);

}
