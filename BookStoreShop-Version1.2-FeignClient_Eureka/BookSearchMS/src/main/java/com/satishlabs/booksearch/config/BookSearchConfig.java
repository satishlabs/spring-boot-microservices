package com.satishlabs.booksearch.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BookSearchConfig implements WebMvcConfigurer{
	
	public static final String BOOK_RATING_QUEUE = "MyBookRating-Queue";
	public static final String BOOK_RATING_EXCHNAGE = "MyBookRating-Exchange";
	
	public static final String INVENTORY_QUEUE="MyInventory-Queue";
	public static final String INVENTORY_EXCHANGE="MyInventory-Exchange";
	
	@Bean(name = "myBookRatingQueue")
	Queue createBookRatingQueue() {
		return QueueBuilder.durable(BOOK_RATING_QUEUE).build();
	}

	@Bean(name = "myBookRatingExchange")
	Exchange createBookRatingExchange() {
		return ExchangeBuilder.topicExchange(BOOK_RATING_EXCHNAGE).build();
	}

	@Bean
	Binding bookRatingBinding(Queue myBookRatingQueue, TopicExchange myBookRatingExchange) {
		return BindingBuilder.bind(myBookRatingQueue).to(myBookRatingExchange).with(BOOK_RATING_QUEUE);
	}
	
	//Inventory Details
	
	@Bean(name = "myInventoryQueue")
	Queue createInventoryQueue() {
		return QueueBuilder.durable(INVENTORY_QUEUE).build();
	}
	
	@Bean(name = "myInventoryExchange")
	Exchange createInventoryExchange() {
		return ExchangeBuilder.topicExchange(INVENTORY_EXCHANGE).build();
	}
	
	@Bean
	Binding inventoryBinding(Queue myInventoryQueue,TopicExchange myInventoryExchange) {
		return BindingBuilder.bind(myInventoryQueue).to(myInventoryExchange).with(INVENTORY_QUEUE);
	}
}
