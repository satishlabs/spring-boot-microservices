/**
 * 
 */
package com.satishlabs.placeorder.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.satishlabs.placeorder.entity.MyOrder;
import com.satishlabs.placeorder.service.OrderService;

/**
 * @author Satish

 * Jul 18, 2022
 */
@CrossOrigin
@RestController
public class OrderController {
	static Logger logInfo = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;

	@GetMapping("/myorders/{userId}")
	public List<MyOrder> getOrdersByUserId(@PathVariable String userId){
		logInfo.info("--- OrderController --- getOrdersByUserId() ----");
		List<MyOrder> orderList = orderService.getOrdersByUserId(userId);
		return orderList;
	}
	
	@GetMapping("/myorder/{orderId}")
	public MyOrder getOrderByOrder(@PathVariable Integer orderId) {
		logInfo.info("--- OrderController --- getOrderByOrder() ----");
		MyOrder myorder = orderService.getOrderByOrderId(orderId);
		return myorder;
	}
	
}
