/**
 * 
 */
package com.satishlabs.placeorder.service;

import java.util.List;

import com.satishlabs.placeorder.entity.MyOrder;

/**
 * @author Satish

 * Jul 18, 2022
 */
public interface OrderService {
	//public void placeOrder(OrderInfo orderInfo);
	public List<MyOrder> getOrdersByUserId(String userId);
	public MyOrder getOrderByOrderId(Integer orderId);
	
}
