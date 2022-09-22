package com.booksearchms.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "BookPriceMS")
public interface BookPriceProxy {
	@GetMapping("/bookprice")
	public String getBookPrice();
}
