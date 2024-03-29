package com.satishlabs.booksearch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.satishlabs.booksearch.info.BookInfo;
import com.satishlabs.booksearch.info.BookPriceInfo;

@RestController
public class BookSearchController {
	static Logger logInfo = LoggerFactory.getLogger(BookSearchController.class);

	@Value(value = "${server.port}")
	String serverPort;

	@GetMapping("/mybook/{bookId}")
	public BookInfo getBookById(@PathVariable Integer bookId) {
		logInfo.info("---- BookSearchController --- getBookById() ----");
		BookInfo bookInfo = new BookInfo(bookId, "Spring Boot", "Satish", "Web", "SatishLabs");

		// Option-1: Using RestTemplate
		String serverURL = "http://localhost:9100";
		System.out.println("serverURL: " + serverURL);

		String endpoint = serverURL + "/bookprice/" + bookId;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<BookPriceInfo> responseEntity = restTemplate.getForEntity(endpoint, BookPriceInfo.class);
		BookPriceInfo bookPriceInfo = responseEntity.getBody();
		
		bookInfo.setPrice(bookPriceInfo.getPrice());
		bookInfo.setOffer(bookPriceInfo.getOffer());
		bookInfo.setServerPort(bookPriceInfo.getServerPort());
		return bookInfo;
	}
}
