package com.satishlabs.booksearch.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.satishlabs.booksearch.info.BookInfo;

@FeignClient(name = "BookPriceMS")
public interface BookPriceProxy {
	@GetMapping("/bookprice/{bookId}")
	public BookInfo getBookPrice(@PathVariable Integer bookId);

	@GetMapping("/offerprice/{bookId}")
	public double getOfferPrice(@PathVariable Integer bookId);
}
