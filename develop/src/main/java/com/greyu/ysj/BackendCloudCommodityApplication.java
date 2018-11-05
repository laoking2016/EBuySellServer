package com.greyu.ysj;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import com.greyu.ysj.storage.StorageProperties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
//@SpringBootApplication
@MapperScan("com.greyu.ysj.mapper")
//public class BackendCloudCommodityApplication {
@EnableConfigurationProperties(StorageProperties.class)
public class BackendCloudCommodityApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BackendCloudCommodityApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	    return builder.sources(BackendCloudCommodityApplication.class);
	}
}
