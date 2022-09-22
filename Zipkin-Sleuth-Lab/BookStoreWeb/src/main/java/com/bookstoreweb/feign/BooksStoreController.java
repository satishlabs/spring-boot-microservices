package com.bookstoreweb.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksStoreController {
	
	@Autowired
	private BookSearchProxy bookSearchProxy;
	
	@GetMapping("/books")
	public String getBooks() {
		System.out.println("---BookStoreController -- getBooks()---");
		String msg = bookSearchProxy.getBooks();
		return msg;
	}
}
