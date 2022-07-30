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

	public static final String RATINGS_QUEUE = "MyRatings-Queue";
	public static final String RATINGS_EXCHNAGE = "MyRatings-Exchange";

	@Bean(name = "myRatingsQueue")
	Queue createRatingsQueue() {
		return QueueBuilder.durable(RATINGS_QUEUE).build();
	}

	@Bean(name = "myRatingsExchange")
	Exchange createRatingsExchange() {
		return ExchangeBuilder.topicExchange(RATINGS_EXCHNAGE).build();
	}

	@Bean
	Binding ratingBinding(Queue myRatingQueue, TopicExchange myRatingExchange) {
		return BindingBuilder.bind(myRatingQueue).to(myRatingExchange).with(RATINGS_QUEUE);
	}
}
