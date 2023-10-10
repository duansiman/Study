package com.epdc.study;

import java.io.Serializable;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-12-29.
 */
public class Order implements Serializable {
	/**
	 * The Id.
	 */
	public long id;
	/**
	 * The User id.
	 */
	public String userId;
	/**
	 * The Commodity code.
	 */
	public String commodityCode;
	/**
	 * The Count.
	 */
	public int count;
	/**
	 * The Money.
	 */
	public int money;

	@Override
	public String toString() {
		return "Order{" + "id=" + id + ", userId='" + userId + '\'' + ", commodityCode='" + commodityCode + '\''
				+ ", count=" + count + ", money=" + money + '}';
	}
}

