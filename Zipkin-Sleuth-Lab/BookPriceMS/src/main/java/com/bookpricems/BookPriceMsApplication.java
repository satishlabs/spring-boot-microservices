package com.bookpricems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;

@SpringBootApplication
@EnableEurekaClient
public class BookPriceMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookPriceMsApplication.class, args);
	}
	
	@Bean
	public Sampler defSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

}
