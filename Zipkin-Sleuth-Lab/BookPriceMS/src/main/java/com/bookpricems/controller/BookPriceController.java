package com.bookpricems.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookPriceController {
	
	Logger logger = LoggerFactory.getLogger(BookPriceController.class);
	
	@GetMapping("/bookprice")
	public String getBookPrice() {
		logger.info("---BookPriceController --- getBookPrice()---");
		String msg = "Ok Guys -- Book Info is Ready -- See and Buy";
		return msg;
	}
}
