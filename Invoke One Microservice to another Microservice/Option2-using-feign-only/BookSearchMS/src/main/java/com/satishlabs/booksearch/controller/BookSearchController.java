package com.satishlabs.booksearch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.satishlabs.booksearch.feign.BookPriceProxy;
import com.satishlabs.booksearch.info.BookInfo;
import com.satishlabs.booksearch.info.BookPriceInfo;

@RestController
public class BookSearchController {
	static Logger logInfo = LoggerFactory.getLogger(BookSearchController.class);

	@Autowired
	private BookPriceProxy bookPriceProxy;

	@GetMapping("/mybook/{bookId}")
	public BookInfo getBookById(@PathVariable Integer bookId) {
		logInfo.info("---- BookSearchController --- getBookById() ----");
		BookInfo bookInfo = new BookInfo(bookId, "Spring Boot", "Satish", "Web", "SatishLabs");

		// Option-2: Using Feign Only
		BookPriceInfo bookPriceInfo = bookPriceProxy.getBookPrice(bookId);

		bookInfo.setPrice(bookPriceInfo.getPrice());
		bookInfo.setOffer(bookPriceInfo.getOffer());
		bookInfo.setServerPort(bookPriceInfo.getServerPort());
		return bookInfo;
	}
}
