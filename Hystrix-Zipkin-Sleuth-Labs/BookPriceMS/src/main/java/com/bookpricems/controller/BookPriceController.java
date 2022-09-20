package com.bookpricems.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookpricems.info.BookPriceInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class BookPriceController {
		
	@Value(value = "${server.port}")
	String serverPort;
	
	@HystrixCommand(fallbackMethod = "getBookPriceFallBack")
	@GetMapping("/bookPrice")
	public BookPriceInfo getBookPrice() {
		System.out.println("---BookPriceController---getBookPrice()----: " + serverPort);
		//Problem Case
		 if(1==1) {
		 throw new ArithmeticException();
		 }
		 return null; 

	}
	
	
	public BookPriceInfo getBookPriceFallBack() {
		System.out.println("---BookPriceController---getBookPriceFallBack()----: ");
		
		BookPriceInfo bookPriceInfo = new BookPriceInfo(0, 0, 0, serverPort);
		return bookPriceInfo;
	}
}
