package com.satishlabs.booksearch.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.satishlabs.booksearch.dto.BookPriceInfo;

@FeignClient(value = "BookPriceMS",url = "http://localhost:9000")
public interface BookPriceProxy {
	@GetMapping("/bookprice/{bookId}")
	public BookPriceInfo getBookPrice(@PathVariable Integer bookId);

	@GetMapping("/offerprice/{bookId}")
	public double getOfferPrice(@PathVariable Integer bookId);
}
