package com.satishlabs.booksearch.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookSearchConfig {
	public static final String RATINGS_QUEUE="MyRatings-Queue";
	public static final String RATINGS_EXCHNAGE = "MyRatings-Exchange";
	
	public static final String INVENTORY_QUEUE="MyInventory-Queue";
	public static final String INVENTORY_EXCHANGE="MyInventory-Exchange";
	
	@Bean(name = "myRatingsQueue")
	Queue createRatingsQueue() {
		return QueueBuilder.durable(RATINGS_QUEUE).build();
	}
	
	@Bean(name = "myRatingsExchange")
	Exchange createRatingsExchange() {
		return ExchangeBuilder.topicExchange(RATINGS_EXCHNAGE).build();
	}
}
