package com.satishlabs.booksearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableFeignClients
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class BookSearchApplication{
	static Logger logInfo = LoggerFactory.getLogger(BookSearchApplication.class);
	

	public static void main(String[] args) {
		logInfo.info("--- BookSearchApplication -- begin----");
		SpringApplication.run(BookSearchApplication.class, args);
		logInfo.info("--- BookSearchApplication -- end----");
	}

}
