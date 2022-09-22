package com.bookstoreweb.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "BookSearchMS")
public interface BookSearchProxy {
	
	@GetMapping("/mybooks")
	public String getBooks();
}
