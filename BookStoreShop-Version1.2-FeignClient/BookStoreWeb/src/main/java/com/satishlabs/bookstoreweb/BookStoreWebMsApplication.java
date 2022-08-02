package com.satishlabs.bookstoreweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BookStoreWebMsApplication implements CommandLineRunner{
	static Logger logInfo=LoggerFactory.getLogger(BookStoreWebMsApplication.class);


	public static void main(String[] args) {
		logInfo.info("--- BookStoreWebMsApplication -- begin----");
		SpringApplication.run(BookStoreWebMsApplication.class, args);
		logInfo.info("--- BookStoreWebMsApplication -- end----");
	}


	@Override
	public void run(String... args) throws Exception {
		logInfo.info("--- BookStoreWebMsApplication -- Launched----");
		
	}

}
