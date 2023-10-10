package com.epdc.study;

import com.epdc.study.service.IStorageTblService;
import io.seata.core.context.RootContext;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-12-29.
 */
@DubboService
public class StockServiceImpl implements StockService {

	@Autowired
	IStorageTblService storageTblService;

	@Override
	public void deduct(String commodityCode, int count) {
		System.out.println("Stock Service Begin ... xid: " + RootContext.getXID());
		System.out.println(String.format("Deducting inventory SQL: update stock_tbl set count = count - %s where commodity_code = %s", count,
				commodityCode));
		storageTblService.update(count, commodityCode);
		System.out.println("Stock Service End ... ");

	}

	@Override
	public void batchDeduct(String commodityCode, int count) {
		System.out.println("Stock Service Begin ... xid: " + RootContext.getXID());
		System.out.println(String.format("Deducting inventory SQL: update stock_tbl set count = count - %s where commodity_code = %s", count,
				commodityCode));

//		jdbcTemplate.batchUpdate(
//				"update stock_tbl set count = count - " + count + " where commodity_code = '" + commodityCode + "'",
//				"update stock_tbl set count = count - " + count + " where commodity_code = '" + commodityCode + "'");
		System.out.println("Stock Service End ... ");

	}
}
