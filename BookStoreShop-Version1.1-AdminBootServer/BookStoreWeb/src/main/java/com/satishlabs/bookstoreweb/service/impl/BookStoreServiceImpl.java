/**
 * 
 */
package com.satishlabs.bookstoreweb.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.satishlabs.bookstoreweb.config.BookStoreWebConfig;
import com.satishlabs.bookstoreweb.dto.Book;
import com.satishlabs.bookstoreweb.dto.BookInfo;
import com.satishlabs.bookstoreweb.dto.UserRating;
import com.satishlabs.bookstoreweb.service.BookStoreService;
import com.satishlabs.rabbitmq.Order;
import com.satishlabs.rabbitmq.OrderInfo;
import com.satishlabs.rabbitmq.OrderItem;
import com.satishlabs.rabbitmq.UserRatingInfo;

/**
 * @author Satish
 * 
 *         Jul 18, 2022
 */

@Service
public class BookStoreServiceImpl implements BookStoreService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	static Logger logInfo = LoggerFactory.getLogger(BookStoreServiceImpl.class);

	Map<Integer, Book> booksMap = new LinkedHashMap<>();

	@Override
	public List<String> getAuthorsList() {
		logInfo.info("----BookStoreServiceImpl --- getAuthorsList()----");
		List<String> authorsList = new ArrayList<>();
		authorsList.add("All Authors");
		authorsList.add("Srinivas");
		authorsList.add("Vas");
		authorsList.add("Sri");
		return authorsList;

	}

	@Override
	public List<String> getCategoryList() {
		logInfo.info("----BookStoreServiceImpl --- getCategoryList()----");
		List<String> catList = new ArrayList<>();
		catList.add("All Categories");
		catList.add("Web");
		catList.add("Spring");
		return catList;

	}

	@Override
	public List<Book> getMyBooks(String author, String category) {
		logInfo.info("----BookStoreServiceImpl --- getMyBooks()----");
		if (author == null || author.length() == 0) {
			author = "All Authors";
		}
		if (category == null || category.length() == 0) {
			category = "All Categories";
		}
		// Invoke BookSearchMS
		RestTemplate bookSearchRest = new RestTemplate();
		String endpoint = "http://localhost:8000/mybooks/" + author + "/" + category;
		List<Map> list = bookSearchRest.getForObject(endpoint, List.class);
		List<Book> bookList = new ArrayList<>();
		for (Map mymap : list) {
			Book mybook = convertMapToBook(mymap);
			bookList.add(mybook);
			booksMap.put(mybook.getBookId(), mybook);
		}
		return bookList;
	}

	@Override
	public Book getBookByBookId(Integer bookId) {
		logInfo.info("----BookStoreServiceImpl --- getBookByBookId()----");
		System.out.println(bookId);
		Book mybook = booksMap.get(bookId);
		return mybook;

	}

	@Override
	public BookInfo getBookInfoByBookId(Integer bookId) {
		logInfo.info("----BookStoreServiceImpl --- getBookInfoByBookId()----");
		RestTemplate bookSearchRest = new RestTemplate();
		String endpoint = "http://localhost:8000/mybook/" + bookId;
		BookInfo bookInfo = bookSearchRest.getForObject(endpoint, BookInfo.class);
		return bookInfo;

	}

	@Override
	public void placeOrder(Map<Integer, Book> mycartMap) {
		logInfo.info("----2. BookStoreServiceImpl --- placeOrder()----");

		List<OrderItem> itemList = new ArrayList<>();
		double totalPrice = 0.0;
		int totalQuantity = 0;
		for (Book mybook : mycartMap.values()) {
			Integer bookId = mybook.getBookId();
			// Invoke BookPrice Controller
			RestTemplate bookPriceRest = new RestTemplate();
			String priceEndpoint = "http://localhost:9000/offerprice/" + bookId;
			double offerPrice = bookPriceRest.getForObject(priceEndpoint, Double.class);
			OrderItem item = new OrderItem(0, bookId, 1, offerPrice);
			itemList.add(item);
			totalPrice = totalPrice + offerPrice;
			totalQuantity = totalQuantity + 1;
		}
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		String orderDate = formatter.format(today);
		System.out.println(orderDate);
		Order order = new Order(orderDate, "U-111", totalQuantity, totalPrice, "New");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder(order);
		orderInfo.setItemsList(itemList);

		/*
		 * // Invoke PlaceOrder MS String orderEndpoint =
		 * "http://localhost:7000/placeorder"; RestTemplate orderRest = new
		 * RestTemplate(); orderRest.put(orderEndpoint, orderInfo);
		 * System.out.println("Order Placed");
		 */

		// Send Order Message to RabbitMQ
		rabbitTemplate.convertAndSend(BookStoreWebConfig.ORDER_QUEUE, orderInfo);
		System.out.println("Order Placed....");

	}

	public void addUserRating(UserRating userRating) {
		logInfo.info("----2. BookStoreServiceImpl --- addUserRating()----");
		// Invoke UserRating MS
		/*
		 * String ratingEndpoint = "http://localhost:6500/addusrrating"; RestTemplate
		 * ratingRest = new RestTemplate(); ratingRest.put(ratingEndpoint, userRating);
		 */
		UserRatingInfo userRatingInfo = new UserRatingInfo(userRating.getUserId(), userRating.getBookId(),
				userRating.getRating(), userRating.getReview());
		rabbitTemplate.convertAndSend(BookStoreWebConfig.USER_RATING_QUEUE, userRatingInfo);
		System.out.println("Rating Added...");
	}

	@Override
	public List<UserRating> getMyRatings(String userId) {
		logInfo.info("----BookStoreServiceImpl --- getMyRatings()----");
		List<UserRating> ratingsList = new ArrayList<>();
		String ratingEndpoint = "http://localhost:6500/userrating/" + userId;
		RestTemplate ratingRest = new RestTemplate();
		List<Map> mymap = ratingRest.getForObject(ratingEndpoint, List.class);
		for (Map map : mymap) {
			UserRating urtaing = convertMapToUserRating(map);
			ratingsList.add(urtaing);
			System.out.println(map);
		}
		return ratingsList;
	}

	private UserRating convertMapToUserRating(Map map) {
		UserRating rating = new UserRating();
		rating.setRatingId(new Integer(map.get("ratingId").toString()));
		rating.setUserId(map.get("userId").toString());
		rating.setBookId(new Integer(map.get("bookId").toString()));
		rating.setRating(new Double(map.get("rating").toString()));
		rating.setReview(map.get("review").toString());
		return rating;
	}

	private Book convertMapToBook(Map map) {
		Book mybook = new Book();
		mybook.setBookId(Integer.parseInt(map.get("bookId").toString()));
		mybook.setBookName((map.get("bookName").toString()));
		mybook.setAuthor((map.get("author").toString()));
		mybook.setPublications(map.get("publications").toString());
		mybook.setCategory(map.get("category").toString());
		return mybook;
	}

}
