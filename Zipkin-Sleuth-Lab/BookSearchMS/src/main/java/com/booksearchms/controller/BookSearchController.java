package com.booksearchms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booksearchms.feign.BookPriceProxy;

@RestController
public class BookSearchController {
	@Autowired
	private BookPriceProxy bookPriceProxy;
	
	@GetMapping("/mybooks")
	public String getBooks() {
		String msg = bookPriceProxy.getBookPrice();
		return msg;
	}
}
