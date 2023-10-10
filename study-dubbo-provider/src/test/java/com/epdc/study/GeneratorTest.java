package com.epdc.study;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

import java.util.Collections;

/**
 * @author duansm@akulaku.com
 * Created by devin on 2022-12-29.
 */
public class GeneratorTest {

	@Test
	public void test() {
		FastAutoGenerator.create("jdbc:mysql://localhost:3307/test", "root", "123456")
				.globalConfig(builder -> {
					builder.author("epdc") // 设置作者
//							.enableSwagger() // 开启 swagger 模式
							.fileOverride() // 覆盖已生成文件
							.outputDir("/Users/devin/mybatisplus"); // 指定输出目录
				})
				.packageConfig(builder -> {
					builder.parent("com.epdc.study") // 设置父包名
//							.moduleName("Study") // 设置父包模块名
							.pathInfo(Collections.singletonMap(OutputFile.xml, "/Users/devin/mybatisplus")); // 设置mapperXml生成路径
				})
				.strategyConfig(builder -> {
					builder.addInclude("account_tbl") // 设置需要生成的表名
							.addInclude("order_tbl")
							.addInclude("storage_tbl")
							.addInclude("undo_log");
				})
				.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
				.execute();
	}

}
