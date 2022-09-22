package com.bookstoreweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class BookStoreWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreWebApplication.class, args);
	}
	
	@Bean
	public Sampler defSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

}
