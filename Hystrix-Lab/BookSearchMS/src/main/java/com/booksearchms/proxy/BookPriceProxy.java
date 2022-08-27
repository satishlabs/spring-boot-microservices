package com.booksearchms.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.booksearchms.info.BookPriceInfo;


@FeignClient(name = "BookPriceMS")
public interface BookPriceProxy {
	
	@GetMapping("/bookPrice")
	public BookPriceInfo getBookPrice();
	
}
