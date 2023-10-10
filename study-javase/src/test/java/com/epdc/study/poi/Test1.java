package com.epdc.study.poi;

import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2023-01-05.
 */
public class Test1 {

	@Test
	public void test() throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook(new File("/Users/devin/Documents/乐乐工作/result01.xlsx"));
//		List<String> sheetNames = Arrays.asList("2022年订单明细", "2021年订单明细", "2020年订单明细", "2019年订单明细");
		List<String> sheetNames = Arrays.asList("数据源");
		XSSFWorkbook resultWorkbook = new XSSFWorkbook();
		for (String sheetName : sheetNames) {
			System.out.println(sheetName);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			Map<String, Map<String, Map<String, Integer>>> asin2Site = new HashMap<>();
			for (int i = 1; ; i++) {
				XSSFRow row = sheet.getRow(i);
				if (Objects.isNull(row)) {
					System.out.println(String.format("总行数：%s", i));
					break;
				}
				XSSFCell asin = row.getCell(5);
				XSSFCell month = row.getCell(20);
				XSSFCell site = row.getCell(17);
				XSSFCell salesCount = row.getCell(6);

				if (Objects.isNull(salesCount) || StringUtil.isBlank(salesCount.getRawValue())) {
					System.out.println(String.format("总行数：%s", i));
					break;
				}

				if (Objects.isNull(asin) || StringUtil.isBlank(asin.getStringCellValue())) {
					continue;
				}
				Map<String, Map<String, Integer>> site2Month = asin2Site.getOrDefault(asin.getStringCellValue(), new HashMap<>());
				asin2Site.put(asin.getStringCellValue(), site2Month);

				Map<String, Integer> month2SalesCount = site2Month.getOrDefault(site.getStringCellValue(), new HashMap<>());
				site2Month.put(site.getStringCellValue(), month2SalesCount);

				Integer value = month2SalesCount.getOrDefault(month.getStringCellValue(), 0);
				month2SalesCount.put(month.getStringCellValue(), value + Integer.parseInt(salesCount.getRawValue()));
			}

			XSSFSheet resultWorkbookSheet = resultWorkbook.createSheet(sheetName);
			int[] rowNum = new int[]{0};
			XSSFRow titleRow = resultWorkbookSheet.createRow(rowNum[0]++);
			List<String> titles = Arrays.asList("ASIN", "站点", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");
			for (int i = 0; i < titles.size(); i++) {
				XSSFCell cell = titleRow.createCell(i);
				cell.setCellValue(titles.get(i));
			}

			Set<String> asins = asin2Site.keySet();
			for (String asin : asins) {
				Map<String, Map<String, Integer>> site2Month = asin2Site.get(asin);
				Set<String> sites = site2Month.keySet();
				sites.forEach(site -> {
					XSSFRow row = resultWorkbookSheet.createRow(rowNum[0]++);
					Map<String, Integer> month2SalesCount = site2Month.get(site);

					XSSFCell asinCell = row.createCell(0);
					asinCell.setCellValue(asin);

					XSSFCell siteCell = row.createCell(1);
					siteCell.setCellValue(site);

					for (int i = 2; i < titles.size(); i++) {
						XSSFCell monthCell = row.createCell(i);
						monthCell.setCellValue(month2SalesCount.getOrDefault(titles.get(i), 0));
					}
				});
			}
		}
		resultWorkbook.write(new FileOutputStream(new File("/Users/devin/Documents/乐乐工作/result.xlsx")));
		resultWorkbook.close();
		workbook.close();
	}


	@Test
	public void test2() throws Exception {
			XSSFWorkbook workbook = new XSSFWorkbook(new File("/Users/devin/Documents/乐乐工作/result01.xlsx"));
			List<String> sheetNames = Arrays.asList("数据源");
			XSSFWorkbook resultWorkbook = new XSSFWorkbook();
			for (String sheetName : sheetNames) {
				System.out.println(sheetName);
				XSSFSheet sheet = workbook.getSheet(sheetName);
				Map<String, Map<String, Integer>> unqKey2Months = new HashMap<>();
				for (int i = 1; ; i++) {
					XSSFRow row = sheet.getRow(i);
					if (Objects.isNull(row)) {
						System.out.println(String.format("总行数：%s", i));
						break;
					}

					XSSFCell salesCount = row.getCell(0);
					XSSFCell sku = row.getCell(1);
					XSSFCell customSku = row.getCell(2);
					XSSFCell asin = row.getCell(3);
					XSSFCell category = row.getCell(4);
					XSSFCell yunying = row.getCell(5);
					XSSFCell source = row.getCell(6);
					XSSFCell year = row.getCell(7);
					XSSFCell month = row.getCell(8);

					String unqKey = String.format("%s+%s+%s+%s+%s+%s+%s",
							sku.getStringCellValue(),
							customSku.getStringCellValue(),
							asin.getStringCellValue(),
							category.getStringCellValue(),
							yunying.getStringCellValue(),
							source.getStringCellValue(),
							year.getStringCellValue());


					if (Objects.isNull(salesCount) || StringUtil.isBlank(salesCount.getRawValue())) {
						System.out.println(String.format("总行数：%s", i));
						break;
					}

					if (Objects.isNull(asin) || StringUtil.isBlank(asin.getStringCellValue())) {
						continue;
					}
					Map<String, Integer> month2SalesCount = unqKey2Months.getOrDefault(unqKey, new HashMap<>());
					unqKey2Months.put(unqKey, month2SalesCount);

					Integer value = month2SalesCount.getOrDefault(month.getStringCellValue(), 0);
					month2SalesCount.put(month.getStringCellValue(), value + Integer.parseInt(salesCount.getRawValue()));
				}

				XSSFSheet resultWorkbookSheet = resultWorkbook.createSheet(sheetName);
				int[] rowNum = new int[]{0};
				XSSFRow titleRow = resultWorkbookSheet.createRow(rowNum[0]++);
				List<String> titles = Arrays.asList("unqKey", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");
				for (int i = 0; i < titles.size(); i++) {
					XSSFCell cell = titleRow.createCell(i);
					cell.setCellValue(titles.get(i));
				}

				Set<String> unqKeys = unqKey2Months.keySet();
				for (String unqkey : unqKeys) {
					XSSFRow row = resultWorkbookSheet.createRow(rowNum[0]++);
					XSSFCell asinCell = row.createCell(0);
					asinCell.setCellValue(unqkey);

					Map<String, Integer> month2SalesCount = unqKey2Months.get(unqkey);
					for (int i = 1; i < titles.size(); i++) {
						XSSFCell monthCell = row.createCell(i);
						monthCell.setCellValue(month2SalesCount.getOrDefault(titles.get(i), 0));
					}
				}
			}
			resultWorkbook.write(new FileOutputStream(new File("/Users/devin/Documents/乐乐工作/result03.xlsx")));
			resultWorkbook.close();
			workbook.close();
	}


}
