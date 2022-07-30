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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.satishlabs.placeorder.dto.OrderInfo;
import com.satishlabs.placeorder.entity.Order;
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
	
	@PutMapping("/placeorder")
	public void placeOrder(@RequestBody OrderInfo orderInfo) {
		logInfo.info("--- OrderController --- placeorder() ----");
		orderService.placeOrder(orderInfo);
	}
	
	@GetMapping("/myorders/{userId}")
	public List<Order> getOrdersByUserId(@PathVariable String userId){
		logInfo.info("--- OrderController --- getOrdersByUserId() ----");
		List<Order> orderList = orderService.getOrdersByUserId(userId);
		return orderList;
	}
	
	@GetMapping("/myorder/{orderId}")
	public Order getOrderByOrder(@PathVariable Integer orderId) {
		logInfo.info("--- OrderController --- getOrderByOrder() ----");
		Order myorder = orderService.getOrderByOrderId(orderId);
		return myorder;
	}
	
}
