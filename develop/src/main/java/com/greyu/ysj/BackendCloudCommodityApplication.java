package com.greyu.ysj;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import com.greyu.ysj.config.WxMappingJackson2HttpMessageConverter;
import com.greyu.ysj.storage.StorageProperties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
//@SpringBootApplication
@MapperScan("com.greyu.ysj.mapper")
//public class BackendCloudCommodityApplication {
@EnableConfigurationProperties(StorageProperties.class)
public class BackendCloudCommodityApplication extends SpringBootServletInitializer {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate restTemplate = builder.build();
		restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
		return restTemplate;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BackendCloudCommodityApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	    return builder.sources(BackendCloudCommodityApplication.class);
	}
}
