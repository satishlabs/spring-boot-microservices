/**
 * 
 */
package com.satishlabs.userrating.config;

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

/**
 * @author Satish
 * 
 *         Jul 18, 2022
 */

@SpringBootApplication
public class UserRatingConfig implements WebMvcConfigurer {

	public static final String BOOK_RATING_QUEUE = "MyBookRating-Queue";
	public static final String BOOK_RATING_EXCHNAGE = "MyBookRating-Exchange";

	public static final String USER_RATING_QUEUE = "MyUserRating-Queue";
	public static final String USER_RATING_EXCHNAGE = "MyUserRating-Exchange";

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

	@Bean(name = "myUserRatingQueue")
	Queue createUserRatingQueue() {
		return QueueBuilder.durable(USER_RATING_QUEUE).build();
	}

	@Bean(name = "myUserRatingExchange")
	Exchange createUserRatingExchange() {
		return ExchangeBuilder.topicExchange(USER_RATING_EXCHNAGE).build();
	}

	@Bean
	Binding userRatingBinding(Queue myUserRatingQueue, TopicExchange myUserRatingExchange) {
		return BindingBuilder.bind(myUserRatingQueue).to(myUserRatingExchange).with(USER_RATING_QUEUE);
	}
}
