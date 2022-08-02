package com.satishlabs.placeorder.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satishlabs.placeorder.entity.MyOrderItem;

public interface OrderItemDAO extends JpaRepository<MyOrderItem, Integer>{
	public List<MyOrderItem> getOrderItemsByOrderId(Integer orderId);
}
