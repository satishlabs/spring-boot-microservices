package com.satishlabs.bookprice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.satishlabs.bookprice.info.BookPriceInfo;

@RestController
public class BookPriceController {
	
	@Value(value="${server.port}")
	String serverPort;
	
	@GetMapping("/bookprice/{bookId}")
	public BookPriceInfo getBookPrice(@PathVariable Integer bookId) {
		System.out.println("---BookPriceController---getBookPrice()----: " + serverPort);
		BookPriceInfo bookPrice = new BookPriceInfo(bookId, 12000, 10.0, serverPort);
		return bookPrice;
	}

}
