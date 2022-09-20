package com.booksearchms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.booksearchms.info.BookInfo;
import com.booksearchms.info.BookPriceInfo;
import com.booksearchms.proxy.BookPriceProxy;


@RestController
public class BookSearchController {
	
	@Autowired
	private BookPriceProxy bookPriceProxy;
	
	@GetMapping("/mybook/{bookId}")
	public BookInfo getBookById(@PathVariable Integer bookId) {
		System.out.println("---BookSearchController -- getBookById()-----");
		BookInfo bookInfo = new BookInfo(bookId, "Master Spring Boot 2", "Srinivas", "JLC", "Java");
		
		BookPriceInfo bookPriceInfo = bookPriceProxy.getBookPrice();
		bookInfo.setPrice(bookPriceInfo.getPrice());
		bookInfo.setOffer(bookPriceInfo.getOffer());
		bookInfo.setServerPort(bookPriceInfo.getServerPort());
		
		System.out.println("---BookController --- getBookById()-- "+bookId+" --Port--"+bookInfo.getServerPort());
		
		return bookInfo; 
	}
}
