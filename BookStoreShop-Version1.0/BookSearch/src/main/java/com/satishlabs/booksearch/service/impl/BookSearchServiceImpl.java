package com.satishlabs.booksearch.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.satishlabs.booksearch.dao.BookDAO;
import com.satishlabs.booksearch.dao.BookInventoryDAO;
import com.satishlabs.booksearch.dao.BookRatingDAO;
import com.satishlabs.booksearch.dto.BookInfo;
import com.satishlabs.booksearch.dto.BookPriceInfo;
import com.satishlabs.booksearch.entity.Book;
import com.satishlabs.booksearch.entity.BookInventory;
import com.satishlabs.booksearch.entity.BookRating;
import com.satishlabs.booksearch.service.BookSearchService;

@Service
@Transactional
public class BookSearchServiceImpl implements BookSearchService{
	static Logger logInfo = LoggerFactory.getLogger(BookSearchServiceImpl.class);
	@Autowired
	private BookDAO bookDAO;
	
	@Autowired
	private BookRatingDAO bookRatingDAO;
	
	@Autowired
	private BookInventoryDAO bookInventoryDAO;

	@Override
	public List<Book> getBooks(String author, String category) {
		logInfo.info("----BookSearchServiceImpl --- getBooks()--- ");
		List<Book> mybooks = new ArrayList<>();
		
		if(author.equals("All Authors") && category.equals("All Categories")) {
			mybooks = bookDAO.findAll();
		}else if(author.equals("All Authors")&& !category.equals("All Categories")) {
			mybooks = bookDAO.getBooksByAuthor(author);
		}else if(!author.equals("All Authors")&& category.equals("All Categories")) {
			mybooks = bookDAO.getBooksByCategory(category);
		}else {
			mybooks = bookDAO.getBooksByAuthorAndCategory(author, category);
		}
		return mybooks;
	}

	@Override
	public BookInfo getBookInfo(Integer bookId) {
		logInfo.info("----BookSearchServiceImpl --- getBookInfo()--- ");

		BookInfo bookInfo = new BookInfo();
		
		//1. Book Details
		Book book = bookDAO.findById(bookId).get();
		bookInfo.setBookId(book.getBookId());
		bookInfo.setBookName(book.getBookName());
		bookInfo.setAuthor(book.getAuthor());
		bookInfo.setCategory(book.getCategory());
		bookInfo.setPublications(book.getPublications());
		
		//2. BookRating Details
		BookRating bookRating = bookRatingDAO.findById(bookId).get();
		bookInfo.setAvgRating(bookRating.getAvgRating());
		bookInfo.setNumberOfSearches(bookRating.getNumberOfSeaches());
		
		
		//3. BookInventory Details
		BookInventory bookInventory = bookInventoryDAO.findById(bookId).get();
		bookInfo.setBooksAvailable(bookInventory.getBooksAvailable());
		
		//4. BookPrice Details
		RestTemplate bookPriceRest = new RestTemplate();
		String endpoint = "http://localhost:9000/bookprice/"+bookId;
		System.out.println("Endpoint :"+endpoint);
		BookPriceInfo bookPriceInfo = bookPriceRest.getForObject(endpoint, BookPriceInfo.class);
		bookInfo.setPrice(bookPriceInfo.getPrice());
		bookInfo.setOffer(bookPriceInfo.getOffer());
		
		return bookInfo;
	}

	@Override
	public void updateBookRating(BookRating bookRating) {
		logInfo.info("----BookSearchServiceImpl --- updateBookRating()--- ");
		bookRatingDAO.save(bookRating);
	}

	@Override
	public void updateBookInventory(BookInventory bookInventory) {
		logInfo.info("----BookSearchServiceImpl --- updateBookInventory()--- ");
		bookInventoryDAO.save(bookInventory);
	}

}
