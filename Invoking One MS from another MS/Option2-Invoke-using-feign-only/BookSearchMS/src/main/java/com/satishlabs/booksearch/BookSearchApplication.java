package com.satishlabs.booksearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookSearchApplication{
	static Logger logInfo = LoggerFactory.getLogger(BookSearchApplication.class);
	

	public static void main(String[] args) {
		logInfo.info("--- BookSearchApplication -- begin----");
		SpringApplication.run(BookSearchApplication.class, args);
		logInfo.info("--- BookSearchApplication -- end----");
	}

}
