package com.satishlabs.booksearch.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BookSearchConfig{
	
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemp() {
		return new RestTemplate();
	}
}
