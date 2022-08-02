package com.satishlabs.bookprice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.satishlabs.bookprice.entity.BookPrice;
import com.satishlabs.bookprice.service.BookPriceService;
import com.satishlabs.bookprice.service.impl.BookPriceServiceImpl;

@CrossOrigin
@RestController
public class BookPriceController {
	static Logger logInfo=LoggerFactory.getLogger(BookPriceController.class);
	@Autowired
	private BookPriceService bookPriceService;
	
	@GetMapping("/bookprice/{bookId}")
	public BookPrice getBookPrice(@PathVariable Integer bookId) {
		logInfo.info("----- BookPriceController --- getBookPrice()---");
		return bookPriceService.getBookPrice(bookId);
	}
	
	@GetMapping("/offerprice/{bookId}")
	public double getOfferPrice(@PathVariable Integer bookId) {
		logInfo.info("----- BookPriceController --- getOfferPrice()---");
		return bookPriceService.getOfferPrice(bookId);
	}
}
