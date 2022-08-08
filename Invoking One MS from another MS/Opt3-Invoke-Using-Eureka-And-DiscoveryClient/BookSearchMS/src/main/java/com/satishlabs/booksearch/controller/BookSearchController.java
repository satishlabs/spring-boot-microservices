package com.satishlabs.booksearch.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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
	
	@Autowired
	private DiscoveryClient discoveryClient;

	@GetMapping("/mybook/{bookId}")
	public BookInfo getBookById(@PathVariable Integer bookId) {
		logInfo.info("---- BookSearchController --- getBookById() ----");
		BookInfo bookInfo = new BookInfo(bookId, "Spring Boot", "Satish", "Web", "SatishLabs");
		
		// Option-3: Using Eureka and DiscoveryClient (Without Ribbon)
				
		List<ServiceInstance> instancesList= discoveryClient.getInstances("BookPriceMS");
		for(ServiceInstance myInstance : instancesList) {
			System.out.println("Hello : "+myInstance.getUri());
		}
		
		String baseURL = instancesList.get(0).getUri().toString();
		System.out.println("Base URL: "+baseURL);
		
		String endpoint = baseURL+"/bookprice/"+bookId;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<BookPriceInfo> responseEntity = restTemplate.getForEntity(endpoint, BookPriceInfo.class);
		BookPriceInfo bookPriceInfo = responseEntity.getBody();
		bookInfo.setPrice(bookPriceInfo.getPrice());
		bookInfo.setOffer(bookPriceInfo.getOffer());
		bookInfo.setServerPort(bookPriceInfo.getServerPort());
		
		System.out.println("---BookSearchController  -- getBookById() ---"+bookId+" -- Port"+bookInfo.getServerPort());
		
		return bookInfo;
	}
}
