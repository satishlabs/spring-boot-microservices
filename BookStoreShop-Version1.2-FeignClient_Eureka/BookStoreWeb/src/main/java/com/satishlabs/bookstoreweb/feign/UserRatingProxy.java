package com.satishlabs.bookstoreweb.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.satishlabs.bookstoreweb.dto.BookRatingInfo;
import com.satishlabs.bookstoreweb.dto.UserRating;




//@FeignClient(value = "UserRatingMS",url = "http://localhost:6500")
@FeignClient(name = "UserRatingMS")
public interface UserRatingProxy {
	
	@GetMapping("/userrating/{userId}")
	public List<UserRating> getUserRatingByUserId(@PathVariable String userId);
	
	@GetMapping("/bookrating/{bookId}")
	public BookRatingInfo getBookRatingByBookId(@PathVariable Integer bookId);
}
