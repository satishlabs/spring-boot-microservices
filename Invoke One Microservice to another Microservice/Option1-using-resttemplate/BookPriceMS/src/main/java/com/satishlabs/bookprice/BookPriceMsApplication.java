package com.satishlabs.bookprice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BookPriceMsApplication{
	static Logger logInfo=LoggerFactory.getLogger(BookPriceMsApplication.class);


	public static void main(String[] args) {
		logInfo.info("--- BookPriceMsApplication -- begin----");
		SpringApplication.run(BookPriceMsApplication.class, args);
		logInfo.info("--- BookPriceMsApplication -- end----");
	}


}
